package ptud.DAO;

import java.util.ArrayList;
import ptud.DAO.DAOInterface;
import ptud.Entity.NhanVien;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import ptud.Connection.ConnectDB;
import ptud.Entity.BoPhan;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author TomTom
 */
public class DAO_NhanVien implements DAOInterface<NhanVien> {

    public static DAO_NhanVien getInstance() {
        return new DAO_NhanVien();
    }

    @Override
    public NhanVien get(String id) {
        NhanVien nhanVien = null;
        try {
            ConnectDB.getInstance();
            java.sql.Connection connection = ConnectDB.getConnection();

            String query = "SELECT * FROM NhanVien WHERE maNV = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                BoPhan boPhan = new DAO_BoPhan().get(resultSet.getString("maBP"));
                String ten = resultSet.getString("tenNV");
                boolean gioiTinh = resultSet.getBoolean("gioiTinh");
                LocalDate ngaySinh = resultSet.getDate("ngaySinh").toLocalDate();
                LocalDate ngayBatDauLam = resultSet.getDate("ngayBatDauLam").toLocalDate();
                String cccd = resultSet.getString("CCCD");
                String dienThoai = resultSet.getString("dienThoai");
                boolean trangThai = resultSet.getBoolean("trangThai");
                byte[] avatar = resultSet.getBytes("hinhAnh");
                double luongCoBan = resultSet.getDouble("luongCoBan");
                double phuCap = resultSet.getDouble("phuCap");
                nhanVien = new NhanVien(id, boPhan, ten, gioiTinh,
                        ngaySinh, ngayBatDauLam, cccd, dienThoai, trangThai,
                        avatar, luongCoBan, phuCap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nhanVien;
    }

    @Override
    public ArrayList<NhanVien> getAll() {
        ArrayList<NhanVien> dsNhanVien = new ArrayList<NhanVien>();
        try {
            ConnectDB.getInstance();
            java.sql.Connection connection = ConnectDB.getConnection();

            String query = "SELECT * FROM NhanVien WHERE trangThai = 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maNV = resultSet.getString("maNV");
                BoPhan boPhan = new DAO_BoPhan().get(resultSet.getString("maBP"));
                String ten = resultSet.getString("tenNV");
                boolean gioiTinh = resultSet.getBoolean("gioiTinh");
                LocalDate ngaySinh = resultSet.getDate("ngaySinh").toLocalDate();
                LocalDate ngayBatDauLam = resultSet.getDate("ngayBatDauLam").toLocalDate();
                String cccd = resultSet.getString("CCCD");
                String dienThoai = resultSet.getString("dienThoai");
                boolean trangThai = resultSet.getBoolean("trangThai");
                byte[] avatar = resultSet.getBytes("hinhAnh");
                double luongCoBan = resultSet.getDouble("luongCoBan");
                double phuCap = resultSet.getDouble("phuCap");
                NhanVien nhanVien = new NhanVien(maNV, boPhan, ten, gioiTinh,
                        ngaySinh, ngayBatDauLam, cccd, dienThoai, trangThai,
                        avatar, luongCoBan, phuCap);

                dsNhanVien.add(nhanVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNhanVien;
    }

    @Override
    public boolean insert(NhanVien nhanVien) {
        ConnectDB.getInstance();
        java.sql.Connection connection = ConnectDB.getConnection();
        try {
            String sql = "INSERT INTO NhanVien\n"
                    + "           ([maNV]\n"
                    + "           ,[maBP]\n"
                    + "           ,[tenNV]\n"
                    + "           ,[gioiTinh]\n"
                    + "           ,[ngaySinh]\n"
                    + "           ,[ngayBatDauLam]\n"
                    + "           ,[CCCD]\n"
                    + "           ,[luongCoBan]\n"
                    + "           ,[phuCap]\n"
                    + "           ,[trangThai]\n"
                    + "           ,[dienThoai]\n"
                    + "           ,[hinhAnh])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nhanVien.getMaNV());
            statement.setString(2, nhanVien.getBoPhan().getMaBP());
            statement.setString(3, nhanVien.getTen());
            statement.setBoolean(4, nhanVien.isGioiTinh());
            statement.setDate(5, java.sql.Date.valueOf(nhanVien.getNgaySinh()));
            statement.setDate(6, java.sql.Date.valueOf(nhanVien.getNgayBatDauLam()));
            statement.setString(7, nhanVien.getCccd());
            statement.setDouble(8, nhanVien.getLuongCoBan());
            statement.setDouble(9, nhanVien.getPhuCap());
            statement.setBoolean(10, nhanVien.isTrangThai());
            statement.setString(11, nhanVien.getDienThoai());
            statement.setBytes(12, nhanVien.getAvatar());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(NhanVien nhanVien) {

        try {
            ConnectDB.getInstance();
            java.sql.Connection connection = ConnectDB.getConnection();
            String sql = "UPDATE [dbo].[NhanVien]\n"
                    + "   SET [maBP] = ?\n"
                    + "      ,[tenNV] = ?\n"
                    + "      ,[gioiTinh] = ?\n"
                    + "      ,[ngaySinh] = ?\n"
                    + "      ,[ngayBatDauLam] = ?\n"
                    + "      ,[CCCD] = ?\n"
                    + "      ,[luongCoBan] = ?\n"
                    + "      ,[phuCap] = ?\n"
                    + "      ,[trangThai] = ?\n"
                    + "      ,[dienThoai] = ?\n"
                    + "      ,[hinhAnh] = ?\n"
                    + " WHERE [maNV] = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nhanVien.getBoPhan().getMaBP());
            statement.setString(2, nhanVien.getTen());
            statement.setBoolean(3, nhanVien.isGioiTinh());
            statement.setDate(4, java.sql.Date.valueOf(nhanVien.getNgaySinh()));
            statement.setDate(5, java.sql.Date.valueOf(nhanVien.getNgayBatDauLam()));
            statement.setString(6, nhanVien.getCccd());
            statement.setDouble(7, nhanVien.getLuongCoBan());
            statement.setDouble(8, nhanVien.getPhuCap());
            statement.setBoolean(9, nhanVien.isTrangThai());
            statement.setString(10, nhanVien.getDienThoai());
            statement.setBytes(11, nhanVien.getAvatar());
            statement.setString(12, nhanVien.getMaNV());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        try {
            ConnectDB.getInstance();
            java.sql.Connection connection = ConnectDB.getConnection();
            String sql = "UPDATE [dbo].[NhanVien]\n"
                    + "   SET [trangThai] = ?\n"
                    + " WHERE [maNV] = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, false);
            statement.setString(2, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    đếm số lượng nhân viên
    public int countAll() {
        int count = 0;
        try {
            ConnectDB.getInstance();
            java.sql.Connection connection = ConnectDB.getConnection();

            String query = "SELECT COUNT(*) AS total FROM NhanVien";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
