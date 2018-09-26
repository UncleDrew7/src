package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class GradeCourseTotal {

    private int gradeCourseTotalId;
    private int courseId;
    private long studentId;
    private int gradeTotal;
    private Date createdAt;
    private Date updatedAt;

    public GradeCourseTotal(){
        super();
    }

    public GradeCourseTotal(int courseId, long studentId, int gradeTotal) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.gradeTotal = gradeTotal;
    }

    public int getGradeCourseTotalId() {
        return gradeCourseTotalId;
    }

    public void setGradeCourseTotalId(int gradeCourseTotalId) {
        this.gradeCourseTotalId = gradeCourseTotalId;
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

    public int getGradeTotal() {
        return gradeTotal;
    }

    public void setGradeTotal(int gradeTotal) {
        this.gradeTotal = gradeTotal;
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
}
