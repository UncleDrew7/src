package build.model;

/**
 * Created by apple on 15/08/2017.
 */
public class StudentCourseEnrolment {
    private int id;
    private long studentId;
    private int courseId;
    private String enrolmentStatus;
    private String submissionDate;
    private String enrolmentDate;
    private String unEnrolmentDate;

    public StudentCourseEnrolment() {
        super();
    }

    public StudentCourseEnrolment(long studentId, int courseId, String enrolmentStatus, String submissionDate, String enrolmentDate, String unEnrolmentDate) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrolmentStatus = enrolmentStatus;
        this.submissionDate = submissionDate;
        this.enrolmentDate = enrolmentDate;
        this.unEnrolmentDate = unEnrolmentDate;
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

    public String getEnrolmentStatus() {
        return enrolmentStatus;
    }

    public void setEnrolmentStatus(String enrolmentStatus) {
        this.enrolmentStatus = enrolmentStatus;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getEnrolmentDate() {
        return enrolmentDate;
    }

    public void setEnrolmentDate(String enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
    }

    public String getUnEnrolmentDate() {
        return unEnrolmentDate;
    }

    public void setUnEnrolmentDate(String unEnrolmentDate) {
        this.unEnrolmentDate = unEnrolmentDate;
    }
}
