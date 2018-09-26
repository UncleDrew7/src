package com.cdai.controllers.appController;

import build.dao.*;
import build.model.*;
import com.alibaba.fastjson.JSONObject;
import com.cdai.models.tempModels.ExcelExam;
import com.cdai.models.tempModels.ExcelStudent;
import com.cdai.security.UserUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.scene.control.Alert;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.cdai.util.ExamEnrollmentUtils.*;
import static com.cdai.util.ExamEnrollmentUtils.enrolmentRequestLedger;
import static com.cdai.util.StudentGradeAndReportingUtil.getForChildCourseExamList;

/**
 * Created by apple on 19/08/2017.
 */
@Controller
public class ExamController {
    private static final Logger logger = Logger.getLogger(AppAdminController.class);
    private static final  ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");

    private static ExamDao daoAccessExams = (ExamDao) applicationContext.getBean("ExamDao");
 //   private static userDao daoAccessUsers = (userDao) applicationContext.getBean("userDao");

    private static ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");
    private static GradeItemsDao daoAccessGradeItem = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
    private static CourseEnrolmentDao daoAccessCourseEnroll = (CourseEnrolmentDao ) applicationContext.getBean("CourseEnrolmentDao");

    /**
     * RETURN ADD EXAM FORM
     */
    @RequestMapping(value = "admin/manage-exams/add",method = RequestMethod.GET)
    public String addExam(Model model){
        ParentCoursesDao daoAccess = (ParentCoursesDao ) applicationContext.getBean("ParentCoursesDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        SemesterDao daoAccessSemester = (SemesterDao) applicationContext.getBean("SemesterDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("semesterSelectList",daoAccessSemester.getSystemSemesterList());
        model.addAttribute("examFormSelectCourseList",daoAccess.getAllParentCourses());
        model.addAttribute("lastAddedList",daoAccessExam.getLastAddedExamList());
        model.addAttribute("teacherSelectList",daoAccessUser.getTeachersList());

        return "Admin_App/subPages/addExamForm";
    }

    /**
     * RETURN ADD EXAM FORM
     */
    @RequestMapping(value = "admin/manage-exams/add/{parentCourseId}",method = RequestMethod.GET)
    public String addExamOfParentCourseId(Model model, @PathVariable int parentCourseId){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        SemesterDao daoAccessSemester = (SemesterDao) applicationContext.getBean("SemesterDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("semesterSelectList",daoAccessSemester.getSystemSemesterList());
        model.addAttribute("examFormSelectCourseList",daoAccess.getActiveCourseListForFormSelect( parentCourseId ));
        model.addAttribute("lastAddedList",daoAccessExam.getLastAddedExamList());
        model.addAttribute("teacherSelectList",daoAccessUser.getTeachersList());

        return "Admin_App/subPages/addExamFormbyParentCourseId";
    }

    /**
     * RETURN ADD INTERMEDIATE CLEARANCE EXAM FORM
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-exams/exam/add-clearance-exam/check/{parentExamId}",method = RequestMethod.POST)
    public String checkIfCleareanceExamExist(Model model, @PathVariable int parentExamId){
        //   GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        SemesterDao daoAccessSemester = (SemesterDao) applicationContext.getBean("SemesterDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        model.addAttribute("currentUserRole",currentUserRole);
        //    model.addAttribute("parentCourseId",parentCourseId);
        model.addAttribute("parentExamId",parentExamId);

        if ( !daoAccessExam.checkIfParentExamAlreadyHasAClearanceExam( parentExamId) ) {
           return "200";
        }

        return "400";
    }

    /**
     * RETURN ADD INTERMEDIATE CLEARANCE EXAM FORM
     */
    @RequestMapping(value = "admin/manage-exams/exam/add-clearance-exam/{parentExamId}",method = RequestMethod.GET)
    public String getAddIntermediateClearanceExamPage(Model model, @PathVariable int parentExamId){
     //   GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        SemesterDao daoAccessSemester = (SemesterDao) applicationContext.getBean("SemesterDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        model.addAttribute("currentUserRole",currentUserRole);
    //    model.addAttribute("parentCourseId",parentCourseId);
        model.addAttribute("parentExamId",parentExamId);
        model.addAttribute("lastAddedList",daoAccessExam.getLastAddedClearanceExams());
        model.addAttribute("parentExamData",daoAccessExam.getExamDataForFormDisplayByExamId(parentExamId));


        return "Admin_App/subPages/addClearanceExamForm";
    }


    /**
     * RETURN EDIT EXAM FORM PAGE
     */
    @RequestMapping(value = "admin/manage-exams/edit/{examId}",method = RequestMethod.GET)
    public String editExam(Model model, @PathVariable int examId){
        //GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");

        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("examId",examId);
        model.addAttribute("examData",daoAccessExam.getExamDataForFormDisplayByExamId(examId));
        model.addAttribute("lastEditedList",daoAccessExam.getLastEditedExamList());
        model.addAttribute("hasPermission", daoAccessExam.checkPermissionForTeacherAgainstExamId(currentUserId,examId));


        return "Admin_App/subPages/editExamForm";
    }

    /**
     * GET ENROLLMENT LIST
     */
    @RequestMapping(value = "admin/manage-exams/enrolmentList/{examId}/{nav}",method = RequestMethod.GET)
    public String displayExamEnrolmentList(Model model, @PathVariable int examId,@PathVariable int nav){
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
      //  GradeItemsDao daoAccessGradeItems = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        ExamEnrolmentDao daoAccessExamEnrollment = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");

        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        model.addAttribute("examId",examId);
        model.addAttribute("nav",nav);
        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("enrolmentList",daoAccess.getExamEnrollmentListByExamId(examId));
        model.addAttribute("requestList",daoAccess.getExamEnrollmentRequestList(examId));
        model.addAttribute("examDetails",daoAccessExam.getSingleExamFullDetailsByExamId(examId));
        model.addAttribute("hasPermission", daoAccessExam.checkPermissionForTeacherAgainstExamId(currentUserId,examId));

        return "Admin_App/subPages/examEnrolmentList";
    }

    /**
     * RETURN EXAM STUDENT ENROLLMENT PAGE
     */
    @RequestMapping(value = "admin/manage-exam/enroll/{examId}/{nav}",method = RequestMethod.GET)
    public String examStudentEnrollmentPage(Model model, @PathVariable int examId, @PathVariable int nav){
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
      //  GradeItemsDao daoAccessGradeItems = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");

        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        model.addAttribute("examId",examId);
        model.addAttribute("nav",nav);
     //   model.addAttribute("courseId",courseId);
        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("studentsEnrolledInExamList",daoAccess.getStudentsEnrolledInExamList(examId));
        model.addAttribute("studentsNotEnrolledInExamList",daoAccess.getStudentsNotEnrolledInExamList(examId));
        model.addAttribute("examDetails",daoAccessExam.getSingleExamFullDetailsByExamId(examId));
        model.addAttribute("hasPermission", daoAccessExam.checkPermissionForTeacherAgainstExamId(currentUserId,examId));


        return "Admin_App/subPages/examStudentEnrolmentPage";
    }

    /**
     * ADMIN COURSE EXAM PAGE Course Exam Page
     */
    @RequestMapping(value = "admin/manage-courses/course-exam/{courseId}",method = RequestMethod.GET)
    public String displayCourseExamPage(Model model, @PathVariable int courseId){
    //    GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");

        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        model.addAttribute("courseId",courseId);
        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("courseNames",daoAccessCourses.getCourseNameByCourseId(courseId));
        model.addAttribute("examList",getForChildCourseExamList(courseId));
        model.addAttribute("hasPermission", daoAccessCourses.checkPermissionForTeacherAgainstCourseId(currentUserId,courseId));


        return "Admin_App/subPages/courseExamPage";
    }



    /**
     * PROCESS EDIT EXAM FORM
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-exams/edit/{examId}",method = RequestMethod.POST)
    public String processEditExam(Model model, @PathVariable int examId , HttpServletRequest request){
     //   GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");

        logger.info(request.getParameter("exName"));
        logger.info(request.getParameter("examDate"));
        logger.info(request.getParameter("enrolmentStartDate"));
        logger.info(request.getParameter("enrolmentEndDate"));


        logger.info(request.getParameter("minGrade"));
        logger.info(request.getParameter("maxGrade"));
        logger.info(request.getParameter("exWeight"));

        Exam newExam = new Exam();

        newExam.setId(examId);
        newExam.setExamName(request.getParameter("exName"));
        newExam.setDateOfExam(request.getParameter("examDate"));
        newExam.setEnrolmentStartDate(request.getParameter("enrolmentStartDate"));
        newExam.setEnrolmentCloseDate(request.getParameter("enrolmentEndDate"));
        newExam.setMinGrade(Integer.parseInt(request.getParameter("minGrade")));
        newExam.setMaxGrade(Integer.parseInt(request.getParameter("maxGrade")));
        newExam.setWeight(Double.parseDouble(request.getParameter("exWeight")));

        if(daoAccessExam.editExam(newExam).equals("200")){
            System.out.println("@::Exam Update Complete and Successful::200");
            return "200";
        }else{
            System.out.println("@::Exam Updated Complete and Failed::400");
            return "400";
        }

    }

    /**
     * DEACTIVATE EXAM
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-exams/deactivate/{examId}",method = RequestMethod.POST)
    public String deactivateExam(Model model, @PathVariable int examId ){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");

        logger.info(examId);
        String confirm = daoAccess.deActivateExam(examId);

        return confirm;
    }



    /**
     * Return Exam Page
     */
    @RequestMapping(value = "admin/view/exam-page",method = RequestMethod.GET)
    public String displayExamDetailsPage(Model model){
        return "Admin_App/subPages/examPage";
    }


    /**
     * STUDENT APP :: RETURN STUDENT CUSTOMIZED LATEST EXAMS
     */
    @ResponseBody
    @RequestMapping(value = "student/get-student-latest-exams",method = RequestMethod.GET)
    public List<GradeItems> getStudentLatestExams(Model model){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        return  daoAccess.getLatestExamsForStudent(currentUserId);
    }

    /**
     * PROCESS AND ADD STUDENTS GRADES
     */
    @ResponseBody
    @RequestMapping(value = "admin/exam/grade/updateCleExamGrades/{clexamId}",method =RequestMethod.POST)
    public JSONObject adminUpdateCleExamGrade(@RequestBody List<ExamEnrolment> grades, Model model, @PathVariable int clexamId){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        ExamDao daoAccess = (ExamDao) applicationContext.getBean("ExamDao");
        ExamEnrolmentDao daoAccessExamEnroll = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");
        String confirm ="";

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();


        for(ExamEnrolment grade : grades){
            if(grade.getStudentId() != 0 ){
                grade.setCleExamID(clexamId);
                grade.setStudentId(grade.getStudentId());
                grade.setStudentName(grade.getStudentName());
                grade.setClexamGrade(grade.getClexamGrade());
                logger.info(grade.getStudentId());
                logger.info(clexamId);
                logger.info(grade.getClexamGrade());

                confirm = daoAccessExamEnroll.updateClexamGrade(grade);

                /*if( daoAccess.checkIfStudentHasGradeForGradeItem(gradeItemId,grade.getStudentId())){
                    confirm = daoAccess.updateGrade(grade);
                }else{
                    confirm = daoAccess.addGrade(grade);
                }*/

                System.out.println(grade.getStudentName()+"::>> " +confirm );

            }

        }

        JSONObject rtn = new JSONObject();

        rtn.put("msg","Success");
        return rtn;


    }

    /**
     * PROCESS AND ADD STUDENTS GRADES
     */
    @ResponseBody
    @RequestMapping(value = "admin/exam/grade/updateExamGrades260/{clexamId}",method = RequestMethod.POST)
    public JSONObject adminUpdateExamGrade260(@RequestBody List<ExamEnrolment> grades, Model model, @PathVariable int clexamId){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
        ExamEnrolmentDao daoAccessExamEnroll = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");
        String confirm ="";

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();

        daoAccessExam.setClexamSubmitted(clexamId);
        int pExamId = daoAccessExam.getClearanceExamParentExam(clexamId);
        int pcId = daoAccessExam.getParentCourseId(pExamId);
        double weight = daoAccessExam.getExamWeight(pExamId);
        double newScoreDouble = 60 * weight;
        DecimalFormat decimalFormat = new DecimalFormat("###.##");//格式化设置
        String newScore = decimalFormat.format(newScoreDouble);

        for(ExamEnrolment grade : grades){
            if(grade.getStudentId() != 0 && grade.getClexamGrade().equals("Passed") ){
                grade.setCleExamID(clexamId);
                grade.setStudentId(grade.getStudentId());
                grade.setExamScore(newScore);
                logger.info(grade.getStudentId());
             //   logger.info(clexamId);
                logger.info(grade.getExamScore());

             //   confirm = daoAccessExamEnroll.updateExamGrades260(grade);

                try {

                 //   List<ExamEnrolment> stuList = daoAccessExamEnroll.getExamStudentGrades(examId);

                //    for ( ExamEnrolment stu:stuList ) {
                        long studentId = grade.getStudentId();
                      //  String examResult = stu.getExamResult();
                      //  double examResultDouble = Double.parseDouble(examResult);
                        int courseId = daoAccessExam.getChildCourseId(pcId, studentId);
                        int enrolls = daoAccessExamEnroll.getEnrollsOfExam(pExamId, studentId);
                        double courseScore = Double.parseDouble( daoAccessCourseEnroll.getCourseScore(courseId, studentId) );

                        switch ( enrolls ){
                            case 1:
                                confirm = daoAccessExamEnroll.updateExam1( courseId, studentId, newScore);
                                confirm = daoAccessExamEnroll.updateFinal1( courseId, studentId );
                                break;
                            case 2:
                                confirm = daoAccessExamEnroll.updateExam2(courseId, studentId, newScore);
                                confirm = daoAccessExamEnroll.updateFinal2(courseId, studentId);
                                break;
                            case 3:
                                confirm = daoAccessExamEnroll.updateExam3(courseId, studentId, newScore);
                                confirm = daoAccessExamEnroll.updateFinal3(courseId, studentId);
                                break;
                        }

                        if ( newScoreDouble + courseScore > 60 ){
                            String highest = decimalFormat.format(newScoreDouble + courseScore);
                            daoAccessExamEnroll.updateCourseFinish(courseId, studentId);
                            daoAccessExamEnroll.updateCourseHighest(courseId, studentId, highest);

                        }

//                else
//                    daoAccessExamEnroll.updateCourseNotFinish(courseId, studentId);

                //    }
                    System.out.println( confirm);

                }catch (Exception e){
                    e.printStackTrace();
                //    return "Error!";
                }

                System.out.println(grade.getStudentName()+"::>> " +confirm );

            }

        }

        JSONObject rtn = new JSONObject();

        rtn.put("msg","Success");
        return rtn;


    }

    /**
     * PROCESS AND ADD STUDENTS GRADES
     */
    @ResponseBody
    @RequestMapping(value = "admin/exam/grade/updateExamGrades/{examId}",method = RequestMethod.POST)
    public JSONObject adminUpdateExamGrade(@RequestBody List<ExamEnrolment> grades, Model model, @PathVariable int examId){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        ExamDao daoAccess = (ExamDao) applicationContext.getBean("ExamDao");
        ExamEnrolmentDao daoAccessExamEnroll = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");
        String confirm ="";

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();


        for(ExamEnrolment grade : grades){
            if(grade.getStudentId() != 0 ){
                grade.setExamId(examId);
                grade.setStudentId(grade.getStudentId());
                grade.setExamScore(grade.getExamScore());
                logger.info(grade.getStudentId());
                logger.info(examId);
                logger.info(grade.getExamScore());

                confirm = daoAccessExamEnroll.updateGrade(grade);

                /*if( daoAccess.checkIfStudentHasGradeForGradeItem(gradeItemId,grade.getStudentId())){
                    confirm = daoAccess.updateGrade(grade);
                }else{
                    confirm = daoAccess.addGrade(grade);
                }*/

                System.out.println(grade.getStudentName()+"::>> " +confirm );

            }

        }

        JSONObject rtn = new JSONObject();

        rtn.put("msg","Success");
        return rtn;

//        resultList = ()request.getParameter("grades");



//        for(int i = 0; i < resultList.size(); i++){
//            JSONObject param = resultList.getJSONObject(i);
//            grade.setStudentId(Integer.valueOf((String) param.get("StudentId")));
//            grade.setGrade(Integer.valueOf((String) param.get("grade")));
//            confirm = daoAccess.addGrade(grade);
//            System.out.println(param.get("StudentId")+"::>> " +confirm );
//        }



    }


    /**
     * ADMIN GET COURSE STUDENT GRADE OVERVIEW
     */
    @RequestMapping(value ={ "admin/exam/grade/addExamGrades/{examId}"},method = RequestMethod.GET)
    public String addExamStudentGrades(Model model ,@PathVariable int examId){
       // GradeDao daoAccessGrade = (GradeDao) applicationContext.getBean("GradeDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
        ExamEnrolmentDao daoAccessExamEnroll = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");
//        EventsDao daoAccessEvents = (EventsDao) applicationContext.getBean("EventsDao");

        User user = UserUtils.getUser();
        long userId = user.getUserId();


        model.addAttribute("examId",examId);
        model.addAttribute("examDetail",daoAccessExam.getSingleExamFullDetailsByExamId(examId));
        model.addAttribute("examGrades",daoAccessExamEnroll.getExamStudentGrades(examId));
        model.addAttribute("studentEnrolledList",daoAccessExamEnroll.getEnrolledStudentList(examId));
               // model.addAttribute("upcomingEvents", daoAccessEvents.displayAdminUpcomingEvent(userId));


        return "Admin_App/subPages/addExamGrades";
    }

    /**
     * ADMIN GET COURSE STUDENT GRADE OVERVIEW
     */
    @RequestMapping(value ={ "admin/exam/grade/overview/{examId}"},method = RequestMethod.GET)
    public String getStudentExamGradesOverview(Model model ,@PathVariable int examId){
      //  GradeDao daoAccessGrade = (GradeDao) applicationContext.getBean("GradeDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
        ExamEnrolmentDao daoAccessExamEnroll = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");

        User user = UserUtils.getUser();
        long userId = user.getUserId();


        model.addAttribute("examId",examId);
        model.addAttribute("examDetail",daoAccessExam.getSingleExamFullDetailsByExamId(examId));
        model.addAttribute("examGrades",daoAccessExamEnroll.getExamStudentGrades(examId));
        model.addAttribute("studentEnrolledList",daoAccessExamEnroll.getEnrolledStudentList(examId));
       // model.addAttribute("courseName",daoAccessCourse.getCourseNameByCourseId(examId));
        // model.addAttribute("upcomingEvents", daoAccessEvents.displayAdminUpcomingEvent(userId));


        return "Admin_App/subPages/examStudentGradeOverview";
    }


    /**
     * STUDENT MAKE EXAM ENROLLMENT REQUEST
     */
    @ResponseBody
    @RequestMapping(value = "student/exam/request-enrollment/{examId}",method = RequestMethod.POST)
    public String makeStudentExamEnrollmentRequst(Model model,@PathVariable int examId){
        ExamEnrolmentDao daoAccessExamEnrollment = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        long userId = UserUtils.getUser().getUserId();
        long studentId = userId;
        long parentCourseId = daoAccessExamEnrollment.getParentCourseId(examId);
        int childCourseId = daoAccessCourseEnroll.getCourseId(parentCourseId, studentId);
        int enrolls = daoAccessExamEnrollment.getCountOfEnrolledExams((int) parentCourseId, studentId);
        String degreeType = daoAccessUser.getStudentDegreeType(userId);

        if ( enrolls >= 3 && degreeType.equals("Double-Degree") ){
            //stop enrollment no available attempts --------
            System.out.println("400------ERROR DOUBLE DEGREE USER HAS ALREADY ATTEMPTED EXAM MORE THAN 3 TIME ");
            return "430";
        }else
            enrolls = enrolls + 1;

      //  long parentCourseId = daoAccessChildCourses.getChildCourseParentId(examId);
//        int childCourseId = 1;  //暂留，待修正


        if(examEnrolmentDateCheck(examId)) {
            //true date still open contitue
//            if(attemptsAvailable(studentId,parentCourseId)){
                //attempts still avialabe
//                if(!checkIfLastMostResentTakenExamHelpedPassCourse(studentId,parentCourseId,childCourseId )){
                  if(!checkIfLastMostResentTakenExamHelpedPassCourse(studentId,childCourseId)){
//                    if(clearanceExamEligibilityCheck(studentId,parentCourseId,childCourseId)){
//                        //offer cleareance exam to be enrolled into ====|
//                        System.out.println("200------ELIGIBLE BUT OFFERING CLEARANCE EXAM INSTEAD OF ENROLLING STUDENT IN REQUESTED EXAM");
//                        return "240";
//
//                    }else{
                        //create new exam  =====|
                        if(!daoAccessExamEnrollment.checkIfStudentIsAlreadyEnrolld(examId,studentId)){
                            System.out.println("-------------------------200------------------------");
                            enrolmentRequestLedger(examId, studentId);
                            System.out.println(daoAccessExamEnrollment.acceptStudentEnrolmentRequest(examId, studentId));
                            System.out.println(daoAccessExamEnrollment.enrollStudentInExam(new ExamEnrolment(examId, studentId, enrolls)));
                            return "200";
                        }else{
                            // already enrolled in exam exit --------------
                            System.out.println("400------ERROR USER ALREADY ENROLLED IN EXAM");
                            return "410";
                        }
//                    }

                }else{
                    //returened true course passed exit enrolment -------
                    System.out.println("400------ERROR USER ALREADY PASSED EXAM");
                    return "420";
                }

           /* }else{
                //stop enrollment no available attempts --------
                System.out.println("400------ERROR DOUBLE DEGREE USER HAS ALREADY ATTEMPTED EXAM MORE THAN 3 TIME ");
                return "430";
            }*/

        }else {
            //stop enrollment data has passed ---------
            System.out.println("400------ERROR EXAM ENROLMENT DEADLINE HAS PASSED ENROLLMENT INTO EXAM IS CLOSED ");
            return "440";

        }

    }

    /**
     * ENROLL STUDENT IN CLEXAM
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-clexam/enroll/{clexamId}",method = RequestMethod.POST)
    public String enrollStudentInClexam(Model model, @PathVariable int clexamId, HttpServletRequest request){
        ExamDao daoAccess = (ExamDao) applicationContext.getBean("ExamDao");

        logger.info(request.getParameter("userId"));
        logger.info(clexamId);
        logger.info(request.getParameter("method"));

        String confirm = "";
        long userId = Long.parseLong(request.getParameter("userId"));

        confirm = daoAccess.enrollStudentInClexam(clexamId,userId);

        logger.info(confirm);

        return "Exam Enrollment Sucessfull ";
    }

    /**
     * UN-ENROLL STUDENT FROM EXAM
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-clexam/un-enroll/{clexamId}",method = RequestMethod.POST)
    public String unEnrollStudentFromClexam(Model model, @PathVariable int clexamId , HttpServletRequest request){

        ExamEnrolmentDao daoAccess = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");

        String confirm = "";
        logger.info(request.getParameter("userId"));
        logger.info(clexamId);
        //  logger.info(courseId);
        long userId = Long.parseLong(request.getParameter("userId"));

        confirm = daoAccess.unEnrollStudentFromClexam(clexamId, userId);

        logger.info(confirm);

        return "Exam UNEnrollment Sucessfull ";
    }


    /**
     * ENROLL STUDENT IN EXAM
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-exam/enroll/{examId}",method = RequestMethod.POST)
    public String enrollStudentInExam(Model model, @PathVariable int examId,HttpServletRequest request){
        ExamEnrolmentDao daoAccessEn = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        logger.info(request.getParameter("userId"));
        logger.info(examId);
        logger.info(request.getParameter("method"));

        String confirm = "";
        long userId = Long.parseLong(request.getParameter("userId"));

        int pCourseId = daoAccessExam.getParentCourseId(examId);
        int enrolls = daoAccessEn.getCountOfEnrolledExams(pCourseId,userId);

        if ( !daoAccessUser.getStudentDegreeType( userId ).equals( "Double-Degree")){
            enrolls++;
            confirm = daoAccessEn.enrollStudentInExam(new ExamEnrolment(examId, userId, enrolls));
        }else {
            if ( enrolls < 3 ) {
                enrolls = enrolls + 1;
                confirm = daoAccessEn.enrollStudentInExam(new ExamEnrolment(examId, userId, enrolls));
            }
            else return "You have already registered 3 times!";
        }



     /*   int check = daoAccess.checkIfStudentIsAlreadyEnrolled(examId,userId);
        if(check == -1){
            logger.info( "has never been enrolled before ");
            confirm = daoAccess.enrollStudentInExam(new ExamEnrolment(examId,userId));
            if(request.getParameter("method").equals("request")){
                String confirm2 = daoAccess.acceptStudentEnrolmentRequest(examId, userId);
                logger.info( "Accpt -"+confirm2);
            }


        }else if(check == 0){
            logger.info("has been enrolled before ");
            confirm = daoAccess.reEnrollPreviouslyUnenrolledStudent(examId,userId);

        }*/
        logger.info(confirm);

        return "Exam Enrollment Sucessfull ";
    }

    /**
     * UN-ENROLL STUDENT FROM EXAM
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-exam/un-enroll/{examId}",method = RequestMethod.POST)
    public String unEnrollStudentFromExam(Model model, @PathVariable int examId , HttpServletRequest request){

        ExamEnrolmentDao daoAccess = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");

        String confirm = "";
        logger.info(request.getParameter("userId"));
        logger.info(examId);
      //  logger.info(courseId);
        long userId = Long.parseLong(request.getParameter("userId"));

        confirm = daoAccess.unEnrollStudentFromExam(examId, userId);

        logger.info(confirm);

        return "Exam UNEnrollment Sucessfull ";
    }

    /**
     * DElETE STUDENT ENROLMENT REQUEST
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-exam/enrollment-delete/{examId}",method = RequestMethod.POST)
    public String deleteStudentExamEnrolmentRequest(Model model, @PathVariable int examId, HttpServletRequest request){
        ExamEnrolmentDao daoAccess = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");

        logger.info(request.getParameter("userId"));
        logger.info(examId);

        String confirm = "";
        long userId = Long.parseLong(request.getParameter("userId"));

        confirm = daoAccess.deleteStudentEnrollmentRequest(examId,userId);
        logger.info(confirm);

        return "Decline Sucessfull ";
    }






    /**
     * Return Student Exam Enrolment Request List
     */
    @RequestMapping(value = "admin/manage-exams/enrolment-request-list",method = RequestMethod.GET)
    public String displayExamEnrolmentRequestList(Model model){

        return "Admin_App/subPages/examStudentRequestList";
    }




    /**
     * Course  Enrolment List
     */
    @RequestMapping(value = "admin/manage-courses/course-enrolment-list",method = RequestMethod.GET)
    public String displayCourseEnrolmentList(Model model){
        return "Admin_App/subPages/courseEnrolmentList";
    }






    /**
     * ADD NEW EXAM
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-exams/add-exam",method = RequestMethod.POST)
    public String addNewExam(Model model, HttpServletRequest request){

        long userId = UserUtils.getUser().getUserId();
        logger.info("...Ripper starting execution now.......!");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");



        logger.info(userId);
        logger.info(request.getParameter("parentCourseId"));
        logger.info(request.getParameter("semesterId"));
        logger.info(request.getParameter("teacherId"));
        logger.info(request.getParameter("examName"));
        logger.info(request.getParameter("maxGrade"));
        logger.info(request.getParameter("minGrade"));
        logger.info(request.getParameter("examDate"));
        logger.info(request.getParameter("enrolmentDeadLine"));
        logger.info(request.getParameter("weight"));

     //   long parentCourseId = daoAccessChildCourses.getChildCourseParentId(Integer.parseInt(request.getParameter("childCourseId")));
        int parentCourseId = Integer.parseInt(request.getParameter("parentCourseId"));
        int semesterId = Integer.parseInt(request.getParameter("semesterId"));
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        int minGrade = 0;
        int maxGrade = 100;
        String examName = request.getParameter("examName");
        double weight = Double.parseDouble(request.getParameter("weight"));

        if(!request.getParameter("minGrade").equals("") || request.getParameter("minGrade") != null){
            minGrade = Integer.parseInt(request.getParameter("minGrade"));
        }
        if(!request.getParameter("maxGrade").equals("") || request.getParameter("maxGrade") != null){
            maxGrade = Integer.parseInt(request.getParameter("maxGrade"));
        }

        Exam exam = new Exam();
        exam.setParentCourseId(parentCourseId);
      //  exam.setChildCourseId(childCourseId);
        exam.setSemesterIdId(semesterId);
        exam.setTeacherId(teacherId);
        exam.setExamName(examName);
        exam.setMaxGrade(maxGrade);
        exam.setMinGrade(minGrade);
        exam.setDateOfExam(request.getParameter("examDate"));
        exam.setEnrolmentCloseDate(request.getParameter("enrolmentDeadLine"));
        exam.setCreatedBy(currentUserId);
        exam.setWeight(weight);

        daoAccessExams.addNewExam(exam);

        return "200";

        /*if (!daoAccessChildCourses.checkIfChildCourseExamIsInSystem(childCourseId,examName)){
            daoAccessGradeItem.createChildCourseExam(exam);
            return "200";
        }else{
            System.out.println("----400 -----Error Exam in System");
            return "400";
        }*/

    }

