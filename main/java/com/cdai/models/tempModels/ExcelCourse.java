package com.cdai.models.tempModels;

/**
 * Created by apple on 18/08/2017.
 */
public class ExcelCourse {

    private String major;
    private long parentCourseId;
    private String courseType;
    private double credits;
    private String courseName;
    private String courseShortName;
    private String enrolmentDeadline;
    private int courseId ;
    private long teacherId;
    private String teacherName;
    private int categoryId;
    private String  CourseDescription;
    private String startDate;
    private String endDate;



    public ExcelCourse() {
        super();
    }


    public ExcelCourse(String major, long parentCourseId, String courseType, double credits, String courseName, String courseShortName, String enrolmentDeadline, long teacherId, String teacherName) {
        this.major = major;
        this.parentCourseId = parentCourseId;
        this.courseType = courseType;
        this.credits = credits;
        this.courseName = courseName;
        this.courseShortName = courseShortName;
        this.enrolmentDeadline = enrolmentDeadline;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public ExcelCourse(int courseId, long teacherId, int categoryId, String courseName, String courseDescription, String courseType, String startDate, String endDate) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.categoryId = categoryId;
        this.courseName = courseName;
        CourseDescription = courseDescription;
        this.courseType = courseType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ExcelCourse(long parentCourseId, String courseType, String courseShortName, String courseName, double credits, String courseDescription) {
        this.parentCourseId = parentCourseId;
        this.courseType = courseType;
        this.courseShortName = courseShortName;
        this.courseName = courseName;
        this.credits = credits;
        CourseDescription = courseDescription;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return CourseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        CourseDescription = courseDescription;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
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

    public long getParentCourseId() {
        return parentCourseId;
    }

    public void setParentCourseId(long parentCourseId) {
        this.parentCourseId = parentCourseId;
    }

    public String getCourseShortName() {
        return courseShortName;
    }

    public void setCourseShortName(String courseShortName) {
        this.courseShortName = courseShortName;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEnrolmentDeadline() {
        return enrolmentDeadline;
    }

    public void setEnrolmentDeadline(String enrolmentDeadline) {
        this.enrolmentDeadline = enrolmentDeadline;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
