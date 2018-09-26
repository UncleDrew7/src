package com.cdai.controllers.appController;

import build.dao.*;
import build.model.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdai.models.tempModels.GetGrade;
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
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import static com.cdai.util.ExamsUtil.getAvailableRecommendedExamsListForStudent;
import static com.cdai.util.StudentGradeAndReportingUtil.getCustomStudentGradeReport;

/**
 * Created by apple on 07/08/2017.
 */
@Controller
public class GradesController {
    private static final Logger logger = Logger.getLogger(GradesController.class);
    private static final  ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");


    /**
     * STUDENT GRADES DETAIL PAGE FULL REPORT
     */
    @RequestMapping(value = "student/grades/full-report/{courseId}",method = RequestMethod.GET)
    public String displayDetailedGradeReport(Model model, @PathVariable int courseId){
        GradeItemsDao daoAccess = (GradeItemsDao) applicationContext.getBean("GradeItemsDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");
        GradeDao daoAccessGrade = (GradeDao) applicationContext.getBean("GradeDao");
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();

        String userName =  daoAccessUser.getUserNameById(userId);
        long parentCourseId = daoAccessChildCourses.getChildCourseParentId(courseId);
        int score = daoAccessGrade.getCourseScoreComputedByHighestExamGradeforStudent(userId,parentCourseId,courseId);
        if(daoAccessExam.getPassGradeIfAnyForParentCourseClearanceExam(userId, parentCourseId).equals("pass")){
            score = 60;
        }

        model.addAttribute("userId",userId);
        model.addAttribute("userName",userName);
        model.addAttribute("courseId",courseId);
        model.addAttribute("courseName",daoAccessCourse.getCourseNameByCourseId(courseId));
        model.addAttribute("courseGradeFullReportList",getCustomStudentGradeReport(parentCourseId,courseId,userId));
        model.addAttribute("recommendedAndAvailableExams",getAvailableRecommendedExamsListForStudent(parentCourseId,userId));
        model.addAttribute("courseTotal",score);

        return "Student_App/subPages/grades_details";
    }


    /**
     * ADMIN VIEW STUDENT GRADE OVERVIEW PAGE
     */
    @RequestMapping(value = "admin/grades/student-grade-overview/{studentId}",method = RequestMethod.GET)
    public String viewStudentGradeOverview(Model model, @PathVariable long studentId,@RequestParam(value="semester", required=false) Integer semesterId){

        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");

        model.addAttribute("studentId",studentId);
        model.addAttribute("semesterId",semesterId);
        model.addAttribute("semesterList",daoAccess.getSemesterList());

        if (semesterId == null){
            model.addAttribute("coursesWithGradeAverageList",daoAccess.getUserCoursesGradeAverage(studentId));
        }
        else{
            model.addAttribute("coursesWithGradeAverageList",daoAccess.getUserCoursesGradeAverageFilterByCourseCategory(studentId,semesterId));
        }


        return "Admin_App/subPages/studentGradesOverViewReport";
    }

    /**
     * ADMIN VIEW STUDENT COURSE VIEW
     */
    @RequestMapping(value = "admin/grades/student-course-grades/{childCourseId}/{studentId}",method = RequestMethod.GET)
    public String viewStudentCourseGrades(Model model, @PathVariable int childCourseId , @PathVariable long studentId){

        GradeItemsDao daoAccess = (GradeItemsDao) applicationContext.getBean("GradeItemsDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");
        GradeDao daoAccessGrade = (GradeDao) applicationContext.getBean("GradeDao");
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");


        String userName =  daoAccessUser.getUserNameById(studentId);
        long parentCourseId = daoAccessChildCourses.getChildCourseParentId(childCourseId);
        int score = daoAccessGrade.getCourseScoreComputedByHighestExamGradeforStudent(studentId,parentCourseId,childCourseId);
        if(daoAccessExam.getPassGradeIfAnyForParentCourseClearanceExam(studentId, parentCourseId).equals("pass")){
            score = 60;
        }

        model.addAttribute("userName",userName);
        model.addAttribute("courseId",childCourseId);
        model.addAttribute("userId",studentId);
        model.addAttribute("courseName",daoAccessCourse.getCourseNameByCourseId(childCourseId));
        model.addAttribute("courseGradeFullReportList",getCustomStudentGradeReport(parentCourseId,childCourseId,studentId));
        model.addAttribute("courseTotal",score);


        return "Admin_App/subPages/studentCourseGrades";
    }


    /**
     * ADMIN GRADES DETAILS PAGE FULL REPORT
     */
    @RequestMapping(value = "admin/grades/grades-details",method = RequestMethod.GET)
    public String addGradesPage(Model model){


        return "Admin_App/subPages/grades-detailed";
    }

