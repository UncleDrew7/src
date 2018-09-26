package build.dao;
import build.model.ExamStudentRequestEnrolment;

/**
 * Created by apple on 29/08/2017.
 */
public interface ExamStudentRequestEnrolmentDao {

    public String makeExamEnrolmentRequest(ExamStudentRequestEnrolment examStudentRequestEnrolment);

    public int confirmExamEnrollment(ExamStudentRequestEnrolment examStudentRequestEnrolment);


    //TEACHER DASHBOARD STATS
    public int allExamRequestsCountForTeachersCourses();
}
