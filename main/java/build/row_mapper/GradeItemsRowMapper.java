package build.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import build.model.GradeItems;

import org.springframework.jdbc.core.RowMapper;
/**
 * Created by apple on 14/09/2017.
 */
public class GradeItemsRowMapper implements RowMapper<GradeItems>{


    @Override
    public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
        GradeItems gradeItem = new GradeItems();

        gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
        gradeItem.setCourseId(resultSet.getInt("course_id"));
        gradeItem.setCourseName(resultSet.getString( "course_name"));
        gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
        gradeItem.setCreatedBy(resultSet.getInt("created_by"));
        gradeItem.setCreatedByUserName(resultSet.getString( "user_name"));
        gradeItem.setDateOfExam(resultSet.getString( "examDate"));
        gradeItem.setEnrolmentStartDate(resultSet.getString( "enrolment_start_date"));
        gradeItem.setEnrolmentCloseDate(resultSet.getString( "deadline"));
        gradeItem.setActiveStatus(resultSet.getInt("active_status"));

        return gradeItem;
    }
}
