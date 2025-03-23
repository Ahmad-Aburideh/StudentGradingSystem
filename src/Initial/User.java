package Initial;

/**
 *
 * @author ahmad
 */
public class User {
     private int UserId;
    private String Username;
    private String Role;

    public User(int userId, String username, String role) {
        this.UserId = userId;
        this.Username = username;
        this.Role = role;
    }

    public int getUserId() {
        return UserId;
    }

    public String getUsername() {
        return Username;
    }

    public String getRole() {
        return Role;
    }
}
