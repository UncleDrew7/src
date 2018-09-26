package build.model;

/**
 * Created by apple on 01/12/2017.
 */
public class ParentCoursesMajor {

    private int id ;
    private int majorId;
    private int parentCourseId;
    private String createdAt;
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public int getParentCourseId() {
        return parentCourseId;
    }

    public void setParentCourseId(int parentCourseId) {
        this.parentCourseId = parentCourseId;
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
