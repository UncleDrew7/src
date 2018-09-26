package com.cdai.controllers.appController;

import build.dao.*;
import build.model.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdai.models.tempModels.ExcelCourse;
import com.cdai.models.tempModels.ExcelStudent;
import com.cdai.models.tempModels.ExcelTeacher;
import com.cdai.security.UserUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.cdai.models.testModels.Book;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.log4j.Logger;
import java.util.List;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by apple on 07/08/2017.
 */
@Controller
public class AppAdminController {
    private static final Logger logger = Logger.getLogger(AppAdminController.class);
    private static final  ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");

    /**
     * ADMIN DASHBOARD
     */
    @RequestMapping(value = "admin/dashboard",method = RequestMethod.GET)
    public String adminDashBoard(Model model, HttpServletRequest request){

        LogHistoryDao daoAccessLog = (LogHistoryDao) applicationContext.getBean("LogHistoryDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        System.out.println("@IP;;;;;>>"+remoteAddr);
        daoAccessLog.logUser(new LogHistory("Admin_App","Dashboard","/admin/dashboard",currentUserId,"Teacher",remoteAddr,"Page Vist"));


        //User user = UserUtils.getUser();
        //int userId = user.getUserId();
        long userId = currentUserId;
        int limit = 5;
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();

        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");
        GradeItemsDao daoAccessGradeItem = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        LogHistoryDao daoAccessLogHistory = (LogHistoryDao) applicationContext.getBean("LogHistoryDao");
        EventsDao daoAccessEvents = (EventsDao) applicationContext.getBean("EventsDao");
        SemesterDao daoAccessSemester = (SemesterDao) applicationContext.getBean("SemesterDao");
        ClassDao daoAccessClass = (ClassDao) applicationContext.getBean("ClassDao");



        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("UserProfilePicUri",daoAccessUser.getUserProfilePic(currentUserId));


        model.addAttribute("totalStudents",daoAccessUser.getTotalNumberOfSystemStudents());
        model.addAttribute("totalTeachers",daoAccessUser.getTotalNumberOfSystemTeachers());
        model.addAttribute("totalChineseDegreeStudents",daoAccessUser.getTotalNumberOfSystemGeneralDegreeStudents());
        model.addAttribute("totalDoubleDegreeStudents",daoAccessUser.getTotalNumberOfSystemDoubleDegreeStudents());
        model.addAttribute("totalActiveCourses",daoAccessCourse.getTotalActiveCoursesCount());
        model.addAttribute("totalActiveExams",daoAccessGradeItem.adminGetAllCurrentlyActiveExamsCount());
        //T/A TOTAL EXAMS

        if(currentUserRole.equals("admin")){

            model.addAttribute("totalSystemAdmins",daoAccessUser.getTotalSystemAdminsCount());
            model.addAttribute("totalUsers",daoAccessUser.getTotalNumberOfSystemUsers());
            model.addAttribute("totalExamEnrollments",daoAccessGradeItem.getTotalExamEnrolmentsCount());
            model.addAttribute("totalCourseEnrollments",daoAccessCourse.getTotalSystemsChildCoursesEnrollmentsCount());
            model.addAttribute("parentCourseCount",daoAccessCourse.getTotalParentCoursesCount());
            model.addAttribute("childCoursesCount",daoAccessCourse.getTotalChildCoursesCount());
            model.addAttribute("totalSystemMajors",daoAccessCourse.getTotalSystemMajorCount());
            model.addAttribute("totalSystemSemesterCount",daoAccessSemester.getTotalSemesterCount());
            model.addAttribute("totalClasses",daoAccessClass.getActiveSystemClassesCount());
            model.addAttribute("totalSystemParentExams",daoAccessGradeItem.adminGetTotalSystemCreatedExamsCount());
            model.addAttribute("totalSystemClearanceExams",daoAccessGradeItem.adminGetTotalSystemCreatedExamsCount());
            model.addAttribute("totalActiveClearanceExamCount",daoAccessGradeItem.getTotalActiveClearanceExamCount());
            model.addAttribute("totalClearanceExamCount",daoAccessGradeItem.getTotalClearanceExamCount());
            model.addAttribute("totalStudentsEnrolledInClearanceExams",daoAccessGradeItem.getTotalStudentsEnrolledInClearanceExams());

            model.addAttribute("systemLatestCourseEnrollmentNotifications",daoAccessUser.getSystemsLatestStudentCourseEnrollmentNotificationsList(5));
            model.addAttribute("systemsLatestExamEnrollmentNotifications",daoAccessUser.getSystemsLatestExamEnrollmentNotificationsAdmin(5));
            model.addAttribute("degreeTypeChangeRequests",daoAccessUser.getAllStudentDegreeTypeChangeRequest());

        }else{
            model.addAttribute("examEnrolmentRequest",daoAccessGradeItem.adminGetAllExamEnrollmentRequestCountsByTeacherId(userId));
            model.addAttribute("courseEnrolmentRequests",daoAccessCourse.getAllCourseEnrollmentRequestsCountByTeacherId(userId));
            //get per teacher active requests
            model.addAttribute("systemLatestCourseEnrollmentNotifications",daoAccessUser.getSystemsLatestCourseEnrollmentNotificationsForTeacher(userId,5));
            model.addAttribute("systemsLatestExamEnrollmentNotifications",daoAccessUser.getSystemsLatestExamEnrollmentNotificationsTeacher(userId,5));
            model.addAttribute("myTotalExams",daoAccessGradeItem.getAllTeacherExamsCount(currentUserId));
            model.addAttribute("myTotalCourses",daoAccessCourse.getAllTeacherCoursesCount(currentUserId));
            model.addAttribute("myActiveCourses",daoAccessCourse.getActiveCourseCountForTeacher(currentUserId));
            model.addAttribute("myTotalActiveExams",daoAccessGradeItem.getActiveExamsCountForTeacher(currentUserId));
            model.addAttribute("myTotalExamEnrollments",daoAccessGradeItem.getTotalExamEnrollmentsForTeacher(currentUserId));
            model.addAttribute("myTotalCourseEnrollments",daoAccessCourse.getTotalCourseEnrollmentCountForTeacher(currentUserId));
            model.addAttribute("totalActiveClearanceExamCount",daoAccessGradeItem.getTotalActiveClearanceExamCountForTeacher(currentUserId));
            model.addAttribute("totalClearanceExamCount",daoAccessGradeItem.getTotalClearanceExamCountForTeacher(currentUserId));
            model.addAttribute("totalStudentsEnrolledInClearanceExams",daoAccessGradeItem.getTotalStudentsEnrolledInClearanceExamsForTeacher(currentUserId));
        }


        model.addAttribute("upcomingEvents", daoAccessEvents.displayAdminUpcomingEvent(userId));
        model.addAttribute("calendarEvents", JSON.toJSONString(daoAccessEvents.displayAdminCalenderEvent(userId)));


        return "Admin_App/dashboard";
    }



    /**
     * ADMIN MANAGE USERS
     */
    @RequestMapping(value = "admin/manage-users/{object}",method = RequestMethod.GET)
    public String adminManageUsers(Model model,HttpServletRequest request,@PathVariable String object,
                                   @RequestParam(value="major", required=false) Integer majorId,
                                   @RequestParam(value="filter", required=false) Integer roleFilterId,
                                   @RequestParam(value="intake", required=false) String intake){

        LogHistoryDao daoAccessLog = (LogHistoryDao) applicationContext.getBean("LogHistoryDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        System.out.println("@IP;;;;;>>"+remoteAddr);
        daoAccessLog.logUser(new LogHistory("Admin_App","Manage Users","/admin/manage-users",currentUserId,"Teacher",remoteAddr,"Page Vist"));


        ClassDao daoAccessClass = (ClassDao) applicationContext.getBean("ClassDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        User user = UserUtils.getUser();

        long userId = user.getUserId();

        if(roleFilterId == null){
            roleFilterId = 4;
        }

        List<String> futureIntakeList = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        Calendar startYear = Calendar.getInstance();
        Calendar midYear = Calendar.getInstance();

        startYear.set(year,Calendar.JANUARY,1);
        midYear.set(year,Calendar.JULY,30);

       /* for (int i=1;i<=2;i++){
            int tempYear = year + i;
            futureIntakeList.add(""+tempYear+"-1");
            futureIntakeList.add(""+tempYear+"-2");
        }*/

        for (int i=1;i<=50;i++){
            int tempYear = 2013 + i;
            futureIntakeList.add(""+tempYear);
          //  futureIntakeList.add(""+tempYear+"-2");
        }

        logger.info("Role Filter ID ="+roleFilterId);
        logger.info("major ID = "+majorId);
        logger.info("Object Type = "+object);
        logger.info("Intake = "+intake);

        model.addAttribute("roleFilterId",roleFilterId);
        model.addAttribute("majorId",majorId);
        model.addAttribute("intake",intake);
        model.addAttribute("object",object);
        model.addAttribute("currentUserRoleP" ,currentUserRole);

        model.addAttribute("totalUsers",daoAccessUser.getTotalNumberOfSystemUsers());
        model.addAttribute("toatlStudents",daoAccessUser.getTotalNumberOfSystemStudents());
        model.addAttribute("totalTeachers",daoAccessUser.getTotalNumberOfSystemTeachers());
        model.addAttribute("totalClasses",daoAccessClass.getActiveSystemClassesCount());
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("futureIntakeList",futureIntakeList);
        model.addAttribute("intakeSelectList", daoAccessUser.getCurrentUserIntakesList());
        model.addAttribute("classSelectList",daoAccessClass.getAllClassesNamesSelectList());
        model.addAttribute("studentIntakeList",daoAccessUser.getCurrentUserIntakesList());


        if(object.equals("users")){
            if(currentUserRole.equals("admin")){
                if (majorId == null){
                    //without major
                    switch (roleFilterId){
                        case 1:
                            model.addAttribute("userTableList",daoAccessUser.getSystemUsersWithProfileDetails());
                            break;
                        case 2:
                            model.addAttribute("userTableList",daoAccessUser.getSystemTeachersAndAdminsUserData());
                            break;
                        case 3:
                        {

                            if(intake == null || intake.equals("")){
                                model.addAttribute("userTableList",daoAccessUser.getSystemStudentsUserData());
                            }else {
                                model.addAttribute("userTableList",daoAccessUser.getSystemStudentUserDataFilteredByIntake(intake));
                            }
                            break;
                        }
                        default:
                            model.addAttribute("userTableList",daoAccessUser.getSystemUsersWithProfileDetails());
                            break;
                    }
                }else {
                    model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));

                    if(intake == null ){
                        model.addAttribute("userTableList",daoAccessUser.getSystemStudentUserDataFilteredByMajor(majorId));
                    }else {
                        model.addAttribute("userTableList",daoAccessUser.getSystemStudentUserDataFilteredByMajorAndIntake(majorId,intake));
                    }

                }


            }else{
                if (majorId == null){
                    switch (roleFilterId){
                        case 1:
                            model.addAttribute("userTableList",daoAccessUser.getSystemUsersWithProfileDetailsForTeacher());
                            break;
                        case 2:
                            model.addAttribute("userTableList",daoAccessUser.getSystemTeachersUserData());
                            break;
                        case 3:
                        {
                            if(intake == null ){
                                model.addAttribute("userTableList",daoAccessUser.getSystemStudentsUserData());
                            }else {
                                model.addAttribute("userTableList",daoAccessUser.getSystemStudentUserDataFilteredByIntake(intake));
                            }
                            break;
                        }

                        default:
                            model.addAttribute("userTableList",daoAccessUser.getSystemUsersWithProfileDetailsForTeacher());
                            break;
                    }
                }else{
                    model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));

                    if(intake == null ){
                        model.addAttribute("userTableList",daoAccessUser.getSystemStudentUserDataFilteredByMajor(majorId));
                    }else {
                        model.addAttribute("userTableList",daoAccessUser.getSystemStudentUserDataFilteredByMajorAndIntake(majorId,intake));
                    }
                }


            }
        }
        else if (object.equals("classes")){
            model.addAttribute("classIntakeList",daoAccessUser.getCurrentClassIntakeList());

            if (majorId == null){
                if(intake == null){
                    model.addAttribute("classesTableList",daoAccessClass.getAllSystemClasses());
                }else {
                    //get classes with no major but with intake
                    model.addAttribute("classesTableList",daoAccessClass.getAllSystemClassesFilteredByIntake(intake));
                }
            }else{
                model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
                if(intake == null ){
                    // get classes filtered by major with no intake
                    model.addAttribute("classesTableList",daoAccessClass.getAllSystemClassesFilteredByMajor(majorId));
                }else {
                    // get class filterd by major and intake
                    model.addAttribute("classesTableList",daoAccessClass.getAllSystemClassesFilteredByMajorAndIntake(majorId,intake));
                }

            }
        }else{
            model.addAttribute("pageInfoCode","500");
            model.addAttribute("pageInfo","No Object Selected");
        }

