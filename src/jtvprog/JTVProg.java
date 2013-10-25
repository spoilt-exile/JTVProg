/**
 * This file is part of JTVProg application (check README).
 * Copyright (C) 2013 Stanislav Nepochatov
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
**/

package jtvprog;

import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Main class
 * @author Stanislav Nepochatov
 */
public class JTVProg {

    /**
     * Configuration object
     */
    public static config configer;
    
    /**
     * Main window object
     */
    public static mainFrame mainWindow;
    
    /**
     * Input window object
     */
    public static inputFrame inputWindow;
    
    /**
     * Log window object
     */
    private static logerFrame logWindow;
    
    /**
     * Processing window object
     */
    public static procFrame procWindow;
    
    /**
     * Clipboard handler object;
     */
    public static clipboardProvider cilper = new clipboardProvider();
    
    /**
     * Global pass channel switch;
     * @since JTVProg v0.3r4
     */
    private static Boolean globalPassSwitch = false;
    
    /**
     * List of passed channels;
     * @since JTVProg v0.3r4
     */
    private static java.util.ArrayList<String> globalPassList = new java.util.ArrayList();
    
    /**
     * Ribbon application object.
     */
    public static AppComponents.RibbonApplication tvApp = new AppComponents.RibbonApplication("RibbonTVProg", "Стрічка: Телепрограма", AppComponents.RibbonApplication.ApplicationRole.CLEINT);
    
    /**
     * Main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        logWindow = new logerFrame();
        logWindow.setVisible(false);
        logWindow.setLocation(700, 0);
        logPrint("JTVProg", 3, "старт программы ");
        try {
            configer = new config();
        } catch (IOException ex) {
            logPrint("JTVProg", 0, "ошибка ввода/вывода при чтении файла конфигурации");
            ex.printStackTrace();
        }
        mainWindow = new mainFrame();
        configer.createMainTable();
    }
    
    /**
     * Print formated messages to System.out<br>
     * <b>Message types:</b><br>
     * <ul>
     * <li>(<b>0</b>): critical error in application;</li>
     * <li>(<b>1</b>): handleable error in application;</li>
     * <li>(<b>2</b>): warning during execution;</li>
     * <li>(<b>3</b>): common information;</li>
     * </ul>
     * @param component component which call this method;
     * @param type message type (see below);
     * @param message string of text message;
     */
    public static void logPrint(Object component, int type, String message) {
        String typeStr = "неизвестно";
        switch (type) {
            case 0:
                typeStr = "критическая ошибка";
                break;
            case 1:
                typeStr = "ошибка";
                break;
            case 2:
                typeStr = "предупреждение";
                break;
            case 3:
                typeStr = "сообщение";
                break;
        }
        if (component instanceof String) {
            //System.out.println("["+ component + "]: " + typeStr + ">> '" + message + "';");
            JTVProg.tvApp.log(type, message);
            logWindow.append(String.valueOf(component), type, message);
        }
        else{
            //System.out.println("["+ component.getClass().toString().substring(14) + "]: " + typeStr + ">> '" + message + "';");
            JTVProg.tvApp.log(type, message);
            logWindow.append(component.getClass().toString().substring(14), type, message);
        }
    }
    
    /**
     * Set log window visibility with given flag
     * @param flag given boolean flag for log window visibility
     */
    public static void setLogWindowVisibilty(Boolean flag) {
        logWindow.setVisible(flag);
    }
    
    /**
     * Show graphical warning message
     * @param Message text of warning message
     */
    public static void warningMessage (String Message) {
        final JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, Message, "Внимание!", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Set channel as passed empty.
     * @param chName name of channel;
     * @param passFlag flag of passing;
     * @since JTVProg v0.3r4
     */
    public static void setPass(String chName, Boolean passFlag) {
        if (passFlag) {
            if (JTVProg.globalPassSwitch == false) {
                JTVProg.globalPassSwitch = true;
                JTVProg.logPrint("JTVProg", 2, "включен режим неполного выпуска.");
            }
            if (!JTVProg.globalPassList.contains(chName)) {
                JTVProg.globalPassList.add(chName);
            }
        } else {
            JTVProg.globalPassList.remove(chName);
            if (JTVProg.globalPassList.isEmpty()) {
                JTVProg.globalPassSwitch = false;
                JTVProg.logPrint("JTVProg", 3, "переход в нормальный режим.");
            }
        }
    }
    
    /**
     * Return string list of passed channels.
     * @return string list.
     * @since JTVProg v0.3r4
     */
    public static String getPassList() {
        StringBuffer buf = new StringBuffer();
        for (String currCh : JTVProg.globalPassList) {
            buf.append(currCh + "\n");
        }
        return buf.toString();
    }
    
    /**
     * Find out pass mode;
     * @return pass flag.
     * @since JTVProg v0.3r4
     */
    public static Boolean isPass() {
        return JTVProg.globalPassSwitch;
    }
    
    /**
     * Reset state of passing channels.
     * @since JTVProg v0.3r4
     */
    public static void resetPass() {
        JTVProg.globalPassList = new java.util.ArrayList<String>();
        JTVProg.globalPassSwitch = false;
    }
    
    /**
    * Clipboard provider for system integration
    * @author Stanislav Nepochatov
    * Code was taken from javapracticies.com
    */
    public static class clipboardProvider implements ClipboardOwner {

        /**
        * Empty implementation of the ClipboardOwner interface.
        */
        @Override
        public void lostOwnership(Clipboard aClipboard, Transferable aContents) {
            //do nothing
        }

        /**
        * Place a String on the clipboard, and make this class the
        * owner of the Clipboard's contents.
        * @param aString future clipboard content
        */
        public void setClipboardContents(String aString){
            StringSelection stringSelection = new StringSelection(aString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, this);
        }

        /**
        * Get the String residing on the clipboard.
        *
        * @return any text found on the Clipboard; if none found, return an
        * empty String.
        */
        public String getClipboardContents() {
            String result = "";
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            //odd: the Object param of getContents is not currently used
            Transferable contents = clipboard.getContents(null);
            boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
            if ( hasTransferableText ) {
                try {
                    result = (String) contents.getTransferData(DataFlavor.stringFlavor);
                }
                catch (UnsupportedFlavorException ex){
                //highly unlikely since we are using a standard DataFlavor
                    System.out.println(ex);
                    ex.printStackTrace();
                }
                catch (IOException ex) {
                    System.out.println(ex);
                    ex.printStackTrace();
                }
            }
            return result;
        }
    }
}
