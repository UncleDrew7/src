package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class Semester {

    private int semesterId;
    private String semesterCode;
    private String startDate;
    private String endDate;
    private long createdBy;
    private int totalNumberOfCourses;
    private Date createdAt;
    private String updatedAt;

    public Semester(){
        super();
    }



    //ADD SEMESTER
    public Semester(String semesterCode, String startDate, String endDate, long createdBy) {
        this.semesterCode = semesterCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdBy = createdBy;
    }

    //UPDATE SEMESTER
    public Semester(int semesterId, String semesterCode, String startDate, String endDate) {
        this.semesterId = semesterId;
        this.semesterCode = semesterCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTotalNumberOfCourses() {
        return totalNumberOfCourses;
    }

    public void setTotalNumberOfCourses(int totalNumberOfCourses) {
        this.totalNumberOfCourses = totalNumberOfCourses;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }
}
