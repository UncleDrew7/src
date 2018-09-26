package com.cdai.security;

//import com.rocaircraft.common.utils.SpringContextHolder;
//import com.rocaircraft.modules.entity.SysRole; // model
//import com.rocaircraft.modules.entity.SysUser; // model
//import com.rocaircraft.modules.sys.service.UserService;//service
//import com.rocaircraft.modules.sys.utils.UserUtils;
import build.dao.UserDao;
import build.model.Role;
import build.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.PrincipalMap;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 30/08/2017.
 */

@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {


    private static final Logger logger = Logger.getLogger(SystemAuthorizingRealm.class);

    private UserDao userDaoAccess;

    public String roleName = "Admin";
    public int roleId = 1;
    public String userName = "Lani";
    public String password = "kate";


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/login.xml");
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
        logger.debug("调用doGetAuthenticationInfo（）方法");

//        get logging in user name and password
//        String user = userName;
        User user = daoAccess.getUserByUserId(Long.parseLong(authenticationToken.getPrincipal().toString()));
        logger.debug("@SystemAuthorizingRealm - user:"+ user.getUserName());
        if(user != null){
            return new SimpleAuthenticationInfo(new Principal(user),user.getPassword(),getName());
        }else{
            throw new UnknownAccountException();
        }
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        logger.debug("@SystemAuthorizingRealm  >> doGetAuthorizationInfo（)");

        //get role
        Role role = (Role) UserUtils.getCache(UserUtils.CACHE_ROLE);

        if(role != null){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
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
            return null;
        }
    }



    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

    //userService getUserService
    public UserDao getUserDaoAccess(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        if(userDaoAccess == null){
            userDaoAccess = (UserDao) applicationContext.getBean("userDao");
        }

        return userDaoAccess;
    }

    public static class Principal implements  Serializable{

        private static final long serialVersionUID = 1L;

        private long id;
        private String loginName;
        private String name;
        private Map<String,Object> cacheMap;

        public Principal(User user){
            this.id = user.getUserId();
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

        public Map<String, Object> getCacheMap(){
            if(cacheMap == null){
                cacheMap = new HashMap<String,Object>();
            }
            return cacheMap;
        }
    }

//    handle the password

}
