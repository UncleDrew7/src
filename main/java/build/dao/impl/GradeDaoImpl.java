package build.dao.impl;

import build.model.ExamEnrolment;
import build.model.Grade;
import build.dao.GradeDao;

import build.model.GradeItems;
import build.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public class GradeDaoImpl extends JdbcDaoSupport implements GradeDao{
    @Override
    public String addGrade(Grade grade) {

        String sql = "INSERT INTO  grade(parent_course_id,course_id ,student_id,uploaded_by,grade_item_id,grade) VALUES( ?,?,?,?,?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{grade.getParentCourseId(), grade.getCourseId(),grade.getStudentId(),grade.getUploadedBy(),grade.getGradeItemId(),grade.getGrade() }
        );
        if(1 == returnValue)
            return "GRADES ADDED SUCCESSFULLY MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String addNumericGrade(Grade grade) {
        String sql = "INSERT INTO  grade(parent_course_id,course_id ,student_id,uploaded_by,grade_score_type,grade_item_id,grade) VALUES( ?,?,?,?,?,?,? )";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{grade.getParentCourseId(), grade.getCourseId(),grade.getStudentId(),grade.getUploadedBy(),grade.getGradeScoreType(),grade.getGradeItemId(),grade.getNumericGrade() }
            );
            if(returnValue == 1){
                System.out.println("GRADE ADDED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("GRADE ADD FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "INTERNAL SYSTEM QUERY ERROR!!";
        }
    }

    @Override
    public String addStringGrade(Grade grade) {
        String sql = "INSERT INTO  grade(parent_course_id,course_id ,student_id,uploaded_by,grade_score_type,grade_item_id,custom_grade) VALUES( ?,?,?,?,?,?,? )";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{grade.getParentCourseId(), grade.getCourseId(),grade.getStudentId(),grade.getUploadedBy(),grade.getGradeScoreType(),grade.getGradeItemId(),grade.getStringGrade() }
            );
            if(returnValue == 1){
                System.out.println("GRADE ADDED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("GRADE ADD FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "INTERNAL SYSTEM QUERY ERROR!!";
        }
    }

    @Override
    public String updateGrade(Grade grade) {
        String sql = "UPDATE  grade SET grade = ? WHERE grade_item_id =? AND student_id = ? ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{grade.getGrade(),grade.getGradeItemId(),grade.getStudentId()}
        );
        if(1 == returnValue)
            return "GRADE SUCCESSFULLY UPDATED  ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public List<Grade> getCourseSingleGradeItemGrades(int courseId, int gradeItemId) {
        String  sql = "SELECT DISTINCT " +
                "ce.student_id, " +
                "u.user_name, " +
                "(SELECT g.grade FROM grade g WHERE  g.student_id = ce.student_id AND g.grade_item_id = ?) " +
                "grade " +
                "FROM course_enrolment ce " +
                "LEFT JOIN user u ON u.user_id = ce.student_id " +
                "WHERE ce.course_id =  ?";

        try{
            List<Grade> gradeObject = getJdbcTemplate().query(
                    sql,
                    new Object[]{gradeItemId, courseId},
                    new RowMapper<Grade>() {
                        @Override
                        public Grade mapRow(ResultSet resultSet, int i) throws SQLException {
                            Grade grade = new Grade();

                            grade.setStudentId(resultSet.getLong("student_id"));
                            grade.setStudentName(resultSet.getString("user_name"));
                            grade.setGrade(resultSet.getInt("grade"));

                            return  grade;
                        }
                    }

            );
            return gradeObject;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeDaoImpl::getCourseSingleGradeItemGrades::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@GradeDaoImpl::getCourseSingleGradeItemGrades::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public double getGradeItemWeightTotalByCourseId(int courseId) {
        return 0;
    }

    @Override
    public int getCourseFinalScoreByCourseId(int courseId) {
        return 0;
    }

    @Override
    public List<Grade> getCourseOverViewByCourseId(int courseId) {
        return null;
    }

    @Override
    public List<Grade> getStudentCourseGradeOverviewByStudentId(long userId) {
        return null;
    }

    @Override
    public List<Grade> getstudentGradeItemReportByUserId(long userId) {
        return null;
    }

    @Override
    public List<Grade> getCourseStudentEnrollmentAddGradesList(int courseId, int gradeItemId) {
        String sql = "SELECT   " +
                "ce.student_id, " +
                "u.user_name, " +
                "gi.grade_item_id, " +
                "gi.grade_item_name, " +
                "(SELECT c.class_name FROM class c, student_class sc WHERE c.class_id = sc.class_id AND  sc.student_id = ce.student_id) " +
                "class, " +
                "(SELECT g.grade FROM grade g WHERE g.student_id = ce.student_id AND g.grade_item_id = gi.grade_item_id ) " +
                "grade " +
                "FROM course_enrolment ce " +
                "LEFT JOIN user u ON u.user_id = ce.student_id " +
                "LEFT JOIN grade_items gi ON gi.course_id = ce.course_id " +
                "WHERE u.status = 'active' " +
                "AND ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND  ce.course_id = ? " +
                "AND gi.grade_item_id = ? " +
                "ORDER BY u.user_name ";


        try{

            List<Grade> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId, gradeItemId},
                    new RowMapper<Grade>() {
                        @Override
                        public Grade mapRow(ResultSet resultSet, int i) throws SQLException {
                            Grade grade = new Grade();
                            grade.setStudentId(resultSet.getLong("student_id"));
                            grade.setStudentName(resultSet.getString("user_name"));
                            grade.setStudentClass(resultSet.getString("class"));
                            grade.setGrade(resultSet.getInt("grade"));
                            return grade;
                        }
                    }

            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeDaoImpl::@getCourseStudentEnrollmentAddGradesList::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@GradeDaoImpl::@getCourseStudentEnrollmentAddGradesList::EMPTY !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Grade> getExamStudentEnrollmentAddGradesList(int gradeItemId) {
        String sql = "SELECT   " +
                "u.user_name, " +
                "ee.student_id, " +
                "(SELECT c.class_name FROM class c, student_class sc WHERE c.class_id = sc.class_id AND  sc.student_id = ee.student_id) " +
                "class, " +
                "(SELECT g.grade FROM grade g WHERE g.student_id = ee.student_id AND g.grade_item_id = gi.grade_item_id ) " +
                "grade " +
                "FROM exam_enrolment ee  " +
                "LEFT JOIN user u ON ee.student_id = u.user_id  " +
                "LEFT JOIN grade_items gi ON  gi.grade_item_id = ee.exam_id  " +
                "WHERE  " +
                " ee.exam_id = ? " +
                "AND ee.is_Enrolled = 1 " +
                "AND ee.has_unEnrolled = 0 " +
                "AND u.status = 'active' " +
                "AND gi.grade_item_type = 'Exam' " +
                "ORDER BY u.user_name";


        try{

            List<Grade> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{gradeItemId},
                    new RowMapper<Grade>() {
                        @Override
                        public Grade mapRow(ResultSet resultSet, int i) throws SQLException {
                            Grade grade = new Grade();
                            grade.setStudentId(resultSet.getLong("student_id"));
                            grade.setStudentName(resultSet.getString("user_name"));
                            grade.setStudentClass(resultSet.getString("class"));
                            grade.setGrade(resultSet.getInt("grade"));
                            return grade;
                        }
                    }

            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeDaoImpl::@getExamStudentEnrollmentAddGradesList::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@GradeDaoImpl::@getExamStudentEnrollmentAddGradesList::ERROR !");
            e.printStackTrace();
            return  null;
        }

    }



    @Override
    public boolean checkIfStudentHasGradeForGradeItem(int gradeItemId, long userId) {
        String sql = "SELECT " +
                "g.grade_id " +
                "FROM grade g " +
                "WHERE g.student_id = ? " +
                "AND g.grade_item_id = ? ";

        try{
            int object = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId,gradeItemId},
                    Integer.class
            );

            return true;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeDaoImpl::@checkIfStudentHasGradeForGradeItem::EMPTY !");
//            e.printStackTrace();
            return false;
        }catch (Exception e){
            System.out.println("@GradeDaoImpl::@checkIfStudentHasGradeForGradeItem::ERROR !");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkIfStudentIsEnrolledInExam(int gradeItemId, long userId) {
        String sql  = "SELECT  " +
                "ee.id " +
                "FROM exam_enrolment ee " +
                "WHERE  " +
                "ee.is_Enrolled = 1  AND " +
                "ee.has_unEnrolled = 0 " +
                "AND ee.exam_id = ?  " +
                "AND ee.student_id =  ?";

        try{
            int object = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{gradeItemId,userId,},
                    Integer.class
            );

            return true;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeDaoImpl::@checkIfStudentIsEnrolledInExam::EMPTY !");
//            e.printStackTrace();
            return false;
        }catch (Exception e){
            System.out.println("@GradeDaoImpl::@checkIfStudentIsEnrolledInExam::ERROR !");
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean checkIfStudentIsEnrolledInCourse(int courseId, long userId) {
        String sql = "SELECT  " +
                "ce.id " +
                "FROM course_enrolment ce " +
                "WHERE ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND ce.course_id = ?  " +
                "AND ce.student_id = ? ";

        try{
            int object = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseId,userId,},
                    Integer.class
            );

            return true;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeDaoImpl::@checkIfStudentIsEnrolledInCourse::EMPTY !");
//            e.printStackTrace();
            return false;
        }catch (Exception e){
            System.out.println("@GradeDaoImpl::@checkIfStudentIsEnrolledInCourse::ERROR !");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<GradeItems> getGradeItemsFromCourseByCourseId(int courseId) {
        String sql = "SELECT  " +
                "gi.grade_item_id, " +
                "gi.course_id, " +
                "gi.weight, " +
                "gi.grade_item_type, " +
                "gi.grade_item_name, " +
                "gi.date_of_grade_item, " +
                "gi.active_status, " +
                "gi.created_at " +
                "FROM grade_items gi " +
                "WHERE gi.course_id = ? " +
                "ORDER BY gi.grade_item_name ";

        try{

            List<GradeItems> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItems = new GradeItems();

                            gradeItems.setCourseId(resultSet.getInt("course_id"));
                            gradeItems.setGradeItemId(resultSet.getInt("grade_item_id"));
                            gradeItems.setWeight(resultSet.getDouble("weight"));
                            gradeItems.setGradeItemType(resultSet.getString("grade_item_type"));
                            gradeItems.setGradeItemName(resultSet.getString("grade_item_name"));
                            gradeItems.setDateOfExam(resultSet.getString("date_of_grade_item"));
                            gradeItems.setActiveStatus(resultSet.getInt("active_status"));
                            gradeItems.setCreatedAt(resultSet.getDate("created_at"));

                            return  gradeItems;
                        }
                    }

            );
            return list;

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
    public List<Grade> getAllGradesForCourseGradeItemsByCourseId(int courseId) {
       String sql  = "SELECT  " +
               "g.grade_id, " +
               "g.course_id, " +
               "g.student_id, " +
               "g.grade_item_id, " +
               "g.grade " +
               "FROM grade g  " +
               "WHERE g.course_id =  ? ";


        try{

            List<Grade> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<Grade>() {
                        @Override
                        public Grade mapRow(ResultSet resultSet, int i) throws SQLException {
                            Grade grade = new Grade();
                            grade.setGradeId(resultSet.getInt("grade_id"));
                            grade.setCourseId(resultSet.getInt("course_id"));
                            grade.setGradeItemId(resultSet.getInt("grade_item_id"));
                            grade.setStudentId(resultSet.getLong("student_id"));
                            grade.setGrade(resultSet.getInt("grade"));
                            return grade;
                        }
                    }

            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeDaoImpl::@getAllGradesForCourseGradeItemsByCourseId::EMPTY !");
            return null;
        }catch (Exception e){
            System.out.println("@GradeDaoImpl::@getAllGradesForCourseGradeItemsByCourseId::ERROR !");
            e.printStackTrace();
            return  null;
        }



    }

    @Override
    public int getCourseScoreComputedByHighestExamGradeforStudent(long studentId, long parentCourseId, int childCourseId) {
        //#grade items is based on ur child course
        //#exam is based on the parent course
        String sql = "SELECT  " +
                "IFNULL((SUM((g.grade/gi.max_grade)*gi.weight*100)DIV 1),0) + " +
                "IFNULL((SELECT  " +
                "MAX((ge.grade/gie.max_grade)*gie.weight*100) " +
                "FROM grade ge " +
                "LEFT JOIN grade_items gie ON gie.grade_item_id = ge.grade_item_id " +
                "WHERE gie.grade_item_type = 'Exam' " +
                "AND ge.parent_course_id =  ? " +
                "AND ge.student_id =  ?),0) " +
                "FROM grade g " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = g.grade_item_id " +
                "WHERE gi.grade_item_type != 'Exam' " +
                "AND g.course_id =  ?  " +
                "AND g.student_id =  ? ";

        try{

            int courseGradeItemOverallTotal = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{parentCourseId,studentId,childCourseId,studentId},
                    Integer.class
            );
            return courseGradeItemOverallTotal;

        } catch (EmptyResultDataAccessException e) {
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getCourseScoreComputedByLatestWrittenExamGrade(long studentId, long parentCourseId, int childCourseId) {
        String sql = "SELECT  " +
                "IFNULL((SUM((g.grade/gi.max_grade)*gi.weight*100)DIV 1),0) + " +
                "IFNULL((SELECT  " +
                "((ge.grade/gie.max_grade)*gie.weight*100) " +
                "FROM grade ge " +
                "LEFT JOIN grade_items gie ON gie.grade_item_id = ge.grade_item_id " +
                "WHERE gie.grade_item_type = 'Exam' " +
                "AND ge.parent_course_id = ?  " +
                "AND ge.student_id =  ? " +
                "AND gie.date_of_grade_item = (SELECT " +
                "MAX(giee.date_of_grade_item) " +
                "FROM exam_enrolment eee " +
                "LEFT JOIN grade_items giee ON giee.grade_item_id = eee.exam_id " +
                "WHERE eee.student_id = ge.student_id " +
                "AND giee.parent_course_id = ge.parent_course_id)),0) " +
                "FROM grade g " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = g.grade_item_id " +
                "WHERE gi.grade_item_type != 'Exam' " +
                "AND g.course_id = ? " +
                "AND g.student_id =  ?";

        try{

            int courseGradeItemOverallTotal = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{parentCourseId,studentId,childCourseId,studentId},
                    Integer.class
            );
            return courseGradeItemOverallTotal;

        } catch (EmptyResultDataAccessException e) {
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public double getStudentExamWithHighestGrade(long studentId, long parentCourseId) {
        String sql = "SELECT   " +
                "IFNULL(((gt.grade/git.max_grade)*git.weight*100 DIV 1),0) " +
                "FROM grade gt " +
                "LEFT JOIN grade_items git ON git.grade_item_id = gt.grade_item_id " +
                "WHERE gt.student_id = ?  " +
                "AND git.grade_item_type = 'Exam' " +
                "AND gt.parent_course_id =  ?  " +
                "AND gt.grade = (SELECT  " +
                "MAX(gtt.grade) " +
                "FROM grade gtt " +
                "WHERE gtt.student_id = gt.student_id " +
                "AND gtt.parent_course_id = gt.parent_course_id) " +
                "GROUP BY gt.parent_course_id  ";

        try{

            int courseGradeItemOverallTotal = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{studentId,parentCourseId},
                    Integer.class
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
    public double getStudentsTotalScoreForAllChildCourseGradeItems(long studentId, int childCourseId) {
        String sql = "SELECT   " +
                "IFNULL((SUM((g.grade/gi.max_grade)*gi.weight*100)DIV 1),0) " +
                "FROM grade g  " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = g.grade_item_id  " +
                "WHERE gi.grade_item_type != 'Exam'  " +
                "AND g.course_id =  ?  " +
                "AND g.student_id =   ?  ";

        try{

            int courseGradeItemOverallTotal = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{childCourseId,studentId},
                    Integer.class
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
    public List<User> getClearanceExamStudentGradeList(int clearanceExamId) {
        String sql = "SELECT  " +
                "u.user_id,  " +
                "u.user_name,  " +
                "c.class_id,  " +
                "c.class_name,  " +
                "gc.grade " +
                "FROM grade_custom gc " +
                "LEFT JOIN USER u ON gc.student_id = u.user_id  " +
                "LEFT JOIN student_class sc ON u.user_id = sc.student_id   " +
                "LEFT JOIN class c ON c.class_id = sc.class_id  " +
                "WHERE gc.clearance_exam_id =  ? ";
        try{
            List<User> studentList = getJdbcTemplate().query(
                    sql,
                    new Object[]{clearanceExamId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();

                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setCustomGrade(resultSet.getString("grade"));
                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("Error!!");
            return null;

        }catch (Exception e ){
            System.out.println("Error!!");
            e.printStackTrace();
            return null;
        }
    }

}
