package com.cdai.controllers.appController;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by apple on 23/09/2017.
 */
@Controller
public class ReportsController {
    /**
     *
     */
    private static final Logger logger = Logger.getLogger(ReportsController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");

    /**
     * TIMElINE  FULL LlST
     */
    @RequestMapping(value = "admin/timeline",method = RequestMethod.GET)
    public String viewNotifications(Model model){

        return "Admin_App/subPages/timelineList";
    }

}
