/*
 * This code is distributed under terms of GNU GPLv2.
 * *See LICENSE file.
 * ©UKRINFORM 2011-2012
 */

package jtvprog;

/**
 * Class of main JTVProg window
 * @author Stanislav Nepochatov
 */
public class mainFrame extends javax.swing.JFrame {

    /** Creates new form mainFrame */
    public mainFrame() {
        initComponents();
        this.setVisible(true);
        JTVProg.logPrint(this, 3, "показ главного окна");
    }
    
    /**
     * Call file deletion dialog and delete files
     */
    public void callDelDialog() {
        Object[] options = {"Да", "Нет"};
        Integer result = javax.swing.JOptionPane.showOptionDialog(this,
            "Удалить сохраненные файлы телепрограммы?",
            "Вопрос",
            javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1]);
        if (result == 0) {
            try {
                JTVProg.logPrint(this, 2, "удаление файлов телепрограммы");
                java.io.File[] chFiles = new java.io.File("по каналам/").listFiles();
                for (Integer chIndex = 0; chIndex < chFiles.length; chIndex++) {
                    chFiles[chIndex].delete();
                }
                java.io.File[] dayFiles =new java.io.File("по дням/").listFiles();
                for (Integer dayIndex = 0; dayIndex < dayFiles.length; dayIndex++) {
                    dayFiles[dayIndex].delete();
                }
                new java.io.File("по каналам/").delete();
                new java.io.File("по дням/").delete();
            } catch (NullPointerException ex) {
                JTVProg.warningMessage("Файлы телепрограммы уже удалены!");
                JTVProg.logPrint(this, 1, "файлы уже удалены!");
            }
        }
    }
    
    /**
     * Init execution of day processing;
     */
    public void initProcWindow() {
        JTVProg.procWindow = new jtvprog.procFrame(this, false);
        JTVProg.procWindow.setVisible(true);
        JTVProg.procWindow.runExec(JTVProg.configer.ChannelProcessor.new processDaysThread());
    }
    
    /**
     * Init execution of old release file processing;
     */
    public void initReleaseWindow() {
        JTVProg.procWindow = new jtvprog.procFrame(this, false);
        JTVProg.procWindow.setVisible(true);
        JTVProg.procWindow.runExec(JTVProg.configer.ChannelProcessor.new processOldReleaseThread());
    }
    
    /**
     * Lock menues to prevent data damage;
     */
    public void lockMenues() {
        this.tvOldRelease.setEnabled(false);
        this.tvDeleteFiles.setEnabled(false);
        this.tvchReleaseOrder.setEnabled(false);
        this.tvchSettings.setEnabled(false);
        this.tvReset.setEnabled(true);
    }
    
    /**
     * Unlock menues after program complete reset;
     */
    public void unlockMenues() {
        this.tvOldRelease.setEnabled(true);
        this.tvDeleteFiles.setEnabled(true);
        this.tvchReleaseOrder.setEnabled(true);
        this.tvchSettings.setEnabled(true);
        this.tvReset.setEnabled(false);
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
        tvchTable = new javax.swing.JTable();
        tvFillBut = new javax.swing.JButton();
        tvProcBut = new javax.swing.JButton();
        tvReleaseBut = new javax.swing.JButton();
        exitBut = new javax.swing.JButton();
        tvBar = new javax.swing.JMenuBar();
        tvSettings = new javax.swing.JMenu();
        tvchSettings = new javax.swing.JMenuItem();
        tvchReleaseOrder = new javax.swing.JMenuItem();
        tvFillFromFile = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        ribbonItem = new javax.swing.JMenuItem();
        tvActions = new javax.swing.JMenu();
        tvOldRelease = new javax.swing.JMenuItem();
        tvReset = new javax.swing.JMenuItem();
        tvDeleteFiles = new javax.swing.JMenuItem();
        tvOther = new javax.swing.JMenu();
        showLogBox = new javax.swing.JCheckBoxMenuItem();
        tvHelp = new javax.swing.JMenuItem();
        tvAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Обработчик телепрограммы");
        setIconImage(JTVProg.configer.jtvprogIcon);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tvchTable.setModel(new javax.swing.table.DefaultTableModel(
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

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tvchTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tvchTable);

        tvFillBut.setText("(1) Заполнить");
        tvFillBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvFillButActionPerformed(evt);
            }
        });

        tvProcBut.setText("(2) Обработать");
        tvProcBut.setEnabled(false);
        tvProcBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvProcButActionPerformed(evt);
            }
        });

        tvReleaseBut.setText("(3) Выпустить");
        tvReleaseBut.setEnabled(false);
        tvReleaseBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvReleaseButActionPerformed(evt);
            }
        });

        exitBut.setText("Выход");
        exitBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButActionPerformed(evt);
            }
        });

        tvSettings.setText("Настройки");

        tvchSettings.setText("Редактировать список каналов");
        tvchSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvchSettingsActionPerformed(evt);
            }
        });
        tvSettings.add(tvchSettings);

        tvchReleaseOrder.setText("Порядок выпуска");
        tvchReleaseOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvchReleaseOrderActionPerformed(evt);
            }
        });
        tvSettings.add(tvchReleaseOrder);

        tvFillFromFile.setText("Брать текст из файла");
        tvSettings.add(tvFillFromFile);
        tvSettings.add(jSeparator1);

        ribbonItem.setText("Настройки выпуска в систему");
        ribbonItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ribbonItemActionPerformed(evt);
            }
        });
        tvSettings.add(ribbonItem);

        tvBar.add(tvSettings);

        tvActions.setText("Действия");

        tvOldRelease.setText("Выпустить старые файлы");
        tvOldRelease.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvOldReleaseActionPerformed(evt);
            }
        });
        tvActions.add(tvOldRelease);

        tvReset.setText("Сбросить состояние программы");
        tvReset.setEnabled(false);
        tvReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvResetActionPerformed(evt);
            }
        });
        tvActions.add(tvReset);

        tvDeleteFiles.setText("Удалить файлы");
        tvDeleteFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvDeleteFilesActionPerformed(evt);
            }
        });
        tvActions.add(tvDeleteFiles);

        tvBar.add(tvActions);

        tvOther.setText("Справка");

        showLogBox.setText("Показывать журнал");
        showLogBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                showLogBoxItemStateChanged(evt);
            }
        });
        tvOther.add(showLogBox);

        tvHelp.setText("Помощь");
        tvHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvHelpActionPerformed(evt);
            }
        });
        tvOther.add(tvHelp);

        tvAbout.setText("О программе");
        tvAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvAboutActionPerformed(evt);
            }
        });
        tvOther.add(tvAbout);

        tvBar.add(tvOther);

        setJMenuBar(tvBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tvFillBut)
                        .addGap(18, 18, 18)
                        .addComponent(tvProcBut)
                        .addGap(18, 18, 18)
                        .addComponent(tvReleaseBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                        .addComponent(exitBut)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tvFillBut)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tvProcBut)
                        .addComponent(tvReleaseBut)
                        .addComponent(exitBut)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButActionPerformed
        JTVProg.logPrint(this, 2, "дана команда завершения!");
        JTVProg.configer.storeChannelSet();
        System.exit(0);
    }//GEN-LAST:event_exitButActionPerformed

    private void tvchReleaseOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvchReleaseOrderActionPerformed
        tvchOrderFrame orderWindow = new tvchOrderFrame(this);
        orderWindow.setVisible(true);
    }//GEN-LAST:event_tvchReleaseOrderActionPerformed

    private void tvchSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvchSettingsActionPerformed
        tvchSettingsFrame settingsWindow = new tvchSettingsFrame(this);
        settingsWindow.setVisible(true);
    }//GEN-LAST:event_tvchSettingsActionPerformed

    private void tvHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvHelpActionPerformed
        helpFrame helpWindow = new helpFrame(this, true);
        helpWindow.setVisible(true);
    }//GEN-LAST:event_tvHelpActionPerformed

    private void tvFillButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvFillButActionPerformed
        JTVProg.inputWindow = new inputFrame();
        JTVProg.inputWindow.setVisible(true);
    }//GEN-LAST:event_tvFillButActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        JTVProg.configer.storeChannelSet();
    }//GEN-LAST:event_formWindowClosing

    private void tvProcButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvProcButActionPerformed
        this.tvProcBut.setEnabled(false);
        this.initProcWindow();
    }//GEN-LAST:event_tvProcButActionPerformed

    private void tvReleaseButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvReleaseButActionPerformed
        outputFrame outputWindow = new outputFrame(this);
        outputWindow.setVisible(true);
    }//GEN-LAST:event_tvReleaseButActionPerformed

    private void tvAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvAboutActionPerformed
        aboutFrame aboutWindow = new aboutFrame(this, true);
        aboutWindow.setVisible(true);
    }//GEN-LAST:event_tvAboutActionPerformed

    private void showLogBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_showLogBoxItemStateChanged
        JTVProg.setLogWindowVisibilty(this.showLogBox.isSelected());
    }//GEN-LAST:event_showLogBoxItemStateChanged

    private void tvDeleteFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvDeleteFilesActionPerformed
        this.callDelDialog();
    }//GEN-LAST:event_tvDeleteFilesActionPerformed

    private void tvResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvResetActionPerformed
        Integer result;
        Object[] options = {"Да", "Нет"};
        result = javax.swing.JOptionPane.showOptionDialog(this,
            "Вы уверены, что хотите сбросить состояние программы?\nДанные текущего выпуска будут утеряны!",
            "Вопрос",
            javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1]);
        if (result == 0) {
            JTVProg.resetPass();
            JTVProg.configer.resetProcessor();
            this.unlockMenues();
            this.tvProcBut.setEnabled(false);
            this.tvReleaseBut.setEnabled(false);
            this.tvFillBut.setEnabled(true);
        }
    }//GEN-LAST:event_tvResetActionPerformed

    private void tvOldReleaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvOldReleaseActionPerformed
        JTVProg.configer.ChannelProcessor = new chProcSet(JTVProg.configer.Channels.pullList());
        this.lockMenues();
        this.initReleaseWindow();
    }//GEN-LAST:event_tvOldReleaseActionPerformed

    private void ribbonItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ribbonItemActionPerformed
        if (JTVProg.tvApp.isInited) {
            RibbonTVSettings setDialog = new RibbonTVSettings(JTVProg.mainWindow, false);
            setDialog.setVisible(true);
        } else {
        Thread loginer = new Thread() {
                @Override
                public void run() {
                    JTVProg.tvApp.connect(AppComponents.NetWorker.class, new Runnable() {

                        @Override
                        public void run() {
                            RibbonTVSettings setDialog = new RibbonTVSettings(JTVProg.mainWindow, false);
                            setDialog.setVisible(true);
                        }

                    });
                }
            };
            loginer.start();
        }
    }//GEN-LAST:event_ribbonItemActionPerformed

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
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new mainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitBut;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem ribbonItem;
    private javax.swing.JCheckBoxMenuItem showLogBox;
    private javax.swing.JMenuItem tvAbout;
    private javax.swing.JMenu tvActions;
    private javax.swing.JMenuBar tvBar;
    private javax.swing.JMenuItem tvDeleteFiles;
    public javax.swing.JButton tvFillBut;
    public javax.swing.JCheckBoxMenuItem tvFillFromFile;
    private javax.swing.JMenuItem tvHelp;
    private javax.swing.JMenuItem tvOldRelease;
    private javax.swing.JMenu tvOther;
    public javax.swing.JButton tvProcBut;
    public javax.swing.JButton tvReleaseBut;
    private javax.swing.JMenuItem tvReset;
    private javax.swing.JMenu tvSettings;
    private javax.swing.JMenuItem tvchReleaseOrder;
    private javax.swing.JMenuItem tvchSettings;
    public javax.swing.JTable tvchTable;
    // End of variables declaration//GEN-END:variables
}
