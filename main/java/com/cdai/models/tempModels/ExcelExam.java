package com.cdai.models.tempModels;

/**
 * Created by apple on 20/09/2017.
 */
public class ExcelExam {


    private int courseId;
    private String courseName;
    private String examCode;
    private String examDate;
    private String enrolmentStartDate;
    private String enrolmentEndDate;
    private int createdBy;
    private int semesterId;
    private String gradeItemType;
    private float weight ;
    private int minGrade;
    private int maxGrade;
    private int gradeItemPosition;

    private int majorId;
    private String majorName;
    private int childCourseId;
    private long parentCourseId;
    private String childCourseName;
    private String parentCourseName;
    private String semesterCode;


    public ExcelExam() {
        super();
    }

    public ExcelExam(int courseId, String courseName, String examCode, String examDate, String enrolmentStartDate, String enrolmentEndDate) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.examCode = examCode;
        this.examDate = examDate;
        this.enrolmentStartDate = enrolmentStartDate;
        this.enrolmentEndDate = enrolmentEndDate;
    }

    public ExcelExam(String majorName, String semesterCode, long parentCourseId, int childCourseId,  String parentCourseName,String childCourseName,String examCode, String examDate, String enrolmentStartDate, String enrolmentEndDate) {
        this.majorName = majorName;
        this.childCourseId = childCourseId;
        this.parentCourseId = parentCourseId;
        this.childCourseName = childCourseName;
        this.parentCourseName = parentCourseName;
        this.semesterCode = semesterCode;
        this.examCode = examCode;
        this.examDate = examDate;
        this.enrolmentStartDate = enrolmentStartDate;
        this.enrolmentEndDate = enrolmentEndDate;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
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

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getGradeItemType() {
        return gradeItemType;
    }

    public void setGradeItemType(String gradeItemType) {
        this.gradeItemType = gradeItemType;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
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

    public int getGradeItemPosition() {
        return gradeItemPosition;
    }

    public void setGradeItemPosition(int gradeItemPosition) {
        this.gradeItemPosition = gradeItemPosition;
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

    public int getChildCourseId() {
        return childCourseId;
    }

    public void setChildCourseId(int childCourseId) {
        this.childCourseId = childCourseId;
    }

    public long getParentCourseId() {
        return parentCourseId;
    }

    public void setParentCourseId(long parentCourseId) {
        this.parentCourseId = parentCourseId;
    }

    public String getChildCourseName() {
        return childCourseName;
    }

    public void setChildCourseName(String childCourseName) {
        this.childCourseName = childCourseName;
    }

    public String getParentCourseName() {
        return parentCourseName;
    }

    public void setParentCourseName(String parentCourseName) {
        this.parentCourseName = parentCourseName;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }
}
