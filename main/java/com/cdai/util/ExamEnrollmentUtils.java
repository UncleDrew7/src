package com.cdai.util;

import build.dao.*;
import build.model.Exam;
import build.model.ExamEnrolment;
import build.model.ExamStudentRequestEnrolment;
import build.model.GradeItems;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by apple on 06/01/2018.
 */
public class ExamEnrollmentUtils {

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
    private static UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
    private static ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");
    private static ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
    private static GradeItemsDao daoAccessGradeItem = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
    private static ExamEnrolmentDao daoAccessExamEnrollment = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");
    private static GradeDao daoAccessGrade = (GradeDao) applicationContext.getBean("GradeDao");
    private static ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
    private static CourseEnrolmentDao daoAccessCourseEn = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");


    public ExamEnrollmentUtils() {
        super();
    }



    //1st time returnes fauls = new exam
    //if true offer clearance exam for student
    // if fauls enroll into new main exam
    public static boolean clearanceExamEligibilityCheck(long studentId,long parentCourseId,int childCourseId){

        //if attemp analyser is true
        //check is if attempts are greater or equal to 2 before enrolling in a 3rd exam
        if( daoAccessExamEnrollment.getExamEnrolmentAttemptsCountUnderSameParent(parentCourseId,studentId) >= 2){

            return false;
            //check grade of toatl course score using the current most highest exam
                //write sql api to make this check
            //--check if prevouse most latest exam made student pass course
            //call checkIfLastMostResentTakenExamHelpedPassCourse()

                //get course total score that was nt engough to pass computed using the LastMostResentTakenExam score
                // make check to see if it falls within the clearance range
            //make range check using most recent take exam
//                if(checkIfLastMostResentTakenExamIsWithinClearanceExamEligibilityRange(studentId,parentCourseId,childCourseId)){
//                    //search for available clearace exams and display to student to enrollinto
//                    return true;
//                }else{
//                    //skeep clearcance exam and procesd to new exam enrollment
//                    //return faulse
//                    return false;
//                }

        }else{
            // not yet eligible for clearence exam ||
            //create new main exam
            return true;
        }
    }

    //if true show the clearance exam to student
    public static boolean checkIfLastMostResentTakenExamIsWithinClearanceExamEligibilityRange(long studentId, long parentCourseId, int childCourseId){
        int withLatestExamComputedScore = daoAccessGrade.getCourseScoreComputedByLatestWrittenExamGrade(studentId, parentCourseId, childCourseId);
        if( withLatestExamComputedScore >= 48 && withLatestExamComputedScore < 60){
            System.out.println("@::Show the clearance exam");
            return true;
        }else{
            System.out.println("-----wont show student not eligible");
            return false;
        }
    }

    public static boolean checkIfLastMostResentTakenExamHelpedPassCourse(long studentId,long childCourseId ){
        // first check if attempts is not zero if zero skep check and return fauls of not passed to move on to next check
        if( daoAccessCourseEn.isCoursePassed(childCourseId, studentId ) != 0 ){
            //attempts = 0
            //if not do check
            //run check
            //get score of last most recent exam
            //computes to get course toatl and couclude if passed return true if failed return fauls
            // computed scoure < 60 (failed) return faluse
            //contitues enrollment
//            if(daoAccessGrade.getCourseScoreComputedByHighestExamGradeforStudent(studentId, parentCourseId) < 60){
//                // computed scoure < 60 (failed) return faluse
//                //contitues enrollment
//                System.out.println("@::LastMostResentTakenExam FAILED to help pass course");
//                return false;//no it didnt
//            }else {
                System.out.println("-----::LastMostResentTakenExamHelpedPassCourse");
                return true; //yes it did
//            }
        }else {
            // first check if attempts is not zero if zero skep check and return fauls of not passed to move on to next check
            //attempt is eqll to zero so skeeped check and returen faulse
            System.out.println("@::LastMostResentTakenExam FAILED to help pass course");
            return false;
        }

        // eg failed
        //if true end enrolleemt already passed
    }
    //first time will return falus == new exam
    //if true stop enrollment and exit already passed
    public static boolean checkIfLastMostResentTakenExamHelpedPassCourse(long studentId,long parentCourseId,int childCourseId ){
        // first check if attempts is not zero if zero skep check and return fauls of not passed to move on to next check
        if(daoAccessExamEnrollment.getExamEnrolmentAttemptsCountUnderSameParent(parentCourseId,studentId) != 0){
            //attempts = 0
            //if not do check
            //run check
            //get score of last most recent exam
            //computes to get course toatl and couclude if passed return true if failed return fauls
            // computed scoure < 60 (failed) return faluse
            //contitues enrollment
            if(daoAccessGrade.getCourseScoreComputedByHighestExamGradeforStudent(studentId, parentCourseId, childCourseId) < 60){
                // computed scoure < 60 (failed) return faluse
                //contitues enrollment
                System.out.println("@::LastMostResentTakenExam FAILED to help pass course");
                return false;//no it didnt
            }else {
                System.out.println("-----::LastMostResentTakenExamHelpedPassCourse");
                return true; //yes it did
            }
        }else {
            // first check if attempts is not zero if zero skep check and return fauls of not passed to move on to next check
            //attempt is eqll to zero so skeeped check and returen faulse
            System.out.println("@::LastMostResentTakenExam FAILED to help pass course");
            return false;
        }

        // eg failed
        //if true end enrolleemt already passed
    }

    //send parent course id to test attempts  if fauls stop enrolment and exit
    public static boolean attemptsAvailable(long studentId,long parentCourseId){

        if(daoAccessUser.getStudentDegreeType(studentId).equals("double-degree")){
            //student can only join 3 exams for the same course id
            if(daoAccessExamEnrollment.getExamEnrolmentAttemptsCountUnderSameParent(parentCourseId,studentId) < 3){
                System.out.println("@::STUDENT ENROLLMENT ATTEMPS AVAILABLE");
                return true;
            }else {
                System.out.println("----::DOUBLE DEGREE STUDENT HAS MAXED ENROLMENT LIMIT ");
                return false;
            }
        }else {
            System.out.println("@::STUDENT ENROLLMENT ATTEMPTS AVAILABLE NO LIMIT STUDENTS");
            return true;
        }
    }

    public static boolean examEnrolmentDateCheck(int examId){
        Exam exam = daoAccessExam.getSingleExamFullDetailsByExamId(examId);

        String currentDate = new java.sql.Date(System.currentTimeMillis()).toString();

        boolean status = false;

        try{
            //Date systemDate = new SimpleDateFormat("yyyy-mm-dd").parse(currentDate);
            //Date deadlineDate = new SimpleDateFormat("yyyy-mm-dd").parse(exam.getEnrolmentCloseDate());

            if(exam.getEnrolmentCloseDate().compareTo(currentDate) >= 0 ){
                System.out.println("@::EXAM ENROLLMENT STILL OPEN");
                status = true;
            }else{
                System.out.println("----::EXAM DEADLINE ALREADY PASSED ENROLLMENT FAILED STOP ....!");
                status = false;
            }

        }catch (Exception e){
            System.out.println("----::EXAM DEADLINE CHECK SYSTEM FAILED ENROLLEMENT STOPPED ....!");
            status = false;
        }

        return status;

    }

    public static void enrolmentRequestLedger( int examId, long userId){
        // recode request in enrollment request ledger
        System.out.println(daoAccessExamEnrollment.makeExamStudentRequestEnrollment(new ExamStudentRequestEnrolment(examId,userId)));
    }


}
