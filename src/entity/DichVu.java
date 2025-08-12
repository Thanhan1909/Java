package entity;

import java.util.Objects;

public class DichVu {

	private String idDichVu;
	private String tenSanPham;
	private int soLuong;
	private double donGia;
	public DichVu(String idDichVu, String tenSanPham, int soLuong, double donGia) {
		super();
		this.idDichVu = idDichVu;
		this.tenSanPham = tenSanPham;
		this.soLuong = soLuong;
		this.donGia = donGia;
	}
	public String getIdDichVu() {
		return idDichVu;
	}
	public void setIdDichVu(String idDichVu) {
		this.idDichVu = idDichVu;
	}
	public String getTenSanPham() {
		return tenSanPham;
	}
	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}
	@Override
	public String toString() {
		return "DichVu [idDichVu=" + idDichVu + ", tenSanPham=" + tenSanPham + ", soLuong=" + soLuong + ", donGia="
				+ donGia + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idDichVu);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DichVu other = (DichVu) obj;
		return Objects.equals(idDichVu, other.idDichVu);
	}


}