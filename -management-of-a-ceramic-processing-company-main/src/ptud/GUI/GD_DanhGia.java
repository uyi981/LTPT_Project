/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ptud.GUI;


import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import ptud.DAO.DAO_BangDanhGiaCongNhan;
import ptud.DAO.DAO_BangDanhGiaNhanVien;
import ptud.DAO.DAO_CongNhan;
import ptud.DAO.DAO_NhanVien;
import ptud.Entity.BangDanhGiaCongNhan;
import ptud.Entity.BangDanhGiaNhanVien;

import ptud.Entity.CongNhan;
import ptud.Entity.NhanVien;
import ptud.ults.ImageCus;

/**
 *
 * @author TomTom
 */
public class GD_DanhGia extends javax.swing.JPanel {

    private Timer timer;
    private static int yearCN;
    private static int yearNV;

    private boolean kiemTraGiaTri(float value) {
        return value >= 0 && value <= 10;
    }

    private void handlerBtnBack() {
        Layout.instance.showLayout("tabNS");
        GD_QLNS.instance.updateData();
    }

//  tab NV
    private void populateNhanVienTable() {
        DefaultTableModel model = (DefaultTableModel) tableNV.getModel();
        model.setRowCount(0); // Clear existing data

        DAO_NhanVien daoNhanVien = DAO_NhanVien.getInstance();
        ArrayList<NhanVien> danhSachNhanVien = daoNhanVien.getAll();

        for (NhanVien nhanVien : danhSachNhanVien) {
            DAO_BangDanhGiaNhanVien daoBangDanhGia = DAO_BangDanhGiaNhanVien.getInstance();
            BangDanhGiaNhanVien bangDanhGia = daoBangDanhGia.getBangDanhGiaCuaNhanVien(nhanVien.getMaNV(), yearNV);
            model.addRow(new Object[]{nhanVien.getMaNV(), nhanVien.getTen(),
                bangDanhGia == null ? "" : bangDanhGia.getDiemHieuSuat() + "",
                bangDanhGia == null ? "" : bangDanhGia.getDiemChuyenMon() + "",
                bangDanhGia == null ? "" : bangDanhGia.getDiemThaiDo() + "",
                bangDanhGia == null ? "" : bangDanhGia.getDiemChuyenCan() + "",
                bangDanhGia == null ? "" : bangDanhGia.tinhTongDiem() + "",
                bangDanhGia == null ? "" : bangDanhGia.getBac() + ""
            });
        }
    }

