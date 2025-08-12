package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.Enum_ChucVu;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.ChucVu;
import entity.NhanVien;
import entity.TaiKhoan;
import javafx.beans.property.ReadOnlyStringWrapper;
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

public class GD_QLTaiKhoan_Controller implements Initializable{

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
    private TableColumn<TaiKhoan, String> clSTT;

    @FXML
    private TableColumn<TaiKhoan, String> clIDTK;

    @FXML
    private TableColumn<TaiKhoan, String> clMKTK;

    @FXML
    private TableColumn<TaiKhoan, String> clIDNV;

    @FXML
    private TableColumn<TaiKhoan, String> clTenNV;

    @FXML
    private TableColumn<TaiKhoan, String> clChucVu;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private TextField lb_MaNV;

    @FXML
    private Label lb_MaTK;

    @FXML
    private Label lb_TimKiem;
    
    @FXML
    private Label lb_ChucVu;
    
    @FXML
    private Label lb_TenNV;

    @FXML
    private TableView<TaiKhoan> tableTaiKhoan;

    @FXML
    private TextField txtMatKhau;

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
    void moGiaoDienTimKiemTK(MouseEvent event) throws IOException {
    	App.setRoot("GD_TKTaiKhoan");
    }
    @FXML
    void moGiaoDienUuDai(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLUuDai");
    }
    
