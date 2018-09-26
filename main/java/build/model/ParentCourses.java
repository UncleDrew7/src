package build.model;

/**
 * Created by apple on 01/12/2017.
 */
public class ParentCourses {

    private long parentCourseId;
    private String courseType;
    private String courseShortName;
    private String courseShortNameCN;
    private String courseName;
    private String description;
    private double credits;
    private String status;
    private long createdBy;
    private String createdAt;
    private String updatedAt;
    private String majorName;
    private String majorShortName;
    private int majorId;
    private int childCoursesCount;

    private int childCourseId;

    public ParentCourses() {
        super();
    }

    public ParentCourses(long parentCourseId, String courseType ,String courseShortName, String courseName, String description, int credits, long createdBy, int majorId) {
        this.parentCourseId = parentCourseId;
        this.courseType = courseType;
        this.courseShortName = courseShortName;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
        this.createdBy = createdBy;
        this.majorId = majorId;
    }

    public ParentCourses(long parentCourseId, String courseType, String courseShortName, String courseName, String description, int credits, String status, int majorId) {
        this.parentCourseId = parentCourseId;
        this.courseType = courseType;
        this.courseShortName = courseShortName;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
        this.status = status;
        this.majorId = majorId;
    }

    public long getParentCourseId() {
        return parentCourseId;
    }

    public void setParentCourseId(long parentCourseId) {
        this.parentCourseId = parentCourseId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseShortName() {
        return courseShortName;
    }

    public void setCourseShortName(String courseShortName) {
        this.courseShortName = courseShortName;
    }

    public String getCourseShortNameCN() {
        return courseShortNameCN;
    }

    public void setCourseShortNameCN(String courseShortNameCN) {
        this.courseShortNameCN = courseShortNameCN;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
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

    public int getChildCoursesCount() {
        return childCoursesCount;
    }

    public void setChildCoursesCount(int childCoursesCount) {
        this.childCoursesCount = childCoursesCount;
    }

    public int getChildCourseId() {
        return childCourseId;
    }

    public void setChildCourseId(int childCourseId) {
        this.childCourseId = childCourseId;
    }
}
