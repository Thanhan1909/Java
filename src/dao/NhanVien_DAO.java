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
import entity.NhanVien;

public class NhanVien_DAO {
	public ArrayList<NhanVien> getAllNhanVien() {
		ArrayList<NhanVien> dsNV = new ArrayList<NhanVien>();
		Connection conN = ConnectDB.getInstance().getConnection();
		Statement stm = null;

		try {
			stm = conN.createStatement();
			String sql = "select * from NhanVien";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String idNhanVien = rs.getString("IDNhanVien");
				String tenNhanVien = rs.getString("TenNhanVien");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				boolean gioiTinh = rs.getBoolean("GioiTinh");
				String cccd = rs.getString("CCCD");
				int chucVu = rs.getInt("ChucVu");
				ChucVu cv = null;
				
				if(chucVu == 1) {
					cv = ChucVu.NHANVIENLETAN;
				} else if (chucVu == 2) {
					cv = ChucVu.NGUOIQUANLY;
				}
				
//				if(chucVu.equalsIgnoreCase(ChucVu.NHANVIENLETAN.toString())) {
//					cv = ChucVu.NHANVIENLETAN;
//					
//				} else if(chucVu.equalsIgnoreCase(ChucVu.NGUOIQUANLY.toString())) {
//					cv = ChucVu.NGUOIQUANLY;
//				}
				
				
				//NhanVien nv = new NhanVien(idNhanVien, tenNhanVien, soDienThoai, gioiTinh, cccd, cv);
				dsNV.add(new NhanVien(idNhanVien, tenNhanVien, soDienThoai, ngaySinh, gioiTinh, cccd, cv));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsNV;
	}
	
	public boolean themNhanVien(NhanVien nhanvien) {
	    ConnectDB.getInstance();
	    Connection conn = ConnectDB.getInstance().getConnection();
	    PreparedStatement pstm = null;
	    int n = 0;
	    try {
	        String nv = nhanvien.getIdNhanVien(); // Sử dụng mã đã được tạo từ generateMaNV()
	        String sql = "INSERT INTO NhanVien (IDNhanVien, TenNhanVien, SoDienThoai, NgaySinh, GioiTinh, CCCD, ChucVu) VALUES (?,?,?,?,?,?,?)";
	        pstm = conn.prepareStatement(sql);

	        // Kiểm tra mã NV và CCCD không trùng
	        if ((new NhanVien_DAO().getNhanVienTheoMa(nv)) == null && 
	            (new NhanVien_DAO().getNhanVienTheoCCCD(nhanvien.getCccd())) == null) {
	            
	            // Set các giá trị cho PreparedStatement
	            pstm.setString(1, nv);
	            pstm.setString(2, nhanvien.getTenNhanVien());
	            pstm.setString(3, nhanvien.getSoDienThoai());
	            pstm.setDate(4, Date.valueOf(nhanvien.getNgaySinh()));
	            pstm.setInt(5, nhanvien.isGioiTinh() ? 1 : 0);
	            pstm.setString(6, nhanvien.getCccd());

	            // Xử lý chức vụ
	            int cv = 0;
	            if(nhanvien.getChucVu().toString().equalsIgnoreCase("Nhân viên lễ tân")) {
	                cv = 1;
	            } else if (nhanvien.getChucVu().toString().equalsIgnoreCase("Người quản lý")) {
	                cv = 2;
	            } 
	            pstm.setInt(7, cv);

	            // Thực hiện câu lệnh SQL
	            n = pstm.executeUpdate();
	        } else {
	            // Thông báo nếu trùng mã NV hoặc CCCD
	            System.out.println("Mã nhân viên hoặc CCCD đã tồn tại!");
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if(pstm != null) {
	                pstm.close();
	            }
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	    }
	    return n > 0;
	}
	
	public NhanVien getNhanVienTheoMa(String ma) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		NhanVien nv = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM NhanVien WHERE IDNhanVien = ?";
			stmt = con.prepareStatement(sql);
	        stmt.setString(1, ma);
	        rs = stmt.executeQuery();
			while (rs.next()) {

				String tenNhanVien = rs.getString("TenNhanVien");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				boolean gioiTinh = rs.getBoolean("GioiTinh");
				String cccd = rs.getString("CCCD");
//				String chucVu = rs.getString("ChucVu");
//				ChucVu cv = null;
//				if(chucVu.equalsIgnoreCase(ChucVu.NHANVIENLETAN.toString())) {
//					cv = ChucVu.NHANVIENLETAN;
//					
//				} else if(chucVu.equalsIgnoreCase(ChucVu.NGUOIQUANLY.toString())) {
//					cv = ChucVu.NGUOIQUANLY;
//				}
				int chucVu = rs.getInt("ChucVu");
				ChucVu cv = null;
				
				if(chucVu == 1) {
					cv = ChucVu.NHANVIENLETAN;
				} else if (chucVu == 2) {
					cv = ChucVu.NGUOIQUANLY;
				}
				
				//NhanVien nv = new NhanVien(idNhanVien, tenNhanVien, soDienThoai, gioiTinh, cccd, cv);
				nv = new NhanVien(ma, tenNhanVien, soDienThoai, ngaySinh, gioiTinh, cccd, cv);
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
		 return nv;
	 }
	public boolean capNhatNhanVien(NhanVien nhanvien) {
	    int n = 0;
	    ConnectDB.getInstance();
	    Connection conN = ConnectDB.getInstance().getConnection();
	    PreparedStatement pstm = null;
	    String sql = "update NhanVien set TenNhanVien=?, SoDienThoai=?, NgaySinh=?, GioiTinh=?, CCCD=?, ChucVu=? where IDNhanVien=? ";
	    try {
	        pstm = conN.prepareStatement(sql);

	        pstm.setString(1, nhanvien.getTenNhanVien());
	        pstm.setString(2, nhanvien.getSoDienThoai());
	        pstm.setDate(3, Date.valueOf(nhanvien.getNgaySinh()));
	        pstm.setInt(4, nhanvien.isGioiTinh() ? 1 : 0);
	        pstm.setString(5, nhanvien.getCccd());

	        // Sửa phần xử lý chức vụ
	        int cv = 0;
	        if (nhanvien.getChucVu().toString().equalsIgnoreCase("Nhân viên lễ tân")) {
	            cv = 1;
	        } else if (nhanvien.getChucVu().toString().equalsIgnoreCase("Người quản lý")) {
	            cv = 2;
	        }
	        
	        // Debug để kiểm tra giá trị
	        System.out.println("Chức vụ enum: " + nhanvien.getChucVu());
	        System.out.println("Giá trị cv: " + cv);

	        pstm.setInt(6, cv);
	        pstm.setString(7, nhanvien.getIdNhanVien());

	        n = pstm.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstm != null) {
	                pstm.close(); 
	            }
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	    }
	    return n > 0;
	}

	
	public boolean xoaTheoMaNhanVien(String maNV) {
		ConnectDB.getInstance();
		Connection conn = ConnectDB.getInstance().getConnection();
		PreparedStatement pstm = null;
		String sql = "delete from NhanVien where IdNhanVien ='" + maNV + "'";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean isMaNVExists(String maNV) {
	    Connection conn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    String sql = "SELECT COUNT(*) FROM NhanVien WHERE IDNhanVien = ?";
	    
	    try {
	        conn = ConnectDB.getInstance().getConnection();
	        pstm = conn.prepareStatement(sql);
	        pstm.setString(1, maNV);
	        rs = pstm.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
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
	    return false;
	}
	
	public int getCountOfNhanVienInDay(LocalDate date) {
        Connection conn = ConnectDB.getInstance().getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        
        try {
            // SQL để đếm số nhân viên được thêm trong ngày cụ thể
            String sql = "SELECT COUNT(*) FROM NhanVien WHERE IDNhanVien LIKE ?";
            pstm = conn.prepareStatement(sql);
            
            String pattern = "NV" + 
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
//	public static int demSLNhanVien(int ns) throws SQLException {
//        Connection conn = ConnectDB.getInstance().getConnection();
//        Statement stmt = null;
//
//        try {
//            stmt = conn.createStatement();
//            String sql = "SELECT YEAR(NgaySinh) AS NamSinh, COUNT(*) AS SoLuongNhanVien "
//                    + "FROM NhanVien "
//                    + "GROUP BY YEAR(NgaySinh)";
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                if (rs.getInt("NamSinh") == ns) {
//                    return rs.getInt("SoLuongNhanVien");
//                }
//            }
//            return 0;
//        } finally {
//            if (stmt != null) {
//                stmt.close();
//            }
//        }
//    }
	
	public NhanVien getNhanVienTheoCCCD(String ma) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		NhanVien nv = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM NhanVien WHERE CCCD = ?";
			stmt = con.prepareStatement(sql);
	        stmt.setString(1, ma);
	        rs = stmt.executeQuery();
			while (rs.next()) {
				String maNhanVien = rs.getString("IDNhanVien");
				String tenNhanVien = rs.getString("TenNhanVien");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				boolean gioiTinh = rs.getBoolean("GioiTinh");
//				String chucVu = rs.getString("ChucVu");
//				ChucVu cv = null;
//				if(chucVu.equalsIgnoreCase(ChucVu.NHANVIENLETAN.toString())) {
//					cv = ChucVu.NHANVIENLETAN;
//					
//				} else if(chucVu.equalsIgnoreCase(ChucVu.NGUOIQUANLY.toString())) {
//					cv = ChucVu.NGUOIQUANLY;
//				}
				int chucVu = rs.getInt("ChucVu");
				ChucVu cv = null;
				
				if(chucVu == 1) {
					cv = ChucVu.NHANVIENLETAN;
				} else if (chucVu == 2) {
					cv = ChucVu.NGUOIQUANLY;
				}
				
				//NhanVien nv = new NhanVien(idNhanVien, tenNhanVien, soDienThoai, gioiTinh, cccd, cv);
				nv = new NhanVien(maNhanVien, tenNhanVien, soDienThoai, ngaySinh, gioiTinh, ma, cv);
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
		 return nv;
	 }
	public NhanVien quenMatKhau(String ten, String sdt, String cccd) {
		Connection con = ConnectDB.getInstance().getConnection();
		Statement stmt = null;
		NhanVien nv = null;
		try {
			stmt = con.createStatement();
			String sql = String.format("SELECT * FROM NhanVien WHERE TenNhanVien = N'%s' AND SoDienThoai = '%s' AND CCCD = '%s'", ten, sdt, cccd);
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maNhanVien = rs.getString("IDNhanVien");
				String tenNhanVien = rs.getString("TenNhanVien");
				String soDienThoai = rs.getString("SoDienThoai");
				LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
				boolean gioiTinh = rs.getBoolean("GioiTinh");
//				String chucVu = rs.getString("ChucVu");
//				ChucVu cv = null;
//				if(chucVu.equalsIgnoreCase(ChucVu.NHANVIENLETAN.toString())) {
//					cv = ChucVu.NHANVIENLETAN;
//					
//				} else if(chucVu.equalsIgnoreCase(ChucVu.NGUOIQUANLY.toString())) {
//					cv = ChucVu.NGUOIQUANLY;
//				}
				int chucVu = rs.getInt("ChucVu");
				ChucVu cv = null;
				
				if(chucVu == 1) {
					cv = ChucVu.NHANVIENLETAN;
				} else if (chucVu == 2) {
					cv = ChucVu.NGUOIQUANLY;
				}
				
				//NhanVien nv = new NhanVien(idNhanVien, tenNhanVien, soDienThoai, gioiTinh, cccd, cv);
				nv = new NhanVien(maNhanVien, tenNhanVien, soDienThoai, ngaySinh, gioiTinh, cccd, cv);
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
		 return nv;
	 }
}
