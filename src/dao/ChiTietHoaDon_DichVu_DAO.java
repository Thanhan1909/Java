package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connectDB.ConnectDB;

import entity.ChiTietHD_DichVu;
import entity.DichVu;
import entity.HoaDon;




public class ChiTietHoaDon_DichVu_DAO {
	
	public boolean themChiTietHoaDon(ChiTietHD_DichVu chiTietHoaDon) {
        int n = 0;
        ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO ChiTietHD_DichVu (IDHoaDon, IDDichVu, SoLuong) VALUES (?, ?, ?)";
        try {
            preparedStatement = conN.prepareStatement(sql);
            preparedStatement.setString(1, chiTietHoaDon.getHoaDon().getIdHoaDon());
            preparedStatement.setString(2, chiTietHoaDon.getDichVu().getIdDichVu());
            preparedStatement.setInt(3, chiTietHoaDon.getSoLuong());
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
	public boolean SuaChiTietHoaDon(String chiTietHoaDon, String idHoaDonCu) {
	    int n = 0;
	    ConnectDB.getInstance();
	    Connection conN = ConnectDB.getInstance().getConnection();
	    PreparedStatement preparedStatement = null;
	    String sql = "UPDATE ChiTietHD_DichVu SET IDHoaDon=? WHERE IDHoaDon=?";
	    try {
	        preparedStatement = conN.prepareStatement(sql);
	        preparedStatement.setString(1, chiTietHoaDon);
	        preparedStatement.setString(2, idHoaDonCu);
	        
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
	public ArrayList<ChiTietHD_DichVu>  getAll() {
        ArrayList<ChiTietHD_DichVu> danhSachChiTietHoaDon = new ArrayList<>();
        Connection connection = ConnectDB.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM ChiTietHD_DichVu";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	String IdHD = resultSet.getString("IDHoaDon");
                String IdDichVu = resultSet.getString("IDDichVu");
                int soLuongSP = resultSet.getInt("SoLuong");
                // Lấy thông tin của sản phẩm từ cơ sở dữ liệu
                DichVu dichvu = null;
                HoaDon_DAO dshd = new HoaDon_DAO();
                dshd.getAllHoaDon();
                HoaDon hd = dshd.layHoaDonTheoMaHoaDon(IdHD);
				try {
					DichVu_DAO dsDV = new DichVu_DAO();
					dichvu = dsDV.layDichVuTheoMa(IdDichVu);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                // Tạo đối tượng ChiTietHoaDon
                ChiTietHD_DichVu chiTietHoaDon = new ChiTietHD_DichVu(hd, dichvu, soLuongSP);
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
	public List<ChiTietHD_DichVu> layChiTietHoaDonTheoMaHoaDon(String maHoaDon) {
        List<ChiTietHD_DichVu> danhSachChiTietHoaDon = new ArrayList<>();
        Connection connection = ConnectDB.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM ChiTietHD_DichVu WHERE IDHoaDon = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maHoaDon);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String IdDichVu = resultSet.getString("IDDichVu");
                int soLuongSP = resultSet.getInt("SoLuong");
                // Lấy thông tin của sản phẩm từ cơ sở dữ liệu
                DichVu dichvu = null;
                HoaDon_DAO dshd = new HoaDon_DAO();
                dshd.getAllHoaDon();
                HoaDon hd = dshd.layHoaDonTheoMaHoaDon(maHoaDon);
				try {
					DichVu_DAO dsDV = new DichVu_DAO();
					dichvu = dsDV.layDichVuTheoMa(IdDichVu);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                // Tạo đối tượng ChiTietHoaDon
                ChiTietHD_DichVu chiTietHoaDon = new ChiTietHD_DichVu(hd, dichvu, soLuongSP);
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
	public List<ChiTietHD_DichVu> layChiTietHoaDonTheoMaHoaDonMaSP(String maHoaDon,String maSP) {
        List<ChiTietHD_DichVu> danhSachChiTietHoaDon = new ArrayList<>();
        Connection connection = ConnectDB.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM ChiTietHD_DichVu WHERE IDHoaDon = ? and IDDichVu = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maHoaDon);
            preparedStatement.setString(2, maSP);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String IdDichVu = resultSet.getString("IDDichVu");
                int soLuongSP = resultSet.getInt("SoLuong");
                // Lấy thông tin của sản phẩm từ cơ sở dữ liệu
                DichVu dichvu = null;
                HoaDon_DAO dshd = new HoaDon_DAO();
                dshd.getAllHoaDon();
                HoaDon hd = dshd.layHoaDonTheoMaHoaDon(maHoaDon);
				try {
					DichVu_DAO dsDV = new DichVu_DAO();
					dichvu = dsDV.layDichVuTheoMa(IdDichVu);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                // Tạo đối tượng ChiTietHoaDon
                ChiTietHD_DichVu chiTietHoaDon = new ChiTietHD_DichVu(hd, dichvu, soLuongSP);
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
	public ChiTietHD_DichVu layChiTietHoaDonTheoMaHoaDon1(String maHoaDon) {
	    Connection connection = ConnectDB.getInstance().getConnection();
	    PreparedStatement preparedStatement = null;
	    ChiTietHD_DichVu chiTietHoaDon = null;
	    ResultSet resultSet = null;
	    try {
	        String sql = "SELECT * FROM ChiTietHD_DichVu WHERE IDHoaDon = ?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, maHoaDon);
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            String IdDichVu = resultSet.getString("IDDichVu");
	            int soLuongSP = resultSet.getInt("SoLuong");
	            HoaDon_DAO dshd = new HoaDon_DAO();
	            HoaDon hd = dshd.layHoaDonTheoMaHoaDon(maHoaDon);
	            DichVu_DAO dsDV = new DichVu_DAO();
	            DichVu dichvu = dsDV.layDichVuTheoMa(IdDichVu);

	            chiTietHoaDon = new ChiTietHD_DichVu(hd, dichvu, soLuongSP);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return chiTietHoaDon;
	}

}
