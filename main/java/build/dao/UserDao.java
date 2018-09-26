package build.dao;

import build.model.StudentClass;
import build.model.StudentDegreeTypeChangeRequest;
import build.model.User;
import build.model.UserProfile;

import java.util.List;
/**
 * Created by apple on 15/08/2017.
 */
public interface UserDao {

    public String createNewUser(User newUser);

    public String updateCurrentUser(User currentUser);

    public String deleteCurrentUser(User currentUser);

    public List<User> displayCurrentUsers();

    //ADMIN VIEW PROFILE
    //STUDENT PROFILE
    public User getUserByUserId(long userId);

    //COURSE GRADE DETAILED REPORT
    public String getUserNameById(long userId);

    // ADMIN DASHBOARD STATS
    //ADMIN MANAGE -USERS USER STATS
    public int getTotalNumberOfSystemUsers();

    // ADMIN DASHBOARD STATS
    //ADMIN MANAGE -USERS STUDENT STATS
    public int getTotalNumberOfSystemStudents();

    public int getTotalSystemAdminsCount();

    // ADMIN DASHBOARD STATS
    public int getTotalNumberOfSystemDoubleDegreeStudents();

    // ADMIN DASHBOARD STATS
    public int getTotalNumberOfSystemGeneralDegreeStudents();

    // ADMIN DASHBOARD STATS
    ////ADMIN MANAGE -USERS TEACHER STATS
    public int getTotalNumberOfSystemTeachers();

    public List<UserProfile> getSystemsLatestStudentCourseEnrollmentNotificationsList(int limit);

    public List<UserProfile> getSystemsLatestCourseEnrollmentNotificationsForTeacher(long teacherId, int limit);

    public List<UserProfile> getSystemsLatestExamEnrollmentNotificationsAdmin(int limit);

    public List<UserProfile> getSystemsLatestExamEnrollmentNotificationsTeacher(long teacher , int limit);

    //ADMIN MANAGE -USERS USER TABLE
    //public List<UserProfile> getSystemUsersWithProfileDetails();
    public List<User> getSystemUsersWithProfileDetails();

    //TEACHER MANAGE-USER TABLE
    public List<User> getSystemUsersWithProfileDetailsForTeacher();

    //filter by teacher / admin
    public List<User> getSystemTeachersAndAdminsUserData();
    //filter by teachers
    public List<UserProfile> getSystemTeachersUserData();
    //filter by students
    public List<User> getSystemStudentsUserData();
    //filter by intake
    public List<User> getSystemStudentUserDataFilteredByIntake(String intake);

    public List<User> getSystemStudentUserDataFilteredByMajor(int majorId);

    public List<User> getSystemStudentUserDataFilteredByMajorAndIntake(int majorId, String intake);

    //ADMIN EDIT USER
    public String adminUpdateUserProfileDetails(UserProfile user);

    //STUDENT MAKE UPDATE TO PROFILE
    public String studentUpdateDetails(UserProfile user);

    //ADMIN EDIT USER
    public String adminUpdateUserMainDetails(User user);

    //STUDENT EDIT USER
    public String studentUpdateUserMainDetails(User user);

    //ADMIN EDIT USER
    public String adminUpdateStudentClass(StudentClass studentClass);

    //ADMIN EDIT USER DEAFULT PASSWORDS SELECT
    public UserProfile getUserDefaultPasswordsForReset(long userId);

    //ADMIN EDIT PROFILE
    public String adminResetUserPassword(long userID , String defaultPassword);

    //SYSTEM GET CURRENT PASSWORD AUTHENTICATION CONTROLLER to test
    public String getUserPasswordByUserId(long userId);

    //USER CHANGE PASSWORD AUTHENTICATION CONTROLLER
    public String changePassword(long userId,String newPassword);

    //ADMINN EDIT USER
    public UserProfile getCurrentProfileData(long userId);

    //ADMIN EDIT USER SETTINGS
    public UserProfile getUserSettings( long userId);

    // USER SETTINGS
    public String changeUserProfileSettings(UserProfile user);

    //ADMIN EDIT USER
    public String disableUser(long userId);

    //ADMIN EDIT USER
    public  String enableUser(long userId);

    public String addNewIntake(String intakeName,String intakeYear);

    //ANDMIN ENROLL STUDENT
    public List<User> getStudentsNotEnrolledInCourseList(int childCourseId);

