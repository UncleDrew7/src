package build.dao.impl;

import build.model.StudentMajor;
import build.dao.StudentMajorDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;


/**
 * Created by apple on 01/12/2017.
 */
public class StudentMajorDaoImpl extends JdbcDaoSupport implements StudentMajorDao{
    @Override
    public String addStudentToMajor(int majorId, long studentId) {
        String sql = "INSERT INTO student_major (major_id,student_id) VALUES(?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{majorId,studentId}
            );
            if(returnValue == 1){
                System.out.println("STUDENT MAJOR ADDED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("STUDENT MAJOR CREATE FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "INTERNAL SYSTEM QUERY ERROR!!";
        }
    }

    @Override
    public String   updateStudentsMajor(int newMajorId, long studentId) {

        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

        String sql = " UPDATE student_major SET major_id = ? ,updated_at=? WHERE student_id = ?  ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{newMajorId,timestamp,studentId}
            );
            if(returnValue == 1){
                System.out.println("STUDENT MAJOR UPDATED SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("STUDENT MAJOR UPDATED FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INTERNAL SYSTEM QUERY ERROR!!");
            return "500";
        }
    }

    @Override
    public String deleteStudentFromMajor(int majorId, long studentId) {
        String sql = "DELETE FROM student_major WHERE student_id = ? AND major_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{studentId,majorId}
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
            return "ERROR!!";
        }
    }
}
