/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.GUI.table;

import coffeeshop.DAO.AreaDao;
import coffeeshop.DAO.BillDao;
import coffeeshop.DAO.BillDetailDao;
import coffeeshop.DAO.ProductDao;
import coffeeshop.DAO.TableDao;
import coffeeshop.DTO.Area;
import coffeeshop.DTO.Bill;
import coffeeshop.DTO.BillDetail;
import coffeeshop.DTO.Product;
import coffeeshop.DTO.Table;
import coffeeshop.DTO.User;
import coffeeshop.Util.Common;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TUANANH-PC
 */
public final class JDTableInfo extends javax.swing.JDialog {

    CallbackTableExit callback;

    public interface CallbackTableExit {

        public void actionTableExit();
    }

    User user;
    Table table;
    Area area;

    Product product = null;
    Bill bill = null;
    BillDetail billDetail = null;

    List<Product> products = new ArrayList<>();
    List<BillDetail> billDetails = new ArrayList<>();

    ProductDao productDao = new ProductDao();
    BillDao billDao = new BillDao();
    BillDetailDao billDetailDao = new BillDetailDao();

    /**
     * Creates new form JDBillDetail
     *
     * @param parent
     * @param modal
     * @param callback
     * @param user
     * @param table
     */
    public JDTableInfo(java.awt.Frame parent, boolean modal, CallbackTableExit callback, User user, Table table) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);

        this.callback = callback;
        this.user = user;
        this.table = table;

        loadingProduct();
        loadingBill();
    }

    public void loadingProduct() {
        try {
            tblProduct.removeAll();
            products = productDao.getAll(null, null, null, null, null);

            String columns[] = {"Id", "Tên sản phẩm", "Giá", "Tên danh mục"};
            DefaultTableModel dtm = new DefaultTableModel(columns, 0);

            if (!products.isEmpty()) {
                products.forEach(pro -> {
                    dtm.addRow(new Object[]{pro.getId(), pro.getName(), pro.getPrice(), pro.getCategory_name()});
                });

                tblProduct.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
                    int position = tblProduct.getSelectedRow();
                    if (position < 0) {
                        position = 0;
                    }

                    product = products.get(position);

                    txtProductId.setText(String.valueOf(product.getId()));
                    txtProductName.setText(String.valueOf(product.getName()));
                    txtProductPrice.setText(String.valueOf(product.getPrice()));
                    txtProductAmount.setText("");
                });

                tblProduct.changeSelection(0, 0, false, false);
//          tblProduct.setRowSelectionInterval(0, 0);
            }

            tblProduct.setModel(dtm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadingBill() {
        try {
            bill = null;
            billDetail = null;
            billDetails = new ArrayList<>();

            txtUserId.setText(user.getName());
            txtTableId.setText(table.getName());

            txtBillId.setText("");
            txtBillTime.setText("");
            txtTotalPrice.setText("");

            String columns[] = {"Id", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"};
            DefaultTableModel dtm = new DefaultTableModel(columns, 0);
            tblBillDetail.removeAll();
            tblBillDetail.setModel(dtm);

            bill = billDao.getBillByTableId(table.getId(), false);

            if (Common.isNullOrEmpty(bill)) {
                btnAddProduct.setEnabled(false);
                btnCheckout.setEnabled(false);
                btnBook.setEnabled(true);
            } else {
                txtBillId.setText(String.valueOf(bill.getId()));
                txtBillTime.setText(String.valueOf(bill.getCreated_at()));

                btnBook.setEnabled(false);
                btnAddProduct.setEnabled(true);
                btnCheckout.setEnabled(true);
                loadingBillDetail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadingBillDetail() {
        try {
            tblBillDetail.removeAll();
            billDetails = billDetailDao.getAllByBillId(bill.getId());

            String columns[] = {"Id", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"};
            DefaultTableModel dtm = new DefaultTableModel(columns, 0);
            Float total_price = 0f;

            if (!billDetails.isEmpty()) {
                for (BillDetail obj : billDetails) {
                    float total = (float) (obj.getProduct_price() * obj.getAmount());
                    total_price += total;
                    dtm.addRow(new Object[]{obj.getProduct_id(), obj.getProduct_name(), obj.getProduct_price(), obj.getAmount(), total});
                }

                txtTotalPrice.setText(String.valueOf(total_price));

//            tblBillDetail.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
//                int position = tblBillDetail.getSelectedRow();
//                if (position < 0) {
//                    position = 0;
//                }
//
//                BillDetail billDetail = billDetails.get(position);
//            });
//
//            tblBillDetail.changeSelection(0, 0, false, false);
//          tblProduct.setRowSelectionInterval(0, 0);
            }

            tblBillDetail.setModel(dtm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        pnlAddProduct = new javax.swing.JPanel();
        lblProductId = new javax.swing.JLabel();
        lblProductName = new javax.swing.JLabel();
        lblProductPrice = new javax.swing.JLabel();
        lblProductAmount = new javax.swing.JLabel();
        txtProductId = new javax.swing.JTextField();
        txtProductName = new javax.swing.JTextField();
        txtProductPrice = new javax.swing.JTextField();
        txtProductAmount = new javax.swing.JTextField();
        btnAddProduct = new javax.swing.JButton();
        pnlBill = new javax.swing.JPanel();
        lblBillId = new javax.swing.JLabel();
        txtBillId = new javax.swing.JTextField();
        btnBook = new javax.swing.JButton();
        lblBillTime = new javax.swing.JLabel();
        txtBillTime = new javax.swing.JTextField();
        lblTableName = new javax.swing.JLabel();
        txtTableId = new javax.swing.JTextField();
        txtUserId = new javax.swing.JTextField();
        lblAreaName = new javax.swing.JLabel();
        txtTotalPrice = new javax.swing.JTextField();
        lblVND = new javax.swing.JLabel();
        lblTotalPrice = new javax.swing.JLabel();
        btnCheckout = new javax.swing.JButton();
        scrollPaneBillDetail = new javax.swing.JScrollPane();
        tblBillDetail = new javax.swing.JTable();
        scrollPaneProduct = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        lblProductId.setText("Mã sản phẩm:");

        lblProductName.setText("Tên sản phẩm:");

        lblProductPrice.setText("Đơn giá:");

        lblProductAmount.setText("Số lượng:");

        txtProductId.setEditable(false);
        txtProductId.setFocusable(false);

        txtProductName.setEditable(false);
        txtProductName.setFocusable(false);

        txtProductPrice.setEditable(false);
        txtProductPrice.setFocusable(false);

        btnAddProduct.setText("Thêm sản phẩm");
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAddProductLayout = new javax.swing.GroupLayout(pnlAddProduct);
        pnlAddProduct.setLayout(pnlAddProductLayout);
        pnlAddProductLayout.setHorizontalGroup(
            pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblProductId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblProductName, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProductId)
                    .addComponent(txtProductName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(txtProductPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(txtProductAmount))
                .addContainerGap())
        );
        pnlAddProductLayout.setVerticalGroup(
            pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddProductLayout.createSequentialGroup()
                        .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProductPrice)
                            .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProductAmount)
                            .addComponent(txtProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlAddProductLayout.createSequentialGroup()
                        .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProductId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProductName)
                            .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddProduct)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblBillId.setText("Mã hoá đơn:");

        txtBillId.setEditable(false);
        txtBillId.setFocusable(false);

        btnBook.setText("Đặt bàn");
        btnBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookActionPerformed(evt);
            }
        });

        lblBillTime.setText("Thời gian:");

        txtBillTime.setEditable(false);
        txtBillTime.setFocusable(false);

        lblTableName.setText("Tên bàn:");

        txtTableId.setEditable(false);
        txtTableId.setFocusable(false);

        txtUserId.setEditable(false);
        txtUserId.setFocusable(false);

        lblAreaName.setText("Tên  nhân viên:");

        txtTotalPrice.setEditable(false);
        txtTotalPrice.setFocusable(false);

        lblVND.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblVND.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblVND.setText("VNĐ");

        lblTotalPrice.setText("Tổng tiền:");

        btnCheckout.setText("Thanh toán");
        btnCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBillLayout = new javax.swing.GroupLayout(pnlBill);
        pnlBill.setLayout(pnlBillLayout);
        pnlBillLayout.setHorizontalGroup(
            pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBillLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addComponent(btnBook, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addComponent(lblBillId, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBillId))
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addComponent(lblBillTime, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBillTime, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(lblTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblVND, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblAreaName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTableName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUserId)
                            .addComponent(txtTableId))))
                .addContainerGap())
        );
        pnlBillLayout.setVerticalGroup(
            pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBillLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBillId)
                            .addComponent(txtBillId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBillTime)
                            .addComponent(txtBillTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTableName)
                            .addComponent(txtTableId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAreaName)
                            .addComponent(txtUserId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBook)
                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVND)
                    .addComponent(lblTotalPrice)
                    .addComponent(btnCheckout))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scrollPaneBillDetail.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tblBillDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollPaneBillDetail.setViewportView(tblBillDetail);

        scrollPaneProduct.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollPaneProduct.setViewportView(tblProduct);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAddProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPaneProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPaneBillDetail))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                    .addComponent(scrollPaneBillDetail))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookActionPerformed
        try {
            bill = new Bill();
            bill.setUser_id(user.getId());
            bill.setTable_id(table.getId());
            bill.setStatus(false);

            Map<String, Object> billCreate = billDao.create(bill);

            if ((boolean) billCreate.get("status") == true) {
                btnBook.setEnabled(false);
                btnAddProduct.setEnabled(true);
                btnCheckout.setEnabled(true);
                loadingBill();
            } else {
                JOptionPane.showConfirmDialog(rootPane, billCreate.get("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnBookActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        try {
            billDetail = new BillDetail();
            billDetail.setBill_id(bill.getId());
            billDetail.setProduct_id(product.getId());
            billDetail.setAmount(Integer.parseInt(txtProductAmount.getText()));

            Map<String, Object> billDetailUpdate = billDetailDao.create(billDetail);

            if ((boolean) billDetailUpdate.get("status") == true) {
                loadingBillDetail();
                txtProductAmount.setText("");
            } else {
                JOptionPane.showMessageDialog(rootPane, billDetailUpdate.get("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutActionPerformed
        try {
            bill.setTotal_price(Float.parseFloat(txtTotalPrice.getText()));
            bill.setStatus(true);

            Map<String, Object> billCheckout = billDao.update(bill);

            if ((boolean) billCheckout.get("status") == true) {
                loadingBill();
            } else {
                JOptionPane.showMessageDialog(rootPane, billCheckout.get("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnCheckoutActionPerformed

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
            java.util.logging.Logger.getLogger(JDTableInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDTableInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDTableInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDTableInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            User u = new User();
            u.setId(1);

            Table t = new Table();
            t.setId(1);

            JDTableInfo dialog = new JDTableInfo(new javax.swing.JFrame(), true, null, u, t);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnBook;
    private javax.swing.JButton btnCheckout;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAreaName;
    private javax.swing.JLabel lblBillId;
    private javax.swing.JLabel lblBillTime;
    private javax.swing.JLabel lblProductAmount;
    private javax.swing.JLabel lblProductId;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblProductPrice;
    private javax.swing.JLabel lblTableName;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JLabel lblVND;
    private javax.swing.JPanel pnlAddProduct;
    private javax.swing.JPanel pnlBill;
    private javax.swing.JScrollPane scrollPaneBillDetail;
    private javax.swing.JScrollPane scrollPaneProduct;
    private javax.swing.JTable tblBillDetail;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTextField txtBillId;
    private javax.swing.JTextField txtBillTime;
    private javax.swing.JTextField txtProductAmount;
    private javax.swing.JTextField txtProductId;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtProductPrice;
    private javax.swing.JTextField txtTableId;
    private javax.swing.JTextField txtTotalPrice;
    private javax.swing.JTextField txtUserId;
    // End of variables declaration//GEN-END:variables
}
