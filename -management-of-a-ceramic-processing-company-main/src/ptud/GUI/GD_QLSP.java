/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ptud.GUI;

import java.awt.Component;
import java.io.EOFException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;

import org.jdesktop.swingx.autocomplete.AutoCompleteComboBoxEditor;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import ptud.DAO.*;
import ptud.Entity.BoPhan;
import ptud.Entity.ChiTietPhanCong;
import ptud.Entity.CongDoan;
import ptud.Entity.CongNhan;
import ptud.Entity.HopDong;
import ptud.Entity.SanPham;

/**
 *
 * @author Khanh
 */
public class GD_QLSP extends javax.swing.JPanel {

    /**
     * Creates new form GD_QLSP
     */
    public GD_QLSP() {
        initComponents();
        init();
    }

    public void init() {
        AutoCompleteDecorator.decorate(jComboBoxMaHopDong);
        AutoCompleteDecorator.decorate(jComboBoxMaBoPhan);
        AutoCompleteDecorator.decorate(jComboBoxCDTQ);

        dsHopDong = DAO_HopDong.getInstance().getAll();
        dsCongNhan = new DAO_CongNhan().getAll();
        dsSanPham = DAO_SanPham.getInstance().getAll();
        dsCongDoan = new DAO_CongDoan().getAll();
        dsBoPhan = new ArrayList<BoPhan>();
        dsCTPC = new ArrayList<ChiTietPhanCong>();
        // loaddata to jComboBoxMaHopDong

        

        loadJComboBoxMaHopDong(); 

        // loaddata to jComboBoxMaBoPhan
        for (BoPhan boPhan : new DAO_BoPhan().getAll()) {
            // Only take maBP prefixed with 'SX'
            if (boPhan.getMaBP().startsWith("SX")) {
                jComboBoxMaBoPhan.addItem(boPhan.getMaBP());
                jComboBoxBoPhan.addItem(boPhan.getTenBP());
                jComboBoxBoPhan1.addItem(boPhan.getTenBP());
                dsBoPhan.add(boPhan);
            }
        }

        // loaddata sanpham vao jcombobox
        for (SanPham sanPham : dsSanPham) {
            System.out.println(sanPham.getMaSanPham());
            HopDong hd = new DAO_HopDong().get(sanPham.getMaHD());
            System.out.println(hd);
            if(hd.getTrangThai().equals("đang thực hiện"))
                jComboBoxSanPham.addItem(sanPham.getTenSanPham());
        }

        // loaddata jcombobox congdoan
        for (CongDoan cd : DAO_CongDoan.getInstance().getAll())
            jComboBoxCongDoan.addItem(cd.getTenCD());

        // loaddata to jtableCongDoan
        loadDsCongDoan();
        loadDsCongNhan();

        jDateChooser1.setDate(new Date());
        loadDsCTPC();
    }

    // biến toàn cục
    public ArrayList<CongDoan> dsCongDoan;
    public String maSP;
    public CongDoan congDoan;
    public ArrayList<SanPham> dsSanPham;
    public ArrayList<CongNhan> dsCongNhan;
    public ArrayList<BoPhan> dsBoPhan;
    public ArrayList<ChiTietPhanCong> dsCTPC;
    ArrayList<HopDong> dsHopDong; 


    private void loadJcomboboxSanPham() {
        for (SanPham sanPham : dsSanPham) {
            System.out.println(sanPham.getMaSanPham());
            HopDong hd = new DAO_HopDong().get(sanPham.getMaHD());
            System.out.println(hd);
            if(hd.getTrangThai().equals("đang thực hiện"))
                jComboBoxSanPham.addItem(sanPham.getTenSanPham());
        }
    }

    // loaddata SanPham
    private void loadDsSanPham() {
        dsSanPham = DAO_SanPham.getInstance().getAll();
        DefaultTableModel tblModel = (DefaultTableModel) jTableSanPham.getModel();
        // xóa hết các phần tử trong jTableSanPham
        tblModel.setRowCount(0);
        String maHD = jComboBoxMaHopDong.getSelectedItem().toString();
        for (SanPham sp : dsSanPham) {
            String maHD1 = "0511202301";
            maHD1 = sp.getMaHD();
            HopDong hd = DAO_HopDong.getInstance().get(maHD1);
            String typeHD = jComboBoxLoaiHopDong.getSelectedItem().toString(); 
            if(hd.getTrangThai().equals(typeHD))
            if (maHD1.equals(maHD) || maHD.equals("Tất cả")) {
                String tbData[] = { sp.getMaSanPham(), sp.getTenSanPham() };
                tblModel.addRow(tbData);
            }
        }
        tblModel.fireTableDataChanged();
    }

    // loaddata CongDoan
    private void loadDsCongDoan() {
        ArrayList<CongDoan> dsCongDoan = DAO_CongDoan.getInstance().getAll();
        ArrayList<CongDoan> dsCongDoan2 = new ArrayList<CongDoan>();
        for (CongDoan cd : dsCongDoan) {
            SanPham sp = new DAO_SanPham().get(cd.getMaSP());
            HopDong hd = new DAO_HopDong().get(sp.getMaHD());
            if(!hd.getTrangThai().equals("đang thực hiện"))
                continue; 
            if (cd.getSoLuongChuanBi() < cd.getSoLuongChuanBiToiThieu())
                continue;

            if (jComboBoxBoPhan.getSelectedIndex() > 0) {
                int id = jComboBoxBoPhan.getSelectedIndex() - 1;
                if (cd.getMaBP().equals(dsBoPhan.get(id).getMaBP())) {
                    if (jComboBoxSanPham.getSelectedIndex() > 0) {
                        int id2 = jComboBoxSanPham.getSelectedIndex() - 1;
                        if (cd.getMaSP().equals(dsSanPham.get(id2).getMaSanPham()))
                            dsCongDoan2.add(cd);
                    } else
                        dsCongDoan2.add(cd);

                }
            } else {
                if (jComboBoxSanPham.getSelectedIndex() > 0) {
                    int id2 = jComboBoxSanPham.getSelectedIndex() - 1;
                    if (cd.getMaSP().equals(dsSanPham.get(id2).getMaSanPham()))
                        dsCongDoan2.add(cd);
                } else
                    dsCongDoan2.add(cd);
            }
        }

        // loaddata to jTableCongDoan1
        DefaultTableModel tblModelCongDoan = (DefaultTableModel) jTableCongDoan1.getModel();
        tblModelCongDoan.setRowCount(0);
        for (CongDoan cd : dsCongDoan2) {
            String tenBP = new DAO_BoPhan().get(cd.getMaBP()).getTenBP();
            String tbData[] = { cd.getMaCD(), cd.getTenCD(), tenBP, String.valueOf(cd.getSoLuongChuanBi()),
                    String.valueOf(cd.getSoLuongHoanThanh()) };
            tblModelCongDoan.addRow(tbData);
        }
        tblModelCongDoan.fireTableDataChanged();
    }

    private void loadDsCongNhan() {
        dsCongNhan = DAO_CongNhan.getInstance().getAll();
        DefaultTableModel tblModelCongNhan = (DefaultTableModel) jTableCongNhan.getModel();
        tblModelCongNhan.setRowCount(0);
        for (CongNhan cn : dsCongNhan) {
            int idBP = jComboBoxBoPhan.getSelectedIndex() - 1;
            if (!cn.isTrangThai())
                continue;
            if (idBP >= 0) {
                if (!cn.getBoPhan().getMaBP().equals(dsBoPhan.get(idBP).getMaBP()))
                    continue;
            } else {
                int row = jTableCongDoan1.getSelectedRow();
                if (row >= 0) {
                    String macd = (String) jTableCongDoan1.getValueAt(row, 0);
                    String mabp = DAO_CongDoan.getInstance().get(macd).getMaBP();
                    if (!cn.getBoPhan().getMaBP().equals(mabp))
                        continue;
                }
            }

            if (cn.isChoPhanCong()) {
                // add to jTableCongNhan
                String tbData[] = { cn.getMaCN(), cn.getTen(), "" };
                tblModelCongNhan.addRow(tbData);
            }
            
        }
    }

