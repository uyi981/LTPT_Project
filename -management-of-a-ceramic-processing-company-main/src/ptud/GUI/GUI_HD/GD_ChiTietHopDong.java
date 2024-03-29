/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ptud.GUI.GUI_HD;

import java.awt.CardLayout;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ptud.DAO.DAOInterface;
import ptud.DAO.DAO_CongDoan;
import ptud.DAO.DAO_HopDong;
import ptud.DAO.DAO_KhachHang;
import ptud.Entity.HopDong;
import ptud.Entity.KhachHang;
import ptud.Entity.SanPham;
import ptud.GUI.GD_QLHD;
import java.time.temporal.ChronoField;
import java.util.HashSet;
import java.util.Set;
import ptud.DAO.DAO_SanPham;

/**
 *
 * @author VoPhuocHau
 */
public class GD_ChiTietHopDong extends javax.swing.JPanel {
    //
    private HopDong hopDong;
    private GD_QLHD gD_QLHD;
    private DefaultTableModel sanPhamModel, tienDoModel;
    // khoi tao cac DAO 
    private DAO_HopDong daohd = new DAO_HopDong(); 
    private DAO_KhachHang daokh = new DAO_KhachHang();
    private DAO_SanPham daosp = new DAO_SanPham();
    CardLayout cardLayout;

