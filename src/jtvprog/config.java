/*
 * This code is distributed under terms of GNU GPLv2.
 * *See LICENSE file.
 * ©UKRINFORM 2011-2012
 */

package jtvprog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.swing.table.DefaultTableModel;

/**
 * Properties configuration handle class;
 * @author Stanislav Nepochatov
 */
public class config {
    
    /**
     * Name of configuration file
     */
    private String ConfigName = "tvSet.properties";
    
    /**
     * File object of configuration
     */
    private File ConfigFile;
    
    /**
     * JTVProg properties config file
     */
    private java.util.Properties ConfigProps = new java.util.Properties();
    
    /**
     * Channels set
     */
    public chSet Channels = new chSet();
    
    /**
     * Channels processor for file processing
     */
    public chProcSet ChannelProcessor;
    
    /**
     * Window icon image object
     */
    public java.awt.Image jtvprogIcon = java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/jtvprog/jtvprog-logo.png"));
    
    /**
     * Main table model
     */
    private DefaultTableModel tvMainTable = this.getMainTableModel();
    
    /**
     * System encoding charset
     */
    public String sysEncoding = System.getProperty("file.encoding");
    
    /**
     * Default constructor, find properties file or create it
     * @throws IOException 
     */
    config() throws IOException {
        ConfigFile = new File(ConfigName);
        if (!ConfigFile.exists()) {
            JTVProg.logPrint(this, 2, "создание файла конфигурации");
            ConfigFile.createNewFile();
            java.util.Properties tempProps = new java.util.Properties();
            tempProps.load(JTVProg.class.getResourceAsStream("tv.properties"));
            tempProps.store(new FileWriter(ConfigFile), null);
        }
        else {
            JTVProg.logPrint(this, 3, "файл конфигурации обнаружен");
        }
        this.ConfigProps.load(new java.io.FileInputStream(ConfigFile));
        this.resumeChannelSet();
    }
    
    /**
     * Resume channel set from properties
     */
    private void resumeChannelSet() {
        String propsEncoding = this.ConfigProps.getProperty("encoding");
        JTVProg.logPrint("JTVPRog", 3, "системная кодировка: " + this.sysEncoding);
        if (!sysEncoding.equals(propsEncoding)) {
            JTVProg.logPrint(this, 2, "файл конфигурации не соответствует системной кодировке [" + sysEncoding + "]!");
        } else {
            JTVProg.logPrint(this, 3, "системная кодировка совпадает с кодировкой файла");
        }
        Integer lastId = Integer.parseInt(ConfigProps.getProperty("tv_set.last_id"));
        for (Integer id = 1; id <= lastId; id++) {
            String tvPattern = "tv_set.channel_" + id;
            try {
                String encChName = new String(ConfigProps.getProperty(tvPattern + ".name").getBytes("ISO-8859-1"), propsEncoding);;
                String encFileName = new String(ConfigProps.getProperty(tvPattern + ".file_name").getBytes("ISO-8859-1"), propsEncoding);;
                if (!sysEncoding.equals(propsEncoding)) {
                    //encChName = new String(encChName.getBytes(propsEncoding), sysEncoding);
                    //encFileName = new String(encChName.getBytes(propsEncoding), sysEncoding);
                }
                this.Channels.addChannel(
                    //new String(ConfigProps.getProperty(tvPattern + ".name").getBytes("ISO-8859-1"), this.sysEncoding),
                    encChName, 
                    Integer.parseInt(ConfigProps.getProperty(tvPattern + ".fill_order")), 
                    Integer.parseInt(ConfigProps.getProperty(tvPattern + ".release_order")), 
                    encFileName
                    //new String(ConfigProps.getProperty(tvPattern + ".file_name").getBytes("ISO-8859-1"), this.sysEncoding)
                );
            } catch (UnsupportedEncodingException ex) {
                JTVProg.logPrint(this, 1, "ошибка декодирования файла конфигурации");
            }
        }
    }
    
    /**
     * Store set to properties file and save it
     */
    public void storeChannelSet() {
        Channels.storeToProperties(ConfigProps);
        ConfigProps.setProperty("encoding", sysEncoding);
        try {
            ConfigProps.store(new FileWriter(ConfigFile), null);
        } catch (IOException ex) {
            JTVProg.logPrint(this, 0, "ошибка записи файла конфигурации");
        } finally {
            JTVProg.logPrint(this, 3, "файл конфигурации записан");
        }
    }
    
    /**
     * Return empty main table model
     * @return table model
     */
    private DefaultTableModel getMainTableModel() {
            return new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Название канала", "Сохранен", "Обработан"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
    }
    
    /**
     * Draw main table with existed data
     */
    public void createMainTable() {
        this.tvMainTable = this.getMainTableModel();
        for (Integer chCounter = 1; chCounter < Channels.getSetSize() + 1; chCounter++) {
            this.tvMainTable.addRow(new Object[] {Channels.getChannelByFOrder(chCounter), false, false});
        }
        JTVProg.mainWindow.tvchTable.setModel(this.tvMainTable);
    }
    
    /**
     * Mark channel as writed in main table
     * @param chIndex index of channel
     */
    public void markWrited(Integer chIndex) {
        this.tvMainTable.setValueAt(true, chIndex, 1);
    }
    
    /**
     * Mark channel as processed in main table
     * @param chIndex index of channel
     */
    public void markProcessed(Integer chIndex) {
        this.tvMainTable.setValueAt(true, chIndex, 2);
    }
}
