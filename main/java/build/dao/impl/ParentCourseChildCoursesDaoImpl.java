package build.dao.impl;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import build.model.ParentCourseChildCourses;
import build.dao.ParentCourseChildCoursesDao;
/**
 * Created by apple on 01/12/2017.
 */
public class ParentCourseChildCoursesDaoImpl extends JdbcDaoSupport implements ParentCourseChildCoursesDao {
    @Override
    public String superDeleteAllParentCourseChildCourseRelations() {
        String sql = "DELETE FROM parent_course_child_courses";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql
            );
            if(returnValue == 1){
                System.out.println("SUPER DELETE ALL :: CHILD COURSES ALL REMOVED FROM PARENT COURSE SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("CHILD COURSE DELETE FROM PARENT COURSE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String addChildCourseToParentCourse(long parentCourseId, int childCourseId) {
        String sql = "INSERT INTO parent_course_child_courses (parent_course_id,child_course_id) VALUES(?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{parentCourseId,childCourseId}
            );
            if(returnValue == 1){
                System.out.println("CHILD COURSE ADDED TO PARENT COURSE SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("CHILD COURSE ADDED TO PARENT COURSE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String updateParentCourseChildCourses(long newParentCourseId, int childCourseId) {
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
    public String deleteChildCourseFromParentCourse(long parentCourseId, int childCourse) {

        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

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
    public String deleteParentCourseChildCoursesRecord(int recordId) {
        String sql = "DELETE FROM parent_course_child_courses " +
                "WHERE  " +
                "id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{recordId}
            );
            if(returnValue == 1){
                System.out.println("CHILD COURSE REMOVED FROM PARENT COURSE SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("CHILD COURSE DELETE FROM PARENT COURSE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }
}
