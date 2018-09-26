package build.dao;

import build.model.Courses;
import build.model.Semester;

import java.util.List;

/**
 * Created by apple on 15/08/2017.
 */
public interface CoursesDao {

    public String deleteChildCourseFromChildCourseTable(int childCourseId);

    public String createNewCourse(Courses newCourse);

    //ADMIN EDIT COURSE
    public String updateExistingCourse(Courses existingCourse);

    //ADMIN EDIT COURSE
    public String updateCourseSettings(Courses existingCourse);

    //ADMIN CLOSE COURSE
    public String closeCourse(int courseId);
    public String closeCoursesBySemesterId(int semesterId);

    //ADMIN EDIT COURSE
    public List<Courses> getLastEditedCoursesList();

    //ADMIN ADD COURSE
    public List<Courses> getLastAddedCourseList();

    //ADMIN MANAGE COURSES
    public String deleteExistingCourse(int courseId);

    public List<Courses> displayExistingCourses();

    public  List<Courses> latestCourseForStudentsForParentCourseNotTakenByMajor(long userId,int majorId);

    //STUDENT HOMEPAGE COURSE LIST
    public List<Courses> activeCourseList(long userId , int limit , int majorId);
    public List<Courses> activeCourseListSearch(long userId , String search , int majorId);

    //STUDENT HOE PAGE
    public List<Courses> getStudentMostVisitedLatestCourses(long userId, int limit);


    //STUDENT HOMEPAGE COURSE LIST ABOVE LIMIT
    public List<Courses> activeCourseListAboveLimit(long userId , int limit);

    //STUDENT MY-COURSES MY-COURSE LIST // *no longer in use*
    public List<Courses> studentMyCoursesList(long userId);

    //STUDENT FILTERD COURSE LIST FOR NON ACTIVE USERS COURSE
    public List<Courses> studentCompletedCourseList(long userId);

    //STUDENT FILTERED LIST GET ALL COURSES
    public List<Courses> studentAllEnrolledCourses(long userId);

    //STUDENT FILTER MY COURSE LIST BY SEMESTER
    public List<Courses> studentCoursesFilteredBySemesterId(long userId, int semesterId);

    //STUDENT GET SYSTEM SEMESTERS LIST
    public List<Semester> getSemesterList();
    public List<Semester> getSemesterListMore();

    public List<Semester> getStudentCustomSemesterList(long userId);

    //STUDENT COURSE DETAIL PAGE
    public Courses getCourseAboutDetails(int childCourseId);

    public Courses getCourseAboutDetailsForStudentExamPage(int childCourseId , long studentId);

    //STUDENT GRADE OVERVIEW FILTER CURRENT COURSES
    public List<Courses> getUserCoursesGradeAverage(long userId);


    //STUDENT GRADE OVERVIEW FILTER COMPLETED COURSE
    public List<Courses> getUserCoursesGradeAverageFilterByCompletedCourses(long userId);

    //STUDENT GRADE OVERVIEW FILTER BY ALL COURSE
    public List<Courses> getUserCoursesGradeAverageFilterByAllCourses(long userId);

    //STUDENT GRADE OVERVIEW FILTER BY SEMESTER
    public List<Courses> getUserCoursesGradeAverageFilterByCourseCategory(long userId, int semesterId);

    // STUDENT PROFILE PAGE
    public List<Courses> getStudentCurrentCourses(long userId);

    //ADMIN TEACHER PROFILE VIEW PAGE
    public List<Courses> getTeacherCurrentCourses(long userId);//

    //STUDENT PROFILE PAGE
    public int getStudentCurrentCoursesCount(long userId);

    //ADMIN TEACHER PROFILE VIEW PAGE
    public int getTeacherCurrentCourseCount(long userId);//

    //STUDENT PROFILE PAGE
    public List<Courses> getAllStudentCourses(long userId);

    ///ADMIN TEACHER PROFILE VIEW PAGE
    public List<Courses> getAllTeacherCourses(long userId);//

    //STUDENT PROFILE PAGE
    public int getAllStudentCoursesCount(long userId);

    ///ADMIN TEACHER PROFILE VIEW PAGE
    public int getAllTeacherCoursesCount(long userId);//

    public int getAllTeacherActiveCourseCount(long userId);


    //ADMIN DASHBOARD STATS
    //MANAGE COURSES STATS
    public int getTotalActiveCoursesCount();

    //TEACHER TOTAL EXAMS COUNT
    public int getTotalTeacherExamsCount(long teacherId);

    //ADMIN ALL COURSES COUNT FOR TEACHER
    public int getTotalCourseCountForTeacher(long teacherId);

    //ADMIN ACTIVE COURSES COUNT FOR TEACHER
    public int getActiveCourseCountForTeacher(long teacherId);

    public int getTotalCourseEnrollmentCountForTeacher(long teacherId);

    public int getTotalParentCoursesCount();

    public int getTotalChildCoursesCount();

    public int getTotalSystemMajorCount();

    public int getTotalSystemsChildCoursesEnrollmentsCount();

    //ADMIN DASHBOARD STATS
    //MANAGE COURSES STATS


    public int getTotalDoubleDegreeCoursesCount();

    //ADMIN DASHBOARD STATS
    //MANAGE COURSES STATS
    public int getTotalGeneralDegreeCoursesCount();

    //ADMIN DASHBAORS STATS
    public int getTotalCourseUnEnrolmentCount();

    //ADMIN DASHBOARD NOTIFICATION LIST
    public List<Courses> getActiveCourseRequestNotifications();

