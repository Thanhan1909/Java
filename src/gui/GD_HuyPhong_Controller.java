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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.KhachHang_DAO;
import dao.PhieuThuePhong_DAO;
import dao.Phong_DAO;
import entity.KhachHang;
import entity.PhieuThuePhong;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import main.App;

public class GD_HuyPhong_Controller implements Initializable{
	
	@FXML
    private ImageView avt;

    @FXML
    private Button btnTim;

    @FXML
    private TableColumn<Object[], String> clMaPhong;

    @FXML
    private TableColumn<Object[], Integer> clSTT;

    @FXML
    private TableColumn<Object[], LocalDate> clTGNP;

    @FXML
    private TableColumn<Object[], LocalDate> clTGTP;

    @FXML
    private TableColumn<Object[], String> clMaPhieuThue;

    @FXML
    private TableColumn<Object[], String> cltenKH;
    
    @FXML
    private TableColumn<Object[], Void> clHuy;

    @FXML
    private ImageView icon_QuanLy;

    @FXML
    private ImageView icon_ThongKe;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private ImageView icon_TimKiem1;

    @FXML
    private Label l;

    @FXML
    private Label lb_DonGia;

    @FXML
    private Label lb_LoaiPhong;

    @FXML
    private Label lb_QuanLy;

    @FXML
    private Label lb_ThongKe;

    @FXML
    private Label lb_TieuChi;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label maNV;

    @FXML
    private TableView<Object[]> tbPhieuThue;

    @FXML
    private Label tenNV;

    @FXML
    private TextField txt_CCCD;
    	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    lb_LoaiPhong.setText("");
	    lb_TieuChi.setText("");
	    lb_DonGia.setText("");
 
	    loadDuLieu();
	    
	    tbPhieuThue.setOnMouseClicked(event -> {
	        if (event.getClickCount() == 1) { 
	            Object[] selectedRow = tbPhieuThue.getSelectionModel().getSelectedItem();
	            if (selectedRow != null) {
	            	String maPhong = selectedRow[2].toString();
	            	Phong_DAO pdao = new Phong_DAO();
	            	Phong p = pdao.getPhongTheoMa(maPhong);
	                // Giả sử rằng vị trí của DonGia, LoaiPhong, TieuChi trong mảng là 5, 6, 7
	            	lb_DonGia.setText(String.valueOf(p.getDonGia()));
	                lb_LoaiPhong.setText(p.getLoaiPhongString());
	                lb_TieuChi.setText(p.getTieuChi());
	            }
	        }
	    });
	    btnTim.setOnAction(event -> timPhong(txt_CCCD.getText().trim()));

