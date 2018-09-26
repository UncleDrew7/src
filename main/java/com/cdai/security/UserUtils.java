package com.cdai.security;

import build.dao.RoleDao;
import build.model.User;
import build.model.UserRole;
import build.model.Role;
import  build.dao.UserDao;

import com.google.common.collect.Maps;
import com.mysql.jdbc.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by apple on 16/09/2017.
 */
public class UserUtils {
    private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
    private static  UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");

    public static final String CACHE_USER = "user";
    public static final String CACHE_ROLE = "role";
    public Map loginCache = new HashMap();

    public static void setUser(){
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setTimeout(1800000);
        MyRealm.Principal principal = (MyRealm.Principal) subject.getPrincipal();

        RoleDao daoAccessRole = (RoleDao) applicationContext.getBean("roleDao");
        if(principal != null){

            User user = daoAccess.getUserByUserId(principal.getId());
            logger.info("@UserUtils>>@setUser::#getUser = %"+user.getUserName()+"%");

            UserUtils.putCache(UserUtils.CACHE_USER,user);
            Role role = daoAccessRole.getUserRoleByUserId(user.getUserId());
            logger.info("@UserUtils>>@setUser::#getrole = %"+role.getRole_name()+"%");
            UserUtils.putCache(UserUtils.CACHE_ROLE,role);
            //get role by user id

        }
    }

    public static User getUser(){
        User user = (User)UserUtils.getCache(UserUtils.CACHE_USER);
        return user;
    }

    public static User getUser(boolean isRefresh){
        if(isRefresh){
            removeCache(CACHE_USER);
        }
        return getUser();
    }

    public static User getUserById(String id){
        if(StringUtils.isNullOrEmpty(id)){
            return (User) daoAccess.getUserByUserId(Long.parseLong(id));
        }else{
            return null;
        }
    }

    // ============== User Cache ==============
    public static Object getCache(String key){
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue){
        Object obj = getCacheMap().get(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value){
        getCacheMap().put(key,value);
    }

    public static void removeCache(String key){
        getCacheMap().remove(key);
    }
    public static void removeAllCache(){
        getCacheMap().clear();
    }

    public static Map<String,Object> getCacheMap(){
        Map<String,Object> map = Maps.newHashMap();
        try{
            Subject subject = SecurityUtils.getSubject();
            MyRealm.Principal principal = (MyRealm.Principal) subject.getPrincipal();
            return principal !=null ? principal.getCacheMap() : map;
        }catch(UnavailableSecurityManagerException e1){
            logger.warn(""+e1.getStackTrace());
        }catch (InvalidSessionException e){
            logger.warn(""+e.getStackTrace());
        }
        return map;
    }

}