        return "Admin_App/manage_users";
    }

    /**
     * ADMIN MANAGE COURSES
     */
    @RequestMapping(value = "admin/manage-courses2/{object}",method = RequestMethod.GET)
    public String adminManageCourses2(Model model, HttpServletRequest request,@PathVariable String object,
                                     @RequestParam(value="filterId", required=false) Integer filterId,
                                     @RequestParam(value="major", required=false) Integer majorId){


        LogHistoryDao daoAccessLog = (LogHistoryDao) applicationContext.getBean("LogHistoryDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        SemesterDao daoAccessSemester = (SemesterDao) applicationContext.getBean("SemesterDao");
        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");

        System.out.println("Filter = "+ filterId);
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        System.out.println("@IP;;;;;>>"+remoteAddr);
        daoAccessLog.logUser(new LogHistory("Admin_App","Manage Courses","/admin/manage-courses",currentUserId,"Teacher",remoteAddr,"Page Vist"));

        model.addAttribute("currentUserRole",currentUserRole);

        model.addAttribute("semesterSelectList",daoAccessSemester.getSystemSemesterList());
        model.addAttribute("object",object);
        model.addAttribute("majorId",majorId);
        model.addAttribute("semesterTableList",daoAccessSemester.getAllSemesterTableDisplayData());
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("parentCourseCount",daoAccessCourse.getTotalParentCoursesCount());
        model.addAttribute("childCouresCount",daoAccessCourse.getTotalChildCoursesCount());

        switch (currentUserRole){
            case "admin":
            {
                model.addAttribute("totalSemesters",daoAccessSemester.getTotalSemesterCount());
                model.addAttribute("totalCourses",daoAccessCourse.getTotalActiveCoursesCount());


                model.addAttribute("majorTableList",daoAccessMajor.getAllMajors());
                if (majorId == null){
                    model.addAttribute("parentCourseList",daoAccessParentCourse.getAllParentCourses());
                }else{
                    model.addAttribute("parentCourseList",daoAccessParentCourse.getAllParentCoursesByMajorId(majorId));
                }

                if (majorId == null){
                    if (filterId == null){
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCourses2());
                    }
                    else{
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getFilteredBySemesterChildCourse2(filterId));
                    }
                }else {
                    model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
                    if (filterId == null){
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCoursesForSpecificMajorId2(majorId));
                    }
                    else{
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getFilteredBySemesterChildCoursesForSpecificMajorId2(filterId,majorId));
                    }
                }

                break;
            }
            case "teacher":
            {
                //GET ACTIVE TEACHER COURSE COUNT
                model.addAttribute("activeTeacherCoursesCount",daoAccessCourse.getActiveCourseCountForTeacher(currentUserId));
                model.addAttribute("totalTeacherCoursesCount",daoAccessCourse.getTotalCourseCountForTeacher(currentUserId));
                model.addAttribute("courseRequests",daoAccessCourse.getAllCourseEnrollmentRequestsCountByTeacherId(currentUserId));

                model.addAttribute("majorTableList",daoAccessMajor.getAllMajors());
                if (majorId == null){
                    model.addAttribute("parentCourseList",daoAccessParentCourse.getAllParentCourses());
                }else{
                    model.addAttribute("parentCourseList",daoAccessParentCourse.getAllParentCoursesByMajorId(majorId));
                }

                if (majorId == null){
                    if (filterId == null){
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCoursesForTeacher2(currentUserId));
                    }
                    else{
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCoursesForTeacherFilteredBySemester2(filterId,currentUserId));
                    }
                }else {
                    model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
                    if (filterId == null){
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCoursesForTeacherByMajor2(majorId,currentUserId));
                    }
                    else{
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCoursesForTeacherFilteredBySemesterAndMajor(filterId,majorId,currentUserId));
                    }
                }

                break;
            }
            default:
                break;
        }

        return "Admin_App/manage_courses2";
    }

    /**
     * ADMIN MANAGE COURSES
     */
    @RequestMapping(value = "admin/manage-courses/{object}",method = RequestMethod.GET)
    public String adminManageCourses(Model model, HttpServletRequest request,@PathVariable String object,
                                     @RequestParam(value="filterId", required=false) Integer filterId,
                                     @RequestParam(value="major", required=false) Integer majorId){


        LogHistoryDao daoAccessLog = (LogHistoryDao) applicationContext.getBean("LogHistoryDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        SemesterDao daoAccessSemester = (SemesterDao) applicationContext.getBean("SemesterDao");
        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");

        System.out.println("Filter = "+ filterId);
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        System.out.println("@IP;;;;;>>"+remoteAddr);
        daoAccessLog.logUser(new LogHistory("Admin_App","Manage Courses","/admin/manage-courses",currentUserId,"Teacher",remoteAddr,"Page Vist"));

        //User user = UserUtils.getUser();
        //model.addAttribute("user",user);

        model.addAttribute("currentUserRole",currentUserRole);

        model.addAttribute("semesterSelectList",daoAccessSemester.getSystemSemesterList());
        model.addAttribute("object",object);
        model.addAttribute("majorId",majorId);
        model.addAttribute("semesterTableList",daoAccessSemester.getAllSemesterTableDisplayData());
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("parentCourseCount",daoAccessCourse.getTotalParentCoursesCount());
        model.addAttribute("childCouresCount",daoAccessCourse.getTotalChildCoursesCount());
//        model.addAttribute("filterId",filterId);

        switch (currentUserRole){
            case "admin":
            {
                model.addAttribute("totalSemesters",daoAccessSemester.getTotalSemesterCount());
                model.addAttribute("totalCourses",daoAccessCourse.getTotalActiveCoursesCount());


                model.addAttribute("majorTableList",daoAccessMajor.getAllMajors());
                if (majorId == null){
                    model.addAttribute("parentCourseList",daoAccessParentCourse.getAllParentCourses());
                }else{
                    model.addAttribute("parentCourseList",daoAccessParentCourse.getAllParentCoursesByMajorId(majorId));
                }

                if (majorId == null){
                    if (filterId == null){
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCourses());
                    }
                    else{
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getFilteredBySemesterChildCourse(filterId));
                    }
                }else {
                    model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
                    if (filterId == null){
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCoursesForSpecificMajorId(majorId));
                    }
                    else{
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getFilteredBySemesterChildCoursesForSpecificMajorId(filterId,majorId));
                    }
                }




