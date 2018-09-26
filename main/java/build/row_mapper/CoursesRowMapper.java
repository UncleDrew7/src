package build.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import build.model.Courses;
/**
 * Created by apple on 15/08/2017.
 */

public class CoursesRowMapper implements RowMapper<Courses>{

    public Courses mapRow(ResultSet resultSet, int rowNumber)throws SQLException{

        Courses courses = new Courses();

//        courses.setId(resultSet.getInt("id"));
        courses.setCourseId(resultSet.getInt("course_id"));
        courses.setCategoryId(resultSet.getInt("category_id"));
        courses.setSemesterCode(resultSet.getString("semester_code"));
        courses.setCourseShortName(resultSet.getString("course_short_name"));
        courses.setCourseName(resultSet.getString("course_name"));
        courses.setCourseDescriptionEn(resultSet.getString("course_description_En"));
//        courses.setCourseDescriptionCn(resultSet.getString("course_description_Cn"));
        courses.setTeacherId(resultSet.getInt("user_id"));
        courses.setTeacherName(resultSet.getString("user_name"));
        courses.setCourseType(resultSet.getString("course_type"));
        courses.setStartDate(resultSet.getString("start_date"));
        courses.setEndDate(resultSet.getString("end_date"));

        return courses;
    }

}
