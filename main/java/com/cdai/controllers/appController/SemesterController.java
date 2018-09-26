package com.cdai.controllers.appController;

import build.dao.CoursesDao;
import build.dao.MajorDao;
import build.dao.SemesterDao;
import build.model.Semester;
import com.cdai.security.UserUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by apple on 18/09/2017.
 */
@Controller
public class SemesterController {
    /**
     *
     */
    private static final Logger logger = Logger.getLogger(SemesterController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");

    /**
     * RETURN ADD SEMESTER FORM
     */
    @RequestMapping(value = "/admin/semester/add",method = RequestMethod.GET)
    public String getAddSemesterForm(Model model){
        SemesterDao daoAccess = (SemesterDao) applicationContext.getBean("SemesterDao");

        model.addAttribute("lastAddList",daoAccess.getLastAddedSemestersList());

        return "Admin_App/subPages/addSemesterForm";
    }

    /**
     *  PROCESS ADD SEMESTER FORM
     */
    @ResponseBody
    @RequestMapping(value = "/admin/semester/add", method = RequestMethod.POST)
    public String addSemester(Model model, HttpServletRequest request){
        SemesterDao daoAccess = (SemesterDao) applicationContext.getBean("SemesterDao");
        long userId = UserUtils.getUser().getUserId();

        logger.info(request.getParameter("semesterCode"));
        logger.info(request.getParameter("startDate"));
        logger.info(request.getParameter("endDate"));
        logger.info(request.getParameter("createdBy"));

        String confirm = daoAccess.addSemester(new Semester(request.getParameter("semesterCode"),request.getParameter("startDate"),request.getParameter("endDate"),userId));

        logger.info(confirm);
        return confirm;
    }

    /**
     *  RETURN UPDATE SEMESTER FORM
     */
    @RequestMapping(value = "/admin/semester/{action}/{semesterId}", method = RequestMethod.GET)
    public String getUpdateSemesterForm(Model model, HttpServletRequest request,@PathVariable String action ,@PathVariable int semesterId){
        SemesterDao daoAccess = (SemesterDao) applicationContext.getBean("SemesterDao");

        model.addAttribute("action",action);
        model.addAttribute("semesterId",semesterId);
        model.addAttribute("lastUpdatedList",daoAccess.getLastEditedSemesterList());
        model.addAttribute("singleSemester",daoAccess.getSingleSemesterBySemesterId(semesterId));

        return "Admin_App/subPages/addSemesterForm";
    }

    /**
     *  PROCESS UPDATE SEMESTER FORM
     */
    @ResponseBody
    @RequestMapping(value = "/admin/semester/{action}/{semesterId}", method = RequestMethod.POST)
    public String updateSemester(Model model, HttpServletRequest request,@PathVariable String action ,@PathVariable int semesterId){
        SemesterDao daoAccess = (SemesterDao) applicationContext.getBean("SemesterDao");


        logger.info(request.getParameter(""+semesterId));
        logger.info(request.getParameter("semesterCode"));
        logger.info(request.getParameter("startDate"));
        logger.info(request.getParameter("endDate"));

        String confirm = daoAccess.updateSemester(new Semester(semesterId,request.getParameter("semesterCode"), request.getParameter("startDate"),request.getParameter("endDate")));
        logger.info(confirm);
        return confirm;
    }

    /**
     * RETURN SEMESTER COURSE LIST
     */
    @RequestMapping(value = "/admin/semester/list/{semesterId}",method = RequestMethod.GET)
    public String getAddSemesterForm(Model model, @PathVariable int semesterId,@RequestParam(value="major", required=false) Integer majorId){
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");
        SemesterDao daoAccess = (SemesterDao) applicationContext.getBean("SemesterDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");

        model.addAttribute("semesterId",semesterId);
        model.addAttribute("numberOfCourse",daoAccess.getNumberOfCoursesForSemester(semesterId));
        model.addAttribute("singleSemester",daoAccess.getSingleSemesterBySemesterId(semesterId));
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());

        if (majorId == null){
            model.addAttribute("semesterCourseList",daoAccessCourses.getSemesterCourseListBySemesterId(semesterId));
        }else {
            model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
            model.addAttribute("semesterCourseList",daoAccessCourses.getSemesterCourseListForSemesterByMajorId(semesterId,majorId));
        }

        return "Admin_App/subPages/semesterCoursesList";
    }

}
