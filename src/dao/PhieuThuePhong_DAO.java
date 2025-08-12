package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.PhieuThuePhong;
import entity.Phong;


public class PhieuThuePhong_DAO {
	public boolean themPhieuThue(PhieuThuePhong phieuthue, String idHoaDon) {
	    int n = 0;
	    ConnectDB.getInstance();
	    Connection conN = ConnectDB.getInstance().getConnection();
	    PreparedStatement pstm = null;
	    try {
	        String sql = "INSERT INTO PhieuThuePhong (IDPhieuThue, IDKhachHang, IDPhong, IDNhanVien, ThoiGianNhanPhong, ThoiHanGiaoPhong, HieuLuc, IDHoaDon) VALUES (?,?,?,?,?,?,?,?)";
	        pstm = conN.prepareStatement(sql);
	        pstm.setString(1, phieuthue.getIdPhieuThue());
	        pstm.setString(2, phieuthue.getKhachHang().getIdKhachHang());
	        pstm.setString(3, phieuthue.getPhong().getIdPhong());
	        pstm.setString(4, phieuthue.getNhanVienLap().getIdNhanVien());
	        pstm.setDate(5, Date.valueOf(phieuthue.getThoiGianNhanPhong()));
	        pstm.setDate(6, Date.valueOf(phieuthue.getThoiHanGiaoPhong()));
	        pstm.setBoolean(7, Boolean.TRUE);

	        // Nếu idHoaDon null hoặc rỗng hoặc chưa tồn tại trong bảng HoaDon thì setNull, tránh lỗi khóa ngoại
	        if (idHoaDon == null || idHoaDon.trim().isEmpty() || !hoaDonDaTonTai(conN, idHoaDon)) {
	            pstm.setNull(8, java.sql.Types.VARCHAR);
	        } else {
	            pstm.setString(8, idHoaDon);
	        }

	        n = pstm.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try { if (pstm != null) pstm.close(); } catch (Exception e2) { e2.printStackTrace(); }
	    }
	    // Cập nhật lại thời gian nhận/trả phòng với giờ mặc định 12:00
	    LocalDateTime nhan = LocalDateTime.of(phieuthue.getThoiGianNhanPhong(), LocalTime.of(12, 0));
	    LocalDateTime tra = LocalDateTime.of(phieuthue.getThoiHanGiaoPhong(), LocalTime.of(12, 0));
	    if (suaThoiGian(phieuthue.getIdPhieuThue(), nhan, tra)) {
	        n = 1;
	    }
	    return n > 0;
	}

