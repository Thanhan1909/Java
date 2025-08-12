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

import dao.Enum_ChucVu;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.KhuyenMai_DAO;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.NhanVien;
import entity.TaiKhoan;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.App;

public class GD_QLHoaDon_Controller implements Initializable{
	@FXML
    private ImageView avt;

    @FXML
    private Button btnThem;

    @FXML
    private Button btnTim;

    @FXML
    private Button btnXoaTrang;

    @FXML
    private TableColumn<HoaDon, String> clMaHD;

    @FXML
    private TableColumn<HoaDon, String> clMaKH;

    @FXML
    private TableColumn<HoaDon, String> clMaNV;

    @FXML
    private TableColumn<HoaDon, String> clSTT;

    @FXML
    private TableColumn<HoaDon, String> clTGCI;

    @FXML
    private TableColumn<HoaDon, String> clTGT;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private TableColumn<HoaDon, String> clMaKM;

    @FXML
    private TextField lb_HD;

    @FXML
    private TextField lb_MaKH;

    @FXML
    private TextField lb_MaKM;

    @FXML
    private TextField lb_MaNV;

    @FXML
    private TextField lb_TgCheckIn;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private TextField lb_tgTao;

    @FXML
    private TableView<HoaDon> tableNhanVien;
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
    @FXML
    void moGiaoDienDichVu(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLDichVu");
    }

    @FXML
    void moGiaoDienHoaDon(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLHoaDon");
    }

    @FXML
    void moGiaoDienKhachHang(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLKhachHang");
    }

    @FXML
    void moGiaoDienPhong(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLPhong");
    }

    @FXML
    void moGiaoDienQLNV(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLNhanVien");
    }

    @FXML
    void moGiaoDienQuanLy(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLPhong");
    }

    @FXML
    void moGiaoDienTaiKhoan(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLTaiKhoan");
    }

    @FXML
    void moGiaoDienThongKe(MouseEvent event) throws IOException {
    	App.setRoot("GD_ThongKeDoanhThu");
    }

    @FXML
    void moGiaoDienThuePhong(MouseEvent event) throws IOException {
    	App.setRoot("GD_SoDoPhong");
    }

    @FXML
    void moGiaoDienTimKiem(MouseEvent event) throws IOException {
    	App.setRoot("GD_TKHoaDon");
    }

    @FXML
    void moGiaoDienUuDai(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLUuDai");
    }
    
