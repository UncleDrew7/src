package com.cdai.dao.userDaos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cdai.models.userModels.UserModel;

/**
 * Created by apple on 05/08/2017.
 */
public class UserDao {

    private final Session session;

    public UserDao(Session session){
        this.session = session;
    }

    public UserModel getUserByEmail(String userEmail){
        UserModel user = (UserModel)session.createQuery("from user where email= ?").setString(0,userEmail).uniqueResult();
        return user;
    }
}
