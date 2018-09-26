package com.cdai.controllers.appController;

import build.dao.LogAccessDao;
import build.dao.UserDao;
import com.cdai.security.HashCredentials;
import com.cdai.security.UserUtils;
import jdk.nashorn.internal.objects.Global;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.config.IniSecurityManagerFactory;

import build.model.User;
import build.model.UserRole;
import build.model.UserRolePermission;

/**
 * Created by apple on 28/08/2017.
 */
@Controller
public class AuthenticationController {
    private static final Logger logger = Logger.getLogger(AppAdminController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/login.xml");


    /**
     * ADMIN DASHBOARD
     */
    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public ModelAndView adminPage(){

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Login Form - Database Authentication");
        model.addObject("message", "This page is for ROLE_ADMIN only!");
        model.setViewName("authentication/admin");
        return model;
    }

    /**
     * LOGIN PAGE RETURNED
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("authentication/login");


        return model;

    }

    /**
     * PROCESS LOGIN FORM
     */
//    @ResponseBody
    @ResponseBody
    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public  String loginProcess(HttpServletRequest request) {

        logger.info("@AuthenticationController..@loginProcess-->"+request.getParameter("userId"));
        logger.info("@AuthenticationController..@loginProcess-->"+request.getParameter("password"));
        logger.info("@AuthenticationController..@loginProcess-->"+request.getParameter("rememberMe"));


        LogAccessDao daoAccess = (LogAccessDao) applicationContext.getBean("LogAccessDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        Subject subject = SecurityUtils.getSubject();
        String forward ="login";
        if(!daoAccessUser.checkIfUserIsActive2(request.getParameter("userId"))){
            logger.error("@AuthenticationController..@loginProcess-->"+"User ID Does Note Exist !!");
            return "400";//"redirect:/login";
        }

        String securePass = new HashCredentials().securePass("cdai",request.getParameter("password"));

       if(request.getParameter("userId") != null){
           logger.debug("@AuthenticationController..@loginProcess --> "+"Requesting User => "+ request.getParameter("userId"));
           UsernamePasswordToken token = new UsernamePasswordToken(request.getParameter("userId"),securePass);
           try{
               subject.login(token);
               UserUtils.setUser();//
               Session session = subject.getSession();
               session.setAttribute("user", request.getParameter("userId"));
//               return "redirect:/admin/dashboard";
           }catch (UnknownAccountException uae) {
               logger.error("Incorrect User Password For: " + token.getPrincipal());
           } catch (IncorrectCredentialsException ice) {
               logger.error("Password for account " + token.getPrincipal() + " was incorrect!");
           } catch (LockedAccountException lae) {
               logger.error("The account for username " + token.getPrincipal() + " is locked.  " +
                       "Please contact your administrator to unlock it.");
           }
           // ... catch more exceptions here (maybe custom ones specific to your application?
           catch (AuthenticationException ae) {
               ae.printStackTrace();
           }
           catch (Exception e){
               e.printStackTrace();
           }
       }

            if(subject.isAuthenticated()){
                logger.info("User is authenticated!!!!!");

                long userId = UserUtils.getUser().getUserId();
                Session session = subject.getSession();

                daoAccess.createNewLogAccess(userId);

                session.setAttribute("userName",UserUtils.getUser().getUserName());
                session.setAttribute("userId",userId);
                session.setAttribute("role",UserUtils.getUser().getRoleId());
                Subject currentUser = SecurityUtils.getSubject();

                if(currentUser.hasRole("admin")){
                    session.setAttribute("roleName","admin");
                    logger.info("@AuthenticationController..@loginProcess-->"+" Can access admin functions");
                    return "200";//"redirect:/admin/dashboard";
                }
                else if(currentUser.hasRole("teacher")){
                    session.setAttribute("roleName","teacher");
                    logger.info("@AuthenticationController..@loginProcess-->"+" Can access admin  functions");
                    return "200";//;"redirect:/admin/dashboard";
                }
                else if(currentUser.hasRole("student")){
                    session.setAttribute("roleName","student");
                    logger.info("@AuthenticationController..@loginProcess-->"+" Can access student functions");
                    return "201";//"redirect:/student/home";
                }
                else {
                    logger.info("@AuthenticationController..@loginProcess-->"+" USER HAS NO ROLE ");
                    return "201";//"redirect:/student/home";
                }

            }
            else{
                logger.error("@AuthenticationController..@loginProcess-->"+"Incorrect User Password: User Not Authenticated");
                return "400";//"redirect:/login";
            }
    }

    //for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied() {

        ModelAndView model = new ModelAndView();
        model.setViewName("authentication/403");
        return model;

    }

    //LOGOUT USER
    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
    public String logOut(HttpServletRequest request,HttpServletResponse response) {
        Subject currentUser = SecurityUtils.getSubject();
        if (SecurityUtils.getSubject().getSession() != null){
            currentUser.logout();
            UserUtils.removeAllCache();
        }
        String forward = "redirect:/";
        return forward;
    }



}


//
//   Subject currentUser = SecurityUtils.getSubject();
//
//           if (!currentUser.isAuthenticated()) {
//           UsernamePasswordToken token = new UsernamePasswordToken(request.getParameter("userId"),request.getParameter("password"));
//           token.setRememberMe(true);
//           logger.info("CURRENT USER ==>"+currentUser);
//
//           try {
//           currentUser.login(token);
//           Session session = currentUser.getSession();
//           session.setAttribute("user", user.getUserName());
//           logger.info(" CURRENT USER ==> "+currentUser);
//
//
//           } catch (UnknownAccountException uae) {
//           logger.error("There is no user with username of " + token.getPrincipal());
//           } catch (IncorrectCredentialsException ice) {
//           logger.error("Password for account " + token.getPrincipal() + " was incorrect!");
//           } catch (LockedAccountException lae) {
//           logger.error("The account for username " + token.getPrincipal() + " is locked.  " +
//           "Please contact your administrator to unlock it.");
//           }
//           // ... catch more exceptions here (maybe custom ones specific to your application?
//           catch (AuthenticationException ae) {
//           //unexpected condition?  error?
//           }
//           catch (Exception e){
//           e.printStackTrace();
//           }