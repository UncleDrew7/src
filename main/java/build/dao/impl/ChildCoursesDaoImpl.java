package build.dao.impl;

import build.model.ChildCourses;
import build.dao.ChildCoursesDao;

import build.model.GradeItems;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 14/12/2017.
 */
public class ChildCoursesDaoImpl extends JdbcDaoSupport implements ChildCoursesDao{
    @Override
    public String superDeleteAllChildCourse() {
        String sql = "DELETE FROM child_courses";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql
            );
            if(returnValue == 1){
                System.out.println("SUPER DELETE ALL :: CHILD COURSES ALL DELETED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("CHILD COURSE ALL DELETE  FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String createNewChildCourse(ChildCourses childCourse) {
        String sql = "INSERT INTO child_courses ( parent_course_id,teacher_id,semester_id,enrolment_start_date,enrolment_deadline,created_by) VALUES(?,?,?,?,?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{childCourse.getParentCourseId(),childCourse.getTeacherId(),childCourse.getSemesterId(),childCourse.getEnrolmentStartDate(),childCourse.getEnrolmentDeadline(),childCourse.getCreatedBy()}
            );
            if(returnValue == 1){
                System.out.println("CHILD COURSE CREATED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("CHILD COURSE CREATE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INTERNAL SYSTEM QUERY ERROR!!");
            return "400";
        }
    }

    @Override
    public String updateChildCourse(ChildCourses childCourse) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp updateTimestamp = new java.sql.Timestamp(date.getTime());

        String sql =  "UPDATE child_courses " +
                "SET  " +
                "teacher_id =  ?, " +
                "semester_id =  ?, " +
                "enrolment_deadline =  ?  ," +
                "enrolment_start_date = ? , " +
                "updated_at = ?  " +
                "WHERE child_course_id = ?;";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{childCourse.getTeacherId(),childCourse.getSemesterId(),childCourse.getEnrolmentDeadline(),childCourse.getEnrolmentStartDate(),updateTimestamp,childCourse.getChildCourseId()}
            );
            if(returnValue == 1){
                System.out.println("CHILD COURSE UPDATED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("CHILD COURSE UPDATED FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INTERNAL SYSTEM QUERY ERROR!!");
            return "500";
        }
    }

    @Override
    public String deleteChildCourse(int childCourseId) {
        return null;
    }

    @Override
    public String deleteChildCourseFromChildCourseTable(int childCourseId) {
        String sql = "DELETE FROM child_courses WHERE child_course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{childCourseId}
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
    public String getChildCourseEnrollmentDeadlineDate(int childCourseId) {
        String sql = "SELECT  " +
                "cc.enrolment_deadline " +
                "FROM child_courses cc " +
                "WHERE cc.child_course_id = ?";
        try{
            String val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{childCourseId},
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
    public String removeStudentFromChildCourse(int childCourseId, long studentId) {
        String sql = "DELETE FROM course_enrolment WHERE course_id = ? AND  student_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{childCourseId , studentId}
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
    public int getTheLargestChildCourseId() {

        String sql = "SELECT " +
                "MAX(cs.child_course_id) " +
                "FROM child_courses cs";
        try{
            int id = getJdbcTemplate().queryForObject(
                    sql,
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
    public long getChildCourseParentId(int childCourseId) {
        String sql ="SELECT cc.parent_course_id" +
                " FROM child_courses cc  " +
                "WHERE cc.child_course_id = ?  ";
        try{
            long val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{childCourseId},
                    Long.class
            );
            return val;

        } catch (EmptyResultDataAccessException e) {
            return -1;
        }catch (Exception e ){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<ChildCourses> getAllChildCourses() {
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
                "pc.course_name,  " +
                "cc.child_course_id,  " +
                "cc.active,  " +
                "cc.enrolment_deadline,  " +
                "cc.teacher_id,  " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name,  " +
                "pc.description,  " +
                "pc.status , " +
                "(SELECT " +
                "COUNT(*) FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE cc.active = 0 " +
                "ORDER BY s.semester_code DESC ,m.major_name,pc.course_name";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                         //   childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));
                            childCourse.setActive(resultSet.getInt("active"));

                            return childCourse;
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
    public List<ChildCourses> getAllChildCourses2() {
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
                "pc.course_name,  " +
                "cc.child_course_id,  " +
                "cc.active,  " +
                "cc.enrolment_deadline,  " +
                "cc.teacher_id,  " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name,  " +
                "pc.description,  " +
                "pc.status , " +
                "(SELECT " +
                "COUNT(*) FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
//                "WHERE cc.active = 0 " +
                "ORDER BY s.semester_code DESC ,m.major_name,pc.course_name";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            //   childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));
                            childCourse.setActive(resultSet.getInt("active"));

                            return childCourse;
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
    public List<ChildCourses> getAllChildCoursesForSpecificMajorId(int majorId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status," +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE m.major_id =  ?  AND cc.active = 0 " +
                "ORDER BY s.semester_code DESC ,m.major_name,pc.course_name";




        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getAllChildCoursesForSpecificMajorId2(int majorId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status," +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE m.major_id =  ?  " +
                "ORDER BY s.semester_code DESC ,m.major_name,pc.course_name";




        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getFilteredBySemesterChildCourse(int filterId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status, " +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE s.semester_id = ? AND cc.active = 0 " +
                "ORDER BY s.semester_code DESC ";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{filterId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getFilteredBySemesterChildCourse2(int filterId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status, " +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE s.semester_id = ? " +
                "ORDER BY s.semester_code DESC ";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{filterId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getFilteredBySemesterChildCoursesForSpecificMajorId(int filterId,int majorId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status ," +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE s.semester_id = ? " +
                "AND m.major_id = ?  AND cc.active = 0 " +
                "ORDER BY s.semester_code DESC ";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{filterId,majorId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getFilteredBySemesterChildCoursesForSpecificMajorId2(int filterId,int majorId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status ," +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE s.semester_id = ? " +
                "AND m.major_id = ?  " +
                "ORDER BY s.semester_code DESC ";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{filterId,majorId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getAllChildCoursesForTeacher(long teacherId) {
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
                "pc.course_name,  " +
                "cc.child_course_id,  " +
                "cc.child_course_name,  " +
                "cc.enrolment_deadline,  " +
                "cc.teacher_id,  " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name,  " +
                "pc.description,  " +
                "pc.status , " +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE cc.teacher_id = ?  AND cc.active = 0 " +
                "ORDER BY s.semester_code DESC ,m.major_name,pc.course_name";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getAllChildCoursesForTeacher2(long teacherId) {
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
                "pc.course_name,  " +
                "cc.child_course_id,  " +
                "cc.child_course_name,  " +
                "cc.enrolment_deadline,  " +
                "cc.teacher_id,  " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name,  " +
                "pc.description,  " +
                "pc.status , " +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE cc.teacher_id = ?  " +
                "ORDER BY s.semester_code DESC ,m.major_name,pc.course_name";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getAllChildCoursesForTeacherFilteredBySemester(int filterId , long teacherId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status, " +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE s.semester_id = ? " +
                "AND cc.teacher_id = ?  AND cc.active = 0 " +
                "ORDER BY s.semester_code DESC ";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{filterId,teacherId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getAllChildCoursesForTeacherFilteredBySemester2(int filterId , long teacherId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status, " +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE s.semester_id = ? " +
                "AND cc.teacher_id = ?  " +
                "ORDER BY s.semester_code DESC ";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{filterId,teacherId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getAllChildCoursesForTeacherByMajor(int majorId, long teacherId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status," +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE m.major_id =  ?  " +
                "AND cc.teacher_id =  ?  AND cc.active = 0 " +
                "ORDER BY s.semester_code DESC ,m.major_name,pc.course_name";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId, teacherId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getAllChildCoursesForTeacherByMajor2(int majorId, long teacherId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status," +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE m.major_id =  ?  " +
                "AND cc.teacher_id =  ? " +
                "ORDER BY s.semester_code DESC ,m.major_name,pc.course_name";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId, teacherId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getAllChildCoursesForTeacherFilteredBySemesterAndMajor(int filterId, int majorId , long teacherId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status ," +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE s.semester_id = ? " +
                "AND m.major_id = ? " +
                "AND cc.teacher_id = ?  AND cc.active = 0 " +
                "ORDER BY s.semester_code DESC ";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{filterId,majorId,teacherId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getAllChildCoursesForTeacherFilteredBySemesterAndMajor2(int filterId, int majorId , long teacherId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status ," +
                "(SELECT " +
                "COUNT(*)FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE s.semester_id = ? " +
                "AND m.major_id = ? " +
                "AND cc.teacher_id = ?  " +
                "ORDER BY s.semester_code DESC ";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{filterId,majorId,teacherId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getDouble("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));

                            return childCourse;
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
    public List<ChildCourses> getAllStudentsCoursesByStudentId(long studentId) {

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
                "pc.course_name,  " +
                "cc.child_course_id,  " +
                "cc.child_course_name,  " +
                "cc.enrolment_deadline,  " +
                "cc.teacher_id,  " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name,  " +
                "pc.description,  " +
                "pc.status  " +
                "FROM child_courses cc  " +
               // "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id  " +
               // "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
              //  "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
              //  "LEFT JOIN child_course_semester ccc ON ccc.child_course_id = cc.child_course_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "LEFT JOIN course_enrolment ce ON ce.course_id = cc.child_course_id " +
                "WHERE ce.student_id = ? " +
                "AND ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "ORDER BY s.semester_code DESC ";

        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getInt("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));

                            return childCourse;
                        }
                    }

            );
            return list;

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
    public List<ChildCourses> getAllStudentsCoursesByStudentIdAndSearch(long studentId, String search) {

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
                "pc.course_name,  " +
                "cc.child_course_id,  " +
                "cc.child_course_name,  " +
                "cc.enrolment_deadline,  " +
                "cc.teacher_id,  " +
                "u.user_name teacher_name,  " +
                "pc.description,  " +
                "pc.status  " +
                "FROM child_courses cc  " +
                "LEFT JOIN user u ON cc.teacher_id = u.user_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "LEFT JOIN course_enrolment ce ON ce.course_id = cc.child_course_id " +
                "WHERE ce.student_id = ? " +
                "AND ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND ( pc.course_short_name LIKE ? OR pc.course_name LIKE ? OR u.user_name LIKE ? ) " +
                "ORDER BY s.semester_code DESC, pc.course_short_name ";

        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId, String.format("%%%s%%",search), String.format("%%%s%%",search), String.format("%%%s%%",search)},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getInt("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));

                            return childCourse;
                        }
                    }

            );
            return list;

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
    public List<ChildCourses> getStudentCoursesBySemesterId(long studentId,int semesterId) {

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
                "pc.course_name,  " +
                "cc.child_course_id,  " +
                "cc.child_course_name,  " +
                "cc.enrolment_deadline,  " +
                "cc.teacher_id,  " +
                "(SELECT u.user_name FROM USER u WHERE u.user_id = cc.teacher_id)teacher_name,  " +
                "pc.description,  " +
                "pc.status  " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "LEFT JOIN course_enrolment ce ON ce.course_id = cc.child_course_id " +
                "WHERE ce.student_id = ? " +
                "AND ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND s.semester_id =? " +
                "ORDER BY s.semester_code DESC ";

        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId,semesterId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getInt("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));

                            return childCourse;
                        }
                    }

            );
            return list;

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
    public List<ChildCourses> getStudentCoursesBySemesterIdAndSearch(long studentId,int semesterId, String search) {

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
                "pc.course_name,  " +
                "cc.child_course_id,  " +
                "cc.child_course_name,  " +
                "cc.enrolment_deadline,  " +
                "cc.teacher_id,  " +
                "u.user_name teacher_name,  " +
                "pc.description,  " +
                "pc.status  " +
                "FROM child_courses cc  " +
                "LEFT JOIN user u ON cc.teacher_id = u.user_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "LEFT JOIN course_enrolment ce ON ce.course_id = cc.child_course_id " +
                "WHERE ce.student_id = ? " +
                "AND ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND s.semester_id =? " +
                "AND ( pc.course_short_name LIKE ? OR pc.course_name LIKE ? OR u.user_name LIKE ? ) " +
                "ORDER BY s.semester_code DESC, pc.course_short_name ";

        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId,semesterId, String.format("%%%s%%",search), String.format("%%%s%%",search), String.format("%%%s%%",search) },
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getInt("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));

                            return childCourse;
                        }
                    }

            );
            return list;

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
    public ChildCourses getSingleChildCourse(int childCourseId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.credits, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "(SELECT u.user_name FROM user u WHERE u.user_id = cc.teacher_id)teacher_name, " +
                "pc.description, " +
                "pc.status " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +

                "WHERE cc.child_course_id = ? " ;
        try{
            ChildCourses object = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{childCourseId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getInt("credits"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setEnrolmentDeadline(resultSet.getString("enrolment_deadline"));
                            childCourse.setTeacherId(resultSet.getLong("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("teacher_name"));
                            childCourse.setDescription(resultSet.getString("description"));
                            childCourse.setStatus(resultSet.getString("status"));

                            return childCourse;
                        }
                    }

            );
            return object;

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
    public List<ChildCourses> getLastAddedChildCourses() {
        String sql = "SELECT  " +
                "pc.parent_course_id, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +

                "FROM child_courses cc " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "ORDER BY cc.created_at DESC  " +
                "LIMIT 5 ";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));


                            return childCourse;
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
    public List<ChildCourses> getLastEditedChildCourses() {
        String sql = "SELECT  " +
                "pc.parent_course_id, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +

                "FROM child_courses cc " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "ORDER BY cc.updated_at DESC  " +
                "LIMIT 5 ";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseShortName(resultSet.getString("course_short_name"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));

                            return childCourse;
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
    public List<ChildCourses> getChildCourseListByMajorAndSemesterId(int majorId, String semeseterCode) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_type, " +
                "pc.credits,  " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM " +
                "child_courses cc " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE m.major_id =  ? " +
                "AND s.semester_code = ?  " +
                "ORDER BY pc.parent_course_id";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId,semeseterCode},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getInt("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));

                            return childCourse;
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
    public List<ChildCourses> getChildCoursesListForSpecificParentCourse(long parentCourseId) {
        String sql = "SELECT  " +
                "m.major_id,  " +
                "m.major_name,  " +
                "m.major_short_code,  " +
                "pc.parent_course_id,  " +
                "pc.course_name,  " +
                "pc.course_type,  " +
                "pc.credits,  " +
                "cc.child_course_id,  " +
                "cc.child_course_name,  " +
                "s.semester_id,  " +
                "s.semester_code , " +
                "cc.teacher_id,  " +
                "u.user_name, " +
                "(SELECT COUNT(*) FROM course_enrolment cet " +
                "WHERE cet.is_enrolled = 1 AND cet.has_unenrolled = 0 " +
                "AND cet.course_id = cc.child_course_id)enrolledStudents " +
                "FROM  " +
                "child_courses cc  " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pc.major_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "LEFT JOIN user u ON u.user_id = cc.teacher_id " +
                "WHERE pc.parent_course_id =  ? " +
                "ORDER BY s.semester_code DESC ";
        try{
            List<ChildCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{parentCourseId},
                    new RowMapper<ChildCourses>() {
                        @Override
                        public ChildCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ChildCourses childCourse = new ChildCourses();

                            childCourse.setMajorId(resultSet.getInt("major_id"));
                            childCourse.setMajorName(resultSet.getString("major_name"));
                            childCourse.setMajorShortCode(resultSet.getString("major_short_code"));
                            childCourse.setParentCourseId(resultSet.getLong("parent_course_id"));
                            childCourse.setCourseType(resultSet.getString("course_type"));
                            childCourse.setCredits(resultSet.getInt("credits"));
                            childCourse.setSemesterId(resultSet.getInt("semester_id"));
                            childCourse.setSemesterCode(resultSet.getString("semester_code"));
                            childCourse.setCourseName(resultSet.getString("course_name"));
                            childCourse.setChildCourseId(resultSet.getInt("child_course_id"));
                            childCourse.setChildCourseName(resultSet.getString("child_course_name"));
                            childCourse.setTotalEnrolledStudents(resultSet.getInt("enrolledStudents"));
                            childCourse.setTeacherId(resultSet.getInt("teacher_id"));
                            childCourse.setTeacherName(resultSet.getString("user_name"));

                            return childCourse;
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
    public boolean checkIfChildCourseIsInSystem(int childCourseId, String childCourseName) {
        String sql = "SELECT  " +
                "cc.child_course_id " +
                "FROM child_courses cc " +
                "WHERE cc.child_course_id =  ? " +
                "OR cc.child_course_name =  ? " +
                "GROUP BY cc.child_course_name  ";

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{childCourseId,childCourseName},
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
    public boolean checkIfChildCourseIsInSystem(int childCourseId) {
        String sql = "SELECT  " +
                "cc.child_course_id " +
                "FROM child_courses cc " +
                "WHERE cc.child_course_id =  ? " ;

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{childCourseId},
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
    public boolean checkIfChildCourseExistsForParentCourseAtSemester(long parentCourseId, int semesterId, int childCourseId) {
        String sql = "SELECT " +
                "cc.child_course_id " +
                "FROM child_courses cc " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "WHERE cc.semester_id = ? " +
                "AND pc.parent_course_id =  ?  " +
                "AND cc.child_course_id =  ? ";

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{semesterId,parentCourseId,childCourseId},
                    Integer.class
            );
            return true;

        } catch (EmptyResultDataAccessException e) {
            return false;
        }catch (Exception e ){
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public boolean checkIfChildCourseExamIsInSystem(int childCourseId, String childCourseExamName) {
        String sql = "SELECT  " +
                "gi.grade_item_id " +
                "FROM grade_items gi " +
                "WHERE gi.course_id = ?  " +
                "AND gi.grade_item_name =  ? ";

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{childCourseId , childCourseExamName},
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
    public boolean checkIfChildCourseExamHasGradeItems(int childCourseId) {
        String sql  = "SELECT " +
                "gi.grade_item_id " +
                "FROM grade_items gi  " +
                "WHERE gi.course_id =  ?";

        try{

            List<GradeItems> gradeItemList  = getJdbcTemplate().query(
                    sql,
                    new Object[]{childCourseId},
                    new RowMapper<GradeItems>() {
                        @Override
                        public GradeItems mapRow(ResultSet resultSet, int i) throws SQLException {
                            GradeItems gradeItem = new GradeItems();
                            gradeItem.setGradeItemId(resultSet.getInt("grade_item_id"));
                            return gradeItem;
                        }
                    }
            );

            if(gradeItemList.isEmpty()){
                return false;
            }else{
                return true;
            }

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@GradeItemsDaoImpl::getGradeItemsWithWeightsByCourseId::EMPTY !");
            return false;
        }catch (Exception e){
            System.out.println("@GradeItemsDaoImpl::getGradeItemsWithWeightsByCourseId::ERROR !");
            e.printStackTrace();
            return true;
        }
    }
}
