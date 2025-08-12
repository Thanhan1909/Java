package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietHD_DichVu;
import entity.ChiTietHD_Phong;
import entity.DichVu;
import entity.HoaDon;
import entity.Phong;

public class ChiTietHoaDon_Phong_DAO {
	public boolean themChiTietHoaDon_Phong(ChiTietHD_Phong chiTietHoaDon) {
        int n = 0;
        ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO ChiTietHD_Phong (IDHoaDon, IDPhong, GioCheckout) VALUES (?, ?, ?)";
        try {
            preparedStatement = conN.prepareStatement(sql);
            preparedStatement.setString(1, chiTietHoaDon.getHoaDon().getIdHoaDon());
            preparedStatement.setString(2, chiTietHoaDon.getPhong().getIdPhong());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(chiTietHoaDon.getGioCheckout())); // ThoiGianCheckin là LocalDateTime
            n = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n > 0;
    }
	public List<ChiTietHD_Phong> layChiTietHoaDon_PhongTheoMaHoaDon(String maHoaDon) {
        List<ChiTietHD_Phong> danhSachChiTietHoaDon = new ArrayList<>();
        Connection connection = ConnectDB.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM ChiTietHD_Phong WHERE IDHoaDon = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maHoaDon);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String IdPhong = resultSet.getString("IDPhong");
                LocalDateTime gioCheckout = resultSet.getTimestamp("GioCheckout").toLocalDateTime();
                // Lấy thông tin của sản phẩm từ cơ sở dữ liệu
                Phong phong = null;
                HoaDon_DAO dshd = new HoaDon_DAO();
                dshd.getAllHoaDon();
                HoaDon hd = dshd.layHoaDonTheoMaHoaDon(maHoaDon);
				try {
					Phong_DAO dsp = new Phong_DAO();
					dsp.getAllPhong();
					phong = dsp.getPhongTheoMa(IdPhong);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                // Tạo đối tượng ChiTietHoaDon
                ChiTietHD_Phong chiTietHoaDon = new ChiTietHD_Phong(hd, phong, gioCheckout);
                danhSachChiTietHoaDon.add(chiTietHoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhSachChiTietHoaDon;
    }
}
