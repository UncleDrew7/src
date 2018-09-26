package com.cdai.controllers.appController;

import build.dao.*;
import build.model.*;
import com.cdai.models.tempModels.*;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 05/11/2017.
 */
@Controller
public class ExcelController {
    private static final Logger logger = Logger.getLogger(ExcelController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");


    /**
     *  DOWNLOADING  EXAM EXCEL FILE TEMPLATE
     */
    @RequestMapping(value = "admin/download/examTemplate",method = RequestMethod.GET)
    public ModelAndView downloadExamExcel(@RequestParam("majorId") int majorId,
                                          @RequestParam("semesterId") int semesterId){
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");

        List<ExcelExam> examTemplate = new ArrayList<>();

        examTemplate.add(new ExcelExam("Computer Science","2017-2018-1",9008,56,"Networking and Switching","CS4092","Final Exam Name","2018-01-01","2017-12-01","2017-12-30"));
        for(ChildCourses entity : daoAccessChildCourses.getFilteredBySemesterChildCoursesForSpecificMajorId(semesterId,majorId)){
            ExcelExam excelExam = new ExcelExam();

            excelExam.setMajorName(entity.getMajorName());
            excelExam.setSemesterCode(entity.getSemesterCode());
            excelExam.setParentCourseId(entity.getParentCourseId());
            excelExam.setParentCourseName(entity.getCourseName());
            excelExam.setChildCourseId(entity.getChildCourseId());
            excelExam.setChildCourseName(entity.getChildCourseName());
            excelExam.setExamCode("");
            excelExam.setExamDate("");
            excelExam.setEnrolmentStartDate("");
            excelExam.setEnrolmentEndDate("");

            examTemplate.add(excelExam);
        }
        return new ModelAndView("examExcelTemplate","examTemplate",examTemplate);
    }


    /**
     *  DOWNLOADING CLASS EXCEL FILE TEMPLATE
     */
    @RequestMapping(value = "admin/download/class-excel",method = RequestMethod.GET)
    public ModelAndView downloadClassExcel(){

        List<ExcelClass> classTemplate = new ArrayList<>();
        classTemplate.add(new ExcelClass("class123","2013 Fall"));

        return new ModelAndView("classExcelTemplate","classTemplate",classTemplate);
    }
    /**
     *  DOWNLOADING CLASS EXCEL FILE TEMPLATE
     */


    /**
     * DOWNLOAD TEACHER EXCEL FILE
     */
    @RequestMapping(value="download/teacher-excel",method = RequestMethod.GET)
    public ModelAndView downloadTeacherExcel(){

        List<ExcelTeacher> template = new ArrayList<ExcelTeacher>();
//        List<Book> template = new ArrayList<Book>();

        template.add(new ExcelTeacher(4422,"Teacher","Rex","Madison","male","rex@qq.com","Hangzhou","China"));
//        template.add(new Book("Effective Java", "Joshua Bloch", "0321356683",
//                "May 28, 2008", 38.11F));

        return new ModelAndView("excelTemplate","template",template);
    }
    /**
     * DOWNLOAD TEACHER EXCEL FILE
     */



    /**
     * DOWNLOAD STUDENT EXCEL FILE TEMPLATE
     */
    @RequestMapping(value = "download/student-excel",method = RequestMethod.GET)
    public ModelAndView downloadStudentExcel(){

        List<ExcelStudent> stTemplate = new ArrayList<ExcelStudent>();
        stTemplate.add(new ExcelStudent(2001,"testName","testLastName","male", "double-degree", "email@qq.com", "Hangzhou", "China"));

        return new ModelAndView("stdExcelTemplate","stTemplate",stTemplate);
    }

//    /**
//     * DOWNLOAD STUDENT GRADES LIST
//     */
//    @RequestMapping(value = "admin/download/student-grades-excel/{semesterId}/{majorId}/{intakeId}",method = RequestMethod.GET )
//    public ModelAndView downloadStudentGradesExcel(@PathVariable int semesterId, @PathVariable int majorId,
//                                                   @PathVariable int intakeId){
//        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
//        CourseEnrolmentDao daoAccessCourseEnroll = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");
//
//        List<ExcelStudent> student = new ArrayList<ExcelStudent>();
//
//        List<User> list = daoAccessMajor.getAllStudentListByMajorAndIntake(majorId, intakeId);
//
//        List<CourseEnrolment> list2 = daoAccessCourseEnroll.getAllCourseGradesListByMIS(majorId, intakeId, semesterId);
//
//        for (User entity: list ){
//            for(CourseEnrolment entity2: list2 ){
//                if (entity.getUserId() == entity2.getStudentId() ) {
//                    student.add( new ExcelStudent( entity.getUserId(), entity.getUserName(), entity2.getCourseCode(), entity2.getFinalAll() ));
//                }
//            }
//        }
//
//        return new ModelAndView("stdFinalGradeExcelTemplate", "student", student);
//    }

    /**
     * DOWNLOAD STUDENT GRADES LIST
     */
    @RequestMapping(value = "admin/download/student-grades-excel-1/{semesterId}/{majorId}/{intakeId}",method = RequestMethod.GET )
    public ModelAndView downloadStudentHighestGradesExcel(@PathVariable int semesterId, @PathVariable int majorId,
                                                   @PathVariable int intakeId){
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");
        CourseEnrolmentDao daoAccessCourseEnroll = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");
        SemesterDao daoSemester = (SemesterDao) applicationContext.getBean("SemesterDao");

        List<ExcelStudent> students = new ArrayList<ExcelStudent>();

        Semester sem = daoSemester.getSingleSemesterBySemesterId( semesterId );
        List<User> intakeStudents = daoAccessMajor.getAllStudentListByMajorAndIntake(majorId, intakeId);
        List<Courses> intakeCourses = daoAccess.getAllCourseListBySemesterAndMajorId(semesterId,majorId);
        List<CourseEnrolment> coursesResults = daoAccessCourseEnroll.getAllCourseGradesListByMIS(majorId, intakeId, semesterId);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("stdFinalGradeExcelTemplateH");
        mav.addObject("students",intakeStudents);
        mav.addObject("courses",intakeCourses);
        mav.addObject("results",coursesResults);
        mav.addObject("semester",sem.getSemesterCode());

        return mav;

//        return new ModelAndView("stdFinalGradeExcelTemplateH", "students", students);
    }

        /**
     * DOWNLOAD STUDENT GRADES LIST
     */
    @RequestMapping(value = "admin/download/student-grades-excel/{semesterId}/{majorId}/{intakeId}",method = RequestMethod.GET )
    public ModelAndView downloadStudentGradesExcel(@PathVariable int semesterId, @PathVariable int majorId,
                                                   @PathVariable int intakeId){
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");
        CoursesDao daoAccess = (CoursesDao) applicationContext.getBean("CoursesDao");
        CourseEnrolmentDao daoAccessCourseEnroll = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");
        SemesterDao daoSemester = (SemesterDao) applicationContext.getBean("SemesterDao");

        List<ExcelStudent> students = new ArrayList<ExcelStudent>();

        Semester sem = daoSemester.getSingleSemesterBySemesterId( semesterId );
        List<User> intakeStudents = daoAccessMajor.getAllStudentListByMajorAndIntake(majorId, intakeId);
        List<Courses> intakeCourses = daoAccess.getAllCourseListBySemesterAndMajorId(semesterId,majorId);
        List<CourseEnrolment> coursesResults = daoAccessCourseEnroll.getAllCourseGradesListByMIS(majorId, intakeId, semesterId);

        /*List<ExcelStudent> courseName = new ArrayList<ExcelStudent>();
        for (Courses entity1: list1){
            courseName.add( new ExcelStudent(entity1.getCourseName(),entity1.getSemesterCode()));
        }

        List<ExcelStudent> student = new ArrayList<ExcelStudent>();
        for (User entity: list ){
            for(CourseEnrolment entity2: list2 ){
                if (entity.getUserId() == entity2.getStudentId() ) {
                    student.add( new ExcelStudent( entity.getUserId(), entity.getUserName(), entity2.getCourseCode(), entity2.getFinalAll(), entity2.getHighest() ));
                }
            }
        }

        students.add(new ExcelStudent(courseName,student));*/

        ModelAndView mav = new ModelAndView();
        mav.setViewName("stdFinalGradeExcelTemplate");
        mav.addObject("students",intakeStudents);
        mav.addObject("courses",intakeCourses);
        mav.addObject("results",coursesResults);
        mav.addObject("semester",sem.getSemesterCode());

        return mav;
//        return new ModelAndView("stdFinalGradeExcelTemplate", "students", students);
    }

    /**
     * DOWNLOAD STUDENT GRADES LIST
     */
    @RequestMapping(value = "admin/download/student-retake-grades-excel",method = RequestMethod.GET )
    public ModelAndView downloadRetakeStudentGradesExcel(@RequestParam(value="semester", required=false) Integer semesterId){

        CourseEnrolmentDao daoAccessCourseEnroll = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");

        List<ExcelStudent> student = new ArrayList<ExcelStudent>();

        List<CourseEnrolment> list = new ArrayList<>();
        if (semesterId != null) {
            list = daoAccessCourseEnroll.getAllRetakeCourseGradesListBySemester(semesterId);
        }
        else {
            list = daoAccessCourseEnroll.getAllRetakeCourseGradesList();
        }

        for (CourseEnrolment entity: list ){
            student.add( new ExcelStudent( entity.getStudentId(), entity.getStudentName(), entity.getSemester(), entity.getCourseCode(), entity.getCourseName(), entity.getCourseNameCN(), entity.getFinalAll() ));
        }

        return new ModelAndView("stdRetakeGradeExcelTemplate", "student", student);
    }

    /**
     * DOWNLOAD STUDENT EXCEL FILE TEMPLATE
     */

    /**
     * DOWNLOAD COURSE EXCEL
     */
    @RequestMapping(value = "download/parent-course-excel",method = RequestMethod.GET)
    public ModelAndView downloadParentCourseExcel(){

        List<ExcelCourse> crsTemplate = new ArrayList<ExcelCourse>();
        crsTemplate.add(new ExcelCourse(1000000001, "必修课","CST1002-2017-161", "工程经济与项目管理", 2, "工程经济与项目管理"));

        return new ModelAndView("pcrsExcelTemplate","pcrsTemplate",crsTemplate);
    }

    /**
     * DOWNLOAD CHILD COURSE EXCEL
     */
    @RequestMapping(value = "download/child-course-excel",method = RequestMethod.POST)
    public ModelAndView downloadChildCourseExcel(@RequestParam("majorId") int majorId){

        List<ExcelCourse> crsTemplate = new ArrayList<ExcelCourse>();
        ParentCoursesDao daoAccess = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");


        List<ParentCourses> list = daoAccess.getExcelParentCourseListForMajor(majorId);
        for(ParentCourses entity: list){
            crsTemplate.add(new ExcelCourse(entity.getMajorName(),entity.getParentCourseId(),entity.getCourseType(),entity.getCredits(), entity.getCourseName(), entity.getCourseShortName(),"2017-10-10", 902, "Russul London") );
        }

        return new ModelAndView("crsExcelTemplate","crsTemplate",crsTemplate);
    }

    /**
     * DOWNLOAD CHILD COURSE EXCEL
     */
    @RequestMapping(value = "download/teacher-list-excel",method = RequestMethod.GET)
    public ModelAndView downloadTeacherListExcel(){

        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
        List<ExcelStudent> teacher = new ArrayList<ExcelStudent>();

        List<User> list = daoAccess.getTeachersList();
        for( User entity: list){
            teacher.add(new ExcelStudent(entity.getUserId(),entity.getUserName()));
        }
        return new ModelAndView("stdEnrolmentExcelTemplate","student",teacher);

    }

    /**
     * DOWNLOAD COURSE EXCEL
     */
    @RequestMapping(value = "download/course-excel",method = RequestMethod.GET)
    public ModelAndView downloadCourseExcel(){

        List<ExcelCourse> crsTemplate = new ArrayList<ExcelCourse>();
        crsTemplate.add(new ExcelCourse(2007,5,2,"Data Science","Learn Machine learning Easily","double-degree","2017-09-11","2017-09-20"));

        return new ModelAndView("crsExcelTemplate","crsTemplate",crsTemplate);
    }


    /**
     * DOWNLOAD STUDENTS WITH NO CLASS EXCEL LIST
     * http://localhost:8080/admin/class/enroll/3
     */
    @RequestMapping(value = "admin/download/student-with-no-class-excel",method = RequestMethod.GET)
    public ModelAndView downloadStudentsWithNoClassExcel(){
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");

        List<ExcelStudent> student = new ArrayList<ExcelStudent>();

        List<User> list = daoAccess.getStudentsNotEnrolledInAnyClassList();
        for(User entity: list){
            student.add(new ExcelStudent(entity.getUserId(),entity.getUserName()));
        }
        return new ModelAndView("stdEnrolmentExcelTemplate","student",student);



    }

    /**
     * DOWNLOAD STUDENT CLASS LIST
     */
    @RequestMapping(value = "admin/download/{classId}/class-list-excel",method = RequestMethod.GET)
    public ModelAndView downloadClassListExcel(@PathVariable int classId){
        ClassDao daoAccess = (ClassDao) applicationContext.getBean("ClassDao");
        List<ExcelStudent> student = new ArrayList<ExcelStudent>();

        //this is the part where you populate from database

        List<User> list = daoAccess.getClassListByClassId(classId);
        for(User entity: list){
            student.add(new ExcelStudent(entity.getUserId(),entity.getUserName()));
        }


        return new ModelAndView("stdEnrolmentExcelTemplate","student",student);


    }



    /**
     * DOWNLOAD STUDENT LIST NOT ENROLLED IN COURSE EXCEL LIST
     */
    @RequestMapping(value = "admin/download/{courseId}/students-not-enrolled-in-course-excel",method = RequestMethod.GET)
    public ModelAndView downloadStudentsNotEnrolledInCourseExcel(@PathVariable int courseId){
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        List<ExcelStudent> student = new ArrayList<ExcelStudent>();

        //this is the part where you populate from database
        List<User> list = daoAccessUser.getStudentsNotEnrolledInCourseList(courseId);
        for(User entity: list){
            student.add(new ExcelStudent(entity.getUserId(),entity.getUserName()));
        }
        return new ModelAndView("stdEnrolmentExcelTemplate","student",student);


    }

    /**
     * DOWNLOAD STUDENT COURSE ENROLLED  EXCEL LIST
     */
    @RequestMapping(value = "admin/download/{courseId}/course-enrolment-list-excel",method = RequestMethod.GET)
    public ModelAndView downloadCourseEnrolmentList(@PathVariable int courseId){
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        List<ExcelStudent> student = new ArrayList<ExcelStudent>();

        //this is the part where you populate from database
        List<User> list = daoAccessUser.getStudentsEnrolledInCourseList(courseId);
        for(User entity: list){
            student.add(new ExcelStudent(entity.getUserId(),entity.getUserName()));
        }
        return new ModelAndView("stdEnrolmentExcelTemplate","student",student);


    }

    /**
     * DOWNLOAD STUDENTS NOT ENROLLED IN EXAM EXCEL LIST
     */
    @RequestMapping(value = "admin/download/{examId}/students-not-enrolled-in-exam-excel",method = RequestMethod.GET)
    public ModelAndView downloadStudentsNotEnrolledInExamExcel(@PathVariable int examId){
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
        List<ExcelStudent> student = new ArrayList<ExcelStudent>();

        //this is the part where you populate from database
        List<User> list = daoAccess.getStudentsNotEnrolledInExamList(examId);
        for(User entity: list){
            student.add(new ExcelStudent(entity.getUserId(),entity.getUserName()));
        }
        return new ModelAndView("stdEnrolmentExcelTemplate","student",student);


    }


    /**
     * DOWNLOAD STUDENT EXAM ENROLLED LIST
     */
    @RequestMapping(value = "admin/download/{examId}/exam-enrolment-list-excel",method = RequestMethod.GET)
    public ModelAndView downloadExamEnrolmentListExcel(@PathVariable int examId){
        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");
        List<ExcelStudent> student = new ArrayList<ExcelStudent>();

        //this is the part where you populate from database
        List<User> list = daoAccess.getStudentsEnrolledInExamList(examId);
        for(User entity: list){
            student.add(new ExcelStudent(entity.getUserId(),entity.getUserName()));
        }
        return new ModelAndView("stdEnrolmentExcelTemplate","student",student);


    }


    /**
     * DOWNLOAD COURSE ENROLLMENT EXCEL LIST FOR ADDING GRADES
     */
    @RequestMapping(value = "admin/download/{courseId}/{gradeItemId}/{gradeItemTypeId}/course-student-add-grade-list-excel",method = RequestMethod.GET)
    public ModelAndView downloadCourseGradeStudentListExcel(@PathVariable int courseId,@PathVariable int gradeItemId,@PathVariable int gradeItemTypeId){
        GradeDao daoAccess = (GradeDao) applicationContext.getBean("GradeDao");
        List<ExcelStudent> student = new ArrayList<ExcelStudent>();

        //this is the part where you populate from database

        //exam
        if( gradeItemTypeId == 1){
            List<Grade> list = daoAccess.getExamStudentEnrollmentAddGradesList(gradeItemId);
            for(Grade entity: list){
                student.add(new ExcelStudent(entity.getStudentId(),entity.getStudentName(),entity.getGrade()));
            }
        }
        //other grade items
        else{
            List<Grade> list = daoAccess.getCourseStudentEnrollmentAddGradesList(courseId,gradeItemId);
            for(Grade entity: list){
                student.add(new ExcelStudent(entity.getStudentId(),entity.getStudentName(),entity.getGrade()));
            }
        }

        return new ModelAndView("stdGradeExcelTemplate","student", student);


    }







}
