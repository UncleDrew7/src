package build.dao;
import build.model.*;

import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public interface ExamEnrolmentDao {

    //STUDENT MAKE EXAM REQUEST TO SYSTEM
    public String makeExamStudentRequestEnrollment(ExamStudentRequestEnrolment examStudentRequestEnrolment);

    //STUDENT CHECK IF EXAM ENROLLMENT HAS BEEN PREVIOUSL MADE BY STUDENT
    public boolean checkIfExamEnrollmentWasPreviouslyMade(int examId, long studentId);

    //ADMIN ACCEPT STUDENT ENROLLMENT REQUEST
    public String acceptStudentEnrolmentRequest(int examId,long studentId);

    //ADMIN DELETE STUDENT ENROLLMENT REQUEST
    public String deleteStudentEnrollmentRequest(int examId, long studentId);

    //ADMIN GET STUDENT EXAM REQUESTS
    public List<ExamStudentRequestEnrolment> getExamStudentEnrollmentRequests();

    //ADMIN CHECK IF ALREADY ENROLLED IN EXAM
    public int checkIfStudentIsAlreadyEnrolled(int examId,long studentId);
    public boolean checkIfStudentIsAlreadyEnrolld(int examId,long studentId);

    public boolean checkIfStudentIsEnrolledInExam(int examId, long studentId);

    public boolean checkIfStudentHasAlreadyMadePendingEnrolmentRequest(int examId,long studentId);

    //ADMIN ENROLL STUDENT IN EXAM
    public String enrollStudentInExam(ExamEnrolment examEnrolment);

    //ADMIN UN-ENROLL FROM EXAM
    public String unEnrollStudentFromExam(int examId,long studentId);

    public String unEnrollStudentFromClexam(int clexamId,long studentId);

    //ADMIN RE-ENROLL PREVIOUSLY UNENROLLED STUDENT
    public String reEnrollPreviouslyUnenrolledStudent(int examId,long studentId);

    //ADMIN ENROLMENT LIST GET REQUEST COUNT FOR EXXAM
    public int getExamRequestCountByExamId(int examId);

    //FOR STUDENT SHOW PENDING EXAM ENROLMENTS
    public List<GradeItems> getExamStudentEnrolmentPendingListByUserId(long userId);

    //COUNT PENDING ENROLMENTS
    public int getCountOfPendingExamsByUserId(long userId);

    //FOR STUDENT GET EXAM ENROLMENT CONFIRMATION LIST
    public List<GradeItems> getExamStudentEnrolmentConfirmationListByUserId(long userId);

    //COUNT ENROLLED
    public int getCountOfEnrolledExamsByUserId(long userId);
    public int getCountOfEnrolledExams(int pCourseId, long userId);
    public int getEnrollsOfExam( int examId, long studentId);

    //GET NUMBER OF COURSE EXAM ENROLLMENTS BY STUDENT
    public int getCourseExamEnrollmentCount(int courseId ,long userId);

    //GET COUNT FOR NUMBER OF EXAM ENROLLMENT ATTEMPTS BY STUDENT FOR EXAMS UNDER THE SAME PARENT COURSE
    public int getExamEnrolmentAttemptsCountUnderSameParent(long parentCourseId,long studentId);

    public int getParentCourseId(long examId );

    public List<ExamEnrolment> getExamStudentGrades(int examId);
    public List<User> getEnrolledStudentList(int examId);

    public List<Exam> getStudentEnrolledExamList(long studentId);
    public List<Exam> getStudentEnrolledExamListAndSearch(long studentId, String search);

    public List<Exam> getStudentEnrolledExamListbySemester(long studentId, int semesterId);
    public List<Exam> getStudentEnrolledExamListbySemesterAndSearch(long studentId, int semesterId, String search);

    public String updateGrade(ExamEnrolment grade);

    public String updateClexamGrade(ExamEnrolment grade);
    public String updateExamGrades260(ExamEnrolment grade);
    public String updateFinal1(int courseId, long studentId);
    public String updateFinal2(int courseId, long studentId);
    public String updateFinal3(int courseId, long studentId);

    public String updateExam1(int courseId, long studentId, String examResult);
    public String updateExam2(int courseId, long studentId, String examResult);
    public String updateExam3(int courseId, long studentId, String examResult);

    public String updateCourseFinish(int courseId, long studentId);
    public String updateCourseHighest(int courseId, long studentId, String examHighest);
    public String updateCourseNotFinish(int courseId, long studentId);

    public List<ExamEnrolment> getClearanceEnrolledStudentsAddGradesList(int clearanceExamId);
//    public List<ExamEnrolment> getClearanceExamGradesList(int clearanceExamId);



}
