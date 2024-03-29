/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ptud.GUI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import ptud.DAO.DAO_BoPhan;
import ptud.DAO.DAO_CongNhan;
import ptud.DAO.DAO_NhanVien;
import ptud.Entity.BoPhan;
import ptud.Entity.CongNhan;
import ptud.Entity.NhanVien;

/**
 *
 * @author TomTom
 */
public class GD_ThemNS extends javax.swing.JPanel {

    private void updateCboBoPhan(String filter) {
        cboBoPhan.removeAllItems();
        DAO_BoPhan daoBoPhan = DAO_BoPhan.getInstance();
        ArrayList<BoPhan> danhSachBoPhan = daoBoPhan.filter(filter);
        for (BoPhan boPhan : danhSachBoPhan) {
            cboBoPhan.addItem(boPhan.getMaBP() + "-" + boPhan.getTenBP());
        }
    }

    private boolean validateForm() {
        // Kiểm tra tên không được trống
        String ten = txtName.getText().trim();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtName.requestFocusInWindow();
            return false;
        }
        if (!ten.matches("([a-zA-Z\\p{IsLatin}]* ?)*")) {
            JOptionPane.showMessageDialog(null, "Tên không được chứa ký tự đặc biệt và chỉ gồm chữ cái và khoảng trắng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtName.requestFocusInWindow();
            return false;
        }

        Date dateNgaySinh = txtNgaySinh.getDate();
        if (dateNgaySinh == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày sinh", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNgaySinh.requestFocusInWindow();
            return false;
        }

        // Kiểm tra ngày sinh không được null và không trong tương lai
        LocalDate ngaySinh = txtNgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (ngaySinh == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày sinh", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNgaySinh.requestFocusInWindow();
            return false;
        }
        if (ngaySinh.isAfter(LocalDate.now().minusYears(18))) {
            JOptionPane.showMessageDialog(null, "Ngày sinh không hợp lệ. Phải lớn hơn hoặc bằng 18 tuổi.", "Lỗi", JOptionPane.ERROR_MESSAGE);

            txtNgaySinh.requestFocusInWindow();
            return false;
        }

        // Kiểm tra CCCD không được trống
        String cccd = txtCccd.getText().trim();
        if (cccd.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số CCCD", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtCccd.requestFocusInWindow();
            return false;
        }
        if (!cccd.matches("\\d{12}")) {
            JOptionPane.showMessageDialog(null, "CCCD phải có đúng 12 số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtCccd.requestFocusInWindow();
            return false;
        }

        // Kiểm tra số điện thoại không được trống và phải là số
        String dienThoai = txtDienThoai.getText().trim();
        if (dienThoai.isEmpty() || !dienThoai.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtDienThoai.requestFocusInWindow();
            return false;
        }
        if (!dienThoai.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "Số điện thoại phải có đúng 10 số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtDienThoai.requestFocusInWindow();
            return false;
        }

        Date dateNgayVaoLam = txtNgayBatDauLam.getDate();
        if (dateNgayVaoLam == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày vào làm", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNgayBatDauLam.requestFocusInWindow();
            return false;
        }
        // Kiểm tra ngày vào làm không được null và không trong tương lai
        LocalDate ngayVaoLam = txtNgayBatDauLam.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (ngayVaoLam == null || ngayVaoLam.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày vào làm hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNgayBatDauLam.requestFocusInWindow();
            return false;
        }

        // Kiểm tra đường dẫn ảnh không được trống
        String path = txtPath.getText().trim();
        if (path.isEmpty() || path.equals("Chọn file ...")) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ảnh thẻ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtPath.requestFocusInWindow();
            return false;
        }

        if (rdoNhanVien.isSelected()) {

            // Kiểm tra lương cơ bản là số không âm
            String luongCoBan = txtLuongCoBan.getText().trim();
            if (!luongCoBan.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Lương cơ bản phải là số không âm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtLuongCoBan.requestFocusInWindow();
                return false;
            }

            // Kiểm tra thưởng là số không âm
            String thuong = txtThuong.getText().trim();
            if (!thuong.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Thưởng phải là số không âm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtThuong.requestFocusInWindow();
                return false;
            }
        }

        // Nếu đã qua tất cả kiểm tra, trả về true
        return true;
    }

