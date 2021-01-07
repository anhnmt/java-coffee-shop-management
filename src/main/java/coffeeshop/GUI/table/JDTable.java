/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.GUI.table;

import coffeeshop.DAO.impl.BillDao;
import coffeeshop.DAO.impl.BillDetailDao;
import coffeeshop.DAO.impl.ProductDao;
import coffeeshop.DTO.*;
import coffeeshop.GUI.bill.JDDeleteBill;
import coffeeshop.GUI.billdetail.JDDeleteBillDetail;
import coffeeshop.GUI.billdetail.JDModifyBillDetail;
import coffeeshop.Util.BaseMessage;
import coffeeshop.Util.Common;
import coffeeshop.Util.Constant;
import coffeeshop.Util.DbUtil;
import lombok.extern.log4j.Log4j;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author TUANANH-PC
 */
@Log4j
public final class JDTable extends javax.swing.JDialog implements JDModifyBillDetail.CallbackBillDetailModify, JDDeleteBillDetail.CallbackBillDetailDelete, JDChangeTable.CallbackTableChange, JDDeleteBill.CallbackBillDelete {

    CallbackTableExit callback;

    public interface CallbackTableExit {

        public void actionTableExit();
    }

    DbUtil dbUtil;

    User user = null;
    Table table = null;
    Area area = null;

    Product product = null;
    Bill bill = null;
    BillDetail billDetail = null;

    List<Product> products = new ArrayList<>();
    List<BillDetail> billDetails = new ArrayList<>();

    ProductDao productDao = null;
    BillDao billDao = null;
    BillDetailDao billDetailDao = null;
    private BaseMessage response;
    Frame parent;

    /**
     * Creates new form JDBillDetail
     *
     * @param parent
     * @param modal
     * @param dbUtil
     * @param callback
     * @param user
     * @param table
     */
    public JDTable(Frame parent, boolean modal, DbUtil dbUtil, CallbackTableExit callback, User user, Table table) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.parent = parent;
        this.callback = callback;
        this.user = user;
        this.table = table;

        this.dbUtil = dbUtil;
        this.productDao = new ProductDao(dbUtil);
        this.billDao = new BillDao(dbUtil);
        this.billDetailDao = new BillDetailDao(dbUtil);

