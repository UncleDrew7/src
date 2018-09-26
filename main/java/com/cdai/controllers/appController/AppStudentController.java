package com.cdai.controllers.appController;
import build.dao.*;
import build.model.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.cdai.util.CoursesUtil.returnStudentCustomHomePageCoursesApi;
import static com.cdai.util.CoursesUtil.returnStudentCustomHomePageCoursesSearch;


/**
 * Created by nick on 06/08/2017.
 */

@Controller
public class AppStudentController {
    private static final Logger logger = Logger.getLogger(AppStudentController.class);
    private static final  ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");

    /**
     * APPLICATION LOGIN PAGE   (/login)
     * USER PRIVATE PROFILE PAGE   (/student/profile)
     * USERS PUBLIC PROFILE PAGE    (/view/profile)
     * USER UPDATE PROFILE PAGE     (/profile/update)
     * STUDENT HOME PAGE    (/student/home)
     *
     */



    /**
     * STUDENT HOME PAGE  ROUTE
     */

    @RequestMapping(value = {"/student/home2"},method = RequestMethod.GET)
    public String Home2(Model model, HttpServletRequest request,@RequestParam(value="filter", required=false) String filter ,@RequestParam(value="search", required=false) String search){

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
        daoAccessLog.logUser(new LogHistory("Student_App","Home","/student/home",currentUserId,"Student",remoteAddr,"Page Vist"));



        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");
        GradeItemsDao daoAccessGradeItems = (GradeItemsDao) applicationContext.getBean("GradeItemsDao");
//        NewsNotificationsDao daoAccessNewsNotifications = (NewsNotificationsDao) applicationContext.getBean("NewsNotificationsDao");
        EventsDao daoAccessEvents = (EventsDao) applicationContext.getBean("EventsDao");
        CourseEnrolmentDao daoAccessCourseE = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");


        GradeItems gradeItems = new GradeItems();
        Courses courses = new Courses();

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();
        int majorId = daoAccessUser.getStudentMajorId(currentUserId);

        List<Courses> currentCourseList = returnStudentCustomHomePageCoursesSearch(userId,search,majorId);//daoAccess.activeCourseList(userId,8,majorId);
//        List<GradeItems> enrolledCoursesAvailableExamsList = daoAccessGradeItems.getAvailableExamsForStudent(userId);
        List<Exam> enrolledCoursesAvailableExamsList = daoAccessExam.getStudentAvailableExamList(userId, search);

//        List<NewsNotifications> notificationList = daoAccessNewsNotifications.getStudentFocusedNotifications(2);//limited to only 2 change #


        model.addAttribute("userId",userId);
        model.addAttribute("filter",filter);
        model.addAttribute("search",search);


//        model.addAttribute("notificationList",notificationList);
        model.addAttribute("upcomingEvents", daoAccessEvents.displayAdminUpcomingEvent(userId));
        model.addAttribute("calendarEvents", JSON.toJSONString(daoAccessEvents.getStudentCalenderEvents(userId)));


        if(filter == null){
            model.addAttribute("currentCourseList",currentCourseList);
        }else{
            if (filter.equals("exams")){

                    model.addAttribute("enrolledCoursesAvailableExamsList",enrolledCoursesAvailableExamsList);

            }else{
                model.addAttribute("currentCourseList",currentCourseList);
            }
        }

        return "Student_App/index";
    }

    /**
     * STUDENT HOME PAGE  ROUTE
     */
    @RequestMapping(value = {"/student/home"},method = RequestMethod.GET)
    public String Home(Model model, HttpServletRequest request,@RequestParam(value="filter", required=false) String filter ,@RequestParam(value="exam", required=false) String exam){

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
        daoAccessLog.logUser(new LogHistory("Student_App","Home","/student/home",currentUserId,"Student",remoteAddr,"Page Vist"));



        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");
        GradeItemsDao daoAccessGradeItems = (GradeItemsDao) applicationContext.getBean("GradeItemsDao");
//        NewsNotificationsDao daoAccessNewsNotifications = (NewsNotificationsDao) applicationContext.getBean("NewsNotificationsDao");
        EventsDao daoAccessEvents = (EventsDao) applicationContext.getBean("EventsDao");
        CourseEnrolmentDao daoAccessCourseE = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");


        GradeItems gradeItems = new GradeItems();
        Courses courses = new Courses();

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();
        int majorId = daoAccessUser.getStudentMajorId(currentUserId);

        List<Courses> currentCourseList = returnStudentCustomHomePageCoursesApi(userId,8,majorId);//daoAccess.activeCourseList(userId,8,majorId);
        List<Courses> retakeCourseList = daoAccessCourseE.getAvailableRetakeCourses(userId);
//        List<GradeItems> enrolledCoursesAvailableExamsList = daoAccessGradeItems.getAvailableExamsForStudent(userId);
        List<Exam> enrolledCoursesAvailableExamsList = daoAccessExam.getStudentAvailableExamList(userId);

//        List<NewsNotifications> notificationList = daoAccessNewsNotifications.getStudentFocusedNotifications(2);//limited to only 2 change #


        model.addAttribute("userId",userId);
        model.addAttribute("filter",filter);
        model.addAttribute("exam",exam);


//        model.addAttribute("notificationList",notificationList);
        model.addAttribute("upcomingEvents", daoAccessEvents.displayAdminUpcomingEvent(userId));
        model.addAttribute("calendarEvents", JSON.toJSONString(daoAccessEvents.getStudentCalenderEvents(userId)));


        if(filter == null){
            model.addAttribute("currentCourseList",currentCourseList);
        }else{
            if (filter.equals("exams")){
                if(exam == null){
                    model.addAttribute("enrolledCoursesAvailableExamsList",enrolledCoursesAvailableExamsList);
                }else if(exam.equals("latest")){
//                    model.addAttribute("enrolledCoursesAvailableExamsList",daoAccessGradeItems.getLatestExamsForStudent(currentUserId));
                    model.addAttribute("enrolledCoursesAvailableExamsList",enrolledCoursesAvailableExamsList);
                }else{
                    model.addAttribute("enrolledCoursesAvailableExamsList",enrolledCoursesAvailableExamsList);
                }
            }else if ( filter.equals("retakes")) {
                model.addAttribute("retakeCourseList",retakeCourseList);
            }
            else {
                model.addAttribute("currentCourseList",currentCourseList);
            }
        }

        return "Student_App/index";
    }

