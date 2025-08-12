package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;

import entity.KhuyenMai;

public class KhuyenMai_DAO {
	public boolean themKhuyenMai(KhuyenMai khuyenmai) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "INSERT INTO KhuyenMai ( IDKhuyenMai, TenKhuyenMai, ChietKhau) VALUES (?,?,?)";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, khuyenmai.getIdKhuyenMai());
			pstm.setString(2, khuyenmai.getTenKhuyenMai());
			pstm.setDouble(3, khuyenmai.getChietKhau());
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
	public boolean suaKhuyenMai(KhuyenMai khuyenmai) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update KhuyenMai set TenKhuyenMai=?, ChietKhau=? where IDKhuyenMai=? ";
		try {
			
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, khuyenmai.getTenKhuyenMai());
			pstm.setDouble(2, khuyenmai.getChietKhau());
			pstm.setString(3, khuyenmai.getIdKhuyenMai());
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
	public boolean xoaKhuyenMai(String idKhuyenMai) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "delete from KhuyenMai where IDKhuyenMai ='" + idKhuyenMai + "'";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	public KhuyenMai layKhuyenMaiTheoMa(String idKhuyenMai) {
	    KhuyenMai km = null;
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM KhuyenMai WHERE IDKhuyenMai = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idKhuyenMai);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            String tenKhuyenMai = rs.getString("TenKhuyenMai");
	            double chietkhau = rs.getDouble("ChietKhau");
	            km = new KhuyenMai(idKhuyenMai, tenKhuyenMai, chietkhau);
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
	    return km;
	}
	public ArrayList<KhuyenMai> getAllKhuyenMai(){
		ArrayList<KhuyenMai>dsKM = new ArrayList<KhuyenMai>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = "select*from KhuyenMai";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idDichvu = rs.getString("IDKhuyenMai");
				String tenSP = rs.getString("TenKhuyenMai");
				double chietkhau = rs.getDouble("ChietKhau");
				KhuyenMai km = new KhuyenMai(idDichvu, tenSP, chietkhau);
				dsKM.add(km);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsKM;
	}
}
