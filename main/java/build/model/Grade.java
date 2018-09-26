package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class Grade {

    private int gradeId;
    private long parentCourseId;
    private int childCourseId;
    private int courseId;
    private long studentId;
    private long uploadedBy;
    private int gradeItemId;
    private String studentName;
    private int classId;
    private String studentClass;
    private Object customGradeScore;
    private int numericGrade;
    private String StringGrade;
    private String gradeScoreType;
    private int grade;
    private Date createdAt;
    private Date updatedAt;
    private int  clearanceGrade;

    public Grade(){
        super();
    }

    public Grade(int courseId, long studentId, long uploadedBy, int gradeItemId, int grade) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.uploadedBy = uploadedBy;
        this.gradeItemId = gradeItemId;
        this.grade = grade;
    }




    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(long uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getGradeItemId() {
        return gradeItemId;
    }

    public void setGradeItemId(int gradeItemId) {
        this.gradeItemId = gradeItemId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
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

    public Object getCustomGradeScore() {
        return customGradeScore;
    }

    public void setCustomGradeScore(Object customGradeScore) {
        this.customGradeScore = customGradeScore;
    }

    public int getNumericGrade() {
        return numericGrade;
    }

    public void setNumericGrade(int numericGrade) {
        this.numericGrade = numericGrade;
    }

    public String getStringGrade() {
        return StringGrade;
    }

    public void setStringGrade(String stringGrade) {
        StringGrade = stringGrade;
    }

    public String getGradeScoreType() {
        return gradeScoreType;
    }

    public void setGradeScoreType(String gradeScoreType) {
        this.gradeScoreType = gradeScoreType;
    }

    public int getClearanceGrade() {
        return clearanceGrade;
    }

    public void setClearanceGrade(int clearanceGrade) {
        this.clearanceGrade = clearanceGrade;
    }
}
