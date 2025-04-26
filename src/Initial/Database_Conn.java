package Initial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database_Conn {
    private static final String Url = "jdbc:mysql://localhost:3306/Student_Grading_System";
    private static final String User = "";
    private static final String Password = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Url, User, Password);
    }
}
