package entity;

public enum LoaiPhong {


	PHONGDON("Phòng đơn"),
	PHONGDOI("Phòng đôi"),
	PHONGGIADINH("Phòng gia đình");

	private String loaiPhong;

	private LoaiPhong(String loaiPhong) {
		this.loaiPhong = loaiPhong;
	}
	
	private LoaiPhong() {
		this("");
	}
	@Override
	public String toString() {
		return loaiPhong;
	}

}