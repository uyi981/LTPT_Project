/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ptud.Entity.CongDoan;
import ptud.Entity.SanPham;

import static ptud.Mang.connection;

/**
 *
 * @author KHANH PC
 */

public class DAO_CongDoan implements DAOInterface<CongDoan> {

    public static DAO_CongDoan getInstance() {
        return new DAO_CongDoan();
    }

    @Override
    public CongDoan get(String id) {
        // let code to get CongDoan t from database
        try {
            String query = "SELECT * FROM CongDoan WHERE maCD = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String maCD = resultSet.getString("maCD");
                String maSP = resultSet.getString("maSP");
                String maBP = resultSet.getString("maBP");
                String tenCD = resultSet.getString("tenCD");
                double donGia = resultSet.getDouble("donGia");
                boolean trangThai = resultSet.getBoolean("trangThai");
                int soLuongChuanBiToiThieu = resultSet.getInt("soLuongChuanBiToiThieu");

                ArrayList<String> dsCDTQ = new ArrayList<>();
                // get dsCDTQ from my CongDoanTienQuyet database
                String queryCDTQ = "SELECT maCDTQ FROM CongDoanTienQuyet WHERE maCD = ?";
                PreparedStatement statementCDTQ = connection.prepareStatement(queryCDTQ);
                statementCDTQ.setString(1, id);
                ResultSet resultSetCDTQ = statementCDTQ.executeQuery();

                while (resultSetCDTQ.next()) {
                    String maCDTQ = resultSetCDTQ.getString("maCDTQ");
                    dsCDTQ.add(maCDTQ);
                }

                CongDoan congDoan = new CongDoan(maCD, maSP, maBP, tenCD, donGia, trangThai, soLuongChuanBiToiThieu,
                        dsCDTQ);
                return congDoan;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public ArrayList<CongDoan> getAll() {
        // let code to get all CongDoan from database
        ArrayList<CongDoan> congDoans = new ArrayList<>();
        try {
            String query = "SELECT * FROM CongDoan";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maCD = resultSet.getString("maCD");
                String maSP = resultSet.getString("maSP");
                String maBP = resultSet.getString("maBP");
                String tenCD = resultSet.getString("tenCD");
                double donGia = resultSet.getDouble("donGia");
                boolean trangThai = resultSet.getBoolean("trangThai");
                int soLuongChuanBiToiThieu = resultSet.getInt("soLuongChuanBiToiThieu");

                ArrayList<String> dsCDTQ = new ArrayList<>();
                // get dsCDTQ from my CongDoanTienQuyet database
                String queryCDTQ = "SELECT maCDTQ FROM CongDoanTienQuyet WHERE maCD = ?";
                PreparedStatement statementCDTQ = connection.prepareStatement(queryCDTQ);
                statementCDTQ.setString(1, maCD);
                ResultSet resultSetCDTQ = statementCDTQ.executeQuery();

                while (resultSetCDTQ.next()) {
                    String maCDTQ = resultSetCDTQ.getString("maCDTQ");
                    dsCDTQ.add(maCDTQ);
                }

                CongDoan congDoan = new CongDoan(maCD, maSP, maBP, tenCD, donGia, trangThai, soLuongChuanBiToiThieu,
                        dsCDTQ);
                congDoans.add(congDoan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return congDoans;
    }

    public ArrayList<CongDoan> getAllByMaSP(String maSP) {
        // let code to get all CongDoan from database by maSP
        ArrayList<CongDoan> congDoans = new ArrayList<>();
        try {
            String query = "SELECT * FROM CongDoan WHERE maSP = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maSP);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maCD = resultSet.getString("maCD");
                String maBP = resultSet.getString("maBP");
                String tenCD = resultSet.getString("tenCD");
                double donGia = resultSet.getDouble("donGia");
                boolean trangThai = resultSet.getBoolean("trangThai");
                int soLuongChuanBiToiThieu = resultSet.getInt("soLuongChuanBiToiThieu");

                ArrayList<String> dsCDTQ = new ArrayList<>();
                // get dsCDTQ from my CongDoanTienQuyet database
                String queryCDTQ = "SELECT maCDTQ FROM CongDoanTienQuyet WHERE maCD = ?";
                PreparedStatement statementCDTQ = connection.prepareStatement(queryCDTQ);
                statementCDTQ.setString(1, maCD);
                ResultSet resultSetCDTQ = statementCDTQ.executeQuery();

                while (resultSetCDTQ.next()) {
                    String maCDTQ = resultSetCDTQ.getString("maCDTQ");
                    dsCDTQ.add(maCDTQ);
                }

                CongDoan congDoan = new CongDoan(maCD, maSP, maBP, tenCD, donGia, trangThai, soLuongChuanBiToiThieu,
                        dsCDTQ);
                congDoans.add(congDoan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return congDoans;
    }

    public ArrayList<CongDoan> getAllByMaBP(String maBP) {
        // let code to get all CongDoan from database by maBP
        try {
            String query = "SELECT * FROM CongDoan WHERE maBP = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maBP);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<CongDoan> congDoans = new ArrayList<>();

            while (resultSet.next()) {
                String maCD = resultSet.getString("maCD");
                String maSP = resultSet.getString("maSP");
                String tenCD = resultSet.getString("tenCD");
                double donGia = resultSet.getDouble("donGia");
                boolean trangThai = resultSet.getBoolean("trangThai");
                int soLuongChuanBiToiThieu = resultSet.getInt("soLuongChuanBiToiThieu");

                ArrayList<String> dsCDTQ = new ArrayList<>();
                // get dsCDTQ from my CongDoanTienQuyet database
                String queryCDTQ = "SELECT maCDTQ FROM CongDoanTienQuyet WHERE maCD = ?";
                PreparedStatement statementCDTQ = connection.prepareStatement(queryCDTQ);
                statementCDTQ.setString(1, maCD);
                ResultSet resultSetCDTQ = statementCDTQ.executeQuery();

                while (resultSetCDTQ.next()) {
                    String maCDTQ = resultSetCDTQ.getString("maCDTQ");
                    dsCDTQ.add(maCDTQ);
                }
                CongDoan congDoan = new CongDoan(maCD, maSP, maBP, tenCD, donGia, trangThai, soLuongChuanBiToiThieu,
                        dsCDTQ);
                congDoans.add(congDoan);
            }
            return congDoans;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<CongDoan> getAllByTrangThai(boolean trangThai) {
        // let code to get all CongDoan from database by TrangThai
        try {
            ArrayList<CongDoan> congDoans = new ArrayList<>();

            String query = "SELECT * FROM CongDoan WHERE trangThai = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBoolean(1, trangThai);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maCD = resultSet.getString("maCD");
                String maSP = resultSet.getString("maSP");
                String maBP = resultSet.getString("maBP");
                String tenCD = resultSet.getString("tenCD");
                double donGia = resultSet.getDouble("donGia");
                int soLuongChuanBiToiThieu = resultSet.getInt("soLuongChuanBiToiThieu");

                ArrayList<String> dsCDTQ = new ArrayList<>();
                // get dsCDTQ from my CongDoanTienQuyet database
                String queryCDTQ = "SELECT maCDTQ FROM CongDoanTienQuyet WHERE maCD = ?";
                PreparedStatement statementCDTQ = connection.prepareStatement(queryCDTQ);
                statementCDTQ.setString(1, maCD);
                ResultSet resultSetCDTQ = statementCDTQ.executeQuery();

                while (resultSetCDTQ.next()) {
                    String maCDTQ = resultSetCDTQ.getString("maCDTQ");
                    dsCDTQ.add(maCDTQ);
                }

                CongDoan congDoan = new CongDoan(maCD, maSP, maBP, tenCD, donGia, trangThai, soLuongChuanBiToiThieu,
                        dsCDTQ);
                congDoans.add(congDoan);
            }
            return congDoans;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean insert(CongDoan t) {
        // let code to insert CongDoan t into database, and insert CongDoanTienQuyet too
        try {
            // Insert CongDoan t into database
            String query = "INSERT INTO CongDoan (maCD, maSP, maBP, tenCD, donGia, trangThai, soLuongChuanBiToiThieu, soLuongChuanBi, soLuongHoanThanh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            // Set the values of the parameters
            statement.setString(1, t.getMaCD());
            statement.setString(2, t.getMaSP());
            statement.setString(3, t.getMaBP());
            statement.setString(4, t.getTenCD());
            statement.setDouble(5, t.getDonGia());
            statement.setBoolean(6, t.isTrangThai());
            statement.setInt(7, t.getSoLuongChuanBiToiThieu());
            statement.setInt(8, t.getSoLuongChuanBi());
            statement.setInt(9, t.getSoLuongHoanThanh());
            // Execute the query
            int rowsAffected = statement.executeUpdate();

            // Check if the insert was successful
            if (rowsAffected > 0) {

                // Insert new CongDoanTienQuyet
                for (String maCDTQ : t.getDsCDTQ()) {
                    String insertQuery = "INSERT INTO CongDoanTienQuyet (maCD, maCDTQ) VALUES (?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                    insertStatement.setString(1, t.getMaCD());
                    insertStatement.setString(2, maCDTQ);
                    insertStatement.executeUpdate();
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(CongDoan t) {
        // let code to update CongDoan t to database
        try {
            // Update CongDoan t in the database
            String query = "UPDATE CongDoan SET maSP = ?, maBP = ?, tenCD = ?, donGia = ?, trangThai = ?, soLuongChuanBiToiThieu = ?, soLuongChuanBi = ?, soLuongHoanThanh = ?  WHERE maCD = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            // Set the values of the parameters
            statement.setString(1, t.getMaSP());
            statement.setString(2, t.getMaBP());
            statement.setString(3, t.getTenCD());
            statement.setDouble(4, t.getDonGia());
            statement.setBoolean(5, t.isTrangThai());
            statement.setInt(6, t.getSoLuongChuanBiToiThieu());
            statement.setInt(7, t.getSoLuongChuanBi());
            statement.setInt(8, t.getSoLuongHoanThanh());
            statement.setString(9, t.getMaCD());
            // Execute the query
            int rowsAffected = statement.executeUpdate();

            // Check if the update was successful
            if (rowsAffected > 0) {
                // Update CongDoanTienQuyet
                // First, delete all existing CongDoanTienQuyet records for the current CongDoan
                String deleteQuery = "DELETE FROM CongDoanTienQuyet WHERE maCD = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setString(1, t.getMaCD());
                deleteStatement.executeUpdate();

                // Then, insert new CongDoanTienQuyet records
                for (String maCDTQ : t.getDsCDTQ()) {
                    String insertQuery = "INSERT INTO CongDoanTienQuyet (maCD, maCDTQ) VALUES (?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                    insertStatement.setString(1, t.getMaCD());
                    insertStatement.setString(2, maCDTQ);
                    insertStatement.executeUpdate();
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        // let code to delete CongDoan t from database
        try {
            // CREATE TABLE CongDoanTienQuyet (
            // maCDTQ VARCHAR(20),
            // maCD VARCHAR(20),
            // PRIMARY KEY (maCD, maCDTQ),
            // FOREIGN KEY (maCDTQ) REFERENCES CongDoan(maCD),
            // FOREIGN KEY (maCD) REFERENCES CongDoan(maCD)
            // );
            // first Delete all records CongDoanTienQuyet has maCDTQ == id
            String deleteQuery = "DELETE FROM CongDoanTienQuyet WHERE maCDTQ = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, id);
            deleteStatement.executeUpdate();
            // second Delete all records CongDoanTienQuyet has maCD == id
            deleteQuery = "DELETE FROM CongDoanTienQuyet WHERE maCD = ?";
            deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, id);
            deleteStatement.executeUpdate();

            String query = "DELETE FROM CongDoan WHERE maCD = ?";
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

    public String getLastMaCD(String maSP) {
        try {
            String query = "SELECT TOP 1 maCD FROM CongDoan WHERE maSP = ? ORDER BY maCD DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maSP);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("maCD");
            } else {
                return maSP + "00";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getDsCDHQ(String maCDTQ) {

        try {
            String query = "SELECT maCD FROM CongDoanTienQuyet WHERE maCDTQ = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maCDTQ);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> dsCD = new ArrayList<>();
            while (resultSet.next()) {
                dsCD.add(resultSet.getString("maCD"));
            }
            return dsCD;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    public int getSoLuongHoanThanh(String maCD) {
        String query = "SELECT SUM(soLuongCD + soLuongCDTangCa) AS soLuongHoanThanh " +
                "FROM ChiTietPhanCong " +
                "JOIN PhieuChamCongCongNhan ON ChiTietPhanCong.maCTPC = PhieuChamCongCongNhan.maCTPC " +
                "WHERE maCD = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, maCD);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("soLuongHoanThanh");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<CongDoan> getDsCDTQ(String maCD) {
        String query = "SELECT maCDTQ FROM CongDoanTienQuyet WHERE maCD = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, maCD);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<CongDoan> dsCDTQ = new ArrayList<>();
            while (resultSet.next()) {
                dsCDTQ.add(this.get(resultSet.getString("maCDTQ")));
            }
            return dsCDTQ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<CongDoan>();
    }

    public int getSoLuongChuanBi(String maCD) {
        // ko có công đoạn tiên quyết thì trả về số lượng
        if( this.getDsCDTQ(maCD).isEmpty() ) {
            String maSP = this.get(maCD).getMaSP(); 
            SanPham sp = DAO_SanPham.getInstance().get(maSP);
            return sp.getSoLuong(); 
        } else {
            // Lấy số lượng hoàn thành nhỏ nhất của công đoạn tiên quyết làm số lượng chuẩn bị
            ArrayList<CongDoan> dsCDTQ = this.getDsCDTQ(maCD);
            int sum = 99999;
            for (CongDoan cd : dsCDTQ) {
                int slht = cd.getSoLuongHoanThanh(); 
                sum = Math.min(sum, slht);
            }
            return sum; 
        }
    }

    public void updateSoLuong( String maCD, int delta, boolean giam ) {
        String query = "UPDATE CongDoan SET soLuongCD = soLuongCD + ? WHERE maCD = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, delta);
            statement.setString(2, maCD);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   
}
