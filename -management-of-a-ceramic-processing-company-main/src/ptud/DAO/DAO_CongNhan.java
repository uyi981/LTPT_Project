package ptud.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import ptud.Entity.BoPhan;
import ptud.Entity.CongNhan;
import static ptud.Mang.connection;

public class DAO_CongNhan implements DAOInterface<CongNhan> {

    public static DAO_CongNhan getInstance() {
        return new DAO_CongNhan();
    }

    @Override
    public CongNhan get(String id) {
        CongNhan congNhan = null;
        try {
            String query = "SELECT * FROM CongNhan WHERE maCN = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                BoPhan boPhan = new DAO_BoPhan().get(resultSet.getString("maBP"));
                String ten = resultSet.getString("tenCN");
                boolean gioiTinh = resultSet.getBoolean("gioiTinh");
                LocalDate ngaySinh = resultSet.getDate("ngaySinh").toLocalDate();
                LocalDate ngayBatDauLam = resultSet.getDate("ngayBatDauLam").toLocalDate();
                String cccd = resultSet.getString("CCCD");
                String dienThoai = resultSet.getString("dienThoai");
                boolean trangThai = resultSet.getBoolean("trangThai");
                byte[] avatar = resultSet.getBytes("hinhAnh");
                boolean choPhanCong = resultSet.getBoolean("choPhanCong");
                congNhan = new CongNhan(id, boPhan, ten, gioiTinh, ngaySinh, ngayBatDauLam, cccd, dienThoai, trangThai, avatar, choPhanCong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return congNhan;
    }

    @Override
    public ArrayList<CongNhan> getAll() {
        ArrayList<CongNhan> dsCongNhan = new ArrayList<>();
        try {
            String query = "SELECT * FROM CongNhan WHERE trangThai = 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                String maCN = resultSet.getString("maCN");
                BoPhan boPhan = new DAO_BoPhan().get(resultSet.getString("maBP"));
                String ten = resultSet.getString("tenCN");
                boolean gioiTinh = resultSet.getBoolean("gioiTinh");
                LocalDate ngaySinh = resultSet.getDate("ngaySinh").toLocalDate();
                LocalDate ngayBatDauLam = resultSet.getDate("ngayBatDauLam").toLocalDate();
                String cccd = resultSet.getString("CCCD");
                String dienThoai = resultSet.getString("dienThoai");
                boolean trangThai = resultSet.getBoolean("trangThai");
                byte[] avatar = resultSet.getBytes("hinhAnh");
                boolean choPhanCong = resultSet.getBoolean("choPhanCong");
                CongNhan congNhan = new CongNhan(maCN, boPhan, ten, gioiTinh, ngaySinh, ngayBatDauLam, cccd, dienThoai, trangThai, avatar, choPhanCong);

                dsCongNhan.add(congNhan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsCongNhan;
    }

    @Override
    public boolean insert(CongNhan congNhan) {
        try {
            String sql = "INSERT INTO CongNhan (maCN, maBP, tenCN, gioiTinh, ngaySinh, ngayBatDauLam, CCCD, dienThoai, trangThai, hinhAnh, choPhanCong) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, congNhan.getMaCN());
            statement.setString(2, congNhan.getBoPhan().getMaBP()); // Điều chỉnh tên cột maBP tùy thuộc vào cơ sở dữ liệu của bạn
            statement.setString(3, congNhan.getTen());
            statement.setBoolean(4, congNhan.isGioiTinh());
            statement.setDate(5, java.sql.Date.valueOf(congNhan.getNgaySinh()));
            statement.setDate(6, java.sql.Date.valueOf(congNhan.getNgayBatDauLam()));
            statement.setString(7, congNhan.getCccd());
            statement.setString(8, congNhan.getDienThoai());
            statement.setBoolean(9, congNhan.isTrangThai());
            statement.setBytes(10, congNhan.getAvatar());
            statement.setBoolean(11, congNhan.isChoPhanCong());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(CongNhan congNhan) {
        try {
            String sql = "UPDATE CongNhan SET maBP = ?, tenCN = ?, gioiTinh = ?, ngaySinh = ?, ngayBatDauLam = ?, CCCD = ?, dienThoai = ?, trangThai = ?, hinhAnh = ?, choPhanCong = ? WHERE maCN = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, congNhan.getBoPhan().getMaBP()); // Điều chỉnh tên cột maBP tùy thuộc vào cơ sở dữ liệu của bạn
            statement.setString(2, congNhan.getTen());
            statement.setBoolean(3, congNhan.isGioiTinh());
            statement.setDate(4, java.sql.Date.valueOf(congNhan.getNgaySinh()));
            statement.setDate(5, java.sql.Date.valueOf(congNhan.getNgayBatDauLam()));
            statement.setString(6, congNhan.getCccd());
            statement.setString(7, congNhan.getDienThoai());
            statement.setBoolean(8, congNhan.isTrangThai());
            statement.setBytes(9, congNhan.getAvatar());
            statement.setBoolean(10, congNhan.isChoPhanCong());
            statement.setString(11, congNhan.getMaCN());
          
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        try {
            String sql = "UPDATE CongNhan SET trangThai = ? WHERE maCN = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, false);
            statement.setString(2, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
//    đếm số lượng công nhân
     public int countAll() {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) AS total FROM CongNhan";
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
     
     public ArrayList<CongNhan> search(String searchText, String searchCriteria) {
        ArrayList<CongNhan> dsCongNhan = new ArrayList<>();
        try {
            String query = "";
            PreparedStatement statement = null;

            // Xây dựng câu truy vấn dựa trên tiêu chí tìm kiếm
            switch (searchCriteria) {
                case "Mã CN":
                    query = "SELECT * FROM CongNhan WHERE maCN LIKE ? AND trangThai = 1";
                    break;
                case "Tên công nhân":
                    query = "SELECT * FROM CongNhan WHERE tenCN LIKE ? AND trangThai = 1";
                    break;
                case "Số điện thoại":
                    query = "SELECT * FROM CongNhan WHERE dienThoai LIKE ? AND trangThai = 1";
                    break;
                // Thêm các trường hợp khác nếu cần
                default:
                    // Không hỗ trợ tiêu chí tìm kiếm
                    return dsCongNhan;
            }

            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + searchText + "%");

            ResultSet resultSet = statement.executeQuery();
            System.out.println("từ khóa: " + searchText);
            System.out.println("Query: " + query);
            
            while (resultSet.next()) {
                String maCN = resultSet.getString("maCN");
                BoPhan boPhan = new DAO_BoPhan().get(resultSet.getString("maBP"));
                String ten = resultSet.getString("tenCN");
                boolean gioiTinh = resultSet.getBoolean("gioiTinh");
                LocalDate ngaySinh = resultSet.getDate("ngaySinh").toLocalDate();
                LocalDate ngayBatDauLam = resultSet.getDate("ngayBatDauLam").toLocalDate();
                String cccd = resultSet.getString("CCCD");
                String dienThoai = resultSet.getString("dienThoai");
                boolean trangThai = resultSet.getBoolean("trangThai");
                byte[] avatar = resultSet.getBytes("hinhAnh");
                boolean choPhanCong = resultSet.getBoolean("choPhanCong");
                CongNhan congNhan = new CongNhan(maCN, boPhan, ten, gioiTinh, ngaySinh, ngayBatDauLam, cccd, dienThoai, trangThai, avatar, choPhanCong);

                dsCongNhan.add(congNhan);
            }
            System.out.println("Kết quả: " + dsCongNhan.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsCongNhan;
    }
}
