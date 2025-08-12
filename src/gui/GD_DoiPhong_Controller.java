package gui;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import dao.PhieuThuePhong_DAO;
import dao.Phong_DAO;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.App;


public class GD_DoiPhong_Controller implements Initializable{
	public static String maPhongHienTai; // để đổi phòng

	@FXML
    private ImageView avt;
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
    @FXML
    private ImageView icon_TimKiem1;

    @FXML
    private Label kb_UuDai;

    @FXML
    private Label lb_DichVu;

    @FXML
    private Label lb_DoiPhong;

    @FXML
    private Label lb_GiaHanPhong;

    @FXML
    private Label lb_HoaDon;

    @FXML
    private Label lb_HuyPhong;

    @FXML
    private Label lb_KhachHang;

    @FXML
    private Label lb_NgayNhan;

    @FXML
    private Label lb_Ngaytra;

    @FXML
    private Label lb_NhanVien;

    @FXML
    private Label lb_Phong;

    @FXML
    private Label lb_SDT;

    @FXML
    private Label lb_SoDoPhong;

    @FXML
    private Label lb_TKDoanhThu;

    @FXML
    private Label lb_TKKhachHang;

    @FXML
    private Label lb_TKSanPham;

    @FXML
    private Label lb_TaiKhoan;

    @FXML
    private Label lb_maPhong;

    @FXML
    private Label lb_tenKH;
    
    @FXML
    private Label lb_tieuChi;

    @FXML
    private GridPane scrollPane_GDDOi;
    
    @FXML
    private Button btnTim;
    
    @FXML
    private TextField txt_MaPhong;
    
    @FXML
    private ComboBox<String> cbbLoaiPhong;

    @FXML
    private ComboBox<String> cbbTieuChi;
    
    public String maPhongDoi;
    public ArrayList<PhieuThuePhong> list;
    public PhieuThuePhong[] pthople;
    public String maphong;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Reset các label
        lb_maPhong.setText("");
        lb_tenKH.setText("");
        lb_SDT.setText("");
        lb_NgayNhan.setText("");
        lb_Ngaytra.setText("");

        if (maPhongHienTai != null && !maPhongHienTai.isEmpty()) {
            try {
                // Tự động điền mã phòng và tìm kiếm
//                txt_MaPhong.setText(maPhongHienTai);
                
                // Gọi phương thức tìm kiếm phiếu thuê
                timKiemPhieuThue(maPhongHienTai);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Đã xảy ra lỗi: " + e.getMessage());
                alert.showAndWait();
            } finally {
                // Luôn reset giá trị static
                maPhongHienTai = null;
            }
        }


        Phong_DAO dsP = new Phong_DAO();
        renderArrayPhong(dsP.getPhongTheoTrangThaiDanhSach(2));
        
        // Cài đặt sự kiện tìm kiếm
        btnTim.setOnAction(even -> timKiemPhieuThue(txt_MaPhong.getText()));
        
        // Thiết lập ComboBox
        ObservableList<String> list = FXCollections.observableArrayList("Tất cả", "Phòng đơn", "Phòng đôi","Phòng gia đình");
        cbbLoaiPhong.setItems(list);
        cbbLoaiPhong.setValue("Tất cả");
        
        ObservableList<String> list1 = FXCollections.observableArrayList("Tất cả", "View biển", "View thành phố","");
        cbbTieuChi.setItems(list1);
        cbbTieuChi.setValue("Tất cả");
        
        // Thêm sự kiện lọc
        cbbLoaiPhong.setOnAction(event -> locDanhSachPhong());
        cbbTieuChi.setOnAction(event -> locDanhSachPhong());
        
