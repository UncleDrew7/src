package build.dao;


import build.model.StudentCourseEnrolment;

import java.util.List;

/**
 * Created by apple on 15/08/2017.
 */
public interface StudentCourseEnrolmentDao {

    public String createNewStudentCourseEnrolment(StudentCourseEnrolment studentCourseEnrolment);

    public String updateStudentCourseEnrolment(StudentCourseEnrolment studentCourseEnrolment);

    public String deleteStudentCourseEnrolment(StudentCourseEnrolment studentCourseEnrolment);

    public List<StudentCourseEnrolment> displayAllStudentCourseEnrolment();
}
