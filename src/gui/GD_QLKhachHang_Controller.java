package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entity.KhachHang;
import entity.NhanVien;
import entity.TaiKhoan;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
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

public class GD_QLKhachHang_Controller implements Initializable{

    @FXML
    private ImageView avt;

    @FXML
    private Button btnSua;

    @FXML
    private Button btnThem;

    @FXML
    private Button btnTimKiem;

    @FXML
    private Button btnXoa;

    @FXML
    private Button btnXoaTrang;

    @FXML
    private TableColumn<KhachHang, String> clCCCD;

    @FXML
    private TableColumn<KhachHang, String> clDiem;

    @FXML
    private TableColumn<KhachHang, String> clMaKH;

    @FXML
    private TableColumn<KhachHang, String> clNgaySinh;

    @FXML
    private TableColumn<KhachHang, String> clSDT;

    @FXML
    private TableColumn<KhachHang, String> clSTT;

    @FXML
    private TableColumn<KhachHang, String> clTenKh;

    @FXML
    private Label lb_MaNV;

    @FXML
    private Label lb_diem;

    @FXML
    private TableView<KhachHang> tableNhanVien;

    @FXML
    private TextField txtCCCD;

    @FXML
    private DatePicker txtNgaySinh;

    @FXML
    private TextField txtSDT;
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
    @FXML
    private TextField txtTenKH;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	clSTT.setCellFactory(col -> {
            return new TableCell<KhachHang, String>() {
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

        // Set up các cột cho TableView
        clMaKH.setCellValueFactory(new PropertyValueFactory<>("idKhachHang"));
        clTenKh.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
        clSDT.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        
        // Format ngày sinh
        clNgaySinh.setCellValueFactory(cellData -> {
            LocalDate ngaySinh = cellData.getValue().getNgaySinh();
            if (ngaySinh != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String ngaySinhFormatted = ngaySinh.format(formatter);
                return new ReadOnlyStringWrapper(ngaySinhFormatted);
            }
            return new ReadOnlyStringWrapper("");
        });
        clDiem.setCellValueFactory(new PropertyValueFactory<>("tichDiem"));
        clCCCD.setCellValueFactory(new PropertyValueFactory<>("cccd"));

        // Load dữ liệu
        loadTableData();
        
        
        tableNhanVien.setOnMouseClicked(event -> {
            KhachHang selectedKhachHang = tableNhanVien.getSelectionModel().getSelectedItem();
            if (selectedKhachHang != null) {
                // Cập nhật thông tin vào các trường
                lb_MaNV.setText(selectedKhachHang.getIdKhachHang());
                txtTenKH.setText(selectedKhachHang.getTenKhachHang());
                txtSDT.setText(selectedKhachHang.getSoDienThoai());
                txtCCCD.setText(selectedKhachHang.getCccd());
                txtNgaySinh.setValue(selectedKhachHang.getNgaySinh()); // Đảm bảo kiểu dữ liệu là LocalDate
                lb_diem.setText(String.valueOf(selectedKhachHang.getTichDiem()));

            }
        });
        
        addUserLogin();
    };
    
    private void loadTableData() {
        try {
            KhachHang_DAO khachHangDAO = new KhachHang_DAO();
            ArrayList<KhachHang> dsNV = khachHangDAO.getAllKhachHang();
            
            // Debug: in ra số lượng nhân viên
            System.out.println("Số lượng nhân viên: " + dsNV.size());
            
            ObservableList<KhachHang> observableList = FXCollections.observableArrayList(dsNV);
            tableNhanVien.setItems(observableList);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Có thể thêm Alert để thông báo lỗi cho người dùng
        }
    }
    
    @FXML
    void moGiaoDienTimKiemKH(MouseEvent event) throws IOException {
    	App.setRoot("GD_TKKhachHang");
    }

    @FXML
    void suaTTNV(MouseEvent event) {
        if (lb_MaNV.getText().trim().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn khách hàng cần sửa!");
            alert.showAndWait();
            return;
        }

        // Kiểm tra các trường thông tin bắt buộc
        if (txtTenKH.getText().trim().isEmpty() || 
            txtSDT.getText().trim().isEmpty() || 
            txtNgaySinh.getValue() == null || 
            lb_diem.getText() == null || 
            txtCCCD.getText().isEmpty()) {
            
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
            alert.showAndWait();
            return;
        }
            

        // Lấy thông tin từ các trường
        String maKH = lb_MaNV.getText();
        String tenKH = txtTenKH.getText();
        String sdt = txtSDT.getText();
        LocalDate ngaySinh = txtNgaySinh.getValue();
        int diem = Integer.parseInt(lb_diem.getText());
        String cccd = txtCCCD.getText();

        KhachHang updatedKhachHang = new KhachHang(maKH, tenKH, sdt, ngaySinh, cccd, diem);

        try {
            KhachHang_DAO khachHangDAO = new KhachHang_DAO();
            boolean updated = khachHangDAO.capNhatKhachHangTheoMa(updatedKhachHang);

            if (updated) {
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Thành công");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Cập nhật thông tin khách hàng thành công!");
                successAlert.showAndWait();

                // Cập nhật lại TableView và xóa trắng form
                loadTableData();
                lamMoiKhachHang();
            } else {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Lỗi");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Không thể cập nhật thông tin khách hàng!");
                errorAlert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Lỗi");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Đã xảy ra lỗi trong quá trình cập nhật!\n" + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    @FXML
    void themNV(MouseEvent event) throws Exception {
    	themKhachHang();
    }


    @FXML
    void xoaTrang(MouseEvent event) throws Exception {
    	lamMoiKhachHang();
    }

	private String generateMaKH() {
        // Lấy ngày hiện tại
        LocalDate today = LocalDate.now();
        String day = String.format("%02d", today.getDayOfMonth());
        String month = String.format("%02d", today.getMonthValue());
        String year = String.valueOf(today.getYear()).substring(2); // Lấy 2 chữ số cuối của năm

        KhachHang_DAO kh = new KhachHang_DAO();
        int countToday = kh.getCountOfKhachHangInDay(today);
        
        // Tạo mã nhân viên với số thứ tự là countToday + 1
        String maKH = String.format("KH%s%s%s%02d", year, month, day, countToday + 1);
        
        return maKH;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
    private void showAlertLoi(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
    public void lamMoiKhachHang() throws Exception {
        lb_MaNV.setText("");
        txtTenKH.setText("");
        txtSDT.setText("");
        txtNgaySinh.setValue(null);
        lb_diem.setText("");
        txtCCCD.setText("");
        tableNhanVien.getSelectionModel().clearSelection();
    }
    
    public boolean isNameFormatValid(String name) {
        String[] words = name.split("\\s+");
        for (String word : words) {
            if (!word.matches("\\p{Lu}\\p{Ll}*")) {
                return false;
            }
        }
        return true;
    }
    
    public boolean kiemTraDuLieu() throws Exception{
    	if (txtTenKH.getText().equals("")) {
            showAlertLoi("Lỗi nhập dữ liệu", "Họ tên khách hàng không được rỗng");
            return false;
        }
    	if (!isNameFormatValid(txtTenKH.getText())) {
            showAlertLoi("Lỗi nhập dữ liệu", "Họ tên khách hàng phải in hoa ký tự đầu");
            return false;
        }
    	if (!txtSDT.getText().matches("0[23789]\\d{8}")) {
            showAlertLoi("Lỗi nhập dữ liệu", "Số điện thoại khách hàng là dãy gồm 10 ký số. 2 ký số đầu là {02, 03, 05, 07, 08, 09}");
            return false;
        }
    	if (txtNgaySinh.getValue() == null) {
            showAlert("Lỗi nhập dữ liệu", "Ngày sinh không được rỗng");
            return false;
        }

        if ((LocalDate.now().getYear() - txtNgaySinh.getValue().getYear()) < 18) {
            showAlertLoi("Lỗi nhập dữ liệu", "Nhân viên phải từ 18 trở lên");
            return false;
        }
        
    	if (!txtCCCD.getText().matches("\\d{12}")) {
            showAlertLoi("Lỗi nhập dữ liệu", "CCCD là một dãy gồm 12 số");
            return false;
        }
        if (txtCCCD.getText().equals("")) {
            showAlertLoi("Lỗi nhập dữ liệu", "CCCD khách sạn không được rỗng");
            return false;
        }
		return true;
    }
    public boolean themKhachHang() throws Exception {
        if (!kiemTraDuLieu()) {
            return false;
        }
        KhachHang kh = null;
        String maKh = null;
        String hoTen = txtTenKH.getText();
        String soDienThoai = txtSDT.getText();
        LocalDate ngaySinh = (LocalDate) txtNgaySinh.getValue();
       
        String cccd = txtCCCD.getText();
        int tichDiem = 0;
       


        kh = new KhachHang(null, hoTen, soDienThoai, ngaySinh, cccd, tichDiem);
        if(new KhachHang_DAO().themKhachHang(kh) == true) {
        	showAlert("Thông báo", "Thêm khách hàng thành công");
        	loadTableData();
        }
		return false;
    }
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
    	App.setRoot("GD_TKKhachHang");
    }

    @FXML
    void moGiaoDienUuDai(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLUuDai");
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
