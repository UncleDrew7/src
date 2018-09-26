package com.cdai.security;

import build.dao.UserDao;
import build.model.Role;
import build.model.User;
import netscape.security.Principal;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by apple on 01/09/2017.
 */
public class MyRealm extends JdbcRealm {
    private static final Logger log = LoggerFactory.getLogger(MyRealm.class);

    public MyRealm() {
        this.saltStyle = JdbcRealm.SaltStyle.NO_SALT;
    }


    /**
     * Principle colector
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("@MyRealm..@AuthorizationInfo..@doGetAuthorizationInfo(PrincipalCollection )");

//        String username = (String) principals.getPrimaryPrincipal();

        Role role = (Role) UserUtils.getCache(UserUtils.CACHE_ROLE);


        if(role != null){
            log.info("@MyReal-->role=" + role.getRole_name());
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole(role.getRole_name());
            int roleId = role.getRoleId();

            if(roleId==1){
                info.addStringPermission("admin");
            }
            if(roleId == 2){
                info.addStringPermission("teacher");
                info.addStringPermission("admin");
            }
            if(roleId==3){
                info.addStringPermission("student");
                info.addStringPermission("admin");
            }
            return info;

        }else{
            log.info("@MyReal-->No Role Returned");
            return null;

        }
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //identify account to log to
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/login.xml");
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");

        log.debug("@AuthenticationInfo..@doGetAuthenticationInfo -->> Processing Sent authentication data ");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        final String username = usernamePasswordToken.getUsername();
        char[] password = usernamePasswordToken.getPassword();

//        User user = daoAccess.getUserByUserId(username);
//        if(username == null){
//            log.error("Authentication failure ");
//            log.error("Username is null");
//            throw new AccountException("Null usernames are not allowed by this realm.");
//        }
            //get username to userId
        long userId =Long.parseLong(usernamePasswordToken.getUsername());
            User user = daoAccess.getUserByUserId(userId);

            log.debug("@myRealm --> comparing the two passwords");
            log.debug(usernamePasswordToken.getUsername());
            log.debug("@myRealm --> sent pass # "+usernamePasswordToken.getPassword());
            log.debug("@MyRealm --> Saved password ##");
            log.debug(user.getPassword());

            if(user != null){
                if(user.getPassword().equals(new String(password))){
                    log.debug("@myReal --> Passwords Match");
                    Principal principal = new Principal(user);
                    log.info("@myReal --> principle"+principal);

                    AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(new Principal(user),user.getPassword(),getName());
                    //return new SimpleAuthenticationInfo(user.getUserId(),user.getPassword(),user.getUserName());
                    return authcInfo;

                }else {
                    log.error("Incorrect User Password");
                    throw new UnknownAccountException();
                }
            }

        return null;
    }


    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private long id;
        private String loginName;
        private String name;
        private Map<String, Object> cacheMap;

        public Principal(User user) {
            this.id =  user.getUserId();
            this.loginName = user.getUserName();
            this.name = user.getUserName();
        }

        public long getId() {
            return id;
        }

        public String getLoginName() {
            return loginName;
        }

        public String getName() {
            return name;
        }

        public Map<String, Object> getCacheMap() {
            if (cacheMap == null) {
                cacheMap = new HashMap<String, Object>();
            }
            return cacheMap;
        }

    }


}
