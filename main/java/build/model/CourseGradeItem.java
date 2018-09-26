package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class CourseGradeItem {

    private int id;
    private String  gradeItemId;
    private String  courseId;
    private Date createdAt;

    public CourseGradeItem(){
        super();
    }


    public CourseGradeItem(String gradeItemId, String courseId) {
        this.gradeItemId = gradeItemId;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGradeItemId() {
        return gradeItemId;
    }

    public void setGradeItemId(String gradeItemId) {
        this.gradeItemId = gradeItemId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
