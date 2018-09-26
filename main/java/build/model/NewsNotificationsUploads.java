package build.model;

/**
 * Created by apple on 26/11/2017.
 */
public class NewsNotificationsUploads {
    private int newsNotificationUploadId ;
    private int newsNotificationId;
    private String uploadName ;
    private String  uploadSystemName;
    private String  uploadUrl;
    private String  fileType;
    private String  visible;
    private String  createdAt;
    private String  updatedAt;

    public NewsNotificationsUploads() {
        super();
    }

    public NewsNotificationsUploads(int newsNotificationId, String uploadName, String uploadSystemName, String uploadUrl, String fileType) {
        this.newsNotificationId = newsNotificationId;
        this.uploadName = uploadName;
        this.uploadSystemName = uploadSystemName;
        this.uploadUrl = uploadUrl;
        this.fileType = fileType;
    }

    public int getNewsNotificationUploadId() {
        return newsNotificationUploadId;
    }

    public void setNewsNotificationUploadId(int newsNotificationUploadId) {
        this.newsNotificationUploadId = newsNotificationUploadId;
    }

    public int getNewsNotificationId() {
        return newsNotificationId;
    }

    public void setNewsNotificationId(int newsNotificationId) {
        this.newsNotificationId = newsNotificationId;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public String getUploadSystemName() {
        return uploadSystemName;
    }

    public void setUploadSystemName(String uploadSystemName) {
        this.uploadSystemName = uploadSystemName;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
