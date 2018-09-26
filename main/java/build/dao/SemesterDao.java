package build.dao;
import build.model.Semester;

import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public interface SemesterDao {

    public String addSemester(Semester semester);

//    ADMIN MANAGE COURSES
    public String updateSemester(Semester semester);

    //ADMIN MANAGE COURSES
    public Semester getSingleSemesterBySemesterId(int semesterId);

    public int getNumberOfCoursesForSemester(int semesterId);

    //ADMIN MANAGE-SEMESTERS TABLE DATA
    public List<Semester> getAllSemesterTableDisplayData();

    //ADMIN MANAGE SEMESTER STATS
    public int getTotalSemesterCount();

    //ADMIN ADD SEMESTER
    public List<Semester> getLastAddedSemestersList();

    //ADMIN EDIT SEMESTER
    public List<Semester> getLastEditedSemesterList();

    //ADMIN FOR FORMS SYSTEM SEMESTER LISTS
    public List<Semester> getSystemSemesterList();


}
