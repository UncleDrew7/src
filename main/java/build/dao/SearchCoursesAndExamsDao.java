package build.dao;

import build.model.SearchCoursesAndExams;
import java.util.List;

/**
 * Created by apple on 14/09/2017.
 */
public interface SearchCoursesAndExamsDao {

    public List<SearchCoursesAndExams> searchExamsAndCourses(String searchParam);

    public List<SearchCoursesAndExams> searchFilteredByCourses(String searchParam);

    public List<SearchCoursesAndExams> searchFilteredByExams(String searchParam);

    public List<SearchCoursesAndExams> searchFilteredBySemester(String searchParam);

    //new
    public List<SearchCoursesAndExams> searchChildCourses(int majorId , String searchParam);
    public List<SearchCoursesAndExams> searchChildCoursesFilteredBySemesterId(int majorId, String searchParam , int semesterId);

    public List<SearchCoursesAndExams> searchChildCourseExams(int majorId , String searchParam , long studentId);
    public List<SearchCoursesAndExams> searchChildCourseExamsFilteredBySemesterId(int majorId,String searchParam , int semesterId, long studentId);
}
