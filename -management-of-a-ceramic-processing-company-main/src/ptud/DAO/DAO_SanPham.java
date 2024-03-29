/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ptud.Entity.SanPham;
import static ptud.Mang.connection;

/**
 *
 * @author vohau
 */
public class DAO_SanPham implements DAOInterface<SanPham>
{
      
    public static DAO_SanPham getInstance() 
    {
        return new DAO_SanPham();
    }
   
   
    @Override
    public SanPham get(String id) {
        // let code to get SanPham t from database
        SanPham sanPham = null;
        try {
            String query = "SELECT * FROM SanPham WHERE maSP = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) 
            {                
                String tenSP = resultSet.getString("tenSP");
                int soLuong = resultSet.getInt("soLuong");
                double donGia = resultSet.getFloat("donGia");
                String maHD = resultSet.getString("maHD");
                sanPham = new SanPham(id, tenSP, soLuong, donGia,maHD);
                
            }          
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return sanPham;
    }

    @Override
    public ArrayList<SanPham> getAll() {
        // let code to get all SanPham from database
        ArrayList<SanPham> sanPhams = new ArrayList<>();
        try {
            String query = "SELECT * FROM SanPham";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String maSP = resultSet.getString("maSP");
                String tenSP = resultSet.getString("tenSP");
                int soLuong = resultSet.getInt("soLuong");
                double donGia = resultSet.getFloat("donGia");
                String maHD = resultSet.getString("maHD");
                SanPham sanPham = new SanPham(maSP, tenSP, soLuong, donGia,maHD);            
                sanPhams.add(sanPham);
            } 
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return sanPhams;
    }
    
    @Override
    public boolean insert (SanPham sanPham) {
        //let code to insert sanPham to database sqlserver 
        try {
            // Create a PreparedStatement to insert the data  
            String query = "INSERT INTO SanPham (maSP, tenSP,soLuong, donGia,maHD) VALUES (?, ?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(query);           
            // Set the values of the parameters
            statement.setString(1, sanPham.getMaSanPham());
            statement.setString(2, sanPham.getTenSanPham());
            statement.setInt(3, sanPham.getSoLuong()); 
            statement.setDouble(4, sanPham.getDonGia()); 
            statement.setString(5, sanPham.getMaHD());                       
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
    public boolean update(SanPham sanPham) {
        try {
            // Create a PreparedStatement to update the data  
            String query = "UPDATE SanPham SET tenSP = ?, soLuong = ?, donGia = ? WHERE maSP = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Set the values of the parameters         
            statement.setString(1, sanPham.getTenSanPham());
            statement.setInt(2, sanPham.getSoLuong()); 
            statement.setDouble(3, sanPham.getDonGia());
            statement.setString(4, sanPham.getMaSanPham());
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
    public boolean deleteById(String id) 
    {
            // let code to delete sanPham from database
        try {
            // Create a PreparedStatement to delete the data  
            String query = "DELETE FROM SanPham WHERE maSP = ?";
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
      return true;
    }
}

