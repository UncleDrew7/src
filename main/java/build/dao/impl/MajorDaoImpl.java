package build.dao.impl;
import build.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import build.model.Major;
import build.dao.MajorDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 01/12/2017.
 */
public class MajorDaoImpl extends JdbcDaoSupport implements MajorDao{
    @Override
    public String createNewMajor(Major major) {
        String sql = " INSERT INTO major (major_short_code,major_name,created_by) VALUES(?,?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{major.getMajorShortName(),major.getMajorName() ,major.getCreatedBy()}
            );
            if(returnValue == 1){
                System.out.println("MAJOR CREATED SUCCESSFULLY");
                return "200";
            }
            else {
                System.out.println("MAJOR CREATED SUCCESSFULLY");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("ERROR CREATING REQUEST");
            return "400";
        }
    }

    @Override
    public String updateMajor(Major major) {

        java.util.Date date = new java.util.Date();
        java.sql.Timestamp updateTimestamp = new java.sql.Timestamp(date.getTime());

        String sql = "UPDATE major " +
                "SET  " +
                "major_short_code =  ?, " +
                "major_name = ?," +
                "updated_at = ? " +
                "WHERE major_id = ?;";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{major.getMajorShortName(),major.getMajorName(),updateTimestamp,major.getMajorId()}
            );
            if (returnValue ==  1){
                System.out.println("MAJOR UPDATED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("MAJOR UPDATED FAILED");
                return "400";
            }


        }catch (Exception e){
            e.printStackTrace();
            System.out.println("SYSTEM ERROR! UPDATED FAILED");
            return "400";
        }
    }

    @Override
    public String deleteMajor(int majorId) {
        String sql = " ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ }
            );
            System.out.println(" ");
            return "  ";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteMajorFromMajorTable(int majorId) {
        String sql = "DELETE FROM  major WHERE major_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{majorId}
            );
           if(returnValue == 1){
               System.out.println("1::@MAJOR DELETED FROM MAJOR TABLE 1");
               return "200";
           }else{
               System.out.println("1::@MORE THAN ONE MAJOR DELETED FROM MAJOR TABLE WITH THE SAME MAJOR ID ");
               return "200";
           }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("MAJOR DELETED FROM MAJOR TABLE FAILED");
            return "400";
        }
    }

    @Override
    public List<Major> getAllMajors() {

        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "m.created_at, " +
                "m.updated_at " +
                "FROM major m " +
                "ORDER BY m.created_at DESC ";
        try{
            List<Major> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Major>() {
                        @Override
                        public Major mapRow(ResultSet resultSet, int i) throws SQLException {
                            Major major = new Major();
                            major.setMajorId(resultSet.getInt("major_id"));
                            major.setMajorShortName(resultSet.getString("major_short_code"));
                            major.setMajorName(resultSet.getString("major_name"));
                            major.setCreatedAt(resultSet.getString("created_at"));
                            major.setUpdatedAt(resultSet.getString("updated_at"));
                            return major;
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
    public Major getSingleMajor(int majorId) {

        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "m.created_at, " +
                "m.updated_at " +
                "FROM major m  " +
                "WHERE M.major_id = ? " +
                "ORDER BY m.created_at DESC ";
        try{
            Major object = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<Major>() {
                        @Override
                        public Major mapRow(ResultSet resultSet, int i) throws SQLException {
                            Major major = new Major();
                            major.setMajorId(resultSet.getInt("major_id"));
                            major.setMajorShortName(resultSet.getString("major_short_code"));
                            major.setMajorName(resultSet.getString("major_name"));
                            major.setCreatedAt(resultSet.getString("created_at"));
                            major.setUpdatedAt(resultSet.getString("updated_at"));
                            return major;
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
    public List<Major> getLastAddedMajors() {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "m.created_at, " +
                "m.updated_at " +
                "FROM major m  " +
                "ORDER BY m.created_at DESC " +
                "LIMIT 5;";
        try{
            List<Major> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Major>() {
                        @Override
                        public Major mapRow(ResultSet resultSet, int i) throws SQLException {
                            Major major = new Major();
                            major.setMajorId(resultSet.getInt("major_id"));
                            major.setMajorShortName(resultSet.getString("major_short_code"));
                            major.setMajorName(resultSet.getString("major_name"));
                            major.setCreatedAt(resultSet.getString("created_at"));
                            major.setUpdatedAt(resultSet.getString("updated_at"));
                            return major;
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
    public List<Major> getLastEditedMajors() {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "m.created_at, " +
                "m.updated_at " +
                "FROM major m  " +
                "ORDER BY m.updated_at DESC " +
                "LIMIT 5";
        try{
            List<Major> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Major>() {
                        @Override
                        public Major mapRow(ResultSet resultSet, int i) throws SQLException {
                            Major major = new Major();
                            major.setMajorId(resultSet.getInt("major_id"));
                            major.setMajorShortName(resultSet.getString("major_short_code"));
                            major.setMajorName(resultSet.getString("major_name"));
                            major.setCreatedAt(resultSet.getString("created_at"));
                            major.setUpdatedAt(resultSet.getString("updated_at"));
                            return major;
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
    public List<Major> getAllMajorNamesSelectList() {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name " +
                "FROM major m  " +
                "ORDER BY m.major_name ";
        try{
            List<Major> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Major>() {
                        @Override
                        public Major mapRow(ResultSet resultSet, int i) throws SQLException {
                            Major major = new Major();
                            major.setMajorId(resultSet.getInt("major_id"));
                            major.setMajorShortName(resultSet.getString("major_short_code"));
                            major.setMajorName(resultSet.getString("major_name"));
                            return major;
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
    public List<Major> getAllStudentsInMajorList(int majorId) {
        String sql = "SELECT  " +
                "sm.student_id " +
                "FROM student_major sm " +
                "WHERE sm.major_id = ?  ";
        try{
            List<Major> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<Major>() {
                        @Override
                        public Major mapRow(ResultSet resultSet, int i) throws SQLException {
                            Major major = new Major();
                           major.setStudentId(resultSet.getLong("student_id"));
                            return major;
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
    public List<User> getAllStudentListByMajorAndIntake(int majorId, int intakeId) {
        String sql = "SELECT  " +
                "u.user_id, " +
                "u.user_name, " +
                "c.intake_period, " +
                "c.major_id, " +
                "concat(m.major_name,c.class_name,'班') cusClassName " +
                "FROM user u, class c, major m " +
                "WHERE u.class_id = c.class_id  " +
                "AND c.major_id = m.major_id " +
                "AND  c.major_id =  ? " +
                "AND  c.intake_period =  ? " ;

        try{
            List<User> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId, intakeId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();
                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setMajorId(resultSet.getInt("major_id"));
                            user.setIntake(resultSet.getInt("intake_period"));
                            user.setCustomClassName(resultSet.getString("cusClassName"));
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
    public List<Major> getAllParentCoursesInMajorList(int major) {
        String sql = "SELECT  " +
                "pcm.parent_course_id " +
                "FROM parent_courses_major pcm " +
                "WHERE pcm.major_id =  ? ";
        try{
            List<Major> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{major},
                    new RowMapper<Major>() {
                        @Override
                        public Major mapRow(ResultSet resultSet, int i) throws SQLException {
                            Major major = new Major();
                            major.setParentCourseId(resultSet.getLong("parent_course_id"));
                            return major;
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
    public int getStudentMajorId(long studentId) {
        String sql = "SELECT  " +
                "sm.major_id " +
                "FROM student_major sm " +
                "WHERE sm.student_id =  ? ";
        try{
            int val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{studentId},
                    Integer.class
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
    public String deleteMajorFromStudentMajor(int majorId) {
        String sql = "DELETE FROM student_major WHERE major_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{majorId}
            );
            System.out.println("2::@DELETE COMPLETED OF TOTAL NUMBER OF ::"+returnValue);
            return "200";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteMajorFromClassMajor(int majorId) {
        String sql = "DELETE FROM class_major WHERE major_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{majorId}
            );
            System.out.println("3::@DELETE COMPLETED TOTAL NUMBER OF ::"+returnValue);
            return "200";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteMajorFromParentCourseMajor(int majorId) {
        String sql = "DELETE FROM parent_courses_major WHERE major_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{majorId}
            );
            System.out.println("4::@DELETE COMPLETED OF TOTAL NUMBER OF ::"+returnValue);
            return "200";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }
}
