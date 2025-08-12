package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.SplashController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.App;

public class SplashController implements Initializable{
	@FXML
	private Label lblLoading;
	private static Label lblLoadingg;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblLoadingg = lblLoading;
	}
	public String checkFuntions() {
		final String[] message = {""};

		Thread t1 = new Thread(() -> {
			message[0] = "Đang khởi chạy ứng dụng";
			Platform.runLater(() -> {
				lblLoadingg.setText(message[0]);
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});

		Thread t2 = new Thread(() -> {
			message[0] = "Nạp dữ liệu lên ứng dụng";
			Platform.runLater(() -> {
				lblLoadingg.setText(message[0]);
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});

		Thread t3 = new Thread(() -> {
			Platform.runLater(() -> {
				try {
				Thread.sleep(1000);
				App.openModal("GD_DangNhap");
				
			} catch (IOException | InterruptedException ex) {
				Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
			}
			});
		});

		try {
			t1.start();
			t1.join();
			t2.start();
			t2.join();
			t3.start();
			t3.join();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		return message[0];
	}
}
