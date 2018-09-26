package com.cdai.models.testModels;

/**
 * Created by apple on 19/08/2017.
 * formv1
 * >>https://www.tutorialspoint.com/spring/spring_mvc_form_handling_example.htm
 */
public class Student {

    private Integer age;
    private String name;
    private Integer id;

    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getAge() {
        return age;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
}
