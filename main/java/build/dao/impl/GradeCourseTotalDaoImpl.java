package build.dao.impl;

import build.model.GradeCourseTotal;
import build.dao.GradeCourseTotalDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 29/08/2017.
 */
public class GradeCourseTotalDaoImpl extends JdbcDaoSupport implements GradeCourseTotalDao{
    @Override
    public String addGradeCourseTotal(GradeCourseTotal gradeCourseTotal) {

        String sql = "INSERT INTO grade_course_total ( course_id,student_id,grade_total) VALUES( ?,?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{gradeCourseTotal.getCourseId() ,gradeCourseTotal.getStudentId(),gradeCourseTotal.getGradeTotal() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }
}
