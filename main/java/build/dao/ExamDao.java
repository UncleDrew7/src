package build.dao;
import build.model.*;

import java.util.List;

/**
 * Created by apple on 29/08/2017.
 * winni
 */
public interface ExamDao {

    public String addNewExam(Exam exam);

    public String updateCurrentExam(Exam exam);

    public String deleteExistingExam(Exam exam);

    //CLEARANCE EXAM
    public String createClearanceExam(ClearanceExam clearanceExam);

    public String makeClearanceExamEnrolment(ClearanceExamEnrolment clearanceExamEnrolment);

    public String addStudentClearanceExamScores(GradeCustom gradeCustom);

    public boolean checkIfParentExamAlreadyHasAClearanceExam(int parentExamId);

    public boolean checkIfStudentClearanceExamHasGrade(long studentId, int clearanceExam);

    public ClearanceExam getClearanceExamForMostLatestStudentExam(long studentId, long parentCourseId);

    public ClearanceExam getClearanceExamByChildExamId(int childExamId);

    public List<GradeCustom> getIfAnyClearanceExamResultsList(long studentId, long parentCourseId);

    public List<ClearanceExam> getIfAnyStudentClearanceExamList(long studentId, long parentCourseId);

    public List<ClearanceExam> getIfAnyChildCourseClearnceExamList(int childCourseId);

    public List<ClearanceExam> getAllClearanceExams();

    public List<ClearanceExam> getAllClearanceExamsByMajorId(int majorId);

    public List<ClearanceExam> getAllClearanceExamsBySemesterId(int semesterId);

    public List<ClearanceExam> getAllClearanceExamBySemesterIdAndMajorId(int semesterId, int majorId);

    public List<ClearanceExam> getLastAddedClearanceExams();

    public List<ClearanceExam> getLastEditedClearanceExams();

    public String getPassGradeIfAnyForParentCourseClearanceExam(long studentId, long parentCourseId);

    public List<GradeCustom> getStudentClearanceExamResults(long studentId, long parentCourseId);

    public int getStudentClearanceExamTimes(long studentId, long parentCourseId);

    public long getClearanceExamParentCourse(int clearanceExamId);

    public int getClearanceExamChildCourse(int clearanceExamId);

    public int getClearanceExamParentExam(int clearanceExamId);

    public int isStudentInClearanceExam(long studentId, int clearanceExamId);



    public List<Exam> adminGetAllExamDataList();

    public List<Exam> getLastAddedExamList();

    public int adminGetTotalSystemCreatedExamsCount();

    public int adminGetAllCurrentlyActiveExamsCount();

    public int adminGetAllExamEnrollmentRequestCounts();
    public List<Exam> adminGetCourseExamDataListBySemester(int semesterId);
    public List<Exam> adminGetCourseExamDataListByMajorId(int majorId);
    public List<Exam> adminGetCourseExamDataListBySemesterAndMajorId(int semesterId,int majorId);
    public int getActiveTeacherCourseExamCount(long teacherId);
    public int getTotalTeacherCourseExamCount(long currentUserId);
    public int adminGetAllExamEnrollmentRequestCountsByTeacherId(long teacherId);

    public List<Exam> teacherGetCourseExamDataList(long teacherId);
    public List<Exam> teacherGetCourseExamDataListBySemester(int semesterId,long currentUserId);
    public List<Exam> teacherGetCourseExamDataListByMajorId(int majorId,long currentUserId);
    public List<Exam> teacherGetCourseExamDataListBySemesterAndMajorId(int semesterId, int majorId, long currentUserId);

    public Exam getExamDataForFormDisplayByExamId(int parentExamId);
    public Exam getSingleExamFullDetailsByExamId(int examId);
    public boolean checkPermissionForTeacherAgainstExamId(long teacherId, int examId);
    public List<Exam> getLastEditedExamList();
    public String editExam(Exam newExam);


    public String enrollStudentInClexam(int clexamId, long userId);
    public boolean setClexamSubmitted(int clexamId );
    public int isClexamSubmitted(int clexamId );
    public int getParentCourseId(long examId);
    public int getChildCourseId(int pcId, long studentId);
    public double getExamWeight(int examId);

    public List<Exam> getStudentAvailableExamList(long studentId);
    public List<Exam> getStudentAvailableExamList(long studentId, String searchText );


}
