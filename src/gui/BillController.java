package gui;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.ChiTietHoaDon_DichVu_DAO;
import dao.HoaDon_DAO;
import dao.PhieuThuePhong_DAO;
import entity.ChiTietHD_DichVu;
import entity.HoaDon;
import entity.PhieuThuePhong;
import entity.TaiKhoan;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import main.App;

public class BillController implements Initializable{
    @FXML
    private TableColumn<ChiTietHD_DichVu, String> soLuong;

    @FXML
    private TableColumn<PhieuThuePhong, String> giaPhong;

    @FXML
    private TableColumn<PhieuThuePhong, LocalDate> gioPhong;

    @FXML
    private TableColumn<PhieuThuePhong, String> phongCol;

    @FXML
    private TableView<ChiTietHD_DichVu> tableDichVu;

    @FXML
    private TableView<PhieuThuePhong> tablePhong;

    @FXML
    private TableColumn<ChiTietHD_DichVu, String> tenSP;

    @FXML
    private TableColumn<ChiTietHD_DichVu, String> thanhTienDV;

    @FXML
    private Text txtGiamGia;

    @FXML
    private Text txtKhachHang;

    @FXML
    private Text txtLuongGiamGia;

    @FXML
    private Text txtThanhToan;

    @FXML
    private Text txtThoiGianLap;

    @FXML
    private Text txtTienKhach;

    @FXML
    private Text txtTienThua;

    @FXML
    private Text txtTienThueVAT;

    @FXML
    private Text txtTongTien;

    @FXML
    private Text txtTongTienDichVu;

    @FXML
    private Text txtTongTienPhong;

    @FXML
    private Text txt_HoaDon;

    @FXML
    private Text txt_NhanVien;
    DecimalFormat df = new DecimalFormat("#,###,###,###");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static String maHD = null;
	private static ArrayList<ChiTietHD_DichVu> ds = new ArrayList<ChiTietHD_DichVu>();
    public static double tienNhan = 0;
	public static double tienGiam = 0;
	private static double tienDV = 0;
	private static double tienPhong = 0;
	private static double tienThue = 0;
	private static double chietKhau = 0;
	private static double tienkm = 0;
	private static double tong = 0;
	public static double tienThua = 0;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		maHD = GD_ThanhToanController.maHD;
		for (ChiTietHD_DichVu pt : GD_ThanhToanController.dsdv) {
			ds.add(pt);
		}
		loadThongTin();
		loadTableData();
		
	}
	
	private void loadTableData() {
		phongCol.setCellValueFactory(cellData -> 
        new ReadOnlyStringWrapper(cellData.getValue().getPhong().getIdPhong()));
		gioPhong.setCellValueFactory(new PropertyValueFactory<>("ThoiHanGiaoPhong"));
		giaPhong.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getPhong().getDonGia())));

		
		
		tenSP.setCellValueFactory(cellData ->
		new ReadOnlyStringWrapper(cellData.getValue().getDichVu().getTenSanPham()));
		soLuong.setCellValueFactory(cellData ->
		new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getSoLuong())));
		thanhTienDV.setCellValueFactory(cellData ->
		new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getDichVu().getDonGia() * cellData.getValue().getSoLuong())));
		
		try {
	        PhieuThuePhong_DAO dao = new PhieuThuePhong_DAO();
	        ArrayList<PhieuThuePhong> dsPT = dao.layPhieuThueTheoMaHD(maHD);
	        ObservableList<PhieuThuePhong> observableList1 = FXCollections.observableArrayList(dsPT);

	        // Gán danh sách vào TableView
	        tablePhong.setItems(observableList1);;
	        ObservableList<ChiTietHD_DichVu> observableList = FXCollections.observableArrayList(ds);

	        // Gán danh sách vào TableView
	        tableDichVu.setItems(observableList);;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		try {
			ChiTietHoaDon_DichVu_DAO ds = new ChiTietHoaDon_DichVu_DAO();
			
			
//			List<ChiTietHD_DichVu> dsChitiet = ds.layChiTietHoaDonTheoMaHoaDon(maHD);
//	        ObservableList<ChiTietHD_DichVu> observableList = FXCollections.observableList(dsChitiet);
//
//	        // Gán danh sách vào TableView
//	        tableDichVu.setItems(observableList);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	void loadThongTin () {
		TaiKhoan t = App.tk;
		HoaDon_DAO dsHD = new HoaDon_DAO();
		System.out.println(maHD);
		HoaDon hd = dsHD.layHoaDonTheoMaHoaDon(maHD);
		System.out.println(hd);
		txt_HoaDon.setText(maHD);
		txt_NhanVien.setText(t.getNhanVien().getIdNhanVien());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String formattedDateTime = formatter.format(hd.getThoiGianTao());
		txtThoiGianLap.setText(formattedDateTime);
		txtKhachHang.setText(hd.getKhachHang().getTenKhachHang());
		
		// Lấy các giá trị tiền từ đối tượng HoaDon và hiển thị
		txtTongTienPhong.setText(df.format(hd.tinhTongTienPhong()) + " VND");
		txtTongTienDichVu.setText(df.format(hd.tinhTongTienDichVu()) + " VND");
		txtTienThueVAT.setText(df.format(hd.tinhThue()) + " VND");
		// Sử dụng hd.tinhGiamGia() để lấy tiền giảm từ đối tượng HoaDon
		txtGiamGia.setText(df.format(hd.tinhGiamGia()) + " VND");
		// Lấy tổng trước thuế từ HoaDon
		txtTongTien.setText(df.format(hd.tinhTongTruocThue()) + " VND");
		// Lấy tổng thanh toán từ HoaDon
		txtThanhToan.setText(df.format(hd.tinhTongThanhToan()) + " VND");
		
		// Tiền khách đưa và tiền thừa vẫn lấy từ GD_ThanhToanController (nếu không lưu trong HoaDon)
		txtTienKhach.setText(df.format(GD_ThanhToanController.tienNhan) + " VND");
		txtTienThua.setText(df.format(GD_ThanhToanController.tienThua) + " VND");
		
	}
}

