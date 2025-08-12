package dao;

public enum Enum_ChucVu {
	NHANVIENLETAN,
	NGUOIQUANLY;
	
	public String getChucVu() {
        switch (this) {
            case NHANVIENLETAN:
                return "Nhân viên lễ tân";
            case NGUOIQUANLY:
                return "Người quản lý";
            default:
                return null;
        }
    }
}
