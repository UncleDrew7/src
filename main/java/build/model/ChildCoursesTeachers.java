package build.model;

/**
 * Created by apple on 14/12/2017.
 */
public class ChildCoursesTeachers {

    private int id;
    private long teacherId;
    private int childCourseId;
    private String createdAt;
    private String updatedAt;

    public ChildCoursesTeachers() {
        super();
    }

    public ChildCoursesTeachers(long teacherId, int childCourseId) {
        this.teacherId = teacherId;
        this.childCourseId = childCourseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public int getChildCourseId() {
        return childCourseId;
    }

    public void setChildCourseId(int childCourseId) {
        this.childCourseId = childCourseId;
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
}
