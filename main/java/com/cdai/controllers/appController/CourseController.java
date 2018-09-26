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
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import static com.cdai.util.CourseEnrollmentUtils.enrolmentRequestLedger;
import static com.cdai.util.CourseEnrollmentUtils.mainCourseEnrolmentLedger;
import static com.cdai.util.CourseEnrollmentUtils.mainCourseEnrolmentLedgerCheck;
import static com.cdai.util.ExamsUtil.getAvailableRecommendedExamsListForStudent;
import static com.cdai.util.ServiceUtil.checkIfDateIsStillActive;
import static com.cdai.util.StudentGradeAndReportingUtil.getForStudentEnrolledExamsList;

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
     * COURSE VIEW PAGE
     */
    @RequestMapping(value = "admin/view/course/{childCourseId}",method = RequestMethod.GET)
    public String adminViewCourse(Model model,@PathVariable int childCourseId){

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



        model.addAttribute("courseId",childCourseId);
        model.addAttribute("courseDetails",daoAccessCourses.getCourseAboutDetails(childCourseId));
        model.addAttribute("lessonContentList",daoAccess.lessonContent(childCourseId));
        model.addAttribute("lessonUploadsList",daoAccessLessonUploads.getLessonUploadsByCourseId(childCourseId));
        model.addAttribute("enrolmentRequestCounts",daoAccessCourses.getCourseEnrolmentRequestCountByCourseId(childCourseId));
        model.addAttribute("courseEventList",daoAccessEvent.getCourseEventListByCourseId(childCourseId));
        model.addAttribute("hasPermission", daoAccessCourses.checkPermissionForTeacherAgainstCourseId(userId,childCourseId));

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
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();
        long parentCourseId = daoAccessChildCourses.getChildCourseParentId(courseId);


        model.addAttribute("userId",userId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("courseExamsList",getForStudentEnrolledExamsList(parentCourseId,userId));
        model.addAttribute("recommendedAndAvailableExams",getAvailableRecommendedExamsListForStudent(parentCourseId,userId));
        model.addAttribute("courseDetails",daoAccessCourses.getCourseAboutDetailsForStudentExamPage(courseId,userId));

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
     *  STUDENT MAKE COURSE ENROLLMENT AND AUTOMATIC ENROLMENT PROCESSING
     */
    @ResponseBody
    @RequestMapping(value = "student/make-course-enrollment/{childCourseId}",method = RequestMethod.POST)
    public String studentMakeCourseEnrollementWithAutomaticRequestProcessing(Model model, @PathVariable int childCourseId ){
        CourseEnrolmentDao daoAccess = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");
//        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long studentId = (long) session.getAttribute("userId");

        if ( daoAccess.checkIfStudentWasPreviouslyEnrolled( childCourseId, studentId )){
//            System.out.println("@---::retake course not allowed currently!");
//            return "300";
            List<Courses> enrolledCourses = daoAccess.getStudentEnrolledCoursesList( childCourseId, studentId );

            for( Courses entity:enrolledCourses){
                daoAccess.setEnrolledCourseInactive( entity.getCourseId(), studentId );
            }

        }

        if(enrolmentRequestLedger(childCourseId,studentId).equals("200")){

            if(mainCourseEnrolmentLedgerCheck(childCourseId,studentId)){
                if(mainCourseEnrolmentLedger(childCourseId,studentId).equals("200")){

                    System.out.println("@-----------------::Student::"+studentId +" enrolled in ::"+childCourseId+" |");
                    return "200";

                }else
                {
                    System.out.println("@-------------------::Enrollment Failed !");
                    return "400";
                }
            }else{
                System.out.println("@---::student Check failed");
                return "400";
            }

        }else{
            System.out.println("@---::Failed to get student initial enrollment request ");
            return "400";
        }

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
        model.addAttribute("courseDetails",daoAccessCourses.getCourseAboutDetails(courseId));
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

    @RequestMapping(value = "admin/manage-courses/enroll-student/{childCourseId}/{nav}",method = RequestMethod.GET)
    public String adminEnrollStudent(HttpServletRequest request,@PathVariable int childCourseId,@PathVariable int nav, Model model){
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");


        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");




        model.addAttribute("nav",nav);
        model.addAttribute("childCourseId",childCourseId);
        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("enrollmentsCount",daoAccessCourses.getTotalCourseEnrolments(childCourseId));
        model.addAttribute("aboutCourse",daoAccessCourses.getCourseAboutDetails(childCourseId));
        model.addAttribute("notEnrolledList",daoAccessUser.getStudentsNotEnrolledInCourseList(childCourseId));
        model.addAttribute("enrolledList",daoAccessUser.getStudentsEnrolledInCourseList(childCourseId));
        model.addAttribute("hasPermission", daoAccessCourses.checkPermissionForTeacherAgainstCourseId(currentUserId,childCourseId));

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
    @RequestMapping(value = "/admin/manage-course/edit/{childCourseId}/{nav}", method = RequestMethod.GET)
    public String editCourse(Model model,@PathVariable int childCourseId,@PathVariable int nav , HttpServletRequest request){
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");
        UserDao daoAccessU = (UserDao) applicationContext.getBean("userDao");

        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");



        model.addAttribute("childCourseId",childCourseId);
        model.addAttribute("nav",nav);
        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("currentCourseDetails",daoAccessCourses.getCourseAboutDetails(childCourseId));
        model.addAttribute("lastEditedCourses",daoAccessCourses.getLastEditedCoursesList());
        model.addAttribute("teachersList",daoAccessU.getTeachersList());
        model.addAttribute("semesterList",daoAccessCourses.getSemesterList());
        model.addAttribute("hasPermission", daoAccessCourses.checkPermissionForTeacherAgainstCourseId(currentUserId,childCourseId));


        return "Admin_App/subPages/editCourseForm";
    }

    /**
     * PROCESS COURSE UPDATE
     */
    @ResponseBody
    @RequestMapping(value = "/admin/manage-course/update-course-details/{childCourseId}", method = RequestMethod.POST)
    public String updateCourseDetails(Model model,@PathVariable int childCourseId,  HttpServletRequest request){
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");

        logger.info("@updateCourseDetails");

//        logger.info(request.getParameter("courseDescription"));
//        logger.info(request.getParameter("courseShortName"));
        logger.info(request.getParameter("teacherId"));
        logger.info(request.getParameter("category"));
        logger.info(request.getParameter("deadline"));
        logger.info(request.getParameter("startDate"));

        ChildCourses newChildCourse = new ChildCourses();
        newChildCourse.setChildCourseId(childCourseId);
//        newChildCourse.setCourseShortName(request.getParameter("courseShortName"));
        newChildCourse.setTeacherId(Long.parseLong(request.getParameter("teacherId")));
        newChildCourse.setSemesterId(Integer.parseInt( request.getParameter("category")) );
        newChildCourse.setEnrolmentDeadline(request.getParameter("deadline"));
        newChildCourse.setEnrolmentStartDate(request.getParameter("startDate"));

        String confirm = daoAccessChildCourses.updateChildCourse( newChildCourse );

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
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");
        ParentCourseChildCoursesDao daoAccessPCChild = (ParentCourseChildCoursesDao) applicationContext.getBean("ParentCourseChildCoursesDao");
        ChildCourseSemesterDao daoAccessCCSemester = (ChildCourseSemesterDao) applicationContext.getBean("ChildCourseSemesterDao");
        ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        logger.info(request.getParameter("major"));
        logger.info(request.getParameter("course"));
        logger.info(request.getParameter("semester"));
        logger.info(request.getParameter("teacherId"));
        logger.info(request.getParameter("deadline"));
      //  logger.info(request.getParameter("childCourseName"));
        logger.info(request.getParameter("startDate"));


        int childId = daoAccessChildCourses.getTheLargestChildCourseId();
        if (childId == -1){
            childId = 0;
        }
        System.out.println("Start#"+childId);
//        System.out.println("id#"+ ++childId);

        long parentCourseId = Long.parseLong(request.getParameter("course"));
        //String childCourseName = request.getParameter("childCourseName");
        long teacherId = Long.parseLong(request.getParameter("teacherId"));
        String enrolmentStartDate = request.getParameter("startDate");
        String enrolmentDeadline = request.getParameter("deadline");
        int semesterId = Integer.parseInt(request.getParameter("semester"));

        ChildCourses childCourse = new ChildCourses();
        childCourse.setChildCourseId(++childId);
        childCourse.setParentCourseId(parentCourseId);
       // childCourse.setChildCourseName(childCourseName);
        childCourse.setTeacherId(teacherId);
        childCourse.setSemesterId(semesterId);
        childCourse.setEnrolmentDeadline(enrolmentDeadline);
        childCourse.setCreatedBy(currentUserId);
        childCourse.setEnrolmentStartDate(enrolmentStartDate);

        if(daoAccessChildCourses.checkIfChildCourseExistsForParentCourseAtSemester(childCourse.getParentCourseId(),semesterId,childCourse.getChildCourseId())){
            System.out.println("400 Fail -- Child Course already exists");
            return "400";
        }else{
            daoAccessChildCourses.createNewChildCourse(childCourse);
         //   daoAccessPCChild.addChildCourseToParentCourse(childCourse.getParentCourseId(),childCourse.getChildCourseId());
          //  daoAccessCCSemester.addChildCourseToSemester(childCourse.getParentCourseId(),childCourse.getChildCourseId(),semesterId);
            System.out.println("200 -- Sucess");
            return "200";
        }

    }


    /**
     * ADD COURSE FORM
     */
    @RequestMapping(value = "admin/manage-courses/add",method = RequestMethod.GET)
    public String adminAddCourse(Model model){
        UserDao daoAccessU = (UserDao) applicationContext.getBean("userDao");
        SemesterDao daoAccessSems = (SemesterDao) applicationContext.getBean("SemesterDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");

        model.addAttribute("teachersList",daoAccessU.getTeachersList());
        model.addAttribute("semesterList", daoAccessSems.getSystemSemesterList());
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("lastAdded",daoAccessChildCourses.getLastAddedChildCourses());

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
     * UN ENROLL STUDNET FROM COURSE
     */
    @ResponseBody
    @RequestMapping(value = "/student/course/un-enroll/",method = RequestMethod.POST)
    public String unEnrollStudentFromCourse(Model model,
                                            @RequestParam("childCourseId") int childCourseId){
         ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        logger.info("CourseId  = " +childCourseId);


        if(checkIfDateIsStillActive( daoAccessChildCourses.getChildCourseEnrollmentDeadlineDate(childCourseId))){
            if(!daoAccessChildCourses.checkIfChildCourseExamHasGradeItems(childCourseId)){

                if(daoAccessChildCourses.removeStudentFromChildCourse(childCourseId,currentUserId).equals("200")){
                    System.out.println("@200 student un-enroll from course success");
                    return "200";
                }else{
                    System.out.println("@400 Query Error request failed");
                    return "400";
                }

            }else{
                System.out.println("@400 course has grade items meaning course has already started  ");
                return "400";
            }
        }else{
            System.out.println("@400 failed ! Course Enrollment period has ended  ");
            return "400";
        }
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
            String confirm = daoAccess.deleteChildCourseFromChildCourseTable(courseId);

            return confirm;

        }catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }

    }

    /**
     * ADMIN CLOSE COURSE FROM WHOLE SYSTEM
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-courses/closeCourse/{courseId}",method = RequestMethod.POST)
    public String closeCourseFromSystem(Model model,@PathVariable int courseId){

        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");

        try {

            String confirm = daoAccess.closeCourse(courseId);

            return confirm;

        }catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }

    }



    /**
     * ADMIN CLOSE COURSE FROM WHOLE SYSTEM
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-courses/closeCoursesBySemester/{semesterId}",method = RequestMethod.POST)
    public String closeCoursesBySemester(Model model,@PathVariable int semesterId){

        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");

        try {

            String confirm = daoAccess.closeCoursesBySemesterId(semesterId);

            return confirm;

        }catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }

    }


}
