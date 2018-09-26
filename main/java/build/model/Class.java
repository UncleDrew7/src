package build.model;

/**
 * Created by apple on 15/08/2017.
 */
public class Class {

    private int  id;//classId
    private long createdBy;
    private String  className;
    private int  intakePeriod;
    private String  status;
    private int major;
    private int numberOfStudents;
    private String  createdAt;
    private String  deletedAt;

    private int majorId;
    private String majorName;
    private String majorShortName;


    public Class() {
        super();
    }

    public Class(int id, String className, int intakePeriod, String status, String createdAt, String deletedAt) {
        this.id = id;
        this.className = className;
        this.intakePeriod = intakePeriod;
        this.status = status;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public Class(String className, int intakePeriod, String status, String createdAt, String deletedAt) {
        this.className = className;
        this.intakePeriod = intakePeriod;
        this.status = status;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public Class(String className, int intakePeriod,long createdBy) {
        this.className = className;
        this.intakePeriod = intakePeriod;
        this.createdBy = createdBy;
    }

    public Class(int id, String className, int intakePeriod, String status) {
        this.id = id;
        this.className = className;
        this.intakePeriod = intakePeriod;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getIntakePeriod() {
        return intakePeriod;
    }

    public void setIntakePeriod(int intakePeriod) {
        this.intakePeriod = intakePeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorShortName() {
        return majorShortName;
    }

    public void setMajorShortName(String majorShortName) {
        this.majorShortName = majorShortName;
    }
}
