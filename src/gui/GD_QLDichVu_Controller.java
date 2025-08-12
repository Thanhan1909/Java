
package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.DichVu_DAO;
import entity.DichVu;
import entity.TaiKhoan;
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

public class GD_QLDichVu_Controller implements Initializable{
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
    private TableColumn <DichVu, String> clDonGia;

    @FXML
    private TableColumn<DichVu, String> clMaDichVu;

    @FXML
    private TableColumn<DichVu, String> clSL;

    @FXML
    private TableColumn<DichVu, String> clSTT;

    @FXML
    private TableColumn<DichVu, String> clTenDV;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private Label lbIDDV;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label maNV;

    @FXML
    private TableView<DichVu> tableDichVu;

    @FXML
    private Label tenNV;

    @FXML
    private TextField txtDonGia;

    @FXML
    private TextField txtSoLuong;

    @FXML
    private TextField txtTenDichVu;
    
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
    void moGiaoDienTimKiemDV(MouseEvent event) throws IOException {
    	App.setRoot("GD_TKDichVu");
    }

    @FXML
    void moGiaoDienUuDai(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLUuDai");
    }

    @FXML
    void suaKM(MouseEvent event) {
    	if (lbIDDV.getText().trim().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn dịch vụ cần sửa!");
            alert.showAndWait();
            return;
    	}
    	if (txtTenDichVu.getText().trim().isEmpty() ||
    			txtDonGia.getText().trim().isEmpty() ||
    			txtSoLuong.getText().trim().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
            alert.showAndWait();
            return;
    	}
    	String maDV = lbIDDV.getText();
    	String tenDV = txtTenDichVu.getText();
    	double dongia = Double.valueOf(txtDonGia.getText());
    	int soLuong = Integer.valueOf(txtSoLuong.getText());
    	
    	DichVu dv = new DichVu(maDV, tenDV, soLuong, dongia);
        boolean updated = new DichVu_DAO().suaDichVu(dv);
        
        if (updated) {
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Thành công");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Cập nhật thông tin dịch vụ thành công!");
            successAlert.showAndWait();

            // Cập nhật lại TableView và xóa trắng form
            loadTableData();
            clearFields();
            } else {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Lỗi");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Không thể cập nhật thông tin dịch vụ!");
            errorAlert.showAndWait();
        }
    	
    }

    private String generateMaDV() {
        // Format cơ bản: SPXXX
        String baseFormat = "SP";
        
        try {
            DichVu_DAO dvDAO = new DichVu_DAO();
            List<DichVu> listDV = dvDAO.getAllDichVu();
            
            int maxNumber = 0;
            
            for (DichVu dv : listDV) {
                String maDV = dv.getIdDichVu();
                // Kiểm tra mã có đúng format không
                if (maDV.startsWith(baseFormat)) {
                    try {
                        // Lấy 2 số cuối của mã
                        int number = Integer.parseInt(maDV.substring(2));
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
            String numberStr = String.format("%03d", maxNumber);
            
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
            String tenDV = txtTenDichVu.getText().trim();
            String SoLuong = txtSoLuong.getText().trim();
            String DonGia = txtDonGia.getText().trim();
            // Kiểm tra dữ liệu
            if (tenDV.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập tên dịch vụ!");
                alert.showAndWait();
                txtTenDichVu.requestFocus();
                return;
            }
            
            if (SoLuong.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập số lượng!");
                alert.showAndWait();
                txtSoLuong.requestFocus();
                return;
            }
            int soluong;
            try {
                soluong = Integer.parseInt(SoLuong);
                if (soluong <= 0) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Số lượng phải lớn hơn 0!");
                    alert.showAndWait();
                    txtDonGia.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Số lượng phải là số!");
                alert.showAndWait();
                txtDonGia.requestFocus();
                return;
            }
            
            if (DonGia.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập đơn giá!");
                alert.showAndWait();
                txtDonGia.requestFocus();
                return;
            }
            
            double dongia;
            try {
                dongia = Double.parseDouble(DonGia);
                if (dongia <= 0) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Đơn giá phải lớn hơn 0!");
                    alert.showAndWait();
                    txtDonGia.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Đơn giá phải là số!");
                alert.showAndWait();
                txtDonGia.requestFocus();
                return;
            }
            
            // Tự động sinh mã khuyến mãi
            String maDV = generateMaDV();
            if (maDV == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Lỗi khi tạo mã dịch vụ!");
                alert.showAndWait();
                return;
            }
            
            // Tạo đối tượng khuyến mãi mới
            DichVu dv = new DichVu(maDV, tenDV, soluong, dongia);
            
            // Thêm vào database
            DichVu_DAO dvDAO = new DichVu_DAO();
            boolean result = dvDAO.themDichVu(dv);
            
            if (result) {
                // Hiển thị thông báo thành công
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Thêm dịch vụ thành công!");
                alert.showAndWait();
                
                // Clear các trường nhập liệu
                clearFields();
                
                // Reload lại dữ liệu trong bảng
                loadTableData();
                
                // Reset focus
                txtTenDichVu.requestFocus();
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
    private void loadTableData() {
        try {
            // Tạo DAO object
           DichVu_DAO dvDAO = new DichVu_DAO();
            
            // Xóa dữ liệu cũ trong table
            tableDichVu.getItems().clear();
            
            // Lấy danh sách khuyến mãi từ database
            ObservableList<DichVu> listDV = FXCollections.observableArrayList(dvDAO.getAllDichVu());
            
            // Thiết lập cell value factory cho các cột
            clMaDichVu.setCellValueFactory(new PropertyValueFactory<>("idDichVu"));
            clTenDV.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
            clSL.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
            clDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
            
            // Cập nhật dữ liệu vào table
            tableDichVu.setItems(listDV);
            
            // Refresh table view
            tableDichVu.refresh();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void clearFields() {
    	lbIDDV.setText("");
    	txtTenDichVu.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
    }
    @FXML
    void xoaKM(MouseEvent event) {
    	DichVu selectedDichVu = tableDichVu.getSelectionModel().getSelectedItem();
    	if (selectedDichVu == null) {
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
        confirmAlert.setContentText("Bạn có chắc chắn muốn xóa dịch vụ " + selectedDichVu.getTenSanPham() + "?");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean deleted = new DichVu_DAO().xoaDichVu(selectedDichVu.getIdDichVu());
                if (deleted) {
                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("Thành công");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Đã xóa dịch vụ thành công!");
                    successAlert.showAndWait();
                    
                    // Cập nhật lại bảng
                    loadTableData();
                    clearFields();
                } else {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Lỗi");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Không thể xóa dịch vụ. Vui lòng thử lại!");
                    errorAlert.showAndWait();
                }
            } catch (Exception e) {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Lỗi");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Đã xảy ra lỗi trong quá trình xóa phòng!");
                errorAlert.showAndWait();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void xoaTrang(MouseEvent event) {
		lbIDDV.setText("");
		txtTenDichVu.setText("");
		txtTenDichVu.requestFocus();
		txtSoLuong.setText("");
		txtDonGia.setText("");
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setTable();
		addUserLogin();
		tableDichVu.setOnMouseClicked(event -> {
			DichVu selectedDichVu = tableDichVu.getSelectionModel().getSelectedItem();
			if (selectedDichVu != null) {
				lbIDDV.setText(selectedDichVu.getIdDichVu());
				txtTenDichVu.setText(selectedDichVu.getTenSanPham());
				txtSoLuong.setText(String.valueOf(selectedDichVu.getSoLuong()));
				txtDonGia.setText(String.valueOf(selectedDichVu.getDonGia()));
			}
		});
		
	}
	
	
	private void setTable() {
		clSTT.setCellFactory(col -> {
			return new TableCell<DichVu, String>() {
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
        clMaDichVu.setCellValueFactory(new PropertyValueFactory<>("idDichVu"));
        clTenDV.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        clSL.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        clDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        
        ObservableList<DichVu> observableList = FXCollections.observableArrayList(new DichVu_DAO().getAllDichVu());
        tableDichVu.setItems(observableList);
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

