package build.dao;
import build.model.Exam;
import build.model.GradeItems;

import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */

public interface GradeItemsDao {

    //
    public String addGradeItems(GradeItems gradeItems);

    //ADMIN ADD NEW GRADE ITEM FOR COURSE
    public String addNewGradeItemForCourse(GradeItems gradeItems);

    public String createNewExam(GradeItems gradeItems);

    public String createChildCourseExam(Exam exam);

    //ADMIN EDIT GRADE ITEMS
    public String updateGradeItem(GradeItems gradeItems);

    //ADMIN DELETE GRADE ITEMS
    public String deleteGradeItemByGradeItemId(int gradeItemId);

    //ADMIN DELETING GRADE ITEM REPRESENTATION IN THE GRADE TABLE
    public String deleteGradeItemStudentGrades(int gradeItemId);

    public String removeStudentFromExam(int examId, long studentId);

    //Student Home display
    public List<GradeItems> getAvailableExamsForStudent(long userId);
    //GET LATEST RELATIVE EXMAS FOR STUDENT ENROLLED COURSES
    public List<GradeItems> getLatestExamsForStudent(long studentId);

    public List<GradeItems> getRecommendedAvailableExamsForStudent(long parentCourseId , long studentId);

    public List<GradeItems> getStudentEnrolledExamsBySemesterId(long studentId, int semesterId);

    //ADMIN COURESE PAGE EXAMS
    public List<GradeItems> getCourseExamsByCourseId(int courseId);

    //student home
    public int getTotalNumberOfAvailableExamsForStudent(long userId);

    //STUDENT COURSE EXAM PAGE
    public List<GradeItems> getCourseExams(long parentCourseId, long studentId);

    //ADMIN FOR GRADE ITEMS PAGE GET CREATED EXAMS FOR SPECIFIC CHILD COURSE
    public List<GradeItems> getChildCourseExams(int childCourseId);

    //STUDENT USER DETAILED GRADE REPORT (redundunt)
    public List<GradeItems> getStudentCourseGradeDetailedReport(long parentId,long userId);

    public List<GradeItems> getStudentCourseExamsReportList(long parentCourseId, long userId);

    public List<GradeItems> getStudentCourseGradeItemReportList(int childCourseId,long userId);

    //STUDENT GRADE ITEMS FULL REPORT
    public double getGradeItemsOverallTotal(int courseId, long userId);

    //STUDENT PROFILE PAGE
    public List<GradeItems> getAllStudentExams(long userId);

    //ADMIN VIEW PROFILE TEACHER
    public List<GradeItems> getAllTeacherExams(long userId);

    //STUDENT PROFILE PAGE
    public int getAllStudentExamsCount(long userId);

    //ADMIN VIEW PROFILE TEACHER
    public int getAllTeacherExamsCount(long userId);

    //ADMIN DASHBOARD
    public int getTotalExamEnrolmentsCount();

    //ADMIN DASHBOARD
    public List<GradeItems> getActiveExamRequestNotifications();

    //ADMIN DASHBOARD  GET REQUEST NOTIFICATIONS FOR SPECIFIC TEACHER
    public List<GradeItems> getActiveExamRequestNotificationsByTeacherId(long teacherId);

    //ADMIN DASHBOARD
    public List<GradeItems> getNotActiveExamRequestNotifications();

    //ADMIN MANAGE -EXAMS  REQUEST STATS
    //ADMIN DASHBOARD STATS
    public int adminGetAllExamEnrollmentRequestCounts();

    //ADMIN GET ENROLLMENT REQUEST COUNT FOR EXAM CREATED BY TEACHER
    public int adminGetAllExamEnrollmentRequestCountsByTeacherId(long teacherId);

    //ADMIN MANAGE - EXAMS  ACTIVE STATS BEFORE DATE OF EXAM
    public  int adminGetAllCurrentlyActiveExamsCount();

    //ADMIN TOTAL EXAMS
    public int adminGetTotalSystemCreatedExamsCount();

    public int getTotalClearanceExamCount();

    public int getTotalActiveClearanceExamCount();

    public int getTotalStudentsEnrolledInClearanceExams();

    public int getTotalClearanceExamCountForTeacher(long teacherId);

    public int getTotalActiveClearanceExamCountForTeacher(long teacherId);

    public int getTotalStudentsEnrolledInClearanceExamsForTeacher(long teacherId);

    //------manage exams  list--------///

    //ADMIN MANAGE - EXAMS FILTER 1 ALL EXAMS
    public List<GradeItems> adminGetExamsTableData();

    public List<GradeItems> adminGetCourseExamDataList();

    public List<GradeItems> adminGetCourseExamDataListByMajorId(int majorId);

    public List<GradeItems> adminGetCourseExamDataListBySemester(int semesterId);

    public List<GradeItems> adminGetCourseExamDataListBySemesterAndMajorId(int semesterId, int majorId);

    public List<GradeItems> teacherGetCourseExamDataList(long teacherId);

    public List<GradeItems> teacherGetCourseExamDataListByMajorId(int majorId, long teacherId);

