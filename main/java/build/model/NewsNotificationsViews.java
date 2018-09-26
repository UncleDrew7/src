package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class NewsNotificationsViews {

    private int id;
    private int newsNotificationId;
    private long userId;
    private int viewed;
    private Date viewedDatetime;

    public NewsNotificationsViews(){
        super();
    }

    public NewsNotificationsViews(int newsNotificationId, long userId, int viewed) {
        this.newsNotificationId = newsNotificationId;
        this.userId = userId;
        this.viewed = viewed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsNotificationId() {
        return newsNotificationId;
    }

    public void setNewsNotificationId(int newsNotificationId) {
        this.newsNotificationId = newsNotificationId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    public Date getViewedDatetime() {
        return viewedDatetime;
    }

    public void setViewedDatetime(Date viewedDatetime) {
        this.viewedDatetime = viewedDatetime;
    }
}
