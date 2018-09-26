package build.model;

import java.util.Date;

/**
 * Created by Lani on 29/08/2017.
 * WINNI
 *
 */
public class CourseStudentRequestEnrolment {

    private int id;
    private String  courseId;
    private String  studentId;
    private String  enrolmentStatus;
    private Date submissionDate;

    CourseStudentRequestEnrolment(){
        super();
    }

    public CourseStudentRequestEnrolment(String courseId, String studentId, String enrolmentStatus, Date submissionDate) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.enrolmentStatus = enrolmentStatus;
        this.submissionDate = submissionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEnrolmentStatus() {
        return enrolmentStatus;
    }

    public void setEnrolmentStatus(String enrolmentStatus) {
        this.enrolmentStatus = enrolmentStatus;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
}
