package com.cdai.controllers.appController;

import build.dao.*;
import build.model.*;
import com.alibaba.fastjson.JSONObject;
import com.cdai.security.HashCredentials;
import com.cdai.security.UserUtils;
import com.sun.istack.internal.Nullable;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;

import static com.cdai.util.ServiceUtil.checkIfInputIsAnInteger;
import static com.cdai.util.ServiceUtil.getStudentIntakeList;
import static com.cdai.util.UserUtil.*;

/**
 * Created by apple on 08/09/2017.
 */
@Controller
public class UserController {
    /**
     *
     */
    private static final Logger logger = Logger.getLogger(UserController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");

    /**
     * ADD USER UI
     */
    @RequestMapping(value = "admin/manage-users/add/{nav}",method = RequestMethod.GET)
    public String adminAddUser(Model model, @PathVariable int nav){
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
        SemesterDao daoAccessSems = (SemesterDao) applicationContext.getBean("SemesterDao");
        ClassDao daoAccessClass = (ClassDao) applicationContext.getBean("ClassDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");

        model.addAttribute("nav",nav);
        model.addAttribute("lastAdded",daoAccess.getLastAddedUsersList());
        model.addAttribute("semesterList", daoAccessSems.getSystemSemesterList());
        model.addAttribute("classList",daoAccessClass.getAllSystemClasses());
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());
        model.addAttribute("studentIntakeList",getStudentIntakeList());
        return "Admin_App/subPages/addUserForm";
    }

    /**
     * ADMIN process Add USER
     */
    @ResponseBody
    @RequestMapping(value = "admin/process-add-user",method = RequestMethod.POST)
    public String adminProcessAddUserForm(Model model, HttpServletRequest request){

        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        UserProfileDao daoAccessProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");
        RoleDao daoAccess = (RoleDao) applicationContext.getBean("roleDao");
        ClassDao daoAccessClass = (ClassDao) applicationContext.getBean("ClassDao");
        StudentMajorDao daoAccessMajor = (StudentMajorDao) applicationContext.getBean("StudentMajorDao");

        int role = 0;
        String defaultPassword = "";

        logger.info(request.getParameter("role"));
        logger.info(request.getParameter("majorId"));
        logger.info(request.getParameter("userId"));
        logger.info(request.getParameter("firstName"));
        logger.info(request.getParameter("lastName"));
        logger.info(request.getParameter("gender"));
        logger.info(request.getParameter("dateOfBirth"));
        logger.info(request.getParameter("email"));
        logger.info(request.getParameter("city"));
        logger.info(request.getParameter("country"));
        logger.info(request.getParameter("classId"));
        logger.info(request.getParameter("intake"));
        logger.info(request.getParameter("degreeType"));
        logger.info(request.getParameter("passwordSelect"));
        logger.info("password = 1234");

        if(!checkIfInputIsAnInteger(request.getParameter("userId"))){
            System.out.println("User Id Format Error , User Id Can only Be in integer form using number no letters allowed ");
            return "502";
        }


        // CREATING USER
        User newUser = new User();
        newUser.setUserId(Long.parseLong(request.getParameter("userId")));
        switch (request.getParameter("role")) {
            case "Admin": role = 1;
                break;
            case "Teacher":  role = 2;
                break;
            case "Student":  role = 3;
                break;
            default: role = 0;
                break;
        }

        //SET DEFAULT PASSWORD FROM SELECTED OPTIONS
        switch (Integer.parseInt(request.getParameter("passwordSelect"))) {
            case 1: defaultPassword = request.getParameter("userId");
                break;
            case 2:  defaultPassword = request.getParameter("firstName");
                break;
            case 3:  defaultPassword = request.getParameter("lastName");
                break;
            default: defaultPassword = defaultPassword = request.getParameter("userId");
                break;
        }



        //CREATING USER
        String fullName = request.getParameter("firstName");
        newUser.setRoleId(role);
        newUser.setUserName(fullName);
        newUser.setEmail(request.getParameter("email"));
        newUser.setPassword(new HashCredentials().securePass("cdai",defaultPassword));
        newUser.setSalt("1234");
        //CREATING USER

        //INSERT INTO USER PROFILE
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(Long.parseLong(request.getParameter("userId")));
        userProfile.setProfileImageUrl("avatar.png");
        userProfile.setChineseFullName(fullName);
        userProfile.setFirstName(request.getParameter("firstName"));
        userProfile.setLastName(request.getParameter("lastName"));
        userProfile.setOtherName("");
        userProfile.setGender(request.getParameter("gender"));
        userProfile.setDateOfBirth(request.getParameter("dateOfBirth"));
        userProfile.setMobilePhone("");
        userProfile.setWeChatId("");
        userProfile.setQqId("");
        userProfile.setHomeAddress("");
        userProfile.setCity(request.getParameter("city"));
        userProfile.setCountry(request.getParameter("country"));
        userProfile.setInterests("");
        userProfile.setSelfDescription("");
        userProfile.setRole(request.getParameter("role"));


//        if(newUser.getRoleId() == 3){
//            userProfile.setMajorId(Integer.parseInt(request.getParameter("majorId")));
//            userProfile.setDegreeType(request.getParameter("degreeType"));
//            userProfile.setIntake(request.getParameter("intake"));
//        }

        String msg = "";

        if(!daoAccessUser.checkIfUserIdInSystem(newUser.getUserId())){
            if(newUser.getRoleId() == 3){
                if(request.getParameter("classId") == null  || request.getParameter("classId").equals("")){
                    logger.info

                            ("No Class Selected ");
                }else {
                    String confirmClass = daoAccessClass.enrollStudentInClass(Integer.parseInt(request.getParameter("classId")), Long.parseLong(request.getParameter("userId")));
                    System.out.println("::::>" + confirmClass);
                }
            }
            System.out.println(" saved data  :>> " +daoAccessUser.createNewUser(newUser));
            if(role == 3){
                System.out.println("@:::"+daoAccessProfile.createNewStudentProfile(userProfile));
                System.out.println( daoAccessMajor.addStudentToMajor(userProfile.getMajorId(),userProfile.getUserId()));
            }else {
                System.out.println("@:::"+daoAccessProfile.createNewUserProfile(userProfile));
            }
            System.out.println(daoAccess.addUserRole(Long.parseLong(request.getParameter("userId")),role));
            msg = "200";
        }else{
            msg =  "501";
        }



        return msg;
    }

