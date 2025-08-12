package entity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import dao.Phong_DAO;

public class Phong {

	private String idPhong;
	private LoaiPhong loaiPhong;
	private double donGia;
	private TrangThaiPhong trangThai;
	private String tieuChi;
	
	public Phong(String idPhong, LoaiPhong loaiPhong, double donGia, TrangThaiPhong trangThai, String tieuChi) {
		super();
		this.idPhong = idPhong;
		this.loaiPhong = loaiPhong;
		this.donGia = donGia;
		this.trangThai = trangThai;
		this.tieuChi = tieuChi;
	}

	public Phong(String idPhong) {
		super();
		this.idPhong = idPhong;
	}

	public Phong() {
		this("", LoaiPhong.PHONGDON, 0, TrangThaiPhong.TRONG, "");
	}
	public String getIdPhong() {
		return idPhong;
	}
	public void setIdPhong(String idPhong) {
		this.idPhong = idPhong;
	}
	public LoaiPhong getLoaiPhong() {
		return loaiPhong;
	}
	public void setLoaiPhong(LoaiPhong loaiPhong) {
		this.loaiPhong = loaiPhong;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public TrangThaiPhong getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(TrangThaiPhong trangThai) {
		this.trangThai = trangThai;
	}

	  public String getTieuChi() {
		return tieuChi;
	}

	public void setTieuChi(String tieuChi) {
		this.tieuChi = tieuChi;
	}

	public String getLoaiPhongString() {
	        return getLoaiPhong() == null ? "" : getLoaiPhong().toString(); // Handles null
	    }
		  public String getTrangThaiString() {
	        return getTrangThai() == null ? "" : getTrangThai().toString(); //Handles null
	    }
	
	@Override
		public String toString() {
			return "Phong [idPhong=" + idPhong + ", loaiPhong=" + loaiPhong + ", donGia=" + donGia + ", trangThai="
					+ trangThai + ", tieuChi=" + tieuChi + "]";
		}

	@Override
	public int hashCode() {
		return Objects.hash(idPhong);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phong other = (Phong) obj;
		return Objects.equals(idPhong, other.idPhong);
	}
	
	
	
}