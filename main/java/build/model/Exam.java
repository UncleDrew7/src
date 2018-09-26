package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class Exam {

    private int id;
    private int  courseId;
    private long parentCourseId;
    private int semesterId;
    private String semesterCode;
    private String parentCourseName;
    private String parentCourseShortName;
    private int childCourseId;
    private String childCourseName;
    private String  examName;
    private long  createdBy;
    private String dateOfExam;
    private String  enrolmentStartDate;
    private String  enrolmentCloseDate;
    private double weight ;
    private int minGrade;
    private int maxGrade;
    private Date createdAt;
    private Date updatedAt;
    private int enrollMentStudents;

    private int majorId;
    private String majorCode;
    private String majorName;

    private int teacherId;
    private String teacherName;


    public Exam(){
        super();
    }

    public Exam(int pCourseId, int semesterId, String examName, int createdBy, String dateOfExam, String enrolmentCloseDate) {
        this.parentCourseId = pCourseId;
        this.semesterId = semesterId;
        this.examName = examName;
        this.createdBy = createdBy;
        this.dateOfExam = dateOfExam;
        this.enrolmentCloseDate = enrolmentCloseDate;
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

    public int getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId;  }
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName;  }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterIdId(int semesterId) { this.semesterId = semesterId; }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }


    public int getMajorId() {
        return majorId;
    }
    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }
    public String getMajorCode() {
        return majorCode;
    }
    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }
    public String getMajorName() {
        return majorName;
    }
    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }


    public int getEnrollMentStudents() {
        return enrollMentStudents;
    }

    public void setEnrollMentStudents(int enrollMentStudents) {
        this.enrollMentStudents = enrollMentStudents;
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

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public String getDateOfExam() {
        return dateOfExam;
    }

    public void setDateOfExam(String dateOfExam) {
        this.dateOfExam = dateOfExam;
    }

    public String getEnrolmentStartDate() {
        return enrolmentStartDate;
    }

    public void setEnrolmentStartDate(String enrolmentStartDate) {
        this.enrolmentStartDate = enrolmentStartDate;
    }

    public String getEnrolmentCloseDate() {
        return enrolmentCloseDate;
    }

    public void setEnrolmentCloseDate(String enrolmentCloseDate) {
        this.enrolmentCloseDate = enrolmentCloseDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(int minGrade) {
        this.minGrade = minGrade;
    }

    public int getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    public String getParentCourseName() {
        return parentCourseName;
    }

    public void setParentCourseName(String parentCourseName) {
        this.parentCourseName = parentCourseName;
    }

    public String getParentCourseShortName() {
        return parentCourseShortName;
    }

    public void setParentCourseShortName(String parentCourseShortName) {
        this.parentCourseShortName = parentCourseShortName;
    }

    public String getChildCourseName() {
        return childCourseName;
    }

    public void setChildCourseName(String childCourseName) {
        this.childCourseName = childCourseName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
