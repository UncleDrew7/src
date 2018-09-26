package com.cdai.controllers.appController;

import build.dao.*;
import build.model.Class;
import com.alibaba.fastjson.JSONObject;
import com.cdai.models.tempModels.ExcelClass;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import static com.cdai.util.ClassUtils.classUploadEntranceChamber;
import static com.cdai.util.ClassUtils.processClassUploadChamber;
import static com.cdai.util.ServiceUtil.buildIntakeList;

/**
 * Created by apple on 18/09/2017.
 */
@Controller
public class ClassController {
    /**
     *
     */
    private static final Logger logger = Logger.getLogger(ClassController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");




    /**
     *  RETURN CLASS PAGE
     */
    @RequestMapping(value = "/admin/class/view/{classId}",method = RequestMethod.GET)
    public String getClassPage(Model mode, @PathVariable int classId){
        ClassDao daoAccess = (ClassDao) applicationContext.getBean("ClassDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        Long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        mode.addAttribute("classId",classId);
        mode.addAttribute("aboutClass",daoAccess.getSingleClassDetailsByClassId(classId));
        mode.addAttribute("classList",daoAccess.getClassListByClassId(classId));
        mode.addAttribute("enrollmentCount",daoAccess.getCountOfTotalEnrolledStudentsInClass(classId));
        mode.addAttribute("currentUserRole",currentUserRole);

        return "Admin_App/subPages/classPage";
    }

    /**
     *  RETURN CLASS ENROLMENT PAGE
     */
    @RequestMapping(value = "/admin/class/enroll/{classId}",method = RequestMethod.GET)
    public String getClassEnrollmentPage(Model model, @PathVariable int classId){
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
        ClassDao daoAccessClass = (ClassDao) applicationContext.getBean("ClassDao");

        model.addAttribute("classId",classId);
        model.addAttribute("studentsNotEnrolledInAnyClassList",daoAccess.getStudentsNotEnrolledInAnyClassList());
        model.addAttribute("classEnrolledList",daoAccess.getClassEnrolledListByClassId(classId));
        model.addAttribute("enrollmentCount",daoAccessClass.getCountOfTotalEnrolledStudentsInClass(classId));
        model.addAttribute("aboutClass",daoAccessClass.getSingleClassDetailsByClassId(classId));

        return "Admin_App/subPages/classStudentEnrolmentPage";
    }

    /**
     * PROCESS ENROLL STUDENT TO CLASS
     */
    @ResponseBody
    @RequestMapping(value = "/admin/class/process-enrollment/{classId}/{userId}", method = RequestMethod.POST)
    public String enrollStudentInClass(Model model, @PathVariable int classId,@PathVariable long userId,HttpServletRequest request){
        ClassDao daoAccess = (ClassDao) applicationContext.getBean("ClassDao");

        logger.info("@enrollStudentInClass::CLASS ID:>>:"+classId);
        logger.info("@enrollStudentInClass::USER ID::>>:"+userId);

        String confirmFinal = "";

//        if(daoAccess.checkIfStudentBelongsToClassInSystem(userId)){
//            System.out.println("@enrollStudentInClass::....CHECKING WHAT CLASS USER IS IN .....");
//            String confirm = daoAccess.getStudentClassByUserId(userId);
//            System.out.println("@::enrollStudentInClassUSER CLASS ===>>"+confirm );
//            System.out.println("@enrollStudentInClass::USER ALREADY BELONGS TO A CLASS IN SYSTEM NOT ADDING");
//        }else{
            System.out.println("@enrollStudentInClass::............ADDING USER TO CLASS............");
            confirmFinal = daoAccess.enrollStudentInClass(classId,userId);
            logger.info("@enrollStudentInClass:::"+confirmFinal);

//        }

        return confirmFinal;
    }

    /**
     * PROCESS DELETE STUDENT FROM CLASS
     */
    @ResponseBody
    @RequestMapping(value = "/admin/class/remove/{classId}/{userId}", method = RequestMethod.POST)
    public String deleteStudentFromClass(Model model, @PathVariable int classId,@PathVariable long userId,HttpServletRequest request){
        ClassDao daoAccess = (ClassDao) applicationContext.getBean("ClassDao");

        logger.info("@deleteStudentFromClass::CLASS ID:>>:"+classId);
        logger.info("@deleteStudentFromClass::USER ID::>>:"+userId);

        String confirm = daoAccess.deleteStudentFromClass(classId,userId);
        logger.info("@deleteStudentFromClass ::"+confirm);


        return confirm;
    }



    /**
     *  RETURN ADD CLASS FORM
     */
    @RequestMapping(value = "/admin/class/{action}",method = RequestMethod.GET)
    public String getAddClassForm(Model model,@PathVariable String action){
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        ClassDao daoAccessClass = (ClassDao) applicationContext.getBean("ClassDao");

        model.addAttribute("intakeList",buildIntakeList());
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("lastAddedClasses", daoAccessClass.getLastAddedClasses());
        model.addAttribute("action",action);

        return "Admin_App/subPages/addClassForm";
    }



    /**
     * PROCESS CLASS BEING ADDED
     */
    @ResponseBody
    @RequestMapping(value = "/admin/class/add", method = RequestMethod.POST)
    public String addClass(Model model, HttpServletRequest request){
        ClassDao daoAccessClass = (ClassDao) applicationContext.getBean("ClassDao");
        ClassMajorDao daoAccesscClassMajor = (ClassMajorDao) applicationContext.getBean("ClassMajorDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        logger.info( request.getParameter("major"));
        logger.info( request.getParameter("className"));
        logger.info(request.getParameter("intake"));

        int majorId = Integer.parseInt(request.getParameter("major"));
        int intake = Integer.parseInt(request.getParameter("intake"));
        String className = request.getParameter("className");

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        Calendar startYear = Calendar.getInstance();
        Calendar midYear = Calendar.getInstance();

        startYear.set(year,Calendar.JANUARY,1);
        midYear.set(year,Calendar.JULY,30);

        if(intake == 0 ){
            if(now.after(startYear) && now.before(midYear)){
                intake = year -1;
            }
            else{
                intake = year -2;
            }
        }

        int classId= daoAccessClass.getLargestClassId();
        if (classId == -1){
            classId = 0;
        }
        System.out.println("classId = "+classId);


        Class newClass = new Class();
        newClass.setId(++classId);
        newClass.setClassName(className);
        newClass.setIntakePeriod(intake);
        newClass.setMajorId(majorId);
        newClass.setCreatedBy(currentUserId);

//        if(!daoAccessClass.checkIfClassNameAlreadyInSystem(className)){
            daoAccessClass.createNewClass(newClass);
//            daoAccesscClassMajor.addClassToMajor(majorId,newClass.getId());
            System.out.println("@::New Class Has Been Created Successfully");
            return "200";
//        }else{
//            System.out.println("@::Error Could Not Create New Class , Class Name Entered Already In System, Try Agin With New Class Name");
//            return "400";
//        }
    }

    /**
     *  RETURN EDIT CLASS FORM
     */
    @RequestMapping(value = "/admin/class/{action}/{classId}",method = RequestMethod.GET)
    public String getEditClassForm(Model model,@PathVariable String action,@PathVariable int classId){
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        ClassDao daoAccessClass = (ClassDao) applicationContext.getBean("ClassDao");

        model.addAttribute("intakeList",buildIntakeList());
        model.addAttribute("lastEditedClasses", daoAccessClass.getLastAddedClasses());
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("action",action);
        model.addAttribute("classId",classId);
        model.addAttribute("classCurrentDetails",daoAccessClass.getSingleClassDetailsByClassId(classId));

        return "Admin_App/subPages/addClassForm";
    }

    /**
     * PROCESS CLASS UPDATE
     */
    @ResponseBody
    @RequestMapping(value = "/admin/class/update/{classId}", method = RequestMethod.POST)
    public String updateClass(Model model, HttpServletRequest request,@PathVariable int classId){
        ClassDao daoAccess = (ClassDao) applicationContext.getBean("ClassDao");
        ClassMajorDao daoAccessClassMajor = (ClassMajorDao) applicationContext.getBean("ClassMajorDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        logger.info(request.getParameter("major"));
        logger.info( request.getParameter("className"));
        logger.info(request.getParameter("intake"));
        logger.info(request.getParameter("status"));

        System.out.println(daoAccessClassMajor.updateClassMajor(Integer.parseInt(request.getParameter("major")), classId));
        String confirm  =daoAccess.updateExistingClass(new Class(classId,request.getParameter("className"),Integer.parseInt(request.getParameter("intake")),request.getParameter("status")));

        return confirm;
    }

    /**
     * DELETE CLASS AND CLASS STUDENTS
     */
    @ResponseBody
    @RequestMapping(value = "/admin/class/delete-class/{classId}", method = RequestMethod.POST)
    public String deleteStudentFromClass(Model model, @PathVariable int classId,HttpServletRequest request){
        ClassDao daoAccess = (ClassDao) applicationContext.getBean("ClassDao");

        try{
            daoAccess.deleteExistingClass(classId);
            daoAccess.deleteExistingClassStudents(classId);
            daoAccess.deleteClassMajor(classId);

        }catch (Exception e){
            e.printStackTrace();
            return "400";
        }

        System.out.println("Class Deletion Process Complete ...");
        return "200";
    }


    /**
     * ADD CLASS EXCEL
     */
    @ResponseBody
    @RequestMapping(value = "admin/manage-users/add-class-excel",method = RequestMethod.POST)
    public JSONObject addClassByUploadingExcelFile(Model model,
                                               @RequestParam("classExcelListFile")MultipartFile classExcelListFile,
                                               @RequestParam("majorId") int majorId,
                                               @RequestParam("intake")  String intake) {

        /**
         * CREATE ROW
         */
        HSSFRow row;

        /**
         * GET RESPECTIVE DAO ACCESS
         */
        ClassDao daoAccess = (ClassDao) applicationContext.getBean("ClassDao");
        long userId = UserUtils.getUser().getUserId();
        JSONObject result = new JSONObject();

        logger.debug("@ClassController::@addClassByUploadingExcelFile:::#STARTING TO IMPORT CLASS EXCEL FILE#");
        logger.info("Major id = "+majorId);
        logger.info("Intake = "+intake);


        try{


            if (classExcelListFile.isEmpty()) {
                System.out.println("NO DATA RECEIVED!!");
            } else {
                System.out.println("DATA RECEIVED!!");
            }


            HSSFWorkbook workbook = new HSSFWorkbook(classExcelListFile.getInputStream());

            JSONObject res = classUploadEntranceChamber(workbook,majorId,intake);
            System.out.println("Reciving ==========="+res.getString("msg"));
            List<Class> list = (List<Class>) res.get("data");
            if(res.getString("msg").equals("200")){
//               for(Class entity: tempList){
//                System.out.println(entity.getClassName());
//            }
                processClassUploadChamber(list);
                result.put("status_code",200);
                result.put("list",list);
            }
            else if(res.getString("msg").equals("405")){
                result.put("status_code",405);
                result.put("list",list);
            }
            else if(res.getString("msg").equals("406")){
                result.put("status_code",406);
                result.put("list",list);
            }else{
                result.put("status_code",400);
                result.put("list",list);
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        return result;


    }


}
