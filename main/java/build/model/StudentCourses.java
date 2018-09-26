package build.model;

/**
 * Created by apple on 15/08/2017.
 */
public class StudentCourses {
    private int id ;
    private long studentId;
    private int courseId;
    private int categoryId;
    private int isCurrentCourse;
    private int isCompletedCourse;
    private int isUnEnrolledCourse;

    public StudentCourses() {
        super();
    }

    public StudentCourses(long studentId, int courseId, int categoryId, int isCurrentCourse, int isCompletedCourse, int isUnEnrolledCourse) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.categoryId = categoryId;
        this.isCurrentCourse = isCurrentCourse;
        this.isCompletedCourse = isCompletedCourse;
        this.isUnEnrolledCourse = isUnEnrolledCourse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getIsCurrentCourse() {
        return isCurrentCourse;
    }

    public void setIsCurrentCourse(int isCurrentCourse) {
        this.isCurrentCourse = isCurrentCourse;
    }

    public int getIsCompletedCourse() {
        return isCompletedCourse;
    }

    public void setIsCompletedCourse(int isCompletedCourse) {
        this.isCompletedCourse = isCompletedCourse;
    }

    public int getIsUnEnrolledCourse() {
        return isUnEnrolledCourse;
    }

    public void setIsUnEnrolledCourse(int isUnEnrolledCourse) {
        this.isUnEnrolledCourse = isUnEnrolledCourse;
    }
}
