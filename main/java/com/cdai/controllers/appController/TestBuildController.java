package com.cdai.controllers.appController;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//-----------FORM IMPORTS ----------------------
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;
//-----------FORM IMPORTS ----------------------

//------------------formv3 tech -------------------///


import org.apache.log4j.Logger;

/**
 * Created by apple on 19/08/2017.
 */
@Controller
public class TestBuildController {
    private static final Logger logger = Logger.getLogger(AppAdminController.class);
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");



}