    //ADMIN ENROLLMENT LIST
    public List<User> getStudentsEnrolledInCourseList(int childCourseId);

    //##ADMIN COURSE ENROLMENT LIST WITH COURSE GRADE AVERAGE
    public List<User> getStudentEnrolledInCourseWithGradeAverage(int courseId);

    // ADMIN ENROLL IN EXAM
    public List<User> getStudentsNotEnrolledInExamList(int examId);

    //ADMIN ENROLL IN CLASS
    public List<User> getStudentsNotEnrolledInAnyClassList();

    //ADMIN CLASS ENROLMENT LIST
    public List<User> getClassEnrolledListByClassId(int classId);

    //##ADMIN EXAM STUDENT ENROLLMENT LIST
    public List<User> getStudentsEnrolledInExamList(int examId);

    //CLEARANCE EXAM STUDENT ENROLMENT LIST
    public List<User> getStudentsEnrolledInClearanceExam(int clearanceExamId);

    //ADMIN COURSE ENROLLMENT lIST same as getStudentsEnrolledInCourseList exept for DD
    public List<User> getCourseEnrollmentListByCourseId(int courseId);

    //ADMIN COURESE STUDENT ENROLLMENET REQUEST LIST
    public List<User> getCourseEnrollmentRequestList(int courseId);

    //##ADMIN EXAM ENROLMENT LIST
    public List<User> getExamEnrollmentListByExamId(int examId);

    //ADMIN EXAM STUDENT ENROLMENT REQUEST LIST
    public List<User> getExamEnrollmentRequestList(int examId);

    //ADMIN ADD USER LAST ADDED
    public List<User> getLastAddedUsersList();

    //ADMIM GET LAST EDITED USERS
    public List<User> getLastEditedUsersList();

    //GET DISTINCT USER INTAKE SELECT LIST
    public List<String> getCurrentUserIntakesList();

    // GET DISTINCT CLASS INTAKE SELECT LIST
    public List<String> getCurrentClassIntakeList();

    //ADMIN TEST FOR ADDING STUDENT USING EXCEL TO COURSE
    //CHECK IF USER ID IS IN SYSTEM ELSE DONT ENROLL IN CLASS
    //RETURNS TRUE IF USER IN SYSTEM
    public boolean checkIfUserIdInSystem(long userId);

    //LOGIN CHECK IF USER IS DISABLED OR NOT
    public boolean checkIfUserIsActive(long userId);

    //updated version
    public boolean checkIfUserIsActive2(String userId);

//    //ENROLL STUDENT
//    public List<User> getCourseEnrolmentList(int courseId);

    //ADMIN GET TEACHER LIST
    public List<User> getTeachersList();

    //GET STUDENT DEGREE TYPE

    public String getStudentDegreeType(long studentId);

    public int getStudentMajorId(long studentId);

    public String makeStudentDegreeTypeChangeRequest(StudentDegreeTypeChangeRequest studentDegreeTypeChangeRequest);

    public List<StudentDegreeTypeChangeRequest> getAllStudentDegreeTypeChangeRequest();

    public List<StudentDegreeTypeChangeRequest> getAllStudentDegreeTypeChangeRequestForStudent(long studentId);

    public String acceptStudentDegreeChangeRequest(int requestId);

    public String changeStudentDegreeType(long studentId , String degreeType);

    public String declineStudentDegreeChangeRequest(int requestId);

    public String cancelDegreeTypeChangeRequest(int requestId);

    public String getUserProfilePic(long userId);

    //ADMIN DELETE STUDENT FROM WHOLE SYSTEM
    public String deleteStudentFromCourseEnrolments(long studentId);

    public String deleteStudentFromCourseStudentRequestEnrolment(long studentId);

    public String deleteStudentFromExamEnrolment(long studentId);

    public String deleteStudentFromExamStudentRequestEnrolment(long studentId);

    public String deleteStudentFromGrade(long studentId);

    public String deleteStudentFromStudentClass(long studentId);

    public String deleteStudentFromUserRole(long studentId);

    public String deleteUserFromUsers(long userId);

    public String deleteUserFromUserProfile(long userId);

    public String deleteUserFromStudentMajor(long studentId);

    public String deleteStudentFromClearanceExamEnrolment(long studentId);

    public String deleteStudentFromGradeCustom(long studentId);













}
