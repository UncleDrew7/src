package build.dao.impl;

import build.model.LessonUploads;
import build.model.NewsNotificationsUploads;
import build.dao.NewsNotificationsUploadsDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 26/11/2017.
 */
public class NewsNotificationsUploadsDaoImpl extends JdbcDaoSupport implements NewsNotificationsUploadsDao{
    @Override
    public String addNewUploadInfo(NewsNotificationsUploads upload) {

        try {

            String sql = "INSERT INTO news_notifications_uploads( news_notification_id,upload_name,upload_system_name,upload_url,file_type) VALUES( ?,?,?,?,? )";
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{upload.getNewsNotificationId(),upload.getUploadName(),upload.getUploadSystemName(),upload.getUploadUrl(),upload.getFileType() }
            );
            System.out.println("@addNewUploadInfo::UPLOAD INFO ADDED SUCCESSFULLY");
            return "Uploaded Successfully";

        }catch (Exception e){
            System.out.println("@addNewUploadInfo::ERROR!!!");
            e.printStackTrace();
            return "UPLOADING ERROR!";
        }
    }

    @Override
    public String updateUploadName(int newsNotificationUploadId, String newName) {

        try {

            String sql = "UPDATE  news_notifications_uploads " +
                    "SET upload_name =  ? " +
                    "WHERE news_notification_upload_id =  ?";

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{newName,newsNotificationUploadId}
            );
            return "Change Saved Successfully";

        }catch (Exception e){
            System.out.println("ERROR!!!");
            e.printStackTrace();
            return "ERROR";
        }
    }

    @Override
    public String deleteSingleUploadInfo(int newsNotificationUploadId) {

        try {

            String sql = "DELETE FROM news_notifications_uploads WHERE news_notification_upload_id  = ? ";
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{newsNotificationUploadId}
            );
            System.out.println("::DELETED SUCCESSFULLY");
            return "Deleted  Successfully ";

        }catch (Exception e){
            System.out.println("ERROR!!!");
            e.printStackTrace();
            return "ERROR!!!";
        }
    }

    @Override
    public List<NewsNotificationsUploads> getNewsNotificationUploads(int newsNotificationId) {
        String sql= "SELECT  " +
                "nnu.news_notification_upload_id, " +
                "nnu.news_notification_id, " +
                "nnu.upload_name, " +
                "nnu.upload_system_name, " +
                "nnu.upload_url, " +
                "nnu.file_type  " +
                "FROM news_notifications_uploads nnu  " +
                "WHERE nnu.visible = 1 " +
                "AND nnu.news_notification_id = ?" ;



        try{

            List<NewsNotificationsUploads> newsNotificationsUploadsList = getJdbcTemplate().query(
                    sql,
                    new Object[]{newsNotificationId},
                    new RowMapper<NewsNotificationsUploads>() {
                        @Override
                        public NewsNotificationsUploads mapRow(ResultSet resultSet, int i) throws SQLException {
                            NewsNotificationsUploads upload = new NewsNotificationsUploads();

                            upload.setNewsNotificationUploadId(resultSet.getInt("news_notification_upload_id"));
                            upload.setNewsNotificationId(resultSet.getInt("news_notification_id"));
                            upload.setUploadName(resultSet.getString("upload_name"));
                            upload.setUploadSystemName(resultSet.getString("upload_system_name"));
                            upload.setUploadUrl(resultSet.getString("upload_url"));
                            upload.setFileType(resultSet.getString("file_type"));

                            return  upload;
                        }
                    }
            );
            return newsNotificationsUploadsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<NewsNotificationsUploads> getAllNewsNotificationUploads() {
        String sql= "SELECT  " +
                "nnu.news_notification_upload_id, " +
                "nnu.news_notification_id, " +
                "nnu.upload_name, " +
                "nnu.upload_system_name, " +
                "nnu.upload_url, " +
                "nnu.file_type  " +
                "FROM news_notifications_uploads nnu  " +
                "WHERE nnu.visible = 1 " ;



        try{

            List<NewsNotificationsUploads> newsNotificationsUploadsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<NewsNotificationsUploads>() {
                        @Override
                        public NewsNotificationsUploads mapRow(ResultSet resultSet, int i) throws SQLException {
                            NewsNotificationsUploads upload = new NewsNotificationsUploads();

                            upload.setNewsNotificationUploadId(resultSet.getInt("news_notification_upload_id"));
                            upload.setNewsNotificationId(resultSet.getInt("news_notification_id"));
                            upload.setUploadName(resultSet.getString("upload_name"));
                            upload.setUploadSystemName(resultSet.getString("upload_system_name"));
                            upload.setUploadUrl(resultSet.getString("upload_url"));
                            upload.setFileType(resultSet.getString("file_type"));

                            return  upload;
                        }
                    }
            );
            return newsNotificationsUploadsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
