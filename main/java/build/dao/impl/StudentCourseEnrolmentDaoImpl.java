package build.dao.impl;


import build.model.StudentCourseEnrolment;
import build.dao.StudentCourseEnrolmentDao;
import build.row_mapper.StudentCourseEnrolmentRowMapper;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
/**
 * Created by apple on 15/08/2017.
 */
public class StudentCourseEnrolmentDaoImpl extends JdbcDaoSupport implements StudentCourseEnrolmentDao{
    @Override
    public String createNewStudentCourseEnrolment(StudentCourseEnrolment studentCourseEnrolment) {

        String sql = "INSERT INTO student_course_enrolment (student_id,course_id,enrolment_status,enrolment_date,unenrolment_date)VALUES(?,?,?,?,?) ";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{
                       studentCourseEnrolment.getStudentId(),
                        studentCourseEnrolment.getCourseId(),
                        studentCourseEnrolment.getEnrolmentStatus(),
                        studentCourseEnrolment.getEnrolmentDate(),
                        studentCourseEnrolment.getUnEnrolmentDate(),

                }
        );
        if(1 == returnValue)
            return "# New StudentCourseEnrolment>>> "+studentCourseEnrolment.getStudentId()+"--"+studentCourseEnrolment.getCourseId()+" CREATED SUCCESSFULLY ";
        else
            return "# CREATION FAILURE";
    }

    @Override
    public String updateStudentCourseEnrolment(StudentCourseEnrolment studentCourseEnrolment) {
        return null;
    }

    @Override
    public String deleteStudentCourseEnrolment(StudentCourseEnrolment studentCourseEnrolment) {
        return null;
    }

    @Override
    public List<StudentCourseEnrolment> displayAllStudentCourseEnrolment() {
        return null;
    }
}

