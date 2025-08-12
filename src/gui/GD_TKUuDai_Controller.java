package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.KhuyenMai_DAO;
import dao.TaiKhoan_DAO;
import entity.KhuyenMai;
import entity.TaiKhoan;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.stage.Stage;
import main.App;

public class GD_TKUuDai_Controller implements Initializable{

    @FXML
    private ImageView avt;
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
    @FXML
    private Button btnQLUD;

    @FXML
    private Button btnTraCuu;

    @FXML
    private ComboBox<String> cbbGiaoDien;

    @FXML
    private TableColumn<KhuyenMai, String> clCK;

    @FXML
    private TableColumn<KhuyenMai, String> clSTT;

    @FXML
    private TableColumn<KhuyenMai, String> clmaKM;
    
    @FXML
    private TableColumn<KhuyenMai, String> cltenKM;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private ImageView icon_TimKiem1;

    @FXML
    private Label lb_ChietKhau;

    @FXML
    private Label lb_MaKM;

    @FXML
    private Label lb_TenKM;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private TableView<KhuyenMai> tableKhuyenMai;

    @FXML
    private TextField txt_tkKM;

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
    void denGDQLUD(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLUuDai");
    }

    @FXML
    void timKiem(MouseEvent event) {
    	
    	String maKhuyenMai = txt_tkKM.getText();
    	App.ma = maKhuyenMai;
    	KhuyenMai km = new KhuyenMai_DAO().layKhuyenMaiTheoMa(maKhuyenMai);
		lb_MaKM.setText(km.getIdKhuyenMai());
		lb_TenKM.setText(km.getTenKhuyenMai());
		lb_ChietKhau.setText(String.valueOf(km.getChietKhau()));
		highlightMatchingRow(maKhuyenMai);
    }
    private void highlightMatchingRow(String maTaiKhoan) {
        for (int i = 0; i < tableKhuyenMai.getItems().size(); i++) {
            KhuyenMai khuyenmai = tableKhuyenMai.getItems().get(i);
            if (khuyenmai.getIdKhuyenMai().equals(khuyenmai)) {
                // Select the row (important)
            	tableKhuyenMai.getSelectionModel().select(i);
                // Set focus to the row
            	tableKhuyenMai.getFocusModel().focus(i);
                // Highlight the row (optional, but recommended)
            	tableKhuyenMai.getFocusModel().focus(i);
            	tableKhuyenMai.getSelectionModel().focus(i);
                break;
            }
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lb_MaKM.setText("");
		lb_TenKM.setText("");
		lb_ChietKhau.setText("");
		ObservableList<String> list = FXCollections.observableArrayList("Ưu đãi", "Phòng", "Nhân viên", "Khách hàng", "Hoá đơn",
				"Dịch vụ", "Tài khoản");
		cbbGiaoDien.setItems(list);
		cbbGiaoDien.setValue("Ưu đãi");
		 // Xử lý sự kiện chọn ComboBox để chuyển giao diện
        cbbGiaoDien.setOnAction(event -> {
            String selectedValue = cbbGiaoDien.getValue();
            switch (selectedValue) {
            case "Ưu đãi":
				try {
					App.setRoot("GD_TKUuDai");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
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
                default:
                    System.out.println("Không tìm thấy giao diện phù hợp!");
                    break;
            }
        });
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
        
        
        clmaKM.setCellValueFactory(new PropertyValueFactory<>("idKhuyenMai"));
        cltenKM.setCellValueFactory(new PropertyValueFactory<>("tenKhuyenMai"));
        clCK.setCellValueFactory(new PropertyValueFactory<>("chietKhau"));
        loadTableData();
        tableKhuyenMai.setOnMouseClicked(event -> {
        	KhuyenMai selectedKhuyenMai = tableKhuyenMai.getSelectionModel().getSelectedItem();
        	if (selectedKhuyenMai != null) {
        		lb_MaKM.setText(selectedKhuyenMai.getIdKhuyenMai());
        		lb_TenKM.setText(selectedKhuyenMai.getTenKhuyenMai());
        		lb_ChietKhau.setText(String.valueOf(selectedKhuyenMai.getChietKhau()));

        	}
        });
        
        addUserLogin();
	}
    private void loadTableData() {
        try {
            KhuyenMai_DAO kmdao = new KhuyenMai_DAO();
            ArrayList<KhuyenMai> dskm = kmdao.getAllKhuyenMai();

            ObservableList<KhuyenMai> observableList = FXCollections.observableArrayList(dskm);
            tableKhuyenMai.setItems(observableList);
            
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
    void dongUngDung(MouseEvent event) throws IOException {
		App.user = "";
		Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		stage.close();
		App.openModal("GD_DangNhap");
    }
}
