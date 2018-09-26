package com.cdai.controllers.appController;

import build.dao.EventsDao;
import build.model.Events;
import build.model.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdai.security.UserUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by apple on 18/09/2017.
 */
@Controller
public class EventController {
    /**
     *
     */
    private static final Logger logger = Logger.getLogger(EventController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");



    /**
     * ADMIN RETURN ADD EVENT FORM
     */
    @RequestMapping(value = "/admin/event/add",method = RequestMethod.GET)
    public String getAddEventForm(Model model){
        User user = UserUtils.getUser();
        long userId = user.getUserId();

        EventsDao daoAccessEvents = (EventsDao) applicationContext.getBean("EventsDao");

        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();

        model.addAttribute("upcomingEvents", daoAccessEvents.displayAdminUpcomingEvent(userId));
        model.addAttribute("lastAddedEvents", daoAccessEvents.getLastAddedEvents());

        return "Admin_App/subPages/addEventForm";
    }


    /**
     *  ADMIN PROCESS ADD EVENT FORM
     */
    @ResponseBody
    @RequestMapping(value = "/admin/event/add/", method = RequestMethod.POST)
    public String addEvent(Model model, HttpServletRequest request){
        EventsDao daoAccessEvents = (EventsDao) applicationContext.getBean("EventsDao");
        User user = UserUtils.getUser();
        long userId = user.getUserId();
        String confirm = "";

        logger.info(request.getParameter("eventName"));
        logger.info(request.getParameter("eventType"));
        logger.info(request.getParameter("eventDate"));
        logger.info(request.getParameter("eventDescription"));

        Events event = new Events(request.getParameter("eventType"),request.getParameter("eventName"),request.getParameter("eventDescription"),request.getParameter("eventDate"),userId);
        confirm = daoAccessEvents.addEvent(event);

        return confirm;
    }


    /**
     * ADMIN  Events FULL LlST
     */
    @RequestMapping(value = "admin/events/list",method = RequestMethod.GET)
    public String viewNotifications(Model model){
        EventsDao daoAccess = (EventsDao) applicationContext.getBean("EventsDao");

        model.addAttribute("eventsList", daoAccess.getAllSystemEvents());

        return "Admin_App/subPages/eventsListAdmin";
    }

    /**
     * STUDENT ALL EVENTS PAGE
     */
    @RequestMapping(value = "student/all-events/{filter}/{userId}",method = RequestMethod.GET)
    public String displayNotificationDetails(Model model, @PathVariable int filter,@PathVariable long userId){
        EventsDao daoAccess = (EventsDao) applicationContext.getBean("EventsDao");

        model.addAttribute("filter",filter);
        model.addAttribute("userId",userId);
        model.addAttribute("countOfUpcomingEvents",daoAccess.getCountOfMostUpcomingUserEvents(userId));
        switch (filter){
            case 1:
                //allUpcomingEventsListForUser
                model.addAttribute("eventsList",daoAccess.getMostUpcomingUserEvents(userId));
                break;
            case 2:
                //systemEventsListMarkedAll
                model.addAttribute("eventsList",daoAccess.getAllSystemEventsListForAll());
                break;
            case 3:
                //courseEventsListforUser
                model.addAttribute("eventsList",daoAccess.getAllCoursesEventsListForUser(userId));
                break;
            case 4:
                //getAllUpcomingEventsAndHistoryForUser
                model.addAttribute("eventsList",daoAccess.getAllEventsAndHistoryForUser(userId));
                break;
            default:
                model.addAttribute("eventsList",daoAccess.getMostUpcomingUserEvents(userId));
        }

        return "Student_App/subPages/event_details";
    }

    /**
     * ADIM MAIN EDIT EVENT
     */
    @RequestMapping(value = "admin/events/edit/{eventId}/{nav}",method = RequestMethod.GET)
    public String editEvents(Model model,@PathVariable int eventId,@PathVariable int nav){
        EventsDao daoAccess = (EventsDao) applicationContext.getBean("EventsDao");

        model.addAttribute("event", daoAccess.getEventDetailsByEventId(eventId));
        model.addAttribute("action","edit");
        model.addAttribute("nav",nav);
        model.addAttribute("eventId",eventId);

        return "Admin_App/subPages/addEventForm";
    }
}
