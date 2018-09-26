package build.dao.impl;

import build.model.SearchCoursesAndExams;
import build.dao.SearchCoursesAndExamsDao;

import build.row_mapper.SearchCoursesAndExamsRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 14/09/2017.
 */
public class SearchCoursesAndExamsDaoImpl extends JdbcDaoSupport implements SearchCoursesAndExamsDao{

    @Override
    public List<SearchCoursesAndExams> searchExamsAndCourses(String searchParam) {
        String sql = "SELECT " +
                "gi.grade_item_id," +
                "gi.course_id," +
                "gi.grade_item_name," +
                "gi.created_by," +
                "crs.teacher_id," +
                "usr.user_name," +
                "crs.course_name," +
                "crs.course_description_En," +
                "crs.category_id," +
                "crs.start_date," +
                "crs.end_date," +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y')ExamDate," +
                "gi.enrolment_start_date," +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y')deadline," +
                "gi.active_status" +
                " FROM grade_items gi " +
                "LEFT JOIN courses crs ON gi.course_id = crs.course_id " +
                "RIGHT JOIN user usr ON usr.user_id = crs.teacher_id " +
                "WHERE gi.active_status = 1 " +
                "AND gi.grade_item_type = 'Exam' " +
                "AND crs.course_name  LIKE ? ";

        List<SearchCoursesAndExams> searchList = getJdbcTemplate().query(
                sql,
                new Object[]{'%'+searchParam+ '%'},
                new SearchCoursesAndExamsRowMapper()
        );
        return searchList;


    }

