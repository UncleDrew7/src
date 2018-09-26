package com.cdai.dao.userDaos;

import com.cdai.models.userModels.UserRoleModel;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by apple on 05/08/2017.
 * dao access pool generator
 */
public class UserRoleDao {

    private final Session session;

    public UserRoleDao(Session session) {
        this.session = session;
    }

    public List<UserRoleDao> getUserRolesByEmail(String userEmail){
        @SuppressWarnings("unchecked")
                List<UserRoleDao> roles = session.createQuery("from userrole where email=?").setString(0,userEmail).list();
        return roles;
    }
    
    public void insert(UserRoleModel role){
        session.persist(role);
    }
}
