package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import connectDB.ConnectDB;
import entity.ChiTietHD_DichVu;
import entity.ChiTietHD_Phong;
import entity.ChucVu;
import entity.DichVu;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;

public class testDao {
	
	public static void main(String[] args) throws SQLException {
		connect();
//		testTaiKhoan();
//		testNhanVien();

//		testPhieuThue();

//		testKH();
//		testHoaDon();
//		testPhong();
		testCTHD_DV();
//		testKH();
//
//		testPhieuThue();
//		testKH();

//		testHoaDon();
//		testPhong();
	}
	
	private static void testCTHD_DV() {
		ChiTietHoaDon_DichVu_DAO cth = new ChiTietHoaDon_DichVu_DAO();
//		List<ChiTietHD_DichVu> ct = cth.layChiTietHoaDonTheoMaHoaDon("HD24121206");
		ChiTietHD_DichVu ct = new ChiTietHD_DichVu(new HoaDon_DAO().layHoaDonTheoMaHoaDon("HD00"), new DichVu_DAO().layDichVuTheoMa("SP002"), 1);
		System.out.println(cth.themChiTietHoaDon(ct));
	}

	private static void testCTHD_Phong() {
		// TODO Auto-generated method stub
		ChiTietHoaDon_Phong_DAO cthd = new ChiTietHoaDon_Phong_DAO();
		
//		HoaDon hd = new HoaDon("HD24100301");
//		Phong p = new Phong("T01P01");
//		LocalDateTime ngayGio = LocalDateTime.of(2024, 10, 20, 14, 30); // Ngày 20/10/2024 lúc 14:30
		System.out.println();
//		cthd.themChiTietHoaDon_Phong(new ChiTietHD_Phong(hd, p, ngayGio));
//		System.out.println(cthd.layChiTietHoaDon_PhongTheoMaHoaDon("HD24100301"));
	}

	//done
	public static void testPhong() throws SQLException {
		Phong_DAO pdao = new Phong_DAO();
		
		
//		System.out.println(pdao.getAllPhong());

//		Phong p = new Phong("T03P07", LoaiPhong.PHONGDOI, 200000, TrangThaiPhong.TRONG);
//		pdao.themPhong(p);
//		pdao.capNhatTrangThaiPhong(new Phong("T01P01", LoaiPhong.PHONGGIADINH, 2300000, TrangThaiPhong.TRONG));
//		pdao.xoaPhong("T03P01");
//		System.out.println(pdao.getPhongTheoLoai("Phòng đơn"));
		System.out.println(pdao.getPhongTheoTieuChi("View biển"));
	}
//done
	public static void testTaiKhoan() {
//		TaiKhoan_DAO tkdao = new TaiKhoan_DAO();
//		NhanVien_DAO nvdao = new NhanVien_DAO();
//		NhanVien nvVien = nvdao.getNhanVienTheoMa("NV24100301");
//		TaiKhoan tk1 = new TaiKhoan("NV0303", "12", "nghi viec",nvVien);
//		tkdao.capNhatTaiKhoan(tk1);
//		
	}
	
