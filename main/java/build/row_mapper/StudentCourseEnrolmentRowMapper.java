package build.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import build.model.StudentCourseEnrolment;
/**
 * Created by apple on 15/08/2017.
 */

public class StudentCourseEnrolmentRowMapper implements RowMapper<StudentCourseEnrolment>{

    public StudentCourseEnrolment mapRow(ResultSet resultSet, int rowNumber)throws SQLException{

        StudentCourseEnrolment studentCourseEnrolment = new StudentCourseEnrolment();

        studentCourseEnrolment.setId(resultSet.getInt("id"));
        studentCourseEnrolment.setStudentId(resultSet.getInt("student_id"));
        studentCourseEnrolment.setCourseId(resultSet.getInt("course_id"));
        studentCourseEnrolment.setEnrolmentStatus(resultSet.getString("enrolment_status"));
        studentCourseEnrolment.setSubmissionDate(resultSet.getString("submission_date"));
        studentCourseEnrolment.setEnrolmentDate(resultSet.getString("enrolment_date"));
        studentCourseEnrolment.setUnEnrolmentDate(resultSet.getString("unenrolment_date"));

        return studentCourseEnrolment;
    }

}
