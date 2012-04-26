/*
 * This code is distributed under terms of GNU GPLv2.
 * *See LICENSE file.
 * ©UKRINFORM 2011-2012
 */

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
            System.out.println("["+ component + "]: " + typeStr + ">> '" + message + "';");
            logWindow.append(String.valueOf(component), type, message);
        }
        else{
            System.out.println("["+ component.getClass().toString().substring(14) + "]: " + typeStr + ">> '" + message + "';");
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
