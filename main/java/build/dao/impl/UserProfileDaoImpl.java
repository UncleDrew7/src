package build.dao.impl;


import build.model.StudentDegreeTypeChangeRequest;
import build.model.UserProfile;
import build.dao.UserProfileDao;
import build.row_mapper.UserProfileRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
/**
 * Created by apple on 15/08/2017.
 */
public class UserProfileDaoImpl extends JdbcDaoSupport implements UserProfileDao{
    @Override
    public String createNewUserProfile(UserProfile userProfile) {
        String sql = "INSERT INTO user_profile (user_id,profile_image_url,chinese_fullname,first_name,last_name,other_name,gender,data_of_birth,intake,mobile_phone,wechat_id,qq_id,home_address,city,country,interests,self_description,role)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{
                       userProfile.getUserId(),
                        userProfile.getProfileImageUrl(),
                        userProfile.getChineseFullName(),
                        userProfile.getFirstName(),
                        userProfile.getLastName(),
                        userProfile.getOtherName(),
                        userProfile.getGender(),
                        userProfile.getDateOfBirth(),
                        userProfile.getIntake(),
                        userProfile.getMobilePhone(),
                        userProfile.getWeChatId(),
                        userProfile.getQqId(),
                        userProfile.getHomeAddress(),
                        userProfile.getCity(),
                        userProfile.getCountry(),
                        userProfile.getInterests(),
                        userProfile.getSelfDescription(),
                        userProfile.getRole()
                }
        );
        if(1 == returnValue)
            return "# New User Profile>>> "+userProfile.getUserId()+" CREATED SUCCESSFULLY ";
        else
            return "# CREATION FAILURE";
    }

    @Override
    public String createNewStudentProfile(UserProfile userProfile) {
        String sql = "INSERT INTO user_profile (user_id,profile_image_url,chinese_fullname,first_name,last_name,other_name,gender,data_of_birth,intake,mobile_phone,wechat_id,qq_id,home_address,city,country,interests,self_description,role)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{
                        userProfile.getUserId(),
                        userProfile.getProfileImageUrl(),
                        userProfile.getChineseFullName(),
                        userProfile.getFirstName(),
                        userProfile.getLastName(),
                        userProfile.getOtherName(),
                        userProfile.getGender(),
                        userProfile.getDateOfBirth(),
                        userProfile.getIntake(),
                        userProfile.getMobilePhone(),
                        userProfile.getWeChatId(),
                        userProfile.getQqId(),
                        userProfile.getHomeAddress(),
                        userProfile.getCity(),
                        userProfile.getCountry(),
                        userProfile.getInterests(),
                        userProfile.getSelfDescription(),
                        userProfile.getRole(),
                      //  userProfile.getDegreeType()
                }
        );
        if(1 == returnValue)
            return "# New User Profile>>> "+userProfile.getUserId()+" CREATED SUCCESSFULLY ";
        else
            return "# CREATION FAILURE";
    }

    @Override
    public String updateUserProfile(UserProfile userProfile) {
        return null;
    }

    @Override
    public String deleteUserProfile(UserProfile userProfile) {
        return null;
    }

    @Override
    public UserProfile displayUserProfileDetails(long userId) {
        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_name, " +
                "m.major_short_code, " +
                "up.user_id,  " +
                "up.profile_image_url,  " +
                "up.first_name,  " +
                "up.last_name,  " +
                "c.class_id,  " +
                "c.class_name,  " +
                "up.intake,  " +
                "up.gender,  " +
                "up.country,  " +
                "up.role,  " +
                "up.degree_type,  " +
                "up.self_description  " +
                "FROM user_profile up  " +
                "LEFT JOIN user u ON u.user_id = up.user_id   " +
                "LEFT JOIN class c ON c.class_id = u.class_id   " +
             //   "LEFT JOIN student_major sm ON sm.student_id = up.user_id " +
                "LEFT JOIN major m ON m.major_id = c.major_id " +
                "WHERE up.user_id =  ? ";


        try{
            UserProfile userProfileDetails = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    new RowMapper<UserProfile>() {
                        @Override
                        public UserProfile mapRow(ResultSet resultSet, int i) throws SQLException {
                            UserProfile userProfile = new UserProfile();

                            userProfile.setUserId(resultSet.getLong("user_id"));
                            userProfile.setProfileImageUrl(resultSet.getString("profile_image_url"));
                            userProfile.setFirstName(resultSet.getString("first_name"));
                            userProfile.setLastName(resultSet.getString("last_name"));
                            userProfile.setClassId(resultSet.getInt("class_id"));
                            userProfile.setClassName(resultSet.getString("class_name"));
                            userProfile.setGender(resultSet.getString("gender"));
                            userProfile.setIntake(resultSet.getString("intake"));
                            userProfile.setCountry(resultSet.getString("country"));
                            userProfile.setSelfDescription(resultSet.getString("self_description"));
                            userProfile.setRole(resultSet.getString("role"));
                            userProfile.setDegreeType(resultSet.getString("degree_type"));
                            userProfile.setMajorId(resultSet.getInt("major_id"));
                            userProfile.setMajorName(resultSet.getString("major_name"));
                            userProfile.setMajorShortName(resultSet.getString("major_short_code"));

                            return userProfile;
                        }
                    }

            );
            return userProfileDetails;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String changeUserProfilePic(long userId, String imgUrl) {
        String sql = "UPDATE user_profile SET profile_image_url = ? WHERE user_id = ? ";

        try{
           int returnValue = getJdbcTemplate().update(
                   sql,
                   new Object[]{imgUrl,userId}
           );
           System.out.println("IMAGE PROFILE CHANGED FOR "+ userId);
          return "Profile Image change Successful";

       }catch (Exception e){
           e.printStackTrace();
           return "ERROR";
       }
    }

    @Override
    public String getCurrentProfilePicUrl(long userId) {
        String sql = "SELECT profile_image_url FROM user_profile WHERE user_id = ?";

        try{
            String imageUrl = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    String.class
            );
            return imageUrl;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return "400";
        }
        catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }



}
