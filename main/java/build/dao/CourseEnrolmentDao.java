package build.dao;

import build.model.CourseEnrolment;
import build.model.Courses;

import java.util.List;

/**
 * Created by apple on 15/08/2017.
 */
public interface CourseEnrolmentDao {

    public String createCourseEnrolment(CourseEnrolment courseEnrolment);

    //TAKES COURSE ID AND STUDENT ID
    public String enrollStudentInCourse(CourseEnrolment courseEnrolment);

    public String removeStudentFromCourse(CourseEnrolment courseEnrolment);

    public String deleteCourseEnrolment(CourseEnrolment courseEnrolment);

    public Boolean checkIfStudentWasPreviouslyEnrolled(CourseEnrolment courseEnrolment);
    public Boolean checkIfStudentWasPreviouslyEnrolled(int courseId , long userId);
    public Boolean setEnrolledCourseInactive(int courseId , long userId );

    //ADMIN CHECK IF USER IS ENROLLED TRUE OR FALSE
    public Boolean checkIfStudentIsCurrentlyEnrolledInCourse(int courseId , long userId);

    public String enrollPreviouslyEnrolledStudent(CourseEnrolment courseEnrolment);

    public List<CourseEnrolment> displayCourseEnrolment();

    public boolean checkIfStudentHasAlreadyMadeAnEnrollmentRequest(int courseId , long userId);

    //STUDENT MAKE COURSE ENROLMENT REQUEST
    public String studentMakeCourseEnrollmentRequest(int courseId , long userId );

    //ADMIN ACCEPT STUDENT ENROLLMENT REQUEST
    public String acceptStudentCourseEnrollmentRequest(int courseId, long userId);

    //ADMIN DECLINE STUDENT ENROLMENT REQUEST
    public String declineStudentCourseEnrollmentRequet(int courseId, long userId);

    //FOR STUDENT SHOW PENDING COURSE ENROLMENTS
    public List<Courses> getCourseStudentEnrolmentPendingListByUserId(long userId);

    //COUNT PENDING ENROLMENTS
    public int getCountOfPendingCoursesByUserId(long userId);

    //FOR STUDENT GET COURSE ENROLMENT CONFIRMATION LIST
    public List<Courses> getCourseStudentEnrolmentConfirmationListByUserId(long userId);

    //COUNT ENROLLED
    public int getCountOfEnrolledCoursesByUserId(long userId);



    public List<CourseEnrolment> getAllCourseGradesListByMIS(int majorId, int intakeId, int semesterId);
    public List<CourseEnrolment> getAllCourseGradesListByCourseId(int courseId);

    public String updateCourseGrade(long userId, int courseId, String score);
    public String getCourseScore(int courseId, long userId);
    public String getCourseHighestGrade(int courseId, long userId);
    public int getCourseId(long parentCourseId, long userId);
    public int isCoursePassed(long childCourseId, long userId);

    public List<Courses> getAvailableRetakeCourses(long userId);
    public List<Courses> getStudentEnrolledCoursesList(long childCourseId, long userId);

    public List<CourseEnrolment> getAllRetakeCourseGradesList();
    public List<CourseEnrolment> getAllRetakeCourseGradesListBySemester(int semesterId);
    public List<CourseEnrolment> getAllRetakeCourseGradesListBySemesterAndMajor(int semesterId, int majorId);

}
