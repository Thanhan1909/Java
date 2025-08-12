package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	private static Connection con = null;
	private static ConnectDB instance = new ConnectDB();
	
	public static ConnectDB getInstance() {
		return instance;
	}
	
	public void connect() {
		String url = "jdbc:sqlserver://localhost:1433;databaseName=N11_PTUD_DB";
		String username = "sa";
		String password = "sapassword";
		try {
			con = DriverManager.getConnection(url, username, password);
			System.out.println("OK");
		} catch (SQLException e) {
			System.out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public Connection getConnection() {
	try {
        if (con == null || con.isClosed()) {
            // Tạo lại kết nối nếu nó bị null hoặc đã đóng
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=N11_PTUD_DB;user=sa;password=sapassword");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return con;
}
}