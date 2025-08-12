package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import dao.KhuyenMai_DAO;
import dao.TaiKhoan_DAO;
import entity.KhuyenMai;
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

public class GD_QLUuDai_Controller implements Initializable{

	@FXML
    private ImageView avt;
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
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
    private TableColumn<KhuyenMai, String> clCK;

    @FXML
    private TableColumn<KhuyenMai, String> clIDKM;

    @FXML
    private TableColumn<KhuyenMai, String> clSTT;

    @FXML
    private TableColumn<KhuyenMai, String> clTenKM;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private Label lbIDKM;

    @FXML
    private TableView<KhuyenMai> tableKhuyenMai;

    @FXML
    private TextField txtTenKM;

    @FXML
    private TextField txtCK;

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
    	App.setRoot("GD_TKPhong");
    }
    @FXML
    void moGiaoDienTimKiemUD(MouseEvent event) throws IOException {
    	App.setRoot("GD_TKUuDai");
    }

    @FXML
    void moGiaoDienUuDai(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLUuDai");
    }
    
    @FXML
    void chonUD(MouseEvent event) {
        KhuyenMai selectedKM = tableKhuyenMai.getSelectionModel().getSelectedItem();        
        if(selectedKM != null) {
            try {
                lbIDKM.setText(selectedKM.getIdKhuyenMai());
                txtTenKM.setText(selectedKM.getTenKhuyenMai());
                txtCK.setText(String.valueOf(selectedKM.getChietKhau()));            
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String generateMaKM() {
        // Lấy ngày hiện tại
        LocalDateTime now = LocalDateTime.now();
        String year = String.format("%02d", now.getYear() % 100); // Lấy 2 số cuối của năm
        String month = String.format("%02d", now.getMonthValue());
        
        // Format cơ bản: KMyyMM
        String baseFormat = "KM" + year + month;
        
        try {
            KhuyenMai_DAO kmDAO = new KhuyenMai_DAO();
            List<KhuyenMai> listKM = kmDAO.getAllKhuyenMai();
            
            // Tìm số thứ tự lớn nhất hiện tại
            int maxNumber = 0;
            
            for (KhuyenMai km : listKM) {
                String maKM = km.getIdKhuyenMai();
                // Kiểm tra mã có đúng format không
                if (maKM.startsWith(baseFormat)) {
                    try {
                        // Lấy 2 số cuối của mã
                        int number = Integer.parseInt(maKM.substring(maKM.length() - 2));
                        if (number > maxNumber) {
                            maxNumber = number;
                        }
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
            
            // Tăng số thứ tự lên 1
            maxNumber++;
            
            // Format số thứ tự thành 2 chữ số
            String numberStr = String.format("%02d", maxNumber);
            
            // Trả về mã khuyến mãi mới
            return baseFormat + numberStr;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    void themKM(MouseEvent event) {
        try {
        	
            // Lấy dữ liệu từ các trường nhập
            String tenKM = txtTenKM.getText().trim();
            String chietKhauStr = txtCK.getText().trim();
            
            // Kiểm tra dữ liệu
            if (tenKM.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập tên khuyến mãi!");
                alert.showAndWait();
                txtTenKM.requestFocus();
                return;
            }
            
            if (chietKhauStr.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập chiết khấu!");
                alert.showAndWait();
                txtCK.requestFocus();
                return;
            }
            
            double chietKhau;
            try {
                chietKhau = Double.parseDouble(chietKhauStr);
                if (chietKhau <= 0 || chietKhau > 100) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Chiết khấu phải lớn hơn 0 và nhỏ hơn hoặc bằng 100!");
                    alert.showAndWait();
                    txtCK.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Chiết khấu phải là số!");
                alert.showAndWait();
                txtCK.requestFocus();
                return;
            }
            
            // Tự động sinh mã khuyến mãi
            String maKM = generateMaKM();
            if (maKM == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Lỗi khi tạo mã khuyến mãi!");
                alert.showAndWait();
                return;
            }
            
            // Tạo đối tượng khuyến mãi mới
            KhuyenMai km = new KhuyenMai(maKM, tenKM, chietKhau);
            
            // Thêm vào database
            KhuyenMai_DAO kmDAO = new KhuyenMai_DAO();
            boolean result = kmDAO.themKhuyenMai(km);
            
            if (result) {
                // Hiển thị thông báo thành công
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Thêm khuyến mãi thành công!");
                alert.showAndWait();
                
                // Clear các trường nhập liệu
                clearFields();
                
                // Reload lại dữ liệu trong bảng
                loadTableData();
                
                // Reset focus
                txtTenKM.requestFocus();
            } else {
                // Hiển thị thông báo lỗi
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null );
                alert.setContentText("Thêm khuyến mãi thất bại!");
                alert.showAndWait();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void xoaKM(MouseEvent event) {
        // Lấy khuyến mãi được chọn từ TableView
        KhuyenMai selectedKM = tableKhuyenMai.getSelectionModel().getSelectedItem();
        
        if (selectedKM == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn khuyến mãi cần xóa!");
            alert.showAndWait();
            return;
        }

        // Hiển thị hộp thoại xác nhận
        Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
        confirmAlert.setTitle("Xác nhận xóa");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Bạn có chắc chắn muốn xóa khuyến mãi này?");
        
        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            try {
                KhuyenMai_DAO kmDAO = new KhuyenMai_DAO();
                boolean result = kmDAO.xoaKhuyenMai(selectedKM.getIdKhuyenMai());
                
                if (result) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Thành công");
                    alert.setHeaderText(null);
                    alert.setContentText("Xóa khuyến mãi thành công!");
                    alert.showAndWait();
                    
                    // Clear các trường nhập liệu
                    clearFields();
                    
                    // Reload lại dữ liệu trong bảng
                    loadTableData();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Xóa khuyến mãi thất bại!");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Đã xảy ra lỗi trong quá trình xóa!");
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    void suaKM(MouseEvent event) {
        try {
            // Lấy khuyến mãi được chọn
            KhuyenMai selectedKM = tableKhuyenMai.getSelectionModel().getSelectedItem();
            if (selectedKM == null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng chọn khuyến mãi cần sửa!");
                alert.showAndWait();
                return;
            }

            // Kiểm tra và lấy dữ liệu từ các trường nhập
            String tenKM = txtTenKM.getText().trim();
            String chietKhauStr = txtCK.getText().trim();
            
            if (tenKM.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập tên khuyến mãi!");
                alert.showAndWait();
                txtTenKM.requestFocus();
                return;
            }
            
            if (chietKhauStr.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập chiết khấu!");
                alert.showAndWait();
                txtCK.requestFocus();
                return;
            }
            
            double chietKhau;
            try {
                chietKhau = Double.parseDouble(chietKhauStr);
                if (chietKhau <= 0 || chietKhau > 100) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Chiết khấu phải lớn hơn 0 và nhỏ hơn hoặc bằng 100!");
                    alert.showAndWait();
                    txtCK.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Chiết khấu phải là số!");
                alert.showAndWait();
                txtCK.requestFocus();
                return;
            }

            // Hiển thị hộp thoại xác nhận
            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Xác nhận sửa");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Bạn có chắc chắn muốn sửa thông tin khuyến mãi này?");
            
            if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                // Tạo đối tượng khuyến mãi mới
                KhuyenMai km = new KhuyenMai(selectedKM.getIdKhuyenMai(), tenKM, chietKhau);
                
                // Sửa khuyến mãi trong database
                KhuyenMai_DAO kmDAO = new KhuyenMai_DAO();
                boolean result = kmDAO.suaKhuyenMai(km);
                
                if (result) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Thành công");
                    alert.setHeaderText(null);
                    alert.setContentText("Sửa khuyến mãi thành công!");
                    alert.showAndWait();
                    // Clear các trường nhập liệu
                    clearFields();
                    
                    // Reload lại dữ liệu trong bảng
                    loadTableData();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Sửa khuyến mãi thất bại!");
                    alert.showAndWait();
                }
            } else {
                // Nếu người dùng chọn Cancel, không làm gì cả
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã xảy ra lỗi trong quá trình sửa!");
            alert.showAndWait();
        }
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	clSTT.setCellFactory(col -> {
            return new TableCell<KhuyenMai, String>() {
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
        tableKhuyenMai.getItems().addListener((ListChangeListener<? super KhuyenMai>) c -> {
            tableKhuyenMai.refresh();
        });
        
        tableKhuyenMai.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Đổ dữ liệu vào các trường
                lbIDKM.setText(newValue.getIdKhuyenMai());
                txtTenKM.setText(newValue.getTenKhuyenMai());
                txtCK.setText(String.valueOf(newValue.getChietKhau()));
            }
        });
        loadTableData();
        addUserLogin();
	}
    
    private void loadTableData() {
        try {
            // Tạo DAO object
            KhuyenMai_DAO kmDAO = new KhuyenMai_DAO();
            
            // Xóa dữ liệu cũ trong table
            tableKhuyenMai.getItems().clear();
            
            // Lấy danh sách khuyến mãi từ database
            ObservableList<KhuyenMai> listKM = FXCollections.observableArrayList(kmDAO.getAllKhuyenMai());
            
            // Thiết lập cell value factory cho các cột
            clIDKM.setCellValueFactory(new PropertyValueFactory<>("idKhuyenMai"));
            clTenKM.setCellValueFactory(new PropertyValueFactory<>("tenKhuyenMai"));
            clCK.setCellValueFactory(new PropertyValueFactory<>("chietKhau"));
            
            // Cập nhật dữ liệu vào table
            tableKhuyenMai.setItems(listKM);
            
            // Refresh table view
            tableKhuyenMai.refresh();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void xoaTrang(MouseEvent event) {
    	lbIDKM.setText("");
        txtTenKM.setText("");
        txtCK.setText("");
    }
    
    private void clearFields() {
    	lbIDKM.setText("");
        txtTenKM.setText("");
        txtCK.setText("");
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