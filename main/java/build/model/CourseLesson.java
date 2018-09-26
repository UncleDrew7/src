package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class CourseLesson {

    private int lessonId;
    private int courseId;
    private String  lessonName;
    private String  description;
    private int position;
    private long createdBy;
    private Date createdAt;
    private Date  updatedAt;


    public CourseLesson(){
        super();
    }


    public CourseLesson(int courseId, String lessonName, String description, int position, long createdBy) {
        this.courseId = courseId;
        this.lessonName = lessonName;
        this.description = description;
        this.position = position;
        this.createdBy = createdBy;
    }

    public CourseLesson(int courseId, String lessonName, String description, long createdBy) {
        this.courseId = courseId;
        this.lessonName = lessonName;
        this.description = description;
        this.createdBy = createdBy;
    }

    public CourseLesson(int lessonId, String lessonName, String description) {
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.description = description;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
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
}