    /**
     * STUDENT COURSES STUDENT PAGE ROUTE
     */

    @RequestMapping(value = "student/my-courses/{object}",method = RequestMethod.GET)
    public String myCoureses(Model model, HttpServletRequest request,@PathVariable String object,  @RequestParam(value="semester", required=false) Integer semesterId){

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
        daoAccessLog.logUser(new LogHistory("Student_App","My Courses","/student/my-courses",currentUserId,"Student",remoteAddr,"Page Vist"));


        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");
        ExamEnrolmentDao daoAccessExams = (ExamEnrolmentDao ) applicationContext.getBean("ExamEnrolmentDao");

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();

        model.addAttribute("userId",userId);
        model.addAttribute("object",object);
        model.addAttribute("semesterId",semesterId);
        model.addAttribute("semesterList",daoAccess.getSemesterList());


        if(object.equals("Courses")){
            if (semesterId == null){
                model.addAttribute("studentMyCourseList",daoAccessChildCourses.getAllStudentsCoursesByStudentId(userId));
            }
            else{
                model.addAttribute("studentMyCourseList",daoAccessChildCourses.getStudentCoursesBySemesterId(userId,semesterId));
            }
        }
        else if(object.equals("Exam")){
            if (semesterId == null){
                model.addAttribute("studentExamsList",daoAccessExams.getStudentEnrolledExamList(userId));
            }
            else{
                model.addAttribute("studentExamsList",daoAccessExams.getStudentEnrolledExamListbySemester(userId,semesterId));
            }

        }
        else{
            model.addAttribute("studentMyCourseList",daoAccessChildCourses.getAllStudentsCoursesByStudentId(userId));
        }


        return "Student_App/my_course";
    }

    @RequestMapping(value = "student/my-courses-search/{object}",method = RequestMethod.GET)
    public String searchCoursesOrExams(Model model, HttpServletRequest request,@PathVariable String object,
                                       @RequestParam(value="semester", required=false) Integer semesterId,
                                       @RequestParam(value = "search", required = false) String search){

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
        daoAccessLog.logUser(new LogHistory("Student_App","My Courses","/student/my-courses",currentUserId,"Student",remoteAddr,"Page Vist"));


        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");
        ExamEnrolmentDao daoAccessExams = (ExamEnrolmentDao ) applicationContext.getBean("ExamEnrolmentDao");

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();

        model.addAttribute("userId",userId);
        model.addAttribute("object",object);
        model.addAttribute("search",search);
        model.addAttribute("semesterId",semesterId);
        model.addAttribute("semesterList",daoAccess.getSemesterList());


        if(object.equals("Courses")){
            if (semesterId == null){
                model.addAttribute("studentMyCourseList",daoAccessChildCourses.getAllStudentsCoursesByStudentIdAndSearch(userId, search));
            }
            else{
                model.addAttribute("studentMyCourseList",daoAccessChildCourses.getStudentCoursesBySemesterIdAndSearch(userId,semesterId, search));
            }
        }
        else if(object.equals("Exam")){
            if (semesterId == null){
                model.addAttribute("studentExamsList",daoAccessExams.getStudentEnrolledExamListAndSearch(userId, search));
            }
            else{
                model.addAttribute("studentExamsList",daoAccessExams.getStudentEnrolledExamListbySemesterAndSearch(userId,semesterId, search));
            }

        }
        else{
            model.addAttribute("studentMyCourseList",daoAccessChildCourses.getAllStudentsCoursesByStudentIdAndSearch(userId, search));
        }


        return "Student_App/my_course";
    }