    //ADMIN DASHBOARD NOTIFICATION LIST FOR SPECIFIC TEACHERS COURESES BY TEACHERID
    public List<Courses> getActiveCourseRequestNotificationsByTeacherId(long teacherId);

    //ADMIN DASHBOARD NOTIFICATION LIST
    public List<Courses> getNotActiveCourseRequestNotifications();

    //ADMIN MANAGE-COURSE TABLE DATA
    public List<Courses> getAllCoursesTableDisplayData();

    //ADMIN GET ONLY TEACHERS COURSES
    public List<Courses> getAllCoursesTableDisplayDataByTeacherId(long teacherId);

    //ADMIN MANAGE - GRADES TABLE DATA //ALL COURSES FOR ADMIN
    public List<Courses> adminGetCoursesWithEnrolledStudentsCountList();

//-----------MANAGE GRADES -----------///
    //ACTIVE COURSES  FOR ADMIN ALL
    public List<Courses> adminGetAllCoursesWithCourseAverageList();

    public List<Courses> getAllCourseAndGradeScoreList();

    public List<Courses> getAllCourseAndGradeScoreListByMajorId(int majorId);

    public List<Courses> getAllCourseAndGradeScoreListBySemester(int semesterId);

    public List<Courses> getAllCourseAndGradeScoreListBySemesterAndMajorId(int semesterId, int majorId);

    public List<Courses> teacherGetAllCourseAndGradeScoreList(long teacherId);

    public List<Courses> teacherGetAllCourseAndGradeScoreListByMajorId(int majorId, long teacherId);

    public List<Courses> teacherGetAllCourseAndGradeScoreListBySemester(int semesterId , long teacherId);

    public List<Courses> teacherGetAllCourseAndGradeScoreListBySemesterAndMajorId(int semesterId, int majorId , long teacherId);

    public List<Courses> getAllCourseListBySemesterAndMajorId(int semesterId, int majorId);

    //ACTIVE COURSES  FOR ADMIN
    public List<Courses> adminGetActiveCoursesWithCourseAverageList();

    //IN-ACTIVE COURSES  FOR ADMIN
    public List<Courses> adminGetInActiveCoursesWithCourseAverageList();

    //COURSES PER SEMESTER FOR ADMIN
    public List<Courses> adminGetCoursesWithCourseAverageListBySemesterId(int semseterId);

    //ADMIN GET GRADE TABLE DATA FOR TEACHER COURSES
    public List<Courses> adminGetCoursesWithEnrolledStudentsCountListByTeacherId(long teacherId);

    //ACTIVE COURSES  FOR TEACHER ALL
    public List<Courses> teacherGetAllCoursesWithCourseAverageList(long tecaherId);

    //ACTIVE COURSES FOR TEACHER
    public List<Courses> teacherGetActiveCoursesWithCourseAverageList(long tecaherId);

    //IN-ACTIVE COURSES FOR TEACHERS
    public List<Courses> teacherGetInActiveCoursesWithCourseAverageList(long tecaherId);

    //COURSES PER SEMESTER FOR TEACHERS
    public List<Courses> teacherGetCoursesWithCourseAverageListBySemesterId(long tecaherId,int semseterId);

    //-----------MANAGE GRADES END-----------///

    //ADMIN DASHBOARD
    public int getAllCourseEnrollmentRequestsCount();



    //ADMIN ENROLMENT REQUEST COUNT FOR COURSE CREATED BY TEACHER
    public int getAllCourseEnrollmentRequestsCountByTeacherId(long teacherId);

    //ADMIN ADD COURSE CONTENT
    public Courses getCourseNameByCourseId(int courseId);

    //ADMIN COURSE ENROLLMENT
    public int getTotalCourseEnrolments(int childCourseId);

    //ADMIN COURSE ENROLMENT LIST
    public int getCourseEnrolmentRequestCountByCourseId(int courseId);

    //ADMIN SEMESTER COURSE LIST
    public List<Courses> getSemesterCourseListBySemesterId(int semesterId);

    public List<Courses> getSemesterCourseListForSemesterByMajorId(int semesterId, int majorId);

    //ADMIN CHECK PERMISSION OF TEACHER AGAINST COURSE ID
    public boolean checkPermissionForTeacherAgainstCourseId(long teacherId , int courseId);

    //STUDENT CHECK IF HOME PAGE COURSE HAS MORE COURSES
    public boolean checkIfMoreCoursesToLoadForStudent(long studentId,int currentLimit);

    //ADMIN DELETE COURSE FROM WHOLE SYSTEM

    public String deleteCourseFromCourseStudentRequestEnrolment(int courseId);

    public String deleteCourseFromCourseEnrolment(int courseId);

    public String deleteCourseFromEvents(int courseId);

    public String deleteCourseFromExamEnrolment(int courseId);

    public String deleteCourseFromExamStudentRequestEnrolment(int courseId);

    public String deleteCourseFromGrade(int courseId);

    public String deleteCourseFromClearanceExamEnrolment(int courseId);

    public String deleteCourseFromClearanceExam(int courseId);

    public String deleteCourseFromGradeItems(int courseId);

    public String deleteCourseUploads(int courseId);

    public String deleteCourseLessons(int courseId);

    public String deleteCourseFromChildCourseSemester(int courseId);

    public String deleteCourseFromChildCourseTeachers(int courseId);

    public String deleteCourseFromGradeCustom(int courseId);

    public String deleteCourseFromParentCourseChildCourses(int courseId);

    public String deleteCourseFromChildCourses(int courseId);

















}
