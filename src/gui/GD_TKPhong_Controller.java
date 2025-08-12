package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.Phong_DAO;
import dao.TaiKhoan_DAO;
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
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.App;

public class GD_TKPhong_Controller implements Initializable{

    @FXML
    private ImageView avt;
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
    @FXML
    private Button btnQLP;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private ImageView iconFind;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label lb_donGia;

    @FXML
    private Label lb_loaiPhong;

    @FXML
    private Label lb_maPhong;

    @FXML
    private Label lb_trangThai;
    
    @FXML
    private Label lb_TieuChi;

    @FXML
    private TableView<Phong> tablePhong;
    
    @FXML
    private Button btnTraCuu;

    @FXML
    private ComboBox<String> cbbGiaoDien;

    @FXML
    private TableColumn<Phong, String> clDG;
    @FXML
    private TableColumn<Phong, String> clLP;

    @FXML
    private TableColumn<Phong, String> clMaP;

    @FXML
    private TableColumn<Phong, String> clSTT;

    @FXML
    private TableColumn<Phong, String> clTT;
    
    @FXML
    private TableColumn<Phong, String> clTC;

    @FXML
    private TextField txt_MaPhong;

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
    void denGDQLPhong(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLPhong");
    }
   

    @FXML
    void timKiem(MouseEvent event) {
   
    	String maPhong = txt_MaPhong.getText();
    	App.ma = maPhong;
    	Phong p = new Phong_DAO().getPhongTheoMa(maPhong);
		lb_maPhong.setText(p.getIdPhong());
		lb_loaiPhong.setText(p.getLoaiPhongString());
		lb_donGia.setText(String.valueOf(p.getDonGia()));
		lb_trangThai.setText(p.getTrangThaiString());
		highlightMatchingRow(maPhong);
    }
    private void highlightMatchingRow(String maPhong) {
        for (int i = 0; i < tablePhong.getItems().size(); i++) {
            Phong phong = tablePhong.getItems().get(i);
            if (phong.getIdPhong().equals(maPhong)) {
                // Select the row (important)
                tablePhong.getSelectionModel().select(i);
                // Set focus to the row
                tablePhong.getFocusModel().focus(i);
                // Highlight the row (optional, but recommended)
                tablePhong.getFocusModel().focus(i);
        		tablePhong.getSelectionModel().focus(i);
                break;
            }
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lb_maPhong.setText("");
		lb_loaiPhong.setText("");
		lb_donGia.setText("");
		lb_trangThai.setText("");
		lb_TieuChi.setText("");
		ObservableList<String> list = FXCollections.observableArrayList("Phòng", "Hoá đơn", "Nhân viên", "Khách hàng",
				"Dịch vụ", "Tài khoản", "Ưu đãi");
		cbbGiaoDien.setItems(list);
		cbbGiaoDien.setValue("Phòng");
		 // Xử lý sự kiện chọn ComboBox để chuyển giao diện
        cbbGiaoDien.setOnAction(event -> {
            String selectedValue = cbbGiaoDien.getValue();
            switch (selectedValue) {
            case "Phòng":
				try {
					App.setRoot("GD_TKPhong");
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
            return new TableCell<Phong, String>() {
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
        
        
        clMaP.setCellValueFactory(new PropertyValueFactory<>("idPhong"));
        clLP.setCellValueFactory(cellData -> {
        	LoaiPhong lp = cellData.getValue().getLoaiPhong();
        	return new ReadOnlyStringWrapper(lp.toString());
        });
        clDG.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        clTT.setCellValueFactory(cellData -> {
        	TrangThaiPhong tt = cellData.getValue().getTrangThai();
        	return new ReadOnlyStringWrapper(tt.toString());
        });
        clTC.setCellValueFactory(new PropertyValueFactory<>("tieuChi"));
        
        loadTableData();
        tablePhong.setOnMouseClicked(event -> {
        	Phong selectedPhong = tablePhong.getSelectionModel().getSelectedItem();
        	if (selectedPhong != null) {
        		lb_maPhong.setText(selectedPhong.getIdPhong());
        		lb_loaiPhong.setText(selectedPhong.getLoaiPhongString());
        		lb_donGia.setText(String.valueOf(selectedPhong.getDonGia()));
        		lb_trangThai.setText(selectedPhong.getTrangThaiString());
        		lb_TieuChi.setText(selectedPhong.getTieuChi());
        	}
        });
        
        addUserLogin();
	}
    private void loadTableData() {
        try {
            Phong_DAO pdao = new Phong_DAO();
            ArrayList<Phong> dsp = pdao.getAllPhong();
            ObservableList<Phong> observableList = FXCollections.observableArrayList(dsp);
            tablePhong.setItems(observableList);
            
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