    /**
     * RETURN ADD GRADE VIEW
     */
    @RequestMapping(value ={ "admin/grades/add-grades/{courseId}/{gradeItemId}/{gradeItemTypeId}",},method = RequestMethod.GET)
    public String addStudentGrades(Model model ,@PathVariable int courseId,@PathVariable int gradeItemId,@PathVariable int gradeItemTypeId){
        GradeDao daoAccess = (GradeDao) applicationContext.getBean("GradeDao");
        CoursesDao daoAccessCourses = (CoursesDao) applicationContext.getBean("CoursesDao");

        model.addAttribute("courseId",courseId);
        model.addAttribute("gradeItemId",gradeItemId);
        model.addAttribute("gradeItemTypeId",gradeItemTypeId);
        model.addAttribute("courseName",daoAccessCourses.getCourseNameByCourseId(courseId));

        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        model.addAttribute("currentUserRole",currentUserRole);

        model.addAttribute("hasPermission", daoAccessCourses.checkPermissionForTeacherAgainstCourseId(currentUserId,courseId));

        //exam
        if( gradeItemTypeId == 1){
            model.addAttribute("studentGradeList",daoAccess.getExamStudentEnrollmentAddGradesList(gradeItemId));
        }
        //other grade items
        else{
            model.addAttribute("studentGradeList",daoAccess.getCourseStudentEnrollmentAddGradesList(courseId,gradeItemId));
        }
        // get course name by course id
        // get grade list
        return "Admin_App/subPages/addGrades";
    }


    /**
     * ADMIN ADD NEW GRADE ITEM
     */
    @ResponseBody
    @RequestMapping(value = "admin/grades/grade-items/add/{courseId}",method = RequestMethod.POST)
    public String addNewGradeItem(Model model, @PathVariable int courseId ,HttpServletRequest request){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");

        GradeItems gradeItems = new GradeItems();
        long userId = UserUtils.getUser().getUserId();


        logger.info(request.getParameter("gradeItemType"));
        logger.info(request.getParameter("gradeItemName"));
        logger.info(request.getParameter("weight"));
        logger.info(request.getParameter("minGrade"));
        logger.info(request.getParameter("maxGrade"));
        logger.info(request.getParameter("dateOfExam"));
        logger.info(request.getParameter("semesterId"));


        gradeItems.setCourseId(courseId);
        gradeItems.setGradeItemType(request.getParameter("gradeItemType"));
        gradeItems.setGradeItemName(request.getParameter("gradeItemName"));
        gradeItems.setWeight(Double.parseDouble(request.getParameter("weight")));
        gradeItems.setMinGrade(Integer.parseInt(request.getParameter("minGrade")));
        gradeItems.setMaxGrade(Integer.parseInt(request.getParameter("maxGrade")));
        gradeItems.setDateOfExam(request.getParameter("dateOfExam"));
        gradeItems.setSemesterId(Integer.parseInt(request.getParameter("semesterId")));
        gradeItems.setCreatedBy(userId);

        String confirm  = daoAccess.addNewGradeItemForCourse(gradeItems);
        return confirm;
    }

    /**
     * ADMIN EDIT GRADE ITEM
     */
    @ResponseBody
    @RequestMapping(value = "admin/grades/grade-items/edit/{gradeItemId}",method = RequestMethod.POST)
    public String editGradeItem(Model model, @PathVariable int gradeItemId ,HttpServletRequest request){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");

        GradeItems gradeItems = new GradeItems();


        logger.info(request.getParameter("gradeItemType"));
        logger.info(request.getParameter("gradeItemName"));
        logger.info(request.getParameter("weight"));
        logger.info(request.getParameter("minGrade"));
        logger.info(request.getParameter("maxGrade"));
        logger.info(request.getParameter("dateOfExam"));
        logger.info(request.getParameter("semesterId"));


        gradeItems.setGradeItemId(gradeItemId);
        gradeItems.setGradeItemType(request.getParameter("gradeItemType"));
        gradeItems.setGradeItemName(request.getParameter("gradeItemName"));
        gradeItems.setWeight(Double.parseDouble(request.getParameter("weight")));
        gradeItems.setMinGrade(Integer.parseInt(request.getParameter("minGrade")));
        gradeItems.setMaxGrade(Integer.parseInt(request.getParameter("maxGrade")));
        gradeItems.setDateOfExam(request.getParameter("dateOfExam"));
        gradeItems.setSemesterId(Integer.parseInt(request.getParameter("semesterId")));

        String confirm  =  daoAccess.editGradeItem(gradeItems);
        return confirm;
    }

