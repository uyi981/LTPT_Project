/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ptud.GUI;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import ptud.DAO.DAO_CongNhan;
import ptud.DAO.DAO_NhanVien;
import ptud.Entity.CongNhan;
import ptud.Entity.NhanVien;
import ptud.ults.ImageCus;

/**
 *
 * @author TomTom
 */
public class GD_QLNS extends javax.swing.JPanel {

    public static GD_QLNS instance;
//  cập nhật lại khi mở lại tab này

    public void updateData() {
        populateNhanVienTable();
        populatCongNhanTable();
    }

//  thêm NV vào table
    private void populateNhanVienTable() {
        DefaultTableModel model = (DefaultTableModel) tblNV.getModel();
        model.setRowCount(0); // Clear existing data

        DAO_NhanVien daoNhanVien = DAO_NhanVien.getInstance();
        ArrayList<NhanVien> danhSachNhanVien = daoNhanVien.getAll();

        for (NhanVien nhanVien : danhSachNhanVien) {
            // Add each NhanVien object to the table
            model.addRow(new Object[]{nhanVien.getMaNV(), nhanVien.getTen(), nhanVien.getNgayBatDauLam(),
                nhanVien.getCccd(), nhanVien.getDienThoai(), nhanVien.isGioiTinh() ? "Nam" : "Nữ",
                nhanVien.getNgaySinh(), nhanVien.getPhuCap(), nhanVien.getLuongCoBan()});
        }
    }

    private void handlerBtnThoiViecNV() {
        DefaultTableModel model = (DefaultTableModel) tblNV.getModel();
        DAO_NhanVien daoNhanVien = DAO_NhanVien.getInstance();
        int[] selectedRows = tblNV.getSelectedRows();

        if (selectedRows.length > 0) {
            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thôi việc nhân viên?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                // Xóa từng dòng được chọn
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    String maNV = (String) model.getValueAt(selectedRows[i], 0);
                    daoNhanVien.deleteById(maNV);
                }

                // Cập nhật lại bảng sau khi xóa
                populateNhanVienTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn nhân viên để thôi việc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handlerBtnSearchNV() {
    }
//  thêm CN vào table

    private void populatCongNhanTable() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0); // Clear existing data

        DAO_CongNhan daoCongNhan = DAO_CongNhan.getInstance();
        ArrayList<CongNhan> danhSachCongNhan = daoCongNhan.getAll();

