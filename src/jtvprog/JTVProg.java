/*
 * JTVProg is a service software for National News Agency of Ukraine:
 * UKRINFORM 2011
 * v0.1
 */
package jtvprog;

/**
 * Main class
 * @author Stanislav Nepochatov
 */
public class JTVProg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        logPrint("JTVProg", 3, "старт программы ");
        mainFrame mainWindow = new mainFrame();
    }
    
    public static void logPrint(String component, int type, String message) {
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
        System.out.println("["+ component + "]: " + typeStr + ">> '" + message + "';");
    }
}
