package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class LessonUploads {

    private int lessonUploadId;
    private int lessonId;
    private int courseId;
    private String uploadName ;
    private String uploadSystemName;
    private String uploadUrl;
    private String fileType;
    private int position;
    private int visible;
    private Date createdAt;
    private Date updatedAt;

    public LessonUploads(){
        super();
    }

    public LessonUploads(int lessonId, int courseId, String uploadName, String uploadUrl, String fileType, int position) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.uploadName = uploadName;
        this.uploadUrl = uploadUrl;
        this.fileType = fileType;
        this.position = position;
    }



    public int getLessonUploadId() {
        return lessonUploadId;
    }

    public void setLessonUploadId(int lessonUploadId) {
        this.lessonUploadId = lessonUploadId;
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

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
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

    public String getUploadSystemName() {
        return uploadSystemName;
    }

    public void setUploadSystemName(String uploadSystemName) {
        this.uploadSystemName = uploadSystemName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
