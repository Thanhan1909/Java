package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.HoaDon_DAO;
import dao.TaiKhoan_DAO;
import entity.HoaDon;
import entity.TaiKhoan;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.App;

public class GD_TKTaiKhoan_Controller implements Initializable{
	@FXML
	private AnchorPane mainContainer; // Hoặc BorderPane nếu container chính là BorderPane
	
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
    @FXML
    private ImageView avt;

    @FXML
    private Button btnQLTK;

    @FXML
    private Button btnTraCuu;

    @FXML
    private ComboBox<String> cbbGiaoDien;

    @FXML
    private TableColumn<TaiKhoan, String> clSTT;

    @FXML
    private TableColumn<TaiKhoan, String> clTrangThai;

    @FXML
    private TableColumn<TaiKhoan, String> clmaNV;

    @FXML
    private TableColumn<TaiKhoan, String> clmaTK;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private ImageView icon_TimKiem1;

    @FXML
    private Label lb_MaNV;

    @FXML
    private Label lb_MaTK;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label lb_TrangThai;

    @FXML
    private TableView<TaiKhoan> tableTaiKhoan;

    @FXML
    private TextField txt_tkTK;

    @FXML
    void moGiaoDienQuanLy(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLPhong");
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
    void moGiaoDienUuDai(MouseEvent event) throws IOException {
    	App.setRoot("GD_TKUuDai");
    }

    
    @FXML
    void denGDQLTK(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLTaiKhoan");
    }

    @FXML
    void timKiem(MouseEvent event) {
    	
    	String maTaiKhoan = txt_tkTK.getText();
    	App.ma = maTaiKhoan;
    	TaiKhoan tk = new TaiKhoan_DAO().layTaiKhoanTheoMaNV(maTaiKhoan);
		lb_MaTK.setText(tk.getIdTaiKhoan());
		lb_MaNV.setText(tk.getNhanVien().getIdNhanVien());
		lb_TrangThai.setText(tk.getTrangThai());
		highlightMatchingRow(maTaiKhoan);
    }
    private void highlightMatchingRow(String maTaiKhoan) {
        for (int i = 0; i < tableTaiKhoan.getItems().size(); i++) {
            TaiKhoan taikhoan = tableTaiKhoan.getItems().get(i);
            if (taikhoan.getIdTaiKhoan().equals(taikhoan)) {
                // Select the row (important)
            	tableTaiKhoan.getSelectionModel().select(i);
                // Set focus to the row
            	tableTaiKhoan.getFocusModel().focus(i);
                // Highlight the row (optional, but recommended)
            	tableTaiKhoan.getFocusModel().focus(i);
            	tableTaiKhoan.getSelectionModel().focus(i);
                break;
            }
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lb_MaNV.setText("");
		lb_MaTK.setText("");
		lb_TrangThai.setText("");
		ObservableList<String> list = FXCollections.observableArrayList("Tài khoản", "Hoá đơn", "Phòng", "Nhân viên", "Khách hàng",
				"Dịch vụ","Ưu đãi");
		cbbGiaoDien.setItems(list);
		cbbGiaoDien.setValue("Tài khoản");
		
		 // Xử lý sự kiện chọn ComboBox để chuyển giao diện
        cbbGiaoDien.setOnAction(event -> {
            String selectedValue = cbbGiaoDien.getValue();
            switch (selectedValue) {
                case "Tài khoản":
				try {
					App.setRoot("GD_TKTaiKhoan");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "Hoá đơn":
				try {
					App.setRoot("GD_TKHoaDon");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "Phòng":
				try {
					App.setRoot("GD_TKPhong");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "Nhân viên":
				try {
					App.setRoot("GD_TKNhanVien");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "Khách hàng":
				try {
					App.setRoot("GD_TKKhachHang");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "Dịch vụ":
				try {
					App.setRoot("GD_TKDichVu");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "Ưu đãi":
				try {
					App.setRoot("GD_TKUuDai");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                default:
                    System.out.println("Không tìm thấy giao diện phù hợp!");
                    break;
            }
        });
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
        
        
        clmaTK.setCellValueFactory(new PropertyValueFactory<>("idTaiKhoan"));
        clmaNV.setCellValueFactory(cellData -> {
        	String manv = cellData.getValue().getNhanVien().getIdNhanVien();
        	return new ReadOnlyStringWrapper(manv);
        });
        clTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        loadTableData();
        tableTaiKhoan.setOnMouseClicked(event -> {
        	TaiKhoan selectedTaiKhoan = tableTaiKhoan.getSelectionModel().getSelectedItem();
        	if (selectedTaiKhoan != null) {
        		lb_MaTK.setText(selectedTaiKhoan.getIdTaiKhoan());
        		lb_MaNV.setText(selectedTaiKhoan.getNhanVien().getIdNhanVien());
        		lb_TrangThai.setText(selectedTaiKhoan.getTrangThai());

        	}
        });
        
        addUserLogin();
	}
    


    private void loadTableData() {
        try {
            TaiKhoan_DAO tkdao = new TaiKhoan_DAO();
            ArrayList<TaiKhoan> dstk = tkdao.getAllTaiKhoan();

            ObservableList<TaiKhoan> observableList = FXCollections.observableArrayList(dstk);
            tableTaiKhoan.setItems(observableList);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	private void addUserLogin() {
		TaiKhoan tk = App.tk;
		maNV.setText(String.valueOf(tk.getNhanVien().getIdNhanVien()));
		tenNV.setText(String.valueOf(tk.getNhanVien().getTenNhanVien()));
	}
    
    @FXML
    void suaTK(MouseEvent event) {

    }

    @FXML
    void themTK(MouseEvent event) {

    }

    @FXML
    void xoaTK(MouseEvent event) {

    }

    @FXML
    void xoaTrang(MouseEvent event) {

    }
    @FXML
    void dongUngDung(MouseEvent event) throws IOException {
		App.user = "";
		Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		stage.close();
		App.openModal("GD_DangNhap");
    }
}
