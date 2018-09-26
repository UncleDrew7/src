package build.dao.impl;

import build.model.LessonUploads;
import build.dao.LessonUploadsDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public class LessonUploadsDaoImpl extends JdbcDaoSupport implements LessonUploadsDao{
    @Override
    public String addLessonUploads(LessonUploads lessonUploads) {
        String sql = "INSERT INTO lesson_uploads ( lesson_id,course_id,upload_name,upload_url,postion ,file_type) VALUES( ?,?,?,?,?,? )";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ lessonUploads.getLessonId(),lessonUploads.getCourseId(),lessonUploads.getUploadName(),lessonUploads.getUploadUrl(),lessonUploads.getPosition(),lessonUploads.getFileType() }
            );
            if(1 == returnValue)
                return "QUERY SUCESS MESSAGE ";
            else
                return "QUERY FAILURE MESSAGE";
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String editLessonUploadName(int lessonUploadId , String newName) {
        String sql = "UPDATE  lesson_uploads  " +
                "SET upload_name = ?  " +
                "WHERE lesson_upload_id =  ? ";

        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{newName,lessonUploadId}
            );

            return "Change Saved Successfully";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }

    @Override
    public String deleteUploadInfoByLessonUploadId(int lessonUploadId) {
        String sql = "DELETE FROM lesson_uploads  WHERE lesson_upload_id = ?";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{lessonUploadId}
            );
            return "Deleted  Successfully ";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }

    @Override
    public String deleteLessonUploadsByLessonId(int lessonId) {
        String sql = "DELETE FROM lesson_uploads WHERE lesson_id = ? ";
        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{lessonId}
            );
            return "Deleted  Successfully ";

        }catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }

    @Override
    public List<LessonUploads> getLessonUploadsByCourseId(int courseId) {
        String sql= "SELECT " +
                "lu.lesson_upload_id," +
                "lu.lesson_id," +
                "lu.upload_name," +
                "lu.upload_system_name," +
                "lu.upload_url," +
                "lu.file_type," +
                "lu.postion" +
                " FROM lesson_uploads lu" +
                " WHERE lu.visible = 1 " +
                "AND lu.course_id = ?";

        List<LessonUploads> lessonUploadsList = getJdbcTemplate().query(
                sql,
                new Object[]{courseId},
                new RowMapper<LessonUploads>() {
                    @Override
                    public LessonUploads mapRow(ResultSet resultSet, int i) throws SQLException {
                        LessonUploads lessonUpload = new LessonUploads();

                        lessonUpload.setLessonUploadId(resultSet.getInt("lesson_upload_id"));
                        lessonUpload.setLessonId(resultSet.getInt("lesson_id"));
                        lessonUpload.setUploadName(resultSet.getString( "upload_name"));
                        lessonUpload.setUploadSystemName(resultSet.getString( "upload_system_name"));
                        lessonUpload.setUploadUrl(resultSet.getString( "upload_url"));
                        lessonUpload.setFileType(resultSet.getString( "file_type"));
                        lessonUpload.setPosition(resultSet.getInt("postion"));

                        return  lessonUpload;

                    }
                }
        );
        return lessonUploadsList;

    }

    @Override
    public List<LessonUploads> getLessonUploadsByLessonId(int lessonId) {
        String sql= "SELECT " +
                "lu.lesson_upload_id," +
                "lu.lesson_id," +
                "lu.upload_name," +
                "lu.upload_system_name," +
                "lu.upload_url," +
                "lu.file_type," +
                "lu.postion" +
                " FROM lesson_uploads lu" +
                " WHERE lu.visible = 1 " +
                "AND lu.lesson_id = ?";



        try{

            List<LessonUploads> lessonUploadsList = getJdbcTemplate().query(
                    sql,
                    new Object[]{lessonId},
                    new RowMapper<LessonUploads>() {
                        @Override
                        public LessonUploads mapRow(ResultSet resultSet, int i) throws SQLException {
                            LessonUploads lessonUpload = new LessonUploads();

                            lessonUpload.setLessonUploadId(resultSet.getInt("lesson_upload_id"));
                            lessonUpload.setLessonId(resultSet.getInt("lesson_id"));
                            lessonUpload.setUploadName(resultSet.getString( "upload_name"));
                            lessonUpload.setUploadSystemName(resultSet.getString( "upload_system_name"));
                            lessonUpload.setUploadUrl(resultSet.getString( "upload_url"));
                            lessonUpload.setFileType(resultSet.getString( "file_type"));
                            lessonUpload.setPosition(resultSet.getInt("postion"));

                            return  lessonUpload;

                        }
                    }
            );
            return lessonUploadsList;

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
