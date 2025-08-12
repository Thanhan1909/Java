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

import dao.HoaDon_DAO;
import dao.Phong_DAO;
import dao.TaiKhoan_DAO;
import entity.HoaDon;
import entity.KhuyenMai;
import entity.LoaiPhong;
import entity.Phong;
import entity.TaiKhoan;
import entity.TrangThaiPhong;
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

public class GD_TKHoaDon_Controller implements Initializable{

    @FXML
    private ImageView avt;
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
    @FXML
    private Button btnTraCuu;

    @FXML
    private ComboBox<String> cbbGiaoDien;

    @FXML
    private TableColumn<HoaDon, String> clSTT;

    @FXML
    private TableColumn<HoaDon, String> clTGT;

    @FXML
    private TableColumn<HoaDon, String> clTHC;

    @FXML
    private TableColumn<HoaDon, String> clmaHD;

    @FXML
    private TableColumn<HoaDon, String> clmaKH;

    @FXML
    private TableColumn<HoaDon, String> clmaKM;

    @FXML
    private TableColumn<HoaDon, String> clmaNV;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private ImageView icon_TimKiem1;

    @FXML
    private Label lb_MaHD;

    @FXML
    private Label lb_MaKH;

    @FXML
    private Label lb_MaKM;

    @FXML
    private Label lb_MaNV;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label lb_tgCheckin;

    @FXML
    private Label lb_tgTao;

    @FXML
    private TableView<HoaDon> tableHoaDon;

    @FXML
    private TextField txt_tkHD;
    
    @FXML
    private Button btnQLHD;

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
    void denGDQLHD(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLHoaDon");
    }

    @FXML
    void timKiem(MouseEvent event) {
    	
    	String maHoaDon = txt_tkHD.getText();
    	App.ma = maHoaDon;
    	HoaDon hd = new HoaDon_DAO().layHoaDonTheoMaHoaDon(maHoaDon);
		lb_MaHD.setText(hd.getIdHoaDon());
		lb_MaNV.setText(hd.getNhanVienLap().getIdNhanVien());
		lb_MaKH.setText(hd.getKhachHang().getIdKhachHang());
		lb_MaKM.setText(hd.getKhuyenmai() != null ? hd.getKhuyenmai().getIdKhuyenMai() : "");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		lb_tgTao.setText(hd.getThoiGianTao().format(formatter));
		lb_tgCheckin.setText(hd.getThoiGianCheckin().format(formatter));
		highlightMatchingRow(maHoaDon);
    }
    private void highlightMatchingRow(String maHoaDon) {
        for (int i = 0; i < tableHoaDon.getItems().size(); i++) {
            HoaDon hoadon = tableHoaDon.getItems().get(i);
            if (hoadon.getIdHoaDon().equals(maHoaDon)) {
                // Select the row (important)
            	tableHoaDon.getSelectionModel().select(i);
                // Set focus to the row
            	tableHoaDon.getFocusModel().focus(i);
                // Highlight the row (optional, but recommended)
            	tableHoaDon.getFocusModel().focus(i);
            	tableHoaDon.getSelectionModel().focus(i);
                break;
            }
        }
    }

   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lb_MaHD.setText("");
		lb_MaNV.setText("");
		lb_MaKH.setText("");
		lb_MaKM.setText("");
		lb_tgCheckin.setText("");
		lb_tgTao.setText("");
		ObservableList<String> list = FXCollections.observableArrayList("Hoá đơn", "Phòng", "Nhân viên", "Khách hàng",
				"Dịch vụ", "Tài khoản", "Ưu đãi");
		cbbGiaoDien.setItems(list);
		cbbGiaoDien.setValue("Hoá đơn");
		 // Xử lý sự kiện chọn ComboBox để chuyển giao diện
        cbbGiaoDien.setOnAction(event -> {
            String selectedValue = cbbGiaoDien.getValue();
            switch (selectedValue) {
            	case "Hoá đơn":
				try {
					App.setRoot("GD_TKHoaDon");
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
        
        
		
        clmaHD.setCellValueFactory(new PropertyValueFactory<>("idHoaDon"));
        clmaNV.setCellValueFactory(cellData -> {
        	String manv = cellData.getValue().getNhanVienLap().getIdNhanVien();
        	return new ReadOnlyStringWrapper(manv);
        });
        clmaKH.setCellValueFactory(cellData -> {
        	String manv = cellData.getValue().getKhachHang().getIdKhachHang();
        	return new ReadOnlyStringWrapper(manv);
        });
        clmaKM.setCellValueFactory(cellData -> {
        	KhuyenMai km = cellData.getValue().getKhuyenmai();
        	return new ReadOnlyStringWrapper(km != null ? km.getIdKhuyenMai() : "");
        });
        clTGT.setCellValueFactory(cellData -> {
            LocalDateTime ngayTao = cellData.getValue().getThoiGianTao();
            if (ngayTao != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
                String ngayTaoFormatted = ngayTao.format(formatter);
                return new ReadOnlyStringWrapper(ngayTaoFormatted);
            }
            return new ReadOnlyStringWrapper("");
        });
        clTHC.setCellValueFactory(cellData -> {
            LocalDateTime ngayCheckin = cellData.getValue().getThoiGianCheckin();
            if (ngayCheckin != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
                String ngayCheckinFormatted = ngayCheckin.format(formatter);
                return new ReadOnlyStringWrapper(ngayCheckinFormatted);
            }
            return new ReadOnlyStringWrapper("");
        });
        loadTableData();
        tableHoaDon.setOnMouseClicked(event -> {
        	HoaDon selectedHoaDon = tableHoaDon.getSelectionModel().getSelectedItem();
        	if (selectedHoaDon != null) {
        		lb_MaHD.setText(selectedHoaDon.getIdHoaDon());
        		lb_MaNV.setText(selectedHoaDon.getNhanVienLap().getIdNhanVien());
        		lb_MaKH.setText(selectedHoaDon.getKhachHang().getIdKhachHang());
        		lb_MaKM.setText(selectedHoaDon.getKhuyenmai() != null ? selectedHoaDon.getKhuyenmai().getIdKhuyenMai() : "");
        		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        		lb_tgTao.setText(selectedHoaDon.getThoiGianTao().format(formatter));
        		lb_tgCheckin.setText(selectedHoaDon.getThoiGianCheckin().format(formatter));
        	}
        });
        
        addUserLogin();
	}
    private void loadTableData() {
        try {
            HoaDon_DAO hddao = new HoaDon_DAO();
            ArrayList<HoaDon> dshd = hddao.getAllHoaDon();

            ObservableList<HoaDon> observableList = FXCollections.observableArrayList(dshd);
            tableHoaDon.setItems(observableList);
            
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
