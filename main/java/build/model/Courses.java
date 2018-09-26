package build.model;

/**
 * Created by apple on 15/08/2017.
 */
public class Courses {
    private int id;
    private int courseId;
    private int categoryId;//replace with semester id after handling dependencies
    private int semseterId;
    private String semesterCode;
    private String courseShortName;
    private String courseName;
    private String contentLanguage;
    private String courseDescriptionEn;
    private String courseDescriptionCn;
    private long teacherId;//userId
    private String teacherName;//username
    private String courseType;
    private double gradeAverage;
    private int totalEnrollments;
    private int  totalEnrollmentRequests;
    private String startDate;
    private String endDate;
    private String EnrollmentDeadline;
    private String status;
    private int currentEnrollmentStatus;
    private String courseEnrollmentSubmissionDate;
    private String coursEnrollmentDate;
    private String updatedAt;
    private int courseAverage ;

    private int majorId;
    private String  majorName;
    private String majorShortName;
    private long parentCourseId;
    private int childCourseId;
    private double credits;
    private int examCount;

    private String displayType;

    private int enrollmentId;



    public Courses() {
        super();
    }

    public Courses(int courseId, long teacherId, int categoryId, String courseShortName,String courseName, String courseDescriptionEn, String courseDescriptionCn, String courseType, String startDate, String endDate) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.categoryId = categoryId;
        this.courseName = courseName;
        this.courseDescriptionEn = courseDescriptionEn;
        this.courseDescriptionCn = courseDescriptionCn;
        this.courseType = courseType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseShortName = courseShortName;
    }

    //UPDATE EXSISTING  COURSE
    public Courses(int courseId, String courseShortName, String courseName,String courseDescriptionEn, long teacherId, String startDate, String endDate) {
        this.courseId = courseId;
        this.courseShortName = courseShortName;
        this.courseName = courseName;
        this.courseDescriptionEn = courseDescriptionEn;
        this.teacherId = teacherId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //UPDATE EXSITING COURSE SETTINGS
    public Courses(int courseId, String contentLanguage, String courseType, String status) {
        this.courseId = courseId;
        this.contentLanguage = contentLanguage;
        this.courseType = courseType;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getContentLanguage() {
        return contentLanguage;
    }

    public void setContentLanguage(String contentLanguage) {
        this.contentLanguage = contentLanguage;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCourseDescriptionEn() {
        return courseDescriptionEn;
    }

    public void setCourseDescriptionEn(String courseDescriptionEn) {
        this.courseDescriptionEn = courseDescriptionEn;
    }

    public String getCourseDescriptionCn() {
        return courseDescriptionCn;
    }

    public void setCourseDescriptionCn(String courseDescriptionCn) {
        this.courseDescriptionCn = courseDescriptionCn;
    }

    public String getCourseShortName() {
        return courseShortName;
    }

    public void setCourseShortName(String courseShortName) {
        this.courseShortName = courseShortName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }

    public double getGradeAverage() {
        return gradeAverage;
    }

    public void setGradeAverage(double gradeAverage) {
        this.gradeAverage = gradeAverage;
    }

    public int getTotalEnrollments() {
        return totalEnrollments;
    }

    public void setTotalEnrollments(int totalEnrollments) {
        this.totalEnrollments = totalEnrollments;
    }

    public int getTotalEnrollmentRequests() {
        return totalEnrollmentRequests;
    }

    public void setTotalEnrollmentRequests(int totalEnrollmentRequests) {
        this.totalEnrollmentRequests = totalEnrollmentRequests;
    }

    public int getSemseterId() {
        return semseterId;
    }

    public void setSemseterId(int semseterId) {
        this.semseterId = semseterId;
    }

    public String getEnrollmentDeadline() {
        return EnrollmentDeadline;
    }

    public void setEnrollmentDeadline(String enrollmentDeadline) {
        EnrollmentDeadline = enrollmentDeadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCurrentEnrollmentStatus() {
        return currentEnrollmentStatus;
    }

    public void setCurrentEnrollmentStatus(int currentEnrollmentStatus) {
        this.currentEnrollmentStatus = currentEnrollmentStatus;
    }

    public String getCourseEnrollmentSubmissionDate() {
        return courseEnrollmentSubmissionDate;
    }

    public void setCourseEnrollmentSubmissionDate(String courseEnrollmentSubmissionDate) {
        this.courseEnrollmentSubmissionDate = courseEnrollmentSubmissionDate;
    }

    public String getCoursEnrollmentDate() {
        return coursEnrollmentDate;
    }

    public void setCoursEnrollmentDate(String coursEnrollmentDate) {
        this.coursEnrollmentDate = coursEnrollmentDate;
    }

    public int getCourseAverage() {
        return courseAverage;
    }

    public void setCourseAverage(int courseAverage) {
        this.courseAverage = courseAverage;
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

    public String getMajorShortName() {
        return majorShortName;
    }

    public void setMajorShortName(String majorShortName) {
        this.majorShortName = majorShortName;
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

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public int getExamCount() {
        return examCount;
    }

    public void setExamCount(int examCount) {
        this.examCount = examCount;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }
}
