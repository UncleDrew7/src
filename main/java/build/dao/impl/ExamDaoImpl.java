package build.dao.impl;

import build.model.*;
import build.dao.ExamDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public class ExamDaoImpl extends JdbcDaoSupport implements ExamDao{
    @Override
    public String addNewExam(Exam exam) {
        String sql = "INSERT INTO exam (parent_course_id ,semester_id, teacher_id, exam_name,created_by, date_of_exam, enrolment_close_date, min_grade, max_grade, weight ) VALUES( ?,?,?,?,?,?,?,?,?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ exam.getParentCourseId(),exam.getSemesterId(), exam.getTeacherId(), exam.getExamName(), exam.getCreatedBy(),exam.getDateOfExam(), exam.getEnrolmentCloseDate(), exam.getMinGrade(), exam.getMaxGrade(), exam.getWeight() }
        );
        if(1 == returnValue)
            return "EXAM #"+exam.getExamName()+" CREATED SUCCESSFULLY";
        else
            return "QUERY FAILURE MESSAGE COULD NOT CREATE EXAM";
    }


    @Override
    public String updateCurrentExam(Exam exam) {

        String sql = "UPDATE exam SET exam_name = ? ,date_of_exam = ?,enrolment_start_date = ?,enrolment_close_date= ?  WHERE id = ? ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{exam.getExamName(),exam.getDateOfExam(),exam.getEnrolmentStartDate(),exam.getEnrolmentCloseDate(),exam.getId()}
        );
        if(1 == returnValue)
            return "EXAM #"+exam.getExamName()+" HAS SUCCESSFULLY BEEN UPDATED ";
        else
            return "UPDATE FAILURE MESSAGE";
    }

    @Override
    public String deleteExistingExam(Exam exam) {
        return null;
    }

    @Override
    public String createClearanceExam(ClearanceExam clearanceExam) {
        String sql = "INSERT INTO  clearance_exam (parent_exam_id,exam_name,exam_date, created_by) VALUES(?,?,?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{clearanceExam.getParentExamId(),clearanceExam.getExamName(),clearanceExam.getExamDate(),clearanceExam.getCreatedBy()}
            );
            if(returnValue == 1){
                System.out.println(" CLEARANCE EXAM CREATED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("CLEARANCE EXAM CREATE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INTERNAL SYSTEM QUERY ERROR!!");
            return "400";
        }
    }

    @Override
    public String makeClearanceExamEnrolment(ClearanceExamEnrolment clearanceExamEnrolment) {
        String sql = "INSERT INTO clearance_exam_enrolment (clearance_exam_id,parent_exam_id,student_id) VALUES(?,?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{clearanceExamEnrolment.getClearanceExamId(),clearanceExamEnrolment.getParentExamId(),clearanceExamEnrolment.getStudentId()}
            );
            if(returnValue == 1){
                System.out.println(" CLEARANCE EXAM ENROLLMENT SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println(" CLEARANCE EXAM ENROLLMENT FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INTERNAL SYSTEM QUERY ERROR!!");
            return "400";
        }
    }

    @Override
    public String addStudentClearanceExamScores(GradeCustom gradeCustom) {
        String sql = "INSERT INTO grade_custom (clearance_exam_id,parent_exam_id,parent_course_id,child_course_id,student_id,grade,created_by) VALUES(?,?,?,?,?,?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{gradeCustom.getClearanceExamId(),gradeCustom.getParentExamId(),gradeCustom.getParentCourseId(),gradeCustom.getChildCourseId(),gradeCustom.getStudentId(),gradeCustom.getGrade(),gradeCustom.getCreatedBy()}
            );
            if(returnValue == 1){
                System.out.println("STUDENT CLEARANCE EXAM GRADE ADDED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("STUDENT CLEARANCE EXAM GRADE ADD FAILED !!");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INTERNAL SYSTEM QUERY ERROR!!");
            return "400";
        }
    }

    @Override
    public boolean checkIfParentExamAlreadyHasAClearanceExam(int parentExamId) {
        String sql = "SELECT " +
                "cle.clearance_exam_id " +
                "FROM clearance_exam cle " +
                "WHERE cle.parent_exam_id = ? ";

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{parentExamId},
                    Integer.class
            );
            return true;

        } catch (EmptyResultDataAccessException e) {
            return false;
        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkIfStudentClearanceExamHasGrade(long studentId, int clearanceExam) {
        String sql = "SELECT " +
                "gc.grade_custom_id " +
                "FROM grade_custom gc " +
                "WHERE gc.student_id = ? " +
                "AND gc.clearance_exam_id = ?";

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{studentId,clearanceExam},
                    Integer.class
            );
            return true;

        } catch (EmptyResultDataAccessException e) {
            return false;
        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ClearanceExam getClearanceExamForMostLatestStudentExam(long studentId, long parentCourseId) {
//        #clearance exam for LscCE
        String sql = "SELECT " +
                "cle.clearance_exam_id, " +
                "cle.parent_exam_id, " +
                "cle.parent_course_id, " +
                "cle.child_course_id, " +
                "cle.exam_name, " +
                "cle.exam_date, " +
                "cle.enrolment_start_date, " +
                "cle.enrolment_end_date, " +
                "cle.clearance_grade_score, " +
                "cle.enrolment_grade_min, " +
                "cle.enrolment_grade_max " +
                "FROM clearance_exam cle " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = cle.parent_exam_id " +
                "WHERE CURDATE()<= DATE(cle.enrolment_end_date) " +
                "AND cle.parent_exam_id = (SELECT  " +
                "gi.grade_item_id " +
                "FROM exam_enrolment ee " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = ee.exam_id " +
                "WHERE ee.is_Enrolled = 1 " +
                "AND gi.grade_item_type = 'Exam' " +
                "AND ee.student_id =  ? " +
                "AND gi.parent_course_id = ? " +
                "AND gi.date_of_grade_item = (SELECT " +
                "MAX(giee.date_of_grade_item) " +
                "FROM exam_enrolment eee " +
                "LEFT JOIN grade_items giee ON giee.grade_item_id = eee.exam_id " +
                "WHERE eee.student_id = ee.student_id " +
                "AND giee.parent_course_id = gi.parent_course_id) " +
                ") " +
                "ORDER BY cle.enrolment_end_date DESC";
        try{
            ClearanceExam object = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{studentId,parentCourseId},
                    new RowMapper<ClearanceExam>() {
                        @Override
                        public ClearanceExam mapRow(ResultSet resultSet, int i) throws SQLException {
                            ClearanceExam clearanceExam  = new ClearanceExam();

                            clearanceExam.setClearanceExamId(resultSet.getInt("clearance_exam_id"));
                            clearanceExam.setParentCourseId(resultSet.getLong("parent_course_id"));
                            clearanceExam.setParentExamId(resultSet.getInt("parent_exam_id"));
                            clearanceExam.setChildCourseId(resultSet.getInt("child_course_id"));
                            clearanceExam.setExamName(resultSet.getString("exam_name"));
                            clearanceExam.setExamDate(resultSet.getString("exam_date"));
                            clearanceExam.setEnrolmentStartDate(resultSet.getString("enrolment_start_date"));
                            clearanceExam.setEnrolmentEndDate(resultSet.getString("enrolment_end_date"));
                            clearanceExam.setClearanceGradeScore(resultSet.getDouble("clearance_grade_score"));
                            clearanceExam.setEnrolmentGradeMin(resultSet.getDouble("enrolment_grade_min"));
                            clearanceExam.setEnrolmentGradeMax(resultSet.getDouble("enrolment_grade_max"));

                            return clearanceExam;
                        }
                    }

            );
            return object;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            return null;

        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public ClearanceExam getClearanceExamByChildExamId(int childExamId) {
        String sql = "SELECT  " +
                "cle.clearance_exam_id," +
                "cle.parent_exam_id,  " +
                "cle.parent_course_id, " +

                "ex.exam_name parent_exam_name, " +

                "pc.course_name,  " +
                "pc.course_short_name,  " +
                "cle.exam_name cl_exam_name, " +
                "cle.exam_date,  " +
                "cle.enrolment_start_date,  " +
                "cle.enrolment_end_date,  " +
                "cle.clearance_grade_score,  " +
                "cle.enrolment_grade_min,  " +
                "cle.enrolment_grade_max, " +
                "cle.submitted, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "(SELECT  " +
                "COUNT(*) " +
                "FROM clearance_exam_enrolment ceet " +
                "WHERE ceet.is_enrolled = 1 " +
                "AND ceet.clearance_exam_id = cle.clearance_exam_id" +
                ") total_enrolled_std " +
                "FROM clearance_exam cle  " +
                "LEFT JOIN exam ex ON ex.id = cle.parent_exam_id " +
                "LEFT JOIN semester s ON s.semester_id = ex.semester_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = ex.parent_course_id " +
                "WHERE cle.clearance_exam_id = ? ";
        try{
            ClearanceExam object = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{childExamId},
                    new RowMapper<ClearanceExam>() {
                        @Override
                        public ClearanceExam mapRow(ResultSet resultSet, int i) throws SQLException {
                            ClearanceExam clearanceExam  = new ClearanceExam();

                            clearanceExam.setClearanceExamId(resultSet.getInt("clearance_exam_id"));
                            clearanceExam.setParentCourseId(resultSet.getLong("parent_course_id"));
                            clearanceExam.setParentCourseName(resultSet.getString("course_name"));
                            clearanceExam.setParentCourseShortName(resultSet.getString("course_short_name"));
                            clearanceExam.setParentExamId(resultSet.getInt("parent_exam_id"));
                            clearanceExam.setParentExamName(resultSet.getString("parent_exam_name"));

                            clearanceExam.setExamName(resultSet.getString("cl_exam_name"));
                            clearanceExam.setExamDate(resultSet.getString("exam_date"));
                            clearanceExam.setEnrolmentStartDate(resultSet.getString("enrolment_start_date"));
                            clearanceExam.setEnrolmentEndDate(resultSet.getString("enrolment_end_date"));
                            clearanceExam.setClearanceGradeScore(resultSet.getDouble("clearance_grade_score"));
                            clearanceExam.setEnrolmentGradeMin(resultSet.getDouble("enrolment_grade_min"));
                            clearanceExam.setEnrolmentGradeMax(resultSet.getDouble("enrolment_grade_max"));
                            clearanceExam.setSubmitted(resultSet.getInt("submitted"));

                            clearanceExam.setSemesterId(resultSet.getInt("semester_id"));
                            clearanceExam.setSemesterCode(resultSet.getString("semester_code"));
                            clearanceExam.setEnrolledStudents(resultSet.getInt("total_enrolled_std"));


                            return clearanceExam;
                        }
                    }

            );
            return object;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            return null;

        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<GradeCustom> getIfAnyClearanceExamResultsList(long studentId, long parentCourseId) {
        String sql = "SELECT " +
                "gc.clearance_exam_id, " +
                "gc.parent_exam_id, " +
                "gc.parent_course_id, " +
                "gc.child_course_id, " +
                "gc.student_id, " +
                "gc.grade " +
                "FROM grade_custom gc " +
                "WHERE gc.student_id =  ? " +
                "AND gc.parent_course_id = ? ";
        try{
            List<GradeCustom> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId,parentCourseId},
                    new RowMapper<GradeCustom>() {
                        @Override
                        public GradeCustom mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeCustom gradeCustom = new GradeCustom();

                            gradeCustom.setClearanceExamId(resultSet.getInt("clearance_exam_id"));
                            gradeCustom.setParentExamId(resultSet.getInt("parent_exam_id"));
                            gradeCustom.setParentCourseId(resultSet.getLong("parent_course_id"));
                            gradeCustom.setChildCourseId(resultSet.getInt("child_course_id"));
                            gradeCustom.setStudentId(resultSet.getLong("student_id"));
                            gradeCustom.setGrade(resultSet.getString("grade"));

                            return gradeCustom;
                        }
                    }

            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<ClearanceExam> getIfAnyStudentClearanceExamList(long studentId, long parentCourseId) {
        String sql = "SELECT " +
                "cle.clearance_exam_id, " +
                "cle.parent_exam_id, " +
                "cle.parent_course_id, " +
                "cle.child_course_id, " +
                "cle.exam_name, " +
                "DATE_FORMAT(cle.exam_date, '%D %M %Y') exam_date, " +
                "DATE_FORMAT(cle.enrolment_start_date, '%D %M %Y') enrolment_start_date, " +
                "DATE_FORMAT(cle.enrolment_end_date, '%D %M %Y') enrolment_end_date, " +
                "cle.clearance_grade_score, " +
                "cle.enrolment_grade_min, " +
                "cle.enrolment_grade_max, " +
                "gc.grade " +
                "FROM clearance_exam cle " +
                "LEFT JOIN clearance_exam_enrolment cee ON cee.clearance_exam_id = cle.clearance_exam_id " +
                "LEFT JOIN grade_custom gc ON gc.student_id = cee.student_id " +
                "WHERE cle.parent_course_id =  ? " +
                "AND cee.student_id =  ? ";
        try{
            List<ClearanceExam> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{parentCourseId,studentId},
                    new RowMapper<ClearanceExam>() {
                        @Override
                        public ClearanceExam mapRow(ResultSet resultSet, int i) throws SQLException {
                            ClearanceExam clearanceExam = new ClearanceExam();

                            clearanceExam.setClearanceExamId(resultSet.getInt("clearance_exam_id"));
                            clearanceExam.setParentExamId(resultSet.getInt("parent_exam_id"));
                            clearanceExam.setParentCourseId(resultSet.getLong("parent_course_id"));
                            clearanceExam.setChildCourseId( resultSet.getInt("child_course_id"));
                            clearanceExam.setExamName(resultSet.getString("exam_name"));
                            clearanceExam.setExamDate( resultSet.getString("exam_date"));
                            clearanceExam.setEnrolmentStartDate(resultSet.getString("enrolment_start_date"));
                            clearanceExam.setEnrolmentEndDate(resultSet.getString("enrolment_end_date"));
                            clearanceExam.setClearanceGradeScore(resultSet.getInt("clearance_grade_score"));
                            clearanceExam.setEnrolmentGradeMin(resultSet.getInt("enrolment_grade_min"));
                            clearanceExam.setEnrolmentGradeMax(resultSet.getInt("enrolment_grade_max"));
                            clearanceExam.setGrade(resultSet.getString("grade"));
                            return clearanceExam;
                        }
                    }
            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<ClearanceExam> getIfAnyChildCourseClearnceExamList(int childCourseId) {
        String sql =  "SELECT  " +
                "cle.clearance_exam_id,  " +
                "cle.parent_exam_id,  " +
                "cle.parent_course_id,  " +
                "cle.child_course_id,  " +
                "cle.exam_name,  " +
                "DATE_FORMAT(cle.exam_date, '%D %M %Y') exam_date,  " +
                "DATE_FORMAT(cle.enrolment_start_date, '%D %M %Y') enrolment_start_date,  " +
                "DATE_FORMAT(cle.enrolment_end_date, '%D %M %Y') enrolment_end_date,  " +
                "cle.clearance_grade_score,  " +
                "cle.enrolment_grade_min, " +
                "cle.enrolment_grade_max  " +
                "FROM clearance_exam cle  " +
                "WHERE cle.child_course_id = ? ";
        try{
            List<ClearanceExam> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{childCourseId},
                    new RowMapper<ClearanceExam>() {
                        @Override
                        public ClearanceExam mapRow(ResultSet resultSet, int i) throws SQLException {
                            ClearanceExam clearanceExam = new ClearanceExam();

                            clearanceExam.setClearanceExamId(resultSet.getInt("clearance_exam_id"));
                            clearanceExam.setParentExamId(resultSet.getInt("parent_exam_id"));
                            clearanceExam.setParentCourseId(resultSet.getLong("parent_course_id"));
                            clearanceExam.setChildCourseId( resultSet.getInt("child_course_id"));
                            clearanceExam.setExamName(resultSet.getString("exam_name"));
                            clearanceExam.setExamDate( resultSet.getString("exam_date"));
                            clearanceExam.setEnrolmentStartDate(resultSet.getString("enrolment_start_date"));
                            clearanceExam.setEnrolmentEndDate(resultSet.getString("enrolment_end_date"));
                            clearanceExam.setClearanceGradeScore(resultSet.getInt("clearance_grade_score"));
                            clearanceExam.setEnrolmentGradeMin(resultSet.getInt("enrolment_grade_min"));
                            clearanceExam.setEnrolmentGradeMax(resultSet.getInt("enrolment_grade_max"));

                            return clearanceExam;
                        }
                    }
            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<ClearanceExam> getAllClearanceExams() {
        String sql = "SELECT  " +
                "cle.clearance_exam_id, " +
                "cle.parent_exam_id, " +
                "cle.parent_course_id, " +
                "ex.exam_name parent_exam_name, " +
                "pc.course_name, " +
                "pc.course_short_name parent_course_name, " +
                "cle.child_course_id, " +
                "cle.exam_name clexam_name, " +
                "cle.exam_date clexam_date," +
                "cle.enrolment_start_date, " +
                "cle.enrolment_end_date, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "(SELECT COUNT(*) " +
                "FROM clearance_exam_enrolment cee " +
                "WHERE cee.clearance_exam_id = cle.clearance_exam_id)enrolledStudents " +
                "FROM clearance_exam cle " +
                "LEFT JOIN exam ex ON ex.id = cle.parent_exam_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = ex.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ex.semester_id " +
                "ORDER BY s.semester_code DESC , clexam_date DESC , clexam_name ";
        try{
            List<ClearanceExam> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<ClearanceExam>() {
                        @Override
                        public ClearanceExam mapRow(ResultSet resultSet, int i) throws SQLException {
                            ClearanceExam clearanceExam = new ClearanceExam();

                            clearanceExam.setClearanceExamId(resultSet.getInt("clearance_exam_id"));
                            clearanceExam.setParentExamId(resultSet.getInt("parent_exam_id"));
                            clearanceExam.setParentExamName(resultSet.getString("parent_exam_name"));
                            clearanceExam.setParentCourseId(resultSet.getLong("parent_course_id"));
                            clearanceExam.setParentCourseName(resultSet.getString("parent_course_name"));
                            clearanceExam.setChildCourseId( resultSet.getInt("child_course_id"));
                            clearanceExam.setExamName(resultSet.getString("clexam_name"));
                            clearanceExam.setExamDate( resultSet.getString("clexam_date"));
                            clearanceExam.setEnrolmentStartDate(resultSet.getString("enrolment_start_date"));
                            clearanceExam.setEnrolmentEndDate(resultSet.getString("enrolment_end_date"));
                            clearanceExam.setParentCourseName(resultSet.getString("course_name"));
                            clearanceExam.setSemesterId(resultSet.getInt("semester_id"));
                            clearanceExam.setSemesterCode(resultSet.getString("semester_code"));
                            clearanceExam.setEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return clearanceExam;
                        }
                    }
            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<ClearanceExam> getAllClearanceExamsByMajorId(int majorId) {
        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "cle.clearance_exam_id,  " +
                "cle.parent_exam_id,  " +
                "gi.grade_item_name,  " +
                "cle.parent_course_id,  " +
                "pc.course_name,  " +
                "cle.child_course_id,  " +
                "cle.exam_name,  " +
                "cle.exam_date,  " +
                "cle.enrolment_start_date,  " +
                "cle.enrolment_end_date,  " +
                "s.semester_id,  " +
                "s.semester_code,  " +
                "(SELECT COUNT(*)  " +
                "FROM clearance_exam_enrolment cee  " +
                "WHERE cee.clearance_exam_id = cle.clearance_exam_id)enrolledStudents  " +
                "FROM clearance_exam cle  " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = cle.parent_exam_id  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cle.parent_course_id  " +
                "LEFT JOIN child_course_semester ccs ON  ccs.child_course_id = cle.child_course_id  " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id  " +
                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id   " +
                "LEFT JOIN major m ON m.major_id = pcm.major_id   " +
                "WHERE m.major_id =  ? " +
                "ORDER BY s.semester_code DESC , cle.exam_date DESC , gi.grade_item_name ";
        try{
            List<ClearanceExam> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<ClearanceExam>() {
                        @Override
                        public ClearanceExam mapRow(ResultSet resultSet, int i) throws SQLException {
                            ClearanceExam clearanceExam = new ClearanceExam();

                            clearanceExam.setClearanceExamId(resultSet.getInt("clearance_exam_id"));
                            clearanceExam.setParentExamId(resultSet.getInt("parent_exam_id"));
                            clearanceExam.setParentCourseId(resultSet.getLong("parent_course_id"));
                            clearanceExam.setChildCourseId( resultSet.getInt("child_course_id"));
                            clearanceExam.setExamName(resultSet.getString("exam_name"));
                            clearanceExam.setExamDate( resultSet.getString("exam_date"));
                            clearanceExam.setEnrolmentStartDate(resultSet.getString("enrolment_start_date"));
                            clearanceExam.setEnrolmentEndDate(resultSet.getString("enrolment_end_date"));
                            clearanceExam.setParentExamName(resultSet.getString("grade_item_name"));
                            clearanceExam.setParentCourseName(resultSet.getString("course_name"));
                            clearanceExam.setSemesterId(resultSet.getInt("semester_id"));
                            clearanceExam.setSemesterCode(resultSet.getString("semester_code"));
                            clearanceExam.setEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return clearanceExam;
                        }
                    }
            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<ClearanceExam> getAllClearanceExamsBySemesterId(int semesterId) {
        String sql = "SELECT  " +
                "cle.clearance_exam_id, " +
                "cle.parent_exam_id, " +
                "gi.grade_item_name, " +
                "cle.parent_course_id, " +
                "pc.course_name, " +
                "cle.child_course_id, " +
                "cle.exam_name, " +
                "cle.exam_date, " +
                "cle.enrolment_start_date, " +
                "cle.enrolment_end_date, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "(SELECT COUNT(*) " +
                "FROM clearance_exam_enrolment cee " +
                "WHERE cee.clearance_exam_id = cle.clearance_exam_id)enrolledStudents " +
                "FROM clearance_exam cle " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = cle.parent_exam_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cle.parent_course_id " +
                "LEFT JOIN child_course_semester ccs ON  ccs.child_course_id = cle.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE s.semester_id =  ? " +
                "ORDER BY s.semester_code DESC , cle.exam_date DESC , gi.grade_item_name ";
        try{
            List<ClearanceExam> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId},
                    new RowMapper<ClearanceExam>() {
                        @Override
                        public ClearanceExam mapRow(ResultSet resultSet, int i) throws SQLException {
                            ClearanceExam clearanceExam = new ClearanceExam();

                            clearanceExam.setClearanceExamId(resultSet.getInt("clearance_exam_id"));
                            clearanceExam.setParentExamId(resultSet.getInt("parent_exam_id"));
                            clearanceExam.setParentCourseId(resultSet.getLong("parent_course_id"));
                            clearanceExam.setChildCourseId( resultSet.getInt("child_course_id"));
                            clearanceExam.setExamName(resultSet.getString("exam_name"));
                            clearanceExam.setExamDate( resultSet.getString("exam_date"));
                            clearanceExam.setEnrolmentStartDate(resultSet.getString("enrolment_start_date"));
                            clearanceExam.setEnrolmentEndDate(resultSet.getString("enrolment_end_date"));
                            clearanceExam.setParentExamName(resultSet.getString("grade_item_name"));
                            clearanceExam.setParentCourseName(resultSet.getString("course_name"));
                            clearanceExam.setSemesterId(resultSet.getInt("semester_id"));
                            clearanceExam.setSemesterCode(resultSet.getString("semester_code"));
                            clearanceExam.setEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return clearanceExam;
                        }
                    }
            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<ClearanceExam> getAllClearanceExamBySemesterIdAndMajorId(int semesterId, int majorId) {
        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "cle.clearance_exam_id,  " +
                "cle.parent_exam_id,  " +
                "gi.grade_item_name,  " +
                "cle.parent_course_id,  " +
                "pc.course_name,  " +
                "cle.child_course_id,  " +
                "cle.exam_name,  " +
                "cle.exam_date,  " +
                "cle.enrolment_start_date,  " +
                "cle.enrolment_end_date,  " +
                "s.semester_id,  " +
                "s.semester_code,  " +
                "(SELECT COUNT(*)  " +
                "FROM clearance_exam_enrolment cee  " +
                "WHERE cee.clearance_exam_id = cle.clearance_exam_id)enrolledStudents  " +
                "FROM clearance_exam cle  " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = cle.parent_exam_id  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cle.parent_course_id  " +
                "LEFT JOIN child_course_semester ccs ON  ccs.child_course_id = cle.child_course_id  " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id  " +
                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id   " +
                "LEFT JOIN major m ON m.major_id = pcm.major_id   " +
                "WHERE s.semester_id =  ? " +
                "AND m.major_id =  ? " +
                "ORDER BY s.semester_code DESC , cle.exam_date DESC , gi.grade_item_name ";
        try{
            List<ClearanceExam> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId,majorId},
                    new RowMapper<ClearanceExam>() {
                        @Override
                        public ClearanceExam mapRow(ResultSet resultSet, int i) throws SQLException {
                            ClearanceExam clearanceExam = new ClearanceExam();

                            clearanceExam.setClearanceExamId(resultSet.getInt("clearance_exam_id"));
                            clearanceExam.setParentExamId(resultSet.getInt("parent_exam_id"));
                            clearanceExam.setParentCourseId(resultSet.getLong("parent_course_id"));
                            clearanceExam.setChildCourseId( resultSet.getInt("child_course_id"));
                            clearanceExam.setExamName(resultSet.getString("exam_name"));
                            clearanceExam.setExamDate( resultSet.getString("exam_date"));
                            clearanceExam.setEnrolmentStartDate(resultSet.getString("enrolment_start_date"));
                            clearanceExam.setEnrolmentEndDate(resultSet.getString("enrolment_end_date"));
                            clearanceExam.setParentExamName(resultSet.getString("grade_item_name"));
                            clearanceExam.setParentCourseName(resultSet.getString("course_name"));
                            clearanceExam.setSemesterId(resultSet.getInt("semester_id"));
                            clearanceExam.setSemesterCode(resultSet.getString("semester_code"));
                            clearanceExam.setEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return clearanceExam;
                        }
                    }
            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<ClearanceExam> getLastAddedClearanceExams() {
        String sql = "SELECT  " +
                "cle.clearance_exam_id, " +
                "cle.parent_exam_id, " +
                "cle.parent_course_id, " +
                "ex.exam_name, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "cle.exam_name, " +
                "cle.exam_date, " +
                "s.semester_id," +
                "s.semester_code," +
                "(SELECT COUNT(*)" +
                "FROM clearance_exam_enrolment cee" +
                "WHERE cee.clearance_exam_id = cle.clearance_exam_id)enrolledStudents" +
                "FROM clearance_exam cle\n" +
                "LEFT JOIN exam ex ON  cle.parent_exam_id = ex.id" +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = ex.parent_course_id" +
                "LEFT JOIN semester s ON s.semester_id = ex.semester_id" +
                "ORDER BY s.semester_code DESC , cle.exam_date DESC , " +
                "LIMIT 5";
        try{
            List<ClearanceExam> list = getJdbcTemplate().query(
                    sql,
                 //   new Object[]{ },
                    new RowMapper<ClearanceExam>() {
                        @Override
                        public ClearanceExam mapRow(ResultSet resultSet, int i) throws SQLException {
                            ClearanceExam clearanceExam = new ClearanceExam();

                            clearanceExam.setClearanceExamId(resultSet.getInt("clearance_exam_id"));
                            clearanceExam.setParentExamId(resultSet.getInt("parent_exam_id"));
                            clearanceExam.setParentCourseId(resultSet.getLong("parent_course_id"));
                            clearanceExam.setParentCourseId(resultSet.getLong("course_name"));
                            clearanceExam.setParentCourseId(resultSet.getLong("course_short_name"));
                            clearanceExam.setExamName(resultSet.getString("exam_name"));
                            clearanceExam.setExamDate( resultSet.getString("exam_date"));
                            clearanceExam.setEnrolledStudents(resultSet.getInt("enrolledStudents"));
                            clearanceExam.setSemesterId(resultSet.getInt("semester_id"));
                            clearanceExam.setSemesterCode(resultSet.getString("semester_code"));
                            return clearanceExam;
                        }
                    }
            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<ClearanceExam> getLastEditedClearanceExams() {
//        String sql = "SELECT  " +
//                "cle.clearance_exam_id, " +
//                "cle.exam_name " +
//                "FROM clearance_exam cle " +
//                "ORDER BY cle.updated_at DESC  " +
//                "LIMIT 5";
//        try{
//            List<ClearanceExam> list = getJdbcTemplate().query(
//                    sql,
//                    new RowMapper<ClearanceExam>() {
//                        @Override
//                        public ClearanceExam mapRow(ResultSet resultSet, int i) throws SQLException {
//                            ClearanceExam clearanceExam = new ClearanceExam();
//
//                            clearanceExam.setClearanceExamId(resultSet.getInt("clearance_exam_id"));
//                            clearanceExam.setExamName(resultSet.getString("exam_name"));
//
//                            return clearanceExam;
//                        }
//                    }
//            );
//            return list;
//
//        }
//        catch(EmptyResultDataAccessException e){
//            System.out.println("EMPTY QUERY");
//            e.printStackTrace();
//            return null;
//        }catch (Exception e){
//            System.out.println("@ERROR!!");
//            e.printStackTrace();
//            return  null;
//        }
        return null;
    }

    @Override
    public String getPassGradeIfAnyForParentCourseClearanceExam(long studentId, long parentCourseId) {
        String sql = "SELECT " +
                "gc.grade " +
                "FROM grade_custom gc " +
                "WHERE gc.grade = 'pass'  " +
                "AND gc.student_id = ? " +
                "AND gc.parent_course_id = ?";

        try{
            String val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{studentId,parentCourseId},
                    String.class
            );
            return val;

        } catch (EmptyResultDataAccessException e) {
            return "";
        }catch (Exception e ){
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public List<GradeCustom> getStudentClearanceExamResults(long studentId, long parentCourseId) {
        String sql = "SELECT " +
                "gc.grade_custom_id, " +
                "gc.clearance_exam_id, " +
                "gc.parent_exam_id, " +
                "gc.parent_course_id, " +
                "gc.child_course_id, " +
                "gc.student_id, " +
                "gc.grade " +
                "FROM grade_custom gc " +
                "WHERE gc.student_id =  ? " +
                "AND gc.parent_course_id =  ? ";

        try{
            List<GradeCustom> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId,parentCourseId},
                    new RowMapper<GradeCustom>() {
                        @Override
                        public GradeCustom mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeCustom gradeCustom = new GradeCustom();

                            gradeCustom.setGradeCustomId(resultSet.getInt("grade_custom_id"));
                            gradeCustom.setClearanceExamId(resultSet.getInt("clearance_exam_id"));
                            gradeCustom.setParentExamId(resultSet.getInt("parent_exam_id"));
                            gradeCustom.setParentCourseId(resultSet.getLong("parent_course_id"));
                            gradeCustom.setChildCourseId(resultSet.getInt("child_course_id"));
                            gradeCustom.setStudentId(resultSet.getInt("student_id"));
                            gradeCustom.setGrade(resultSet.getString("grade"));
                            return gradeCustom;
                        }
                    }
            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public int isStudentInClearanceExam(long studentId, int clearanceExamId){
        String sql = "SELECT COUNT(*) " +
                "FROM clearance_exam_enrolment clee  " +
                "WHERE clee.student_id = ? AND clee.clearance_exam_id = ?  ";

        try{

            int count = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{studentId, clearanceExamId},
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
    public int getStudentClearanceExamTimes(long studentId, long parentCourseId) {
        String sql = "SELECT COUNT(*) " +

                "FROM clearance_exam_enrolment clee, clearance_exam cle, exam ex, parent_courses pc " +
                "WHERE clee.clearance_exam_id = cle.clearance_exam_id AND cle.parent_exam_id = ex.id AND ex.parent_course_id = pc.parent_course_id " +
                "AND clee.student_id = ? AND pc.parent_course_id = ? ";

        try{
            int clExamTime = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{studentId, parentCourseId},
                    Integer.class
            );
            return clExamTime;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return 0;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  -1;
        }
    }

    @Override
    public long getClearanceExamParentCourse(int clearanceExamId) {
        String sql = "SELECT " +
                "cle.parent_course_id " +
                "FROM clearance_exam cle " +
                "WHERE cle.clearance_exam_id =  ?";
        try{
            long id = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{clearanceExamId},
                    Long.class
            );
            return id;

        } catch (EmptyResultDataAccessException e) {
            return 0;
        }catch (Exception e ){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getClearanceExamChildCourse(int clearanceExamId) {
        String sql = "SELECT " +
                "cle.child_course_id " +
                "FROM clearance_exam cle " +
                "WHERE cle.clearance_exam_id =  ?";
        try{
            int id = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{clearanceExamId},
                    Integer.class
            );
            return id;

        } catch (EmptyResultDataAccessException e) {
            return 0;
        }catch (Exception e ){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getClearanceExamParentExam(int clearanceExamId) {
        String sql = "SELECT " +
                "cle.parent_exam_id " +
                "FROM clearance_exam cle " +
                "WHERE cle.clearance_exam_id =  ?";
        try{
            int id = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{clearanceExamId},
                    Integer.class
            );
            return id;

        } catch (EmptyResultDataAccessException e) {
            return 0;
        }catch (Exception e ){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Added by zgp on 13/03/2018.
     */
    @Override
    public List<Exam> adminGetAllExamDataList() {
        String sql = "SELECT  " +
                "e.id, " +
                "e.parent_course_id, " +
                "e.date_of_exam, " +
                "e.exam_name, " +
                "e.date_of_exam," +
                "e.enrolment_close_date, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "(SELECT COUNT(*) " +
                "FROM exam_enrolment ee " +
                "WHERE ee.exam_id = e.id)enrolledStudents " +
                "FROM exam e " +
               // "LEFT JOIN grade_items gi ON gi.grade_item_id = cle.parent_exam_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = e.parent_course_id " +
             //   "LEFT JOIN child_course_semester ccs ON  ccs.child_course_id = cle.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = e.semester_id " +
                "ORDER BY s.semester_code DESC , e.date_of_exam DESC , e.exam_name ";
        try{
            List<Exam> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exams = new Exam();

                            exams.setId(resultSet.getInt("id"));
                            exams.setParentCourseId(resultSet.getInt("parent_course_id"));
                            exams.setParentCourseName(resultSet.getString("course_name"));
                            exams.setParentCourseShortName(resultSet.getString("course_short_name"));
                            exams.setSemesterIdId(resultSet.getInt("semester_id"));
                            exams.setSemesterCode(resultSet.getString("semester_code"));
                            exams.setExamName(resultSet.getString("exam_name"));
                            exams.setDateOfExam( resultSet.getString("date_of_exam"));
                            exams.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));
                            exams.setEnrollMentStudents(resultSet.getInt("enrolledStudents"));

                            return exams;
                        }
                    }
            );
            return list;
        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Exam> getLastAddedExamList() {
        String sql = "SELECT  " +
                "ex.id, " +
                "ex.exam_name, " +
                "ex.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name " +
                "FROM exam ex " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = ex.parent_course_id  " +
                "WHERE ex.active_status = '1' " +
                "ORDER BY ex.created_at DESC  " +
                "LIMIT 5";

        try{

            List<Exam> examsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam ex = new Exam();

                            ex.setId(resultSet.getInt("id"));
                            ex.setExamName(resultSet.getString("exam_name"));
                            ex.setParentCourseId(resultSet.getInt("parent_course_id"));
                            ex.setParentCourseName(resultSet.getString("course_name"));
                            ex.setParentCourseShortName(resultSet.getString("course_short_name"));

                            return ex;
                        }
                    }
            );
            return examsList;

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
    public int adminGetTotalSystemCreatedExamsCount() {

        String sql = "SELECT COUNT(*)  " +
                " FROM exam ex ";

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
    public int adminGetAllCurrentlyActiveExamsCount() {
        String sql = " SELECT COUNT(*) " +
                "    FROM exam ex " +
                "    WHERE ex.active_status = 1 AND CURDATE()<= DATE(ex.date_of_exam) ";

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
    public int adminGetAllExamEnrollmentRequestCounts() {
        String sql = "SELECT COUNT(*)  " +
                " FROM exam_student_request_enrolment er " +
                " WHERE enrolment_status = 0  " ;
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
    public List<Exam> adminGetCourseExamDataListBySemester(int semesterId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "gi.id, " +
                "gi.exam_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_exam, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.exam_id = gi.id)EnrolledStudents  " +
                "FROM exam gi " +
              //  "LEFT JOIN child_courses cc ON cc.child_course_id = gi.course_id " +
              //  "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = gi.parent_course_id " +
              //  "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
             //   "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = gi.semester_id " +
            //    "WHERE gi.grade_item_type = 'Exam' " +
                "WHERE s.semester_id = ? " +
                "ORDER BY s.semester_code DESC";
        try{

            List<Exam> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exam = new Exam();

                            exam.setId(resultSet.getInt("id"));
                            exam.setExamName(resultSet.getString("exam_name"));
                            exam.setParentCourseId(resultSet.getInt("parent_course_id"));
                            exam.setParentCourseName(resultSet.getString("course_name"));
                            exam.setParentCourseShortName(resultSet.getString("course_short_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setEnrollMentStudents(resultSet.getInt("EnrolledStudents"));

                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorCode(resultSet.getString("major_short_code"));
                        //    exam.setMinPassScore(resultSet.getInt("min_pass_score"));
                            exam.setSemesterIdId(resultSet.getInt("semester_id"));
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
    public List<Exam> adminGetCourseExamDataListByMajorId(int majorId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "gi.id, " +
                "gi.exam_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_exam, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.exam_id = gi.id)EnrolledStudents  " +
                "FROM exam gi " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = gi.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
                "LEFT JOIN semester s ON s.semester_id = gi.semester_id " +
                //    "WHERE gi.grade_item_type = 'Exam' " +
                "WHERE m.major_id = ? " +
                "ORDER BY s.semester_code DESC";
        try{

            List<Exam> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exam = new Exam();

                            exam.setId(resultSet.getInt("id"));
                            exam.setExamName(resultSet.getString("exam_name"));
                            exam.setParentCourseId(resultSet.getInt("parent_course_id"));
                            exam.setParentCourseName(resultSet.getString("course_name"));
                            exam.setParentCourseShortName(resultSet.getString("course_short_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setEnrollMentStudents(resultSet.getInt("EnrolledStudents"));

                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorCode(resultSet.getString("major_short_code"));
                            exam.setSemesterIdId(resultSet.getInt("semester_id"));
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
    public List<Exam> adminGetCourseExamDataListBySemesterAndMajorId(int semesterId,int majorId){
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "gi.id, " +
                "gi.exam_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(gi.date_of_exam, '%D %M %Y') examDate, " +
                "DATE_FORMAT(gi.enrolment_close_date, '%D %M %Y') deadlineDate, " +
                "(SELECT COUNT(*) FROM exam_enrolment ee WHERE ee.exam_id = gi.id)EnrolledStudents  " +
                "FROM exam gi " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = gi.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
                "LEFT JOIN semester s ON s.semester_id = gi.semester_id " +
                //    "WHERE gi.grade_item_type = 'Exam' " +
                "WHERE s.semester_id = ? " +
                "AND m.major_id = ? " +
                "ORDER BY s.semester_code DESC";
        try{

            List<Exam> getExamList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId, majorId},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exam = new Exam();

                            exam.setId(resultSet.getInt("id"));
                            exam.setExamName(resultSet.getString("exam_name"));
                            exam.setParentCourseId(resultSet.getInt("parent_course_id"));
                            exam.setParentCourseName(resultSet.getString("course_name"));
                            exam.setParentCourseShortName(resultSet.getString("course_short_name"));
                            exam.setDateOfExam(resultSet.getString("examDate"));
                            exam.setEnrolmentCloseDate(resultSet.getString("deadlineDate"));
                            exam.setEnrollMentStudents(resultSet.getInt("EnrolledStudents"));

                            exam.setMajorId(resultSet.getInt("major_id"));
                            exam.setMajorName(resultSet.getString("major_name"));
                            exam.setMajorCode(resultSet.getString("major_short_code"));
                            exam.setSemesterIdId(resultSet.getInt("semester_id"));
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
    public int getActiveTeacherCourseExamCount(long teacherId) {
        String sql = "SELECT COUNT(*) " +
                "FROM exam gi  " +
                "WHERE gi.active_status= 1  " +
                "AND CURDATE()<= DATE(gi.date_of_exam) " +
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
                "FROM exam ex  " +
                "WHERE ex.active_status= 1  " +
                "AND CURDATE()<= DATE(ex.date_of_exam) " +
                "AND ex.teacher_id =  ?";

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
    public int adminGetAllExamEnrollmentRequestCountsByTeacherId(long teacherId) {
        String sql = "SELECT COUNT(*)  " +
                "FROM exam_enrolment er " +
                "LEFT JOIN exam ex ON er.exam_id = ex.exam_id  " +
                "WHERE er.is_Enrolled = 0   " +
                "AND ex.teacher_id =  ? ";
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
    public List<Exam> teacherGetCourseExamDataList(long teacherId) {
        String sql = "SELECT  " +
                "ex.id, " +
                "ex.parent_course_id, " +
                "ex.date_of_exam, " +
                "ex.exam_name, " +
                "ex.date_of_exam," +
                "ex.enrolment_close_date, " +
                "ex.weight, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "(SELECT COUNT(*) " +
                "FROM exam_enrolment ee " +
                "WHERE ee.exam_id = ex.id)enrolledStudents " +
                "FROM exam ex " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = ex.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ex.semester_id " +
                "WHERE ex.teacher_id = ? " +
                "ORDER BY s.semester_code DESC , ex.date_of_exam DESC , ex.exam_name ";
        try{
            List<Exam> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exams = new Exam();

                            exams.setId(resultSet.getInt("id"));
                            exams.setParentCourseId(resultSet.getInt("parent_course_id"));
                            exams.setParentCourseName(resultSet.getString("course_name"));
                            exams.setParentCourseShortName(resultSet.getString("course_short_name"));
                            exams.setSemesterIdId(resultSet.getInt("semester_id"));

                            exams.setExamName(resultSet.getString("exam_name"));
                            exams.setDateOfExam( resultSet.getString("date_of_exam"));
                            exams.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));
                            exams.setEnrollMentStudents(resultSet.getInt("enrolledStudents"));
                            exams.setWeight(resultSet.getInt("weight"));

                            return exams;
                        }
                    }
            );
            return list;
        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Exam> teacherGetCourseExamDataListBySemester(int semesterId,long currentUserId){
        String sql = "SELECT  " +
                "ex.id, " +
                "ex.parent_course_id, " +
                "ex.date_of_exam, " +
                "ex.exam_name, " +
                "ex.date_of_exam," +
                "ex.enrolment_close_date, " +
                "ex.weight, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "(SELECT COUNT(*) " +
                "FROM exam_enrolment ee " +
                "WHERE ee.exam_id = ex.id)enrolledStudents " +
                "FROM exam ex " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = ex.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ex.semester_id " +
                "WHERE ex.teacher_id = ? AND s.semester_id = ? " +
                "ORDER BY s.semester_code DESC , ex.date_of_exam DESC , ex.exam_name ";
        try{
            List<Exam> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{currentUserId, semesterId},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exams = new Exam();

                            exams.setId(resultSet.getInt("id"));
                            exams.setParentCourseId(resultSet.getInt("parent_course_id"));
                            exams.setParentCourseName(resultSet.getString("course_name"));
                            exams.setParentCourseShortName(resultSet.getString("course_short_name"));
                            exams.setSemesterIdId(resultSet.getInt("semester_id"));
                            exams.setSemesterCode(resultSet.getString("semester_code"));
                            exams.setExamName(resultSet.getString("exam_name"));
                            exams.setDateOfExam( resultSet.getString("date_of_exam"));
                            exams.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));
                            exams.setEnrollMentStudents(resultSet.getInt("enrolledStudents"));
                            exams.setEnrollMentStudents(resultSet.getInt("weight"));

                            return exams;
                        }
                    }
            );
            return list;
        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }


    @Override
    public List<Exam> teacherGetCourseExamDataListByMajorId(int majorId,long currentUserId){
        String sql = "SELECT  " +
                "ex.id, " +
                "ex.parent_course_id, " +
                "ex.date_of_exam, " +
                "ex.exam_name, " +
                "ex.date_of_exam," +
                "ex.enrolment_close_date, " +
                "ex.weight, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "(SELECT COUNT(*) " +
                "FROM exam_enrolment ee " +
                "WHERE ee.exam_id = ex.id)enrolledStudents " +
                "FROM exam ex " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = ex.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ex.semester_id " +
                "LEFT JOIN major m ON m.major_id = pc.major_id " +
                "WHERE m.major_id = ? AND ex.teacher_id = ? " +
                "ORDER BY s.semester_code DESC , ex.date_of_exam DESC , ex.exam_name ";
        try{
            List<Exam> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId, currentUserId},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exams = new Exam();

                            exams.setId(resultSet.getInt("id"));
                            exams.setParentCourseId(resultSet.getInt("parent_course_id"));
                            exams.setParentCourseName(resultSet.getString("course_name"));
                            exams.setParentCourseShortName(resultSet.getString("course_short_name"));
                            exams.setSemesterIdId(resultSet.getInt("semester_id"));
                            exams.setSemesterCode(resultSet.getString("semester_code"));
                            exams.setExamName(resultSet.getString("exam_name"));
                            exams.setDateOfExam( resultSet.getString("date_of_exam"));
                            exams.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));
                            exams.setEnrollMentStudents(resultSet.getInt("enrolledStudents"));
                            exams.setEnrollMentStudents(resultSet.getInt("weight"));

                            return exams;
                        }
                    }
            );
            return list;
        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Exam> teacherGetCourseExamDataListBySemesterAndMajorId(int semesterId, int majorId, long currentUserId){
        String sql = "SELECT  " +
                "ex.id, " +
                "ex.parent_course_id, " +
                "ex.date_of_exam, " +
                "ex.exam_name, " +
                "ex.date_of_exam," +
                "ex.enrolment_close_date, " +
                "ex.weight, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "(SELECT COUNT(*) " +
                "FROM exam_enrolment ee " +
                "WHERE ee.exam_id = ex.id)enrolledStudents " +
                "FROM exam ex " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = ex.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ex.semester_id " +
                "LEFT JOIN major m ON m.major_id = pc.major_id " +
                "WHERE s.semester_id = ? AND m.major_id = ? AND ex.teacher_id = ? " +
                "ORDER BY s.semester_code DESC , ex.date_of_exam DESC , ex.exam_name ";
        try{
            List<Exam> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId, majorId, currentUserId},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exams = new Exam();

                            exams.setId(resultSet.getInt("id"));
                            exams.setParentCourseId(resultSet.getInt("parent_course_id"));
                            exams.setParentCourseName(resultSet.getString("course_name"));
                            exams.setParentCourseShortName(resultSet.getString("course_short_name"));
                            exams.setSemesterIdId(resultSet.getInt("semester_id"));
                            exams.setSemesterCode(resultSet.getString("semester_code"));
                            exams.setExamName(resultSet.getString("exam_name"));
                            exams.setDateOfExam( resultSet.getString("date_of_exam"));
                            exams.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));
                            exams.setEnrollMentStudents(resultSet.getInt("enrolledStudents"));
                            exams.setEnrollMentStudents(resultSet.getInt("weight"));

                            return exams;
                        }
                    }
            );
            return list;
        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public Exam getExamDataForFormDisplayByExamId(int parentExamId){

        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "ex.id,  " +
                "ex.exam_name, " +
                "ex.weight, " +
                "ex.min_grade, " +
                "ex.max_grade, " +
                "ex.date_of_exam,  " +
                "ex.parent_course_id, " +
                "ex.enrolment_start_date, " +
                "ex.enrolment_close_date, " +
                "pc.course_name, " +
                "pc.parent_course_id,  " +
                "pc.course_name,  " +
                "pc.course_short_name,  " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM  exam ex  " +
                "LEFT JOIN parent_courses pc ON ex.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ex.semester_id " +
                "LEFT JOIN major m ON m.major_id = pc.major_id " +
                "WHERE  ex.id = ?" ;

        try{

            Exam exam1 = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{parentExamId},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exams = new Exam();

                            exams.setMajorId(resultSet.getInt("major_id"));
                            exams.setMajorName(resultSet.getString("major_name"));
                            exams.setMajorCode(resultSet.getString("major_short_code"));
                            exams.setId(resultSet.getInt("id"));
                            exams.setExamName(resultSet.getString("exam_name"));
                            exams.setParentCourseId(resultSet.getLong("parent_course_id"));
                            exams.setParentCourseName(resultSet.getString("course_name"));
                            exams.setParentCourseId(resultSet.getInt("parent_course_id"));
                            exams.setParentCourseShortName(resultSet.getString("course_short_name"));
                            exams.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));
                            exams.setEnrolmentStartDate(resultSet.getString("enrolment_start_date"));
                            exams.setDateOfExam(resultSet.getString("date_of_exam"));
                            exams.setSemesterIdId(resultSet.getInt("semester_id"));
                            exams.setSemesterCode(resultSet.getString("semester_code"));
                            exams.setMinGrade(resultSet.getInt("min_grade"));
                            exams.setMaxGrade(resultSet.getInt("max_grade"));
                            exams.setWeight(resultSet.getDouble("weight"));

                            return exams;
                        }
                    }
            );
            return exam1;

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
    public Exam getSingleExamFullDetailsByExamId(int examId){
        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +

                "ex.id,  " +
                "ex.exam_name, " +
                "ex.weight, " +
                "ex.min_grade, " +
                "ex.max_grade, " +
                "ex.date_of_exam,  " +
                "ex.parent_course_id, " +
                "ex.enrolment_close_date, " +

                "pc.course_name, " +
                "pc.course_short_name,  " +

                "ex.teacher_id, " +
                "u.user_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "(SELECT COUNT(*) " +
                "FROM exam_enrolment ee " +
                "WHERE ee.exam_id = ex.id)enrolledStudents " +
                "FROM  exam ex  " +
                "LEFT JOIN parent_courses pc ON ex.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ex.semester_id " +
                "LEFT JOIN major m ON m.major_id = pc.major_id " +
                "LEFT JOIN user u ON ex.teacher_id = u.user_id " +
                "WHERE  ex.id = ?" ;

        try{

            Exam exam1 = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examId},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exams = new Exam();

                            exams.setMajorId(resultSet.getInt("major_id"));
                            exams.setMajorName(resultSet.getString("major_name"));
                            exams.setMajorCode(resultSet.getString("major_short_code"));
                            exams.setId(resultSet.getInt("id"));
                            exams.setExamName(resultSet.getString("exam_name"));
                            exams.setDateOfExam(resultSet.getString("date_of_exam"));
                            exams.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));
                            exams.setMinGrade(resultSet.getInt("min_grade"));
                            exams.setMaxGrade(resultSet.getInt("max_grade"));
                            exams.setWeight(resultSet.getDouble("weight"));
                            exams.setEnrollMentStudents(resultSet.getInt("enrolledStudents"));

                            exams.setParentCourseId(resultSet.getLong("parent_course_id"));
                            exams.setParentCourseName(resultSet.getString("course_name"));
                            exams.setParentCourseShortName(resultSet.getString("course_short_name"));

                            exams.setSemesterIdId(resultSet.getInt("semester_id"));
                            exams.setSemesterCode(resultSet.getString("semester_code"));

                            exams.setTeacherId(resultSet.getInt("teacher_id"));
                            exams.setTeacherName(resultSet.getString("user_name"));


                            return exams;
                        }
                    }
            );
            return exam1;

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
    public boolean checkPermissionForTeacherAgainstExamId(long teacherId, int examId) {
        String sql = "SELECT ex.id " +
                "FROM exam ex  " +
                "WHERE ex.teacher_id =  ?  AND ex.id = ?";

        try{
            int val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId, examId},
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
    public List<Exam> getLastEditedExamList() {
        String sql = " SELECT  " +
                "ex.id, " +
                "ex.exam_name, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name  " +
                "FROM exam ex " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = ex.parent_course_id " +
                "ORDER BY ex.updated_at DESC  " +
                "LIMIT 5";

        try{

            List<Exam> editExamList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exam = new Exam();

                            exam.setId(resultSet.getInt("id"));
                            exam.setExamName(resultSet.getString("exam_name"));
                            exam.setParentCourseId(resultSet.getInt("parent_course_id"));
                            exam.setParentCourseName(resultSet.getString("course_name"));
                            exam.setParentCourseShortName(resultSet.getString("course_short_name"));

                            return exam;
                        }
                    }
            );
            return editExamList;

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
    public String editExam(Exam newExam) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp updateTimestamp = new java.sql.Timestamp(date.getTime());

        String sql = "UPDATE exam " +
                "SET  date_of_exam = ? , " +
                "enrolment_start_date =  ? , " +
                "enrolment_close_date =  ?, " +
                "exam_name = ? , " +
                "min_grade = ? , " +
                "max_grade = ?, " +
                "weight = ? ," +
                "updated_at = ? " +
                "WHERE id = ?;";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{newExam.getDateOfExam(),newExam.getEnrolmentStartDate(),newExam.getEnrolmentCloseDate(),newExam.getExamName(), newExam.getMinGrade(), newExam.getMaxGrade(), newExam.getWeight(),updateTimestamp,newExam.getId()}
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
    public String enrollStudentInClexam(int clexamId, long userId) {

        String sql = "INSERT INTO clearance_exam_enrolment (clearance_exam_id,student_id) VALUES (?,?)";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{clexamId, userId}
            );
            if (1 == returnValue)
                return "INSERT SUCESS MESSAGE ";
            else
                return "INSERT FAILURE MESSAGE";

        } catch (Exception e) {
            System.out.println("@ExamDaoImpl::enrollStudentInClexam::ERROR!!!");
            e.printStackTrace();
            return "@ExamDaoImpl::@enrollStudentInClexam::ERROR!!!";
        }
    }

    @Override
    public boolean setClexamSubmitted(int clexamId ) {
        String sql = "UPDATE clearance_exam SET submitted = 1 WHERE clearance_exam_id = ? ";

        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{clexamId}
            );
            return  true;
        }
        catch(EmptyResultDataAccessException e){
         return false;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }


    @Override
    public int isClexamSubmitted(int clexamId ){
        String sql = "SELECT submitted FROM clearance_exam  WHERE submitted = 1 AND clearance_exam_id = ? ";

        try{
            int val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{clexamId},
                    Integer.class
            );
            return 1;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return  0;
        }
    }

    @Override
    public double getExamWeight(int examId){
        String sql =
                "SELECT ex.weight FROM exam ex WHERE ex.id =  ? ";

        try {
            Double w = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examId},
                    Double.class
            );
            return w;

        } catch (EmptyResultDataAccessException e) {
            System.out.println("@ExamDaoImpl::getExamWeight::EMPTY !");
            // e.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("@ExamDaoImpl::getExamWeight::ERROR !");
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getParentCourseId(long examId){
        String sql =
                "SELECT ex.parent_course_id FROM exam ex WHERE ex.id =  ? ";

        try {
            int pId = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examId},
                    Integer.class
            );
            return pId;

        } catch (EmptyResultDataAccessException e) {
            System.out.println("@ExamEnrolmentDaoImpl::getCountOfEnrolledExamsByUserId::EMPTY !");
            // e.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("@ExamEnrolmentDaoImpl::getCountOfEnrolledExamsByUserId::ERROR !");
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getChildCourseId(int pcId, long studentId){
        String sql =
                "SELECT  ce.course_id " +
                        "FROM course_enrolment ce, child_courses cc " +
                        "WHERE ce.course_id = cc.child_course_id " +
                        "AND cc.parent_course_id = ? AND ce.student_id = ? AND ce.active = 0 ";

        try {
            int childCourseId = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{pcId, studentId},
                    Integer.class
            );
            return childCourseId;

        } catch (EmptyResultDataAccessException e) {
            System.out.println("@ExamDaoImpl::getChildCourseId::EMPTY !");
            // e.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("@ExamDaoImpl::getChildCourseId::ERROR !");
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public List<Exam> getStudentAvailableExamList(long studentId){
        String sql = " SELECT  " +
                "ex.id, " +
                "ex.exam_name, " +
                "ex.date_of_exam, " +
                "ex.enrolment_close_date, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name  " +
                "FROM exam ex, parent_courses pc where  pc.parent_course_id = ex.parent_course_id " +
                "AND pc.parent_course_id IN ( SELECT ccc.parent_course_id FROM child_courses ccc, course_enrolment cec WHERE ccc.child_course_id = cec.course_id " +
                                                "AND cec.student_id = ? ) " +
                "AND ex.id NOT IN (SELECT  ee.exam_id  FROM exam_enrolment ee WHERE ee.has_unEnrolled = 0  " +
                                    "AND ee.is_Enrolled = 1 AND ee.student_id =  ? )  " +

                "ORDER BY ex.date_of_exam DESC, ex.semester_id DESC  ";
                //"LIMIT 5";

        try{

            List<Exam> editExamList = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId, studentId},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exam = new Exam();

                            exam.setId(resultSet.getInt("id"));
                            exam.setExamName(resultSet.getString("exam_name"));
                            exam.setParentCourseId(resultSet.getInt("parent_course_id"));
                            exam.setParentCourseName(resultSet.getString("course_name"));
                            exam.setParentCourseShortName(resultSet.getString("course_short_name"));
                            exam.setDateOfExam((resultSet.getString("date_of_exam")));
                            exam.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));

                            return exam;
                        }
                    }
            );
            return editExamList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamDaoImpl::getStudentAvailableExamList::EMPTY !");
            return null;
        }catch (Exception e){
            System.out.println("@ExamDaoImpl::getStudentAvailableExamList::ERROR!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Exam> getStudentAvailableExamList(long studentId, String searchText){
        String sql = " SELECT  " +
                "ex.id, " +
                "ex.exam_name, " +
                "ex.date_of_exam, " +
                "ex.enrolment_close_date, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name  " +
                "FROM exam ex, parent_courses pc where  pc.parent_course_id = ex.parent_course_id " +
                "AND pc.parent_course_id IN ( SELECT ccc.parent_course_id FROM child_courses ccc, course_enrolment cec WHERE ccc.child_course_id = cec.course_id " +
                "AND cec.student_id = ? ) " +
                "AND ex.id NOT IN (SELECT  ee.exam_id  FROM exam_enrolment ee WHERE ee.has_unEnrolled = 0  " +
                "AND ee.is_Enrolled = 1 AND ee.student_id =  ? )  " +
                "AND ( pc.course_name LIKE  ? OR pc.course_short_name LIKE  ? OR ex.date_of_exam LIKE ? OR ex.exam_name LIKE ? ) " +

                "ORDER BY ex.date_of_exam DESC, ex.semester_id DESC  ";
        //"LIMIT 5";

        try{

            List<Exam> editExamList = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId, studentId, '%'+searchText+'%', '%' + searchText + '%', '%' + searchText + '%', '%' + searchText + '%'},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam exam = new Exam();

                            exam.setId(resultSet.getInt("id"));
                            exam.setExamName(resultSet.getString("exam_name"));
                            exam.setParentCourseId(resultSet.getInt("parent_course_id"));
                            exam.setParentCourseName(resultSet.getString("course_name"));
                            exam.setParentCourseShortName(resultSet.getString("course_short_name"));
                            exam.setDateOfExam((resultSet.getString("date_of_exam")));
                            exam.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));

                            return exam;
                        }
                    }
            );
            return editExamList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamDaoImpl::getStudentAvailableExamList::EMPTY !");
            return null;
        }catch (Exception e){
            System.out.println("@ExamDaoImpl::getStudentAvailableExamList::ERROR!");
            e.printStackTrace();
            return  null;
        }
    }


}

