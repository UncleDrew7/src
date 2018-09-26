package build.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import build.model.StudentCourses;

/**
 * Created by apple on 15/08/2017.
 */
public class StudentCoursesRowMapper implements RowMapper<StudentCourses> {

    public StudentCourses mapRow(ResultSet resultSet, int rowNumber)throws SQLException{

        StudentCourses studentCourses = new StudentCourses();

        studentCourses.setId(resultSet.getInt("id"));
        studentCourses.setStudentId(resultSet.getInt("studentid"));
        studentCourses.setCourseId(resultSet.getInt("courseid"));
        studentCourses.setCategoryId(resultSet.getInt("category_id"));
        studentCourses.setIsCurrentCourse(resultSet.getInt("is_current_course"));
        studentCourses.setIsCompletedCourse(resultSet.getInt("is_completed_course"));
        studentCourses.setIsUnEnrolledCourse(resultSet.getInt("is_unenrolled_course"));

        return studentCourses;
    }

}