    /**
     * ADD NEW EXAM
     */
    @ResponseBody
    @RequestMapping(value = " admin/manage-exams/exam/process/add-clearance-exam/{parentExamId}",method = RequestMethod.POST)
    public String addClearanceExam(Model model,@PathVariable int parentExamId, HttpServletRequest request){
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");

        long userId = UserUtils.getUser().getUserId();
        logger.info("...Ripper starting execution now.......!");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        logger.info(userId);
        logger.info(parentExamId);
        logger.info(request.getParameter("examName"));
        logger.info(request.getParameter("examDate"));

        String clearanceExamName = request.getParameter("examName");
        String examDate = request.getParameter("examDate");
        long createdBy = currentUserId;

        ClearanceExam clearanceExam  = new ClearanceExam();
        clearanceExam.setParentExamId(parentExamId);
        clearanceExam.setExamName(clearanceExamName);
        clearanceExam.setExamDate(examDate);
        clearanceExam.setCreatedBy(createdBy);

        //check if clearance exam exists
        if(!daoAccessExam.checkIfParentExamAlreadyHasAClearanceExam(clearanceExam.getParentExamId())){
            if(daoAccessExam.createClearanceExam(clearanceExam).equals("200")){
                System.out.println("|                                                                                    |");
                System.out.println("------------------------------------200 EXAM CREATED 200------------------------------");
                return "200";
            }else{
                System.out.println("|                                                                                                        |");
                System.out.println("-------------------400 SYSTEM ERROR CREATING EXAM  400------------------------------");
                return "400";
            }
        }else{
            System.out.println("|                                                                                                        |");
            System.out.println("----------------------400 ERROR PARENT EXAM SELECTED HAS CLEARENCE EXAM 400------------------------------");
            return "400";
        }

    }





