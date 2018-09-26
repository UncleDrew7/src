package build.dao.impl;

import build.model.Teachers;
import build.dao.TeachersDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
/**
 * Created by apple on 30/08/2017.
 */
public class TeachersDaoImpl extends JdbcDaoSupport implements TeachersDao {
    @Override
    public String addTeachers(Teachers teachers) {

        String sql = "INSERT INTO teachers ( teacher_id,teacher_name ) VALUES( ?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ teachers.getTeacherId(),teachers.getTeacherName() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }
}
