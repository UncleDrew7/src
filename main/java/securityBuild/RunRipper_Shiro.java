package securityBuild;

//https://shiro.apache.org/tutorial.html

import build.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by apple on 16/09/2017.
 */
public class RunRipper_Shiro {

    private static final transient Logger logger = LoggerFactory.getLogger(RunRipper_Shiro.class);

    public static void main(String[] args){
        run();
    }

    public static void run(){
        String userName = "800";
        String password = "1234";
        //-->SET UP SECURITY MANGER
        Subject currentUser = setUpSecurityManagerIni();
        //-->LOGIN USER
        loginUser(userName,password,currentUser);

        if (currentUser.hasRole("TEACHER")) {
            logger.info("HELLO TEACHER !");
        } else {
            logger.info("UNKNOWN ROLE.");
        }

        //test a typed permission (not instance-level)
        if (currentUser.isPermitted("lightsaber:weild")) {
            logger.info("YOU HAVE PPERMISSION ");
        } else {
            logger.info("YOU DO NOT HAVE PERMISSION ");
        }



    }

    public static void loginUser(String userName,String password,Subject currentUser){

        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                logger.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                logger.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                logger.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        //say who they are:
        //print their identifying principal (in this case, a username):
        logger.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");
    }
    public static Subject setUpSecurityManagerIni(){
        logger.debug("# SETTING UP AND LOADING SECURITY MANAGER FROM INI #");
        //1.
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2.
        SecurityManager securityManager = factory.getInstance();
        //3.
        SecurityUtils.setSecurityManager(securityManager);
        logger.debug("# SECURITY MANAGER SET UP COMPLETED # !");
        // get the currently executing user:
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser;
    }
    public static void run2(){

        int userId = 800;
        String password = "1234";

        logger.info("Running Ripper Apache Shiro Discovery!!!");
        logger.info(""+userId);
        logger.info(password);

        //creating new user object
        User user = new User();

        //get the sent sps
        Subject subject = SecurityUtils.getSubject();
        if(userId != 0){
            logger.debug("Requesting User => "+ userId);
            UsernamePasswordToken token = new UsernamePasswordToken(""+userId,password);
            token.setRememberMe(true);
            try{
                //submit sps vai subject
                subject.login(token);
                Session session = subject.getSession();
                session.setAttribute("user", userId);
//               return "redirect:/admin/dashboard";
            }catch (UnknownAccountException uae) {
                logger.error("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                logger.error("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                logger.error("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        if(subject.isAuthenticated()){
            logger.info("User is authenticated!!!!!");

            Session session = subject.getSession();

            session.setAttribute("userName",subject.getPrincipal().toString());
            session.setAttribute("userId",subject.getPrincipal());
            session.setAttribute("role",2);

//                if(subject.hasRole("admin")){
//                    logger.info(user.getUserName()+" Can access admin functions");
//                }
//                if(subject.hasRole("teacher")){
//                    logger.info(user.getUserName()+" Can access admin  functions");
//                }
//                if (subject.hasRole("student")){
//                    logger.info(user.getUserName()+" Can access student functions");
//                }
            logger.info("Log in Complete and sucessfull");
        }
        else{
            logger.error("user was not authenticated!!!!!!");
            logger.error("loggin attempt unsucessful");
        }
    }
}