    /**
     * UPDATE CURRENT EXAM
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-exam/update",method = RequestMethod.POST)
    public String updateCurrentExam(Model model){
        return "Admin_App/subPages/courseExamPage.ripper.xml";
    }


    /**
     * DELETE EXISTING EXAM
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-exam/delete",method = RequestMethod.POST)
    public String deleteExistingExam(Model model){
        return "Admin_App/subPages/courseExamPage.ripper.xml";
    }



    /**
     * ADMIN DELETE EXAM FROM WHOLE SYSTEM
     */
    @ResponseBody
    @RequestMapping(value = "/admin/manage-exams/delete-exam/{examId}",method = RequestMethod.POST)
    public String deleteExamFromSystem(Model model,@PathVariable int examId){

     //   GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");

        try {
            /*daoAccessExam.deleteExamFromExamEnrolment(examId);
            daoAccessExam.deleteExamFromExamStudentRequestEnrolment(examId);
            daoAccessExam.deleteExamFromGrade(examId);
            String confirm = daoAccessExam.deleteExamFromGradeItems(examId);*/

            String confirm = "not deleted!";
            return confirm;

        }catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }


    }


    @ResponseBody
    @RequestMapping(value = "/admin/manage-exams/deleteClexam/{examId}",method = RequestMethod.POST)
    public String deleteClExamFromSystem(Model model,@PathVariable int examId){

        //   GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");

        try {
            /*daoAccessExam.deleteExamFromExamEnrolment(examId);
            daoAccessExam.deleteExamFromExamStudentRequestEnrolment(examId);
            daoAccessExam.deleteExamFromGrade(examId);
            String confirm = daoAccessExam.deleteExamFromGradeItems(examId);*/

            String confirm = "not deleted!";
            return confirm;

        }catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }

    }

    @RequestMapping(value = "/admin/exam/grade/updateExamGradesToCourse/{examId}",method = RequestMethod.GET)
    public String updateExamGradesToCourse(Model model,@PathVariable int examId ){

        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
        ExamEnrolmentDao daoAccessExamEnroll = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");
        CourseEnrolmentDao daoAccessEnroll = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");

        String confirm = "";
        int courseId = 0;

        try {
            int pcId = daoAccessExam.getParentCourseId(examId);
            List<ExamEnrolment> stuList = daoAccessExamEnroll.getExamStudentGrades(examId);

            for ( ExamEnrolment stu:stuList ) {
                long studentId = stu.getStudentId();
                String examResult = stu.getExamResult();
                double examResultDouble = Double.parseDouble(examResult);
                courseId = daoAccessExam.getChildCourseId(pcId, studentId);
                int enrolls = daoAccessExamEnroll.getEnrollsOfExam(examId, studentId);
                double courseScore = Double.parseDouble( daoAccessEnroll.getCourseScore(courseId, studentId) );

                double highestGrade =  Double.parseDouble( daoAccessEnroll.getCourseHighestGrade(courseId, studentId) );

                switch ( enrolls ){
                    case 1:
                        confirm = daoAccessExamEnroll.updateExam1( courseId, studentId, examResult);
                        confirm = daoAccessExamEnroll.updateFinal1( courseId, studentId );
                        break;
                    case 2:
                        confirm = daoAccessExamEnroll.updateExam2(courseId, studentId, examResult);
                        confirm = daoAccessExamEnroll.updateFinal2(courseId, studentId);
                        break;
                    case 3:
                    default:
                        confirm = daoAccessExamEnroll.updateExam3(courseId, studentId, examResult);
                        confirm = daoAccessExamEnroll.updateFinal3(courseId, studentId);
                        break;

                }

                if ( examResultDouble + courseScore > 60 )
                    daoAccessExamEnroll.updateCourseFinish(courseId, studentId);

                if ( examResultDouble + courseScore > highestGrade ){
                    DecimalFormat decimalFormat = new DecimalFormat("###.##");//格式化设置
                    String highest = decimalFormat.format(examResultDouble + courseScore);

                    daoAccessExamEnroll.updateCourseHighest(courseId, studentId, highest );

                }


//                else
//                    daoAccessExamEnroll.updateCourseNotFinish(courseId, studentId);

            }
            System.out.println( confirm);

        }catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }


        model.addAttribute("courseId",courseId);
