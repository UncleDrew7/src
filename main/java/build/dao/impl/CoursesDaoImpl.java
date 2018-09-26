package build.dao.impl;

import build.model.Courses;
import build.dao.CoursesDao;
import build.model.Exam;
import build.model.Semester;
import build.row_mapper.CoursesRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 15/08/2017.
 */
public class CoursesDaoImpl extends JdbcDaoSupport implements CoursesDao{


    @Override
    public String deleteChildCourseFromChildCourseTable(int childCourseId) {

        String sql = "DELETE FROM child_courses WHERE child_course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{childCourseId}
            );
            System.out.println("@DELETE COMPLETED");
            return "DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String createNewCourse(Courses newCourse) {

        String sql = "INSERT INTO child_courses (course_id,teacher_id,category_id,course_short_name,course_name,course_description_En,course_description_Cn,course_type,start_date,end_date)VALUES(?,?,?,?,?,?,?,?,?,?) ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{
                        newCourse.getCourseId(),
                        newCourse.getTeacherId(),
                        newCourse.getCategoryId(),
                        newCourse.getCourseShortName(),
                        newCourse.getCourseName(),
                        newCourse.getCourseDescriptionEn(),
                        newCourse.getCourseDescriptionCn(),
                        newCourse.getCourseType(),
                        newCourse.getStartDate(),
                        newCourse.getEndDate()

                }
        );

        if(1 == returnValue)
            return "# New Course >>> "+newCourse.getCourseName()+" CREATED SUCCESSFULLY ";
        else
            return "# CREATION FAILURE";

    }

    @Override
    public String updateExistingCourse(Courses existingCourse) {
        String sql ="UPDATE child_courses " +
                "SET  " +
                "teacher_id =  ?, " +
                "course_name =  ?, " +
                "course_short_name= ? , " +
                "course_description_En = ? ," +
                "start_date =  ? , " +
                "end_date = ? " +
                "WHERE child_course_id = ?;";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ existingCourse.getTeacherId(),existingCourse.getCourseName(),existingCourse.getCourseShortName(),existingCourse.getCourseDescriptionEn(),existingCourse.getStartDate(),existingCourse.getEndDate(),existingCourse.getCourseId() }
            );
            if(1 == returnValue)
                return "@CourseDaoImpl::@updateExistingCourse::COURSE UPDATE SUCCESSFUL ";
            else
                return "@CourseDaoImpl::@updateExistingCourse::UPDATE FAILED ";


        }catch (Exception e){
            System.out.println("@CourseDaoImpl::@updateExistingCourse::UPDATE FAILED");
            e.printStackTrace();
            return "@CourseDaoImpl::@updateExistingCourse::UPDATE FAILED ";
        }

    }

    @Override
    public String closeCourse(int courseId){
        String sql="UPDATE child_courses " +
                "SET  " +
                "active =  1 " +
                "WHERE child_course_id = ?;";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ courseId }
            );
            if(1 == returnValue)
                return "@CourseDaoImpl::@closeCourse::COURSE CLOSED SUCCESSFUL ";
            else
                return "@CourseDaoImpl::@closeCourse::CLOSE FAILED ";


        }catch (Exception e){
            System.out.println("@CourseDaoImpl::@closeCourse::CLOSE FAILED");
            e.printStackTrace();
            return "@CourseDaoImpl::@closeCourse::CLOSE FAILED ";
        }
    }

    @Override
    public String closeCoursesBySemesterId(int semesterId){
        String sql="UPDATE child_courses " +
                "SET  " +
                "active =  1 " +
                "WHERE semester_id = ?;";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ semesterId }
            );
            if( returnValue >= 1 )
                return "@CourseDaoImpl::@closeCoursesBySemesterId::COURSES CLOSED SUCCESSFUL ";
            else
                return "@CourseDaoImpl::@closeCoursesBySemesterId::COURSES CLOSE FAILED ";


        }catch (Exception e){
            System.out.println("@CourseDaoImpl::@closeCoursesBySemesterId::COURSES CLOSE FAILED");
            e.printStackTrace();
            return "@CourseDaoImpl::@closeCoursesBySemesterId::COURSES CLOSE FAILED ";
        }
    }

    @Override
    public String updateCourseSettings(Courses existingCourse) {
        String sql="UPDATE child_courses " +
                "SET  " +
                "course_type =  ?, " +
                "content_language=  ?, " +
                "STATUS =  ? " +
                "WHERE course_id = ?;";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ existingCourse.getCourseType(),existingCourse.getContentLanguage(),existingCourse.getStatus(),existingCourse.getCourseId() }
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
    public List<Courses> getLastEditedCoursesList() {
        String sql ="SELECT  " +
                "pc.parent_course_id, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name " +
                "FROM child_courses cc " +
             //   "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
             //   "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "ORDER BY cc.updated_at DESC  " +
                "LIMIT 5 ";

        try{
            List<Courses> lastEditedList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses course = new Courses();
                            course.setCourseId(resultSet.getInt("child_course_id"));
                            course.setCourseShortName(resultSet.getString("child_course_name"));
                            course.setCourseName(resultSet.getString("course_name"));
                            return course;
                        }
                    }

            );
            return lastEditedList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CourseDaoImpl::@getLastEditedCoursesList::EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@CourseDaoImpl::@getLastEditedCoursesList::ERROR!!");
            e.printStackTrace();
            return  null;
        }


    }

    @Override
    public List<Courses> getLastAddedCourseList() {
        String sql = "   SELECT " +
                "   crs.course_id," +
                "   crs.course_name," +
                "   crs.course_short_name" +
                "    FROM courses crs" +
                "    ORDER BY crs.created_at DESC" +
                "    LIMIT 5;";

        try{
            List<Courses> lastEditedList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses course = new Courses();
                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setCourseName(resultSet.getString("course_name"));
                            return course;
                        }
                    }

            );
            return lastEditedList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CourseDaoImpl::@getLastEditedCoursesList::EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@CourseDaoImpl::@getLastEditedCoursesList::ERROR!!");
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public String deleteExistingCourse(int courseId) {

        String sql = "UPDATE courses " +
                "SET  " +
                "STATUS = 'not-active' " +
                "WHERE course_id = ?;";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ courseId }
            );
            if(1 == returnValue)
                return "@CourseDaoImpl::@deleteExistingCourse::COURSE DISABLED SUCCESSFUL ";
            else
                return "@CourseDaoImpl::@deleteExistingCourse::DISABLED FAILED ";


        }catch (Exception e){
            System.out.println("@CourseDaoImpl::@deleteExistingCourse::DISABLED FAILED");
            e.printStackTrace();
            return "@CourseDaoImpl::@deleteExistingCourse::DISABLED FAILED ";
        }

    }

    @Override
    public List<Courses> displayExistingCourses() {

        return null;
    }

    @Override
    public List<Courses> latestCourseForStudentsForParentCourseNotTakenByMajor(long userId, int majorId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "pc.course_type, " +
                "pc.description, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.content_language, " +
                "cc.enrolment_start_date, " +
                "cc.enrolment_deadline, " +
                "cc.teacher_id, " +
                "u.user_name, " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM child_courses cc " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id   " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id   " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id   " +
                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id  " +
                "LEFT JOIN major m ON m.major_id = pcm.major_id   " +
                "LEFT JOIN child_course_semester ccc ON ccc.child_course_id = cc.child_course_id   " +
                "LEFT JOIN semester s ON s.semester_id = ccc.semester_id   " +
                "LEFT JOIN user u  ON cc.teacher_id = u.user_id   " +
                "WHERE m.major_id = ? " +
                "AND CURDATE()>= DATE(cc.enrolment_start_date) " +
                "AND DATE(cc.enrolment_deadline)>= CURDATE() " +
                "AND pc.parent_course_id NOT IN (SELECT " +
                "pct.parent_course_id " +
                "FROM course_enrolment cet " +
                "LEFT JOIN parent_course_child_courses pcct ON pcct.child_course_id = cet.course_id   " +
                "LEFT JOIN parent_courses pct ON pct.parent_course_id = pcct.parent_course_id   " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.student_id = ? ) " +
                "ORDER BY s.semester_code DESC ,cc.enrolment_deadline ";

        try{

            List<Courses> currentCourseList = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId,userId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setCourseId(resultSet.getInt("child_course_id"));
                            courses.setCategoryId(resultSet.getInt("semester_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseDescriptionEn(resultSet.getString("description"));
                            courses.setTeacherId(resultSet.getLong("teacher_id"));
                            courses.setTeacherName(resultSet.getString("user_name"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setEndDate(resultSet.getString("enrolment_deadline"));
                            courses.setCredits(resultSet.getDouble("credits"));
                            courses.setStartDate(resultSet.getString("enrolment_start_date"));


                            return courses;
                        }
                    }
            );
            return currentCourseList;

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
    public List<Courses> activeCourseList(long userId ,int limit,int majorId) {
        String sql = "SELECT    " +
                "cc.child_course_id ,  " +
                "s.semester_id ,  " +
                "s.semester_code,  " +
                "cc.child_course_name,   " +
                "pc.parent_course_id, " +
                "pc.course_name ,  " +
                "pc.course_short_name ,  " +
                "pc.course_type,  " +
                "pc.description,  " +
                "u.user_id,   " +
                "u.user_name,   " +
                "cc.enrolment_deadline ,  " +
                "(SELECT ce.is_enrolled FROM course_enrolment ce WHERE ce.has_unenrolled = 0 AND ce.is_enrolled = 1 AND ce.course_id = cc.child_course_id AND ce.student_id =   ? )   " +
                "enrollment_status   " +
                "FROM child_courses cc  " +
                "LEFT JOIN USER u  ON cc.teacher_id = u.user_id   " +
               // "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id   " +
               // "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id   " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id   " +
               // "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id   " +
                "LEFT JOIN major m ON m.major_id = pc.major_id   " +
               // "LEFT JOIN child_course_semester ccc ON ccc.child_course_id = cc.child_course_id   " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id   " +

                "WHERE pc.parent_course_id NOT IN ( " +
                "SELECT  cc2.parent_course_id " +
                "FROM course_enrolment cet " +
                "LEFT JOIN child_courses cc2 ON cc2.child_course_id = cet.course_id " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.student_id =  ? " +
                ")   " +

                "AND m.major_id =  ?  " +
                "AND CURDATE()>= DATE(cc.enrolment_start_date) " +
                "AND DATE(cc.enrolment_deadline) >= CURDATE() " +
                "ORDER BY s.semester_code DESC    " ;
//                "LIMIT   ? ";

        try{

            List<Courses> currentCourseList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId,userId,majorId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setParentCourseId(resultSet.getInt("parent_course_id"));
                            courses.setCourseId(resultSet.getInt("child_course_id"));
                            courses.setCategoryId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setCourseDescriptionEn(resultSet.getString("description"));
                            courses.setTeacherId(resultSet.getLong("user_id"));
                            courses.setTeacherName(resultSet.getString("user_name"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setEndDate(resultSet.getString("enrolment_deadline"));
                            courses.setCurrentEnrollmentStatus(resultSet.getInt("enrollment_status"));


                            return courses;
                        }
                    }
            );
            return currentCourseList;

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
    public List<Courses> activeCourseListSearch(long userId , String search, int majorId) {
        String sql = "SELECT    " +
                "cc.child_course_id ,  " +
                "s.semester_id ,  " +
                "s.semester_code,  " +
                "cc.child_course_name,   " +
                "pc.parent_course_id, " +
                "pc.course_name ,  " +
                "pc.course_short_name ,  " +
                "pc.course_type,  " +
                "pc.description,  " +
                "u.user_id,   " +
                "u.user_name,   " +
                "cc.enrolment_deadline ,  " +
                "(SELECT ce.is_enrolled FROM course_enrolment ce WHERE ce.has_unenrolled = 0 AND ce.is_enrolled = 1 AND ce.course_id = cc.child_course_id AND ce.student_id =   ? )   " +
                "enrollment_status   " +
                "FROM child_courses cc  " +
                "LEFT JOIN USER u  ON cc.teacher_id = u.user_id   " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id   " +
                "LEFT JOIN major m ON m.major_id = pc.major_id   " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id   " +

                "WHERE pc.parent_course_id NOT IN ( " +
                "SELECT  cc2.parent_course_id " +
                "FROM course_enrolment cet " +
                "LEFT JOIN child_courses cc2 ON cc2.child_course_id = cet.course_id " +
                "WHERE cet.is_enrolled = 1 " +
                "AND cet.has_unenrolled = 0 " +
                "AND cet.student_id =  ? " +
                ")  " +

                "AND m.major_id =  ?  " +
                "AND CURDATE()>= DATE(cc.enrolment_start_date) " +
                "AND DATE(cc.enrolment_deadline) >= CURDATE() " +
                "AND ( pc.course_name LIKE  ?  OR pc.course_short_name LIKE  ? OR u.user_name LIKE ? )" +
                "ORDER BY s.semester_code DESC    " ;
//                "LIMIT   ? ";

        try{

            List<Courses> currentCourseList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId,userId,majorId, '%' + search + '%', '%' + search + '%', '%' + search + '%'},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setParentCourseId(resultSet.getInt("parent_course_id"));
                            courses.setCourseId(resultSet.getInt("child_course_id"));
                            courses.setCategoryId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setCourseDescriptionEn(resultSet.getString("description"));
                            courses.setTeacherId(resultSet.getLong("user_id"));
                            courses.setTeacherName(resultSet.getString("user_name"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setEndDate(resultSet.getString("enrolment_deadline"));
                            courses.setCurrentEnrollmentStatus(resultSet.getInt("enrollment_status"));


                            return courses;
                        }
                    }
            );
            return currentCourseList;

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
    public List<Courses> getStudentMostVisitedLatestCourses(long userId, int limit) {
        String sql = "SELECT  " +
                "cc.child_course_id ,  " +
                "s.semester_id ,   " +
                "s.semester_code,   " +
                "cc.child_course_name, " +
                "pc.course_name ,   " +
                "pc.course_type,   " +
                "pc.description,   " +
                "u.user_id,   " +
                "u.user_name,    " +
                "cc.enrolment_deadline, " +
                "ce.is_enrolled " +
                "FROM course_enrolment ce " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ce.course_id " +
                "LEFT JOIN USER u  ON cc.teacher_id = u.user_id    " +
            //    "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id    " +
            //    "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id    " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id    " +
            //    "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id   " +
                "LEFT JOIN major m ON m.major_id = pc.major_id    " +
            //    "LEFT JOIN child_course_semester ccc ON ccc.child_course_id = cc.child_course_id    " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id   " +
                "WHERE " +
                "ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND ce.student_id =  ? " +
                "ORDER BY s.semester_code DESC ,cc.child_course_name   ";
//                "LIMIT  ? ";

        try{

            List<Courses> currentCourseList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setCourseId(resultSet.getInt("child_course_id"));
                            courses.setCategoryId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseDescriptionEn(resultSet.getString("description"));
                            courses.setTeacherId(resultSet.getLong("user_id"));
                            courses.setTeacherName(resultSet.getString("user_name"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setEndDate(resultSet.getString("enrolment_deadline"));
//                            courses.setCurrentEnrollmentStatus(resultSet.getInt("enrollment_status"));


                            return courses;
                        }
                    }
            );
            return currentCourseList;

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
    public List<Courses> activeCourseListAboveLimit(long userId, int limit) {
        String sql = "SELECT  " +
                "c.course_id, " +
                "c.category_id, " +
                "c.course_name, " +
                "c.course_short_name, " +
                "c.course_type, " +
                "c.course_description_En, " +
                "u.user_id, " +
                "u.user_name, " +
                "c.start_date, " +
                "c.end_date , " +
                "(SELECT ce.is_enrolled FROM course_enrolment ce WHERE ce.has_unenrolled = 0 AND ce.is_enrolled = 1 AND ce.course_id = c.course_id AND ce.student_id =  ?) " +
                "enrollment_status " +
                "FROM courses c " +
                "LEFT JOIN user u  ON c.teacher_id = u.user_id " +
                "WHERE  c.status= 'active' " +
                "AND c.course_id NOT IN (SELECT cr.course_id FROM course_student_request_enrolment cr WHERE cr.student_id = ?) " +
                "ORDER BY enrollment_status  " +
                "LIMIT ? OFFSET ?  " ;

        try{

            List<Courses> currentCourseList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId,userId,limit,limit},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setCourseId(resultSet.getInt("course_id"));
                            courses.setCategoryId(resultSet.getInt("category_id"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseDescriptionEn(resultSet.getString("course_description_En"));
                            courses.setTeacherId(resultSet.getLong("user_id"));
                            courses.setTeacherName(resultSet.getString("user_name"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setStartDate(resultSet.getString("start_date"));
                            courses.setEndDate(resultSet.getString("end_date"));
                            courses.setCurrentEnrollmentStatus(resultSet.getInt("enrollment_status"));


                            return courses;
                        }
                    }
            );
            return currentCourseList;

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
    public List<Courses> studentMyCoursesList(long userId) {
        String sql = "SELECT " +
                        "crs.course_id," +
                        "crs.course_short_name," +
                        "crs.course_name," +
                        "usr.user_id," +
                        "usr.user_name," +
                        "crs.start_date," +
                        "crs.end_date," +
                        "crs.course_type," +
                        "crs.category_id," +
                        "crs.course_description_En," +
                        "s.semester_code" +
                        " FROM courses crs, user usr, course_enrolment ce,semester s" +
                        " WHERE crs.course_id = ce.course_id " +
                        " AND s.semester_id = crs.category_id" +
                        " AND ce.is_enrolled = 1" +
                        " AND ce.has_unenrolled = 0" +
                        " AND crs.status = 'active'" +
                        " AND crs.teacher_id = usr.user_id" +
                        " AND ce.student_id = ? " +
                        " ORDER BY crs.course_name";

        try{
            List<Courses> studentMyCourseList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new CoursesRowMapper()
            );
            return studentMyCourseList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Courses> studentCompletedCourseList(long userId) {
        String sql = "SELECT " +
                "crs.course_id," +
                "crs.course_short_name," +
                "crs.course_name," +
                "usr.user_id," +
                "usr.user_name," +
                "crs.start_date," +
                "crs.end_date," +
                "crs.course_type," +
                "crs.category_id," +
                "crs.course_description_En," +
                "s.semester_code" +
                " FROM courses crs, user usr, course_enrolment ce,semester s" +
                " WHERE crs.course_id = ce.course_id " +
                " AND s.semester_id = crs.category_id" +
                " AND ce.is_enrolled = 1" +
                " AND ce.has_unenrolled = 0" +
                " AND crs.status = 'not-active'" +
                " AND crs.teacher_id = usr.user_id" +
                " AND ce.student_id = ? " +
                " ORDER BY crs.course_name";

        try{
            List<Courses> studentMyCourseList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new CoursesRowMapper()
            );
            return studentMyCourseList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Courses> studentAllEnrolledCourses(long userId) {
        String sql = "SELECT " +
                "crs.course_id," +
                "crs.course_short_name," +
                "crs.course_name," +
                "usr.user_id," +
                "usr.user_name," +
                "crs.start_date," +
                "crs.end_date," +
                "crs.course_type," +
                "crs.category_id," +
                "crs.course_description_En," +
                "s.semester_code" +
                " FROM courses crs, user usr, course_enrolment ce,semester s" +
                " WHERE crs.course_id = ce.course_id " +
                " AND s.semester_id = crs.category_id" +
                " AND ce.is_enrolled = 1" +
                " AND ce.has_unenrolled = 0" +
                " AND crs.teacher_id = usr.user_id" +
                " AND ce.student_id = ? " +
                " ORDER BY crs.course_name";

        try{
            List<Courses> studentMyCourseList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new CoursesRowMapper()
            );
            return studentMyCourseList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Courses> studentCoursesFilteredBySemesterId(long userId, int semesterId) {
        String sql = "SELECT " +
                "crs.course_id," +
                "crs.course_short_name," +
                "crs.course_name," +
                "usr.user_id," +
                "usr.user_name," +
                "crs.start_date," +
                "crs.end_date," +
                "crs.course_type," +
                "crs.category_id," +
                "crs.course_description_En," +
                "s.semester_code" +
                " FROM courses crs, user usr, course_enrolment ce,semester s" +
                " WHERE crs.course_id = ce.course_id " +
                " AND s.semester_id = crs.category_id" +
                " AND ce.is_enrolled = 1" +
                " AND ce.has_unenrolled = 0" +
                " AND crs.teacher_id = usr.user_id" +
                " AND ce.student_id = ?   " +
                "AND crs.category_id = ? " +
                " ORDER BY crs.course_name";

        try{
            List<Courses> studentMyCourseList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId,semesterId},
                    new CoursesRowMapper()
            );
            return studentMyCourseList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Semester> getSemesterList() {
        String sql = "SELECT  " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM semester s  " +
                "ORDER BY s.semester_code DESC " +
                "LIMIT 9 ";

        try{
            List<Semester> semesterList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Semester>() {
                        @Override
                        public Semester mapRow(ResultSet resultSet, int i) throws SQLException {
                            Semester semester = new Semester();
                            semester.setSemesterId(resultSet.getInt("semester_id"));
                            semester.setSemesterCode(resultSet.getString("semester_code"));
                            return  semester;
                        }
                    }

            );
            return semesterList;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Semester> getSemesterListMore() {
        String sql = "SELECT  " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM semester s  " +
                "ORDER BY s.semester_code DESC " +
                "LIMIT 8,100 ";

        try{
            List<Semester> semesterList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Semester>() {
                        @Override
                        public Semester mapRow(ResultSet resultSet, int i) throws SQLException {
                            Semester semester = new Semester();
                            semester.setSemesterId(resultSet.getInt("semester_id"));
                            semester.setSemesterCode(resultSet.getString("semester_code"));
                            return  semester;
                        }
                    }

            );
            return semesterList;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Semester> getStudentCustomSemesterList(long userId) {
        String sql = "SELECT DISTINCT " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM course_enrolment ce " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ce.course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND ce.student_id =  ? " +
                "ORDER BY s.created_at DESC";

        try{
            List<Semester> semesterList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Semester>() {
                        @Override
                        public Semester mapRow(ResultSet resultSet, int i) throws SQLException {
                            Semester semester = new Semester();
                            semester.setSemesterId(resultSet.getInt("semester_id"));
                            semester.setSemesterCode(resultSet.getString("semester_code"));
                            return  semester;
                        }
                    }

            );
            return semesterList;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Courses getCourseAboutDetails(int childCourseId) {
        String sql ="SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "cc.child_course_id, " +
                "pc.course_short_name, " +
                "cc.content_language, " +
                "cc.enrolment_start_date, " +
                "cc.enrolment_deadline,  " +
                "pc.course_type, " +
                "cc.teacher_id, " +
                "u.user_name, " +
                "cc.semester_id, " +
                "s.semester_code, " +
                "pc.description " +
                "FROM child_courses cc " +
                "LEFT JOIN user u ON u.user_id = cc.teacher_id " +
            //    "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
             //   "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN major m ON m.major_id = pc.major_id " +
            //    "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE cc.child_course_id = ? " +
                "ORDER BY pc.parent_course_id ";

        try{
            Courses courseDetails = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{childCourseId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setContentLanguage(resultSet.getString("content_language"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseDescriptionEn(resultSet.getString("description"));
                            courses.setTeacherId(resultSet.getLong("teacher_id"));
                            courses.setTeacherName(resultSet.getString("user_name"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setEnrollmentDeadline(resultSet.getString("enrolment_deadline"));
                            courses.setStartDate(resultSet.getString("enrolment_start_date"));

                            return courses;
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
    public Courses getCourseAboutDetailsForStudentExamPage(int childCourseId, long studentId) {
        String sql = "SELECT " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "pc.description, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.enrolment_deadline, " +
                "cc.min_pass_score, " +
                "cc.teacher_id, " +
                "u.user_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "(SELECT IFNULL(COUNT(*),0) " +
                "FROM exam_enrolment eet " +
                "WHERE eet.is_Enrolled = 1 " +
                "AND eet.has_unEnrolled = 0 " +
                "AND eet.student_id =  ? )totalEnrolledExams " +
                "FROM child_courses cc " +
                "LEFT JOIN parent_course_child_courses pcc ON cc.child_course_id = pcc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pcc.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN user u ON u.user_id = cc.teacher_id " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id " +
                "WHERE cc.child_course_id =  ? ";

        try{
            Courses courseDetails = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{studentId,childCourseId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseDescriptionEn(resultSet.getString("description"));
                            courses.setTeacherId(resultSet.getLong("teacher_id"));
                            courses.setTeacherName(resultSet.getString("user_name"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setEnrollmentDeadline(resultSet.getString("enrolment_deadline"));
                            courses.setExamCount(resultSet.getInt("totalEnrolledExams"));
                            courses.setCredits(resultSet.getDouble("credits"));

                            return courses;
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
    public List<Courses> getUserCoursesGradeAverage(long userId) {
//        (SELECT ((g.grade/gi.max_grade)*gi.weight)*100 DIV 1 )final_grade
        String sql = "SELECT  " +
                "m.major_id,  " +
                "m.major_name,  " +
                "m.major_short_code,  " +
                "pc.parent_course_id,  " +
                "pc.course_name,  " +
                "pc.course_short_name,  " +
                "pc.course_type,  " +
                "pc.credits,  " +
                "cc.child_course_id,  " +
                "cc.child_course_name,  " +
                "s.semester_id,  " +
                "s.semester_code,  " +
                "ce.course_score gradeAverage " +
                "FROM course_enrolment ce  " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ce.course_id  " +
              //  "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id  " +
             //   "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id  " +
                "LEFT JOIN major m ON pc.major_id = m.major_id  " +
             //   "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id  " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id  " +
                "WHERE ce.is_enrolled = 1  " +
                "AND ce.has_unenrolled = 0  " +
                "AND ce.student_id =  ? " +
                "ORDER BY s.semester_code DESC ";

        try{

            List<Courses> coursesGradeAveragesList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setGradeAverage(resultSet.getDouble("gradeAverage"));
                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setCredits(resultSet.getDouble("credits"));

                            return  courses;
                        }
                    }
            );
            return coursesGradeAveragesList;

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
    public List<Courses> getUserCoursesGradeAverageFilterByCompletedCourses(long userId) {
        String sql = "SELECT  " +
                "crs.course_id , " +
                "crs.course_name, " +
                "crs.course_short_name, " +
                "(SELECT SUM((grade.grade/max_grade)*grade_items.weight*100)DIV 1  " +
                "FROM grade,courses,grade_items  WHERE courses.course_id = grade.course_id  " +
                "AND grade_items.grade_item_id = grade.grade_item_id AND courses.course_id = crs.course_id  AND grade.student_id = ce.student_id) " +
                "gradeAverage " +
                "FROM courses crs,course_enrolment ce " +
                "WHERE crs.course_id = ce.course_id  " +
                "AND crs.status = 'not-active' " +
                "AND ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND ce.student_id =  ? " +
                "ORDER BY crs.course_short_name";

        try{

            List<Courses> coursesGradeAveragesList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();
                            courses.setCourseId(resultSet.getInt("course_id"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setGradeAverage(resultSet.getDouble("gradeAverage"));

                            return  courses;
                        }
                    }
            );
            return coursesGradeAveragesList;

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
    public List<Courses> getUserCoursesGradeAverageFilterByAllCourses(long userId) {
        String sql = "SELECT  " +
                "crs.course_id , " +
                "crs.course_name, " +
                "crs.course_short_name, " +
                "(SELECT SUM((grade.grade/max_grade)*grade_items.weight*100)DIV 1  " +
                "FROM grade,courses,grade_items  WHERE courses.course_id = grade.course_id  " +
                "AND grade_items.grade_item_id = grade.grade_item_id AND courses.course_id = crs.course_id  AND grade.student_id = ce.student_id) " +
                "gradeAverage " +
                "FROM courses crs,course_enrolment ce " +
                "WHERE crs.course_id = ce.course_id  " +
                "AND ce.is_enrolled = 1  " +
                "AND ce.has_unenrolled = 0  " +
                "AND ce.student_id = ?  " +
                "ORDER BY crs.course_short_name";

        try{

            List<Courses> coursesGradeAveragesList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();
                            courses.setCourseId(resultSet.getInt("course_id"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setGradeAverage(resultSet.getDouble("gradeAverage"));

                            return  courses;
                        }
                    }
            );
            return coursesGradeAveragesList;

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
    public List<Courses> getUserCoursesGradeAverageFilterByCourseCategory(long userId, int semesterId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "ce.course_score gradeAverage  " +
                "FROM course_enrolment ce " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ce.course_id " +
            //    "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
             //   "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
             //   "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND ce.student_id =  ? " +
                "AND s.semester_id = ? " +
                "ORDER BY s.semester_code DESC  ";

        try{

            List<Courses> coursesGradeAveragesList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId,semesterId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setGradeAverage(resultSet.getDouble("gradeAverage"));
                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setCredits(resultSet.getDouble("credits"));

                            return  courses;
                        }
                    }
            );
            return coursesGradeAveragesList;

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
    public List<Courses> getStudentCurrentCourses(long userId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM course_enrolment ce " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ce.course_id " +
            //    "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
            //    "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
            //    "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND ce.student_id = ? " +
                "ORDER BY s.semester_code DESC ";

        try{

            List<Courses> currentCoursesList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                         //   courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            return courses;
                        }
                    }
            );
            return currentCoursesList;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Courses> getTeacherCurrentCourses(long userId) {
        String sql =  "SELECT  " +
                "pc.parent_course_id, " +
                "cc.child_course_id, " +
                "pc.course_name, " +
                "cc.child_course_name, " +
                "cc.teacher_id, " +
                "u.user_name " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN USER u ON u.user_id = cc.teacher_id " +
                "WHERE cc.teacher_id =  ? ";
        try{

            List<Courses> currentCoursesList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();
                            courses.setCourseId(resultSet.getInt("child_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            return courses;
                        }
                    }
            );
            return currentCoursesList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getStudentCurrentCoursesCount(long userId) {
        String sql = "SELECT COUNT(*) " +
                "FROM course_enrolment ce  " +
                "WHERE ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND ce.student_id =  ? ";


        try{
            int currentCourseTotal = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return currentCourseTotal;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTeacherCurrentCourseCount(long userId) {
        String sql = "SELECT COUNT(*)  " +
                "FROM child_courses cc  " +
                "WHERE cc.teacher_id =  ? ";


        try{
            int currentCourseTotal = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return currentCourseTotal;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Courses> getAllStudentCourses(long userId) {
        String sql = "SELECT  " +
                "pc.parent_course_id, " +
                "cc.child_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "cc.child_course_name, " +
                "cc.teacher_id, " +
                "u.user_name " +
                "FROM course_enrolment ce  " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ce.course_id " +
           //     "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "LEFT JOIN USER u ON u.user_id = cc.teacher_id " +
                "WHERE ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND ce.student_id =  ? ";


        try{
            List<Courses> allCoursesList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setCourseId(resultSet.getInt("child_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                         //   courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setTeacherId(resultSet.getLong("teacher_id"));
                            courses.setTeacherName(resultSet.getString("user_name"));

                            return courses;
                        }
                    }
            );
            return allCoursesList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<Courses> getAllTeacherCourses(long userId) {
        String sql = "SELECT  " +
                "pc.parent_course_id, " +
                "cc.child_course_id, " +
                "pc.course_name, " +
                "cc.child_course_name, " +
                "cc.teacher_id, " +
                "u.user_name " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id " +
                "LEFT JOIN USER u ON u.user_id = cc.teacher_id " +
                "WHERE cc.teacher_id =  ? ";


        try{
            List<Courses> allCoursesList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setCourseId(resultSet.getInt("child_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            return courses;
                        }
                    }
            );
            return allCoursesList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getAllStudentCoursesCount(long userId) {
        String sql = "SELECT COUNT(*) " +
                "FROM course_enrolment ce  " +
                "WHERE ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND ce.student_id =  ? ";


        try{
            int allStudentCouresTotal = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return allStudentCouresTotal;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getAllTeacherCoursesCount(long userId) {
        String sql = "SELECT COUNT(*)  " +
                "FROM child_courses cc  " +
                "WHERE cc.teacher_id =  ? ";


        try{
            int currentCourseTotal = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return currentCourseTotal;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getAllTeacherActiveCourseCount(long userId) {
        String sql = "SELECT COUNT(*)  " +
                "FROM child_courses cc  " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id  " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id  " +
                "WHERE CURDATE() BETWEEN DATE(s.start_date) AND DATE(s.end_date) " +
                "AND cc.teacher_id = ? ";

        try{

            int totalCourse = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return totalCourse;

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
    public int getTotalActiveCoursesCount() {
        String sql = "SELECT COUNT(*) " +
                "FROM child_courses cc " +
              //  "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE CURDATE() BETWEEN DATE(s.start_date) AND DATE(s.end_date)";

        try{

            int totalCourse = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalCourse;

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
    public int getTotalTeacherExamsCount(long teacherId) {

        String sql = "SELECT COUNT(*) " +
                "FROM grade_items gi  " +
                "LEFT JOIN courses c ON c.course_id = gi.course_id " +
                "WHERE c.teacher_id =  ?";

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
    public int getTotalCourseCountForTeacher(long teacherId) {
        String sql = "SELECT " +
                "COUNT(*) " +
                "FROM courses c " +
                "WHERE c.teacher_id =  ? ";

        try{

            int totalCourse = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId},
                    Integer.class
            );
            return totalCourse;

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
    public int getActiveCourseCountForTeacher(long teacherId) {

        String sql ="SELECT COUNT(*)  " +
                "FROM child_courses cc  " +
                "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id  " +
                "LEFT JOIN semester s ON s.semester_id = ccs.semester_id  " +
                "WHERE CURDATE() BETWEEN DATE(s.start_date) AND DATE(s.end_date) " +
                "AND cc.teacher_id = ? ";

        try{

            int totalCourse = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId},
                    Integer.class
            );
            return totalCourse;

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
    public int getTotalCourseEnrollmentCountForTeacher(long teacherId) {

        String sql = "SELECT COUNT(*) " +
                "FROM course_enrolment ce " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ce.course_id " +
                "WHERE ce.is_Enrolled = 1 " +
                "AND ce.has_unEnrolled = 0 " +
                "AND cc.teacher_id =  ?";

        try{

            int totalCourse = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId},
                    Integer.class
            );
            return totalCourse;

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
    public int getTotalParentCoursesCount() {

        String sql = "SELECT COUNT(*)" +
                "FROM  parent_courses";
        try{

            int totalCourse = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalCourse;

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
    public int getTotalChildCoursesCount() {
        String sql = "SELECT COUNT(*) " +
                "FROM  child_courses ";
        try{

            int totalCourse = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalCourse;

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
    public int getTotalSystemMajorCount() {
        String sql = "SELECT COUNT(*) " +
                "FROM major m  ";
        try{

            int totalCourse = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalCourse;

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
    public int getTotalSystemsChildCoursesEnrollmentsCount() {
        String sql = "SELECT COUNT(*) " +
                "FROM course_enrolment ce  " +
                "WHERE ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 ";
        try{

            int totalCourse = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalCourse;

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
    public int getTotalDoubleDegreeCoursesCount() {
        String sql = "SELECT COUNT(*)" +
                "    FROM courses crs" +
                "    WHERE crs.status = 'active' " +
                "    AND crs.course_type= 'double-degree'";

        try{

            int totalDoubleDegreeCourses = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalDoubleDegreeCourses;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTotalGeneralDegreeCoursesCount() {
        String sql = "SELECT COUNT(*) " +
                "FROM child_courses cc ";

        try{

            int totalGenralDegreeCourses = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalGenralDegreeCourses;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTotalCourseUnEnrolmentCount() {
        String sql = " SELECT COUNT(*)" +
                "    FROM  course_enrolment ce" +
                "    WHERE ce.is_enrolled = 0 " +
                "   AND ce.has_unenrolled = 1 ";

        try{
            int totalCourseUnEnrolments = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalCourseUnEnrolments;
        }catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Courses> getActiveCourseRequestNotifications() {

        String sql = " SELECT " +
                "    crs.course_id," +
                "    crs.course_short_name ," +
                "    crs.course_name," +
                "    ( SELECT COUNT(*) FROM  course_enrolment WHERE crs.course_id=course_enrolment.course_id AND is_enrolled = 1 )" +
                "     totalEnrollments," +
                "    COUNT(crs.course_id)totalEnrollmentRequests " +
                "    FROM courses crs,course_student_request_enrolment crsr " +
                "    WHERE crs.course_id = crsr.course_id AND crsr.enrolment_status = 0 " +
                "    GROUP BY crs.course_id " +
                "    ORDER BY totalEnrollmentRequests DESC";


        try{
            List<Courses> activeCourseRequestNotificationsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setTotalEnrollments(resultSet.getInt("totalEnrollments"));
                            course.setTotalEnrollmentRequests(resultSet.getInt("totalEnrollmentRequests"));
                            return course;
                    }
                    }
            );
            return activeCourseRequestNotificationsList;

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
    public List<Courses> getActiveCourseRequestNotificationsByTeacherId(long teacherId) {
        String sql = " SELECT " +
                "    crs.course_id," +
                "    crs.course_short_name ," +
                "    crs.course_name," +
                "    ( SELECT COUNT(*) FROM  course_enrolment WHERE crs.course_id=course_enrolment.course_id AND is_enrolled = 1 )" +
                "     totalEnrollments," +
                "    COUNT(crs.course_id)totalEnrollmentRequests " +
                "    FROM courses crs,course_student_request_enrolment crsr " +
                "    WHERE crs.course_id = crsr.course_id " +
                "    AND crsr.enrolment_status = 0 " +
                "    AND crs.teacher_id = ? " +
                "    GROUP BY crs.course_id " +
                "    ORDER BY totalEnrollmentRequests DESC";


        try{
            List<Courses> activeCourseRequestNotificationsList = getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setTotalEnrollments(resultSet.getInt("totalEnrollments"));
                            course.setTotalEnrollmentRequests(resultSet.getInt("totalEnrollmentRequests"));
                            return course;
                        }
                    }
            );
            return activeCourseRequestNotificationsList;

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
    public List<Courses> getNotActiveCourseRequestNotifications() {

        String sql = "SELECT  " +
                "crs.child_course_id, " +
                "pc.course_name, " +
                "crs.child_course_id, " +
                "crs.child_course_name , " +
                "( SELECT COUNT(*) FROM  course_enrolment WHERE crs.child_course_id=course_enrolment.course_id AND is_enrolled = 1 ) " +
                "totalEnrollments, " +
                "( SELECT COUNT(*) FROM  course_student_request_enrolment  WHERE crs.child_course_id = course_student_request_enrolment.course_id AND enrolment_status = 0 ) " +
                "totalEnrollmentRequests " +
                "FROM child_courses crs " +
                "LEFT JOIN parent_course_child_courses pcc ON crs.child_course_id = pcc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pcc.parent_course_id = pc.parent_course_id  " +
                "WHERE ( SELECT COUNT(*) FROM  course_student_request_enrolment  WHERE crs.child_course_id = course_student_request_enrolment.course_id AND enrolment_status = 0 )= 0 " +
                "GROUP BY crs.child_course_id " +
                "ORDER BY totalEnrollmentRequests DESC " +
                "LIMIT 2";


        try{

            List<Courses> notActiveCourseRequestNotificationsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("child_course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("child_course_name"));
                            course.setTotalEnrollments(resultSet.getInt("totalEnrollments"));
                            course.setTotalEnrollmentRequests(resultSet.getInt("totalEnrollmentRequests"));
                            return course;
                        }
                    }
            );
            return notActiveCourseRequestNotificationsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<Courses> getAllCoursesTableDisplayData() {
        String sql = "SELECT   " +
                " crs.course_id,  " +
                " crs.course_short_name, " +
                " crs.course_name, " +
                " crs.content_language, " +
                " s.semester_id, " +
                " s.semester_code, " +
                " crs.teacher_id, " +
                " usr.user_name, " +
                " DATE_FORMAT(crs.start_date, '%D %M %Y')startDate, " +
                " DATE_FORMAT(crs.end_date, '%D %M %Y')endDate, " +
                " ( SELECT COUNT(*) FROM course_enrolment WHERE course_id = crs.course_id AND is_enrolled = 1 )  " +
                "  totalEnrolledStudents " +
                "    FROM courses crs " +
                "    LEFT JOIN user usr ON crs.teacher_id = usr.user_id " +
                "    LEFT JOIN semester s ON s.semester_id = crs.category_id  " +
                "    ORDER BY crs.created_at DESC";

        try{
            List<Courses> allCoursesDataList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setContentLanguage(resultSet.getString("content_language"));
                            course.setSemseterId(resultSet.getInt("semester_id"));
                            course.setSemesterCode(resultSet.getString("semester_code"));
                            course.setTeacherId(resultSet.getLong("teacher_id"));
                            course.setTeacherName(resultSet.getString("user_name"));
                            course.setStartDate(resultSet.getString("startDate"));
                            course.setEndDate(resultSet.getString("endDate"));
                            course.setTotalEnrollments(resultSet.getInt("totalEnrolledStudents"));
                            return course;
                        }
                    }

            );
            return allCoursesDataList;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Courses> getAllCoursesTableDisplayDataByTeacherId(long teacherId) {
        String sql = "SELECT   " +
                " crs.course_id,  " +
                " crs.course_short_name, " +
                " crs.course_name, " +
                " crs.content_language, " +
                " s.semester_id, " +
                " s.semester_code, " +
                " crs.teacher_id, " +
                " usr.user_name, " +
                " DATE_FORMAT(crs.start_date, '%D %M %Y')startDate, " +
                " DATE_FORMAT(crs.end_date, '%D %M %Y')endDate, " +
                " ( SELECT COUNT(*) FROM course_enrolment WHERE course_id = crs.course_id AND is_enrolled = 1 )  " +
                "  totalEnrolledStudents " +
                "    FROM courses crs " +
                "    LEFT JOIN user usr ON crs.teacher_id = usr.user_id " +
                "    LEFT JOIN semester s ON s.semester_id = crs.category_id   " +
                "   WHERE crs.teacher_id = ? " +
                "    ORDER BY crs.created_at DESC";

        try{
            List<Courses> allCoursesDataList = getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setContentLanguage(resultSet.getString("content_language"));
                            course.setSemseterId(resultSet.getInt("semester_id"));
                            course.setSemesterCode(resultSet.getString("semester_code"));
                            course.setTeacherId(resultSet.getLong("teacher_id"));
                            course.setTeacherName(resultSet.getString("user_name"));
                            course.setStartDate(resultSet.getString("startDate"));
                            course.setEndDate(resultSet.getString("endDate"));
                            course.setTotalEnrollments(resultSet.getInt("totalEnrolledStudents"));
                            return course;
                        }
                    }

            );
            return allCoursesDataList;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Courses> adminGetCoursesWithEnrolledStudentsCountList() {
        String sql = " SELECT " +
                "    crs.course_id, " +
                "    crs.course_short_name, " +
                "    crs.course_name ,  " +
                "    (SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = crs.course_id)participants " +
                "    FROM courses crs  " +
                "    WHERE crs.status = 'active'";

        try{
            List<Courses> courseWithStudentCountList =  getJdbcTemplate().query(
                    sql,
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {

                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setTotalEnrollments(resultSet.getInt("participants"));
                            return course;

                        }
                    }

            );
            return courseWithStudentCountList;
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
    public List<Courses> adminGetAllCoursesWithCourseAverageList() {

        String sql = "SELECT  " +
                "c.course_id,  " +
                "c.course_short_name,  " +
                "c.course_name ,   " +
                "(SELECT   " +
                "AVG((SELECT SUM((grade.grade/max_grade)*grade_items.weight*100) " +
                "FROM grade,courses,grade_items  WHERE courses.course_id = grade.course_id   " +
                "AND grade_items.grade_item_id = grade.grade_item_id AND courses.course_id = crs.course_id  AND grade.student_id = ce.student_id) ) " +
                "FROM courses crs,course_enrolment ce " +
                "WHERE crs.course_id = ce.course_id   " +
                "AND ce.course_id = c.course_id " +
                ")DIV 1  " +
                "courseAverage, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = c.course_id)participants  " +
                " FROM courses c " +
                " ORDER BY c.created_at DESC ";

        try{
            List<Courses> courseList =  getJdbcTemplate().query(
                    sql,
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {

                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setCourseAverage(resultSet.getInt("courseAverage"));
                            course.setTotalEnrollments(resultSet.getInt("participants"));
                            return course;

                        }
                    }

            );
            return courseList;
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
    public List<Courses> getAllCourseAndGradeScoreList() {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = cc.child_course_id)participants , " +
                "(SELECT " +
                "AVG(g.grade)DIV 1 " +
                "FROM grade g " +
                "WHERE g.course_id = cc.child_course_id " +
                ")gradeAverage, " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM child_courses cc " +
               // "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
             //   "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
             //   "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "ORDER BY s.semester_code DESC ";

        try{
            List<Courses> courseDetails = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setGradeAverage(resultSet.getDouble("gradeAverage"));
                            courses.setTotalEnrollments(resultSet.getInt("participants"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setCredits(resultSet.getDouble("credits"));
                            return courses;
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
    public List<Courses> getAllCourseAndGradeScoreListByMajorId(int majorId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = cc.child_course_id)participants , " +
                "(SELECT " +
                "AVG(g.grade)DIV 1 " +
                "FROM grade g " +
                "WHERE g.course_id = cc.child_course_id " +
                ")gradeAverage, " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM child_courses cc " +
              //  "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
              //  "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
              //  "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE m.major_id = ? " +
                "ORDER BY s.semester_code DESC ";

        try{
            List<Courses> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setGradeAverage(resultSet.getDouble("gradeAverage"));
                            courses.setTotalEnrollments(resultSet.getInt("participants"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setCredits(resultSet.getDouble("credits"));
                            return courses;
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
    public List<Courses> getAllCourseAndGradeScoreListBySemester(int semesterId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = cc.child_course_id)participants , " +
                "(SELECT " +
                "AVG(g.grade)DIV 1 " +
                "FROM grade g " +
                "WHERE g.course_id = cc.child_course_id " +
                ")gradeAverage, " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM child_courses cc " +
              //  "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
              //  "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
              //  "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE  s.semester_id = ? " +
                "ORDER BY s.semester_code DESC ";

        try{
            List<Courses> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setGradeAverage(resultSet.getDouble("gradeAverage"));
                            courses.setTotalEnrollments(resultSet.getInt("participants"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setCredits(resultSet.getDouble("credits"));
                            return courses;
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
    public List<Courses> getAllCourseListBySemesterAndMajorId(int semesterId, int majorId){
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM child_courses cc " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE  s.semester_id = ? " +
                "AND  m.major_id =  ? " +
                "ORDER BY s.semester_code DESC ";

        try{
            List<Courses> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId,majorId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setCredits(resultSet.getDouble("credits"));
                            return courses;
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
    public List<Courses> getAllCourseAndGradeScoreListBySemesterAndMajorId(int semesterId, int majorId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = cc.child_course_id)participants , " +
                "(SELECT " +
                "AVG(g.grade)DIV 1 " +
                "FROM grade g " +
                "WHERE g.course_id = cc.child_course_id " +
                ")gradeAverage, " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM child_courses cc " +
              //  "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
              //  "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
              //  "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE  s.semester_id = ? " +
                "AND  m.major_id =  ? " +
                "ORDER BY s.semester_code DESC ";

        try{
            List<Courses> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId,majorId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setGradeAverage(resultSet.getDouble("gradeAverage"));
                            courses.setTotalEnrollments(resultSet.getInt("participants"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setCredits(resultSet.getDouble("credits"));
                            return courses;
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
    public List<Courses> teacherGetAllCourseAndGradeScoreList(long teacherId) {

        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = cc.child_course_id)participants , " +
                "(SELECT " +
                "AVG(g.grade)DIV 1 " +
                "FROM grade g " +
                "WHERE g.course_id = cc.child_course_id " +
                ")gradeAverage, " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM child_courses cc " +
                //  "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                //  "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
                //  "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE cc.teacher_id =  ? " +
                "ORDER BY s.semester_code DESC ";

        try{
            List<Courses> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setGradeAverage(resultSet.getDouble("gradeAverage"));
                            courses.setTotalEnrollments(resultSet.getInt("participants"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setCredits(resultSet.getDouble("credits"));
                            return courses;
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
    public List<Courses> teacherGetAllCourseAndGradeScoreListByMajorId(int majorId, long teacherId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = cc.child_course_id)participants , " +
                "(SELECT " +
                "AVG(g.grade)DIV 1 " +
                "FROM grade g " +
                "WHERE g.course_id = cc.child_course_id " +
                ")gradeAverage, " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM child_courses cc " +
                //  "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                //  "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
                //  "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE m.major_id = ? " +
                "AND cc.teacher_id = ? " +
                "ORDER BY s.semester_code DESC ";

        try{
            List<Courses> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId, teacherId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setGradeAverage(resultSet.getDouble("gradeAverage"));
                            courses.setTotalEnrollments(resultSet.getInt("participants"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setCredits(resultSet.getDouble("credits"));
                            return courses;
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
    public List<Courses> teacherGetAllCourseAndGradeScoreListBySemester(int semesterId, long teacherId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = cc.child_course_id)participants , " +
                "(SELECT " +
                "AVG(g.grade)DIV 1 " +
                "FROM grade g " +
                "WHERE g.course_id = cc.child_course_id " +
                ")gradeAverage, " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM child_courses cc " +
              //  "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
              //  "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
              //  "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE  s.semester_id = ? " +
                "AND cc.teacher_id = ? " +
                "ORDER BY s.semester_code DESC ";

        try{
            List<Courses> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId,teacherId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setGradeAverage(resultSet.getDouble("gradeAverage"));
                            courses.setTotalEnrollments(resultSet.getInt("participants"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setCredits(resultSet.getDouble("credits"));
                            return courses;
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
    public List<Courses> teacherGetAllCourseAndGradeScoreListBySemesterAndMajorId(int semesterId, int majorId, long teacherId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.course_type, " +
                "pc.credits, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.min_pass_score, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = cc.child_course_id)participants , " +
                "(SELECT " +
                "AVG(g.grade)DIV 1 " +
                "FROM grade g " +
                "WHERE g.course_id = cc.child_course_id " +
                ")gradeAverage, " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM child_courses cc " +
                //  "LEFT JOIN parent_course_child_courses pcc ON pcc.child_course_id = cc.child_course_id " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                //  "LEFT JOIN parent_courses_major pcm ON pc.parent_course_id = pcm.parent_course_id " +
                "LEFT JOIN major m ON pc.major_id = m.major_id " +
                //  "LEFT JOIN child_course_semester ccs ON ccs.child_course_id = cc.child_course_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE  s.semester_id = ? " +
                "AND  m.major_id =  ? " +
                "AND  cc.teacher_id =  ? " +
                "ORDER BY s.semester_code DESC ";

        try{
            List<Courses> courseDetails = getJdbcTemplate().query(
                    sql,
                    new Object[]{semesterId,majorId, teacherId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses courses = new Courses();

                            courses.setMajorId(resultSet.getInt("major_id"));
                            courses.setMajorName(resultSet.getString("major_name"));
                            courses.setMajorShortName(resultSet.getString("major_short_code"));
                            courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                            courses.setChildCourseId(resultSet.getInt("child_course_id"));
                            courses.setCourseId(resultSet.getInt("parent_course_id"));
                            courses.setSemseterId(resultSet.getInt("semester_id"));
                            courses.setSemesterCode(resultSet.getString("semester_code"));
                            courses.setCourseShortName(resultSet.getString("child_course_name"));
                            courses.setCourseName(resultSet.getString("course_name"));
                            courses.setCourseShortName(resultSet.getString("course_short_name"));
                            courses.setGradeAverage(resultSet.getDouble("gradeAverage"));
                            courses.setTotalEnrollments(resultSet.getInt("participants"));
                            courses.setCourseType(resultSet.getString("course_type"));
                            courses.setCredits(resultSet.getDouble("credits"));
                            return courses;
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
    public List<Courses> adminGetActiveCoursesWithCourseAverageList() {
        String sql = "SELECT  " +
                "c.course_id,  " +
                "c.course_short_name,  " +
                "c.course_name ,   " +
                "(SELECT   " +
                "AVG((SELECT SUM((grade.grade/max_grade)*grade_items.weight*100) " +
                "FROM grade,courses,grade_items  WHERE courses.course_id = grade.course_id   " +
                "AND grade_items.grade_item_id = grade.grade_item_id AND courses.course_id = crs.course_id  AND grade.student_id = ce.student_id) ) " +
                "FROM courses crs,course_enrolment ce " +
                "WHERE crs.course_id = ce.course_id   " +
                "AND ce.course_id = c.course_id " +
                ")DIV 1  " +
                "courseAverage, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = c.course_id)participants  " +
                " FROM courses c " +
                "WHERE c.status = 'active' " +
                "ORDER BY c.created_at DESC";

        try{
            List<Courses> courseList =  getJdbcTemplate().query(
                    sql,
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {

                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setCourseAverage(resultSet.getInt("courseAverage"));
                            course.setTotalEnrollments(resultSet.getInt("participants"));
                            return course;

                        }
                    }

            );
            return courseList;
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
    public List<Courses> adminGetInActiveCoursesWithCourseAverageList() {
        String sql = "SELECT  " +
                "c.course_id,  " +
                "c.course_short_name,  " +
                "c.course_name ,   " +
                "(SELECT   " +
                "AVG((SELECT SUM((grade.grade/max_grade)*grade_items.weight*100) " +
                "FROM grade,courses,grade_items  WHERE courses.course_id = grade.course_id   " +
                "AND grade_items.grade_item_id = grade.grade_item_id AND courses.course_id = crs.course_id  AND grade.student_id = ce.student_id) ) " +
                "FROM courses crs,course_enrolment ce " +
                "WHERE crs.course_id = ce.course_id   " +
                "AND ce.course_id = c.course_id " +
                ")DIV 1  " +
                "courseAverage, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = c.course_id)participants  " +
                " FROM courses c " +
                "WHERE c.status = 'not-active' " +
                " ORDER BY c.created_at DESC";

        try{
            List<Courses> courseList =  getJdbcTemplate().query(
                    sql,
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {

                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setCourseAverage(resultSet.getInt("courseAverage"));
                            course.setTotalEnrollments(resultSet.getInt("participants"));
                            return course;

                        }
                    }

            );
            return courseList;
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
    public List<Courses> adminGetCoursesWithCourseAverageListBySemesterId(int semseterId) {

        String sql = "SELECT  " +
                "c.course_id,  " +
                "c.course_short_name,  " +
                "c.course_name ,   " +
                "(SELECT   " +
                "AVG((SELECT SUM((grade.grade/max_grade)*grade_items.weight*100) " +
                "FROM grade,courses,grade_items  WHERE courses.course_id = grade.course_id   " +
                "AND grade_items.grade_item_id = grade.grade_item_id AND courses.course_id = crs.course_id  AND grade.student_id = ce.student_id) ) " +
                "FROM courses crs,course_enrolment ce " +
                "WHERE crs.course_id = ce.course_id   " +
                "AND ce.course_id = c.course_id " +
                ")DIV 1  " +
                "courseAverage, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = c.course_id)participants  " +
                " FROM courses c " +
                "WHERE c.category_id = ? " +
                " ORDER BY c.created_at DESC";

        try{
            List<Courses> courseList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{semseterId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {

                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setCourseAverage(resultSet.getInt("courseAverage"));
                            course.setTotalEnrollments(resultSet.getInt("participants"));
                            return course;

                        }
                    }

            );
            return courseList;
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
    public List<Courses> adminGetCoursesWithEnrolledStudentsCountListByTeacherId(long teacherId) {
        String sql = " SELECT " +
                "    crs.course_id, " +
                "    crs.course_short_name, " +
                "    crs.course_name ,  " +
                "    (SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = crs.course_id)participants " +
                "    FROM courses crs  " +
                "    WHERE crs.status = 'active' " +
                "   AND crs.teacher_id = ? ";

        try{
            List<Courses> courseWithStudentCountList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {

                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setTotalEnrollments(resultSet.getInt("participants"));
                            return course;

                        }
                    }

            );
            return courseWithStudentCountList;
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
    public List<Courses> teacherGetAllCoursesWithCourseAverageList(long tecaherId) {
        String sql = "SELECT  " +
                "c.course_id,  " +
                "c.course_short_name,  " +
                "c.course_name ,   " +
                "(SELECT   " +
                "AVG((SELECT SUM((grade.grade/max_grade)*grade_items.weight*100) " +
                "FROM grade,courses,grade_items  WHERE courses.course_id = grade.course_id   " +
                "AND grade_items.grade_item_id = grade.grade_item_id AND courses.course_id = crs.course_id  AND grade.student_id = ce.student_id) ) " +
                "FROM courses crs,course_enrolment ce " +
                "WHERE crs.course_id = ce.course_id   " +
                "AND ce.course_id = c.course_id " +
                ")DIV 1  " +
                "courseAverage, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = c.course_id)participants  " +
                " FROM courses c " +
                "WHERE c.teacher_id = ? " +
                " ORDER BY c.created_at DESC";

        try{
            List<Courses> courseList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{tecaherId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {

                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setCourseAverage(resultSet.getInt("courseAverage"));
                            course.setTotalEnrollments(resultSet.getInt("participants"));
                            return course;

                        }
                    }

            );
            return courseList;
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
    public List<Courses> teacherGetActiveCoursesWithCourseAverageList(long tecaherId) {
        String sql = "SELECT  " +
                "c.course_id,  " +
                "c.course_short_name,  " +
                "c.course_name ,   " +
                "(SELECT   " +
                "AVG((SELECT SUM((grade.grade/max_grade)*grade_items.weight*100) " +
                "FROM grade,courses,grade_items  WHERE courses.course_id = grade.course_id   " +
                "AND grade_items.grade_item_id = grade.grade_item_id AND courses.course_id = crs.course_id  AND grade.student_id = ce.student_id) ) " +
                "FROM courses crs,course_enrolment ce " +
                "WHERE crs.course_id = ce.course_id   " +
                "AND ce.course_id = c.course_id " +
                ")DIV 1  " +
                "courseAverage, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = c.course_id)participants  " +
                " FROM courses c " +
                "WHERE c.teacher_id = ? " +
                " AND c.status = 'active' " +
                " ORDER BY c.created_at DESC";

        try{
            List<Courses> courseList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{tecaherId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {

                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setCourseAverage(resultSet.getInt("courseAverage"));
                            course.setTotalEnrollments(resultSet.getInt("participants"));
                            return course;

                        }
                    }

            );
            return courseList;
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
    public List<Courses> teacherGetInActiveCoursesWithCourseAverageList(long tecaherId) {
        String sql = "SELECT  " +
                "c.course_id,  " +
                "c.course_short_name,  " +
                "c.course_name ,   " +
                "(SELECT   " +
                "AVG((SELECT SUM((grade.grade/max_grade)*grade_items.weight*100) " +
                "FROM grade,courses,grade_items  WHERE courses.course_id = grade.course_id   " +
                "AND grade_items.grade_item_id = grade.grade_item_id AND courses.course_id = crs.course_id  AND grade.student_id = ce.student_id) ) " +
                "FROM courses crs,course_enrolment ce " +
                "WHERE crs.course_id = ce.course_id   " +
                "AND ce.course_id = c.course_id " +
                ")DIV 1  " +
                "courseAverage, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = c.course_id)participants  " +
                " FROM courses c " +
                "WHERE c.teacher_id = ? " +
                " AND c.status = 'not-active' " +
                " ORDER BY c.created_at DESC";

        try{
            List<Courses> courseList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{tecaherId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {

                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setCourseAverage(resultSet.getInt("courseAverage"));
                            course.setTotalEnrollments(resultSet.getInt("participants"));
                            return course;

                        }
                    }

            );
            return courseList;
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
    public List<Courses> teacherGetCoursesWithCourseAverageListBySemesterId(long tecaherId, int semseterId) {
        String sql = "SELECT  " +
                "c.course_id,  " +
                "c.course_short_name,  " +
                "c.course_name ,   " +
                "(SELECT   " +
                "AVG((SELECT SUM((grade.grade/max_grade)*grade_items.weight*100) " +
                "FROM grade,courses,grade_items  WHERE courses.course_id = grade.course_id   " +
                "AND grade_items.grade_item_id = grade.grade_item_id AND courses.course_id = crs.course_id  AND grade.student_id = ce.student_id) ) " +
                "FROM courses crs,course_enrolment ce " +
                "WHERE crs.course_id = ce.course_id   " +
                "AND ce.course_id = c.course_id " +
                ")DIV 1  " +
                "courseAverage, " +
                "(SELECT COUNT(*) FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = c.course_id)participants  " +
                " FROM courses c " +
                "WHERE c.teacher_id = ? " +
                " AND c.category_id = ?  " +
                " ORDER BY c.created_at DESC";

        try{
            List<Courses> courseList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{tecaherId, semseterId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {

                            Courses course = new Courses();

                            course.setCourseId(resultSet.getInt("course_id"));
                            course.setCourseName(resultSet.getString("course_name"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setCourseAverage(resultSet.getInt("courseAverage"));
                            course.setTotalEnrollments(resultSet.getInt("participants"));
                            return course;

                        }
                    }

            );
            return courseList;
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
    public int getAllCourseEnrollmentRequestsCount() {
        String sql = "SELECT COUNT(*) AS courseEnrollmentRequests " +
                "    FROM course_student_request_enrolment " +
                "    WHERE  enrolment_status = 0";
        try{
            int courseRequestCount = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return  courseRequestCount;

        }catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
        catch (Exception e ){
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public int getAllCourseEnrollmentRequestsCountByTeacherId(long teacherId) {
        String sql = "SELECT COUNT(*)  " +
                "FROM course_student_request_enrolment cr  " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = cr.course_id   " +
                "WHERE  cr.enrolment_status = 0   " +
                "AND cc.teacher_id=   ? ";
        try{
            int courseRequestCount = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{teacherId},
                    Integer.class
            );
            return  courseRequestCount;

        }catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
        catch (Exception e ){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Courses getCourseNameByCourseId(int courseId) {
        String sql = "SELECT " +
                "pc.course_short_name, " +
                "cc.child_course_id, " +
                "pc.course_name, " +
                "cc.parent_course_id " +
                "FROM  " +
                "child_courses cc " +
                "LEFT JOIN parent_courses pc ON cc.parent_course_id = pc.parent_course_id " +
                "WHERE cc.child_course_id =  ? ";


        try{
            Courses courseNames = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<Courses>() {
                        @Override
                        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                            Courses course = new Courses();
                            course.setParentCourseId(resultSet.getLong("parent_course_id"));
                            course.setCourseId(resultSet.getInt("child_course_id"));
                            course.setCourseShortName(resultSet.getString("course_short_name"));
                            course.setCourseName(resultSet.getString("course_name"));
                            return course;
                        }
                    }
            );
            return courseNames;

        }
        catch(EmptyResultDataAccessException e){
//            e.printStackTrace();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public int getTotalCourseEnrolments(int childCourseId) {
        String sql = "SELECT COUNT(*) " +
                "FROM course_enrolment ce ,user usr " +
                "WHERE ce.student_id = usr.user_id " +
                "AND usr.status = 'active' " +
                "AND ce.is_enrolled = 1 " +
                "AND ce.has_unenrolled = 0 " +
                "AND ce.course_id =  ?";


        try{
            int totalEnrolments = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{childCourseId},
                    Integer.class
            );
            return totalEnrolments;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CoursesDaoImpl::@getTotalCourseEnrolments::Error!!");
            e.printStackTrace();
            return 0;
        }
        catch (Exception e){
            System.out.println("@CoursesDaoImpl::@getTotalCourseEnrolments::Error!!");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getCourseEnrolmentRequestCountByCourseId(int courseId) {

        String sql = "SELECT COUNT(*)  " +
                "FROM course_student_request_enrolment er " +
                "LEFT JOIN user u ON u.user_id = er.student_id " +
                "WHERE  " +
                "u.status = 'active' " +
                "AND er.enrolment_status = 0 " +
                "AND er.course_id = ?";

        try{
            int totalEnrolmentRequests = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseId},
                    Integer.class
            );
            return totalEnrolmentRequests;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CoursesDaoImpl::@getCourseEnrolmentRequestCountByCourseId::Error!!");
            e.printStackTrace();
            return 0;
        }
        catch (Exception e){
            System.out.println("@CoursesDaoImpl::@getCourseEnrolmentRequestCountByCourseId::Error!!");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Courses> getSemesterCourseListBySemesterId(int semesterId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.content_language, " +
                "pc.course_type, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "(SELECT COUNT(*) FROM course_enrolment ce LEFT JOIN USER u ON u.user_id = ce.student_id WHERE u.status = 'active' AND ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = cc.child_course_id)  " +
                "enrolled  " +
                "FROM child_courses cc " +
                "LEFT JOIN parent_courses pc ON cc.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN major m ON m.major_id = pc.major_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE s.semester_id = ? " +
                "ORDER BY pc.parent_course_id";


        try{

            List<Courses> semesterCourseList =
                    getJdbcTemplate().query(
                            sql,
                            new Object[]{semesterId},
                            new RowMapper<Courses>() {
                                @Override
                                public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                                    Courses courses = new Courses();
                                    courses.setMajorId(resultSet.getInt("major_id"));
                                    courses.setMajorName(resultSet.getString("major_name"));
                                    courses.setMajorShortName(resultSet.getString("major_short_code"));
                                    courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                                    courses.setChildCourseId(resultSet.getInt("child_course_id"));
                                    courses.setCourseId(resultSet.getInt("parent_course_id"));
                                    courses.setSemseterId(resultSet.getInt("semester_id"));
                                    courses.setContentLanguage(resultSet.getString("content_language"));
                                    courses.setSemesterCode(resultSet.getString("semester_code"));
                                    courses.setCourseShortName(resultSet.getString("child_course_name"));
                                    courses.setCourseName(resultSet.getString("course_name"));
                                    courses.setCourseShortName(resultSet.getString("course_short_name"));
                                    courses.setCourseType(resultSet.getString("course_type"));
                                    courses.setTotalEnrollments(resultSet.getInt("enrolled"));
                                     return  courses;

                                }
                            }

                    );
            return semesterCourseList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CoursesDaoImple::@getSemesterCourseListBySemesterId::EMPTY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@CoursesDaoImple::@getSemesterCourseListBySemesterId::ERROR");
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public List<Courses> getSemesterCourseListForSemesterByMajorId(int semesterId, int majorId) {
        String sql = "SELECT " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "cc.child_course_id, " +
                "cc.child_course_name, " +
                "cc.content_language, " +
                "pc.course_type, " +
                "s.semester_id, " +
                "s.semester_code, " +
                "(SELECT COUNT(*) FROM course_enrolment ce LEFT JOIN USER u ON u.user_id = ce.student_id WHERE u.status = 'active' AND ce.is_enrolled = 1 AND ce.has_unenrolled = 0 AND ce.course_id = cc.child_course_id)  " +
                "enrolled  " +
                "FROM child_courses cc " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = cc.parent_course_id " +
                "LEFT JOIN major m ON m.major_id = pc.major_id " +
                "LEFT JOIN semester s ON s.semester_id = cc.semester_id " +
                "WHERE s.semester_id = ? " +
                "AND m.major_id =  ? " +
                "ORDER BY pc.parent_course_id";


        try{

            List<Courses> semesterCourseList =
                    getJdbcTemplate().query(
                            sql,
                            new Object[]{semesterId, majorId},
                            new RowMapper<Courses>() {
                                @Override
                                public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
                                    Courses courses = new Courses();
                                    courses.setMajorId(resultSet.getInt("major_id"));
                                    courses.setMajorName(resultSet.getString("major_name"));
                                    courses.setMajorShortName(resultSet.getString("major_short_code"));
                                    courses.setParentCourseId(resultSet.getLong("parent_course_id"));
                                    courses.setChildCourseId(resultSet.getInt("child_course_id"));
                                    courses.setCourseId(resultSet.getInt("parent_course_id"));
                                    courses.setSemseterId(resultSet.getInt("semester_id"));
                                    courses.setContentLanguage(resultSet.getString("content_language"));
                                    courses.setSemesterCode(resultSet.getString("semester_code"));
                                    courses.setCourseShortName(resultSet.getString("child_course_name"));
                                    courses.setCourseName(resultSet.getString("course_name"));
                                    courses.setCourseShortName(resultSet.getString("course_short_name"));
                                    courses.setCourseType(resultSet.getString("course_type"));
                                    courses.setTotalEnrollments(resultSet.getInt("enrolled"));
                                    return  courses;

                                }
                            }

                    );
            return semesterCourseList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@CoursesDaoImple::@getSemesterCourseListBySemesterId::EMPTY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@CoursesDaoImple::@getSemesterCourseListBySemesterId::ERROR");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public boolean checkPermissionForTeacherAgainstCourseId(long teacherId, int courseId) {
        String sql = " SELECT cc.child_course_id " +
                " FROM child_courses cc " +
                " WHERE cc.child_course_id = ?" +
                " AND cc.teacher_id = ? ";

        try{
            int val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{courseId,teacherId},
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
    public boolean checkIfMoreCoursesToLoadForStudent(long studentId, int currentLimit) {
        String sql = "SELECT COUNT(*) " +
                "FROM courses c " +
                "WHERE  " +
                "c.status = 'active' " +
                "AND c.course_id NOT IN (SELECT ce.course_id FROM course_enrolment ce WHERE ce.is_enrolled = 1 AND ce.student_id = ?) " +
                "AND c.course_id NOT IN (SELECT cr.course_id FROM course_student_request_enrolment cr WHERE cr.enrolment_status = 0 AND cr.student_id = ? ) ";
        try{
            int val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{studentId,studentId},
                    Integer.class
            );

            if(val > currentLimit){
                return true;
            }
            else {
                return false;
            }

        }
        catch(EmptyResultDataAccessException e){
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }

    }

    @Override
    public String deleteCourseFromCourseStudentRequestEnrolment(int courseId) {
        String sql = "DELETE FROM course_student_request_enrolment WHERE course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@CourseStudentRequestEnrolment::COURSE DELETED::1");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteCourseFromCourseEnrolment(int courseId) {
        String sql = "DELETE FROM course_enrolment WHERE course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@CourseEnrolment::COURSE DELETED::2");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteCourseFromEvents(int courseId) {
        String sql = "DELETE FROM events WHERE course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@Events::COURSE DELETED::3");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteCourseFromExamEnrolment(int courseId) {
        String sql = "DELETE FROM exam_enrolment WHERE course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@ExamEnrolment::COURSE DELETED::4");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteCourseFromExamStudentRequestEnrolment(int courseId) {
        String sql = "DELETE FROM exam_student_request_enrolment WHERE course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@ExamStudentRequestEnrolment::COURSE DELETED::5");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteCourseFromGrade(int courseId) {
        String sql = "DELETE FROM grade WHERE course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@Grade::COURSE DELETED::6");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteCourseFromClearanceExamEnrolment(int courseId) {
        String sql = "DELETE clearance_exam_enrolment " +
                "FROM clearance_exam_enrolment  " +
                "LEFT JOIN grade_items  ON grade_items.grade_item_id = clearance_exam_enrolment.parent_exam_id " +
                "WHERE grade_items.course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@Courses::COURSE DELETED::7");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteCourseFromClearanceExam(int courseId) {
        String sql = "DELETE FROM clearance_exam WHERE child_course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@Courses::COURSE DELETED::8");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }


    @Override
    public String deleteCourseFromGradeItems(int courseId) {
        String sql = "DELETE FROM grade_items WHERE course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@GradeItems::COURSE DELETED::9");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteCourseUploads(int courseId) {

        String sql = "DELETE FROM lesson_uploads WHERE course_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@Courses::COURSE UPLOADS DELETED::10");
            return "COURSE UPLOADS DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }


    @Override
    public String deleteCourseLessons(int courseId) {
        String sql = "DELETE FROM course_lesson WHERE course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@Courses::COURSE LESSONS DELETED::11");
            return "COURSE LESSONS DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }


    @Override
    public String deleteCourseFromChildCourseSemester(int courseId) {
        String sql = "DELETE FROM child_course_semester WHERE child_course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@Courses::COURSE DELETED::12");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteCourseFromChildCourseTeachers(int courseId) {
        String sql = "DELETE FROM child_courses_teachers WHERE child_course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@Courses::COURSE DELETED::13");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }


    @Override
    public String deleteCourseFromGradeCustom(int courseId) {
        String sql = "DELETE FROM grade_custom WHERE child_course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@Courses::COURSE DELETED::14");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteCourseFromParentCourseChildCourses(int courseId) {
        String sql = "DELETE FROM parent_course_child_courses WHERE child_course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@Courses::COURSE DELETED::15");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }

    }

    @Override
    public String deleteCourseFromChildCourses(int courseId) {
        String sql = "DELETE FROM child_courses WHERE child_course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{courseId}
            );
            System.out.println("@Courses::COURSE DELETED::16");
            return "COURSE DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }


}