//
//                if(object.equals("majors")){
//                    model.addAttribute("parentCourseList",daoAccessParentCourse.getAllParentCourses());
//                    model.addAttribute("majorTableList",daoAccessMajor.getAllMajors());
//                }
//                else if(object.equals("courses")){
//                    if (filterId == null){
//                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCourses());
//                    }
//                    else{
//                        model.addAttribute("childCourseTable",daoAccessChildCourses.getFilteredBySemesterChildCourse(filterId));
//                    }
//                    //model.addAttribute("courseTableList",daoAccessCourse.getAllCoursesTableDisplayData());
//                }
//                else if(object.equals("semesters")){
//                    model.addAttribute("semesterTableList",daoAccessSemester.getAllSemesterTableDisplayData());
//                    model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
//                }else {
//                    //return major
//                }

                break;
            }
            case "teacher":
            {
                //GET ACTIVE TEACHER COURSE COUNT
                model.addAttribute("activeTeacherCoursesCount",daoAccessCourse.getActiveCourseCountForTeacher(currentUserId));
                model.addAttribute("totalTeacherCoursesCount",daoAccessCourse.getTotalCourseCountForTeacher(currentUserId));
                model.addAttribute("courseRequests",daoAccessCourse.getAllCourseEnrollmentRequestsCountByTeacherId(currentUserId));

                model.addAttribute("majorTableList",daoAccessMajor.getAllMajors());
                if (majorId == null){
                    model.addAttribute("parentCourseList",daoAccessParentCourse.getAllParentCourses());
                }else{
                    model.addAttribute("parentCourseList",daoAccessParentCourse.getAllParentCoursesByMajorId(majorId));
                }

                if (majorId == null){
                    if (filterId == null){
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCoursesForTeacher(currentUserId));
                    }
                    else{
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCoursesForTeacherFilteredBySemester(filterId,currentUserId));
                    }
                }else {
                    model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
                    if (filterId == null){
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCoursesForTeacherByMajor(majorId,currentUserId));
                    }
                    else{
                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCoursesForTeacherFilteredBySemesterAndMajor(filterId,majorId,currentUserId));
                    }
                }
