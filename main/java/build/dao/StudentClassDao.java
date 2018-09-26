package build.dao;


import build.model.StudentClass;

import java.util.List;

/**
 * Created by apple on 15/08/2017.
 */
public interface StudentClassDao {

    public String createStudentClass(StudentClass studentClass);

    public String updateStudentClass(StudentClass studentClass);

    public String deleteStudentClass(StudentClass studentClass);

    public List<StudentClass> displayAllStudentClass();
}