    private void loadDsCTPC() {

        Instant instant = jDateChooser1.getDate().toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        ArrayList<ChiTietPhanCong> dsCTPC = DAO_ChiTietPhanCong.getInstance().getAllByNgay(localDate);
        DefaultTableModel tblModelCTPC = (DefaultTableModel) jTableCTPC.getModel();
        tblModelCTPC.setRowCount(0);

        String bp = jComboBoxBoPhan1.getSelectedItem().toString();
        String cd2 = jComboBoxCongDoan.getSelectedItem().toString();

        for (ChiTietPhanCong ctpc : dsCTPC) {
            CongNhan cn = DAO_CongNhan.getInstance().get(ctpc.getMaCN());
            CongDoan cd = DAO_CongDoan.getInstance().get(ctpc.getMaCD());
            int soLuongHoanThanh = 0;
            // get Phieu cham cong cong nhan của cn trong ngày hôm nay 
            LocalDate date = jDateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            soLuongHoanThanh = DAO_ChiTietPhanCong.getInstance().getSoLuongCongDoanHoanThanhByMaCongNhan(cn.getMaCN(), date);

            String tenBP = new DAO_BoPhan().get(DAO_CongDoan.getInstance().get(ctpc.getMaCD()).getMaBP()).getTenBP();
            String tenCD = cd.getTenCD();
            boolean ok = true;
            if (!tenBP.equals(bp) && !bp.equals("Tất cả")) {
                ok = false;
            }

            if (!tenCD.equals(cd2) && !cd2.equals("Tất cả"))
                ok = false;

            if (ok) {
                String tbData[] = { ctpc.getMaCN(), cn.getTen(), tenBP, cd.getMaSP(), tenCD,
                        ctpc.getSoLuongCDGiao() + "", soLuongHoanThanh + "" };
                tblModelCTPC.addRow(tbData);
            }
        }
        // sao chép jTableCTPC vào jTableCTPC1
        DefaultTableModel modelCTPC = (DefaultTableModel) jTableCTPC.getModel();
        DefaultTableModel modelCTPC1 = (DefaultTableModel) jTableCTPC1.getModel();

        modelCTPC1.setRowCount(0);
        for (int row = 0; row < modelCTPC.getRowCount(); row++) {
            Vector rowData = new Vector();
            for (int column = 0; column < modelCTPC.getColumnCount(); column++) {
                rowData.add(modelCTPC.getValueAt(row, column));
            }
            modelCTPC1.addRow(rowData);
        }
    }

    private void loadJComboBoxMaHopDong() {
        String tt = jComboBoxLoaiHopDong.getSelectedItem().toString(); 
        jComboBoxMaHopDong.removeAllItems();
        jComboBoxMaHopDong.addItem("Tất cả");
        jComboBoxMaHopDong.setSelectedIndex(0);
        for (HopDong h : dsHopDong) {
           if( h.getTrangThai().equals(tt) ) {
                jComboBoxMaHopDong.addItem(h.getMaHD());
           }
        }
        loadDsSanPham(); 
    }