//                if(object.equals("majors")){
//
//                }
//                else if(object.equals("courses")){
//                    //model.addAttribute("courseTableList",daoAccessCourse.getAllCoursesTableDisplayDataByTeacherId(currentUserId));
//                    if (filterId == null){
//                        model.addAttribute("childCourseTable",daoAccessChildCourses.getAllChildCourses());
//                    }
//                    else{
//                        model.addAttribute("childCourseTable",daoAccessChildCourses.getFilteredBySemesterChildCourse(filterId));
//                    }
//                }
//               else {
//                    //return major
//                }

                break;
            }
            default:
//                //GET ACTIVE TEACHER COURSE COUNT
//                model.addAttribute("activeTeacherCoursesCount",daoAccessCourse.getActiveCourseCountForTeacher(currentUserId));
//                //GET GET ALL TECAHER COURSE COUNT
//                model.addAttribute("totalTeacherCoursesCount",daoAccessCourse.getTotalCourseCountForTeacher(currentUserId));
//                model.addAttribute("courseTableList",daoAccessCourse.getAllCoursesTableDisplayDataByTeacherId(currentUserId));
                break;
        }
        //GET SPECIFIC TEACHER COURSE


        // GET COURSE REQUEST COUNT

        //GET ALL SYSTEMS COURSES


        return "Admin_App/manage_courses";
    }

    /**
     * Return Main Exam Page View
     */
    @RequestMapping(value = "admin/manage-exams/{examType}",method = RequestMethod.GET)
    public String manageExams(Model model, HttpServletRequest request,@PathVariable String examType,
                              @RequestParam(value="semester", required=false) Integer semesterId,
//                              @RequestParam(value="examType", required=false) String examType,
                              @RequestParam(value="major", required=false) Integer majorId){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        LogHistoryDao daoAccessLog = (LogHistoryDao) applicationContext.getBean("LogHistoryDao");
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        System.out.println("@IP;;;;;>>"+remoteAddr);
        daoAccessLog.logUser(new LogHistory("Admin_App","Manage Exams","/admin/manage-exams",currentUserId,"Teacher",remoteAddr,"Page Vist"));



        User user = UserUtils.getUser();

        model.addAttribute("user",user);
        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("semesterId",semesterId);
        model.addAttribute("examType",examType);
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("semesterList",daoAccessCourses.getSemesterList());
        model.addAttribute("semesterListMore",daoAccessCourses.getSemesterListMore());
        model.addAttribute("majorId",majorId);


        switch (currentUserRole){
            case "admin":
            {
                model.addAttribute("allTeacherCourseExamsCount",daoAccessExam.adminGetTotalSystemCreatedExamsCount());
                model.addAttribute("totalActiveExams",daoAccessExam.adminGetAllCurrentlyActiveExamsCount());
                model.addAttribute("totalEnrolmentRequest",daoAccessExam.adminGetAllExamEnrollmentRequestCounts());
//                model.addAttribute("examEnrolmentRequestByGradeItemList",daoAccess.adminGetExamEnrolmentRequestByGradeItem());

               if( examType.equals("exams") ){
                   if (majorId == null){
                       if(semesterId == null){
                           //show all
                        //   model.addAttribute("examTableList",daoAccess.adminGetCourseExamDataList());
                           model.addAttribute("examTableList",daoAccessExam.adminGetAllExamDataList());

                       }else{
                           model.addAttribute("examTableList",daoAccessExam.adminGetCourseExamDataListBySemester(semesterId));
                       }
                   }else{
                       model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
                       if(semesterId == null){
                           //show all
                           model.addAttribute("examTableList",daoAccessExam.adminGetCourseExamDataListByMajorId(majorId));//

                       }else{
                           model.addAttribute("examTableList",daoAccessExam.adminGetCourseExamDataListBySemesterAndMajorId(semesterId,majorId));
                       }
                   }
               }else{
                  //show clearnce exams
                   if (majorId == null){
                       if(semesterId == null){
                           //show all
                           model.addAttribute("clearanceExamTableList",daoAccessExam.getAllClearanceExams());
                       }else{
                           model.addAttribute("clearanceExamTableList",daoAccessExam.getAllClearanceExamsBySemesterId(semesterId));
                       }
                   }else{
                       model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
                       if(semesterId == null){
                           //show all
                           model.addAttribute("clearanceExamTableList",daoAccessExam.getAllClearanceExamsByMajorId(majorId));
                       }else{
                           model.addAttribute("clearanceExamTableList",daoAccessExam.getAllClearanceExamBySemesterIdAndMajorId(semesterId,majorId));
                       }
                   }
               }
                //---------------------        -------------       -------------
                break;
            }
            case "teacher":
            {
                model.addAttribute("totalActiveExams",daoAccessExam.getActiveTeacherCourseExamCount(currentUserId));
                model.addAttribute("allTeacherCourseExamsCount",daoAccessExam.getTotalTeacherCourseExamCount(currentUserId));
                model.addAttribute("totalEnrolmentRequest",daoAccessExam.adminGetAllExamEnrollmentRequestCountsByTeacherId(currentUserId));
             //   model.addAttribute("examEnrolmentRequestByGradeItemList",daoAccessExam.teacherGetExamEnrolmentRequestByGradeItem(currentUserId));

                if( examType.equals("exams") ){
                    if (majorId == null){
                        if(semesterId == null){
                            //show all
                            model.addAttribute("examTableList",daoAccessExam.teacherGetCourseExamDataList(currentUserId));

                        }else{
                            model.addAttribute("examTableList",daoAccessExam.teacherGetCourseExamDataListBySemester(semesterId,currentUserId));
                        }
                    }else{
                        model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
                        if(semesterId == null){
                            //show all
                            model.addAttribute("examTableList",daoAccessExam.teacherGetCourseExamDataListByMajorId(majorId,currentUserId));//

                        }else{
                            model.addAttribute("examTableList",daoAccessExam.teacherGetCourseExamDataListBySemesterAndMajorId(semesterId,majorId,currentUserId));
                        }
                    }
                }else{
                    //show clearnce exams
                    if (majorId == null){
                        if(semesterId == null){
                            //show all
                            model.addAttribute("clearanceExamTableList",daoAccessExam.getAllClearanceExams());
                        }else{
                            model.addAttribute("clearanceExamTableList",daoAccessExam.getAllClearanceExamsBySemesterId(semesterId));
                        }
                    }else{
                        model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
                        if(semesterId == null){
                            //show all
                            model.addAttribute("clearanceExamTableList",daoAccessExam.getAllClearanceExamsByMajorId(majorId));
                        }else{
                            model.addAttribute("clearanceExamTableList",daoAccessExam.getAllClearanceExamBySemesterIdAndMajorId(semesterId,majorId));
                        }
                    }
                }

                break;
            }
            default:
                //nothing

                break;
        }

        //GET TEACHER EXAM COUNT AND COURSE COUNT
        //GET TEACHER COURSE AND EXAM COUNT
        //GET TEACHE EXAM ENROLLMENT COUNT


        return "Admin_App/manage_exam";
    }

    /**
     * ADMIN GRADES
     */
    @RequestMapping(value = "admin/grades2",method = RequestMethod.GET)
    public String adminManageGrades2(Model model, HttpServletRequest request,
                                    @RequestParam(value="semester", required=false) Integer semesterId,
                                    @RequestParam(value="major", required=false) Integer majorId,
                                    @RequestParam(value="intake", required=false) Integer intakeId){

        LogHistoryDao daoAccessLog = (LogHistoryDao) applicationContext.getBean("LogHistoryDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        ClassDao daoAccessClass = (ClassDao) applicationContext.getBean("ClassDao");
        CourseEnrolmentDao daoAccessCourseEnroll = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");
        Subject currentUser = SecurityUtils.getSubject();

        Session session = currentUser.getSession();
        long currentUserId = (long) session.getAttribute("userId") ;

        String currentUserRole = (String) session.getAttribute("roleName");
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        System.out.println("@IP;;;;;>>"+remoteAddr);
        daoAccessLog.logUser(new LogHistory("Admin_App","Manage Grades","/admin/grades",currentUserId,"Teacher",remoteAddr,"Page Vist"));


        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");


        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("intakeSelectList", daoAccessClass.getAllIntakeNamesSelectList());

        model.addAttribute("isChecked", "checked");

        model.addAttribute("semesterId",semesterId);
        model.addAttribute("semesterList",daoAccess.getSemesterList());
        model.addAttribute("majorId",majorId);
        if ( majorId != null ){
            model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
        }

        // model.addAttribute("intakeId",intakeId);
        model.addAttribute("intakeId",intakeId);

        switch (currentUserRole){
            case "admin":
            {
//                model.addAttribute("gradeTableList", daoAccess. adminGetCoursesWithEnrolledStudentsCountList());
//                break;
                if (majorId == null && intakeId == null && semesterId == null ){
                    model.addAttribute("retakeCourseList", daoAccessCourseEnroll.getAllRetakeCourseGradesList());
                    break;
                }

                if ( semesterId != null ){
                    if ( majorId != null )
                        model.addAttribute("retakeCourseList", daoAccessCourseEnroll.getAllRetakeCourseGradesListBySemesterAndMajor(semesterId, majorId));
                    else
                        model.addAttribute("retakeCourseList", daoAccessCourseEnroll.getAllRetakeCourseGradesListBySemester(semesterId));
                    break;
                }

            }
            case "teacher":
            {
//                if (majorId == null){
//                    if(semesterId == null){
//                        //show all
//                        model.addAttribute("gradeTableList",daoAccess.teacherGetAllCourseAndGradeScoreList(currentUserId));
//                    }else{
//                        model.addAttribute("gradeTableList",daoAccess.teacherGetAllCourseAndGradeScoreListBySemester(semesterId,currentUserId));
//                    }
//
//                }else{
//                    model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
//                    if(semesterId == null){
//                        //show all
//                        model.addAttribute("gradeTableList",daoAccess.teacherGetAllCourseAndGradeScoreListByMajorId(majorId, currentUserId));
//                    }else{
//                        model.addAttribute("gradeTableList",daoAccess.teacherGetAllCourseAndGradeScoreListBySemesterAndMajorId(semesterId,majorId,currentUserId));
//                    }
//
//                }
//                break;
            }
        }

        //GET TEACHER COURES GRADE MANAGEMNET
        //GET ALL GRADE MANAGEMENT

        return "Admin_App/grades2";
    }

    /**
     * ADMIN GRADES
     */
    @RequestMapping(value = "admin/grades",method = RequestMethod.GET)
    public String adminManageGrades(Model model, HttpServletRequest request,
                                    @RequestParam(value="semester", required=false) Integer semesterId,
                                    @RequestParam(value="major", required=false) Integer majorId,
                                    @RequestParam(value="intake", required=false) Integer intakeId){

        LogHistoryDao daoAccessLog = (LogHistoryDao) applicationContext.getBean("LogHistoryDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        ClassDao daoAccessClass = (ClassDao) applicationContext.getBean("ClassDao");
        CourseEnrolmentDao daoAccessCourseEnroll = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");
        Subject currentUser = SecurityUtils.getSubject();

        Session session = currentUser.getSession();
        long currentUserId = (long)session.getAttribute("userId") ;

        String currentUserRole = (String) session.getAttribute("roleName");
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        System.out.println("@IP;;;;;>>"+remoteAddr);
        daoAccessLog.logUser(new LogHistory("Admin_App","Manage Grades","/admin/grades",currentUserId,"Teacher",remoteAddr,"Page Vist"));


        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");


        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("intakeSelectList", daoAccessClass.getAllIntakeNamesSelectList());

        model.addAttribute("isChecked", "");

        model.addAttribute("semesterId",semesterId);
        model.addAttribute("semesterList",daoAccess.getSemesterList());
        model.addAttribute("majorId",majorId);
        if ( majorId != null ){
            model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
        }

       // model.addAttribute("intakeId",intakeId);
        model.addAttribute("intakeId",intakeId);

        switch (currentUserRole){
            case "admin":
            {
//                model.addAttribute("gradeTableList", daoAccess. adminGetCoursesWithEnrolledStudentsCountList());
//                break;
                if (majorId == null || intakeId == null || semesterId == null ){
//                    model.addAttribute("classSelectList", daoAccessClass.getAllClassesNamesSelectList());
                    break;
                }else{

                    model.addAttribute("studentList",daoAccessMajor.getAllStudentListByMajorAndIntake(majorId, intakeId));
                    model.addAttribute("semesterCourseList",daoAccess.getAllCourseListBySemesterAndMajorId(semesterId,majorId));
                    model.addAttribute("courseGradesList",daoAccessCourseEnroll.getAllCourseGradesListByMIS(majorId, intakeId, semesterId));

                    break;
                }


             /*   if (majorId == null){
                    if(semesterId == null){
                        //show all
                        model.addAttribute("gradeTableList",daoAccess.getAllCourseAndGradeScoreList());
                    }else{
                        model.addAttribute("gradeTableList",daoAccess.getAllCourseAndGradeScoreListBySemester(semesterId));
                    }

                }else{
                    model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
                    if(semesterId == null){
                        //show all
                        model.addAttribute("gradeTableList",daoAccess.getAllCourseAndGradeScoreListByMajorId(majorId));
                    }else{
                        model.addAttribute("courseList",daoAccess.getAllCourseListBySemesterAndMajorId(semesterId,majorId));
                    }

                }*/
            }
            case "teacher":
            {
                if (majorId == null){
                    if(semesterId == null){
                        //show all
                        model.addAttribute("gradeTableList",daoAccess.teacherGetAllCourseAndGradeScoreList(currentUserId));
                    }else{
                        model.addAttribute("gradeTableList",daoAccess.teacherGetAllCourseAndGradeScoreListBySemester(semesterId,currentUserId));
                    }

                }else{
                    model.addAttribute("currentMajor",daoAccessMajor.getSingleMajor(majorId));
                    if(semesterId == null){
                        //show all
                        model.addAttribute("gradeTableList",daoAccess.teacherGetAllCourseAndGradeScoreListByMajorId(majorId, currentUserId));
                    }else{
                        model.addAttribute("gradeTableList",daoAccess.teacherGetAllCourseAndGradeScoreListBySemesterAndMajorId(semesterId,majorId,currentUserId));
                    }

                }
                break;
            }
        }

        //GET TEACHER COURES GRADE MANAGEMNET
        //GET ALL GRADE MANAGEMENT

        return "Admin_App/grades";
    }



    /**
     * ADMIN REPORTS
     */
    @RequestMapping(value = "view/notifications",method = RequestMethod.GET)
    public String viewNotifications(Model model){
        return "Admin_App/subPages/notifications";
    }


    /**
     * ADMIN REPORTS
     */
    @RequestMapping(value = "admin/reports",method = RequestMethod.GET)
    public String adminManageReports(Model model, HttpServletRequest request){

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
        daoAccessLog.logUser(new LogHistory("Admin_App","Reports","/admin/reports",currentUserId,"Teacher",remoteAddr,"Page Vist"));


        LogHistoryDao daoAccess = (LogHistoryDao) applicationContext.getBean("LogHistoryDao");
        //User user = UserUtils.getUser();

        model.addAttribute("logList",daoAccess.getAllSystemLogs());

        return "Admin_App/reports";
    }


    /**
     * ADMIN PUBLISH
     */
    @RequestMapping(value = "admin/publish",method = RequestMethod.GET)
    public String adminPublishNotifications(Model model, HttpServletRequest request){

        LogHistoryDao daoAccessLog = (LogHistoryDao) applicationContext.getBean("LogHistoryDao");
        NewsNotificationsDao daoAccess = (NewsNotificationsDao) applicationContext.getBean("NewsNotificationsDao");
        NewsNotificationsUploadsDao daoAccessUploads = (NewsNotificationsUploadsDao) applicationContext.getBean("NewsNotificationsUploadsDao");

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
        daoAccessLog.logUser(new LogHistory("Admin_App","Publish","/admin/publish",currentUserId,"Teacher",remoteAddr,"Page Vist"));




        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();

        List<NewsNotifications> notificationList = daoAccess.getNewsFeed(10);// limit set default make dynamic

        model.addAttribute("userId",userId);
        model.addAttribute("notificationList",notificationList);
        model.addAttribute("newsUploads",daoAccessUploads.getAllNewsNotificationUploads());

        return "Admin_App/publish";
    }



    /**
     * ADMIN BACKUP
     */
    @RequestMapping(value = "admin/backup",method = RequestMethod.GET)
    public String adminBackUp(Model model){
        return "Admin_App/backup";
    }



    /**
     * -------------------------------------------------------------------
     * -----------------------------SUB PAGES-----------------------------
     * -------------------------------------------------------------------
     */







    /**
     * MANAGE STUDENT CLASS /ADD STUDENT TO CLASS
     */
    @RequestMapping(value = "admin/manage-student-class",method = RequestMethod.GET)
    public String adminManageStudentClass(Model model){
        //create class
        // and student to class
        //view students in each class
        return "Admin_App/subPages/manageStudentClass";
    }



}
