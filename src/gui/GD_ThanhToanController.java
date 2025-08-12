package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


import entity.ChiTietHD_DichVu;
import dao.ChiTietHoaDon_DichVu_DAO;
import dao.DichVu_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.KhuyenMai_DAO;
import dao.PhieuThuePhong_DAO;
import dao.Phong_DAO;
import entity.DichVu;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.LoaiPhong;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.App;

public class GD_ThanhToanController implements Initializable{
	@FXML
    private AnchorPane GD_ThanhToan;
	public DecimalFormat df = new DecimalFormat("#,###,###,##0.##");
	public double tongtien;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnThemDichVu;
    @FXML
    private TextField txtMaDichVu;
    @FXML
    private TextField txt_SoLuong;
    @FXML
    private Button btnXoaDichVu;
    @FXML
    private Button btnThanhToan;
    @FXML
    private ImageView imgIn;
    @FXML
    private CheckBox checkBoxInHD;

    @FXML
    private TableColumn<PhieuThuePhong, String> donGiaCol;

    @FXML
    private TableColumn<PhieuThuePhong, LocalDate> gioRaCol;

    @FXML
    private TableColumn<PhieuThuePhong, LocalDate> gioVaoCol;

    @FXML
    private ImageView imgCheckKM;

    @FXML
    private TableColumn<PhieuThuePhong, String> loaiPhongCol;

    @FXML
    private TableColumn<PhieuThuePhong, String> maPhongCol;



    @FXML
    private TableView<ChiTietHD_DichVu> tableDichVu;

    @FXML
    private TableView<PhieuThuePhong> tablePhong;

    @FXML
    private TableColumn<ChiTietHD_DichVu, String> tenDichVuCol;
    @FXML
    private TableColumn<ChiTietHD_DichVu, String> thanhTienDVCol;
    @FXML
    private TableColumn<ChiTietHD_DichVu, String> soLuongCol;

    @FXML
    private Text txtKhachHang;

    @FXML
    private Text txtMaHoaDon;

    @FXML
    private TextField txtMaKhuyenMai;

    @FXML
    private Text txtNgayLap;

    @FXML
    private Text txtNhanVien;
    @FXML
    private Text txtTong;
    @FXML
    private Text txtTienDaGiam;

    @FXML
    private Text txtTienDichVu;

    @FXML
    private TextField txtTienNhan;

    @FXML
    private Text txtTienPhong;

    @FXML
    private Text txtTienThua;

    @FXML
    private Text txtTienThue;

