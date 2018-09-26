package com.cdai.controllers.appController;

import build.dao.LogHistoryDao;
import build.dao.NewsNotificationsDao;
import build.dao.NewsNotificationsUploadsDao;
import build.model.LogHistory;
import build.model.NewsNotifications;
import build.model.NewsNotificationsUploads;
import com.cdai.security.UserUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by apple on 07/08/2017.
 */
@Controller
public class ActivityController {

    private static final Logger logger = Logger.getLogger(ActivityController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");


    /**
     * STUDENT ACTIVITIES STUDENT PAGE ROUTE
     */
    @RequestMapping(value = "student/activity",method = RequestMethod.GET)
    public String activity(Model model, HttpServletRequest request){

        LogHistoryDao daoAccessLog = (LogHistoryDao) applicationContext.getBean("LogHistoryDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        System.out.println("@IP;;;;;>>"+remoteAddr);
        daoAccessLog.logUser(new LogHistory("Student_App","Activity","/student/activity",currentUserId,"Student",remoteAddr,"Page Vist"));

        NewsNotificationsDao daoAccess = (NewsNotificationsDao) applicationContext.getBean("NewsNotificationsDao");

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();

        List<NewsNotifications> notificationList = daoAccess.getNewsFeed(10);// limit set default make dynamic

        model.addAttribute("userId",userId);
        model.addAttribute("notificationList",notificationList);

        return "Student_App/activity";
    }

    /**
     * ADD SINGLE NEWS NOTIFICATION
     */
    @ResponseBody
    @RequestMapping(value = "admin/news-notification/add",method = RequestMethod.POST)
    public String addNewsNotification(Model model, HttpServletRequest request){
        NewsNotificationsDao daoAccess = (NewsNotificationsDao) applicationContext.getBean("NewsNotificationsDao");
        long userId =  UserUtils.getUser().getUserId();

        logger.info(request.getParameter("createdBy"));
        logger.info(request.getParameter("title"));
        logger.info(request.getParameter("message"));

        NewsNotifications newsNotifications = new NewsNotifications();
        newsNotifications.setCreatedBy(userId);
        newsNotifications.setTitle(request.getParameter("title"));
        newsNotifications.setMessage(request.getParameter("message"));

        String  confirm = daoAccess.addNews(newsNotifications);

        return confirm;
    }

    /**
     * RETURN SINGLE NEWS NOTIFICATION
     */
    @ResponseBody
    @RequestMapping(value = "admin/single-notification/{newsNotificationId}",method = RequestMethod.POST)
    public NewsNotifications getNotificationDisplayDetails(Model model, @PathVariable int newsNotificationId){
        NewsNotificationsDao daoAccess = (NewsNotificationsDao) applicationContext.getBean("NewsNotificationsDao");

        NewsNotifications newsNotifications  = daoAccess.getSingleNewsNotificationByNewsNotificationId(newsNotificationId);

        return newsNotifications;
    }

    /**
     * EDIT  SINGLE NEWS NOTIFICATION
     */
    @ResponseBody
    @RequestMapping(value = "admin/news-notification/edit/{newsNotificationId}",method = RequestMethod.POST)
    public String editNewsNotification(Model model,@PathVariable int newsNotificationId, HttpServletRequest request){
        NewsNotificationsDao daoAccess = (NewsNotificationsDao) applicationContext.getBean("NewsNotificationsDao");

        logger.info(request.getParameter("title"));
        logger.info(request.getParameter("message"));

        String  confirm = daoAccess.editNewsNotification(new NewsNotifications(newsNotificationId,request.getParameter("title"),request.getParameter("message")));

        return confirm;
    }

    /**
     * DELETE SINGLE NEWS NOTIFICATION
     */
    @ResponseBody
    @RequestMapping(value = "admin/news-notification/delete/{newsNotificationId}",method = RequestMethod.POST)
    public String deleteNewsNotification(Model model,@PathVariable int newsNotificationId, HttpServletRequest request){
        NewsNotificationsDao daoAccess = (NewsNotificationsDao) applicationContext.getBean("NewsNotificationsDao");


        String confirm = daoAccess.deleteNewsNotification(newsNotificationId);

        return confirm;
    }

    /**
     * GET SINGLE NEWS NOTIFICATION UPLOADS
     */
    @ResponseBody
    @RequestMapping(value = "uploads/news-notifications/{newsNotificationId}",method = RequestMethod.POST)
    public List<NewsNotificationsUploads> getNewsNotificationUploads(Model model,@PathVariable int newsNotificationId, HttpServletRequest request){
        NewsNotificationsUploadsDao daoAccess = (NewsNotificationsUploadsDao) applicationContext.getBean("NewsNotificationsUploadsDao");

        return daoAccess.getNewsNotificationUploads(newsNotificationId);

    }



//    /**
//     *
//     */
//    @RequestMapping(value = "student/activity/notification",method = RequestMethod.GET)
//    public String displayNotificationDetails(Model model){
//        return "Student_App/subPages/event_details";
//    }
//



}
