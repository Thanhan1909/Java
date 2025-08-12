package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.App;
import java.io.IOException;
import connectDB.ConnectDB;
import dao.TaiKhoan_DAO;
import entity.TaiKhoan;
import javafx.event.ActionEvent;

public class DangNhap_Controller{
	@FXML
	private TextField txt_ten;
	@FXML
	private TextField txt_mk;
	@FXML
	private Button btn_LogIn;
	@FXML
    private ImageView avt;
	@FXML
	private Label lb_quenMK;
	
	// Event Listener on Button[#btn_LogIn].onAction
	@FXML
	public void LogIn(ActionEvent event) throws IOException {
		connect();
		String userName = txt_ten.getText().trim();
		String password = txt_mk.getText().trim();
		
		TaiKhoan_DAO tkdao = new TaiKhoan_DAO();
		TaiKhoan tk = tkdao.getTaiKhoanTheoUserNameAndPassword(userName, password);
		if (tk == null) {
			Alert alert = new Alert(AlertType.ERROR, "Vui lòng kiểm tra lại tài khoản và mật khẩu của bạn!", ButtonType.OK);
			alert.getDialogPane().setStyle("-fx-font-family: 'sans-serif';");
            alert.setTitle("Đăng nhập thất bại");
            alert.setHeaderText("Sai tài khoản hoặc mật khẩu");
            alert.showAndWait();
		} else {
			tkdao.capNhatDangNhap();
			tkdao.capNhatTaiKhoan(new TaiKhoan(userName, password, "Đang đăng nhập", tk.getNhanVien()));
			App.user = tk.getIdTaiKhoan();
			App.ma = tk.getMatKhau();
			App.tk = new TaiKhoan_DAO().getTaiKhoanTheoUserNameAndPassword(userName, password);
			Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
			stage.close();
            App.openMainGUI(); // Mở màn hình chính
            
        }
	}
	
	@FXML
    public void handleKeyboardEvent(KeyEvent ke) throws Exception {
        if (ke.getCode() == KeyCode.ENTER) {
            LogIn(new ActionEvent(ke.getSource(), ke.getTarget()));
        }
    }
	static void connect() {
		ConnectDB cn = new ConnectDB();
		cn.getInstance().connect();
	}
    @FXML
    void quenMK(MouseEvent event) throws IOException {
//		Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
//		stage.close();
//		App.openQuenMK();
    	App.openModal("GD_QuenMK", 600, 400);
    }
}
