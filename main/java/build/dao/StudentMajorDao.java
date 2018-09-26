package build.dao;

import build.model.StudentMajor;

import java.util.List;
/**
 * Created by apple on 01/12/2017.
 */
public interface StudentMajorDao {

    public String addStudentToMajor(int majorId , long studentId);

    public String updateStudentsMajor(int newMajorId, long studentId);

    public String deleteStudentFromMajor(int majorId, long studentId);

}