	    addUserLogin();
	}
	
	private void loadDuLieu() {
	    // Tạo danh sách chứa dữ liệu kết hợp
	    ObservableList<Object[]> data = FXCollections.observableArrayList();

	    // Lấy dữ liệu từ DAO
	    PhieuThuePhong_DAO phieuThueDAO = new PhieuThuePhong_DAO();
	    Phong_DAO phongDAO = new Phong_DAO(); // Thêm DAO để truy vấn trạng thái phòng
	    ArrayList<PhieuThuePhong> dsPhieuThue = phieuThueDAO.layPhieuThueTheoHieuLuc(true);
	    KhachHang_DAO khachHangDAO = new KhachHang_DAO();

	    // Kết hợp dữ liệu từ hai bảng
	    for (PhieuThuePhong pt : dsPhieuThue) {
	    	Phong phong = pt.getPhong();
	        TrangThaiPhong trangThaiPhong = phong.getTrangThai();
	        
	        if (trangThaiPhong == TrangThaiPhong.SAPCHECKIN || trangThaiPhong == TrangThaiPhong.TRONG) {
	            KhachHang kh = khachHangDAO.getKhachHangTheoMa(pt.getKhachHang().getIdKhachHang());
	            data.add(new Object[]{
	                pt.getIdPhieuThue(),
	                kh.getTenKhachHang(),
	                phong.getIdPhong(),
	                pt.getThoiGianNhanPhong(),
	                pt.getThoiHanGiaoPhong()
	            });
	        }
	        
//	        KhachHang kh = khachHangDAO.getKhachHangTheoMa(pt.getKhachHang().getIdKhachHang());
//	        data.add(new Object[]{
//	            pt.getIdPhieuThue(),
//	            kh.getTenKhachHang(),
//	            pt.getPhong().getIdPhong(),
//	            pt.getThoiGianNhanPhong(),
//	            pt.getThoiHanGiaoPhong()
//	        });
	    }

	    // Gắn dữ liệu vào TableView
	    tbPhieuThue.setItems(data);

	    // Cột STT tự tăng
	    clSTT.setCellValueFactory(cellData -> 
	        new ReadOnlyObjectWrapper<>(tbPhieuThue.getItems().indexOf(cellData.getValue()) + 1)
	    );

	    // Gắn giá trị cột
	    clMaPhieuThue.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[0]));
	    cltenKH.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[1]));
	    clMaPhong.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[2]));

	    // Định dạng cột TGNP
	    clTGNP.setCellValueFactory(cellData -> new SimpleObjectProperty<>((LocalDate) cellData.getValue()[3]));
	    clTGNP.setCellFactory(column -> new TableCell<Object[], LocalDate>() {
	        @Override
	        protected void updateItem(LocalDate item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || item == null) {
	                setText(null);
	            } else {
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	                setText(formatter.format(item));
	            }
	        }
	    });

	    // Định dạng cột TGTP
	    clTGTP.setCellValueFactory(cellData -> new SimpleObjectProperty<>((LocalDate) cellData.getValue()[4]));
	    clTGTP.setCellFactory(column -> new TableCell<Object[], LocalDate>() {
	        @Override
	        protected void updateItem(LocalDate item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || item == null) {
	                setText(null);
	            } else {
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	                setText(formatter.format(item));
	            }
	        }
	    });

	    // Gắn giá trị cho cột Hủy
	    clHuy.setCellFactory(col -> new TableCell<>() {
	        private final Button btnHuy = new Button("Huỷ");

	        {
	            btnHuy.setStyle("-fx-background-color: red; -fx-text-fill: white;");
	            btnHuy.setOnAction(event -> {
	                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	                alert.setTitle("Xác nhận huỷ");
	                alert.setHeaderText("Bạn có chắc chắn muốn huỷ phòng này?");
	                alert.setContentText("Thao tác này không thể hoàn tác.");

	                Optional<ButtonType> result = alert.showAndWait();
	                if (result.isPresent() && result.get() == ButtonType.OK) {
	                    Object[] rowData = getTableView().getItems().get(getIndex());
	                    String maPhieuThue = (String) rowData[0];

	                    PhieuThuePhong_DAO phieuThueDAO = new PhieuThuePhong_DAO();
	                    PhieuThuePhong phieuThue = phieuThueDAO.layPhieuThueTheoMa(maPhieuThue);
	                    
	                    if (phieuThue != null) {
	                        phieuThue.setHieuLuc(false);
	                        boolean isSuccess = phieuThueDAO.suaPhieuThue(phieuThue);
	                        
	                        if (isSuccess) {
	                            // Tải lại dữ liệu
	                            loadDuLieu();
	                            
	                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
	                            successAlert.setTitle("Thành công");
	                            successAlert.setHeaderText(null);
	                            successAlert.setContentText("Đã huỷ phòng thành công!");
	                            successAlert.show();
	                        } else {
	                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	                            errorAlert.setTitle("Lỗi");
	                            errorAlert.setHeaderText(null);
	                            errorAlert.setContentText("Không thể huỷ phòng. Vui lòng thử lại!");
	                            errorAlert.show();
	                        }
	                    }
	                }
	            });
	        }

	        @Override
	        protected void updateItem(Void item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setGraphic(null);
	            } else {
	                setGraphic(btnHuy);
	                setAlignment(Pos.CENTER);
	            }
	        }
	    });
	}


	private void timPhong(String cccd) {
	    // Kiểm tra xem người dùng đã nhập CCCD chưa
	    if (cccd.isEmpty()) {
	        loadDuLieu();
	        return;
	    }

	    // Tìm kiếm khách hàng theo CCCD
	    KhachHang_DAO khachHangDAO = new KhachHang_DAO();
	    KhachHang khachHang = khachHangDAO.getKhachHangTheoCCCD(cccd);

	    // Kiểm tra xem khách hàng có tồn tại không
	    if (khachHang == null) {
	        new Alert(Alert.AlertType.WARNING, "Không tìm thấy khách hàng với CCCD này.").showAndWait();
	        return;
	    }

	    // Lấy danh sách phiếu thuê của khách hàng
	    PhieuThuePhong_DAO phieuThueDAO = new PhieuThuePhong_DAO();
	    ArrayList<PhieuThuePhong> phieuThueList = phieuThueDAO.layPhieuThueTheoMaKH(khachHang.getIdKhachHang());

	    // Lọc danh sách phiếu thuê còn hiệu lực
	    ArrayList<PhieuThuePhong> filteredPhieuThue = new ArrayList<>();
	    for (PhieuThuePhong pt : phieuThueList) {
	        if (pt.getHieuLuc()) {
	            filteredPhieuThue.add(pt);
	        }
	    }

	    // Kiểm tra xem khách hàng có phiếu thuê phòng nào không
	    if (filteredPhieuThue.isEmpty()) {
	        new Alert(Alert.AlertType.WARNING, "Khách hàng này chưa đặt phòng.").showAndWait();
	        return;
	    }

	    // Tạo danh sách dữ liệu mới cho TableView
	    ObservableList<Object[]> data = FXCollections.observableArrayList();

	    // Thêm dữ liệu từ các phiếu thuê đã lọc
	    for (PhieuThuePhong pt : filteredPhieuThue) {
	        data.add(new Object[]{
	            pt.getIdPhieuThue(),
	            khachHang.getTenKhachHang(),
	            pt.getPhong().getIdPhong(),
	            pt.getThoiGianNhanPhong(),
	            pt.getThoiHanGiaoPhong()
	        });
	    }

	    // Cập nhật TableView với dữ liệu mới
	    tbPhieuThue.setItems(data);
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
	
   
    
    private void checkTrangThai() {
	    ArrayList<PhieuThuePhong> dspt = new PhieuThuePhong_DAO().layPhieuThueTheoHieuLuc(true);
	    LocalDateTime now = LocalDateTime.now();

	    for (PhieuThuePhong pt : dspt) {
	        LocalDateTime tgnp = new PhieuThuePhong_DAO().getThoiGianNhanPhong(pt.getIdPhieuThue());
	        LocalDateTime tggp = new PhieuThuePhong_DAO().getThoiGianTraPhong(pt.getIdPhieuThue());

	        Phong p = new Phong_DAO().getPhongTheoMa(pt.getPhong().getIdPhong());

	     // Kiểm tra trạng thái sắp nhận phòng (SẮP CHECKIN)
	        if (!now.isAfter(tgnp) && !now.isBefore(tgnp.minusHours(24))) {
	            p.setTrangThai(TrangThaiPhong.SAPCHECKIN);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        }
	        // Trạng thái trống nếu thời gian nhận phòng còn trên 12 giờ
	        else if (now.isBefore(tgnp.minusHours(24))) {
	            p.setTrangThai(TrangThaiPhong.TRONG);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        }


	        // Kiểm tra trạng thái đang thuê (DANGTHUE)
	        if (now.isAfter(tgnp) && now.isBefore(tggp.minusHours(2))) {
	            p.setTrangThai(TrangThaiPhong.DANGTHUE);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        }

	        // Kiểm tra trạng thái sắp trả phòng (SẮP CHECKOUT)
	        if (!now.isAfter(tggp) && !now.isBefore(tggp.minusHours(2))) {
	            p.setTrangThai(TrangThaiPhong.SAPCHECKOUT);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	        }

	        // Kiểm tra trạng thái sau khi trả phòng (TRỐNG)
	        if (now.isAfter(tggp)) {
	            p.setTrangThai(TrangThaiPhong.TRONG);
	            new Phong_DAO().capNhatTrangThaiPhong(p);
	            pt.setHieuLuc(Boolean.FALSE);
	            new PhieuThuePhong_DAO().suaPhieuThue(pt);
	        }
	    }
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
