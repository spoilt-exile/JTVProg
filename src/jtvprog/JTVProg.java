/*
 * This code is distributed under terms of GNU GPLv2.
 * *See LICENSE file.
 * ©UKRINFORM 2011-2012
 */

package jtvprog;

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
    
    private static logerFrame logWindow;
    
    /**
     * Main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        logWindow = new logerFrame();
        logWindow.setVisible(true);
        logWindow.setLocation(1360, 0);
        logPrint("JTVProg", 3, "старт программы ");
        try {
            configer = new config();
        } catch (IOException ex) {
            logPrint("JTVProg", 0, "ошибка при создании файла конфигурации");
        } catch (Exception ex) {
            logPrint("JTVProg", 0, "общая ошибка чтения конфигурации");
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
     * Show graphical warning message
     * @param Message text of warning message
     */
    public static void warningMessage (String Message) {
        final JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, Message, "Внимание!", JOptionPane.WARNING_MESSAGE);
    }
}
