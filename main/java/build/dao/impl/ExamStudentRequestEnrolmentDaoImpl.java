package build.dao.impl;

import build.model.ExamStudentRequestEnrolment;
import build.dao.ExamStudentRequestEnrolmentDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 29/08/2017.
 */
public class ExamStudentRequestEnrolmentDaoImpl extends JdbcDaoSupport implements ExamStudentRequestEnrolmentDao{


    @Override
    public String makeExamEnrolmentRequest(ExamStudentRequestEnrolment examStudentRequestEnrolment) {
        String sql = "INSERT INTO exam_student_request_enrolment (student_id ,course_id,exam_id ) VALUES( ?,?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ examStudentRequestEnrolment.getStudentId(),examStudentRequestEnrolment.getCourseId(),examStudentRequestEnrolment.getExamId() }
        );
        if(1 == returnValue)
            return "SUCCESSFULLY ENROLLED #"+examStudentRequestEnrolment.getStudentId()+" IN EXAM #"+ examStudentRequestEnrolment.getExamId();
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public int confirmExamEnrollment(ExamStudentRequestEnrolment examStudentRequestEnrolment) {
        String sql = "UPDATE exam_student_request_enrolment SET enrolment_status = 1 WHERE exam_id =? AND student_id = ? ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{examStudentRequestEnrolment.getExamId(),examStudentRequestEnrolment.getStudentId()}
        );
        if(1 == returnValue)
                return 1;
            else
                return 0;
    }



    //write and query by teacher id
    @Override
    public int allExamRequestsCountForTeachersCourses() {
        return 0;
    }
}
