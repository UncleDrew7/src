package build.model;

public class StudentDegreeTypeChangeRequest {

    private int requestId;
    private long studentId;
    private String currentDegreeType;
    private String newRequestedDegreeType;
    private String requestStatus;
    private String submissionDate;
    private String dateRequestProcessed;
    private String userName;

    public StudentDegreeTypeChangeRequest() {
        super();
    }

    public StudentDegreeTypeChangeRequest(long studentId, String currentDegreeType, String newRequestedDegreeType) {
        this.studentId = studentId;
        this.currentDegreeType = currentDegreeType;
        this.newRequestedDegreeType = newRequestedDegreeType;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getCurrentDegreeType() {
        return currentDegreeType;
    }

    public void setCurrentDegreeType(String currentDegreeType) {
        this.currentDegreeType = currentDegreeType;
    }

    public String getNewRequestedDegreeType() {
        return newRequestedDegreeType;
    }

    public void setNewRequestedDegreeType(String newRequestedDegreeType) {
        this.newRequestedDegreeType = newRequestedDegreeType;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getDateRequestProcessed() {
        return dateRequestProcessed;
    }

    public void setDateRequestProcessed(String dateRequestProcessed) {
        this.dateRequestProcessed = dateRequestProcessed;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
