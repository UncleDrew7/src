package build.row_mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import build.model.NewsNotifications;
/**
 * Created by apple on 14/09/2017.
 */
public class NewsNotificationsRowMapper implements  RowMapper<NewsNotifications> {
    @Override
    public NewsNotifications mapRow(ResultSet resultSet, int i) throws SQLException {

        NewsNotifications newsNotification = new NewsNotifications();
        newsNotification.setId(resultSet.getInt("id"));
        newsNotification.setCreatedBy(resultSet.getInt("created_by"));
        newsNotification.setCreatedByUserName(resultSet.getString( "user_name"));
        newsNotification.setCreatedAt(resultSet.getString( "created_at"));
        newsNotification.setTitle(resultSet.getString( "title"));
        return newsNotification;
    }
}
