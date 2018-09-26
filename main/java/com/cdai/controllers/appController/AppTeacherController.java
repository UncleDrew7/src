package com.cdai.controllers.appController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by apple on 07/08/2017.
 */
@Controller
public class AppTeacherController {


    /**
     *  TEACHER DASHBOARD
     */
    @RequestMapping(value = "teacher/dashboard",method = RequestMethod.GET)
    public String teacherDashboard(Model model){
        return "Teacher_App/dashboard";
    }



    /**
     *  TEACHER MANAGE STUDENTS
     */
    @RequestMapping(value = "teacher/manage-students",method = RequestMethod.GET)
    public String teacherManageStudent(Model model){
        return "Teacher_App/manage_students";
    }



    /**
     *  TEACHER MANAGE COURSES
     */
    @RequestMapping(value = "teacher/manage-courses",method = RequestMethod.GET)
    public String teacherManageCourse(Model model){
        return "Teacher_App/manage_courses";
    }



    /**
     *  TEACHER MANAGE GRADES
     */
    @RequestMapping(value = "teacher/manage-grades",method = RequestMethod.GET)
    public String teacherManageGrades(Model model){
        return "Teacher_App/grades";
    }


    /**
     *  TEACHER REPORTS
     */
    @RequestMapping(value = "teacher/reports",method = RequestMethod.GET)
    public String teacherReports(Model model){
        return "Teacher_App/reports";
    }



    /**
     *  TEACHER PUBLISH
     */
    @RequestMapping(value = "teacher/publish",method = RequestMethod.GET)
    public String teacherPublish(Model model){
        return "Teacher_App/publish";
    }




    /**
     *  TECAHER ACTIVITY
     */
    @RequestMapping(value = "teacher/activity",method = RequestMethod.GET)
    public String teacherActivity(Model model){
        return "Teacher_App/activity";
    }


}
