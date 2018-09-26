package com.cdai.controllers.appController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by apple on 19/08/2017.
 */

@Controller
public class SystemLogsController {

    /**
     * View Detailed Logged Information
     */
    @RequestMapping(value = "admin/log/details",method = RequestMethod.GET)
    public String adminLogDetail(Model model){
        return "Admin_App/subPages/logDetails";
    }



}