    /**
     * ADD USER VIA UPLOADING USER EXCEL FILE
     */
    @ResponseBody
    @RequestMapping(value = "manage-users/add-user-excel",method = RequestMethod.POST)
    public JSONObject addUserByUploadingUserExcelFile(
                                                  @RequestParam("userExcelListFile") MultipartFile userExcelListFile,
//                                                  @RequestParam("majorId") int majorId,
//                                                  @RequestParam("intake")  String intake,
                                                  @RequestParam("classId") int classId) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        //DAOS
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        UserProfileDao daoAccessProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");
        RoleDao daoAccessRole = (RoleDao) applicationContext.getBean("roleDao");
        StudentMajorDao daoAccessMajor = (StudentMajorDao) applicationContext.getBean("StudentMajorDao");
        StudentClassDao daoAccessClass = (StudentClassDao) applicationContext.getBean("StudentClassDao");

//        logger.info("Major id = "+majorId);
//        logger.info("Intake = "+intake);
        logger.info("Class id = "+classId);


        List<UserProfile> tempListError = new ArrayList<>();
        List<UserProfile> tempListErrorDuplicates = new ArrayList<>();
        List<UserProfile> tempListProcessed = new ArrayList<>();
        List<UserProfile> tempListProcessedFinal = new ArrayList<>();
        HashSet<Long> set = new HashSet<>();
        JSONObject result = new JSONObject();

//        Calendar now = Calendar.getInstance();
//        int year = now.get(Calendar.YEAR);
//        Calendar startYear = Calendar.getInstance();
//        Calendar midYear = Calendar.getInstance();
//
//        startYear.set(year, Calendar.JANUARY, 1);
//        midYear.set(year, Calendar.JULY, 30);

//        if (intake.equals("")) {
//            if (now.after(startYear) && now.before(midYear)) {
//                intake = year + "-1";
//            } else {
//                intake = year + "-2";
//            }
//        }

