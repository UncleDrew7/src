package build.dao.impl;

import build.model.CourseSemester;
import build.dao.CourseSemesterDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;


/**
 * Created by apple on 29/08/2017.
 */
public class CourseSemesterDaoImpl extends JdbcDaoSupport implements CourseSemesterDao{
    @Override
    public String addCourseSemester(CourseSemester courseSemester) {
        String sql = "INSERT INTO course_semester ( course_id,semester_id ) VALUES( ?,?)";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{courseSemester.getCourseId() ,courseSemester.getSemesterId() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }
}
