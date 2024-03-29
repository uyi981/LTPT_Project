/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.DAO;

/**
 *
 * @author TranLoc
 */
import java.awt.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import ptud.Connection.ConnectDB;
import static ptud.Mang.connection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ptud.Entity.TaiKhoan;

public class DAO_TaiKhoan {

    public static DAO_TaiKhoan getInstance() {
        return new DAO_TaiKhoan();
    }

    public static String hashPassword(String password, int length) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().substring(0, Math.min(length, sb.length()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getUserRole(String username, String password) throws SQLException {
        String query = "select vaiTro\n"
                + "from [dbo].[TaiKhoan]\n"
                + "where userName like ? and matKhau like ?";
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, hashPassword(password, 16));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("vaiTro");
        }
        return 0;
    }

    public String getTenNhanVienTaiKhoanByIDNV(String maNV) throws SQLException {
        String query = "select NV.tenNV\n"
                + "from [dbo].[NhanVien] NV\n"
                + "where NV.maNV = ?";
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        statement.setString(1, maNV);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("tenNV");
        }
        return "";
    }

    public int getUserRoleByUserName(String username) throws SQLException {
        String query = "select vaiTro\n"
                + "from [dbo].[TaiKhoan]\n"
                + "where userName = ?";
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("vaiTro");
        }
        return 0;
    }

    public ArrayList<TaiKhoan> getAll() throws SQLException {
        ArrayList<TaiKhoan> dsTaiKhoan = new ArrayList<TaiKhoan>();
        String query = "select TK.maNV, userName, matKhau, vaiTro, tenNV\n"
                + "from [dbo].[TaiKhoan] TK\n"
                + "join [dbo].[NhanVien] NV\n"
                + "on TK.maNV = NV.maNV \n"
                + "where TK.trangThai = 1";
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            TaiKhoan tk = new TaiKhoan(rs.getString("userName"), rs.getString("maNV"), rs.getString("matKhau"), rs.getInt("vaiTro"), true);
            dsTaiKhoan.add(tk);
        }
        return dsTaiKhoan;
    }

    public static void updateTaiKhoan(TaiKhoan tk) {
        try {
            String query = "update [dbo].[TaiKhoan]\n"
                    + "set matKhau = ?, vaiTro = ?\n"
                    + "where maNV = ?";
            PreparedStatement statement;
            statement = connection.prepareStatement(query);
            statement.setString(1, tk.getMatKhat());
            statement.setInt(2, tk.getVaiTro());
            statement.setString(3, tk.getMaNV());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAO_TaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void createTaiKhoan(TaiKhoan tk) {
        try {
            String query = "insert into [dbo].[TaiKhoan]\n"
                    + "values (?, ?, ?, ?, ?)";
            PreparedStatement statement;
            statement = connection.prepareStatement(query);
            statement.setString(1, tk.getMaNV());
            statement.setString(2, tk.getUserName());
            statement.setString(3, DAO_TaiKhoan.hashPassword(tk.getMatKhat(), 16));
            statement.setInt(4, tk.getVaiTro());
            statement.setBoolean(5, true);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAO_TaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteTaiKhoanByID(String ID) {
        try {
            String query = "update [dbo].[TaiKhoan]\n"
                    + "set trangThai = 0\n"
                    + "where maNV = ?";
            PreparedStatement statement;
            statement = connection.prepareStatement(query);
            statement.setString(1, ID);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAO_TaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