    /**
     * Creates new form GD_ChiTietHopDong
     */
    public GD_ChiTietHopDong() 
    {
        initComponents();
        // chinh sua cell cua table + tao cac model
        tienDoSanPhamTable.getColumnModel().getColumn(1).setCellRenderer(new TienDoTableCellRender());
        sanPhamModel = (DefaultTableModel) sanPhamTable.getModel();
        tienDoModel = (DefaultTableModel) tienDoSanPhamTable.getModel();
        jDateChooser1.setEnabled(false);
        cardLayout = (CardLayout)footer_2.getLayout();
    }
    // phuong thuc tao san pham va insert san pham vao table ( chua them vao database)
    void createSanPham() {
        try {
            double donGia;
            int soLuong = Integer.parseInt(textFieldSoLuong.getText());
            donGia = Double.parseDouble(textFieldDonGia.getText());
            boolean isCreate = true;
            if (!checkRegex("^[A-Z]{1}", textFieldTenSP.getText())) {
                errTenSP.setText("Chữ cái đầu phải viết hoa");
                isCreate = false;
            }
            if (donGia <= 0) {
                errSoLuong.setText("Gía trị sản phẩm phải là số không âm!");
                isCreate = false;
            }
            if (soLuong <= 0) {
                errDonGia.setText("số lượng sản phẩm phải là số không âm!");
                isCreate = false;
            }
            if (isCreate == true) {
                try {

                    changeEnityToObject(); // them san pham vao table

                } catch (Exception e) {
                    JOptionPane.showConfirmDialog(this, "Lỗi tạo hợp đồng!");
                }
            }
        } catch (Exception e) {
            // bat ngoai le
            errTenSP.setText("Gía trị sản phẩm phải là số không âm!");
            errSoLuong.setText("số lượng sản phẩm phải là số không âm!");
        }
    }
    // phuong truc tra ve true khi phu hop regex, false khi khong phu hop regex
    public boolean checkRegex(String pattern, String input) {
        Pattern patternCore = Pattern.compile(pattern);
        Matcher matcher = patternCore.matcher(input);
        boolean matchFound = matcher.find();
        return matchFound;
    }
    // dua enity vao table
    void changeEnityToObject() {
        String tenSP = textFieldTenSP.getText();
        double donGia;
        int soLuong = Integer.parseInt(textFieldSoLuong.getText());
        donGia = Double.parseDouble(textFieldDonGia.getText());
        Object[] rowData = new Object[4];
        rowData[0] = "Sản phẩm chưa có mã";
        rowData[1] = tenSP;
        rowData[2] = soLuong;
        rowData[3] = donGia;
        sanPhamModel.addRow(rowData);
    }
    // chuyen du lieu table thanh enity
    void changeObjectToEnity(Object[] rowData, int stt) {
        double donGia;
        int soLuong = Integer.parseInt(rowData[1].toString());
        donGia = Double.parseDouble(rowData[2].toString());
        String tenSP = rowData[0].toString();
        SanPham sanPham = new SanPham(stt, tenSP, soLuong, donGia, hopDong.getMaHD());
        if (!hopDong.getSanPhams().contains(sanPham)) {
            daosp.insert(sanPham);
        }
    }
    // tao hop dong va them hopdong vao database
    void createHopDong() {
        try {
            boolean isCreate = true;
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat vn = NumberFormat.getInstance(localeVN);
            hopDong.setDonGia(triGiamaHDTextField.getText());
            if (!checkRegex("^[A-Z]{1}", tenHDmaHDTextField.getText())) {
                jLabel10.setText("Chữ cái đầu phải viết hoa");
                isCreate = false;
            }
            if (!checkRegex("^[0-9]{6}$", maKHmaHDTextField.getText())) {
                jLabel13.setText("Mã khách hàng phải thuộc định dạng 6 ký tự số");
                isCreate = false;
            }
            if (hopDong.getDonGia() <= 0) {
                jLabel11.setText("Gía trị tài khoản phải là số không âm!");
                isCreate = false;
            }

            if (isCreate == true) {
                try {
                    if (jDateChooser1.getCalendar() != null) {
                        LocalDate ngayKT = LocalDate.ofInstant(jDateChooser1.getCalendar().toInstant(), ZoneId.systemDefault());
                        hopDong.setNgayKetThucDuKien(ngayKT);
                    }
                    String tenHD = tenHDmaHDTextField.getText();
                    String maKH = maKHmaHDTextField.getText();
                    hopDong.setTenHD(tenHD);
                    hopDong.setMaKH(maKH);
                    daohd.update(hopDong);

                } catch (Exception e) {
                    JOptionPane.showConfirmDialog(this, "Lỗi tạo hợp đồng!");
                }
            }
        } catch (Exception e) {
            jLabel11.setText("Gía trị tài khoản phải là số không âm!");
        }
         
    }
   // dua sanpham vao table
    void changeEnityToObject(SanPham sanPham) {
        Object[] rowData = new Object[4];
        rowData[0] = sanPham.getMaSanPham();
        rowData[1] = sanPham.getTenSanPham();
        rowData[2] = sanPham.getSoLuong();
        rowData[3] = sanPham.getDonGia();
        Object[] tienDoData = new Object[3];
        tienDoData[0] = sanPham.getTenSanPham();
        sanPham.setTienDo();
        tienDoData[1] = (int)((100.0*sanPham.getTienDo())/sanPham.getSoLuong()); 
        tienDoData[2] = ""+sanPham.getTienDo()+"/"+sanPham.getSoLuong();
        System.out.println("tienDo"+tienDoData[1].toString());
        sanPhamModel.addRow(rowData);
        tienDoModel.addRow(tienDoData);

    }
    // them sanpham vao database
    void insertSanPhamToDatabase() {

        for (int i = 0; i < sanPhamModel.getRowCount(); i++) {
            Object[] objects = new Object[3];
            if (sanPhamModel.getValueAt(i, 0).toString().equals("Sản phẩm chưa có mã")) {
                objects[0] = sanPhamModel.getValueAt(i, 1);
                objects[1] = sanPhamModel.getValueAt(i, 2);
                objects[2] = sanPhamModel.getValueAt(i, 3);
                changeObjectToEnity(objects, i + 1);
                System.out.println(i);
            }
        }
    }
    // moi khi event click vao danh sach hopDong thi phuong thuc se duoc goi
    public void receiveHopDong(HopDong hopDong1, GD_QLHD gD_QLHD) {
       // nhan du lieu hopdong tu hopdongtable o class GD_QLHD
        hopDong = hopDong1;
        updateData();
        this.gD_QLHD = gD_QLHD; // tham chieu gd_glhd
        updateTable(); // lam moi du lieu cac table
        // xu ly 4 truong hop hop dong khac nhau
        if (hopDong.getTrangThai().equals("chờ xác nhận")) {
            xacNhanButton.setEnabled(true);
            xacNhanButton.setVisible(true);
            capNhatButton.setEnabled(true);
            capNhatButton.setVisible(true);
            cardLayout.show(footer_2,"tienDo");
            buttonThemSanPham.setVisible(false);
            buttonXoaSanPham.setVisible(false);
        } else if (hopDong.getTrangThai().equals("đang thực hiện")) {
            xacNhanButton.setEnabled(false);
            xacNhanButton.setVisible(false);
            capNhatButton.setEnabled(false);
            capNhatButton.setVisible(false);
            cardLayout.show(footer_2,"tienDo");
            buttonThemSanPham.setVisible(false);
            buttonXoaSanPham.setVisible(false);
        } else if (hopDong.getTrangThai().equals("hoàn thành")) {
            xacNhanButton.setEnabled(false);
            xacNhanButton.setVisible(false);
            capNhatButton.setEnabled(false);
            capNhatButton.setVisible(false);
            cardLayout.show(footer_2,"tienDo");
            buttonThemSanPham.setVisible(false);
            buttonXoaSanPham.setVisible(false);
        } else if (hopDong.getTrangThai().equals("Hủy")) {
            xacNhanButton.setEnabled(false);
            xacNhanButton.setVisible(false);
            capNhatButton.setEnabled(false);
            capNhatButton.setVisible(false);
            cardLayout.show(footer_2,"tienDo");
            buttonThemSanPham.setVisible(false);
            buttonXoaSanPham.setVisible(false);
        }
        tenHDmaHDTextField.setEditable(false);
        maKHmaHDTextField.setEditable(false);
        triGiamaHDTextField.setEditable(false);
        ngayKTmaHDTextField.setEditable(false);
        jDateChooser1.setEnabled(false);
        // tinh toan so ngay con lai
        double ngayChenhLechTong = timNgayChenhlech(hopDong.getNgayBatDau(), hopDong.getNgayKetThucDuKien());
        double ngayConLai = timNgayChenhlech(hopDong.getNgayBatDau(), LocalDate.now());

        double percent = 100 * ngayConLai / ngayChenhLechTong;
        System.out.println(ngayChenhLechTong + "ngayConLai" + ngayConLai);
        progessBarThoiGian.setValue((int) percent);
        // ket thuc tinh toan ngay
        // tinh toan tien do tong
        int sum = 0;
        int sumMax = 0;
        for (SanPham sanPham : hopDong.getSanPhams()) {
            sanPham.setTienDo();
            sum += sanPham.getTienDo();
            sumMax += sanPham.getSoLuong();
        }
        jLabel17.setText(""+sum+"/"+sumMax);
        jLabel18.setText(""+(int)ngayConLai+"/"+(int)ngayChenhLechTong);
        progessBarTienDo.setMaximum(sumMax);
        progessBarTienDo.setValue(sum);
        // ket thuc tinh toan tien do tong

    }
    // phuong thuc tim so ngay chenh lech giua 2 ngay bat ki
    public int timNgayChenhlech(LocalDate dateStart, LocalDate dateEnd) {
        if (dateStart.getYear() == dateEnd.getYear()) {
            return dateEnd.getDayOfYear() - dateStart.getDayOfYear();
        } else {
            int yearChenhLech = dateEnd.getYear() - dateStart.getYear();
            int ngayChenhLech = 0;
            for (int i = 1; i < yearChenhLech; i++) {
                ngayChenhLech += LocalDate.of(i + dateStart.getYear(), 12, 31).getDayOfYear();
                ngayChenhLech++;
            }
            ngayChenhLech += (LocalDate.of(dateStart.getYear(), 12, 31).getDayOfYear() - dateStart.getDayOfYear()) + 1;
            ngayChenhLech += (dateEnd.getDayOfYear() - LocalDate.of(dateEnd.getYear(), 1, 1).getDayOfYear());
            return ngayChenhLech;
        }

    }
    // them thong tin hopDong vao cac textField
    public void updateData() {
        maHDTextField.setText(hopDong.getMaHD());
        tenHDmaHDTextField.setText(hopDong.getTenHD());
        triGiamaHDTextField.setText(hopDong.getDonGiaString());
        trangThaimaHDTextField.setText(hopDong.getTrangThai());
        ngayBDmaHDTextField.setText(hopDong.getNgayBatDauString());
        ngayKTmaHDTextField.setText(hopDong.getNgayKetThucString());
        maKHmaHDTextField.setText(hopDong.getMaKH());
    }
    // chuyen trang thai hopDong
    public void thayDoiTrangThaiHopDong(String trangThai) {
        hopDong.setTrangThai(trangThai);
        if (!daohd.update(hopDong)) {
            JOptionPane.showConfirmDialog(this, "Xác nhận thất bại,vui lòng thử lại sau ít phút!");
        }
    }
// lam moi table
    void updateTable() {
        deleleTable();
        fillHopDongTable();
    }
// xoa table
    int deleleTable() {
        int rowIndex = sanPhamModel.getRowCount();
        for (int i = 0; i < rowIndex; i++) {
            if (sanPhamModel.getRowCount() == 0) {
                return 1;
            } else {
                sanPhamModel.removeRow(0);
            }
        }
        for (int i = 0; i < rowIndex; i++) {
            if (tienDoModel.getRowCount() == 0) {
                return 1;
            } else {
                tienDoModel.removeRow(0);
            }
        }
        return 0;

    }

