package com.cdai.controllers.appController;

import build.dao.*;
import build.model.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import static com.cdai.util.CoursesUtil.processChildCourseCreationForSpecificSemester;
import static com.cdai.util.CoursesUtil.processParentCourseCreation;
import static com.cdai.util.ExamsUtil.daoAccessUser;
import static com.cdai.util.ExamsUtil.examUploadEntranceChamber;
import static com.cdai.util.ExamsUtil.getEligibleStudentsForClearanceExam;
import static com.cdai.util.ServiceUtil.checkIfInputIsAnInteger;

/**
 * Created by apple on 10/12/2017.
 */
@Controller
public class ZRipperController {

    private static final Logger logger = Logger.getLogger(ZRipperController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
    private static ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
    ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");

    /**
     * ADMIN RETURN CHILD COURSES BY SEMESTER
     */
    @ResponseBody
    @RequestMapping(value = "admin/get-child-courses/{majorId}/{semesterCode}",method = RequestMethod.GET)
    public List<ChildCourses> getChildCourseByMajorIdAndSemesterCode(Model model, @PathVariable int majorId, @PathVariable String semesterCode ){
        logger.info("Major id ::"+majorId);
        logger.info("SemesterCode :: "+semesterCode);
        return daoAccessChildCourses.getChildCourseListByMajorAndSemesterId(majorId,semesterCode);
    }

    /**
     * ADMIN RETURN PARENT COURSES BY MAJORID
     */
    @ResponseBody
    @RequestMapping(value = "admin/get-parent-courses/{majorId}",method = RequestMethod.GET)
    public List<ParentCourses> getParentCourseByMajorId(Model model, @PathVariable int majorId){
        ParentCoursesDao daoAccess = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        logger.info("Major id ::"+majorId);
        return daoAccess.getParentCoursesForMajor(majorId);
    }

    /**
     * ADMIN RETURN PARENT COURSE NAME BY COURSE ID
     */
    @ResponseBody
    @RequestMapping(value = "admin/get-parent-coursesName/{courseId}",method = RequestMethod.GET)
    public List<ParentCourses> getParentCourseNameById(Model model, @PathVariable int courseId){
        ParentCoursesDao daoAccess = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        logger.info("Course id ::"+courseId);
        return daoAccess.getParentCourseNameForCourseId(courseId);
    }


    /**
     * ADMIN RETURN  ADD MAJOR OR PARENT COURSE FORM
     */
    @RequestMapping(value = "admin/major-parentCourse-form/{type}",method = RequestMethod.GET)
    public String addMajorAndParentCourseForm(Model model, @PathVariable int type){
        ParentCoursesDao daoAccess = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");


        model.addAttribute("type",type);

        if(type == 2){
            model.addAttribute("lastAddedParentCourse",daoAccess.getLastAddedParentCourses());
            model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        }
        return "Admin_App/subPages/addMajorParentCourseForm";
    }

    /**
     * ADMIN RETURN PARENT COURSE PAGE
     */
    @RequestMapping(value = "admin/parent-course",method = RequestMethod.GET)
    public String parentCourse(Model model){
        ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");

        model.addAttribute("parentCourseList",daoAccessParentCourse.getAllParentCourses());


        return "Admin_App/subPages/parentCourse";
    }

    /**
     * ADMIN RETURN PARENT COURSE PAGE
     */

    /**
     * ADMIN MAJOR_PARENT COURSES GET THEIR EDIT PAGE
     */
    @RequestMapping(value = "admin/edit-parent-course-major/{object}/{itemId}",method = RequestMethod.GET)
    public String editMajorAndParentCourses(Model model, @PathVariable String object ,@PathVariable long itemId){
        ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");

        model.addAttribute("object",object);

        if(object.equals("parent_course")){
            long parentId = itemId;
            model.addAttribute("itemId",parentId);
            model.addAttribute("parentCourseData",daoAccessParentCourse.getSingleParentCourse(parentId));
            model.addAttribute("lastEditedParentCourse",daoAccessParentCourse.getLastEditedParentCourses());
            //
        }else{
            return "redirect:/admin/manage-courses/majors";
        }


        return "Admin_App/subPages/editMajorAndParentCourse";
    }

    /**
     * ADMIN EDIT MAJOR
     */
    @RequestMapping(value = "admin/edit-parent-course-major/{object}",method = RequestMethod.GET)
    public String editMajor(Model model, @PathVariable String object ,@RequestParam(value="majorId", required=false) Integer majorId ){

        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");

        model.addAttribute("object",object);
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("lastEditedMajor",daoAccessMajor.getLastEditedMajors());

        if(object.equals("major")){

            if (majorId == null){
                model.addAttribute("majorData",null);
                model.addAttribute("majorId",null);
            }
            else{
                model.addAttribute("majorData",daoAccessMajor.getSingleMajor(majorId));
                model.addAttribute("majorId",majorId);
            }
//            model.addAttribute("majorId",majorId);
        }else{
            return "redirect:/admin/manage-courses/majors";
        }

        return "Admin_App/subPages/editMajorAndParentCourse";
    }

    /**
     * ADMIN  VIEW PARENT COURSE PAGE
     */
    @RequestMapping(value = "admin/parent-course/view/{parentCourseId}",method = RequestMethod.GET)
    public String viewParentCourse(Model model, @PathVariable long parentCourseId){

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        int majorId = daoAccessParentCourse.getMajorIdForParentCourse(parentCourseId);

        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("parentCourseId",parentCourseId);
        model.addAttribute("parentDetails",daoAccessParentCourse.getSingleParentCourse(parentCourseId));
        model.addAttribute("sameMajorCourses",daoAccessParentCourse.getParentCoursesForMajor(majorId));
        model.addAttribute("childCourseList",daoAccessChildCourses.getChildCoursesListForSpecificParentCourse(parentCourseId));

        return "Admin_App/subPages/viewParentCourse";
    }

    /**
     * ADMIN CREATE CHILD COURSE FROM PARENT COURSE
     */
    @RequestMapping(value = "admin/parent-course/create-child-course/{parentCourseId}",method = RequestMethod.GET)
    public String getFormToCreateChildCourseForParent(Model model, @PathVariable long parentCourseId){
        UserDao daoAccessU = (UserDao) applicationContext.getBean("userDao");
        SemesterDao daoAccessSems = (SemesterDao) applicationContext.getBean("SemesterDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");


        int majorId = daoAccessParentCourse.getMajorIdForParentCourse(parentCourseId);

        model.addAttribute("parentCourseId",parentCourseId);
        model.addAttribute("majorId",majorId);
        model.addAttribute("parentDetails",daoAccessParentCourse.getSingleParentCourse(parentCourseId));
        model.addAttribute("teachersList",daoAccessU.getTeachersList());
        model.addAttribute("semesterList", daoAccessSems.getSystemSemesterList());
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("lastAdded",daoAccessChildCourses.getLastAddedChildCourses());


        return "Admin_App/subPages/addCourseForm";
    }

    /**
     * ADMIN RETURN STUDENT CLEARANCE EXAM ENROLLMENT LIST
     */
    @RequestMapping(value = "admin/manage-exams/clearance-exam/enrollment-list/{clearanceExamId}",method = RequestMethod.GET)
    public String clearanceExamEnrollmentList(Model model , @PathVariable int clearanceExamId){
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");


        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        model.addAttribute("currentUserRole",currentUserRole);

        model.addAttribute("clearanceExamId",clearanceExamId);
        model.addAttribute("clExamData", daoAccessExam.getClearanceExamByChildExamId(clearanceExamId));
        model.addAttribute("clEnrolmentList",daoAccessUser.getStudentsEnrolledInClearanceExam(clearanceExamId));

        return "Admin_App/subPages/clearanceExamEnrollmentList";
    }


    /**
     * ADMIN RETURN STUDENT CLEARANCE EXAM ENROLLMENT PAGE
     */
    @RequestMapping(value = "admin/manage-exams/clearance-exam/enroll-students/{clearanceExamId}",method = RequestMethod.GET)
    public String clearanceExamReturnEnrollStudentsPage(Model model , @PathVariable int clearanceExamId){
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");


        model.addAttribute("clearanceExamId",clearanceExamId);
        model.addAttribute("clExamData", daoAccessExam.getClearanceExamByChildExamId(clearanceExamId));
        model.addAttribute("clEnrolmentList",daoAccessUser.getStudentsEnrolledInClearanceExam(clearanceExamId));
        model.addAttribute("clEligibleToEnrollList",getEligibleStudentsForClearanceExam(clearanceExamId));
        return "Admin_App/subPages/clearanceExamEnrollStudentPage";
    }

    /**
     * ADMIN RETURN CLEARANCE EXAM ADD GRADES PAGE
     */
    @RequestMapping(value = "admin/mange-exams/clearance-exam/add/grades/{clearanceExamId}",method = RequestMethod.GET)
    public String clearanceExamViewAddGradePage(Model model, @PathVariable int clearanceExamId){
      //  GradeDao daoAccessGrade = (GradeDao) applicationContext.getBean("GradeDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
        ExamEnrolmentDao daoAccessExamEnroll = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");

//        int childCourseId = daoAccessExam.getClearanceExamChildCourse(clearanceExamId);

        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        model.addAttribute("currentUserRole",currentUserRole);


//        model.addAttribute("hasPermission", daoAccessCourses.checkPermissionForTeacherAgainstCourseId(currentUserId,childCourseId));
        model.addAttribute("clearanceExamId",clearanceExamId);
        model.addAttribute("cleExam",daoAccessExam.getClearanceExamByChildExamId(clearanceExamId));
        model.addAttribute("clEnrolmentList",daoAccessUser.getStudentsEnrolledInClearanceExam(clearanceExamId));
        model.addAttribute("gradeList",daoAccessExamEnroll.getClearanceEnrolledStudentsAddGradesList(clearanceExamId));
        model.addAttribute("isUpdated",daoAccessExam.isClexamSubmitted(clearanceExamId));
        return "Admin_App/subPages/clearanceExamAddGrades";
    }

    /**
     * ADMIN RETURN CLEARANCE EXAM GRADES VIEW
     */
    @RequestMapping(value = "admin/manage-exams/clearance-exam/view/grades/{clearanceExamId}",method = RequestMethod.GET)
    public String viewClearanceExamGrades(Model model,  @PathVariable int clearanceExamId){
        ExamDao daoAccessE = (ExamDao) applicationContext.getBean("ExamDao");
        ExamEnrolmentDao daoAccessExam = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");

        model.addAttribute("clearanceExamId",clearanceExamId);
        model.addAttribute("gradeList",daoAccessExam.getClearanceEnrolledStudentsAddGradesList(clearanceExamId));
        model.addAttribute("clExamData", daoAccessE.getClearanceExamByChildExamId(clearanceExamId));
        return "Admin_App/subPages/clearanceExamGradeOverview";
    }

    /**
     * ADMIN EDIT CLEARANCE EXAM *EDIT*
     */
    @RequestMapping(value = "admin/manage-exams/clearance-exam/edit/{clearanceExamId}",method = RequestMethod.GET)
    public String editClearanceExam(Model model,  @PathVariable int clearanceExamId){
        GradeDao daoAccessGrade = (GradeDao) applicationContext.getBean("GradeDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");

        model.addAttribute("clearanceExamId",clearanceExamId);
        model.addAttribute("clExamData", daoAccessExam.getClearanceExamByChildExamId(clearanceExamId));
        model.addAttribute("action","edit");
        model.addAttribute("lastEditedClearanceExams",daoAccessExam.getLastEditedClearanceExams());

        return "Admin_App/subPages/editClearanceExamForm";
    }

    /**
     * ADMIN CREATE MAJOR
     */
    @ResponseBody
    @RequestMapping(value = "admin/add-major",method = RequestMethod.POST)
    public String addMajor(Model model, HttpServletRequest request){

        MajorDao daoAccess = (MajorDao) applicationContext.getBean("MajorDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        logger.info(request.getParameter("majorShortName"));
        logger.info(request.getParameter("majorName"));
        logger.info(request.getParameter(""+currentUserId));

        String confirm = daoAccess.createNewMajor(new Major(request.getParameter("majorShortName"),request.getParameter("majorName"),currentUserId));
        return confirm;

    }


    /**
     * ADMIN UPDATE MAJOR
     */
    @ResponseBody
    @RequestMapping(value = "admin/update-major/{majorId}",method = RequestMethod.POST)
    public String updateMajor(Model model, @PathVariable int majorId, HttpServletRequest request){

        MajorDao daoAccess = (MajorDao) applicationContext.getBean("MajorDao");

        logger.info(majorId);
        logger.info(request.getParameter("majorShortName"));
        logger.info(request.getParameter("majorName"));


        String confirm = daoAccess.updateMajor(new Major(majorId,request.getParameter("majorShortName"), request.getParameter("majorName")));
        return confirm;

    }

    /**
     * ADMIN DELETE MAJOR
     */
    @ResponseBody
    @RequestMapping(value = "admin/delete-major/{majorId}",method = RequestMethod.POST)
    public String deleteMajor(Model model, @PathVariable int majorId){

        MajorDao daoAccess = (MajorDao) applicationContext.getBean("MajorDao");
        logger.info(majorId);


        if( daoAccess.deleteMajorFromMajorTable(majorId).equals("200")){
            daoAccess.deleteMajorFromStudentMajor(majorId);
            daoAccess.deleteMajorFromClassMajor(majorId);
            daoAccess.deleteMajorFromParentCourseMajor(majorId);
            return "200";
        }else{
            return "400";
        }

    }


    /**
     * ADMIN DELETE PARENT COURSE
     */
    @ResponseBody
    @RequestMapping(value = "admin/delete-parent-course/{parentCourseId}",method = RequestMethod.POST)
    public String deleteParentCourse(Model model, @PathVariable long parentCourseId){

        ParentCoursesDao daoAccess = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        logger.info(parentCourseId);

        if(!daoAccess.checkIfParentCourseHasChildCourses(parentCourseId)){
            if( daoAccess.deleteParentCourseFromParentCourseTable((parentCourseId)).equals("200")){
                daoAccess.deleteParentCourseFromParentCourseMajor(parentCourseId);
                return "200";
            }else{
                return "400";
            }
        }else{
            return "501";
        }


    }

    /**
     * ADMIN CREATE NEW PARENT COURSE
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-courses/add-parent-course",method = RequestMethod.POST)
    public String createNewParentCourse(Model model,HttpServletRequest request){

        MajorDao daoAccess = (MajorDao) applicationContext.getBean("MajorDao");
        ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        ParentCoursesMajorDao daoAccessPCM = (ParentCoursesMajorDao) applicationContext.getBean("ParentCoursesMajorDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        logger.info( request.getParameter("major"));
//        logger.info( request.getParameter("courseId"));
        logger.info( request.getParameter("courseName"));
        logger.info( request.getParameter("courseShortName"));
        logger.info( request.getParameter("courseShortNameCn"));
        logger.info( request.getParameter("courseType"));
        logger.info( request.getParameter("credits"));
        logger.info( request.getParameter("courseDescription"));

       /* if(!checkIfInputIsAnInteger(request.getParameter("courseId"))){
            System.out.println("Parent Course Id Format Error , Parent Course Can only Be in integer form using numbers no letters allowed ");
            return "502";
        }*/


        int majorId = Integer.parseInt(request.getParameter("major"));
        ParentCourses parentCourse = new ParentCourses();

      // parentCourse.setParentCourseId(Long.parseLong(request.getParameter("courseId")));
        //  parentCourse.setParentCourseId(request.getParameter("courseId"));
        parentCourse.setCourseType(request.getParameter("courseType"));
        parentCourse.setCourseShortName(request.getParameter("courseShortName"));
        parentCourse.setCourseShortNameCN(request.getParameter("courseShortNameCn"));
        parentCourse.setCourseName(request.getParameter("courseName"));
        parentCourse.setCredits(Double.parseDouble( request.getParameter("credits")));
        parentCourse.setDescription( request.getParameter("courseDescription"));
        parentCourse.setCreatedBy(currentUserId);
        parentCourse.setMajorId(majorId);

        if (!daoAccessParentCourse.checkIfParentCourseIdInSystem(parentCourse.getParentCourseId())){
            daoAccessParentCourse.createNewParentCourse(parentCourse);
           // daoAccessPCM.addParentCourseToMajor(parentCourse.getParentCourseId(),majorId);
            System.out.println("200 success");
            return "200";
        }else{
            System.out.println("501 Inputted Parent Course Id Is Already In system ");
            return "501";
        }

    }


    /**
     * ADMIN UPDATE PARENT COURSE
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-courses/update-parent-course/{parentCourseId}",method = RequestMethod.POST)
    public String updateParentCourse(Model model,HttpServletRequest request, @PathVariable long parentCourseId){

        MajorDao daoAccess = (MajorDao) applicationContext.getBean("MajorDao");
        ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        ParentCoursesMajorDao daoAccessPCM = (ParentCoursesMajorDao) applicationContext.getBean("ParentCoursesMajorDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        logger.info( request.getParameter("major"));
    //    logger.info( request.getParameter("courseId"));
        logger.info( request.getParameter("courseName"));
        logger.info( request.getParameter("courseShortName"));
        logger.info( request.getParameter("courseShortNameCN"));
        logger.info( request.getParameter("courseType"));
        logger.info( request.getParameter("credits"));
        logger.info( request.getParameter("courseDescription"));


        int majorId = Integer.parseInt(request.getParameter("major"));
        ParentCourses parentCourse = new ParentCourses();

        parentCourse.setParentCourseId(parentCourseId);
        parentCourse.setCourseType(request.getParameter("courseType"));
        parentCourse.setCourseShortName(request.getParameter("courseShortName"));
        parentCourse.setCourseShortNameCN(request.getParameter("courseShortNameCN"));
        parentCourse.setCourseName(request.getParameter("courseName"));
        parentCourse.setCredits(Double.parseDouble( request.getParameter("credits")));
        parentCourse.setDescription( request.getParameter("courseDescription"));



            if(daoAccessParentCourse.updateParentCourse(parentCourse).equals("200")){
                daoAccessPCM.updateParentCoursesMajor(parentCourse.getParentCourseId(),majorId);
                System.out.println("200 success");
                return "200";
            }else{
                System.out.println("400 fail");
                return "400";
            }

    }

    /**
     * ADD PARENT COURSE VIA UPLOADING  EXCEL FILE
     */
    @ResponseBody
    @RequestMapping(value = "manage-courses/add-parent-course-excel",method = RequestMethod.POST)
    public JSONObject addParentCourseByUploadingExcelFile(Model model, @RequestParam("parentCourseExcelListFile") MultipartFile parentCourseExcelListFile,@RequestParam("majorId") int majorId){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        //DAOS
        //create parent course
        ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        ParentCoursesMajorDao daoAccessPCM = (ParentCoursesMajorDao) applicationContext.getBean("ParentCoursesMajorDao");
        //add parent course to major

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");




//        List<UserProfile> tempListError = new ArrayList<>();
//        List<UserProfile> tempListProcessed = new ArrayList<>();

        List<ParentCourses> tempListError = new ArrayList<>();
        List<ParentCourses> tempListProcessed = new ArrayList<>();
        JSONObject result = new JSONObject();

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String intake = "";
        Calendar startYear = Calendar.getInstance();
        Calendar midYear = Calendar.getInstance();

        startYear.set(year,Calendar.JANUARY,1);
        midYear.set(year,Calendar.JULY,30);

        if(now.after(startYear) && now.before(midYear)){
            intake = year+"-1";
        }
        else{
            intake = year+"-2";
        }

        HSSFRow row;
        try{

            int i =0;

            if (parentCourseExcelListFile.isEmpty()){
                System.out.println("NO DATA RECEIVED!!");
                //empty excel file error ;
                result.put("status_code",407);
                return result;
            }
            else {
                System.out.println("DATA RECEIVED!!");
            }

            HSSFWorkbook workbook = new HSSFWorkbook(parentCourseExcelListFile.getInputStream());
            HSSFSheet worksheet = workbook.getSheetAt(0);
            Iterator< Row > rowIterator = worksheet.iterator();



            while(rowIterator.hasNext()){
                row = (HSSFRow)rowIterator.next();
                if(row.getRowNum()==0){
                    continue; //just skip the rows if row number is 0
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
////
////                    // CREATING USER
                    ParentCourses parentCourse = new ParentCourses();
                    parentCourse.setParentCourseId((long)row.getCell(0).getNumericCellValue());
                    parentCourse.setCourseType(row.getCell(1).getStringCellValue());
                    parentCourse.setCourseShortName(row.getCell(2).getStringCellValue());
                    parentCourse.setCourseName(row.getCell(3).getStringCellValue());
                    parentCourse.setCredits((int)row.getCell(4).getNumericCellValue());
                    parentCourse.setDescription(row.getCell(5).getStringCellValue());
                    parentCourse.setCreatedBy(currentUserId);
                    parentCourse.setMajorId(majorId);



                    //check if Course id is already in system

                    if (daoAccessParentCourse.checkIfParentCourseIdInSystem((long)row.getCell(0).getNumericCellValue()) || 1000000001 == (long)row.getCell(0).getNumericCellValue() ){
                        System.out.println(" Error Parent Course Id @::"+row.getCell(0).getNumericCellValue()+"::Already In System");
                        tempListError.add(parentCourse);
                    }else{
                        tempListProcessed.add(parentCourse);
                    }
                    break;
                }

            }
            System.out.println("@::Excel Processing Complete!!");

            //process uploads and insert into system
            if(tempListError.isEmpty()){
                System.out.println("@::New Parent Course Added");

                processParentCourseCreation(majorId,tempListProcessed);

                result.put("status_code",200);
                result.put("list",tempListProcessed);
                return result;
            }
            else{
                System.out.println("@::ERROR THE FOLLOWING PARENT COURSES ID ARE ALREADY IN THE SYSTEM ");
                System.out.println("@::Delete Parent course or change parent course id ");
                System.out.println("________________________________________________________________");
                for(ParentCourses entity:tempListError){
                    System.out.println("Parent Course Id: "+entity.getParentCourseId()+" --- Parent Course Name:"+entity.getCourseName());
                }

                result.put("status_code",406);
                result.put("list",tempListError);
                return result;
            }
//            return "SUCCESS";
//            return null;
//
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("System Error!!");
            result.put("status_code",399);
            return result;
        }
        //logger.info("This is Error message", new Exception("Testing"));

    }

    /**
     * ADD CHILD COURSE VIA UPLOADING  EXCEL FILE
     */
    @ResponseBody
    @RequestMapping(value = "manage-courses/add-child-course-excel",method = RequestMethod.POST)
    public JSONObject addChildCourseByUploadingExcelFile(Model model, @RequestParam("childCourseExcelListFile") MultipartFile childCourseExcelListFile,@RequestParam("semesterId") int semesteId){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");

        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");
        ParentCourseChildCoursesDao daoAccessPCChild = (ParentCourseChildCoursesDao) applicationContext.getBean("ParentCourseChildCoursesDao");
        ChildCourseSemesterDao daoAccessCCSemester = (ChildCourseSemesterDao) applicationContext.getBean("ChildCourseSemesterDao");
        ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        int childId = daoAccessChildCourses.getTheLargestChildCourseId();
        if (childId == -1){
            childId = 0;
        }
        System.out.println("Start#"+childId);
        System.out.println(semesteId);

        List<ChildCourses> tempListError = new ArrayList<>();
        List<ChildCourses> tempListProcessed = new ArrayList<>();

        JSONObject result = new JSONObject();

        HSSFRow row;
        try{

            int i =0;

            if (childCourseExcelListFile.isEmpty()){
                System.out.println("NO EXCEL DATA RECEIVED!!");
                //empty excel file error ;
                result.put("status_code",407);
                return result;
            }
            else {
                System.out.println("EXCEL DATA RECEIVED!!");
            }

            HSSFWorkbook workbook = new HSSFWorkbook(childCourseExcelListFile.getInputStream());
            HSSFSheet worksheet = workbook.getSheetAt(0);
            Iterator< Row > rowIterator = worksheet.iterator();



            while(rowIterator.hasNext()){
                row = (HSSFRow)rowIterator.next();
                if(row.getRowNum()==0){
                    continue; //just skip the rows if row number is 0
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();

                    ChildCourses childCourse = new ChildCourses();
                    childCourse.setChildCourseId(++childId);
                    childCourse.setParentCourseId((long) row.getCell(1).getNumericCellValue());
                    childCourse.setChildCourseName(row.getCell(5).getStringCellValue());
                    childCourse.setEnrolmentStartDate(row.getCell(6).getStringCellValue());
                    childCourse.setEnrolmentDeadline(row.getCell(7).getStringCellValue());
                    childCourse.setCreatedBy(currentUserId);
                    if (daoAccessUser.checkIfUserIdInSystem((long) row.getCell(8).getNumericCellValue())){
                        childCourse.setTeacherId((long) row.getCell(8).getNumericCellValue());
                        childCourse.setTeacherName(row.getCell(9).getStringCellValue());
                    }


                    if (daoAccessParentCourse.checkIfParentCourseIdInSystem((long) row.getCell(1).getNumericCellValue())) {
                        tempListProcessed.add(childCourse);
                    } else {
                        System.out.println(" Error Parent Course Id @::" + row.getCell(1).getNumericCellValue() + "::Not In System");
                        tempListError.add(childCourse);

                    }

                    break;

                }

            }
            System.out.println("@::Excel Processing Complete!!");

            if(tempListError.isEmpty()){
                System.out.println("@::New Child Course Added");

                processChildCourseCreationForSpecificSemester(semesteId,tempListProcessed);
                result.put("status_code",200);
                result.put("list",tempListProcessed);
                return result;
            }
            else{
                System.out.println("@::ERROR THE FOLLOWING PARENT COURSES ID ARE ALREADY IN THE SYSTEM ");
                System.out.println("@::Delete Parent course or change parent course id ");
                System.out.println("________________________________________________________________");
                for (ChildCourses entity : tempListError) {
                    System.out.println("Parent Course Id: " + entity.getParentCourseId() + "--- FOR CHILD Course :  " + entity.getChildCourseId());
                }
                result.put("status_code",406);
                result.put("list",tempListError);
                return result;
            }

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("System Error!!");
            result.put("status_code",399);
            return result;
        }

    }


    /**
     * ADD CHILD COURSE EXAM VAI EXCEL UPLOAD
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-exams/add-child-course-exam-excel",method = RequestMethod.POST)
    public JSONObject addChildCourseExamByUploadingExcelFile(Model model, @RequestParam("examExcelFile") MultipartFile examExcelFile,
                                                             @RequestParam("semesterId") int semesteId,
                                                             @RequestParam("majorId") int majorId){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");


        GradeItemsDao daoAccessGradeItem = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");


        List<ChildCourses> tempListError = new ArrayList<>();
        List<ChildCourses> tempListProcessed = new ArrayList<>();

        JSONObject result = new JSONObject();

        HSSFRow row;
        try{

            int i =0;

            if ( examExcelFile.isEmpty()){
                System.out.println("NO EXCEL DATA RECEIVED!!");
                //empty excel file error ;
                result.put("status_code",407);
                return result;
            }
            else {
                System.out.println("EXCEL DATA RECEIVED!!");
            }

            HSSFWorkbook workbook = new HSSFWorkbook( examExcelFile.getInputStream());
            JSONObject res = examUploadEntranceChamber(workbook);
            System.out.println("Reciving ==========="+res.getString("msg"));

            if(res.getString("msg").equals("200")){
                if(res.getString("data") == null || res.getString("msg").isEmpty()){
                    System.out.println("400-----ERROR PLEASE CHECK EXCEL DATA AND TRY AGAIN");
                }else{
                    List<Exam> examBundle = (List<Exam>) res.get("data");
                    System.out.println("Printing data agian");
                    for (Exam entity: examBundle){
                        entity.setCreatedBy(currentUserId);
                        daoAccessGradeItem.createChildCourseExam(entity);
                    }
                    System.out.println("EXAM CREATION PROCESS COMPLTE ");

                }
            }

            return res;


        }catch(Exception e){
            e.printStackTrace();
            System.out.println("System Error!!");
            result.put("status_code",399);
            return result;
        }

    }


}