        for (CongNhan congNhan : danhSachCongNhan) {
            // Add each NhanVien object to the table
            model.addRow(new Object[]{congNhan.getMaCN(), congNhan.getTen(), congNhan.getNgayBatDauLam(),
                congNhan.getCccd(), congNhan.getDienThoai(), congNhan.isGioiTinh() ? "Nam" : "Nữ",
                congNhan.getNgaySinh(), congNhan.isChoPhanCong() ? "Đã phân công" : "Chưa phân công",});
        }
    }

    private void handlerBtnThoiViecCN() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        DAO_CongNhan daoCongNhan = DAO_CongNhan.getInstance();
        int[] selectedRows = jTable2.getSelectedRows();

        if (selectedRows.length > 0) {
            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thôi việc công nhân?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                // Xóa từng dòng được chọn
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    String maCN = (String) model.getValueAt(selectedRows[i], 0);
                    daoCongNhan.deleteById(maCN);
                }

                // Cập nhật lại bảng sau khi xóa
                populatCongNhanTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn công nhân để thôi việc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handlerBtnSearchCN() {
        String searchText = txtSearchCN.getText().trim();
        String searchCriteria = (String) cboTieuChiCN.getSelectedItem();

        // Kiểm tra xem searchText có rỗng hay không
        if (searchText.isEmpty()) {
            populatCongNhanTable();
            return;
        }

        // Thực hiện tìm kiếm dựa trên tiêu chí
        DAO_CongNhan daoCongNhan = DAO_CongNhan.getInstance();
        ArrayList<CongNhan> dsKetQuaTimKiem = new ArrayList<>();

        switch (searchCriteria) {
            case "Mã CN": {
                dsKetQuaTimKiem = daoCongNhan.search(searchText, "Mã CN");
                break;
            }
            case "Tên công nhân": {
                dsKetQuaTimKiem = daoCongNhan.search(searchText, "Tên công nhân");
                break;
            }
            case "Số điện thoại": {
                dsKetQuaTimKiem = daoCongNhan.search(searchText, "Số điện thoại");
                break;
            }
            default:
                JOptionPane.showMessageDialog(this, "Tiêu chí tìm kiếm không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
        }
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (CongNhan congNhan : dsKetQuaTimKiem) {
             model.addRow(new Object[]{congNhan.getMaCN(), congNhan.getTen(), congNhan.getNgayBatDauLam(),
                congNhan.getCccd(), congNhan.getDienThoai(), congNhan.isGioiTinh() ? "Nam" : "Nữ",
                congNhan.getNgaySinh(), congNhan.isChoPhanCong() ? "Đã phân công" : "Chưa phân công",});
        }
    }

    /**
     * Creates new form QLHD_GD
     */
    public GD_QLNS() {
        instance = this;
        initComponents();
        btnSearchNV.setIcon(ImageCus.getScaledImageIcon("/assets/icons/search.jpeg", 30, 30));
        btnSearchCN.setIcon(ImageCus.getScaledImageIcon("/assets/icons/search.jpeg", 30, 30));

        updateData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        tmp = new javax.swing.JPanel();
        body = new javax.swing.JTabbedPane();
        dsCn = new javax.swing.JPanel();
        option1 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        btnThoiViecCN = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        search1 = new javax.swing.JPanel();
        btnSearchCN = new javax.swing.JButton();
        txtSearchCN = new javax.swing.JTextField();
        cboTieuChiCN = new javax.swing.JComboBox<>();
        tableNV1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        dsNv = new javax.swing.JPanel();
        tableNV = new javax.swing.JScrollPane();
        tblNV = new javax.swing.JTable();
        option = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        btnThoiViecNV = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        search = new javax.swing.JPanel();
        btnSearchNV = new javax.swing.JButton();
        txtSearchNV = new javax.swing.JTextField();
        cboTieuChiNV = new javax.swing.JComboBox<>();

        setLayout(new java.awt.BorderLayout());

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setPreferredSize(new java.awt.Dimension(555, 100));
        header.setLayout(new java.awt.BorderLayout());

        title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        title.setForeground(new java.awt.Color(0, 0, 0));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("QUẢN LÝ NHÂN SỰ");
        title.setPreferredSize(new java.awt.Dimension(105, 60));
        header.add(title, java.awt.BorderLayout.NORTH);

        tmp.setBackground(new java.awt.Color(198, 222, 192));

        javax.swing.GroupLayout tmpLayout = new javax.swing.GroupLayout(tmp);
        tmp.setLayout(tmpLayout);
        tmpLayout.setHorizontalGroup(
            tmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        tmpLayout.setVerticalGroup(
            tmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        header.add(tmp, java.awt.BorderLayout.CENTER);

        add(header, java.awt.BorderLayout.NORTH);

        body.setBackground(new java.awt.Color(255, 255, 255));

        option1.setPreferredSize(new java.awt.Dimension(100, 60));

        jButton4.setBackground(new java.awt.Color(198, 222, 192));
        jButton4.setForeground(new java.awt.Color(0, 0, 0));
        jButton4.setText("Đánh giá công nhân");
        jButton4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton4.setPreferredSize(new java.awt.Dimension(150, 30));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnThoiViecCN.setBackground(new java.awt.Color(255, 102, 102));
        btnThoiViecCN.setForeground(new java.awt.Color(0, 0, 0));
        btnThoiViecCN.setText("Thôi việc công nhân");
        btnThoiViecCN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnThoiViecCN.setPreferredSize(new java.awt.Dimension(150, 30));
        btnThoiViecCN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThoiViecCNMouseClicked(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(198, 222, 192));
        jButton6.setForeground(new java.awt.Color(0, 0, 0));
        jButton6.setText("Thêm công nhân");
        jButton6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton6.setPreferredSize(new java.awt.Dimension(150, 30));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        search1.setLayout(new java.awt.BorderLayout());

        btnSearchCN.setBackground(new java.awt.Color(198, 222, 192));
        btnSearchCN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/search.jpeg"))); // NOI18N
        btnSearchCN.setBorder(null);
        btnSearchCN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchCN.setPreferredSize(new java.awt.Dimension(30, 30));
        btnSearchCN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchCNMouseClicked(evt);
            }
        });

        txtSearchCN.setBorder(null);
        txtSearchCN.setPreferredSize(new java.awt.Dimension(71, 30));

        cboTieuChiCN.setBackground(new java.awt.Color(198, 222, 192));
        cboTieuChiCN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã CN", "Tên công nhân", "Số điện thoại", "CCCD", " " }));
        cboTieuChiCN.setBorder(null);
        cboTieuChiCN.setMinimumSize(new java.awt.Dimension(100, 30));

        javax.swing.GroupLayout option1Layout = new javax.swing.GroupLayout(option1);
        option1.setLayout(option1Layout);
        option1Layout.setHorizontalGroup(
            option1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(option1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThoiViecCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearchCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtSearchCN, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cboTieuChiCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        option1Layout.setVerticalGroup(
            option1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(option1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(option1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearchCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTieuChiCN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(option1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThoiViecCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"2210001", "Lê Thị Xuân Mai", "23/04/2022", "054198003412", "0987566743", "Nữ", "11/05/1998", null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã CN", "Tên công nhân", "Ngày bắt đầu làm", "CCCD", "Số điện thoại", "Giới tính", "Ngày sinh", "Đã phân công"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jTable2.setAutoscrolls(false);
        jTable2.setRowHeight(30);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        tableNV1.setViewportView(jTable2);

        javax.swing.GroupLayout dsCnLayout = new javax.swing.GroupLayout(dsCn);
        dsCn.setLayout(dsCnLayout);
        dsCnLayout.setHorizontalGroup(
            dsCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableNV1)
            .addComponent(option1, javax.swing.GroupLayout.DEFAULT_SIZE, 1326, Short.MAX_VALUE)
        );
        dsCnLayout.setVerticalGroup(
            dsCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsCnLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(option1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tableNV1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
        );

        body.addTab("Công nhân", dsCn);

        dsNv.setBackground(new java.awt.Color(255, 255, 255));

        tblNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"2210001", "Lê Thị Xuân Mai", "23/04/2022", "054198003412", "0987566743", "Nữ", "11/05/1998",  new Double(500000.0), null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NV", "Tên nhân viên", "Ngày bắt đầu làm", "CCCD", "Số điện thoại", "Giới tính", "Ngày sinh", "Phụ cấp", "Lương cơ bảng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNV.setRowHeight(30);
        tblNV.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblNV.getTableHeader().setReorderingAllowed(false);
        tblNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNVMouseClicked(evt);
            }
        });
        tableNV.setViewportView(tblNV);
        tblNV.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        option.setPreferredSize(new java.awt.Dimension(100, 60));

        jButton3.setBackground(new java.awt.Color(198, 222, 192));
        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setText("Đánh giá nhân viên");
        jButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton3.setPreferredSize(new java.awt.Dimension(150, 30));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        btnThoiViecNV.setBackground(new java.awt.Color(255, 102, 102));
        btnThoiViecNV.setForeground(new java.awt.Color(0, 0, 0));
        btnThoiViecNV.setText("Thôi việc nhân viên");
        btnThoiViecNV.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnThoiViecNV.setPreferredSize(new java.awt.Dimension(150, 30));
        btnThoiViecNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThoiViecNVMouseClicked(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(198, 222, 192));
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Thêm nhân viên");
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton1.setPreferredSize(new java.awt.Dimension(150, 30));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        search.setLayout(new java.awt.BorderLayout());

        btnSearchNV.setBackground(new java.awt.Color(198, 222, 192));
        btnSearchNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/search.jpeg"))); // NOI18N
        btnSearchNV.setBorder(null);
        btnSearchNV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchNV.setPreferredSize(new java.awt.Dimension(30, 30));
        btnSearchNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchNVMouseClicked(evt);
            }
        });

        txtSearchNV.setBorder(null);
        txtSearchNV.setPreferredSize(new java.awt.Dimension(71, 30));

        cboTieuChiNV.setBackground(new java.awt.Color(198, 222, 192));
        cboTieuChiNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã NV", "Tên nhân viên", "Số điện thoại", "CCCD" }));
        cboTieuChiNV.setBorder(null);
        cboTieuChiNV.setMinimumSize(new java.awt.Dimension(100, 30));

        javax.swing.GroupLayout optionLayout = new javax.swing.GroupLayout(option);
        option.setLayout(optionLayout);
        optionLayout.setHorizontalGroup(
            optionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThoiViecNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearchNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtSearchNV, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cboTieuChiNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        optionLayout.setVerticalGroup(
            optionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(optionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearchNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTieuChiNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(optionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThoiViecNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dsNvLayout = new javax.swing.GroupLayout(dsNv);
        dsNv.setLayout(dsNvLayout);
        dsNvLayout.setHorizontalGroup(
            dsNvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(option, javax.swing.GroupLayout.DEFAULT_SIZE, 1326, Short.MAX_VALUE)
            .addComponent(tableNV)
        );
        dsNvLayout.setVerticalGroup(
            dsNvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsNvLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(option, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tableNV, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
        );

        body.addTab("Nhân viên", dsNv);

        add(body, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void tblNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNVMouseClicked

        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = tblNV.rowAtPoint(evt.getPoint());
            if (row != -1) {
                String maNV = (String) tblNV.getValueAt(row, 0);
                Layout.instance.showChiTietNV(maNV);
            }
        }
    }//GEN-LAST:event_tblNVMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        Layout.instance.showLayout("themNS");
    }//GEN-LAST:event_jButton1MouseClicked

    private void btnThoiViecCNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThoiViecCNMouseClicked
        handlerBtnThoiViecCN();
    }//GEN-LAST:event_btnThoiViecCNMouseClicked

    private void btnThoiViecNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThoiViecNVMouseClicked
        handlerBtnThoiViecNV();
    }//GEN-LAST:event_btnThoiViecNVMouseClicked

    private void btnSearchCNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchCNMouseClicked
        handlerBtnSearchCN();
    }//GEN-LAST:event_btnSearchCNMouseClicked

    private void btnSearchNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchNVMouseClicked
        handlerBtnSearchNV();
    }//GEN-LAST:event_btnSearchNVMouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable2MouseClicked
        if (evt.getClickCount() == 2) {
            int row = jTable2.rowAtPoint(evt.getPoint());
            if (row != -1) {
                String maCN = (String) jTable2.getValueAt(row, 0);
                Layout.instance.showChiTietCN(maCN);
            }
        }
    }// GEN-LAST:event_jTable2MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Layout.instance.showLayout("danhGiaNS");
    }// GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBox1ActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        System.out.println("search");
    }// GEN-LAST:event_btnSearchActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Layout.instance.showLayout("danhGiaNS");
    }// GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        Layout.instance.showLayout("themNS");
    }// GEN-LAST:event_jButton6ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField2ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:

    }// GEN-LAST:event_jComboBox2ActionPerformed

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSearch1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnSearch1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane body;
    private javax.swing.JButton btnSearchCN;
    private javax.swing.JButton btnSearchNV;
    private javax.swing.JButton btnThoiViecCN;
    private javax.swing.JButton btnThoiViecNV;
    private javax.swing.JComboBox<String> cboTieuChiCN;
    private javax.swing.JComboBox<String> cboTieuChiNV;
    private javax.swing.JPanel dsCn;
    private javax.swing.JPanel dsNv;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JTable jTable2;
    private javax.swing.JPanel option;
    private javax.swing.JPanel option1;
    private javax.swing.JPanel search;
    private javax.swing.JPanel search1;
    private javax.swing.JScrollPane tableNV;
    private javax.swing.JScrollPane tableNV1;
    private javax.swing.JTable tblNV;
    private javax.swing.JLabel title;
    private javax.swing.JPanel tmp;
    private javax.swing.JTextField txtSearchCN;
    private javax.swing.JTextField txtSearchNV;
    // End of variables declaration//GEN-END:variables

}
