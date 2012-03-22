/*
 * This code is distributed under terms of GNU GPLv2.
 * *See LICENSE file.
 * ©UKRINFORM 2011-2012
 */

package jtvprog;

import java.io.*;
import javax.swing.table.*;

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
        Integer lastId = Integer.parseInt(ConfigProps.getProperty("tv_set.last_id"));
        for (Integer id = 1; id <= lastId; id++) {
            String tvPattern = "tv_set.channel_" + id;
            try {
            this.Channels.addChannel(
                new String(ConfigProps.getProperty(tvPattern + ".name").getBytes("ISO-8859-1"), "UTF-8"),
                Integer.parseInt(ConfigProps.getProperty(tvPattern + ".fill_order")), 
                Integer.parseInt(ConfigProps.getProperty(tvPattern + ".release_order")), 
                new String(ConfigProps.getProperty(tvPattern + ".file_name").getBytes("ISO-8859-1"), "UTF-8")
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
                "Название канала", "Обработан?", "Сохранен?"
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
        DefaultTableModel mainModel = this.getMainTableModel();
        for (Integer chCounter = 1; chCounter < Channels.getSetSize() + 1; chCounter++) {
            mainModel.addRow(new Object[] {Channels.getChannelByFOrder(chCounter), false, false});
        }
        JTVProg.mainWindow.tvchTable.setModel(mainModel);
    }
}
