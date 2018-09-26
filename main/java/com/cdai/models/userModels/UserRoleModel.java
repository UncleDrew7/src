package com.cdai.models.userModels;

import javax.persistence.*;

/**
 * Created by apple on 05/08/2017.
 */

@Entity
@Table(name = "userrole")
public class UserRoleModel {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column(name = "roleName")
    private String roleName;

    @Column(name = "email")
    private String email;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString(){
        return "id="+id+",roleName ="+roleName+",email="+email;
    }


}
