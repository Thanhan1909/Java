package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiPhong;
import entity.Phong;
import entity.TrangThaiPhong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Phong_DAO {
	public ArrayList<Phong> getAllPhong() {
		ArrayList<Phong> dsPhong = new ArrayList<Phong>();
		Connection con = ConnectDB.getInstance().getConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "SELECT * FROM Phong";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String ma = rs.getString("IDPhong");
				int loaiPhong = rs.getInt("LoaiPhong");
				LoaiPhong lp = null;
				if(loaiPhong == 1) {
					lp = LoaiPhong.PHONGDON;
					
				} else if(loaiPhong == 2) {
					lp = LoaiPhong.PHONGDOI;
					
				} else if(loaiPhong == 3) {
					lp = LoaiPhong.PHONGGIADINH;
				}
				double donGia = rs.getDouble("DonGia");
				int trangThai = rs.getInt("TrangThai");
				TrangThaiPhong tt = null;
				if(trangThai == 1) {
					tt = TrangThaiPhong.DANGTHUE;
				} else if(trangThai == 2) {
					tt = TrangThaiPhong.TRONG;
				} else if(trangThai == 3) {
					tt = TrangThaiPhong.SAPCHECKIN;
				} else {
					tt = TrangThaiPhong.SAPCHECKOUT;
				}
				
				String tc = rs.getString("TieuChi");
				
				Phong phong = new Phong(ma, lp, donGia, tt, tc);
				dsPhong.add(phong);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dsPhong;
		
	}
	
	public boolean themPhong(Phong phong) {
	    Connection con = null;
	    PreparedStatement pstm = null;
	    int n = 0;
	    try {
	        con = ConnectDB.getInstance().getConnection();
	        String sql = "INSERT INTO Phong (IDPhong, LoaiPhong, DonGia, TrangThai, TieuChi) VALUES(?,?,?,?,?)";
	        pstm = con.prepareStatement(sql);
	        
	        // Gán giá trị cho các tham số
	        pstm.setString(1, phong.getIdPhong());

	        // Xử lý LoaiPhong
	        if (phong.getLoaiPhong() == LoaiPhong.PHONGDON) {
	            pstm.setInt(2, 1); // Phòng đơn
	        } else if (phong.getLoaiPhong() == LoaiPhong.PHONGDOI) {
	            pstm.setInt(2, 2); // Phòng đôi
	        } else {
	            pstm.setInt(2, 3); // Phòng gia đình
	        }

	        pstm.setDouble(3, phong.getDonGia());

	        pstm.setInt(4, 2); // Trống

	        pstm.setString(5, phong.getTieuChi());

	        // Thực thi câu lệnh SQL
	        n = pstm.executeUpdate();

	        if (n > 0) {
	            System.out.println("Thêm phòng thành công");
	        } else {
	            System.out.println("Không có thay đổi nào trong cơ sở dữ liệu");
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Lỗi khi thêm phòng vào cơ sở dữ liệu!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Đã xảy ra lỗi không xác định!");
	    } finally {
	        try {
	            if (pstm != null) pstm.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Lỗi khi đóng tài nguyên kết nối!");
	        }
	    }
	    
	    return n > 0;
	}


	 public Phong getPhongTheoMa(String ma) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		Phong phong = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM Phong WHERE IDPhong = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ma);
	        rs = stmt.executeQuery();
			while (rs.next()) {
				int loaiPhong = rs.getInt("LoaiPhong");
				LoaiPhong lp = null;
				if(loaiPhong == 1) {
					lp = LoaiPhong.PHONGDOI;
				} else if(loaiPhong == 2) {
					lp = LoaiPhong.PHONGDON;
				} else{
					lp = LoaiPhong.PHONGGIADINH;
				}
				Double donGia = rs.getDouble("DonGia");
				int trangThai = rs.getInt("TrangThai");
				TrangThaiPhong tt = null;
				if(trangThai == 1) {
					tt = TrangThaiPhong.DANGTHUE;
				} else if(trangThai == 2) {
					tt = TrangThaiPhong.TRONG;
				} else if(trangThai == 3) {
					tt = TrangThaiPhong.SAPCHECKIN;
				} else {
					tt = TrangThaiPhong.SAPCHECKOUT;
				}
				
				String tc = rs.getString("TieuChi");
				
				phong = new Phong(ma, lp, donGia, tt, tc);
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
		return phong;
	 
	 }
	 public ArrayList<Phong> getPhongTheoLoai(int loai) {
		 ArrayList<Phong> dsPhong = new ArrayList<Phong>();
		 Connection con = ConnectDB.getInstance().getConnection();
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
			
			try {
				String sql = "SELECT * FROM Phong WHERE LoaiPhong = ?";
				stmt = con.prepareStatement(sql);
		        stmt.setInt(1, loai);
		        rs = stmt.executeQuery();
				while (rs.next()) {
					String IDPhong = rs.getString("IDPhong");
					LoaiPhong lphong = null;
					if(loai == 1) {
						lphong = LoaiPhong.PHONGDOI;
					} else if(loai == 2) {
						lphong = LoaiPhong.PHONGDON;
					} else{
						lphong = LoaiPhong.PHONGGIADINH;
					}
					Double donGia = rs.getDouble("DonGia");
					int trangThai = rs.getInt("TrangThai");
					TrangThaiPhong tt = null;
					if(trangThai == 1) {
						tt = TrangThaiPhong.DANGTHUE;
					} else if(trangThai == 2) {
						tt = TrangThaiPhong.TRONG;
					} else if(trangThai == 3) {
						tt = TrangThaiPhong.SAPCHECKIN;
					} else {
						tt = TrangThaiPhong.SAPCHECKOUT;
					}
					
					String tc = rs.getString("TieuChi");
				
					Phong phong = new Phong(IDPhong, lphong, donGia, tt, tc);
					dsPhong.add(phong);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return dsPhong;
		 }
	 public int getPhongTheoTrangThai(int trangthai) {
		 ArrayList<Phong> dsPhong = new ArrayList<Phong>();
		 Connection con = ConnectDB.getInstance().getConnection();
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
			
			try {
				String sql = "SELECT * FROM Phong WHERE TrangThai = ?";
				stmt = con.prepareStatement(sql);
		        stmt.setInt(1, trangthai);
		        rs = stmt.executeQuery();
				while (rs.next()) {
					String IDPhong = rs.getString("IDPhong");
					LoaiPhong lphong = null;
					int loai = rs.getInt("LoaiPhong");
					if(loai == 1) {
						lphong = LoaiPhong.PHONGDOI;
					} else if(loai == 2) {
						lphong = LoaiPhong.PHONGDON;
					} else{
						lphong = LoaiPhong.PHONGGIADINH;
					}
					Double donGia = rs.getDouble("DonGia");
					TrangThaiPhong tt = null;
					if(trangthai == 1) {
						tt = TrangThaiPhong.DANGTHUE;
					} else if(trangthai == 2) {
						tt = TrangThaiPhong.TRONG;
					} else if(trangthai == 3) {
						tt = TrangThaiPhong.SAPCHECKIN;
					} else {
						tt = TrangThaiPhong.SAPCHECKOUT;
					}
					
					String tc = rs.getString("TieuChi");
					
					Phong phong = new Phong(IDPhong, lphong, donGia, tt, tc);
					dsPhong.add(phong);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return dsPhong.size();
		 }
	 
	 public ArrayList<Phong> getPhongTheoTieuChi(String tc) {
		    ArrayList<Phong> dsPhong = new ArrayList<Phong>();
		    Connection con = ConnectDB.getInstance().getConnection();
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        String sql = "SELECT * FROM Phong WHERE TieuChi COLLATE Latin1_General_CI_AI LIKE ?";
		        stmt = con.prepareStatement(sql);
		        stmt.setString(1, "%" + tc + "%"); // Cho phép tìm kiếm chứa từ khóa
		        rs = stmt.executeQuery();
		        
		        while (rs.next()) {
		            String IDPhong = rs.getString("IDPhong");
		            int loaiPhong = rs.getInt("LoaiPhong");
		            LoaiPhong lphong;
		            if (loaiPhong == 1) {
		                lphong = LoaiPhong.PHONGDOI;
		            } else if (loaiPhong == 2) {
		                lphong = LoaiPhong.PHONGDON;
		            } else {
		                lphong = LoaiPhong.PHONGGIADINH;
		            }

		            double donGia = rs.getDouble("DonGia");
		            int trangThai = rs.getInt("TrangThai");
		            TrangThaiPhong tt;
		            if (trangThai == 1) {
		                tt = TrangThaiPhong.DANGTHUE;
		            } else if (trangThai == 2) {
		                tt = TrangThaiPhong.TRONG;
		            } else if (trangThai == 3) {
		                tt = TrangThaiPhong.SAPCHECKIN;
		            } else {
		                tt = TrangThaiPhong.SAPCHECKOUT;
		            }

		            String tieuChi = rs.getString("TieuChi");
		            Phong phong = new Phong(IDPhong, lphong, donGia, tt, tieuChi);
		            dsPhong.add(phong);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return dsPhong;
		}

	 
	 public ArrayList<Phong> getPhongTheoTrangThaiDanhSach(int trangthai) {
		 ArrayList<Phong> dsPhong = new ArrayList<Phong>();
		 Connection con = ConnectDB.getInstance().getConnection();
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
			
			try {
				String sql = "SELECT * FROM Phong WHERE TrangThai = ?";
				stmt = con.prepareStatement(sql);
		        stmt.setInt(1, trangthai);
		        rs = stmt.executeQuery();
				while (rs.next()) {
					String IDPhong = rs.getString("IDPhong");
					LoaiPhong lphong = null;
					int loai = rs.getInt("LoaiPhong");
					if(loai == 1) {
						lphong = LoaiPhong.PHONGDON;
					} else if(loai == 2) {
						lphong = LoaiPhong.PHONGDOI;
					} else{
						lphong = LoaiPhong.PHONGGIADINH;
					}
					Double donGia = rs.getDouble("DonGia");
					TrangThaiPhong tt = null;
					if(trangthai == 1) {
						tt = TrangThaiPhong.DANGTHUE;
					} else if(trangthai == 2) {
						tt = TrangThaiPhong.TRONG;
					} else if(trangthai == 3) {
						tt = TrangThaiPhong.SAPCHECKIN;
					} else {
						tt = TrangThaiPhong.SAPCHECKOUT;
					}
					
					String tc = rs.getString("TieuChi");
					
					Phong phong = new Phong(IDPhong, lphong, donGia, tt, tc);
					dsPhong.add(phong);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return dsPhong;
		 }
	 public boolean capNhatPhong(Phong phong) {
		    int n = 0;
		    Connection conN = null;
		    PreparedStatement pstm = null;
		    String sql = "UPDATE Phong SET LoaiPhong=?, DonGia=?, TrangThai=?, TieuChi=? WHERE IDPhong=?";
		    
		    try {
		        conN = ConnectDB.getInstance().getConnection();
		        pstm = conN.prepareStatement(sql);
		        
		        // Chuyển đổi loại phòng
		        int lp = switch(phong.getLoaiPhong()) {
		            case PHONGDOI -> 1;
		            case PHONGDON -> 2;
		            case PHONGGIADINH -> 3;
		        };
		        pstm.setInt(1, lp);
		        
		        pstm.setDouble(2, phong.getDonGia());
		        
		        // Chuyển đổi trạng thái
		        int tt = switch(phong.getTrangThai()) {
		            case DANGTHUE -> 1;  // Đang thuê
		            case TRONG -> 2;      // Trống
		            case SAPCHECKIN -> 3; // Sắp checkin
		            case SAPCHECKOUT -> 4; // Sắp checkout
		        };
		        pstm.setInt(3, tt);
		        
		        // Set giá trị cho TieuChi
		        pstm.setString(4, phong.getTieuChi());
		        
		        pstm.setString(5, phong.getIdPhong());
		        
		        n = pstm.executeUpdate();
		        
		        // Log thông tin cập nhật
		        System.out.println("Cập nhật phòng: " + phong.getIdPhong() 
		                           + ", Trạng thái: " + phong.getTrangThai() 
		                           + ", Số dòng cập nhật: " + n);
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (pstm != null) pstm.close();
		        } catch (SQLException e2) {
		            e2.printStackTrace();
		        }
		    }
		    
		    return n > 0;
		}

	 
	 public boolean capNhatTrangThaiPhong(Phong phong) {
		    int n = 0;
		    Connection conN = null;
		    PreparedStatement pstm = null;
		    String sql = "UPDATE Phong SET TrangThai=? WHERE IDPhong=?";
		    
		    try {
		        conN = ConnectDB.getInstance().getConnection();
		        pstm = conN.prepareStatement(sql);
		        
		        // Chuyển đổi trạng thái
		        int tt = switch(phong.getTrangThai()) {
		            case DANGTHUE -> 1;  // Đang thuê
		            case TRONG -> 2;      // Trống
		            case SAPCHECKIN -> 3; // Sắp checkin
		            case SAPCHECKOUT -> 4; // Sắp checkout
		        };
		        pstm.setInt(1, tt);
		        pstm.setString(2, phong.getIdPhong());
		        
		        n = pstm.executeUpdate();
		        
		        // Log thông tin cập nhật
		        System.out.println("Cập nhật trạng thái phòng: " + phong.getIdPhong() 
		                           + ", Trạng thái: " + phong.getTrangThai() 
		                           + ", Số dòng cập nhật: " + n);
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (pstm != null) pstm.close();
		        } catch (SQLException e2) {
		            e2.printStackTrace();
		        }
		    }
		    
		    return n > 0;
		}
	 
	 public ObservableList<Phong> getAllPhongOb() {
	        ObservableList<Phong> phongList = FXCollections.observableArrayList();
	        Connection con = ConnectDB.getInstance().getConnection();
	        Statement stmt = null;

	        try {
	            stmt = con.createStatement();
	            String sql = "SELECT * FROM Phong";
	            ResultSet rs = stmt.executeQuery(sql);

	            while (rs.next()) {
	                String idPhong = rs.getString("IDPhong");
	                int loaiPhong = rs.getInt("LoaiPhong");
	                int trangThai = rs.getInt("TrangThai");
	                double donGia = rs.getDouble("DonGia");
	                String tieuChi = rs.getString("TieuChi");

	                LoaiPhong loaiPhongEnum = getLoaiPhongEnum(loaiPhong);
	                TrangThaiPhong trangThaiEnum = getTrangThaiEnum(trangThai);

	                if(loaiPhongEnum != null && trangThaiEnum != null){
	                    Phong phong = new Phong(idPhong, loaiPhongEnum, donGia, trangThaiEnum, tieuChi);
	                    phongList.add(phong);
	                }

	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (stmt != null) stmt.close();
	                // Important: Close the connection too!
					if (con != null) con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return phongList;
	    }
	 private LoaiPhong getLoaiPhongEnum(int loaiPhong) {
	        switch (loaiPhong) {
	            case 1: return LoaiPhong.PHONGDOI;
	            case 2: return LoaiPhong.PHONGDON;
	            case 3: return LoaiPhong.PHONGGIADINH;
				default: return null; // Handle invalid values
	        }
	    }
	    private TrangThaiPhong getTrangThaiEnum(int trangThai) {
	        switch (trangThai) {
	            case 1: return TrangThaiPhong.DANGTHUE;
	            case 2: return TrangThaiPhong.TRONG;
	            case 3: return TrangThaiPhong.SAPCHECKIN;
	            case 4: return TrangThaiPhong.SAPCHECKOUT;
				default: return null;  // Handle invalid values
	        }
	    }
	    public boolean xoaPhong(String idPhong) {
	        Connection con = null;
	        PreparedStatement pstm = null;
	        try {
	            con = ConnectDB.getInstance().getConnection();
	            String sql = "DELETE FROM Phong WHERE IDPhong = ?";
	            pstm = con.prepareStatement(sql);
	            pstm.setString(1, idPhong);
	            int n = pstm.executeUpdate();
	            return n > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        } finally {
	            try {
	                if (pstm != null) {
	                    pstm.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
}
