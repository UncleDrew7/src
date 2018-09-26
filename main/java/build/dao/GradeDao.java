package build.dao;
import build.model.Grade;
import build.model.GradeItems;
import build.model.User;

import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public interface GradeDao {

    public String addGrade(Grade grade);

    public String addNumericGrade(Grade grade);

    public String addStringGrade(Grade grade);

    //ADMIN UPDATE USER GRADE FOR GRADE ITEM
    public String updateGrade(Grade grade);

    //ADMIN QUERY GRADE ITEM GRADES
    public List<Grade> getCourseSingleGradeItemGrades(int courseId, int gradeItemId);

    //ADMIN GET COURSE WEIGHT TOTAL
    public double getGradeItemWeightTotalByCourseId(int courseId);

    //ADMIN GET GRADE AVERAGE
    public int getCourseFinalScoreByCourseId(int courseId);

    //ADMIN GET COURSE GRADE  OVERVIEW
    public List<Grade> getCourseOverViewByCourseId(int courseId);

    //ADMIN GET STUDENT GRADE OVERVIEW
    public List<Grade> getStudentCourseGradeOverviewByStudentId(long userId);

    //ADMIN GET STUDENT GRADE ITEM USER REPORT
    public List<Grade> getstudentGradeItemReportByUserId(long userId);

    //ADMIN GET ADD GRADE LIST FOR COURSE ENROLLED STUDENTS
    public List<Grade> getCourseStudentEnrollmentAddGradesList(int courseId , int gradeItemId);

    //ADMIN GET ADD GRADE LIST FOR EXAM ENROLLED STUDENTS
    public List<Grade> getExamStudentEnrollmentAddGradesList(int gradeItemId);

    //ADMIN GET ADD CLEARANCE EXAM GRADE LIST FOR ENROLLED STUDENTS

    //ADMIN CHECK IF STUDENT HAS GRADE FOR PARTICULAR GRADE ITEM
    public boolean checkIfStudentHasGradeForGradeItem(int gradeItemId, long userId);

    //ADMIN GRADE EXCEL INPUT CHECK IS STUDENT IN EXCEL IS ENROLLED
    public boolean checkIfStudentIsEnrolledInExam(int gradeItemId, long userId);

    //ADMIN GRADE EXCEL INPUT CHECK IF STUDENT IS ENROLLED IN COURSE
    public boolean checkIfStudentIsEnrolledInCourse(int courseId, long userId);

    //GRADE OVER VIEW PAGE GET GRADE ITEMS FOR COURSE
    public List<GradeItems> getGradeItemsFromCourseByCourseId(int courseId);

    //GRADE OVERVIEW GET GRADES FOR ALL GRADE ITEMS FOR COURSE
    public List<Grade> getAllGradesForCourseGradeItemsByCourseId(int courseId);

    public int getCourseScoreComputedByHighestExamGradeforStudent(long studentId,long parentCourseId , int childCourseId);

    public int getCourseScoreComputedByLatestWrittenExamGrade(long studentId, long parentCourseId, int childCourseId);

    //FOR CLEARANCE EXAM ELIGIBILITY TEST

    public double getStudentExamWithHighestGrade(long studentId, long parentCourseId);

    public double getStudentsTotalScoreForAllChildCourseGradeItems(long studentId, int childCourseId);

    public List<User> getClearanceExamStudentGradeList(int clearanceExamId);







}
