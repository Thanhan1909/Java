package entity;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dao.ChiTietHoaDon_DichVu_DAO;
import dao.HoaDon_DAO;
import dao.PhieuThuePhong_DAO;

public class HoaDon {

    private String idHoaDon;
    private NhanVien nhanVienLap;
    private KhachHang khachHang;
    private KhuyenMai khuyenmai;
    private LocalDateTime thoiGianTao;
    private LocalDateTime thoiGianCheckin;

    // Constructor đầy đủ tham số
    public HoaDon(String idHoaDon, NhanVien nhanVienLap, KhachHang khachHang, KhuyenMai khuyenmai,
                  LocalDateTime thoiGianTao, LocalDateTime thoiGianCheckin) {
        super();
        this.idHoaDon = idHoaDon;
        this.nhanVienLap = nhanVienLap;
        this.khachHang = khachHang;
        this.khuyenmai = khuyenmai;
        this.thoiGianTao = thoiGianTao;
        this.thoiGianCheckin = thoiGianCheckin;
    }

    public HoaDon(String idHoaDon) {
        super();
        this.idHoaDon = idHoaDon;
    }


    public HoaDon() {
        super();
    }


	public String getIdHoaDon() {
		return idHoaDon;
	}

	public void setIdHoaDon(String idHoaDon) {
		this.idHoaDon = idHoaDon;
	}

	public NhanVien getNhanVienLap() {
		return nhanVienLap;
	}

	public void setNhanVienLap(NhanVien nhanVienLap) {
		this.nhanVienLap = nhanVienLap;
	}
	
	public String getNhanVienString() {
		return getNhanVienLap() == null ? "" : getNhanVienLap().toString();
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public String getKhachHangString() {
		return getKhachHang() == null ? "" : getKhachHang().toString();
	}
	
	public LocalDateTime getThoiGianTao() {
		return thoiGianTao;
	}

	public void setThoiGianTao(LocalDateTime thoiGianTao) {
		this.thoiGianTao = thoiGianTao;
	}

	public LocalDateTime getThoiGianCheckin() {
		return thoiGianCheckin;
	}

	public void setThoiGianCheckin(LocalDateTime thoiGianCheckin) {
		this.thoiGianCheckin = thoiGianCheckin;
	}

	public KhuyenMai getKhuyenmai() {
		return khuyenmai;
	}

	public void setKhuyenmai(KhuyenMai khuyenmai) {
		this.khuyenmai = khuyenmai;
	}
	
	public String getKhuyenMaiString() {
		return getKhuyenmai() == null ? "" : getKhuyenmai().toString();
	}

	@Override
	public String toString() {
		return "\nHoaDon [idHoaDon=" + idHoaDon + ", nhanVienLap=" + nhanVienLap + ", khachHang=" + khachHang
				+ ", khuyenmai=" + khuyenmai + ", thoiGianTao=" + thoiGianTao + ", thoiGianCheckin=" + thoiGianCheckin
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idHoaDon);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(idHoaDon, other.idHoaDon);
	}

	public void chietKhau() {
		// TODO - implement HoaDon.chietKhau
		throw new UnsupportedOperationException();
	}

	public double tongTien() {
		return tinhTongThanhToan();
	}

	public double tinhTongTienPhong() {
		return thanhTien();
	}

	public double tinhTongTienDichVu() {
		ChiTietHoaDon_DichVu_DAO ctdvDAO = new ChiTietHoaDon_DichVu_DAO();
		List<ChiTietHD_DichVu> dsDV = ctdvDAO.layChiTietHoaDonTheoMaHoaDon(idHoaDon);
		double tong = 0;
		for (ChiTietHD_DichVu ctdv : dsDV) {
			if (ctdv.getDichVu() != null) {
				tong += ctdv.getDichVu().getDonGia() * ctdv.getSoLuong();
			}
		}
		return tong;
	}

	public double tinhTongTruocThue() {
		return tinhTongTienPhong() + tinhTongTienDichVu();
	}

	public double tinhThue() {
		return tinhTongTruocThue() * 0.1;
	}

	public double tinhGiamGia() {
		if (khuyenmai != null && khuyenmai.getChietKhau() > 0) {
			return tinhTongTruocThue() * (khuyenmai.getChietKhau() / 100.0);
		}
		return 0;
	}

	public double tinhTongThanhToan() {
		return tinhTongTruocThue() + tinhThue() - tinhGiamGia();
	}

	public double tinhTienThua(double tienNhan) {
		return tienNhan - tinhTongThanhToan();
	}

	public double thanhTien() {
		PhieuThuePhong_DAO ptdao = new PhieuThuePhong_DAO();
		ArrayList<PhieuThuePhong> listPhieuThue = ptdao.layPhieuThueTheoMaHD(idHoaDon);
	    double tongTien = 0;
	    int soNgayThue = 0;
	    if (listPhieuThue != null) {
	        for (PhieuThuePhong phieuThue : listPhieuThue) {
	            if (phieuThue != null){
	            	soNgayThue = phieuThue.getThoiHanGiaoPhong().getDayOfYear() - phieuThue.getThoiGianNhanPhong().getDayOfYear();
	            		if(soNgayThue > 0 && phieuThue.getPhong().getDonGia() > 0){
	            		    double thanhTienPhieu = soNgayThue * phieuThue.getPhong().getDonGia();
	            			tongTien += thanhTienPhieu;
	            		}else{

	            		}
	            }
	        }

			// Important step - handle potential null value before formatting
			if(tongTien > 0){
	        	return tongTien;
			}else{
				System.out.println("No valid reservation found.");
			}
	    }else {
	        System.out.println("Không tìm thấy hóa đơn thuê phòng.");
	    }
		return 0;
	}

	public static String autoIdHoaDon() {
        HoaDon_DAO hoaDonDAO = new HoaDon_DAO(); // Đối tượng DAO để truy xuất dữ liệu từ database
        ArrayList<HoaDon> hoaDonList = null;

        try {
            hoaDonList = hoaDonDAO.getAllHoaDon(); // Lấy danh sách hóa đơn từ database
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Hoặc trả về giá trị mặc định nếu lỗi xảy ra
        }


        if (hoaDonList == null || hoaDonList.isEmpty()) {
            return "HD24100101"; // Hoặc giá trị mặc định khác nếu không có hóa đơn nào
        }

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String dateString = currentDate.format(formatter);


        int max = 0;
        for (HoaDon hd : hoaDonList) {
            String idHD = hd.getIdHoaDon();
            if (idHD.startsWith("HD" + dateString)) {
                String suffix = idHD.substring(idHD.length() - 2); // Lấy 2 ký tự cuối
                try {
                    int suffixInt = Integer.parseInt(suffix);
                    if (suffixInt > max) {
                        max = suffixInt;
                    }
                } catch (NumberFormatException e) {
                    // Xử lý trường hợp suffix không phải là số nguyên
                    System.err.println("Lỗi định dạng ID hóa đơn: " + suffix);
                    // Xử lý lỗi phù hợp. Ví dụ, trả về null hoặc giá trị mặc định
                    return null;
                }
            }
        }

        int nextId = max + 1;
        String formattedNextId = new DecimalFormat("00").format(nextId); // Định dạng 4 số
        return "HD" + dateString + formattedNextId;
    }

}