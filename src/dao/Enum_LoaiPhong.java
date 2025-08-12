package dao;

public enum Enum_LoaiPhong {
	PHONGDON,
	PHONGDOI,
	PHONGGIADINH;
	
	public String getLoaiPhong() {
		switch (this) {
		case PHONGDON:
            return "Phòng đơn";
        case PHONGDOI:
            return "Phòng đôi";
        case PHONGGIADINH:
            return "Phòng gia đình";
        default:
            return null;
		}
	}
}
