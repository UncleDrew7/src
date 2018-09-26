package build.model;

/**
 * Created by apple on 06/01/2018.
 */
public class GradeCustom {

    private int gradeCustomId;
    private int clearanceExamId;
    private int parentExamId;
    private long parentCourseId;
    private int childCourseId;
    private long studentId;
    private String grade ;
    private long createdBy;
    private String createdAt;
    private String updatedAt ;

    public GradeCustom() {
        super();
    }

    public int getGradeCustomId() {
        return gradeCustomId;
    }

    public void setGradeCustomId(int gradeCustomId) {
        this.gradeCustomId = gradeCustomId;
    }

    public int getClearanceExamId() {
        return clearanceExamId;
    }

    public void setClearanceExamId(int clearanceExamId) {
        this.clearanceExamId = clearanceExamId;
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

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public int getParentExamId() {
        return parentExamId;
    }

    public void setParentExamId(int parentExamId) {
        this.parentExamId = parentExamId;
    }
}
