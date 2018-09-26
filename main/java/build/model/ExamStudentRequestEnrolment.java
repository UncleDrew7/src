package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class ExamStudentRequestEnrolment {

    private int id;
    private long studentId;
    private int courseId;
    private int examId;
    private int enrolmentStatus;
    private Date submissionDate;

    public ExamStudentRequestEnrolment(){
        super();
    }

    //STUDENT MAKE ENROLLMENT REQUEST
    public ExamStudentRequestEnrolment(int courseId,int examId,long studentId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.examId = examId;
    }

    public ExamStudentRequestEnrolment(int examId, long studentId) {
        this.studentId = studentId;
        this.examId = examId;
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

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getEnrolmentStatus() {
        return enrolmentStatus;
    }

    public void setEnrolmentStatus(int enrolmentStatus) {
        this.enrolmentStatus = enrolmentStatus;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
}
