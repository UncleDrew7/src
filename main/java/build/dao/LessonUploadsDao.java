package build.dao;
import build.model.LessonUploads;

import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public interface LessonUploadsDao {

    public String addLessonUploads(LessonUploads lessonUploads);

    // EDIT UPLOAD NAME
    public String editLessonUploadName(int lessonUploadId , String newName);

    // DELETE UPLOAD DATABASE REPRESENTATION
    public String deleteUploadInfoByLessonUploadId(int lessonUploadId);

    public String deleteLessonUploadsByLessonId(int lessonId);

    //COURSE DETAIL PAGE
    public List<LessonUploads> getLessonUploadsByCourseId(int courseId);

    public List<LessonUploads> getLessonUploadsByLessonId(int lessonId);
}
