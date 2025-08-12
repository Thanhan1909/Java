package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import connectDB.ConnectDB;
import entity.DichVu;

public class DichVu_DAO {
	public ArrayList<DichVu> getAllDichVu(){
		ArrayList<DichVu>dsDV = new ArrayList<DichVu>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = "select*from DichVu";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idDichvu = rs.getString("IDDichVu");
				String tenSP = rs.getString("TenSanPham");
				int sl = rs.getInt("soLuong");
				double dongia = rs.getDouble("DonGia");
				DichVu dv = new DichVu(idDichvu, tenSP, sl, dongia);
				dsDV.add(dv);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsDV;
	}
	public boolean themDichVu(DichVu dichvu) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "INSERT INTO DichVu ( IDDichVu, TenSanPham, SoLuong, DonGia) VALUES (?,?,?,?)";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, dichvu.getIdDichVu());
			pstm.setString(2, dichvu.getTenSanPham());
			pstm.setInt(3, dichvu.getSoLuong());
			pstm.setDouble(4, dichvu.getDonGia());
			n = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return n > 0;
	}
	public boolean suaDichVu(DichVu dichvu) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update DichVu set TenSanPham=?, SoLuong=?, DonGia=? where IDDichVu=? ";
		try {
			pstm = conN.prepareStatement(sql);
			
			pstm.setString(1, dichvu.getTenSanPham());
			pstm.setInt(2, dichvu.getSoLuong());
			pstm.setDouble(3, dichvu.getDonGia());
			pstm.setString(4, dichvu.getIdDichVu());
			
			n = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return n > 0;
	}
	public boolean capNhatDichVu(DichVu dichvu, int a) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update DichVu set TenSanPham=?, SoLuong=?, DonGia=? where IDDichVu=? ";
		try {
			pstm = conN.prepareStatement(sql);
			
			pstm.setString(1, dichvu.getTenSanPham());
			pstm.setInt(2, a);
			pstm.setDouble(3, dichvu.getDonGia());
			pstm.setString(4, dichvu.getIdDichVu());
			
			n = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return n > 0;
	}
	public boolean xoaDichVu(String idDichVu) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "delete from DichVu where IDDichVu ='" + idDichVu + "'";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	public DichVu layDichVuTheoMa(String idDichVu) {
	    DichVu dv = null;
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM DichVu WHERE IDDichVu = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idDichVu);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	        	String tenSP = rs.getString("TenSanPham");
				int sl = rs.getInt("soLuong");
				double dongia = rs.getDouble("DonGia");
	            dv = new DichVu(idDichVu, tenSP, sl, dongia);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return dv;
	}
}
