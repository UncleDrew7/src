package build.dao.impl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import build.model.ParentCourses;
import build.dao.ParentCoursesDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 01/12/2017.
 */
public class ParentCoursesDaoImpl extends JdbcDaoSupport implements ParentCoursesDao{
    @Override
    public String createNewParentCourse(ParentCourses parentCourse) {
        String sql = "INSERT INTO parent_courses (course_type,course_short_name,course_short_name_cn, course_name,description,credits,created_by,major_id) VALUES(?,?,?,?,?,?,?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{parentCourse.getCourseType(),parentCourse.getCourseShortName(),parentCourse.getCourseShortNameCN(),parentCourse.getCourseName(),parentCourse.getDescription(),parentCourse.getCredits(),parentCourse.getCreatedBy(), parentCourse.getMajorId()}
            );
            if(returnValue == 1){
                System.out.println("PARENT CREATED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("PARENT CREATE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String updateParentCourse(ParentCourses parentCourse) {

        java.util.Date date = new java.util.Date();
        java.sql.Timestamp updateTimestamp = new java.sql.Timestamp(date.getTime());

        String sql =  "UPDATE parent_courses " +
                "SET  " +
                "course_type = ? , " +
                "course_short_name =  ?, " +
                "course_short_name_cn =  ?, " +
                "course_name =  ?, " +
                "description =  ?, " +
                "credits = ?, " +
                "updated_at = ? " +
                "WHERE parent_course_id = ?;";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{parentCourse.getCourseType(),parentCourse.getCourseShortName(),parentCourse.getCourseShortNameCN(),parentCourse.getCourseName(),parentCourse.getDescription(),parentCourse.getCredits(),updateTimestamp,parentCourse.getParentCourseId()}
            );
            if(returnValue == 1){
                System.out.println("PARENT COURSE UPDATED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("PARENT COURSE UPDATED FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteParentCourse(int parentCourseId) {
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
    public String deleteParentCourseFromParentCourseTable(long parentCourseId) {
        String sql = "DELETE FROM parent_courses WHERE parent_course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{parentCourseId}
            );
            System.out.println("1::@PARENT COURSE DELETE COMPLETED");
            return "200";

        }catch (Exception e){
            e.printStackTrace();
            return "400";
        }
    }

    @Override
    public String deleteParentCourseFromParentCourseMajor(long parentCourseId) {
        String sql = "DELETE FROM parent_courses_major WHERE parent_course_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{parentCourseId}
            );
            System.out.println("2::@PARENT COURSE DELETE COMPLETED");
            return "200";

        }catch (Exception e){
            e.printStackTrace();
            return "400";
        }
    }


    @Override
    public int getMajorIdForParentCourse(long parentCourseId) {
        String sql ="SELECT  " +
                "pcm.major_id " +
                "FROM parent_courses_major pcm " +
                "WHERE pcm.parent_course_id =  ? ";
        try{
            int val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{parentCourseId},
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
    public List<ParentCourses> getAllParentCourses() {

        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "pc.parent_course_id,  " +
                "pc.course_type,   " +
                "pc.course_short_name,  " +
                "pc.course_short_name_cn,  " +
                "pc.course_name,  " +
                "pc.description,  " +
                "pc.credits,  " +
                "pc.status,  " +
                "pc.created_by,  " +
                "pc.created_at,  " +
                "pc.updated_at, " +
                "(SELECT COUNT(*) " +
                "FROM child_courses pcc " +
                "WHERE pcc.parent_course_id = pc.parent_course_id) totalCourseVersions " +
                "FROM parent_courses pc  " +
               // "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN major m ON m.major_id = pc.major_id " +
                "ORDER BY pc.created_at DESC , pc.course_name ";
        try{
            List<ParentCourses> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<ParentCourses>() {
                        @Override
                        public ParentCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ParentCourses parent = new ParentCourses();
                            parent.setMajorId(resultSet.getInt("major_id"));
                            parent.setMajorName(resultSet.getString("major_name"));
                            parent.setMajorShortName(resultSet.getString("major_short_code"));
                            parent.setChildCoursesCount(resultSet.getInt("totalCourseVersions"));
                            parent.setParentCourseId(resultSet.getLong("parent_course_id"));
                            parent.setCourseType(resultSet.getString("course_type"));
                            parent.setCourseShortName(resultSet.getString("course_short_name"));
                            parent.setCourseShortNameCN(resultSet.getString("course_short_name_cn"));
                            parent.setCourseName(resultSet.getString("course_name"));
                            parent.setDescription(resultSet.getString("description"));
                            parent.setCredits(resultSet.getDouble("credits"));
                            parent.setStatus(resultSet.getString("status"));
                            parent.setCreatedBy(resultSet.getLong("created_by"));
                            parent.setCreatedAt(resultSet.getString("created_at"));
                            parent.setUpdatedAt(resultSet.getString("updated_at"));
                            return parent;
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
    public List<ParentCourses> getAllParentCoursesByMajorId(int majorId) {
        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "pc.parent_course_id,  " +
                "pc.course_type,   " +
                "pc.course_short_name,  " +
                "pc.course_short_name_cn,  " +
                "pc.course_name,  " +
                "pc.description,  " +
                "pc.credits,  " +
                "pc.status,  " +
                "pc.created_by,  " +
                "pc.created_at,  " +
                "pc.updated_at, " +
                "(SELECT COUNT(*) " +
                "FROM child_courses pcc " +
                "WHERE pcc.parent_course_id = pc.parent_course_id) totalCourseVersions " +
                "FROM parent_courses pc  " +
             //   "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN major m ON m.major_id = pc.major_id " +
                "WHERE m.major_id = ?  " +
                "ORDER BY pc.created_at DESC , pc.course_name ";
        try{
            List<ParentCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<ParentCourses>() {
                        @Override
                        public ParentCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ParentCourses parent = new ParentCourses();
                            parent.setMajorId(resultSet.getInt("major_id"));
                            parent.setMajorName(resultSet.getString("major_name"));
                            parent.setMajorShortName(resultSet.getString("major_short_code"));
                            parent.setChildCoursesCount(resultSet.getInt("totalCourseVersions"));
                            parent.setParentCourseId(resultSet.getLong("parent_course_id"));
                            parent.setCourseType(resultSet.getString("course_type"));
                            parent.setCourseShortName(resultSet.getString("course_short_name"));
                            parent.setCourseShortNameCN(resultSet.getString("course_short_name_cn"));
                            parent.setCourseName(resultSet.getString("course_name"));
                            parent.setDescription(resultSet.getString("description"));
                            parent.setCredits(resultSet.getDouble("credits"));
                            parent.setStatus(resultSet.getString("status"));
                            parent.setCreatedBy(resultSet.getLong("created_by"));
                            parent.setCreatedAt(resultSet.getString("created_at"));
                            parent.setUpdatedAt(resultSet.getString("updated_at"));
                            return parent;
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
    public ParentCourses getSingleParentCourse(long parentCourseId) {
        String sql = " SELECT   " +
                " m.major_id, " +
                " m.major_name, " +
                " m.major_short_code, " +
                " pc.parent_course_id,  " +
                " pc.course_type,  " +
                " pc.course_short_name,  " +
                "pc.course_short_name_cn, " +
                " pc.course_name,  " +
                " pc.description,  " +
                " pc.credits,  " +
                " pc.status,  " +
                " pc.created_by,  " +
                " DATE_FORMAT(pc.created_at, '%D %M %Y')created_at,  " +
                " pc.updated_at  " +
                " FROM parent_courses pc  " +
            //    " LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id " +
                " LEFT JOIN major m ON m.major_id = pc.major_id " +
                " WHERE pc.parent_course_id =  ? ";
        try{
            ParentCourses object = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{parentCourseId},
                    new RowMapper<ParentCourses>() {
                        @Override
                        public ParentCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ParentCourses parent = new ParentCourses();
                            parent.setMajorId(resultSet.getInt("major_id"));
                            parent.setMajorName(resultSet.getString("major_name"));
                            parent.setParentCourseId(resultSet.getLong("parent_course_id"));
                            parent.setCourseType(resultSet.getString("course_type"));
                            parent.setCourseShortName(resultSet.getString("course_short_name"));
                            parent.setCourseShortNameCN(resultSet.getString("course_short_name_cn"));
                            parent.setCourseName(resultSet.getString("course_name"));
                            parent.setDescription(resultSet.getString("description"));
                            parent.setCredits(resultSet.getInt("credits"));
                            parent.setStatus(resultSet.getString("status"));
                            parent.setCreatedBy(resultSet.getLong("created_by"));
                            parent.setCreatedAt(resultSet.getString("created_at"));
                            parent.setUpdatedAt(resultSet.getString("updated_at"));
                            return parent;
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
    public List<ParentCourses> getLastAddedParentCourses() {
        String sql = "SELECT  " +
                "pc.parent_course_id, " +
                "pc.course_type,  " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "pc.description, " +
                "pc.credits, " +
                "pc.status, " +
                "pc.created_by, " +
                "pc.created_at, " +
                "pc.updated_at " +
                "FROM parent_courses pc " +
                "ORDER BY pc.created_at DESC " +
                "LIMIT 5;";
        try{
            List<ParentCourses> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<ParentCourses>() {
                        @Override
                        public ParentCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ParentCourses parent = new ParentCourses();
                            parent.setParentCourseId(resultSet.getLong("parent_course_id"));
                            parent.setCourseType(resultSet.getString("course_type"));
                            parent.setCourseShortName(resultSet.getString("course_short_name"));
                            parent.setCourseName(resultSet.getString("course_name"));
                            parent.setDescription(resultSet.getString("description"));
                            parent.setCredits(resultSet.getInt("credits"));
                            parent.setStatus(resultSet.getString("status"));
                            parent.setCreatedBy(resultSet.getLong("created_by"));
                            parent.setCreatedAt(resultSet.getString("created_at"));
                            parent.setUpdatedAt(resultSet.getString("updated_at"));
                            return parent;
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
    public List<ParentCourses> getLastEditedParentCourses() {
        String sql = "SELECT  " +
                "pc.parent_course_id, " +
                "pc.course_type,  " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "pc.description, " +
                "pc.credits, " +
                "pc.status, " +
                "pc.created_by, " +
                "pc.created_at, " +
                "pc.updated_at " +
                "FROM parent_courses pc " +
                "ORDER BY pc.updated_at DESC " +
                " LIMIT 5";
        try{
            List<ParentCourses> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<ParentCourses>() {
                        @Override
                        public ParentCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ParentCourses parent = new ParentCourses();
                            parent.setParentCourseId(resultSet.getLong("parent_course_id"));
                            parent.setCourseType(resultSet.getString("course_type"));
                            parent.setCourseShortName(resultSet.getString("course_short_name"));
                            parent.setCourseName(resultSet.getString("course_name"));
                            parent.setDescription(resultSet.getString("description"));
                            parent.setCredits(resultSet.getInt("credits"));
                            parent.setStatus(resultSet.getString("status"));
                            parent.setCreatedBy(resultSet.getLong("created_by"));
                            parent.setCreatedAt(resultSet.getString("created_at"));
                            parent.setUpdatedAt(resultSet.getString("updated_at"));
                            return parent;
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
    public List<ParentCourses> getListOfAllParentCourseChildCourses(long parentCourse) {
        String sql = "SELECT  " +
                "pcc.child_course_id, " +
                "pc.parent_course_id, " +
                "pc.course_name " +
                "FROM parent_course_child_courses pcc " +
                "LEFT JOIN parent_courses pc ON pc.parent_course_id = pcc.parent_course_id  " +
                "WHERE pcc.parent_course_id =  ? ";
        try{
            List<ParentCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{parentCourse},
                    new RowMapper<ParentCourses>() {
                        @Override
                        public ParentCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ParentCourses parent = new ParentCourses();
                            parent.setParentCourseId(resultSet.getLong("parent_course_id"));
                            parent.setCourseName(resultSet.getString("course_name"));
                            parent.setChildCourseId(resultSet.getInt("child_course_id"));
                            return parent;
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
    public List<ParentCourses> getAllParentCourseNamesSelectList() {
        String sql = "SELECT  " +
                "pc.parent_course_id, " +
                "pc.course_type,  " +
                "pc.course_short_name, " +
                "pc.course_name, " +
                "pc.description, " +
                "pc.credits  " +
                "FROM parent_courses pc " +
                "ORDER BY pc.course_name  " ;
        try{
            List<ParentCourses> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<ParentCourses>() {
                        @Override
                        public ParentCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ParentCourses parent = new ParentCourses();
                            parent.setParentCourseId(resultSet.getLong("parent_course_id"));
                            parent.setCourseType(resultSet.getString("course_type"));
                            parent.setCourseShortName(resultSet.getString("course_short_name"));
                            parent.setCourseName(resultSet.getString("course_name"));
                            parent.setDescription(resultSet.getString("description"));
                            parent.setCredits(resultSet.getInt("credits"));
                            return parent;
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
    public List<ParentCourses> getExcelParentCourseListForMajor(int majorId) {
        String sql = "SELECT  " +
                "m.major_name, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.credits " +
                "FROM parent_courses pc " +
                //"LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN major m ON m.major_id = pc.major_id " +
                "WHERE m.major_id = ? " +
                "ORDER BY pc.parent_course_id";
        try{
            List<ParentCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<ParentCourses>() {
                        @Override
                        public ParentCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ParentCourses parent = new ParentCourses();
                            parent.setMajorName(resultSet.getString("major_name"));
                            parent.setParentCourseId(resultSet.getLong("parent_course_id"));
                            parent.setCourseType(resultSet.getString("course_type"));
                            parent.setCourseShortName(resultSet.getString("course_short_name"));
                            parent.setCourseName(resultSet.getString("course_name"));
                            parent.setCredits(resultSet.getDouble("credits"));
                            return parent;
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
    public List<ParentCourses> getParentCourseNameForCourseId(int  courseId) {
        String sql = "SELECT  " +
                "pc.parent_course_id, " +
                "pc.course_name, " +
                "pc.course_short_name " +
                "FROM parent_courses pc " +
                "WHERE pc.parent_course_id = ? " +
                "ORDER BY pc.parent_course_id";
        try{
            List<ParentCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<ParentCourses>() {
                        @Override
                        public ParentCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ParentCourses parent = new ParentCourses();
                            parent.setParentCourseId(resultSet.getLong("parent_course_id"));
                            parent.setCourseName(resultSet.getString("course_name"));
                            parent.setCourseShortName(resultSet.getString("course_short_name"));

                            return parent;
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
    public List<ParentCourses> getParentCoursesForMajor(int majorId) {
        String sql = "SELECT  " +
                "m.major_name, " +
                "pc.parent_course_id, " +
                "pc.course_type, " +
                "pc.course_name, " +
                "pc.course_short_name, " +
                "pc.credits " +
                "FROM parent_courses pc " +
              //  "LEFT JOIN parent_courses_major pcm ON pcm.parent_course_id = pc.parent_course_id " +
                "LEFT JOIN major m ON m.major_id = pc.major_id " +
                "WHERE m.major_id = ? " +
                "ORDER BY pc.parent_course_id";
        try{
            List<ParentCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<ParentCourses>() {
                        @Override
                        public ParentCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ParentCourses parent = new ParentCourses();
                            parent.setMajorName(resultSet.getString("major_name"));
                            parent.setParentCourseId(resultSet.getLong("parent_course_id"));
                            parent.setCourseType(resultSet.getString("course_type"));
                            parent.setCourseShortName(resultSet.getString("course_short_name"));
                            parent.setCourseName(resultSet.getString("course_name"));
                            parent.setCredits(resultSet.getInt("credits"));
                            return parent;
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
    public boolean checkIfParentCourseIdInSystem(long parentCourseId) {
        String sql = "SELECT  " +
                "pc.parent_course_id " +
                "FROM parent_courses pc " +
                "WHERE pc.parent_course_id = ? ";

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{parentCourseId},
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
    public boolean checkIfParentCourseHasChildCourses(long parentCourseId) {
        String sql = "SELECT " +
                "pcc.child_course_id " +
                "FROM parent_course_child_courses pcc " +
                "WHERE pcc.parent_course_id =  ? ";

        try{
            List<ParentCourses> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{parentCourseId},
                    new RowMapper<ParentCourses>() {
                        @Override
                        public ParentCourses mapRow(ResultSet resultSet, int i) throws SQLException {
                            ParentCourses parent = new ParentCourses();
                            parent.setParentCourseId(resultSet.getLong("child_course_id"));
                            return parent;
                        }
                    }

            );
            if(list.isEmpty()){
                return false;
            }else{
                return true;
            }

        } catch (EmptyResultDataAccessException e) {
            return false;
        }catch (Exception e ){
            e.printStackTrace();
            return true;
        }
    }
}