    private void clearData() {
        jTextFieldMaCD.setText("");
        jTextFieldTenCD.setText("");
        jTextFieldDonGia.setText("");
        if (jComboBoxMaBoPhan.getItemCount() > 0)
            jComboBoxMaBoPhan.setSelectedIndex(0);
        jTextFieldSLCBTT.setText("1");
        jCheckBoxHoanThanh.setSelected(false);
        if (jComboBoxCDTQ.getItemCount() > 0)
            jComboBoxCDTQ.setSelectedIndex(0);
        DefaultTableModel tblModel = (DefaultTableModel) jTableCDTQ.getModel();
        tblModel.setRowCount(0);
        tblModel.fireTableDataChanged();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButtonTaoMoi = new javax.swing.JButton();
        jButtonSua = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jButtonLuu = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxMaBoPhan = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldDonGia = new javax.swing.JTextField();
        jCheckBoxHoanThanh = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCDTQ = new javax.swing.JTable();
        jButtonXoaCDTQ = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jComboBoxCDTQ = new javax.swing.JComboBox<>();
        jButtonThemCDTQ = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableSanPham = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxMaHopDong = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jComboBoxLoaiHopDong = new javax.swing.JComboBox<>();
        jTextFieldMaCD = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableCongDoan = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxSapXep = new javax.swing.JComboBox<>();
        jToggleButtonSort = new javax.swing.JToggleButton();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldSLCBTT = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldTenCD = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxSanPham = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableCongDoan1 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableCongNhan = new javax.swing.JTable();
        jButtonPhanCong = new javax.swing.JButton();
        jButtonNhapSoLuong = new javax.swing.JButton();
        jTextFieldSoLuong = new javax.swing.JTextField();
        jButtonLuu2 = new javax.swing.JButton();
        jComboBoxBoPhan = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableCTPC = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButtonSua1 = new javax.swing.JButton();
        jButtonXoa1 = new javax.swing.JButton();
        jButtonLuu1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboBoxCongDoan = new javax.swing.JComboBox<>();
        jComboBoxBoPhan1 = new javax.swing.JComboBox<>();
        jTableCTPC1 = new javax.swing.JTable();

        jButtonTaoMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/icons8-add-24.png"))); // NOI18N
        jButtonTaoMoi.setText("Tạo mới");
        jButtonTaoMoi.setEnabled(false);
        jButtonTaoMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonTaoMoiMouseReleased(evt);
            }
        });
        jButtonTaoMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTaoMoiActionPerformed(evt);
            }
        });

        jButtonSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/editing.png"))); // NOI18N
        jButtonSua.setText("Sửa");
        jButtonSua.setEnabled(false);
        jButtonSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonSuaMouseReleased(evt);
            }
        });
        jButtonSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaActionPerformed(evt);
            }
        });

        jButtonXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/trash-bin.png"))); // NOI18N
        jButtonXoa.setText("Xoá");
        jButtonXoa.setEnabled(false);
        jButtonXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonXoaMouseReleased(evt);
            }
        });
        jButtonXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaActionPerformed(evt);
            }
        });

        jButtonLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/diskette.png"))); // NOI18N
        jButtonLuu.setText("Lưu");
        jButtonLuu.setEnabled(false);
        jButtonLuu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonLuuMouseReleased(evt);
            }
        });
        jButtonLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLuuActionPerformed(evt);
            }
        });

        jLabel3.setText("Mã công đoạn:");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel4.setText("Mã bộ phận");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jComboBoxMaBoPhan.setEditable(true);
        jComboBoxMaBoPhan.setEnabled(false);
        jComboBoxMaBoPhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMaBoPhanActionPerformed(evt);
            }
        });

        jLabel5.setText("Đơn giá:");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jTextFieldDonGia.setEditable(false);
        jTextFieldDonGia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldDonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDonGiaActionPerformed(evt);
            }
        });

        jCheckBoxHoanThanh.setText("Hoàn thành");
        jCheckBoxHoanThanh.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBoxHoanThanh.setEnabled(false);
        jCheckBoxHoanThanh.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jCheckBoxHoanThanh.setIconTextGap(30);
        jCheckBoxHoanThanh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jCheckBoxHoanThanhMouseReleased(evt);
            }
        });
        jCheckBoxHoanThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxHoanThanhActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách công đoạn tiên quyết"));

        jTableCDTQ.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã công đoạn", "Tên công đoạn"
            }
        ));
        jTableCDTQ.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableCDTQ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableCDTQMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableCDTQ);
        jTableCDTQ.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jButtonXoaCDTQ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/trash-bin.png"))); // NOI18N
        jButtonXoaCDTQ.setText("Xoá");
        jButtonXoaCDTQ.setEnabled(false);
        jButtonXoaCDTQ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonXoaCDTQMouseReleased(evt);
            }
        });
        jButtonXoaCDTQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaCDTQActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jButtonXoaCDTQ, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(237, Short.MAX_VALUE)
                .addComponent(jButtonXoaCDTQ)
                .addContainerGap())
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(43, Short.MAX_VALUE)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Thêm công đoạn tiên quyết"));

        jComboBoxCDTQ.setEditable(true);
        jComboBoxCDTQ.setEnabled(false);
        jComboBoxCDTQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCDTQActionPerformed(evt);
            }
        });

        jButtonThemCDTQ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/icons8-add-24.png"))); // NOI18N
        jButtonThemCDTQ.setText("Thêm");
        jButtonThemCDTQ.setEnabled(false);
        jButtonThemCDTQ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonThemCDTQMouseReleased(evt);
            }
        });
        jButtonThemCDTQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemCDTQActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jComboBoxCDTQ, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jButtonThemCDTQ)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jComboBoxCDTQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonThemCDTQ)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Chọn sản phẩm"));

        jTableSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSanPham.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableSanPhamMouseReleased(evt);
            }
        });
        jTableSanPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableSanPhamKeyReleased(evt);
            }
        });
        jTableSanPham.setRowHeight(30);
        jTableSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setViewportView(jTableSanPham);
        jTableSanPham.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jLabel6.setText("Hợp đồng:");

        jComboBoxMaHopDong.setEditable(true);
        jComboBoxMaHopDong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        jComboBoxMaHopDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jComboBoxMaHopDongMouseReleased(evt);
            }
        });
        jComboBoxMaHopDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMaHopDongActionPerformed(evt);
            }
        });

        jLabel12.setText("Loại hợp đồng:");

        jComboBoxLoaiHopDong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "chờ xác nhận", "đang thực hiện", "hủy" }));
        jComboBoxLoaiHopDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLoaiHopDongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxLoaiHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxMaHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxMaHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12)
                    .addComponent(jComboBoxLoaiHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTextFieldMaCD.setEditable(false);
        jTextFieldMaCD.setEnabled(false);
        jTextFieldMaCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMaCDActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách công đoạn"));

        jTableCongDoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã công đoạn", "Tên công đoạn", "Đơn giá", "Bộ phận", "Mã sản phẩm", "Trạng thái", "Số lượng chuẩn bị tối thiểu", "Số lượng chuẩn bị", "Số lượng hoàn thành"
            }
        ));
        jTableCongDoan.setRowHeight(30);
        jTableCongDoan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTableCongDoan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableCongDoan.setShowHorizontalLines(true);
        jTableCongDoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableCongDoanMouseReleased(evt);
            }
        });
        jTableCongDoan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableCongDoanKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTableCongDoan);

        jLabel1.setText("Sắp xếp theo:");

        jComboBoxSapXep.setEditable(true);
        jComboBoxSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã công đoạn", "Tên công đoạn", "Đơn giá", "Bộ phận", "Mã sản phẩm", "Trạng thái", "Số lượng chẩn bị tối thiểu", "Số lượng chuẩn bị", "Số lượng hoàn thành" }));
        jComboBoxSapXep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSapXepActionPerformed(evt);
            }
        });

        jToggleButtonSort.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/icons8-sort2-50.png"))); // NOI18N
        jToggleButtonSort.setToolTipText("");
        jToggleButtonSort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jToggleButtonSortMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jToggleButtonSort, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 759, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3)))
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jComboBoxSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButtonSort))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel7.setText("Số lượng chuẩn bị tối thiểu:");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jTextFieldSLCBTT.setEditable(false);
        jTextFieldSLCBTT.setText("1");
        jTextFieldSLCBTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSLCBTTActionPerformed(evt);
            }
        });

        jLabel13.setText(".000đ");

        jTextFieldTenCD.setEditable(false);
        jTextFieldTenCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTenCDActionPerformed(evt);
            }
        });

        jLabel14.setText("Tên công đoạn:");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonSua, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonTaoMoi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(jButtonXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonLuu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldMaCD))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxMaBoPhan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextFieldDonGia)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBoxHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldSLCBTT, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldTenCD)))
                        .addGap(27, 27, 27)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(105, 105, 105))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldMaCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextFieldTenCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBoxMaBoPhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(jTextFieldDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jTextFieldSLCBTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBoxHoanThanh))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonTaoMoi)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSua)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonXoa)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonLuu))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(54, 54, 54))
        );

        jTabbedPane1.addTab("Quản lý công đoạn", jPanel1);

        jLabel2.setText("Bộ phận");

        jComboBoxSanPham.setEditable(true);
        jComboBoxSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        jComboBoxSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSanPhamActionPerformed(evt);
            }
        });

        jLabel8.setText("Sản phẩm:");

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách công đoạn"));

        jTableCongDoan1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã công đoạn", "Tên công đoạn", "Bộ phận", "Số lượng chuẩn bị", "Số lượng hoàn thành"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCongDoan1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableCongDoan1.setRowHeight(30);
        jTableCongDoan1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTableCongDoan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableCongDoan1MouseReleased(evt);
            }
        });
        jTableCongDoan1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTableCongDoan1PropertyChange(evt);
            }
        });
        jTableCongDoan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableCongDoan1KeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(jTableCongDoan1);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách công nhân"));

        jTableCongNhan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã công nhân", "Tên công nhân", "Số lượng giao"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCongNhan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableCongNhan.setRowHeight(30);
        jTableCongNhan.setEnabled(false);
        jTableCongNhan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(jTableCongNhan);

        jButtonPhanCong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/icons8-assignment-24.png"))); // NOI18N
        jButtonPhanCong.setText("Phân công");
        jButtonPhanCong.setEnabled(false);
        jButtonPhanCong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonPhanCongMouseReleased(evt);
            }
        });
        jButtonPhanCong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPhanCongActionPerformed(evt);
            }
        });

        jButtonNhapSoLuong.setText("Nhập số lượng");
        jButtonNhapSoLuong.setEnabled(false);
        jButtonNhapSoLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonNhapSoLuongMouseReleased(evt);
            }
        });
        jButtonNhapSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNhapSoLuongActionPerformed(evt);
            }
        });

        jTextFieldSoLuong.setEditable(false);

        jButtonLuu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/diskette.png"))); // NOI18N
        jButtonLuu2.setText("Lưu");
        jButtonLuu2.setEnabled(false);
        jButtonLuu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonLuu2MouseReleased(evt);
            }
        });
        jButtonLuu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLuu2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jButtonNhapSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonLuu2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonPhanCong)
                        .addGap(50, 50, 50))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPhanCong)
                    .addComponent(jButtonNhapSoLuong)
                    .addComponent(jTextFieldSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLuu2))
                .addContainerGap())
        );

        jComboBoxBoPhan.setEditable(true);
        jComboBoxBoPhan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        jComboBoxBoPhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxBoPhanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxBoPhan, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel8)
                            .addComponent(jComboBoxSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxBoPhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Bảng phân công"));

        jTableCTPC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Bộ phận", "Mã sản phẩm", "Công đoạn", "Số lượng được giao", "Số lượng hoàn thành"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCTPC.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableCTPC.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableCTPC.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableCTPC.setRowHeight(30);
        jTableCTPC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableCTPCMouseReleased(evt);
            }
        });
        jTableCTPC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableCTPCKeyReleased(evt);
            }
        });
        jScrollPane6.setViewportView(jTableCTPC);

        jLabel9.setText("Ngày:");

        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jButtonSua1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/editing.png"))); // NOI18N
        jButtonSua1.setText("Sửa");
        jButtonSua1.setEnabled(false);
        jButtonSua1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonSua1MouseReleased(evt);
            }
        });
        jButtonSua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSua1ActionPerformed(evt);
            }
        });

        jButtonXoa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/trash-bin.png"))); // NOI18N
        jButtonXoa1.setText("Xoá");
        jButtonXoa1.setEnabled(false);
        jButtonXoa1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonXoa1MouseReleased(evt);
            }
        });
        jButtonXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoa1ActionPerformed(evt);
            }
        });

        jButtonLuu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/diskette.png"))); // NOI18N
        jButtonLuu1.setText("Lưu");
        jButtonLuu1.setEnabled(false);
        jButtonLuu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonLuu1MouseReleased(evt);
            }
        });
        jButtonLuu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLuu1ActionPerformed(evt);
            }
        });

        jLabel10.setText("Bộ phận:");

        jLabel11.setText("Công đoạn:");

        jComboBoxCongDoan.setEditable(true);
        jComboBoxCongDoan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        jComboBoxCongDoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCongDoanActionPerformed(evt);
            }
        });

        jComboBoxBoPhan1.setEditable(true);
        jComboBoxBoPhan1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        jComboBoxBoPhan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxBoPhan1ActionPerformed(evt);
            }
        });

        jTableCTPC1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Bộ phận", "Mã sản phẩm", "Công đoạn", "Số lượng được giao", "Số lượng hoàn thành"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCTPC1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableCTPC.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableCTPC.setRowHeight(30);
        jTableCTPC1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableCTPC1MouseReleased(evt);
            }
        });
        jTableCTPC1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableCTPC1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonLuu1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonSua1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxBoPhan1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxCongDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(505, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                    .addGap(130, 130, 130)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1124, Short.MAX_VALUE)
                    .addGap(15, 15, 15)))
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                    .addContainerGap(1195, Short.MAX_VALUE)
                    .addComponent(jTableCTPC1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(74, Short.MAX_VALUE)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jComboBoxBoPhan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jComboBoxCongDoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addComponent(jButtonSua1)
                .addGap(18, 18, 18)
                .addComponent(jButtonXoa1)
                .addGap(18, 18, 18)
                .addComponent(jButtonLuu1)
                .addContainerGap(128, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                    .addGap(49, 49, 49)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addGap(20, 20, 20)))
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                    .addContainerGap(112, Short.MAX_VALUE)
                    .addComponent(jTableCTPC1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(192, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1)))
                .addGap(97, 97, 97))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(97, 97, 97))
        );

        jTabbedPane1.addTab("Phân chia công đoạn", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonXoa1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonXoa1MouseReleased



        if(jButtonXoa1.isEnabled()) {
            // yes no option
            int result = JOptionPane.showConfirmDialog(this, "Bạn đã chắc chắn chưa ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                int row = jTableCTPC.getSelectedRow(); 
                if(row >= 0) {
                    String maCN = jTableCTPC.getValueAt(row, 0).toString();
                    DAO_ChiTietPhanCong.getInstance().deleteHomNayByMaCN(maCN);
                    DAO_ChiTietPhanCong.getInstance().updateChoPhanCong(maCN, true);
                }
                loadDsCTPC();
                loadDsCongNhan();
                loadDsCongDoan();
            } 
        }
    }//GEN-LAST:event_jButtonXoa1MouseReleased

    private void jComboBoxLoaiHopDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLoaiHopDongActionPerformed
        // TODO add your handling code here:
        //viết code kiểm tra jComboBoxLoaiHopDong có tồn tại item nào không ?
        // TODO add your handling code here: viết code kiểm tra jComboBoxLoaiHopDong có tồn tại item nào không ?
        if(jComboBoxLoaiHopDong.isEnabled() && jComboBoxLoaiHopDong.getItemCount() > 0){
            loadJComboBoxMaHopDong(); 
        }
    }//GEN-LAST:event_jComboBoxLoaiHopDongActionPerformed

    private void jComboBoxMaHopDongMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxMaHopDongMouseReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBoxMaHopDongMouseReleased

    private void jTableCTPC1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCTPC1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableCTPC1MouseReleased

    private void jTableCTPC1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableCTPC1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableCTPC1KeyReleased

    private void jTableCongDoan1MouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTableCongDoan1MouseReleased
        // TODO add your handling code here:
        if (jTableCongDoan1.isEnabled()) {
            loadDsCongNhan();
            jButtonNhapSoLuong.setEnabled(jTableCongNhan.getRowCount() > 0);
        }
    }// GEN-LAST:event_jTableCongDoan1MouseReleased

    private void jTableCongDoan1PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jTableCongDoan1PropertyChange
        // TODO add your handling code here:

    }// GEN-LAST:event_jTableCongDoan1PropertyChange

    private void jTableCongDoan1KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTableCongDoan1KeyReleased
        // TODO add your handling code here:
        if (jTableCongDoan1.isEnabled()) {
            loadDsCongNhan();
            jButtonNhapSoLuong.setEnabled(jTableCongNhan.getRowCount() > 0);
        }
    }// GEN-LAST:event_jTableCongDoan1KeyReleased

    private void jTableSanPhamKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTableSanPhamKeyReleased
        // TODO add your handling code here:
        if (jTableSanPham.isEnabled()) {
            int row = jTableSanPham.getSelectedRow();
            maSP = (String) jTableSanPham.getValueAt(row, 0);
            loadDataJTableCongDoan();
            loadDataJComboBoxCDTQ();
            jButtonTaoMoi.setEnabled(true);
            jButtonXoa.setEnabled(false);
            jButtonSua.setEnabled(false);
            jTableCongDoan.setEnabled(true);
            clearData();
        }
    }// GEN-LAST:event_jTableSanPhamKeyReleased

    private void jTableCongDoanKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTableCongDoanKeyReleased
        // TODO add your handling code here:
        if (jTableCongDoan.isEnabled()) {
            if (jTableCongDoan.getSelectedRowCount() > 0) {
                jButtonXoa.setEnabled(true);
                jButtonSua.setEnabled(true);

                int selectedRow = jTableCongDoan.getSelectedRow();
                String maCD = (String) jTableCongDoan.getValueAt(selectedRow, 0);
                congDoan = DAO_CongDoan.getInstance().get(maCD);
                jTextFieldMaCD.setText(congDoan.getMaCD());
                jComboBoxMaBoPhan.setSelectedItem(congDoan.getMaBP());
                jTextFieldTenCD.setText(congDoan.getTenCD());
                jTextFieldDonGia.setText(String.format("%.0f", congDoan.getDonGia() / 1000));
                // String.format("%.0f", congDoan.getDonGia()/1000);
                jCheckBoxHoanThanh.setSelected(congDoan.isTrangThai());
                jTextFieldSLCBTT.setText(String.valueOf(congDoan.getSoLuongChuanBiToiThieu()));
                // loadDataJTableCongDoanTienQuyet(maCD);
                loadDataJTableCDTQ();
                loadDataJComboBoxCDTQ();
            }
        }
    }// GEN-LAST:event_jTableCongDoanKeyReleased

    private void jButtonNhapSoLuongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonNhapSoLuongActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButtonNhapSoLuongActionPerformed

    private void jButtonNhapSoLuongMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonNhapSoLuongMouseReleased
        if (jButtonNhapSoLuong.isEnabled()) {
            if (!jButtonNhapSoLuong.getText().equals("Hủy")) {
                jButtonNhapSoLuong.setText("Hủy");
                jButtonPhanCong.setEnabled(true);
                jComboBoxBoPhan.setEnabled(false);
                jComboBoxSanPham.setEnabled(false);
                jTableCongDoan1.setEnabled(false);
                jTableCongNhan.setEnabled(true);

                // jTableCongNhan.editCellAt(0, 2);
                // Component editor = jTableCongNhan.getEditorComponent();
                // if (editor instanceof JTextField) {
                // JTextField textField = (JTextField) editor;
                // textField.requestFocus();
                //
                // // Đặt con trỏ chuột ở cuối cell
                // textField.setCaretPosition(textField.getText().length());
                // }
                jTextFieldSoLuong.setEditable(true);
                jTextFieldSoLuong.requestFocus();
                jButtonLuu2.setEnabled(true);
            } else {
                jButtonNhapSoLuong.setText("Nhập số lượng");
                jButtonPhanCong.setEnabled(false);
                jComboBoxBoPhan.setEnabled(true);
                jComboBoxSanPham.setEnabled(true);
                jTableCongDoan1.setEnabled(true);
                jTableCongNhan.setEnabled(false);
                // jTableCongNhan.removeEditor();
                // jTableCongNhan.requestFocus();
                jTextFieldSoLuong.setEditable(false);
                jTextFieldSoLuong.setText("");
                jButtonLuu2.setEnabled(false);

                loadDsCongNhan();
            }
        }
    }// GEN-LAST:event_jButtonNhapSoLuongMouseReleased

    private void jButtonPhanCongMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonPhanCongMouseReleased
        // TODO add your handling code here:
        if (jButtonPhanCong.isEnabled()) {
            try {
                int row = jTableCongDoan1.getSelectedRow();
                if(row<0)
                    throw new Exception("Vui lòng chọn công đoạn cần phân công!");
                String macd = jTableCongDoan1.getValueAt(row, 0).toString();
                CongDoan cd = DAO_CongDoan.getInstance().get(macd);
                int soLuongChuanBi = cd.getSoLuongChuanBi();

                // check 
                boolean have = false;
                int sum = 0; 
                for (int i = 0; i < jTableCongNhan.getRowCount(); i++) {
                    String maCN = jTableCongNhan.getValueAt(i, 0).toString();
                    String sl = jTableCongNhan.getValueAt(i, 2).toString();
                    if(sl==null || sl.isBlank()) 
                        continue; 
                    if (!sl.matches("\\d+"))
                        throw new Exception("Vui lòng nhập số lượng là số tự nhiên >= 0");
                    int soLuong = Integer.parseInt(sl);
                    if (soLuong == 0)
                        continue;
                    System.out.println(soLuong);
                    if (soLuong < 0) 
                        throw new Exception("Vui lòng nhập số lượng là số tự nhiên >=  0");
                    have = true;
                    sum += soLuong; 
                }
                if (!have)
                    throw new Exception("Vui lòng nhập số lượng cho ít nhất một công nhân!");
                if(sum > soLuongChuanBi)
                    throw new Exception("Số lượng phân công vượt quá số lượng chuẩn bị!"); 

                // yes, no option
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm?", "Xác nhận",JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < jTableCongNhan.getRowCount(); i++) {
                        String maCN = jTableCongNhan.getValueAt(i, 0).toString();
                        String sl = jTableCongNhan.getValueAt(i, 2).toString();
                        String ngay = new SimpleDateFormat("ddMMyyyy").format(new Date());
                        String maCTPC = ngay + maCN;
                        if(sl.isBlank()) 
                            continue; 
                        if (!sl.matches("\\d+"))
                            throw new Exception("Vui lòng nhập số lượng là số tự nhiên >= 0");
                        int soLuong = Integer.parseInt(sl);
                        if (soLuong == 0)
                            continue; 
                        DAO_ChiTietPhanCong.getInstance()
                                .insert(new ChiTietPhanCong(maCTPC, macd, maCN, LocalDate.now(), soLuong));
                        DAO_ChiTietPhanCong.getInstance().updateChoPhanCong(maCN, false);
                    }  
                    loadDsCongNhan();
                    loadDsCongDoan();
                    loadDsCTPC();
                    // cập nhật vào bảng phân công
                    jButtonNhapSoLuong.setText("Nhập số lượng");
                    jButtonNhapSoLuong.setEnabled(false);
                    jButtonPhanCong.setEnabled(false);
                    jComboBoxBoPhan.setEnabled(true);
                    jComboBoxSanPham.setEnabled(true);
                    jTableCongDoan1.setEnabled(true);
                    jTableCongNhan.setEnabled(false);
                    jTableCongNhan.removeEditor();
                    jTableCongNhan.requestFocus();
                    jTextFieldSoLuong.setEditable(false);
                    jButtonLuu2.setEnabled(false);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
                jTextFieldSoLuong.requestFocus();
            }
        }

        
    }// GEN-LAST:event_jButtonPhanCongMouseReleased

    private void jTableCTPCMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTableCTPCMouseReleased
        if (jTableCTPC.isEnabled()) {
            int row = jTableCTPC.getSelectedRow();
            // kiểm tra jDateChooser1 đang chỉ đúng ngày hiện tại không, chỉ lấy
            // ngày/tháng/năm
            Date selectedDate = jDateChooser1.getDate();
            LocalDate current = LocalDate.now();
            LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (row >= 0 && current.equals(localDate)) {
                jButtonSua1.setEnabled(true);
                jButtonXoa1.setEnabled(true);
            }
        }
    }// GEN-LAST:event_jTableCTPCMouseReleased

    private void jButtonLuu2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonLuu2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButtonLuu2ActionPerformed

    private void jButtonLuu2MouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonLuu2MouseReleased
        if (jButtonLuu2.isEnabled()) {
            try {
                loadDsCongNhan();
                String sl = jTextFieldSoLuong.getText().toString();
                jTextFieldSoLuong.setText("");
                int soLuong = Integer.parseInt(sl);

                if (soLuong <= 0)
                    throw new Exception("Vui lòng nhập số lượng là số tự nhiên > 0");
                int row = jTableCongDoan1.getSelectedRow();
                String macd = jTableCongDoan1.getValueAt(row, 0).toString();
                CongDoan cd = DAO_CongDoan.getInstance().get(macd);
                int soLuongChuanBi = cd.getSoLuongChuanBi();
                for (int i = 0; i < jTableCongNhan.getRowCount(); i++) {
                    if (soLuongChuanBi > 0) {
                        // set giá trị row i, col 2 = min (soLuongChuanbi, soLuong)
                        jTableCongNhan.setValueAt(Math.min(soLuongChuanBi, soLuong), i, 2);
                        soLuongChuanBi -= Math.min(soLuongChuanBi, soLuong);
                    } else
                        break;
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng là số tự nhiên > 0");
                jTextFieldSoLuong.requestFocus();
                return;
            }

        }
    }// GEN-LAST:event_jButtonLuu2MouseReleased 

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jDateChooser1PropertyChange
        // TODO add your handling code here:
        loadDsCTPC();
        jButtonSua1.setEnabled(false);
        jButtonXoa1.setEnabled(false);
        jButtonLuu1.setEnabled(false);
    }// GEN-LAST:event_jDateChooser1PropertyChange

    private void jComboBoxBoPhan1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxBoPhan1ActionPerformed
        // TODO add your handling code here:
        if (jComboBoxBoPhan1.isEnabled())
            loadDsCTPC();
    }// GEN-LAST:event_jComboBoxBoPhan1ActionPerformed

    private void jTableCTPCKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTableCTPCKeyReleased
        // TODO add your handling code here:
        if (jTableCTPC.isEnabled()) {
            int row = jTableCTPC.getSelectedRow();
            // kiểm tra jDateChooser1 đang chỉ đúng ngày hiện tại không, chỉ lấy
            // ngày/tháng/năm
            Date selectedDate = jDateChooser1.getDate();
            LocalDate current = LocalDate.now();
            LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (row >= 0 && current.equals(localDate)) {
                jButtonSua1.setEnabled(true);
                jButtonXoa1.setEnabled(true);
            }
        }
    }// GEN-LAST:event_jTableCTPCKeyReleased

    private void jButtonSua1MouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonSua1MouseReleased
        if (jButtonSua1.isEnabled()) {

            if (!jButtonSua1.getText().equals("Hủy")) {
                jButtonSua1.setText("Hủy");
                jButtonLuu1.setEnabled(true);
                jButtonXoa1.setEnabled(false);
                jTableCTPC.setEnabled(false);

                int row = jTableCTPC.getSelectedRow();
                jTableCTPC.editCellAt(row, 5);
                Component editor = jTableCTPC.getEditorComponent();
                if (editor instanceof JTextField) {
                    JTextField textField = (JTextField) editor;
                    textField.requestFocus();
                    // Đặt con trỏ chuột ở cuối cell
                    textField.setCaretPosition(textField.getText().length());
                }

            } else {
                jButtonSua1.setText("Sửa");
                jButtonLuu1.setEnabled(false);
                jButtonXoa1.setEnabled(true);
                jTableCTPC.setEnabled(true);
                jTableCTPC.clearSelection();
                jTableCTPC.removeEditor();
                loadDsCTPC();
            }

        }
    }// GEN-LAST:event_jButtonSua1MouseReleased

    private void jButtonLuu1MouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonLuu1MouseReleased
        // TODO add your handling code here:
        if (jButtonLuu1.isEnabled()) {
            int row = jTableCTPC.getSelectedRow();
            try {
                System.out.println(jTableCTPC.getValueAt(row, 5).toString());
                int soLuongGiaoMoi = Integer.parseInt(jTableCTPC.getValueAt(row, 5).toString());                
                int soLuongGiaoCu = Integer.parseInt(jTableCTPC1.getValueAt(row, 5).toString());

                // int soLuongChuanBi = jTableCTPC.getValueAt(row, 4).toString()
                if (soLuongGiaoMoi <= 0)
                    throw new Exception("Vui lòng nhập số lượng là số tự nhiên > 0");

                String tenCD = jTableCTPC.getValueAt(row, 4).toString();
                String maSP = jTableCTPC.getValueAt(row, 3).toString();
                ArrayList<CongDoan> dsCD = DAO_CongDoan.getInstance().getAllByMaSP(maSP); 
                CongDoan cd = null;
                for (CongDoan congDoan : dsCD) {
                    if (congDoan.getTenCD().equals(tenCD)) {
                        cd = congDoan;
                        break;
                    }
                }
                if (cd == null)
                    throw new Exception("Vui lòng chọn công đoạn");
                if(soLuongGiaoMoi-soLuongGiaoCu > cd.getSoLuongChuanBi()) 
                    throw new Exception("Số lượng giao không được lớn hơn số lượng chuẩn bị");

                // yes, no option 
                int option = JOptionPane.showConfirmDialog(this, "Bạn đã chắc chắn chưa ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    DAO_ChiTietPhanCong.getInstance().updateSoLuongGiaoHomNayByMaCN(jTableCTPC.getValueAt(row, 0).toString(), soLuongGiaoMoi);
                    jTableCTPC.clearSelection();
                    jTableCTPC.removeEditor();
                    jButtonSua1.setText("Sửa");
                    jButtonLuu1.setEnabled(false);
                    jButtonXoa1.setEnabled(true);
                    jTableCTPC.setEnabled(true);
                    loadDsCTPC();
                    loadDsCongDoan();
                    loadDsCongNhan();
                } else {
                    Component editor = jTableCTPC.getEditorComponent();
                    if (editor instanceof JTextField) {
                        JTextField textField = (JTextField) editor;
                        textField.requestFocus();
                        textField.setCaretPosition(textField.getText().length());
                    }
                }

            } catch (Exception e) {
                // TODO: handle exception
                JOptionPane.showMessageDialog(this, e.getMessage());
                jTableCTPC.editCellAt(row, 5);
                Component editor = jTableCTPC.getEditorComponent();
                if (editor instanceof JTextField) {
                    JTextField textField = (JTextField) editor;
                    textField.setText("");
                    textField.requestFocus();
                    // Đặt con trỏ chuột ở cuối cell
                    textField.setCaretPosition(textField.getText().length());
                }
            }
        }
    }// GEN-LAST:event_jButtonLuu1MouseReleased

    private void jCheckBoxHoanThanhMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jCheckBoxHoanThanhMouseReleased
        // TODO add your handling code here:
        // if( jCheckBoxHoanThanh.isEnabled() )
        // jCheckBoxHoanThanh.setSelected(jCheckBoxHoanThanh.isSelected());
    }// GEN-LAST:event_jCheckBoxHoanThanhMouseReleased

    private void jToggleButtonSortMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jToggleButtonSortMouseReleased
        // change the icon of this button

        if (jToggleButtonSort.isSelected())
            jToggleButtonSort
                    .setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/icons8-sort-50.png")));
        else
            jToggleButtonSort
                    .setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/icons8-sort2-50.png")));
        loadDataJTableCongDoan();
    }// GEN-LAST:event_jToggleButtonSortMouseReleased
     // TODO add your handling code here:
     // TODO add your handling code here:

    private void jComboBoxCongDoanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxMaHopDong1ActionPerformed
        // TODO add your handling code here:
        if (jComboBoxCongDoan.isEnabled()) {
            loadDsCTPC();
            jComboBoxBoPhan1.setSelectedIndex(0);
        }

    }// GEN-LAST:event_jComboBoxMaHopDong1ActionPerformed

    private void jComboBoxSanPhamActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxMaHopDong2ActionPerformed
        // TODO add your handling code here:
        if (jComboBoxSanPham.isEnabled()) {
            loadDsCongDoan();
        }
    }// GEN-LAST:event_jComboBoxMaHopDong2ActionPerformed

    private void jButtonSua1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton9ActionPerformed

    private void jButtonPhanCongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton9ActionPerformed

    private void jButtonXoa1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton10ActionPerformed

    private void jButtonLuu1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton11ActionPerformed

    private void jComboBoxBoPhanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxMaHopDong3ActionPerformed
        // TODO add your handling code here:
        if (jComboBoxBoPhan.isEnabled()) {
            loadDsCongDoan();
            loadDsCongNhan();
            loadJcomboboxSanPham();
        }
    }// GEN-LAST:event_jComboBoxMaHopDong3ActionPerformed

    private void jComboBoxBoPhan2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxMaHopDong4ActionPerformed
        // TODO add your handling code here:
        if (jComboBoxCongDoan.isEnabled())
            loadDsCTPC();
    }// GEN-LAST:event_jComboBoxMaHopDong4ActionPerformed

    private void jComboBoxSapXep1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxSapXep1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBoxSapXep1ActionPerformed

    private void jTextFieldSLCBTTActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextFieldSLCBTTActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextFieldSLCBTTActionPerformed

    private void jComboBoxSapXepActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxSapXepActionPerformed
        // TODO add your handling code here:
        loadDataJTableCongDoan();
    }// GEN-LAST:event_jComboBoxSapXepActionPerformed

    private void jTextFieldMaCDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextFieldMaCDActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextFieldMaCDActionPerformed

    private void jComboBoxMaHopDongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxMaHopDongActionPerformed
        // TODO add your handling code here:
        if(jComboBoxMaHopDong.getItemCount()>0) {
            System.out.println("load sp");
            loadDsSanPham();
            jButtonTaoMoi.setEnabled(false);
        }
    }// GEN-LAST:event_jComboBoxMaHopDongActionPerformed

    private void jButtonThemCDTQActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonThemCDTQActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButtonThemCDTQActionPerformed

    private void jComboBoxCDTQActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxCDTQActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBoxCDTQActionPerformed

    private void jButtonXoaCDTQActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonXoaCDTQActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButtonXoaCDTQActionPerformed

    private void jTextFieldDonGiaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextFieldDonGiaActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextFieldDonGiaActionPerformed

    private void jComboBoxMaBoPhanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxMaBoPhanActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBoxMaBoPhanActionPerformed

    private void jButtonLuuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonLuuActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButtonLuuActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonXoaActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButtonXoaActionPerformed

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSuaActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButtonSuaActionPerformed

    private void jButtonTaoMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonTaoMoiActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButtonTaoMoiActionPerformed

    private void jCheckBoxHoanThanhActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jCheckBoxHoanThanhActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jCheckBoxHoanThanhActionPerformed

    private void jTableSanPhamMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTableSanPhamMouseReleased
        // get the data of col 0 and selected row
        if (jTableSanPham.isEnabled()) {
            int row = jTableSanPham.getSelectedRow();
            maSP = (String) jTableSanPham.getValueAt(row, 0);
            loadDataJTableCongDoan();
            loadDataJComboBoxCDTQ();
            String typeHD = jComboBoxLoaiHopDong.getSelectedItem().toString(); 
            if(typeHD.equals("chờ xác nhận"))
                jButtonTaoMoi.setEnabled(true);
            jButtonXoa.setEnabled(false);
            jButtonSua.setEnabled(false);
            jTableCongDoan.setEnabled(true);
            clearData();
        }
    }// GEN-LAST:event_jTableSanPhamMouseReleased

    private void loadDataJTableCongDoan() {
        dsCongDoan = DAO_CongDoan.getInstance().getAllByMaSP(maSP);
        DefaultTableModel model = (DefaultTableModel) jTableCongDoan.getModel();
        model.setRowCount(0);
        int sapXepTheo = jComboBoxSapXep.getSelectedIndex();
        boolean tangDan = !jToggleButtonSort.isSelected();
        // sap xep dsCongDoan
        // Sort dsCongDoan based on sapXepTheo and tangDan
        Collections.sort(dsCongDoan, new Comparator<CongDoan>() {
            @Override
            public int compare(CongDoan cd1, CongDoan cd2) {
                // Compare based on sapXepTheo and tangDan
                // Modify the comparison logic based on your requirements
                if (sapXepTheo == 0) {
                    // Sort by maCD
                    if (tangDan) {
                        return cd1.getMaCD().compareTo(cd2.getMaCD());
                    } else {
                        return cd2.getMaCD().compareTo(cd1.getMaCD());
                    }
                } else if (sapXepTheo == 1) {
                    // Sort by tenCD
                    if (tangDan) {
                        return cd1.getTenCD().compareTo(cd2.getTenCD());
                    } else {
                        return cd2.getTenCD().compareTo(cd1.getTenCD());
                    }
                } else if (sapXepTheo == 2) {
                    // Sort by donGia
                    if (tangDan) {
                        return Double.compare(cd1.getDonGia(), cd2.getDonGia());
                    } else {
                        return Double.compare(cd2.getDonGia(), cd1.getDonGia());
                    }
                } else if (sapXepTheo == 3) {
                    // sorting by maBP
                    if (tangDan) {
                        return cd1.getMaBP().compareTo(cd2.getMaBP());
                    } else {
                        return cd2.getMaBP().compareTo(cd1.getMaBP());
                    }
                } else if (sapXepTheo == 4) {
                    // sorting by maSP
                    if (tangDan) {
                        return cd1.getMaSP().compareTo(cd2.getMaSP());
                    } else {
                        return cd2.getMaSP().compareTo(cd1.getMaSP());
                    }
                } else if (sapXepTheo == 5) {
                    // sorting by trangThai
                    if (tangDan) {
                        return Boolean.compare(cd1.isTrangThai(), cd2.isTrangThai());
                    } else {
                        return Boolean.compare(cd2.isTrangThai(), cd1.isTrangThai());
                    }
                } else if (sapXepTheo == 6) {
                    // sorting by soLuongChuanBiToiThieu
                    if (tangDan) {
                        return Integer.compare(cd1.getSoLuongChuanBiToiThieu(), cd2.getSoLuongChuanBiToiThieu());
                    } else {
                        return Integer.compare(cd2.getSoLuongChuanBiToiThieu(), cd1.getSoLuongChuanBiToiThieu());
                    }
                } else if (sapXepTheo == 7) {
                    // sorting by soLuongChuanBi
                    if (tangDan) {
                        return Integer.compare(cd1.getSoLuongChuanBi(), cd2.getSoLuongChuanBi());
                    } else {
                        return Integer.compare(cd2.getSoLuongChuanBi(), cd1.getSoLuongChuanBi());
                    }
                } else if (sapXepTheo == 8) {
                    // sorting by soLuongHoanThanh
                    if (tangDan) {
                        return Integer.compare(cd1.getSoLuongHoanThanh(), cd2.getSoLuongHoanThanh());
                    } else {
                        return Integer.compare(cd2.getSoLuongHoanThanh(), cd1.getSoLuongHoanThanh());
                    }
                } else {
                    // Default sorting
                    if (tangDan) {
                        return cd1.getMaCD().compareTo(cd2.getMaCD());
                    } else {
                        return cd2.getMaCD().compareTo(cd1.getMaCD());
                    }
                }
            }
        });
        for (CongDoan cd : dsCongDoan) {
            model.addRow(new Object[] { cd.getMaCD(), cd.getTenCD(), cd.getDonGia(), cd.getMaBP(), cd.getMaSP(),
                    cd.isTrangThai(), cd.getSoLuongChuanBiToiThieu(), cd.getSoLuongChuanBi(),
                    cd.getSoLuongHoanThanh() });

        }

        model.fireTableDataChanged();
    }

    private void loadDataJTableCDTQ() {
        if (congDoan != null) {
            ArrayList<String> dsCDTQ = congDoan.getDsCDTQ();
            DefaultTableModel model = (DefaultTableModel) jTableCDTQ.getModel();
            model.setRowCount(0);
            for (String maCDTQ : dsCDTQ) {
                model.addRow(new Object[] { maCDTQ, DAO_CongDoan.getInstance().get(maCDTQ).getTenCD() });
            }
            model.fireTableDataChanged();
        }
    }

    private void loadDataJComboBoxCDTQ() {
        // Lấy ra công đoạn con cháu cua congDoan hien tai
        ArrayList<CongDoan> dsCha = new ArrayList<CongDoan>();
        ArrayList<CongDoan> dsCon = new ArrayList<CongDoan>();
        ArrayList<CongDoan> dsCDTQTrongTable = new ArrayList<CongDoan>();

        if (congDoan != null && !jButtonTaoMoi.getText().equals("Hủy")) {
            dsCha.add(congDoan);
            dsCon.add(congDoan);
            while (!dsCha.isEmpty()) {
                CongDoan cd = dsCha.get(0);
                dsCha.remove(0);
                for (String macdhq : DAO_CongDoan.getInstance().getDsCDHQ(cd.getMaCD())) {
                    CongDoan cdhq = DAO_CongDoan.getInstance().get(macdhq);
                    dsCon.add(cdhq);
                    dsCha.add(cdhq);
                }
            }
        }

        for (int i = 0; i < jTableCDTQ.getRowCount(); i++) {
            String maCD = (String) jTableCDTQ.getValueAt(i, 0);
            dsCDTQTrongTable.add(DAO_CongDoan.getInstance().get(maCD));
        }

        jComboBoxCDTQ.removeAllItems();
        for (CongDoan cd : dsCongDoan) {
            if (dsCDTQTrongTable.contains(cd) || dsCon.contains(cd))
                continue;
            jComboBoxCDTQ.addItem(cd.getMaCD().substring(cd.getMaCD().length() - 2) + ". " + cd.getTenCD());
        }

        if (jComboBoxCDTQ.getItemCount() > 0) {
            jComboBoxCDTQ.setSelectedIndex(0);
            jButtonThemCDTQ.setEnabled(jButtonLuu.isEnabled());
        } else {
            jButtonThemCDTQ.setEnabled(false);
        }

    }

    private void AfterSaveOrCancel() {
        if (!jButtonSua.isEnabled())
            clearData();
        jTableSanPham.setEnabled(true);
        jTableCongDoan.setEnabled(true);
        jComboBoxMaHopDong.setEnabled(true);
        jButtonTaoMoi.setEnabled(true);
        jButtonTaoMoi.setText("Tạo mới");
        jButtonSua.setText("Sửa");
        jButtonThemCDTQ.setEnabled(false);
        jButtonLuu.setEnabled(false);
        jButtonSua.setEnabled(false);
        jButtonXoaCDTQ.setEnabled(false);
        jTextFieldTenCD.setEditable(false);
        jTextFieldDonGia.setEditable(false);
        jComboBoxMaBoPhan.setEnabled(false);
        jTextFieldSLCBTT.setEditable(false);
        jComboBoxCDTQ.setEnabled(false);
        jCheckBoxHoanThanh.setEnabled(false);
        jTableCDTQ.setEnabled(false);
        loadDataJComboBoxCDTQ();
    }

    private void editting() {
        jButtonLuu.setEnabled(true);
        jButtonThemCDTQ.setEnabled(true);
        jTextFieldTenCD.setEditable(true);
        jTextFieldDonGia.setEditable(true);
        jComboBoxMaBoPhan.setEnabled(true);
        jTextFieldSLCBTT.setEditable(true);
        jComboBoxCDTQ.setEnabled(true);
        jTableCDTQ.setEnabled(true);
        jTableCongDoan.setEnabled(false);
        jTableSanPham.setEnabled(false);
    }

    private void jButtonTaoMoiMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonTaoMoiMouseReleased
        if (jButtonTaoMoi.isEnabled()) {
            if (!jButtonTaoMoi.getText().equals("Hủy")) {
                clearData();
                DAO_CongDoan dao = DAO_CongDoan.getInstance();
                String lastMaCD = dao.getLastMaCD(maSP);
                // create new MaCD from lastMaCD, Increase the last 2 characters by 1 unit
                // example xxxx01 to xxxx02, xxxx11 to xxxx12
                String newMaCD = lastMaCD.substring(0, lastMaCD.length() - 2);
                int lastTwoDigits = Integer.parseInt(lastMaCD.substring(lastMaCD.length() - 2));
                lastTwoDigits++;
                String paddedLastTwoDigits = String.format("%02d", lastTwoDigits);
                newMaCD = newMaCD + paddedLastTwoDigits;
                jTableSanPham.setEnabled(false);
                jTableCongDoan.setEnabled(false);
                jComboBoxMaHopDong.setEnabled(false);
                jTextFieldMaCD.setText(newMaCD);
                jButtonTaoMoi.setText("Hủy");
                jButtonSua.setEnabled(false);
                jButtonXoa.setEnabled(false);
                editting();
                loadDataJComboBoxCDTQ();
            }

            else {
                AfterSaveOrCancel();
            }
        }
    }// GEN-LAST:event_jButtonTaoMoiMouseReleased

    private void jButtonThemCDTQMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonThemCDTQMouseReleased
        // TODO add your handling code here:
        if (jButtonThemCDTQ.isEnabled()) {
            // click sẽ thêm công đoạn tiên quyết đã chọn ở jcombox vào bảng cdtq
            String selectedCongDoan = jComboBoxCDTQ.getSelectedItem().toString();
            DefaultTableModel model = (DefaultTableModel) jTableCDTQ.getModel();

            // hãy lấy 2 ký tự đầu tiên của selectedCongDoan
            String firstTwoCharacters = selectedCongDoan.substring(0, 2);
            // Tìm kiếm trong dsCongDoan xem CongDoan nào có maCD với 2 ký tự cuối giống với
            // 2 ký tự ở trên, thì thêm công đoạn này vào jTableCDTQ
            for (CongDoan congDoan : dsCongDoan) {
                String maCD = congDoan.getMaCD();
                if (maCD.substring(maCD.length() - 2).equals(firstTwoCharacters)) {
                    Object[] rowData = { congDoan.getMaCD(), congDoan.getTenCD() };
                    model.addRow(rowData);
                }
            }
            loadDataJComboBoxCDTQ();
        }
    }// GEN-LAST:event_jButtonThemCDTQMouseReleased

    private void jTableCDTQMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTableCDTQMouseReleased
        // TODO add your handling code here:
        if (jTableCDTQ.isEnabled()) {
            if (jTableCDTQ.getSelectedRowCount() >= 0)
                jButtonXoaCDTQ.setEnabled(true);
        }
    }// GEN-LAST:event_jTableCDTQMouseReleased

    private void jButtonXoaCDTQMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonXoaCDTQMouseReleased
        // TODO add your handling code here:
        if (jButtonXoaCDTQ.isEnabled()) {
            // getSeclect Row of jTableCDTQ
            // TODO add your handling code here: getSelected Row of jTableCDTQ
            int selectedRow = jTableCDTQ.getSelectedRow();
            if (selectedRow >= 0) {
                // delete this row from jTableCDTQ
                DefaultTableModel model = (DefaultTableModel) jTableCDTQ.getModel();
                model.removeRow(selectedRow);
                model.fireTableDataChanged();
                loadDataJComboBoxCDTQ();
            }
            jButtonXoaCDTQ.setEnabled(false);
        }
    }// GEN-LAST:event_jButtonXoaCDTQMouseReleased

    private void jButtonLuuMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonLuuMouseReleased
        // TODO add your handling code here:
        if (jButtonLuu.isEnabled()) {
            String maCD = "", maSP1 = "", maBP = "", tenCD = "";
            double donGia = 0;
            int soLuongChuanBiToiThieu = 0;
            boolean trangThai = false;
            try {
                maCD = jTextFieldMaCD.getText();
                maSP1 = maSP;
                maBP = jComboBoxMaBoPhan.getSelectedItem().toString();
                tenCD = jTextFieldTenCD.getText();
                if (tenCD.isBlank())
                    throw new Exception("Tên công đoạn không được để trống");
                try {
                    donGia = Double.parseDouble(jTextFieldDonGia.getText()) * 1000;
                } catch (Exception e) {
                    throw new Exception("Giá trị đơn giá phải là số không âm");
                }
                trangThai = jCheckBoxHoanThanh.isSelected();
                try {
                    soLuongChuanBiToiThieu = Integer.parseInt(jTextFieldSLCBTT.getText());
                    if (soLuongChuanBiToiThieu < 0)
                        throw new Exception("Giá trị số lượng phải là số không âm");
                } catch (Exception e) {
                    // TODO: handle exception
                    throw new Exception("Giá trị số lượng phải là số không âm");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn đã chắc chắn chưa ?", "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {

                ArrayList<String> dsCDTQ = new ArrayList<String>();
                for (int i = 0; i < jTableCDTQ.getRowCount(); i++) {
                    String maCDTQ = (String) jTableCDTQ.getValueAt(i, 0);
                    dsCDTQ.add(maCDTQ);
                }
                CongDoan congDoan = new CongDoan(maCD, maSP1, maBP, tenCD, donGia, trangThai, soLuongChuanBiToiThieu,
                        dsCDTQ);
                DAO_CongDoan dao = DAO_CongDoan.getInstance();
                if (jButtonTaoMoi.getText().equals("Hủy"))
                    dao.insert(congDoan);
                else
                    dao.update(congDoan);
                // update the JComboBox for dsCongDoanTienQuyet
                loadDataJTableCongDoan();
                loadDataJComboBoxCDTQ();
                AfterSaveOrCancel();
                loadDsCongDoan();
            }
        }
    }// GEN-LAST:event_jButtonLuuMouseReleased

    private void jTextFieldTenCDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextFieldTenCDActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextFieldTenCDActionPerformed

    private void jTableCongDoanMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTableCongDoanMouseReleased
        if (jTableCongDoan.isEnabled()) {
            if (jTableCongDoan.getSelectedRowCount() > 0) {
                jButtonXoa.setEnabled(true);
                jButtonSua.setEnabled(true);

                int selectedRow = jTableCongDoan.getSelectedRow();
                String maCD = (String) jTableCongDoan.getValueAt(selectedRow, 0);
                congDoan = DAO_CongDoan.getInstance().get(maCD);
                jTextFieldMaCD.setText(congDoan.getMaCD());
                jComboBoxMaBoPhan.setSelectedItem(congDoan.getMaBP());
                jTextFieldTenCD.setText(congDoan.getTenCD());
                jTextFieldDonGia.setText(String.format("%.0f", congDoan.getDonGia() / 1000));
                // String.format("%.0f", congDoan.getDonGia()/1000);
                jCheckBoxHoanThanh.setSelected(congDoan.isTrangThai());
                jTextFieldSLCBTT.setText(String.valueOf(congDoan.getSoLuongChuanBiToiThieu()));
                // loadDataJTableCongDoanTienQuyet(maCD);
                loadDataJTableCDTQ();
                loadDataJComboBoxCDTQ();
            }
        }
    }// GEN-LAST:event_jTableCongDoanMouseReleased

    private void jButtonXoaMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonXoaMouseReleased
        // TODO add your handling code here:
        if (jButtonXoa.isEnabled()) {
            int selectedRow = jTableCongDoan.getSelectedRow();
            if (selectedRow != -1) {
                String maCD = jTableCongDoan.getValueAt(selectedRow, 0).toString();
                CongDoan cd = DAO_CongDoan.getInstance().get(maCD);
                JFrame confirmationFrame = new JFrame();
                String message = "Bạn chắc chắn muốn xóa công đoạn " + cd.getMaCD() + " ?";
                int option = JOptionPane.showConfirmDialog(confirmationFrame, message, "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    // Delete the CongDoan and update the table
                    DAO_CongDoan.getInstance().deleteById(maCD);
                    loadDataJTableCongDoan();
                    AfterSaveOrCancel();
                    jButtonXoa.setEnabled(false);
                    jButtonSua.setEnabled(false);
                    jButtonTaoMoi.setEnabled(true);
                    clearData();
                }
            }
        }
    }// GEN-LAST:event_jButtonXoaMouseReleased

    private void jButtonSuaMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButtonSuaMouseReleased
        // TODO add your handling code here:
        if (jButtonSua.isEnabled()) {
            if (!jButtonSua.getText().equals("Hủy")) {
                jButtonSua.setText("Hủy");
                jButtonXoa.setEnabled(false);
                jButtonLuu.setEnabled(true);
                jCheckBoxHoanThanh.setEnabled(true);
                jButtonTaoMoi.setEnabled(false);
                editting();
            } else {
                jButtonSua.setText("Sửa");
                AfterSaveOrCancel();
                jButtonXoa.setEnabled(true);
                jButtonSua.setEnabled(true);
                jButtonLuu.setEnabled(false);
                jButtonTaoMoi.setEnabled(true);
            }
        }
    }// GEN-LAST:event_jButtonSuaMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLuu;
    private javax.swing.JButton jButtonLuu1;
    private javax.swing.JButton jButtonLuu2;
    private javax.swing.JButton jButtonNhapSoLuong;
    private javax.swing.JButton jButtonPhanCong;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonSua1;
    private javax.swing.JButton jButtonTaoMoi;
    private javax.swing.JButton jButtonThemCDTQ;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JButton jButtonXoa1;
    private javax.swing.JButton jButtonXoaCDTQ;
    private javax.swing.JCheckBox jCheckBoxHoanThanh;
    private javax.swing.JComboBox<String> jComboBoxBoPhan;
    private javax.swing.JComboBox<String> jComboBoxBoPhan1;
    private javax.swing.JComboBox<String> jComboBoxCDTQ;
    private javax.swing.JComboBox<String> jComboBoxCongDoan;
    private javax.swing.JComboBox<String> jComboBoxLoaiHopDong;
    private javax.swing.JComboBox<String> jComboBoxMaBoPhan;
    private javax.swing.JComboBox<String> jComboBoxMaHopDong;
    private javax.swing.JComboBox<String> jComboBoxSanPham;
    private javax.swing.JComboBox<String> jComboBoxSapXep;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableCDTQ;
    private javax.swing.JTable jTableCTPC;
    private javax.swing.JTable jTableCTPC1;
    private javax.swing.JTable jTableCongDoan;
    private javax.swing.JTable jTableCongDoan1;
    private javax.swing.JTable jTableCongNhan;
    private javax.swing.JTable jTableSanPham;
    private javax.swing.JTextField jTextFieldDonGia;
    private javax.swing.JTextField jTextFieldMaCD;
    private javax.swing.JTextField jTextFieldSLCBTT;
    private javax.swing.JTextField jTextFieldSoLuong;
    private javax.swing.JTextField jTextFieldTenCD;
    private javax.swing.JToggleButton jToggleButtonSort;
    // End of variables declaration//GEN-END:variables
}
