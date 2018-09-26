package com.cdai.models.tempModels;

import build.model.Courses;

import java.util.List;

/**
 * Created by apple on 18/08/2017.
 */
public class ExcelStudent {

    private long userId;
    private String role;
    private String firstName;
    private String lastName;
    private String fullName;
    private String gender;
    private String intake;
    private String degreeType;
    private String studentClass;
    private String email;
    private String city;
    private String country;
    private int grade;
    private String finalAll;
    private String highestGrade;

    private String courseName;
    private List courseList;
    private List studentList;

    private String semester;
    private String courseCode;
    private String courseNameCN;


    public ExcelStudent() {
        super();
    }

    public ExcelStudent(long userId, String fullName,  String semester, String courseCode, String courseName,  String courseNameCN, String finalAll) {
        this.userId = userId;
        this.fullName = fullName;
        this.semester = semester;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseNameCN = courseNameCN;
        this.finalAll = finalAll;
    }

    public ExcelStudent(long userId, String role, String firstName, String lastName, String gender, String intake, String studentClass, String email, String city, String country) {
        this.userId = userId;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.intake = intake;
        this.studentClass = studentClass;
        this.email = email;
        this.city = city;
        this.country = country;
    }

    public ExcelStudent(long userId, String firstName, String lastName, String gender, String degreeType, String email, String city, String country) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.degreeType = degreeType;
        this.email = email;
        this.city = city;
        this.country = country;
    }

    public ExcelStudent(long userId, String fullName) {
        this.userId = userId;
        this.fullName = fullName;
    }

    public ExcelStudent(long userId, String fullName, int grade) {
        this.userId = userId;
        this.fullName = fullName;
        this.grade = grade;
    }

    public ExcelStudent(long userId, String fullName, String courseName ,String finalAll, String highestGrade) {
        this.userId = userId;
        this.fullName = fullName;
        this.courseName = courseName;
        this.finalAll = finalAll;
        this.highestGrade = highestGrade;
    }


    public ExcelStudent(List courseList, List studentList) {
        this.courseList = courseList;
        this.studentList = studentList;
    }

    public ExcelStudent(String courseName,String semester) {
        this.courseName = courseName;
        this.semester = semester;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List getCourseList() {
        return courseList;
    }

    public void setCourseList(List courseList) {
        this.courseList = courseList;
    }

    public List getStudentList() {
        return studentList;
    }

    public void setStudentList(List studentList) {
        this.studentList = studentList;
    }

    public String getFinalAll() {
        return finalAll;
    }
    public void setFinalAll(String finalAll) {
        this.finalAll = finalAll;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getHighestGrade() {
        return highestGrade;
    }

    public void setHighestGrade(String highestGrade) {
        this.highestGrade = highestGrade;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseNameCN() {
        return courseNameCN;
    }

    public void setCourseNameCN(String courseNameCN) {
        this.courseNameCN = courseNameCN;
    }
}
