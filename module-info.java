module PTUD_N11_2025fx {
	requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // nếu có dùng JDBC
    requires javafx.graphics;
    requires javafx.base;
    opens PTUD_N11_2025fx to javafx.fxml;
    exports PTUD_N11_2025fx;
}