    @Override
    public List<SearchCoursesAndExams> searchFilteredByCourses(String searchParam) {
        String sql = "SELECT  " +
                "c.course_id, " +
                "c.course_name, " +
                "c.course_short_name, " +
                "c.teacher_id, " +
                "u.user_name, " +
                "c.course_description_En, " +
                "c.category_id, " +
                "c.status, " +
                "c.start_date, " +
                "c.end_date " +
                "FROM courses c " +
                "LEFT JOIN user u ON u.user_id = c.teacher_id " +
                "WHERE c.course_name LIKE  ?  " +
                "OR c.course_short_name LIKE  ?  " +
                "OR c.course_id LIKE  ? ";

        try{
            List<SearchCoursesAndExams> searchList = getJdbcTemplate().query(
                    sql,
                    new Object[]{'%' + searchParam + '%', '%' + searchParam + '%', '%' + searchParam + '%'},
                    new RowMapper<SearchCoursesAndExams>() {
                        @Override
                        public SearchCoursesAndExams mapRow(ResultSet resultSet, int i) throws SQLException {
                            SearchCoursesAndExams searchCoursesAndExams = new SearchCoursesAndExams();
                            searchCoursesAndExams.setCourseId(resultSet.getInt("course_id")) ;
                            searchCoursesAndExams.setTeacherId(resultSet.getLong("teacher_id"));
                            searchCoursesAndExams.setSemesterId(resultSet.getInt("category_id"));
                            searchCoursesAndExams.setTeacherName(resultSet.getString( "user_name"));
                            searchCoursesAndExams.setExamStatus(resultSet.getString( "status") );
                            searchCoursesAndExams.setCourseName(resultSet.getString( "course_name") ) ;
                            searchCoursesAndExams.setCourseDescriptionEn( resultSet.getString( "course_description_En"));
                            searchCoursesAndExams.setCourseStartDate( resultSet.getString( "start_date")) ;
                            searchCoursesAndExams.setCourseEndDate(resultSet.getString( "end_date") );
                            return searchCoursesAndExams;
                        }
                    }
            );
            return searchList;
        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SearchCoursesAndExams> searchFilteredByExams(String searchParam) {
       String sql = "SELECT  " +
               "c.category_id, " +
               "gi.grade_item_id, " +
               "gi.course_id, " +
               "gi.grade_item_name, " +
               "c.course_description_En," +
               "gi.date_of_grade_item, " +
               "gi.enrolment_start_date, " +
               "gi.enrolment_close_date, " +
               "gi.active_status, " +
               "c.course_name " +
               "FROM grade_items gi  " +
               "LEFT JOIN courses c ON c.course_id = gi.course_id " +
               "WHERE gi.grade_item_type = 'Exam' " +
               "AND gi.grade_item_name LIKE ? " +
               "OR gi.date_of_grade_item LIKE ? " +
               "OR gi.enrolment_start_date LIKE ? " +
               "OR gi.course_id LIKE  ? " +
               "OR c.course_name LIKE  ? " +
               "OR c.course_short_name LIKE ? " +
               "ORDER BY c.category_id DESC ,gi.enrolment_close_date,gi.date_of_grade_item,gi.grade_item_name";

        try{
            List<SearchCoursesAndExams> searchList = getJdbcTemplate().query(
                    sql,
                    new Object[]{'%' + searchParam + '%', '%' + searchParam + '%', '%' + searchParam + '%','%' + searchParam + '%','%' + searchParam + '%','%' + searchParam + '%'},
                    new RowMapper<SearchCoursesAndExams>() {
                        @Override
                        public SearchCoursesAndExams mapRow(ResultSet resultSet, int i) throws SQLException {
                            SearchCoursesAndExams searchCoursesAndExams = new SearchCoursesAndExams();

                            searchCoursesAndExams.setCourseDescriptionEn( resultSet.getString( "course_description_En"));
                            searchCoursesAndExams.setExamId( resultSet.getInt("grade_item_id"));
                            searchCoursesAndExams.setExamName(resultSet.getString( "grade_item_name")) ;
                            searchCoursesAndExams.setExamDate( resultSet.getString( "date_of_grade_item")) ;
                            searchCoursesAndExams.setGetExamEnrolmentStartDate(resultSet.getString( "enrolment_start_date") );
                            searchCoursesAndExams.setExamEnrolmentEndDate(resultSet.getString( "enrolment_close_date") ) ;
                            searchCoursesAndExams.setExamStatus(resultSet.getString( "active_status") );
                            searchCoursesAndExams.setCourseId(resultSet.getInt("course_id")) ;
                            searchCoursesAndExams.setSemesterId(resultSet.getInt("category_id"));
                            searchCoursesAndExams.setCourseName(resultSet.getString( "course_name") ) ;

                            return searchCoursesAndExams;
                        }
                    }
            );
            return searchList;
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SearchCoursesAndExams> searchFilteredBySemester(String searchParam) {
        return null;
    }

    @Override
    public List<SearchCoursesAndExams> searchChildCourses(int majorId ,String searchParam) {
        String sql = "SELECT   " +
                "m.major_id,  " +
                "m.major_name,  " +
                "m.major_short_code,  " +
                "pc.parent_course_id,  " +
                "pc.course_type,  " +
                "pc.credits,  " +
                "s.semester_id,  " +
                "s.semester_code,  " +
                "pc.course_short_name,  " +
                "pc.course_name, " +
                "pc.description, " +
                "cc.child_course_id,  " +
                "cc.child_course_name,  " +
                "cc.enrolment_deadline,  " +
                "u.user_id, " +
                "cc.teacher_id,  " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name,  " +
                "pc.description,  " +
                "pc.status  " +
                "FROM child_courses cc  " +
                "LEFT JOIN USER u ON u.user_id = cc.teacher_id " +
//                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id  " +
//                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
//                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
//                "LEFT JOIN child_course_semester ccc ON ccc.child_course_id = cc.child_course_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE  cc.child_course_name LIKE  ? " +
                "OR pc.course_name LIKE  ? " +
                "OR pc.course_short_name LIKE  ? " +
                "OR u.user_name LIKE  ?  " +
                "HAVING m.major_id = ? " +
                "ORDER BY s.semester_code DESC ,pc.course_name";

        try{
            List<SearchCoursesAndExams> searchList = getJdbcTemplate().query(
                    sql,
                    new Object[]{'%' + searchParam + '%', '%' + searchParam + '%', '%' + searchParam + '%','%' + searchParam + '%',majorId},
                    new RowMapper<SearchCoursesAndExams>() {
                        @Override
                        public SearchCoursesAndExams mapRow(ResultSet resultSet, int i) throws SQLException {
                            SearchCoursesAndExams searchCoursesAndExams = new SearchCoursesAndExams();

                            searchCoursesAndExams.setExamEnrolmentEndDate(resultSet.getString( "enrolment_deadline") ) ;
                            searchCoursesAndExams.setCourseName(resultSet.getString( "course_name") ) ;
                            searchCoursesAndExams.setMajorId(resultSet.getInt("major_id"));
                            searchCoursesAndExams.setMajorName(resultSet.getString("major_name"));
                            searchCoursesAndExams.setMajorShortCode(resultSet.getString("major_short_code"));
                            searchCoursesAndExams.setCourseType(resultSet.getString("course_type"));
                            searchCoursesAndExams.setSemesterId(resultSet.getInt("semester_id"));
                            searchCoursesAndExams.setSemesterCode(resultSet.getString("semester_code"));
                            searchCoursesAndExams.setChildCourseName(resultSet.getString("child_course_name"));
                            searchCoursesAndExams.setParentCourseId(resultSet.getInt("parent_course_id"));
                            searchCoursesAndExams.setChildCourseId(resultSet.getInt("child_course_id"));
                            searchCoursesAndExams.setParentCouresName(resultSet.getString("course_name"));
                            searchCoursesAndExams.setCourseDescriptionEn(resultSet.getString("description"));
                            searchCoursesAndExams.setTeacherId(resultSet.getLong("teacher_id"));
                            searchCoursesAndExams.setTeacherName(resultSet.getString("teacher_name"));


                            return searchCoursesAndExams;
                        }
                    }
            );
            return searchList;
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SearchCoursesAndExams> searchChildCoursesFilteredBySemesterId(int majorId, String searchParam, int semesterId) {
        String sql = "SELECT   " +
                "m.major_id,  " +
                "m.major_name,  " +
                "m.major_short_code,  " +
                "pc.parent_course_id,  " +
                "pc.course_type,  " +
                "pc.credits,  " +
                "s.semester_id,  " +
                "s.semester_code,  " +
                "pc.course_short_name,  " +
                "pc.course_name, " +
                "pc.description, " +
                "cc.child_course_id,  " +
                "cc.child_course_name,  " +
                "cc.enrolment_deadline,  " +
                "u.user_id, " +
                "cc.teacher_id,  " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name,  " +
                "pc.description,  " +
                "pc.status  " +
                "FROM child_courses cc  " +
                "LEFT JOIN USER u ON u.user_id = cc.teacher_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id  " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id  " +
                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pcm.major_id  " +
                "LEFT JOIN child_course_semester ccc ON ccc.child_course_id = cc.child_course_id  " +
                "LEFT JOIN semester s ON s.semester_id = ccc.semester_id  " +
                "WHERE  cc.child_course_name LIKE  ? " +
                "OR pc.course_name LIKE  ? " +
                "OR pc.course_short_name LIKE  ? " +
                "OR u.user_name LIKE  ?  " +
                "HAVING m.major_id = ? AND s.semester_id =  ? " +
                "ORDER BY s.semester_code DESC ,pc.course_name";

        try{
            List<SearchCoursesAndExams> searchList = getJdbcTemplate().query(
                    sql,
                    new Object[]{'%' + searchParam + '%', '%' + searchParam + '%', '%' + searchParam + '%','%' + searchParam + '%',majorId, semesterId},
                    new RowMapper<SearchCoursesAndExams>() {
                        @Override
                        public SearchCoursesAndExams mapRow(ResultSet resultSet, int i) throws SQLException {
                            SearchCoursesAndExams searchCoursesAndExams = new SearchCoursesAndExams();

                            searchCoursesAndExams.setExamEnrolmentEndDate(resultSet.getString( "enrolment_deadline") ) ;
                            searchCoursesAndExams.setCourseName(resultSet.getString( "course_name") ) ;
                            searchCoursesAndExams.setMajorId(resultSet.getInt("major_id"));
                            searchCoursesAndExams.setMajorName(resultSet.getString("major_name"));
                            searchCoursesAndExams.setMajorShortCode(resultSet.getString("major_short_code"));
                            searchCoursesAndExams.setCourseType(resultSet.getString("course_type"));
                            searchCoursesAndExams.setSemesterId(resultSet.getInt("semester_id"));
                            searchCoursesAndExams.setSemesterCode(resultSet.getString("semester_code"));
                            searchCoursesAndExams.setChildCourseName(resultSet.getString("child_course_name"));
                            searchCoursesAndExams.setParentCourseId(resultSet.getInt("parent_course_id"));
                            searchCoursesAndExams.setChildCourseId(resultSet.getInt("child_course_id"));
                            searchCoursesAndExams.setParentCouresName(resultSet.getString("course_name"));
                            searchCoursesAndExams.setCourseDescriptionEn(resultSet.getString("description"));
                            searchCoursesAndExams.setTeacherId(resultSet.getLong("teacher_id"));
                            searchCoursesAndExams.setTeacherName(resultSet.getString("teacher_name"));


                            return searchCoursesAndExams;
                        }
                    }
            );
            return searchList;
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SearchCoursesAndExams> searchChildCourseExams(int majorId, String searchParam, long studentId) {
        String sql = "SELECT    " +
                "m.major_id,   " +
                "m.major_name,   " +
                "m.major_short_code,   " +
                "pc.parent_course_id,   " +
                "pc.course_type,   " +
                "pc.credits,   " +
                "s.semester_id,   " +
                "s.semester_code,   " +
                "pc.course_short_name,  " +
                "pc.course_name,   " +
                "cc.child_course_id,   " +
                "cc.child_course_name,  " +
                "cc.enrolment_deadline,  " +
                "cc.teacher_id,  " +
                "gi.grade_item_id,  " +
                "gi.grade_item_name,  " +
                "gi.date_of_grade_item,  " +
                "gi.enrolment_close_date,  " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name,   " +
                "pc.description,   " +
                "pc.status   " +
                "FROM grade_items gi   " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id  " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id   " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id   " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id   " +
                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id   " +
                "LEFT JOIN major m ON m.major_id = pcm.major_id   " +
                "LEFT JOIN child_course_semester ccc ON ccc.child_course_id = cc.child_course_id   " +
                "LEFT JOIN semester s ON s.semester_id = ccc.semester_id   " +
                "WHERE ( gi.grade_item_type = 'Exam') " +
                "AND ( CURDATE()<= DATE(gi.enrolment_close_date)) " +
                "AND (gi.grade_item_id NOT IN  " +
                "(SELECT  " +
                "eet.exam_id " +
                "FROM exam_enrolment eet " +
                "WHERE eet.has_unEnrolled = 0 " +
                "AND eet.is_Enrolled = 1 " +
                "AND eet.student_id =  ? ) " +
                ")" +
                "AND(gi.grade_item_id =   ? " +
                "OR gi.grade_item_name LIKE   ? " +
                "OR gi.date_of_grade_item LIKE  ? " +
                "OR cc.child_course_name LIKE   ?  " +
                "OR pc.course_name LIKE  ? " +
                "OR pc.course_short_name LIKE  ? ) " +
                "HAVING m.major_id =   ? " +
                "ORDER BY s.semester_code DESC, gi.enrolment_close_date DESC  ";

        try{
            List<SearchCoursesAndExams> searchList = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId,'%' + searchParam + '%', '%' + searchParam + '%', '%' + searchParam + '%','%' + searchParam + '%', '%' + searchParam + '%', '%' + searchParam + '%', majorId},
                    new RowMapper<SearchCoursesAndExams>() {
                        @Override
                        public SearchCoursesAndExams mapRow(ResultSet resultSet, int i) throws SQLException {
                            SearchCoursesAndExams searchCoursesAndExams = new SearchCoursesAndExams();

                            searchCoursesAndExams.setExamEnrolmentEndDate(resultSet.getString( "enrolment_deadline") ) ;
                            searchCoursesAndExams.setCourseName(resultSet.getString( "course_name") ) ;
                            searchCoursesAndExams.setMajorId(resultSet.getInt("major_id"));
                            searchCoursesAndExams.setMajorName(resultSet.getString("major_name"));
                            searchCoursesAndExams.setMajorShortCode(resultSet.getString("major_short_code"));
                            searchCoursesAndExams.setCourseType(resultSet.getString("course_type"));
                            searchCoursesAndExams.setSemesterId(resultSet.getInt("semester_id"));
                            searchCoursesAndExams.setSemesterCode(resultSet.getString("semester_code"));
                            searchCoursesAndExams.setChildCourseName(resultSet.getString("child_course_name"));
                            searchCoursesAndExams.setParentCourseId(resultSet.getInt("parent_course_id"));
                            searchCoursesAndExams.setChildCourseId(resultSet.getInt("child_course_id"));
                            searchCoursesAndExams.setParentCouresName(resultSet.getString("course_name"));
                            searchCoursesAndExams.setCourseDescriptionEn(resultSet.getString("description"));

                            searchCoursesAndExams.setExamId(resultSet.getInt("grade_item_id"));
                            searchCoursesAndExams.setExamName(resultSet.getString("grade_item_name"));
                            searchCoursesAndExams.setExamDate(resultSet.getString("date_of_grade_item"));



                            return searchCoursesAndExams;
                        }
                    }
            );
            return searchList;
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SearchCoursesAndExams> searchChildCourseExamsFilteredBySemesterId(int majorId, String searchParam, int semesterId, long studentId) {
        String sql = "SELECT    " +
                "m.major_id,   " +
                "m.major_name,   " +
                "m.major_short_code,   " +
                "pc.parent_course_id,   " +
                "pc.course_type,   " +
                "pc.credits,   " +
                "s.semester_id,   " +
                "s.semester_code,   " +
                "pc.course_short_name,  " +
                "pc.course_name,   " +
                "cc.child_course_id,   " +
                "cc.child_course_name,  " +
                "cc.enrolment_deadline,  " +
                "cc.teacher_id,  " +
                "gi.grade_item_id,  " +
                "gi.grade_item_name,  " +
                "gi.date_of_grade_item,  " +
                "gi.enrolment_close_date,  " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name,   " +
                "pc.description,   " +
                "pc.status   " +
                "FROM grade_items gi   " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id  " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id   " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id   " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id   " +
                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id   " +
                "LEFT JOIN major m ON m.major_id = pcm.major_id   " +
                "LEFT JOIN child_course_semester ccc ON ccc.child_course_id = cc.child_course_id   " +
                "LEFT JOIN semester s ON s.semester_id = ccc.semester_id   " +
                "WHERE ( gi.grade_item_type = 'Exam') " +
                "AND ( CURDATE()<= DATE(gi.enrolment_close_date)) " +
                "AND (gi.grade_item_id NOT IN  " +
                "(SELECT  " +
                "eet.exam_id " +
                "FROM exam_enrolment eet " +
                "WHERE eet.has_unEnrolled = 0 " +
                "AND eet.is_Enrolled = 1 " +
                "AND eet.student_id =  ? ) " +
                ")" +
                "AND(gi.grade_item_id =   ? " +
                "OR gi.grade_item_name LIKE   ? " +
                "OR gi.date_of_grade_item LIKE  ? " +
                "OR cc.child_course_name LIKE   ?  " +
                "OR pc.course_name LIKE  ? " +
                "OR pc.course_short_name LIKE  ? ) " +
                "HAVING m.major_id =   ? AND s.semester_id = ? " +
                "ORDER BY s.semester_code DESC, gi.enrolment_close_date DESC  ";

        try{
            List<SearchCoursesAndExams> searchList = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId,'%' + searchParam + '%', '%' + searchParam + '%', '%' + searchParam + '%','%' + searchParam + '%', '%' + searchParam + '%', '%' + searchParam + '%', majorId, semesterId},
                    new RowMapper<SearchCoursesAndExams>() {
                        @Override
                        public SearchCoursesAndExams mapRow(ResultSet resultSet, int i) throws SQLException {
                            SearchCoursesAndExams searchCoursesAndExams = new SearchCoursesAndExams();

                            searchCoursesAndExams.setExamEnrolmentEndDate(resultSet.getString( "enrolment_close_date") ) ;
                            searchCoursesAndExams.setCourseName(resultSet.getString( "course_name") ) ;
                            searchCoursesAndExams.setMajorId(resultSet.getInt("major_id"));
                            searchCoursesAndExams.setMajorName(resultSet.getString("major_name"));
                            searchCoursesAndExams.setMajorShortCode(resultSet.getString("major_short_code"));
                            searchCoursesAndExams.setCourseType(resultSet.getString("course_type"));
                            searchCoursesAndExams.setSemesterId(resultSet.getInt("semester_id"));
                            searchCoursesAndExams.setSemesterCode(resultSet.getString("semester_code"));
                            searchCoursesAndExams.setChildCourseName(resultSet.getString("child_course_name"));
                            searchCoursesAndExams.setParentCourseId(resultSet.getInt("parent_course_id"));
                            searchCoursesAndExams.setChildCourseId(resultSet.getInt("child_course_id"));
                            searchCoursesAndExams.setParentCouresName(resultSet.getString("course_name"));
                            searchCoursesAndExams.setCourseDescriptionEn(resultSet.getString("description"));

                            searchCoursesAndExams.setExamId(resultSet.getInt("grade_item_id"));
                            searchCoursesAndExams.setExamName(resultSet.getString("grade_item_name"));
                            searchCoursesAndExams.setExamDate(resultSet.getString("date_of_grade_item"));



                            return searchCoursesAndExams;
                        }
                    }
            );
            return searchList;
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
