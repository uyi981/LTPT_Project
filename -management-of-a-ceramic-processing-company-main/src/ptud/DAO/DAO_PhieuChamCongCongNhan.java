/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ptud.Connection.ConnectDB;
import ptud.Entity.ChamCongDTO;
import ptud.Entity.PhieuChamCongCongNhan;
import ptud.Entity.PhieuLuongCongNhan;

import static ptud.Mang.connection;

/**
 *
 * @author TranLoc
 */
public class DAO_PhieuChamCongCongNhan {

    public static DAO_PhieuChamCongCongNhan getInstance() {
        return new DAO_PhieuChamCongCongNhan();
    }

    public static String formatChuoi(int thang, int nam, String idCN) {
        String thangStr = String.format("%02d", thang);
        String namStr = String.format("%04d", nam);
        return thangStr + namStr + idCN;
    }
    
    public static String formatID_PCCCN(LocalDate date, String idCN) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("ddMMyy"));
        return formattedDate + idCN;
    }

    public boolean insert(PhieuChamCongCongNhan PCCCN) throws SQLException {
        String query = "INSERT INTO [dbo].[PhieuChamCongCongNhan]\n"
                + "           ([maPCCCN]\n"
                + "           ,[maCTPC]\n"
                + "           ,[ngayChamCong]\n"
                + "           ,[vang]\n"
                + "           ,[soLuongCD]\n"
                + "           ,[soLuongCDTangCa]\n"
                + "           ,[noiDungPhat]\n"
                + "           ,[tienCong]\n"
                + "           ,[tienPhat]\n"
                + "           ,[tienThuong])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, PCCCN.getMaPCCCN());
        statement.setString(2, PCCCN.getMaCTPC());
        statement.setDate(3, Date.valueOf(PCCCN.getNgay()));
        statement.setBoolean(4, PCCCN.isVang());
        statement.setInt(5, PCCCN.getSoLuongSanPham());
        statement.setInt(6, PCCCN.getSoLuongSanPhamTangCa());
        statement.setString(7, PCCCN.getNoiDungPhat());
        statement.setFloat(8, (float) PCCCN.getTienCong());
        statement.setFloat(9, (float) PCCCN.getTienPhat());
        statement.setFloat(10, (float) PCCCN.getTienThuong());
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return true;
        }
        return false;
    }

    public int getSoNgayLam(String idCN, int thang, int nam) throws SQLException {
        String query = "select count(*) as soLuong from [dbo].[PhieuChamCongCongNhan] where maPCCCN like ? and vang = 0";
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        statement.setString(1, "%" + formatChuoi(thang, nam, idCN));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("soLuong");
        }
        return 0;
    }

    public float getTongTienPhatTrongThang(String idCN, int thang, int nam) throws SQLException {
        String query = "select sum(tienPhat) as tongTienPhat\n"
                + "from [dbo].[PhieuChamCongCongNhan]\n"
                + "where maPCCCN like ?";
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        statement.setString(1, "%" + formatChuoi(thang, nam, idCN));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getFloat("tongTienPhat");
        }
        return 0;
    }

    public float getTongTienThuongTrongThang(String idCN, int thang, int nam) throws SQLException {
        String query = "select sum(tienThuong) as tongTienThuong\n"
                + "from [dbo].[PhieuChamCongCongNhan]\n"
                + "where maPCCCN like ?";
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        statement.setString(1, "%" + formatChuoi(thang, nam, idCN));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getFloat("tongTienThuong");
        }
        return 0;
    }

    public float getTongTienCongKhongTangCaTrongThang(String idCN, int thang, int nam) throws SQLException {
        String query = "select sum(soLuongCD * donGia)\n as tongTien\n"
                + "from [dbo].[PhieuChamCongCongNhan] p\n"
                + "join [dbo].[ChiTietPhanCong] c on p.maCTPC = c.maCTPC\n"
                + "join [dbo].[CongDoan] cd on c.maCD = cd.maCD\n"
                + "where maPCCCN like ?";
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        statement.setString(1, "%" + formatChuoi(thang, nam, idCN));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getFloat("tongTien");
        }
        return 0;
    }
 
    public float getTongTienCongTangCaTrongThang(String idCN, int thang, int nam) throws SQLException {
        String query = "select sum(soLuongCDTangCa * donGia * 1.2) as tongTien\n"
                + "from [dbo].[PhieuChamCongCongNhan] p\n"
                + "join [dbo].[ChiTietPhanCong] c on p.maCTPC = c.maCTPC\n"
                + "join [dbo].[CongDoan] cd on c.maCD = cd.maCD\n"
                + "where maPCCCN like ?";
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        statement.setString(1, "%" + formatChuoi(thang, nam, idCN));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getFloat("tongTien");
        }
        return 0;
    }

    public float getTongTienCongTrongThang(String idCN, int thang, int nam) throws SQLException {
        return getTongTienCongKhongTangCaTrongThang(idCN, thang, nam)
                + getTongTienCongTangCaTrongThang(idCN, thang, nam);
    }

    public float getTienLuongTrongThang(String idCN, int thang, int nam) throws SQLException {
        return getTongTienCongTrongThang(idCN, thang, nam)
                + getTongTienThuongTrongThang(idCN, thang, nam)
                - getTongTienPhatTrongThang(idCN, thang, nam);
    }

    public static String getCurrentDateYYYYMMDD() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }

    public static ArrayList<Object[]> getDanhSachThongTinChamCongByIDBoPhan(String maBoPhan) {
        ArrayList<Object[]> ds = new ArrayList<Object[]>();
        try {
            String query = "select cn.maCN, cn.tenCN, cd.tenCD, c.soLuongCDGiao\n"
                    + "from [dbo].[ChiTietPhanCong] c\n"
                    + "join [dbo].[CongNhan] cn on c.maCN = cn.maCN\n"
                    + "join [dbo].[CongDoan] cd on c.maCD = cd.maCD\n"
                    + "left join PhieuChamCongCongNhan p on c.maCTPC = p.maCTPC\n"
                    + "where cd.maBP = ? and ngay = ? and c.maCTPC not in (select p.maCTPC from [dbo].[PhieuChamCongCongNhan] p)\n";
            PreparedStatement statement;
            statement = connection.prepareStatement(query);
            statement.setString(1, maBoPhan);
            statement.setString(2, getCurrentDateYYYYMMDD());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Object[] thongTinChamCong = new Object[10];
                thongTinChamCong[0] = resultSet.getString("maCN");
                thongTinChamCong[1] = resultSet.getString("tenCN");
                thongTinChamCong[2] = false;
                thongTinChamCong[3] = resultSet.getString("tenCD");
                thongTinChamCong[4] = resultSet.getInt("soLuongCDGiao");
                thongTinChamCong[5] = thongTinChamCong[4];
                thongTinChamCong[6] = 0;
                thongTinChamCong[7] = 0;
                thongTinChamCong[8] = 0;
                thongTinChamCong[9] = "";
                ds.add(thongTinChamCong);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO_PhieuChamCongCongNhan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

    public static Object[] getThongTinChamCongByMaCongNhan(String maCN) {
        Object[] thongTinChamCong = new Object[5];
        try {
            String query = "select cd.maBP, cn.maCN, cn.tenCN, cd.tenCD, c.soLuongCDGiao\n"
                    + "from [dbo].[ChiTietPhanCong] c\n"
                    + "join [dbo].[CongNhan] cn on c.maCN = cn.maCN\n"
                    + "join [dbo].[CongDoan] cd on c.maCD = cd.maCD\n"
                    + "where cn.maCN = ? and ngay = ? and c.maCTPC not in (select p.maCTPC from [dbo].[PhieuChamCongCongNhan] p)\n";
            PreparedStatement statement;
            statement = connection.prepareStatement(query);
            statement.setString(1, maCN);
            statement.setString(2, getCurrentDateYYYYMMDD());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                thongTinChamCong[0] = resultSet.getString("maBP");
                thongTinChamCong[1] = resultSet.getString("maCN");
                thongTinChamCong[2] = resultSet.getString("tenCN");
                thongTinChamCong[3] = resultSet.getString("tenCD");
                thongTinChamCong[4] = resultSet.getInt("soLuongCDGiao");

            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO_PhieuChamCongCongNhan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return thongTinChamCong;
    }

    public ArrayList<ChamCongDTO> getDsCongDoanLamDuocCuaCongNhanTrongThang( String maCN, int thang, int nam ) {
        // CREATE TABLE PhieuChamCongCongNhan (
        //     maPCCCN VARCHAR(50) PRIMARY KEY,
        //     maCTPC VARCHAR(50), 
        //     ngayChamCong DATE,
        //     vang BIT,
        //     soLuongCD INT,
        //     soLuongCDTangCa INT,	
        //     noiDungPhat NVARCHAR(255),
        //     tienCong FLOAT(10),
        //     tienPhat FLOAT(10),
        //     tienThuong FLOAT(10),
        //     FOREIGN KEY (maCTPC) REFERENCES ChiTietPhanCong(maCTPC)
        // );
        // CREATE TABLE ChiTietPhanCong (
        //     maCTPC VARCHAR(50),
        //     maCD VARCHAR(50),
        //     maCN VARCHAR(50),
        //     ngay DATE,
        //     soLuongCDGiao INT,
        //     PRIMARY KEY (maCTPC),
        //     FOREIGN KEY (maCD) REFERENCES CongDoan(maCD),
        //     FOREIGN KEY (maCN) REFERENCES CongNhan(maCN)
        // );
       
        // từ 2 bảng trên code DAO để trả về dữ liệu như sau
        // maCN  tương ứng với ChiTietPhanCong[maCN], thang và nam tương ứng với PhieuChamCongCongNhan[ngayChamCong]
        // trả về dữ liệu gồm danh sách gồm: maCD, TongsoLuongCD là tổng soLuongCD, TongSoLuongCDTangCa là tông soLuongCDTangCa
        // hãy tạo kiểu dữ liệu java để lưu trữ dữ liệu trên
       
        ArrayList<ChamCongDTO> ds = new ArrayList<>();
        try {
            String query = "select cn.maCD, SUM(c.soLuongCD) as TongSoLuongCD, SUM(c.soLuongCDTangCa) as TongSoLuongCDTangCa\n"
                    + "from [dbo].[PhieuChamCongCongNhan] c\n"
                    + "join [dbo].[ChiTietPhanCong] cn on c.maCTPC = cn.maCTPC\n"
                    + "where cn.maCN = ? and MONTH(ngay) = ? and YEAR(ngay) = ?\n"
                    + "group by cn.maCD";
            PreparedStatement statement;
            statement = connection.prepareStatement(query);
            statement.setString(1, maCN);
            statement.setInt(2, thang);
            statement.setInt(3, nam);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("haha2");
            while (resultSet.next()) {
                ChamCongDTO thongTinChamCong = new ChamCongDTO();
                thongTinChamCong.setMaCD(resultSet.getString("maCD"));
                thongTinChamCong.setTongSoLuongCD(resultSet.getInt("TongSoLuongCD"));
                thongTinChamCong.setTongSoLuongCDTangCa(resultSet.getInt("TongSoLuongCDTangCa"));
                ds.add(thongTinChamCong);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO_PhieuChamCongCongNhan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }
}
