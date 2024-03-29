/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this licenseOverride
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ptud.Entity.PhieuLuongCongNhan;
import ptud.Entity.PhieuLuongNhanVien;

import static ptud.Mang.connection;

/**
 *
 * @author KHANH PC
 */
public class DAO_PhieuLuongCongNhan implements DAOInterface<PhieuLuongCongNhan>{

    public static DAO_PhieuLuongCongNhan getInstance() {
        return new DAO_PhieuLuongCongNhan();
    }
    @Override
    public PhieuLuongCongNhan get(String id) {
        // let code to get PhieuLuongCongNhan t from database
        try {
            String query = "SELECT * FROM PhieuLuongCongNhan WHERE maPL = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                String maPL = resultSet.getString("maPL");
                int thang = resultSet.getInt("thang");
                int nam = resultSet.getInt("nam");
                String maCN = resultSet.getString("maCN");
                double luong = resultSet.getDouble("luong");
                // double thuong = resultSet.getDouble("thuong");
                // double phat = resultSet.getDouble("phat");
                // int soNgayLam = resultSet.getInt("soNgayLam");
                // double luongThucNhan = resultSet.getDouble("luongThucNhan");
                
                PhieuLuongCongNhan phieuLuongCongNhan = new PhieuLuongCongNhan(maPL, thang, nam, maCN, luong);
                return phieuLuongCongNhan;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<PhieuLuongCongNhan> getAll() {
        // let code to get all PhieuLuongCongNhan from database
        ArrayList<PhieuLuongCongNhan> phieuLuongCongNhans = new ArrayList<>();
        try {
            String query = "SELECT * FROM PhieuLuongCongNhan";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String maPL = resultSet.getString("maPL");
                int thang = resultSet.getInt("thang");
                int nam = resultSet.getInt("nam");
                String maCN = resultSet.getString("maCN");
                double luong = resultSet.getDouble("luong");
                // double thuong = resultSet.getDouble("thuong");
                // double phat = resultSet.getDouble("phat");
                // int soNgayLam = resultSet.getInt("soNgayLam");
                // double luongThucNhan = resultSet.getDouble("luongThucNhan");
                
                PhieuLuongCongNhan phieuLuongCongNhan = new PhieuLuongCongNhan(maPL, thang, nam, maCN, luong);
                phieuLuongCongNhans.add(phieuLuongCongNhan);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return phieuLuongCongNhans;
    }

    @Override
    public boolean insert(PhieuLuongCongNhan t) {
        // let code to insert PhieuLuongCongNhan t to database sqlserver
        try {
            // Create a PreparedStatement to insert the data  
            String query = "INSERT INTO PhieuLuongCongNhan (maPL, thang, nam, maCN, luong, thuong, phat, soNgayLam, luongThucNhan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Set the values of the parameters
            statement.setString(1, t.getMaPL());
            statement.setInt(2, t.getThang());
            statement.setInt(3, t.getNam());
            statement.setString(4, t.getMaCN());
            statement.setDouble(5, t.getLuong());
            statement.setDouble(6, t.getThuong());
            statement.setDouble(7, t.getPhat());
            statement.setInt(8, t.getSoNgayLam());
            statement.setDouble(9, t.getLuongThucNhan());
            
            // Execute the query
            int rowsAffected = statement.executeUpdate();
          
            // Check if the insert was successful
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
        
    }

    @Override
    public boolean update(PhieuLuongCongNhan t) {
        // let code to update PhieuLuongCongNhan t to database sqlserver
        try {
            // Create a PreparedStatement to update the data  
            String query = "UPDATE PhieuLuongCongNhan SET thang = ?, nam = ?, maCN = ?, luong = ?, thuong = ?, phat = ?, soNgayLam = ?, luongThucNhan = ?  WHERE maPL = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Set the values of the parameters
            statement.setInt(1, t.getThang());
            statement.setInt(2, t.getNam());
            statement.setString(3, t.getMaCN());
            statement.setDouble(4, t.getLuong());
            statement.setDouble(5, t.getThuong());
            statement.setDouble(6, t.getPhat());
            statement.setInt(7, t.getSoNgayLam());
            statement.setDouble(8, t.getLuongThucNhan());
            statement.setString(9, t.getMaPL());
            // Execute the query
            int rowsAffected = statement.executeUpdate();
          
            // Check if the update was successful
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
        
        //let code to delete PhieuLuongCongNhan t from database
        try {
            // Create a PreparedStatement to delete the data  
            String query = "DELETE FROM PhieuLuongCongNhan WHERE maPL = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Set the value of the parameter
            statement.setString(1, id);
            
            // Execute the query
            int rowsAffected = statement.executeUpdate();
          
            // Check if the delete was successful
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public  ArrayList<PhieuLuongCongNhan> getAllByThangNam(int thang, int nam) {
        ArrayList<PhieuLuongCongNhan> phieuLuongCongNhans = new ArrayList<>();
        try {
            String query = "SELECT * FROM PhieuLuongCongNhan WHERE thang = ? AND nam = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, thang);
            statement.setInt(2, nam);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String maPL = resultSet.getString("maPL");
                int thang1 = resultSet.getInt("thang");
                int nam1 = resultSet.getInt("nam");
                String maCN = resultSet.getString("maCN");
                double phat = resultSet.getDouble("phat");
                // double thuong = resultSet.getDouble("thuong");
                // double phat = resultSet.getDouble("phat");
                // int soNgayLam = resultSet.getInt("soNgayLam");
                // double luongThucNhan = resultSet.getDouble("luongThucNhan");
                
                PhieuLuongCongNhan phieuLuongCongNhan = new PhieuLuongCongNhan(maPL, thang1, nam1, maCN, phat);
                phieuLuongCongNhans.add(phieuLuongCongNhan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phieuLuongCongNhans;
    } 

    public  ArrayList<PhieuLuongCongNhan> getAllByMaPBThangNam(String maBP, int thang, int nam) {
        // let code to get all PhieuLuongCongNhan from database by mabp, thang, nam
        ArrayList<PhieuLuongCongNhan> phieuLuongCongNhans = new ArrayList<>();
        try {
            String query = "SELECT * FROM PhieuLuongCongNhan WHERE maCN IN (SELECT maCN FROM CongNhan WHERE maBP = ?) AND thang = ? AND nam = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maBP);
            statement.setInt(2, thang);
            statement.setInt(3, nam);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String maPL = resultSet.getString("maPL");
                int thang1 = resultSet.getInt("thang");
                int nam1 = resultSet.getInt("nam");
                String maCN = resultSet.getString("maCN");
                double phat = resultSet.getDouble("phat");
                // double thuong = resultSet.getDouble("thuong");
                // double phat = resultSet.getDouble("phat");
                // int soNgayLam = resultSet.getInt("soNgayLam");
                // double luongThucNhan = resultSet.getDouble("luongThucNhan");
                PhieuLuongCongNhan phieuLuongCongNhan = new PhieuLuongCongNhan(maPL, thang1, nam1, maCN, phat);
                phieuLuongCongNhans.add(phieuLuongCongNhan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phieuLuongCongNhans;
    }
}
