package build.dao;
import build.model.NewsNotifications;

import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public interface NewsNotificationsDao {


    public String addNewsNotifications(NewsNotifications newsNotifications);

    //ADMIN ADD NEWS
    public String addNews(NewsNotifications newsNotifications);

    //ADMIN EDIT NEWS
    public String editNewsNotification(NewsNotifications newsNotifications);

    //ADMIN DELETE NEWS
    public String deleteNewsNotification(int notificationId);

    //ADMIN GET SINGLE NOTIFICATION DATA
    public NewsNotifications getSingleNewsNotificationByNewsNotificationId(int newsNotificationId);

    //Student Home Page
    public List<NewsNotifications> getStudentFocusedNotifications(int limit);

    //STUDENT ACTIVITY NEWS FEED
    public List<NewsNotifications> getNewsFeed(int limit);



}
