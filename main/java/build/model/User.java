package build.model;

/**
 * Created by apple on 15/08/2017.
 */
public class User {

    private int id;
    private long userId;
    private int roleId;
    private String roleName;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String salt ;

    private String gender;

    private int classId;
    private String className;
    private String enrolmentDate;
    private String submissionDate;
    private String degreeType;
    private double  courseGradeAverage ;
    private String customGrade;

    private String customClassName;
    private int majorId;
    private String majorShortCode;
    private String majorName;
    private int intake;
    private String lastLogin;

    private String currentEnrolledExamScore;

    public User() {
        super();
    }

    public User(long userId, int roleId, String userName, String email, String password, String salt) {
        this.userId = userId;
        this.roleId = roleId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    public User(long userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    public User(long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public int getMajorId() {
        return majorId;
    }
    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }
    public int getIntake() {
        return intake;
    }
    public void setIntake(int intake) {
        this.majorId = intake;
    }
    public String getCustomClassName() {
        return customClassName;
    }
    public void setCustomClassName(String customClassName) {
        this.customClassName = customClassName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getEnrolmentDate() {
        return enrolmentDate;
    }

    public void setEnrolmentDate(String enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public double getCourseGradeAverage() {
        return courseGradeAverage;
    }

    public void setCourseGradeAverage(double courseGradeAverage) {
        this.courseGradeAverage = courseGradeAverage;
    }

    public String getCustomGrade() {
        return customGrade;
    }

    public void setCustomGrade(String customGrade) {
        this.customGrade = customGrade;
    }

    public String getCurrentEnrolledExamScore() {
        return currentEnrolledExamScore;
    }

    public void setCurrentEnrolledExamScore(String currentEnrolledExamScore) {
        this.currentEnrolledExamScore = currentEnrolledExamScore;
    }

    public String getMajorShortCode() {
        return majorShortCode;
    }

    public void setMajorShortCode(String majorShortCode) {
        this.majorShortCode = majorShortCode;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
