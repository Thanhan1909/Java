package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import connectDB.ConnectDB;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TaiKhoan;

public class TaiKhoan_DAO {
	public boolean themTaiKhoan(TaiKhoan taikhoan) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		try {
			String sql="INSERT INTO TaiKhoan ( IDTaiKhoan, MatKhau, TrangThai, IDNhanVien) values(?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, taikhoan.getIdTaiKhoan());
			pstm.setString(2, taikhoan.getMatKhau());
			pstm.setString(3, taikhoan.getTrangThai());
			pstm.setString(4, taikhoan.getNhanVien().getIdNhanVien());
			n = pstm.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstm.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return n>0;
	}
	public boolean capNhatTaiKhoan(TaiKhoan taikhoan) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update TaiKhoan set MatKhau=?, TrangThai=?, IDNhanVien=? where IDTaiKhoan=? ";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(1, taikhoan.getMatKhau());
			pstm.setString(2, taikhoan.getTrangThai());
			pstm.setString(3, taikhoan.getNhanVien().getIdNhanVien());
			pstm.setString(4, taikhoan.getIdTaiKhoan());
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
	public boolean xoaTheoMaTaiKhoan(String maTK) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "delete from TaiKhoan where IDTaiKhoan ='" + maTK + "'";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	public ArrayList<TaiKhoan> getAllTaiKhoan() {
		ArrayList<TaiKhoan> dsTK = new ArrayList<TaiKhoan>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		System.out.println(1);
		try {
			stm = conN.createStatement();
			String sql = "select * from TaiKhoan";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idtaikhoan = rs.getString("IDTaiKhoan");
				String matkhau = rs.getString("MatKhau");
				String trangthai = rs.getString("TrangThai");
				String idnhanvien = rs.getString("IDNhanVien");
				NhanVien nv = new NhanVien_DAO().getNhanVienTheoMa(idnhanvien);
				TaiKhoan tk = new TaiKhoan(idtaikhoan, matkhau, trangthai, nv);
				dsTK.add(tk);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsTK;
	}
	public TaiKhoan layTaiKhoanTheoMa(String idTaiKhoan) {
	    TaiKhoan tk = null;
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM TaiKhoan WHERE IDTaiKhoan = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idTaiKhoan);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	        	String matkhau = rs.getString("MatKhau");
	            String trangthai = rs.getString("TrangThai");
	            String idnhanvien = rs.getString("IDNhanVien");
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            tk = new TaiKhoan(idTaiKhoan, matkhau, trangthai, nv);
	            
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
	    return tk;
	}
	public TaiKhoan layTaiKhoanTheoMaNV(String maNV) {
	    TaiKhoan tk = null;
	    try {
	    	Connection con = ConnectDB.getInstance().getConnection();
	        String sql = "SELECT * FROM TaiKhoan WHERE idNhanVien = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, maNV);
	        ResultSet rs = stmt.executeQuery();
	        
	        if(rs.next()) {
	            NhanVien_DAO nvDAO = new NhanVien_DAO();
	            NhanVien nv = nvDAO.getNhanVienTheoMa(rs.getString("idNhanVien"));
	            
	            tk = new TaiKhoan(
	                rs.getString("idTaiKhoan"),
	                rs.getString("matKhau"),
	                rs.getString("trangThai"),
	                nv
	            );
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return tk;
	}
	public TaiKhoan getTaiKhoanTheoUserNameAndPassword(String username, String password) {
		TaiKhoan tk = null;
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM TaiKhoan WHERE IDTaiKhoan = ? AND MatKhau = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, username); 
	        stmt.setString(2, password); 
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	        	String matkhau = rs.getString("MatKhau");
	            String trangthai = rs.getString("TrangThai");
	            String idnhanvien = rs.getString("IDNhanVien");
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            tk = new TaiKhoan(username, password, trangthai, nv);
	            
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
	    return tk;
	}
	public boolean capNhatDangNhap() {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update TaiKhoan set TrangThai=?";
		try {
			pstm = conN.prepareStatement(sql);
			String nu = "NULL";
			pstm.setString(1, nu);
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
}
