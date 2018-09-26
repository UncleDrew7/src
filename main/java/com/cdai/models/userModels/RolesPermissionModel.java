package com.cdai.models.userModels;
        import javax.persistence.*;

/**
 * Created by apple on 05/08/2017.
 */
@Entity
@Table(name = "rolespermission")
public class RolesPermissionModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "permission")
    private String permission;

    @Column(name = "roleName")
    private String roleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public String toString(){
        return "id="+id+",permission="+permission+",roleName="+roleName;
    }
}
