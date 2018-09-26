package build.model;

/**
 * Created by apple on 15/08/2017.
 */
public class UserProfile {
    private int id;
    private long userId;
    private String profileImageUrl;
    private String chineseFullName;
    private String firstName;
    private String lastName;
    private String otherName;
    private String degreeType;
    private int classId;
    private String className;
    private String gender;
    private String dateOfBirth;
    private String role;
    private String intake;
    private String mobilePhone;
    private String weChatId;
    private String qqId;
    private String homeAddress;
    private String city;
    private String country;
    private String interests;
    private String selfDescription;
    private String primaryLanguage;
    private String LastLoggedIn;
    private String createdAt;
    private String userName;
    private String email;

    private int majorId;
    private String  majorName;
    private String majorShortName;
    private long parentCourseId;
    private String parentCourseName;
    private int childCourseId;
    private String childCourseName ;
    private String enrollmentDate;
    private String examName;
    private int examId;



    public UserProfile() {
        super();
    }

    public UserProfile(long userId, String profileImageUrl, String chineseFullName, String firstName, String lastName, String otherName, String gender, String dateOfBirth, String role, String intake, String mobilePhone, String weChatId, String qqId, String homeAddress, String city, String country, String interests, String selfDescription, String createdAt) {
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.chineseFullName = chineseFullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.intake = intake;
        this.mobilePhone = mobilePhone;
        this.weChatId = weChatId;
        this.qqId = qqId;
        this.homeAddress = homeAddress;
        this.city = city;
        this.country = country;
        this.interests = interests;
        this.selfDescription = selfDescription;
        this.createdAt = createdAt;
    }


    public UserProfile(long userId, String firstName, String lastName, String city, String country) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
    }

    //changeUserProfileSettings
    public UserProfile(long userId, String degreeType, String primaryLanguage) {
        this.userId = userId;
        this.degreeType = degreeType;
        this.primaryLanguage = primaryLanguage;
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

    public String getPrimaryLanguage() {
        return primaryLanguage;
    }


    public void setPrimaryLanguage(String primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getChineseFullName() {
        return chineseFullName;
    }

    public void setChineseFullName(String chineseFullName) {
        this.chineseFullName = chineseFullName;
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

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getSelfDescription() {
        return selfDescription;
    }

    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastLoggedIn() {
        return LastLoggedIn;
    }

    public void setLastLoggedIn(String lastLoggedIn) {
        LastLoggedIn = lastLoggedIn;
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

    public long getParentCourseId() {
        return parentCourseId;
    }

    public void setParentCourseId(long parentCourseId) {
        this.parentCourseId = parentCourseId;
    }

    public String getParentCourseName() {
        return parentCourseName;
    }

    public void setParentCourseName(String parentCourseName) {
        this.parentCourseName = parentCourseName;
    }

    public int getChildCourseId() {
        return childCourseId;
    }

    public void setChildCourseId(int childCourseId) {
        this.childCourseId = childCourseId;
    }

    public String getChildCourseName() {
        return childCourseName;
    }

    public void setChildCourseName(String childCourseName) {
        this.childCourseName = childCourseName;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }
}