    /**
     * ADMIN DELETE GRADE ITEM
     */
    @ResponseBody
    @RequestMapping(value = "admin/grades/grade-items/delete/{gradeItemId}",method = RequestMethod.POST)
    public String deleteGradeItem(Model model, @PathVariable int gradeItemId ,HttpServletRequest request){
        GradeItemsDao daoAccess = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");

        String confirm = daoAccess.deleteGradeItem(gradeItemId);
        return confirm;

    }

    /**
     * ADMIN GET COURSE ALL GRADE OVERVIEW
     */
    @RequestMapping(value ={ "admin/course/courseAllGrades/{courseId}"},method = RequestMethod.GET)
    public String getCourseAllGradesOverview(Model model ,@PathVariable int courseId){
//      GradeDao daoAccessGrade = (GradeDao) applicationContext.getBean("GradeDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");
        CourseEnrolmentDao daoAccessEnroll = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");

        String confirm ="";

        User user = UserUtils.getUser();
        long userId = user.getUserId();

        List<User> stuGrades = daoAccessUser.getStudentEnrolledInCourseWithGradeAverage(courseId);
        for(User grade : stuGrades){
            if(grade.getUserId() != 0 ){

                confirm = daoAccessEnroll.updateCourseGrade(grade.getUserId(), courseId, String.valueOf(grade.getCourseGradeAverage()));

                System.out.println(grade.getUserId()+"::>> " +confirm );

            }
        }

        model.addAttribute("courseId",courseId);
//        model.addAttribute("studentEnrolledList",daoAccessUser.getStudentEnrolledInCourseWithGradeAverage(courseId));
        model.addAttribute("courseName",daoAccessCourse.getCourseNameByCourseId(courseId));
        model.addAttribute("courseAllGrades", daoAccessEnroll.getAllCourseGradesListByCourseId(courseId));

        return "Admin_App/subPages/courseAllGradeOverview";
    }

    /**
     * ADMIN GET COURSE STUDENT GRADE OVERVIEW
     */
    @RequestMapping(value ={ "admin/course/grade/overviewFinal/{courseId}"},method = RequestMethod.GET)
    public String courseStudentGradesOverviewFinal(Model model ,@PathVariable int courseId){
        GradeDao daoAccessGrade = (GradeDao) applicationContext.getBean("GradeDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");
        CourseEnrolmentDao daoAccessEnroll = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");

        User user = UserUtils.getUser();
        long userId = user.getUserId();


        model.addAttribute("courseId",courseId);
        model.addAttribute("gradeItems",daoAccessGrade.getGradeItemsFromCourseByCourseId(courseId));
        model.addAttribute("courseGrades",daoAccessGrade.getAllGradesForCourseGradeItemsByCourseId(courseId));
        model.addAttribute("studentEnrolledList",daoAccessUser.getStudentEnrolledInCourseWithGradeAverage(courseId));
        model.addAttribute("courseName",daoAccessCourse.getCourseNameByCourseId(courseId));
        model.addAttribute("courseAllGrades", daoAccessEnroll.getAllCourseGradesListByCourseId(courseId));
        // model.addAttribute("upcomingEvents", daoAccessEvents.displayAdminUpcomingEvent(userId));


        return "Admin_App/subPages/courseAllGradeOverview";
    }

    /**
     * ADMIN GET COURSE STUDENT GRADE OVERVIEW
     */
    @RequestMapping(value ={ "admin/course/grade/overview/{courseId}"},method = RequestMethod.GET)
    public String addCourseStudentGradesOverview(Model model ,@PathVariable int courseId){
        GradeDao daoAccessGrade = (GradeDao) applicationContext.getBean("GradeDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");
//        EventsDao daoAccessEvents = (EventsDao) applicationContext.getBean("EventsDao");

        User user = UserUtils.getUser();
        long userId = user.getUserId();


        model.addAttribute("courseId",courseId);
        model.addAttribute("gradeItems",daoAccessGrade.getGradeItemsFromCourseByCourseId(courseId));
        model.addAttribute("courseGrades",daoAccessGrade.getAllGradesForCourseGradeItemsByCourseId(courseId));
        model.addAttribute("studentEnrolledList",daoAccessUser.getStudentEnrolledInCourseWithGradeAverage(courseId));
        model.addAttribute("courseName",daoAccessCourse.getCourseNameByCourseId(courseId));
        // model.addAttribute("upcomingEvents", daoAccessEvents.displayAdminUpcomingEvent(userId));


        return "Admin_App/subPages/courseStudentGradeOverview";
    }


