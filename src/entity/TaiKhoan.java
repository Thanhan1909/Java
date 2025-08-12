package entity;

import java.util.Objects;

public class TaiKhoan {

	private String idTaiKhoan;
	private String matKhau;
	private String trangThai;
	private NhanVien nhanVien;
	public TaiKhoan(String idTaiKhoan, String matKhau, String trangThai, NhanVien nhanVien) {
		super();
		this.idTaiKhoan = idTaiKhoan;
		this.matKhau = matKhau;
		this.trangThai = trangThai;
		this.nhanVien = nhanVien;
	}
	public String getIdTaiKhoan() {
		return idTaiKhoan;
	}
	public void setIdTaiKhoan(String idTaiKhoan) {
		this.idTaiKhoan = idTaiKhoan;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	@Override
	public String toString() {
		return "TaiKhoan [idTaiKhoan=" + idTaiKhoan + ", matKhau=" + matKhau + ", trangThai=" + trangThai
				+ ", nhanVien=" + nhanVien + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idTaiKhoan);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaiKhoan other = (TaiKhoan) obj;
		return Objects.equals(idTaiKhoan, other.idTaiKhoan);
	}
}