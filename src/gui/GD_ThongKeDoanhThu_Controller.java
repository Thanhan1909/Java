package gui;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import dao.HoaDon_DAO;
import dao.TaiKhoan_DAO;
import entity.TaiKhoan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.App;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class GD_ThongKeDoanhThu_Controller implements Initializable{
	@FXML
    private ImageView avt;
    @FXML
    private ComboBox<String> cbbLoai;
    @FXML
    private ComboBox<String> cbbNam;
    @FXML
    private ComboBox<String> cbbThang;
    @FXML
    private BarChart<String, Double> chart_DTTN;
    @FXML
    private Button btnThongKe;
    @FXML
    private Label chon;
    @FXML
    private Label chon1;
    @FXML
    private Label maNV;
    @FXML
    private Label tenNV;
    @FXML
    private Label chon2;
    @FXML
    private Label chon21;
    @FXML
    private Label chon11;
    @FXML
    private Label chon111;
    @FXML
    private DatePicker datePickerNgaybd;
    @FXML
    private DatePicker datePickerNgaykt;
    @FXML
    private ImageView icon_TimKiem;
    @FXML
    private Label lb_DT;
    @FXML
    private Label lb_DTSS;
    @FXML
    private Label lb_SoHD;
    @FXML
    private Label lb_SoHoaDon;
    @FXML
    private Label lb_Tien;
    @FXML
    private Label lb_Tienss;
    @FXML
    private Label lb_TimKiem;
    @FXML
    private Label note;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lb_DTSS.setVisible(false);
		lb_Tienss.setVisible(false);
		createCBB();
		addUserLogin();
//		thongKeTheoNgay(LocalDate.of(2024,10,12));
//		thongKeTheoThang(10,2024);
//		thongKeTheoNam(2023);
	}
    @FXML
    void thongKe(MouseEvent event) {
        if (cbbLoai.getValue() != null && cbbLoai.getValue().equals("Theo tháng")) {
    		datePickerNgaybd.setValue(null);
    		datePickerNgaykt.setValue(null);
            String month = cbbThang.getValue();
            String yearString = cbbNam.getValue();
            if (month == null && yearString == null) {
                note.setText("*Bạn chưa chọn tháng hoặc năm"); // Corrected message
                chart_DTTN.getData().clear();
                chart_DTTN.setLegendVisible(false);
        	} else if (month == null) {
                note.setText("*Tháng không hợp lệ");
                chart_DTTN.getData().clear();
                chart_DTTN.setLegendVisible(false);
            } else if(yearString == null) {
                note.setText("*Năm không hợp lệ");
                chart_DTTN.getData().clear();
                chart_DTTN.setLegendVisible(false);
            }
            if (month != null && yearString != null) {
                    int thang = getThangInt(month);
                    int nam = Integer.parseInt(yearString);

                    if (thang != -1) {
                        thongKeTheoThang(thang, nam);
                    }
            }
        } else if (cbbLoai.getValue() != null && cbbLoai.getValue().equals("Theo năm")) {
    		datePickerNgaybd.setValue(null);
    		datePickerNgaykt.setValue(null);
        	cbbThang.setValue("");
            String yearString = cbbNam.getValue();
            if (yearString == null) {
                note.setText("*Năm không hợp lệ");
                chart_DTTN.getData().clear();
                chart_DTTN.setLegendVisible(false);
            } else {
            	int nam = Integer.parseInt(yearString);
            	thongKeTheoNam(nam);
            	note.setText("");
            }
        } else if (cbbLoai.getValue() != null && cbbLoai.getValue().equals("Theo ngày")) {
    		cbbNam.setValue("");
    		cbbThang.setValue("");
        	LocalDate dateA = datePickerNgaybd.getValue();
        	LocalDate dateB = datePickerNgaykt.getValue();
        	
        	if (dateA == null || dateB == null) {
                note.setText("*Vui lòng chọn cả hai ngày.");
                chart_DTTN.getData().clear();
                chart_DTTN.setLegendVisible(false);
                return;
            }
        	if (dateA != null && dateB != null) {
        		thongKeTheoNgayA(dateA, dateB);
        		note.setText("");
        	}
        }
    }
	public void createCBB() {
		ObservableList<String> list = FXCollections.observableArrayList("Theo ngày", "Theo tháng", "Theo năm");
		cbbLoai.setItems(list);
		cbbLoai.setValue("Theo ngày");
		ObservableList<String> thang = FXCollections.observableArrayList("", "Tháng 1","Tháng 2","Tháng 3"
				,"Tháng 4","Tháng 5","Tháng 6","Tháng 7","Tháng 8","Tháng 9","Tháng 10","Tháng 11","Tháng 12");
		cbbThang.setItems(thang);
		ObservableList<String> nam = FXCollections.observableArrayList();
		LocalDate now = LocalDate.now();
		nam.add("");
		for (int i = now.getYear(); i > now.getYear() - 5; i--) {
			nam.add(String.valueOf(i));
		}
		cbbNam.setItems(nam);
	}
	
	public void thongKeTheoNgay(LocalDate dateA, LocalDate dateB) {
        XYChart.Series<String, Double> chart1 = new XYChart.Series<>();
		for (int i = 1; i <= 31; i++) {
			chart1.getData().add(new XYChart.Data<>(String.valueOf(i), 0.0));
		}
        ArrayList<Map<LocalDate, Double>> dsHD = new HoaDon_DAO().TheoNgayob(dateA, dateB);
		if (dsHD == null || dsHD.isEmpty()) {
        	new Alert(Alert.AlertType.CONFIRMATION, "Không có dữ liệu để thống kê").showAndWait();
    		chart_DTTN.getData().clear();
    		chart_DTTN.setLegendVisible(false);
            return;
        }
		
        for (Map<LocalDate, Double> map : dsHD) {
            for (Map.Entry<LocalDate, Double> entry : map.entrySet()) {
                LocalDate ngay = entry.getKey();
                double tongTien = entry.getValue();
                String ngayString = String.valueOf(ngay.getDayOfMonth());
                boolean found = false;
                for (XYChart.Data<String, Double> data : chart1.getData()) {
                    if (data.getXValue().equals(ngayString)) {
                        data.setYValue(tongTien);
						found = true;
                        break;
                    }
//                    if (data.equals(String.valueOf(date.getDayOfMonth()))) {
//                    	data.getNode().setStyle("-fx-background-color: #FFC0CB; -fx-text-fill: black;");
//                    }
                }
                if(!found){
                    System.err.println("Ngày " + ngayString + " không tìm thấy trong danh sách.");
                }
            }
        }
		chart_DTTN.getData().clear();
		chart_DTTN.setLegendVisible(false);
		chart_DTTN.getData().add(chart1);
    }
	public void thongKeTheoNgayA(LocalDate dateA, LocalDate dateB) {
		setValue();
	    XYChart.Series<String, Double> chart1 = new XYChart.Series<>();

	    // Crucial:  Generate dates and populate the chart initially.
	    LocalDate currentDate = dateA;
	    while (currentDate.isBefore(dateB.plusDays(1))) {
	        String ngayString = currentDate.format(DateTimeFormatter.ofPattern("dd/MM")); // Use dd/MM format
	        chart1.getData().add(new XYChart.Data<>(ngayString, 0.0));
	        currentDate = currentDate.plusDays(1);
	    }

	    ArrayList<Map<LocalDate, Double>> dsHD = new HoaDon_DAO().TheoNgayob(dateA, dateB);

	    if (dsHD == null || dsHD.isEmpty()) {
	    	setValue();
	        new Alert(Alert.AlertType.CONFIRMATION, "Không có dữ liệu để thống kê").showAndWait();
	        chart_DTTN.getData().clear();
	        chart_DTTN.setLegendVisible(false);
	        return;
	    }

	    for (Map<LocalDate, Double> map : dsHD) {
	        for (Map.Entry<LocalDate, Double> entry : map.entrySet()) {
	            LocalDate ngay = entry.getKey();
	            double tongTien = entry.getValue();
	            String ngayString = ngay.format(DateTimeFormatter.ofPattern("dd/MM")); // Format to dd/MM

	            // Use streams for efficient lookup.  Much cleaner and easier to read
	            chart1.getData().stream()
	                    .filter(data -> data.getXValue().equals(ngayString)) // Filter for the correct day
	                    .findFirst()
	                    .ifPresent(data -> data.setYValue(tongTien)); // Set Y value if found
	        }
	    }

		// Clear and add the updated chart series.
	    chart_DTTN.getData().clear();
	    chart_DTTN.setLegendVisible(false);
	    chart_DTTN.getData().add(chart1);
		int soHD = new HoaDon_DAO().demHDAtoB(dateA, dateB);
		if (soHD >= 0) {
			lb_SoHoaDon.setText(String.valueOf(soHD));
			lb_Tien.setText(String.valueOf((int)(new HoaDon_DAO().tongDTAtoB(dateA, dateB)) + " VND"));
		}
	}
	public void thongKeTheoThang(int month, int year) {
		setValue();
        XYChart.Series<String, Double> chart1 = new XYChart.Series<>();
        
        String[] tenThang = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};

        for (int i = 0; i < 12; i++) {
            chart1.getData().add(new XYChart.Data<>(tenThang[i], 0.0));
        }
        
        ArrayList<Map<Integer, Double>> tongTienTheoThang = new HoaDon_DAO().theoThang(month, year);

        //Kiểm tra nếu không có dữ liệu trả về
        if (tongTienTheoThang == null || tongTienTheoThang.isEmpty()) {
        	setValue();
        	new Alert(Alert.AlertType.CONFIRMATION, "Không có dữ liệu để thống kê").showAndWait();
    		chart_DTTN.getData().clear();
    		chart_DTTN.setLegendVisible(false);
            return;
        }

        for (Map<Integer, Double> map : tongTienTheoThang) {
            for (Map.Entry<Integer, Double> entry : map.entrySet()) {
                int thang = entry.getKey();
                double tongTien = entry.getValue();
                int index = thang - 1; // Tính chỉ số trong mảng tenThang

                if (index >= 0 && index < tenThang.length) { // Kiểm tra chỉ số hợp lệ
                    for (XYChart.Data<String, Double> data : chart1.getData()) {
                        if (data.getXValue().equals(tenThang[index])) {
                            data.setYValue(tongTien);
                            break;
                        }
                    }
                }
            }
        }
		chart_DTTN.getData().clear();
		chart_DTTN.setLegendVisible(false);
		chart_DTTN.getData().add(chart1);
		int soHD = new HoaDon_DAO().demHDTheoThang(month, year);
		int dt = (int) new HoaDon_DAO().tongDTThang(month, year);
		if (soHD >= 0) {
			lb_SoHoaDon.setText(String.valueOf(soHD));
			lb_Tien.setText(String.valueOf(dt + " VND"));
			lb_DTSS.setVisible(true);
			int dttruoc = (int) new HoaDon_DAO().tongDTThang(month - 1, year);
			int so = (int) dt - dttruoc;
			lb_Tienss.setVisible(true);
			lb_Tienss.setText(String.valueOf(so + " VND"));
		}

    }
	public void thongKeTheoNam(int year) {
		setValue();
        XYChart.Series<String, Double> chart1 = new XYChart.Series<>();

        for (int i = year - 2; i <= year + 2; i++) {
            chart1.getData().add(new XYChart.Data<>(String.valueOf(i), 0.0));
        }

        ArrayList<Map<Integer, Double>> tongTienTheoNam = new HoaDon_DAO().theoNam(year);

        //Kiểm tra nếu không có dữ liệu trả về
        if (tongTienTheoNam == null || tongTienTheoNam.isEmpty()) {
        	new Alert(Alert.AlertType.CONFIRMATION, "Không có dữ liệu để thống kê").showAndWait();
    		chart_DTTN.getData().clear();
    		chart_DTTN.setLegendVisible(false);
            return;
        }

        for (Map<Integer, Double> map : tongTienTheoNam) {
            for (Map.Entry<Integer, Double> entry : map.entrySet()) {
                int nam = entry.getKey();
                double tongTien = entry.getValue();
                int index = nam; // Tính chỉ số trong mảng tenThang

                if (index >= year - 2 && index <= year + 2) { // Kiểm tra chỉ số hợp lệ
                    for (XYChart.Data<String, Double> data : chart1.getData()) {
                        if (data.getXValue().equals(String.valueOf(index))) {
                            data.setYValue(tongTien);
                            System.out.println(tongTien);
                            break;
                        }
                    }
                }
            }
        }

		chart_DTTN.getData().clear();
		chart_DTTN.setLegendVisible(false);
		chart_DTTN.getData().add(chart1);
		int soHD = new HoaDon_DAO().demHDTheoNam(year);
		int dt = (int) new HoaDon_DAO().tongDTTNam(year);
		if (soHD >= 0) {
			lb_SoHoaDon.setText(String.valueOf(soHD));
			lb_Tien.setText(String.valueOf(dt + " VND"));
			lb_DTSS.setVisible(true);
			int dttruoc = (int) new HoaDon_DAO().tongDTTNam(year - 1);
			int so = (int) dt - dttruoc;
			lb_Tienss.setVisible(true);
			lb_Tienss.setText(String.valueOf(so + " VND"));
		}
    }

	    
	    @FXML
	    void moGDQL(MouseEvent event) throws IOException {
	    	App.setRoot("GD_QLPhong");
	    }

	    @FXML
	    void moGDQLTP(MouseEvent event) throws IOException {
	    	App.setRoot("GD_SoDoPhong");
	    }

	    @FXML
	    void moGDTK(MouseEvent event) throws IOException {
			 App.setRoot("GD_TKPhong");
	    }

	    @FXML
	    void moGDTKDT(MouseEvent event) throws IOException {
			 App.setRoot("GD_ThongKeDoanhThu");
	    }

	    @FXML
	    void moGDTKKH(MouseEvent event) throws IOException {
	    	App.setRoot("GD_ThongKeSoKhachHang");
	    }

	    @FXML
	    void moGDTKe(MouseEvent event) throws IOException {
			 App.setRoot("GD_ThongKeDoanhThu");
	    }
	    private int getThangInt(String thangString) {
	        switch (thangString) {
	            case "Tháng 1": return 1;
	            case "Tháng 2": return 2;
	            case "Tháng 3": return 3;
	            case "Tháng 4": return 4;
	            case "Tháng 5": return 5;
	            case "Tháng 6": return 6;
	            case "Tháng 7": return 7;
	            case "Tháng 8": return 8;
	            case "Tháng 9": return 9;
	            case "Tháng 10": return 10;
	            case "Tháng 11": return 11;
	            case "Tháng 12": return 12;
	            default: return -1; // Or throw an exception if needed
	        }
	    }
	    private void setValue() {
			lb_SoHoaDon.setText("0");
			lb_Tien.setText("0 VND");
			lb_Tienss.setText("0 VND");
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
