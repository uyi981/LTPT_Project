/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import ptud.Entity.HopDong;
import static ptud.Mang.connection;

/**
 *
 * @author voHau
 */
public class DAO_HopDong implements DAOInterface<HopDong> {

    
    public static DAO_HopDong getInstance() 
    {
        return new DAO_HopDong();
    }
    
    
    @Override
    public HopDong get(String id) {
        // let code to get HopDong t from database
        HopDong hopDong = null;
        try {
            String query = "SELECT * FROM HopDong WHERE maHD = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) 
            {
                
                String tenHD = resultSet.getString("tenHD");
                LocalDate ngayBatDau = resultSet.getDate("ngayBatDau").toLocalDate();
                LocalDate ngayKetThuc = resultSet.getDate("ngayKetThucDukien").toLocalDate();
                String maKH = resultSet.getString("maKH");
                double donGia = resultSet.getDouble("donGia");
                String trangThai = resultSet.getString("trangThai");
                hopDong = new HopDong(id,tenHD, ngayBatDau, ngayKetThuc, donGia,maKH,trangThai);   
                hopDong.setMaHD(id);
            }      
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return hopDong;
    }

    @Override
    public ArrayList<HopDong> getAll() {
        // let code to get all HopDong from database
        ArrayList<HopDong> hopDongs = new ArrayList<>();
        try {
            String query = "SELECT * FROM HopDong";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String maHD =  resultSet.getString("maHD");
                String tenHD = resultSet.getString("tenHD");
                LocalDate ngayBatDau = resultSet.getDate("ngayBatDau").toLocalDate();
                LocalDate ngayKetThuc = resultSet.getDate("ngayKetThucDukien").toLocalDate();              
                String maKH = resultSet.getString("maKH");
                double donGia = resultSet.getDouble("donGia");
                String trangThai = resultSet.getString("trangThai");                                            
                HopDong hopDong = new HopDong(maHD,tenHD, ngayBatDau, ngayKetThuc, donGia,maKH,trangThai);   
                hopDongs.add(hopDong);
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return hopDongs;
    }

    
   
    @Override
    public boolean insert(HopDong hopDong) {
        //let code to insert HopDong hopDong to database sqlserver 
        try {
            // Create a PreparedStatement to insert the data  
            String query = "INSERT INTO HopDong (maHD, maKH, tenHD, donGia, ngayBatDau, ngayKetThucDukien, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Set the values of the parameters
            statement.setString(1, hopDong.getMaHD());
            statement.setString(2, hopDong.getMaKH());
            statement.setString(3, hopDong.getTenHD());
            statement.setDouble(4, hopDong.getDonGia());
            statement.setDate(5,Date.valueOf(hopDong.getNgayBatDau()));
            statement.setDate(6,Date.valueOf(hopDong.getNgayKetThuc()));
            statement.setString(7, hopDong.getTrangThai());       
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
    public boolean update(HopDong hopDong) {
        try {
            // Create a PreparedStatement to update the data  
            String query = "UPDATE HopDong SET maKH = ?, tenHD = ?, donGia = ?, ngayBatDau = ?, ngayKetThucDukien = ?, trangThai = ? WHERE maHD = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Set the values of the parameters         
            statement.setString(1, hopDong.getMaKH());
            statement.setString(2, hopDong.getTenHD());
            statement.setDouble(3, hopDong.getDonGia());
            statement.setDate(4,Date.valueOf(hopDong.getNgayBatDau()));
            statement.setDate(5,Date.valueOf(hopDong.getNgayKetThuc()));
            statement.setString(6, hopDong.getTrangThai());   
            statement.setString(7, hopDong.getMaHD());
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
    // khong co xoa hopDong 
    @Override
    public boolean deleteById(String id) 
    {
      return true;
    }


}

    
