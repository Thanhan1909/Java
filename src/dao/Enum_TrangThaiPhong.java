package dao;

public enum Enum_TrangThaiPhong {
	DANGTHUE,
	TRONG,
	SAPCHECKIN,
	SAPCHECKOUT;
	
	public String getTrangThaiPhong() {
        switch (this) {
            case DANGTHUE:
                return "Đang thuê";
            case TRONG:
                return "Trống";
            case SAPCHECKIN:
                return "Sắp checkin";
            case SAPCHECKOUT:
                return "Sắp checkout";
            default:
                return null;
        }
    }
}
