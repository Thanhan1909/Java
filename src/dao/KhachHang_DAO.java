package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.ChucVu;
import entity.KhachHang;
import entity.KhachHang;
import entity.KhachHang;
import entity.KhachHang;

public class KhachHang_DAO {
	public ArrayList<KhachHang> getAllKhachHang() {
		ArrayList<KhachHang> dsKH = new ArrayList<KhachHang>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = "select * from KhachHang";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String IDKhachHang = rs.getString("IDKhachHang");
				String tenKhachHang = rs.getString("TenKhachHang");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				String cccd = rs.getString("CCCD");
				Integer tichDiem = rs.getInt("TichDiem");
				
				//KhachHang nv = new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, gioiTinh, cccd, cv);
				dsKH.add(new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, ngaySinh, cccd, tichDiem));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsKH;
	}
	
	public boolean themKhachHang(KhachHang khachhang) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		int n = 0;
		try {
			String kh = khachhang.autoIdKhachHang();
			String sql="INSERT INTO KhachHang ( IDKhachHang, TenKhachHang, SoDienThoai, NgaySinh, CCCD, TichDiem) values(?,?,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			if ((new KhachHang_DAO().getKhachHangTheoMa(kh)) == null && new KhachHang_DAO().getKhachHangTheoCCCD(khachhang.getCccd()) == null) {
				pstm.setString(1, kh);
				pstm.setString(2, khachhang.getTenKhachHang());
				pstm.setString(3, khachhang.getSoDienThoai());
				pstm.setDate(4, Date.valueOf(khachhang.getNgaySinh()));
				pstm.setString(5, khachhang.getCccd());
				pstm.setInt(6, khachhang.getTichDiem());
				System.out.println("chưa tồn tại khách hàng " + khachhang.getTenKhachHang());
				n = pstm.executeUpdate();
			}


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
	
	public KhachHang getKhachHangTheoCCCD(String cccd) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		KhachHang kh = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM KhachHang WHERE CCCD = ?";
			stmt = con.prepareStatement(sql);
	        stmt.setString(1, cccd);
	        rs = stmt.executeQuery();
			while (rs.next()) {
				String IDKhachHang = rs.getString("IDKhachHang");
				String tenKhachHang = rs.getString("TenKhachHang");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				Integer tichDiem = rs.getInt("TichDiem");
				//KhachHang nv = new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, gioiTinh, cccd, cv);
				kh = new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, ngaySinh, cccd, tichDiem);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return kh;
	 }
	
	public KhachHang getKhachHangTheoSDT(String sdt) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		KhachHang kh = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM KhachHang WHERE SoDienThoai = ?";
			stmt = con.prepareStatement(sql);
	        stmt.setString(1, sdt);
	        rs = stmt.executeQuery();
			while (rs.next()) {
				String IDKhachHang = rs.getString("IDKhachHang");
				String tenKhachHang = rs.getString("TenKhachHang");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				String cccd = rs.getString("CCCD");
				Integer tichDiem = rs.getInt("TichDiem");
				//KhachHang nv = new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, gioiTinh, cccd, cv);
				kh = new KhachHang(IDKhachHang, tenKhachHang, sdt, ngaySinh, cccd, tichDiem);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return kh;
	 }
	public KhachHang getKhachHangTheoMa(String IDKhachHang) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		KhachHang kh = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM KhachHang WHERE IDKhachHang = ?";
			stmt = con.prepareStatement(sql);
	        stmt.setString(1, IDKhachHang);
	        rs = stmt.executeQuery();
			while (rs.next()) {
				String tenKhachHang = rs.getString("TenKhachHang");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				String cccd = rs.getString("CCCD");
				Integer tichDiem = rs.getInt("TichDiem");
				//KhachHang nv = new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, gioiTinh, cccd, cv);
				kh = new KhachHang(IDKhachHang, tenKhachHang, soDienThoai, ngaySinh, cccd, tichDiem);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 return kh;
	 }

	public boolean capNhatKhachHangTheoMa(KhachHang khachhang) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update KhachHang set TenKhachHang=?, NgaySinh=?, CCCD=?, TichDiem=?, SoDienThoai=? where  IDKhachHang = ?";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(6, khachhang.getIdKhachHang());
			pstm.setString(1, khachhang.getTenKhachHang());
			pstm.setDate(2, Date.valueOf(khachhang.getNgaySinh()));
			pstm.setString(3, khachhang.getCccd());
			pstm.setInt(4, khachhang.getTichDiem());
			pstm.setString(5, khachhang.getSoDienThoai());
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
	public boolean capNhatKhachHangTheoMa1(KhachHang khachhang, int a) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update KhachHang set TenKhachHang=?, NgaySinh=?, CCCD=?, TichDiem=?, SoDienThoai=? where  IDKhachHang = ?";
		try {
			pstm = conN.prepareStatement(sql);
			pstm.setString(6, khachhang.getIdKhachHang());
			pstm.setString(1, khachhang.getTenKhachHang());
			pstm.setDate(2, Date.valueOf(khachhang.getNgaySinh()));
			pstm.setString(3, khachhang.getCccd());
			pstm.setInt(4, a);
			pstm.setString(5, khachhang.getSoDienThoai());
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
	public int getCountOfKhachHangInDay(LocalDate date) {
        Connection conn = ConnectDB.getInstance().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        
        try {
            // SQL để đếm số khách hàng được thêm trong ngày cụ thể
            String sql = "SELECT COUNT(*) FROM KhachHang WHERE IDKhachHang LIKE ?";
            pstm = conn.prepareStatement(sql);
            
            String pattern = "KH" + 
                            String.format("%02d", date.getYear() % 100) + 
                            String.format("%02d", date.getMonthValue()) + 
                            String.format("%02d", date.getDayOfMonth()) + 
                            "%";
            
            pstm.setString(1, pattern);
            rs = pstm.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return count;
    }

	public KhachHang getKhachHangTheoCCCDHoacSoDienThoai(String soCCCDHoacSoDienThoai) {
	    // First check for CCCD, if not found, check for phone number
	    KhachHang kh = getKhachHangTheoCCCD(soCCCDHoacSoDienThoai);
	    if (kh == null) {
	        kh = getKhachHangTheoSDT(soCCCDHoacSoDienThoai);
	    }
	    return kh;
	}

	public KhachHang timKhachHangTheoCCCD(String thongTin) {
	    KhachHang khachHang = null;
	    try {
	        Connection con = ConnectDB.getInstance().getConnection();
	        // Kiểm tra xem thông tin là số điện thoại hay căn cước công dân
	        String sql = "SELECT * FROM KhachHang WHERE CCCD = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, thongTin); // Set thông tin vào cả 2 trường

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            // Lấy thông tin khách hàng từ kết quả truy vấn
	            String idKhachHang = rs.getString("IDKhachHang");
	            String tenKhachHang = rs.getString("TenKhachHang");
	            String soDienThoai = rs.getString("SoDienThoai");
	            LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
	            String cccd = rs.getString("CCCD");
	            int tichDiem = rs.getInt("TichDiem");

	            // Tạo đối tượng KhachHang từ kết quả
	            khachHang = new KhachHang(idKhachHang, tenKhachHang, soDienThoai, ngaySinh, cccd, tichDiem);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return khachHang;
	}




}