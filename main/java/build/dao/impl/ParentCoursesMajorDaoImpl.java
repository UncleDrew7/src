package build.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import build.model.ParentCoursesMajor;
import build.dao.ParentCoursesMajorDao;

/**
 * Created by apple on 01/12/2017.
 */
public class ParentCoursesMajorDaoImpl  extends JdbcDaoSupport implements ParentCoursesMajorDao{
    @Override
    public String addParentCourseToMajor(long parentCourseId, int majorId) {
        String sql = "INSERT INTO parent_courses_major (major_id,parent_course_id) VALUES(?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ majorId,parentCourseId}
            );
            if(returnValue == 1){
                System.out.println("PARENT COURSE ADDED TO MAJOR SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("PARENT COURSE ADDED TO MAJOR FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String updateParentCoursesMajor(long parentCourseId, int newMajorId) {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp updateTimestamp = new java.sql.Timestamp(date.getTime());

        String sql = "UPDATE  parent_courses_major SET major_id = ?, updated_at = ? WHERE parent_course_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ newMajorId,updateTimestamp,parentCourseId}
            );
            if(returnValue == 1){
                System.out.println("PARENT COURSE MAJOR UPDATE SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("PARENT COURSE MAJOR UPDATE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteParentCourseFromMajor(long parentCourseId, int majorId) {
        String sql = "DELETE FROM parent_courses_major " +
                "WHERE  " +
                "parent_course_id = ? " +
                "AND major_id =  ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ parentCourseId, majorId}
            );
            if(returnValue == 1){
                System.out.println("PARENT COURSE DELETED FROM MAJOR SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("PARENT COURSE DELETE FROM MAJOR FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public boolean checkIfCourseHasMajor(long parentCourseId, int majorId) {
        String sql = "SELECT pcm.id " +
                "FROM parent_courses_major pcm  " +
                "WHERE pcm.parent_course_id = ? " +
                "AND pcm.major_id = ? ";

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{parentCourseId,majorId},
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
}
