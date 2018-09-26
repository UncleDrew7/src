package build.model;

/**
 * Created by apple on 14/09/2017.
 */
public class SearchCoursesAndExams {
    private int examId;//gradeItemId
    private int courseId;
    private long teacherId;
    private String teacherName;//userName
    private String examName;//gradeItemName
    private int examCreatedBy;//createdBy
    private String examDate;
    private String getExamEnrolmentStartDate;
    private String examEnrolmentEndDate;
    private String examStatus;
    private String courseName;
    private String courseDescriptionEn;
    private String courseStartDate;
    private String courseEndDate;

    private int majorId;
    private String majorName;
    private String majorShortCode;
    private String courseType;
    private int semesterId;
    private String semesterCode;
    private int childCourseId;
    private String childCourseName;
    private long parentCourseId;
    private String parentCouresName;


    public SearchCoursesAndExams() {
        super();
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getExamCreatedBy() {
        return examCreatedBy;
    }

    public void setExamCreatedBy(int examCreatedBy) {
        this.examCreatedBy = examCreatedBy;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getGetExamEnrolmentStartDate() {
        return getExamEnrolmentStartDate;
    }

    public void setGetExamEnrolmentStartDate(String getExamEnrolmentStartDate) {
        this.getExamEnrolmentStartDate = getExamEnrolmentStartDate;
    }

    public String getExamEnrolmentEndDate() {
        return examEnrolmentEndDate;
    }

    public void setExamEnrolmentEndDate(String examEnrolmentEndDate) {
        this.examEnrolmentEndDate = examEnrolmentEndDate;
    }

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescriptionEn() {
        return courseDescriptionEn;
    }

    public void setCourseDescriptionEn(String courseDescriptionEn) {
        this.courseDescriptionEn = courseDescriptionEn;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorShortCode() {
        return majorShortCode;
    }

    public void setMajorShortCode(String majorShortCode) {
        this.majorShortCode = majorShortCode;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }

    public String getChildCourseName() {
        return childCourseName;
    }

    public void setChildCourseName(String childCourseName) {
        this.childCourseName = childCourseName;
    }

    public long getParentCourseId() {
        return parentCourseId;
    }

    public void setParentCourseId(long parentCourseId) {
        this.parentCourseId = parentCourseId;
    }

    public int getChildCourseId() {
        return childCourseId;
    }

    public void setChildCourseId(int childCourseId) {
        this.childCourseId = childCourseId;
    }

    public String getParentCouresName() {
        return parentCouresName;
    }

    public void setParentCouresName(String parentCouresName) {
        this.parentCouresName = parentCouresName;
    }
}
