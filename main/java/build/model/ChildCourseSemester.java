package build.model;

/**
 * Created by apple on 14/12/2017.
 */
public class ChildCourseSemester {

    private int id;
    private long parentCourseId;
    private int semesterId;
    private int childCourseId;
    private String createdAt;
    private String updatedAt;

    public ChildCourseSemester() {
        super();
    }

    public ChildCourseSemester(long parentCourseId, int semesterId, int childCourseId) {
        this.parentCourseId = parentCourseId;
        this.semesterId = semesterId;
        this.childCourseId = childCourseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getParentCourseId() {
        return parentCourseId;
    }

    public void setParentCourseId(long parentCourseId) {
        this.parentCourseId = parentCourseId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
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
