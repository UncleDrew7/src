package build.dao.impl;

import build.model.CourseStudentRequestEnrolment;
import build.dao.CourseStudentRequestEnrolmentDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 29/08/2017.
 */
public class CourseStudentRequestEnrolmentDaoImpl extends JdbcDaoSupport implements CourseStudentRequestEnrolmentDao{
    @Override
    public String addCourseStudentRequestEnrolment(CourseStudentRequestEnrolment courseStudentRequestEnrolment) {
        String sql = "INSERT INTO course_student_request_enrolment ( course_id,student_id,enrolment_status ) VALUES( ? ,?,?)";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ courseStudentRequestEnrolment.getCourseId(),courseStudentRequestEnrolment.getStudentId(),courseStudentRequestEnrolment.getEnrolmentStatus() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }


}