    @FXML
    private Text txtTongTien;
    public TaiKhoan tk = App.tk;
    public static String maHD = null;
    public static ArrayList<PhieuThuePhong> pt = null;
    public static double tienNhan = 0;
	public static double tienGiam = 0;
	public static ArrayList<ChiTietHD_DichVu> dsdv = new ArrayList<ChiTietHD_DichVu>();
	public static double tienDV = 0;
	public static double tienPhong = 0;
	public static double tienThue = 0;
	public static double chietKhau = 0;
	public static double tienkm = 0;
	public static double tienThua = 0;
	public static double tong = 0;
	public static ArrayList<ChiTietHD_DichVu> getDsdv() {
		return dsdv;
	}
	public static void setDsdv(ArrayList<ChiTietHD_DichVu> dsdv) {
		GD_ThanhToanController.dsdv = dsdv;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
		dsPT = new PhieuThuePhong_DAO().layPhieuThueTheoMaHD(maHD);
		loadTableData();
		loadThongTin();
		
		// Thêm listener để tính tiền thừa khi tiền nhận thay đổi
		txtTienNhan.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Chỉ tính toán nếu ô Tiền nhận không rỗng
                if (!newValue.trim().isEmpty()) {
                    tinhTThua();
                } else {
                    // Nếu ô Tiền nhận rỗng, đặt tiền thừa về 0
                    txtTienThua.setText("0.0");
                    tienThua = 0;
                }
            } catch (NumberFormatException e) {
                // Xử lý nếu người dùng nhập không phải số
                txtTienThua.setText("Lỗi nhập liệu");
                tienThua = 0;
            } catch (Exception e) {
                e.printStackTrace();
                txtTienThua.setText("Lỗi tính toán");
                tienThua = 0;
            }
        });
        
        // Thêm listener cho ô nhập mã khuyến mãi
        txtMaKhuyenMai.textProperty().addListener((observable, oldValue, newValue) -> {
            tinhTienKM(); // Gọi hàm tính toán khuyến mãi khi mã thay đổi
        });
	}
	//load dữ liệu lên bảng
	private void loadTableData() {
		maPhongCol.setCellValueFactory(cellData -> 
        new ReadOnlyStringWrapper(cellData.getValue().getPhong().getIdPhong()));
		loaiPhongCol.setCellValueFactory(cellData -> 
        new ReadOnlyStringWrapper(cellData.getValue().getPhong().getLoaiPhong().toString()));
		
		gioVaoCol.setCellValueFactory(new PropertyValueFactory<>("ThoiGianNhanPhong"));
		gioRaCol.setCellValueFactory(new PropertyValueFactory<>("ThoiHanGiaoPhong"));
		donGiaCol.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getPhong().getDonGia())));
		
		
		tenDichVuCol.setCellValueFactory(cellData ->
		new ReadOnlyStringWrapper(cellData.getValue().getDichVu().getTenSanPham()));
		soLuongCol.setCellValueFactory(cellData ->
		new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getSoLuong())));
		thanhTienDVCol.setCellValueFactory(cellData ->
		new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getDichVu().getDonGia() * cellData.getValue().getSoLuong())));
		
		try {
	        PhieuThuePhong_DAO dao = new PhieuThuePhong_DAO();
	        ArrayList<PhieuThuePhong> dsPT = dao.layPhieuThueTheoMaHD(maHD);
	        ObservableList<PhieuThuePhong> observableList = FXCollections.observableArrayList(dsPT);

	        // Gán danh sách vào TableView
	        tablePhong.setItems(observableList);;
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
	//hiển thị dữ liệu lên các txt
	private void loadThongTin() {
	    try {
	        imgIn.setVisible(true);
	        
	        // 1. Lấy thông tin hóa đơn
	        HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
	        HoaDon hd = hoaDonDAO.layHoaDonTheoMaHoaDon(maHD);
	        if (hd == null) {
	            showAlert(AlertType.ERROR, "Không tìm thấy hóa đơn: " + maHD);
	            return;
	        }

	        // 2. Set thông tin cơ bản
	        txtMaHoaDon.setText(maHD);
	        txtNhanVien.setText(tk.getNhanVien().getTenNhanVien());
	        // Lấy thời gian tạo hóa đơn từ đối tượng HoaDon
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String formattedDateTime = formatter.format(hd.getThoiGianTao());
	        txtNgayLap.setText(formattedDateTime);

	        // 3. Load thông tin phiếu thuê phòng để hiển thị Khách hàng (nếu cần, nếu không có thể lấy từ HoaDon)
	        // Hiện tại đang lấy Khách hàng từ phiếu thuê, nếu HoaDon đã có đủ thông tin thì có thể bỏ qua bước này
	        if (hd.getKhachHang() != null) {
	            txtKhachHang.setText(hd.getKhachHang().getTenKhachHang());
	        } else { // Fallback nếu HoaDon không có thông tin KH (không nên xảy ra nếu luồng đúng)
	             PhieuThuePhong_DAO phieuThueDAO = new PhieuThuePhong_DAO();
	             PhieuThuePhong pThuePhong = phieuThueDAO.layPhieuThueTheoMaHD_1PT(maHD);
	             if (pThuePhong != null && pThuePhong.getKhachHang() != null) {
	                 txtKhachHang.setText(pThuePhong.getKhachHang().getTenKhachHang());
	             } else {
	                 txtKhachHang.setText("Không tìm thấy thông tin khách hàng");
	             }
	        }

	        // 4. Lấy danh sách dịch vụ từ database (đã được lưu ở bước xác nhận thanh toán)
	        ChiTietHoaDon_DichVu_DAO ctdvDAO = new ChiTietHoaDon_DichVu_DAO();
	        List<ChiTietHD_DichVu> dsChitietDV = ctdvDAO.layChiTietHoaDonTheoMaHoaDon(maHD);
	        dsdv.clear(); // Xóa dữ liệu cũ nếu có
	        dsdv.addAll(dsChitietDV); // Cập nhật danh sách dịch vụ tĩnh
	        
	        // 5. Tính toán các giá trị tiền DỰA TRÊN DỮ LIỆU ĐÃ LƯU VÀ LẤY TỪ DB
	        // Bây giờ chúng ta có thể gọi các hàm tính toán trong HoaDon object
	        tienPhong = hd.tinhTongTienPhong(); // Lấy tiền phòng đã tính trong HoaDon
	        tienDV = hd.tinhTongTienDichVu();   // Lấy tiền dịch vụ đã tính trong HoaDon
	        tienThue = hd.tinhThue();           // Lấy tiền thuế đã tính trong HoaDon
	        tienkm = hd.tinhGiamGia();          // Lấy tiền giảm giá đã tính trong HoaDon
	        tong = hd.tinhTongThanhToan();      // Lấy tổng thanh toán đã tính trong HoaDon
	        
	        // Tiền nhận và tiền thừa cần được lấy từ input nếu có, hoặc mặc định
	        // Hiện tại logic tính tiền thừa đang nằm trong tinhTThua() được gọi bởi listener
	        // txtTienNhan.setText(String.valueOf(tienNhan)); // Nếu muốn hiển thị tiền nhận đã lưu trước đó
	        // txtTienThua.setText(String.valueOf(tienThua)); // Nếu muốn hiển thị tiền thừa đã tính trước đó

	        // 6. Hiển thị các giá trị tiền lên UI
	        txtTienPhong.setText(df.format(tienPhong) + " VND");
	        txtTienDichVu.setText(df.format(tienDV) + " VND");
	        txtTienThue.setText(df.format(tienThue) + " VND");
	        txtTienDaGiam.setText(df.format(tienkm) + " VND"); // Dùng tienkm cho tiền đã giảm
	        txtTongTien.setText(df.format(hd.tinhTongTruocThue()) + " VND"); // Tổng trước thuế
	        txtTong.setText(df.format(tong) + " VND"); // Tổng thanh toán
	        
	        // Cập nhật table dịch vụ từ danh sách đã lấy từ DB
	        ObservableList<ChiTietHD_DichVu> observableListDV = FXCollections.observableArrayList(dsdv);
	        tableDichVu.setItems(observableListDV);
	        
			// Gọi lại tinhTThua() để cập nhật tiền thừa dựa trên tiền nhận hiện tại
			tinhTThua();
		

	    } catch (Exception e) {
	        System.err.println("Lỗi khi load thông tin: " + e.getMessage());
	        e.printStackTrace();
	        showAlert(AlertType.ERROR, "Lỗi khi tải thông tin: " + e.getMessage());
	    }
	}

	private void showAlert(AlertType type, String content) {
	    Alert alert = new Alert(type);
	    alert.setTitle("Thông báo");
	    alert.setHeaderText(null);
	    alert.setContentText(content);
	    alert.showAndWait();
	}

	// Sửa lại phương thức showAlert để nhận thêm tham số title
	private void showAlert(AlertType type, String title, String message) {
	    Alert alert = new Alert(type);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}

	// lưu hóa đơn vào database
	private boolean luuHoaDon() {
	    try {
	        PhieuThuePhong pThuePhong = new PhieuThuePhong_DAO().layPhieuThueTheoMaHD_1PT(maHD);
	        if (pThuePhong == null) return false;

	        KhuyenMai km = new KhuyenMai_DAO().layKhuyenMaiTheoMa("KM241001");

	        LocalDateTime thoiGianNhanPhong = pThuePhong.getThoiGianNhanPhong().atTime(12, 0);
	        LocalDateTime thoiGianGiaoPhong = pThuePhong.getThoiHanGiaoPhong().atTime(12, 0);

	        HoaDon hd = new HoaDon(
	            maHD,
	            tk.getNhanVien(),
	            pThuePhong.getKhachHang(),
	            km,
	            thoiGianGiaoPhong,
	            thoiGianNhanPhong
	        );

	        HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
	        return hoaDonDAO.themHoaDon(hd); // trả về true/false từ DAO
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	// Sự kiện nút lưu
	public void handleEventInBtn() {
	    btnThanhToan.setOnAction(evt -> {
	        if (txtTienThua.getText().equals("0")) {
	            Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng kiểm tra lại tiền nhận!", ButtonType.OK);
	            alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
	            alert.setTitle("Lỗi");
	            alert.setHeaderText("Tiền nhận không phù hợp");
	            alert.showAndWait();
	            return;
	        }
	        
	        // 1. Cập nhật trạng thái phiếu thuê và phòng
	        PhieuThuePhong_DAO dsPhieu = new PhieuThuePhong_DAO();
	        ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
	        dsPT = new PhieuThuePhong_DAO().layPhieuThueTheoMaHD(maHD);
	        for (PhieuThuePhong pt : dsPT) {
	            pt.setHieuLuc(false);
	            dsPhieu.suaPhieuThue(pt);
	            Phong_DAO dsP = new Phong_DAO();
	            Phong p = dsP.getPhongTheoMa(pt.getPhong().getIdPhong());
	            p.setTrangThai(TrangThaiPhong.TRONG);
	            dsP.capNhatTrangThaiPhong(p);
	        }
	        
	        // 2. Lưu tất cả dịch vụ vào bảng ChiTietHD_DichVu với đúng IDHoaDon
	        ChiTietHoaDon_DichVu_DAO ctdvDAO = new ChiTietHoaDon_DichVu_DAO();
	        HoaDon hd = new HoaDon_DAO().layHoaDonTheoMaHoaDon(maHD);
	        if (hd != null) { // Đảm bảo hóa đơn tồn tại trước khi gán
		        for (ChiTietHD_DichVu ctdv : dsdv) {
		            ctdv.setHoaDon(hd); // Gán đúng hóa đơn cho dịch vụ
		            ctdvDAO.themChiTietHoaDon(ctdv);
		        }
	        }
	        
	        // 3. Cập nhật hóa đơn (bao gồm cả khuyến mãi nếu có)
	        suaHoaDon();
	        
	        // 4. Cập nhật số lượng tồn kho dịch vụ
	        tinhLaiSP();
	        
	        // 5. Cập nhật tích điểm khách hàng
	        setTichDiem();
	        
	        // 6. Hiển thị thông báo thành công
	        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
	        alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
	        alert.setTitle("Thanh toán phòng thành công");
	        alert.setHeaderText("Bạn đã thanh toán phòng thành công!");
	        alert.showAndWait();
	        
	        // 7. Mở giao diện Bill (sau khi mọi thứ đã được lưu)
	        try {
				moGDBill();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    });
	}

	@FXML
	private void moGDBill() throws IOException {
		App.openModal("Bill", 450, 760);
	}
    @FXML
    void themDichVu(ActionEvent event) {
    	String ma = txtMaDichVu.getText();
    	int soLuong = Integer.valueOf(txt_SoLuong.getText());
    	DichVu dv = new DichVu_DAO().layDichVuTheoMa(ma);
    	HoaDon hd = new HoaDon_DAO().layHoaDonTheoMaHoaDon(maHD);
    	ChiTietHD_DichVu ct = new ChiTietHD_DichVu(hd, dv, soLuong);
    	System.out.println(ct);
    	dsdv.add(ct);
    	ObservableList<ChiTietHD_DichVu> observableList = FXCollections.observableArrayList(dsdv);
    	tableDichVu.getItems().clear();
    	tableDichVu.setItems(observableList);
    	tinhTienDV();
    	tinhTongTien();
    	tinhTong();
    }

    @FXML
    void xoaDichVu(ActionEvent event) {
        ChiTietHD_DichVu selectedDV = tableDichVu.getSelectionModel().getSelectedItem();
        if (selectedDV == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn dịch vụ cần xóa!");
            alert.showAndWait();
            return;
        }
        Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
        confirmAlert.setTitle("Xác nhận xóa");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Bạn có chắc chắn muốn xóa phòng " + selectedDV.getDichVu().getTenSanPham() + "?");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            	dsdv.remove(selectedDV);
            	tableDichVu.getItems().clear();
               	ObservableList<ChiTietHD_DichVu> observableList = FXCollections.observableArrayList(dsdv);
            	tableDichVu.setItems(observableList);
            	tinhTienDV();
            	tinhTongTien();
            	tinhTong();
        }
    }
    @FXML
    void tinhTienKhuyenMai(ActionEvent event) {
    	tinhTienKM();
    }
    void tinhTienKM() {
        String maKM = txtMaKhuyenMai.getText().trim();
        tienGiam = 0; // Reset tiền giảm
        // chietKhau = 0; // Có thể bỏ biến này nếu chỉ dùng tienGiam
        tienkm = 0; // Có thể bỏ biến này nếu chỉ dùng tienGiam

        if (!maKM.isEmpty() && maKM.length() >= 3) { // ví dụ độ dài tối thiểu là 3 ký tự
            KhuyenMai_DAO kmDAO = new KhuyenMai_DAO();
            KhuyenMai km = kmDAO.layKhuyenMaiTheoMa(maKM);

            if (km != null) {
                double phanTramGiam = km.getChietKhau();
                tienGiam = tongtien * (phanTramGiam / 100.0);
                if (tienGiam > tongtien) {
                    tienGiam = tongtien;
                }
            } else {
                
            }
        }

        // Cập nhật hiển thị tiền giảm
        txtTienDaGiam.setText(df.format(tienGiam) + " VND");
        
        // Sau khi tính tiền giảm, cần tính lại tổng tiền thanh toán cuối cùng
        tinhTong();
    }
    private void tinhTienDV() {
        try {
            double tien = 0;
            for (ChiTietHD_DichVu ct : dsdv) {
                tien += ct.getSoLuong() * ct.getDichVu().getDonGia();
            }
            tienDV = tien;
            txtTienDichVu.setText(String.valueOf(tienDV));
        } catch (Exception e) {
            e.printStackTrace();
            // Hoặc hiển thị lên giao diện:
            Alert alert = new Alert(Alert.AlertType.ERROR, "Lỗi tính tiền dịch vụ: " + e.getMessage());
            alert.showAndWait();
        }
    }
    private void tinhTongTien() {
		tongtien = tienPhong + tienThue + tienDV;
		txtTongTien.setText(df.format(tongtien) + " VND");
	}
    private void tinhTong() {
    	// Tính tổng cuối cùng sau khi đã tính tổng tiền ban đầu (tongtien) và tiền giảm (tienGiam)
		tong = tongtien - tienGiam;
		txtTong.setText(df.format(tong) + " VND");
    }
    @FXML
    void tinhTienThua(ActionEvent event) {
    	tinhTThua();
    }
    void tinhTThua() {
        tinhTienDV();
        tinhTongTien();
        tinhTong();

        String input = txtTienNhan.getText().trim();
        try {
            tienNhan = Double.parseDouble(input);
            tienThua = tienNhan - tong;
            txtTienThua.setText(String.format("%.2f", tienThua));
        } catch (NumberFormatException e) {
            tienThua = 0;
            txtTienThua.setText(""); // Không thông báo gì ở đây
        }
    }

    @FXML
    void open(ActionEvent event) {
    	if (checkBoxInHD.isSelected()) {
    		btnThanhToan.setDisable(false);
    		 
    	} else {
    		btnThanhToan.setDisable(true);
    	}
    }
    @FXML
    void xacNhanThanhToan(ActionEvent event) throws IOException {
        tinhTienDV();
        tinhTongTien();
        tinhTong();
        PhieuThuePhong_DAO dsPhieu = new PhieuThuePhong_DAO();
        ArrayList<PhieuThuePhong> dsPT = new ArrayList<PhieuThuePhong>();
        dsPT = new PhieuThuePhong_DAO().layPhieuThueTheoMaHD(maHD);
        for(PhieuThuePhong pt: dsPT) {
            pt.setHieuLuc(false);
            dsPhieu.suaPhieuThue(pt);
            Phong_DAO dsP = new Phong_DAO();
            Phong p = dsP.getPhongTheoMa(pt.getPhong().getIdPhong());
            p.setTrangThai(TrangThaiPhong.TRONG);
            dsP.capNhatTrangThaiPhong(p);
        }
        // Lưu tất cả dịch vụ vào bảng ChiTietHD_DichVu với đúng IDHoaDon
        ChiTietHoaDon_DichVu_DAO ctdvDAO = new ChiTietHoaDon_DichVu_DAO();
        HoaDon hd = new HoaDon_DAO().layHoaDonTheoMaHoaDon(maHD);
        for (ChiTietHD_DichVu ctdv : dsdv) {
            ctdv.setHoaDon(hd); // Gán đúng hóa đơn cho dịch vụ
            ctdvDAO.themChiTietHoaDon(ctdv);
        }
        suaHoaDon();
        tinhLaiSP();
        setTichDiem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
        alert.setTitle("Thanh toán phòng thành công");
        alert.setHeaderText("Bạn đã thanh toán phòng thành công!");
        alert.showAndWait();
        moGDBill();
    }
	private void suaHoaDon() {
		PhieuThuePhong pThuePhong = new PhieuThuePhong_DAO().layPhieuThueTheoMaHD_1PT(maHD);
		
		// Lấy mã khuyến mãi từ ô nhập liệu
    	String maKM = txtMaKhuyenMai.getText().trim();
    	KhuyenMai km = null; // Khởi tạo null
    	
    	// Nếu có mã khuyến mãi được nhập, lấy đối tượng KhuyenMai đầy đủ từ DAO
    	if (!maKM.isEmpty()) {
        	km = new KhuyenMai_DAO().layKhuyenMaiTheoMa(maKM);
    	}
    	// Nếu mã khuyến mãi rỗng, hoặc mã nhập không tìm thấy, có thể gán null
    	// hoặc gán khuyến mãi mặc định nếu có logic đó.
    	// Dựa vào logic hiện tại, nếu trống thì gán KM241001, nếu nhập thì tìm theo mã.
    	// Ta sẽ giữ lại logic tìm theo mã nhập, nếu không tìm thấy thì km vẫn là null
    	// Nếu muốn gán KM241001 khi trống, logic đó đã có sẵn.
    	
		LocalDate thoiHanNhanPhong = LocalDate.now(); // Lấy giá trị LocalDate
		LocalDateTime thoiGianNhanPhongLocalDateTime = thoiHanNhanPhong.atTime(12, 00);
		LocalDate thoiHan = pThuePhong.getThoiHanGiaoPhong(); // Lấy giá trị LocalDate
		LocalDateTime thoiHanLocalDateTime = thoiHan.atTime(12, 00);
		
		// Tạo đối tượng HoaDon với đối tượng KhuyenMai đầy đủ (hoặc null nếu không có KM)
		HoaDon hd = new HoaDon(maHD, tk.getNhanVien(),pThuePhong.getKhachHang(),km,thoiGianNhanPhongLocalDateTime,thoiHanLocalDateTime);
		HoaDon_DAO dsHDao = new HoaDon_DAO();
		dsHDao.suaHoaDon(hd); // Lưu hóa đơn đã cập nhật vào database
	}
	void tinhLaiSP() {
		for (ChiTietHD_DichVu ds : dsdv) {
			int a = ds.getDichVu().getSoLuong() - ds.getSoLuong();
			new DichVu_DAO().capNhatDichVu(ds.getDichVu(), a);
		}
	}
	void setTichDiem() {
		HoaDon hd = new HoaDon_DAO().layHoaDonTheoMaHoaDon(maHD);
		KhachHang kh = hd.getKhachHang();
		int tich = (int) (tienPhong * 5 / 100);
		new KhachHang_DAO().capNhatKhachHangTheoMa1(kh, tich);
	}
	
    @FXML
    void inHD(MouseEvent event) throws IOException {
    	
    	moGDBill();
    }
    
}
