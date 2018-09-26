package com.cdai.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;

import org.hibernate.Session;

import com.cdai.dao.userDaos.UserDao;
import com.cdai.models.userModels.UserModel;

import com.cdai.util.HibernateUtil;


/**
 * Created by apple on 05/08/2017.
 */
public class MyCustomRealm extends JdbcRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token)throws AuthenticationException{
        //identify account to log to
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        final  String username = userPassToken.getUsername();

        if(username == null){
            System.out.println("Username is null.");
            return null;
        }

        //read password has and salt from db
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try{
            UserDao userDao = new UserDao(session);
            final UserModel user = userDao.getUserByEmail(username);

            if(user == null){
                System.out.println("No account found for user["+username+"]");
                return null;
            }
            //return salted credentials
            SaltedAuthenticationInfo info = new MySaltedAuthentificationInfo(username,user.getPassword(),user.getSalt());
            return info;
        }finally {
            session.getTransaction().commit();
            if(session.isOpen())session.close();
        }


    }

}
