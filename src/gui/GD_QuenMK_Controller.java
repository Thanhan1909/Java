package gui;

import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class GD_QuenMK_Controller {

    @FXML
    private TextField txtCCCD;

    @FXML
    private TextField txtSDT;

    @FXML
    private TextField txtTenNV;

    @FXML
    private Label value_MatKhau;

    @FXML
    private Label value_TaiKhoan;

    @FXML
    void kiemTra(MouseEvent event) {
    	String ten = txtTenNV.getText();
    	String sdt = txtSDT.getText();
    	String cccd = txtCCCD.getText();
    	NhanVien nv = new NhanVien_DAO().quenMatKhau(ten, sdt, cccd);
    	if (nv != null) {
    		TaiKhoan tk = new TaiKhoan_DAO().layTaiKhoanTheoMaNV(nv.getIdNhanVien());
    		if (tk != null) {
        		value_TaiKhoan.setText(tk.getIdTaiKhoan());
        		value_MatKhau.setText(tk.getMatKhau());
    		} else {
    			showAlert("Lỗi", "Không tìm thấy tài khoản nhân viên này.");
    		}

    	} else {
    		showAlert("Lỗi", "Không tìm thấy nhân viên với thông tin đã nhập.");
    	}
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
