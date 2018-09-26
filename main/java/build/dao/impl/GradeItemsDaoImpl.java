package build.dao.impl;

import build.model.Exam;
import build.model.ExamEnrolment;
import build.model.Grade;
import build.model.GradeItems;
import build.dao.GradeItemsDao;

import build.row_mapper.GradeItemsRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public class GradeItemsDaoImpl extends JdbcDaoSupport implements GradeItemsDao{
    @Override
    public String addGradeItems(GradeItems gradeItems) {
        String sql = "INSERT INTO  grade_items( course_id,grade_item_type,grade_item_name,weight,min_grade,max_grade,created_by ) VALUES( ?,?,?,?,?,?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{gradeItems.getCourseId(),gradeItems.getGradeItemType(),gradeItems.getGradeItemName(),gradeItems.getWeight(),gradeItems.getMinGrade(),gradeItems.getMaxGrade(),gradeItems.getCreatedBy() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String createNewExam(GradeItems gradeItems) {
        String sql = "INSERT INTO  grade_items( parent_course_id,course_id,grade_item_type,grade_item_name,weight,min_grade,max_grade,created_by,date_of_grade_item,enrolment_start_date,enrolment_close_date) VALUES( ?,?,?,?,?,?,?,?,?,?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ gradeItems.getParentCourseId(),gradeItems.getCourseId(),gradeItems.getGradeItemType(),gradeItems.getGradeItemName(),gradeItems.getWeight(),gradeItems.getMinGrade(),gradeItems.getMaxGrade(),gradeItems.getCreatedBy(),gradeItems.getDateOfExam(),gradeItems.getEnrolmentStartDate(),gradeItems.getEnrolmentCloseDate() }
        );
        if(1 == returnValue)
            return "EXAM #"+gradeItems.getGradeItemName()+" CREATED SUCCESSFULLY";
        else
            return "QUERY FAILURE MESSAGE COULD NOT CREATE EXAM";
    }

    @Override
    public String createChildCourseExam(Exam exam) {
        String sql = "INSERT INTO  grade_items( parent_course_id,course_id,grade_item_type,grade_item_name,weight,min_grade,max_grade,created_by,date_of_grade_item,enrolment_close_date,enrolment_start_date) VALUES( ?,?,?,?,?,?,?,?,?,?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{exam.getParentCourseId(),exam.getChildCourseId(),"Exam",exam.getExamName(),exam.getWeight(),exam.getMinGrade(),exam.getMaxGrade(),exam.getCreatedBy(),exam.getDateOfExam(),exam.getEnrolmentCloseDate(),exam.getEnrolmentStartDate()}
            );
            if(returnValue == 1){
                System.out.println("EXAM CREATED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("EXAM CREATE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INTERNAL SYSTEM QUERY ERROR!!");
            return "400";
        }
    }

    @Override
    public String updateGradeItem(GradeItems gradeItems) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

        String sql = "UPDATE grade_items " +
                "SET  " +
                "grade_item_name  = ? , " +
                " min_grade = ?, " +
                " max_grade =  ?, " +
                " date_of_grade_item =  ?, " +
                " enrolment_start_date = ? , " +
                " enrolment_close_date = ? ," +
                " weight = ?, " +
                " updated_at = ?  " +
                " WHERE grade_item_id =  ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{gradeItems.getGradeItemName(),gradeItems.getMinGrade(),gradeItems.getMaxGrade(),gradeItems.getDateOfExam(),gradeItems.getEnrolmentStartDate(),gradeItems.getEnrolmentCloseDate(),gradeItems.getWeight(),timestamp,gradeItems.getGradeItemId()}
            );
            if(1 == returnValue)
                return "GRADE ITEM UPDATED";
            else
                return "UPDATE FAILED";


        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteGradeItemByGradeItemId(int gradeItemId) {
        String sql = "DELETE FROM grade_items WHERE grade_item_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{gradeItemId}
            );
            if(1 == returnValue)
                return "GRADE ITEM DELETED";
            else
                return "DELETE FAILED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }

    }

    @Override
    public String deleteGradeItemStudentGrades(int gradeItemId) {

        String sql = "DELETE FROM grade WHERE grade_item_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{gradeItemId}
            );
            return "GRADE ITEM GRADES DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String removeStudentFromExam(int examId, long studentId) {
        String sql = "DELETE FROM exam_enrolment WHERE exam_id = ? AND student_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{examId,studentId}
            );
            if(returnValue == 1){
                System.out.println("@DELETE COMPLETED");
                return "200";
            }else{
                System.out.println("@DELETE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INTERNAL SYSTEM QUERY ERROR!!");
            return "500";
        }
    }

    @Override
    public List<GradeItems> getAvailableExamsForStudent(long userId) {
        String sql = "SELECT  " +
                "gi.grade_item_id,  " +
                "gi.parent_course_id,  " +
                "gi.course_id,  " +
                "pc.course_name,  " +
                "cc.child_course_name,  " +
                "pc.description,  " +
                "gi.grade_item_name,  " +
                "gi.enrolment_start_date, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y')examDate,  " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadline  " +
                "FROM grade_items gi  " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id  " +
                "LEFT JOIN course_enrolment ce ON ce.course_id = gi.course_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND gi.date_of_grade_item > CURRENT_DATE  " +
                "AND gi.grade_item_id NOT IN (SELECT  " +
                "ee.exam_id  " +
                "FROM exam_enrolment ee  " +
                "WHERE ee.has_unEnrolled = 0  " +
                "AND ee.is_Enrolled = 1 " +
                "AND ee.student_id =  ? )  " +
                "AND ce.student_id =  ?  " +
                "ORDER BY gi.enrolment_close_date ";

        try{
            List<GradeItems> studentExamList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId,userId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();

                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setParentCourseId(resultSet.getLong("parent_course_id"));
                            gradeItem.setCourseId(resultSet.getInt("course_id"));
                            gradeItem.setCourseName(resultSet.getString( "course_name"));
                            gradeItem.setCourseShortName(resultSet.getString("child_course_name"));
                            gradeItem.setCourseDescription(resultSet.getString("description"));
                            gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
                            gradeItem.setDateOfExam(resultSet.getString( "examDate"));
                            gradeItem.setEnrolmentCloseDate(resultSet.getString( "deadline"));

                            return gradeItem;
                        }
                    }
            );
            return studentExamList;

        }
        catch(EmptyResultDataAccessException e){
//            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> getLatestExamsForStudent(long studentId) {
//        "# Latest exams / exams from parent course relatives student is directly enrolled in that they are not yet enrolled in \n" +
        String sql = "SELECT  " +
                "gi.grade_item_id,  " +
                "gi.parent_course_id,  " +
                "gi.course_id,  " +
                "pc.course_name,  " +
                "cc.child_course_name,  " +
                "pc.description,  " +
                "gi.grade_item_name,  " +
                "gi.enrolment_start_date, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y')examDate,  " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadline  " +
                "FROM grade_items gi  " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id  " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND gi.date_of_grade_item > CURRENT_DATE  " +
                "" +
                "AND gi.grade_item_id NOT IN (SELECT  " +
                "ee.exam_id  " +
                "FROM exam_enrolment ee   " +
                "WHERE ee.has_unEnrolled = 0  " +
                "AND ee.is_Enrolled = 1 " +
                "AND ee.student_id =  ?)  " +
                "" +
                "AND gi.parent_course_id IN ( " +
                "(SELECT  " +
                "pct.parent_course_id " +
                "FROM child_courses cct  " +
                "LEFT JOIN course_enrolment cet ON cet.course_id = cct.child_course_id " +
                "LEFT JOIN parent_course_child_courses pcct ON pcct.child_course_id = cct.child_course_id  " +
                "LEFT JOIN parent_courses pct ON pct.parent_course_id = pcct.parent_course_id  " +
                "WHERE cet.has_unenrolled = 0 " +
                "AND cet.is_enrolled = 1 " +
                "AND cet.student_id =  ?) " +
                ") " +
                "ORDER BY gi.date_of_grade_item ";

        try{
            List<GradeItems> studentExamList = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId,studentId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();

                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setParentCourseId(resultSet.getLong("parent_course_id"));
                            gradeItem.setCourseId(resultSet.getInt("course_id"));
                            gradeItem.setCourseName(resultSet.getString( "course_name"));
                            gradeItem.setCourseShortName(resultSet.getString("child_course_name"));
                            gradeItem.setCourseDescription(resultSet.getString("description"));
                            gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
                            gradeItem.setDateOfExam(resultSet.getString( "examDate"));
                            gradeItem.setEnrolmentCloseDate(resultSet.getString( "deadline"));

                            return gradeItem;
                        }
                    }
            );
            return studentExamList;

        }
        catch(EmptyResultDataAccessException e){
//            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> getRecommendedAvailableExamsForStudent(long parentCourseId, long studentId) {
        String sql = "SELECT  " +
                "gi.grade_item_id,  " +
                "gi.parent_course_id,  " +
                "gi.course_id,  " +
                "pc.course_name,  " +
                "cc.child_course_name,  " +
                "pc.description,  " +
                "gi.grade_item_name,  " +
                "gi.enrolment_start_date, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y')examDate,  " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadline  " +
                "FROM grade_items gi  " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id  " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND gi.date_of_grade_item > CURRENT_DATE  " +
                "AND gi.grade_item_id NOT IN (SELECT   " +
                "ee.exam_id  " +
                "FROM exam_enrolment ee   " +
                "WHERE ee.has_unEnrolled = 0  " +
                "AND ee.is_Enrolled = 1 " +
                "AND ee.student_id = ?)  " +
                "AND gi.parent_course_id = ?  " +
                "ORDER BY gi.date_of_grade_item";

        try{
            List<GradeItems> studentExamList = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId,parentCourseId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();

                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setParentCourseId(resultSet.getLong("parent_course_id"));
                            gradeItem.setCourseId(resultSet.getInt("course_id"));
                            gradeItem.setCourseName(resultSet.getString( "course_name"));
                            gradeItem.setCourseShortName(resultSet.getString("child_course_name"));
                            gradeItem.setCourseDescription(resultSet.getString("description"));
                            gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
                            gradeItem.setDateOfExam(resultSet.getString( "examDate"));
                            gradeItem.setEnrolmentCloseDate(resultSet.getString( "deadline"));

                            return gradeItem;
                        }
                    }
            );
            return studentExamList;

        }
        catch(EmptyResultDataAccessException e){
//            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> getStudentEnrolledExamsBySemesterId(long studentId, int semesterId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents  " +
                "FROM exam_enrolment ee  " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = ee.exam_id " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pcm.major_id = m.major_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND ee.is_Enrolled = 1 " +
                "AND ee.has_unEnrolled = 0 " +
                "AND ee.student_id = ? " +
                "AND s.semester_id = ?  " +
                "ORDER BY s.semester_code DESC";


        try{
            List<GradeItems> allExamsList = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId , semesterId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("parent_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("child_course_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            exam.setChildCourseId(resultSet.getInt("child_course_id"));
                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorShortName(resultSet.getString("major_short_code"));
                            exam.setMinPassScore(resultSet.getInt("min_pass_score"));
                            exam.setSemesterId(resultSet.getInt("semester_id"));
                            exam.setSemesterCode(resultSet.getString("semester_code"));
                            return exam;
                        }
                    }
            );
            return allExamsList;

        }
        catch(EmptyResultDataAccessException e){
//            e.printStackTrace();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<GradeItems> getCourseExamsByCourseId(int courseId) {
        String sql = " SELECT  " +
                " gi.course_id, " +
                " gi.grade_item_id, " +
                " gi.grade_item_name, " +
                " DATE_FORMAT( gi.date_of_grade_item, '%D %M %Y')  exam_date, " +
                " DATE_FORMAT(gi.enrolment_start_date, '%D %M %Y') start_date , " +
                " DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') close_date , " +
                " gi.active_status, " +
                " gi.created_by, " +
                " u.user_name, " +
                "(SELECT COUNT(*)FROM exam_enrolment ee ,user u WHERE ee.course_id = gi.course_id AND u.user_id = ee.student_id AND u.status = 'active' AND ee.is_Enrolled = 1)participates " +
                " FROM grade_items gi " +
                " LEFT JOIN user u ON u.user_id = gi.created_by " +
                " WHERE gi.course_id = ? " +
                " AND gi.grade_item_type = 'Exam'";


        try{

            List<GradeItems> examList = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItems = new GradeItems();

                            gradeItems.setCourseId(resultSet.getInt("course_id"));
                            gradeItems.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItems.setGradeItemName(resultSet.getString("grade_item_name"));
                            gradeItems.setDateOfExam(resultSet.getString("exam_date"));
                            gradeItems.setEnrolmentStartDate(resultSet.getString("start_date"));
                            gradeItems.setEnrolmentCloseDate(resultSet.getString("close_date"));
                            gradeItems.setActiveStatus(resultSet.getInt("active_status"));
                            gradeItems.setCreatedBy(resultSet.getLong("created_by"));
                            gradeItems.setCreatedByUserName(resultSet.getString("user_name"));
                            gradeItems.setParticipants(resultSet.getString("participates"));

                            return  gradeItems;
                        }
                    }

            );
            return examList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@getCourseExamsByCourseId::getCourseExamsByCourseId::Error!!");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@getCourseExamsByCourseId::getCourseExamsByCourseId::Error!!");
            e.printStackTrace();
            return  null;
        }



    }

    @Override
    public int getTotalNumberOfAvailableExamsForStudent(long userId) {
        String sql = "SELECT COUNT(gi.grade_item_id) " +
                "FROM  " +
                "grade_items gi,course_enrolment ce, user usr,courses crs  " +
                "WHERE  " +
                "crs.course_id = gi.course_id  " +
                "AND usr.user_id = gi.created_by  " +
                "AND gi.active_status = 1  " +
                "AND gi.grade_item_type = 'Exam'  " +
                "AND gi.course_id = ce.course_id  " +
                "AND ce.is_enrolled = 1  " +
                "AND gi.course_id NOT IN (SELECT ee.course_id FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.has_unEnrolled = 0 AND ee.student_id = ce.student_id) " +
                "AND gi.course_id NOT IN (SELECT er.course_id FROM exam_student_request_enrolment er WHERE er.student_id = ce.student_id) " +
                "AND ce.student_id = ? " +
                "ORDER BY gi.enrolment_close_date";

        try{
            int totalAvailableExams = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return totalAvailableExams;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<GradeItems> getCourseExams(long parentCourseId, long studentId) {
        String sql = "SELECT " +
                "gi.parent_course_id, " +
                "gi.course_id, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') date_of_grade_item, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y')enrolment_close_date " +
                "FROM grade_items gi " +
                "LEFT JOIN exam_enrolment ee ON ee.exam_id = gi.grade_item_id " +
                "WHERE ee.is_Enrolled = 1 " +
                "AND ee.has_unEnrolled = 0  " +
                "AND gi.parent_course_id = ? " +
                "AND ee.student_id =  ? " +
                "ORDER BY gi.date_of_grade_item DESC";


        try{
            List<GradeItems> gradeItemsList = getJdbcTemplate().query(
                    sql,
                    new Object[]{parentCourseId,studentId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();

                            gradeItem.setParentCourseId(resultSet.getLong("parent_course_id"));
                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setCourseId(resultSet.getInt("course_id"));
                            gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
                            gradeItem.setDateOfExam(resultSet.getString( "date_of_grade_item"));
                            gradeItem.setEnrolmentCloseDate(resultSet.getString( "enrolment_close_date"));
                            return gradeItem;
                        }

                    }
            );
            return gradeItemsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> getChildCourseExams(int childCourseId) {

        String sql = "SELECT " +
                "gi.parent_course_id,  " +
                "gi.course_id,  " +
                "gi.grade_item_type,  " +
                "gi.grade_item_id,  " +
                "gi.grade_item_name,  " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') date_of_grade_item, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y')enrolment_close_date,  " +
                "gi.date_of_grade_item exam_end_date, " +
                "gi.weight,  " +
                "gi.max_grade,  " +
                "gi.min_grade, " +
                "(SELECT COUNT(*) " +
                "FROM exam_enrolment ee  " +
                "WHERE ee.is_Enrolled = 1 " +
                "AND ee.has_unEnrolled = 0 " +
                "AND ee.exam_id = gi.grade_item_id) totalEnrolled_students " +
                "FROM grade_items gi   " +
                "WHERE gi.course_id =   ? " +
                "ORDER BY gi.date_of_grade_item DESC";


        try{
            List<GradeItems> gradeItemsList = getJdbcTemplate().query(
                    sql,
                    new Object[]{childCourseId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();

                            gradeItem.setParentCourseId(resultSet.getLong("parent_course_id"));
                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setCourseId(resultSet.getInt("course_id"));
                            gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
                            gradeItem.setDateOfExam(resultSet.getString( "date_of_grade_item"));
                            gradeItem.setGradeItemType(resultSet.getString("grade_item_type"));
                            gradeItem.setEnrolmentCloseDate(resultSet.getString( "enrolment_close_date"));
                            gradeItem.setExamEndDate(resultSet.getString("exam_end_date"));
                            gradeItem.setWeight(resultSet.getDouble("weight"));
                            gradeItem.setMinGrade(resultSet.getInt("min_grade"));
                            gradeItem.setMaxGrade(resultSet.getInt("max_grade"));
                            gradeItem.setTotalEnrollments(resultSet.getInt("totalEnrolled_students"));
                            return gradeItem;
                        }

                    }
            );
            return gradeItemsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> getStudentCourseGradeDetailedReport(long parentCourseId , long userId) {
        String sql = "SELECT " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "gi.min_grade, " +
                "gi.max_grade, " +
                "gi.grade_item_type, " +
                "gi.course_id , " +
                "(SELECT g.grade FROM grade g WHERE g.grade_item_id = gi.grade_item_id AND g.student_id = u.user_id) grade, " +
                "ROUND(((SELECT g.grade FROM grade g WHERE g.grade_item_id = gi.grade_item_id AND g.student_id = u.user_id)/gi.max_grade*100),1)percentage, " +
                "(SELECT letter FROM grade_letters WHERE (SELECT g.grade FROM grade g WHERE g.grade_item_id = gi.grade_item_id AND g.student_id = u.user_id) BETWEEN grade_letters.lowest AND grade_letters.highest)letter " +
                "FROM grade_items gi " +
                "LEFT JOIN user u ON u.user_id =  ? " +
                "WHERE gi.parent_course_id = ? ";


       try{
           List<GradeItems> courseGradeDetailedList = getJdbcTemplate().query(
                   sql,
                   new Object[]{userId, parentCourseId},
                   new RowMapper<GradeItems>() {
                       @Override
                       public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                           GradeItems gradeItem = new GradeItems();
                           gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                           gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
                           gradeItem.setGradeItemType(resultSet.getString("grade_item_type"));
                           gradeItem.setGrade(resultSet.getFloat("grade"));
                           gradeItem.setMinGrade(resultSet.getInt("min_grade"));
                           gradeItem.setMaxGrade(resultSet.getInt("max_grade"));
                           gradeItem.setPercentage(resultSet.getDouble("percentage"));
                           gradeItem.setLetter(resultSet.getString("letter"));

                           return  gradeItem;

                       }
                   }
           );
           return  courseGradeDetailedList;

       }catch (EmptyResultDataAccessException e) {
           e.printStackTrace();
           List<GradeItems> emptyList = null;
           return emptyList;
       }

    }

    @Override
    public List<GradeItems> getStudentCourseExamsReportList(long parentCourseId, long userId) {
        String sql = "SELECT " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "gi.min_grade,   " +
                "gi.max_grade,  " +
                "gi.grade_item_type,  " +
                "gi.course_id, " +
                "g.grade, " +
                "ROUND((g.grade/gi.max_grade*100),1)percentage,  " +
                "(SELECT letter FROM grade_letters WHERE g.grade BETWEEN grade_letters.lowest AND grade_letters.highest)letter  " +
                "FROM grade g " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = g.grade_item_id " +
                "WHERE gi.grade_item_type = 'Exam'  " +
                "AND g.parent_course_id = ? " +
                "AND g.student_id =  ? " +
                "ORDER BY g.grade DESC ";


        try{
            List<GradeItems> courseGradeDetailedList = getJdbcTemplate().query(
                    sql,
                    new Object[]{ parentCourseId, userId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();
                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
                            gradeItem.setGradeItemType(resultSet.getString("grade_item_type"));
                            gradeItem.setGrade(resultSet.getFloat("grade"));
                            gradeItem.setMinGrade(resultSet.getInt("min_grade"));
                            gradeItem.setMaxGrade(resultSet.getInt("max_grade"));
                            gradeItem.setPercentage(resultSet.getDouble("percentage"));
                            gradeItem.setLetter(resultSet.getString("letter"));

                            return  gradeItem;

                        }
                    }
            );
            return  courseGradeDetailedList;

        }catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            List<GradeItems> emptyList = null;
            return emptyList;
        }
    }

    @Override
    public List<GradeItems> getStudentCourseGradeItemReportList(int childCourseId, long userId) {
        String sql = " SELECT  " +
                " gi.grade_item_id,  " +
                " gi.grade_item_name,  " +
                " gi.min_grade,  " +
                " gi.max_grade,  " +
                " gi.grade_item_type,  " +
                " gi.course_id ,  " +
                " (SELECT g.grade FROM grade g WHERE g.grade_item_id = gi.grade_item_id AND g.student_id = u.user_id) grade,  " +
                " ROUND(((SELECT g.grade FROM grade g WHERE g.grade_item_id = gi.grade_item_id AND g.student_id = u.user_id)/gi.max_grade*100),1)percentage,  " +
                " (SELECT letter FROM grade_letters WHERE (SELECT g.grade FROM grade g WHERE g.grade_item_id = gi.grade_item_id AND g.student_id = u.user_id) BETWEEN grade_letters.lowest AND grade_letters.highest)letter  " +
                " FROM grade_items gi " +
                " LEFT JOIN child_course_semester ccs ON ccs.child_course_id = gi.course_id " +
                " LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                " LEFT JOIN USER u ON u.user_id =   ? " +
                " WHERE gi.course_id =  ? " +
                " AND gi.grade_item_type != 'Exam' " +
                " ORDER BY gi.grade_item_type ";


        try{
            List<GradeItems> courseGradeDetailedList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId, childCourseId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();
                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
                            gradeItem.setGradeItemType(resultSet.getString("grade_item_type"));
                            gradeItem.setGrade(resultSet.getFloat("grade"));
                            gradeItem.setMinGrade(resultSet.getInt("min_grade"));
                            gradeItem.setMaxGrade(resultSet.getInt("max_grade"));
                            gradeItem.setPercentage(resultSet.getDouble("percentage"));
                            gradeItem.setLetter(resultSet.getString("letter"));

                            return  gradeItem;

                        }
                    }
            );
            return  courseGradeDetailedList;

        }catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            List<GradeItems> emptyList = null;
            return emptyList;
        }
    }

    @Override
    public double getGradeItemsOverallTotal(int courseId, long userId) {
        String sql = "SELECT " +
                "SUM((g.grade/gi.max_grade)*gi.weight*100)DIV 1 " +
                "FROM grade g  " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = g.grade_item_id " +
                "WHERE g.course_id =  ?" +
                "AND g.student_id =  ? ";

        try{

            double courseGradeItemOverallTotal = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseId,userId},
                    Double.class
            );
            return courseGradeItemOverallTotal;

        } catch (EmptyResultDataAccessException e) {
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public List<GradeItems> getAllStudentExams(long userId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents  " +
                "FROM exam_enrolment ee  " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = ee.exam_id " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pcm.major_id = m.major_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND ee.is_Enrolled = 1 " +
                "AND ee.has_unEnrolled = 0 " +
                "AND ee.student_id = ? " +
                "ORDER BY s.semester_code DESC";


        try{
            List<GradeItems> allExamsList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("parent_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("child_course_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            exam.setChildCourseId(resultSet.getInt("child_course_id"));
                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorShortName(resultSet.getString("major_short_code"));
                            exam.setMinPassScore(resultSet.getInt("min_pass_score"));
                            exam.setSemesterId(resultSet.getInt("semester_id"));
                            exam.setSemesterCode(resultSet.getString("semester_code"));
                            return exam;
                        }
                    }
            );
            return allExamsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> getAllTeacherExams(long userId) {
        String sql = "SELECT  " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "gi.course_id, " +
                "pc.parent_course_id, " +
                "cc.child_course_id, " +
                "pc.course_name, " +
                "cc.child_course_name " +
                "FROM grade_items gi " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND cc.teacher_id =  ? " +
                "ORDER BY gi.date_of_grade_item DESC ";


        try{
            List<GradeItems> allExamsList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();

                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
                            gradeItem.setCourseId(resultSet.getInt("child_course_id"));
                            gradeItem.setChildCourseId(resultSet.getInt("child_course_id"));
                            gradeItem.setParentCourseId(resultSet.getLong("parent_course_id"));
                            gradeItem.setCourseShortName(resultSet.getString("child_course_name"));
                            gradeItem.setCourseName(resultSet.getString("course_name"));
                            return gradeItem;
                        }
                    }
            );
            return allExamsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getAllStudentExamsCount(long userId) {
        String sql = "SELECT COUNT(*) " +
                "FROM exam_enrolment ee " +
                "WHERE ee.is_Enrolled = 1 " +
                "AND ee.has_unEnrolled = 0 " +
                "AND ee.student_id =  ? ";


        try{
            int allExamsCount = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            ) ;
            return allExamsCount;
        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getAllTeacherExamsCount(long userId) {
        String sql = "SELECT COUNT(*) " +
                "FROM grade_items gi  " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND cc.teacher_id =  ? ";


        try{
            int allExamsCount = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            ) ;
            return allExamsCount;
        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTotalExamEnrolmentsCount() {
        String sql = "SELECT COUNT(*) " +
                "FROM exam_enrolment ee  " +
                "WHERE ee.is_Enrolled = 1 " +
                "AND ee.has_unEnrolled = 0";

        try{
            int totalExamEnrolments = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalExamEnrolments;
        }
        catch(EmptyResultDataAccessException e){
        e.printStackTrace();
        return 0;
    }
    }

    @Override
    public List<GradeItems> getActiveExamRequestNotifications() {
        String sql = " SELECT " +
                " gi.grade_item_id," +
                " gi.grade_item_name," +
                " ( SELECT COUNT(*) FROM  exam_enrolment WHERE gi.grade_item_id = exam_enrolment.exam_id AND is_enrolled = 1 ) " +
                " totalEnrollments, " +
                " ( SELECT COUNT(*) FROM  exam_student_request_enrolment WHERE gi.grade_item_id = exam_student_request_enrolment.exam_id AND enrolment_status = 0 )" +
                " totalEnrollmentRequests" +
                "  FROM  grade_items gi, exam_student_request_enrolment er" +
                "  WHERE " +
                "  gi.grade_item_type = 'exam'" +
                "  AND gi.active_status=1 " +
                "  AND gi.grade_item_id = er.exam_id " +
                "  AND er.enrolment_status = 0" +
                "  GROUP BY gi.grade_item_id" +
                "  ORDER BY totalEnrollmentRequests DESC";

        try{

            List<GradeItems> activeExamsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();
                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
                            gradeItem.setTotalEnrollments(resultSet.getInt("totalEnrollments"));
                            gradeItem.setTotalEnrollmentRequests(resultSet.getInt("totalEnrollmentRequests"));

                            return  gradeItem;
                        }
                    }

            );
            return activeExamsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public List<GradeItems> getActiveExamRequestNotificationsByTeacherId(long teacherId) {
        String sql = "SELECT  " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "( SELECT COUNT(*) FROM  exam_enrolment WHERE gi.grade_item_id = exam_enrolment.exam_id AND is_enrolled = 1 )  " +
                "totalEnrollments,  " +
                "( SELECT COUNT(*) FROM  exam_student_request_enrolment WHERE gi.grade_item_id = exam_student_request_enrolment.exam_id AND enrolment_status = 0 ) " +
                "totalEnrollmentRequests  " +
                "FROM  grade_items gi, exam_student_request_enrolment er , child_courses cc  " +
                "WHERE  " +
                "gi.grade_item_type = 'exam' " +
                "AND gi.active_status=1 " +
                "AND gi.grade_item_id = er.exam_id  " +
                "AND er.enrolment_status = 0  " +
                "AND cc.child_course_id = gi.course_id  " +
                "AND cc.teacher_id =  ? " +
                "GROUP BY gi.grade_item_id  " +
                "ORDER BY totalEnrollmentRequests DESC ";

        try{

            List<GradeItems> activeExamsList = getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();
                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
                            gradeItem.setTotalEnrollments(resultSet.getInt("totalEnrollments"));
                            gradeItem.setTotalEnrollmentRequests(resultSet.getInt("totalEnrollmentRequests"));

                            return  gradeItem;
                        }
                    }

            );
            return activeExamsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> getNotActiveExamRequestNotifications() {
        String sql = "SELECT " +
                " gi.grade_item_id, " +
                " gi.grade_item_name, " +
                " ( SELECT COUNT(*) FROM  exam_enrolment WHERE gi.grade_item_id = exam_enrolment.exam_id AND is_enrolled = 1 ) " +
                " totalEnrollments, " +
                " ( SELECT COUNT(*) FROM  exam_student_request_enrolment WHERE gi.grade_item_id = exam_student_request_enrolment.exam_id AND enrolment_status = 0 ) " +
                " totalEnrollmentRequests " +
                " FROM  grade_items gi " +
                " WHERE gi.grade_item_type = 'exam' " +
                " AND gi.active_status=1 " +
                " AND ( SELECT COUNT(*) FROM  exam_student_request_enrolment WHERE gi.grade_item_id = exam_student_request_enrolment.exam_id AND enrolment_status = 0 ) = 0 " +
                " ORDER BY totalEnrollmentRequests DESC " +
                " LIMIT 2";

        try{
            List<GradeItems> notActiveExamRequestList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();
                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setGradeItemName(resultSet.getString( "grade_item_name"));
                            gradeItem.setTotalEnrollments(resultSet.getInt("totalEnrollments"));
                            gradeItem.setTotalEnrollmentRequests(resultSet.getInt("totalEnrollmentRequests"));

                            return  gradeItem;
                        }
                    }

            );
            return notActiveExamRequestList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }




    }

    @Override
    public int adminGetAllExamEnrollmentRequestCounts() {
        String sql = "SELECT COUNT(*)  " +
                " FROM exam_student_request_enrolment er " +
                " LEFT JOIN grade_items gi ON gi.grade_item_id = er.exam_id " +
                " LEFT JOIN child_courses cc ON cc.child_course_id = er.course_id " +
                " WHERE enrolment_status = 0  " +
                " AND gi.grade_item_type = 'Exam' " ;
        try{

            int examEnrollmentRequests = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return examEnrollmentRequests;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int adminGetAllExamEnrollmentRequestCountsByTeacherId(long teacherId) {
        String sql = "SELECT COUNT(*)  " +
                "FROM exam_student_request_enrolment er " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = er.exam_id  " +
                "LEFT JOIN child_courses cc ON cc.child_course_id= er.course_id  " +
                "WHERE enrolment_status = 0   " +
                "AND gi.grade_item_type = 'Exam' " +
                "AND cc.teacher_id =  ? ";
        try{

            int examEnrollmentRequests = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId},
                    Integer.class
            );
            return examEnrollmentRequests;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int adminGetAllCurrentlyActiveExamsCount() {
        String sql = " SELECT COUNT(*) " +
                "    FROM grade_items exam " +
                "    WHERE exam.active_status = 1 AND CURDATE()<= DATE(exam.date_of_grade_item) ";

        try{
            int activeExamCount = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return activeExamCount;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int adminGetTotalSystemCreatedExamsCount() {

        String sql = "SELECT COUNT(*)  " +
                " FROM grade_items gi " +
                " WHERE gi.grade_item_type = 'Exam'";

        try{
            int activeExamCount = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return activeExamCount;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTotalClearanceExamCount() {
        String sql = "SELECT COUNT(*) " +
                "FROM clearance_exam cle";

        try{

            int count = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTotalActiveClearanceExamCount() {
        String sql = "SELECT COUNT(*) " +
                "FROM clearance_exam cle " +
                "WHERE  CURDATE() <= DATE(cle.exam_date)";

        try{

            int count = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTotalStudentsEnrolledInClearanceExams() {
        String sql = "SELECT COUNT(*) " +
                "FROM clearance_exam_enrolment clee " +
                "WHERE clee.is_enrolled = 1" ;

        try{

            int count = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTotalClearanceExamCountForTeacher(long teacherId) {
        String sql = "SELECT COUNT(*) " +
                "FROM clearance_exam cle , child_courses cc " +
                "WHERE cle.child_course_id = cc.child_course_id " +
                "AND cc.teacher_id = ? ";

        try{

            int count = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTotalActiveClearanceExamCountForTeacher(long teacherId) {
        String sql = "SELECT COUNT(*) " +
                "FROM clearance_exam cle , child_courses cc " +
                "WHERE cle.child_course_id = cc.child_course_id " +
                "AND cc.teacher_id = ? " +
                "AND CURDATE() <= cle.exam_date";

        try{

            int count = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTotalStudentsEnrolledInClearanceExamsForTeacher(long teacherId) {
        String sql = "SELECT COUNT(*) " +
                "FROM clearance_exam_enrolment clee " +
                "LEFT JOIN grade_items gi ON  gi.grade_item_id = clee.parent_exam_id " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "WHERE clee.is_enrolled = 1 " +
                "AND cc.teacher_id = ? ";

        try{

            int count = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<GradeItems> adminGetExamsTableData() {
        String sql = "   SELECT  " +
                "   gi.grade_item_id, " +
                "   gi.grade_item_name, " +
                "    gi.created_by, " +
                "    gi.active_status, " +
                "    crs.course_id, " +
                "    crs.course_short_name, " +
                "    crs.course_name, " +
                "    DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "    DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "    (SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents " +
                "    FROM grade_items gi,courses crs " +
                "    WHERE gi.grade_item_type = 'Exam' " +
                "    AND gi.date_of_grade_item IS NOT NULL  " +
                "    AND gi.course_id = crs.course_id " +
                "    ORDER BY gi.created_at DESC ";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCreatedBy(resultSet.getLong("created_by"));
                            exam.setActiveStatus(resultSet.getInt("active_status"));
                            exam.setCourseId(resultSet.getInt("course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("course_short_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> adminGetCourseExamDataList() {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents  " +
                "FROM grade_items gi " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pcm.major_id = m.major_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "ORDER BY s.semester_code DESC";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("parent_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("child_course_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            exam.setChildCourseId(resultSet.getInt("child_course_id"));
                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorShortName(resultSet.getString("major_short_code"));
                            exam.setMinPassScore(resultSet.getInt("min_pass_score"));
                            exam.setSemesterId(resultSet.getInt("semester_id"));
                            exam.setSemesterCode(resultSet.getString("semester_code"));


                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> adminGetCourseExamDataListByMajorId(int majorId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents  " +
                "FROM grade_items gi " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pcm.major_id = m.major_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND m.major_id =  ? " +
                "ORDER BY s.semester_code DESC";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("parent_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("child_course_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            exam.setChildCourseId(resultSet.getInt("child_course_id"));
                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorShortName(resultSet.getString("major_short_code"));
                            exam.setMinPassScore(resultSet.getInt("min_pass_score"));
                            exam.setSemesterId(resultSet.getInt("semester_id"));
                            exam.setSemesterCode(resultSet.getString("semester_code"));


                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> adminGetCourseExamDataListBySemester(int semesterId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents  " +
                "FROM grade_items gi " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pcm.major_id = m.major_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND s.semester_id = ? " +
                "ORDER BY s.semester_code DESC";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("parent_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("child_course_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            exam.setChildCourseId(resultSet.getInt("child_course_id"));
                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorShortName(resultSet.getString("major_short_code"));
                            exam.setMinPassScore(resultSet.getInt("min_pass_score"));
                            exam.setSemesterId(resultSet.getInt("semester_id"));
                            exam.setSemesterCode(resultSet.getString("semester_code"));


                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> adminGetCourseExamDataListBySemesterAndMajorId(int semesterId, int majorId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents  " +
                "FROM grade_items gi " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pcm.major_id = m.major_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND s.semester_id = ? " +
                "AND m.major_id =  ? " +
                "ORDER BY s.semester_code DESC";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId,majorId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("parent_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("child_course_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            exam.setChildCourseId(resultSet.getInt("child_course_id"));
                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorShortName(resultSet.getString("major_short_code"));
                            exam.setMinPassScore(resultSet.getInt("min_pass_score"));
                            exam.setSemesterId(resultSet.getInt("semester_id"));
                            exam.setSemesterCode(resultSet.getString("semester_code"));


                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> teacherGetCourseExamDataList(long teacherId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents  " +
                "FROM grade_items gi " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pcm.major_id = m.major_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND cc.teacher_id = ? " +
                "ORDER BY s.semester_code DESC";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("parent_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("child_course_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            exam.setChildCourseId(resultSet.getInt("child_course_id"));
                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorShortName(resultSet.getString("major_short_code"));
                            exam.setMinPassScore(resultSet.getInt("min_pass_score"));
                            exam.setSemesterId(resultSet.getInt("semester_id"));
                            exam.setSemesterCode(resultSet.getString("semester_code"));


                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> teacherGetCourseExamDataListByMajorId(int majorId, long teacherId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents  " +
                "FROM grade_items gi " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pcm.major_id = m.major_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND m.major_id =  ? " +
                "AND cc.teacher_id = ? " +
                "ORDER BY s.semester_code DESC";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId,teacherId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("parent_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("child_course_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            exam.setChildCourseId(resultSet.getInt("child_course_id"));
                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorShortName(resultSet.getString("major_short_code"));
                            exam.setMinPassScore(resultSet.getInt("min_pass_score"));
                            exam.setSemesterId(resultSet.getInt("semester_id"));
                            exam.setSemesterCode(resultSet.getString("semester_code"));


                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> teacherGetCourseExamDataListBySemester(int semesterId, long teacherId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents  " +
                "FROM grade_items gi " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pcm.major_id = m.major_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND s.semester_id = ? " +
                "AND cc.teacher_id = ? " +
                "ORDER BY s.semester_code DESC";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId,teacherId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("parent_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("child_course_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            exam.setChildCourseId(resultSet.getInt("child_course_id"));
                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorShortName(resultSet.getString("major_short_code"));
                            exam.setMinPassScore(resultSet.getInt("min_pass_score"));
                            exam.setSemesterId(resultSet.getInt("semester_id"));
                            exam.setSemesterCode(resultSet.getString("semester_code"));


                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> teacherGetCourseExamDataListBySemesterAndMajorId(int semesterId, int majorId, long teacherId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents  " +
                "FROM grade_items gi " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pcm.major_id = m.major_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND s.semester_id = ? " +
                "AND m.major_id =  ? " +
                "AND cc.teacher_id = ? " +
                "ORDER BY s.semester_code DESC";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId,majorId, teacherId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("parent_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("child_course_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            exam.setChildCourseId(resultSet.getInt("child_course_id"));
                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorShortName(resultSet.getString("major_short_code"));
                            exam.setMinPassScore(resultSet.getInt("min_pass_score"));
                            exam.setSemesterId(resultSet.getInt("semester_id"));
                            exam.setSemesterCode(resultSet.getString("semester_code"));


                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> adminGetExamsTableDataUpcomingExams() {
        String sql = "   SELECT  " +
                "   gi.grade_item_id, " +
                "   gi.grade_item_name, " +
                "    gi.created_by, " +
                "    gi.active_status, " +
                "    crs.course_id, " +
                "    crs.course_short_name, " +
                "    crs.course_name, " +
                "    DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "    DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "    (SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents " +
                "    FROM grade_items gi,courses crs " +
                "    WHERE gi.grade_item_type = 'Exam' " +
                "    AND gi.date_of_grade_item IS NOT NULL  " +
                "    AND gi.course_id = crs.course_id " +
                "   AND CURDATE()<= DATE(gi.date_of_grade_item) " +
                "    ORDER BY gi.created_at DESC ";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCreatedBy(resultSet.getLong("created_by"));
                            exam.setActiveStatus(resultSet.getInt("active_status"));
                            exam.setCourseId(resultSet.getInt("course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("course_short_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> adminGetExamsTableDataPastExams() {
        String sql = "   SELECT  " +
                "   gi.grade_item_id, " +
                "   gi.grade_item_name, " +
                "    gi.created_by, " +
                "    gi.active_status, " +
                "    crs.course_id, " +
                "    crs.course_short_name, " +
                "    crs.course_name, " +
                "    DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "    DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "    (SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents " +
                "    FROM grade_items gi,courses crs " +
                "    WHERE gi.grade_item_type = 'Exam' " +
                "    AND gi.date_of_grade_item IS NOT NULL  " +
                "    AND gi.course_id = crs.course_id " +
                "   AND CURDATE()>= DATE(gi.date_of_grade_item)  " +
                "    ORDER BY gi.created_at DESC ";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCreatedBy(resultSet.getLong("created_by"));
                            exam.setActiveStatus(resultSet.getInt("active_status"));
                            exam.setCourseId(resultSet.getInt("course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("course_short_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> adminGetExamsTableDataByTeacherId(long teacherId) {

        String sql = "   SELECT  " +
                "   gi.grade_item_id, " +
                "   gi.grade_item_name, " +
                "    gi.created_by, " +
                "    gi.active_status, " +
                "    crs.course_id, " +
                "    crs.course_short_name, " +
                "    crs.course_name, " +
                "    DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "    DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "    (SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents " +
                "    FROM grade_items gi,courses crs " +
                "    WHERE gi.grade_item_type = 'Exam' " +
                "    AND gi.date_of_grade_item IS NOT NULL  " +
                "    AND gi.course_id = crs.course_id " +
                "   AND crs.teacher_id = ? " +
                "    ORDER BY gi.created_at DESC ";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCreatedBy(resultSet.getLong("created_by"));
                            exam.setActiveStatus(resultSet.getInt("active_status"));
                            exam.setCourseId(resultSet.getInt("course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("course_short_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> teacherGetExamsTableDataUpcomingExams(long teacherId) {
        String sql = "   SELECT  " +
                "   gi.grade_item_id, " +
                "   gi.grade_item_name, " +
                "    gi.created_by, " +
                "    gi.active_status, " +
                "    crs.course_id, " +
                "    crs.course_short_name, " +
                "    crs.course_name, " +
                "    DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "    DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "    (SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents " +
                "    FROM grade_items gi,courses crs " +
                "    WHERE gi.grade_item_type = 'Exam' " +
                "    AND gi.date_of_grade_item IS NOT NULL  " +
                "    AND gi.course_id = crs.course_id " +
                "    AND crs.teacher_id = ?  " +
                "    AND CURDATE()<= DATE(gi.date_of_grade_item) " +
                "    ORDER BY gi.created_at DESC ";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCreatedBy(resultSet.getLong("created_by"));
                            exam.setActiveStatus(resultSet.getInt("active_status"));
                            exam.setCourseId(resultSet.getInt("course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("course_short_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> teacherGetExamsTableDataPastExams(long teacherId) {
        String sql = "   SELECT  " +
                "   gi.grade_item_id, " +
                "   gi.grade_item_name, " +
                "    gi.created_by, " +
                "    gi.active_status, " +
                "    crs.course_id, " +
                "    crs.course_short_name, " +
                "    crs.course_name, " +
                "    DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "    DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "    (SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = gi.grade_item_id)EnrolledStudents " +
                "    FROM grade_items gi,courses crs " +
                "    WHERE gi.grade_item_type = 'Exam' " +
                "    AND gi.date_of_grade_item IS NOT NULL  " +
                "    AND gi.course_id = crs.course_id " +
                "    AND crs.teacher_id = ?  " +
                "    AND CURDATE()>= DATE(gi.date_of_grade_item) " +
                "    ORDER BY gi.created_at DESC ";
        try{

            List<GradeItems> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCreatedBy(resultSet.getLong("created_by"));
                            exam.setActiveStatus(resultSet.getInt("active_status"));
                            exam.setCourseId(resultSet.getInt("course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("course_short_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("EnrolledStudents"));

                            return exam;
                        }
                    }

            );
            return getExamList;

        }
        catch(EmptyResultDataAccessException e){
            return  null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getActiveTeacherCourseExamCount(long teacherId) {
        String sql = "SELECT COUNT(*) " +
                "FROM grade_items gi  " +
                "LEFT JOIN courses c ON c.course_id = gi.course_id  " +
                "WHERE gi.grade_item_type = 'Exam'  " +
                "AND gi.active_status= 1  " +
                "AND CURDATE()<= DATE(gi.date_of_grade_item) " +
                "AND c.teacher_id =  ?";

        try{

            int count = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }


    }

    @Override
    public int getTotalTeacherCourseExamCount(long teacherId) {
        String sql = "SELECT COUNT(*) " +
                "FROM grade_items gi " +
                "LEFT JOIN courses c ON c.course_id = gi.course_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND c.teacher_id =  ?";

        try{

            int count = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public List<GradeItems> adminGetExamEnrolmentRequestByCourses() {
        String sql = "    SELECT " +
                "crs.course_name , " +
                "crs.course_id , " +
                "COUNT(esr.course_id) AS examEnrolmentRequests " +
                "    FROM exam_student_request_enrolment esr,courses crs,grade_items gi " +
                "    WHERE esr.course_id = crs.course_id  " +
                "    AND gi.grade_item_id = esr.exam_id " +
                "    AND gi.grade_item_type = 'Exam' " +
                "    AND esr.enrolment_status = 0 " +
                "    GROUP BY esr.course_id;";

        try{

            List<GradeItems> examEnrolmentRequestByCourseList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setCourseId(resultSet.getInt("course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setTotalEnrollmentRequests(resultSet.getInt("examEnrolmentRequests"));

                            return exam;
                        }
                    }

            );
            return examEnrolmentRequestByCourseList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> adminGetExamEnrolmentRequestByGradeItem() {
        String sql = "  SELECT  " +
                " gi.course_id, " +
                " gi.grade_item_id, " +
                " gi.grade_item_name, " +
                " COUNT(ere.student_id) AS number_of_student_requests " +
                "  FROM exam_student_request_enrolment ere,grade_items gi " +
                "  WHERE  " +
                "  gi.grade_item_type = 'Exam' " +
                "  AND ere.exam_id = gi.grade_item_id " +
                "  AND ere.enrolment_status = 0 " +
                "  GROUP BY ere.exam_id ";

        try{

            List<GradeItems> examEnrolmentRequestByGradeItem = getJdbcTemplate().query(
                    sql,
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("course_id"));
                            exam.setTotalEnrollmentRequests(resultSet.getInt("number_of_student_requests"));

                            return exam;
                        }
                    }

            );
            return examEnrolmentRequestByGradeItem;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> teacherGetExamEnrolmentRequestByGradeItem(long teacherId) {

        String sql = "  SELECT  " +
                " gi.course_id, " +
                " gi.grade_item_id, " +
                " gi.grade_item_name, " +
                " COUNT(ere.student_id) AS number_of_student_requests " +
                "  FROM exam_student_request_enrolment ere,grade_items gi ,courses c" +
                "  WHERE  " +
                "  gi.grade_item_type = 'Exam' " +
                "  AND ere.exam_id = gi.grade_item_id " +
                "  AND ere.enrolment_status = 0 " +
                "  AND c.course_id = gi.course_id  " +
                "  AND c.teacher_id =  ? " +
                "  GROUP BY ere.exam_id ";

        try{

            List<GradeItems> examEnrolmentRequestByGradeItem = getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("course_id"));
                            exam.setTotalEnrollmentRequests(resultSet.getInt("number_of_student_requests"));

                            return exam;
                        }
                    }

            );
            return examEnrolmentRequestByGradeItem;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getActiveExamsCountForTeacher(long teacherId) {

        String sql ="SELECT COUNT(*) " +
                "FROM grade_items gi " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND  CURDATE() <= DATE(gi.date_of_grade_item) " +
                "AND cc.teacher_id =  ? ";

        try{

            int count = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTotalExamEnrollmentsForTeacher(long teacherId) {
        String sql = "SELECT COUNT(*) " +
                "FROM exam_enrolment ee " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ee.course_id " +
                "WHERE ee.is_Enrolled = 1 " +
                "AND ee.has_unEnrolled = 0 " +
                "AND cc.teacher_id =  ? ";

        try{

            int count = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<GradeItems> getCoursesWithExamsFilterList() {
        String sql = "     SELECT  " +
                "    gi.course_id, " +
                "    cr.course_name " +
                "    FROM grade_items gi , courses cr " +
                "    WHERE gi.course_id = cr.course_id " +
                "    AND gi.grade_item_type = 'Exam' " +
                "    AND gi.active_status = 1 " +
                "    GROUP BY cr.course_id ";

        try{

            List<GradeItems> courseFilterList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setCourseId(resultSet.getInt("course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));

                            return exam;
                        }
                    }

            );
            return courseFilterList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeItems> getActiveCourseListForFormSelect() {
        String sql = "    SELECT  " +
                " cr.parent_course_id, " +
                " cr.course_name, " +
                " cr.course_short_name " +
                "    FROM  parent_courses cr " +
                "    WHERE cr.status= 'active'";

        try{

            List<GradeItems> activeCoursesList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setCourseId(resultSet.getInt("parent_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("course_short_name"));

                            return exam;
                        }
                    }

            );
            return activeCoursesList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GradeItems getActiveCourseListForFormSelect( int parentCourseId ) {
        String sql = "    SELECT  " +
                " pc.parent_course_id, " +
                " pc.course_name, " +
                " pc.course_short_name, " +
                " m.major_id, " +
                " m.major_name " +
                " FROM  parent_courses pc, major m " +
                " WHERE pc.major_id = m.major_id " +
                "AND  pc.status= 'active' AND  pc.parent_course_id = ? ";

        try{

           GradeItems activeCoursesList = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{parentCourseId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setParentCourseId(resultSet.getLong("parent_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("course_short_name"));
                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));

                            return exam;
                        }
                    }

            );
            return activeCoursesList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<GradeItems> getGradeItemNamesByCourseId(int courseId) {
        String sql = "SELECT  " +
                " gi.grade_item_id, " +
                " gi.grade_item_name " +
                " FROM grade_items gi " +
                " WHERE gi.course_id =  ?";


        try{

            List<GradeItems> gradeItemsList = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItems = new GradeItems();

                            gradeItems.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItems.setGradeItemName(resultSet.getString("grade_item_name"));

                            return  gradeItems;
                        }
                    }

            );
            return gradeItemsList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeItemsDaoImpl::@getGradeItemNamesByCourseId::Error");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::@getGradeItemNamesByCourseId::Error");
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public GradeItems getSingleExamFullDetailsByExamId(int examId) {
        String sql = "SELECT " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "DATE_FORMAT(gi.date_of_grade_item, '%D %M %Y') examDate, " +
                "pc.parent_course_id, " +
                "cc.child_course_id, " +
                "cc.teacher_id, " +
                "u.user_name,  " +
                "pc.course_name, " +
                "cc.child_course_name, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM  exam_enrolment WHERE gi.grade_item_id = exam_enrolment.exam_id AND is_enrolled = 1 ) " +
                "totalEnrollments,  " +
                "( SELECT COUNT(*) FROM  exam_student_request_enrolment WHERE gi.grade_item_id = exam_student_request_enrolment.exam_id AND enrolment_status = 0 ) " +
                "totalEnrollmentRequests  " +
                "FROM  grade_items gi  " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "LEFT JOIN USER u ON u.user_id = cc.teacher_id " +
                "WHERE  " +
                "gi.grade_item_type = 'exam'  " +
                "AND gi.grade_item_id = ?";

        try{

            GradeItems gradeItem = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setTeacherId(resultSet.getLong("teacher_id"));
                            exam.setTeacherName(resultSet.getString("user_name"));
                            exam.setCourseId(resultSet.getInt("parent_course_id"));
                            exam.setChildCourseId(resultSet.getInt("child_course_id"));
                            exam.setCourseShortName(resultSet.getString("child_course_name"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setTotalEnrollments(resultSet.getInt("totalEnrollments"));
                            exam.setTotalEnrollmentRequests(resultSet.getInt("totalEnrollmentRequests"));
                            exam.setSemesterId(resultSet.getInt("semester_id"));
                            exam.setSemesterCode(resultSet.getString("semester_code"));

                            return exam;
                        }
                    }
            );
            return gradeItem;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeItemsDaoImpl::getSingleExamFullDetailsByExamId::EMPTY !");
            return null;
        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::getSingleExamFullDetailsByExamId::ERROR!");
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public List<GradeItems> getLastAddedExamList() {
        String sql = "SELECT  " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "gi.course_id, " +
                "c.course_name, " +
                "c.course_short_name " +
                "FROM grade_items gi " +
                "LEFT JOIN courses c ON c.course_id = gi.course_id  " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "ORDER BY gi.created_at DESC  " +
                "LIMIT 5";

        try{

            List<GradeItems> gradeItemsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("course_short_name"));

                            return exam;
                        }
                    }
            );
            return gradeItemsList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeItemsDaoImpl::getLastAddedExamList::EMPTY !");
            return null;
        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::getLastAddedExamList::ERROR!");
            e.printStackTrace();
            return  null;
        }


    }

    @Override
    public List<GradeItems> getLastEditedExamList() {
        String sql = " SELECT  " +
                " gi.grade_item_id, " +
                " gi.grade_item_name, " +
                " gi.course_id, " +
                "c.course_name, " +
                "c.course_short_name  " +
                "FROM grade_items gi " +
                "LEFT JOIN courses c ON c.course_id = gi.course_id " +
                "WHERE gi.grade_item_type = 'Exam'" +
                "ORDER BY gi.updated_at DESC  " +
                "LIMIT 5";

        try{

            List<GradeItems> gradeItemsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseShortName(resultSet.getString("course_short_name"));

                            return exam;
                        }
                    }
            );
            return gradeItemsList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeItemsDaoImpl::getLastEditedExamList::EMPTY !");
            return null;
        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::getLastEditedExamList::ERROR!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<GradeItems> getExamListByExamName(String examName) {
        String sql = "SELECT  " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "gi.course_id " +
                "FROM grade_items gi  " +
                "WHERE gi.grade_item_name =  ? " +
                "AND gi.course_id NOT IN (38,39,40,41,42,43,44,45,46,47,48,49,50);";

        try{

            List<GradeItems> gradeItemsList = getJdbcTemplate().query(
                    sql,
                    new Object[]{examName},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setCourseId(resultSet.getInt("course_id"));

                            return exam;
                        }
                    }
            );
            return gradeItemsList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeItemsDaoImpl::getLastEditedExamList::EMPTY !");
            return null;
        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::getLastEditedExamList::ERROR!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public String deActivateExam(int examId) {
        String sql = "UPDATE grade_items " +
                "SET  active_status = 0  " +
                "WHERE grade_item_id =  ? ;";


        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{examId}
            );
            if(1 == returnValue)
                return "QUERY SUCESS MESSAGE ";
            else
                return "QUERY FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::deActivateExam()::ERROR!!!");
            e.printStackTrace();
            return "@GradeItemsDaoImpl::deActivateExam()::ERROR!!!";
        }


    }

    @Override
    public String editExam(GradeItems gradeItems) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp updateTimestamp = new java.sql.Timestamp(date.getTime());

        String sql = "UPDATE grade_items " +
                "SET  date_of_grade_item = ? , " +
                "enrolment_start_date =  ? , " +
                "enrolment_close_date =  ?, " +
                "grade_item_name = ? , " +
                "min_grade = ? , " +
                "max_grade = ?, " +
                "weight = ? ," +
                "updated_at = ? " +
                "WHERE grade_item_id = ?;";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{gradeItems.getDateOfExam(),gradeItems.getEnrolmentStartDate(),gradeItems.getEnrolmentCloseDate(),gradeItems.getGradeItemName(), gradeItems.getMinGrade(), gradeItems.getMaxGrade(), gradeItems.getWeight(),updateTimestamp,gradeItems.getGradeItemId()}
            );
            if(1 == returnValue){
                System.out.println("@::200::EXAM UPDATE SUCCESS");
                return "200";
            }else{
                System.out.println("@::400::EXAM UPDATE QUERY FAILURE ");
                return "400";
            }


        }catch (Exception e){
            System.out.println("@::500::QUERY NOT EXECUTED ENCHANTED SYSTEM ERROR!");
            e.printStackTrace();
            return "400";
        }
    }

    @Override
    public GradeItems getExamDataForFormDisplayByExamId(int examId) {

        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "gi.grade_item_id,  " +
                "gi.grade_item_name, " +
                "gi.min_grade, " +
                "gi.max_grade, " +
                "gi.weight, " +
                "gi.date_of_grade_item,  " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "cc.child_course_id,  " +
                "cc.child_course_name,  " +
                "gi.enrolment_close_date,  " +
                "gi.enrolment_start_date , " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM  grade_items gi  " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN major m ON m.major_id = pcm.major_id " +
                "WHERE  " +
                "gi.grade_item_type = 'exam'  " +
                "AND gi.grade_item_id =  ? ";

        try{

            GradeItems gradeItem = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems exam = new GradeItems();

                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorShortName(resultSet.getString("major_short_code"));
                            exam.setGradeItemId(resultSet.getInt("grade_item_id"));
                            exam.setGradeItemName(resultSet.getString("grade_item_name"));
                            exam.setParentCourseId(resultSet.getLong("parent_course_id"));
                            exam.setChildCourseId(resultSet.getInt("child_course_id"));
                            exam.setCourseName(resultSet.getString("course_name"));
                            exam.setCourseId(resultSet.getInt("child_course_id"));
                            exam.setCourseShortName(resultSet.getString("child_course_name"));
                            exam.setDateOfExam(resultSet.getString("date_of_grade_item"));
                            exam.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));
                            exam.setEnrolmentStartDate(resultSet.getString("enrolment_start_date"));
                            exam.setSemesterId(resultSet.getInt("semester_id"));
                            exam.setSemesterCode(resultSet.getString("semester_code"));
                            exam.setMinGrade(resultSet.getInt("min_grade"));
                            exam.setMaxGrade(resultSet.getInt("max_grade"));
                            exam.setWeight(resultSet.getInt("weight"));

                            return exam;
                        }
                    }
            );
            return gradeItem;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeItemsDaoImpl::getExamDataForFormDisplayByExamId::EMPTY !");
            return null;
        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::getExamDataForFormDisplayByExamId::ERROR!");
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public List<GradeItems> getCourseGradeItemsByCourseId(int courseId) {
        String  sql = "SELECT " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "gi.course_id, " +
                "c.course_name, " +
                "c.course_short_name, " +
                "gi.weight, " +
                "gi.min_grade, " +
                "gi.max_grade, " +
                "gi.created_by, " +
                "gi.date_of_grade_item, " +
                "gi.enrolment_start_date, " +
                "gi.enrolment_close_date, " +
                "gi.active_status, " +
                "gi.semester " +
                "FROM grade_items gi " +
                "LEFT JOIN courses c ON c.course_id = gi.course_id " +
                "WHERE gi.course_id =  ?";

        try{

           List<GradeItems> gradeItemList = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();

                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setGradeItemName(resultSet.getString("grade_item_name"));
                            gradeItem.setCourseId(resultSet.getInt("course_id"));
                            gradeItem.setCourseName(resultSet.getString("course_name"));
                            gradeItem.setCourseName(resultSet.getString("course_short_name"));
                            gradeItem.setWeight(resultSet.getDouble("weight"));
                            gradeItem.setMinGrade(resultSet.getInt("min_grade"));
                            gradeItem.setMaxGrade(resultSet.getInt("max_grade"));
                            gradeItem.setCreatedBy(resultSet.getLong("created_by"));
                            gradeItem.setDateOfExam(resultSet.getString("date_of_grade_item"));
                            gradeItem.setEnrolmentStartDate(resultSet.getString("enrolment_start_date"));
                            gradeItem.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));
                            gradeItem.setActiveStatus(resultSet.getInt("active_status"));
                            gradeItem.setSemesterId(resultSet.getInt("semester"));

                            return gradeItem;
                        }
                    }
            );
            return gradeItemList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeItemsDaoImpl::getCourseGradeItemsByCourseId::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::getCourseGradeItemsByCourseId::ERROR !");
            e.printStackTrace();
            return  null;
        }


    }

    @Override
    public GradeItems getSingleGradeItemDetailByGradeItemId(int gradeItemId) {
        String  sql = "SELECT " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "gi.grade_item_type , " +
                "gi.course_id, " +
                "c.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "gi.weight, " +
                "gi.min_grade, " +
                "gi.max_grade, " +
                "gi.created_by, " +
                "gi.date_of_grade_item, " +
                "gi.enrolment_start_date, " +
                "gi.enrolment_close_date, " +
                "gi.active_status, " +
                "gi.semester " +
                "FROM grade_items gi " +
                "LEFT JOIN child_courses c ON c.child_course_id = gi.course_id " +
                "LEFT JOIN parent_courses pc ON c.parent_course_id = pc.parent_course_id " +
                "WHERE gi.grade_item_id = ?";

        try{

            GradeItems gradeItem = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{gradeItemId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();

                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setGradeItemName(resultSet.getString("grade_item_name"));
                            gradeItem.setGradeItemType(resultSet.getString("grade_item_type"));
                            gradeItem.setCourseId(resultSet.getInt("course_id"));
                            gradeItem.setCourseName(resultSet.getString("course_name"));
                            gradeItem.setCourseName(resultSet.getString("course_short_name"));
                            gradeItem.setWeight(resultSet.getDouble("weight"));
                            gradeItem.setMinGrade(resultSet.getInt("min_grade"));
                            gradeItem.setMaxGrade(resultSet.getInt("max_grade"));
                            gradeItem.setCreatedBy(resultSet.getLong("created_by"));
                            gradeItem.setDateOfExam(resultSet.getString("date_of_grade_item"));
                            gradeItem.setEnrolmentStartDate(resultSet.getString("enrolment_start_date"));
                            gradeItem.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));
                            gradeItem.setActiveStatus(resultSet.getInt("active_status"));
                            gradeItem.setSemesterId(resultSet.getInt("semester"));

                            return gradeItem;
                        }
                    }
            );
            return gradeItem;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeItemsDaoImpl::getSingleGradeItemDetailByGradeItemId::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::getSingleGradeItemDetailByGradeItemId::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public GradeItems getChildCourseExamDataForGradeAddition(int childCourseId, String childCourseExamName) {
        String  sql = "SELECT " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name " +
                "FROM " +
                "child_courses cc " +
                "LEFT JOIN grade_items gi ON gi.course_id = cc.child_course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN major m ON m.major_id = pcm.major_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE cc.child_course_id = ? AND gi.grade_item_name = ?  " +
                "ORDER BY pc.parent_course_id";

        try{

            GradeItems gradeItem = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{childCourseId,childCourseExamName},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();

                            gradeItem.setParentCourseId(resultSet.getLong("parent_course_id"));
                            gradeItem.setChildCourseId(resultSet.getInt("child_course_id"));
                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setGradeItemName(resultSet.getString("grade_item_name"));
                            gradeItem.setCourseName(resultSet.getString("course_name"));
                            gradeItem.setCourseName(resultSet.getString("child_course_name"));

                            return gradeItem;
                        }
                    }
            );
            return gradeItem;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeItemsDaoImpl::getSingleGradeItemDetailByGradeItemId::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::getSingleGradeItemDetailByGradeItemId::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public GradeItems getExamDates(int examid) {
        String  sql = "SELECT " +
                "gi.date_of_grade_item, " +
                "gi.enrolment_close_date  " +
                "FROM grade_items gi " +
                "WHERE gi.grade_item_type = 'Exam' " +
                "AND gi.grade_item_id = ? ";
        try{

            GradeItems gradeItem = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examid},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();

                            gradeItem.setDateOfExam(resultSet.getString("date_of_grade_item"));
                            gradeItem.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));


                            return gradeItem;
                        }
                    }
            );
            return gradeItem;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeItemsDaoImpl::getSingleGradeItemDetailByGradeItemId::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::getSingleGradeItemDetailByGradeItemId::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public String addNewGradeItemForCourse(GradeItems gradeItems) {
        String  sql = "INSERT INTO grade_items (course_id,grade_item_type,grade_item_name,weight,min_grade,max_grade,date_of_grade_item,created_by) " +
                      "VALUES (?,?,?,?,?,?,?,?);";
        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ gradeItems.getCourseId(),gradeItems.getGradeItemType(),gradeItems.getGradeItemName(),gradeItems.getWeight(),
                            gradeItems.getMinGrade(),gradeItems.getMaxGrade(),gradeItems.getDateOfExam(),gradeItems.getCreatedBy()}
            );
            if(1 == returnValue)
            {
                System.out.println("@200::GRADE ITEM CREATED SUCCESSFULLY ");
                return "200";
            }
            else
            {
                System.out.println("@400::GRADE ITEM FAILED TO BE CREATED  ");
                return "400";
            }

        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::@addNewGradeItemForCourse::ERROR!!!");
            e.printStackTrace();
            return "400";
        }

    }

    @Override
    public String editGradeItem(GradeItems gradeItems) {
        String  sql = "UPDATE grade_items " +
                "SET grade_item_type = ? ,grade_item_name= ?, weight= ?,min_grade = ?, max_grade = ?  " +
                "WHERE grade_item_id = ?";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{gradeItems.getGradeItemType() ,gradeItems.getGradeItemName(),gradeItems.getWeight(),gradeItems.getMinGrade(),gradeItems.getMaxGrade(),gradeItems.getGradeItemId() }
            );
            if(1 == returnValue)
                return "UPDATE SUCCESS MESSAGE ";
            else
                return "UPDATE FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::@editGradeItem::ERROR!!!");
            e.printStackTrace();
            return "@GradeItemsDaoImpl::@editGradeItem::ERROR!!!";
        }

    }

    @Override
    public String deleteGradeItem(int gradeItemId) {
        String  sql = "DELETE FROM grade_items " +
                "WHERE grade_item_id = ?";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ gradeItemId}
            );
            if(1 == returnValue)
                return "DELETE SUCESS MESSAGE ";
            else
                return "DELETE FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::@deleteGradeItem::ERROR!!!");
            e.printStackTrace();
            return "@GradeItemsDaoImpl::@deleteGradeItem::ERROR!!!";
        }



    }

    @Override
    public List<GradeItems> getGradeItemsWithWeightsByCourseId(int courseId) {
        String sql  = "    SELECT  " +
                "    gi.grade_item_id , " +
                "    gi.grade_item_name, " +
                "    gi.weight, " +
                "    gi.max_grade, " +
                "    gi.min_grade, " +
                "    gi.grade_item_type " +
                "    FROM grade_items gi " +
                "    WHERE gi.course_id =  ?;";

        try{

            List<GradeItems> gradeItemList  = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();
                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItem.setGradeItemName(resultSet.getString("grade_item_name"));
                            gradeItem.setWeight(resultSet.getDouble("weight"));
                            gradeItem.setMinGrade(resultSet.getInt("min_grade"));
                            gradeItem.setMaxGrade(resultSet.getInt("max_grade"));
                            gradeItem.setGradeItemType(resultSet.getString("grade_item_type"));
                            return gradeItem;
                        }
                    }
            );
            return gradeItemList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeItemsDaoImpl::getGradeItemsWithWeightsByCourseId::EMPTY !");
            return null;
        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::getGradeItemsWithWeightsByCourseId::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public Double getGradeItemWeightForCourse(int courseId) {
        String sql = "    SELECT  " +
                "    TRUNCATE(SUM(gi.weight), 1) " +
                "    FROM grade_items gi " +
                "    WHERE gi.course_id =  ? ;";

        try{
            Double weight = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseId},
                    Double.class
            );
            return weight;

        }
        catch(EmptyResultDataAccessException e){
            return 0.0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public boolean checkPermissionForTeacherAgainstExamId(long teacherId, int examId) {
        String sql = "SELECT gi.grade_item_id " +
                "FROM grade_items gi  " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id   " +
                "WHERE gi.grade_item_type = 'Exam'  " +
                "AND gi.grade_item_id =  ? " +
                "AND cc.teacher_id =  ? ";

        try{
            int val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examId,teacherId},
                    Integer.class
            );
            return true;

        }
        catch(EmptyResultDataAccessException e){
            return false;
        }catch (Exception e){
             e.printStackTrace();
            return  false;
        }
    }

    @Override
    public boolean checkIfExamHasGrades(int examId, long studentId) {
        String sql = "SELECT  " +
                "er.exam_score " +
                "FROM exam_enrolment er " +
                "WHERE er.exam_id = ? AND er.student_id = ? ";


        try{

            List<ExamEnrolment> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{examId, studentId},
                    new RowMapper<ExamEnrolment>() {
                        @Override
                        public ExamEnrolment mapRow(ResultSet resultSet, int i) throws SQLException {
                            ExamEnrolment grade = new ExamEnrolment();
                            grade.setExamScore(resultSet.getString("exam_score"));
                            return grade;
                        }
                    }

            );

            if(list.isEmpty()){
                return false;
            }else {
                return true;
            }


        }
        catch(EmptyResultDataAccessException e){
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return  true;
        }
    }

    @Override
    public String deleteExamFromExamEnrolment(int examId) {
        String sql = "DELETE FROM exam_enrolment WHERE exam_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{examId}
            );
            System.out.println("@ExamEnrolment::EXAM DELETED::1");
            return "EXAM DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteExamFromExamStudentRequestEnrolment(int examId) {
        String sql = "DELETE FROM exam_student_request_enrolment WHERE exam_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{examId}
            );
            System.out.println("@ExamStudentRequestEnrolment::EXAM DELETED::2");
            return "EXAM DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteExamFromGrade(int examId) {
        String sql = "DELETE FROM grade WHERE grade_item_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{examId}
            );
            System.out.println("@Grade::EXAM DELETED::3");
            return "EXAM DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteExamFromGradeItems(int examId) {
        String sql = "DELETE FROM grade_items WHERE grade_item_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{examId}
            );
            System.out.println("@GradeItems::EXAM DELETED::4");
            return "EXAM DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }
}

