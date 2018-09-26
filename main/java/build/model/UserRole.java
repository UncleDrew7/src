package build.model;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class UserRole {

    private int id;
    private long userId;
    private int role_id;
    private String role;

    public UserRole(){
        super();
    }

    public UserRole(int userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
