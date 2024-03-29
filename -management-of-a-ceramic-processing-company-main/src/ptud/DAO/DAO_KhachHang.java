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
import ptud.Entity.KhachHang;
import static ptud.Mang.connection;

/**
 *
 * @author vohau
 */
public class DAO_KhachHang implements DAOInterface<KhachHang>
{
      
    public static DAO_KhachHang getInstance() 
    {
        return new DAO_KhachHang();
    }
   
   
    @Override
    public KhachHang get(String id) {
        // let code to get KhachHang t from database
        KhachHang khachHang = null;
        try {
            String query = "SELECT * FROM KhachHang WHERE maKH = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) 
            {
                
                String tenKH = resultSet.getString("tenKH");
                boolean isToChuc =  resultSet.getBoolean("toChuc");
                String email = resultSet.getString("email");
                String sdt = resultSet.getString("dienThoai"); 
                khachHang = new KhachHang(id, tenKH, isToChuc, email, sdt);
                
            }           
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return khachHang;
    }

    @Override
    public ArrayList<KhachHang> getAll() {
        // let code to get all KhachHang from database
        ArrayList<KhachHang> khachHangs = new ArrayList<>();
        try {
            String query = "SELECT * FROM KhachHang";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String maKH =  resultSet.getString("maKH");
                String tenKH = resultSet.getString("tenKH");
                boolean isToChuc =  resultSet.getBoolean("toChuc");
                String email = resultSet.getString("email");
                String sdt = resultSet.getString("dienThoai"); 
                KhachHang khachHang = new KhachHang(maKH, tenKH, isToChuc, email, sdt);
                khachHangs.add(khachHang);
            } 
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return khachHangs;
    }
    
    @Override
    public boolean insert (KhachHang khachHang) {
        //let code to insert khachHang to database sqlserver 
        try {
            // Create a PreparedStatement to insert the data  
            String query = "INSERT INTO KhachHang (maKH, tenKH,toChuc, email, dienThoai) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);           
            // Set the values of the parameters
            statement.setString(1, khachHang.getMaKhachHang());
            statement.setString(2, khachHang.getTenKhachHang());
            statement.setBoolean(3, khachHang.isToChuc()); 
            statement.setString(4, khachHang.getEmail());
            statement.setString(5, khachHang.getSdt());          
                
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
    public boolean update(KhachHang khachHang) {
        try {
            // Create a PreparedStatement to update the data  
            String query = "UPDATE KhachHang SET tenKH = ?, toChuc = ?, email = ?, sdt = ? WHERE maKH = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Set the values of the parameters         
            statement.setString(1, khachHang.getTenKhachHang());
            statement.setBoolean(2, khachHang.isToChuc()); 
            statement.setString(3, khachHang.getEmail());
            statement.setString(4, khachHang.getSdt());     
            statement.setString(5, khachHang.getMaKhachHang());

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
    // khong co xoa khachHang 
    @Override
    public boolean deleteById(String id) 
    {
      return true;
    }
}
