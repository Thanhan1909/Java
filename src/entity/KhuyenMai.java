package entity;

import java.util.Objects;

public class KhuyenMai {

	private String idKhuyenMai;
	private String tenKhuyenMai;
	private double chietKhau;
	public KhuyenMai(String idKhuyenMai, String tenKhuyenMai, double chietKhau) {
		super();
		this.idKhuyenMai = idKhuyenMai;
		this.tenKhuyenMai = tenKhuyenMai;
		this.chietKhau = chietKhau;
	}
	
	public KhuyenMai(String idKhuyenMai) {
		this.idKhuyenMai = idKhuyenMai;
	}
	public String getIdKhuyenMai() {
		return idKhuyenMai;
	}
	public void setIdKhuyenMai(String idKhuyenMai) {
		this.idKhuyenMai = idKhuyenMai;
	}
	public String getTenKhuyenMai() {
		return tenKhuyenMai;
	}
	public void setTenKhuyenMai(String tenKhuyenMai) {
		this.tenKhuyenMai = tenKhuyenMai;
	}
	public double getChietKhau() {
		return chietKhau;
	}
	public void setChietKhau(double chietKhau) {
		this.chietKhau = chietKhau;
	}
	@Override
	public String toString() {
		return "KhuyenMai [idKhuyenMai=" + idKhuyenMai + ", tenKhuyenMai=" + tenKhuyenMai + ", chietKhau=" + chietKhau
				+ "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idKhuyenMai);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhuyenMai other = (KhuyenMai) obj;
		return Objects.equals(idKhuyenMai, other.idKhuyenMai);
	}

	
}