    /**
     * Creates new form GD_ThemNV
     */
    public GD_ThemNS() {
        initComponents();

        typeNS.add(rdoCongNhan);
        typeNS.add(rdoNhanVien);

        typeGioiTinh.add(rdoNu);
        typeGioiTinh.add(rdoNam);

        updateCboBoPhan("HC");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        typeNS = new javax.swing.ButtonGroup();
        typeGioiTinh = new javax.swing.ButtonGroup();
        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        type = new javax.swing.JPanel();
        rdoCongNhan = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        btnConfirm = new javax.swing.JButton();
        body = new javax.swing.JPanel();
        form = new javax.swing.JPanel();
        name = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        boPhan = new javax.swing.JPanel();
        lblBoPhan = new javax.swing.JLabel();
        cboBoPhan = new javax.swing.JComboBox<>();
        ngaySinh = new javax.swing.JPanel();
        lblNgaySinh = new javax.swing.JLabel();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        gioiTinh = new javax.swing.JPanel();
        lblGioiTinh = new javax.swing.JLabel();
        groupGioiTinh = new javax.swing.JPanel();
        rdoNu = new javax.swing.JRadioButton();
        rdoNam = new javax.swing.JRadioButton();
        CCCD = new javax.swing.JPanel();
        lblCccd = new javax.swing.JLabel();
        txtCccd = new javax.swing.JTextField();
        dienThoai = new javax.swing.JPanel();
        lblDienThoai = new javax.swing.JLabel();
        txtDienThoai = new javax.swing.JTextField();
        ngayBatDaulam = new javax.swing.JPanel();
        lblNgayBatDauLam = new javax.swing.JLabel();
        txtNgayBatDauLam = new com.toedter.calendar.JDateChooser();
        hinhAnh = new javax.swing.JPanel();
        lblHinhAnh = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnUpload = new javax.swing.JButton();
        txtPath = new javax.swing.JTextField();
        luongCoBan = new javax.swing.JPanel();
        lblLuongCoBan = new javax.swing.JLabel();
        txtLuongCoBan = new javax.swing.JTextField();
        thuong = new javax.swing.JPanel();
        lblThuong = new javax.swing.JLabel();
        txtThuong = new javax.swing.JTextField();
        footer = new javax.swing.JPanel();

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("THÊM NHÂN SỰ");
        jLabel1.setPreferredSize(new java.awt.Dimension(87, 60));
        header.add(jLabel1, java.awt.BorderLayout.CENTER);

        type.setBackground(new java.awt.Color(198, 222, 192));
        type.setMinimumSize(new java.awt.Dimension(100, 40));
        type.setPreferredSize(new java.awt.Dimension(916, 40));
        type.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                typeMouseClicked(evt);
            }
        });

        rdoCongNhan.setBackground(new java.awt.Color(198, 222, 192));
        rdoCongNhan.setForeground(new java.awt.Color(0, 0, 0));
        rdoCongNhan.setText("Công nhân");
        rdoCongNhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoCongNhanMouseClicked(evt);
            }
        });

        rdoNhanVien.setBackground(new java.awt.Color(198, 222, 192));
        rdoNhanVien.setForeground(new java.awt.Color(0, 0, 0));
        rdoNhanVien.setSelected(true);
        rdoNhanVien.setText("Nhân viên");
        rdoNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoNhanVienMouseClicked(evt);
            }
        });
        rdoNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNhanVienActionPerformed(evt);
            }
        });

        btnConfirm.setBackground(new java.awt.Color(238, 250, 235));
        btnConfirm.setForeground(new java.awt.Color(0, 0, 0));
        btnConfirm.setText("Xác nhận");
        btnConfirm.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnConfirm.setPreferredSize(new java.awt.Dimension(150, 30));
        btnConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfirmMouseClicked(evt);
            }
        });
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout typeLayout = new javax.swing.GroupLayout(type);
        type.setLayout(typeLayout);
        typeLayout.setHorizontalGroup(
            typeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(typeLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(rdoCongNhan)
                .addGap(15, 15, 15)
                .addComponent(rdoNhanVien)
                .addGap(30, 30, 30)
                .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(535, Short.MAX_VALUE))
        );
        typeLayout.setVerticalGroup(
            typeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(typeLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(typeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoCongNhan)
                    .addComponent(rdoNhanVien)
                    .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(5, Short.MAX_VALUE))
        );

        header.add(type, java.awt.BorderLayout.SOUTH);

        body.setLayout(new java.awt.BorderLayout());

        form.setBackground(new java.awt.Color(255, 255, 255));
        form.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 100, 200, 100));
        form.setLayout(new java.awt.GridLayout(6, 2, 20, 20));

        name.setBackground(new java.awt.Color(255, 255, 255));
        name.setLayout(new java.awt.BorderLayout());

        lblName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblName.setForeground(new java.awt.Color(0, 0, 0));
        lblName.setText("Tên nhân viên:");
        lblName.setPreferredSize(new java.awt.Dimension(180, 100));
        lblName.setRequestFocusEnabled(false);
        name.add(lblName, java.awt.BorderLayout.WEST);

        txtName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        name.add(txtName, java.awt.BorderLayout.CENTER);

        form.add(name);

        boPhan.setBackground(new java.awt.Color(255, 255, 255));
        boPhan.setLayout(new java.awt.BorderLayout());

        lblBoPhan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBoPhan.setForeground(new java.awt.Color(0, 0, 0));
        lblBoPhan.setText("Bộ phận:");
        lblBoPhan.setPreferredSize(new java.awt.Dimension(180, 100));
        lblBoPhan.setRequestFocusEnabled(false);
        boPhan.add(lblBoPhan, java.awt.BorderLayout.WEST);

        cboBoPhan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        boPhan.add(cboBoPhan, java.awt.BorderLayout.CENTER);

        form.add(boPhan);

        ngaySinh.setBackground(new java.awt.Color(255, 255, 255));
        ngaySinh.setLayout(new java.awt.BorderLayout());

        lblNgaySinh.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNgaySinh.setForeground(new java.awt.Color(0, 0, 0));
        lblNgaySinh.setText("Ngày sinh: ");
        lblNgaySinh.setPreferredSize(new java.awt.Dimension(180, 100));
        lblNgaySinh.setRequestFocusEnabled(false);
        ngaySinh.add(lblNgaySinh, java.awt.BorderLayout.WEST);
        ngaySinh.add(txtNgaySinh, java.awt.BorderLayout.CENTER);

        form.add(ngaySinh);

        gioiTinh.setBackground(new java.awt.Color(255, 255, 255));
        gioiTinh.setLayout(new java.awt.BorderLayout());

        lblGioiTinh.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblGioiTinh.setForeground(new java.awt.Color(0, 0, 0));
        lblGioiTinh.setText("Giới tính");
        lblGioiTinh.setPreferredSize(new java.awt.Dimension(180, 100));
        lblGioiTinh.setRequestFocusEnabled(false);
        gioiTinh.add(lblGioiTinh, java.awt.BorderLayout.WEST);

        groupGioiTinh.setBackground(new java.awt.Color(255, 255, 255));

        rdoNu.setBackground(new java.awt.Color(255, 255, 255));
        rdoNu.setForeground(new java.awt.Color(0, 0, 0));
        rdoNu.setSelected(true);
        rdoNu.setText("Nữ");
        rdoNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNuActionPerformed(evt);
            }
        });

        rdoNam.setBackground(new java.awt.Color(255, 255, 255));
        rdoNam.setForeground(new java.awt.Color(0, 0, 0));
        rdoNam.setText("Nam");

        javax.swing.GroupLayout groupGioiTinhLayout = new javax.swing.GroupLayout(groupGioiTinh);
        groupGioiTinh.setLayout(groupGioiTinhLayout);
        groupGioiTinhLayout.setHorizontalGroup(
            groupGioiTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groupGioiTinhLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdoNu)
                .addGap(40, 40, 40)
                .addComponent(rdoNam)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        groupGioiTinhLayout.setVerticalGroup(
            groupGioiTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groupGioiTinhLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(groupGioiTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNu)
                    .addComponent(rdoNam))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        gioiTinh.add(groupGioiTinh, java.awt.BorderLayout.CENTER);

        form.add(gioiTinh);

        CCCD.setBackground(new java.awt.Color(255, 255, 255));
        CCCD.setLayout(new java.awt.BorderLayout());

        lblCccd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblCccd.setForeground(new java.awt.Color(0, 0, 0));
        lblCccd.setText("CCCD:");
        lblCccd.setPreferredSize(new java.awt.Dimension(180, 100));
        lblCccd.setRequestFocusEnabled(false);
        CCCD.add(lblCccd, java.awt.BorderLayout.WEST);

        txtCccd.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        CCCD.add(txtCccd, java.awt.BorderLayout.CENTER);

        form.add(CCCD);

        dienThoai.setBackground(new java.awt.Color(255, 255, 255));
        dienThoai.setLayout(new java.awt.BorderLayout());

        lblDienThoai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblDienThoai.setForeground(new java.awt.Color(0, 0, 0));
        lblDienThoai.setText("Số điện thoại:");
        lblDienThoai.setPreferredSize(new java.awt.Dimension(180, 100));
        lblDienThoai.setRequestFocusEnabled(false);
        dienThoai.add(lblDienThoai, java.awt.BorderLayout.WEST);

        txtDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        dienThoai.add(txtDienThoai, java.awt.BorderLayout.CENTER);

        form.add(dienThoai);

        ngayBatDaulam.setBackground(new java.awt.Color(255, 255, 255));
        ngayBatDaulam.setLayout(new java.awt.BorderLayout());

        lblNgayBatDauLam.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNgayBatDauLam.setForeground(new java.awt.Color(0, 0, 0));
        lblNgayBatDauLam.setText("Ngày vào làm:");
        lblNgayBatDauLam.setPreferredSize(new java.awt.Dimension(180, 100));
        lblNgayBatDauLam.setRequestFocusEnabled(false);
        ngayBatDaulam.add(lblNgayBatDauLam, java.awt.BorderLayout.WEST);
        ngayBatDaulam.add(txtNgayBatDauLam, java.awt.BorderLayout.CENTER);

        form.add(ngayBatDaulam);

        hinhAnh.setBackground(new java.awt.Color(255, 255, 255));
        hinhAnh.setLayout(new java.awt.BorderLayout());

        lblHinhAnh.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHinhAnh.setForeground(new java.awt.Color(0, 0, 0));
        lblHinhAnh.setText("Ảnh thẻ:");
        lblHinhAnh.setPreferredSize(new java.awt.Dimension(180, 100));
        lblHinhAnh.setRequestFocusEnabled(false);
        hinhAnh.add(lblHinhAnh, java.awt.BorderLayout.WEST);

        jPanel1.setLayout(new java.awt.BorderLayout());

        btnUpload.setText("Chọn ảnh thẻ");
        btnUpload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUploadMouseClicked(evt);
            }
        });
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpload, java.awt.BorderLayout.WEST);

        txtPath.setEditable(false);
        txtPath.setBorder(null);
        txtPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPathActionPerformed(evt);
            }
        });
        jPanel1.add(txtPath, java.awt.BorderLayout.CENTER);

        hinhAnh.add(jPanel1, java.awt.BorderLayout.CENTER);

        form.add(hinhAnh);

        luongCoBan.setBackground(new java.awt.Color(255, 255, 255));
        luongCoBan.setLayout(new java.awt.BorderLayout());

        lblLuongCoBan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblLuongCoBan.setForeground(new java.awt.Color(0, 0, 0));
        lblLuongCoBan.setText("Lương cơ bản:");
        lblLuongCoBan.setPreferredSize(new java.awt.Dimension(180, 100));
        lblLuongCoBan.setRequestFocusEnabled(false);
        luongCoBan.add(lblLuongCoBan, java.awt.BorderLayout.WEST);

        txtLuongCoBan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        luongCoBan.add(txtLuongCoBan, java.awt.BorderLayout.CENTER);

        form.add(luongCoBan);

        thuong.setBackground(new java.awt.Color(255, 255, 255));
        thuong.setLayout(new java.awt.BorderLayout());

        lblThuong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblThuong.setForeground(new java.awt.Color(0, 0, 0));
        lblThuong.setText("Thưởng:");
        lblThuong.setPreferredSize(new java.awt.Dimension(180, 100));
        lblThuong.setRequestFocusEnabled(false);
        thuong.add(lblThuong, java.awt.BorderLayout.WEST);

        txtThuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        thuong.add(txtThuong, java.awt.BorderLayout.CENTER);

        form.add(thuong);

        body.add(form, java.awt.BorderLayout.CENTER);

        footer.setBackground(new java.awt.Color(198, 222, 192));
        footer.setPreferredSize(new java.awt.Dimension(100, 100));
        footer.setRequestFocusEnabled(false);

        javax.swing.GroupLayout footerLayout = new javax.swing.GroupLayout(footer);
        footer.setLayout(footerLayout);
        footerLayout.setHorizontalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        footerLayout.setVerticalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        body.add(footer, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 1026, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNhanVienActionPerformed

    }//GEN-LAST:event_rdoNhanVienActionPerformed

    private void rdoNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNuActionPerformed

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUploadActionPerformed

    private void rdoCongNhanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoCongNhanMouseClicked
        lblName.setText("Tên công nhân:");
        thuong.setVisible(false);
        luongCoBan.setVisible(false);
        revalidate();
        repaint();

        updateCboBoPhan("SX");
    }//GEN-LAST:event_rdoCongNhanMouseClicked

    private void typeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_typeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_typeMouseClicked

    private void rdoNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoNhanVienMouseClicked
        lblName.setText("Tên nhân viên:");
        thuong.setVisible(true);
        luongCoBan.setVisible(true);
        revalidate();
        repaint();

        updateCboBoPhan("HC");

    }//GEN-LAST:event_rdoNhanVienMouseClicked

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void btnConfirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmMouseClicked
        if (validateForm()) {
            try {
                // Lấy thông tin từ giao diện
                String ten = txtName.getText();
                String[] parts = cboBoPhan.getSelectedItem().toString().split("-");
                BoPhan boPhan = new BoPhan(parts[0], parts[1]);
                LocalDate ngaySinh = txtNgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                boolean gioiTinh = rdoNu.isSelected();
                String cccd = txtCccd.getText();
                String dienThoai = txtDienThoai.getText();
                LocalDate ngayBatDauLam = txtNgayBatDauLam.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Path path = Paths.get(txtPath.getText());
                byte[] avatar = Files.readAllBytes(path);

                if (rdoCongNhan.isSelected()) {
                    CongNhan congNhan = new CongNhan(boPhan, ten, rdoNam.isSelected(), ngaySinh, ngayBatDauLam, cccd, dienThoai, true, avatar, true);
                    DAO_CongNhan daoCongNhan = DAO_CongNhan.getInstance();
                    daoCongNhan.insert(congNhan);
                    JOptionPane.showMessageDialog(null, "Thêm công nhân thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    Layout.instance.showChiTietCN(congNhan.getMaCN());
                } else {
                    double luongCoBan = Double.parseDouble(txtLuongCoBan.getText());
                    double thuong = Double.parseDouble(txtThuong.getText());
                    NhanVien nhanVien = new NhanVien(boPhan, ten, rdoNam.isSelected(), ngaySinh, ngayBatDauLam, cccd, dienThoai, true, avatar, luongCoBan, thuong);
                    DAO_NhanVien daoNhanVien = DAO_NhanVien.getInstance();
                    daoNhanVien.insert(nhanVien);
                    JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    Layout.instance.showChiTietNV(nhanVien.getMaNV());
                }
            } catch (IOException ex) {
                Logger.getLogger(GD_ThemNS.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnConfirmMouseClicked

    private void btnUploadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUploadMouseClicked
        // Mở hộp thoại chọn file
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn của file đã chọn
            java.io.File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            // Hiển thị đường dẫn file trên giao diện
            txtPath.setText(filePath);
        }
    }//GEN-LAST:event_btnUploadMouseClicked

    private void txtPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPathActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CCCD;
    private javax.swing.JPanel boPhan;
    private javax.swing.JPanel body;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnUpload;
    private javax.swing.JComboBox<String> cboBoPhan;
    private javax.swing.JPanel dienThoai;
    private javax.swing.JPanel footer;
    private javax.swing.JPanel form;
    private javax.swing.JPanel gioiTinh;
    private javax.swing.JPanel groupGioiTinh;
    private javax.swing.JPanel header;
    private javax.swing.JPanel hinhAnh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBoPhan;
    private javax.swing.JLabel lblCccd;
    private javax.swing.JLabel lblDienThoai;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JLabel lblLuongCoBan;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNgayBatDauLam;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblThuong;
    private javax.swing.JPanel luongCoBan;
    private javax.swing.JPanel name;
    private javax.swing.JPanel ngayBatDaulam;
    private javax.swing.JPanel ngaySinh;
    private javax.swing.JRadioButton rdoCongNhan;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JPanel thuong;
    private javax.swing.JTextField txtCccd;
    private javax.swing.JTextField txtDienThoai;
    private javax.swing.JTextField txtLuongCoBan;
    private javax.swing.JTextField txtName;
    private com.toedter.calendar.JDateChooser txtNgayBatDauLam;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtPath;
    private javax.swing.JTextField txtThuong;
    private javax.swing.JPanel type;
    private javax.swing.ButtonGroup typeGioiTinh;
    private javax.swing.ButtonGroup typeNS;
    // End of variables declaration//GEN-END:variables

}
