package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class NewsNotifications {

    private int id;
    private long createdBy;
    private String createdByUserName;
    private String title;
    private String message;
    private String attachmentUrl;
    private String startDate;
    private String endDate;
    private String locationTo;
    private int locationToId;
    private String visibility;
    private String visibilityShowDate;
    private String createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public NewsNotifications(){
        super();
    }


    public NewsNotifications(long createdBy, String title, String message, String attachmentUrl, String startDate, String endDate, String locationTo, int locationToId, String visibility, String visibilityShowDate) {
        this.createdBy = createdBy;
        this.title = title;
        this.message = message;
        this.attachmentUrl = attachmentUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locationTo = locationTo;
        this.locationToId = locationToId;
        this.visibility = visibility;
        this.visibilityShowDate = visibilityShowDate;
    }

//EDITE NEWS
public NewsNotifications(int id, String title, String message) {
    this.id = id;
    this.title = title;
    this.message = message;
}



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }

    public int getLocationToId() {
        return locationToId;
    }

    public void setLocationToId(int locationToId) {
        this.locationToId = locationToId;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getVisibilityShowDate() {
        return visibilityShowDate;
    }

    public void setVisibilityShowDate(String visibilityShowDate) {
        this.visibilityShowDate = visibilityShowDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String  createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
    this.createdByUserName = createdByUserName;
}
}
