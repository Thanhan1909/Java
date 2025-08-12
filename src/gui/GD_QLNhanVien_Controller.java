package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.beans.property.ReadOnlyStringWrapper;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import dao.Enum_ChucVu;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.ChucVu;
import entity.NhanVien;
import entity.TaiKhoan;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.App;

public class GD_QLNhanVien_Controller implements Initializable{
	
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
	    private ComboBox<String> cbbGioiTinh;
	    
	    @FXML
	    private ComboBox<String> cbbChucVu;

	    @FXML
	    private Label lb_MaNV;

	    @FXML
	    private TextField txtCCCD;

	    @FXML
	    private DatePicker txtNgaySinh;

	    @FXML
	    private TextField txtSDT;

	    @FXML
	    private TextField txtTenNV;
	    
	    @FXML
	    private TableView<NhanVien> tableNhanVien;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clSTT;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clIDNV;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clTenNV;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clSDT;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clNgaySinh;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clGioiTinh;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clCCCD;
	    
	    @FXML
	    private TableColumn<NhanVien, String> clChucVu;
	    @FXML
	    private Label maNV;
	    @FXML
	    private Label tenNV;
	    @Override
	    public void initialize(URL arg0, ResourceBundle arg1) {
	        // Set up ComboBox
	        ObservableList<String> list = FXCollections.observableArrayList("Nam", "Nữ");
	        cbbGioiTinh.setItems(list);
	        cbbGioiTinh.setValue("");
	        
	     // Set up ComboBox
	        ObservableList<String> list1 = FXCollections.observableArrayList("Nhân viên lễ tân", "Người quản lý");
	        cbbChucVu.setItems(list1);
	        cbbChucVu.setValue("");
	        
	        clSTT.setCellFactory(col -> {
	            return new TableCell<NhanVien, String>() {
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
	        
	        // Set style cho cột STT
	        clSTT.setStyle("-fx-alignment: CENTER;");
	        clSTT.setPrefWidth(50);
	        clSTT.setResizable(false);
	        
	        // Đảm bảo STT cập nhật khi data thay đổi
	        tableNhanVien.getItems().addListener((ListChangeListener<? super NhanVien>) c -> {
	            tableNhanVien.refresh();
	        });

	        // Set up các cột cho TableView
	        clIDNV.setCellValueFactory(new PropertyValueFactory<>("idNhanVien"));
	        clTenNV.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
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

	        clCCCD.setCellValueFactory(new PropertyValueFactory<>("cccd"));
	        
	        // Format giới tính
	        clGioiTinh.setCellValueFactory(cellData -> {
	            boolean gioiTinh = cellData.getValue().isGioiTinh();
	            return new ReadOnlyStringWrapper(gioiTinh ? "Nam" : "Nữ");
	        });

	        // Format chức vụ
	        clChucVu.setCellValueFactory(cellData -> {
	            ChucVu chucVu = cellData.getValue().getChucVu();
	            String chucVuString = (chucVu != null) ? chucVu.getchucVu() : "";
	            return new ReadOnlyStringWrapper(chucVuString);
	        });

	        // Load dữ liệu
	        loadTableData();
	        addUserLogin();
	    }
	    
	    private void loadTableData() {
	        try {
	            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
	            List<NhanVien> danhSachNV = nhanVienDAO.getAllNhanVien();
	            
	            // Chuyển danh sách thành ObservableList
	            ObservableList<NhanVien> data = FXCollections.observableArrayList(danhSachNV);
	            
	            // Xóa dữ liệu cũ và set dữ liệu mới
	            tableNhanVien.getItems().clear();
	            tableNhanVien.setItems(data);
	            
	            // Refresh table
	            tableNhanVien.refresh();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Lỗi");
	            alert.setHeaderText(null);
	            alert.setContentText("Không thể tải dữ liệu nhân viên: " + e.getMessage());
	            alert.showAndWait();
	        }
	    }
	    
	    @FXML
	    void chonNV(MouseEvent event) {
	        NhanVien selectedNhanVien = tableNhanVien.getSelectionModel().getSelectedItem();
	        if (selectedNhanVien != null) {
	            // Cập nhật thông tin vào các trường
	            lb_MaNV.setText(selectedNhanVien.getIdNhanVien());
	            txtTenNV.setText(selectedNhanVien.getTenNhanVien());
	            txtSDT.setText(selectedNhanVien.getSoDienThoai());
	            txtCCCD.setText(selectedNhanVien.getCccd());
	            txtNgaySinh.setValue(selectedNhanVien.getNgaySinh());
	            cbbGioiTinh.setValue(selectedNhanVien.isGioiTinh() ? "Nam" : "Nữ");
	            ChucVu chucVu = selectedNhanVien.getChucVu();
	            if (chucVu != null) {
	                cbbChucVu.setValue(chucVu.getchucVu());
	            } else {
	                cbbChucVu.setValue(null);
	            }
	        }
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
	    	App.setRoot("GD_TKNhanVien");
	    }
	    @FXML
	    void moGiaoDienTimKiemNV(MouseEvent event) throws IOException {
	    	App.setRoot("GD_TKNhanVien");
	    }
	    @FXML
	    void moGiaoDienUuDai(MouseEvent event) throws IOException {
	    	App.setRoot("GD_QLUuDai");
	    }
	    
	    @FXML
	    void suaTTNV(MouseEvent event) {
	        // Kiểm tra xem đã chọn nhân viên chưa
	        if (lb_MaNV.getText().trim().isEmpty()) {
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText(null);
	            alert.setContentText("Vui lòng chọn nhân viên cần sửa!");
	            alert.showAndWait();
	            return;
	        }

	        // Kiểm tra các trường thông tin bắt buộc
	        if (txtTenNV.getText().trim().isEmpty() || 
	            txtSDT.getText().trim().isEmpty() || 
	            txtCCCD.getText().trim().isEmpty() || 
	            txtNgaySinh.getValue() == null || 
	            cbbGioiTinh.getValue() == null || 
	            cbbGioiTinh.getValue().isEmpty() ||
	            cbbChucVu.getValue() == null || 
	            cbbChucVu.getValue().isEmpty()) {
	            
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText(null);
	            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
	            alert.showAndWait();
	            return;
	        }

	        // Lấy thông tin từ các trường
	        String maNV = lb_MaNV.getText();
	        String tenNV = txtTenNV.getText();
	        String sdt = txtSDT.getText();
	        String cccd = txtCCCD.getText();
	        LocalDate ngaySinh = txtNgaySinh.getValue();
	        boolean gioiTinh = cbbGioiTinh.getValue().equals("Nam");

	        // Xử lý chức vụ
	        String chucVuString = cbbChucVu.getValue();
	        ChucVu chucVu = null;
	        
	        if (chucVuString.equals("Nhân viên lễ tân")) {
	            chucVu = ChucVu.NHANVIENLETAN;
	        } else if (chucVuString.equals("Người quản lý")) {
	            chucVu = ChucVu.NGUOIQUANLY;
	        }

	        // Tạo đối tượng NhanVien với thông tin đã sửa
	        NhanVien updatedNhanVien = new NhanVien(maNV, tenNV, sdt, ngaySinh, gioiTinh, cccd, chucVu);

	        try {
	            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
	            boolean updated = nhanVienDAO.capNhatNhanVien(updatedNhanVien);

	            if (updated) {
	                Alert successAlert = new Alert(AlertType.INFORMATION);
	                successAlert.setTitle("Thành công");
	                successAlert.setHeaderText(null);
	                successAlert.setContentText("Cập nhật thông tin nhân viên thành công!");
	                successAlert.showAndWait();

	                // Cập nhật lại TableView và xóa trắng form
	                loadTableData();
	                clearFields();
	            } else {
	                Alert errorAlert = new Alert(AlertType.ERROR);
	                errorAlert.setTitle("Lỗi");
	                errorAlert.setHeaderText(null);
	                errorAlert.setContentText("Không thể cập nhật thông tin nhân viên!");
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
	    
	    private String generateMaNV() {
	        // Lấy ngày hiện tại
	        LocalDate today = LocalDate.now();
	        String day = String.format("%02d", today.getDayOfMonth());
	        String month = String.format("%02d", today.getMonthValue());
	        String year = String.valueOf(today.getYear()).substring(2); // Lấy 2 chữ số cuối của năm

	        NhanVien_DAO nv = new NhanVien_DAO();
	        // Đếm số nhân viên đã thêm trong ngày hiện tại
	        int countToday = nv.getCountOfNhanVienInDay(today);
	        
	        String maNV;
	        boolean exists;
	        int counter = countToday + 1;
	        
	        // Vòng lặp để tìm mã nhân viên chưa tồn tại
	        do {
	            maNV = String.format("NV%s%s%s%02d", year, month, day, counter);
	            exists = nv.isMaNVExists(maNV);
	            if (exists) {
	                counter++;
	            }
	        } while (exists);
	        
	        return maNV;
	    }

	    

	    @FXML
	    void themNV(MouseEvent event) throws Exception {
	    	
	    	if(!kiemTraDuLieu()) {
	        	return ;
	        }
	        // Kiểm tra các trường bắt buộc
	        if (txtTenNV.getText().trim().isEmpty() || 
	            txtSDT.getText().trim().isEmpty() || 
	            txtCCCD.getText().trim().isEmpty() || 
	            txtNgaySinh.getValue() == null || 
	            cbbGioiTinh.getValue() == null || 
	            cbbGioiTinh.getValue().isEmpty() ||
	            cbbChucVu.getValue() == null || 
	            cbbChucVu.getValue().isEmpty()) {
	            
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText(null);
	            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
	            alert.showAndWait();
	            return;
	        }
	        
	        

	        // Lấy thông tin từ các trường
	        String tenNV = txtTenNV.getText();
	        String sdt = txtSDT.getText();
	        String cccd = txtCCCD.getText();
	        LocalDate ngaySinh = txtNgaySinh.getValue();
	        boolean gioiTinh = cbbGioiTinh.getValue().equals("Nam");

	        // Lấy giá trị chức vụ từ ComboBox và chuyển đổi
	        String chucVuString = cbbChucVu.getValue();
	        ChucVu chucVu = null;
	        if (chucVuString.equals("Nhân viên lễ tân")) {
	            chucVu = ChucVu.NHANVIENLETAN;
	        } else if (chucVuString.equals("Người quản lý")) {
	            chucVu = ChucVu.NGUOIQUANLY;
	        }

	        // Tạo mã nhân viên
	        String maNV = generateMaNV();

	        // Tạo đối tượng NhanVien
	        NhanVien newNhanVien = new NhanVien(maNV, tenNV, sdt, ngaySinh, gioiTinh, cccd, chucVu);

	        // Thêm nhân viên vào cơ sở dữ liệu
	        try {
	            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
	            boolean added = nhanVienDAO.themNhanVien(newNhanVien);
	            
	            if (added) {
	                // Thông báo thành công
	                Alert successAlert = new Alert(AlertType.INFORMATION);
	                successAlert.setTitle("Thành công");
	                successAlert.setHeaderText(null);
	                successAlert.setContentText("Thêm nhân viên mới thành công!\nMã nhân viên: " + maNV);
	                successAlert.showAndWait();

	                // Cập nhật TableView
	                loadTableData();
	                
	                // Xóa trắng các trường nhập liệu
	                clearFields();
	            } else {
	                Alert errorAlert = new Alert(AlertType.ERROR);
	                errorAlert.setTitle("Lỗi");
	                errorAlert.setHeaderText(null);
	                errorAlert.setContentText("Không thể thêm nhân viên!");
	                errorAlert.showAndWait();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            Alert errorAlert = new Alert(AlertType.ERROR);
	            errorAlert.setTitle("Lỗi");
	            errorAlert.setHeaderText(null);
	            errorAlert.setContentText("Đã xảy ra lỗi trong quá trình thêm nhân viên!\n" + e.getMessage());
	            errorAlert.showAndWait();
	        }
	    }

	    @FXML
	    void xoaNV(MouseEvent event) {
	        NhanVien selectedNhanVien = tableNhanVien.getSelectionModel().getSelectedItem();
	        if (selectedNhanVien != null) {
	            // Hiển thị hộp thoại xác nhận
	            Alert alert = new Alert(AlertType.CONFIRMATION);
	            alert.setTitle("Xác nhận xóa");
	            alert.setHeaderText(null);
	            alert.setContentText("Bạn có chắc chắn muốn xóa nhân viên này?");

	            Optional<ButtonType> result = alert.showAndWait();
	            if (result.get() == ButtonType.OK) {
	                // Người dùng đã xác nhận xóa
	                try {
	                    NhanVien_DAO nvDao = new NhanVien_DAO();
	                    boolean deleted = nvDao.xoaTheoMaNhanVien(selectedNhanVien.getIdNhanVien());
	                    
	                    if (deleted) {
	                        // Xóa thành công
	                        Alert successAlert = new Alert(AlertType.INFORMATION);
	                        successAlert.setTitle("Thông báo");
	                        successAlert.setHeaderText(null);
	                        successAlert.setContentText("Đã xóa nhân viên thành công!");
	                        successAlert.showAndWait();

//	                        // Cập nhật lại TableView
//	                        dsNhanVien.remove(selectedNhanVien);
//	                        tableNhanVien.refresh();
	                        loadTableData();
	                        
	                        // Xóa thông tin trên các trường nhập liệu
	                        clearFields();
	                        
	                    } else {
	                        Alert errorAlert = new Alert(AlertType.ERROR);
	                        errorAlert.setTitle("Lỗi");
	                        errorAlert.setHeaderText(null);
	                        errorAlert.setContentText("Không thể xóa nhân viên!");
	                        errorAlert.showAndWait();
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    Alert errorAlert = new Alert(AlertType.ERROR);
	                    errorAlert.setTitle("Lỗi");
	                    errorAlert.setHeaderText(null);
	                    errorAlert.setContentText("Đã xảy ra lỗi trong quá trình xóa nhân viên!");
	                    errorAlert.showAndWait();
	                }
	            }
	        } else {
	            // Không có nhân viên nào được chọn
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText(null);
	            alert.setContentText("Vui lòng chọn nhân viên cần xóa!");
	            alert.showAndWait();
	        }
	    }

	    // Phương thức để xóa thông tin trên các trường nhập liệu
	    private void clearFields() {
	        lb_MaNV.setText("");
	        txtTenNV.setText("");
	        txtSDT.setText("");
	        txtCCCD.setText("");
	        txtNgaySinh.setValue(null);
	        cbbGioiTinh.setValue(null);
	        cbbChucVu.setValue(null);
	    }

	    @FXML
	    void xoaTrang(MouseEvent event) {
	        lb_MaNV.setText("");
	        txtTenNV.setText("");
	        txtSDT.setText("");
	        txtCCCD.setText("");
	        txtNgaySinh.setValue(null);
	        cbbGioiTinh.setValue(null);
	        cbbChucVu.setValue(null);
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
	    	if (txtTenNV.getText().equals("")) {
	            showAlertLoi("Lỗi nhập dữ liệu", "Họ tên nhân viên không được rỗng");
	            return false;
	        }
	    	if (!isNameFormatValid(txtTenNV.getText())) {
	            showAlertLoi("Lỗi nhập dữ liệu", "Họ tên nhân viên phải in hoa ký tự đầu");
	            return false;
	        }
	    	if (!txtSDT.getText().matches("0[23789]\\d{8}")) {
	            showAlertLoi("Lỗi nhập dữ liệu", "Số điện thoại nhân viên là dãy gồm 10 ký số. 2 ký số đầu là {02, 03, 05, 07, 08, 09}");
	            return false;
	        }
	    	if (txtNgaySinh.getValue() == null) {
	            showAlert("Lỗi nhập dữ liệu", "Ngày sinh không được rỗng");
	            return false;
	        }

	        if ((LocalDate.now().getYear() - txtNgaySinh.getValue().getYear()) < 18) {
	            showAlertLoi("Lỗi nhập dữ liệu", "Khách hàng phải từ 18 trở lên");
	            return false;
	        }
	     
	        
	        if (cbbGioiTinh.getValue().equals("")) {
	            showAlertLoi("Lỗi nhập dữ liệu", "Giới tính nhân viên không được rỗng");
	            return false;
	        }
	    	if (!txtCCCD.getText().matches("\\d{12}")) {
	            showAlertLoi("Lỗi nhập dữ liệu", "CCCD là một dãy gồm 12 số");
	            return false;
	        }
	        if (txtCCCD.getText().equals("")) {
	            showAlertLoi("Lỗi nhập dữ liệu", "CCCD nhân viên không được rỗng");
	            return false;
	        }
	        if (cbbChucVu.getValue().equals("")) {
	            showAlertLoi("Lỗi nhập dữ liệu", "Chức vụ nhân viên không được rỗng");
	            return false;
	        }
			return true;
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
