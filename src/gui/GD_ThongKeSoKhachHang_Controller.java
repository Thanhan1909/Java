package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import dao.PhieuThuePhong_DAO;
import dao.TaiKhoan_DAO;
import entity.TaiKhoan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.App;
import javafx.scene.chart.CategoryAxis;

import javafx.scene.chart.BarChart;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;

public class GD_ThongKeSoKhachHang_Controller implements Initializable{
    @FXML
    private ComboBox<String> cbbLoai;
    @FXML
    private ComboBox<String> cbbNam;
    @FXML
    private ComboBox<String> cbbThang;
    @FXML
    private BarChart<String, Integer> chart_DTTN;
	@FXML
	private ImageView icon_TimKiem;
	@FXML
	private Label lb_TimKiem;
	@FXML
	private Label chon;
	@FXML
	private Label lb_01;
	@FXML
	private Label lb_TongSoKhach;
	@FXML
	private Label lb_02;
	@FXML
	private Label lb_TBSoKhach;
	@FXML
	private CategoryAxis x;
	@FXML
	private NumberAxis y;
	@FXML
	private Label lb_KHSS;
	@FXML
	private Label lb_soKhachSoSanh;
	@FXML
	private Label chon2;
	@FXML
	private Label chon21;
	@FXML
	private Label chon11;
	@FXML
	private DatePicker datePickerNgaybd;
	@FXML
	private Label chon111;
	@FXML
	private DatePicker datePickerNgaykt;
	@FXML
	private Button btnThongKe;
	@FXML
	private Label note;
	@FXML
	private ImageView avt;
	@FXML
	private Label maNV;
	@FXML
	private Label tenNV;
	

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
    	App.setRoot("GD_ThongKeKhachHang");
    }

    @FXML
    void moGDTKe(MouseEvent event) throws IOException {
		 App.setRoot("GD_ThongKeDoanhThu");
    }

	@FXML
	public void thongKe(MouseEvent event) {
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
        		thongKeTheoNgay(dateA, dateB);
        		note.setText("");
        	}
        }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		y.setAnimated(false);
		lb_soKhachSoSanh.setVisible(false);
		lb_KHSS.setVisible(false);
		createCBB();
		addUserLogin();
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
		setValue();
        XYChart.Series<String, Integer> chart1 = new XYChart.Series<>();

        // Crucial:  Generate dates and populate the chart initially.
        LocalDate currentDate = dateA;
        while (currentDate.isBefore(dateB.plusDays(1))) {
            String ngayString = currentDate.format(DateTimeFormatter.ofPattern("dd/MM"));
            chart1.getData().add(new XYChart.Data<>(ngayString, 0)); // Initialize with 0
            currentDate = currentDate.plusDays(1);
        }


        ArrayList<Map<LocalDate, Integer>> dsPT = new PhieuThuePhong_DAO().thongKeTheoNgay(dateA, dateB);

        if (dsPT == null || dsPT.isEmpty()) {
            setValue();
            new Alert(Alert.AlertType.CONFIRMATION, "Không có dữ liệu để thống kê").showAndWait();
            chart_DTTN.getData().clear();
            chart_DTTN.setLegendVisible(false);
            return;
        }

        // Using streams for efficiency and readability.  Much better!
        for (Map<LocalDate, Integer> map : dsPT) {
            map.forEach((ngay, tongKhach) -> {
                String ngayString = ngay.format(DateTimeFormatter.ofPattern("dd/MM"));
                chart1.getData().stream()
                        .filter(data -> data.getXValue().equals(ngayString))
                        .findFirst()
                        .ifPresent(data -> data.setYValue(tongKhach));
            });
        }


        chart_DTTN.getData().clear();
        chart_DTTN.getData().add(chart1);
        chart_DTTN.setLegendVisible(false);
        int soKhach = new PhieuThuePhong_DAO().tongKHAtoB(dateA, dateB);
        lb_TongSoKhach.setText(String.valueOf(soKhach));
        double daysDifference = ChronoUnit.DAYS.between(dateA, dateB);
        double tb = (double) soKhach / daysDifference;
        DecimalFormat df = new DecimalFormat("#.###");
        lb_TBSoKhach.setText(String.valueOf(df.format(tb)));
	}
	public void thongKeTheoThang(int month, int year) {
		setValue();
		int khachTrongThangNay = 0;
		int khachTrongThangTruoc = 0;
        XYChart.Series<String, Integer> chart1 = new XYChart.Series<>();
        
        String[] tenThang = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};

        for (int i = 0; i < 12; i++) {
            chart1.getData().add(new XYChart.Data<>(tenThang[i], 0));
        }
        
        ArrayList<Map<Integer, Integer>> tongSoKhach = new PhieuThuePhong_DAO().thongKeTheoThang(month, year);
        //Kiểm tra nếu không có dữ liệu trả về
        if (tongSoKhach == null || tongSoKhach.isEmpty()) {
        	setValue();
        	new Alert(Alert.AlertType.CONFIRMATION, "Không có dữ liệu để thống kê").showAndWait();
    		chart_DTTN.getData().clear();
    		chart_DTTN.setLegendVisible(false);
            return;
        }

        for (Map<Integer, Integer> map : tongSoKhach) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int thang = entry.getKey();
                int tongKhach = entry.getValue();
                if (thang == month) khachTrongThangNay = tongKhach;
                if (thang == month - 1) khachTrongThangTruoc = tongKhach;
                int index = thang - 1; // Tính chỉ số trong mảng tenThang

                if (index >= 0 && index < tenThang.length) { // Kiểm tra chỉ số hợp lệ
                    for (XYChart.Data<String, Integer> data : chart1.getData()) {
                        if (data.getXValue().equals(tenThang[index])) {
                            data.setYValue(tongKhach);
                            break;
                        }
                    }
                }
            }
        }
		chart_DTTN.getData().clear();
		chart_DTTN.setLegendVisible(false);
		chart_DTTN.getData().add(chart1);
		
		int soKhach = new PhieuThuePhong_DAO().tongKHThang(month, year);
        lb_TongSoKhach.setText(String.valueOf(soKhach));
        DecimalFormat df = new DecimalFormat("#.###");
        if (month == 2) {
        	lb_TBSoKhach.setText(String.valueOf(df.format((double) khachTrongThangNay / 29)));
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
        	lb_TBSoKhach.setText(String.valueOf(df.format((double) khachTrongThangNay / 30)));
        } else {
        	lb_TBSoKhach.setText(String.valueOf(df.format((double) khachTrongThangNay / 31)));
        }
        setVisible();
        int sosanh = khachTrongThangNay - khachTrongThangTruoc;
        if (sosanh > 0) lb_soKhachSoSanh.setText(String.valueOf(sosanh));
        else if (sosanh < 0) lb_soKhachSoSanh.setText("Ít hơn " + String.valueOf(sosanh* (-1)));
    }
	public void thongKeTheoNam(int year) {
		setValue();
		int soKhachNamNay = 0;
		int soKhachNamTruoc = 0;
        XYChart.Series<String, Integer> chart1 = new XYChart.Series<>();

        for (int i = year - 2; i <= year + 2; i++) {
            chart1.getData().add(new XYChart.Data<>(String.valueOf(i), 0));
        }

        ArrayList<Map<Integer, Integer>> tongSoKhach = new PhieuThuePhong_DAO().thongKeTheoNam(year);

        //Kiểm tra nếu không có dữ liệu trả về
        if (tongSoKhach == null || tongSoKhach.isEmpty()) {
        	new Alert(Alert.AlertType.CONFIRMATION, "Không có dữ liệu để thống kê").showAndWait();
    		chart_DTTN.getData().clear();
    		chart_DTTN.setLegendVisible(false);
            return;
        }

        for (Map<Integer, Integer> map : tongSoKhach) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int nam = entry.getKey();
                int soKhach = entry.getValue();
                if (nam == year) soKhachNamNay = soKhach;
                if (nam == year - 1) soKhachNamTruoc = soKhach;
                int index = nam; // Tính chỉ số trong mảng tenThang

                if (index >= year - 2 && index <= year + 2) { // Kiểm tra chỉ số hợp lệ
                    for (XYChart.Data<String, Integer> data : chart1.getData()) {
                        if (data.getXValue().equals(String.valueOf(index))) {
                            data.setYValue(soKhach);
                            System.out.println(soKhach);
                            break;
                        }
                    }
                }
            }
        }

		chart_DTTN.getData().clear();
		chart_DTTN.setLegendVisible(false);
		chart_DTTN.getData().add(chart1);
		int soKhach = new PhieuThuePhong_DAO().tongKHTNam(year);
		lb_TongSoKhach.setText(String.valueOf(soKhach));
        boolean isLeap = isLeapYear(year);
        DecimalFormat df = new DecimalFormat("#.###");
        if (isLeap == true) {
        	double tb = (double) soKhach / 366;
        	lb_TBSoKhach.setText(String.valueOf(df.format(tb)));
        } else {
        	double tb = (double) soKhach / 365;
        	lb_TBSoKhach.setText(String.valueOf(df.format(tb)));
        }
        setVisible();
        int sosanh = soKhachNamNay - soKhachNamTruoc;
        if (sosanh > 0) lb_soKhachSoSanh.setText(String.valueOf(sosanh));
        else if (sosanh < 0) lb_soKhachSoSanh.setText("Ít hơn " + String.valueOf(sosanh* (-1)));

    }
    private void setValue() {
		lb_TongSoKhach.setText("0");
		lb_TBSoKhach.setText("0");
		lb_soKhachSoSanh.setText("0");
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
    private void setVisible() {
		lb_KHSS.setVisible(true);
		lb_soKhachSoSanh.setVisible(true);
	}
    private boolean isLeapYear(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException("Năm phải lớn hơn 0.");
        }
        return Year.of(year).isLeap();
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
