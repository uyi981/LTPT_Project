/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.DAO;

import java.util.ArrayList;
import ptud.Entity.ChiTietPhanCong;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ptud.Mang.connection;

/**
 *
 * @author KHANH PC
 */
public class DAO_ChiTietPhanCong implements DAOInterface<ChiTietPhanCong> {

    public static DAO_ChiTietPhanCong getInstance() {
        return new DAO_ChiTietPhanCong();
    }

    @Override
    public ChiTietPhanCong get(String id) {
        // let code to get ChiTietPhanCong t from database
        ChiTietPhanCong t = null;
        try {
            String query = "SELECT * FROM ChiTietPhanCong WHERE maCTPC = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String maCTPC = resultSet.getString("maCTPC");
                String maCD = resultSet.getString("maCD");
                String maCN = resultSet.getString("maCN");
                LocalDate ngay = resultSet.getDate("ngay").toLocalDate();
                int soLuongCDGiao = resultSet.getInt("soLuongCDGiao");
                t = new ChiTietPhanCong(maCTPC, maCD, maCN, ngay, soLuongCDGiao);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return t;

    }

    @Override
    public ArrayList<ChiTietPhanCong> getAll() {
        // let code to get all ChiTietPhanCong t from database
        ArrayList<ChiTietPhanCong> chiTietPhanCongs = new ArrayList<>();
        try {
            String query = "SELECT * FROM ChiTietPhanCong";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maCTPC = resultSet.getString("maCTPC");
                String maCD = resultSet.getString("maCD");
                String maCN = resultSet.getString("maCN");
                LocalDate ngay = resultSet.getDate("ngay").toLocalDate();
                int soLuongCDGiao = resultSet.getInt("soLuongCDGiao");

                ChiTietPhanCong chiTietPhanCong = new ChiTietPhanCong(maCTPC, maCD, maCN, ngay, soLuongCDGiao);
                chiTietPhanCongs.add(chiTietPhanCong);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chiTietPhanCongs;
    }

    public ArrayList<ChiTietPhanCong> getAllByNgay(LocalDate ngay) {

        ArrayList<ChiTietPhanCong> chiTietPhanCongs = new ArrayList<>();
        try {
            String query = "SELECT * FROM ChiTietPhanCong WHERE ngay = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(ngay));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maCTPC = resultSet.getString("maCTPC");
                String maCD = resultSet.getString("maCD");
                String maCN = resultSet.getString("maCN");
                LocalDate ngay1 = resultSet.getDate("ngay").toLocalDate();
                int soLuongCDGiao = resultSet.getInt("soLuongCDGiao");

                ChiTietPhanCong chiTietPhanCong = new ChiTietPhanCong(maCTPC, maCD, maCN, ngay1, soLuongCDGiao);
                chiTietPhanCongs.add(chiTietPhanCong);
            }

            resultSet.close();
            statement.close();
            return chiTietPhanCongs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<ChiTietPhanCong>();
    }

    @Override
    public boolean insert(ChiTietPhanCong t) {
        // let code to get insert ChiTietPhanCong t from database
        try {
            String query = "INSERT INTO ChiTietPhanCong (maCTPC, maCD, maCN, ngay, soLuongCDGiao) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, t.getMaCTPC());
            statement.setString(2, t.getMaCD());
            statement.setString(3, t.getMaCN());
            statement.setDate(4, Date.valueOf(t.getNgay()));
            statement.setInt(5, t.getSoLuongCDGiao());

            int rowsAffected = statement.executeUpdate();
            statement.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(ChiTietPhanCong t) {
        // let code to get update ChiTietPhanCong t from database
        try {
            String query = "UPDATE ChiTietPhanCong SET maCD = ?, maCN = ?, ngay = ?, soLuongCDGiao = ? WHERE maCTPC = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, t.getMaCD());
            statement.setString(2, t.getMaCN());
            statement.setDate(3, Date.valueOf(t.getNgay()));
            statement.setInt(4, t.getSoLuongCDGiao());
            statement.setString(5, t.getMaCTPC());
            int rowsAffected = statement.executeUpdate();
            statement.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteById(String id) {
        // let code to get delete ChiTietPhanCong t from database
        try {
            String query = "DELETE FROM ChiTietPhanCong WHERE maCTPC = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, id);

            int rowsAffected = statement.executeUpdate();
            statement.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateChoPhanCong(String maCN, boolean trangThai) {
        String updateQuery = "UPDATE CongNhan SET choPhanCong = ? WHERE maCN = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setBoolean(1, trangThai);
            pstmt.setString(2, maCN);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSoLuongCongDoanDuocGiaoHomNay(String maCD) {
        String query = "SELECT SUM(soLuongCDGiao) FROM ChiTietPhanCong WHERE maCD = ? AND ngay = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, maCD);
            pstmt.setDate(2, Date.valueOf(LocalDate.now()));
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getSoLuongCongDoanGiaoByMaCongNHan(String maCN) {
        try {
            LocalDate ngayHomNay = LocalDate.now();

            String chuoiNgayHomNay = ngayHomNay.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
            String query = "select soLuongCDGiao\n"
                    + "from ChiTietPhanCong\n"
                    + "where maCN = ? and maCTPC = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maCN);
            statement.setString(2, chuoiNgayHomNay + maCN);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("soLuongCDGiao");
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ChiTietPhanCong.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void updateSoLuongGiaoHomNayByMaCN(String maCN, int soLuong) {
        try {
            String query = "UPDATE ChiTietPhanCong SET soLuongCDGiao = ? WHERE maCN = ? AND ngay = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, soLuong);
            statement.setString(2, maCN);
            statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ChiTietPhanCong.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteHomNayByMaCN(String maCN) {
        try {
            String query = "DELETE FROM ChiTietPhanCong WHERE maCN = ? AND ngay = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maCN);
            statement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ChiTietPhanCong.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getMaChiTietPhanCongBymaCN(String maCN) {
        try {
            String query = "select maCTPC\n"
                    + "from ChiTietPhanCong\n"
                    + "where maCN = ? and ngay = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maCN);
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
            statement.setString(2, today);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("maCTPC");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ChiTietPhanCong.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "-1";
    }

    public static String getMaCongDoanBymaCTPC(String maCTPC) {
        try {
            String query = "select maCD\n"
                    + "from [dbo].[ChiTietPhanCong]\n"
                    + "where maCTPC = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maCTPC);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("maCD");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ChiTietPhanCong.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "-1";
    }
    public int getSoLuongCongDoanHoanThanhByMaCongNhan(String maCN, LocalDate date) {
        try {
            String fotmatNgay = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String query = "select sum(p.soLuongCD + p.soLuongCDTangCa) as soLuong\n" +
                    "from [dbo].[PhieuChamCongCongNhan] p\n" +
                    "join [dbo].[ChiTietPhanCong] c on p.maCTPC = c.maCTPC\n" +
                    "where c.maCN = ? and p.ngayChamCong = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maCN);
            statement.setString(2, fotmatNgay);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("soLuong");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ChiTietPhanCong.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}

   
