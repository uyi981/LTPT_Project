/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

package ptud.GUI;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import ptud.DAO.*;
import ptud.Entity.ChiTietPhanCong;
import ptud.Entity.CongDoan;
import ptud.Entity.CongNhan;
import ptud.Entity.NhanVien;
import ptud.Entity.PhieuLuongCongNhan;
import ptud.Entity.PhieuLuongNhanVien;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import java.text.MessageFormat;
import javax.swing.JTable;
/**
 *
 * @author Khanh
 */
public class GD_TinhLuong extends javax.swing.JPanel {

    /** Creates new form GD_TinhLuong */
    public GD_TinhLuong() {
        initComponents();
        try {
            init();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<CongNhan> dsCongNhan;
    public ArrayList<NhanVien> dsNhanVien;
    public ArrayList<PhieuLuongNhanVien> dsPhieuLuongNhanVien;
    public ArrayList<PhieuLuongCongNhan> dsPhieuLuongCongNhan;

    private void init() throws SQLException {
        dsNhanVien = DAO_NhanVien.getInstance().getAll();
        dsCongNhan = DAO_CongNhan.getInstance().getAll();
        loadJcomboBoxBoPhan();
        loadJcomboBoxBoPhan2();
        loadDataPhieuLuongNhanVien();
        loadDataPhieuLuongCongNhan();
    }

    private void loadJcomboBoxBoPhan() {
        DAO_BoPhan.getInstance().getAll().forEach((bp) -> {
            String maBP = bp.getMaBP();
            if (maBP.startsWith("HC")) {
                jComboBoxBoPhan.addItem(bp.getTenBP());
            }
        });
    }

    private void loadJcomboBoxBoPhan2() {
        DAO_BoPhan.getInstance().getAll().forEach((bp) -> {
            String maBP = bp.getMaBP();
            if (maBP.startsWith("SX")) {
                // jComboBoxBoPhan2.addItem(bp.getTenBP());
                jComboBoxBoPhan3.addItem(bp.getTenBP());
            }
        });
    }

    private void loadDataPhieuLuongNhanVien() {
        // thêm dsNhanVien vào jTablePhieuLuongNhanVien
        int thang = jMonthChooser1.getMonth() + 1;
        int nam = jYearChooser1.getYear();
        dsPhieuLuongNhanVien = DAO_PhieuLuongNhanVien.getInstance().getAllByThangNam(thang, nam);
        DefaultTableModel model = (DefaultTableModel) jTablePhieuLuongNhanVien.getModel();
        model.setRowCount(0);
        String tenBP = jComboBoxBoPhan.getSelectedItem().toString();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        if (dsPhieuLuongNhanVien.isEmpty()) {
            for (NhanVien nv : dsNhanVien) {
                if (tenBP.equals("Tất cả") || tenBP.equals(nv.getBoPhan().getTenBP())) {
                    Object[] row = { nv.getMaNV(), nv.getTen(), decimalFormat.format(nv.getLuongCoBan()),
                            decimalFormat.format(nv.getPhuCap()), 0 };
                    model.addRow(row);
                }
            }
        } else {
            // thêm data từ dsPhieuLuongNhanVien vào jTablePhieuLuongNhanVien
            for (PhieuLuongNhanVien plnv : dsPhieuLuongNhanVien) {
                String maNV = plnv.getMaNV();
                NhanVien nv = DAO_NhanVien.getInstance().get(maNV);
                double luongCoBan = nv.getLuongCoBan();
                double phuCap = nv.getPhuCap();
                if (tenBP.equals("Tất cả") || tenBP.equals(nv.getBoPhan().getTenBP())) {

                    Object[] row = { maNV, nv.getTen(),
                            decimalFormat.format(luongCoBan),
                            decimalFormat.format(phuCap),
                            decimalFormat.format(plnv.getLuongThucNhan()) };
                    model.addRow(row);
                }

            }
        }

        // tính tổng col cuối của tất cả các row trong jTablePhieuLuongNhanVien 
        double total = 0;
        int rowCount = jTablePhieuLuongNhanVien.getRowCount();
        int lastColumnIndex = jTablePhieuLuongNhanVien.getColumnCount() - 1;

        for (int i = 0; i < rowCount; i++) {
            String str = jTablePhieuLuongNhanVien.getValueAt(i, lastColumnIndex).toString();
            // bỏ hết các ký tự không phải số khỏi str 
            str = str.replaceAll("[^0-9]", "");
            double value = Double.parseDouble(str);
            total += value;
        }
        jTextField1.setText(decimalFormat.format(total));
        
    }

    private void loadDataPhieuLuongCongNhan() throws SQLException {
        // thêm dsNhanVien vào jTablePhieuLuongNhanVien
        int thang = jMonthChooser2.getMonth() + 1;
        int nam = jYearChooser2.getYear();
        dsPhieuLuongCongNhan = DAO_PhieuLuongCongNhan.getInstance().getAllByThangNam(thang, nam);
        
        DefaultTableModel model = (DefaultTableModel) jTablePhieuLuongCongNhan.getModel();
        model.setRowCount(0);

        String tenBP = jComboBoxBoPhan3.getSelectedItem().toString();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        if (dsPhieuLuongCongNhan.isEmpty()) {
            // DecimalFormat decimalFormat = new DecimalFormat("#,###");
            for (CongNhan nv : dsCongNhan) {
                if (tenBP.equals("Tất cả") || tenBP.equals(nv.getBoPhan().getTenBP())) {
                    Object[] row = { nv.getMaCN(), nv.getTen(), 0,
                            0, 0, 0, 0 };
                    model.addRow(row);
                }
            }
        } else {
            // thêm data từ dsPhieuLuongNhanVien vào jTablePhieuLuongNhanVien
            for (PhieuLuongCongNhan plnv : dsPhieuLuongCongNhan) {
                String maCN = plnv.getMaCN();
                CongNhan nv = DAO_CongNhan.getInstance().get(maCN);
                if (tenBP.equals("Tất cả") || tenBP.equals(nv.getBoPhan().getTenBP())) {
                    Object[] row = { maCN, nv.getTen(),
                            decimalFormat.format(plnv.getLuong()),
                         decimalFormat.format(plnv.getLuongThucNhan()) };
                    model.addRow(row);
                }
            }
        }
        double total = 0; 
        int rowCount = jTablePhieuLuongCongNhan.getRowCount();
        int lastColumnIndex = jTablePhieuLuongCongNhan.getColumnCount() - 1;
        for (int i = 0; i < rowCount; i++) {
            String str = jTablePhieuLuongCongNhan.getValueAt(i, lastColumnIndex).toString();
            str = str.replaceAll("[^0-9]", "");
            double value = Double.parseDouble(str);
            total += value;
        }
        jTextField2.setText(decimalFormat.format(total));
    }




    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxBoPhan = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jMonthChooser1 = new com.toedter.calendar.JMonthChooser();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePhieuLuongNhanVien = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButtonTinhLuong = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jMonthChooser2 = new com.toedter.calendar.JMonthChooser();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jYearChooser2 = new com.toedter.calendar.JYearChooser();
        jButton4 = new javax.swing.JButton();
        jButtonTinhLuong2 = new javax.swing.JButton();
        jComboBoxBoPhan3 = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablePhieuLuongCongNhan = new javax.swing.JTable();

        jLabel2.setText("Bộ phận:");

        jComboBoxBoPhan.setEditable(true);
        jComboBoxBoPhan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        jComboBoxBoPhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxBoPhanActionPerformed(evt);
            }
        });

        jLabel3.setText("Tháng:");

        jMonthChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jMonthChooser1PropertyChange(evt);
            }
        });

        jYearChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jYearChooser1PropertyChange(evt);
            }
        });

        jTablePhieuLuongNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Lương cơ bản", "Phụ cấp", "Lương thực nhận"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePhieuLuongNhanVien.setRowHeight(30);
        jTablePhieuLuongNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTablePhieuLuongNhanVienMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePhieuLuongNhanVien);

        jLabel5.setText("Tổng cộng:");

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("In phiếu lương");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Năm:");

        jButtonTinhLuong.setText("Tính lương");
        jButtonTinhLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonTinhLuongMouseReleased(evt);
            }
        });
        jButtonTinhLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTinhLuongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(48, 48, 48)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBoxBoPhan, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jMonthChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jYearChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(39, 39, 39)
                            .addComponent(jButtonTinhLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(268, 268, 268))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jYearChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                        .addComponent(jMonthChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBoxBoPhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButtonTinhLuong))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );

        jTabbedPane1.addTab("Tinh lương nhân viên", jPanel1);

        jLabel6.setText("Tháng:");

        jLabel7.setText("Tổng cộng:");

        jMonthChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jMonthChooser2PropertyChange(evt);
            }
        });

        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel8.setText("Năm:");

        jLabel9.setText("Bộ phận:");

        jYearChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jYearChooser2PropertyChange(evt);
            }
        });

        jButton4.setText("In phiếu lương");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton4MouseReleased(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButtonTinhLuong2.setText("Tính lương");
        jButtonTinhLuong2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonTinhLuong2MouseReleased(evt);
            }
        });
        jButtonTinhLuong2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTinhLuong2ActionPerformed(evt);
            }
        });

        jComboBoxBoPhan3.setEditable(true);
        jComboBoxBoPhan3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        jComboBoxBoPhan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxBoPhan3ActionPerformed(evt);
            }
        });

        jTablePhieuLuongCongNhan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã công nhân", "Tên công nhân", "Lương", "Lương thực nhận"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePhieuLuongCongNhan.setRowHeight(30);
        jTablePhieuLuongCongNhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTablePhieuLuongCongNhanMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTablePhieuLuongCongNhan);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(36, 36, 36)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxBoPhan3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jMonthChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jYearChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jButtonTinhLuong2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(290, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jYearChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxBoPhan3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonTinhLuong2)
                            .addComponent(jMonthChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115))
        );

        jTabbedPane1.addTab("Tính lương công nhân", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents


    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
        MessageFormat header = new MessageFormat("PHIẾU LƯƠNG NHÂN VIÊN " + jMonthChooser1.getMonth()+'/'+jYearChooser1.getYear() + "\n "
                                                + " Bộ phận " + jComboBoxBoPhan.getSelectedItem().toString()); 
        MessageFormat footer = new MessageFormat("Page{0, number, interger}"); 
        
        try {
            jTablePhieuLuongNhanVien.print(JTable.PrintMode.NORMAL, header, footer);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e); 
        }
    }//GEN-LAST:event_jButton1MouseReleased

    private void jTablePhieuLuongNhanVienMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePhieuLuongNhanVienMouseReleased

        if (evt.getClickCount() == 2) {
            int row = jTablePhieuLuongNhanVien.rowAtPoint(evt.getPoint());
            if (row != -1) {
                String maCN = (String) jTablePhieuLuongNhanVien.getValueAt(row, 0);
//                Layout.instance.showChiTietCN(maCN);
                Layout.instance.showChiTietPhieuLuongNV(dsPhieuLuongNhanVien.get(row));
            }
        }
    }//GEN-LAST:event_jTablePhieuLuongNhanVienMouseReleased

    private void jTablePhieuLuongCongNhanMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePhieuLuongCongNhanMouseReleased
        // TODO add your handling code here:
        
        if (evt.getClickCount() == 2) {
            int row = jTablePhieuLuongCongNhan.rowAtPoint(evt.getPoint());
            if (row != -1) {
                String maCN = (String) jTablePhieuLuongCongNhan.getValueAt(row, 0);
//                Layout.instance.showChiTietCN(maCN);
                Layout.instance.showChiTietPhieuLuongCN(dsPhieuLuongCongNhan.get(row));
            }
        }
    }//GEN-LAST:event_jTablePhieuLuongCongNhanMouseReleased

    private void jButton4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseReleased
        MessageFormat header = new MessageFormat("PHIẾU LƯƠNG CÔNG NHÂN " + jMonthChooser2.getMonth()+'/'+jYearChooser2.getYear() + "\n "
                                                + " Bộ phận " + jComboBoxBoPhan3.getSelectedItem().toString()); 
        MessageFormat footer = new MessageFormat("Page{0, number, interger}"); 
        
        try {
            jTablePhieuLuongCongNhan.print(JTable.PrintMode.NORMAL, header, footer);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e); 
        }
    }//GEN-LAST:event_jButton4MouseReleased

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField2ActionPerformed

    private void jComboBoxMaHopDong4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxMaHopDong4ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBoxMaHopDong4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton4ActionPerformed

    private void jButtonTinhLuongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonTinhLuongActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButtonTinhLuongActionPerformed

    private void jButtonTinhLuong2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonTinhLuong2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButtonTinhLuong2ActionPerformed

    private void jButtonTinhLuongMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonTinhLuongMouseReleased
        // TODO add your handling code here:
        if (jButtonTinhLuong.isEnabled()) {
            int thang = jMonthChooser1.getMonth() + 1;
            int nam = jYearChooser1.getYear();
            for (NhanVien nv : dsNhanVien) {
                // tạo phiếu lương nhân viên theo thang, nam nv
                // public PhieuLuongNhanVien(String maPL, int thang, int nam, String maNV,
                // double phat) {
                // mã phiếu lương là mã bộ phận + tháng + năm + mã nhân viên
                String maPL = thang + "" + nam + nv.getMaNV();
                PhieuLuongNhanVien plnv = new PhieuLuongNhanVien(maPL, thang, nam, nv.getMaNV(), 0);

                // kiểm tra pl đã tồn tại trong database chưa 
                if( DAO_PhieuLuongNhanVien.getInstance().get(maPL) == null){
                    DAO_PhieuLuongNhanVien.getInstance().insert(plnv);
                } else 
                    DAO_PhieuLuongNhanVien.getInstance().update(plnv);
                

            }
            loadDataPhieuLuongNhanVien();
        }
    }// GEN-LAST:event_jButtonTinhLuongMouseReleased

    private void jMonthChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jMonthChooser1PropertyChange

        loadDataPhieuLuongNhanVien();

    }// GEN-LAST:event_jMonthChooser1PropertyChange

    private void jYearChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jYearChooser1PropertyChange
        // TODO add your handling code here:
        loadDataPhieuLuongNhanVien();

    }// GEN-LAST:event_jYearChooser1PropertyChange

    private void jComboBoxBoPhanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxBoPhanActionPerformed
        // TODO add your handling code here:
        loadDataPhieuLuongNhanVien();

    }// GEN-LAST:event_jComboBoxBoPhanActionPerformed

    private void jComboBoxBoPhan3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxBoPhan3ActionPerformed
        try {
            // TODO add your handling code here:
            loadDataPhieuLuongCongNhan();
        } catch (SQLException ex) {
            Logger.getLogger(GD_TinhLuong.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_jComboBoxBoPhan3ActionPerformed

    private void jMonthChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jMonthChooser2PropertyChange
        try {
            // TODO add your handling code here:
            loadDataPhieuLuongCongNhan();
        } catch (SQLException ex) {
            Logger.getLogger(GD_TinhLuong.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_jMonthChooser2PropertyChange

    private void jYearChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jYearChooser2PropertyChange
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            loadDataPhieuLuongCongNhan();
        } catch (SQLException ex) {
            Logger.getLogger(GD_TinhLuong.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_jYearChooser2PropertyChange

    private void jButtonTinhLuong2MouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonTinhLuong2MouseReleased
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (jButtonTinhLuong2.isEnabled()) {
            int thang = jMonthChooser2.getMonth() + 1;
            int nam = jYearChooser2.getYear();
            for (CongNhan nv : dsCongNhan) {
                String maPL = thang + "" + nam + nv.getMaCN();
                PhieuLuongCongNhan plcn = new PhieuLuongCongNhan(maPL, thang, nam, nv.getMaCN(), 0);
                if( DAO_PhieuLuongCongNhan.getInstance().get(maPL) == null){
                    DAO_PhieuLuongCongNhan.getInstance().insert(plcn);
                } else
                    DAO_PhieuLuongCongNhan.getInstance().update(plcn);
            }
            try {
                loadDataPhieuLuongCongNhan();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }// GEN-LAST:event_jButtonTinhLuong2MouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonTinhLuong;
    private javax.swing.JButton jButtonTinhLuong2;
    private javax.swing.JComboBox<String> jComboBoxBoPhan;
    private javax.swing.JComboBox<String> jComboBoxBoPhan3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private com.toedter.calendar.JMonthChooser jMonthChooser1;
    private com.toedter.calendar.JMonthChooser jMonthChooser2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTablePhieuLuongCongNhan;
    private javax.swing.JTable jTablePhieuLuongNhanVien;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    private com.toedter.calendar.JYearChooser jYearChooser2;
    // End of variables declaration//GEN-END:variables

}