    public List<GradeItems> teacherGetCourseExamDataListBySemester(int semesterId, long teacherId);

    public List<GradeItems> teacherGetCourseExamDataListBySemesterAndMajorId(int semesterId, int majorId, long teacherId);

    //ADMIN MANAGE - EXAMS FILTER 2 UPCOMING EXAMS
    public List<GradeItems> adminGetExamsTableDataUpcomingExams();

    //ADMIN MANAGE - EXAMS FILTER 3 PAST EXAMS
    public List<GradeItems> adminGetExamsTableDataPastExams();

    //TEACHER MANAGE EXAMS FILTER 1 ALL EXAMS
    public List<GradeItems> adminGetExamsTableDataByTeacherId(long teacherId);

    //TEACHER MANAGE EXAMS FILTER 2 UPCOMING EXAMS
    public List<GradeItems> teacherGetExamsTableDataUpcomingExams(long teacherId);

    //TEACHER MANAGE EXAMS FILTER 3 PAST EXAMS
    public List<GradeItems> teacherGetExamsTableDataPastExams(long teacherId);

    //------manage exams  list--------///

    //ADMIN GET TEACHER ACTIVE EXAM COUNT
    public int getActiveTeacherCourseExamCount(long teacherId);

    //ADMIN GET TEACHER TOTAL EXAM COUNT
    public int getTotalTeacherCourseExamCount(long teacherId);

    //ADMIN MANAGE - EXAMS LIST
    public List<GradeItems> adminGetExamEnrolmentRequestByCourses();

    //ADMIN MANAGE - EXAM LIST
    public List<GradeItems> adminGetExamEnrolmentRequestByGradeItem();

    //TEACHER MANAGE EXAMS GET EXAM REQUEST NOTIFICATION LIST
    public List<GradeItems> teacherGetExamEnrolmentRequestByGradeItem(long teacherId);

    public int getActiveExamsCountForTeacher(long teacherId);

    public int getTotalExamEnrollmentsForTeacher(long teacherId);

    //ADMIN MANAGE -EXAMS FILTER BY COURSE SELECT
    public List<GradeItems> getCoursesWithExamsFilterList();

    //ADMIN MANAGE EXAMS  ADD EXAM FORM GET COURSE FOR SELECT
    public List<GradeItems> getActiveCourseListForFormSelect();
    public GradeItems getActiveCourseListForFormSelect(int parentCourseId);

    //ADMIN COURSE GRADE OVERVIEW
    public List<GradeItems> getGradeItemNamesByCourseId(int courseId);

    //ADMIN EXAM ENROLMENT LIST SINGLE EXAM FULL DETAILS
    public GradeItems getSingleExamFullDetailsByExamId(int examId);

    // ADMIN ADD EXAM GET LAST ADDED EXAMS
    public List<GradeItems> getLastAddedExamList();

    //ADMIN EDIT EXAM GET LAST EDITED EXAMS
    public List<GradeItems> getLastEditedExamList();

    public List<GradeItems> getExamListByExamName(String examName);

    //ADMIN DEACTIVATE GRADE ITEM EXAM
    public String deActivateExam(int examId);

    //ADMIN EDIT EXAM UPDATE
    public String editExam(GradeItems gradeItems);

    //ADMIN GET EXAM DATA TO DISPLAY IN UPDATE FORM*
    public GradeItems getExamDataForFormDisplayByExamId(int examId);
//--
    //ADMIN GET GRADE ITEMS FOR COURSE BY COURSE ID
    public List<GradeItems> getCourseGradeItemsByCourseId(int courseId);

    //ADMIN GET SINGLE GRADE ITEM DETAILS BY GRADE ITEM ID
    public GradeItems getSingleGradeItemDetailByGradeItemId(int gradeItemId);

    public GradeItems getChildCourseExamDataForGradeAddition(int childCourseId, String childCourseExamName);

    //GET EXAM START AND ENROLLMENT DEADLINE DATES
    public GradeItems getExamDates(int examid);

    //ADMIN EDIT GRADE ITEM FOR COURSE
    public String editGradeItem(GradeItems gradeItems);

    //ADMIN DELETE GRADE ITEM FOR COURSE BY GRADE ITEM ID
    public String deleteGradeItem(int gradeItemId);

    //ADMIN GRADE  GRADE ITEMS MAIN PAGE
    public List<GradeItems> getGradeItemsWithWeightsByCourseId(int courseId);

    //ADMIN GRADE ITEMS TOTAL WEIGHT FOR COURSE
    public Double getGradeItemWeightForCourse(int courseId);

    //ADMIN CHECK PERMISSION FOR EXAM ID AGAINST TEACHER ID
    public boolean checkPermissionForTeacherAgainstExamId(long teacherId , int examId);

    public boolean checkIfExamHasGrades(int examId, long studentId);


    //ADMIN DELETE EXAM FROM WHOLE SYSTEM
    public String deleteExamFromExamEnrolment(int examId);

    public String deleteExamFromExamStudentRequestEnrolment(int examId);

    public String deleteExamFromGrade(int examId);

    public String deleteExamFromGradeItems(int examId);












}