    /**
     * PROCESS AND ADD STUDENTS GRADES
     */
    @ResponseBody
    @RequestMapping(value = "admin/grades/add-grades/{courseId}/{gradeItemId}",method = RequestMethod.POST)
    public JSONObject adminViewDetailedGradeInfo(@RequestBody List<Grade> grades, Model model,@PathVariable int courseId,@PathVariable int gradeItemId){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        GradeDao daoAccess = (GradeDao) applicationContext.getBean("GradeDao");
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");
        String confirm ="";

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();


            for(Grade grade : grades){
                if(grade.getStudentId() != 0 ){
                    grade.setGradeItemId(gradeItemId);
                    grade.setCourseId(courseId);
                    grade.setUploadedBy(userId);
                    grade.setParentCourseId(daoAccessChildCourses.getChildCourseParentId(courseId));
                    logger.info(grade.getStudentId());
                    logger.info(grade.getStudentName());
                    logger.info(grade.getGrade());
                    logger.info(grade.getGradeItemId());
                    logger.info(grade.getCourseId());
                    logger.info(grade.getUploadedBy());
                    if( daoAccess.checkIfStudentHasGradeForGradeItem(gradeItemId,grade.getStudentId())){
                        confirm = daoAccess.updateGrade(grade);
                    }else{
                        confirm = daoAccess.addGrade(grade);
                    }

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
     * ADD GRADES BY EXCEL
     */
    @RequestMapping(value = "admin/grades/import-grades/{courseId}/{gradeItemId}/{gradeItemTypeId}",method = RequestMethod.POST)
    public String addStudentGradeItemGradesWithExcelFile(Model model,@PathVariable int courseId,@PathVariable int gradeItemId, @PathVariable int gradeItemTypeId ,@RequestParam("studentGradesFile")MultipartFile studentGradesFile){
        HSSFRow row;

        GradeDao daoAccess = (GradeDao) applicationContext.getBean("GradeDao");
        long userId = UserUtils.getUser().getUserId();

        System.out.println("@............... Started importing Grade Excel file ...............");

        try{

            int i = 0;
            long studentId = 0;
            int grade = -1;
            String confirm = "";
            Grade gradeObject = new Grade();


            HSSFWorkbook workbook = new HSSFWorkbook(studentGradesFile.getInputStream());
            HSSFSheet worksheet = workbook.getSheetAt(0);
            Iterator< Row > rowIterator = worksheet.iterator();

            System.out.println("@............... Reading Excel file ...............");

            int count =0;

            while(rowIterator.hasNext()){
                row = (HSSFRow)rowIterator.next();
                if(row.getRowNum()==0){
                    continue; //just skip the rows if row number is 0
                }
                Iterator<Cell> cellIterator = row.cellIterator();

                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();

                    System.out.println("@............... Adding to database ...............");

                    studentId = (int)row.getCell(0).getNumericCellValue();
                    grade = (int)row.getCell(2).getNumericCellValue();

                    gradeObject.setStudentId(studentId);
                    gradeObject.setCourseId(courseId);
                    gradeObject.setStudentName(row.getCell(1).getStringCellValue());
                    gradeObject.setGradeItemId(gradeItemId);
                    gradeObject.setGrade(grade);
                    gradeObject.setUploadedBy(userId);
                    logger.info(gradeObject.getStudentId());
                    logger.info(gradeObject.getStudentName());
                    logger.info(gradeObject.getGrade());
                    logger.info(gradeObject.getGradeItemId());
                    logger.info(gradeObject.getCourseId());
                    logger.info(gradeObject.getUploadedBy());

                    if( daoAccess.checkIfStudentHasGradeForGradeItem(gradeItemId,gradeObject.getStudentId())){
                        confirm = daoAccess.updateGrade(gradeObject);
                    }else{
                        if(gradeItemTypeId == 1 && daoAccess.checkIfStudentIsEnrolledInExam(gradeItemId,gradeObject.getStudentId())){
                            confirm = daoAccess.addGrade(gradeObject);
                        }else if(gradeItemTypeId != 1 && daoAccess.checkIfStudentIsEnrolledInCourse(courseId,gradeObject.getStudentId())){
                            confirm = daoAccess.addGrade(gradeObject);
                        }
                        else{
                            logger.info("@ExcelAddGrade::USER GRADE NOT ADD :::USER NOT ENROLLED !!");
                            confirm = "GRADE NOT ADDED ";
                        }

                    }

                    System.out.println(gradeObject.getStudentName()+"::>> " +confirm );

                    break;


                }
            }

        }catch (Exception e){
            System.out.println("@addStudentGradeItemGradesWithExcelFile::ERROR");
            e.printStackTrace();
        }

        return "redirect:/admin/grades/add-grades/"+courseId+"/"+gradeItemId+"/"+gradeItemTypeId;


    }


}
