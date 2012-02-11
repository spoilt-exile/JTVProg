/*
 * This code is distributed under terms of GNU GPLv2.
 * *See LICENSE file.
 * ©UKRINFORM 2011-2012
 */

package jtvprog;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;
import javax.swing.table.*;

/**
 * XML configuration handle class;
 * @author Stanislav Nepochatov
 */
public class config {
    
    /**
     * Name of configuration file
     */
    private String ConfigName = "tvSet.xml";
    
    /**
     * File object of configuration
     */
    private File ConfigFile;
    
    /**
     * XML doc object for configuration
     */
    private Document ConfigDoc;
    
    /**
     * Channels set
     */
    public chSet Channels = new chSet();
    
    /**
     * Default constructor. Finding files or create new one
     */
    config() throws IOException, ParserConfigurationException, SAXException {
        int picks;
        ConfigFile = new File(ConfigName);
        if(!ConfigFile.exists()) {
            JTVProg.logPrint(this, 2, "создание файла конфигурации");
            ConfigFile.createNewFile();
            FileOutputStream destStream = new FileOutputStream(ConfigFile);
            InputStream sourceIStream = JTVProg.class.getResourceAsStream("tv.xml");
            while ((picks = sourceIStream.read()) != -1) {
                destStream.write(picks);
            }
            destStream.close();
            sourceIStream.close();
        }
        else {
            JTVProg.logPrint(this, 3, "файл конфигурации обнаружен");
        }
        
        DocumentBuilderFactory configFact = DocumentBuilderFactory.newInstance();
        configFact.setValidating(false);
        DocumentBuilder configBld;
        configBld = configFact.newDocumentBuilder();
        ConfigDoc = configBld.parse(ConfigFile);
        ConfigDoc.getDocumentElement().normalize();
        this.createChOrder();
    }
    
    /**
     * Create set of channels
     */
    private void createChOrder() {
        NodeList chNodes = ConfigDoc.getElementsByTagName("ch-entry");
        JTVProg.logPrint(this, 3, ("количество каналов:" + chNodes.getLength()));
        for (Integer chCounter = 0; chCounter < chNodes.getLength(); chCounter++) {
            Element currentChannel = (Element) chNodes.item(chCounter);
            Integer chFillOrder = Integer.parseInt(currentChannel.getAttribute("fid"));
            Integer chReleaseOrder = Integer.parseInt(currentChannel.getAttribute("rid"));
            String chName = currentChannel.getChildNodes().item(1).getTextContent().trim();
            String chFile = currentChannel.getChildNodes().item(3).getTextContent().trim();
            JTVProg.logPrint(this, 3, ("канал ('" + chName + "', " + chFillOrder + ", " + chReleaseOrder + ", " + chFile + ") обнаружен"));
            Channels.addChannel(chName, chFillOrder, chReleaseOrder, chFile);
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
