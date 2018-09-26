package build.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import build.model.UserProfile;
/**
 * Created by apple on 15/08/2017.
 */
public class UserProfileRowMapper implements RowMapper<UserProfile>{

    public UserProfile mapRow(ResultSet resultSet, int rowNumber)throws SQLException{

        UserProfile userProfile = new UserProfile();

        userProfile.setId(resultSet.getInt("id"));
        userProfile.setUserId(resultSet.getInt("user_id"));
        userProfile.setProfileImageUrl(resultSet.getString("profile_image_url"));
        userProfile.setChineseFullName(resultSet.getString("chinese_fullname"));
        userProfile.setFirstName(resultSet.getString("first_name"));
        userProfile.setLastName(resultSet.getString("last_name"));
        userProfile.setOtherName(resultSet.getString("other_name"));
        userProfile.setGender(resultSet.getString("gender"));
        userProfile.setDateOfBirth(resultSet.getString("data_of_birth"));
        userProfile.setIntake(resultSet.getString("intake"));
        userProfile.setMobilePhone(resultSet.getString("mobile_phone"));
        userProfile.setWeChatId(resultSet.getString("wechat_id"));
        userProfile.setQqId(resultSet.getString("qq_id"));
        userProfile.setHomeAddress(resultSet.getString("home_address"));
        userProfile.setCity(resultSet.getString("city"));
        userProfile.setCountry(resultSet.getString("country"));
        userProfile.setInterests(resultSet.getString("interests"));
        userProfile.setSelfDescription(resultSet.getString("self_description"));
        userProfile.setRole(resultSet.getString("role"));
        userProfile.setCreatedAt(resultSet.getString("created_at"));




        return userProfile;
    }

}
