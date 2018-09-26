package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class GradeItems {

    private int gradeItemId;
    private Long parentCourseId;
    private int childCourseId;
    private int courseId;
    private String courseShortName;
    private String courseName;
    private String courseDescription;
    private String gradeItemType;
    private String gradeItemName;
    private double weight;
    private int minGrade;
    private int maxGrade;
    private float grade;
    private double percentage;
    private double gradeItemsOverallTotal;
    private String letter;
    private int gradeItemPosition;
    private long createdBy;
    private String createdByUserName;
    private String dateOfExam;
    private String  enrolmentStartDate;
    private String  enrolmentCloseDate;
    private int activeStatus;
    private String participants;
    private int totalEnrollments;
    private int totalEnrollmentRequests;
    private int enrollmentStatus;
    private String submissionDate;
    private String  enrollmentDate;
    private int semesterId;
    private Date createdAt;
    private Date updatedAt;

    private int majorId;
    private String majorName;
    private String majorShortName;
    private double credits ;
    private int minPassScore;
    private String semesterCode;
    private Long teacherId;
    private String teacherName;
    private String examEndDate;



    public GradeItems(){
        super();
    }

    public GradeItems(int courseId, String gradeItemType, String gradeItemName, double weight, int minGrade, int maxGrade, long createdBy) {
        this.courseId = courseId;
        this.gradeItemType = gradeItemType;
        this.gradeItemName = gradeItemName;
        this.weight = weight;
        this.minGrade = minGrade;
        this.maxGrade = maxGrade;
        this.createdBy = createdBy;
    }

    public void setGradeItemExam(int courseId,String gradeItemName,long createdBy, String dateOfExam, String enrolmentStartDate, String enrolmentCloseDate) {
        this.courseId = courseId;
        this.gradeItemType = "Exam";
        this.gradeItemName = gradeItemName;
        this.weight = 1.0;
        this.minGrade = 0;
        this.maxGrade = 100;
        this.gradeItemPosition = 1;
        this.createdBy = createdBy;
        this.dateOfExam = dateOfExam;
        this.enrolmentStartDate = enrolmentStartDate;
        this.enrolmentCloseDate = enrolmentCloseDate;
    }

    public GradeItems(int gradeItemId, int courseId, String gradeItemType, String gradeItemName, double weight, int minGrade, int maxGrade, long createdBy, String dateOfExam, String enrolmentStartDate, String enrolmentCloseDate) {
        this.gradeItemId = gradeItemId;
        this.courseId = courseId;
        this.gradeItemType = gradeItemType;
        this.gradeItemName = gradeItemName;
        this.weight = weight;
        this.minGrade = minGrade;
        this.maxGrade = maxGrade;
        this.createdBy = createdBy;
        this.dateOfExam = dateOfExam;
        this.enrolmentStartDate = enrolmentStartDate;
        this.enrolmentCloseDate = enrolmentCloseDate;
    }

    //UPDATE EXAMS CONSTRUCTOR

    public GradeItems(int gradeItemId, String dateOfExam, String enrolmentStartDate, String enrolmentCloseDate) {
        this.gradeItemId = gradeItemId;
        this.dateOfExam = dateOfExam;
        this.enrolmentStartDate = enrolmentStartDate;
        this.enrolmentCloseDate = enrolmentCloseDate;
    }

    public int getGradeItemId() {
        return gradeItemId;
    }

    public Long getParentCourseId() {
        return parentCourseId;
    }

    public void setParentCourseId(Long parentCourseId) {
        this.parentCourseId = parentCourseId;
    }

    public int getChildCourseId() {
        return childCourseId;
    }

    public void setChildCourseId(int childCourseId) {
        this.childCourseId = childCourseId;
    }

    public void setGradeItemId(int gradeItemId) {
        this.gradeItemId = gradeItemId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getGradeItemType() {
        return gradeItemType;
    }

    public void setGradeItemType(String gradeItemType) {
        this.gradeItemType = gradeItemType;
    }

    public String getGradeItemName() {
        return gradeItemName;
    }

    public void setGradeItemName(String gradeItemName) {
        this.gradeItemName = gradeItemName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(int minGrade) {
        this.minGrade = minGrade;
    }

    public int getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    public int getGradeItemPosition() {
        return gradeItemPosition;
    }

    public String getCourseShortName() {
        return courseShortName;
    }

    public void setCourseShortName(String courseShortName) {
        this.courseShortName = courseShortName;
    }

    public void setGradeItemPosition(int gradeItemPosition) {
        this.gradeItemPosition = gradeItemPosition;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public String getDateOfExam() {
        return dateOfExam;
    }

    public void setDateOfExam(String dateOfExam) {
        this.dateOfExam = dateOfExam;
    }

    public String getEnrolmentStartDate() {
        return enrolmentStartDate;
    }

    public void setEnrolmentStartDate(String enrolmentStartDate) {
        this.enrolmentStartDate = enrolmentStartDate;
    }

    public String getEnrolmentCloseDate() {
        return enrolmentCloseDate;
    }

    public void setEnrolmentCloseDate(String enrolmentCloseDate) {
        this.enrolmentCloseDate = enrolmentCloseDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(int activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public double getGradeItemsOverallTotal() {
        return gradeItemsOverallTotal;
    }

    public void setGradeItemsOverallTotal(double gradeItemsOverallTotal) {
        this.gradeItemsOverallTotal = gradeItemsOverallTotal;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public int getTotalEnrollments() {
        return totalEnrollments;
    }

    public void setTotalEnrollments(int totalEnrollments) {
        this.totalEnrollments = totalEnrollments;
    }

    public int getTotalEnrollmentRequests() {
        return totalEnrollmentRequests;
    }

    public void setTotalEnrollmentRequests(int totalEnrollmentRequests) {
        this.totalEnrollmentRequests = totalEnrollmentRequests;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public int getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(int enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
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

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public int getMinPassScore() {
        return minPassScore;
    }

    public void setMinPassScore(int minPassScore) {
        this.minPassScore = minPassScore;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getExamEndDate() {
        return examEndDate;
    }

    public void setExamEndDate(String examEndDate) {
        this.examEndDate = examEndDate;
    }
}
