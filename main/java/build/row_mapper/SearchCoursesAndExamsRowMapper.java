package build.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import build.model.SearchCoursesAndExams;

import org.springframework.jdbc.core.RowMapper;

/**
 * Created by apple on 14/09/2017.
 */
public class SearchCoursesAndExamsRowMapper implements RowMapper<SearchCoursesAndExams>{
    @Override
    public SearchCoursesAndExams mapRow(ResultSet resultSet, int i) throws SQLException {
      try{
          SearchCoursesAndExams searchCoursesAndExams = new SearchCoursesAndExams();
          searchCoursesAndExams.setExamId( resultSet.getInt("grade_item_id"));

          searchCoursesAndExams.setCourseId(resultSet.getInt("course_id")) ;

          searchCoursesAndExams.setTeacherId(resultSet.getInt("teacher_id"));

          searchCoursesAndExams.setSemesterId(resultSet.getInt("category_id"));

          searchCoursesAndExams.setTeacherName(resultSet.getString( "user_name"));

          searchCoursesAndExams.setExamName(resultSet.getString( "grade_item_name")) ;

          searchCoursesAndExams.setExamCreatedBy( resultSet.getInt("created_by"));

          searchCoursesAndExams.setExamDate( resultSet.getString( "examDate")) ;

          searchCoursesAndExams.setGetExamEnrolmentStartDate(resultSet.getString( "enrolment_start_date") );

          searchCoursesAndExams.setExamEnrolmentEndDate(resultSet.getString( "deadline") ) ;

          searchCoursesAndExams.setExamStatus(resultSet.getString( "active_status") );

          searchCoursesAndExams.setCourseName(resultSet.getString( "course_name") ) ;

          searchCoursesAndExams.setCourseDescriptionEn( resultSet.getString( "course_description_En"));

          searchCoursesAndExams.setCourseStartDate( resultSet.getString( "start_date")) ;

          searchCoursesAndExams.setCourseEndDate(resultSet.getString( "end_date") );
          return searchCoursesAndExams;
      }catch (Exception e){
          e.printStackTrace();
          return null;
      }
    }
}
