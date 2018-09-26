package com.cdai.util;

import build.dao.CourseEnrolmentDao;
import build.model.CourseEnrolment;
import com.cdai.controllers.appController.CourseController;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by apple on 05/01/2018.
 * Involved tables total ::2
 * * course_enrolment
 * *course_student_request_enrolment
 */
//
// course_enrolment
// course_student_request_enrolment
public class CourseEnrollmentUtils {

    private static final Logger logger = Logger.getLogger(CourseEnrollmentUtils.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
    public static CourseEnrolmentDao daoAccess = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");

    public CourseEnrollmentUtils() {
        super();
    }

    public static String enrolmentRequestLedger( int childCourseId , long userId){
        System.out.println("-----------------------------------------------::process 1");
      String  msg =   daoAccess.studentMakeCourseEnrollmentRequest(childCourseId,userId);
      return msg;
    }


    public static boolean mainCourseEnrolmentLedgerCheck(int childCourseId , long studentId){
            System.out.println("-----------------------------------------------::process 2");
        if(daoAccess.checkIfStudentIsCurrentlyEnrolledInCourse(childCourseId,studentId)){
            System.out.println("@courseStudentEnroll::User is Already Enrolled deleting request");
            System.out.println("-----------------------------------------------::process 3");
            daoAccess. declineStudentCourseEnrollmentRequet(childCourseId,studentId);
            return false;
        }
        else{
            System.out.println("-----------------------------------------------::process 4");
            if(daoAccess.acceptStudentCourseEnrollmentRequest(childCourseId,studentId).equals("200")){
                return true ;
            }else{
                System.out.println("---::failed to accept student enrollment request");
                return false;
            }
        }

    }

    public static String  mainCourseEnrolmentLedger(int childCourse, long studentId){

        String msg = daoAccess.enrollStudentInCourse(new CourseEnrolment(childCourse,studentId));
        return msg;

    }


}
