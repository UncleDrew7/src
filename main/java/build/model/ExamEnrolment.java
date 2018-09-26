package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class ExamEnrolment {

    private int id;
    private long studentId;
    private String studentName;
    private int courseId;
    private int examId;
    private Date enrolmentDate;
    private Date enrolmentCloseDate;
    private int exSemesterId;
    private String exSemesterCode;

    private int parentCourseId;
    private String examCourseCode;

    private String examCourseShortName;
    private String examScore;
    private String examResult;
    private double examWeight;
    private int enrolls;
    private Date examDate;

    private int cleExamID;
    private String examName;
    private String clexamName;
    private String clexamGrade;
    private Date clexamEnrollDate;
    private String className;



    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Date getEnrolmentCloseDate() {
        return enrolmentCloseDate;
    }

    public void setEnrolmentCloseDate(Date enrolmentCloseDate) {
        this.enrolmentCloseDate = enrolmentCloseDate;
    }

    public String getExamCourseCode() {
        return examCourseCode;
    }

    public void setExamCourseCode(String examCourseCode) {
        this.examCourseCode = examCourseCode;
    }

    public String getExamCourseShortName() {
        return examCourseShortName;
    }

    public void setExamCourseShortName(String examCourseShortName) {
        this.examCourseShortName = examCourseShortName;
    }


    public int getParentCourseId() {
        return parentCourseId;
    }

    public void setParentCourseId(int parentCourseId) {
        this.parentCourseId = parentCourseId;
    }

    public String getExamScore() {
        return examScore;
    }
    public void setExamScore(String examScore) {
        this.examScore = examScore;
    }
    public String getExamResult() {
        return examResult;
    }
    public void setExamResult(String examResult) {
        this.examResult = examResult;
    }
    public double getExamWeight() {
        return examWeight;
    }
    public void setExamWeight(double examWeight) {
        this.examWeight = examWeight;
    }
    public int getExSemesterId() {
        return exSemesterId;
    }
    public void setExSemesterId(int exSemesterId) {
        this.exSemesterId = exSemesterId;
    }

    public String getExSemesterCode() {
        return exSemesterCode;
    }

    public void setExSemesterCode(String exSemesterCode) {
        this.exSemesterCode = exSemesterCode;
    }

    public int getEnrolls() {
        return enrolls;
    }

    public void setEnrolls(int enrolls) {
        this.enrolls = enrolls;
    }


    public ExamEnrolment(){
        super();
    }

    public ExamEnrolment( int examId ,long studentId) {
        this.studentId = studentId;
      //  this.courseId = courseId;
        this.examId = examId;
    }

    public ExamEnrolment( int examId ,long studentId, int enrolls) {
        this.examId = examId;
        this.studentId = studentId;
        this.enrolls = enrolls;
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

    public Date getEnrolmentDate() {
        return enrolmentDate;
    }

    public void setEnrolmentDate(Date enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
    }


    public int getCleExamID() {
        return cleExamID;
    }
    public void setCleExamID(int cleExamID) {
        this.cleExamID = cleExamID;
    }
    public String getExamName() {
        return examName;
    }
    public void setExamName(String examName) {
        this.examName = examName;
    }
    public String getClexamName() {
        return clexamName;
    }
    public void setClexamName(String clexamName) {
        this.clexamName = clexamName;
    }
    public String getClexamGrade() {
        return clexamGrade;
    }
    public void setClexamGrade(String clexamGrade) {
        this.clexamGrade = clexamGrade;
    }
    public Date getClexamEnrollDate() {
        return clexamEnrollDate;
    }
    public void setClexamEnrollDate(Date clexamEnrollDate) {
        this.clexamEnrollDate = clexamEnrollDate;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }




}
