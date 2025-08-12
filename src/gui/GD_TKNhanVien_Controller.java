package gui;

import dao.NhanVien_DAO;
import entity.NhanVien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GD_TKNhanVien_Controller {
    @FXML
    private TextField txt_maNV;
    @FXML
    private ComboBox<String> cbbGiaoDien;
    @FXML
    private TableView<NhanVien> tableNhanVien;
    @FXML
    private TableColumn<NhanVien, String> clmaNv, cltenNv, clSDT, clNgaySinh, clGioiTinh, clCCCD, clChucVu;
    @FXML
    private Label lb_MaNV, lb_tenNV, lb_sdtNV, lb_nsNV, lb_gioiTinh, lb_cccd, lb_chucVu;

    private NhanVien_DAO nhanVienDAO = new NhanVien_DAO();

    @FXML
    public void initialize() {
        cbbGiaoDien.getItems().addAll("Mã nhân viên", "Tên nhân viên", "Số điện thoại", "CCCD", "Chức vụ");
        cbbGiaoDien.getSelectionModel().selectFirst();
        loadAllNhanVien();

        // Thiết lập cột TableView
        clmaNv.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getIdNhanVien()));
        cltenNv.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTenNhanVien()));
        clSDT.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getSoDienThoai()));
        clNgaySinh.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                cell.getValue().getNgaySinh() != null ? cell.getValue().getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : ""));
        clGioiTinh.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().isGioiTinh() ? "Nam" : "Nữ"));
        clCCCD.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCccd()));
        clChucVu.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                cell.getValue().getChucVu() != null ? cell.getValue().getChucVu().getchucVu() : ""));

        // Sự kiện chọn dòng
        tableNhanVien.setOnMouseClicked(event -> {
            NhanVien nv = tableNhanVien.getSelectionModel().getSelectedItem();
            if (nv != null) {
                hienThiNhanVien(nv);
            }
        });
    }

    @FXML
    void timKiem(MouseEvent event) {
        String tuKhoa = txt_maNV.getText().trim().toLowerCase();
        String tieuChi = cbbGiaoDien.getValue();
        List<NhanVien> ds = nhanVienDAO.getAllNhanVien();
        List<NhanVien> ketQua = new ArrayList<>();

        for (NhanVien nv : ds) {
            switch (tieuChi) {
                case "Mã nhân viên":
                    if (nv.getIdNhanVien().toLowerCase().contains(tuKhoa)) ketQua.add(nv);
                    break;
                case "Tên nhân viên":
                    if (nv.getTenNhanVien().toLowerCase().contains(tuKhoa)) ketQua.add(nv);
                    break;
                case "Số điện thoại":
                    if (nv.getSoDienThoai().toLowerCase().contains(tuKhoa)) ketQua.add(nv);
                    break;
                case "CCCD":
                    if (nv.getCccd().toLowerCase().contains(tuKhoa)) ketQua.add(nv);
                    break;
                case "Chức vụ":
                    if (nv.getChucVu() != null && nv.getChucVu().getchucVu().toLowerCase().contains(tuKhoa)) ketQua.add(nv);
                    break;
            }
        }

        ObservableList<NhanVien> data = FXCollections.observableArrayList(ketQua);
        tableNhanVien.setItems(data);

        if (ketQua.isEmpty()) {
            clearLabels();
            showAlert("Không tìm thấy", "Không tìm thấy nhân viên phù hợp!");
        } else {
            hienThiNhanVien(ketQua.get(0));
            tableNhanVien.getSelectionModel().select(0);
        }
    }

    private void loadAllNhanVien() {
        List<NhanVien> ds = nhanVienDAO.getAllNhanVien();
        tableNhanVien.setItems(FXCollections.observableArrayList(ds));
    }

    private void hienThiNhanVien(NhanVien nv) {
        lb_MaNV.setText(nv.getIdNhanVien());
        lb_tenNV.setText(nv.getTenNhanVien());
        lb_sdtNV.setText(nv.getSoDienThoai());
        lb_cccd.setText(nv.getCccd());
        lb_gioiTinh.setText(nv.isGioiTinh() ? "Nam" : "Nữ");
        lb_chucVu.setText(nv.getChucVu() != null ? nv.getChucVu().getchucVu() : "");
        if (nv.getNgaySinh() != null) {
            lb_nsNV.setText(nv.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            lb_nsNV.setText("");
        }
    }

    private void clearLabels() {
        lb_MaNV.setText("");
        lb_tenNV.setText("");
        lb_sdtNV.setText("");
        lb_cccd.setText("");
        lb_gioiTinh.setText("");
        lb_chucVu.setText("");
        lb_nsNV.setText("");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void moGiaoDienTimKiem(MouseEvent event) {
        timKiem(event);
    }

    @FXML
    void denGDQLNhanVien(javafx.scene.input.MouseEvent event) {
        try {
            main.App.setRoot("GD_QLNhanVien");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void moGiaoDienThuePhong(javafx.scene.input.MouseEvent event) {
        try {
            main.App.setRoot("GD_SoDoPhong");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void moGiaoDienQuanLy(MouseEvent event) {
        try {
            main.App.setRoot("GD_QuanLy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void moGiaoDienThongKe(MouseEvent event) {
        try {
            main.App.setRoot("GD_ThongKe");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void dongUngDung(MouseEvent event) {
        System.exit(0);
    }
}