    private void handlerBtnDanhGiaNV() {
        DefaultTableModel model = (DefaultTableModel) tableNV.getModel();
        DAO_NhanVien daoNhanVien = DAO_NhanVien.getInstance();

        for (int row = 0; row < model.getRowCount(); row++) {
            try {
                String maNV = model.getValueAt(row, 0).toString();

                // Lấy điểm từ các cột
                float diemHieuSuat = Float.parseFloat(model.getValueAt(row, 2).toString());
                float diemChuyenMon = Float.parseFloat(model.getValueAt(row, 3).toString());
                float diemThaiDo = Float.parseFloat(model.getValueAt(row, 4).toString());
                float diemChuyenCan = Float.parseFloat(model.getValueAt(row, 5).toString());

                // Kiểm tra giá trị nằm trong khoảng từ 0 đến 10
                if (kiemTraGiaTri(diemHieuSuat) && kiemTraGiaTri(diemChuyenMon)
                        && kiemTraGiaTri(diemThaiDo) && kiemTraGiaTri(diemChuyenCan)) {

                    double tongDiem = diemHieuSuat + diemChuyenMon + diemThaiDo + diemChuyenCan;

                    BangDanhGiaNhanVien bangDanhGiaNhanVien = new BangDanhGiaNhanVien(daoNhanVien.get(maNV), row, diemChuyenCan, diemChuyenMon, diemHieuSuat, diemThaiDo);

                    model.setValueAt(tongDiem + "", row, 6);
                    model.setValueAt(bangDanhGiaNhanVien.getBac(), row, 7);
                } else {
                    JOptionPane.showMessageDialog(this, "Giá trị của các cột phải nằm trong khoảng từ 0 đến 10.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    tableCN.changeSelection(row, 2, false, false); 
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá trị số cho các cột điểm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                tableCN.changeSelection(row, 2, false, false); 
                return;
            }
        }
    }

    private void handlerBtnXacNhanNV() {
        DefaultTableModel model = (DefaultTableModel) tableNV.getModel();
        DAO_NhanVien daoNhanVien = DAO_NhanVien.getInstance();
        DAO_BangDanhGiaNhanVien daoBangDanhGiaNhanVien = DAO_BangDanhGiaNhanVien.getInstance();

        for (int row = 0; row < model.getRowCount(); row++) {
            try {
                String maNV = model.getValueAt(row, 0).toString();
                float diemHieuSuat = Float.parseFloat(model.getValueAt(row, 2).toString());
                float diemChuyenMon = Float.parseFloat(model.getValueAt(row, 3).toString());
                float diemThaiDo = Float.parseFloat(model.getValueAt(row, 4).toString());
                float diemChuyenCan = Float.parseFloat(model.getValueAt(row, 5).toString());
                char bac = model.getValueAt(row, 7).toString().charAt(0);

                BangDanhGiaNhanVien bangDanhGiaNhanVien = new BangDanhGiaNhanVien(daoNhanVien.get(maNV),
                        yearNV,
                        diemChuyenCan, diemChuyenMon, diemThaiDo, diemHieuSuat,
                        bac);

                daoBangDanhGiaNhanVien.addOrUpdate(bangDanhGiaNhanVien);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(this, "Xác nhận lưu trữ thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

    }

    private void handlerChangeYearNV() {
        yearNV = txtYearNV.getYear();
        populateNhanVienTable();
    }
//  tab CN
    private void populateCongNhanTable() {
        DefaultTableModel model = (DefaultTableModel) tableCN.getModel();
        model.setRowCount(0); // Clear existing data

        DAO_CongNhan daoCongNhan = DAO_CongNhan.getInstance();
        ArrayList<CongNhan> danhSachCongNhan = daoCongNhan.getAll();

        for (CongNhan congNhan : danhSachCongNhan) {
            DAO_BangDanhGiaCongNhan daoBangDanhGia = DAO_BangDanhGiaCongNhan.getInstance();
            BangDanhGiaCongNhan bangDanhGia = daoBangDanhGia.getBangDanhGiaCuaCongNhan(congNhan.getMaCN(), yearCN);
            model.addRow(new Object[]{congNhan.getMaCN(), congNhan.getTen(),
                bangDanhGia == null ? "" : bangDanhGia.getDiemHieuSuat() + "",
                bangDanhGia == null ? "" : bangDanhGia.getDiemchuyenMon() + "",
                bangDanhGia == null ? "" : bangDanhGia.getDiemThaiDo() + "",
                bangDanhGia == null ? "" : bangDanhGia.getDiemChuyenCan() + "",
                bangDanhGia == null ? "" : bangDanhGia.tinhTongDiem() + "",
                bangDanhGia == null ? "" : bangDanhGia.getBac() + ""
            });
        }
    }

    private void handlerBtnDanhGiaCN() {
        DefaultTableModel model = (DefaultTableModel) tableCN.getModel();
        DAO_CongNhan daoCongNhan = DAO_CongNhan.getInstance();

        for (int row = 0; row < model.getRowCount(); row++) {
            try {
                String maCN = model.getValueAt(row, 0).toString();
                String tenCN = model.getValueAt(row, 1).toString();

                // Lấy điểm từ các cột
                float diemHieuSuat = Float.parseFloat(model.getValueAt(row, 2).toString());
                float diemChuyenMon = Float.parseFloat(model.getValueAt(row, 3).toString());
                float diemThaiDo = Float.parseFloat(model.getValueAt(row, 4).toString());
                float diemChuyenCan = Float.parseFloat(model.getValueAt(row, 5).toString());

                // Kiểm tra giá trị nằm trong khoảng từ 0 đến 10
                if (kiemTraGiaTri(diemHieuSuat) && kiemTraGiaTri(diemChuyenMon)
                        && kiemTraGiaTri(diemThaiDo) && kiemTraGiaTri(diemChuyenCan)) {

                    double tongDiem = diemHieuSuat + diemChuyenMon + diemThaiDo + diemChuyenCan;

                    BangDanhGiaCongNhan bangDanhGiaCongNhan = new BangDanhGiaCongNhan(daoCongNhan.get(maCN), row, diemChuyenCan, diemChuyenMon, diemHieuSuat, diemThaiDo);

                    model.setValueAt(tongDiem + "", row, 6);
                    model.setValueAt(bangDanhGiaCongNhan.getBac(), row, 7);

                } else {
                    JOptionPane.showMessageDialog(this, "Giá trị của các cột phải nằm trong khoảng từ 0 đến 10.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    tableCN.changeSelection(row, 2, false, false);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá trị số cho các cột điểm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                tableCN.changeSelection(row, 2, false, false); // Di chuyển đến cột 2 (diemHieuSuat) trong hàng đang xét
                return;
            }
        }
    }

    private void handlerBtnXacNhanCN() {
        DefaultTableModel model = (DefaultTableModel) tableCN.getModel();
        DAO_CongNhan daoCongNhan = DAO_CongNhan.getInstance();
        DAO_BangDanhGiaCongNhan daoBangDanhGiaCongNhan = DAO_BangDanhGiaCongNhan.getInstance();

        for (int row = 0; row < model.getRowCount(); row++) {
            try {
                String maCN = model.getValueAt(row, 0).toString();
                float diemHieuSuat = Float.parseFloat(model.getValueAt(row, 2).toString());
                float diemChuyenMon = Float.parseFloat(model.getValueAt(row, 3).toString());
                float diemThaiDo = Float.parseFloat(model.getValueAt(row, 4).toString());
                float diemChuyenCan = Float.parseFloat(model.getValueAt(row, 5).toString());
                char bac = model.getValueAt(row, 7).toString().charAt(0);

                BangDanhGiaCongNhan bangDanhGiaCongNhan = new BangDanhGiaCongNhan(daoCongNhan.get(maCN),
                        yearCN,
                        diemChuyenCan, diemChuyenMon, diemThaiDo, diemHieuSuat,
                        bac);

                daoBangDanhGiaCongNhan.addOrUpdate(bangDanhGiaCongNhan);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(this, "Xác nhận lưu trữ thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

    }

    private void handlerChangeYearCN() {
        yearCN = txtYearCN.getYear();
        populateCongNhanTable();
    }

    /**
     * Creates new form GD_DanhGia
     */
    public GD_DanhGia() {
        yearCN = LocalDate.now().getYear();
        yearNV = LocalDate.now().getYear();
        initComponents();
        txtYearNV.setYear(yearNV);
        txtYearCN.setYear(yearCN);
        btnSearchCN.setIcon(ImageCus.getScaledImageIcon("/assets/icons/search.jpeg", 30, 30));
        btnSearchNV.setIcon(ImageCus.getScaledImageIcon("/assets/icons/search.jpeg", 30, 30));
        populateCongNhanTable();
        populateNhanVienTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        bode = new javax.swing.JTabbedPane();
        tabCN = new javax.swing.JPanel();
        contentCN = new javax.swing.JPanel();
        scrollTableCN = new javax.swing.JScrollPane();
        tableCN = new javax.swing.JTable();
        option = new javax.swing.JPanel();
        txtSearchCN = new javax.swing.JTextField();
        btnSearchCN = new javax.swing.JButton();
        btnXacNhanCN = new javax.swing.JButton();
        btnDanhGiaCN = new javax.swing.JButton();
        txtYearCN = new com.toedter.calendar.JYearChooser();
        tabNV = new javax.swing.JPanel();
        contentNV = new javax.swing.JPanel();
        scrollTableNV = new javax.swing.JScrollPane();
        tableNV = new javax.swing.JTable();
        optionNV = new javax.swing.JPanel();
        txtSearchNV = new javax.swing.JTextField();
        btnSearchNV = new javax.swing.JButton();
        searchCriteriaNV = new javax.swing.JComboBox<>();
        btnXacNhanNV = new javax.swing.JButton();
        btnDanhGiaNV = new javax.swing.JButton();
        txtYearNV = new com.toedter.calendar.JYearChooser();

        setLayout(new java.awt.BorderLayout());

        header.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(198, 222, 192));
        jPanel1.setPreferredSize(new java.awt.Dimension(715, 40));

        btnBack.setBackground(new java.awt.Color(238, 250, 235));
        btnBack.setForeground(new java.awt.Color(0, 0, 0));
        btnBack.setText("Trở về");
        btnBack.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnBack.setPreferredSize(new java.awt.Dimension(150, 30));
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ĐÁNH GIÁ NHÂN SỰ");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1074, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1074, Short.MAX_VALUE)
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(header, java.awt.BorderLayout.PAGE_START);

        tabCN.setLayout(new java.awt.BorderLayout());

        tableCN.setBackground(new java.awt.Color(255, 255, 255));
        tableCN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "1", null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã CN", "Tên công nhân", "Điểm hiệu suất", "Điểm chuyên môn", "Điểm thái độ", "Điểm chuyên cần", "Tổng điểm", "Xếp loại"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, false, false

            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCN.setRowHeight(30);
        scrollTableCN.setViewportView(tableCN);

        option.setPreferredSize(new java.awt.Dimension(884, 60));

        txtSearchCN.setText("Tìm công nhân");
        txtSearchCN.setBorder(null);
        txtSearchCN.setPreferredSize(new java.awt.Dimension(71, 30));
        txtSearchCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchCNActionPerformed(evt);
            }
        });

        btnSearchCN.setBackground(new java.awt.Color(198, 222, 192));
        btnSearchCN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/search.jpeg"))); // NOI18N
        btnSearchCN.setBorder(null);
        btnSearchCN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchCN.setPreferredSize(new java.awt.Dimension(30, 30));
        btnSearchCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCNActionPerformed(evt);
            }
        });

        btnXacNhanCN.setBackground(new java.awt.Color(198, 222, 192));
        btnXacNhanCN.setForeground(new java.awt.Color(0, 0, 0));
        btnXacNhanCN.setText("Xác nhận");
        btnXacNhanCN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnXacNhanCN.setPreferredSize(new java.awt.Dimension(150, 30));
        btnXacNhanCN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXacNhanCNMouseClicked(evt);
            }
        });
        btnXacNhanCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanCNActionPerformed(evt);
            }
        });

        btnDanhGiaCN.setBackground(new java.awt.Color(198, 222, 192));
        btnDanhGiaCN.setForeground(new java.awt.Color(0, 0, 0));
        btnDanhGiaCN.setText("Đánh giá");
        btnDanhGiaCN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnDanhGiaCN.setPreferredSize(new java.awt.Dimension(150, 30));
        btnDanhGiaCN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDanhGiaCNMouseClicked(evt);
            }
        });

        txtYearCN.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtYearCNPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout optionLayout = new javax.swing.GroupLayout(option);
        option.setLayout(optionLayout);
        optionLayout.setHorizontalGroup(
            optionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(txtYearCN, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDanhGiaCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXacNhanCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearchCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtSearchCN, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(265, Short.MAX_VALUE))
        );
        optionLayout.setVerticalGroup(
            optionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(optionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtYearCN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(optionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearchCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXacNhanCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDanhGiaCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout contentCNLayout = new javax.swing.GroupLayout(contentCN);
        contentCN.setLayout(contentCNLayout);
        contentCNLayout.setHorizontalGroup(
            contentCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTableCN)
            .addComponent(option, javax.swing.GroupLayout.DEFAULT_SIZE, 1074, Short.MAX_VALUE)

        );
        contentCNLayout.setVerticalGroup(
            contentCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentCNLayout.createSequentialGroup()
                .addComponent(option, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrollTableCN, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        tabCN.add(contentCN, java.awt.BorderLayout.CENTER);

        bode.addTab("Công nhân", tabCN);

        tabNV.setLayout(new java.awt.BorderLayout());

        tableNV.setBackground(new java.awt.Color(255, 255, 255));
        tableNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NV", "Tên nhân viên", "Điểm hiệu suất", "Điểm chuyên môn", "Điểm thái độ", "Điểm chuyên cần", "Tổng điểm", "Xếp loại"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableNV.setRowHeight(30);
        scrollTableNV.setViewportView(tableNV);

        optionNV.setPreferredSize(new java.awt.Dimension(884, 60));

        txtSearchNV.setText("Tìm nhân viên");
        txtSearchNV.setBorder(null);
        txtSearchNV.setPreferredSize(new java.awt.Dimension(71, 30));
        txtSearchNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchNVActionPerformed(evt);
            }
        });

        btnSearchNV.setBackground(new java.awt.Color(198, 222, 192));
        btnSearchNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/search.jpeg"))); // NOI18N
        btnSearchNV.setBorder(null);
        btnSearchNV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchNV.setPreferredSize(new java.awt.Dimension(30, 30));
        btnSearchNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchNVActionPerformed(evt);
            }
        });

        searchCriteriaNV.setBackground(new java.awt.Color(198, 222, 192));
        searchCriteriaNV.setForeground(new java.awt.Color(0, 0, 0));
        searchCriteriaNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã CN", "Tên nhân viên", " ", " " }));
        searchCriteriaNV.setBorder(null);
        searchCriteriaNV.setMinimumSize(new java.awt.Dimension(100, 30));
        searchCriteriaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchCriteriaNVActionPerformed(evt);
            }
        });
        btnXacNhanNV.setBackground(new java.awt.Color(198, 222, 192));
        btnXacNhanNV.setForeground(new java.awt.Color(0, 0, 0));
        btnXacNhanNV.setText("Xác nhận");
        btnXacNhanNV.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnXacNhanNV.setPreferredSize(new java.awt.Dimension(150, 30));
        btnXacNhanNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXacNhanNVMouseClicked(evt);
            }
        });
        btnXacNhanNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanNVActionPerformed(evt);
            }
        });

        btnDanhGiaNV.setBackground(new java.awt.Color(198, 222, 192));
        btnDanhGiaNV.setForeground(new java.awt.Color(0, 0, 0));
        btnDanhGiaNV.setText("Đánh giá");
        btnDanhGiaNV.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnDanhGiaNV.setPreferredSize(new java.awt.Dimension(150, 30));
        btnDanhGiaNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDanhGiaNVMouseClicked(evt);
            }
        });

        txtYearNV.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtYearNVPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout optionNVLayout = new javax.swing.GroupLayout(optionNV);
        optionNV.setLayout(optionNVLayout);
        optionNVLayout.setHorizontalGroup(
            optionNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionNVLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(txtYearNV, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDanhGiaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXacNhanNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearchNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtSearchNV, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114)
                .addComponent(searchCriteriaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        optionNVLayout.setVerticalGroup(
            optionNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionNVLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(optionNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtYearNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(optionNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(optionNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchCriteriaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearchNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(optionNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSearchNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, optionNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnXacNhanNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnDanhGiaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout contentNVLayout = new javax.swing.GroupLayout(contentNV);
        contentNV.setLayout(contentNVLayout);
        contentNVLayout.setHorizontalGroup(
            contentNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTableNV)
            .addGroup(contentNVLayout.createSequentialGroup()
                .addComponent(optionNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 190, Short.MAX_VALUE))
        );
        contentNVLayout.setVerticalGroup(
            contentNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentNVLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(optionNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrollTableNV, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        tabNV.add(contentNV, java.awt.BorderLayout.CENTER);

        bode.addTab("Nhân viên", tabNV);

        add(bode, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXacNhanNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXacNhanNVActionPerformed

    private void btnXacNhanCNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanCNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXacNhanCNActionPerformed

    private void btnXacNhanCNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanCNMouseClicked
        handlerBtnXacNhanCN();
    }//GEN-LAST:event_btnXacNhanCNMouseClicked

    private void btnXacNhanNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanNVMouseClicked
        handlerBtnXacNhanNV();
    }//GEN-LAST:event_btnXacNhanNVMouseClicked

    private void btnDanhGiaNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDanhGiaNVMouseClicked
        handlerBtnDanhGiaNV();
    }//GEN-LAST:event_btnDanhGiaNVMouseClicked

    private void btnDanhGiaCNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDanhGiaCNMouseClicked
        handlerBtnDanhGiaCN();
    }//GEN-LAST:event_btnDanhGiaCNMouseClicked

    private void txtYearCNPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtYearCNPropertyChange
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(500, e -> {
            handlerChangeYearCN();
            timer.stop();
        });
        timer.start();
    }//GEN-LAST:event_txtYearCNPropertyChange

    private void btnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseClicked
        handlerBtnBack();
    }//GEN-LAST:event_btnBackMouseClicked

    private void txtYearNVPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtYearNVPropertyChange
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(500, e -> {
            handlerChangeYearNV();
            timer.stop();
        });
        timer.start();
    }//GEN-LAST:event_txtYearNVPropertyChange

    private void txtSearchCNActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSearchCNActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtSearchCNActionPerformed

    private void btnSearchCNActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSearchCNActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnSearchCNActionPerformed

    private void seachCriteriaCNActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_seachCriteriaCNActionPerformed
        // TODO add your handling code here:

    }// GEN-LAST:event_seachCriteriaCNActionPerformed

    private void btnDanhGiaCNActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDanhGiaCNActionPerformed

    }// GEN-LAST:event_btnDanhGiaCNActionPerformed

    private void txtSearchNVActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSearchNVActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtSearchNVActionPerformed

    private void btnSearchNVActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSearchNVActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnSearchNVActionPerformed

    private void searchCriteriaNVActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchCriteriaNVActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_searchCriteriaNVActionPerformed

    private void btnDanhGiaNVActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDanhGiaNVActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnDanhGiaNVActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane bode;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDanhGiaCN;
    private javax.swing.JButton btnDanhGiaNV;
    private javax.swing.JButton btnSearchCN;
    private javax.swing.JButton btnSearchNV;
    private javax.swing.JButton btnXacNhanCN;
    private javax.swing.JButton btnXacNhanNV;
    private javax.swing.JPanel contentCN;
    private javax.swing.JPanel contentNV;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel option;
    private javax.swing.JPanel optionNV;
    private javax.swing.JScrollPane scrollTableCN;
    private javax.swing.JScrollPane scrollTableNV;
    private javax.swing.JComboBox<String> searchCriteriaNV;
    private javax.swing.JPanel tabCN;
    private javax.swing.JPanel tabNV;
    private javax.swing.JTable tableCN;
    private javax.swing.JTable tableNV;
    private javax.swing.JTextField txtSearchCN;
    private javax.swing.JTextField txtSearchNV;
    private com.toedter.calendar.JYearChooser txtYearCN;
    private com.toedter.calendar.JYearChooser txtYearNV;
    // End of variables declaration//GEN-END:variables

}
