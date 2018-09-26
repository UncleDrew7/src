package com.cdai.controllers.appController;

import build.dao.*;
import build.model.*;
import com.alibaba.fastjson.JSONObject;
import com.cdai.security.UserUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by apple on 07/08/2017.
 */
@Controller
public class CourseController {
    private static final Logger logger = Logger.getLogger(CourseController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");



    /**
     * STUDENT COURSE PAGE ROUTE   (student/my-courses)
     * SEARCH FOR COURSE    (/search)
     * DISPLAY ALL COURSES     (/courses)
     * COURSE DETAILED PAGE     (/course/details)
     * //student/course-exam{courseId}
     * ///course/details
     */

    /**
     * STUDENT COURSES STUDENT PAGE ROUTE
     */


    @RequestMapping(value = "student/my-courses/{semesterId}/{filter}",method = RequestMethod.GET)
    public String myCoureses(Model model, HttpServletRequest request, @PathVariable int semesterId ,@PathVariable int filter){

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

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();

        model.addAttribute("userId",userId);
        model.addAttribute("filter",filter);
        model.addAttribute("semesterId",semesterId);
        model.addAttribute("semesterList",daoAccess.getSemesterList());
        switch (filter){
            case 1:
                //get all active course
                model.addAttribute("studentMyCourseList",daoAccess.studentMyCoursesList(userId));
                break;
            case 2:
                //get all non active nad completed courses
                model.addAttribute("studentMyCourseList",daoAccess.studentCompletedCourseList(userId));
                break;
            case 3:
                //get all enrolled course for student
                model.addAttribute("studentMyCourseList",daoAccess.studentAllEnrolledCourses(userId));
                break;
            case 4 :
                model.addAttribute("studentMyCourseList",daoAccess. studentCoursesFilteredBySemesterId(userId,semesterId));
                break;
            default:
                model.addAttribute("studentMyCourseList",daoAccess.studentMyCoursesList(userId));
        }

        return "Student_App/my_course";
    }

    /**
     * COURSE VIEW PAGE
     */
    @RequestMapping(value = "admin/view/course/{courseId}",method = RequestMethod.GET)
    public String adminViewCourse(Model model,@PathVariable int courseId){

        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");
        CourseLessonDao daoAccess = (CourseLessonDao) applicationContext.getBean("CourseLessonDao");
        LessonUploadsDao daoAccessLessonUploads = (LessonUploadsDao) applicationContext.getBean("LessonUploadsDao");
        EventsDao daoAccessEvent = (EventsDao) applicationContext.getBean("EventsDao");

        //Subject currentUser = SecurityUtils.getSubject();
        // int userId = (Long) currentUser.getPrincipal();
//        int userId = UserUtils.getUser().getUserId();
        Session session = SecurityUtils.getSubject().getSession();
        long userId  = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");
        model.addAttribute("currentUserRole",currentUserRole);


        Courses courseDetails = daoAccessCourses.getCourseAboutDetails(courseId);
        List<CourseLesson> lessonContentList = daoAccess.lessonContent(courseId);
        List<LessonUploads> lessonUploadsList = daoAccessLessonUploads.getLessonUploadsByCourseId(courseId);


        model.addAttribute("courseId",courseId);
        model.addAttribute("courseDetails",courseDetails);
        model.addAttribute("lessonContentList",lessonContentList);
        model.addAttribute("lessonUploadsList",lessonUploadsList);
        model.addAttribute("enrolmentRequestCounts",daoAccessCourses.getCourseEnrolmentRequestCountByCourseId(courseId));
        model.addAttribute("courseEventList",daoAccessEvent.getCourseEventListByCourseId(courseId));
        model.addAttribute("hasPermission", daoAccessCourses.checkPermissionForTeacherAgainstCourseId(userId,courseId));

        return "Admin_App/subPages/coursePage";
    }

    /**
     * COURSE VIEW PAGE
     */
    @RequestMapping(value = "/student/view/course/{courseId}",method = RequestMethod.GET)
    public String displayCoursePage(Model model,@PathVariable int courseId){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");
        CourseLessonDao daoAccess = (CourseLessonDao) applicationContext.getBean("CourseLessonDao");
        LessonUploadsDao daoAccessLessonUploads = (LessonUploadsDao) applicationContext.getBean("LessonUploadsDao");
        EventsDao daoAccessEvent = (EventsDao) applicationContext.getBean("EventsDao");

       //Subject currentUser = SecurityUtils.getSubject();
        // int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();

        Courses courseDetails = daoAccessCourses.getCourseAboutDetails(courseId);
        List<CourseLesson> lessonContentList = daoAccess.lessonContent(courseId);
        List<LessonUploads> lessonUploadsList = daoAccessLessonUploads.getLessonUploadsByCourseId(courseId);


        model.addAttribute("courseId",courseId);
        model.addAttribute("courseDetails",courseDetails);
        model.addAttribute("lessonContentList",lessonContentList);
        model.addAttribute("lessonUploadsList",lessonUploadsList);
        model.addAttribute("courseEventList",daoAccessEvent.getCourseEventListByCourseId(courseId));

        return "Student_App/subPages/coursePage";
    }


    /**
     * COURSE EXAMS
     */
    @RequestMapping(value = "student/course-exam/{courseId}",method = RequestMethod.GET)
    public String courseExam(Model model ,@PathVariable int courseId ){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        GradeItemsDao daoAccess = (GradeItemsDao) applicationContext.getBean("GradeItemsDao");

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();

        List<GradeItems> courseExamsList  = daoAccess.getCourseExams(courseId);

        model.addAttribute("userId",userId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("courseExamsList",courseExamsList);

        return "Student_App/subPages/courseExamPage";
    }

    /**
     *  STUDENT MAKE COURSE ENROLLMENT
     */
    @ResponseBody
    @RequestMapping(value = "student/course/request/{courseId}",method = RequestMethod.POST)
    public String makeCourseEnrollmentRequest(Model model, @PathVariable int courseId ){
        CourseEnrolmentDao daoAccess = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");

        long userId = UserUtils.getUser().getUserId();
         String confirm = daoAccess.studentMakeCourseEnrollmentRequest(courseId,userId);
        logger.info("@=>"+confirm);

        return confirm;
    }


    /**
     *  ACCEPT ENROLLMENT REQUEST AND  ENROLL STUDENT IN COURSE
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-course/enroll-student/{courseId}/{userId}",method = RequestMethod.POST)
    public String courseStudentEnroll(Model model, HttpServletRequest request,@PathVariable int courseId, @PathVariable long userId  ){
        CourseEnrolmentDao daoAccess = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");

        String mConfirm = "";
        logger.info("@CourseID =>"+courseId);
        logger.info("@User ID  => "+userId);

        if(daoAccess.checkIfStudentIsCurrentlyEnrolledInCourse(courseId,userId)){
            System.out.println("@courseStudentEnroll::User is Already Enrolled deleting request");
            String confirm3 = mConfirm =  daoAccess. declineStudentCourseEnrollmentRequet(courseId,userId);
            System.out.println("@courseStudentEnroll::"+confirm3);

        }
        else{
            String confirm1 =  daoAccess.acceptStudentCourseEnrollmentRequest(courseId,userId);
            System.out.println("@courseStudentEnroll::Accepting user request .....");
            System.out.println("@courseStudentEnroll::"+confirm1);
            String confirm2 =  daoAccess.enrollStudentInCourse(new CourseEnrolment(courseId,userId));
            System.out.println("@courseStudentEnroll::Enrolling student into course .....");
            System.out.println("@courseStudentEnroll::"+confirm2);

            mConfirm = "Request Accepted :"+confirm1+": Enrollment Progress : "+confirm2;

        }


        return mConfirm;
    }

    /**
     *  ADMIN DECLINE ENROLLMENT REQUEST
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-course/decline-request/{courseId}/{userId}",method = RequestMethod.POST)
    public String declineCourseEnrolmentRequest(Model model, @PathVariable int courseId, @PathVariable long userId  ){
        CourseEnrolmentDao daoAccess = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");

        String confirm = daoAccess.declineStudentCourseEnrollmentRequet(courseId,userId);
        logger.info("@=>"+confirm);

        return confirm;
    }


    /**
     * COURSE ENROLLMENT LIST
     */
    @RequestMapping(value = "admin/manage-courses/enrolment-list/{courseId}/{nav}",method = RequestMethod.GET)
    public String adminEnrollStudent(@PathVariable int courseId,@PathVariable int nav, HttpServletRequest request,Model model){
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");

        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        model.addAttribute("courseId",courseId);
        model.addAttribute("nav",nav);
        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("enrollmentList",daoAccessUser.getCourseEnrollmentListByCourseId(courseId));
        model.addAttribute("courseDetails",daoAccessCourses.getAllCourseAboutDetails(courseId));
        model.addAttribute("enrolledStudentsCounts",daoAccessCourses.getTotalCourseEnrolments(courseId));
        model.addAttribute("enrolmentRequestCounts",daoAccessCourses.getCourseEnrolmentRequestCountByCourseId(courseId));
        model.addAttribute("enrollmentRequetsList",daoAccessUser.getCourseEnrollmentRequestList(courseId));
        model.addAttribute("hasPermission", daoAccessCourses.checkPermissionForTeacherAgainstCourseId(currentUserId,courseId));


        return "Admin_App/subPages/courseEnrolmentList";
    }

    /**
     * ENROLL STUDENT  PAGE
     */
    //admin/manage-courses/enroll-student/9007/800

    @RequestMapping(value = "admin/manage-courses/enroll-student/{courseId}/{nav}",method = RequestMethod.GET)
    public String adminEnrollStudent(HttpServletRequest request,@PathVariable int courseId,@PathVariable int nav, Model model){
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("UserDao");


        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");




        model.addAttribute("nav",nav);
        model.addAttribute("courseId",courseId);
        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("enrollmentsCount",daoAccessCourses.getTotalCourseEnrolments(courseId));
        model.addAttribute("aboutCourse",daoAccessCourses.getAllCourseAboutDetails(courseId));
        model.addAttribute("notEnrolledList",daoAccessUser.getStudentsNotEnrolledInCourseList(courseId));
        model.addAttribute("enrolledList",daoAccessUser.getStudentsEnrolledInCourseList(courseId));
        model.addAttribute("hasPermission", daoAccessCourses.checkPermissionForTeacherAgainstCourseId(currentUserId,courseId));

        return "Admin_App/subPages/studentEnrolmentPage";
    }

    @ResponseBody
    @RequestMapping(value = "admin/manage-courses/enroll-student/{courseId}",method = RequestMethod.POST)
    public String adminEnrollStudentAdd(HttpServletRequest request,@PathVariable int courseId,Model model){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        CourseEnrolmentDao daoAccess = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");
        String confirm= "";

        logger.info("getting Parameter ");
        logger.info(courseId);
        logger.info("getting user");
        logger.info(request.getParameter("userId"));
        logger.info(request.getParameter("action"));
        CourseEnrolment courseEnrolment = new CourseEnrolment(courseId,Long.parseLong(request.getParameter("userId")));

        if(request.getParameter("action").equals("remove")){
            logger.info("REMOVEING STUDENT");
            confirm = daoAccess.removeStudentFromCourse(courseEnrolment);
        }
        else{
            logger.info("ENROLLING STUDENT");
            Boolean check = daoAccess.checkIfStudentWasPreviouslyEnrolled(courseEnrolment);
            if(check){
                confirm = daoAccess.enrollPreviouslyEnrolledStudent(courseEnrolment);
            }else{
                confirm = daoAccess.enrollStudentInCourse(courseEnrolment);
            }
        }


        return confirm;
    }

    /**
     *  RETURN EDIT COURSE PAGE
     */
    @RequestMapping(value = "/admin/manage-course/edit/{courseId}/{nav}", method = RequestMethod.GET)
    public String editCourse(Model model,@PathVariable int courseId,@PathVariable int nav , HttpServletRequest request){
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");
        UserDao daoAccessU = (UserDao) applicationContext.getBean("userDao");

        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");



        model.addAttribute("courseId",courseId);
        model.addAttribute("nav",nav);
        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("currentCourseDetails",daoAccessCourses.getAllCourseAboutDetailsForUpdate(courseId));
        model.addAttribute("lastEditedCourses",daoAccessCourses.getLastEditedCoursesList());
        model.addAttribute("teachersList",daoAccessU.getTeachersList());
        model.addAttribute("hasPermission", daoAccessCourses.checkPermissionForTeacherAgainstCourseId(currentUserId,courseId));


        return "Admin_App/subPages/editCourseForm";
    }

    /**
     * PROCESS COURSE UPDATE
     */
    @ResponseBody
    @RequestMapping(value = "/admin/manage-course/update-course-details/{courseId}", method = RequestMethod.POST)
    public String updateCourseDetails(Model model,@PathVariable int courseId,  HttpServletRequest request){
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");

        logger.info("@updateCourseDetails");

        logger.info(request.getParameter("courseDescription"));
        logger.info(request.getParameter("courseName"));
        logger.info(request.getParameter("courseShortName"));
        logger.info(request.getParameter("teacherId"));
        logger.info(request.getParameter("startDate"));
        logger.info(request.getParameter("endDate"));

        String confirm  = daoAccessCourses.updateExistingCourse(new Courses(courseId,request.getParameter("courseShortName"),request.getParameter("courseName"),request.getParameter("courseDescription"),Long.parseLong(request.getParameter("teacherId")),request.getParameter("startDate"),request.getParameter("endDate")));
        return  confirm;
    }

    /**
     * UPDATE COURSE SETTINGS
     */
    @ResponseBody
    @RequestMapping(value = "/admin/manage-course/update-course-settings/{courseId}", method = RequestMethod.POST)
    public String updateCourseSettings(Model model,@PathVariable int courseId,  HttpServletRequest request){
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");

        logger.info("@updateCourseSettings");
        logger.info(request.getParameter("degreeType"));
        logger.info(request.getParameter("contentLanguage"));
        logger.info(request.getParameter("courseStatus"));
        String confirm   = daoAccessCourses.updateCourseSettings(new Courses(courseId,request.getParameter("contentLanguage"),request.getParameter("degreeType"),request.getParameter("courseStatus")));

        return confirm;
    }

    /**
     * DE-ACTIVATE COURSE
     */
    @ResponseBody
    @RequestMapping(value = "/admin/manage-course/deactivate-course/{courseId}", method = RequestMethod.POST)
    public String deActivateCourse(Model model,@PathVariable int courseId,  HttpServletRequest request){
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");

        logger.debug("@deActivateCourse");
        String confirm = daoAccessCourses.deleteExistingCourse(courseId);

        return confirm;
    }

    /**
     * CREATE COURSE EVENTS
     */
    @ResponseBody
    @RequestMapping(value = "/admin/manage-course/create-course-event/{courseId}", method = RequestMethod.POST)
    public String createCourseEvent(Model model,@PathVariable int courseId,  HttpServletRequest request){
        EventsDao daoAccessCourse = (EventsDao) applicationContext.getBean("EventsDao");
        long userId = UserUtils.getUser().getUserId();

        logger.info(request.getParameter("eventName"));
        logger.info(request.getParameter("eventDate"));
        logger.info(request.getParameter("eventDescription"));

        String confim = daoAccessCourse.addCourseEvent(new Events("course",request.getParameter("eventName"),request.getParameter("eventDescription"),request.getParameter("eventDate"),courseId,userId));

        return confim;
    }

    /**
     * EDIT CREATED COURSE EVENTS
     */
    @ResponseBody
    @RequestMapping(value = "/admin/manage-course/event/{eventId}", method = RequestMethod.POST)
    public Events getEventDetails(Model model,@PathVariable int eventId,  HttpServletRequest request){

        EventsDao daoAccessEvent = (EventsDao) applicationContext.getBean("EventsDao");
        Events event =  daoAccessEvent.getEventDetailsByEventId(eventId);
        logger.info(""+event.getTitle());

        return event;
    }

    /**
     * EDIT CREATED COURSE EVENTS
     */
    @ResponseBody
    @RequestMapping(value = "/admin/manage-course/update-course-event/{eventId}", method = RequestMethod.POST)
    public String updateCourseEvent(Model model,@PathVariable int eventId,  HttpServletRequest request){

        EventsDao daoAccessEvent = (EventsDao) applicationContext.getBean("EventsDao");
//        int userId = UserUtils.getUser().getUserId();

        logger.info(eventId);
        logger.info(request.getParameter("eventName"));
        logger.info(request.getParameter("eventDate"));
        logger.info(request.getParameter("eventDescription"));

        String confirm = daoAccessEvent.editCourseEvent(new Events(eventId,request.getParameter("eventName"),request.getParameter("eventDescription"),request.getParameter("eventDate")));

        logger.info(confirm);

        return confirm;
    }

    /**
     * EDIT DELETE COURSE EVENTS
     */

    @ResponseBody
    @RequestMapping(value = "/admin/manage-course/delete-course-event/{eventId}", method = RequestMethod.POST)
    public String deleteCourseEvent(Model model,@PathVariable int eventId,  HttpServletRequest request){

        EventsDao daoAccessEvent = (EventsDao) applicationContext.getBean("EventsDao");
        //int userId = UserUtils.getUser().getUserId();
        logger.info("delete Course ");

        String confirm =  daoAccessEvent.deleteEvent(eventId);


        //String confirm = daoAccessCourse.editCourseEvent(new Events(eventId,request.getParameter("eventName"),request.getParameter("eventDescription"),request.getParameter("eventDate")));

        return confirm;
    }



    /**
     * RETURN ADD COURSE CONTENT  FORM
     */
    @RequestMapping(value = "/admin/course/content/{action}/{courseId}",method = RequestMethod.GET)
    public String getAddCourseContentForm(Model model ,@PathVariable String action, @PathVariable int courseId){
        LessonUploadsDao daoAccessLessonUploads = (LessonUploadsDao) applicationContext.getBean("LessonUploadsDao");
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");
        CourseLessonDao daoAccessLessons = (CourseLessonDao) applicationContext.getBean("CourseLessonDao");

        Courses courseNames = daoAccessCourses.getCourseNameByCourseId(courseId);

        model.addAttribute("courseId",courseId);
        model.addAttribute("action",action);
        model.addAttribute("courseNames",courseNames);

        if(action.equals("add")){
            model.addAttribute("currentlyAddedLessonList",daoAccessLessons.getCurrentlyAddedLessonsList(courseId));
        }

        return "Admin_App/subPages/addCourseContentForm";
    }

    /**
     *  PROCESS ADD COURSE CONTENT FORM
     */
    @ResponseBody
    @RequestMapping(value = "/admin/course/content/add/{courseId}", method = RequestMethod.POST)
    public String addCourseContent(Model model, HttpServletRequest request,@PathVariable int courseId){
        CourseLessonDao daoAccess = (CourseLessonDao) applicationContext.getBean("CourseLessonDao");

        long userId = UserUtils.getUser().getUserId();


        logger.info(courseId);
        logger.info(request.getParameter("topic"));
        logger.info(request.getParameter("topicDescription"));

        String confirm = daoAccess.addCourseLesson(new CourseLesson(courseId,request.getParameter("topic"),request.getParameter("topicDescription"),userId));
        return confirm;
    }

    /**
     * RETURN EDIT COURSE CONTENT  FORM
     */
    @RequestMapping(value = "/admin/course/content/{action}/{courseId}/{lessonId}",method = RequestMethod.GET)
    public String getAddCourseContentForm(Model model , @PathVariable String action , @PathVariable int courseId, @PathVariable int lessonId){
        LessonUploadsDao daoAccessLessonUploads = (LessonUploadsDao) applicationContext.getBean("LessonUploadsDao");
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");
        CourseLessonDao daoAccessClesson = (CourseLessonDao) applicationContext.getBean("CourseLessonDao");

        Courses courseNames = daoAccessCourses.getCourseNameByCourseId(courseId);

        model.addAttribute("courseId",courseId);
        model.addAttribute("action",action);
        model.addAttribute("lessonId",lessonId);
        model.addAttribute("lessonUploadsList",daoAccessLessonUploads.getLessonUploadsByLessonId(lessonId));
        model.addAttribute("courseNames",courseNames);
        model.addAttribute("lessonContent",daoAccessClesson.getSingleCourseLessonByLessonId(lessonId));

        return "Admin_App/subPages/addCourseContentForm";
    }

    /**
     *  UPDATE COURSE LESSON
     */
    @ResponseBody
    @RequestMapping(value = "/admin/course/content/update/{lessonId}", method = RequestMethod.POST)
    public String updateCourseLesson(Model model,@PathVariable int lessonId, HttpServletRequest request){

        CourseLessonDao daoAccess = (CourseLessonDao) applicationContext.getBean("CourseLessonDao");

        logger.info(lessonId);
        logger.info(request.getParameter("topic"));
        logger.info(request.getParameter("topicDescription"));

        String confirm  = daoAccess.updateCourseLessonData(new CourseLesson(lessonId,request.getParameter("topic"),request.getParameter("topicDescription")));
        return confirm;
    }


    /**
     *  DELETE COURSE LESSON
     */
    @ResponseBody
    @RequestMapping(value = "/admin/course/content/delete", method = RequestMethod.POST)
    public String deleteCourseContent(Model model, HttpServletRequest request){

        CourseLessonDao daoAccess = (CourseLessonDao) applicationContext.getBean("CourseLessonDao");
        LessonUploadsDao daoAccessUploads = (LessonUploadsDao) applicationContext.getBean("LessonUploadsDao");
        long userId = UserUtils.getUser().getUserId();

        logger.info("lessonId ==>"+request.getParameter("lessonId"));
        int lessonId = Integer.parseInt(request.getParameter("lessonId"));

        List<LessonUploads> uploads = daoAccessUploads.getLessonUploadsByLessonId(lessonId);

        try{
            String path="C:\\Java\\apache-tomcat-8.5.16\\uploads\\docs";
            for(LessonUploads entity : uploads) {
                File file = new File(path+ File.separator + entity.getUploadUrl());
                if(file.delete()){
                    System.out.println(file.getName() + " is deleted!::@@::For Lesson"+lessonId);
                }else{
                    System.out.println("Delete operation is failed.::@@::For Lesson"+lessonId);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("@@::ERROR!!");
        }

        String confirmUploadsDelets = daoAccessUploads.deleteLessonUploadsByLessonId(lessonId);
        System.out.println("@UPLOADS::"+confirmUploadsDelets+"::@@::For Lesson"+lessonId);

        String confirm = daoAccess.deleteLessonContent(lessonId);
        System.out.println("LESSON TOPIC::"+confirm+"::@@::For Lesson"+lessonId);
        return confirm;
    }


    /**
     * RETURN LESSON UPLOAD  FORM
     */
    @RequestMapping(value = "/admin/course/content/lessons/upload",method = RequestMethod.GET)
    public String getLessonUploadForm(Model model){

        return "Admin_App/subPages/uploadCourseLessonForm";
    }

    /**
     *  UPDATE LESSON UPLOAD NAME
     */
    @ResponseBody
    @RequestMapping(value = "/admin/course/content/lessons/upload-update/{lessonUploadId}", method = RequestMethod.POST)
    public String updateUploadLessonName(Model model, HttpServletRequest request,@PathVariable int lessonUploadId,@RequestParam("uploadName") String newUploadName){

        LessonUploadsDao daoAccess = (LessonUploadsDao) applicationContext.getBean("LessonUploadsDao");

        logger.info("@@::NewName::"+newUploadName);
        logger.info("@@::"+lessonUploadId);

        String confirm = daoAccess.editLessonUploadName(lessonUploadId,newUploadName);

        return confirm;
    }

    /**
     *  DELETE SINGLE LESSON UPLOAD
     */
    @ResponseBody
    @RequestMapping(value = "/admin/course/content/lessons/upload-delete/{lessonUploadId}", method = RequestMethod.POST)
    public String deleteLessonUpload(Model model, HttpServletRequest request ,@PathVariable int lessonUploadId,@RequestParam("docName") String docName){
        LessonUploadsDao daoAccess = (LessonUploadsDao) applicationContext.getBean("LessonUploadsDao");

        logger.info("@::"+docName);
        logger.info("@::"+lessonUploadId);

        try{

            String path="C:\\Java\\apache-tomcat-8.5.16\\uploads\\docs";
            File file = new File(path+ File.separator + docName);
            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete operation is failed.");
            }
            String confirm = daoAccess.deleteUploadInfoByLessonUploadId(lessonUploadId);
            logger.info("@@::"+confirm);
            return "200";


        }catch(Exception e){
            e.printStackTrace();
            logger.info("@@::ERROR!!");
            return "500";
        }

    }

    /**
     * ADMIN PROCESS ADD COURSE FORM
     *<return>JSONObject</return>
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-courses/process-add-course",method = RequestMethod.POST)
    public  String adminProcessAddCourseForm(Model model,HttpServletRequest request){

        JSONObject msg = new JSONObject();
        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");

        logger.info(request.getParameter("courseId"));
        logger.info(request.getParameter("courseShortName"));
        logger.info(request.getParameter("courseName"));
        logger.info(request.getParameter("teacherId"));
        logger.info(request.getParameter("endDate"));
        logger.info(request.getParameter("startDate"));
        logger.info(request.getParameter("courseDescription"));
        logger.info("double-degree");
        logger.info("content type English");
        logger.info("Status Active");

        try{
            Courses courses = new Courses();

            courses.setCourseId(Integer.parseInt(request.getParameter("courseId")));
            courses.setTeacherId(Long.parseLong(request.getParameter("teacherId")));//Qi Zheng
            courses.setCategoryId(2);//2013 spring
            courses.setCourseShortName(request.getParameter("courseShortName"));
            courses.setCourseName(request.getParameter("courseName"));
            courses.setCourseDescriptionEn(request.getParameter("courseDescription"));
            courses.setCourseType("double-degree");//default
            courses.setStartDate(request.getParameter("startDate"));
            courses.setEndDate(request.getParameter("endDate"));

            String confirmation  = daoAccess.createNewCourse(courses);
            logger.info("###SUCCESS### - "+ confirmation);


        }catch (Exception ex){
            msg.put("message","Error! something went wrong check inputted data");
            msg.put("status",400);
            ex.printStackTrace();
            return "Error Check Your data";

        }
        msg.put("message","Course Successfully Created!");
        msg.put("status",201);
        return request.getParameter("courseName")+" Created successfully";
    }


    /**
     * ADD COURSE FORM
     */
    @RequestMapping(value = "admin/manage-courses/add",method = RequestMethod.GET)
    public String adminAddCourse(Model model){
        CoursesDao daoAccessC = (CoursesDao) applicationContext.getBean("CoursesDao");
        UserDao daoAccessU = (UserDao) applicationContext.getBean("userDao");
        SemesterDao daoAccessSems = (SemesterDao) applicationContext.getBean("SemesterDao");

        model.addAttribute("teachersList",daoAccessU.getTeachersList());
        model.addAttribute("semesterList", daoAccessSems.getSystemSemesterList());
        model.addAttribute("lastAdded",daoAccessC.getLastAddedCourseList());

        return "Admin_App/subPages/addCourseForm";
    }


    /**
     * ADD COURSE EXCEL
     */
    @RequestMapping(value = "manage-courses/add-course-excel",method = RequestMethod.POST)
    public String addCourseByUploadingExcelFile(Model model, @RequestParam("courseExcelListFile")MultipartFile courseExcelListFile){
        HSSFRow row;

        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");

        logger.info(">> Started importing Course Excel file << ");

        try{

            int i = 0;
            int id = 0;
            long teacherid = 0;
            int categoryid=0;

            HSSFWorkbook workbook = new HSSFWorkbook(courseExcelListFile.getInputStream());
            HSSFSheet worksheet = workbook.getSheetAt(0);
            Iterator< Row > rowIterator = worksheet.iterator();

            logger.info(">> Reading Excel file ");
            int count =0;

            while(rowIterator.hasNext()){
                row = (HSSFRow)rowIterator.next();
                if(row.getRowNum()==0){
                    continue; //just skip the rows if row number is 0
                }
                Iterator<Cell> cellIterator = row.cellIterator();

                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();

                    logger.info(">>>>>>>>Adding to database>>>>>>>>>");

                    //ADD TO COURSE TABLE
                    Courses courses = new Courses();

                    id = (int)row.getCell(0).getNumericCellValue();
                    teacherid = (int)row.getCell(1).getNumericCellValue();
                    categoryid = (int)row.getCell(2).getNumericCellValue();

                    courses.setCourseId(id);
                    courses.setTeacherId(teacherid);//Qi Zheng
                    courses.setCategoryId(categoryid);//2013 spring
                    courses.setCourseName(row.getCell(3).getStringCellValue());
                    courses.setCourseDescriptionEn(row.getCell(4).getStringCellValue());
                    courses.setCourseType(row.getCell(5).getStringCellValue());
                    courses.setStartDate(row.getCell(6).getStringCellValue());
                    courses.setEndDate(row.getCell(7).getStringCellValue());

                    String confirmation  = daoAccess.createNewCourse(courses);
                    System.out.println("::>> " + confirmation );

                    break;



                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/admin/manage-courses";


    }



    /**
     * SEARCH FOR COURSE
     * send parameters
     */
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String search(Model model){
        return "subViews/search";
    }



    /**
     * DISPLAY ALL COURSES
     */
    @RequestMapping(value = "/courses",method = RequestMethod.GET)
    public String displayAllCourses(Model model){
        return "Student_App/all_courses";
    }

    /**
     * ADMIN DELETE COURSE FROM WHOLE SYSTEM
     */
    @ResponseBody
    @RequestMapping(value = "/admin/manage-courses/delete-course/{courseId}",method = RequestMethod.POST)
    public String deleteCourseFromSystem(Model model,@PathVariable int courseId){

        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");
        LessonUploadsDao daoAccessUploads = (LessonUploadsDao) applicationContext.getBean("LessonUploadsDao");

        String path="C:\\Java\\apache-tomcat-8.5.16\\uploads\\docs";
        List<LessonUploads> uploads = daoAccessUploads.getLessonUploadsByCourseId(courseId);

        try {
            daoAccess.deleteCourseFromCourseStudentRequestEnrolment(courseId);
            daoAccess.deleteCourseFromCourseEnrolment(courseId);
            daoAccess.deleteCourseFromEvents(courseId);
            daoAccess.deleteCourseFromExamEnrolment(courseId);
            daoAccess.deleteCourseFromExamStudentRequestEnrolment(courseId);
            daoAccess.deleteCourseFromGrade(courseId);
            daoAccess.deleteCourseFromGradeItems(courseId);

            for(LessonUploads entity : uploads) {
                File file = new File(path+ File.separator + entity.getUploadUrl());
                if(file.delete()){
                    System.out.println(file.getName() + " is deleted!::@@::For Course"+courseId);
                }else{
                    System.out.println("Delete operation is failed.::@@::For Course"+courseId);
                }
            }
            daoAccess.deleteCourseUploads(courseId);

            daoAccess.deleteCourseLessons(courseId);
            String confirm = daoAccess.deleteCourseFromCourses(courseId);

            return confirm;

        }catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }

    }





}
