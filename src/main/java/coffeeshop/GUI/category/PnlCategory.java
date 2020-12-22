/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.GUI.category;

import coffeeshop.DAO.CategoryDao;
import coffeeshop.DTO.Category;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Minh
 */
public class PnlCategory extends javax.swing.JPanel implements JDModify.CallbackModify, JDDelete.CallbackDelete, JDSearch.CallbackSearch {

    Frame parent;
    List<Category> list = new ArrayList<Category>();
    Category category;
    CategoryDao categoryDao = new CategoryDao();

    /**
     * Creates new form PnlCategory
     */
    public PnlCategory(Frame parent) {
        initComponents();
        this.parent = parent;
        loading(null, null);
    }

    public void loading(String name, Boolean status) {
        tblCategory.removeAll();
        if (name != null || status != null) {
            list = categoryDao.getAll(name, status);
        } else {
            list = categoryDao.getAll();
        }

        System.out.println(list);

        String columns[] = {"Id", "Tên", "Trạng thái"};
        DefaultTableModel dtm = new DefaultTableModel(columns, 0);

        if (!list.isEmpty()) {
            for (Category category : list) {
                dtm.addRow(new Object[]{category.getId(), category.getName(), category.isStatus() ? "Hoạt động" : "Không hoạt động"});
            }

            tblCategory.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    int position = tblCategory.getSelectedRow();
                    if (position < 0) {
                        position = 0;
                    }

                    category = list.get(position);
                }
            });

            tblCategory.changeSelection(0, 0, false, false);
//        tblCategory.setRowSelectionInterval(0, 0);
        }

        tblCategory.setModel(dtm);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblAdd = new javax.swing.JLabel();
        lblUpdate = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();
        lblDelete = new javax.swing.JLabel();
        lblRefresh = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCategory = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 50, 20));

        lblAdd.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        lblAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffeeshop/assets/img/icons8_add_50px_2.png"))); // NOI18N
        lblAdd.setText("Thêm mới");
        lblAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAddMouseClicked(evt);
            }
        });
        jPanel2.add(lblAdd);

        lblUpdate.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        lblUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffeeshop/assets/img/icons8_update_50px.png"))); // NOI18N
        lblUpdate.setText("Sửa đổi");
        lblUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUpdate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUpdateMouseClicked(evt);
            }
        });
        jPanel2.add(lblUpdate);

        lblSearch.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        lblSearch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffeeshop/assets/img/icons8_search_50px_1.png"))); // NOI18N
        lblSearch.setText("Tìm kiếm");
        lblSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSearchMouseClicked(evt);
            }
        });
        jPanel2.add(lblSearch);

        lblDelete.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        lblDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffeeshop/assets/img/icons8_delete_50px.png"))); // NOI18N
        lblDelete.setText("Xoá");
        lblDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDeleteMouseClicked(evt);
            }
        });
        jPanel2.add(lblDelete);

        lblRefresh.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        lblRefresh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffeeshop/assets/img/icons8_delete_50px.png"))); // NOI18N
        lblRefresh.setText("Làm mới");
        lblRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRefreshMouseClicked(evt);
            }
        });
        jPanel2.add(lblRefresh);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        tblCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblCategory);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 882, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 633, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lblAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddMouseClicked
        JDModify jdm = new JDModify(this.parent, true, this, new Category());
        jdm.setVisible(true);
    }//GEN-LAST:event_lblAddMouseClicked

    private void lblUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUpdateMouseClicked
        JDModify jdm = new JDModify(this.parent, true, this, category);
        jdm.setVisible(true);
    }//GEN-LAST:event_lblUpdateMouseClicked

    private void lblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseClicked
        JDSearch jds = new JDSearch(this.parent, true, this);
        jds.setVisible(true);
    }//GEN-LAST:event_lblSearchMouseClicked

    private void lblDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDeleteMouseClicked
        JDDelete jdd = new JDDelete(this.parent, true, this, category);
        jdd.setVisible(true);
    }//GEN-LAST:event_lblDeleteMouseClicked

    private void lblRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefreshMouseClicked
        loading(null, null);
    }//GEN-LAST:event_lblRefreshMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblAdd;
    private javax.swing.JLabel lblDelete;
    private javax.swing.JLabel lblRefresh;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblUpdate;
    private javax.swing.JTable tblCategory;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionModify() {
        loading(null, null);
    }

    @Override
    public void actionDelete() {
        loading(null, null);
    }

    @Override
    public void actionSearch(String name, Boolean status) {
        loading(name, status);
    }
}