    void fillHopDongTable() {

        hopDong.updateListSanPham();
        for (SanPham sanPham : hopDong.getSanPhams()) {
            changeEnityToObject(sanPham);
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

        Body = new javax.swing.JPanel();
        header_body = new javax.swing.JPanel();
        header_header_body = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        infoHolder = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        maHDTextField = new javax.swing.JTextField();
        tenHDmaHDTextField = new javax.swing.JTextField();
        ngayBDmaHDTextField = new javax.swing.JTextField();
        ngayKTmaHDTextField = new javax.swing.JTextField();
        triGiamaHDTextField = new javax.swing.JTextField();
        maKHmaHDTextField = new javax.swing.JTextField();
        trangThaimaHDTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        capNhatButton = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        scrollSanPham = new javax.swing.JScrollPane();
        sanPhamTable = new javax.swing.JTable();
        buttonBar = new javax.swing.JPanel();
        huyButton = new javax.swing.JButton();
        xacNhanButton = new javax.swing.JButton();
        buttonThemSanPham = new javax.swing.JButton();
        buttonXoaSanPham = new javax.swing.JButton();
        tienDoSanPham = new javax.swing.JPanel();
        footer_2 = new javax.swing.JPanel();
        footer_1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        progessBarTienDo = new javax.swing.JProgressBar();
        progessBarThoiGian = new javax.swing.JProgressBar();
        tienDoSanPhamScroll = new javax.swing.JScrollPane();
        tienDoSanPhamTable = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        panelThemSanPham = new javax.swing.JPanel();
        textFieldTenSP = new javax.swing.JTextField();
        textFieldSoLuong = new javax.swing.JTextField();
        textFieldDonGia = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        buttonXacNhanThemSanPham = new javax.swing.JButton();
        buttonHuyThemSanPham = new javax.swing.JButton();
        errTenSP = new javax.swing.JLabel();
        errSoLuong = new javax.swing.JLabel();
        errDonGia = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        Body.setBackground(new java.awt.Color(255, 255, 255));

        header_body.setBackground(new java.awt.Color(255, 255, 255));

        header_header_body.setBackground(new java.awt.Color(225, 240, 221));
        header_header_body.setPreferredSize(new java.awt.Dimension(434, 50));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("THÔNG TIN HỢP ĐỒNG");
        jLabel12.setMaximumSize(new java.awt.Dimension(192, 50));
        jLabel12.setMinimumSize(new java.awt.Dimension(192, 50));
        jLabel12.setPreferredSize(new java.awt.Dimension(192, 50));

        javax.swing.GroupLayout header_header_bodyLayout = new javax.swing.GroupLayout(header_header_body);
        header_header_body.setLayout(header_header_bodyLayout);
        header_header_bodyLayout.setHorizontalGroup(
            header_header_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(header_header_bodyLayout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        header_header_bodyLayout.setVerticalGroup(
            header_header_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(header_header_bodyLayout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        infoHolder.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Mã hợp đồng");
        jLabel1.setPreferredSize(null);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tên hợp đồng");
        jLabel2.setPreferredSize(null);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Ngày bắt đầu");
        jLabel3.setPreferredSize(null);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Ngày kết thúc");
        jLabel4.setPreferredSize(null);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Trị giá hợp đồng");
        jLabel5.setPreferredSize(null);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Mã khách hàng");
        jLabel6.setPreferredSize(null);

        maHDTextField.setEditable(false);
        maHDTextField.setBackground(new java.awt.Color(255, 255, 255));
        maHDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maHDTextFieldActionPerformed(evt);
            }
        });

        tenHDmaHDTextField.setEditable(false);
        tenHDmaHDTextField.setBackground(new java.awt.Color(255, 255, 255));
        tenHDmaHDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenHDmaHDTextFieldActionPerformed(evt);
            }
        });

