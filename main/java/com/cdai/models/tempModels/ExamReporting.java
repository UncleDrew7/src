package com.cdai.models.tempModels;

/**
 * Created by apple on 07/01/2018.
 */
public class ExamReporting {
    private String examType;
    private long parentCourseId;
    private int childCourseId;
    private String parentCourseName;
    private String childCourseName;
    private int parentExamId;
    private int childExamId;
    private String examName;
    private String examDate;
    private String examEnrollmentDeadline;
    private String grade ;

    private double weight;
    private int maxGrade;
    private int minGrade ;
    private boolean isExamActive;
    private int totalEnrolledStudents;

    public ExamReporting() {
            super();
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
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

    public int getParentExamId() {
        return parentExamId;
    }

    public void setParentExamId(int parentExamId) {
        this.parentExamId = parentExamId;
    }

    public int getChildExamId() {
        return childExamId;
    }

    public void setChildExamId(int childExamId) {
        this.childExamId = childExamId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamEnrollmentDeadline() {
        return examEnrollmentDeadline;
    }

    public void setExamEnrollmentDeadline(String examEnrollmentDeadline) {
        this.examEnrollmentDeadline = examEnrollmentDeadline;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getParentCourseName() {
        return parentCourseName;
    }

    public void setParentCourseName(String parentCourseName) {
        this.parentCourseName = parentCourseName;
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

    public int getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    public int getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(int minGrade) {
        this.minGrade = minGrade;
    }

    public boolean getIsExamActive() {
        return isExamActive;
    }

    public void setIsExamActive(boolean examActive) {
        isExamActive = examActive;
    }

    public boolean isExamActive() {
        return isExamActive;
    }

    public void setExamActive(boolean examActive) {
        isExamActive = examActive;
    }

    public int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }

    public void setTotalEnrolledStudents(int totalEnrolledStudents) {
        this.totalEnrolledStudents = totalEnrolledStudents;
    }
}
