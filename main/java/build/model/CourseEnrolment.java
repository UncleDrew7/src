package build.model;

/**
 * Created by apple on 15/08/2017.
 */
public class CourseEnrolment {
    private int id ;
    private int courseId;
    private long studentId;
    private String studentName;
    private int isEnrolled;
    private String enrolmentDate;
    private int hasUnenrolled;
    private String unEnrolmentDate;

    private String courseScore;
    private String exam1;
    private String exam2;
    private String exam3;
    private String final1;
    private String final2;
    private String final3;
    private String finalAll;
    private String highest;
    private int gradeType;

    private String courseCode;
    private String courseName;
    private String courseNameCN;
    private String semester;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNameCN() {
        return courseNameCN;
    }

    public void setCourseNameCN(String courseNameCN) {
        this.courseNameCN = courseNameCN;
    }

    public String getExam1() {
        return exam1;
    }

    public void setExam1(String exam1) {
        this.exam1 = exam1;
    }

    public String getExam2() {
        return exam2;
    }

    public void setExam2(String exam2) {
        this.exam2 = exam2;
    }

    public String getExam3() {
        return exam3;
    }

    public void setExam3(String exam3) {
        this.exam3 = exam3;
    }

    public String getFinal1() {
        return final1;
    }

    public void setFinal1(String final1) {
        this.final1 = final1;
    }

    public String getFinal2() {
        return final2;
    }

    public void setFinal2(String final2) {
        this.final2 = final2;
    }

    public String getFinal3() {
        return final3;
    }

    public void setFinal3(String final3) {
        this.final3 = final3;
    }

    public String getFinalAll() {
        return finalAll;
    }

    public void setFinalAll(String finalAll) {
        this.finalAll = finalAll;
    }

    public int getGradeType() {
        return gradeType;
    }

    public void setGradeType(int gradeType) {
        this.gradeType = gradeType;
    }

    public String getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(String courseScore) {
        this.courseScore = courseScore;
    }

    public CourseEnrolment() {
        super();
    }

//    enroll in course
    public CourseEnrolment(int courseId, long studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public CourseEnrolment(int id, int courseId, long studentId, int isEnrolled, String enrolmentDate, int hasUnenrolled, String unEnrolmentDate) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
        this.isEnrolled = isEnrolled;
        this.enrolmentDate = enrolmentDate;
        this.hasUnenrolled = hasUnenrolled;
        this.unEnrolmentDate = unEnrolmentDate;
    }

    public CourseEnrolment(int courseId, long studentId, int isEnrolled, String enrolmentDate, int hasUnenrolled, String unEnrolmentDate) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.isEnrolled = isEnrolled;
        this.enrolmentDate = enrolmentDate;
        this.hasUnenrolled = hasUnenrolled;
        this.unEnrolmentDate = unEnrolmentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getIsEnrolled() {
        return isEnrolled;
    }

    public void setIsEnrolled(int isEnrolled) {
        this.isEnrolled = isEnrolled;
    }

    public String getEnrolmentDate() {
        return enrolmentDate;
    }

    public void setEnrolmentDate(String enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
    }

    public int getHasUnenrolled() {
        return hasUnenrolled;
    }

    public void setHasUnenrolled(int hasUnenrolled) {
        this.hasUnenrolled = hasUnenrolled;
    }

    public String getUnEnrolmentDate() {
        return unEnrolmentDate;
    }

    public void setUnEnrolmentDate(String unEnrolmentDate) {
        this.unEnrolmentDate = unEnrolmentDate;
    }

    public String getHighest() {
        return highest;
    }

    public void setHighest(String highest) {
        this.highest = highest;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
