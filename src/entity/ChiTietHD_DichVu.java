package entity;

import java.util.List;
import java.util.Objects;

import dao.ChiTietHoaDon_DichVu_DAO;
import dao.DichVu_DAO;

public class ChiTietHD_DichVu {

	private HoaDon hoaDon;
	private DichVu dichVu;
	private int soLuong;
	
	public ChiTietHD_DichVu(HoaDon hoaDon, DichVu dichVu, int soLuong) {
		super();
		this.hoaDon = hoaDon;
		this.dichVu = dichVu;
		this.soLuong = soLuong;
	}
	
	public HoaDon getHoaDon() {
		return hoaDon;
	}
	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}
	public DichVu getDichVu() {
		return dichVu;
	}
	public void setDichVu(DichVu dichVu) {
		this.dichVu = dichVu;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	@Override
	public String toString() {
		return "ChiTietHD_DichVu [hoaDon=" + hoaDon + ", dichVu=" + dichVu + ", soLuong=" + soLuong + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(dichVu, hoaDon);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietHD_DichVu other = (ChiTietHD_DichVu) obj;
		return Objects.equals(dichVu, other.dichVu) && Objects.equals(hoaDon, other.hoaDon);
	}
	
}