        HSSFRow row;
        try {

            int i = 0;

            if (userExcelListFile.isEmpty()) {
                System.out.println("NO DATA RECEIVED!!");
            } else {
                System.out.println("DATA RECEIVED!!");
            }

            Workbook workbook = WorkbookFactory.create(userExcelListFile.getInputStream());

        //    HSSFWorkbook workbook = new HSSFWorkbook(userExcelListFile.getInputStream());
         //   XSSFWorkbook workbook = new XSSFWorkbook(userExcelListFile.getInputStream());
        //    JSONObject res = studentUploadEntranceChamber(intake,workbook);
            JSONObject res = studentUploadEntranceChamber(workbook);
            System.out.println("Reciving ==========="+res.getString("msg"));
            List<User>  list = (List<User>) res.get("data");
            if(res.getString("msg").equals("200")){
//                for(UserProfile entity: list ){
//                    System.out.println(entity.getUserId());
//                }
                processStudentUploadChamber(classId,list);
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


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("System Error!!");
            return null;
        }
//        //logger.info("This is Error message", new Exception("Testing"));

        return result;
    }

    /**
     * ADD TEACHER VIA UPLOADING USER EXCEL FILE
     */
    @ResponseBody
    @RequestMapping(value = "manage-users/add-teacher-excel",method = RequestMethod.POST)
    public JSONObject addTeacherByUploadingExcelFile(Model model, @RequestParam("teacherExcelListFile") MultipartFile teacherExcelListFile){
        HSSFRow row;

        //DAOS
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        UserProfileDao daoAccessProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");
        RoleDao daoAccessRole = (RoleDao) applicationContext.getBean("roleDao");
        //MODELS

        User newUser = new User();
        UserProfile userProfile = new UserProfile();

        List<UserProfile> tempListError = new ArrayList<>();
        List<UserProfile> tempListProcessed = new ArrayList<>();
        JSONObject result = new JSONObject();

        logger.info(">> Started importing Teachers Excel file ");
        try{

            int i =0;

            if (teacherExcelListFile.isEmpty()){
                System.out.println("NO DATA RECEIVED!!");
            }
            else {
                System.out.println("DATA RECEIVED!!");
            }

            HSSFWorkbook workbook = new HSSFWorkbook(teacherExcelListFile.getInputStream());
            HSSFSheet worksheet = workbook.getSheetAt(0);
            Iterator< Row > rowIterator = worksheet.iterator();

            logger.info(">> Reading Excel file ");
            int count =0;
            JSONObject res = teacherUploadEntranceChamber(workbook);
            System.out.println("Reciving ==========="+res.getString("msg"));
            List<UserProfile>  list = (List<UserProfile>) res.get("data");
            if(res.getString("msg").equals("200")){
//                for(UserProfile entity: list ){
//                    System.out.println(entity.getUserId());
//                }
                List<UserProfile> tempList = (List<UserProfile>) res.get("data");
                processTeacherUploadChamber(tempList);

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

        }catch(Exception e){
            e.printStackTrace();
            result.put("status_code",400);
            result.put("list",null);
            return result;
        }

        return result;
    }

    /**
     * ADMIN EDIT USER PROFILE
     */
    @RequestMapping(value = "admin/view/user-profile",method = RequestMethod.GET)
    public String adminViewUserProfile( Model model){

        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");
        GradeItemsDao daoAccessGradeItem = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        UserProfileDao daoAccessUserProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        model.addAttribute("userId",currentUserId);
        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("userProfile",daoAccessUserProfile.displayUserProfileDetails(currentUserId));
        model.addAttribute("currentCourses",daoAccessCourse.getTeacherCurrentCourses(currentUserId));
        model.addAttribute("allCourses",daoAccessCourse.getAllTeacherCourses(currentUserId));
        model.addAttribute("currentCourseCounts",daoAccessCourse.getTeacherCurrentCourseCount(currentUserId));
        model.addAttribute("allCoursesCount",daoAccessCourse.getAllTeacherCoursesCount(currentUserId));
        model.addAttribute("allExams",daoAccessGradeItem.getAllTeacherExams(currentUserId));
        model.addAttribute("allExamsCount",daoAccessGradeItem. getAllTeacherExamsCount(currentUserId));



        //get profile date
        //get user data

        return "Admin_App/subPages/userProfilePage";
    }

//    /**
//     * STUDENT EDIT PROFILE
//     */
//    @RequestMapping(value = "student/profile/edit/{userId}",method = RequestMethod.GET)
//    public String studentEditProfile(@PathVariable int userId, Model model){
//
//        model.addAttribute("userId",userId);
//
//        return "Admin_App/subPages/userProfilePage";
//    }
//
//    /**
//     * STUDENT  PROCESS EDIT PROFILE
//     */
//    @RequestMapping(value = "student/profile/edit/{userId}",method = RequestMethod.POST)
//    public String makeEditToStudentProfile(@PathVariable int userId, Model model){
//
//        model.addAttribute("userId",userId);
//
//        return "Admin_App/subPages/userProfilePage";
//    }




    /**
     * RETURN EDIT PROFILE FORM
     */
    @RequestMapping(value = "/admin/profile/view/{userId}",method = RequestMethod.GET)
    public String getProfileForm(Model model,@PathVariable long userId){

        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");
        GradeItemsDao daoAccessGradeItem = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        UserProfileDao daoAccessUserProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");
        ExamEnrolmentDao daoExamEnroll = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");
        ExamDao daoExam = (ExamDao) applicationContext.getBean("ExamDao");


        List<Courses> currentCoursesList = null;
        int currentCourseCount = 0;
        List<Courses> allCoursesList = null;
        int allCourseTotal = 0;
        List<Exam> examsList = null;
        int allExamCount = 0;
        List<Courses> coursesWithGradeAverageList = null;

        UserProfile userProfile = new UserProfile();
        userProfile = daoAccessUserProfile.displayUserProfileDetails(userId);

        if(userProfile.getRole().equals("Student")){
            currentCoursesList = daoAccess.getStudentCurrentCourses(userId);
            currentCourseCount  = daoAccess.getStudentCurrentCoursesCount(userId);
            allCoursesList = daoAccess.getAllStudentCourses(userId);
            allCourseTotal = daoAccess.getAllStudentCoursesCount(userId);
            examsList = daoExamEnroll.getStudentEnrolledExamList(userId);
            allExamCount = daoExamEnroll.getCountOfEnrolledExamsByUserId(userId);

            coursesWithGradeAverageList = daoAccess.getUserCoursesGradeAverage(userId); //to be revised
            model.addAttribute("semesterList",daoAccess.getStudentCustomSemesterList(userId));
        }
        else{
            currentCoursesList = daoAccess.getTeacherCurrentCourses(userId);
            currentCourseCount  = daoAccess.getTeacherCurrentCourseCount(userId);
            allCoursesList = daoAccess.getAllTeacherCourses(userId);
            allCourseTotal = daoAccess.getAllTeacherCoursesCount(userId);
            examsList = daoExam.teacherGetCourseExamDataList(userId);
            allExamCount = daoExam.getTotalTeacherCourseExamCount(userId);
        }

        model.addAttribute("userId",userId);
        model.addAttribute("userProfile",userProfile);
        model.addAttribute("currentCoursesList",currentCoursesList);
        model.addAttribute("currentCourseCount",currentCourseCount);
        model.addAttribute("allCoursesList",allCoursesList);
        model.addAttribute("allCourseTotal",allCourseTotal);
        model.addAttribute("examsList",examsList);
        model.addAttribute("allExamCount",allExamCount);
        model.addAttribute("coursesWithGradeAverageList",coursesWithGradeAverageList);



        return "Admin_App/subPages/viewUserProfilePage";
    }

    /**
     * RETURN EDIT PROFILE FORM
     */
    @RequestMapping(value = "/admin/profile/edit/{userId}/{nav}",method = RequestMethod.GET)
    public String getEditProfileForm(Model model,@PathVariable long userId, @PathVariable int nav){
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("userId",userId);
        model.addAttribute("nav",nav);
        model.addAttribute("userMainDetails",daoAccess.getUserByUserId(userId));
        model.addAttribute("userProfile",daoAccess.getCurrentProfileData(userId));
        model.addAttribute("userSettings",daoAccess.getUserSettings(userId));
        model.addAttribute("lastEditedUsers", daoAccess.getLastEditedUsersList());
        model.addAttribute("majorSelectList", daoAccessMajor.getAllMajorNamesSelectList());


        return "Admin_App/subPages/editProfileForm";
    }


    /**
     * STUDENT VIEW  PROFILE
     */
    @RequestMapping(value = "/student/profile",method = RequestMethod.GET)
    public String studentProfile(Model model){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");
        GradeItemsDao daoAccessGradeItem = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        UserProfileDao daoAccessUserProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");

        CourseEnrolmentDao daoAccessCourseEnrolment = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");
        ExamEnrolmentDao daoAccessExamEnrolment = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");

        //Subject currentUser = SecurityUtils.getSubject();
        //int userId = (Long) currentUser.getPrincipal();
        long userId = UserUtils.getUser().getUserId();
        UserProfile userProfile = new UserProfile();

        userProfile = daoAccessUserProfile.displayUserProfileDetails(userId);
        List<Courses> currentCoursesList = daoAccess.getStudentCurrentCourses(userId);
        int currentCourseCount  = daoAccess.getStudentCurrentCoursesCount(userId);
        List<Courses> allCoursesList = daoAccess.getAllStudentCourses(userId);
        int allCourseTotal = daoAccess.getAllStudentCoursesCount(userId);
        List<Exam> allStudentExamsList = daoAccessExamEnrolment.getStudentEnrolledExamList(userId);
        int allExamCount = daoAccessGradeItem.getAllStudentExamsCount(userId);


        model.addAttribute("userId",userId);
        model.addAttribute("userProfile",userProfile);
        model.addAttribute("currentCoursesList",currentCoursesList);
        model.addAttribute("currentCourseCount",currentCourseCount);
        model.addAttribute("allCoursesList",allCoursesList);
        model.addAttribute("allCourseTotal",allCourseTotal);
        model.addAttribute("allStudentExamsList",allStudentExamsList);
        model.addAttribute("allExamCount",allExamCount);

//        model.addAttribute("pendingExams",daoAccessExamEnrolment.getExamStudentEnrolmentPendingListByUserId(userId));
//        model.addAttribute("pendingCourses",daoAccessCourseEnrolment.getCourseStudentEnrolmentPendingListByUserId(userId));
//        model.addAttribute("enrolledExams",daoAccessExamEnrolment.getExamStudentEnrolmentConfirmationListByUserId(userId));
//        model.addAttribute("enrolledCourses",daoAccessCourseEnrolment.getCourseStudentEnrolmentConfirmationListByUserId(userId));



        return "Student_App/subPages/userProfilePage";
    }

    /**
     * STUDENT VIEW  TEACHER PROFILE
     */
    @RequestMapping(value = {"student/view/{teacherId}","admin/view/{teacherId}"},method = RequestMethod.GET)
    public String studentViewTeacherProfile(Model model , @PathVariable long teacherId){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");
        GradeItemsDao daoAccessGradeItem = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        UserProfileDao daoAccessUserProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");

        Session session = SecurityUtils.getSubject().getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        model.addAttribute("currentUserRole",currentUserRole);
        model.addAttribute("teacherId",teacherId);
        model.addAttribute("role","teacher");
        model.addAttribute("userProfile",daoAccessUserProfile.displayUserProfileDetails(teacherId));
        model.addAttribute("currentCourses",daoAccessCourse.getTeacherCurrentCourses(teacherId));
        model.addAttribute("allCourses",daoAccessCourse.getAllTeacherCourses(teacherId));
        model.addAttribute("currentCourseCounts",daoAccessCourse.getTeacherCurrentCourseCount(teacherId));
        model.addAttribute("allCoursesCount",daoAccessCourse.getAllTeacherCoursesCount(teacherId));
        model.addAttribute("allExams",daoAccessGradeItem.getAllTeacherExams(teacherId));
        model.addAttribute("allExamsCount",daoAccessGradeItem. getAllTeacherExamsCount(teacherId));



        return "Student_App/subPages/teacherProfile";
    }


    /**
     * RETURN STUDENT EDIT PROFILE
     */
    @RequestMapping(value = "/student/profile/edit",method = RequestMethod.GET)
    public String studentEditProfileForm(Model model){
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");

        User user = UserUtils.getUser();
        long userId = user.getUserId();

        model.addAttribute("userId",userId);
        model.addAttribute("userMainDetails",daoAccess.getUserByUserId(userId));
        model.addAttribute("userProfile",daoAccess.getCurrentProfileData(userId));
        model.addAttribute("userSettings",daoAccess.getUserSettings(userId));


        return "Student_App/subPages/studentEditProfile";
    }

    /**
     *  PROCESS EDIT PROFILE FORM
     */
    @ResponseBody
    @RequestMapping(value = "/admin/profile/edit/{userId}", method = RequestMethod.POST)
    public String editProfile(Model model, @PathVariable long userId, HttpServletRequest request){

        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
        RoleDao daoAccessRole = (RoleDao) applicationContext.getBean("roleDao");
        int role = 0;

        logger.info(request.getParameter("role"));
        logger.info(request.getParameter("userId"));
        logger.info(request.getParameter("firstName"));
        logger.info(request.getParameter("lastName"));
        logger.info(request.getParameter("gender"));
        logger.info(request.getParameter("dateOfBirth"));
        logger.info(request.getParameter("email"));
        logger.info(request.getParameter("city"));
        logger.info(request.getParameter("country"));
        logger.info(request.getParameter("userClass"));
        logger.info(request.getParameter("intake"));
        logger.info(request.getParameter("radioSelect"));
        logger.info(request.getParameter("passwordSelect"));
        logger.info("password = 1234");

//        newUser.setUserId(Long.parseInt(request.getParameter("userId")));
        switch (request.getParameter("role")) {
            case "Admin": role = 1;
                break;
            case "Teacher":  role = 2;
                break;
            case "Student":  role = 3;
                break;
            default: role = 0;
                break;
        }

        User user = new User();
        UserProfile userProfile = new UserProfile();

        userProfile.setUserId(userId);
        userProfile.setFirstName(request.getParameter("firstName"));
        userProfile.setLastName(request.getParameter("lastName"));
        userProfile.setDateOfBirth(request.getParameter("dateOfBirth"));
        userProfile.setGender(request.getParameter("gender"));
        userProfile.setRole(request.getParameter("role"));
        userProfile.setCity(request.getParameter("city"));
        userProfile.setCountry(request.getParameter("country"));

        user.setUserId(userId);
        user.setUserName(userProfile.getFirstName()+" "+userProfile.getLastName());
        user.setRoleId(role);
        user.setEmail(request.getParameter("email"));

        String confirm1 = daoAccess.adminUpdateUserProfileDetails(userProfile);
        String confrim2 = daoAccess.adminUpdateUserMainDetails(user);
        String confirm3 = daoAccessRole.updateUserRole(userId,role);



        System.out.println(confirm1 +"==>>"+ confrim2+"==>>"+confirm3);


        return confirm1 +"==>>"+ confrim2;
    }

    /**
     *  PROCESS STUDENT EDIT PROFILE FORM
     */
    @ResponseBody
    @RequestMapping(value = "/student/profile/edit/{userId}", method = RequestMethod.POST)
    public String studentEditProfile(Model model, @PathVariable long userId, HttpServletRequest request){

        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
        RoleDao daoAccessRole = (RoleDao) applicationContext.getBean("roleDao");
        int role = 3;

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        logger.info(request.getParameter("role"));
        logger.info(currentUserId);
        logger.info(request.getParameter("firstName"));
        logger.info(request.getParameter("lastName"));
        logger.info(request.getParameter("gender"));
        logger.info(request.getParameter("dateOfBirth"));
        logger.info(request.getParameter("email"));
        logger.info(request.getParameter("city"));
        logger.info(request.getParameter("country"));
        logger.info(request.getParameter("primaryLanguage"));
        logger.info(request.getParameter("userClass"));
        logger.info(request.getParameter("intake"));
        logger.info(request.getParameter("degreeType"));
        logger.info(request.getParameter("radioSelect"));
        logger.info(request.getParameter("passwordSelect"));
        logger.info(request.getParameter("description"));
        logger.info("password = 1234");



        User user = new User();
        UserProfile userProfile = new UserProfile();

        userProfile.setUserId(userId);
        userProfile.setFirstName(request.getParameter("firstName"));
        userProfile.setLastName(request.getParameter("lastName"));
        userProfile.setDateOfBirth(request.getParameter("dateOfBirth"));
        userProfile.setGender(request.getParameter("gender"));
        userProfile.setRole(request.getParameter("role"));
        userProfile.setCity(request.getParameter("city"));
        userProfile.setCountry(request.getParameter("country"));
        userProfile.setPrimaryLanguage(request.getParameter("primaryLanguage"));
        userProfile.setSelfDescription(request.getParameter("description"));

        user.setUserId(userId);
        user.setUserName(userProfile.getFirstName()+" "+userProfile.getLastName());
        user.setRoleId(role);
        user.setEmail(request.getParameter("email"));

        String confirm1 = daoAccess.studentUpdateDetails(userProfile);
        String confrim2 = daoAccess.studentUpdateUserMainDetails(user);


        if(confirm1.equals("200") && confrim2.equals("200")){
            return "200";
        }else{
            return "400";
        }

    }
    /**
     *  CHANGE USER SETTINGS
     */
    @ResponseBody
    @RequestMapping(value = "/admin/change-user-settings/{userId}", method = RequestMethod.POST)
    public String changeProfileImage(Model model,@PathVariable long userId ,HttpServletRequest request){

        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
        StudentMajorDao daoAccessMajor = (StudentMajorDao) applicationContext.getBean("StudentMajorDao");

      //  logger.info(request.getParameter("majorId"));
        logger.info(request.getParameter("degreeType"));
        logger.info(request.getParameter("primaryLanguage"));

        UserProfile userSettings = new UserProfile();

        userSettings.setUserId(userId);
        userSettings.setPrimaryLanguage(request.getParameter("primaryLanguage"));
     //   userSettings.setDegreeType(request.getParameter("degreeType"));

        String confirm = daoAccess.changeUserProfileSettings(userSettings);
      //  daoAccessMajor.updateStudentsMajor(Integer.parseInt(request.getParameter("majorId")),userId);

        logger.info(confirm);

        return confirm;
    }

    /**
     *  ADMIN RESET USER PASSWORD TO DEFAULT
     */
    @ResponseBody
    @RequestMapping(value = "/admin/profile/edit/reset-password/{userId}", method = RequestMethod.POST)
    public String changePassword(Model model,@PathVariable long userId ,HttpServletRequest request){
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");

        logger.info(request.getParameter("passwordSelect"));
        String securePass = new HashCredentials().securePass("cdai",request.getParameter("passwordSelect"));

        String confirm = daoAccess.changePassword(userId,securePass);

        logger.info(confirm);

        return confirm;
    }


    /**
     * RETURN PROFILE IMAGE FORM
     */
    @RequestMapping(value = "/admin/profile/image",method = RequestMethod.GET)
    public String getProfileImagePage(Model model){

        return "Admin_App/subPages/profileImageForm";
    }


    /**
     *  PROCESS PROFILE IMAGE  FORM
     */
    @ResponseBody
    @RequestMapping(value = "/admin/profile/image", method = RequestMethod.POST)
    public String changeProfileImage(Model model, HttpServletRequest request){

        return "200";
    }


    /**
     * RETURN CHANGE PASSWORD FORM
     */
    @RequestMapping(value = "/admin/profile/edit/change-password",method = RequestMethod.GET)
    public String getChangePasswordPage(Model model){

        return "Admin_App/subPages/changePasswordForm";
    }

    /**
     *  PROCESS CHANGE PASSWORD FORM
     */
    @ResponseBody
    @RequestMapping(value = {"/admin/profile/edit/change-password","/student/profile/edit/change-password"}, method = RequestMethod.POST)
    public String changePassword(Model model, HttpServletRequest request){
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");

        logger.info(request.getParameter("currentPassword"));
        logger.info(request.getParameter("newPassword"));

        String sysCurrentPassword = daoAccess.getUserPasswordByUserId(currentUserId);
        String secureInputedCurrentPassword = new HashCredentials().securePass("cdai",request.getParameter("currentPassword"));
        String secureNewPassword =  new HashCredentials().securePass("cdai",request.getParameter("newPassword"));

        if(sysCurrentPassword.equals(secureInputedCurrentPassword)){
            daoAccess.changePassword(currentUserId,secureNewPassword);
            return "Password changed successfully";
        }else{
            return "Current password input is incorrect";
        }
    }

    /**
     * STUDENT MAKE DEGREE TYPE CHANGE REQUEST
     */
    @ResponseBody
    @RequestMapping(value = "student/degree-change/request",method = RequestMethod.POST)
    public String degreeChangeMakeRequest(Model model,
                                          @RequestParam("newDegreeType") String newDegreeType){

        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        logger.info("newDegreeType  = " + newDegreeType);

        if(daoAccessUser.makeStudentDegreeTypeChangeRequest(new StudentDegreeTypeChangeRequest(currentUserId,daoAccessUser.getStudentDegreeType(currentUserId), newDegreeType)).equals("200")){
            return "200";
        }else{
            return "400";
        }

    }


    /**
     * DEGREE TYPE CHANGE REQUEST ACCEPT
     */
    @ResponseBody
    @RequestMapping(value = "admin/degree-change/accept",method = RequestMethod.POST)
    public String degreeChangeAccept(Model model,
                                       @RequestParam("requestId") int requestId,
                                     @RequestParam("studentId") long studentId,
                                     @RequestParam("degreeType") String degreeType){

        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        logger.info("requestId  = " +requestId);

       if(daoAccessUser.acceptStudentDegreeChangeRequest(requestId).equals("200")){
           daoAccessUser.changeStudentDegreeType(studentId, degreeType);
           return "200";
       }else{
           return "400";
       }

    }

    /**
     * STUDENT DEGREE CHANGE REQUEST DECLINE
     */
    @ResponseBody
    @RequestMapping(value = "admin/degree-change/decline",method = RequestMethod.POST)
    public String degreeChangeDeclined(Model model,
                                       @RequestParam("requestId") int requestId){

        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        logger.info("requestId  = " +requestId);

        if(daoAccessUser.declineStudentDegreeChangeRequest(requestId).equals("200")){
            return "200";
        }else{
            return "400";
        }

    }

    /**
     * DEGREE TYPE CHANGE REQUEST CANCEL
     */
    @ResponseBody
    @RequestMapping(value = "admin/degree-change/cancel",method = RequestMethod.POST)
    public String degreeChangeCancel(Model model,
                                     @RequestParam("requestId") int requestId){

        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        logger.info("requestId  = " +requestId);

        if(daoAccessUser.cancelDegreeTypeChangeRequest(requestId).equals("200")){
            return "200";
        }else{
            return "400";
        }

    }



    /**
     * ADMIN DELETE STUDENT FROM WHOLE SYSTEM
     */
    @ResponseBody
    @RequestMapping(value = "/admin/manage-users/delete-student/{studentId}",method = RequestMethod.POST)
    public String deleteStudentFromSystem(Model model,@PathVariable long studentId){

        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");

        try {
            daoAccess.deleteStudentFromCourseEnrolments(studentId);
            daoAccess.deleteStudentFromCourseStudentRequestEnrolment(studentId);
            daoAccess.deleteStudentFromExamEnrolment(studentId);
            daoAccess.deleteStudentFromExamStudentRequestEnrolment(studentId);
            daoAccess.deleteStudentFromGrade(studentId);
            daoAccess.deleteStudentFromStudentClass(studentId);
            daoAccess.deleteStudentFromUserRole(studentId);
            daoAccess.deleteUserFromUsers(studentId);
            daoAccess.deleteUserFromStudentMajor(studentId);
            String confirm = daoAccess.deleteUserFromUserProfile(studentId);

           return confirm;

        }catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }

    }

    /**
     * ADMIN DISABLE TEACHER AND ADMIN FROM WHOLE SYSTEM
     */
    @ResponseBody
    @RequestMapping(value = "/admin/manage-users/disable-admin/{userId}",method = RequestMethod.POST)
    public String disableAdminAndTeacherFromSystem(Model model,@PathVariable int userId){

        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");

        try {
            String confirm =  daoAccess.disableUser(userId);
            return confirm;

        }catch (Exception e){
            e.printStackTrace();
            return "Error!";
        }

    }


}
