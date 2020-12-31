/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.GUI.table;

import coffeeshop.DAO.BillDao;
import coffeeshop.DAO.BillDetailDao;
import coffeeshop.DAO.ProductDao;
import coffeeshop.DTO.Area;
import coffeeshop.DTO.Bill;
import coffeeshop.DTO.BillDetail;
import coffeeshop.DTO.Product;
import coffeeshop.DTO.Table;
import coffeeshop.DTO.User;
import coffeeshop.Util.Common;
import coffeeshop.Util.DbUtil;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TUANANH-PC
 */
public final class JDTable extends javax.swing.JDialog implements JDModifyBillDetail.CallbackBillDetailModify, JDDeleteBillDetail.CallbackBillDetailDelete {

    CallbackTableExit callback;

    @Override
    public void actionBillDetailDelete() {
        loadingBill();
    }

    @Override
    public void actionBillDetailModify() {
        loadingBill();
    }

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
//    Frame parent;

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
    public JDTable(java.awt.Frame parent, boolean modal, DbUtil dbUtil, CallbackTableExit callback, User user, Table table) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);

        this.callback = callback;
        this.user = user;
        this.table = table;

        this.dbUtil = dbUtil;
        this.productDao = new ProductDao(dbUtil);
        this.billDao = new BillDao(dbUtil);
        this.billDetailDao = new BillDetailDao(dbUtil);

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

            if (!products.isEmpty()) {
                products.forEach(pro -> {
                    dtm.addRow(new Object[]{pro.getId(), pro.getName(), pro.getPrice(), pro.getCategory_name()});
                });

                tblProduct.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
                    int position = tblProduct.getSelectedRow();
                    if (position >= 0) {
                        product = products.get(position);

                        txtProductId.setText(String.valueOf(product.getId()));
                        txtProductName.setText(String.valueOf(product.getName()));
                        txtProductPrice.setText(String.valueOf(product.getPrice()));
                        txtProductAmount.setText("");
                    }
                });
            }

            tblProduct.setModel(dtm);
            tblProduct.changeSelection(0, 0, false, false);
            tblProduct.setRowSelectionInterval(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadingBill() {
        try {
            bill = null;
            billDetail = null;
            billDetails = null;

            tblBillDetail.removeAll();
            tblBillDetail.setModel(new DefaultTableModel());
            DefaultTableModel myTableModel = (DefaultTableModel) this.tblBillDetail.getModel();
            while (myTableModel.getRowCount() > 0) {
                myTableModel.removeRow(0);
            }

            txtUserId.setText(user.getName());
            txtTableId.setText(table.getName());

            txtBillId.setText("");
            txtBillTime.setText("");
            txtTotalPrice.setText("");

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
                loadingBillDetail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadingBillDetail() {
        try {
            btnCheckout.setEnabled(false);
            billDetails = billDetailDao.getAll(bill.getId());

            String columns[] = {"Id", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"};
            DefaultTableModel dtm = new DefaultTableModel(columns, 0);

            Float total_price = 0f;

            if (!Common.isNullOrEmpty(billDetails)) {
                btnCheckout.setEnabled(true);

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
            }

            tblBillDetail.setModel(dtm);
            tblBillDetail.changeSelection(0, 0, false, false);
            tblProduct.setRowSelectionInterval(0, 0);
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

        popupMenu = new javax.swing.JPopupMenu();
        menuItemEdit = new javax.swing.JMenuItem();
        menuItemDelete = new javax.swing.JMenuItem();
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
        lblProductAmountError = new javax.swing.JLabel();
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
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        lblProductId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblProductId.setText("Mã sản phẩm:");

        lblProductName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblProductName.setText("Tên sản phẩm:");

        lblProductPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblProductPrice.setText("Đơn giá:");

        lblProductAmount.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblProductAmount.setText("Số lượng:");

        txtProductId.setEditable(false);
        txtProductId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtProductId.setFocusable(false);

        txtProductName.setEditable(false);
        txtProductName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtProductName.setFocusable(false);

        txtProductPrice.setEditable(false);
        txtProductPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtProductPrice.setFocusable(false);

        txtProductAmount.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N

        btnAddProduct.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnAddProduct.setText("Thêm sản phẩm");
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        lblProductAmountError.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblProductAmountError.setForeground(new java.awt.Color(240, 71, 71));
        lblProductAmountError.setText("Không được để trống");

        javax.swing.GroupLayout pnlAddProductLayout = new javax.swing.GroupLayout(pnlAddProduct);
        pnlAddProduct.setLayout(pnlAddProductLayout);
        pnlAddProductLayout.setHorizontalGroup(
            pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddProductLayout.createSequentialGroup()
                        .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblProductId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblProductName, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProductId)
                            .addComponent(txtProductName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblProductPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtProductPrice)
                            .addComponent(txtProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlAddProductLayout.createSequentialGroup()
                        .addComponent(btnAddProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblProductAmountError, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        pnlAddProductLayout.setVerticalGroup(
            pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProductPrice)
                    .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductName)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProductAmount)
                    .addComponent(txtProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProductAmountError))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblBillId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lblBillId.setText("Mã hoá đơn:");

        txtBillId.setEditable(false);
        txtBillId.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtBillId.setFocusable(false);

        btnBook.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnBook.setText("Đặt bàn");
        btnBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookActionPerformed(evt);
            }
        });

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

        btnCheckout.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
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
                            .addComponent(txtBillId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBillTime)
                            .addComponent(txtBillTime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTableName)
                            .addComponent(txtTableId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAreaName)
                            .addComponent(txtUserId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCheckout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBook, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblVND)
                        .addComponent(lblTotalPrice)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scrollPaneBillDetail.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scrollPaneBillDetail.setComponentPopupMenu(popupMenu);
        scrollPaneBillDetail.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N

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
        tblBillDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillDetailMouseClicked(evt);
            }
        });
        scrollPaneBillDetail.setViewportView(tblBillDetail);

        scrollPaneProduct.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

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
        scrollPaneProduct.setViewportView(tblProduct);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                    .addComponent(pnlAddProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(pnlAddProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneBillDetail, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
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
                btnBook.setEnabled(false);
                btnAddProduct.setEnabled(true);
                loadingBill();
            } else {
                JOptionPane.showMessageDialog(rootPane, billCreate.get("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                    JOptionPane.showMessageDialog(rootPane, billDetailUpdate.get("message"));
                }
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
                JOptionPane.showMessageDialog(rootPane, billCheckout.get("message"));
            } else {
                JOptionPane.showMessageDialog(rootPane, billCheckout.get("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnCheckoutActionPerformed

    private void menuItemEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemEditActionPerformed
        System.out.println("bill detail: " + billDetail);
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
        System.out.println("bill detail: " + billDetail);
        if (!Common.isNullOrEmpty(billDetail)) {
            JDDeleteBillDetail jDDeleteBillDetail = new JDDeleteBillDetail(this, true, dbUtil, this, billDetail);
            jDDeleteBillDetail.setVisible(true);
        }
    }//GEN-LAST:event_menuItemDeleteActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        callback.actionTableExit();
        dispose();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnBook;
    private javax.swing.JButton btnCheckout;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAreaName;
    private javax.swing.JLabel lblBillId;
    private javax.swing.JLabel lblBillTime;
    private javax.swing.JLabel lblProductAmount;
    private javax.swing.JLabel lblProductAmountError;
    private javax.swing.JLabel lblProductId;
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
    private javax.swing.JTextField txtProductId;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtProductPrice;
    private javax.swing.JTextField txtTableId;
    private javax.swing.JTextField txtTotalPrice;
    private javax.swing.JTextField txtUserId;
    // End of variables declaration//GEN-END:variables
}
