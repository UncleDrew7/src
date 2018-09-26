package com.cdai.security.backup;

import build.dao.UserDao;
import build.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 01/09/2017.
 */
public class MyRealm extends JdbcRealm {
    private static final Logger log = LoggerFactory.getLogger(MyRealm.class);

    public MyRealm() {
        this.saltStyle = SaltStyle.NO_SALT;
    }


    /**
     * Principle colector
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("running doGetAuthorizationInfo(PrincipalCollection )");

        return null;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //identify account to log to
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
        log.debug("Processing Sent authentication data ");
        log.debug("Currently Authenticating");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        final String username = usernamePasswordToken.getUsername();
        char[] password = usernamePasswordToken.getPassword();

//        User user = daoAccess.getUserByUserId(username);
        if(username == null){
            log.error("Authentication failure ");
            log.error("Username is null");
            throw new AccountException("Null usernames are not allowed by this realm.");
        }else{
            int userId =Integer.parseInt(usernamePasswordToken.getUsername());
            User user = daoAccess.getUserByUserId(userId);
            log.info(usernamePasswordToken.getUsername());
            log.info(user.getPassword());
            System.out.println(password);

            if(user != null){
                if(user.getPassword().equals(new String(password))){
                    log.info("Passwords Match");
                    log.info("User Model =>"+ user);
                    Principal principal = new Principal(user);
                    log.info("user=>"+principal);
                    Map<String, String>  principalMap = new HashMap<String, String>();
                    principalMap.put("userId",""+user.getUserId());
                    principalMap.put("fullName",user.getUserName());
                    principalMap.put("roleName",user.getRoleName());
                    principalMap.put("roleId",""+user.getRoleId());

                    return new SimpleAuthenticationInfo(user.getUserId(),user.getPassword(),user.getUserName());

                }else {
                    log.error("User Does Not Exsist");
                    throw new UnknownAccountException();
                }
            }
        }

        //READ PASSWORD HASH AND SALT FROM DB
//        final User user =
        return null;
    }


    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id;
        private String loginName;
        private String name;
        private Map<String, Object> cacheMap;

        public Principal(User user) {
            this.id = ""+user.getUserId();
            this.loginName = ""+user.getUserId();
            this.name = user.getUserName();
        }

        public String getId() {
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
