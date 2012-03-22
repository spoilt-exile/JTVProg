/*
 * This code is distributed under terms of GNU GPLv2.
 * *See LICENSE file.
 * ©UKRINFORM 2011-2012
 */

package jtvprog;

/**
 * Loger window class
 * @author Stanislav Nepochatov
 */
public class logerFrame extends javax.swing.JFrame {

    /** Creates new form logerFrame */
    public logerFrame() {
        initComponents();
    }
    
    /**
     * List model for log list
     */
    private javax.swing.DefaultListModel logListModel = new javax.swing.DefaultListModel();
    
    /**
     * Storage for messages
     */
    private java.util.ArrayList<messageEntry> messages = new java.util.ArrayList<messageEntry>();
    
    private class messageEntry {
        
        /**
         * Name of called component
         */
        public String component;
        
        /**
         * Type of messege entry
         */
        public Integer type;
        
        /**
         * Message string
         */
        public String message;
        
        /**
         * Default constructor
         * @param givenType
         * @param givenMessage 
         */
        messageEntry(String givenComponent, Integer givenType, String givenMessage) {
            component = givenComponent;
            type = givenType;
            message = givenMessage;
        }
    }
    
    /**
     * Append log messege to UI list
     * @param component name of component;
     * @param type type of message;
     * @param message text of message
     */
    public void append(String component, Integer type, String message) {
        messages.add(new messageEntry(component, type, message));
        String typeStr = "неизвестно";
        String typeColor = "GRAY";
        switch (type) {
            case 0:
                typeStr = "критическая ошибка";
                typeColor = "RED";
                break;
            case 1:
                typeStr = "ошибка";
                typeColor = "#FF9100";
                break;
            case 2:
                typeStr = "предупреждение";
                typeColor = "GREEN";
                break;
            case 3:
                typeStr = "сообщение";
                typeColor = "BLUE";
                break;
        }
        if (type <= this.logLevel.getSelectedIndex()) {
            this.logListModel.addElement("<html><font color=#836502>["+ component + "]</font>: <font color=" + typeColor + ">" + typeStr + "</font>" + ">> '" + message + "';</html>");
            this.logList.ensureIndexIsVisible(this.logListModel.getSize() - 1);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        logList = new javax.swing.JList();
        logLevel = new javax.swing.JComboBox();
        logFlush = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Журнал событий");

        logList.setBackground(new java.awt.Color(204, 204, 204));
        logList.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        logList.setModel(logListModel);
        jScrollPane1.setViewportView(logList);

        logLevel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<html><font color=RED>Критические ошибки</font></html>", "<html><font color=#FF9100>Ошибки</font></html>", "<html><font color=GREEN>Предупреждения</font></html>", "<html><font color=BLUE>Сообщения</font></html>" }));
        logLevel.setSelectedIndex(3);
        logLevel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                logLevelItemStateChanged(evt);
            }
        });

        logFlush.setText("Очистить журнал");
        logFlush.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logFlushActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(logFlush)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                        .addComponent(logLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logFlush))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logLevelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_logLevelItemStateChanged
        this.logListModel.removeAllElements();
        java.util.ListIterator<messageEntry> logMessagesIterator = messages.listIterator();
        while (logMessagesIterator.hasNext()) {
            messageEntry someMessage = logMessagesIterator.next();
            String component = someMessage.component;
            Integer type = someMessage.type;
            String message = someMessage.message;
            String typeStr = "неизвестно";
            String typeColor = "GRAY";
            switch (type) {
                case 0:
                    typeStr = "критическая ошибка";
                    typeColor = "RED";
                    break;
                case 1:
                    typeStr = "ошибка";
                    typeColor = "#FF9100";
                    break;
                case 2:
                    typeStr = "предупреждение";
                    typeColor = "GREEN";
                    break;
                case 3:
                    typeStr = "сообщение";
                    typeColor = "BLUE";
                    break;
            }
            if (type <= this.logLevel.getSelectedIndex()) {
                this.logListModel.addElement("<html><font color=#836502>["+ component + "]</font>: <font color=" + typeColor + ">" + typeStr + "</font>" + ">> '" + message + "';</html>");
            }
            this.logList.ensureIndexIsVisible(this.logListModel.getSize() - 1);
        }
    }//GEN-LAST:event_logLevelItemStateChanged

    private void logFlushActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logFlushActionPerformed
        this.messages.removeAll(messages);
        this.logListModel.removeAllElements();
    }//GEN-LAST:event_logFlushActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(logerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(logerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(logerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(logerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new logerFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logFlush;
    private javax.swing.JComboBox logLevel;
    private javax.swing.JList logList;
    // End of variables declaration//GEN-END:variables
}
