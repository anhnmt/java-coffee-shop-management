/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.GUI.billdetail;

import coffeeshop.DAO.impl.BillDetailDao;
import java.awt.Color;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import coffeeshop.Util.Common;
import coffeeshop.Util.DbUtil;
import coffeeshop.DTO.BillDetail;
import javax.swing.JDialog;

/**
 *
 * @author Minh
 */
public final class JDModifyBillDetail extends javax.swing.JDialog {

    CallbackBillDetailModify callback;
    DbUtil dbUtil;

    BillDetail billDetail = null;
    BillDetailDao billDetailDao = null;

    public interface CallbackBillDetailModify {

        public void actionBillDetailModify();
    }

    /**
     * Creates new form JDCategoryCreate
     *
     * @param parent
     * @param modal
     * @param dbUtil
     * @param callback
     * @param billDetail
     */
    public JDModifyBillDetail(JDialog parent, boolean modal, DbUtil dbUtil, CallbackBillDetailModify callback, BillDetail billDetail) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.callback = callback;
        this.billDetail = billDetail;
        this.dbUtil = dbUtil;
        this.billDetailDao = new BillDetailDao(dbUtil);

        if (!Common.isNullOrEmpty(billDetail)) {
//            lblTitle.setText("Sửa đổi sản phẩm");
//            btnModify.setText("Sửa đổi");
            txtProductName.setText(billDetail.getProduct_name());
            txtProductPrice.setText(String.valueOf(billDetail.getProduct_price()));
            txtProductAmount.setText(String.valueOf(billDetail.getAmount()));
        }

        txtProductName.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 240, 240)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        txtProductPrice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 240, 240)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        txtProductAmount.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 240, 240)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        lblProductAmountError.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblProductName = new javax.swing.JLabel();
        txtProductName = new javax.swing.JTextField();
        btnModify = new javax.swing.JButton();
        lblProductPrice = new javax.swing.JLabel();
        txtProductPrice = new javax.swing.JTextField();
        lblProductAmountError = new javax.swing.JLabel();
        txtProductAmount = new javax.swing.JTextField();
        lblProductAmount = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cập nhật thông tin hoá đơn | Quản lý quán cà phê - Version 1.0");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        lblTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffeeshop/assets/img/icons8_product_50px_2.png"))); // NOI18N
        lblTitle.setText("SỬA THÔNG TIN HOÁ ĐƠN");

        lblProductName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblProductName.setText("Tên sản phẩm");

        txtProductName.setEditable(false);

        btnModify.setBackground(new java.awt.Color(0, 204, 106));
        btnModify.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnModify.setForeground(new java.awt.Color(255, 255, 255));
        btnModify.setText("Cập nhật");
        btnModify.setBorderPainted(false);
        btnModify.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

        lblProductPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblProductPrice.setText("Đơn giá");

        txtProductPrice.setEditable(false);

        lblProductAmountError.setForeground(new java.awt.Color(240, 71, 71));
        lblProductAmountError.setText("Không được để trống");

        lblProductAmount.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblProductAmount.setText("Số lượng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtProductName)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 427, Short.MAX_VALUE)
                        .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblProductName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblProductPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtProductPrice)
                    .addComponent(lblProductAmountError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtProductAmount)
                    .addComponent(lblProductAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lblProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lblProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProductAmountError)
                .addGap(18, 18, 18)
                .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        boolean validate = true;
        lblProductAmountError.setVisible(false);
        txtProductAmount.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 240, 240)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        lblProductAmount.setForeground(new Color(0, 0, 0));

        String amount = (String) txtProductAmount.getText().trim();
        int product_amount = Integer.parseInt(amount);

        if (Common.isNullOrEmpty(amount)) {
            txtProductAmount.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(240, 71, 71)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)));
            lblProductAmount.setForeground(new Color(240, 71, 71));
            lblProductAmountError.setVisible(true);
            lblProductAmountError.setText("Sô lượng không được để trống");
            validate = false;
        }

        if (!Common.isInteger(amount)) {
            txtProductAmount.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(240, 71, 71)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)));
            lblProductAmount.setForeground(new Color(240, 71, 71));
            lblProductAmountError.setVisible(true);
            lblProductAmountError.setText("Sô lượng phải là kiểu số nguyên");
            validate = false;
        } else {
            if (product_amount <= 0) {
                txtProductAmount.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(240, 71, 71)),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)));
                lblProductAmount.setForeground(new Color(240, 71, 71));
                lblProductAmountError.setVisible(true);
                lblProductAmountError.setText("Sô lượng phải lớn hơn 0");
                validate = false;
            }
        }

        if (validate == true) {
            try {
                billDetail.setAmount(product_amount);

                Map<String, Object> result = billDetailDao.update(billDetail);
                if ((boolean) result.get("status") == true) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin hoá đơn thành công!");
                    callback.actionBillDetailModify();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại, lỗi: " + result.get("message") + "!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnModifyActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModify;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblProductAmount;
    private javax.swing.JLabel lblProductAmountError;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblProductPrice;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtProductAmount;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtProductPrice;
    // End of variables declaration//GEN-END:variables
}
