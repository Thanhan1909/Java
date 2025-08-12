package gui;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import dao.KhachHang_DAO;
import dao.PhieuThuePhong_DAO;
import dao.Phong_DAO;
import entity.HoaDon;
import entity.KhachHang;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.App;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class GD_SoDoPhong_Cotroller implements Initializable {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	Phong_DAO dsp = new Phong_DAO();
	public static KhachHang kh;
	public static String roomID;

	@FXML
	private ImageView avt;
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
	@FXML
	private ComboBox<String> cbb;
	
	@FXML
	private ComboBox<String> cbb_TrangThai;

	@FXML
	private ImageView icon_QuanLy;
	@FXML
    private TextField txt_MaPhongCanTim;
	@FXML
	private ImageView icon_ThongKe;

	@FXML
	private ImageView icon_TimKiem;
	@FXML
    private Button btn_TimKiem;
	@FXML
	private Label l;

	@FXML
	private Label lb_QuanLy;

	@FXML
	private Label lb_ThongKe;
	 @FXML
	private Button btn_DangO;

	    @FXML
	    private Button btn_PhongTrong;

	    @FXML
	    private Button btn_SapCheckIn;

	    @FXML
	    private Button btn_SapCheckOut;

	    @FXML
	    private Button btn_TatCa;
	    
		@FXML
		private GridPane gridPane;

	    @FXML
	    void moGiaoDienGiaHanPhong(MouseEvent event) throws IOException {
			App.setRoot("GD_GiaHanPhong");
	    }

	    @FXML
	    void moGiaoDienHuyPhong(MouseEvent event) throws IOException {
			App.setRoot("GD_HuyPhong");
	    }

	    @FXML
	    void moGiaoDienQuanLy(MouseEvent event) throws IOException {
			App.setRoot("GD_QLPhong");
	    }

	    @FXML
	    void moGiaoDienSoDoPhong(MouseEvent event) throws IOException {
	    	checkTrangThai();
			App.setRoot("GD_SoDoPhong");
	    }

	    @FXML
	    void moGiaoDienThongKe(MouseEvent event) throws IOException {
			App.setRoot("GD_ThongKeDoanhThu");
	    }

	    @FXML
	    void moGiaoDienTimKiem(MouseEvent event) throws IOException {
			App.setRoot("GD_TKPhong");
	    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ObservableList<String> list = FXCollections.observableArrayList("Tất cả", "Phòng đơn", "Phòng đôi","Phòng gia đình");
		btn_PhongTrong.setStyle("-fx-background-color: #31c57e");
		btn_DangO.setStyle("-fx-background-color: #2972d3");
		btn_SapCheckIn.setStyle("-fx-background-color: #edbf6d");
		btn_SapCheckOut.setStyle("-fx-background-color: #ff3131");
    	renderArrayPhong(new Phong_DAO().getAllPhong());
		cbb.setItems(list);
		cbb.setValue("Tất cả");
		ObservableList<String> list_TrangThai = FXCollections.observableArrayList("Tất cả","View biển", "View thành phố");
		cbb_TrangThai.setItems(list_TrangThai);
		cbb_TrangThai.setValue("Tất cả");
		 if (GD_DatPhongChoController.dsMaPhong == null) {
	            GD_DatPhongChoController.dsMaPhong = new ArrayList<>();
	        }
		loadLoaiPhong();
		loadTrangThaiPhong_CBB();
		loadTrangThaiPhong();
		LoadSoPhongTheoLoai();
		suKienNutTim();
		addUserLogin();
		checkTrangThai();
	}
	
	public void suKienNutTim() {
		btn_TimKiem.setOnAction((event ->{
			String maPhong = txt_MaPhongCanTim.getText();
			ArrayList<Phong>dsP = new ArrayList<Phong>();
			Phong p = dsp.getPhongTheoMa(maPhong);
			dsP.add(p);
			gridPane.getChildren().clear();
			renderArrayPhong(dsP);
		}));
	}
	
	public void loadLoaiPhong() {
		renderArrayPhong(new Phong_DAO().getAllPhong());
		cbb.setOnAction(event -> {
			gridPane.getChildren().clear(); // Xóa các thành phần trong GridPane trước khi thêm mới
		    ArrayList<Phong> dsP = kiemTraLoaiPhong();
		    cbb_TrangThai.setValue("Tất cả");// Khởi tạo danh sách phòng
		    renderArrayPhong(dsP);
		    LoadSoPhongTheoLoai();
		});
	}

	public void LoadSoPhongTheoLoai(){
	    String selectedItem = (String) cbb.getSelectionModel().getSelectedItem();
	    if (selectedItem.equals("Phòng đơn")) {
	        locSoPhong(2);
	    } else if (selectedItem.equals("Phòng đôi")) {
	    	locSoPhong(1);
	    } else if (selectedItem.equals("Phòng gia đình")) {
	    	locSoPhong(3);
	    } else if (selectedItem.equals("Tất cả")) {
	    	locSoPhong_TatCa();
	    }
	}
	public void locSoPhong_TatCa() {
		Phong_DAO dsp1 = new Phong_DAO();
	    btn_PhongTrong.setText("Phòng trống ("+String.valueOf(dsp1.getPhongTheoTrangThai(2))+")");
	    btn_DangO.setText("Đang ở ("+String.valueOf(dsp1.getPhongTheoTrangThai(1))+")");
	    btn_SapCheckIn.setText("Sắp Check-in ("+String.valueOf(dsp1.getPhongTheoTrangThai(3))+")");
	    btn_SapCheckOut.setText("Sắp Check-out ("+String.valueOf(dsp1.getPhongTheoTrangThai(4))+")");
	    btn_TatCa.setText("Tất cả ("+String.valueOf(new Phong_DAO().getAllPhong().size())+")");
	}
	
	public ArrayList<Phong> KiemtraTrung(ArrayList<Phong>dsP1, int TrangThai){
		ArrayList<Phong>dsP_CuoiCung = new ArrayList<Phong>();
		ArrayList<Phong>dsP2 = new ArrayList<Phong>();
		dsP2 = new Phong_DAO().getPhongTheoTrangThaiDanhSach(TrangThai);
		for(Phong p: dsP1) {
			if(dsP2.contains(p)) {
				dsP_CuoiCung.add(p);
			}
		}
		return dsP_CuoiCung;
	}
	
	public void locSoPhong(int sott) {
		ArrayList<Phong> dsP_TheoLoai = new ArrayList<Phong>();
		dsP_TheoLoai = new Phong_DAO().getPhongTheoLoai(sott);
		
        btn_PhongTrong.setText("Phòng trống (" +  KiemtraTrung(dsP_TheoLoai,2).size()+ ")");
        btn_DangO.setText("Đang ở (" +KiemtraTrung(dsP_TheoLoai,1).size()  + ")");
        btn_SapCheckIn.setText("Sắp Check-in (" + KiemtraTrung(dsP_TheoLoai,3).size() + ")");
        btn_SapCheckOut.setText("Sắp Check-out (" + KiemtraTrung(dsP_TheoLoai,4).size()+ ")");
        btn_TatCa.setText("Tất cả (" + dsP_TheoLoai.size()	 + ")");
	}
	
	public ArrayList<Phong> kiemTraLoaiPhong() {
		String selectedItem = (String) cbb.getSelectionModel().getSelectedItem();
		
	    ArrayList<Phong> dsP = new ArrayList<Phong>(); // Khởi tạo danh sách phòng
	    
	    if (selectedItem.equals("Phòng đơn")) {
	        dsP = new Phong_DAO().getPhongTheoLoai(2);
	    } else if (selectedItem.equals("Phòng đôi")) {
	        dsP = new Phong_DAO().getPhongTheoLoai(1);
	    } else if (selectedItem.equals("Phòng gia đình")) {
	        dsP = new Phong_DAO().getPhongTheoLoai(3);
	    } else if (selectedItem.equals("Tất cả")) {
	        dsP = new Phong_DAO().getAllPhong(); // Lấy tất cả các phòng
	    }
	    return dsP;
	}
	
	public ArrayList<Phong> LocPhong2TieuChi(int maso ){
		gridPane.getChildren().clear();
		ArrayList<Phong> dsPKiemTra =  kiemTraLoaiPhong();
		ArrayList<Phong> dsKiemTraTT = new Phong_DAO().getPhongTheoTrangThaiDanhSach(maso);
		ArrayList<Phong> dsHoanCHinh = new ArrayList<Phong>();
		for (Phong phongTT : dsPKiemTra) {
		    // Kiểm tra nếu dsPKiemTra chứa phần tử này
		    if (dsKiemTraTT.contains(phongTT)) {
		        // Thêm phần tử vào dsHoanCHinh
		        dsHoanCHinh.add(phongTT);
		    }
		}
		return dsHoanCHinh;
	}
	
	public void loadTrangThaiPhong_CBB() {
		String selectedItemTT = (String) cbb_TrangThai.getSelectionModel().getSelectedItem();	    
	    if (selectedItemTT.equals("View biển")) {
	    	renderArrayPhong(LocPhong2TieuChi2("View biển"));
	    } else if (selectedItemTT.equals("View thành phố")) {
	    	renderArrayPhong(LocPhong2TieuChi2("View thành phố"));
	    }else if(selectedItemTT.equals("Tất cả")) {
	    	gridPane.getChildren().clear();
			ArrayList<Phong> dsPKiemTra =  kiemTraLoaiPhong();
			renderArrayPhong(dsPKiemTra);
	    }
	}
	
	private ArrayList<Phong> LocPhong2TieuChi2(String a) {
		gridPane.getChildren().clear();
		ArrayList<Phong> dsPKiemTra =  kiemTraLoaiPhong();
		ArrayList<Phong> dsKiemTraTT = new Phong_DAO().getPhongTheoTieuChi(a);
		ArrayList<Phong> dsHoanCHinh = new ArrayList<Phong>();
		for (Phong phongTT : dsPKiemTra) {
		    // Kiểm tra nếu dsPKiemTra chứa phần tử này
		    if (dsKiemTraTT.contains(phongTT)) {
		        // Thêm phần tử vào dsHoanCHinh
		        dsHoanCHinh.add(phongTT);
		    }
		}
		return dsHoanCHinh;
	}

	public void loadTrangThaiPhong() {
		
		btn_PhongTrong.setOnAction(event ->{
			renderArrayPhong(LocPhong2TieuChi(2));
		});
		btn_DangO.setOnAction(event ->{
			renderArrayPhong(LocPhong2TieuChi(1));
		});
		btn_SapCheckIn.setOnAction(event ->{
			renderArrayPhong(LocPhong2TieuChi(3));
		});
		btn_SapCheckOut.setOnAction(event ->{
			renderArrayPhong(LocPhong2TieuChi(4));
		});
		btn_TatCa.setOnAction(event->{
			gridPane.getChildren().clear();
			ArrayList<Phong> dsPKiemTra =  kiemTraLoaiPhong();
			renderArrayPhong(dsPKiemTra);
			
		});
	}
	
	public void renderArrayPhong(ArrayList<Phong> dsPhong) {
		for (int i = 0; i < dsPhong.size(); i++) {
			Phong phong = dsPhong.get(i);
			gridPane.setHgap(10); // Khoảng cách giữa các cột
	        gridPane.setVgap(10); // Khoảng cách giữa các hàng
			gridPane.add(taoGiaoDienPhong(phong), i % 5, i / 5);
		}
	}

	public Pane taoGiaoDienPhong(Phong phong) {
		VBox roomItem = new VBox();
		roomItem.setCursor(Cursor.HAND);
		roomItem.setPrefHeight(250);
		roomItem.setPrefWidth(250);
		
		
		String imagePath1 = "src/pic/bed.png";
	    File imageFile1 = new File(imagePath1);
	    if (imageFile1.exists()) {
	        Image image = new Image(imageFile1.toURI().toString());
	        ImageView imageView = new ImageView(image);
	        imageView.setFitWidth(250);
	        imageView.setFitHeight(150); // Kích thước hình ảnh trong giao diện chính
	        imageView.setPreserveRatio(true);
	        roomItem.getChildren().add(imageView);
	    } else {
	        System.out.println("Hình ảnh không tìm thấy! Đường dẫn: " + imagePath1);
	    }
		roomItem.setOnMouseClicked(event->{
			String thongTinPhong = "Mã phòng: " + phong.getIdPhong() + "\n"
		            + "Loại phòng: " + phong.getLoaiPhong() + "\n"
		            + "Đơn giá: " + phong.getDonGia() + " VND\n"
		            + "Trạng thái: " + phong.getTrangThai()+"\n"
		            + "Tiêu chí:" + phong.getTieuChi();

			File imageFile = null;
			String imagePath = "";

			if (phong.getLoaiPhong().toString().equalsIgnoreCase("Phòng đôi")) {
			    imagePath = "data/pic/phongdoi.jpg";
			} else if (phong.getLoaiPhong().toString().equalsIgnoreCase("Phòng đơn")) {
			    imagePath = "data/pic/phongdon.jpg";
			} else {
			    imagePath = "data/pic/phonggiadinh.jpg";
			}

			// Đảm bảo bạn đang trỏ đúng đường dẫn tới thư mục ngoài src
			imageFile = new File(imagePath);

			if (imageFile.exists()) {
			    Image image = new Image(imageFile.toURI().toString());
			    ImageView imageView = new ImageView(image);
			    imageView.setFitWidth(600);
			    imageView.setFitHeight(600); // Đặt kích thước hình ảnh
			    imageView.setPreserveRatio(true); // Đảm bảo tỷ lệ

			    Label thongTinLabel = new Label(thongTinPhong);
			    thongTinLabel.setStyle("-fx-font-size: 16px;");

			    Dialog<Void> dialog = new Dialog<>();
			    dialog.setTitle("Thông tin phòng");
			    dialog.setHeaderText("Chi tiết phòng: " + phong.getIdPhong());

			    VBox content = new VBox(10);
			    content.getChildren().addAll(imageView, thongTinLabel);
			    content.setAlignment(Pos.TOP_LEFT);

			    dialog.getDialogPane().setContent(content);
			    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

			    // Hiển thị Dialog
			    dialog.showAndWait();
			} else {
			    System.out.println("Hình ảnh không tìm thấy! Đường dẫn: " + imagePath);

			    // Hiển thị Alert thông thường
			    Alert alert = new Alert(Alert.AlertType.INFORMATION);
			    alert.setTitle("Thông tin phòng");
			    alert.setHeaderText("Chi tiết phòng: " + phong.getIdPhong());
			    alert.setContentText(thongTinPhong);
			    alert.showAndWait();
			}
		});
		switch (phong.getTrangThai()) {
		case TRONG:
			roomItem.setStyle("-fx-background-color: #31c57e");
			break;
		case SAPCHECKIN:
			roomItem.setStyle("-fx-background-color: #edbf6d");
			break;
		case SAPCHECKOUT:
			roomItem.setStyle("-fx-background-color: #ff3131");
			break;
		default:
			roomItem.setStyle("-fx-background-color: #2972d3");
			break;
		}
		Label lblMaPhong = new Label(phong.getIdPhong());
		lblMaPhong.setStyle("-fx-font-size: 18; -fx-font-weight: 700");
		lblMaPhong.setPadding(new Insets(0, 0, 8, 0));
		roomItem.getChildren().add(lblMaPhong);

		Label lblLoaiPhong = new Label(phong.getLoaiPhong().toString());
		lblLoaiPhong.setStyle("-fx-font-size: 18; -fx-font-weight: 600");
		lblLoaiPhong.setPadding(new Insets(0, 0, 8, 0));
		roomItem.getChildren().add(lblLoaiPhong);
		
		String dongia = String.valueOf(phong.getDonGia());
		Label lbGiaPhong = new Label(dongia + " VND");
		lbGiaPhong.setStyle("-fx-font-size: 18; -fx-font-weight: 700");
		lbGiaPhong.setPadding(new Insets(0, 0, 8, 0));
		roomItem.getChildren().add(lbGiaPhong);
		
		String TieuChi = String.valueOf(phong.getTieuChi());
		Label lbTieuChi = new Label(TieuChi);
		lbTieuChi.setStyle("-fx-font-size: 18; -fx-font-weight: 700");
		lbTieuChi.setPadding(new Insets(0, 0, 8, 0));
		roomItem.getChildren().add(lbTieuChi);
		if (phong.getTrangThai() == TrangThaiPhong.TRONG) {
		    try {
		        PhieuThuePhong_DAO phieuDAO = new PhieuThuePhong_DAO();
		        ArrayList<PhieuThuePhong> dsPhieu = phieuDAO.layPhieuThueTheoMaPhong(phong.getIdPhong());
		        LocalDateTime now = LocalDateTime.now();
		        
		        
		        Set<LocalDate> processedDates = new HashSet<>();
		        
		        for (PhieuThuePhong phieu : dsPhieu) {
		            LocalDateTime thoiGianNhan = phieuDAO.getThoiGianNhanPhong(phieu.getIdPhieuThue());
		            LocalDateTime thoiGianTra = phieuDAO.getThoiGianTraPhong(phieu.getIdPhieuThue());

		            if (phieu.getHieuLuc() && thoiGianNhan != null && thoiGianNhan.isAfter(now)) {
		                LocalDate ngayDat = thoiGianNhan.toLocalDate();
		                LocalDate homNay = now.toLocalDate();

		                // Check if we've already processed this date
		                if (!processedDates.contains(ngayDat)) {
		                    processedDates.add(ngayDat);
		                    
		                    // Create the booking info display
		                    VBox bookingInfo = new VBox(5);
		                    bookingInfo.setAlignment(Pos.CENTER);
		                    bookingInfo.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 5; -fx-background-radius: 5;");
		                    
		                    Label lblDatTruoc = new Label("ĐẶT TRƯỚC");
		                    lblDatTruoc.setStyle("-fx-font-weight: bold; -fx-text-fill: #2196F3;");
		                    
		                    Label lblNgayNhan = new Label("Từ: " + dtf.format(thoiGianNhan));
		                    lblNgayNhan.setStyle("-fx-text-fill: #666666;");
		                    
		                    Label lblNgayTra = new Label("Đến: " + dtf.format(thoiGianTra));
		                    lblNgayTra.setStyle("-fx-text-fill: #666666;");
		                    
		                    bookingInfo.getChildren().addAll(lblDatTruoc, lblNgayNhan, lblNgayTra);
		                    
		                    if (phieu.getKhachHang() != null) {
		                        Label lblKhachHang = new Label("KH: " + phieu.getKhachHang().getTenKhachHang());
		                        lblKhachHang.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
		                        bookingInfo.getChildren().add(lblKhachHang);
		                    }
		                    
		                    roomItem.getChildren().add(bookingInfo);
		                }
		            }
		        }
		    } catch (Exception ex) {
		        System.err.println("Error loading bookings for room " + phong.getIdPhong() + ": " + ex.getMessage());
		        Logger.getLogger(GD_SoDoPhong_Cotroller.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
		if (phong.getTrangThai() == TrangThaiPhong.SAPCHECKIN) {
			try {
				PhieuThuePhong phieu = new PhieuThuePhong_DAO().layPhieuThueTheoMa(phong.getIdPhong());
				if (phieu != null) {
					Label lblGioNhan = new Label("Giờ nhận: " + dtf.format(phieu.getThoiGianNhanPhong()));
					lblGioNhan.setStyle("-fx-font-size: 16; -fx-font-weight: 600");
					lblGioNhan.setPadding(new Insets(0, 0, 8, 0));
					roomItem.getChildren().add(lblGioNhan);
				}
			} catch (Exception ex) {
				Logger.getLogger(GD_SoDoPhong_Cotroller.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		String strBtnLeft = phong.getTrangThai() == TrangThaiPhong.TRONG ? "Chọn phòng"
				: phong.getTrangThai() == TrangThaiPhong.SAPCHECKIN ? "Hủy phòng"
						: phong.getTrangThai() == TrangThaiPhong.DANGTHUE ? "Đổi phòng" : "Gia Hạn Phòng";
		
		String strBtnRight = phong.getTrangThai() == TrangThaiPhong.TRONG ? "Đặt phòng"
				: phong.getTrangThai() == TrangThaiPhong.SAPCHECKIN ? "Nhận phòng"
						: phong.getTrangThai() == TrangThaiPhong.DANGTHUE ? "Thanh toán" : "Thanh toán";
		Button btnLeft = new Button(strBtnLeft);
		Button btnRight = new Button(strBtnRight);
		
		switch (phong.getTrangThai()) {
		case DANGTHUE:
			btnLeft.setStyle("-fx-background-color: #edbf6d; -fx-text-fill: #fff; -fx-font-size: 16");
			btnLeft.setOnAction(((event) -> {
				if (phong.getTrangThai() == TrangThaiPhong.DANGTHUE) {
			        roomID = phong.getIdPhong(); // Lưu mã phòng
			        try {
			            moGDDoiPhong();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			    }
			}));
			btnRight.setOnAction((event) -> {
				String maHD = HoaDon.autoIdHoaDon();
				GD_ThanhToanController.maHD = maHD;
				String maP = phong.getIdPhong();
				ArrayList<PhieuThuePhong>dsPThue_Tam = new ArrayList<PhieuThuePhong>();
				dsPThue_Tam = new PhieuThuePhong_DAO().layPhieuThueTheoMaPhong(maP);
				PhieuThuePhong_DAO dsPt = new PhieuThuePhong_DAO(); 
				for(PhieuThuePhong pt : dsPThue_Tam) {
					if(pt.getHieuLuc()== true) {
						dsPt.suaPhieuThue_ThemIDHoaDon(maHD, pt.getIdPhieuThue());
					}
				}
				try {
					moGDThanhToan();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			break;
		case SAPCHECKIN:
			btnLeft.setStyle("-fx-background-color: #ff3131; -fx-text-fill: #fff; -fx-font-size: 16");
			btnLeft.setDisable(false); // Đảm bảo nút không bị vô hiệu hóa

			btnLeft.setOnAction(event -> {
			    try {
			        phong.setTrangThai(TrangThaiPhong.TRONG);  // Cập nhật trạng thái phòng
			        PhieuThuePhong_DAO ptdao = new PhieuThuePhong_DAO();
			        PhieuThuePhong pt = ptdao.layPhieuThueTheoMaPhong_1Phong(phong.getIdPhong());
			        pt.setHieuLuc(false);  // Cập nhật hiệu lực của phiếu thuê
			        ptdao.suaPhieuThue(pt);
			        dsp.capNhatTrangThaiPhong(phong);  // Cập nhật trạng thái phòng trong hệ thống
			        LoadSoPhongTheoLoai();  // Tải lại số phòng theo loại
			        renderArrayPhong(new Phong_DAO().getAllPhong());  // Render lại danh sách phòng
			    } catch (Exception e) {
			        e.printStackTrace();  // In ra lỗi nếu có
			    }
			});

			btnRight.setOnAction((event) -> {
			    System.out.println("btnRight clicked");  // Xác nhận sự kiện được kích hoạt
			    try {
			        // Bước 1: Yêu cầu nhập CCCD hoặc số điện thoại
			        TextInputDialog dialog = new TextInputDialog();
			        dialog.setTitle("Xác nhận khách đặt phòng");
			        dialog.setHeaderText("Nhập CCCD của khách hàng:");
			        dialog.setContentText("Thông tin:");

			        Optional<String> result = dialog.showAndWait();

			        if (result.isPresent()) {
			            String thongTin = result.get().trim();  // Lấy thông tin nhập vào và loại bỏ khoảng trắng

			            // Bước 2: Tìm khách hàng theo CCCD
			            KhachHang_DAO khDAO = new KhachHang_DAO();
			            KhachHang kh = khDAO.timKhachHangTheoCCCD(thongTin);

			            if (kh != null) {
			                // Bước 3: Hiển thị tên khách hàng
			                Alert customerInfoAlert = new Alert(Alert.AlertType.INFORMATION);
			                customerInfoAlert.setTitle("Thông tin khách hàng");
			                customerInfoAlert.setHeaderText("Tên khách hàng: " + kh.getTenKhachHang());
			                customerInfoAlert.setContentText("Thông tin khách hàng đã được tìm thấy.");
			                customerInfoAlert.showAndWait();

			                // Bước 4: Kiểm tra khách đã đặt phòng chưa (Thông qua phiếu thuê phòng)
			                PhieuThuePhong_DAO ptpDAO = new PhieuThuePhong_DAO();
			                boolean daDat = ptpDAO.kiemTraKhachDaDatPhong(kh.getIdKhachHang(), phong.getIdPhong());

			                if (daDat) {
			                    // Xác nhận nhận phòng
			                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
			                    confirm.setTitle("Xác nhận nhận phòng");
			                    confirm.setHeaderText("Khách hàng: " + kh.getTenKhachHang());
			                    confirm.setContentText("Xác nhận cho nhận phòng?");

			                    Optional<ButtonType> xacNhan = confirm.showAndWait();
			                    if (xacNhan.isPresent() && xacNhan.get() == ButtonType.OK) {
			                        // Cập nhật trạng thái phòng
			                        phong.setTrangThai(TrangThaiPhong.DANGTHUE);
			                        dsp.capNhatTrangThaiPhong(phong);  // Cập nhật trạng thái vào CSDL
			                        renderArrayPhong(new Phong_DAO().getAllPhong());  // Cập nhật lại giao diện
			                    }
			                } else {
			                    // Khách không có đơn đặt phòng này
			                    Alert alert = new Alert(Alert.AlertType.WARNING);
			                    alert.setTitle("Không tìm thấy đơn đặt");
			                    alert.setHeaderText("Khách hàng không có đơn đặt phòng này!");
			                    alert.showAndWait();
			                }
			            } else {
			                // Không tìm thấy khách
			                Alert alert = new Alert(Alert.AlertType.ERROR);
			                alert.setTitle("Không tìm thấy khách hàng");
			                alert.setHeaderText("Không có khách hàng nào khớp với thông tin đã nhập.");
			                alert.showAndWait();
			            }
			        }
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			});

			break;
		case TRONG:
            btnLeft.setStyle("-fx-background-color: #edbf6d; -fx-text-fill: #fff; -fx-font-size: 16");
            btnLeft.setOnAction(((event) -> {
                try {
                    String maChon = phong.getIdPhong();
                    // Khởi tạo dsMaPhong nếu bị null (dự phòng)
                    if (GD_DatPhongChoController.dsMaPhong == null) {
                        GD_DatPhongChoController.dsMaPhong = new ArrayList<>();
                    }
                    if (!GD_DatPhongChoController.dsMaPhong.contains(maChon)) {
                        GD_DatPhongChoController.dsMaPhong.add(maChon);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
            btnRight.setOnAction((event) -> {
                try {
                    String maChon = phong.getIdPhong();
                    // Khởi tạo dsMaPhong nếu bị null (dự phòng)
                    if (GD_DatPhongChoController.dsMaPhong == null) {
                        GD_DatPhongChoController.dsMaPhong = new ArrayList<>();
                    }
                    if (GD_DatPhongChoController.dsMaPhong.isEmpty()) {
                        GD_DatPhongChoController.dsMaPhong.add(maChon);
                    } else {
                        if (!GD_DatPhongChoController.dsMaPhong.contains(maChon)) {
                            GD_DatPhongChoController.dsMaPhong.add(maChon);
                        }
                    }
                    moGDDatPhong();
                    phong.setTrangThai(TrangThaiPhong.SAPCHECKIN);
                    dsp.capNhatTrangThaiPhong(phong);
                    renderArrayPhong(new Phong_DAO().getAllPhong());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            break;
		default:
			btnLeft.setStyle("-fx-background-color: #edbf6d; -fx-text-fill: #fff; -fx-font-size: 16");
			btnLeft.setOnAction((event) -> {
				String maP = phong.getIdPhong();
				ArrayList<PhieuThuePhong> dsP = new ArrayList<PhieuThuePhong>();
				dsP = new PhieuThuePhong_DAO().layPhieuThueTheoMaPhong(maP);
				for(PhieuThuePhong pt:  dsP) {
					if(pt.getHieuLuc()== true) {
						kh = new KhachHang_DAO().getKhachHangTheoMa(pt.getKhachHang().getIdKhachHang());
						break;
					}
				}
				try {
					moGDGiaHan();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			btnRight.setOnAction((event) -> {
				String maHD = HoaDon.autoIdHoaDon();
				GD_ThanhToanController.maHD = maHD;
				String maP = phong.getIdPhong();
				ArrayList<PhieuThuePhong>dsPThue_Tam = new ArrayList<PhieuThuePhong>();
				dsPThue_Tam = new PhieuThuePhong_DAO().layPhieuThueTheoMaPhong(maP);
				ArrayList<PhieuThuePhong>dsPThue = new ArrayList<PhieuThuePhong>();
				dsPThue = new PhieuThuePhong_DAO().getAllPhieuThue();
				
				ArrayList<PhieuThuePhong>dsPThueThanhToan = new ArrayList<PhieuThuePhong>();
				String maKH = null;
				for(PhieuThuePhong pt: dsPThue_Tam) {
					if(pt.getHieuLuc()== true) {
						maKH = pt.getKhachHang().getIdKhachHang();
						for(PhieuThuePhong pt1 : dsPThue) {
							if(pt1.getKhachHang().getIdKhachHang().equals(maKH) && pt1.getHieuLuc() == true) {
								dsPThueThanhToan.add(pt1);
							}
						}
					}
				}
				PhieuThuePhong_DAO dsPt = new PhieuThuePhong_DAO(); 
				for(PhieuThuePhong pt : dsPThueThanhToan) {
					dsPt.suaPhieuThue_ThemIDHoaDon(maHD, pt.getIdPhieuThue());
				}
			});
//             {
//                try {
//                    if (PhieuDatPhong.getBookingTicketOfRoom(phong.getMaPhong()) == null) {
//                        btnRight.setDisable(true);
//                    }
//                } catch (Exception ex) {
//                    Logger.getLogger(GD_QLKinhDoanhPhongController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
			break;

		}
		btnRight.setStyle("-fx-background-color: #379F10; -fx-text-fill: #fff; -fx-font-size: 16");
		HBox hbox = new HBox(btnLeft, btnRight);
		hbox.setSpacing(10);
		hbox.setPadding(new Insets(0, 0, 8, 0));
		hbox.setAlignment(Pos.CENTER);
		hbox.setVisible(false);
		roomItem.getChildren().add(hbox);

		roomItem.setAlignment(Pos.CENTER);
		((Pane) roomItem).hoverProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal) {
				hbox.setVisible(true);
			} else {
				hbox.setVisible(false);
			}
		});

		return roomItem;
		
	}
	
	

	private void checkTrangThai() {
	    ArrayList<PhieuThuePhong> dspt = new PhieuThuePhong_DAO().layPhieuThueTheoHieuLuc(true);
	    LocalDateTime now = LocalDateTime.now();

	    for (PhieuThuePhong pt : dspt) {
	        LocalDateTime tgnp = new PhieuThuePhong_DAO().getThoiGianNhanPhong(pt.getIdPhieuThue());
	        LocalDateTime tggp = new PhieuThuePhong_DAO().getThoiGianTraPhong(pt.getIdPhieuThue());
	        Phong p = new Phong_DAO().getPhongTheoMa(pt.getPhong().getIdPhong());

	        // Check if booking is for today
	        boolean isBookingForToday = tgnp.toLocalDate().equals(now.toLocalDate());
	        
	        if (isBookingForToday) {
	            // If booking is for today, show SAPCHECKIN status
	            if (now.isBefore(tgnp)) {
	                p.setTrangThai(TrangThaiPhong.SAPCHECKIN);
	                new Phong_DAO().capNhatTrangThaiPhong(p);
	            }
	        } else {
	            // If booking is for future date, show as TRONG with booking date
	            if (now.isBefore(tgnp)) {
	                p.setTrangThai(TrangThaiPhong.TRONG);
	                // You might want to store the booking date somewhere to display it
	                // This could be in a separate field in the Phong class
	                new Phong_DAO().capNhatTrangThaiPhong(p);
	            }
	        }

	        // Rest of status checks remain the same
	        if (now.isAfter(tgnp) && now.isBefore(tggp.minusHours(2))) {
	            p.setTrangThai(TrangThaiPhong.DANGTHUE);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        }

	        if (!now.isAfter(tggp) && !now.isBefore(tggp.minusHours(2))) {
	            p.setTrangThai(TrangThaiPhong.SAPCHECKOUT);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        }

	        if (now.isAfter(tggp)) {
	            p.setTrangThai(TrangThaiPhong.TRONG);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	            pt.setHieuLuc(Boolean.FALSE);
	            new PhieuThuePhong_DAO().suaPhieuThue(pt);
	        }
	    }
	}
	
	@FXML
	private void moGDDoiPhong() throws IOException {
	    // Truyền mã phòng hiện tại sang giao diện đổi phòng
	    if (roomID != null && !roomID.isEmpty()) {
	        GD_DoiPhong_Controller.maPhongHienTai = roomID;
	    }
	    App.setRoot("GD_DoiPhong");
	}
	@FXML
	private void moGDGiaHan() throws IOException {
		App.setRoot("GD_GiaHanPhong");
	}
	@FXML
	private void moGDDatPhong() throws IOException {
		App.openModal("GD_DatPhong", 800, 684);
	}

	@FXML
	private void moGDThanhToan() throws IOException {
		App.openModal("GD_ThanhToan", 1280, 740);
	}

    
	private void addUserLogin() {
		TaiKhoan tk = App.tk;
		maNV.setText(String.valueOf(tk.getNhanVien().getIdNhanVien()));
		tenNV.setText(String.valueOf(tk.getNhanVien().getTenNhanVien()));
	}
    @FXML
    void dongUngDung(MouseEvent event) throws IOException {
		App.user = "";
		Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		stage.close();
		App.openModal("GD_DangNhap");
    }
}

