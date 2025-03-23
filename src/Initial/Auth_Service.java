package Initial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ahmad
 */
public class Auth_Service {
    public static User login(String username, String password) {
        User user = null;

        String sql = "SELECT * FROM Users WHERE Userame = ? AND Password = ?";

        try (Connection connection = Database_Conn.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int UserId = result.getInt("User_ID");
                String Role = result.getString("Role");

                user = new User(UserId, username, Role);
            }

        } 
        catch (SQLException excp) {
            System.out.println("Login Failed "+ excp.getMessage());
        }

        return user;
    }
}
