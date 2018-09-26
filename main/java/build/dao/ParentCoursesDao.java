package build.dao;

import build.model.ParentCourses;
import java.util.List;
/**
 * Created by apple on 01/12/2017.
 */
public interface ParentCoursesDao {

    public String createNewParentCourse(ParentCourses parentCourse);

    public String updateParentCourse(ParentCourses parentCourse);

    public String deleteParentCourse(int parentCourseId);

    public String deleteParentCourseFromParentCourseTable(long parentCourseId);

    public String deleteParentCourseFromParentCourseMajor(long parentCourseId);

    public int getMajorIdForParentCourse(long parentCourseId);

    public List<ParentCourses> getAllParentCourses();

    public List<ParentCourses> getAllParentCoursesByMajorId(int majorId);

    public  ParentCourses getSingleParentCourse(long parentCourseId);

    public List<ParentCourses> getLastAddedParentCourses();

    public List<ParentCourses> getLastEditedParentCourses();

    public List<ParentCourses> getListOfAllParentCourseChildCourses(long parentCourse);

    //select lists
    public List<ParentCourses> getAllParentCourseNamesSelectList();
    public List<ParentCourses> getExcelParentCourseListForMajor(int majorId);
    public List<ParentCourses> getParentCoursesForMajor(int majorId);
    public List<ParentCourses> getParentCourseNameForCourseId( int courseId);

    // checks
    public boolean checkIfParentCourseIdInSystem(long parentCourseId);

    public boolean checkIfParentCourseHasChildCourses(long parentCourseId);







}
