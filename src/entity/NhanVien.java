package entity;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import dao.Enum_ChucVu;
import dao.NhanVien_DAO;

public class NhanVien {

	private String idNhanVien;
	private String tenNhanVien;
	private String soDienThoai;
	private LocalDate ngaySinh;
	private boolean gioiTinh;
	private String cccd;
	private ChucVu chucVu;
	
	
	public NhanVien(String idNhanVien, String tenNhanVien, String soDienThoai, LocalDate ngaySinh, boolean gioiTinh,
			String cccd, ChucVu cv) {
		super();
		this.idNhanVien = idNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.soDienThoai = soDienThoai;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.cccd = cccd;
		this.chucVu = cv;
	}
	public NhanVien(String idnhanvien) {
		this.idNhanVien = idnhanvien;
	}
	public String getIdNhanVien() {
		return idNhanVien;
	}
	public void setIdNhanVien(String idNhanVien) {
		this.idNhanVien = idNhanVien;
	}
	public String getTenNhanVien() {
		return tenNhanVien;
	}
	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
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
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getCccd() {
		return cccd;
	}
	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public ChucVu getChucVu() {
		return chucVu;
	}
	public void setChucVu(ChucVu chucVu) {
		this.chucVu = chucVu;
	}
	public String getChucVuString() {
		return getChucVu() == null ? "" : getChucVu().toString();
	}
	@Override
	public String toString() {
		return "NhanVien [idNhanVien=" + idNhanVien + ", tenNhanVien=" + tenNhanVien + ", soDienThoai=" + soDienThoai
				+ ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh + ", cccd=" + cccd + ", chucVu=" + chucVu + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idNhanVien);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(idNhanVien, other.idNhanVien);
	}

	public static String autoIdNhanVien() {
        NhanVien_DAO nhanVienDAO = new NhanVien_DAO(); // Đối tượng DAO để truy xuất dữ liệu từ database
        ArrayList<NhanVien> nhanVienList = null;

        try {
            nhanVienList = nhanVienDAO.getAllNhanVien(); // Lấy danh sách nhân viên từ database
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Hoặc trả về giá trị mặc định nếu lỗi xảy ra
        }


        if (nhanVienList == null || nhanVienList.isEmpty()) {
            return "NV19010101"; // Hoặc giá trị mặc định khác nếu không có nhân viên nào
        }


        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String dateString = currentDate.format(formatter);
		
		int max = 0;
		for(NhanVien nv : nhanVienList) {
			String idNV = nv.getIdNhanVien();
			if (idNV.startsWith("NV" + dateString)) {
				String suffix = idNV.substring(idNV.length() - 2); // Lấy 2 ký tự cuối
				int suffixInt = Integer.parseInt(suffix);
				if (suffixInt > max) {
					max = suffixInt;
				}
			}
		}

		int nextId = max + 1;
        String formattedNextId = new DecimalFormat("00").format(nextId); // Định dạng 2 số
        return "NV" + dateString + formattedNextId;
    }
	
}