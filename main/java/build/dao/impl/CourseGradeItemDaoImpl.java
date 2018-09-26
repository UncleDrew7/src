package build.dao.impl;

import build.model.CourseGradeItem;
import build.dao.CourseGradeItemDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;


/**
 * Created by apple on 29/08/2017.
 */
public class CourseGradeItemDaoImpl extends JdbcDaoSupport implements CourseGradeItemDao {
    @Override
    public String addCourseGradeItem(CourseGradeItem courseGradeItem) {
        String sql = "INSERT INTO course_grade_item( grade_item_id,coures_id ) VALUES( ?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{courseGradeItem.getGradeItemId() ,courseGradeItem.getCourseId() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }
}
