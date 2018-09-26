package build.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import build.model.CourseEnrolment;

/**
 * Created by apple on 15/08/2017.
 */

public class CourseEnrolmentRowMapper implements RowMapper<CourseEnrolment>{

    public CourseEnrolment mapRow(ResultSet resultSet, int rowNumber)throws SQLException{

        CourseEnrolment courseEnrolment = new CourseEnrolment();

        courseEnrolment.setId(resultSet.getInt("id"));
        courseEnrolment.setCourseId(resultSet.getInt("courseid"));
        courseEnrolment.setStudentId(resultSet.getInt("studentid"));
        courseEnrolment.setIsEnrolled(resultSet.getInt("is_enrolled"));
        courseEnrolment.setEnrolmentDate(resultSet.getString("enrolment_date"));
        courseEnrolment.setHasUnenrolled(resultSet.getInt("has_unenrolled"));
        courseEnrolment.setUnEnrolmentDate(resultSet.getString("unenrolment_date"));

        return courseEnrolment;
    }

}