	// Hàm kiểm tra hóa đơn đã tồn tại trong bảng HoaDon chưa
	private boolean hoaDonDaTonTai(Connection con, String idHoaDon) {
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT COUNT(*) FROM HoaDon WHERE IDHoaDon = ?";
	        pstm = con.prepareStatement(sql);
	        pstm.setString(1, idHoaDon);
	        rs = pstm.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try { if (rs != null) rs.close(); if (pstm != null) pstm.close(); } catch (Exception e) { e.printStackTrace(); }
	    }
	    return false;
	}
	public boolean suaPhieuThue(PhieuThuePhong phieuthue) {
	    int n = 0;
	    ConnectDB.getInstance();
	    Connection conN = ConnectDB.getInstance().getConnection();
	    PreparedStatement pstm = null;
	    String sql = "update PhieuThuePhong set IDKhachHang=?, IDPhong=?, IDNhanVien=?, ThoiGianNhanPhong=?, ThoiHanGiaoPhong=?, HieuLuc=? where IDPhieuThue=? ";
	    try {
	        // KIỂM TRA NULL TRƯỚC KHI TRUY CẬP THUỘC TÍNH
	        if (phieuthue == null) {
	            System.out.println("Lỗi: Đối tượng phieuthue null!");
	            return false;
	        }
	        if (phieuthue.getKhachHang() == null) {
	            System.out.println("Lỗi: phieuthue.getKhachHang() null!");
	            return false;
	        }
	        if (phieuthue.getPhong() == null) {
	            System.out.println("Lỗi: phieuthue.getPhong() null!");
	            return false;
	        }
	        if (phieuthue.getNhanVienLap() == null) {
	            System.out.println("Lỗi: phieuthue.getNhanVienLap() null!");
	            return false;
	        }
	        if (phieuthue.getThoiGianNhanPhong() == null) {
	            System.out.println("Lỗi: phieuthue.getThoiGianNhanPhong() null!");
	            return false;
	        }
	        if (phieuthue.getThoiHanGiaoPhong() == null) {
	            System.out.println("Lỗi: phieuthue.getThoiHanGiaoPhong() null!");
	            return false;
	        }
	        pstm = conN.prepareStatement(sql);

	        pstm.setString(1, phieuthue.getKhachHang().getIdKhachHang());
	        pstm.setString(2, phieuthue.getPhong().getIdPhong());
	        pstm.setString(3, phieuthue.getNhanVienLap().getIdNhanVien());
	        pstm.setDate(4, Date.valueOf(phieuthue.getThoiGianNhanPhong()));
	        pstm.setDate(5, Date.valueOf(phieuthue.getThoiHanGiaoPhong()));
	        pstm.setBoolean(6, phieuthue.getHieuLuc());
	        pstm.setString(7, phieuthue.getIdPhieuThue());
	        n = pstm.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstm != null) pstm.close();
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	    }
	    return n > 0;
	}
	
	public boolean suaThoiGian(String ma, LocalDateTime nhan, LocalDateTime tra) {
		int n = 0;
		ConnectDB.getInstance();
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "update PhieuThuePhong set ThoiGianNhanPhong=?, ThoiHanGiaoPhong=? where IDPhieuThue=? ";
		try {
			
			pstm = conN.prepareStatement(sql);
			pstm.setTimestamp(1, Timestamp.valueOf(nhan));
			pstm.setTimestamp(2, Timestamp.valueOf(tra));
			pstm.setString(3, ma);
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
	
	public boolean suaPhieuThue_ThemIDHoaDon(String idHoaDon, String maPhieuThue) {
	    Connection conN = null;
	    PreparedStatement pstmGetPhieu = null;
	    PreparedStatement pstmCheckHD = null;
	    PreparedStatement pstmInsertHD = null;
	    PreparedStatement pstmUpdatePhieu = null;
	    ResultSet rs = null;

	    try {
	        conN = ConnectDB.getInstance().getConnection();
	        conN.setAutoCommit(false);  // Bắt đầu transaction

	        // 1. Lấy thông tin phiếu thuê
	        String getPhieuSQL = "SELECT pt.IDKhachHang, pt.IDNhanVien, pt.ThoiGianNhanPhong " +
	                             "FROM PhieuThuePhong pt WHERE pt.IDPhieuThue = ?";
	        pstmGetPhieu = conN.prepareStatement(getPhieuSQL);
	        pstmGetPhieu.setString(1, maPhieuThue);
	        rs = pstmGetPhieu.executeQuery();

	        if (!rs.next()) {
	            System.err.println("❌ Phiếu thuê không tồn tại: " + maPhieuThue);
	            conN.rollback();
	            return false;
	        }

	        String idKhachHang = rs.getString("IDKhachHang");
	        String idNhanVien = rs.getString("IDNhanVien");
	        Timestamp thoiGianNhanPhong = rs.getTimestamp("ThoiGianNhanPhong");
	        rs.close();

	        // 2. Kiểm tra nếu IDHoaDon đã tồn tại
	        String checkHoaDonSQL = "SELECT 1 FROM HoaDon WHERE IDHoaDon = ?";
	        pstmCheckHD = conN.prepareStatement(checkHoaDonSQL);
	        pstmCheckHD.setString(1, idHoaDon);
	        rs = pstmCheckHD.executeQuery();

	        boolean hoaDonDaTonTai = rs.next();
	        rs.close();

	        if (!hoaDonDaTonTai) {
	            // 3. Tạo hóa đơn nếu chưa tồn tại
	            String insertHoaDonSQL = "INSERT INTO HoaDon (IDHoaDon, IDNhanVien, IDKhachHang, ThoiGianTao, ThoiGianCheckin) " +
	                                     "VALUES (?, ?, ?, GETDATE(), ?)";
	            pstmInsertHD = conN.prepareStatement(insertHoaDonSQL);
	            pstmInsertHD.setString(1, idHoaDon);
	            pstmInsertHD.setString(2, idNhanVien);
	            pstmInsertHD.setString(3, idKhachHang);
	            pstmInsertHD.setTimestamp(4, thoiGianNhanPhong);

	            int hoaDonResult = pstmInsertHD.executeUpdate();
	            if (hoaDonResult <= 0) {
	                conN.rollback();
	                System.err.println("❌ Không thể tạo hóa đơn: " + idHoaDon);
	                return false;
	            }
	        } else {
	            System.out.println("⚠️ IDHoaDon đã tồn tại, bỏ qua bước tạo.");
	        }

	        // 4. Cập nhật phiếu thuê với ID hóa đơn
	        String updatePhieuSQL = "UPDATE PhieuThuePhong SET IDHoaDon = ? WHERE IDPhieuThue = ?";
	        pstmUpdatePhieu = conN.prepareStatement(updatePhieuSQL);
	        pstmUpdatePhieu.setString(1, idHoaDon);
	        pstmUpdatePhieu.setString(2, maPhieuThue);

	        int phieuResult = pstmUpdatePhieu.executeUpdate();
	        if (phieuResult > 0) {
	            conN.commit();
	            System.out.println("✅ Đã cập nhật phiếu thuê với hóa đơn thành công!");
	            return true;
	        } else {
	            conN.rollback();
	            System.err.println("❌ Không thể cập nhật phiếu thuê với hóa đơn");
	            return false;
	        }

	    } catch (SQLException e) {
	        try {
	            if (conN != null) conN.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        System.err.println("❌ Lỗi SQL: " + e.getMessage());
	        e.printStackTrace();
	        return false;

	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmGetPhieu != null) pstmGetPhieu.close();
	            if (pstmCheckHD != null) pstmCheckHD.close();
	            if (pstmInsertHD != null) pstmInsertHD.close();
	            if (pstmUpdatePhieu != null) pstmUpdatePhieu.close();
	            if (conN != null) {
	                conN.setAutoCommit(true);
	                conN.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}


	
	public boolean xoaPhieuThue(String idPhieuThue) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "delete from PhieuThuePhong where IDPhieuThue ='" + idPhieuThue + "'";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	public PhieuThuePhong layPhieuThueTheoMa(String idPhieuThue) {
	    PhieuThuePhong pt = null;
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM PhieuThuePhong WHERE IDPhieuThue = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idPhieuThue);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	        	String idkhachhang = rs.getString("IDKhachHang");
	            String idphong = rs.getString("IDPhong");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);
	            Phong_DAO dsp = new Phong_DAO();
	            dsp.getAllPhong();
	            Phong p = dsp.getPhongTheoMa(idphong);
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            pt = new PhieuThuePhong(idPhieuThue, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	            
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
	    return pt;
	}
	

	
	public PhieuThuePhong layPhieuThueTheoMaPhong_1Phong(String idPhong) {
		PhieuThuePhong pt = new PhieuThuePhong();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM PhieuThuePhong WHERE IDPhong = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idPhong);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	        	String idphieuthue = rs.getString("IDPhieuThue");
	        	String idkhachhang = rs.getString("IDKhachHang");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);
	            Phong_DAO dsp = new Phong_DAO();
	            Phong p = dsp.getPhongTheoMa(idPhong);
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            pt = new PhieuThuePhong(idphieuthue, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
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
	    return pt;
	}
	public PhieuThuePhong layPhieuThueTheoMaPhongTrue(String idPhong) {
		PhieuThuePhong pt = new PhieuThuePhong();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM PhieuThuePhong WHERE IDPhong = ? AND HieuLuc = 'True'";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idPhong);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	        	String idphieuthue = rs.getString("IDPhieuThue");
	        	String idkhachhang = rs.getString("IDKhachHang");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);
	            Phong_DAO dsp = new Phong_DAO();
	            Phong p = dsp.getPhongTheoMa(idPhong);
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            pt = new PhieuThuePhong(idphieuthue, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
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
	    return pt;
	}
	
	public ArrayList<PhieuThuePhong> layPhieuThueTheoMaPhong(String idPhong) {
		ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM PhieuThuePhong WHERE IDPhong = ? and HieuLuc = 'True'";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, idPhong);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	        	String idphieuthue = rs.getString("IDPhieuThue");
	        	String idkhachhang = rs.getString("IDKhachHang");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);
	            Phong_DAO dsp = new Phong_DAO();
	            Phong p = dsp.getPhongTheoMa(idPhong);
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            PhieuThuePhong pt = new PhieuThuePhong(idphieuthue, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	            dsPT.add(pt);
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
	    return dsPT;
	}
	public ArrayList<PhieuThuePhong> getAllPhieuThue(){
		ArrayList<PhieuThuePhong>dsPT = new ArrayList<PhieuThuePhong>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = "select * from PhieuThuePhong";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idphieuthu = rs.getString("IDPhieuThue");
				String idkhachhang = rs.getString("IDKhachHang");
	            String idphong = rs.getString("IDPhong");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);
	            Phong_DAO dsp = new Phong_DAO();
	            dsp.getAllPhong();
	            Phong p = dsp.getPhongTheoMa(idphong);
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            PhieuThuePhong pt = new PhieuThuePhong(idphieuthu, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	            dsPT.add(pt);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsPT;
	}
	
	
	
	// lọc hiệu lực
	public ArrayList<PhieuThuePhong> layPhieuThueTheoMaKH(String maKH){
		ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
		try {
			String sql = "select * from PhieuThuePhong where IDKhachHang = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maKH);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String idphieuthu = rs.getString("IDPhieuThue");
				String idkhachhang = rs.getString("IDKhachHang");
	            String idphong = rs.getString("IDPhong");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);
	            Phong_DAO dsp = new Phong_DAO();
	            dsp.getAllPhong();
	            Phong p = dsp.getPhongTheoMa(idphong);
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            PhieuThuePhong pt = new PhieuThuePhong(idphieuthu, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	            if (hieuLuc == true) {
		            dsPT.add(pt);
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsPT;
	}
	public ArrayList<PhieuThuePhong> layPhieuThueTheoMaHD(String maHD){
	    ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	        String sql = "SELECT * FROM PhieuThuePhong WHERE IDHoaDon = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, maHD);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            String idphieuthu = rs.getString("IDPhieuThue");
	            String idkhachhang = rs.getString("IDKhachHang");
	            String idphong = rs.getString("IDPhong");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();

	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);

	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);

	            Phong_DAO dsp = new Phong_DAO();
	            dsp.getAllPhong();
	            Phong p = dsp.getPhongTheoMa(idphong);

	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            if (nv != null && kh != null && p != null) {
	                PhieuThuePhong pt = new PhieuThuePhong(idphieuthu, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	                dsPT.add(pt);
	            } else {
	                System.out.println("Dữ liệu không hợp lệ cho PhieuThuePhong với ID: " + idphieuthu);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return dsPT;
	}

	
	//lay PT theo maHD tra ve 1 PT
	public PhieuThuePhong layPhieuThueTheoMaHD_1PT(String maHD){
	    Connection con = ConnectDB.getInstance().getConnection();
	    PhieuThuePhong pt = null;  // Khởi tạo null
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "select * from PhieuThuePhong where IDHoaDon = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, maHD);
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            String idphieuthu = rs.getString("IDPhieuThue");
	            String idkhachhang = rs.getString("IDKhachHang");
	            String idphong = rs.getString("IDPhong");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();

	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();  // Nếu dùng cache, ok. Nếu không cần xem lại
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);

	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang(); // Tương tự
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);

	            Phong_DAO dsp = new Phong_DAO();
	            dsp.getAllPhong();
	            Phong p = dsp.getPhongTheoMa(idphong);

	            Boolean hieuLuc = rs.getBoolean("HieuLuc");

	            pt = new PhieuThuePhong(idphieuthu, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return pt;
	}

	
	//lay Phieu thue theo hieu luc 
	public ArrayList<PhieuThuePhong> layPhieuThueTheoHieuLuc(boolean a){
		ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
		try {
			String sql = "select * from PhieuThuePhong where HieuLuc = ? ";
			stmt = con.prepareStatement(sql);
			stmt.setBoolean(1, a);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String idphieuthu = rs.getString("IDPhieuThue");
				String idkhachhang = rs.getString("IDKhachHang");
	            String idphong = rs.getString("IDPhong");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);
	            Phong_DAO dsp = new Phong_DAO();
	            dsp.getAllPhong();
	            Phong p = dsp.getPhongTheoMa(idphong);
	            PhieuThuePhong pt = new PhieuThuePhong(idphieuthu, kh, p, nv, thoigiancheckin, thoigiancheckout, a);
	            dsPT.add(pt);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsPT;
	}
	
	//thongke
	
	public ArrayList<Map<LocalDate, Integer>> thongKeTheoNgay(LocalDate dateA, LocalDate dateB){
		ArrayList<Map<LocalDate, Integer>> kq = new ArrayList<Map<LocalDate, Integer>>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;

		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT CAST(ThoiGianNhanPhong AS DATE) AS Ngay "
					+ "FROM PhieuThuePhong "
					+ "WHERE CAST(ThoiGianNhanPhong AS DATE) BETWEEN '%s' AND '%s' and IDHoaDon IS NOT NULL "
					+ "GROUP BY CAST(ThoiGianNhanPhong AS DATE)",
					dateA.format(DateTimeFormatter.ISO_DATE), 
					dateB.format(DateTimeFormatter.ISO_DATE));
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int dem = 0;
				Map<LocalDate, Integer> map = new HashMap<LocalDate, Integer>();
				LocalDate ngayLap = rs.getDate("Ngay").toLocalDate();
				dem += layPhieuThueTheoNgay(ngayLap);
				map.put(ngayLap, dem);
				System.out.println(map);
				kq.add(map);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return kq;
	}
	public int layPhieuThueTheoNgay(LocalDate date){
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement stm = null;
		int dem = 0;
		try {
			String sql = "select IDPhong from PhieuThuePhong where CAST(ThoiGianNhanPhong AS DATE) = ? and IDHoaDon IS NOT NULL";
			stm = conN.prepareStatement(sql);
			stm.setDate(1, Date.valueOf(date));
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
	            String idphong = rs.getString("IDPhong");
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);
	            if (p.getLoaiPhong() == LoaiPhong.PHONGDON || p.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            	dem += 2;
	            } else if (p.getLoaiPhong() == LoaiPhong.PHONGGIADINH) {
	            	dem += 4;
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dem;
	}
	public ArrayList<Map<Integer, Integer>> thongKeTheoThang(int month, int year){
		ArrayList<Map<Integer, Integer>> kq = new ArrayList<Map<Integer, Integer>>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;

		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT MONTH(ThoiGianNhanPhong) AS Month "
					+ "FROM PhieuThuePhong "
					+ "WHERE YEAR(ThoiGianNhanPhong) = '%d' and IDHoaDon IS NOT NULL "
					+ "GROUP BY MONTH(ThoiGianNhanPhong)", year);
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int dem = 0;
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				int thang = rs.getInt("Month");
				dem += layPhieuThueTheoThang(thang);
				map.put(thang, dem);
				System.out.println(map);
				kq.add(map);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return kq;
	}
	public int layPhieuThueTheoThang(int thang){
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement stm = null;
		int dem = 0;
		try {
			String sql = "select IDPhong from PhieuThuePhong where MONTH(ThoiGianNhanPhong) = ? and IDHoaDon IS NOT NULL";
			stm = conN.prepareStatement(sql);
			stm.setInt(1, thang);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
	            String idphong = rs.getString("IDPhong");
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);
	            if (p.getLoaiPhong() == LoaiPhong.PHONGDON || p.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            	dem += 2;
	            } else if (p.getLoaiPhong() == LoaiPhong.PHONGGIADINH) {
	            	dem += 4;
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dem;
	}
	public ArrayList<Map<Integer, Integer>> thongKeTheoNam(int year){
		ArrayList<Map<Integer, Integer>> kq = new ArrayList<Map<Integer, Integer>>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT Year(ThoiGianNhanPhong) AS Year FROM PhieuThuePhong WHERE Year(ThoiGianNhanPhong) BETWEEN %d AND %d GROUP BY Year(ThoiGianNhanPhong)",year - 2, year + 2);
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int dem = 0;
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				int nam = rs.getInt("Year");
				dem += layPhieuThueTheoNam(nam);
				map.put(nam, dem);
				System.out.println(map);
				kq.add(map);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return kq;
	}
	public int layPhieuThueTheoNam(int nam){
		Connection conN = ConnectDB.getInstance().getConnection();
		PreparedStatement stm = null;
		int dem = 0;
		try {
			String sql = "select IDPhong from PhieuThuePhong where Year(ThoiGianNhanPhong) = ? and IDHoaDon IS NOT NULL";
			stm = conN.prepareStatement(sql);
			stm.setInt(1, nam);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
	            String idphong = rs.getString("IDPhong");
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);
	            if (p.getLoaiPhong() == LoaiPhong.PHONGDON || p.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            	dem += 2;
	            } else if (p.getLoaiPhong() == LoaiPhong.PHONGGIADINH) {
	            	dem += 4;
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dem;
	}
	public int tongKHAtoB(LocalDate dateA, LocalDate dateB) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		int dem = 0;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT IDPhong FROM PhieuThuePhong "
					+ "WHERE CAST(ThoiGianNhanPhong AS DATE) BETWEEN '%s' AND '%s' and IDHoaDon IS NOT NULL",
					dateA.format(DateTimeFormatter.ISO_DATE), 
					dateB.format(DateTimeFormatter.ISO_DATE));
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
	            String idphong = rs.getString("IDPhong");
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);
	            if (p.getLoaiPhong() == LoaiPhong.PHONGDON || p.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            	dem += 2;
	            } else if (p.getLoaiPhong() == LoaiPhong.PHONGGIADINH) {
	            	dem += 4;
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dem;
	}
	public int tongKHThang(int month, int year) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		int dem = 0;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT IDPhong FROM PhieuThuePhong "
					+ "WHERE YEAR(ThoiGianNhanPhong) = %d AND MONTH(ThoiGianNhanPhong) = %d and IDHoaDon IS NOT NULL",
					year, 
					month);
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
	            String idphong = rs.getString("IDPhong");
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);
	            if (p.getLoaiPhong() == LoaiPhong.PHONGDON || p.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            	dem += 2;
	            } else if (p.getLoaiPhong() == LoaiPhong.PHONGGIADINH) {
	            	dem += 4;
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dem;
	}
	public int tongKHTNam(int year) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		int dem = 0;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT IDPhong FROM PhieuThuePhong "
					+ "WHERE YEAR(ThoiGianNhanPhong) = %d and IDHoaDon IS NOT NULL",
					year);
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
	            String idphong = rs.getString("IDPhong");
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);
	            if (p.getLoaiPhong() == LoaiPhong.PHONGDON || p.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            	dem += 2;
	            } else if (p.getLoaiPhong() == LoaiPhong.PHONGGIADINH) {
	            	dem += 4;
	            }
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dem;
	}
	public LocalDateTime getThoiGianNhanPhong(String maPhieuThue) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		LocalDateTime time = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT ThoiGianNhanPhong FROM PhieuThuePhong "
					+ "WHERE IDPhieuThue = '%s'",
					maPhieuThue);
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				time = rs.getTimestamp("ThoiGianNhanPhong").toLocalDateTime();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return time;
	}
	public LocalDateTime getThoiGianTraPhong(String maPhieuThue) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		LocalDateTime time = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT ThoiHanGiaoPhong FROM PhieuThuePhong "
					+ "WHERE IDPhieuThue = '%s'",
					maPhieuThue);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				time = rs.getTimestamp("ThoiHanGiaoPhong").toLocalDateTime();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return time;
	}
	
	public ArrayList<PhieuThuePhong> getPhieuThueTheoMaPhong(String maPhong, LocalDateTime nhan, LocalDateTime tra) {
		ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("select * from PhieuThuePhong where IDPhong = '%s' and ThoiGianNhanPhong = '%s' and ThoiHanGiaoPhong = '%s'", maPhong, formatDateTime(nhan), formatDateTime(tra));
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idphieuthu = rs.getString("IDPhieuThue");
				String idkhachhang = rs.getString("IDKhachHang");
	            String idphong = rs.getString("IDPhong");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();
	            NhanVien_DAO dsnv = new NhanVien_DAO();
	            dsnv.getAllNhanVien();
	            NhanVien nv = dsnv.getNhanVienTheoMa(idnhanvien);
	            KhachHang_DAO dskh = new KhachHang_DAO();
	            dskh.getAllKhachHang();
	            KhachHang kh = dskh.getKhachHangTheoMa(idkhachhang);
	            Phong_DAO dsp = new Phong_DAO();
	            dsp.getAllPhong();
	            Phong p = dsp.getPhongTheoMa(idphong);
	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            PhieuThuePhong pt = new PhieuThuePhong(idphieuthu, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	            dsPT.add(pt);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsPT;
	}
	
	public ArrayList<PhieuThuePhong> getPhieuThueTheoMaPhong(String maPhong, LocalDate nhan, LocalDate tra) {
	    ArrayList<PhieuThuePhong> dsPT = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = ConnectDB.getInstance().getConnection();
	        // Truy vấn kiểm tra trùng lặp thời gian thuê
	        String sql = "SELECT * FROM PhieuThuePhong WHERE IDPhong = ? " +
	                     "AND HieuLuc = 1 " +
	                     "AND ((? BETWEEN ThoiGianNhanPhong AND ThoiHanGiaoPhong) " + // Ngày nhận mới nằm trong khoảng thời gian đã đặt
	                     "OR (? BETWEEN ThoiGianNhanPhong AND ThoiHanGiaoPhong) " + // Ngày trả mới nằm trong khoảng thời gian đã đặt
	                     "OR (ThoiGianNhanPhong BETWEEN ? AND ?))"; // Khoảng thời gian đã đặt nằm trong khoảng thời gian mới
	        
	        pstm = conn.prepareStatement(sql);
	        pstm.setString(1, maPhong);
	        pstm.setDate(2, Date.valueOf(nhan));
	        pstm.setDate(3, Date.valueOf(tra));
	        pstm.setDate(4, Date.valueOf(nhan));
	        pstm.setDate(5, Date.valueOf(tra));
	        
	        rs = pstm.executeQuery();
	        
	        while (rs.next()) {
	            String idphieuthu = rs.getString("IDPhieuThue");
	            String idkhachhang = rs.getString("IDKhachHang");
	            String idphong = rs.getString("IDPhong");
	            String idnhanvien = rs.getString("IDNhanVien");
	            LocalDate thoigiancheckin = rs.getDate("ThoiGianNhanPhong").toLocalDate();
	            LocalDate thoigiancheckout = rs.getDate("ThoiHanGiaoPhong").toLocalDate();

	            // Lấy thông tin liên quan
	            NhanVien nv = new NhanVien_DAO().getNhanVienTheoMa(idnhanvien);
	            KhachHang kh = new KhachHang_DAO().getKhachHangTheoMa(idkhachhang);
	            Phong p = new Phong_DAO().getPhongTheoMa(idphong);

	            Boolean hieuLuc = rs.getBoolean("HieuLuc");
	            PhieuThuePhong pt = new PhieuThuePhong(idphieuthu, kh, p, nv, thoigiancheckin, thoigiancheckout, hieuLuc);
	            dsPT.add(pt);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstm != null) pstm.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return dsPT;
	}

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); 
    }

	public LocalDateTime getThoiGianNhanPhong1(String maPhieuThue) {
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;
		LocalDateTime time = null;
		try {
			stm = conN.createStatement();
			String sql = String.format("SELECT ThoiGianNhanPhong FROM PhieuThuePhong "
					+ "WHERE IDPhieuThue = '%s'",
					maPhieuThue);
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				time = rs.getTimestamp("ThoiGianNhanPhong").toLocalDateTime();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return time;
	}
	public boolean kiemTraKhachDaDatPhong(String maKH, String maPhong) {
	    boolean daDat = false;

	    // Truy vấn kiểm tra khách đã thuê phòng chưa, dựa trên trạng thái của phòng
	    String sql = "SELECT * FROM PhieuThuePhong WHERE IDKhachHang = ? AND IDPhong = ?";

	    try (Connection con = ConnectDB.getInstance().getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        if (con != null && !con.isClosed()) {
	            stmt.setString(1, maKH);     // Gắn ID khách hàng
	            stmt.setString(2, maPhong);  // Gắn ID phòng

	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    daDat = true; // Nếu có bản ghi trả về, khách đã thuê phòng
	                }
	            } catch (SQLException e) {
	                System.out.println("Lỗi khi truy vấn: " + e.getMessage());
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("Kết nối cơ sở dữ liệu bị đóng.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return daDat;
	}



}
