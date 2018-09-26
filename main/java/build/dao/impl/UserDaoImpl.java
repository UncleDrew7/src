package build.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import build.model.StudentClass;
import build.model.StudentDegreeTypeChangeRequest;
import build.model.User;
import build.dao.UserDao;
import build.model.UserProfile;
import  build.row_mapper.UserRowMapper;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
/**
 * Created by apple on 15/08/2017.
 */
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

    @Override
    public String createNewUser(User newUser) {

        String sql = "INSERT INTO user(user_id,role_id,class_id,user_name,email, password,salt)VALUES(?,?,?,?,?,?,?) ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{newUser.getUserId(),newUser.getRoleId(),newUser.getClassId(),newUser.getUserName(),newUser.getEmail(), newUser.getPassword(),newUser.getSalt()}
        );
        if(1 == returnValue)
            return "# New Users>>>"+newUser.getUserName()+" Creation is SUCCESS";
        else
            return "# User Creation is FAILURE";
    }

    @Override
    public String updateCurrentUser(User currentUser) {
        return null;
    }

    @Override
    public String deleteCurrentUser(User currentUser) {
        return null;
    }

    @Override
    public List<User> displayCurrentUsers() {
        String sql = "SELECT * FROM user";
        List<User> userList = getJdbcTemplate().query(
                sql,
                new UserRowMapper()
        );
        return userList;
    }

    @Override
    public User getUserByUserId(long userId) {

        String sql = "SELECT usr.user_id,usr.user_name,r.role_name,usr.email,usr.password,usr.salt" +
                    " FROM user usr, role r" +
                    " WHERE usr.role_id = r.id AND usr.user_id = ?";

        User user = (User)getJdbcTemplate().queryForObject(
                sql,
                new Object[]{userId},
                new UserRowMapper()
        );


        logger.info("getting user");
        logger.info(user.getEmail());
        return user;
    }

    @Override
    public String getUserNameById(long userId) {
       String sql = "SELECT u.user_name " +
                    " FROM user u" +
                    " WHERE u.user_id = ?";

       String userName = getJdbcTemplate().queryForObject(
               sql,
               new Object[]{userId},
               String.class
       );
       return userName;
    }

    @Override
    public int getTotalNumberOfSystemUsers() {
        String sql = "SELECT COUNT(*)" +
                "    FROM user " +
                "    WHERE user.status = 'active'";

        try{
            int totalUsers = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return  totalUsers;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }

    }



    @Override
    public int getTotalNumberOfSystemStudents() {
        String sql = " SELECT COUNT(*)" +
                "    FROM user " +
                "    WHERE user.status = 'active' " +
                "    AND user.role_id = 3";

        try{
            int totalStudents = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalStudents;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public int getTotalSystemAdminsCount() {
        String sql = "SELECT COUNT(*) " +
                "FROM USER u " +
                "WHERE u.status = 'active'  " +
                "AND u.role_id =  1";

        try{
            int totalStudents = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalStudents;

        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getTotalNumberOfSystemDoubleDegreeStudents() {
        String sql = "SELECT COUNT(*) " +
                "    FROM user_profile up , user " +
                "    WHERE user.user_id = up.user_id " +
                "    AND user.status='active' " +
                "    AND user.role_id = 3 " +
                "    AND up.degree_type = 'double-degree'";

        try{

            int totalDoubleDegreeStudents = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalDoubleDegreeStudents;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public int getTotalNumberOfSystemGeneralDegreeStudents() {
        String sql = "SELECT COUNT(*)" +
                "    FROM user_profile up , user " +
                "    WHERE user.user_id = up.user_id " +
                "    AND user.status='active' " +
                "    AND user.role_id = 3 " +
                "    AND up.degree_type = 'chinese-degree'";

        try{
            int totalGeneralDegreeStudents = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalGeneralDegreeStudents;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public int getTotalNumberOfSystemTeachers() {
        String sql = "SELECT COUNT(*)" +
                "    FROM user" +
                "    WHERE user.status = 'active' " +
                "    AND user.role_id = 2";

        try{
            int totalTeachers = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalTeachers;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public List<UserProfile> getSystemsLatestStudentCourseEnrollmentNotificationsList(int limit) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "cc.child_course_name, " +
                "ce.course_id, " +
                "DATE_FORMAT(ce.enrolment_date,'%D %M %Y') enrolment_date, " +
                "ce.student_id, " +
                "u.user_name " +
                "FROM course_enrolment ce " +
                "LEFT JOIN user u ON u.user_id = ce.student_id " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ce.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON ce.course_id = pcc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pcc.parent_course_id = pc.parent_course_id  " +
                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN major m ON m.major_id = pcm.major_id " +
                "WHERE u.status = 'active' " +
                "ORDER BY ce.enrolment_date DESC  " +
                "LIMIT ?";
        try{
            List<UserProfile> systemUserList = getJdbcTemplate().query(
                    sql,
                    new Object[]{limit},
                    new RowMapper<UserProfile>() {
                        @Override
                        public UserProfile mapRow(ResultSet resultSet, int i) throws SQLException {
                            UserProfile user = new UserProfile();

                            user.setUserId(resultSet.getLong("student_id"));
                            user.setUserName(resultSet.getString("user_name"));
                           user.setMajorId(resultSet.getInt("major_id"));
                           user.setMajorName(resultSet.getString("major_name"));
                           user.setMajorShortName(resultSet.getString("major_short_code"));
                           user.setParentCourseId(resultSet.getLong("parent_course_id"));
                           user.setParentCourseName(resultSet.getString("course_name"));
                           user.setChildCourseId(resultSet.getInt("course_id"));
                           user.setChildCourseName(resultSet.getString("child_course_name"));
                           user.setEnrollmentDate(resultSet.getString("enrolment_date"));


                            return  user;
                        }
                    }
            );
            return  systemUserList;
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
    public List<UserProfile> getSystemsLatestCourseEnrollmentNotificationsForTeacher(long teacherId, int limit) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "cc.child_course_name, " +
                "ce.course_id, " +
                "DATE_FORMAT(ce.enrolment_date,'%D %M %Y') enrolment_date, " +
                "ce.student_id, " +
                "u.user_name " +
                "FROM course_enrolment ce " +
                "LEFT JOIN user u ON u.user_id = ce.student_id " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ce.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON ce.course_id = pcc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pcc.parent_course_id = pc.parent_course_id  " +
                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN major m ON m.major_id = pcm.major_id " +
                "WHERE u.status = 'active' " +
                "AND cc.teacher_id = ? " +
                "ORDER BY ce.enrolment_date DESC  " +
                "LIMIT ?";
        try{
            List<UserProfile> systemUserList = getJdbcTemplate().query(
                    sql,
                    new Object[]{teacherId, limit},
                    new RowMapper<UserProfile>() {
                        @Override
                        public UserProfile mapRow(ResultSet resultSet, int i) throws SQLException {
                            UserProfile user = new UserProfile();

                            user.setUserId(resultSet.getLong("student_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setMajorId(resultSet.getInt("major_id"));
                            user.setMajorName(resultSet.getString("major_name"));
                            user.setMajorShortName(resultSet.getString("major_short_code"));
                            user.setParentCourseId(resultSet.getLong("parent_course_id"));
                            user.setParentCourseName(resultSet.getString("course_name"));
                            user.setChildCourseId(resultSet.getInt("course_id"));
                            user.setChildCourseName(resultSet.getString("child_course_name"));
                            user.setEnrollmentDate(resultSet.getString("enrolment_date"));


                            return  user;
                        }
                    }
            );
            return  systemUserList;
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
    public List<UserProfile> getSystemsLatestExamEnrollmentNotificationsAdmin(int limit) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "gi.grade_item_name, " +
                "ee.exam_id, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "cc.child_course_name, " +
                "ee.course_id, " +
                "DATE_FORMAT(ee.enrolment_date,'%D %M %Y') enrolment_date, " +
                "ee.student_id, " +
                "u.user_name " +
                "FROM exam_enrolment ee " +
                "LEFT JOIN USER u ON u.user_id = ee.student_id " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ee.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON ee.course_id = pcc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pcc.parent_course_id = pc.parent_course_id  " +
                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = ee.exam_id " +
                "LEFT JOIN major m ON m.major_id = pcm.major_id " +
                "WHERE u.status = 'active' " +
                "AND ee.is_Enrolled = 1 " +
                "ORDER BY ee.enrolment_date DESC  " +
                "LIMIT ? ";
        try{
            List<UserProfile> systemUserList = getJdbcTemplate().query(
                    sql,
                    new Object[]{limit},
                    new RowMapper<UserProfile>() {
                        @Override
                        public UserProfile mapRow(ResultSet resultSet, int i) throws SQLException {
                            UserProfile user = new UserProfile();

                            user.setUserId(resultSet.getLong("student_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setMajorId(resultSet.getInt("major_id"));
                            user.setMajorName(resultSet.getString("major_name"));
                            user.setMajorShortName(resultSet.getString("major_short_code"));
                            user.setParentCourseId(resultSet.getLong("parent_course_id"));
                            user.setParentCourseName(resultSet.getString("course_name"));
                            user.setChildCourseId(resultSet.getInt("course_id"));
                            user.setChildCourseName(resultSet.getString("child_course_name"));
                            user.setEnrollmentDate(resultSet.getString("enrolment_date"));
                            user.setExamName(resultSet.getString("grade_item_name"));
                            user.setExamId(resultSet.getInt("exam_id"));


                            return  user;
                        }
                    }
            );
            return  systemUserList;
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
    public List<UserProfile> getSystemsLatestExamEnrollmentNotificationsTeacher(long teacher, int limit) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "gi.grade_item_name, " +
                "ee.exam_id, " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "cc.child_course_name, " +
                "ee.course_id, " +
                "DATE_FORMAT(ee.enrolment_date,'%D %M %Y') enrolment_date, " +
                "ee.student_id, " +
                "u.user_name " +
                "FROM exam_enrolment ee " +
                "LEFT JOIN USER u ON u.user_id = ee.student_id " +
                "LEFT JOIN child_courses cc ON cc.child_course_id = ee.course_id " +
                "LEFT JOIN parent_course_child_courses pcc ON ee.course_id = pcc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pcc.parent_course_id = pc.parent_course_id  " +
                "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN grade_items gi ON gi.grade_item_id = ee.exam_id " +
                "LEFT JOIN major m ON m.major_id = pcm.major_id " +
                "WHERE u.status = 'active' " +
                "AND ee.is_Enrolled = 1 " +
                "AND cc.teacher_id =  ?  " +
                "ORDER BY ee.enrolment_date DESC  " +
                "LIMIT ? ";
        try{
            List<UserProfile> systemUserList = getJdbcTemplate().query(
                    sql,
                    new Object[]{teacher , limit},
                    new RowMapper<UserProfile>() {
                        @Override
                        public UserProfile mapRow(ResultSet resultSet, int i) throws SQLException {
                            UserProfile user = new UserProfile();

                            user.setUserId(resultSet.getLong("student_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setMajorId(resultSet.getInt("major_id"));
                            user.setMajorName(resultSet.getString("major_name"));
                            user.setMajorShortName(resultSet.getString("major_short_code"));
                            user.setParentCourseId(resultSet.getLong("parent_course_id"));
                            user.setParentCourseName(resultSet.getString("course_name"));
                            user.setChildCourseId(resultSet.getInt("course_id"));
                            user.setChildCourseName(resultSet.getString("child_course_name"));
                            user.setEnrollmentDate(resultSet.getString("enrolment_date"));
                            user.setExamName(resultSet.getString("grade_item_name"));
                            user.setExamId(resultSet.getInt("exam_id"));


                            return  user;
                        }
                    }
            );
            return  systemUserList;
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
    public List<User> getSystemUsersWithProfileDetails() {

        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "u.user_id,   " +
                "u.user_name,   " +
                "u.role_id,   " +
                "r.role_name, " +
                "usrp.gender,   " +
                "usrp.city,   " +
                "usrp.country,  " +
                "usrp.first_name, " +
                "usrp.last_name, " +
                "u.class_id,   " +
                "c.class_name,   " +
                "(SELECT MAX(la.date_time)FROM log_access la WHERE la.user_id = u.user_id)last_acess  " +
                "FROM user u   " +
                "LEFT JOIN role r ON u.role_id = r.id " +
                "LEFT JOIN user_profile usrp ON usrp.user_id = u.user_id  " +
                "LEFT JOIN class c ON u.class_id = c.class_id   " +
                //   "LEFT JOIN user u  ON u.user_id = usrp.user_id " +
                //   "LEFT JOIN student_major sm ON sm.student_id = usrp.user_id " +
                "LEFT JOIN major m ON m.major_id = c.major_id " +
                "WHERE u.status = 'active'  " +
                "ORDER BY u.created_at DESC, u.user_id DESC ";

        List<User> systemUserList = getJdbcTemplate().query(
                sql,
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet resultSet, int i) throws SQLException {
                        User user = new User();

                        user.setMajorId(resultSet.getInt("major_id"));
                        user.setMajorShortCode(resultSet.getString("major_short_code"));
                        user.setMajorName(resultSet.getString("major_name"));
                        user.setUserId(resultSet.getLong("user_id"));
                        user.setUserName(resultSet.getString("user_name"));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setGender(resultSet.getString("gender"));

                        user.setRoleId(resultSet.getInt("role_id"));
                        user.setRoleName(resultSet.getString("role_name"));
                        //      user.setCity(resultSet.getString("city"));
                        //     user.setCountry(resultSet.getString("country"));
                        user.setClassId(resultSet.getInt("class_id"));
                        user.setClassName(resultSet.getString("class_name"));
                        user.setLastLogin(resultSet.getString("last_acess"));

                        return  user;
                    }
                }
        );
        return  systemUserList;
    }


/*    @Override
    public List<UserProfile> getSystemUsersWithProfileDetails() {

        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "usrp.user_id,   " +
                "usrp.first_name,   " +
                "usrp.last_name,   " +
                "usrp.role,   " +
                "usrp.gender,   " +
                "usrp.city,   " +
                "usrp.country,  " +
                "sc.class_id,   " +
                "c.class_name,   " +
                "(SELECT MAX(la.date_time)FROM log_access la WHERE la.user_id = usrp.user_id)last_acess  " +
                "FROM user_profile usrp   " +
                "LEFT JOIN student_class sc ON usrp.user_id = sc.student_id  " +
                "LEFT JOIN class c ON c.class_id = sc.class_id   " +
                "LEFT JOIN user u  ON u.user_id = usrp.user_id " +
                "LEFT JOIN student_major sm ON sm.student_id = usrp.user_id " +
                "LEFT JOIN major m ON m.major_id = sm.major_id " +
                "WHERE u.status = 'active'  " +
                "ORDER BY usrp.created_at DESC,usrp.user_id DESC ";

        List<UserProfile> systemUserList = getJdbcTemplate().query(
                sql,
                new RowMapper<UserProfile>() {
                    @Override
                    public UserProfile mapRow(ResultSet resultSet, int i) throws SQLException {
                        UserProfile user = new UserProfile();

                        user.setMajorId(resultSet.getInt("major_id"));
                        user.setMajorShortName(resultSet.getString("major_short_code"));
                        user.setMajorName(resultSet.getString("major_name"));
                        user.setUserId(resultSet.getLong("user_id"));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setRole(resultSet.getString("role"));
                        user.setGender(resultSet.getString("gender"));
                        user.setCity(resultSet.getString("city"));
                        user.setCountry(resultSet.getString("country"));
                        user.setClassId(resultSet.getInt("class_id"));
                        user.setClassName(resultSet.getString("class_name"));
                        user.setLastLoggedIn(resultSet.getString("last_acess"));

                        return  user;
                    }
                }
        );
        return  systemUserList;
    }*/

    @Override
    public List<User> getSystemUsersWithProfileDetailsForTeacher() {
        String sql = "SELECT  " +
                "u.user_id,  " +
                "usrp.first_name,  " +
                "usrp.last_name,  " +
                "usrp.role,  " +
                "usrp.gender,  " +
                "usrp.city,  " +
                "usrp.country,  " +
                "u.class_id,  " +
                "c.class_name,  " +
                "(SELECT MAX(la.date_time)FROM log_access la WHERE la.user_id = usrp.user_id)last_acess " +
                "FROM user u   " +
           //     "LEFT JOIN student_class sc ON usrp.user_id = sc.student_id  " +
                "LEFT JOIN class c ON c.class_id = u.class_id  " +
                "LEFT JOIN user_profile usrp ON u.user_id = usrp.user_id  " +
                "WHERE u.status = 'active' " +
                "AND u.role_id = 2  " +
                "OR  u.role_id = 3  " +
                "ORDER BY u.role_id DESC,usrp.created_at DESC,u.user_name ";

        List<User> systemUserList = getJdbcTemplate().query(
                sql,
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet resultSet, int i) throws SQLException {
                        User user = new User();

                        user.setUserId(resultSet.getLong("user_id"));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setRoleName(resultSet.getString("role"));
                        user.setGender(resultSet.getString("gender"));
                    //    user.setCity(resultSet.getString("city"));
                     //   user.setCountry(resultSet.getString("country"));
                        user.setClassId(resultSet.getInt("class_id"));
                        user.setClassName(resultSet.getString("class_name"));
                        user.setLastLogin(resultSet.getString("last_acess"));

                        return  user;
                    }
                }
        );
        return  systemUserList;
    }

    @Override
    public List<User> getSystemTeachersAndAdminsUserData() {
        String sql = "SELECT  " +
                "u.user_id,  " +
                "usrp.first_name,  " +
                "usrp.last_name,  " +
                "usrp.role,  " +
                "usrp.gender,  " +
                "usrp.city,  " +
                "usrp.country,  " +
                "u.class_id,  " +
                "c.class_name,  " +
                "(SELECT MAX(la.date_time)FROM log_access la WHERE la.user_id = usrp.user_id)last_acess " +
                "FROM user u  " +
            //    "LEFT JOIN student_class sc ON usrp.user_id = sc.student_id  " +
                "LEFT JOIN class c ON c.class_id = u.class_id  " +
                "LEFT JOIN user_profile usrp  ON u.user_id = usrp.user_id  " +
                "WHERE u.status = 'active' " +
                "AND u.role_id = 2  " +
                "OR  u.role_id = 1  " +
                "ORDER BY u.role_id ,u.user_name ";
        try{
            List<User> systemUserList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();

                            user.setUserId(resultSet.getLong("user_id"));
                            user.setFirstName(resultSet.getString("first_name"));
                            user.setLastName(resultSet.getString("last_name"));
                            user.setRoleName(resultSet.getString("role"));
                            user.setGender(resultSet.getString("gender"));
                         //   user.setCity(resultSet.getString("city"));
                         //   user.setCountry(resultSet.getString("country"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setLastLogin(resultSet.getString("last_acess"));

                            return  user;
                        }
                    }
            );
            return  systemUserList;
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
    public List<UserProfile> getSystemTeachersUserData() {
        String sql = "SELECT  " +
                "usrp.user_id,  " +
                "usrp.first_name,  " +
                "usrp.last_name,  " +
                "usrp.role,  " +
                "usrp.gender,  " +
                "usrp.city,  " +
                "usrp.country,  " +
                "u.class_id,  " +
                "c.class_name,  " +
                "(SELECT MAX(la.date_time)FROM log_access la WHERE la.user_id = usrp.user_id)last_acess " +
                "FROM user_profile usrp  " +
            //    "LEFT JOIN student_class sc ON usrp.user_id = sc.student_id  " +
                "LEFT JOIN user u  ON u.user_id = usrp.user_id  " +
                "LEFT JOIN class c ON c.class_id = u.class_id  " +
                "WHERE u.status = 'active' " +
                "AND u.role_id = 2  " +
                "ORDER BY  u.user_name ";
        try{
            List<UserProfile> systemUserList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<UserProfile>() {
                        @Override
                        public UserProfile mapRow(ResultSet resultSet, int i) throws SQLException {
                            UserProfile user = new UserProfile();

                            user.setUserId(resultSet.getLong("user_id"));
                            user.setFirstName(resultSet.getString("first_name"));
                            user.setLastName(resultSet.getString("last_name"));
                            user.setRole(resultSet.getString("role"));
                            user.setGender(resultSet.getString("gender"));
                            user.setCity(resultSet.getString("city"));
                            user.setCountry(resultSet.getString("country"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setLastLoggedIn(resultSet.getString("last_acess"));

                            return  user;
                        }
                    }
            );
            return  systemUserList;
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
    public List<User> getSystemStudentsUserData() {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "u.user_id,  " +
                "usrp.first_name,  " +
                "usrp.last_name,  " +
                "usrp.role, " +
                "c.intake_period, " +
                "usrp.gender,  " +
                "usrp.city,  " +
                "usrp.country,  " +
                "u.class_id,  " +
                "c.class_name,  " +
                "(SELECT MAX(la.date_time)FROM log_access la WHERE la.user_id = usrp.user_id)last_acess " +
                "FROM user u  " +
             //   "LEFT JOIN student_class sc ON usrp.user_id = sc.student_id  " +
                "LEFT JOIN class c ON c.class_id = u.class_id  " +
                "LEFT JOIN  user_profile usrp ON u.user_id = usrp.user_id  " +
             //   "LEFT JOIN student_major sm ON sm.student_id = usrp.user_id " +
                "LEFT JOIN major m ON m.major_id = c.major_id " +
                "WHERE u.status = 'active' " +
                "AND u.role_id = 3  " +
                "ORDER BY c.intake_period DESC,u.user_name ";
        try{
            List<User> systemUserList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();
                            user.setMajorId(resultSet.getInt("major_id"));
                            user.setMajorShortCode(resultSet.getString("major_short_code"));
                            user.setMajorName(resultSet.getString("major_name"));
                            user.setUserId(resultSet.getLong("user_id"));
                            user.setFirstName(resultSet.getString("first_name"));
                            user.setLastName(resultSet.getString("last_name"));
                            user.setRoleName(resultSet.getString("role"));
                            user.setIntake(resultSet.getInt("intake_period"));
                            user.setGender(resultSet.getString("gender"));
                       //     user.setCity(resultSet.getString("city"));
                        //    user.setCountry(resultSet.getString("country"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setLastLogin(resultSet.getString("last_acess"));

                            return  user;
                        }
                    }
            );
            return  systemUserList;
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
    public List<User> getSystemStudentUserDataFilteredByIntake(String intake) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "u.user_id,  " +
                "usrp.first_name,  " +
                "usrp.last_name,  " +
                "usrp.role, " +
                "c.intake_period, " +
                "usrp.gender,  " +
                "usrp.city,  " +
                "usrp.country,  " +
                "u.class_id,  " +
                "c.class_name,  " +
                "(SELECT MAX(la.date_time)FROM log_access la WHERE la.user_id = usrp.user_id)last_acess " +
                "FROM user u   " +
              //  "LEFT JOIN student_class sc ON usrp.user_id = sc.student_id  " +
                "LEFT JOIN class c ON c.class_id = u.class_id  " +
                "LEFT JOIN user_profile usrp ON u.user_id = usrp.user_id  " +
             //   "LEFT JOIN student_major sm ON sm.student_id = usrp.user_id " +
                "LEFT JOIN major m ON m.major_id = c.major_id " +
                "WHERE u.status = 'active' " +
                "AND u.role_id = 3 " +
                "AND c.intake_period = ? " +
                "ORDER BY c.intake_period DESC,u.user_name ";
        try{
            List<User> systemUserList = getJdbcTemplate().query(
                    sql,
                    new Object[]{intake},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();
                            user.setMajorId(resultSet.getInt("major_id"));
                            user.setMajorShortCode(resultSet.getString("major_short_code"));
                            user.setMajorName(resultSet.getString("major_name"));
                            user.setUserId(resultSet.getLong("user_id"));
                            user.setFirstName(resultSet.getString("first_name"));
                            user.setLastName(resultSet.getString("last_name"));
                            user.setRoleName(resultSet.getString("role"));
                            user.setIntake(resultSet.getInt("intake_period"));
                            user.setGender(resultSet.getString("gender"));
                        //    user.setCity(resultSet.getString("city"));
                        //    user.setCountry(resultSet.getString("country"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setLastLogin(resultSet.getString("last_acess"));

                            return  user;
                        }
                    }
            );
            return  systemUserList;
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
    public List<User> getSystemStudentUserDataFilteredByMajor(int majorId) {
        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "u.user_id,  " +
                "usrp.first_name,  " +
                "usrp.last_name,  " +
                "usrp.role, " +
                "c.intake_period, " +
                "usrp.gender,  " +
                "usrp.city,  " +
                "usrp.country,  " +
                "u.class_id,  " +
                "c.class_name,  " +
                "(SELECT MAX(la.date_time)FROM log_access la WHERE la.user_id = usrp.user_id)last_acess " +
                "FROM user u   " +
             //   "LEFT JOIN student_class sc ON usrp.user_id = sc.student_id  " +
                "LEFT JOIN class c ON c.class_id = u.class_id  " +
                "LEFT JOIN user_profile usrp ON u.user_id = usrp.user_id  " +
             //   "LEFT JOIN student_major sm ON sm.student_id = usrp.user_id " +
                "LEFT JOIN major m ON m.major_id = c.major_id " +
                "WHERE u.status = 'active' " +
                "AND u.role_id = 3  " +
                "AND m.major_id = ?  " +
                "ORDER BY c.intake_period DESC,u.user_name ";
        try{
            List<User> systemUserList = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();
                            user.setMajorId(resultSet.getInt("major_id"));
                            user.setMajorShortCode(resultSet.getString("major_short_code"));
                            user.setMajorName(resultSet.getString("major_name"));
                            user.setUserId(resultSet.getLong("user_id"));
                            user.setFirstName(resultSet.getString("first_name"));
                            user.setLastName(resultSet.getString("last_name"));
                            user.setRoleName(resultSet.getString("role"));
                            user.setIntake(resultSet.getInt("intake_period"));
                            user.setGender(resultSet.getString("gender"));
                         //   user.setCity(resultSet.getString("city"));
                         //   user.setCountry(resultSet.getString("country"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setLastLogin(resultSet.getString("last_acess"));

                            return  user;
                        }
                    }
            );
            return  systemUserList;
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
    public List<User> getSystemStudentUserDataFilteredByMajorAndIntake(int majorId, String intake) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "u.user_id,  " +
                "usrp.first_name,  " +
                "usrp.last_name,  " +
                "usrp.role, " +
                "c.intake_period, " +
                "usrp.gender,  " +
                "usrp.city,  " +
                "usrp.country,  " +
                "u.class_id,  " +
                "c.class_name,  " +
                "(SELECT MAX(la.date_time)FROM log_access la WHERE la.user_id = usrp.user_id)last_acess " +
                "FROM user u   " +
             //   "LEFT JOIN student_class sc ON usrp.user_id = sc.student_id  " +
                "LEFT JOIN class c ON c.class_id = u.class_id  " +
                "LEFT JOIN user_profile usrp ON u.user_id = usrp.user_id  " +
             //   "LEFT JOIN student_major sm ON sm.student_id = usrp.user_id " +
                "LEFT JOIN major m ON m.major_id = c.major_id " +
                "WHERE u.status = 'active' " +
                "AND u.role_id = 3 " +
                "AND c.intake_period = ? " +
                "AND m.major_id =  ?  " +
                "ORDER BY c.intake_period DESC,u.user_name ";
        try{
            List<User> systemUserList = getJdbcTemplate().query(
                    sql,
                    new Object[]{intake,majorId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();
                            user.setMajorId(resultSet.getInt("major_id"));
                            user.setMajorShortCode(resultSet.getString("major_short_code"));
                            user.setMajorName(resultSet.getString("major_name"));
                            user.setUserId(resultSet.getLong("user_id"));
                            user.setFirstName(resultSet.getString("first_name"));
                            user.setLastName(resultSet.getString("last_name"));
                            user.setRoleName(resultSet.getString("role"));
                            user.setIntake(resultSet.getInt("intake_period"));
                            user.setGender(resultSet.getString("gender"));
                         //   user.setCity(resultSet.getString("city"));
                         //   user.setCountry(resultSet.getString("country"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setLastLogin(resultSet.getString("last_acess"));

                            return  user;
                        }
                    }
            );
            return  systemUserList;
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
    public String adminUpdateUserProfileDetails(UserProfile user) {
        String sql = "UPDATE user_profile  " +
                "SET first_name =  ? , " +
                "last_name = ? , " +
                "gender = ?, " +
                "data_of_birth = ?, " +
                "role = ? ,  " +
                "city =  ? ,  " +
                "country = ?   " +
                "WHERE user_id =  ?;";
        try {

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ user.getFirstName(),user.getLastName(),user.getGender(),user.getDateOfBirth(),user.getRole(),user.getCity(),user.getCountry(),user.getUserId() }
            );
            if(1 == returnValue)
                return "@UserDaoImpl::adminUpdateUserProfileDetails::USER PROFILE UPDATE SUCCESSFUL";
            else
                return "@UserDaoImpl::adminUpdateUserProfileDetails::USER PROFILE UPDATE FAILED !!";

        }catch (Exception e){
            e.printStackTrace();
            return "@UserDaoImpl::adminUpdateUserProfileDetails::USER PROFILE UPDATE FAILED !!";
        }
    }

    @Override
    public String studentUpdateDetails(UserProfile user) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp updateTimestamp = new java.sql.Timestamp(date.getTime());
        String sql = "UPDATE user_profile  " +
                "SET "+
                "data_of_birth = ?, " +
                "city =  ? , " +
                "country =  ? , " +
                "primary_language =  ? , " +
                "self_description =  ? , " +
                "updated_at = ? " +
                "WHERE user_id =  ?;";
        try {

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ user.getDateOfBirth(), user.getCity(), user.getCountry(), user.getPrimaryLanguage(), user.getSelfDescription(),updateTimestamp ,user.getUserId()}
            );
            if(1 == returnValue)
            {
                System.out.println("@UserDaoImpl::studentUpdateDetails::USER PROFILE UPDATE SUCCESSFUL");
                return "200";
            }
            else
            {
                System.out.println("@UserDaoImpl::adminUpdateUserProfileDetails::USER PROFILE UPDATE FAILED !!");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("@UserDaoImpl::adminUpdateUserProfileDetails::USER PROFILE UPDATE FAILED !!");
            return "400";
        }
    }

    @Override
    public String adminUpdateUserMainDetails(User user) {
        String sql = "UPDATE user " +
                "SET user_name =  ? , " +
                "email =  ? , " +
                "role_id = ? " +
                "WHERE user_id =  ?;";
        try {

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{user.getUserName(),user.getEmail(),user.getRoleId(),user.getUserId() }
            );
            if(1 == returnValue)
            {
                System.out.println("@UserDaoImpl::adminUpdateUserMainDetails::USER MAIN UPDATE FAILED !!");
                return "200";
            }
            else
            {
                System.out.println("@UserDaoImpl::adminUpdateUserMainDetails::USER MAIN UPDATE FAILED !!");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "@UserDaoImpl::adminUpdateUserMainDetails::USER MAIN UPDATE FAILED !!";
        }
    }

    @Override
    public String studentUpdateUserMainDetails(User user) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp updateTimestamp = new java.sql.Timestamp(date.getTime());

        String sql = "UPDATE user " +
                "SET email =  ? ," +
                "updated_at = ? " +
                "WHERE user_id =  ?;";
        try {

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{user.getEmail(),updateTimestamp,user.getUserId() }
            );
            if(1 == returnValue)
            {
                System.out.println("@UserDaoImpl::adminUpdateUserMainDetails::USER MAIN UPDATE FAILED !!");
                return "200";
            }
            else
            {
                System.out.println("@UserDaoImpl::adminUpdateUserMainDetails::USER MAIN UPDATE FAILED !!");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("@UserDaoImpl::adminUpdateUserMainDetails::USER MAIN UPDATE FAILED !!");
            return "200";
        }

    }

    @Override
    public String adminUpdateStudentClass(StudentClass studentClass) {
        String sql = "UPDATE student_class " +
                "SET class_id = ? " +
                "WHERE student_id =  ?;";
        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentClass.getClassId(),studentClass.getStudentId() }
            );
            if(1 == returnValue)
                return "@UserDaoImpl::@adminUpdateStudentClass::UPDATE SUCCESSFUL";
            else
                return "@UserDaoImpl::@adminUpdateStudentClass::UPDATE FAILED !!";

        }catch (Exception e){
            e.printStackTrace();
            return "@UserDaoImpl::@adminUpdateStudentClass::UPDATE FAILED !!";
        }
    }

    @Override
    public UserProfile getUserDefaultPasswordsForReset(long userId) {
        String sql = "SELECT " +
                "up.user_id, " +
                "up.first_name, " +
                "up.last_name " +
                "FROM user_profile up " +
                "WHERE up.user_id =  ?";

        try{
            UserProfile userDefaultPsw = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    new RowMapper<UserProfile>() {
                        @Override
                        public UserProfile mapRow(ResultSet resultSet, int i) throws SQLException {

                            UserProfile userProfile = new UserProfile();
                            userProfile.setUserId(resultSet.getLong("user_id"));
                            userProfile.setFirstName(resultSet.getString("first_name"));
                            userProfile.setLastName(resultSet.getString("last_name"));

                            return userProfile;
                        }
                    }

            );
            return userDefaultPsw;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String adminResetUserPassword(long userID, String defaultPassword) {
        String sql = "UPDATE user " +
                "SET PASSWORD =  ? " +
                "WHERE user_id =  ?;";
        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{defaultPassword,userID}
            );
            if(1 == returnValue)
                return "@UserDaoImpl::@adminResetUserPassword::PASSWORD RESET SUCCESSFUL";
            else
                return "@UserDaoImpl::@adminResetUserPassword::PASSWORD RESET FAILED !!";

        }catch (Exception e){
            e.printStackTrace();
            return "@UserDaoImpl::@adminResetUserPassword::PASSWORD RESET FAILED !!";
        }
    }

    @Override
    public String getUserPasswordByUserId(long userId) {
        String sql = "SELECT usr.password " +
                "FROM user usr  " +
                "WHERE usr.user_id = ?";

        try{

            String psw = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{ userId},
                    String.class
                    );
            return psw;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String changePassword(long userId, String newPassword) {
        String sql = "UPDATE user " +
                "SET PASSWORD =  ? " +
                "WHERE user_id =  ?;";
        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{newPassword,userId}
            );
            if(1 == returnValue)
                return "@UserDaoImpl::@changePassword::PASSWORD CHANGED SUCCESSFUL";
            else
                return "@UserDaoImpl::@changePassword::PASSWORD CHANGE FAILED !!";

        }catch (Exception e){
            e.printStackTrace();
            return "@UserDaoImpl::@changePassword::PASSWORD CHANGE FAILED !!";
        }
    }

    @Override
    public UserProfile getCurrentProfileData(final long userId) {
        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "usr.user_id,  " +
                "up.profile_image_url,  " +
                "up.first_name,  " +
                "up.last_name,  " +
                "c.class_id,  " +
                "c.class_name,  " +
                "c.intake_period,  " +
                "up.gender,  " +
                "up.data_of_birth, " +
                "up.city , " +
                "up.country, " +
                "up.role,  " +
                "up.degree_type,  " +
                "up.self_description ,   " +
                "up.primary_language  " +
                "FROM user usr " +
                "LEFT JOIN user_profile up ON usr.user_id = up.user_id " +
                "LEFT JOIN class c ON c.class_id = usr.class_id   " +
                "LEFT JOIN major m ON m.major_id = c.major_id " +
                "WHERE usr.user_id = ? ";

        try{
            UserProfile userProfileDetails = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    new RowMapper<UserProfile>() {
                        @Override
                        public UserProfile mapRow(ResultSet resultSet, int i) throws SQLException {
                            UserProfile userProfile = new UserProfile();

                            userProfile.setUserId(resultSet.getLong("user_id"));
                            userProfile.setProfileImageUrl(resultSet.getString("profile_image_url"));
                            userProfile.setFirstName(resultSet.getString("first_name"));
                            userProfile.setLastName(resultSet.getString("last_name"));
                            userProfile.setClassId(resultSet.getInt("class_id"));
                            userProfile.setClassName(resultSet.getString("class_name"));
                            userProfile.setGender(resultSet.getString("gender"));
                            userProfile.setDateOfBirth(resultSet.getString("data_of_birth"));
                            userProfile.setCity(resultSet.getString("city"));
                            userProfile.setIntake(resultSet.getString("intake_period"));
                            userProfile.setCountry(resultSet.getString("country"));
                            userProfile.setSelfDescription(resultSet.getString("self_description"));
                            userProfile.setRole(resultSet.getString("role"));
                            userProfile.setDegreeType(resultSet.getString("degree_type"));
                            userProfile.setPrimaryLanguage(resultSet.getString("primary_language"));

                            userProfile.setMajorId(resultSet.getInt("major_id"));
                            userProfile.setMajorShortName(resultSet.getString("major_short_code"));
                            userProfile.setMajorName(resultSet.getString("major_name"));

                            return userProfile;
                        }
                    }

            );
            return userProfileDetails;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserProfile getUserSettings(long userId) {
        String sql = "SELECT  " +
                "up.user_id, " +
                "up.degree_type, " +
                "up.primary_language " +
                "FROM user_profile up " +
                "WHERE user_id =  ? ";

        try{
            UserProfile userDefaultPsw = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    new RowMapper<UserProfile>() {
                        @Override
                        public UserProfile mapRow(ResultSet resultSet, int i) throws SQLException {

                            UserProfile userProfile = new UserProfile();
                            userProfile.setUserId(resultSet.getLong("user_id"));
                            userProfile.setDegreeType(resultSet.getString("degree_type"));
                            userProfile.setPrimaryLanguage(resultSet.getString("primary_language"));

                            return userProfile;
                        }
                    }

            );
            return userDefaultPsw;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public String changeUserProfileSettings(UserProfile user) {
        String sql = "UPDATE user_profile  " +
            //    "SET degree_type =  ? , " +
                "primary_language = ? " +
                "WHERE user_id = ? ;";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ user.getPrimaryLanguage(),user.getUserId() }
          //  new Object[]{user.getDegreeType(),user.getPrimaryLanguage(),user.getUserId()}
            );
            if(1 == returnValue)
                return "@UserDaoImpl::@cchangeUserProfileSettings::USER SETTINGS CHANGED SUCCESSFUL";
            else
                return "@UserDaoImpl::@changeUserProfileSettings::USER SETTINGS CHANGE FAILED !!";

        }catch (Exception e){
            e.printStackTrace();
            return "@UserDaoImpl::@changeUserProfileSettings::USER SETTINGS CHANGE FAILED !!";
        }
    }

    @Override
    public String disableUser(long userId) {
        String sql = "UPDATE user " +
                "SET STATUS = 'not-active'" +
                "WHERE user_id = ?;";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{userId}
            );
            if(1 == returnValue)
                return "@UserDaoImpl::@disableUser::USER DISABLED SUCCESSFUL";
            else
                return "@UserDaoImpl::@disableUser::USER  DISABLED FAILED !!";

        }catch (Exception e){
            e.printStackTrace();
            return "@UserDaoImpl::@disableUser::USER  DISABLED FAILED !!";
        }



    }

    @Override
    public String enableUser(long userId) {
        String sql = "UPDATE user " +
                "SET STATUS = 'active' " +
                "WHERE user_id =  ?;";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{userId}
            );
            if(1 == returnValue)
                return "@UserDaoImpl::@enableUser::USER ENABLED SUCCESSFUL";
            else
                return "@UserDaoImpl::@enableUser::USER  ENABLED FAILED !!";

        }catch (Exception e){
            e.printStackTrace();
            return "@UserDaoImpl::@enableUser::USER  ENABLED FAILED !!";
        }




    }

    @Override
    public String addNewIntake(String intakeName, String intakeYear) {
        String sql = "INSERT INTO  intake (intake_name,intake_year) VALUES(?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{intakeName,intakeYear}
            );
            if(returnValue == 1){
                System.out.println("NEW INTAKE ADDED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("NEW INTAKE ADD FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "INTERNAL SYSTEM QUERY ERROR!!";
        }
    }

    @Override
    public List<User> getStudentsNotEnrolledInCourseList(int childCourseId) {
        String sql = "SELECT " +
                "    usr.user_id, " +
                "    usr.role_id, " +
                "    usr.user_name, " +
                "    cls.class_name " +
                "    FROM user usr , class cls " +
                "    WHERE  " +
                "    usr.role_id = 3  " +
                "    AND usr.class_id = cls.class_id  " +
                "    AND usr.status = 'active' " +
                "    AND usr.user_id NOT IN (SELECT student_id FROM course_enrolment WHERE is_enrolled =1 AND course_id = ?);";

        try{

            List<User> studentList = getJdbcTemplate().query(
                    sql,
                    new Object[]{childCourseId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();

                            user.setUserId(resultSet.getLong("user_id"));
                            user.setRoleId(resultSet.getInt("role_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setClassName(resultSet.getString("class_name"));
                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@UserDaoImpl::@getStudentsNotEnrolledInCourseList::Error!!");
            e.printStackTrace();
            return null;

        }catch (Exception e ){
            System.out.println("@UserDaoImpl::@getStudentsNotEnrolledInCourseList::Error!!");
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<User> getStudentsEnrolledInCourseList(int childCourseId) {
        String sql = "  SELECT  " +
                " usr.user_id ,  " +
                " usr.user_name , " +
                " cls.class_name , " +
                " cls.class_id , " +
                " DATE_FORMAT(ce.enrolment_date, '%D %M %Y') enrolment_date" +
                " FROM course_enrolment ce, user usr, class cls " +
                "WHERE  usr.user_id = ce.student_id " +
                "AND cls.class_id = usr.class_id  " +
                "AND usr.status = 'active' " +
                "AND ce.is_enrolled=1  " +
                "AND ce.course_id = ?;";
        try{
            List<User> studentList = getJdbcTemplate().query(
                    sql,
                    new Object[]{childCourseId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();

                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setEnrolmentDate(resultSet.getString("enrolment_date"));
                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@UserDaoImpl::@getStudentsEnrolledInCourseList::Error!!");
            e.printStackTrace();
            return null;

        }catch (Exception e ){
            System.out.println("@UserDaoImpl::@getStudentsEnrolledInCourseList::Error!!");
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public List<User> getStudentEnrolledInCourseWithGradeAverage(int courseId) {
        String sql  = "SELECT   " +
                "usr.user_id ,  " +
                "usr.user_name ,  " +
                "DATE_FORMAT(ce.enrolment_date, '%D %M %Y') enrolment_date, " +
                "(SELECT cast( SUM((grade.grade/grade_items.max_grade)*grade_items.weight*100)  as DECIMAL(5,2) ) " +
                "FROM grade,child_courses,grade_items " +
                "WHERE child_courses.child_course_id = grade.course_id " +
                "AND grade_items.grade_item_id = grade.grade_item_id " +
                "AND child_courses.child_course_id = ce.course_id " +
                "AND grade.student_id = usr.user_id) grade_average " +
                "FROM course_enrolment ce, user usr " +
                "WHERE usr.user_id = ce.student_id   " +
                "AND usr.status = 'active'  " +
                "AND ce.is_enrolled=1  " +
                "AND ce.course_id =  ?;";

        try{
            List<User> studentList = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();

                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("user_name"));
//                            user.setClassId(resultSet.getInt("class_id"));
//                            user.setClassName(resultSet.getString("class_name"));
                           user.setEnrolmentDate(resultSet.getString("enrolment_date"));
                            user.setCourseGradeAverage(resultSet.getDouble("grade_average"));
                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@UserDaoImpl::@getStudentsEnrolledInCourseList::Error!!");
            e.printStackTrace();
            return null;

        }catch (Exception e ){
            System.out.println("@UserDaoImpl::@getStudentsEnrolledInCourseList::Error!!");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getStudentsNotEnrolledInExamList(int examId) {

        String sql = "SELECT  u.user_id, u.role_id, u.user_name, c.class_name , cc.parent_course_id " +
                "FROM user u LEFT JOIN class c ON c.class_id = u.class_id " +
                "left join course_enrolment ce on u.user_id = ce.student_id " +
                "left join child_courses cc on ce.course_id = cc.child_course_id " +
                "left join parent_courses pc on pc.parent_course_id = cc.parent_course_id " +
                "left join exam ex on pc.parent_course_id = ex.parent_course_id " +
                "WHERE  u.status = 'active' AND u.role_id = 3 and ex.id = ? "+
                "AND u.user_id NOT IN ( SELECT er.student_id FROM exam_enrolment er WHERE er.exam_id = ? ) ";

        try{

            List<User> studentList = getJdbcTemplate().query(
                    sql,
                    new Object[]{examId, examId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();

                            user.setUserId(resultSet.getLong("user_id"));
                            user.setRoleId(resultSet.getInt("role_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setClassName(resultSet.getString("class_name"));
                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@@UserDaoImpl::getStudentsNotEnrolledInExamList::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@@UserDaoImpl::getStudentsNotEnrolledInExamList::ERROR !");
            e.printStackTrace();
            return  null;
        }


    }

    @Override
    public List<User> getStudentsNotEnrolledInAnyClassList() {
        String sql = "SELECT  " +
                "u.user_id, " +
                "u.role_id,  " +
                "u.user_name  " +
             //   "c.class_name  " +
                "FROM user u  " +
             //   "LEFT JOIN student_class sc ON u.user_id = sc.student_id  " +
              //  "LEFT JOIN class c ON c.class_id = sc.class_id   " +
                "WHERE   " +
                "u.status = 'active'  " +
                "AND u.role_id = 3  AND u.class_id is NULL " +
              //  "AND u.user_id NOT IN (SELECT student_id FROM student_class)" +
                "ORDER by u.user_id, u.user_name";


        try{

            List<User> studentList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();

                            user.setUserId(resultSet.getLong("user_id"));
                            user.setRoleId(resultSet.getInt("role_id"));
                            user.setUserName(resultSet.getString("user_name"));
                        //    user.setClassName(resultSet.getString("class_name"));
                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@UserDaoImpl::@getStudentsNotEnrolledInAnyClassList::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@@UserDaoImpl::@getStudentsNotEnrolledInAnyClassList::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<User> getClassEnrolledListByClassId(int classId) {
        String sql = "SELECT " +
                "u.user_id,  " +
                "u.user_name,  " +
                "c.class_name, " +
                "DATE_FORMAT(u.created_at, '%D %M %Y') enrolledDate  " +
//                "FROM student_class sc  " +
//                "LEFT JOIN user u ON u.user_id =sc.student_id  " +
                "FROM class c, user u WHERE c.class_id = u.class_id " +
                "AND u.status = 'active'  " +
                "AND u.class_id= ? " +
                "ORDER BY u.user_id, u.user_name";


        try{

            List<User> studentList = getJdbcTemplate().query(
                    sql,
                    new Object[]{classId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();
                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setEnrolmentDate(resultSet.getString("enrolledDate"));
                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@UserDaoImpl::@getClassEnrolledListByClassId::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@@UserDaoImpl::@getClassEnrolledListByClassId::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<User> getStudentsEnrolledInExamList(int examId) {
        String sql = "SELECT  " +
                "u.user_id, " +
                "u.user_name, " +
                "c.class_id, " +
                "c.class_name, " +
                "ee.exam_score, " +
                "DATE_FORMAT(ee.enrolment_date, '%D %M %Y') enrolment_date " +
                "FROM exam_enrolment ee  " +
                "LEFT JOIN user u ON ee.student_id = u.user_id " +
                "LEFT JOIN class c ON c.class_id = u.class_id " +
                "WHERE  " +
                "u.status = 'active' " +
                "AND u.role_id = 3 " +
                "AND ee.is_Enrolled = 1 " +
                "AND ee.exam_id = ? " ;

        try{

            List<User> studentList = getJdbcTemplate().query(
                    sql,
                    new Object[]{examId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {

                            User user = new User();

                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setEnrolmentDate(resultSet.getString("enrolment_date"));
                            user.setCurrentEnrolledExamScore(resultSet.getString("exam_score"));

                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl:: getStudentsEnrolledInExamList::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl:: getStudentsEnrolledInExamList::EMPTY !");
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public List<User> getStudentsEnrolledInClearanceExam(int clearanceExamId) {
        String sql = "SELECT  " +
                "u.user_id,  " +
                "u.user_name,  " +
                "c.class_id,  " +
                "c.class_name,  " +
                "up.degree_type, " +
                "DATE_FORMAT(clee.enrollment_date, '%D %M %Y') enrolment_date  " +
                "FROM clearance_exam_enrolment clee " +
                "LEFT JOIN user u ON clee.student_id = u.user_id  " +
             //   "LEFT JOIN student_class sc ON u.user_id = sc.student_id   " +
                "LEFT JOIN class c ON c.class_id = u.class_id  " +
                "LEFT JOIN user_profile up ON up.user_id = u.user_id " +
                "WHERE   " +
                "u.status = 'active'  " +
                "AND u.role_id = 3  " +
                "AND clee.is_enrolled = 1 " +
                "AND clee.clearance_exam_id = ? ";

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
                            user.setEnrolmentDate(resultSet.getString("enrolment_date"));
                            user.setDegreeType(resultSet.getString("degree_type"));
                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ExamEnrolmentDaoImpl:: getStudentsEnrolledInExamList::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ExamEnrolmentDaoImpl:: getStudentsEnrolledInExamList::EMPTY !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<User> getCourseEnrollmentListByCourseId(int courseId) {

        String sql = "SELECT  " +
                "up.user_id, " +
                " u.user_name, " +
                "up.degree_type, " +
                "c.class_id, " +
                "c.class_name, " +
                " DATE_FORMAT(ce.enrolment_date, '%D %M %Y') enrolment_date " +
                "FROM course_enrolment ce " +
                "LEFT JOIN user u ON u.user_id = ce.student_id " +
                "LEFT JOIN user_profile up ON up.user_id = ce.student_id " +
            //    "LEFT JOIN student_class sc ON sc.student_id = up.user_id " +
                "LEFT JOIN class c ON c.class_id = u.class_id " +
                " WHERE  " +
                " u.status = 'active' " +
                "AND ce.is_enrolled=1  " +
                "AND ce.has_unenrolled = 0 " +
                "AND ce.course_id = ? ";
        try{
            List<User> studentList = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();

                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setEnrolmentDate(resultSet.getString("enrolment_date"));
                            user.setDegreeType(resultSet.getString("degree_type"));
                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@UserDaoImpl::@getCourseEnrollmentListByCourseId::Error!!");
            e.printStackTrace();
            return null;

        }catch (Exception e ){
            System.out.println("@UserDaoImpl::@getCourseEnrollmentListByCourseId::Error!!");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getCourseEnrollmentRequestList(int courseId) {
        String sql = "  SELECT  " +
                "  u.user_id , " +
                "  u.user_name , " +
                "  up.degree_type,  " +
                "  c.class_name ,  " +
                "  c.class_id, " +
                "  DATE_FORMAT( ce.submission_date, '%D %M %Y') submission_date  " +
                "   FROM course_student_request_enrolment ce " +
                "   LEFT JOIN user u ON u.user_id = ce.student_id  " +
                "   LEFT JOIN user_profile up ON up.user_id = ce.student_id  " +
          //      "   LEFT JOIN student_class sc ON sc.student_id = up.user_id " +
                "   LEFT JOIN class c ON c.class_id = u.class_id  " +
                "    WHERE  " +
                "    u.status = 'active' " +
                "    AND ce.enrolment_status=0  " +
                "    AND ce.course_id = ?;";
        try{
            List<User> studentList = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();
                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                           user.setSubmissionDate(resultSet.getString("submission_date"));
                            user.setDegreeType(resultSet.getString("degree_type"));
                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@UserDaoImpl::@getCourseEnrollmentRequestList::Error!!");
            e.printStackTrace();
            return null;

        }catch (Exception e ){
            System.out.println("@UserDaoImpl::@getCourseEnrollmentRequestList::Error!!");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getExamEnrollmentListByExamId(int examId) {
        String sql = "SELECT  " +
                "up.user_id,  " +
                " u.user_name,  " +
                "up.degree_type,  " +
                "c.class_id,  " +
                "c.class_name,  " +
                "DATE_FORMAT(ee.enrolment_date, '%D %M %Y') enrolment_date " +
                "FROM exam_enrolment ee " +
                "LEFT JOIN user u ON u.user_id = ee.student_id  " +
                "LEFT JOIN user_profile up ON up.user_id = ee.student_id  " +
         //       "LEFT JOIN student_class sc ON sc.student_id = up.user_id  " +
                "LEFT JOIN class c ON c.class_id = u.class_id  " +
                " WHERE   " +
                "u.status = 'active'  " +
                "AND ee.is_Enrolled =1   " +
                "AND ee.has_unEnrolled = 0  " +
                "AND ee.exam_id = ?  " +
                "ORDER BY u.user_name;";

        try{
            List<User> studentList = getJdbcTemplate().query(
                    sql,
                    new Object[]{examId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();

                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setEnrolmentDate(resultSet.getString("enrolment_date"));
                            user.setDegreeType(resultSet.getString("degree_type"));
                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@UserDaoImpl::@getExamEnrollmentListByExamId::Error!!");
            e.printStackTrace();
            return null;

        }catch (Exception e ){
            System.out.println("@UserDaoImpl::@getExamEnrollmentListByExamId::Error!!");
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public List<User> getExamEnrollmentRequestList(int examId) {

        String sql = " SELECT   " +
                "u.user_id , " +
                "u.user_name , " +
                "up.degree_type,   " +
                "c.class_name ,   " +
                "c.class_id,  " +
                "DATE_FORMAT( er.submission_date, '%D %M %Y') submission_date   " +
                "FROM exam_student_request_enrolment er  " +
                "LEFT JOIN user u ON u.user_id = er.student_id  " +
                "LEFT JOIN user_profile up ON up.user_id = er.student_id  " +
           //     " LEFT JOIN student_class sc ON sc.student_id = up.user_id  " +
                "LEFT JOIN class c ON c.class_id = u.class_id  " +
                " WHERE   " +
                "u.status = 'active' " +
                "AND er.enrolment_status=0   " +
                "AND er.exam_id = ?;";


        try{
            List<User> studentList = getJdbcTemplate().query(
                    sql,
                    new Object[]{examId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();
                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setClassId(resultSet.getInt("class_id"));
                            user.setClassName(resultSet.getString("class_name"));
                            user.setSubmissionDate(resultSet.getString("submission_date"));
                            user.setDegreeType(resultSet.getString("degree_type"));
                            return user;
                        }
                    }

            );
            return studentList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@UserDaoImpl::@getExamEnrollmentRequestList::Error!!");
            e.printStackTrace();
            return null;

        }catch (Exception e ){
            System.out.println("@UserDaoImpl::@getExamEnrollmentRequestList::Error!!");
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public List<User> getLastAddedUsersList() {
        String sql = " SELECT" +
                " usrp.first_name, " +
                "usrp.last_name, " +
                "usrp.user_id " +
                " FROM user_profile usrp " +
                " ORDER BY usrp.created_at DESC,usrp.user_id DESC " +
                " LIMIT 5;";

        try{
            List<User> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();
                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("first_name")+" "+resultSet.getString("last_name"));
                            return user;
                        }
                    }

            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@UserDaoImpl::@getLastAddedUsersList::Empty!!");
            e.printStackTrace();
            return null;

        }catch (Exception e ){
            System.out.println("@UserDaoImpl::@getLastAddedUsersList::Error!!");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getLastEditedUsersList() {
        String sql = "SELECT " +
                "usrp.first_name,  " +
                "usrp.last_name, " +
                "usrp.user_id  " +
                "FROM user_profile usrp  " +
                "ORDER BY usrp.updated_at DESC " +
                "LIMIT 5;";

        try{
            List<User> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();
                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("first_name")+" "+resultSet.getString("last_name"));
                            return user;
                        }
                    }

            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@UserDaoImpl::@getLastEditedUsersList::Empty!!");
            e.printStackTrace();
            return null;

        }catch (Exception e ){
            System.out.println("@UserDaoImpl::@getLastEditedUsersList::Error!!");
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<String> getCurrentUserIntakesList() {
       String sql ="SELECT DISTINCT " +
               "up.intake " +
               "FROM user_profile up " +
               "ORDER BY up.intake DESC ";

        try{
            List<String> list = (List<String>) getJdbcTemplate().queryForList(
                    sql,
                    String.class
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
    public List<String> getCurrentClassIntakeList() {
        String sql = "SELECT DISTINCT " +
                "cl.intake_period " +
                "FROM class cl " +
                "ORDER BY cl.intake_period DESC";

        try{
            List<String> list = (List<String>) getJdbcTemplate().queryForList(
                    sql,
                    String.class
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
    public boolean checkIfUserIdInSystem(long userId) {
        String sql = "SELECT  " +
                "u.id " +
                "FROM user u  " +
                "WHERE u.status = 'active' " +
                "AND u.user_id = ?";

        try{
            int val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return true;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@UserDaoImpl::@checkIfUserIdInSystem::::USER NOT IN SYSTEM !");
            return false;
        }catch (Exception e){
            System.out.println("@UserDaoImpl::@checkIfUserIdInSystem::ERROR !");
            e.printStackTrace();
            return  false;
        }
    }

    @Override
    public boolean checkIfUserIsActive(long userId) {
        String sql = "SELECT " +
                "u.status " +
                "FROM user u " +
                "WHERE u.user_id = ?";
        try{
            String val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    String.class
            );
            if(val.equals("active")){
                return true;
            }else{
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
    public boolean checkIfUserIsActive2(String userId) {
        String sql = "SELECT " +
                "u.status " +
                "FROM user u " +
                "WHERE u.user_id = ?";
        try{
            String val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    String.class
            );
            if(val.equals("active")){
                return true;
            }else{
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
    public List<User> getTeachersList() {
        String sql = "    SELECT  " +
                "    usr.user_id, " +
                "    usr.user_name " +
                "    FROM user usr " +
                "    WHERE usr.role_id = 2  " +
                "    OR usr.role_id = 1 " +
                "    ORDER BY usr.user_name;";

        try{
            List<User> teachers = getJdbcTemplate().query(
                    sql,
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
            return teachers;

        }
        catch(EmptyResultDataAccessException e){
            return null;

        }catch (Exception e ){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String getStudentDegreeType(long studentId) {
        String sql ="SELECT  " +
                "up.degree_type " +
                "FROM user_profile up " +
                "WHERE up.user_id =  ?";

        try {
            String val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{studentId},
                    String.class
            );
            return val;

        } catch (EmptyResultDataAccessException e) {
            System.out.println("@ExamEnrolmentDaoImpl::getCountOfEnrolledExamsByUserId::EMPTY !");
            // e.printStackTrace();
            return "";
        } catch (Exception e) {
            System.out.println("@ExamEnrolmentDaoImpl::getCountOfEnrolledExamsByUserId::ERROR !");
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public int getStudentMajorId(long studentId) {
        String sql = "SELECT  " +
                "sm.major_id " +
                "FROM student_major sm " +
                "WHERE sm.student_id = ? ";

        try {
            int val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{studentId},
                    Integer.class
            );
            return val;

        } catch (EmptyResultDataAccessException e) {
            System.out.println("::EMPTY !");
            // e.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("::ERROR !");
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public String makeStudentDegreeTypeChangeRequest(StudentDegreeTypeChangeRequest studentDegreeTypeChangeRequest) {
        String sql = "INSERT INTO child_courses (studentId,current_degree_type,new_requested_degree_type) VALUES(?,?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentDegreeTypeChangeRequest.getStudentId(),studentDegreeTypeChangeRequest.getCurrentDegreeType(),studentDegreeTypeChangeRequest.getNewRequestedDegreeType()}
            );
            if(returnValue == 1){
                System.out.println("DEGREE CHANGE REQUEST MADE SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("DEGREE CHANGE REQUESTE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INTERNAL SYSTEM QUERY ERROR!!");
            return "400";
        }
    }

    @Override
    public List<StudentDegreeTypeChangeRequest> getAllStudentDegreeTypeChangeRequest() {
        String sql = "SELECT " +
                "rq.id, " +
                "rq.studentId, " +
                "u.user_name, " +
                "rq.current_degree_type, " +
                "rq.new_requested_degree_type, " +
                "rq.request_status, " +
                "DATE_FORMAT(rq.submission_date, '%D %M %Y')submission_date " +
                "FROM student_degree_type_change_request rq " +
                "LEFT JOIN USER u ON u.user_id = rq.studentId  " +
                "WHERE rq.request_status = 'pending' ";
        try{
            List<StudentDegreeTypeChangeRequest> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<StudentDegreeTypeChangeRequest>() {
                        @Override
                        public StudentDegreeTypeChangeRequest mapRow(ResultSet resultSet, int i) throws SQLException {
                            StudentDegreeTypeChangeRequest request = new StudentDegreeTypeChangeRequest();

                            request.setRequestId(resultSet.getInt("id"));
                            request.setStudentId(resultSet.getLong("studentId"));
                            request.setUserName(resultSet.getString("user_name"));
                            request.setCurrentDegreeType(resultSet.getString("current_degree_type"));
                            request.setNewRequestedDegreeType(resultSet.getString("new_requested_degree_type"));
                            request.setRequestStatus(resultSet.getString("request_status"));
                            request.setSubmissionDate(resultSet.getString("submission_date"));

                            return request;
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
    public List<StudentDegreeTypeChangeRequest> getAllStudentDegreeTypeChangeRequestForStudent(long studentId) {
        String sql = "SELECT " +
                "rq.id, " +
                "rq.studentId, " +
                "u.user_name, " +
                "rq.current_degree_type, " +
                "rq.new_requested_degree_type, " +
                "rq.request_status, " +
                "DATE_FORMAT(rq.submission_date, '%D %M %Y')submission_date " +
                "FROM student_degree_type_change_request rq " +
                "LEFT JOIN USER u ON u.user_id = rq.studentId" +
                "WHERE rq.request_status = 'pending' " +
                "WHERE rq.studentId = ?   ";
        try{
            List<StudentDegreeTypeChangeRequest> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{studentId},
                    new RowMapper<StudentDegreeTypeChangeRequest>() {
                        @Override
                        public StudentDegreeTypeChangeRequest mapRow(ResultSet resultSet, int i) throws SQLException {
                            StudentDegreeTypeChangeRequest request = new StudentDegreeTypeChangeRequest();

                            request.setRequestId(resultSet.getInt("id"));
                            request.setStudentId(resultSet.getLong("studentId"));
                            request.setUserName(resultSet.getString("user_name"));
                            request.setCurrentDegreeType(resultSet.getString("current_degree_type"));
                            request.setNewRequestedDegreeType(resultSet.getString("new_requested_degree_type"));
                            request.setRequestStatus(resultSet.getString("request_status"));
                            request.setSubmissionDate(resultSet.getString("submission_date"));

                            return request;
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
    public String acceptStudentDegreeChangeRequest(int requestId) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp updateTimestamp = new java.sql.Timestamp(date.getTime());

        String sql = "UPDATE student_degree_type_change_request " +
                "SET  " +
                "request_status = ? , " +
                "date_request_processed =  ? " +
                "WHERE id = ?;";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{"accepted",updateTimestamp,requestId}
            );
            if(returnValue == 1){
                System.out.println("DEGREE CHANGE REQUEST MADE SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("DEGREE CHANGE REQUESTE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INTERNAL SYSTEM QUERY ERROR!!");
            return "400";
        }
    }

    @Override
    public String changeStudentDegreeType(long studentId, String degreeType) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp updateTimestamp = new java.sql.Timestamp(date.getTime());

        String sql = "UPDATE user_profile " +
                "SET  " +
                "degree_type = ? , " +
                "updated_at =  ? " +
                "WHERE user_id = ?;";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{degreeType,updateTimestamp,studentId}
            );
            if(returnValue == 1){
                System.out.println("DEGREE CHANGE  MADE SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("DEGREE CHANGE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INTERNAL SYSTEM QUERY ERROR!!");
            return "400";
        }
    }

    @Override
    public String declineStudentDegreeChangeRequest(int requestId) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp updateTimestamp = new java.sql.Timestamp(date.getTime());

        String sql = "UPDATE student_degree_type_change_request " +
                "SET  " +
                "request_status =  , " +
                "date_request_processed =  ? " +
                "WHERE id = ?;";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{"declined",updateTimestamp,requestId}
            );
            if(returnValue == 1){
                System.out.println("DEGREE CHANGE REQUEST MADE SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("DEGREE CHANGE REQUESTE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INTERNAL SYSTEM QUERY ERROR!!");
            return "400";
        }
    }

    @Override
    public String cancelDegreeTypeChangeRequest(int requestId) {
        String sql = "DELETE FROM student_degree_type_change_request WHERE id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{requestId}
            );
            System.out.println("DEGREE TYPE CHANGE CANCEL SUCCESSFULLY ");
            return "200";

        }catch (Exception e){
            e.printStackTrace();
            return "400";
        }
    }

    @Override
    public String getUserProfilePic(long userId) {
        String sql ="SELECT " +
                "up.profile_image_url " +
                "FROM user_profile up " +
                "WHERE up.user_id =  ?";

        try {
            String val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    String.class
            );
            return val;

        } catch (EmptyResultDataAccessException e) {
            System.out.println("@::EMPTY !");
            // e.printStackTrace();
            return "";
        } catch (Exception e) {
            System.out.println("@::ERROR !");
            e.printStackTrace();
            return "";
        }
    }


    @Override
    public String deleteStudentFromCourseEnrolments(long studentId) {

        String sql = "DELETE FROM course_enrolment WHERE student_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentId}
            );
            System.out.println("@CourseEnrolments::STUDENT DELETED::1");
            return "STUDENT DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteStudentFromCourseStudentRequestEnrolment(long studentId) {
        String sql = "DELETE FROM course_student_request_enrolment WHERE student_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentId}
            );
            System.out.println("@CourseRequestEnrolment::STUDENT DELETED::2");
            return "STUDENT DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteStudentFromExamEnrolment(long studentId) {
        String sql = "DELETE FROM exam_enrolment WHERE student_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentId}
            );
            System.out.println("@ExamEnrolment::STUDENT DELETED::3");
            return "STUDENT DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteStudentFromExamStudentRequestEnrolment(long studentId) {
        String sql = "DELETE FROM exam_student_request_enrolment WHERE student_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentId}
            );
            System.out.println("@ExamStudentRequestEnrolment::STUDENT DELETED::4");
            return "STUDENT DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteStudentFromGrade(long studentId) {
        String sql = "DELETE FROM grade WHERE student_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentId}
            );
            System.out.println("@Grade::STUDENT DELETED::5");
            return "STUDENT DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteStudentFromStudentClass(long studentId) {
        String sql = "DELETE FROM student_class WHERE student_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentId}
            );
            System.out.println("@StudentClass::STUDENT DELETED::6");
            return "STUDENT DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteStudentFromUserRole(long studentId) {
        String sql = "DELETE FROM user_role WHERE user_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentId}
            );
            System.out.println("@UserRole::STUDENT DELETED::7");
            return "STUDENT DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteUserFromUsers(long userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{userId}
            );
            System.out.println("@User::STUDENT DELETED::8");
            return "STUDENT DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteUserFromUserProfile(long userId) {
        String sql = "DELETE FROM user_profile WHERE user_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{userId}
            );
            System.out.println("@UserProfile::STUDENT DELETED::9");
            return "STUDENT DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteUserFromStudentMajor(long studentId) {
        String sql = "DELETE FROM student_major WHERE student_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentId}
            );
            System.out.println("@StudentMajor::STUDENT DELETED::10");
            return "STUDENT DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteStudentFromClearanceExamEnrolment(long studentId) {
        String sql = "DELETE FROM clearance_exam_enrolment WHERE student_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentId}
            );
            System.out.println("@StudentMajor::STUDENT DELETED::11");
            return "STUDENT DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteStudentFromGradeCustom(long studentId) {
        String sql = "DELETE FROM grade_custom WHERE student_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentId}
            );
            System.out.println("@StudentMajor::STUDENT DELETED::12 Finished");
            return "STUDENT DELETED";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }
}