//        model.addAttribute("studentEnrolledList",daoAccessUser.getStudentEnrolledInCourseWithGradeAverage(courseId));
        model.addAttribute("courseName",daoAccessCourse.getCourseNameByCourseId(courseId));
        model.addAttribute("courseAllGrades", daoAccessEnroll.getAllCourseGradesListByCourseId(courseId));
        return "Admin_App/subPages/courseAllGradeOverview";

    }

//    /**
//     * DOWNLOAD STUDENT GRADES LIST
//     */
//    @RequestMapping(value = "admin/download/student-grades-excel/{semesterId}/{majorId}/{intakeId}",method = RequestMethod.GET )
//    public ModelAndView downloadStudentGradesExcel(@PathVariable int semesterId, @PathVariable int majorId,
//                                                   @PathVariable int intakeId){
//        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
//        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");
//        CourseEnrolmentDao daoAccessCourseEnroll = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");
//
//        List<ExcelStudent> students = new ArrayList<ExcelStudent>();
//
//        List<User> list = daoAccessMajor.getAllStudentListByMajorAndIntake(majorId, intakeId);
//        List<Courses> list1 = daoAccess.getAllCourseListBySemesterAndMajorId(semesterId,majorId);
//        List<CourseEnrolment> list2 = daoAccessCourseEnroll.getAllCourseGradesListByMIS(majorId, intakeId, semesterId);
//
//        List<ExcelStudent> courseName = new ArrayList<ExcelStudent>();
//        for (Courses entity1: list1){
//            courseName.add( new ExcelStudent(entity1.getCourseName()));
//        }
//
//        List<ExcelStudent> student = new ArrayList<ExcelStudent>();
//        for (User entity: list ){
//            for(CourseEnrolment entity2: list2 ){
//                if (entity.getUserId() == entity2.getStudentId() ) {
//                    student.add( new ExcelStudent( entity.getUserId(), entity.getUserName(), entity2.getCourseCode(), entity2.getFinalAll() ));
//                }
//            }
//        }
//
//        students.add(new ExcelStudent(courseName,student));
//
//        return new ModelAndView("stdFinalGradeExcelTemplate", "students", students);
//    }


}
