package build.dao;

import build.model.Major;
import build.model.User;

import java.util.List;
/**
 * Created by apple on 01/12/2017.
 */
public interface MajorDao {

    //--------the controls
    public String createNewMajor(Major major);

    public String updateMajor(Major major);

    public String deleteMajor(int majorId);

    public String deleteMajorFromMajorTable(int majorId);

    //--------the returns
    public List<Major> getAllMajors();

    public Major getSingleMajor(int majorId);

    public List<Major> getLastAddedMajors();

    public List<Major> getLastEditedMajors();

    //select lists
    public List<Major> getAllMajorNamesSelectList();

    public List<Major> getAllStudentsInMajorList(int majorId);

    public List<User> getAllStudentListByMajorAndIntake(int majorId, int intakeId);

    public List<Major> getAllParentCoursesInMajorList(int major);

    public int getStudentMajorId(long studentId);

    //-------the checks

    public String deleteMajorFromStudentMajor(int majorId);

    public String deleteMajorFromClassMajor(int majorId);

    public String deleteMajorFromParentCourseMajor(int majorId);


}