        ngayBDmaHDTextField.setEditable(false);
        ngayBDmaHDTextField.setBackground(new java.awt.Color(255, 255, 255));
        ngayBDmaHDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ngayBDmaHDTextFieldActionPerformed(evt);
            }
        });

        ngayKTmaHDTextField.setEditable(false);
        ngayKTmaHDTextField.setBackground(new java.awt.Color(255, 255, 255));
        ngayKTmaHDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ngayKTmaHDTextFieldActionPerformed(evt);
            }
        });

        triGiamaHDTextField.setEditable(false);
        triGiamaHDTextField.setBackground(new java.awt.Color(255, 255, 255));
        triGiamaHDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triGiamaHDTextFieldActionPerformed(evt);
            }
        });

        maKHmaHDTextField.setEditable(false);
        maKHmaHDTextField.setBackground(new java.awt.Color(255, 255, 255));
        maKHmaHDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maKHmaHDTextFieldActionPerformed(evt);
            }
        });

        trangThaimaHDTextField.setEditable(false);
        trangThaimaHDTextField.setBackground(new java.awt.Color(255, 255, 255));
        trangThaimaHDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trangThaimaHDTextFieldActionPerformed(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Trạng Thái");
        jLabel9.setPreferredSize(null);

        capNhatButton.setBackground(new java.awt.Color(198, 222, 192));
        capNhatButton.setText("Cập nhật");
        capNhatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capNhatButtonActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("x");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("x");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText("x");

        javax.swing.GroupLayout infoHolderLayout = new javax.swing.GroupLayout(infoHolder);
        infoHolder.setLayout(infoHolderLayout);
        infoHolderLayout.setHorizontalGroup(
            infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoHolderLayout.createSequentialGroup()
                .addComponent(capNhatButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(infoHolderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoHolderLayout.createSequentialGroup()
                        .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(maKHmaHDTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(trangThaimaHDTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoHolderLayout.createSequentialGroup()
                        .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(infoHolderLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tenHDmaHDTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                .addComponent(maHDTextField, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGroup(infoHolderLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoHolderLayout.createSequentialGroup()
                        .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(infoHolderLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(infoHolderLayout.createSequentialGroup()
                                .addComponent(triGiamaHDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(infoHolderLayout.createSequentialGroup()
                                .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ngayBDmaHDTextField)
                                    .addGroup(infoHolderLayout.createSequentialGroup()
                                        .addComponent(ngayKTmaHDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(46, 46, 46))))))
        );
        infoHolderLayout.setVerticalGroup(
            infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoHolderLayout.createSequentialGroup()
                .addComponent(capNhatButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(maHDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(infoHolderLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tenHDmaHDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ngayBDmaHDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ngayKTmaHDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(triGiamaHDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maKHmaHDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trangThaimaHDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.getAccessibleContext().setAccessibleName("");
        jLabel2.getAccessibleContext().setAccessibleName("");
        jLabel3.getAccessibleContext().setAccessibleName("");
        jLabel4.getAccessibleContext().setAccessibleName("");
        jLabel5.getAccessibleContext().setAccessibleName("");
        jLabel6.getAccessibleContext().setAccessibleName("");
        jLabel9.getAccessibleContext().setAccessibleName("");

        sanPhamTable.setBackground(new java.awt.Color(225, 240, 221));
        sanPhamTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "số lượng", "Đơn giá"
            }
        ));
        scrollSanPham.setViewportView(sanPhamTable);

        javax.swing.GroupLayout header_bodyLayout = new javax.swing.GroupLayout(header_body);
        header_body.setLayout(header_bodyLayout);
        header_bodyLayout.setHorizontalGroup(
            header_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header_header_body, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
            .addGroup(header_bodyLayout.createSequentialGroup()
                .addComponent(infoHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 301, Short.MAX_VALUE))
            .addGroup(header_bodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        header_bodyLayout.setVerticalGroup(
            header_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(header_bodyLayout.createSequentialGroup()
                .addComponent(header_header_body, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(infoHolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonBar.setBackground(new java.awt.Color(255, 255, 255));

        huyButton.setBackground(new java.awt.Color(198, 222, 192));
        huyButton.setText("Hủy hợp đồng");
        huyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                huyButtonActionPerformed(evt);
            }
        });

        xacNhanButton.setBackground(new java.awt.Color(198, 222, 192));
        xacNhanButton.setText("Xác nhận hợp đồng");
        xacNhanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xacNhanButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonBarLayout = new javax.swing.GroupLayout(buttonBar);
        buttonBar.setLayout(buttonBarLayout);
        buttonBarLayout.setHorizontalGroup(
            buttonBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xacNhanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(huyButton)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        buttonBarLayout.setVerticalGroup(
            buttonBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xacNhanButton)
                    .addComponent(huyButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonThemSanPham.setBackground(new java.awt.Color(102, 255, 255));
        buttonThemSanPham.setText("Thêm sản phẩm");
        buttonThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonThemSanPhamActionPerformed(evt);
            }
        });

        buttonXoaSanPham.setBackground(new java.awt.Color(102, 255, 255));
        buttonXoaSanPham.setText("Xóa sản phẩm");
        buttonXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonXoaSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BodyLayout = new javax.swing.GroupLayout(Body);
        Body.setLayout(BodyLayout);
        BodyLayout.setHorizontalGroup(
            BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyLayout.createSequentialGroup()
                .addGroup(BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(header_body, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(BodyLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(buttonThemSanPham)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonXoaSanPham))
                    .addComponent(buttonBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );
        BodyLayout.setVerticalGroup(
            BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyLayout.createSequentialGroup()
                .addComponent(header_body, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonThemSanPham)
                    .addComponent(buttonXoaSanPham))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tienDoSanPham.setBackground(new java.awt.Color(255, 255, 255));

        footer_2.setBackground(new java.awt.Color(255, 255, 255));
        footer_2.setLayout(new java.awt.CardLayout());

        footer_1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("Tiến độ chính:");

        jLabel8.setText("Thời gian:");

        progessBarThoiGian.setForeground(new java.awt.Color(204, 0, 51));
        progessBarThoiGian.setToolTipText("");

        tienDoSanPhamTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Sản Phẩm", "Tiến độ", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tienDoSanPhamTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tienDoSanPhamScroll.setViewportView(tienDoSanPhamTable);

        jLabel17.setText("0/0");

        jLabel18.setText("0/0");

        javax.swing.GroupLayout footer_1Layout = new javax.swing.GroupLayout(footer_1);
        footer_1.setLayout(footer_1Layout);
        footer_1Layout.setHorizontalGroup(
            footer_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footer_1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(footer_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(35, 35, 35)
                .addGroup(footer_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(progessBarTienDo, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .addComponent(progessBarThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(footer_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addGap(51, 51, 51))
            .addGroup(footer_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tienDoSanPhamScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        footer_1Layout.setVerticalGroup(
            footer_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footer_1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(footer_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(footer_1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8))
                    .addGroup(footer_1Layout.createSequentialGroup()
                        .addGroup(footer_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(progessBarTienDo, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(footer_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(progessBarThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tienDoSanPhamScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(164, Short.MAX_VALUE))
        );

        footer_2.add(footer_1, "tienDo");

        panelThemSanPham.setBackground(new java.awt.Color(255, 255, 255));

        textFieldTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldTenSPActionPerformed(evt);
            }
        });

        textFieldSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSoLuongActionPerformed(evt);
            }
        });

        jLabel14.setText("Tên sản phẩm");

        jLabel15.setText("Số lượng");

        jLabel16.setText("Đơn giá");

        buttonXacNhanThemSanPham.setBackground(new java.awt.Color(198, 222, 192));
        buttonXacNhanThemSanPham.setText("Xác nhận");
        buttonXacNhanThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonXacNhanThemSanPhamActionPerformed(evt);
            }
        });

        buttonHuyThemSanPham.setBackground(new java.awt.Color(198, 222, 192));
        buttonHuyThemSanPham.setText("Hủy");
        buttonHuyThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHuyThemSanPhamActionPerformed(evt);
            }
        });

        errTenSP.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        errTenSP.setForeground(new java.awt.Color(255, 0, 0));
        errTenSP.setText("x");

        errSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        errSoLuong.setForeground(new java.awt.Color(255, 0, 0));
        errSoLuong.setText("x");

        errDonGia.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        errDonGia.setForeground(new java.awt.Color(255, 0, 0));
        errDonGia.setText("x");

        javax.swing.GroupLayout panelThemSanPhamLayout = new javax.swing.GroupLayout(panelThemSanPham);
        panelThemSanPham.setLayout(panelThemSanPhamLayout);
        panelThemSanPhamLayout.setHorizontalGroup(
            panelThemSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelThemSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelThemSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelThemSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelThemSanPhamLayout.createSequentialGroup()
                        .addComponent(buttonXacNhanThemSanPham)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonHuyThemSanPham))
                    .addGroup(panelThemSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(textFieldSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(errTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(errSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(errDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 78, Short.MAX_VALUE))
        );
        panelThemSanPhamLayout.setVerticalGroup(
            panelThemSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelThemSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelThemSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(5, 5, 5)
                .addComponent(errTenSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelThemSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errSoLuong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelThemSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(2, 2, 2)
                .addComponent(errDonGia)
                .addGap(2, 2, 2)
                .addGroup(panelThemSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonHuyThemSanPham)
                    .addComponent(buttonXacNhanThemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        footer_2.add(panelThemSanPham, "themSP");

        javax.swing.GroupLayout tienDoSanPhamLayout = new javax.swing.GroupLayout(tienDoSanPham);
        tienDoSanPham.setLayout(tienDoSanPhamLayout);
        tienDoSanPhamLayout.setHorizontalGroup(
            tienDoSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tienDoSanPhamLayout.createSequentialGroup()
                .addComponent(footer_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        tienDoSanPhamLayout.setVerticalGroup(
            tienDoSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tienDoSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(footer_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tienDoSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Body, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Body, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tienDoSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void xacNhanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xacNhanButtonActionPerformed
        //xu ly su kien xac nhan 
        //TH1 nut hien tai la xac nhan cap nhat
        if (xacNhanButton.getText().compareToIgnoreCase("Xác nhận") == 0) {
            //reset textField
            jLabel10.setText("");
            jLabel11.setText("");
            jLabel13.setText("");
            // updateHopDong 
            createHopDong();
            gD_QLHD.updateTable();
            // chuyen button ve dang mac dinh 
            xacNhanButton.setText("Xác nhận hợp đồng");
            tenHDmaHDTextField.setEditable(false);
            maKHmaHDTextField.setEditable(false);
            triGiamaHDTextField.setEditable(false);
            ngayKTmaHDTextField.setEditable(false);
            jDateChooser1.setEnabled(false);
            insertSanPhamToDatabase();
            cardLayout.show(footer_2,"tienDo");
            buttonThemSanPham.setVisible(false);
            buttonXoaSanPham.setVisible(false);
            updateTable();
            if (!(hopDong.getTrangThai().compareToIgnoreCase("chờ xác nhận") == 0)) {
                // neu hopDong khong thuoc trang thai cho xac nhan se an nut
                xacNhanButton.setVisible(false);

            }

        } 
        else 
        {
            if (hopDong.getTrangThai().compareToIgnoreCase("chờ xác nhận") == 0) {
                if (JOptionPane.showConfirmDialog(this, "Hợp đồng đã xác nhận sẽ không thể cập nhật thông tin. Vui lòng xác nhận!") == 0) {
                    thayDoiTrangThaiHopDong("đang thực hiện");
                    gD_QLHD.updateTable();
                }
            }

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_xacNhanButtonActionPerformed

    private void ngayKTmaHDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ngayKTmaHDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ngayKTmaHDTextFieldActionPerformed

    private void ngayBDmaHDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ngayBDmaHDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ngayBDmaHDTextFieldActionPerformed

    private void triGiamaHDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triGiamaHDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_triGiamaHDTextFieldActionPerformed

    private void maHDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maHDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maHDTextFieldActionPerformed

    private void trangThaimaHDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trangThaimaHDTextFieldActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_trangThaimaHDTextFieldActionPerformed

    private void capNhatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capNhatButtonActionPerformed
        tenHDmaHDTextField.setEditable(true);
        maKHmaHDTextField.setEditable(true);
        triGiamaHDTextField.setEditable(true);
        ngayKTmaHDTextField.setEditable(false);
        jDateChooser1.setEnabled(true);
        xacNhanButton.setText("Xác nhận");
        xacNhanButton.setEnabled(true);
        xacNhanButton.setVisible(true);
        buttonThemSanPham.setVisible(true);
        buttonXoaSanPham.setVisible(true);

// TODO add your handling code here:
    }//GEN-LAST:event_capNhatButtonActionPerformed

    private void huyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_huyButtonActionPerformed

        if (JOptionPane.showConfirmDialog(this, "Họp đồng bị hủy không thể hoàn tác. Vui Lòng xác nhận!") == 0) {
            thayDoiTrangThaiHopDong("hủy");
            gD_QLHD.updateTable();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_huyButtonActionPerformed

    private void textFieldSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldSoLuongActionPerformed

    private void buttonXacNhanThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonXacNhanThemSanPhamActionPerformed

        createSanPham();
        textFieldTenSP.setText("");
        textFieldSoLuong.setText("");
        textFieldDonGia.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonXacNhanThemSanPhamActionPerformed

    private void buttonHuyThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHuyThemSanPhamActionPerformed
     cardLayout.show(footer_2,"tienDo");        // TODO add your handling code here:
    }//GEN-LAST:event_buttonHuyThemSanPhamActionPerformed

    private void buttonThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonThemSanPhamActionPerformed
     cardLayout.show(footer_2,"themSP");
          // TODO add your handling code here:
    }//GEN-LAST:event_buttonThemSanPhamActionPerformed

    private void buttonXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonXoaSanPhamActionPerformed
        int rowIndex = sanPhamTable.getSelectedRow();
        daosp.deleteById(sanPhamModel.getValueAt(rowIndex, 0).toString());
        sanPhamModel.removeRow(rowIndex);


    }//GEN-LAST:event_buttonXoaSanPhamActionPerformed

    private void maKHmaHDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maKHmaHDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maKHmaHDTextFieldActionPerformed

    private void textFieldTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldTenSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldTenSPActionPerformed

    private void tenHDmaHDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenHDmaHDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenHDmaHDTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Body;
    private javax.swing.JPanel buttonBar;
    private javax.swing.JButton buttonHuyThemSanPham;
    private javax.swing.JButton buttonThemSanPham;
    private javax.swing.JButton buttonXacNhanThemSanPham;
    private javax.swing.JButton buttonXoaSanPham;
    private javax.swing.JButton capNhatButton;
    private javax.swing.JLabel errDonGia;
    private javax.swing.JLabel errSoLuong;
    private javax.swing.JLabel errTenSP;
    private javax.swing.JPanel footer_1;
    private javax.swing.JPanel footer_2;
    private javax.swing.JPanel header_body;
    private javax.swing.JPanel header_header_body;
    private javax.swing.JButton huyButton;
    private javax.swing.JPanel infoHolder;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField maHDTextField;
    private javax.swing.JTextField maKHmaHDTextField;
    private javax.swing.JTextField ngayBDmaHDTextField;
    private javax.swing.JTextField ngayKTmaHDTextField;
    private javax.swing.JPanel panelThemSanPham;
    private javax.swing.JProgressBar progessBarThoiGian;
    private javax.swing.JProgressBar progessBarTienDo;
    private javax.swing.JTable sanPhamTable;
    private javax.swing.JScrollPane scrollSanPham;
    private javax.swing.JTextField tenHDmaHDTextField;
    private javax.swing.JTextField textFieldDonGia;
    private javax.swing.JTextField textFieldSoLuong;
    private javax.swing.JTextField textFieldTenSP;
    private javax.swing.JPanel tienDoSanPham;
    private javax.swing.JScrollPane tienDoSanPhamScroll;
    private javax.swing.JTable tienDoSanPhamTable;
    private javax.swing.JTextField trangThaimaHDTextField;
    private javax.swing.JTextField triGiamaHDTextField;
    private javax.swing.JButton xacNhanButton;
    // End of variables declaration//GEN-END:variables
}
