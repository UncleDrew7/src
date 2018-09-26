package build.dao.impl;

import build.model.NewsNotifications;
import build.dao.NewsNotificationsDao;

import build.row_mapper.NewsNotificationsRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public class NewsNotificationsDaoImpl extends JdbcDaoSupport implements NewsNotificationsDao{
    @Override
    public String addNewsNotifications(NewsNotifications newsNotifications) {

        String sql = "INSERT INTO news_notifications ( created_by,title,message,attachment_url,start_date,end_date,location_to,location_to_id,visibility,visibility_show_date ) VALUES( ?,?,?,?,?,?,?,?,?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{newsNotifications.getCreatedBy() ,newsNotifications.getTitle(),newsNotifications.getMessage(),newsNotifications.getAttachmentUrl(),newsNotifications.getStartDate(),newsNotifications.getEndDate(),newsNotifications.getLocationTo(),newsNotifications.getLocationToId(),newsNotifications.getVisibility(),newsNotifications.getVisibilityShowDate()}
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String addNews(NewsNotifications newsNotifications) {
        String sql = "INSERT INTO news_notifications(created_by ,title,message ) VALUES(?,?,?)";


        try {

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ newsNotifications.getCreatedBy(),newsNotifications.getTitle(),newsNotifications.getMessage() }
            );
            if(1 == returnValue)
                return "INSERT SUCESS MESSAGE ";
            else
                return "INSERT  FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@NewsNotificationsDaoImpl::@addNews::ERROR!!!");
            e.printStackTrace();
            return "@NewsNotificationsDaoImpl::@addNews::ERROR!!!";
        }

    }

    @Override
    public String editNewsNotification(NewsNotifications newsNotifications) {
        String sql  = "UPDATE news_notifications SET title = ? , message = ? WHERE id = ? ";


        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ newsNotifications.getTitle(),newsNotifications.getMessage(),newsNotifications.getId() }
            );
            if(1 == returnValue)
                return "UPDATE SUCESS MESSAGE ";
            else
                return "UPDATE FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@NewsNotificationsDaoImpl::@editNewsNotification::ERROR!!!");
            e.printStackTrace();
            return "@NewsNotificationsDaoImpl::@editNewsNotification::ERROR!!!";
        }

    }

    @Override
    public String deleteNewsNotification(int notificationId) {
       String sql = "DELETE FROM news_notifications WHERE id = ? ";


        try {

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{notificationId }
            );
            if(1 == returnValue)
                return "DELETE SUCESS MESSAGE ";
            else
                return "DELETE FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@NewsNotificationsDaoImpl::@deleteNewsNotification::ERROR!!!");
            e.printStackTrace();
            return "@NewsNotificationsDaoImpl::@deleteNewsNotification::ERROR!!!";
        }

    }

    @Override
    public NewsNotifications getSingleNewsNotificationByNewsNotificationId(int newsNotificationId) {
        String sql = "SELECT " +
                "n.id, " +
                "n.title, " +
                "n.message " +
                "FROM news_notifications n" +
                " WHERE id =  ?";

        try{

            NewsNotifications object = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{newsNotificationId},
                    new RowMapper<NewsNotifications>() {
                        @Override
                        public NewsNotifications mapRow(ResultSet resultSet, int i) throws SQLException {
                            NewsNotifications newsNotification = new NewsNotifications();
                            newsNotification.setId(resultSet.getInt("id"));
                            newsNotification.setTitle(resultSet.getString("title"));
                            newsNotification.setMessage(resultSet.getString("message"));
                            return  newsNotification;


                        }
                    }
            );
            return object;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@NewsNotificationsDaoImpl::@getSingleNewsNotificationByNewsNotificationId::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@NewsNotificationsDaoImpl::@getSingleNewsNotificationByNewsNotificationId::EMPTY !");
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public List<NewsNotifications> getStudentFocusedNotifications(int limit) {
        String sql = " SELECT " +
                "nn.id," +
                "nn.created_by," +
                "usr.user_name," +
                "nn.created_at," +
                "nn.title " +
                "FROM news_notifications nn, user usr " +
                "WHERE nn.created_by = usr.user_id " +
                "LIMIT ?";
        List<NewsNotifications> studentNotificationList = getJdbcTemplate().query(
                sql,
                new Object[]{limit},
                new NewsNotificationsRowMapper()
        );
        return studentNotificationList;
    }

    @Override
    public List<NewsNotifications> getNewsFeed(int limit) {
        String sql = "SELECT " +
                        "nws.id, " +
                        "nws.created_by," +
                        "usr.user_name," +
                        "nws.title," +
                        "nws.message," +
                        "nws.attachment_url," +
                        "nws.location_to," +
                        "nws.location_to_id," +
                        "nws.visibility," +
                        "nws.visibility_show_date, " +
                        "DATE_FORMAT(nws.created_at, '%D %M %Y %r') created_at" +
                        " FROM user usr,news_notifications nws" +
                        " WHERE nws.created_by  = usr.user_id " +
                        " ORDER BY nws.created_at DESC " +
                        " LIMIT ?";

        List<NewsNotifications> newFeedList = getJdbcTemplate().query(
                sql,
                new Object[]{limit},
                new RowMapper<NewsNotifications>() {
                    @Override
                    public NewsNotifications mapRow(ResultSet resultSet, int i) throws SQLException {
                        NewsNotifications newsNotification = new NewsNotifications();
                        newsNotification.setId(resultSet.getInt("id"));
                        newsNotification.setCreatedBy(resultSet.getLong("created_by"));
                        newsNotification.setCreatedByUserName(resultSet.getString("user_name"));
                        newsNotification.setTitle(resultSet.getString("title"));
                        newsNotification.setMessage(resultSet.getString("message"));
                        newsNotification.setAttachmentUrl(resultSet.getString("attachment_url"));
//                        newsNotification.setStartDate(resultSet.getString("start_date"));
//                        newsNotification.setEndDate(resultSet.getString("end_date"));
                        newsNotification.setLocationTo(resultSet.getString("location_to"));
                        newsNotification.setLocationToId(resultSet.getInt("location_to_id"));
                        newsNotification.setVisibility(resultSet.getString("visibility"));
                        newsNotification.setVisibilityShowDate(resultSet.getString("visibility_show_date"));
                        newsNotification.setCreatedAt(resultSet.getString("created_at"));

                        return  newsNotification;


                    }
                }
        );
        return newFeedList;
    }
}
