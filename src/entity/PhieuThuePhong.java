package entity;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import dao.PhieuThuePhong_DAO;

public class PhieuThuePhong {

	private String idPhieuThue;
	private KhachHang khachHang;
	private Phong phong;
	private NhanVien nhanVienLap;
	private LocalDate thoiGianNhanPhong;
	private LocalDate thoiHanGiaoPhong;
	private Boolean HieuLuc;
	public PhieuThuePhong(String idPhieuThue, KhachHang khachHang, Phong phong, NhanVien nhanVienLap,
			LocalDate thoiGianNhanPhong, LocalDate thoiHanGiaoPhong) {
		super();
		this.idPhieuThue = idPhieuThue;
		this.khachHang = khachHang;
		this.phong = phong;
		this.nhanVienLap = nhanVienLap;
		this.thoiGianNhanPhong = thoiGianNhanPhong;
		this.thoiHanGiaoPhong = thoiHanGiaoPhong;
	}
	
	public PhieuThuePhong(String idPhieuThue, KhachHang khachHang, Phong phong, NhanVien nhanVienLap,
			LocalDate thoiGianNhanPhong, LocalDate thoiHanGiaoPhong, Boolean hieuLuc) {
		super();
		this.idPhieuThue = idPhieuThue;
		this.khachHang = khachHang;
		this.phong = phong;
		this.nhanVienLap = nhanVienLap;
		this.thoiGianNhanPhong = thoiGianNhanPhong;
		this.thoiHanGiaoPhong = thoiHanGiaoPhong;
		HieuLuc = hieuLuc;
	}

	public PhieuThuePhong() {
		super();
	}

	public String getIdPhieuThue() {
		return idPhieuThue;
	}
	public void setIdPhieuThue(String idPhieuThue) {
		this.idPhieuThue = idPhieuThue;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public Phong getPhong() {
		return phong;
	}
	public void setPhong(Phong phong) {
		this.phong = phong;
	}
	public NhanVien getNhanVienLap() {
		return nhanVienLap;
	}
	public void setNhanVienLap(NhanVien nhanVienLap) {
		this.nhanVienLap = nhanVienLap;
	}
	public LocalDate getThoiGianNhanPhong() {
		return thoiGianNhanPhong;
	}
	public void setThoiGianNhanPhong(LocalDate thoiGianNhanPhong) {
		this.thoiGianNhanPhong = thoiGianNhanPhong;
	}
	public LocalDate getThoiHanGiaoPhong() {
		return thoiHanGiaoPhong;
	}
	public void setThoiHanGiaoPhong(LocalDate thoiHanGiaoPhong) {
		this.thoiHanGiaoPhong = thoiHanGiaoPhong;
	}
	
	public Boolean getHieuLuc() {
		return HieuLuc;
	}
	public void setHieuLuc(Boolean hieuLuc) {
		HieuLuc = hieuLuc;
	}

	@Override
	public String toString() {
		return "PhieuThuePhong [idPhieuThue=" + idPhieuThue + ", khachHang=" + khachHang + ", phong=" + phong
				+ ", nhanVienLap=" + nhanVienLap + ", thoiGianNhanPhong=" + thoiGianNhanPhong + ", thoiHanGiaoPhong="
				+ thoiHanGiaoPhong + ", HieuLuc=" + HieuLuc + "]\n";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPhieuThue);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhieuThuePhong other = (PhieuThuePhong) obj;
		return Objects.equals(idPhieuThue, other.idPhieuThue);
	}
	
	public static String autoIdPhieuThue() {
	    ArrayList<PhieuThuePhong> PhieuThueList = null;
	    PhieuThuePhong_DAO ptdao = new PhieuThuePhong_DAO();
	    try {
	        PhieuThueList = ptdao.getAllPhieuThue();  // Lấy danh sách Phiếu thuê từ database
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null; // Trả về null nếu lỗi xảy ra
	    }

	    // Trường hợp không có phiếu thuê nào
	    if (PhieuThueList == null || PhieuThueList.isEmpty()) {
	        LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
	        String dateString = currentDate.format(formatter);
	        return "PT" + dateString + "001";
	    }

	    LocalDate currentDate = LocalDate.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
	    String dateString = currentDate.format(formatter);

	    int max = 0;
	    for (PhieuThuePhong pt : PhieuThueList) {
	        String idPT = pt.getIdPhieuThue();
	        if (idPT.startsWith("PT" + dateString) && idPT.length() > 3) {
	            String suffix = idPT.substring(idPT.length() - 3); // Lấy 3 ký tự cuối
	            try {
	                int suffixInt = Integer.parseInt(suffix);
	                if (suffixInt > max) {
	                    max = suffixInt;
	                }
	            } catch (NumberFormatException e) {
	                System.err.println("Lỗi định dạng ID phiếu thuê: " + suffix);
	                return null;
	            }
	        }
	    }
	    
	    int nextId = max + 1;
	    String formattedNextId = new DecimalFormat("000").format(nextId); // Định dạng 3 số
	    return "PT" + dateString + formattedNextId;
	}
	
}