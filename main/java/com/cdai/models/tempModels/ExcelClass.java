package com.cdai.models.tempModels;

/**
 * Created by apple on 18/09/2017.
 */
public class ExcelClass {
    private String className;
    private String intakeSemester;
    //user first should choose intake period before uploading this class excel


    public ExcelClass() {
        super();
    }

    public ExcelClass(String className, String intakeSemester) {
        this.className = className;
        this.intakeSemester = intakeSemester;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIntakeSemester() {
        return intakeSemester;
    }

    public void setIntakeSemester(String intakeSemester) {
        this.intakeSemester = intakeSemester;
    }
}
