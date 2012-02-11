/*
 * This code is distributed under terms of GNU GPLv2.
 * *See LICENSE file.
 * ©UKRINFORM 2011-2012
 */

package jtvprog;

import javax.swing.*;

/**
 * Fill order modify window class
 * @author Stanislav Nepochatov
 */
public class tvchOrderFrame extends javax.swing.JDialog {
    
    /**
     * Local set of channels;
     */
    private chSet localSet = new jtvprog.chSet(JTVProg.configer.Channels.pullList());
    
    /**
     * List model for channel list
     */
    private DefaultListModel chListModel = new DefaultListModel();

    /** Creates new form tvchOrderFrame */
    public tvchOrderFrame(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        JTVProg.logPrint("tvchOrderFrame", 3, "показ окна");
        this.fillChannelList();
        System.out.println(localSet.equals(JTVProg.configer.Channels));
    }
    
    /**
     * Fill jList by channels;
     */
    private void fillChannelList() {
        for (Integer chCounter = 1; chCounter < localSet.getSetSize() + 1; chCounter++) {
            chListModel.addElement(localSet.getChannelByROrder(chCounter));
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
        tvOrderList = new JList(this.chListModel);
        moveUpBut = new javax.swing.JButton();
        moveDownBut = new javax.swing.JButton();
        cancelBut = new javax.swing.JButton();
        saveBut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Порядок выпуска");

        tvOrderList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tvOrderList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                tvOrderListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(tvOrderList);

        moveUpBut.setText("↑");
        moveUpBut.setEnabled(false);
        moveUpBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUpButActionPerformed(evt);
            }
        });

        moveDownBut.setText("↓");
        moveDownBut.setEnabled(false);
        moveDownBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDownButActionPerformed(evt);
            }
        });

        cancelBut.setText("Отмена");
        cancelBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButActionPerformed(evt);
            }
        });

        saveBut.setText("Сохранить");
        saveBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(moveUpBut)
                            .addComponent(moveDownBut)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(saveBut)
                        .addGap(18, 18, 18)
                        .addComponent(cancelBut)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(moveUpBut)
                        .addGap(18, 18, 18)
                        .addComponent(moveDownBut))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelBut)
                    .addComponent(saveBut))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButActionPerformed

    private void moveUpButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUpButActionPerformed
        Integer selectedIndex = this.tvOrderList.getSelectedIndex();
        if (selectedIndex != 0) {
            Object aObject = chListModel.getElementAt(selectedIndex);
            Object bObject = chListModel.getElementAt(selectedIndex - 1);
            chListModel.set(selectedIndex, bObject);
            chListModel.set(selectedIndex - 1, aObject);
            this.tvOrderList.setSelectedIndex(selectedIndex - 1);
            this.tvOrderList.ensureIndexIsVisible(selectedIndex - 1);
            JTVProg.logPrint(this, 3, "сдвиг канала [" + (String) this.tvOrderList.getSelectedValue() + "] на позицию вверх");
            this.localSet.moveChannelReleaseUp(selectedIndex);
        }
        else {
            JTVProg.logPrint(this, 2, "канал уже наверху!");
        }
        /**Integer selectedIndex = this.tvOrderList.getSelectedIndex();
        JTVProg.logPrint(this, 3, "сдвиг канала [" + (String) this.tvOrderList.getSelectedValue() + "] на позицию вверх");
        this.localSet.moveChannelFillUp(selectedIndex);
        ListModel resModel = JTVProg.configer.refreshFillOrder();
        this.tvOrderList.setModel(resModel);**/
    }//GEN-LAST:event_moveUpButActionPerformed

    private void moveDownButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDownButActionPerformed
        Integer selectedIndex = this.tvOrderList.getSelectedIndex();
        if (selectedIndex != chListModel.getSize() - 1) {
            Object aObject = chListModel.getElementAt(selectedIndex);
            Object bObject = chListModel.getElementAt(selectedIndex + 1);
            chListModel.set(selectedIndex, bObject);
            chListModel.set(selectedIndex + 1, aObject);
            this.tvOrderList.setSelectedIndex(selectedIndex + 1);
            this.tvOrderList.ensureIndexIsVisible(selectedIndex + 1);
            JTVProg.logPrint(this, 3, "сдвиг канала [" + (String) this.tvOrderList.getSelectedValue() + "] на позицию вниз");
            this.localSet.moveChannelReleaseDown(selectedIndex);
        }
        else {
            JTVProg.logPrint(this, 2, "канал уже внизу!");
        }
        /**Integer selectedIndex = this.tvOrderList.getSelectedIndex();
        JTVProg.logPrint(this, 3, "сдвиг канала [" + (String) this.tvOrderList.getSelectedValue() + "] на позицию вниз");
        this.localSet.moveChannelFillDown(selectedIndex);
        ListModel resModel = JTVProg.configer.refreshFillOrder();
        this.tvOrderList.setModel(resModel);**/
    }//GEN-LAST:event_moveDownButActionPerformed

    private void saveButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButActionPerformed
        JTVProg.logPrint(this, 2, "обновление списка каналов:\n" + localSet.toString());
        JTVProg.configer.Channels.pushList(localSet.pullList());
        this.dispose();
    }//GEN-LAST:event_saveButActionPerformed

    private void tvOrderListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_tvOrderListValueChanged
        Integer selectedIndex = this.tvOrderList.getSelectedIndex();
        if (selectedIndex == 0) {
            this.moveUpBut.setEnabled(false);
            this.moveDownBut.setEnabled(true);
        }
        else if (selectedIndex == this.localSet.getSetSize() - 1) {
            this.moveUpBut.setEnabled(true);
            this.moveDownBut.setEnabled(false);
        }
        else {
            this.moveUpBut.setEnabled(true);
            this.moveDownBut.setEnabled(true);
        }
    }//GEN-LAST:event_tvOrderListValueChanged

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
            java.util.logging.Logger.getLogger(tvchOrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tvchOrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tvchOrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tvchOrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                tvchOrderFrame dialog = new tvchOrderFrame(new javax.swing.JFrame());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBut;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton moveDownBut;
    private javax.swing.JButton moveUpBut;
    private javax.swing.JButton saveBut;
    private javax.swing.JList tvOrderList;
    // End of variables declaration//GEN-END:variables
}
