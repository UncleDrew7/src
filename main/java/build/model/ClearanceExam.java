package build.model;

/**
 * Created by apple on 06/01/2018.
 */
public class ClearanceExam {

    private int clearanceExamId;
    private int parentExamId;
    private String parentExamName;
    private long parentCourseId;
    private String parentCourseName;
    private String parentCourseShortName;
    private int childCourseId;
    private String childCourseName;
    private String examName;
    private String examDate;
    private String enrolmentStartDate;
    private String enrolmentEndDate;
    private double clearanceGradeScore;
    private double enrolmentGradeMin;
    private double enrolmentGradeMax;
    private long createdBy;
    private String createdAt;
    private String updatedAt;

    private String grade;
    private int semesterId;
    private String semesterCode;
    private int enrolledStudents;
    private int submitted;



    public ClearanceExam() {
        super();
    }


    public int getClearanceExamId() {
        return clearanceExamId;
    }

    public void setClearanceExamId(int clearanceExamId) {
        this.clearanceExamId = clearanceExamId;
    }

    public int getParentExamId() {
        return parentExamId;
    }

    public void setParentExamId(int parentExamId) {
        this.parentExamId = parentExamId;
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

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getEnrolmentStartDate() {
        return enrolmentStartDate;
    }

    public void setEnrolmentStartDate(String enrolmentStartDate) {
        this.enrolmentStartDate = enrolmentStartDate;
    }

    public String getEnrolmentEndDate() {
        return enrolmentEndDate;
    }

    public void setEnrolmentEndDate(String enrolmentEndDate) {
        this.enrolmentEndDate = enrolmentEndDate;
    }

    public double getClearanceGradeScore() {
        return clearanceGradeScore;
    }

    public void setClearanceGradeScore(double clearanceGradeScore) {
        this.clearanceGradeScore = clearanceGradeScore;
    }

    public double getEnrolmentGradeMin() {
        return enrolmentGradeMin;
    }

    public void setEnrolmentGradeMin(double enrolmentGradeMin) {
        this.enrolmentGradeMin = enrolmentGradeMin;
    }

    public double getEnrolmentGradeMax() {
        return enrolmentGradeMax;
    }

    public void setEnrolmentGradeMax(double enrolmentGradeMax) {
        this.enrolmentGradeMax = enrolmentGradeMax;
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

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {     this.grade = grade;    }

    public String getParentExamName() {
        return parentExamName;
    }

    public void setParentExamName(String parentExamName) {
        this.parentExamName = parentExamName;
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

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public String getChildCourseName() {
        return childCourseName;
    }

    public void setChildCourseName(String childCourseName) {
        this.childCourseName = childCourseName;
    }

    public int getSubmitted() {
        return submitted;
    }

    public void setSubmitted(int submitted) {
        this.submitted = submitted;
    }
}
