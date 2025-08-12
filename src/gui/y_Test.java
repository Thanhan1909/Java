package gui;
	
import connectDB.ConnectDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class y_Test extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			connect();
//			Parent root = FXMLLoader.load(getClass().getResource("GD_QLTaiKhoan.fxml"));
//			Parent root = FXMLLoader.load(getClass().getResource("GD_QLNhanVien.fxml"));
//			Parent root = FXMLLoader.load(getClass().getResource("GD_QLUuDai.fxml"));
			Parent root = FXMLLoader.load(getClass().getResource("Bill.fxml"));


			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	static void connect() {
		ConnectDB cn = new ConnectDB();
		cn.getInstance().connect();
	}
}
