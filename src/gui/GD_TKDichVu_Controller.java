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

import dao.DichVu_DAO;
import dao.KhachHang_DAO;
import dao.TaiKhoan_DAO;
import entity.DichVu;
import entity.KhachHang;
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

public class GD_TKDichVu_Controller implements Initializable{
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
    @FXML
    private ImageView avt;

    @FXML
    private Button btnTraCuu;

    @FXML
    private Button btnQLDV;

    @FXML
    private ComboBox<String> cbbGiaoDien;

    @FXML
    private TableColumn<DichVu, String> clDG;

    @FXML
    private TableColumn<DichVu, String> clSL;

    @FXML
    private TableColumn<DichVu, String> clSTT;

    @FXML
    private TableColumn<DichVu, String> clmaDV;

    @FXML
    private TableColumn<DichVu, String> cltenSP;

    @FXML
    private ImageView icon_TimKiem;

    @FXML
    private ImageView icon_TimKiem1;

    @FXML
    private Label lb_MaDV;

    @FXML
    private Label lb_TimKiem;

    @FXML
    private Label lb_giaDV;

    @FXML
    private Label lb_soLuong;

    @FXML
    private Label lb_tenDV;

    @FXML
    private TableView<DichVu> tableDichVu;

    @FXML
    private TextField txt_maDV;

    @FXML
    void moGiaoDienQuanLy(MouseEvent event) {

    }

    @FXML
    void moGiaoDienThongKe(MouseEvent event) {

    }

    @FXML
    void moGiaoDienThuePhong(MouseEvent event) {

    }

    @FXML
    void moGiaoDienTimKiem(MouseEvent event) {

    }
    
    @FXML
    void denGDQLDV(MouseEvent event) throws IOException {
    	App.setRoot("GD_QLDichVu");
    }

    @FXML
    void timKiem(MouseEvent event) {
   
    	String maDichVu = txt_maDV.getText();
    	App.ma = maDichVu;
    	DichVu dv = new DichVu_DAO().layDichVuTheoMa(maDichVu);
		lb_MaDV.setText(dv.getIdDichVu());
		lb_tenDV.setText(dv.getTenSanPham());
		lb_soLuong.setText(String.valueOf(dv.getSoLuong()));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		lb_giaDV.setText(String.valueOf(dv.getDonGia()));
		highlightMatchingRow(maDichVu);
    }
    private void highlightMatchingRow(String maDichVu) {
        for (int i = 0; i < tableDichVu.getItems().size(); i++) {
            DichVu dichvu = tableDichVu.getItems().get(i);
            if (dichvu.getIdDichVu().equals(maDichVu)) {
                // Select the row (important)
                tableDichVu.getSelectionModel().select(i);
                // Set focus to the row
                tableDichVu.getFocusModel().focus(i);
                // Highlight the row (optional, but recommended)
                tableDichVu.getFocusModel().focus(i);
                tableDichVu.getSelectionModel().focus(i);
                break;
            }
        }
    }
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	lb_MaDV.setText("");
    	lb_tenDV.setText("");
    	lb_soLuong.setText("");
    	lb_giaDV.setText("");
		ObservableList<String> list = FXCollections.observableArrayList("Dịch vụ", "Hoá đơn", "Phòng", "Nhân viên", "Khách hàng", 
				"Tài khoản", "Ưu đãi");
		cbbGiaoDien.setItems(list);
		cbbGiaoDien.setValue("Dịch vụ");
		 // Xử lý sự kiện chọn ComboBox để chuyển giao diện
        cbbGiaoDien.setOnAction(event -> {
            String selectedValue = cbbGiaoDien.getValue();
            switch (selectedValue) {
            case "Dịch vụ":
				try {
					App.setRoot("GD_TKDichVu");
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
        
        
        clmaDV.setCellValueFactory(new PropertyValueFactory<>("idDichVu"));
        cltenSP.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        clSL.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        clDG.setCellValueFactory(new PropertyValueFactory<>("donGia"));


        
        loadTableData();
        tableDichVu.setOnMouseClicked(event -> {
        	DichVu selectedDichVu = tableDichVu.getSelectionModel().getSelectedItem();
        	if (selectedDichVu != null) {
        		lb_MaDV.setText(selectedDichVu.getIdDichVu());
        		lb_tenDV.setText(selectedDichVu.getTenSanPham());
        		lb_soLuong.setText(String.valueOf(selectedDichVu.getSoLuong()));
        		lb_giaDV.setText(String.valueOf(selectedDichVu.getDonGia()));
        	}
        });
        
        addUserLogin();
	}
	private void loadTableData() {
        try {
            DichVu_DAO dvdao = new DichVu_DAO();
            ArrayList<DichVu> dsdv = dvdao.getAllDichVu();

            ObservableList<DichVu> observableList = FXCollections.observableArrayList(dsdv);
            tableDichVu.setItems(observableList);
            
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
