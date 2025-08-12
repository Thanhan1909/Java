package entity;

public enum ChucVu {
	

	NHANVIENLETAN("Nhân viên lễ tân"),
	NGUOIQUANLY("Người quản lý");

	private String chucVu;

	private ChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	private ChucVu() {
		this("");
	}
	public String getchucVu() {
		return chucVu;
	}
	
	@Override
	public String toString() {
		return chucVu;
	}
}