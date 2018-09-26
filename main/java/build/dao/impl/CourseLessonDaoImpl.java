package build.dao.impl;

import build.model.CourseLesson;
import build.dao.CourseLessonDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public class CourseLessonDaoImpl extends JdbcDaoSupport implements CourseLessonDao{
    @Override
    public String addCourseLesson(CourseLesson courseLesson) {
        String sql = "INSERT INTO course_lesson (course_id ,lesson_name,description,created_by) VALUES( ?,?,?,? )";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ courseLesson.getCourseId(),courseLesson.getLessonName(),courseLesson.getDescription(),courseLesson.getCreatedBy() }
            );
            if(1 == returnValue)
                return "@CourseLessonDaoImpl::@addCourseLesson::LESSON CRESTED SUCCESSFUL";
            else
                return "@CourseLessonDaoImpl::@addCourseLesson::LESSON CREATION FAILED !!";

        }catch (Exception e){
            e.printStackTrace();
            return "@CourseLessonDaoImpl::@addCourseLesson::LESSON CREATION FAILED !!";
        }
    }

    @Override
    public List<CourseLesson> lessonContent(int courseId) {
        String sql = " SELECT " +
                "crl.lesson_id," +
                "crl.lesson_name," +
                "crl.description," +
                "crl.position" +
                " FROM course_lesson crl" +
                " WHERE crl.course_id = ?" +
                " ORDER BY crl.position;";
        List<CourseLesson> lessonContentList = getJdbcTemplate().query(
                sql,
                new Object[]{courseId},
                new RowMapper<CourseLesson>() {
                    @Override
                    public CourseLesson mapRow(ResultSet resultSet, int i) throws SQLException {
                        CourseLesson courseLesson = new CourseLesson();
                        courseLesson.setLessonId(resultSet.getInt("lesson_id"));
                        courseLesson.setLessonName(resultSet.getString("lesson_name"));
                        courseLesson.setDescription(resultSet.getString("description"));
                        courseLesson.setPosition(resultSet.getInt("position"));
                        return  courseLesson;
                    }
                }
        );
        return lessonContentList;
    }

    @Override
    public CourseLesson getSingleCourseLessonByLessonId(int lessonId) {
        String sql = "SELECT " +
                "cl.lesson_id, " +
                "cl.course_id, " +
                "cl.lesson_name, " +
                "cl.description  " +
                "FROM  " +
                "course_lesson cl " +
                "WHERE cl.lesson_id =  ?";

        try{
            CourseLesson courseLesson = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{lessonId},
                    new RowMapper<CourseLesson>() {
                        @Override
                        public CourseLesson mapRow(ResultSet resultSet, int i) throws SQLException {
                            CourseLesson courseLesson1 = new CourseLesson();
                            courseLesson1.setLessonId(resultSet.getInt("lesson_id"));
                            courseLesson1.setCourseId(resultSet.getInt("course_id"));
                            courseLesson1.setLessonName(resultSet.getString("lesson_name"));
                            courseLesson1.setDescription(resultSet.getString("description"));
                            return courseLesson1;
                        }
                    }
            );
            return courseLesson;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public String updateCourseLessonData(CourseLesson courseLesson) {
        String sql = "UPDATE course_lesson SET " +
                "lesson_name =  ?, " +
                "description = ?" +
                " WHERE lesson_id = ? ";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ courseLesson.getLessonName(),courseLesson.getDescription(),courseLesson.getLessonId() }
            );
            if(1 == returnValue)
                return "COURSE LESSON UPDATE SUCCESSFUL ";
            else
                return "COURSE LESSON UPDATE FAILURE  ";


        }catch (Exception e){
            System.out.println("UPDATE FAILED");
            e.printStackTrace();
            return "UPDATE FAILED ";
        }
    }

    @Override
    public List<CourseLesson> getCurrentlyAddedLessonsList(int courseId) {
        String sql = "SELECT  " +
                "cl.lesson_id, " +
                "cl.course_id, " +
                "cl.lesson_name  " +
                "FROM course_lesson cl " +
                "WHERE cl.course_id =  ? " +
                "ORDER BY cl.created_at DESC ";

        try{
            List<CourseLesson> courseLesson = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<CourseLesson>() {
                        @Override
                        public CourseLesson mapRow(ResultSet resultSet, int i) throws SQLException {
                            CourseLesson courseLesson1 = new CourseLesson();
                            courseLesson1.setLessonId(resultSet.getInt("lesson_id"));
                            courseLesson1.setCourseId(resultSet.getInt("course_id"));
                            courseLesson1.setLessonName(resultSet.getString("lesson_name"));
                            return courseLesson1;
                        }
                    }
            );
            return courseLesson;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public String deleteLessonContent(int lessonId) {
        String sql = "DELETE FROM course_lesson " +
                "WHERE lesson_id = ?";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ lessonId }
            );
            if(1 == returnValue)
                return "@CourseLessonDaoImpl::@deleteLessonContent::LESSON DELETED SUCCESSFUL";
            else
                return "@CourseLessonDaoImpl::@deleteLessonContent::LESSON DELETED FAILED !!";

        }catch (Exception e){
            e.printStackTrace();
            return "@CourseLessonDaoImpl::@deleteLessonContent::LESSON DELETED FAILED !!";
        }
    }
}