    private String taoMaTaiKhoan(NhanVien nv) {
        try {
            // Lấy mã nhân viên
            String maNV = nv.getIdNhanVien();
            
            // Lấy chức vụ của nhân viên
            ChucVu chucVu = nv.getChucVu();
            
            // Tiền tố mã tài khoản dựa vào chức vụ
            String prefix = "";
            switch(chucVu) {
                case NHANVIENLETAN:
                    prefix = "NV";
                    break;
                case NGUOIQUANLY:
                    prefix = "QL";
                    break;
                default:
                    throw new IllegalArgumentException("Chức vụ không hợp lệ");
            }
            
            // Lấy 4 số cuối của mã nhân viên
            String suffix = "";
            if(maNV.length() >= 4) {
                suffix = maNV.substring(maNV.length() - 4);
            } else {
                // Nếu mã nhân viên ít hơn 4 ký tự thì thêm số 0 vào trước
                suffix = String.format("%04d", Integer.parseInt(maNV));
            }
            
            // Ghép tiền tố và hậu tố để tạo mã tài khoản
            String maTK = prefix + suffix;
            
            return maTK;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    void themTK(MouseEvent event) {
        try {
        	if(!kiemTraDuLieu()) {
	    		return;
	    	}
            // Kiểm tra dữ liệu nhập vào
            if(txtMatKhau.getText().trim().isEmpty() || lb_MaNV.getText().trim().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập đầy đủ thông tin!");
                alert.showAndWait();
                return;
            }
            

            // Lấy thông tin nhân viên
            NhanVien_DAO nvDAO = new NhanVien_DAO();
            NhanVien nv = nvDAO.getNhanVienTheoMa(lb_MaNV.getText().trim());
            
            if(nv == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy nhân viên!");
                alert.showAndWait();
                return;
            }

            // Kiểm tra nhân viên đã có tài khoản chưa
            TaiKhoan_DAO tkDAO = new TaiKhoan_DAO();
            if(tkDAO.layTaiKhoanTheoMaNV(nv.getIdNhanVien()) != null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Nhân viên này đã có tài khoản!");
                alert.showAndWait();
                return;
            }

            // Tạo mã tài khoản tự động
            String maTK = taoMaTaiKhoan(nv);
            if(maTK == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Lỗi khi tạo mã tài khoản!");
                alert.showAndWait();
                return;
            }

            // Tạo tài khoản mới
            TaiKhoan tk = new TaiKhoan(
                maTK, 
                txtMatKhau.getText().trim(), 
                "NULL", 
                nv
            );

            // Thêm vào database
            if(tkDAO.themTaiKhoan(tk)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Thêm tài khoản thành công!");
                alert.showAndWait();
                
                // Refresh table
                loadTableData();
                clearFields();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Thêm tài khoản thất bại!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã xảy ra lỗi: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    void xoaTK(MouseEvent event) {
        try {
            // Kiểm tra đã chọn tài khoản chưa
            TaiKhoan selectedTK = tableTaiKhoan.getSelectionModel().getSelectedItem();
            if(selectedTK == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng chọn tài khoản cần xóa!");
                alert.showAndWait();
                return;
            }

            // Hiển thị dialog xác nhận
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc muốn xóa tài khoản này?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if(result.get() == ButtonType.OK) {
                TaiKhoan_DAO tkDAO = new TaiKhoan_DAO();
                if(tkDAO.xoaTheoMaTaiKhoan(selectedTK.getIdTaiKhoan())) {
                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("Thông báo");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Xóa tài khoản thành công!");
                    successAlert.showAndWait();
                    
                    // Refresh table
                    loadTableData();
                    clearFields();
                } else {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Thông báo");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Xóa tài khoản thất bại!");
                    errorAlert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Lỗi");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Đã xảy ra lỗi trong quá trình xóa tài khoản!");
            errorAlert.showAndWait();
        }
    }
    
    @FXML
    void suaTK(MouseEvent event) {
        try {
            // Kiểm tra đã chọn tài khoản chưa
            TaiKhoan selectedTK = tableTaiKhoan.getSelectionModel().getSelectedItem();
            if(selectedTK == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng chọn tài khoản cần sửa!");
                alert.showAndWait();
                return;
            }

            // Kiểm tra mật khẩu mới
            if(txtMatKhau.getText().trim().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập mật khẩu mới!");
                alert.showAndWait();
                return;
            }

            // Tạo đối tượng tài khoản với mật khẩu mới
            TaiKhoan tk = new TaiKhoan(
                selectedTK.getIdTaiKhoan(),
                txtMatKhau.getText().trim(),
                selectedTK.getTrangThai(),
                selectedTK.getNhanVien()
            );

            // Cập nhật vào database
            TaiKhoan_DAO tkDAO = new TaiKhoan_DAO();
            if(tkDAO.capNhatTaiKhoan(tk)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Cập nhật mật khẩu thành công!");
                alert.showAndWait();
                
                // Refresh table
                loadTableData();
                clearFields();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Cập nhật mật khẩu thất bại!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void xoaTrang(MouseEvent event) {
    	lb_MaTK.setText("");
        txtMatKhau.setText("");
        lb_MaNV.setText("");
        lb_TenNV.setText("");
        lb_ChucVu.setText("");
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clSTT.setCellFactory(col -> {
            return new TableCell<TaiKhoan, String>() {
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
        tableTaiKhoan.getItems().addListener((ListChangeListener<? super TaiKhoan>) c -> {
            tableTaiKhoan.refresh();
        });
        
        clIDTK.setCellValueFactory(new PropertyValueFactory<>("idTaiKhoan"));
        clMKTK.setCellValueFactory(new PropertyValueFactory<>("matKhau"));
        
        // Set up các cột liên quan đến NhanVien
        clIDNV.setCellValueFactory(cellData -> 
            new ReadOnlyStringWrapper(cellData.getValue().getNhanVien().getIdNhanVien()));
        
        clTenNV.setCellValueFactory(cellData -> 
            new ReadOnlyStringWrapper(cellData.getValue().getNhanVien().getTenNhanVien()));
        
        clChucVu.setCellValueFactory(cellData -> 
            new ReadOnlyStringWrapper(cellData.getValue().getNhanVien().getChucVu().getchucVu()));
        
        loadTableData();
		addUserLogin();
	}
	
	private void loadTableData() {
	    try {
	        TaiKhoan_DAO tkDAO = new TaiKhoan_DAO();
	        // Xóa dữ liệu cũ trong table
	        tableTaiKhoan.getItems().clear();
	        
	        // Lấy danh sách mới từ database
	        ObservableList<TaiKhoan> list = FXCollections.observableArrayList(tkDAO.getAllTaiKhoan());
	        
	        // Cập nhật vào table
	        tableTaiKhoan.setItems(list);
	        
	        // Refresh table view
	        tableTaiKhoan.refresh();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
    void chonTK(MouseEvent event) {
		TaiKhoan selectedTaiKhoan = tableTaiKhoan.getSelectionModel().getSelectedItem();
	    if (selectedTaiKhoan != null) {
	        // Cập nhật thông tin tài khoản
	        lb_MaTK.setText(selectedTaiKhoan.getIdTaiKhoan());
	        txtMatKhau.setText(selectedTaiKhoan.getMatKhau());
	        
	        // Cập nhật thông tin nhân viên liên quan
	        NhanVien nv = selectedTaiKhoan.getNhanVien();
	        if (nv != null) {
	            lb_MaNV.setText(nv.getIdNhanVien());
	            lb_TenNV.setText(nv.getTenNhanVien());
	            lb_ChucVu.setText(nv.getChucVu().getchucVu());
	        } else {
	            // Xóa thông tin nếu không có nhân viên liên kết
	            lb_MaNV.setText("");
	            lb_TenNV.setText("");
	            lb_ChucVu.setText("");
	        }
	    }
    }
	
	private void clearFields() {
		lb_MaTK.setText("");
        txtMatKhau.setText("");
        lb_MaNV.setText("");
        lb_TenNV.setText("");
        lb_ChucVu.setText("");
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
    	if (!txtMatKhau.getText().matches("^(?=.*[a-zA-Z]).{6,}$")) {
            showAlertLoi("Lỗi nhập dữ liệu", "Không được rỗng, ít nhất 6 ký tự. Trong đó, có 1 ký tự chữ");
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
