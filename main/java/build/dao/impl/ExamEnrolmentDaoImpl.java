package build.dao.impl;

import build.model.*;
import build.dao.ExamEnrolmentDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public class ExamEnrolmentDaoImpl extends JdbcDaoSupport implements ExamEnrolmentDao{


    @Override
    public String makeExamStudentRequestEnrollment(ExamStudentRequestEnrolment examStudentRequestEnrolment) {
        String sql = "INSERT INTO exam_student_request_enrolment (course_id,exam_id,student_id) VALUES (?,?,?)";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ examStudentRequestEnrolment.getCourseId(),examStudentRequestEnrolment.getExamId(),examStudentRequestEnrolment.getStudentId() }
            );
            if(1 == returnValue)
                return "QUERY SUCESS MESSAGE ";
            else
                return "QUERY FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::makeExamStudentRequestEnrollment::ERROR!!!");
            e.printStackTrace();
            return "@ExamEnrolmentDaoImpl::@makeExamStudentRequestEnrollment::ERROR!!!";
        }
    }

    @Override
    public boolean checkIfExamEnrollmentWasPreviouslyMade(int examId, long studentId) {
        String sql = "SELECT  " +
                "er.id " +
                "FROM exam_student_request_enrolment er " +
                "WHERE er.student_id = ? " +
                "AND er.exam_id= ?";

        try{
            int val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{studentId,examId},
                    Integer.class
            );
             return true;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::checkIfExamEnrollmentWasPreviouslyMade::EMPTY !");
            // e.printStackTrace();
            return false;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::checkIfExamEnrollmentWasPreviouslyMade::ERROR !");
            e.printStackTrace();
            return  false;
        }
    }

    @Override
    public String acceptStudentEnrolmentRequest(int examId, long studentId) {
        String sql = "UPDATE exam_student_request_enrolment " +
                "SET enrolment_status = 1 " +
                "WHERE  " +
                "exam_id = ? " +
                "AND student_id = ?";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{examId ,studentId }
            );
            if(1 == returnValue)
                return "QUERY SUCESS MESSAGE ";
            else
                return "QUERY FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::acceptStudentEnrolmentRequest::ERROR!!!");
            e.printStackTrace();
            return "@ExamEnrolmentDaoImpl::acceptStudentEnrolmentRequest::ERROR!!!";
        }
    }

    @Override
    public String deleteStudentEnrollmentRequest(int examId, long studentId) {
        String sql = "DELETE FROM exam_student_request_enrolment " +
                "WHERE  " +
                "exam_id = ? " +
                "AND student_id =  ?";
        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{examId ,studentId }
            );
            if(1 == returnValue)
                return "QUERY SUCESS MESSAGE ";
            else
                return "QUERY FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@deleteStudentEnrollmentRequest::ERROR!!!");
            e.printStackTrace();
            return "@ExamEnrolmentDaoImpl::@deleteStudentEnrollmentRequest::ERROR!!!";
        }
    }

    @Override
    public List<ExamStudentRequestEnrolment> getExamStudentEnrollmentRequests() {
       return null;

    }

    @Override
    public int checkIfStudentIsAlreadyEnrolled(int examId, long studentId) {
        String sql = "SELECT ee.is_Enrolled " +
                "FROM exam_enrolment ee  " +
                "WHERE  " +
                "exam_id =  ? " +
                "AND student_id =  ?";


        try{
            int isEnrolled = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examId,studentId},
                    Integer.class
            );
            return isEnrolled;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::checkIfStudentIsAlreadyEnrolled::EMPTY !");
            // e.printStackTrace();
            return -1;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::checkIfStudentIsAlreadyEnrolled::EMPTY !");
            e.printStackTrace();
            return  -1;
        }
    }

    @Override
    public boolean checkIfStudentIsAlreadyEnrolld(int examId, long studentId) {
        String sql = "SELECT ee.is_Enrolled " +
                "FROM exam_enrolment ee  " +
                "WHERE  " +
                "exam_id =  ? " +
                "AND student_id =  ?";


        try{
            int isEnrolled = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examId,studentId},
                    Integer.class
            );
            return true;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::checkIfStudentIsAlreadyEnrolled::EMPTY !");
            // e.printStackTrace();
            return false;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::checkIfStudentIsAlreadyEnrolled::EMPTY !");
            e.printStackTrace();
            return  false;
        }
    }

    @Override
    public boolean checkIfStudentIsEnrolledInExam(int examId, long studentId) {
        String sql = "SELECT id " +
                "FROM exam_enrolment ee " +
                "WHERE ee.is_Enrolled = 1 " +
                "AND ee.has_unEnrolled = 0 " +
                "AND ee.exam_id =  ? " +
                "AND ee.student_id = ? ";

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examId,studentId},
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
    public boolean checkIfStudentHasAlreadyMadePendingEnrolmentRequest(int examId, long studentId) {

        String sql = "SELECT id " +
                "FROM exam_student_request_enrolment er " +
                "WHERE er.enrolment_status = 0 " +
                "AND er.exam_id = ? " +
                "AND er.student_id = ?";

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examId,studentId},
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
    public String enrollStudentInExam(ExamEnrolment examEnrolment) {
       String sql = "INSERT INTO exam_enrolment ( exam_id,student_id,enrolls) VALUES (?,?,?)";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ examEnrolment.getExamId(),examEnrolment.getStudentId(),examEnrolment.getEnrolls() }
            );
            if(1 == returnValue)
                return "QUERY SUCESS MESSAGE ";
            else
                return "QUERY FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@enrollStudentInExam::ERROR!!!");
            e.printStackTrace();
            return "@ExamEnrolmentDaoImpl::@enrollStudentInExam::ERROR!!!";
        }

    }

    @Override
    public String updateGrade(ExamEnrolment grade) {
        String sql = "UPDATE  exam_enrolment SET exam_score = ? WHERE exam_id =? AND student_id = ? ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{grade.getExamScore(),grade.getExamId(),grade.getStudentId()}
        );
        if(1 == returnValue)
            return "EXAM GRADE SUCCESSFULLY UPDATED  ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String updateExamGrades260(ExamEnrolment grade) {
        String sql = "UPDATE  exam_enrolment SET exam_score = ? WHERE exam_id =? AND student_id = ? ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{grade.getExamScore(),grade.getExamId(),grade.getStudentId()}
        );
        if(1 == returnValue)
            return "EXAM GRADE SUCCESSFULLY UPDATED  ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String updateClexamGrade(ExamEnrolment grade) {
        String sql = "UPDATE  clearance_exam_enrolment SET score = ? WHERE clearance_exam_id =? AND student_id = ? ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{grade.getClexamGrade(),grade.getCleExamID(),grade.getStudentId()}
        );
        if(1 == returnValue)
            return "cleEXAM GRADE SUCCESSFULLY UPDATED  ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String unEnrollStudentFromClexam(int clexamId,long studentId){
        String sql = "DELETE FROM clearance_exam_enrolment " +
                "WHERE  clearance_exam_id = ? AND student_id = ?";

       /*String sql = "UPDATE exam_enrolment " +
               "SET is_Enrolled = 0 " +
               "WHERE  " +
               "exam_id = ? " +
               "AND student_id = ?";*/

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ clexamId,studentId }
            );
            if(1 == returnValue)
                return "QUERY SUCESS MESSAGE ";
            else
                return "QUERY FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@unEnrollStudentFromClExam::ERROR!!!");
            e.printStackTrace();
            return "@ExamEnrolmentDaoImpl::@unEnrollStudentFromClExam::ERROR!!!";
        }
    }


    @Override
    public String unEnrollStudentFromExam(int examId ,long studentId) {

        String sql = "DELETE FROM exam_enrolment " +
                "WHERE  " +
                "exam_id = ? " +
                "AND student_id = ?";

       /*String sql = "UPDATE exam_enrolment " +
               "SET is_Enrolled = 0 " +
               "WHERE  " +
               "exam_id = ? " +
               "AND student_id = ?";*/

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ examId,studentId }
            );
            if(1 == returnValue)
                return "QUERY SUCESS MESSAGE ";
            else
                return "QUERY FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@unEnrollStudentFromExam::ERROR!!!");
            e.printStackTrace();
            return "@ExamEnrolmentDaoImpl::@unEnrollStudentFromExam::ERROR!!!";
        }
    }

    @Override
    public String reEnrollPreviouslyUnenrolledStudent(int examId, long studentId) {
        String sql = "UPDATE exam_enrolment " +
                "SET is_Enrolled = 1 " +
                "WHERE exam_id = ? " +
                "AND student_id = ?";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ examId,studentId }
            );
            if(1 == returnValue)
                return "QUERY SUCESS MESSAGE ";
            else
                return "QUERY FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@reEnrollPreviouslyUnenrolledStudent::ERROR!!!");
            e.printStackTrace();
            return "@ExamEnrolmentDaoImpl::@reEnrollPreviouslyUnenrolledStudent::ERROR!!!";
        }
    }

    @Override
    public int getExamRequestCountByExamId(int examId) {
        String sql = "SELECT COUNT(*) " +
                "FROM exam_student_request_enrolment er " +
                "WHERE er.exam_id = ? " +
                "AND er.enrolment_status = 0";

        try{
            int count  = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::getExamRequestCountByExamID::EMPTY !");
            // e.printStackTrace();
            return 0;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::getExamRequestCountByExamID::EMPTY !");
            e.printStackTrace();
            return  0;
        }


    }

    @Override
    public List<User> getEnrolledStudentList(int examId){
        String sql = "SELECT  " +
                "u.user_id, " +
                "u.user_name " +
                "FROM user u, exam_enrolment er " +
                "WHERE u.user_id = er.student_id  " +
                "AND  er.exam_id =  ? " +
                "ORDER BY u.user_id " ;

        try{
            List<User> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{examId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();

                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("user_name"));

                            return user;
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
    public List<Exam> getStudentEnrolledExamListbySemester(long studentId, int semesterId){
        String sql = "SELECT   " +
                "er.student_id,  " +
                "u.user_name,  " +
                "er.exam_id,  " +
                "er.exam_score, " +
                "ex.semester_id, " +
                "ex.exam_name, " +
                "ex.weight, " +
                "CAST( er.exam_score * ex.weight AS DECIMAL(5,2) ) examResult, " +
                "ex.parent_course_id,  " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "ex.date_of_exam exam_date, " +
                "er.enrolls, " +
                "ex.enrolment_close_date, " +
                "ex.enrolment_start_date " +
                "FROM exam_enrolment er   " +
                "LEFT JOIN user u ON er.student_id = u.user_id  " +
                "LEFT JOIN exam ex ON er.exam_id = ex.id  " +
                "LEFT JOIN parent_courses pc ON ex.parent_course_id = pc.parent_course_id  " +
                "WHERE er.student_id = ? AND ex.semester_id = ? ORDER BY ex.semester_id ";

        try{

            List<Exam> examEN = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId, semesterId},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam examItem = new Exam();

//                            examItem.setStudentId(resultSet.getInt("student_id"));
//                            examItem.setStudentName(resultSet.getString("user_name"));
                            examItem.setId(resultSet.getInt("exam_id"));
                            examItem.setExamName(resultSet.getString("exam_name"));
                            examItem.setSemesterIdId(resultSet.getInt("semester_id"));

                            examItem.setParentCourseName(resultSet.getString("course_name"));
                            examItem.setParentCourseShortName(resultSet.getString("course_short_name"));
                            examItem.setDateOfExam(resultSet.getString("exam_date"));

//                            examItem.setExamScore(resultSet.getString("exam_score"));
//                            examItem.setExamResult(resultSet.getString("examResult"));
                            examItem.setWeight(resultSet.getDouble("weight"));
//                            examItem.setEnrolls(resultSet.getInt("enrolls"));

                            return examItem;

                        }
                    }

            );
            return examEN;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::@getStudentEnrolledExamList::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@getStudentEnrolledExamList::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Exam> getStudentEnrolledExamListbySemesterAndSearch(long studentId, int semesterId, String search){
        String sql = "SELECT   " +
                "er.student_id,  " +
                "u.user_name teacher_name,  " +
                "er.exam_id,  " +
                "er.exam_score, " +
                "ex.semester_id, " +
                "ex.exam_name, " +
                "ex.weight, " +
                "CAST( er.exam_score * ex.weight AS DECIMAL(5,2) ) examResult, " +
                "ex.parent_course_id,  " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "ex.date_of_exam exam_date, " +
                "er.enrolls, " +
                "ex.enrolment_close_date, " +
                "ex.enrolment_start_date " +
                "FROM exam_enrolment er   " +
                "LEFT JOIN exam ex ON er.exam_id = ex.id  " +
                "LEFT JOIN user u ON ex.teacher_id = u.user_id  " +
                "LEFT JOIN parent_courses pc ON ex.parent_course_id = pc.parent_course_id  " +
                "WHERE er.student_id = ? AND ex.semester_id = ? " +
                "AND ( pc.course_short_name LIKE ? OR pc.course_name LIKE ? OR  ex.exam_name LIKE ? ) " +
                "ORDER BY ex.semester_id ";

        try{

            List<Exam> examEN = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId, semesterId, String.format("%%%s%%",search), String.format("%%%s%%",search), String.format("%%%s%%",search)},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam examItem = new Exam();

//                            examItem.setStudentId(resultSet.getInt("student_id"));
                            examItem.setTeacherName(resultSet.getString("teacher_name"));
                            examItem.setId(resultSet.getInt("exam_id"));
                            examItem.setExamName(resultSet.getString("exam_name"));
                            examItem.setSemesterIdId(resultSet.getInt("semester_id"));

                            examItem.setParentCourseName(resultSet.getString("course_name"));
                            examItem.setParentCourseShortName(resultSet.getString("course_short_name"));
                            examItem.setDateOfExam(resultSet.getString("exam_date"));

//                            examItem.setExamScore(resultSet.getString("exam_score"));
//                            examItem.setExamResult(resultSet.getString("examResult"));
                            examItem.setWeight(resultSet.getDouble("weight"));
//                            examItem.setEnrolls(resultSet.getInt("enrolls"));

                            return examItem;

                        }
                    }

            );
            return examEN;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::@getStudentEnrolledExamList::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@getStudentEnrolledExamList::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Exam> getStudentEnrolledExamList(long studentId){
        String sql = "SELECT   " +
                "er.student_id,  " +
                "u.user_name,  " +
                "er.exam_id,  " +
                "er.exam_score, " +
                "ex.semester_id, " +
                "ex.exam_name, " +
                "ex.weight, " +
                "s.semester_code, " +
                "CAST( er.exam_score * ex.weight AS DECIMAL(5,2) ) examResult, " +
                "ex.parent_course_id,  " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "ex.date_of_exam exam_date, " +
                "er.enrolls, " +
                "ex.enrolment_close_date, " +
                "ex.enrolment_start_date " +
                "FROM exam_enrolment er   " +
                "LEFT JOIN user u ON er.student_id = u.user_id  " +
                "LEFT JOIN exam ex ON er.exam_id = ex.id  " +
                "LEFT JOIN semester s ON ex.semester_id = s.semester_id " +
                "LEFT JOIN parent_courses pc ON ex.parent_course_id = pc.parent_course_id  " +
                "WHERE er.student_id = ?  ORDER BY er.exam_id ";

        try{

            List<Exam> examEN = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam examItem = new Exam();

//                            examItem.setStudentId(resultSet.getInt("student_id"));
//                            examItem.setStudentName(resultSet.getString("user_name"));
                            examItem.setId(resultSet.getInt("exam_id"));
                            examItem.setExamName(resultSet.getString("exam_name"));
                            examItem.setSemesterIdId(resultSet.getInt("semester_id"));
                            examItem.setSemesterCode(resultSet.getString("semester_code"));

                            examItem.setParentCourseName(resultSet.getString("course_name"));
                            examItem.setParentCourseShortName(resultSet.getString("course_short_name"));
                            examItem.setDateOfExam(resultSet.getString("exam_date"));
                            examItem.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));

                        //    examItem.setExamScore(resultSet.getString("exam_score"));
                        //    examItem.setExamResult(resultSet.getString("examResult"));
                            examItem.setWeight(resultSet.getDouble("weight"));
                      //      examItem.setEnrolls(resultSet.getInt("enrolls"));

                            return examItem;

                        }
                    }

            );
            return examEN;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::@getStudentEnrolledExamList::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@getStudentEnrolledExamList::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Exam> getStudentEnrolledExamListAndSearch(long studentId, String search){
        String sql = "SELECT   " +
                "er.student_id,  " +
                "u.user_name teacher_name, " +
                "er.exam_id,  " +
                "er.exam_score, " +
                "ex.semester_id, " +
                "ex.exam_name, " +
                "ex.weight, " +
                "s.semester_code, " +
                "CAST( er.exam_score * ex.weight AS DECIMAL(5,2) ) examResult, " +
                "ex.parent_course_id,  " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "ex.date_of_exam exam_date, " +
                "er.enrolls, " +
                "ex.enrolment_close_date, " +
                "ex.enrolment_start_date " +
                "FROM exam_enrolment er   " +
                "LEFT JOIN exam ex ON er.exam_id = ex.id " +
                "LEFT JOIN user u ON ex.teacher_id = u.user_id  " +
                "LEFT JOIN semester s ON ex.semester_id = s.semester_id " +
                "LEFT JOIN parent_courses pc ON ex.parent_course_id = pc.parent_course_id  " +
                "WHERE er.student_id =   ?  " +
               "AND ( pc.course_short_name LIKE ? OR pc.course_name LIKE ? OR  ex.exam_name LIKE ? ) " +
                //     "AND ( ex.exam_name LIKE ? ) " +
                "ORDER BY er.exam_id ";


        try{

            List<Exam> examEN = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId, String.format("%%%s%%",search), String.format("%%%s%%",search), String.format("%%%s%%",search)},
                    new RowMapper<Exam>() {
                        @Override
                        public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
                            Exam examItem = new Exam();

//                            examItem.setStudentId(resultSet.getInt("student_id"));
                            examItem.setTeacherName(resultSet.getString("teacher_name"));
                            examItem.setId(resultSet.getInt("exam_id"));
                            examItem.setExamName(resultSet.getString("exam_name"));
                            examItem.setSemesterIdId(resultSet.getInt("semester_id"));
                            examItem.setSemesterCode(resultSet.getString("semester_code"));

                            examItem.setParentCourseName(resultSet.getString("course_name"));
                            examItem.setParentCourseShortName(resultSet.getString("course_short_name"));
                            examItem.setDateOfExam(resultSet.getString("exam_date"));
                            examItem.setEnrolmentCloseDate(resultSet.getString("enrolment_close_date"));

                            //    examItem.setExamScore(resultSet.getString("exam_score"));
                            //    examItem.setExamResult(resultSet.getString("examResult"));
                            examItem.setWeight(resultSet.getDouble("weight"));
                            //      examItem.setEnrolls(resultSet.getInt("enrolls"));

                            return examItem;

                        }
                    }

            );
            return examEN;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::@getStudentEnrolledExamList::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@getStudentEnrolledExamList::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<ExamEnrolment> getExamStudentGrades(int examId) {
        String sql = "SELECT   " +
                "er.student_id,  " +
                "u.user_name,  " +
                "er.exam_id,  " +
                "er.exam_score, " +
                "ex.weight, " +
                "CAST( er.exam_score * ex.weight AS DECIMAL(5,2) ) examResult, " +
                "ex.parent_course_id,  " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "ex.date_of_exam exam_date, " +
                "er.enrolls, " +
                "ex.enrolment_close_date, " +
                "ex.enrolment_start_date " +
                "FROM exam_enrolment er   " +
                "LEFT JOIN user u ON er.student_id = u.user_id  " +
                "LEFT JOIN exam ex ON er.exam_id = ex.id  " +
                "LEFT JOIN parent_courses pc ON ex.parent_course_id = pc.parent_course_id  " +
                "WHERE er.exam_id =   ?  ORDER BY er.student_id ";

        try{

            List<ExamEnrolment> examEN = getJdbcTemplate().query(
                    sql,
                    new Object[]{examId},
                    new RowMapper<ExamEnrolment>() {
                        @Override
                        public ExamEnrolment mapRow(ResultSet resultSet, int i) throws SQLException {
                            ExamEnrolment examItem = new ExamEnrolment();

                            examItem.setStudentId(resultSet.getInt("student_id"));
                            examItem.setStudentName(resultSet.getString("user_name"));
                            examItem.setExamId(resultSet.getInt("exam_id"));

                            examItem.setExamCourseCode(resultSet.getString("course_name"));
                            examItem.setExamCourseShortName(resultSet.getString("course_short_name"));
                            examItem.setExamDate(resultSet.getDate("exam_date"));

                            examItem.setExamScore(resultSet.getString("exam_score"));
                            examItem.setExamResult(resultSet.getString("examResult"));
                            examItem.setExamWeight(resultSet.getDouble("weight"));
                            examItem.setEnrolls(resultSet.getInt("enrolls"));

                            return examItem;

                        }
                    }

            );
            return examEN;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::@getExamStudentGrades::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@getExamStudentGrades::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<GradeItems> getExamStudentEnrolmentPendingListByUserId(long userId) {
        String sql = "SELECT   " +
                "er.course_id,  " +
                "c.course_short_name,  " +
                "c.course_name, " +
                "er.exam_id,  " +
                "gi.grade_item_name,  " +
                "er.enrolment_status, " +
                "DATE_FORMAT( gi.date_of_grade_item,  '%D %M %Y') exam_date, " +
                "DATE_FORMAT( gi.enrolment_close_date,  '%D %M %Y') deadline, " +
                "DATE_FORMAT( er.submission_date, '%D %M %Y') submission_date,  " +
                "(SELECT DATE_FORMAT( ee.enrolment_date , '%D %M %Y') FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = er.exam_id AND ee.student_id = er.student_id ) " +
                "enrollment_date  " +
                "FROM exam_student_request_enrolment er   " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = er.exam_id  " +
                "LEFT JOIN courses c ON c.course_id = er.course_id  " +
                "WHERE er.student_id =   ?  " +
                "AND er.enrolment_status = 0  " +
                "ORDER BY enrollment_date";

        try{

            List<GradeItems> eList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();
                            gradeItem.setCourseId(resultSet.getInt("course_id"));
                            gradeItem.setCourseShortName(resultSet.getString("course_short_name"));
                            gradeItem.setCourseName(resultSet.getString("course_name"));
                            gradeItem.setGradeItemId(resultSet.getInt("exam_id"));
                            gradeItem.setGradeItemName(resultSet.getString("grade_item_name"));
                            gradeItem.setEnrolmentCloseDate(resultSet.getString("deadline"));
                            gradeItem.setDateOfExam(resultSet.getString("exam_date"));
                            gradeItem.setEnrollmentStatus(resultSet.getInt("enrolment_status"));
                            gradeItem.setSubmissionDate(resultSet.getString("submission_date"));
                            gradeItem.setEnrollmentDate(resultSet.getString("enrollment_date"));

                            return gradeItem;

                        }
                    }

            );
            return eList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::@getExamStudentEnrolmentPendingListByUserId::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@getExamStudentEnrolmentPendingListByUserId::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public int getCountOfPendingExamsByUserId(long userId) {
        String sql = "SELECT   " +
                "COUNT(*) " +
                "FROM exam_student_request_enrolment er   " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = er.exam_id  " +
                "LEFT JOIN courses c ON c.course_id = er.course_id  " +
                "WHERE er.student_id =   ? " +
                "AND er.enrolment_status = 0  " ;

        try{
            int count  = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::getCountOfPendingExamsByUserId::EMPTY !");
            // e.printStackTrace();
            return 0;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::getCountOfPendingExamsByUserId::ERROR !");
            e.printStackTrace();
            return  0;
        }
    }

    @Override
    public List<GradeItems> getExamStudentEnrolmentConfirmationListByUserId(long userId) {
        String sql = "SELECT   " +
                "er.course_id,  " +
                "c.course_short_name,  " +
                "c.course_name, " +
                "er.exam_id,  " +
                "gi.grade_item_name,  " +
                "er.enrolment_status, " +
                "DATE_FORMAT( gi.date_of_grade_item,  '%D %M %Y') exam_date, " +
                "DATE_FORMAT( gi.enrolment_close_date,  '%D %M %Y') deadline, " +
                "DATE_FORMAT( er.submission_date, '%D %M %Y') submission_date,  " +
                "(SELECT DATE_FORMAT( ee.enrolment_date , '%D %M %Y') FROM exam_enrolment ee WHERE ee.is_Enrolled = 1 AND ee.exam_id = er.exam_id AND ee.student_id = er.student_id ) " +
                "enrollment_date  " +
                "FROM exam_student_request_enrolment er   " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = er.exam_id  " +
                "LEFT JOIN courses c ON c.course_id = er.course_id  " +
                "WHERE er.student_id =   ?  " +
                "AND er.enrolment_status = 1  " +
                "ORDER BY enrollment_date";

        try{

            List<GradeItems> eList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();
                            gradeItem.setCourseId(resultSet.getInt("course_id"));
                            gradeItem.setCourseShortName(resultSet.getString("course_short_name"));
                            gradeItem.setCourseName(resultSet.getString("course_name"));
                            gradeItem.setGradeItemId(resultSet.getInt("exam_id"));
                            gradeItem.setGradeItemName(resultSet.getString("grade_item_name"));
                            gradeItem.setEnrolmentCloseDate(resultSet.getString("deadline"));
                            gradeItem.setDateOfExam(resultSet.getString("exam_date"));
                            gradeItem.setEnrollmentStatus(resultSet.getInt("enrolment_status"));
                            gradeItem.setSubmissionDate(resultSet.getString("submission_date"));
                            gradeItem.setEnrollmentDate(resultSet.getString("enrollment_date"));

                            return gradeItem;

                        }
                    }

            );
            return eList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::@getExamStudentEnrolmentConfirmationListByUserId::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@getExamStudentEnrolmentConfirmationListByUserId::EMPTY !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public int getCountOfEnrolledExams(int pCourseId, long userId){
        String sql = "SELECT  COUNT(*) " +
                "FROM exam_enrolment er   " +
                "LEFT JOIN exam ex ON er.exam_id = ex.id  " +
//                "LEFT JOIN parent_courses pc ON ex.parent_course_id = pc.parent_course_id  " +
                "WHERE ex.parent_course_id =  ?  AND er.student_id = ?  " ;

        try{
            int count  = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{pCourseId, userId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::getCountOfEnrolledExamsByUserId::EMPTY !");
            // e.printStackTrace();
            return 0;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::getCountOfEnrolledExamsByUserId::ERROR !");
            e.printStackTrace();
            return  0;
        }
    }

    @Override
    public int getCountOfEnrolledExamsByUserId(long userId) {
        String sql = "SELECT   " +
                "COUNT(*) " +
                "FROM exam_enrolment er   " +
            //    "LEFT JOIN grade_items gi ON gi.grade_item_id = er.exam_id  " +
             //   "LEFT JOIN courses c ON c.course_id = er.course_id  " +
                "WHERE er.student_id =  ?  " +
                "AND er.is_Enrolled = 1  " ;

        try{
            int count  = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::getCountOfEnrolledExamsByUserId::EMPTY !");
            // e.printStackTrace();
            return 0;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::getCountOfEnrolledExamsByUserId::ERROR !");
            e.printStackTrace();
            return  0;
        }


    }

    @Override
    public int getCourseExamEnrollmentCount(int courseId,long userId) {
        String sql ="SELECT " +
                "COUNT(*) " +
                "FROM exam_enrolment ee " +
                "WHERE ee.course_id =  ? " +
                "AND ee.student_id =  ?";

        try {
            int count = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseId,userId},
                    Integer.class
            );
            return count;

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
    public int getExamEnrolmentAttemptsCountUnderSameParent(long parentCourseId, long studentId) {
        String sql ="SELECT COUNT(*) " +
                "FROM exam_enrolment ee " +
                "LEFT JOIN exam ex ON ex.id = ee.exam_id " +
              //  "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
             //   "LEFT JOIN parent_courses pc ON pc.parent_course_id = ex.parent_course_id " +
                "WHERE ee.is_Enrolled = 1 " +
                "AND ee.has_unEnrolled = 0 " +
                "AND ex.parent_course_id = ? " +
                "AND ee.student_id =  ? ";

        try {
            int count = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{parentCourseId,studentId},
                    Integer.class
            );
            return count;

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
    public int getParentCourseId(long examId){
        String sql =
                "SELECT ex.parent_course_id " +
                "FROM exam ex " +
                "WHERE ex.id =  ? ";

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
    public List<ExamEnrolment> getClearanceEnrolledStudentsAddGradesList(int clearanceExamId) {
        String sql = "SELECT    " +
                "u.user_name,  " +
                "clee.student_id,  " +
                "clee.score,  " +
                "c.class_name  " +
                "FROM clearance_exam_enrolment clee   " +
                "LEFT JOIN user u ON clee.student_id = u.user_id   " +
                "LEFT JOIN class c ON u.class_id = c.class_id   " +
                "WHERE   " +
                "clee.clearance_exam_id =  ? " +
                "AND clee.is_enrolled = 1  " +
                "AND u.status = 'active'  " +
                "ORDER BY u.user_name";


        try{

            List<ExamEnrolment> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{clearanceExamId},
                    new RowMapper<ExamEnrolment>() {
                        @Override
                        public ExamEnrolment mapRow(ResultSet resultSet, int i) throws SQLException {
                            ExamEnrolment grade = new ExamEnrolment();
                            grade.setStudentId(resultSet.getLong("student_id"));
                            grade.setStudentName(resultSet.getString("user_name"));
                            grade.setClassName(resultSet.getString("class_name"));
                            grade.setClexamGrade(resultSet.getString("score"));
                            return grade;
                        }
                    }

            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::@getExamStudentEnrollmentAddGradesList::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::@getExamStudentEnrollmentAddGradesList::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public int getEnrollsOfExam( int examId, long studentId){
        String sql = "SELECT  enrolls " +
                "FROM exam_enrolment er   " +
                "WHERE er.exam_id =  ?  AND er.student_id = ?  " ;

        try{
            int count  = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{examId, studentId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl::getEnrollsOfExam::EMPTY !");
            // e.printStackTrace();
            return 0;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl::getEnrollsOfExam::ERROR !");
            e.printStackTrace();
            return  0;
        }
    }

    @Override
    public String updateExam1(int courseId, long studentId, String examResult){
        String sql = "UPDATE  course_enrolment SET exam1 = ? " +
                "WHERE course_id = ? AND student_id = ? AND active = 0";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ examResult, courseId, studentId}
        );
        if(1 == returnValue)
            return "EXAM GRADE In Course SUCCESSFULLY UPDATED  ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String updateExam2(int courseId, long studentId, String examResult){
        String sql = "UPDATE  course_enrolment SET exam2 = ? " +
                "WHERE course_id = ? AND student_id = ? AND active = 0";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ examResult, courseId, studentId}
        );
        if(1 == returnValue)
            return "EXAM GRADE In Course SUCCESSFULLY UPDATED  ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String updateExam3(int courseId, long studentId, String examResult){
        String sql = "UPDATE  course_enrolment SET exam3 = ? " +
                "WHERE course_id = ? AND student_id = ? AND active = 0";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ examResult, courseId, studentId}
        );
        if(1 == returnValue)
            return "EXAM GRADE In Course SUCCESSFULLY UPDATED  ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String updateFinal1(int courseId, long studentId) {
        String sql = "UPDATE  course_enrolment SET final1 = CONVERT(course_score + exam1,DECIMAL(5,2)), final_all = final1 " +
                     "WHERE course_id = ? AND student_id = ? AND active = 0";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ courseId, studentId}
        );
        if(1 == returnValue)
            return "EXAM GRADE In Course SUCCESSFULLY UPDATED  ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String updateFinal2(int courseId, long studentId) {
        String sql = "UPDATE  course_enrolment SET final2 = CONVERT(course_score + exam2,DECIMAL(5,2)), final_all = concat(final1, \"/\", final2)" +
                "WHERE course_id = ? AND student_id = ? AND active = 0";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{courseId, studentId}
        );
        if(1 == returnValue)
            return "EXAM GRADE In Course SUCCESSFULLY UPDATED  ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String updateFinal3(int courseId, long studentId) {
        String sql = "UPDATE  course_enrolment SET final3 = CONVERT(course_score + exam3,DECIMAL(5,2)), final_all = concat(final1, \"/\", final2, \"/\", final3) " +
                "WHERE course_id = ? AND student_id = ? AND active = 0";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{courseId, studentId}
        );
        if(1 == returnValue)
            return "EXAM GRADE In Course SUCCESSFULLY UPDATED  ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String updateCourseHighest(int courseId, long studentId, String examHighest){
        String sql = "UPDATE  course_enrolment SET highest_grade = ? " +
                "WHERE course_id = ? AND student_id = ? AND active = 0";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{examHighest, courseId, studentId}
        );
        if(1 == returnValue)
            return "Course Highest Grade SUCCESSFULLY UPDATED  ";
        else
            return "Course Highest Grade FAILURE MESSAGE";
    }

    @Override
    public String updateCourseFinish(int courseId, long studentId) {
        String sql = "UPDATE  course_enrolment SET course_finished = 1 " +
                "WHERE course_id = ? AND student_id = ? AND active = 0";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{courseId, studentId}
        );
        if(1 == returnValue)
            return "Finish Course SUCCESSFULLY UPDATED  ";
        else
            return "Finish Course FAILURE MESSAGE";
    }

    @Override
    public String updateCourseNotFinish(int courseId, long studentId) {
        String sql = "UPDATE  course_enrolment SET course_finished = 0 " +
                "WHERE course_id = ? AND student_id = ? AND active = 0";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{courseId, studentId}
        );
        if(1 == returnValue)
            return "Finish Course SUCCESSFULLY UPDATED  ";
        else
            return "Finish Course FAILURE MESSAGE";
    }



}
