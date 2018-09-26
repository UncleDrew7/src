package build.dao;

import build.model.NewsNotificationsUploads;
import java.util.List;

/**
 * Created by apple on 26/11/2017.
 */
public  interface NewsNotificationsUploadsDao {

    public String addNewUploadInfo(NewsNotificationsUploads upload);

    public String updateUploadName(int newsNotificationUploadId,String newName);

    public String deleteSingleUploadInfo(int newsNotificationUploadId);

    public List<NewsNotificationsUploads> getNewsNotificationUploads(int newsNotificationId);

    public List<NewsNotificationsUploads> getAllNewsNotificationUploads();
}
