package build.dao.impl;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import build.model.ClassMajor;
import build.dao.ClassMajorDao;

/**
 * Created by apple on 01/12/2017.
 */
public class ClassMajorDaoImpl  extends JdbcDaoSupport implements ClassMajorDao{
    @Override
    public String addClassToMajor(int majorId, int classId) {
        String sql = "INSERT INTO class_major (major_id,class_id) VALUES(?,?)";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{majorId,classId}
            );
            if(returnValue == 1){
                System.out.println("CLASS ADDED TO MAJOR SUCCESSFULLY");
                return "200";
            }
            else{
                System.out.println("CLASS ADD TO MAJOR FAILED");
                return "400";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "INTERNAL SYSTEM QUERY ERROR!!";
        }
    }

    @Override
    public String updateClassMajor(int newMajorId, int classId) {

        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

        String sql = "UPDATE class_major SET  " +
                "major_id = ? " +
                "WHERE class_id =  ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{newMajorId,classId }
            );
            System.out.println(" ");
            return "CLASS MAJOR UPDATED SUCCESSFULLY ";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR!!";
        }
    }

    @Override
    public String deleteClassFromMajor(int majorId, int classId) {
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
}
