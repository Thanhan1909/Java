	package entity;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import dao.KhachHang_DAO;
import dao.NhanVien_DAO;

public class KhachHang {

	private String idKhachHang;
	private String tenKhachHang;
	private String soDienThoai;
	private LocalDate ngaySinh;
	private String cccd;
	private int tichDiem;
	public KhachHang(String idKhachHang, String tenKhachHang, String soDienThoai, LocalDate ngaySinh, String cccd,
			int tichDiem) {
		super();
		this.idKhachHang = idKhachHang;
		this.tenKhachHang = tenKhachHang;
		this.soDienThoai = soDienThoai;
		this.ngaySinh = ngaySinh;
		this.cccd = cccd;
		this.tichDiem = tichDiem;
	}
	
	public KhachHang(String idKhachHang) {
		this.idKhachHang = idKhachHang;
	}
	public String getIdKhachHang() {
		return idKhachHang;
	}
	public void setIdKhachHang(String idKhachHang) {
		this.idKhachHang = idKhachHang;
	}
	public String getTenKhachHang() {
		return tenKhachHang;
	}
	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public LocalDate getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getCccd() {
		return cccd;
	}
	public void setCccd(String cccd) {
		this.cccd = cccd;
	}
	public int getTichDiem() {
		return tichDiem;
	}
	public void setTichDiem(int tichDiem) {
		this.tichDiem = tichDiem;
	}
	@Override
	public String toString() {
		return "KhachHang [idKhachHang=" + idKhachHang + ", tenKhachHang=" + tenKhachHang + ", soDienThoai="
				+ soDienThoai + ", ngaySinh=" + ngaySinh + ", cccd=" + cccd + ", tichDiem=" + tichDiem + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idKhachHang);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(idKhachHang, other.idKhachHang);
	}

	public static String autoIdKhachHang() {
        KhachHang_DAO khachHangDAO = new KhachHang_DAO(); // Đối tượng DAO để truy xuất dữ liệu từ database
        ArrayList<KhachHang> khachHangList = null;

        try {
            khachHangList = khachHangDAO.getAllKhachHang(); // Lấy danh sách nhân viên từ database
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Hoặc trả về giá trị mặc định nếu lỗi xảy ra
        }


        if (khachHangList == null || khachHangList.isEmpty()) {
            return "KH19010101"; // Hoặc giá trị mặc định khác nếu không có nhân viên nào
        }


        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String dateString = currentDate.format(formatter);
		
		int max = 0;
		for(KhachHang kh : khachHangList) {
			String idKH = kh.getIdKhachHang();
			if (idKH.startsWith("KH" + dateString)) {
				String suffix = idKH.substring(idKH.length() - 2); // Lấy 2 ký tự cuối
				int suffixInt = Integer.parseInt(suffix);
				if (suffixInt > max) {
					max = suffixInt;
				}
			}
		}

		int nextId = max + 1;
        String formattedNextId = new DecimalFormat("00").format(nextId); // Định dạng 2 số
        return "KH" + dateString + formattedNextId;
    }


}
