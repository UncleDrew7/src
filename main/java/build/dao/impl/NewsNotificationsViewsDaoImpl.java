package build.dao.impl;

import build.model.NewsNotificationsViews;
import build.dao.NewsNotificationsViewsDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 30/08/2017.
 */
public class NewsNotificationsViewsDaoImpl extends JdbcDaoSupport implements NewsNotificationsViewsDao{
    @Override
    public String addNewsNotificationsViews(NewsNotificationsViews newsNotificationsViews) {
        String sql = "INSERT INTO news_notifications_views (news_notification_id ,user_id,viewed ) VALUES( ?,?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{newsNotificationsViews.getNewsNotificationId(),newsNotificationsViews.getUserId(),newsNotificationsViews.getViewed() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }
}
