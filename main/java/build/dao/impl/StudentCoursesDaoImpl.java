package build.dao.impl;

import build.model.StudentCourses;
import build.dao.StudentCoursesDao;
import build.row_mapper.StudentCoursesRowMapper;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 15/08/2017.
 */
public class StudentCoursesDaoImpl extends JdbcDaoSupport implements StudentCoursesDao{
    @Override
    public String createNewStudentCourses(StudentCourses studentCourses) {
        String sql = "INSERT INTO student_courses (student_id,course_id,category_id,is_current_course,is_completed_course,is_unenrolled_course)VALUES(?,?,?,?,?,?) ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{
                       studentCourses.getStudentId(),
                        studentCourses.getCourseId(),
                        studentCourses.getCategoryId(),
                        studentCourses.getIsCurrentCourse(),
                        studentCourses.getIsCompletedCourse(),
                        studentCourses.getIsUnEnrolledCourse(),
                }
        );
        if(1 == returnValue)
            return "# New StudentCourses>>> "+studentCourses.getStudentId()+"---"+studentCourses.getCourseId()+" CREATED SUCCESSFULLY ";
        else
            return "# CREATION FAILURE";
    }

    @Override
    public String updateStudentCourses(StudentCourses studentCourses) {
        return null;
    }

    @Override
    public String deleteStudentCourses(StudentCourses studentCourses) {
        return null;
    }

    @Override
    public List<StudentCourses> displayAllStudentCourses() {
        return null;
    }
}
