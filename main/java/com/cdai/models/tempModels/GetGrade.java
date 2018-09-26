package com.cdai.models.tempModels;

import java.io.Serializable;

/**
 * Created by apple on 11/09/2017.
 */
public class GetGrade implements Serializable {
    private Integer studentId;
    private String studentName;
    private String studentClass;
    private int grade;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