    @FXML
    void moGiaoDienTKHD(MouseEvent event) throws IOException {
    	App.setRoot("GD_TKHoaDon");
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clSTT.setCellFactory(col -> {
			return new TableCell<HoaDon, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        // Số thứ tự = index + 1
                        setText(String.valueOf(getIndex() + 1));
                    }
                }
			};
		});
        clSTT.setStyle("-fx-alignment: CENTER;");
        clSTT.setPrefWidth(70);
        clSTT.setResizable(false);
        
        clMaHD.setCellValueFactory(new PropertyValueFactory<>("idHoaDon"));
        
        clMaNV.setCellValueFactory(cellData -> {
        	String manv = cellData.getValue().getNhanVienLap().getIdNhanVien();
        	return new ReadOnlyStringWrapper(manv);
        });
        clMaKH.setCellValueFactory(cellData -> {
        	String manv = cellData.getValue().getKhachHang().getIdKhachHang();
        	return new ReadOnlyStringWrapper(manv);
        });
        clMaKM.setCellValueFactory(cellData -> {
        	KhuyenMai km = cellData.getValue().getKhuyenmai();
        	if (km != null) {
        		return new ReadOnlyStringWrapper(km.getIdKhuyenMai());
        	} else {
        		return new ReadOnlyStringWrapper("");
        	}
        });
        
        clTGT.setCellValueFactory(cellData -> {
            LocalDateTime tgTao = cellData.getValue().getThoiGianTao();
            if (tgTao != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
                String ngaySinhFormatted = tgTao.format(formatter);
                return new ReadOnlyStringWrapper(ngaySinhFormatted);
            }
            return new ReadOnlyStringWrapper("");
        });
        
        
        clTGCI.setCellValueFactory(cellData -> {
            LocalDateTime tgCK = cellData.getValue().getThoiGianCheckin();
            if (tgCK != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
                String ngaySinhFormatted = tgCK.format(formatter);
                return new ReadOnlyStringWrapper(ngaySinhFormatted);
            }
            return new ReadOnlyStringWrapper("");
        });
        
        try {
            // Tải dữ liệu ban đầu khi mở màn hình
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Lỗi khi tải danh sách hóa đơn ban đầu: " + e.getMessage());
        }
        
        tableNhanVien.setOnMouseClicked(event -> {
            HoaDon selectedHoaDon = tableNhanVien.getSelectionModel().getSelectedItem();
            if (selectedHoaDon != null) {
                // Cập nhật thông tin vào các trường
                lb_HD.setText(selectedHoaDon.getIdHoaDon());
                lb_MaNV.setText(selectedHoaDon.getNhanVienLap().getIdNhanVien());
                lb_MaKH.setText(selectedHoaDon.getKhachHang().getIdKhachHang());
                lb_MaKM.setText(selectedHoaDon.getKhuyenmai().getIdKhuyenMai());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
                LocalDateTime tgTao = selectedHoaDon.getThoiGianTao();
                LocalDateTime tgCK = selectedHoaDon.getThoiGianCheckin();
                lb_tgTao.setText(tgTao.format(formatter));
                lb_TgCheckIn.setText(tgCK.format(formatter));
            }
        });
        
        
        addUserLogin();
	}
    private void loadTableData() {
        try {
            HoaDon_DAO hdDao = new HoaDon_DAO();
            ArrayList<HoaDon> dshd = hdDao.getAllHoaDon();
            
            
            ObservableList<HoaDon> observableList = FXCollections.observableArrayList(dshd);
            tableNhanVien.setItems(observableList);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Có thể thêm Alert để thông báo lỗi cho người dùng
        }
    }
    @FXML
    void themHD(MouseEvent event) {
        try {
            // Generate and validate new ID
            String idHoaDonMoi = HoaDon.autoIdHoaDon();
            System.out.println("Attempting to create HoaDon with ID: " + idHoaDonMoi);
            
            // Create HoaDon object
            NhanVien nv = new NhanVien_DAO().getNhanVienTheoMa(lb_MaNV.getText());
            if (nv == null) {
                showAlert(AlertType.ERROR, "Không tìm thấy nhân viên!");
                return;
            }
            
            KhachHang kh = new KhachHang_DAO().getKhachHangTheoMa(lb_MaKH.getText());
            if (kh == null) {
                showAlert(AlertType.ERROR, "Không tìm thấy khách hàng!");
                return;
            }
            
            KhuyenMai km = null;
            if (!lb_MaKM.getText().trim().isEmpty()) {
                km = new KhuyenMai_DAO().layKhuyenMaiTheoMa(lb_MaKM.getText());
            }
            
            LocalDateTime thoiGianTao = LocalDateTime.now();
            LocalDateTime thoiGianCheckin = LocalDateTime.now();
            
            HoaDon hoaDon = new HoaDon(idHoaDonMoi, nv, kh, km, thoiGianTao, thoiGianCheckin);
            
            // Save HoaDon
            HoaDon_DAO hddao = new HoaDon_DAO();
            boolean saved = hddao.themHoaDon(hoaDon);
            
            if (saved) {
                System.out.println("HoaDon saved successfully: " + idHoaDonMoi);
                showAlert(AlertType.INFORMATION, "Đã tạo hóa đơn: " + idHoaDonMoi);
                loadTableData();
                xoaTrang(null);
            } else {
                showAlert(AlertType.ERROR, "Không thể lưu hóa đơn. Kiểm tra console để biết thêm chi tiết.");
            }
            
        } catch (Exception e) {
            System.err.println("Error creating HoaDon: " + e.getMessage());
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Lỗi: " + e.getMessage());
        }
    }

    private void showAlert(AlertType type, String content) {
        Alert alert = new Alert(type);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void xoaTrang(MouseEvent event) {
    	lb_HD.setText("");
    	lb_MaKH.setText("");
    	lb_MaKM.setText("");
    	lb_MaNV.setText("");
    	lb_TgCheckIn.setText("");
    	lb_tgTao.setText("");
    	tableNhanVien.getSelectionModel().clearSelection();
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
