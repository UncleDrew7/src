package build.model;

/**
 * Created by apple on 06/01/2018.
 */
public class ClearanceExamEnrolment {
    private int clearanceExamEnrolmentId;
    private int clearanceExamId;
    private int parentExamId;
    private long studentId;
    private int isEnrolled;
    private String enrollmentDate;

    public ClearanceExamEnrolment() {
        super();
    }

    public ClearanceExamEnrolment(int clearanceExamId, long studentId) {
        this.clearanceExamId = clearanceExamId;
        this.studentId = studentId;
    }

    public ClearanceExamEnrolment(int clearanceExamId , int parentExamId, long studentId) {
        this.clearanceExamId = clearanceExamId;
        this.parentExamId = parentExamId;
        this.studentId = studentId;

    }

    public int getClearanceExamEnrolmentId() {
        return clearanceExamEnrolmentId;
    }

    public void setClearanceExamEnrolmentId(int clearanceExamEnrolmentId) {
        this.clearanceExamEnrolmentId = clearanceExamEnrolmentId;
    }

    public int getParentExamId() {
        return parentExamId;
    }

    public void setParentExamId(int parentExamId) {
        this.parentExamId = parentExamId;
    }

    public int getIsEnrolled() {
        return isEnrolled;
    }

    public void setIsEnrolled(int isEnrolled) {
        this.isEnrolled = isEnrolled;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getClearanceExamId() {
        return clearanceExamId;
    }

    public void setClearanceExamId(int clearanceExamId) {
        this.clearanceExamId = clearanceExamId;
    }


}
