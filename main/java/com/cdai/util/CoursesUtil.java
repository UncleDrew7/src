package com.cdai.util;

import build.dao.*;
import build.model.*;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static com.cdai.util.UserUtil.deleteStudentFromSystem;

public class CoursesUtil {
    private static final Logger logger = Logger.getLogger(CourseEnrollmentUtils.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
    public static CourseEnrolmentDao daoAccess = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");


    public CoursesUtil() {
        super();
    }

    public static List<Courses> returnStudentCustomHomePageCoursesApi(long userId,int limit ,int majorId){

        List<Courses> studentHomePageCourses = new ArrayList<>();

        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");


        for(Courses entity : daoAccessCourse.activeCourseList(userId,limit,majorId)){
            entity.setDisplayType("new");
            studentHomePageCourses.add(entity);
        }
      /*  int newLimit = 8 - studentHomePageCourses.size();
        for(Courses entity : daoAccessCourse.getStudentMostVisitedLatestCourses(userId, newLimit)){
            entity.setDisplayType("old");
            studentHomePageCourses.add(entity);
        }*/

        return studentHomePageCourses;
    }



    public static List<Courses> returnStudentCustomHomePageCoursesSearch(long userId,String search ,int majorId){

        List<Courses> studentHomePageCourses = new ArrayList<>();

        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");


        for(Courses entity : daoAccessCourse.activeCourseListSearch(userId,search,majorId)){
            entity.setDisplayType("new");
            studentHomePageCourses.add(entity);
        }
      /*  int newLimit = 8 - studentHomePageCourses.size();
        for(Courses entity : daoAccessCourse.getStudentMostVisitedLatestCourses(userId, newLimit)){
            entity.setDisplayType("old");
            studentHomePageCourses.add(entity);
        }*/

        return studentHomePageCourses;
    }


    public static void processParentCourseCreation(int majorId, List<ParentCourses> tempListProcessed){

        ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        ParentCoursesMajorDao daoAccessPCM = (ParentCoursesMajorDao) applicationContext.getBean("ParentCoursesMajorDao");

        for(ParentCourses entity:tempListProcessed){
            System.out.println("Parent Course Id: "+entity.getParentCourseId()+" --- Parent Course Name: "+entity.getCourseName());
            daoAccessParentCourse.createNewParentCourse(entity);
            daoAccessPCM.addParentCourseToMajor(entity.getParentCourseId(),majorId);

        }
    }

    public static void processChildCourseCreationForSpecificSemester(int semesterId, List<ChildCourses> tempListProcessed ){
        for (ChildCourses entity : tempListProcessed) {

            ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");
            ParentCourseChildCoursesDao daoAccessPCChild = (ParentCourseChildCoursesDao) applicationContext.getBean("ParentCourseChildCoursesDao");
            ChildCourseSemesterDao daoAccessCCSemester = (ChildCourseSemesterDao) applicationContext.getBean("ChildCourseSemesterDao");


            System.out.println("Child Course Id: " + entity.getChildCourseId() + "  --- Child Course Name:" + entity.getChildCourseName());

            daoAccessChildCourses.createNewChildCourse(entity);
            daoAccessPCChild.addChildCourseToParentCourse(entity.getParentCourseId(),entity.getChildCourseId());
            daoAccessCCSemester.addChildCourseToSemester(entity.getParentCourseId(),entity.getChildCourseId(),semesterId);


        }
    }

    public static String deleteSingleChildCourseFromSystem(int courseId){
        CoursesDao daoAccessCourse = (CoursesDao) applicationContext.getBean("CoursesDao");


        daoAccessCourse.deleteCourseFromCourseStudentRequestEnrolment(courseId);

        daoAccessCourse.deleteCourseFromCourseEnrolment(courseId);

        daoAccessCourse.deleteCourseFromEvents(courseId);

        daoAccessCourse.deleteCourseFromExamEnrolment( courseId);

        daoAccessCourse.deleteCourseFromExamStudentRequestEnrolment(courseId);

        daoAccessCourse.deleteCourseFromGrade(courseId);

        daoAccessCourse.deleteCourseFromClearanceExamEnrolment(courseId);

        daoAccessCourse.deleteCourseFromClearanceExam(courseId);

        daoAccessCourse.deleteCourseFromGradeItems(courseId);

        daoAccessCourse.deleteCourseUploads(courseId);

        daoAccessCourse.deleteCourseLessons(courseId);

        daoAccessCourse.deleteCourseFromChildCourseSemester(courseId);

        daoAccessCourse.deleteCourseFromChildCourseTeachers(courseId);

        daoAccessCourse.deleteCourseFromGradeCustom(courseId);

        daoAccessCourse.deleteCourseFromParentCourseChildCourses(courseId);

        daoAccessCourse.deleteCourseFromChildCourses(courseId);

        System.out.println("-------------------------------Delete For Child Course =>"+courseId+"<= Complete !!----------------------------------------");
        System.out.println("");

        return "200";
    }

    public static String deleteSingleParentCourse(long parentCourseId){
        ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");

        List<ParentCourses> list = daoAccessParentCourse.getListOfAllParentCourseChildCourses(parentCourseId);

        if(list.isEmpty()){
            //skeep this delete process
            // use defalut pc delete
            System.out.println("Parent Course has no child courses ");
        }else{
            //run expliot
            for(ParentCourses entity : list){
                System.out.println("@::Parent Course Child Courses Deletion beginning for @:::: "+ parentCourseId );
                System.out.println("@::Total child courses deleting :: "+list.size());
                System.out.println("Now Deleting Child Course With Id ==>"+entity.getChildCourseId());
                deleteSingleChildCourseFromSystem(entity.getChildCourseId());
            }
        }
        daoAccessParentCourse.deleteParentCourseFromParentCourseMajor(parentCourseId);
        daoAccessParentCourse.deleteParentCourseFromParentCourseTable(parentCourseId);
        System.out.println("@:200-------------------------------PARENT COURSE DELETE COMPLETE !!----------------------------------------");
        System.out.println("@:200-------------------------------------------------------------------------------------------------------------");
        System.out.println("");

        return"200";
    }

    public static String deleteSystemMajor(int majorId){
        MajorDao daoAccessMajor = (MajorDao) applicationContext.getBean("MajorDao");

        //get list of parent courses
        // get list of students

        List<Major> parentCourseList = daoAccessMajor.getAllParentCoursesInMajorList(majorId);
        List<Major> studentList = daoAccessMajor.getAllStudentsInMajorList(majorId);

        System.out.println("===================Deleting major With Id ::"+majorId);
        System.out.println("");

        if(!studentList.isEmpty()){
            for(Major entity: daoAccessMajor.getAllStudentsInMajorList(majorId)){
                System.out.println("Number Of Students Being Deleted @::"+studentList.size());
                deleteStudentFromSystem(entity.getStudentId());
            }
            System.out.println("@::==============1 Completed....");
            System.out.println("@::======================================MAJOR STUDENT DELETION COMPLETED=================================");
            System.out.println("@::==============1 Completed....");
        }else{
            System.out.println("@::==============1 Completed....");
            System.out.println("@::==========================This major has to students ");
            System.out.println("@::==============1 Completed....");
        }
        System.out.println("");

        if(!parentCourseList.isEmpty()){
            for(Major entity: daoAccessMajor.getAllParentCoursesInMajorList(majorId)){
                System.out.println("Number Of Parent Courses Being Deleted @::"+parentCourseList.size());
                deleteSingleParentCourse(entity.getParentCourseId());
            }
            System.out.println("@::==============1 Completed....");
            System.out.println("@::======================================MAJOR PARENT COURSE DELETION COMPLETE=================================");
            System.out.println("@::==============1 Completed....");
        }else{
            System.out.println("@::==============2 Completed....");
            System.out.println("@::======================================:This major has no Parent courses =================================");
            System.out.println("@::==============2 Completed....");

        }
        System.out.println("");

        daoAccessMajor.deleteMajorFromMajorTable(majorId);
        daoAccessMajor.deleteMajorFromStudentMajor(majorId);
        daoAccessMajor.deleteMajorFromClassMajor(majorId);
        daoAccessMajor.deleteMajorFromParentCourseMajor(majorId);
        System.out.println("");
        System.out.println("@:200-------------------------------------------------------------------------------------------------------------");
        System.out.println("@:200-------------------------------MAJOR DELETE COMPLETE !!----------------------------------------");
        System.out.println("@:200-------------------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("@::Parent courses deleted ::"+parentCourseList.size());
        System.out.println("@Student deleted ::"+ studentList.size());

        return "200";
    }






}



