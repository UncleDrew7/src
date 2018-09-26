package build.dao;

import build.model.ChildCourses;
import java.util.List;
/**
 * Created by apple on 14/12/2017.
 */
public interface ChildCoursesDao {

    public String superDeleteAllChildCourse();

    public String createNewChildCourse(ChildCourses childCourse);

    public String updateChildCourse(ChildCourses childCourse);

    public String deleteChildCourse(int childCourseId);

    public String deleteChildCourseFromChildCourseTable(int childCourseId);

    public String getChildCourseEnrollmentDeadlineDate(int childCourseId);

    public String removeStudentFromChildCourse(int childCourseId, long studentId);

    //returns

    public int getTheLargestChildCourseId();

    public long getChildCourseParentId(int childCourseId);

    public List<ChildCourses> getAllChildCourses();
    public List<ChildCourses> getAllChildCourses2();

    public List<ChildCourses> getAllChildCoursesForSpecificMajorId(int majorId);
    public List<ChildCourses> getAllChildCoursesForSpecificMajorId2(int majorId);

    public List<ChildCourses> getFilteredBySemesterChildCourse(int filterId);
    public List<ChildCourses> getFilteredBySemesterChildCourse2(int filterId);

    public List<ChildCourses> getFilteredBySemesterChildCoursesForSpecificMajorId(int filterId, int majorId);
    public List<ChildCourses> getFilteredBySemesterChildCoursesForSpecificMajorId2(int filterId, int majorId);

    public List<ChildCourses> getAllChildCoursesForTeacher(long teacherId);
    public List<ChildCourses> getAllChildCoursesForTeacher2(long teacherId);

    public List<ChildCourses> getAllChildCoursesForTeacherFilteredBySemester(int filterId , long teacherId);
    public List<ChildCourses> getAllChildCoursesForTeacherFilteredBySemester2(int filterId , long teacherId);

    public List<ChildCourses> getAllChildCoursesForTeacherByMajor(int majorId , long teacherId);
    public List<ChildCourses> getAllChildCoursesForTeacherByMajor2(int majorId , long teacherId);

    public List<ChildCourses> getAllChildCoursesForTeacherFilteredBySemesterAndMajor(int filterId, int majorId, long teacherId);
    public List<ChildCourses> getAllChildCoursesForTeacherFilteredBySemesterAndMajor2(int filterId, int majorId, long teacherId);

    //student api's
    public List<ChildCourses> getAllStudentsCoursesByStudentId(long studentId);
    public List<ChildCourses> getAllStudentsCoursesByStudentIdAndSearch(long studentId, String search);

    public List<ChildCourses> getStudentCoursesBySemesterId(long studentId,int semesterId);
    public List<ChildCourses> getStudentCoursesBySemesterIdAndSearch(long studentId,int semesterId, String search);

    public  ChildCourses getSingleChildCourse(int childCourseId);

    public List<ChildCourses> getLastAddedChildCourses();

    public List<ChildCourses> getLastEditedChildCourses();

    public List<ChildCourses> getChildCourseListByMajorAndSemesterId(int majorId,String semeseterCode);

    public List<ChildCourses> getChildCoursesListForSpecificParentCourse(long parentCourseId);

    //check
    public boolean checkIfChildCourseIsInSystem(int childCourseId , String childCourseName);
    public boolean checkIfChildCourseIsInSystem(int childCourseId);
    public boolean checkIfChildCourseExistsForParentCourseAtSemester(long parentCourseId,int semesterId,int childCourseId);

    public boolean checkIfChildCourseExamIsInSystem(int childCourseId , String childCourseExamName);

    public boolean checkIfChildCourseExamHasGradeItems(int childCourseId);


}
