/*
 * This code is distributed under terms of GNU GPLv2.
 * *See LICENSE file.
 * ©UKRINFORM 2011-2012
 */

package jtvprog;

/**
 *
 * @author Stanislav Nepochatov
 */
public class helpFrame extends javax.swing.JDialog {

    /** Creates new form helpFrame */
    public helpFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.jTextPane1.setCaretPosition(0);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Инструкция");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18));
        jLabel1.setText("Инструкция по использованию JTVProg");

        jTextPane1.setBackground(javax.swing.UIManager.getDefaults().getColor("TabbedPane.tabAreaBackground"));
        jTextPane1.setContentType("text/html");
        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("FreeSans", 0, 12)); // NOI18N
        jTextPane1.setText("<html>\nДанная программа предназначена для автоматизированной подготовки, обработки и выпуска телепрограмм. \nПрограмма автоматически сохраняет и обрабатывает каналы, соблюдая при этом очередность и целостность выпуска программы.\n<br><br>\n<b>Инструкция:</b>\n<ol>\n<li>Откройте программу с помощью ярлыка. Проверьте список каналов. В случае необходимости, отредактируйте \nсписок каналов, воспользовавшись меню <b>Настройки - Редактировать список каналов</b>. Так же можно редактировать \nочередность обработки и выпуска каналов, используя меню <b>Настройки - Порядок выпуска</b>.</li>\n\n<li>Нажмите кнопку Заполнить в главном окне программы. В открывшееся окно скопируйте текст телепрограммы \nуказанного канала, нажмите <b>Далее</b> - телепрограмма канала автоматически сохранится. В случае несоответствия \nканалов, программа выдаст ошибку, в таком случае повторите копирование телепрограммы необходимого канала.</li>\n\n<li>После заполнения файлов каналов, следует обработать их, нажав кнопку <b>Обработать</b> в главном окне программы. \nПрограмма автоматически создаст программу по дням с указанной очередностью каналов. Все каналы в главной таблице \nдолжны быть отмеченны как обработанные.</li>\n\n<li>Нажмите кнопку <b>Выпустить</b>, для выпуска телепрограммы по соответствющим направлениям в АСОП. Диалог выпуска \nподдерживает разделение сообщений и автоматическое копирование в буфер обмена.</li>\n\n<li>После выпуска телепрограммы завершите работу программы нажав кнопку <b>Выход</b> или закрыв главное окно.</li>\n</ol>\n<br>\n<font color=red><b>Внимание!!</b></font><br>\nДанная версия программы все еще находится в разработке. Просьба обращаться к разработчику при любых отклонениях \nв работе программы.<br>\n<br>\n<b>Автор:</b> <i>Непочатов Станислав (УТТЗ ЕРМ)</i><br>\nУкринформ 2012г.\n</html>\n");
        jTextPane1.setFocusable(false);
        jScrollPane2.setViewportView(jTextPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(helpFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(helpFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(helpFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(helpFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                helpFrame dialog = new helpFrame(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
