package build.dao;


import build.model.StudentCourses;

import java.util.List;

/**
 * Created by apple on 15/08/2017.
 */
public interface StudentCoursesDao {

    public String createNewStudentCourses(StudentCourses studentCourses);

    public String updateStudentCourses(StudentCourses studentCourses);

    public String deleteStudentCourses(StudentCourses studentCourses);

    public List<StudentCourses> displayAllStudentCourses();
}