        addUserLogin();
    }

    private void timKiemPhieuThue(String maPhong) {
        if (maPhong == null || maPhong.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Vui lòng nhập mã phòng.").showAndWait();
            return;
        }

        PhieuThuePhong_DAO dsPT = new PhieuThuePhong_DAO();
        LocalDateTime gioHienTai = LocalDateTime.now();
        
        ArrayList<PhieuThuePhong> danhSachPhieuThue = dsPT.layPhieuThueTheoMaPhong(maPhong);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PhieuThuePhong phieuHienTai = null;
        for (PhieuThuePhong phieuThue : danhSachPhieuThue) {
            LocalDateTime thoiGianNhan = phieuThue.getThoiGianNhanPhong().atStartOfDay();
            LocalDateTime thoiGianTra = phieuThue.getThoiHanGiaoPhong().atStartOfDay();

            // Thêm điều kiện kiểm tra TrangThai == true
            if (!gioHienTai.isAfter(thoiGianTra) && 
                !gioHienTai.isBefore(thoiGianNhan) && 
                phieuThue.getHieuLuc() == true) {  // Thêm điều kiện này
                phieuHienTai = phieuThue;
                break;
            }
        }

        // Nếu tìm thấy phiếu thuê
        if (phieuHienTai != null) {
            lb_maPhong.setText(phieuHienTai.getPhong().getIdPhong());
            lb_tieuChi.setText(phieuHienTai.getPhong().getTieuChi());
            lb_tenKH.setText(phieuHienTai.getKhachHang().getTenKhachHang());
            lb_SDT.setText(phieuHienTai.getKhachHang().getSoDienThoai());
            lb_NgayNhan.setText(phieuHienTai.getThoiGianNhanPhong().format(formatter));
            lb_Ngaytra.setText(phieuHienTai.getThoiHanGiaoPhong().format(formatter));

            pthople = new PhieuThuePhong[1];
            pthople[0] = phieuHienTai;
        } else {
            new Alert(Alert.AlertType.WARNING, "Không tìm thấy phiếu thuê hợp lệ cho phòng này.").showAndWait();
            
            lb_maPhong.setText("");
            lb_tieuChi.setText("");
            lb_tenKH.setText("");
            lb_SDT.setText("");
            lb_NgayNhan.setText("");
            lb_Ngaytra.setText("");
        }
    }

	private void locDanhSachPhong() {
	    Phong_DAO dsP = new Phong_DAO();
	    String loaiPhong = cbbLoaiPhong.getValue();
	    String tieuChi = cbbTieuChi.getValue();

	    // Lấy danh sách phòng ban đầu
	    ArrayList<Phong> danhSachPhong = dsP.getPhongTheoTrangThaiDanhSach(2);

	    // Lọc theo loại phòng
	    if (!loaiPhong.equals("Tất cả")) {
	        danhSachPhong = filterByLoaiPhong(danhSachPhong, loaiPhong);
	    }

	    // Lọc theo tiêu chí
	    if (!tieuChi.equals("Tất cả")) {
	        danhSachPhong = filterByTieuChi(danhSachPhong, tieuChi);
	    }

	    // Render lại danh sách phòng
	    renderArrayPhong(danhSachPhong);
	}

	private ArrayList<Phong> filterByLoaiPhong(ArrayList<Phong> danhSachPhong, String loaiPhong) {
	    ArrayList<Phong> filteredList = new ArrayList<>();
	    for (Phong phong : danhSachPhong) {
	        if (phong.getLoaiPhong().toString().equals(loaiPhong)) {
	            filteredList.add(phong);
	        }
	    }
	    return filteredList;
	}

	private ArrayList<Phong> filterByTieuChi(ArrayList<Phong> danhSachPhong, String tieuChi) {
	    ArrayList<Phong> filteredList = new ArrayList<>();
	    for (Phong phong : danhSachPhong) {
	        if (phong.getTieuChi().toString().equals(tieuChi)) {
	            filteredList.add(phong);
	        }
	    }
	    return filteredList;
	}
	public void renderArrayPhong(ArrayList<Phong> dsPhong) {
	    if (scrollPane_GDDOi instanceof GridPane) {
	        GridPane grid = (GridPane) scrollPane_GDDOi;
	        grid.getChildren().clear();
	        
	        // Thiết lập khoảng cách giữa các ô
	        grid.setHgap(20); 
	        grid.setVgap(20); 
	        grid.setPadding(new Insets(20));
	        
	        // Lấy kích thước của ScrollPane
	        double availableWidth = scrollPane_GDDOi.getWidth() - 60; // Trừ đi padding và scrollbar
	        double columnWidth = (availableWidth - 40) / 3; // Trừ đi khoảng cách giữa các cột (20*2)
	        
	        // Thêm ColumnConstraints để đặt khoảng cách giữa các cột
	        grid.getColumnConstraints().clear();
	        for (int i = 0; i < 3; i++) {
	            ColumnConstraints column = new ColumnConstraints();
	            column.setHgrow(Priority.SOMETIMES);
	            column.setMinWidth(columnWidth);
	            column.setPrefWidth(columnWidth);
	            grid.getColumnConstraints().add(column);
	        }
	        
	        // Số cột tối đa trong grid
	        int maxColumns = 3;
	        
	        // Render từng phòng
	        for (int i = 0; i < dsPhong.size(); i++) {
	            Phong phong = dsPhong.get(i);
	            Pane phongPane = taoGiaoDienPhong(phong);
	            
	            // Tính toán vị trí của phòng trong grid
	            int column = i % maxColumns;
	            int row = i / maxColumns;
	            
	            // Thêm phòng vào grid tại vị trí tính toán
	            grid.add(phongPane, column, row);
	        }
	    }
	}
	private VBox selectedRoomItem = null;

	public Pane taoGiaoDienPhong(Phong phong) {
	    // Tạo VBox chứa thông tin phòng với kích thước cố định
	    VBox roomItem = new VBox(10);
	    
	    // Để VBox có thể mở rộng theo không gian có sẵn
	    roomItem.setPrefWidth(Region.USE_COMPUTED_SIZE);
	    roomItem.setPrefHeight(180);
	    roomItem.setMinHeight(180);
	    roomItem.setMaxHeight(180);
	    
	    roomItem.setStyle("-fx-background-color: #31c57e; -fx-border-color: #000000; -fx-border-width: 1; -fx-padding: 10;");
	    roomItem.setAlignment(Pos.CENTER);

	    // Mã phòng
	    Label lblMaPhong = new Label(phong.getIdPhong());
	    lblMaPhong.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: white;");
	    lblMaPhong.setWrapText(true);
	    lblMaPhong.setAlignment(Pos.CENTER);

	    // Loại phòng
	    Label lblLoaiPhong = new Label(phong.getLoaiPhong().toString());
	    lblLoaiPhong.setStyle("-fx-font-size: 16; -fx-font-weight: normal; -fx-text-fill: white;");
	    lblLoaiPhong.setWrapText(true);
	    lblLoaiPhong.setAlignment(Pos.CENTER);
	    
	    // Tiêu chí
	    Label lblTieuChi = new Label(phong.getTieuChi().toString());
	    lblTieuChi.setStyle("-fx-font-size: 16; -fx-font-weight: normal; -fx-text-fill: white;");
	    lblTieuChi.setWrapText(true);
	    lblTieuChi.setAlignment(Pos.CENTER);

	    // Nút đổi phòng
	    Button btnDoi = new Button("Đổi phòng");
	    btnDoi.setPrefWidth(120);
	    btnDoi.setPrefHeight(35);
	    btnDoi.setStyle("-fx-background-color: #2972d3; -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand;");
	    
	    // Sự kiện khi nhấn vào phòng
	    roomItem.setOnMouseClicked(e -> {
	        // Đổi màu nền roomItem đã chọn
	        if (selectedRoomItem != null) {
	            // Reset style của roomItem trước đó
	            selectedRoomItem.setStyle("-fx-background-color: #31c57e; -fx-border-color: #000000; -fx-border-width: 1; -fx-padding: 10;");
	        }
	        // Cập nhật roomItem mới được chọn
	        selectedRoomItem = roomItem;
	        roomItem.setStyle("-fx-background-color: #2E8B57; -fx-border-color: #000000; -fx-border-width: 1; -fx-padding: 10;");
	        
	        // Lấy mã phòng khi click
	        maPhongDoi = phong.getIdPhong();
//	        txt_MaPhong.setText(maPhongDoi);
	    });
	    

	    btnDoi.setOnAction((event) -> {
	        if (pthople[0] != null && maPhongDoi != null) {
	            Phong phongMoi = new Phong_DAO().getPhongTheoMa(maPhongDoi);
	            System.out.println("Trạng thái phòng mới trước khi đổi: " + phongMoi.getTrangThai());
	            System.out.println("Mã phòng mới: " + phongMoi.getIdPhong());
	            
	            if (phongMoi.getTrangThai() == TrangThaiPhong.TRONG) {
	                // Lưu trữ phòng cũ trước khi thay đổi
	                Phong pCu = pthople[0].getPhong(); // Lấy phòng cũ từ phiếu thuê ban đầu
	                
	                // Thực hiện đổi phòng
	             // Cập nhật phiếu thuê với phòng mới
	                PhieuThuePhong pthuemoi = pthople[0];
	                pthuemoi.setPhong(phongMoi); // Đảm bảo rằng bạn đã cập nhật phòng mới
	                boolean suaPhieuThue = new PhieuThuePhong_DAO().suaPhieuThue(pthuemoi);
	                if (!suaPhieuThue) {
	                    new Alert(Alert.AlertType.ERROR, "Không thể cập nhật phiếu thuê.").showAndWait();
	                    return;
	                }

	                // Cập nhật trạng thái phòng mới
	                phongMoi.setTrangThai(TrangThaiPhong.DANGTHUE);
	                boolean capNhatPhongMoi = new Phong_DAO().capNhatTrangThaiPhong(phongMoi);
	                System.out.println("Cập nhật phòng mới: " + capNhatPhongMoi);

	                // Cập nhật trạng thái phòng cũ
	                pCu.setTrangThai(TrangThaiPhong.TRONG);
	                boolean capNhatPhongCu = new Phong_DAO().capNhatTrangThaiPhong(pCu);
	                System.out.println("Cập nhật phòng cũ: " + capNhatPhongCu);

	                // Kiểm tra lại trạng thái phòng mới sau khi cập nhật
	                Phong phongMoiSauCapNhat = new Phong_DAO().getPhongTheoMa(maPhongDoi);
	                System.out.println("Trạng thái phòng mới sau khi đổi: " + phongMoiSauCapNhat.getTrangThai());

	                new Alert(Alert.AlertType.CONFIRMATION, "Đổi phòng thành công!").showAndWait();

	                // Làm mới giao diện
	                renderArrayPhong(new Phong_DAO().getPhongTheoTrangThaiDanhSach(2));

	                // Làm mới các label
	                lb_maPhong.setText("");
	                lb_tieuChi.setText("");
	                lb_tenKH.setText("");
	                lb_SDT.setText("");
	                lb_NgayNhan.setText("");
	                lb_Ngaytra.setText("");
	                txt_MaPhong.setText("");
	                pthople[0] = null;
	                maPhongDoi = null;
	            } else {
	                new Alert(Alert.AlertType.WARNING, "Phòng được chọn không khả dụng để đổi.").showAndWait();
	            }
	        }
	    });
	    
	    roomItem.getChildren().addAll(lblMaPhong, lblLoaiPhong, lblTieuChi, btnDoi);

	 // Thêm hiệu ứng hover
	    roomItem.setOnMouseEntered(e -> {
	        roomItem.setStyle("-fx-background-color: #28a66a; -fx-border-color: #000000; -fx-border-width: 1; -fx-padding: 10;");
	    });

	    roomItem.setOnMouseExited(e -> {
	        // Kiểm tra nếu roomItem không phải là selectedRoomItem
	        if (roomItem != selectedRoomItem) {
	            roomItem.setStyle("-fx-background-color: #31c57e; -fx-border-color: #000000; -fx-border-width: 1; -fx-padding: 10;");
	        }
	    });

	    return roomItem;
	}


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
	@FXML
	private void moGDDoiPhong() throws IOException {
		App.setRoot("GD_DoiPhong");
	}

	@FXML
	private void moGDDatPhong() throws IOException {
		App.openModal("GD_DatPhong", 800, 684);
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
