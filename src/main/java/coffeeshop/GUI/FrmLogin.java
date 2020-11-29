/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 *
 * @author TUANANH-DESKTOP
 */
public class FrmLogin extends JFrame {

    /**
     * Creates new form FrmLoginNew
     */
    public FrmLogin() {
        initComponents();
        setLocationRelativeTo(null);

        // Custom code
        lblBackground.setBounds(0, 0, 960, 610);
        lblBackground.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/com/example/assets/img/background.png")).getImage().getScaledInstance(lblBackground.getWidth(), lblBackground.getHeight(), 1)));

        lblEmailError.setVisible(false);
        lblPasswordError.setVisible(false);

        txtEmail.setBorder(BorderFactory.createCompoundBorder(
                txtEmail.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                txtPassword.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        String email = txtEmail.getText().trim();
        String password = String.valueOf(txtPassword.getPassword()).trim();
        boolean validate = true;

        // Reset to default
        lblEmailError.setVisible(false);
        lblPasswordError.setVisible(false);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(34, 36, 40)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(34, 36, 40)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        lblEmail.setForeground(new Color(220, 221, 222));
        lblPassword.setForeground(new Color(220, 221, 222));

        if (email.equals("")) {
            txtEmail.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(240, 71, 71)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)));
            lblEmail.setForeground(new Color(240, 71, 71));
            lblEmailError.setText("Email không được để trống");
            lblEmailError.setVisible(true);
            validate = false;
        } else {
            String email_regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            if (!email.matches(email_regex)) {
                txtEmail.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(240, 71, 71)),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)));
                lblEmail.setForeground(new Color(240, 71, 71));
                lblEmailError.setText("Email không đúng định dạng");
                lblEmailError.setVisible(true);
                validate = false;
            }
        }

        if (password.equals("")) {
            txtPassword.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(240, 71, 71)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)));
            lblPassword.setForeground(new Color(240, 71, 71));
            lblPasswordError.setText("Mật khẩu không được để trống");
            lblPasswordError.setVisible(true);
            validate = false;
        }

        if (validate == true) {
            JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtPasswordKeyPressed(KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnLogin.doClick();
        }
    }//GEN-LAST:event_txtPasswordKeyPressed

    private void lblForgotPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForgotPasswordMouseClicked
        JOptionPane.showMessageDialog(null, "Vui lòng liên hệ admin để lấy lại mật khẩu.");
    }//GEN-LAST:event_lblForgotPasswordMouseClicked

    private void lblRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegisterMouseClicked
        JOptionPane.showMessageDialog(null, "Vui lòng liên hệ admin để đăng ký tài khoản.");        // TODO add your handling code here:
    }//GEN-LAST:event_lblRegisterMouseClicked

    private void txtEmailKeyPressed(KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnLogin.doClick();
        }
    }//GEN-LAST:event_txtEmailKeyPressed

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBackround = new JPanel();
        lblTitle = new JLabel();
        panelLogin = new JPanel();
        boxLogin = new JPanel();
        lblEmail = new JLabel();
        txtEmail = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton();
        lblRegister = new JLabel();
        lblQuestionRegister = new JLabel();
        lblForgotPassword = new JLabel();
        lblHeaderPrimary = new JLabel();
        lblHeaderSecondary = new JLabel();
        lblEmailError = new JLabel();
        lblPassword = new JLabel();
        lblPasswordError = new JLabel();
        lblBackground = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Cửa Hàng Cafe - Version 1.0");
        setMinimumSize(new Dimension(960, 610));
        setResizable(false);
        setSize(new Dimension(960, 610));

        panelBackround.setMaximumSize(new Dimension(960, 610));
        panelBackround.setMinimumSize(new Dimension(960, 610));
        panelBackround.setPreferredSize(new Dimension(960, 610));
        panelBackround.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setBackground(new Color(255, 255, 255));
        lblTitle.setFont(new Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setForeground(new Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setText("QUẢN LÝ CỬA HÀNG CAFE");
        panelBackround.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 960, -1));

        panelLogin.setBackground(new Color(54, 57, 63));
        panelLogin.setMaximumSize(new Dimension(480, 406));
        panelLogin.setMinimumSize(new Dimension(480, 406));
        panelLogin.setPreferredSize(new Dimension(480, 406));

        boxLogin.setBackground(new Color(54, 57, 63));
        boxLogin.setMinimumSize(new Dimension(416, 342));
        boxLogin.setPreferredSize(new Dimension(416, 342));

        lblEmail.setFont(new Font("Segoe UI", 1, 12)); // NOI18N
        lblEmail.setForeground(new Color(142, 146, 151));
        lblEmail.setText("EMAIL");

        txtEmail.setBackground(new Color(48, 51, 57));
        txtEmail.setFont(new Font("Segoe UI", 0, 16)); // NOI18N
        txtEmail.setForeground(new Color(220, 221, 222));
        txtEmail.setBorder(BorderFactory.createLineBorder(new Color(34, 36, 40)));
        txtEmail.setCaretColor(new Color(255, 255, 255));
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
        });

        txtPassword.setBackground(new Color(48, 51, 57));
        txtPassword.setFont(new Font("Segoe UI", 0, 16)); // NOI18N
        txtPassword.setForeground(new Color(220, 221, 222));
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(34, 36, 40)));
        txtPassword.setCaretColor(new Color(255, 255, 255));
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        btnLogin.setBackground(new Color(114, 137, 218));
        btnLogin.setFont(new Font("Segoe UI", 1, 16)); // NOI18N
        btnLogin.setForeground(new Color(255, 255, 255));
        btnLogin.setText("Đăng nhập");
        btnLogin.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setFocusable(false);
        btnLogin.setHorizontalTextPosition(SwingConstants.CENTER);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        lblRegister.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        lblRegister.setForeground(new Color(114, 137, 218));
        lblRegister.setText("Đăng ký");
        lblRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRegisterMouseClicked(evt);
            }
        });

        lblQuestionRegister.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        lblQuestionRegister.setForeground(new Color(114, 118, 125));
        lblQuestionRegister.setText("Cần một tài khoản?");

        lblForgotPassword.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        lblForgotPassword.setForeground(new Color(114, 137, 218));
        lblForgotPassword.setText("Quên mật khẩu?");
        lblForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblForgotPasswordMouseClicked(evt);
            }
        });

        lblHeaderPrimary.setFont(new Font("Segoe UI", 1, 24)); // NOI18N
        lblHeaderPrimary.setForeground(new Color(255, 255, 255));
        lblHeaderPrimary.setHorizontalAlignment(SwingConstants.CENTER);
        lblHeaderPrimary.setText("Chào mừng trở lại");

        lblHeaderSecondary.setFont(new Font("Segoe UI", 0, 16)); // NOI18N
        lblHeaderSecondary.setForeground(new Color(185, 187, 190));
        lblHeaderSecondary.setHorizontalAlignment(SwingConstants.CENTER);
        lblHeaderSecondary.setText("Rất vui mừng khi được gặp lại bạn!");

        lblEmailError.setFont(new Font("Segoe UI", 0, 12)); // NOI18N
        lblEmailError.setForeground(new Color(240, 71, 71));
        lblEmailError.setHorizontalAlignment(SwingConstants.TRAILING);
        lblEmailError.setText("Email không được để trống");
        lblEmailError.setHorizontalTextPosition(SwingConstants.CENTER);

        lblPassword.setFont(new Font("Segoe UI", 1, 12)); // NOI18N
        lblPassword.setForeground(new Color(142, 146, 151));
        lblPassword.setText("MẬT KHẨU");

        lblPasswordError.setFont(new Font("Segoe UI", 0, 12)); // NOI18N
        lblPasswordError.setForeground(new Color(240, 71, 71));
        lblPasswordError.setHorizontalAlignment(SwingConstants.TRAILING);
        lblPasswordError.setText("Mật khẩu không được để trống");
        lblPasswordError.setHorizontalTextPosition(SwingConstants.CENTER);

        GroupLayout boxLoginLayout = new GroupLayout(boxLogin);
        boxLogin.setLayout(boxLoginLayout);
        boxLoginLayout.setHorizontalGroup(
            boxLoginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(lblHeaderSecondary, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblHeaderPrimary, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txtEmail)
            .addComponent(txtPassword)
            .addGroup(boxLoginLayout.createSequentialGroup()
                .addGroup(boxLoginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(boxLoginLayout.createSequentialGroup()
                        .addComponent(lblQuestionRegister, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblRegister, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblForgotPassword, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(147, Short.MAX_VALUE))
            .addGroup(boxLoginLayout.createSequentialGroup()
                .addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmailError, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(boxLoginLayout.createSequentialGroup()
                .addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPasswordError, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        boxLoginLayout.setVerticalGroup(
            boxLoginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, boxLoginLayout.createSequentialGroup()
                .addComponent(lblHeaderPrimary)
                .addGap(10, 10, 10)
                .addComponent(lblHeaderSecondary)
                .addGap(18, 18, 18)
                .addGroup(boxLoginLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(lblEmailError))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(boxLoginLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(lblPasswordError))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(lblForgotPassword)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boxLoginLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuestionRegister)
                    .addComponent(lblRegister)))
        );

        GroupLayout panelLoginLayout = new GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(boxLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(boxLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        panelBackround.add(panelLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, -1, -1));

        lblBackground.setHorizontalAlignment(SwingConstants.CENTER);
        lblBackground.setHorizontalTextPosition(SwingConstants.CENTER);
        lblBackground.setMaximumSize(new Dimension(960, 610));
        lblBackground.setMinimumSize(new Dimension(960, 610));
        lblBackground.setPreferredSize(new Dimension(960, 610));
        panelBackround.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 610));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panelBackround, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panelBackround, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel boxLogin;
    private JButton btnLogin;
    private JLabel lblBackground;
    private JLabel lblEmail;
    private JLabel lblEmailError;
    private JLabel lblForgotPassword;
    private JLabel lblHeaderPrimary;
    private JLabel lblHeaderSecondary;
    private JLabel lblPassword;
    private JLabel lblPasswordError;
    private JLabel lblQuestionRegister;
    private JLabel lblRegister;
    private JLabel lblTitle;
    private JPanel panelBackround;
    private JPanel panelLogin;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