    /**
     * STUDENT PROFILE STUDENT PAGE ROUTE
     * users personal private profile page
     */
    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public String userProfile(Model model){
        return "sharedViews/user_profile";
    }



    /**
     * USER UPDATE PROFILE PAGE
     * edit personal profile
     */
    @RequestMapping(value = "/profile/update",method = RequestMethod.GET)
    public String updateUserProfile(Model model){
        return "sharedViews/update_profile";
    }


    /**
     * USER UPDATE PROFILE PAGE
     * edit personal profile
     */
    @ResponseBody
    @RequestMapping(value = "/getMoreCourse",method = RequestMethod.GET)
    public List<Courses> getMOreCourse(Model model){
        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        int limit = 8;

        return daoAccess.activeCourseListAboveLimit(currentUserId,limit);
    }





    /**
     * SEARCH COURSE AND EXAM
     */
    @RequestMapping(value = "/student/search/{object}",method = RequestMethod.GET)
    public String Home(Model model, HttpServletRequest request, @PathVariable String object ,@RequestParam(value="search", required=true) String search,
                       @RequestParam(value="semester", required=false) Integer semesterId){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        SearchCoursesAndExamsDao daoAccess = (SearchCoursesAndExamsDao) applicationContext.getBean("SearchCoursesAndExamsDao");
        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        SearchCoursesAndExams searchVar = new SearchCoursesAndExams();

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");


        model.addAttribute("search",search);
        model.addAttribute("object",object);
        model.addAttribute("semester",semesterId);
        model.addAttribute("semesterList",daoAccessCourse.getSemesterList());

        int majorId = daoAccessMajor.getStudentMajorId(currentUserId);

        logger.info(majorId);
        logger.info(search);


        if(object.equals("courses")){
            if(semesterId == null){
                //show all
                model.addAttribute("searchContentList",daoAccess.searchChildCourses(majorId ,search));
            }else{
                model.addAttribute("searchContentList",daoAccess.searchChildCoursesFilteredBySemesterId(majorId,search ,semesterId));
            }
        }else if(object.equals("exams")){
            if(semesterId == null){
                model.addAttribute("searchContentList",daoAccess.searchChildCourseExams(majorId ,search,currentUserId));
            }else{
                model.addAttribute("searchContentList",daoAccess.searchChildCourseExamsFilteredBySemesterId(majorId,search,semesterId ,currentUserId));
            }
        }

        return "Student_App/subPages/searchCourseAndExam";
    }



    /**
     * STUDENT GRADES PAGE ROUTE
     */
    @RequestMapping(value = "student/grades",method = RequestMethod.GET)
    public String grades(Model model, HttpServletRequest request,@RequestParam(value="semester", required=false) Integer semesterId){

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
        daoAccessLog.logUser(new LogHistory("Student_App","My Grades","/student/grades",currentUserId,"Student",remoteAddr,"Page Vist"));


        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();



        model.addAttribute("userId",userId);
        model.addAttribute("semesterId",semesterId);
        model.addAttribute("semesterList",daoAccess.getSemesterList());

        if (semesterId == null){
            model.addAttribute("coursesWithGradeAverageList",daoAccess.getUserCoursesGradeAverage(userId));
        }
        else{
            model.addAttribute("coursesWithGradeAverageList",daoAccess.getUserCoursesGradeAverageFilterByCourseCategory(userId,semesterId));
        }


        return "Student_App/grades";
    }



    /**
     * CHECK COURSE ENROLMENT STATUS
     */
    @ResponseBody
    @RequestMapping(value = "/student/check-course-status/{courseId}",method = RequestMethod.POST)
    public String checkCourseStudentStatus(Model model,@PathVariable int courseId){

        CourseEnrolmentDao daoAccess = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        //check if enrolled in course
        if(daoAccess.checkIfStudentIsCurrentlyEnrolledInCourse(courseId,currentUserId)){
           return "enrolled";
        }
        else if(daoAccess.checkIfStudentHasAlreadyMadeAnEnrollmentRequest(courseId,currentUserId)){
            return "pending";
        }
        else{
            return "enroll";
        }

    }

    /**
     * CHECK EXAM ENROLMENT STATUS
     */
    @ResponseBody
    @RequestMapping(value = "/student/check-exam-status/{examId}",method = RequestMethod.POST)
    public String checkExamStudentStatus(Model model,@PathVariable int examId){

        ExamEnrolmentDao daoAccess = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        //check if enrolled in course
        if(daoAccess.checkIfStudentIsEnrolledInExam(examId,currentUserId)){
            return "enrolled";
        }
        else if(daoAccess.checkIfStudentHasAlreadyMadePendingEnrolmentRequest(examId,currentUserId)){
            return "pending";
        }
        else{
            return "enroll";
        }


    }









}
