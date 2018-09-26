package build.dao.impl;

import build.model.CourseEnrolment;
import build.dao.CourseEnrolmentDao;
import build.model.Courses;
import build.row_mapper.CourseEnrolmentRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 15/08/2017.
 */
public class CourseEnrolmentDaoImpl extends JdbcDaoSupport implements CourseEnrolmentDao{


    @Override
    public String createCourseEnrolment(CourseEnrolment courseEnrolment) {

        String sql = "INSERT INTO course_enrolment (course_id,student_id,is_enrolled,enrolment_date,has_unenrolled,unenrolment_date)VALUES(?,?,?,?,?,?) ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{
                        courseEnrolment.getCourseId(),
                        courseEnrolment.getStudentId(),
                        courseEnrolment.getIsEnrolled(),
                        courseEnrolment.getEnrolmentDate(),
                        courseEnrolment.getHasUnenrolled(),
                        courseEnrolment.getUnEnrolmentDate()

                }
        );

        if(1 == returnValue)
            return "# New CourseEnrolment on >>> "+courseEnrolment.getEnrolmentDate()+" CREATED SUCCESSFULLY ";
        else
            return "# CREATION FAILURE";


    }

    @Override
    public String enrollStudentInCourse(CourseEnrolment courseEnrolment) {
        String sql = "INSERT INTO course_enrolment (course_id ,student_id) VALUES( ?,? )";
       try{
           int returnValue = getJdbcTemplate().update(
                   sql,
                   new Object[]{ courseEnrolment.getCourseId(),courseEnrolment.getStudentId() }
           );
           if(1 == returnValue)
           {
               System.out.println("STUDENT HAS SUCCESSFULLY BEEN ENROLLED IN COURSE #"+courseEnrolment.getCourseId());
               return "200";
           }
           else{
               System.out.println("FAILED TO ENROLL STUDENT IN COURSE");
               return"400";
           }
       }catch(Exception e){
           e.printStackTrace();
           System.out.println("SYSTEM FAILURE COULD NOT ENROLL STUDENT");
           return "400";
       }

    }

    @Override
    public String removeStudentFromCourse(CourseEnrolment courseEnrolment) {
        String sql = "UPDATE course_enrolment SET is_enrolled = 0 WHERE course_id =? AND student_id = ? ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{courseEnrolment.getCourseId(),courseEnrolment.getStudentId()}
        );
        if(1 == returnValue)
            return "STUDENT HAS SUCCESSFULLY BEEN REMOVED FROM COURSE #"+courseEnrolment.getCourseId();
        else
            return "QUERY FAILURE MESSAGE";
    }


    @Override
    public String deleteCourseEnrolment(CourseEnrolment courseEnrolment) {
        return null;
    }

    @Override
    public Boolean checkIfStudentWasPreviouslyEnrolled(CourseEnrolment courseEnrolment) {
        String sql = "SELECT id FROM course_enrolment WHERE course_id = ? AND student_id = ?";
        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseEnrolment.getCourseId(),courseEnrolment.getStudentId()},
                    Integer.class
            );
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
            return true;
    }

    @Override
    public Boolean setEnrolledCourseInactive( int courseId , long userId ){
        String sql = "UPDATE course_enrolment SET active = 1 WHERE course_id = ? AND student_id = ? AND retake = 1 ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ courseId, userId }
            );
            if(1 == returnValue)
                return true;
            else
                return false;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public Boolean checkIfStudentWasPreviouslyEnrolled(int courseId , long userId) {
        String sql = "SELECT parent_course_id FROM child_courses  WHERE child_course_id = ? " +
                "AND parent_course_id IN ( SELECT ccc.parent_course_id FROM child_courses ccc LEFT JOIN course_enrolment ce ON ccc.child_course_id = ce.course_id " +
                "AND ce.student_id = ? ) ";
        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseId , userId},
                    Integer.class
            );
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean checkIfStudentIsCurrentlyEnrolledInCourse(int courseId, long userId) {
        String sql = "SELECT ce.id" +
                " FROM course_enrolment ce " +
                "WHERE ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0  " +
                "AND ce.course_id =  ?  " +
                "AND ce.student_id = ? ";

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseId,userId},
                    Integer.class
            );
        } catch (EmptyResultDataAccessException e) {
            return false;
        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public String enrollPreviouslyEnrolledStudent(CourseEnrolment courseEnrolment) {
        String sql = "UPDATE course_enrolment SET is_enrolled = 1 WHERE course_id =? AND student_id = ? ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{courseEnrolment.getCourseId(),courseEnrolment.getStudentId()}
        );
        if(1 == returnValue)
            return "STUDENT HAS SUCCESSFULLY BEEN RE-ENROLLED INTO COURSE #"+courseEnrolment.getCourseId();
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public List<CourseEnrolment> displayCourseEnrolment() {
        return null;
    }

    @Override
    public boolean checkIfStudentHasAlreadyMadeAnEnrollmentRequest(int courseId, long userId) {
        String sql  = "SELECT " +
                "id " +
                "FROM course_student_request_enrolment c " +
                "WHERE c.enrolment_status = 0 " +
                "AND c.course_id =  ? " +
                "AND c.student_id = ?";

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseId,userId},
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
    public String studentMakeCourseEnrollmentRequest(int courseId, long userId) {
        String sql = "INSERT INTO  course_student_request_enrolment(course_id,student_id) VALUES(?,?)";

        try {

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId,userId }
            );
            if(1 == returnValue){
                System.out.println("-----::STUDENT COURSE REQUEST RECEIVED SUCCESSFULLY");
                return "200";
            }else{
                System.out.println("-----::STUDENT COURSE REQUEST FAILED");
                return "401";
            }

        }catch (Exception e){
            System.out.println("@StudentCourseEnrolmentDaoImpl::@studentMakeCourseEnrollmentRequest::SYSTEM ERROR!!!");
            e.printStackTrace();
            return "400";
        }
    }

    @Override
    public String acceptStudentCourseEnrollmentRequest(int courseId, long userId) {
       String sql = "UPDATE course_student_request_enrolment SET enrolment_status = 1 WHERE course_id = ? AND student_id = ?" ;
        try {

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId ,userId }
            );
            if(1 == returnValue){
                System.out.println("-----::STUDENT COURSE REQUEST ACCEPTED SUCCESSFULLY");
                return "200";
            }else{
                System.out.println("-----::STUDENT COURSE ACCEPTANCE FAILURE");
                return "401";
            }

        }catch (Exception e){
            System.out.println("@StudentCourseEnrolmentDaoImpl::@acceptStudentCourseEnrollmentRequest::SYSTEM ERROR ACCEPTANCE FAILLED!!!");
            e.printStackTrace();
            return "400";
        }
    }

    @Override
    public String declineStudentCourseEnrollmentRequet(int courseId, long userId) {
        String  sql = "DELETE FROM  course_student_request_enrolment WHERE enrolment_status = 0 and course_id = ? AND student_id= ?";

        try {

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ courseId,userId }
            );
            if(1 == returnValue)
            {
                System.out.println("----::Student Course Enrolment Declined ");
                return "200";
            }
            else{
                System.out.println("----::Student Course Enrolment Declined for more than one instance ");
                return "200";
            }

        }catch (Exception e){
            System.out.println("@StudentCourseEnrolmentDaoImpl::@declineStudentCourseEnrollmentRequet::SYSTEM ERROR DECLINE FAILED!!!");
            e.printStackTrace();
            return "400";
        }
    }

    @Override
    public List<Courses> getCourseStudentEnrolmentPendingListByUserId(long userId) {
        String sql = "SELECT   " +
                "cr.course_id,  " +
                "c.course_short_name,  " +
                "c.course_name,  " +
                "c.teacher_id, " +
                "u.user_name, " +
                "c.course_description_En, " +
                "c.start_date," +
                "c.end_date, " +
                "cr.enrolment_status,  " +
                "DATE_FORMAT( cr.submission_date, '%D %M %Y') submission_date,  " +
                "(SELECT DATE_FORMAT( ce.enrolment_date, '%D %M %Y') FROM course_enrolment ce WHERE ce.course_id = cr.course_id AND ce.student_id = cr.student_id AND ce.is_enrolled = 1)enrolment_date   " +
                "FROM course_student_request_enrolment cr  " +
                "LEFT JOIN courses c ON c.course_id = cr.course_id  " +
                "LEFT JOIN user u ON u.user_id = c.teacher_id " +
                "WHERE cr.enrolment_status = 0  " +
                "AND cr.student_id =  ? " +
                "ORDER BY enrolment_date ";

        try{

            List<Courses> cList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();
                            courses.setCourseId(resultSet.getInt("course_id"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCurrentEnrollmentStatus(resultSet.getInt("enrolment_status"));
                            courses.setCourseEnrollmentSubmissionDate(resultSet.getString("submission_date"));
                            courses.setCoursEnrollmentDate(resultSet.getString("enrolment_date"));
                            courses.setTeacherId(resultSet.getLong("teacher_id"));
                            courses.setTeacherName(resultSet.getString("user_name"));
                            courses.setCourseDescriptionEn(resultSet.getString("course_description_En"));
                            courses.setStartDate(resultSet.getString("start_date"));
                            courses.setEndDate(resultSet.getString("end_date"));

                            return  courses;
                        }
                    }

            );
            return cList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CourseEnrolmentDaoImpl::@getCourseStudentEnrolmentPendingListByUserId::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@CourseEnrolmentDaoImpl::@getCourseStudentEnrolmentPendingListByUserId::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public int getCountOfPendingCoursesByUserId(long userId) {
        String sql = "SELECT   " +
                "COUNT(*) " +
                "FROM course_student_request_enrolment cr  " +
                "LEFT JOIN courses c ON c.course_id = cr.course_id  " +
                "LEFT JOIN user u ON u.user_id = c.teacher_id " +
                "WHERE cr.enrolment_status = 0 " +
                "AND cr.student_id =  ?";

        try{
            int count  = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CourseEnrolmentDaoImpl::getCountOfPendingCoursesByUserId::EMPTY !");
            // e.printStackTrace();
            return 0;
        }catch (Exception e){
            System.out.println("@CourseEnrolmentDaoImpl::getCountOfPendingCoursesByUserId::ERROR !");
            e.printStackTrace();
            return  0;
        }
    }

    @Override
    public List<Courses> getStudentEnrolledCoursesList(long childCourseId, long userId){
        String sql = "SELECT " +
                "pc.parent_course_id, " +
                "cc.child_course_id, " +
                "pc.course_short_name, " +
                "pc.course_name , " +
                "cc.teacher_id,  " +
                "cc.enrolment_start_date, " +
                "cc.enrolment_deadline, " +
                "ce.id, " +
                "u.user_name,  " +
                "pc.description,  " +
                "s.semester_id,  " +
                "s.semester_code  " +
                "FROM course_enrolment ce " +
                "LEFT JOIN child_courses cc ON ce.course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "LEFT JOIN user u ON u.user_id = cc.teacher_id " +
                "WHERE ce.student_id = ? AND cc.parent_course_id IN ( SELECT ccc.parent_course_id FROM child_courses ccc WHERE  ccc.child_course_id = ? ) ";

        try{

            List<Courses> cList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId, childCourseId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();
                            courses.setEnrollmentId(resultSet.getInt("id"));
                            courses.setCourseId(resultSet.getInt("child_course_id"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setCoursEnrollmentDate(resultSet.getString("enrolment_start_date"));
                            courses.setEnrollmentDeadline(resultSet.getString("enrolment_deadline"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setTeacherId(resultSet.getLong("teacher_id"));
                            courses.setTeacherName(resultSet.getString("user_name"));
                            courses.setCourseDescriptionEn(resultSet.getString("description"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));

                            return  courses;
                        }
                    }

            );
            return cList;
        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CourseEnrolmentDaoImpl::@getStudentEnrolledCoursesList::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@CourseEnrolmentDaoImpl::@getStudentEnrolledCoursesList::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Courses> getAvailableRetakeCourses(long userId){
        String sql = "SELECT " +
                "pc.parent_course_id, " +
                "cc.child_course_id, " +
                "pc.course_short_name, " +
                "pc.course_name , " +
                "cc.teacher_id,  " +
                "cc.enrolment_start_date, " +
                "cc.enrolment_deadline, " +
                "u.user_name,  " +
                "pc.description,  " +
                "s.semester_id,  " +
                "s.semester_code  " +
                "FROM child_courses cc " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "LEFT JOIN user u ON u.user_id = cc.teacher_id " +
                "WHERE cc.parent_course_id IN ( SELECT ccc.parent_course_id FROM course_enrolment ce, child_courses ccc WHERE  ce.course_id = ccc.child_course_id AND ce.student_id = ? ) " +
                "AND cc.child_course_id NOT IN ( SELECT ccc2.child_course_id FROM course_enrolment ce2, child_courses ccc2 WHERE ce2.course_id = ccc2.child_course_id " +
                "AND ce2.student_id = ? )";

        try{

            List<Courses> cList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId, userId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();
                            courses.setCourseId(resultSet.getInt("child_course_id"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setCoursEnrollmentDate(resultSet.getString("enrolment_start_date"));
                            courses.setEnrollmentDeadline(resultSet.getString("enrolment_deadline"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setTeacherId(resultSet.getLong("teacher_id"));
                            courses.setTeacherName(resultSet.getString("user_name"));
                            courses.setCourseDescriptionEn(resultSet.getString("description"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));

                            return  courses;
                        }
                    }

            );
            return cList;
        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CourseEnrolmentDaoImpl::@getAvailableRetakeCourses::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@CourseEnrolmentDaoImpl::@getAvailableRetakeCourses::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Courses> getCourseStudentEnrolmentConfirmationListByUserId(long userId) {
        String sql = "SELECT " +
                "pc.parent_course_id, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "pc.course_name , " +
                "cc.teacher_id,  " +
                "u.user_name,  " +
                "pc.description,  " +
                "cc.enrolment_deadline,  " +
                "ce.is_enrolled,  " +
                "s.semester_id,  " +
                "s.semester_code,    " +
                "ce.enrolment_date  " +
                "FROM course_enrolment ce " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ce.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id  " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id  " +
                "LEFT JOIN USER u ON u.user_id = cc.teacher_id " +
                "WHERE ce.has_unenrolled = 0 " +
                "AND ce.is_enrolled = 1 " +
                "AND ce.student_id =  ? ";

        try{

            List<Courses> cList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();
                            courses.setCourseId(resultSet.getInt("child_course_id"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCurrentEnrollmentStatus(resultSet.getInt("is_enrolled"));
                            courses.setTeacherId(resultSet.getLong("teacher_id"));
                            courses.setTeacherName(resultSet.getString("user_name"));
                            courses.setCourseDescriptionEn(resultSet.getString("description"));
//                            courses.setStartDate(resultSet.getString("start_date"));
                            courses.setEndDate(resultSet.getString("enrolment_deadline"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));

                            return  courses;
                        }
                    }

            );
            return cList;
        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CourseEnrolmentDaoImpl::@getCourseStudentEnrolmentConfirmationListByUserId::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@CourseEnrolmentDaoImpl::@getCourseStudentEnrolmentConfirmationListByUserId::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public int getCountOfEnrolledCoursesByUserId(long userId) {
        String sql = "SELECT   " +
                "COUNT(*) " +
                "FROM course_student_request_enrolment cr  " +
                "LEFT JOIN courses c ON c.course_id = cr.course_id  " +
                "LEFT JOIN user u ON u.user_id = c.teacher_id " +
                "WHERE cr.enrolment_status = 1 " +
                "AND cr.student_id =   ? ";


        try{
            int count  = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return count;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CourseEnrolmentDaoImpl::getCountOfEnrolledCoursesByUserId::EMPTY !");
            // e.printStackTrace();
            return 0;
        }catch (Exception e){
            System.out.println("@CourseEnrolmentDaoImpl::getCountOfEnrolledCoursesByUserId::ERROR !");
            e.printStackTrace();
            return  0;
        }
    }

    @Override
    public List<CourseEnrolment> getAllRetakeCourseGradesList(){
        String sql = "SELECT er.id enId, er.student_id, u.user_name, er.course_score, er.exam1, er.exam2, er.exam3, er.final1, er.final2, er.final3, er.final_all, er.highest_grade, " +
                "er.course_id, pc.course_name, pc.course_short_name, pc.course_short_name_cn " +
                "FROM course_enrolment er " +
                "LEFT JOIN user u ON er.student_id = u.user_id " +
                "LEFT JOIN class c ON u.class_id = c.class_id " +
                "LEFT JOIN child_courses cc ON er.course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE  er.active = 0 AND er.is_enrolled = 1 AND er.retake = 1 " +
                "ORDER BY er.student_id, er.course_id DESC  ";
        try{
            List<CourseEnrolment> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{},
                    new RowMapper<CourseEnrolment>() {
                        @Override
                        public CourseEnrolment mapRow(ResultSet resultSet, int i) throws SQLException {
                            CourseEnrolment courseGrade = new CourseEnrolment();

                            courseGrade.setId(resultSet.getInt("enId"));
                            courseGrade.setStudentId(resultSet.getLong("student_id"));
                            courseGrade.setStudentName(resultSet.getString("user_name"));

                            courseGrade.setCourseScore(resultSet.getString("course_score"));
                            courseGrade.setExam1(resultSet.getString("exam1"));
                            courseGrade.setExam2(resultSet.getString("exam2"));
                            courseGrade.setExam3(resultSet.getString("exam3"));
                            courseGrade.setFinal1(resultSet.getString("final1"));
                            courseGrade.setFinal2(resultSet.getString("final2"));
                            courseGrade.setFinal3(resultSet.getString("final3"));
                            courseGrade.setFinalAll(resultSet.getString("final_all"));
                            courseGrade.setHighest(resultSet.getString("highest_grade"));
                            courseGrade.setCourseId(resultSet.getInt("course_id"));
                            courseGrade.setCourseCode(resultSet.getString("course_name"));
                            courseGrade.setCourseName(resultSet.getString("course_short_name"));
                            courseGrade.setCourseNameCN(resultSet.getString("course_short_name_cn"));
                            return courseGrade;
                        }
                    }
            );
            return courseDetails;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CourseEnrolment> getAllRetakeCourseGradesListBySemesterAndMajor(int semesterId, int majorId){
        String sql = "SELECT er.id enId, er.student_id, u.user_name, er.course_score, er.exam1, er.exam2, er.exam3, er.final1, er.final2, er.final3, er.final_all, er.highest_grade, " +
                "er.course_id, pc.course_name, pc.course_short_name, pc.course_short_name_cn, s.semester_code " +
                "FROM course_enrolment er " +
                "LEFT JOIN user u ON er.student_id = u.user_id " +
                "LEFT JOIN class c ON u.class_id = c.class_id " +
                "LEFT JOIN child_courses cc ON er.course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE  er.active = 0 AND er.is_enrolled = 1 AND er.retake = 1 AND cc.semester_id = ? AND c.major_id = ? " +
                "ORDER BY er.student_id, er.course_id DESC  ";
        try{
            List<CourseEnrolment> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{ semesterId, majorId },
                    new RowMapper<CourseEnrolment>() {
                        @Override
                        public CourseEnrolment mapRow(ResultSet resultSet, int i) throws SQLException {
                            CourseEnrolment courseGrade = new CourseEnrolment();

                            courseGrade.setId(resultSet.getInt("enId"));
                            courseGrade.setStudentId(resultSet.getLong("student_id"));
                            courseGrade.setStudentName(resultSet.getString("user_name"));

                            courseGrade.setCourseScore(resultSet.getString("course_score"));
                            courseGrade.setExam1(resultSet.getString("exam1"));
                            courseGrade.setExam2(resultSet.getString("exam2"));
                            courseGrade.setExam3(resultSet.getString("exam3"));
                            courseGrade.setFinal1(resultSet.getString("final1"));
                            courseGrade.setFinal2(resultSet.getString("final2"));
                            courseGrade.setFinal3(resultSet.getString("final3"));
                            courseGrade.setFinalAll(resultSet.getString("final_all"));
                            courseGrade.setHighest(resultSet.getString("highest_grade"));
                            courseGrade.setCourseId(resultSet.getInt("course_id"));
                            courseGrade.setCourseCode(resultSet.getString("course_name"));
                            courseGrade.setCourseName(resultSet.getString("course_short_name"));
                            courseGrade.setCourseNameCN(resultSet.getString("course_short_name_cn"));
                            courseGrade.setSemester(resultSet.getString("semester_code"));
                            return courseGrade;
                        }
                    }
            );
            return courseDetails;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CourseEnrolment> getAllRetakeCourseGradesListBySemester(int semesterId){
        String sql = "SELECT er.id enId, er.student_id, u.user_name, er.course_score, er.exam1, er.exam2, er.exam3, er.final1, er.final2, er.final3, er.final_all, er.highest_grade, " +
                "er.course_id, pc.course_name, pc.course_short_name, pc.course_short_name_cn, s.semester_code " +
                "FROM course_enrolment er " +
                "LEFT JOIN user u ON er.student_id = u.user_id " +
                "LEFT JOIN class c ON u.class_id = c.class_id " +
                "LEFT JOIN child_courses cc ON er.course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE  er.active = 0 AND er.is_enrolled = 1 AND er.retake = 1 AND cc.semester_id = ? " +
                "ORDER BY er.student_id, er.course_id DESC  ";
        try{
            List<CourseEnrolment> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{ semesterId },
                    new RowMapper<CourseEnrolment>() {
                        @Override
                        public CourseEnrolment mapRow(ResultSet resultSet, int i) throws SQLException {
                            CourseEnrolment courseGrade = new CourseEnrolment();

                            courseGrade.setId(resultSet.getInt("enId"));
                            courseGrade.setStudentId(resultSet.getLong("student_id"));
                            courseGrade.setStudentName(resultSet.getString("user_name"));

                            courseGrade.setCourseScore(resultSet.getString("course_score"));
                            courseGrade.setExam1(resultSet.getString("exam1"));
                            courseGrade.setExam2(resultSet.getString("exam2"));
                            courseGrade.setExam3(resultSet.getString("exam3"));
                            courseGrade.setFinal1(resultSet.getString("final1"));
                            courseGrade.setFinal2(resultSet.getString("final2"));
                            courseGrade.setFinal3(resultSet.getString("final3"));
                            courseGrade.setFinalAll(resultSet.getString("final_all"));
                            courseGrade.setHighest(resultSet.getString("highest_grade"));
                            courseGrade.setCourseId(resultSet.getInt("course_id"));
                            courseGrade.setCourseCode(resultSet.getString("course_name"));
                            courseGrade.setCourseName(resultSet.getString("course_short_name"));
                            courseGrade.setCourseNameCN(resultSet.getString("course_short_name_cn"));
                            courseGrade.setSemester(resultSet.getString("semester_code"));
                            return courseGrade;
                        }
                    }
            );
            return courseDetails;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CourseEnrolment> getAllCourseGradesListByCourseId(int courseId){
        String sql = "SELECT er.id enId, er.student_id, u.user_name, er.course_score, er.exam1, er.exam2, er.exam3, er.final1, er.final2, er.final3, er.final_all, " +
                "er.course_id, pc.course_name, pc.course_short_name, s.semester_code " +
                "FROM course_enrolment er " +
                "LEFT JOIN user u ON er.student_id = u.user_id " +
                "LEFT JOIN class c ON u.class_id = c.class_id " +
                "LEFT JOIN child_courses cc ON er.course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE  er.course_id = ? AND er.active = 0 AND er.is_enrolled = 1 " +
                "ORDER BY er.student_id, er.course_id DESC  ";
        try{
            List<CourseEnrolment> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<CourseEnrolment>() {
                        @Override
                        public CourseEnrolment mapRow(ResultSet resultSet, int i) throws SQLException {
                            CourseEnrolment courseGrade = new CourseEnrolment();

                            courseGrade.setId(resultSet.getInt("enId"));
                            courseGrade.setStudentId(resultSet.getLong("student_id"));
                            courseGrade.setStudentName(resultSet.getString("user_name"));

                            courseGrade.setCourseScore(resultSet.getString("course_score"));
                            courseGrade.setExam1(resultSet.getString("exam1"));
                            courseGrade.setExam2(resultSet.getString("exam2"));
                            courseGrade.setExam3(resultSet.getString("exam3"));
                            courseGrade.setFinal1(resultSet.getString("final1"));
                            courseGrade.setFinal2(resultSet.getString("final2"));
                            courseGrade.setFinal3(resultSet.getString("final3"));
                            courseGrade.setFinalAll(resultSet.getString("final_all"));
                            courseGrade.setCourseId(resultSet.getInt("course_id"));
                            courseGrade.setCourseCode(resultSet.getString("course_name"));
                            courseGrade.setCourseName(resultSet.getString("course_short_name"));
                            courseGrade.setSemester(resultSet.getString("semester_code"));
                            return courseGrade;
                        }
                    }
            );
            return courseDetails;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public List<CourseEnrolment> getAllCourseGradesListByMIS(int majorId, int intakeId, int semesterId){
        String sql = "SELECT  " +
//                "m.major_id, m.major_name, m.major_short_code, " +

                "ec.student_id, u.user_name, " +

                "ec.id enId, ec.course_score, ec.exam1, ec.exam2, ec.exam3, ec.final1, ec.final2, ec.final3, ec.final_all, ec.highest_grade, " +

                "pc.parent_course_id, pc.course_name, pc.course_short_name, pc.course_type, pc.credits, " +

                "ec.course_id, cc.child_course_name, cc.min_pass_score, " +
                "s.semester_id, s.semester_code " +
                "FROM course_enrolment ec " +
                "LEFT JOIN user u ON ec.student_id = u.user_id " +
                "LEFT JOIN class c ON u.class_id = c.class_id " +
                "LEFT JOIN child_courses cc ON ec.course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE  c.major_id =  ? " +
                "AND  c.intake_period =  ? " +
                "AND   s.semester_id = ? " +
                "ORDER BY ec.student_id, ec.course_id DESC  ";

        try{
            List<CourseEnrolment> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId,intakeId,semesterId},
                    new RowMapper<CourseEnrolment>() {
                        @Override
                        public CourseEnrolment mapRow(ResultSet resultSet, int i) throws SQLException {
                            CourseEnrolment courseGrade = new CourseEnrolment();

                            courseGrade.setId(resultSet.getInt("enId"));
                            courseGrade.setStudentId(resultSet.getLong("student_id"));
                            courseGrade.setStudentName(resultSet.getString("user_name"));
                            courseGrade.setCourseScore(resultSet.getString("course_score"));
                            courseGrade.setExam1(resultSet.getString("exam1"));
                            courseGrade.setExam2(resultSet.getString("exam2"));
                            courseGrade.setExam3(resultSet.getString("exam3"));
                            courseGrade.setFinal1(resultSet.getString("final1"));
                            courseGrade.setFinal2(resultSet.getString("final2"));
                            courseGrade.setFinal3(resultSet.getString("final3"));
                            courseGrade.setFinalAll(resultSet.getString("final_all"));
                            courseGrade.setHighest(resultSet.getString("highest_grade"));
                            courseGrade.setCourseId(resultSet.getInt("course_id"));
                            courseGrade.setCourseCode(resultSet.getString("course_name"));
                            courseGrade.setCourseName(resultSet.getString("course_short_name"));
                            return courseGrade;
                        }
                    }
            );
            return courseDetails;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String updateCourseGrade(long userId, int courseId, String score){
        String sql="UPDATE course_enrolment " +
                "SET  course_score =  ? " +
                "WHERE course_id = ? AND student_id = ? ";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ score,courseId, userId }
            );
            if(1 == returnValue)
                return "@CourseDaoImpl::@updateCourseSettings::COURSE UPDATE SUCCESSFUL ";
            else
                return "@CourseDaoImpl::@updateCourseSettings::UPDATE FAILED ";


        }catch (Exception e){
            System.out.println("@CourseDaoImpl::@updateCourseSettings::UPDATE FAILED");
            e.printStackTrace();
            return "@CourseDaoImpl::@updateCourseSettings::UPDATE FAILED ";
        }
    }

    @Override
    public String getCourseHighestGrade(int courseId, long userId){
        String sql = "SELECT cr.highest_grade FROM course_enrolment cr " +
                "WHERE cr.course_id = ? AND cr.student_id = ? AND cr.active = 0 ";

        try{
            String score  = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseId, userId},
                    String.class
            );
            return score;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CourseEnrolmentDaoImpl::getCourseScore::EMPTY !");
            // e.printStackTrace();
            return "getCourseScore sucess";
        }catch (Exception e){
            System.out.println("@CourseEnrolmentDaoImpl::getCourseScore::ERROR !");
            e.printStackTrace();
            return  "getCourseScore error";
        }
    }

    @Override
    public String getCourseScore(int courseId, long userId){
        String sql = "SELECT cr.course_score FROM course_enrolment cr " +
                "WHERE cr.course_id = ? AND cr.student_id = ? AND cr.active = 0 ";

        try{
            String score  = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseId, userId},
                    String.class
            );
            return score;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CourseEnrolmentDaoImpl::getCourseScore::EMPTY !");
            // e.printStackTrace();
            return "getCourseScore sucess";
        }catch (Exception e){
            System.out.println("@CourseEnrolmentDaoImpl::getCourseScore::ERROR !");
            e.printStackTrace();
            return  "getCourseScore error";
        }
    }

    @Override
    public int getCourseId(long parentCourseId, long userId){
        String sql = "SELECT cr.course_id FROM course_enrolment cr, child_courses cc " +
                "WHERE cr.course_id = cc.child_course_id " +
                "AND cc.parent_course_id = ? AND cr.student_id = ? AND cr.active = 0 ";

        try{
            int score  = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{parentCourseId, userId},
                    Integer.class
            );
            return score;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CourseEnrolmentDaoImpl::getCourseId::EMPTY !");
            // e.printStackTrace();
            return 0;
        }catch (Exception e){
            System.out.println("@CourseEnrolmentDaoImpl::getCourseId::ERROR !");
            e.printStackTrace();
            return  0;
        }
    }

    @Override
    public int isCoursePassed(long childCourseId, long userId){
        String sql = "SELECT cr.course_finished FROM course_enrolment cr " +
                "WHERE cr.child_course_id = ? AND cr.student_id = ? ";

        try{
            int score  = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{childCourseId, userId},
                    Integer.class
            );
            return score;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CourseEnrolmentDaoImpl::getCourseId::EMPTY !");
            // e.printStackTrace();
            return 0;
        }catch (Exception e){
            System.out.println("@CourseEnrolmentDaoImpl::getCourseId::ERROR !");
            e.printStackTrace();
            return  0;
        }
    }



}
