package build.model;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class UserRolePermission {

    private int id ;
    private String permission;
    private String roleName;
    private String email;

    public UserRolePermission(){
        super();
    }

    public UserRolePermission(String permission, String roleName, String email) {
        this.permission = permission;
        this.roleName = roleName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
