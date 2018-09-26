package build.model;

/**
 * Created by apple on 14/12/2017.
 */
public class ChildCourses {

    private int childCourseId;
    private String childCourseName;
    private long teacherId;
    private String enrolmentDeadline;
    private String enrolmentStartDate;
    private long createdBy;
    private String createdAt;
    private String updatedAt;

    private int majorId;
    private String majorName;
    private String majorShortCode;
    private long parentCourseId;
    private String courseType;
    private String courseShortName;
    private String courseName;
    private String teacherName;
    private String description;
    private String status;
    private double credits;
    private int semesterId;
    private String semesterCode;
    private int active;

    private int totalEnrolledStudents;

    public ChildCourses() {
        super();
    }

    public ChildCourses(String childCourseName, long teacherId, String enrolmentDeadline,long createdBy) {
        this.childCourseName = childCourseName;
        this.teacherId = teacherId;
        this.enrolmentDeadline = enrolmentDeadline;
        this.createdBy = createdBy;
    }

    public ChildCourses(int childCourseId, String childCourseName, long teacherId, String enrolmentDeadline ,String enrolmentStartDate ) {
        this.childCourseId = childCourseId;
        this.childCourseName = childCourseName;
        this.teacherId = teacherId;
        this.enrolmentDeadline = enrolmentDeadline;
        this.enrolmentStartDate = enrolmentStartDate;
    }

    public int getChildCourseId() {
        return childCourseId;
    }

    public void setChildCourseId(int childCourseId) {
        this.childCourseId = childCourseId;
    }

    public String getChildCourseName() {
        return childCourseName;
    }

    public void setChildCourseName(String childCourseName) {
        this.childCourseName = childCourseName;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getEnrolmentDeadline() {
        return enrolmentDeadline;
    }

    public void setEnrolmentDeadline(String enrolmentDeadline) {
        this.enrolmentDeadline = enrolmentDeadline;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public long getParentCourseId() {
        return parentCourseId;
    }

    public void setParentCourseId(long parentCourseId) {
        this.parentCourseId = parentCourseId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseShortName() {
        return courseShortName;
    }

    public void setCourseShortName(String courseShortName) {
        this.courseShortName = courseShortName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }

    public String getEnrolmentStartDate() {
        return enrolmentStartDate;
    }

    public void setEnrolmentStartDate(String enrolmentStartDate) {
        this.enrolmentStartDate = enrolmentStartDate;
    }

    public int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }

    public void setTotalEnrolledStudents(int totalEnrolledStudents) {
        this.totalEnrolledStudents = totalEnrolledStudents;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
