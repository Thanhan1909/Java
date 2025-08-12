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
import java.util.ResourceBundle;

import dao.KhachHang_DAO;
import dao.Phong_DAO;
import dao.TaiKhoan_DAO;
import entity.ChucVu;
import entity.KhachHang;
import entity.NhanVien;
import entity.Phong;
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

public class GD_TKKhachHang_Controller implements Initializable{

    @FXML
    private ImageView avt;
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
    @FXML
    private Button btnTraCuu;

    @FXML
    private Button btnQLKH;

    @FXML
    private ComboBox<String> cbbGiaoDien;

    @FXML
    private TableColumn<KhachHang, String> clCCCD;

    @FXML
    private TableColumn<KhachHang, String> clNgaySinh;

    @FXML
    private TableColumn<KhachHang, String> clSDT;

    @FXML
    private TableColumn<KhachHang, String> clSTT;

    @FXML
    private TableColumn<KhachHang, String> clTichDiem;

    @FXML
    private TableColumn<KhachHang, String> clmaKH;

    @FXML
    private TableColumn<KhachHang, String> cltenKH;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private ImageView icon_TimKiem1;

    @FXML
    private Label lb_MaKH;

    @FXML
    private Label lb_MaNV51;

    @FXML
    private Label lb_TenKH;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label lb_cccd;

    @FXML
    private Label lb_nsKH;

    @FXML
    private Label lb_sdtKH;

    @FXML
    private Label lb_tichDiem;

    @FXML
    private TextField txt_tkKH;
    

    @FXML
    private TableView<KhachHang> tbKhachHang;

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
    void denGDQLKH(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLKhachHang");
    }

    @FXML
    void timKiem(MouseEvent event) {
   
    	String maKhachHang = txt_tkKH.getText();
    	App.ma = maKhachHang;
    	KhachHang kh = new KhachHang_DAO().getKhachHangTheoSDT(maKhachHang);
		lb_MaKH.setText(kh.getIdKhachHang());
		lb_TenKH.setText(kh.getTenKhachHang());
		lb_sdtKH.setText(kh.getSoDienThoai());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		lb_nsKH.setText(kh.getNgaySinh().format(formatter));
		lb_tichDiem.setText(String.valueOf(kh.getTichDiem()));
		highlightMatchingRow(maKhachHang);
    }
    
    private void highlightMatchingRow(String maKhachHang) {
        for (int i = 0; i < tbKhachHang.getItems().size(); i++) {
            KhachHang khachhang = tbKhachHang.getItems().get(i);
            if (khachhang.getIdKhachHang().equals(maKhachHang)) {
                // Select the row (important)
                tbKhachHang.getSelectionModel().select(i);
                // Set focus to the row
                tbKhachHang.getFocusModel().focus(i);
                // Highlight the row (optional, but recommended)
                tbKhachHang.getFocusModel().focus(i);
                tbKhachHang.getSelectionModel().focus(i);
                break;
            }
        }
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	lb_MaKH.setText("");
    	lb_TenKH.setText("");
    	lb_sdtKH.setText("");
    	lb_nsKH.setText("");
    	lb_tichDiem.setText("");
    	lb_cccd.setText("");
    	ObservableList<String> list = FXCollections.observableArrayList("Khách hàng", "Nhân viên", "Phòng", "Hoá đơn",
				"Dịch vụ", "Tài khoản", "Ưu đãi");
		cbbGiaoDien.setItems(list);
		cbbGiaoDien.setValue("Khách Hàng");
		 // Xử lý sự kiện chọn ComboBox để chuyển giao diện
        cbbGiaoDien.setOnAction(event -> {
            String selectedValue = cbbGiaoDien.getValue();
            switch (selectedValue) {
            case "Khách hàng":
				try {
					App.setRoot("GD_TKKhachHang");
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
        
        
        clmaKH.setCellValueFactory(new PropertyValueFactory<>("idKhachHang"));
        cltenKH.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
        clSDT.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        clNgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));

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
        clTichDiem.setCellValueFactory(new PropertyValueFactory<>("tichDiem"));


        
        loadTableData();
        tbKhachHang.setOnMouseClicked(event -> {
        	KhachHang selectedKhachHang = tbKhachHang.getSelectionModel().getSelectedItem();
        	if (selectedKhachHang != null) {
        		lb_MaKH.setText(selectedKhachHang.getIdKhachHang());
        		lb_TenKH.setText(selectedKhachHang.getTenKhachHang());
        		lb_sdtKH.setText(selectedKhachHang.getSoDienThoai());
        		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        		lb_nsKH.setText(selectedKhachHang.getNgaySinh().format(formatter));
        		lb_cccd.setText(selectedKhachHang.getCccd());
        		lb_tichDiem.setText(String.valueOf(selectedKhachHang.getTichDiem()));
        	}
        });
        
        addUserLogin();
	}
	private void loadTableData() {
        try {
            KhachHang_DAO khdao = new KhachHang_DAO();
            ArrayList<KhachHang> dskh = khdao.getAllKhachHang();

            ObservableList<KhachHang> observableList = FXCollections.observableArrayList(dskh);
            tbKhachHang.setItems(observableList);
            
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
