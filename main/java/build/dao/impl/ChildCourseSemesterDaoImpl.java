package build.dao.impl;

import build.model.ChildCourseSemester;
import build.dao.ChildCourseSemesterDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 14/12/2017.
 */
public class ChildCourseSemesterDaoImpl extends JdbcDaoSupport implements ChildCourseSemesterDao{

    @Override
    public String superDeleteAllInChildCourseSemesterRelations() {
        String sql = "DELETE FROM child_course_semester";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql
            );
            if(returnValue == 1){
                System.out.println("SUPER DELETE ALL :: CHILD COURSES ALL REMOVED FROM SEMESTER SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("CHILD COURSE DELETE FROM SEMESTER FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String addChildCourseToSemester(long parentCourseId , int childCourseId, int semesterId) {
        String sql = "INSERT INTO child_course_semester (parent_course_id,child_course_id,semester_id) VALUES(?,?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ parentCourseId,childCourseId,semesterId}
            );
            if(returnValue == 1){
                System.out.println("CHILD COURSE ADDED TO SEMESTER SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("CHILD COURSE ADDED TO SEMESTER FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String updateChildCourseSemester(int recordId, int newSemesterId) {
        return null;
    }

    @Override
    public String removeChildCourseFromSemester(int itemRecord) {
        String sql = "DELETE FROM child_course_semester " +
                "WHERE  " +
                "id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{itemRecord}
            );
            if(returnValue == 1){
                System.out.println("CHILD COURSE REMOVED FROM SEMESTER SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("CHILD COURSE DELETE FROM SEMESTER FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public boolean checkIfChildCourseIdBelongsToASemester(int childCourseId) {
        String sql = "SELECT  " +
                "id " +
                "FROM child_course_semester ccs " +
                "WHERE ccs.child_course_id = ?";

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
}