        // Custom Style
        btnAddProduct.setVisible(false);
        txtProductAmount.setEditable(false);
        btnBook.setVisible(true);
        btnChangeTable.setVisible(false);
        btnCheckout.setVisible(false);
        btnDeleteBill.setVisible(false);
        txtProductName.setBorder(BorderFactory.createCompoundBorder(
                txtProductName.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        txtProductPrice.setBorder(BorderFactory.createCompoundBorder(
                txtProductPrice.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        txtProductAmount.setBorder(BorderFactory.createCompoundBorder(
                txtProductAmount.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        txtBillId.setBorder(BorderFactory.createCompoundBorder(
                txtBillId.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        txtBillTime.setBorder(BorderFactory.createCompoundBorder(
                txtBillTime.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        txtTableId.setBorder(BorderFactory.createCompoundBorder(
                txtTableId.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        txtUserId.setBorder(BorderFactory.createCompoundBorder(
                txtUserId.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        txtTotalPrice.setBorder(BorderFactory.createCompoundBorder(
                txtTotalPrice.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));

        loadingProduct();
        loadingBill();

    }

    public void loadingProduct() {
        try {
            tblProduct.removeAll();
            lblProductAmountError.setVisible(false);
            products = productDao.getAll(null, null, null);

            String columns[] = {"Id", "Tên sản phẩm", "Giá", "Tên danh mục"};
            DefaultTableModel dtm = new DefaultTableModel(columns, 0);

            if (!Common.isNullOrEmpty(products)) {
                products.forEach(pro -> {
                    dtm.addRow(new Object[]{pro.getId(), pro.getName(), pro.getPrice(), pro.getCategory_name()});
                });

                tblProduct.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
                    int position = tblProduct.getSelectedRow();
                    if (position >= 0) {
                        product = products.get(position);

                        txtProductName.setText(String.valueOf(product.getName()));
                        txtProductPrice.setText(String.valueOf(product.getPrice()));
                        txtProductAmount.setText("");
                    }
                });

                tblProduct.changeSelection(0, 0, false, false);
            }

            tblProduct.setModel(dtm);
        } catch (Exception e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(null, response, "loadingProduct"));
        }
    }

    public void loadingBill() {
        try {
            bill = null;
            txtBillId.setText("");
            txtBillTime.setText("");
            txtTotalPrice.setText("");

            tblBillDetail.removeAll();
            tblBillDetail.setModel(new DefaultTableModel());
            DefaultTableModel myTableModel = (DefaultTableModel) this.tblBillDetail.getModel();
            while (myTableModel.getRowCount() > 0) {
                myTableModel.removeRow(0);
            }

            txtUserId.setText(user.getName());
            txtTableId.setText(table.getName());

            bill = billDao.getByTableId(new Bill(table.getId(), false));

            if (Common.isNullOrEmpty(bill)) {
                btnAddProduct.setVisible(false);
                txtProductAmount.setEditable(false);
                btnBook.setVisible(true);
                btnCheckout.setVisible(false);
                btnChangeTable.setVisible(false);
                btnDeleteBill.setVisible(false);
            } else {
                txtBillId.setText(String.valueOf(bill.getId()));
                txtBillTime.setText(String.valueOf(bill.getCreated_at()));

                btnBook.setVisible(false);
                btnDeleteBill.setVisible(true);

                if (!Common.isNullOrEmpty(products)) {
                    txtProductAmount.setEditable(true);
                    btnChangeTable.setVisible(true);
                }

                loadingBillDetail();
            }
        } catch (Exception e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(null, response, "loadingBill"));
        }
    }

    public void loadingBillDetail() {
        try {
            billDetail = null;
            billDetails = null;
            btnCheckout.setVisible(false);
            billDetails = billDetailDao.getAll(bill.getId());

            String columns[] = {"Id", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"};
            DefaultTableModel dtm = new DefaultTableModel(columns, 0);

            Float total_price = 0f;

            if (!Common.isNullOrEmpty(billDetails)) {
                btnCheckout.setVisible(true);

                for (BillDetail obj : billDetails) {
                    float total = (float) (obj.getProduct_price() * obj.getAmount());
                    total_price += total;
                    dtm.addRow(new Object[]{obj.getProduct_id(), obj.getProduct_name(), obj.getProduct_price(), obj.getAmount(), total});
                }

                txtTotalPrice.setText(String.valueOf(total_price));

                tblBillDetail.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
                    int position = tblBillDetail.getSelectedRow();
                    if (position >= 0) {
                        billDetail = billDetails.get(position);
                    }
                });

                tblBillDetail.changeSelection(0, 0, false, false);
            }

            tblBillDetail.setModel(dtm);
        } catch (Exception e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(null, response, "loadingBillDetail"));
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

        popupMenu = new javax.swing.JPopupMenu();
        menuItemEdit = new javax.swing.JMenuItem();
        menuItemDelete = new javax.swing.JMenuItem();
        jPanel3 = new javax.swing.JPanel();
        pnlAddProduct = new javax.swing.JPanel();
        lblProductName = new javax.swing.JLabel();
        lblProductPrice = new javax.swing.JLabel();
        lblProductAmount = new javax.swing.JLabel();
        txtProductName = new javax.swing.JTextField();
        txtProductPrice = new javax.swing.JTextField();
        txtProductAmount = new javax.swing.JTextField();
        btnAddProduct = new javax.swing.JButton();
        lblProductAmountError = new javax.swing.JLabel();
        btnChangeTable = new javax.swing.JButton();
        pnlBill = new javax.swing.JPanel();
        lblBillId = new javax.swing.JLabel();
        txtBillId = new javax.swing.JTextField();
        lblBillTime = new javax.swing.JLabel();
        txtBillTime = new javax.swing.JTextField();
        lblTableName = new javax.swing.JLabel();
        txtTableId = new javax.swing.JTextField();
        txtUserId = new javax.swing.JTextField();
        lblAreaName = new javax.swing.JLabel();
        txtTotalPrice = new javax.swing.JTextField();
        lblVND = new javax.swing.JLabel();
        lblTotalPrice = new javax.swing.JLabel();
        btnBook = new javax.swing.JButton();
        btnCheckout = new javax.swing.JButton();
        btnDeleteBill = new javax.swing.JButton();
        scrollPaneBillDetail = new javax.swing.JScrollPane();
        tblBillDetail = new javax.swing.JTable();
        scrollPaneProduct = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();

        menuItemEdit.setText("Sửa sản phẩm");
        menuItemEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemEditActionPerformed(evt);
            }
        });
        popupMenu.add(menuItemEdit);

        menuItemDelete.setText("Xoá sản phẩm");
        menuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDeleteActionPerformed(evt);
            }
        });
        popupMenu.add(menuItemDelete);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("THÔNG TIN CHI TIẾT BÀN");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        pnlAddProduct.setBackground(new java.awt.Color(255, 255, 255));
        pnlAddProduct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblProductName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblProductName.setText("Tên sản phẩm:");

        lblProductPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblProductPrice.setText("Đơn giá:");

        lblProductAmount.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblProductAmount.setText("Số lượng:");

        txtProductName.setEditable(false);
        txtProductName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtProductName.setFocusable(false);

        txtProductPrice.setEditable(false);
        txtProductPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtProductPrice.setFocusable(false);

        txtProductAmount.setEditable(false);
        txtProductAmount.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N

        btnAddProduct.setBackground(new java.awt.Color(40, 167, 69));
        btnAddProduct.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnAddProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnAddProduct.setText("Thêm sản phẩm");
        btnAddProduct.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnAddProduct.setBorderPainted(false);
        btnAddProduct.setFocusPainted(false);
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        lblProductAmountError.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblProductAmountError.setForeground(new java.awt.Color(240, 71, 71));
        lblProductAmountError.setText("Không được để trống");

        btnChangeTable.setBackground(new java.awt.Color(23, 162, 184));
        btnChangeTable.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnChangeTable.setForeground(new java.awt.Color(255, 255, 255));
        btnChangeTable.setText("Đổi bàn");
        btnChangeTable.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnChangeTable.setBorderPainted(false);
        btnChangeTable.setFocusPainted(false);
        btnChangeTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAddProductLayout = new javax.swing.GroupLayout(pnlAddProduct);
        pnlAddProduct.setLayout(pnlAddProductLayout);
        pnlAddProductLayout.setHorizontalGroup(
            pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddProductLayout.createSequentialGroup()
                        .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChangeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlAddProductLayout.createSequentialGroup()
                        .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProductName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProductName)
                            .addGroup(pnlAddProductLayout.createSequentialGroup()
                                .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlAddProductLayout.createSequentialGroup()
                                        .addComponent(lblProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtProductAmount))
                                    .addComponent(lblProductAmountError, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        pnlAddProductLayout.setVerticalGroup(
            pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductName)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductAmount)
                    .addComponent(txtProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProductPrice)
                    .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnChangeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblProductAmountError))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlBill.setBackground(new java.awt.Color(255, 255, 255));
        pnlBill.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblBillId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblBillId.setText("Mã hoá đơn:");

        txtBillId.setEditable(false);
        txtBillId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtBillId.setFocusable(false);

        lblBillTime.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblBillTime.setText("Thời gian:");

        txtBillTime.setEditable(false);
        txtBillTime.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtBillTime.setFocusable(false);

        lblTableName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblTableName.setText("Tên bàn:");

        txtTableId.setEditable(false);
        txtTableId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtTableId.setFocusable(false);

        txtUserId.setEditable(false);
        txtUserId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtUserId.setFocusable(false);

        lblAreaName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblAreaName.setText("Tên  nhân viên:");

        txtTotalPrice.setEditable(false);
        txtTotalPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtTotalPrice.setFocusable(false);

        lblVND.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblVND.setText("VNĐ");

        lblTotalPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblTotalPrice.setText("Tổng tiền:");

        btnBook.setBackground(new java.awt.Color(0, 123, 255));
        btnBook.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnBook.setForeground(new java.awt.Color(255, 255, 255));
        btnBook.setText("Đặt bàn");
        btnBook.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnBook.setBorderPainted(false);
        btnBook.setFocusPainted(false);
        btnBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookActionPerformed(evt);
            }
        });

        btnCheckout.setBackground(new java.awt.Color(0, 123, 255));
        btnCheckout.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnCheckout.setForeground(new java.awt.Color(255, 255, 255));
        btnCheckout.setText("Thanh toán");
        btnCheckout.setBorderPainted(false);
        btnCheckout.setFocusPainted(false);
        btnCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckoutActionPerformed(evt);
            }
        });

        btnDeleteBill.setBackground(new java.awt.Color(220, 53, 69));
        btnDeleteBill.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnDeleteBill.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteBill.setText("Huỷ đơn");
        btnDeleteBill.setBorderPainted(false);
        btnDeleteBill.setFocusPainted(false);
        btnDeleteBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBillLayout = new javax.swing.GroupLayout(pnlBill);
        pnlBill.setLayout(pnlBillLayout);
        pnlBillLayout.setHorizontalGroup(
            pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBillLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addComponent(lblBillId, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBillId))
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addComponent(btnBook, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnCheckout)
                        .addGap(0, 0, 0)
                        .addComponent(btnDeleteBill)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addComponent(lblBillTime, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBillTime)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblTotalPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTableName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAreaName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUserId, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBillLayout.createSequentialGroup()
                        .addComponent(txtTotalPrice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblVND, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTableId))
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
                            .addComponent(txtBillId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBillTime)
                            .addComponent(txtBillTime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTableName)
                            .addComponent(txtTableId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAreaName)
                            .addComponent(txtUserId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblVND)
                        .addComponent(lblTotalPrice))
                    .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBook, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDeleteBill, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        scrollPaneBillDetail.setBackground(new java.awt.Color(255, 255, 255));
        scrollPaneBillDetail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPaneBillDetail.setComponentPopupMenu(popupMenu);
        scrollPaneBillDetail.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N

        tblBillDetail.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
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
        tblBillDetail.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblBillDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillDetailMouseClicked(evt);
            }
        });
        scrollPaneBillDetail.setViewportView(tblBillDetail);

        scrollPaneProduct.setBackground(new java.awt.Color(255, 255, 255));
        scrollPaneProduct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblProduct.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
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
        tblProduct.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        scrollPaneProduct.setViewportView(tblProduct);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlAddProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPaneProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPaneBillDetail, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlAddProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneBillDetail, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addComponent(scrollPaneProduct))
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
                btnBook.setVisible(false);
                loadingBill();
            } else {
                JOptionPane.showMessageDialog(this, billCreate.get("message"));
            }
        } catch (HeadlessException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(null, response, "btnBookActionPerformed"));
        }
    }//GEN-LAST:event_btnBookActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        try {
            int amount = 0;
            String product_amount = txtProductAmount.getText().trim();
            boolean validate = true;
            txtProductAmount.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(34, 36, 40)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)));
            lblProductAmount.setForeground(new Color(0, 0, 0));
            lblProductAmountError.setVisible(false);

            if (product_amount.equals("")) {
                txtProductAmount.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(240, 71, 71)),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)));
                lblProductAmount.setForeground(new Color(240, 71, 71));
                lblProductAmountError.setText("Số lượng không được để trống");
                lblProductAmountError.setVisible(true);
                validate = false;
            } else {
                if (!Common.isInteger(product_amount)) {
                    txtProductAmount.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(240, 71, 71)),
                            BorderFactory.createEmptyBorder(5, 8, 5, 8)));
                    lblProductAmount.setForeground(new Color(240, 71, 71));
                    lblProductAmountError.setText("Số lượng chỉ nhập số nguyên dương");
                    lblProductAmountError.setVisible(true);
                    validate = false;
                } else {
                    amount = Integer.valueOf(product_amount);

                    if (amount <= 0) {
                        txtProductAmount.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(240, 71, 71)),
                                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
                        lblProductAmount.setForeground(new Color(240, 71, 71));
                        lblProductAmountError.setText("Số lượng phải lớn hơn 0");
                        lblProductAmountError.setVisible(true);
                        validate = false;
                    }

                    if (amount > 50) {
                        txtProductAmount.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(240, 71, 71)),
                                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
                        lblProductAmount.setForeground(new Color(240, 71, 71));
                        lblProductAmountError.setText("Số lượng phải nhỏ hơn 50");
                        lblProductAmountError.setVisible(true);
                        validate = false;
                    }
                }
            }

            if (validate == true) {
                billDetail = new BillDetail();
                billDetail.setBill_id(bill.getId());
                billDetail.setProduct_id(product.getId());
                billDetail.setAmount(amount);

                Map<String, Object> billDetailUpdate = billDetailDao.create(billDetail);

                if ((boolean) billDetailUpdate.get("status") == true) {
                    loadingBillDetail();
                    txtProductAmount.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, billDetailUpdate.get("message"));
                }
            }
        } catch (HeadlessException | NumberFormatException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(null, response, "btnAddProductActionPerformed"));
        }
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutActionPerformed
        try {
            bill.setTotal_price(Float.parseFloat(txtTotalPrice.getText()));
            bill.setStatus(true);

            Map<String, Object> billCheckout = billDao.update(bill);

            if ((boolean) billCheckout.get("status") == true) {
                loadingBill();
                JOptionPane.showMessageDialog(this, billCheckout.get("message"));
            } else {
                JOptionPane.showMessageDialog(this, billCheckout.get("message"));
            }
        } catch (HeadlessException | NumberFormatException e) {
            response = new BaseMessage(Constant.ERROR_RESPONSE, e.getMessage());
            log.error(Common.createMessageLog(null, response, "btnCheckoutActionPerformed"));
        }
    }//GEN-LAST:event_btnCheckoutActionPerformed

    private void menuItemEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemEditActionPerformed
        if (!Common.isNullOrEmpty(billDetail)) {
            JDModifyBillDetail dModifyBillDetail = new JDModifyBillDetail(this, true, dbUtil, this, billDetail);
            dModifyBillDetail.setVisible(true);
        }
    }//GEN-LAST:event_menuItemEditActionPerformed

    private void tblBillDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillDetailMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3) {
            popupMenu.show(tblBillDetail, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tblBillDetailMouseClicked

    private void menuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDeleteActionPerformed
        if (!Common.isNullOrEmpty(billDetail)) {
            JDDeleteBillDetail jDDeleteBillDetail = new JDDeleteBillDetail(this, true, dbUtil, this, billDetail);
            jDDeleteBillDetail.setVisible(true);
        }
    }//GEN-LAST:event_menuItemDeleteActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        callback.actionTableExit();
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void btnChangeTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeTableActionPerformed
        if (!Common.isNullOrEmpty(table) && !Common.isNullOrEmpty(bill)) {
            JDChangeTable changeTable = new JDChangeTable(this, true, dbUtil, this, table, bill);
            changeTable.setVisible(true);
        }
    }//GEN-LAST:event_btnChangeTableActionPerformed

    private void btnDeleteBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBillActionPerformed
        if (!Common.isNullOrEmpty(bill)) {
            JDDeleteBill jddb = new JDDeleteBill(parent, true, dbUtil, this, bill);
            jddb.setVisible(true);
        }
    }//GEN-LAST:event_btnDeleteBillActionPerformed

    @Override
    public void actionBillDetailDelete() {
        loadingBill();
    }

    @Override
    public void actionBillDetailModify() {
        loadingBill();
    }

    @Override
    public void actionTableChange() {
        callback.actionTableExit();
        dispose();
    }

    @Override
    public void actionBillDelete() {
        loadingBill();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnBook;
    private javax.swing.JButton btnChangeTable;
    private javax.swing.JButton btnCheckout;
    private javax.swing.JButton btnDeleteBill;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAreaName;
    private javax.swing.JLabel lblBillId;
    private javax.swing.JLabel lblBillTime;
    private javax.swing.JLabel lblProductAmount;
    private javax.swing.JLabel lblProductAmountError;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblProductPrice;
    private javax.swing.JLabel lblTableName;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JLabel lblVND;
    private javax.swing.JMenuItem menuItemDelete;
    private javax.swing.JMenuItem menuItemEdit;
    private javax.swing.JPanel pnlAddProduct;
    private javax.swing.JPanel pnlBill;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JScrollPane scrollPaneBillDetail;
    private javax.swing.JScrollPane scrollPaneProduct;
    private javax.swing.JTable tblBillDetail;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTextField txtBillId;
    private javax.swing.JTextField txtBillTime;
    private javax.swing.JTextField txtProductAmount;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtProductPrice;
    private javax.swing.JTextField txtTableId;
    private javax.swing.JTextField txtTotalPrice;
    private javax.swing.JTextField txtUserId;
    // End of variables declaration//GEN-END:variables
}
