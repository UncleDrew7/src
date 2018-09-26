package build.model;

/**
 * Created by apple on 14/08/2017.
 */
public class Role {

    private int roleId;
    private String role_name;
    private String roleDescription;

    public Role() {
        super();
    }

    public Role(int id, String role_name) {
        this.roleId = id;
        this.role_name = role_name;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int id) {
        this.roleId = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