	//done
	public static void testNhanVien() {
		NhanVien_DAO nv = new NhanVien_DAO();
		System.out.println(nv.getNhanVienTheoMa("NV24100301"));
		nv.themNhanVien(new NhanVien(null, "Nguyễn Hoàng Sơn", "0385412905", LocalDate.of(2004, 11, 03), true, "087204012824", ChucVu.NGUOIQUANLY));
	}
	public static void testPhieuThue() {
		PhieuThuePhong_DAO dsPT = new PhieuThuePhong_DAO();
		
		boolean a = dsPT.suaThoiGian("PT241206002", LocalDateTime.of(2024, 12, 06, 12, 0), LocalDateTime.of(2024, 12, 07, 12, 0));
		System.out.println(a);
		
//		ArrayList<Map<Integer, Integer>> a = dsPT.thongKeTheoNam(2024);
//		System.out.println(a);
//		
//        LocalDate dateA = LocalDate.of(2023, 10, 26);
//        LocalDate dateB = LocalDate.of(2023, 11, 20);
//
//        // Phương pháp 1 (dùng ChronoUnit): Hiệu quả và dễ đọc
//        double tong = dsPT.tongKHAtoB(LocalDate.of(2024, 10, 03), LocalDate.of(2024, 12, 03));
//        double daysDifference = (double) ChronoUnit.DAYS.between(dateA, dateB);
//        double tb =  tong / daysDifference;
//        System.out.println(tong + "         " + daysDifference + "                 " + tb);
//		ArrayList<PhieuThuePhong> p = dsPT.getPhieuThueTheoMaPhong("T01P01", LocalDate.of(2024, 11, 28), LocalDate.of(2024, 11, 29));
//		System.out.println(p);
		
//		KhachHang_DAO dsKH = new KhachHang_DAO();
//		KhachHang kh1 = dsKH.getKhachHangTheoMa("KH24100301");
//		System.out.println(dsPT.layPhieuThueTheoMaPhong("T01P02"));
//		
//		Phong_DAO dsP = new Phong_DAO();
//		Phong p1 = dsP.getPhongTheoMa("T0P04");
//		
//		NhanVien_DAO nvdao = new NhanVien_DAO();
//		NhanVien nvVien = nvdao.getNhanVienTheoMa("NV24100301");
//		
//		PhieuThuePhong pt1 = new PhieuThuePhong("PT241003002", kh1, p1, nvVien, LocalDate.of(2019, 6, 24) , LocalDate.of(2024, 6, 24));
////		dsPT.themPhieuThue(pt1);
//		
//		System.out.println(dsPT.getAllPhieuThue());
		
	}
//done
	public static void testKH() {
		KhachHang_DAO dsKH = new KhachHang_DAO();
		
		KhachHang kh1 = new KhachHang("KH24100305", "Nguyen B", "012345678", LocalDate.of(2019, 6, 24), "1123123", 0);
		dsKH.themKhachHang(kh1);
//		dsKH.capNhatKhachHangTheoSDT(kh1);
//		System.out.println(dsKH.getAllKhachHang());
	}
	
	//done
	public static void testHoaDon() {
	    HoaDon_DAO hddao = new HoaDon_DAO();
	    System.out.println(hddao.demHDTheoThang(10, 2024));
//	    LocalDate a = LocalDate.of(2024, 10, 12);
//	    System.out.printf("%d,%d,%d\n",a.getYear(),a.getMonthValue(),a.getDayOfMonth());
//	    System.out.println(hddao.TheoNgayob(a));
//	    HoaDon hd = hddao.layHoaDonTheoMaHoaDon("HD24100301");
//	    System.out.println(hd.tongTien());
	    
	    
	    
//	    System.out.print(hddao.layTheoNgay(LocalDate.of(2024, 10, 12)));
//	    LocalDateTime ngayGio = LocalDateTime.now(); // Thời gian hiện tại
//	    LocalDateTime ngayGio2 = LocalDateTime.of(2024, 10, 24, 14, 30); // Ngày 20/10/2024 lúc 14:30
//
//	    NhanVien nv = new NhanVien("NV24100301");
//	    KhachHang kh = new KhachHang("KH24100301");
//	    KhuyenMai km = new KhuyenMai("KM241001");
//
//	    // Tạo đối tượng HoaDon
//	    HoaDon hd = new HoaDon("HD002", nv, kh, km, ngayGio, ngayGio2);
//
//	    // Thêm hóa đơn vào cơ sở dữ liệu
//	    hddao.themHoaDon(hd);
	    
//	    System.out.println(hddao.layHoaDonTheoMaHoaDon("HD001"));
//	    System.out.println(hddao.layHoaDonTheoMaKhachHang("KH24100301"));
//	    System.out.println(hddao.getAllHoaDon());

	}

	static void connect() {
		ConnectDB cn = new ConnectDB();
		cn.getInstance().connect();
	}
}