package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class UserSettings{

    private int id;
    private long userId;
    private int hideEmail;
    private String preferredLanguage;
    private Date createdAt;
    private Date updatedAt;

    public UserSettings(){
        super();
    }
    public UserSettings(long userId, int hideEmail, String preferredLanguage) {
        this.userId = userId;
        this.hideEmail = hideEmail;
        this.preferredLanguage = preferredLanguage;
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

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getHideEmail() {
        return hideEmail;
    }

    public void setHideEmail(int hideEmail) {
        this.hideEmail = hideEmail;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
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
