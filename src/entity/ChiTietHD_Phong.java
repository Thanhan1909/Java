package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class ChiTietHD_Phong {

	private HoaDon hoaDon;
	private Phong phong;
	private LocalDateTime gioCheckout;

	public ChiTietHD_Phong(HoaDon hoaDon, Phong phong, LocalDateTime gioCheckout) {
		super();
		this.hoaDon = hoaDon;
		this.phong = phong;
		this.gioCheckout = gioCheckout;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public Phong getPhong() {
		return phong;
	}

	public void setPhong(Phong phong) {
		this.phong = phong;
	}

	

	public LocalDateTime getGioCheckout() {
		return gioCheckout;
	}

	public void setGioCheckout(LocalDateTime gioCheckout) {
		this.gioCheckout = gioCheckout;
	}

	@Override
	public String toString() {
		return "\nChiTietHD_Phong [hoaDon=" + hoaDon + ", phong=" + phong + ", gioCheckout=" + gioCheckout + "]";
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(hoaDon, phong);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietHD_Phong other = (ChiTietHD_Phong) obj;
		return Objects.equals(hoaDon, other.hoaDon) && Objects.equals(phong, other.phong);
	}


	public void thanhTien() {
		// TODO - implement ChiTietHD_Phong.thanhTien
		throw new UnsupportedOperationException();
	}

	public void tinhThoiGianSuDung() {
		// TODO - implement ChiTietHD_Phong.tinhThoiGianSuDung
		throw new UnsupportedOperationException();
	}

}