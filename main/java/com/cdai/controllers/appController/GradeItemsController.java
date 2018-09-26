package com.cdai.controllers.appController;

import build.dao.ChildCoursesDao;
import build.dao.CoursesDao;
import build.dao.GradeDao;
import build.dao.GradeItemsDao;
import build.model.Exam;
import build.model.GradeItems;
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

import static com.cdai.util.ServiceUtil.checkIfDateIsStillActive;
import static com.cdai.util.StudentGradeAndReportingUtil.daoAccessExam;
import static com.cdai.util.StudentGradeAndReportingUtil.daoAccessGradeItem;
import static com.cdai.util.StudentGradeAndReportingUtil.getForChildCourseExamList;

/**`
 * Created by apple on 15/11/2017.
 */
@Controller
public class GradeItemsController {
    private static final Logger logger = Logger.getLogger(GradeItemsController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");



    /**
     * ADMIN RETURN COURSE GRADE ITEMS PAGE
     */
    @RequestMapping(value = "admin/grades/grade-items/{courseId}",method = RequestMethod.GET)
    public String gradeItems(Model model, @PathVariable int courseId){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");

        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        model.addAttribute("currentUserRole",currentUserRole);

        model.addAttribute("courseId",courseId);
        model.addAttribute("gradeItemsList",getForChildCourseExamList(courseId));
        model.addAttribute("courseName",daoAccessCourse.getCourseNameByCourseId(courseId));
        model.addAttribute("totalWeight",daoAccess.getGradeItemWeightForCourse(courseId));
        model.addAttribute("hasPermission", daoAccessCourse.checkPermissionForTeacherAgainstCourseId(currentUserId,courseId));

        return "Admin_App/subPages/gradeItems";
    }

    /**
     * ADMIN RETURN GRADE ITEMS USER COURSE REPORT
     */
    @RequestMapping(value = "admin/grades/grade-items/{courseId}/{userId}",method = RequestMethod.GET)
    public String gradeItemsUserCourseGradeItemsReport(Model model, @PathVariable int courseId,@PathVariable long userId){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");

        model.addAttribute("courseId",courseId);
        model.addAttribute("userId",userId);

        return "Admin_App/subPages/gradeItemsUserCourseReport";
    }

    /**
     * ADMIN VIEW GRADE ITEM GRADES BY ENROLLMENT LIST
     */
    @RequestMapping(value = "admin/grades/grade-items/{courseId}/{gradeItemId}/{userId}",method = RequestMethod.GET)
    public String gradeItemsUserCourseGradeItemsReport(Model model, @PathVariable int courseId, @PathVariable int gradeItemId ,@PathVariable long userId){
        GradeDao daoAccess = (GradeDao) applicationContext.getBean("GradeDao");

        model.addAttribute("courseId",courseId);
        model.addAttribute("userId",userId);
        model.addAttribute("gradeItemId",gradeItemId);
        model.addAttribute("gradesList", daoAccess.getCourseSingleGradeItemGrades(courseId,gradeItemId));

        return "Admin_App/subPages/gradeItemGradeView";
    }




    /**
     * PROCESS ADD GRADE ITEM REQUEST
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-gradeItems/add-grade-item/{courseId}",method = RequestMethod.POST)
    public String addGradeItem(Model model, HttpServletRequest request, @PathVariable int courseId ){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        GradeItems gradeItems = new GradeItems();

        logger.info(request.getParameter("gradeItemType"));
        logger.info(request.getParameter("giName"));
        logger.info(request.getParameter("giDate"));
        logger.info(request.getParameter("giStartDate"));
        logger.info(request.getParameter("giEndDate"));
        logger.info(Integer.parseInt(request.getParameter("minGrade")));
        logger.info(Integer.parseInt(request.getParameter("maxGrade")));
        logger.info( Double.parseDouble(request.getParameter("giWeight")));

        String confirm = "";
        gradeItems.setCourseId(courseId);
        gradeItems.setGradeItemType(request.getParameter("gradeItemType"));
        gradeItems.setGradeItemName(request.getParameter("giName"));
        gradeItems.setWeight(Double.parseDouble(request.getParameter("giWeight")));
        gradeItems.setMinGrade(Integer.parseInt(request.getParameter("minGrade")));
        gradeItems.setMaxGrade(Integer.parseInt(request.getParameter("maxGrade")));
        gradeItems.setDateOfExam(request.getParameter("giDate"));
        gradeItems.setEnrolmentStartDate(request.getParameter("giStartDate"));
        gradeItems.setEnrolmentCloseDate(request.getParameter("giEndDate"));
        gradeItems.setCreatedBy(currentUserId);
        gradeItems.setWeight(Double.parseDouble(request.getParameter("giWeight")));

        Exam exam = new Exam();
        long parentCourseId = daoAccessChildCourses.getChildCourseParentId(courseId);
        int childCourseId= courseId;
        String parentCourseName = "";
        String childCourseName = "";
        String examName = gradeItems.getGradeItemName();
        int maxGrade = gradeItems.getMaxGrade();
        int minGrade = gradeItems.getMinGrade();
        String examDate = gradeItems.getDateOfExam();
        String examStartDate = gradeItems.getEnrolmentStartDate();
        String enrolmentDeadlineDate = gradeItems.getEnrolmentCloseDate();

        exam.setParentCourseId(parentCourseId);
        exam.setChildCourseId(childCourseId);
        exam.setChildCourseName(childCourseName);
        exam.setParentCourseName(parentCourseName);
        exam.setExamName(examName);
        exam.setMaxGrade(maxGrade);
        exam.setMinGrade(minGrade);
        exam.setDateOfExam(examDate);
        exam.setEnrolmentStartDate(examStartDate);
        exam.setEnrolmentCloseDate(enrolmentDeadlineDate);

        if(gradeItems.getGradeItemType().equals("Exam")){
//            confirm = daoAccess.createNewExam(gradeItems);
            if(daoAccess.createChildCourseExam(exam).equals("200")){
                return "200";
            }else{
                System.out.println("@After processing received date, provided data failed to be inserted in to system ");
                return "400";
            }
        }else{
           // gradeItems.setWeight(0);
            if(daoAccess.addNewGradeItemForCourse(gradeItems).equals("200")){
                return "200";
            }else{
                System.out.println("@After processing received date, provided data failed to be inserted in to system ");
                return "400";
            }
        }

    }


    /**
     * RETURN SINGLE GRADE ITEM DATA FOR UPDATE
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-gradeItems/get-grade-item/{gradeItemId}",method = RequestMethod.POST)
    public GradeItems getGradeItem(Model model, HttpServletRequest request, @PathVariable int gradeItemId ){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        logger.info(gradeItemId);
        GradeItems gradeItem = daoAccess.getSingleGradeItemDetailByGradeItemId(gradeItemId);

        return gradeItem;
    }


    /**
     * PROCESS UPDATE GRADE ITEM REQUEST
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-gradeItems/update-grade-item/{gradeItemId}",method = RequestMethod.POST)
    public String updateGradeItem(Model model, HttpServletRequest request, @PathVariable int gradeItemId ){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        GradeItems gradeItems = new GradeItems();

        logger.info(gradeItemId);
        logger.info(request.getParameter("giName"));
        logger.info(request.getParameter("giDate"));
        logger.info(request.getParameter("giStartDate"));
        logger.info(request.getParameter("giEndDate"));
        logger.info(Integer.parseInt(request.getParameter("minGrade")));
        logger.info(Integer.parseInt(request.getParameter("maxGrade")));


        String confirm = "";
        gradeItems.setGradeItemId(gradeItemId);
        gradeItems.setGradeItemName(request.getParameter("giName"));
        gradeItems.setMinGrade(Integer.parseInt(request.getParameter("minGrade")));
        gradeItems.setMaxGrade(Integer.parseInt(request.getParameter("maxGrade")));
        gradeItems.setDateOfExam(request.getParameter("giDate"));
        gradeItems.setEnrolmentStartDate(request.getParameter("giStartDate"));
        gradeItems.setEnrolmentCloseDate(request.getParameter("giEndDate"));
        gradeItems.setWeight(Double.parseDouble(request.getParameter("giWeight")));

        confirm = daoAccess.updateGradeItem(gradeItems);

        return confirm;
    }

    /**
     * UN ENROLL STUDENT FROM EXAM
     */
    @ResponseBody
    @RequestMapping(value = "/student/exam/un-enroll/",method = RequestMethod.POST)
    public String unEnrollStudentFromExam(Model model,@RequestParam("examId") int examId){

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        logger.info("examId  = " +examId);

        if(checkIfDateIsStillActive( daoAccessExam.getSingleExamFullDetailsByExamId(examId).getDateOfExam())){
            if(!daoAccessGradeItem.checkIfExamHasGrades(examId, currentUserId)){
                if(daoAccessGradeItem.removeStudentFromExam(examId, currentUserId).equals("200")){
                    System.out.println("@200 Student un-enrolled from exam  ");
                    return "200";
                }else{
                    System.out.println("@400 Query failed ");
                    return "400";
                }

            }else{
                System.out.println("@400 failed exam has grades ");
                return "400";
            }
        }else{
            System.out.println("@400 Exam enrollment period is closed ");
            return "400";
        }

    }


    /**
     * PROCESS DELETE GRADE ITEM REQUEST
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-gradeItems/delete-grade-item/{gradeItemId}",method = RequestMethod.POST)
    public String deleteGradeItem(Model model, HttpServletRequest request, @PathVariable int gradeItemId ){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        logger.info(gradeItemId);
        String confirm = "";

        daoAccess.deleteGradeItemStudentGrades(gradeItemId);
        confirm = daoAccess.deleteGradeItemByGradeItemId(gradeItemId);

        return confirm;
    }

}
