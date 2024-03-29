package ptud.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ptud.Entity.BangDanhGiaCongNhan;
import ptud.Entity.CongNhan;
import static ptud.Mang.connection;

public class DAO_BangDanhGiaCongNhan implements DAOInterface<BangDanhGiaCongNhan> {

    public static DAO_BangDanhGiaCongNhan getInstance() {
        return new DAO_BangDanhGiaCongNhan();
    }

    @Override
    public BangDanhGiaCongNhan get(String id) {
        BangDanhGiaCongNhan bangDanhGia = null;
        try {
            String query = "SELECT * FROM BangDanhGiaCongNhan WHERE maBDG = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String maBDG = resultSet.getString("maBDG");
                CongNhan congNhan = new DAO_CongNhan().get(resultSet.getString("maCN"));
                int nam = resultSet.getInt("nam");
                float diemChuyenCan = resultSet.getFloat("diemChuyenCan");
                float diemChuyenMon = resultSet.getFloat("diemChuyenMon");
                float diemThaiDo = resultSet.getFloat("diemThaiDo");
                float diemHieuSuat = resultSet.getFloat("diemHieuSuat");
                char bac = resultSet.getString("bac").charAt(0);
                bangDanhGia = new BangDanhGiaCongNhan(maBDG, congNhan, nam, diemChuyenCan, diemChuyenMon, diemThaiDo, diemHieuSuat, bac);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bangDanhGia;
    }

    @Override
    public ArrayList<BangDanhGiaCongNhan> getAll() {
        ArrayList<BangDanhGiaCongNhan> dsBangDanhGia = new ArrayList<>();
        try {
            String query = "SELECT * FROM BangDanhGiaCongNhan";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maBDG = resultSet.getString("maBDG");
                CongNhan congNhan = new DAO_CongNhan().get(resultSet.getString("maCN"));
                int nam = resultSet.getInt("nam");
                float diemChuyenCan = resultSet.getFloat("diemChuyenCan");
                float diemChuyenMon = resultSet.getFloat("diemChuyenMon");
                float diemThaiDo = resultSet.getFloat("diemThaiDo");
                float diemHieuSuat = resultSet.getFloat("diemHieuSuat");
                char bac = resultSet.getString("bac").charAt(0);
                BangDanhGiaCongNhan bangDanhGia = new BangDanhGiaCongNhan(maBDG, congNhan, nam, diemChuyenCan, diemChuyenMon, diemThaiDo, diemHieuSuat, bac);

                dsBangDanhGia.add(bangDanhGia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsBangDanhGia;
    }

    @Override
    public boolean insert(BangDanhGiaCongNhan bangDanhGia) {
        try {
            String sql = "INSERT INTO BangDanhGiaCongNhan ([maBDG], [maCN], [nam], [diemChuyenCan], [diemChuyenMon], [diemThaiDo], [diemHieuSuat], [bac]) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bangDanhGia.getId());
            statement.setString(2, bangDanhGia.getCongNhan().getMaCN());
            statement.setInt(3, bangDanhGia.getNam());
            statement.setFloat(4, bangDanhGia.getDiemChuyenCan());
            statement.setFloat(5, bangDanhGia.getDiemchuyenMon());
            statement.setFloat(6, bangDanhGia.getDiemThaiDo());
            statement.setFloat(7, bangDanhGia.getDiemHieuSuat());
            statement.setString(8, String.valueOf(bangDanhGia.getBac()));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(BangDanhGiaCongNhan bangDanhGia) {
        try {
            String sql = "UPDATE [dbo].[BangDanhGiaCongNhan] SET [maCN] = ?, [nam] = ?, [diemChuyenCan] = ?, [diemChuyenMon] = ?, [diemThaiDo] = ?, [diemHieuSuat] = ?, [bac] = ? WHERE [maBDG] = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bangDanhGia.getCongNhan().getMaCN());
            statement.setInt(2, bangDanhGia.getNam());
            statement.setFloat(3, bangDanhGia.getDiemChuyenCan());
            statement.setFloat(4, bangDanhGia.getDiemchuyenMon());
            statement.setFloat(5, bangDanhGia.getDiemThaiDo());
            statement.setFloat(6, bangDanhGia.getDiemHieuSuat());
            statement.setString(7, String.valueOf(bangDanhGia.getBac()));
            statement.setString(8, bangDanhGia.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(String id) {
        try {
            String sql = "DELETE FROM [dbo].[BangDanhGiaCongNhan] WHERE [maBDG] = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //    hàm riêng=
    public BangDanhGiaCongNhan getBangDanhGiaCuaCongNhan(String maCN, int year) {
        BangDanhGiaCongNhan bangDanhGia = null;
        try {
            String query = "SELECT * FROM BangDanhGiaCongNhan WHERE maCN = ? AND nam = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maCN);
            statement.setInt(2, year);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String maBDG = resultSet.getString("maBDG");
                CongNhan congNhan = new DAO_CongNhan().get(resultSet.getString("maCN"));
                float diemChuyenCan = resultSet.getFloat("diemChuyenCan");
                float diemChuyenMon = resultSet.getFloat("diemChuyenMon");
                float diemThaiDo = resultSet.getFloat("diemThaiDo");
                float diemHieuSuat = resultSet.getFloat("diemHieuSuat");
                char bac = resultSet.getString("bac").charAt(0);
                bangDanhGia = new BangDanhGiaCongNhan(maBDG, congNhan, year, diemChuyenCan, diemChuyenMon, diemThaiDo, diemHieuSuat, bac);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bangDanhGia;
    }

    public void addOrUpdate(BangDanhGiaCongNhan bangDanhGiaCongNhan) {
        String sql;
        if (get(bangDanhGiaCongNhan.getId()) == null) {
            // Thêm mới
            sql = "INSERT INTO BangDanhGiaCongNhan (maCN, nam, diemChuyenCan, diemChuyenMon, diemThaiDo, diemHieuSuat, bac, maBDG) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            // Cập nhật
            sql = "UPDATE BangDanhGiaCongNhan SET maCN=?, nam=?, diemChuyenCan=?, diemChuyenMon=?, diemThaiDo=?, diemHieuSuat=?, bac=? WHERE maBDG=?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bangDanhGiaCongNhan.getCongNhan().getMaCN());
            preparedStatement.setInt(2, bangDanhGiaCongNhan.getNam());
            preparedStatement.setFloat(3, bangDanhGiaCongNhan.getDiemChuyenCan());
            preparedStatement.setFloat(4, bangDanhGiaCongNhan.getDiemchuyenMon());
            preparedStatement.setFloat(5, bangDanhGiaCongNhan.getDiemThaiDo());
            preparedStatement.setFloat(6, bangDanhGiaCongNhan.getDiemHieuSuat());
            preparedStatement.setString(7, String.valueOf(bangDanhGiaCongNhan.getBac()));
            preparedStatement.setString(8, bangDanhGiaCongNhan.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu cần
        }
    }

}
