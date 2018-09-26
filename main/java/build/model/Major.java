package build.model;

/**
 * Created by apple on 01/12/2017.
 */
public class Major {

    private int majorId;
    private String majorShortName;
    private String majorName;
    private long createdBy;
    private String createdAt;
    private String updatedAt;

    private long parentCourseId;
    private long studentId;

    public Major() {
        super();
    }

    public Major(String majorShortName, String majorName, long createdBy) {
        this.majorShortName = majorShortName;
        this.majorName = majorName;
        this.createdBy = createdBy;
    }

    public Major(int majorId , String majorShortName, String majorName) {
        this.majorId = majorId;
        this.majorShortName = majorShortName;
        this.majorName = majorName;

    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajorShortName() {
        return majorShortName;
    }

    public void setMajorShortName(String majorShortName) {
        this.majorShortName = majorShortName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
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

    public long getParentCourseId() {
        return parentCourseId;
    }

    public void setParentCourseId(long parentCourseId) {
        this.parentCourseId = parentCourseId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }
}
