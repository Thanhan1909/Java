package entity;

public enum TrangThaiPhong {
	
	
	TRONG("Trống"),
	DANGTHUE("Đang thuê"),
	SAPCHECKIN("Sắp checkin"),
	SAPCHECKOUT("Sắp checkout");

	private String trangThaiPhong;

	private TrangThaiPhong(String trangThaiPhong) {
		this.trangThaiPhong = trangThaiPhong;
	}
	private TrangThaiPhong() {
		this("");
	}
	public String getTrangThaiPhong() {
		return trangThaiPhong;
	}
	
	@Override
	public String toString() {
		return trangThaiPhong;
	}
	
}