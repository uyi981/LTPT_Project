package ptud.DAO;

import java.util.ArrayList;
import ptud.DAO.DAOInterface;
import ptud.Entity.BoPhan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ptud.Connection.ConnectDB;
import static ptud.Mang.connection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author TomTom
 */
public class DAO_BoPhan implements DAOInterface<BoPhan> {
    public static DAO_BoPhan getInstance() {
        return new DAO_BoPhan();
    }

    @Override
    public BoPhan get(String id) {
        BoPhan boPhan = null;
        try {
            ConnectDB.getInstance();
            java.sql.Connection connection = ConnectDB.getConnection();
            String query = "SELECT * FROM BoPhan WHERE maBP = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String tenBP = resultSet.getString("tenBP");
                boPhan = new BoPhan(id, tenBP);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boPhan;
    }

    @Override
    public ArrayList<BoPhan> getAll() {
        ArrayList<BoPhan> dsBoPhan = new ArrayList<BoPhan>();
        try {
            ConnectDB.getInstance();
            java.sql.Connection connection = ConnectDB.getConnection();
            String query = "SELECT * FROM BoPhan";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String maBP = resultSet.getString("maBP");
                String tenBP = resultSet.getString("tenBP");
                BoPhan boPhan = new BoPhan(maBP, tenBP);
                dsBoPhan.add(boPhan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dsBoPhan;
    }

    @Override
    public boolean insert(BoPhan boPhan) {
        try {
            ConnectDB.getInstance();
            java.sql.Connection connection = ConnectDB.getConnection();
            String query = "INSERT INTO BoPhan (maBP, tenBP) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, boPhan.getMaBP());
            statement.setString(2, boPhan.getTenBP());

            int rowAffected = statement.executeUpdate();
            if (rowAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(BoPhan boPhan) {
        try {
            ConnectDB.getInstance();
            java.sql.Connection connection = ConnectDB.getConnection();
            String query = "UPDATE BoPhan SET tenBP = ? WHERE maBP = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, boPhan.getTenBP());
            statement.setString(2, boPhan.getMaBP());

            int rowAffected = statement.executeUpdate();
            if (rowAffected > 0) {
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
            String query = "DELETE FROM SanPham WHERE maBP = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    

    public static String getMaBoPhanByTenBoPhan(String tenBoPhan) {
        try {
            String query = "select maBP\n"
                    + "from [dbo].[BoPhan]\n"
                    + "where tenBP = ?";
            PreparedStatement statement;
            statement = connection.prepareStatement(query);
            statement.setNString(1, tenBoPhan);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String ketqua = resultSet.getString("maBP");
                return ketqua;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO_BoPhan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public ArrayList<BoPhan> filter(String prefix) {
        ArrayList<BoPhan> filteredList = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            java.sql.Connection connection = ConnectDB.getConnection();

            // Sử dụng LIKE để lọc các bộ phận bắt đầu bằng "HC" hoặc "SX"
            String query = "SELECT * FROM BoPhan WHERE maBP LIKE ? OR maBP LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, prefix + "%");
            statement.setString(2, prefix + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maBP = resultSet.getString("maBP");
                String tenBP = resultSet.getString("tenBP");
                BoPhan boPhan = new BoPhan(maBP, tenBP);
                filteredList.add(boPhan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredList;
    }
}
