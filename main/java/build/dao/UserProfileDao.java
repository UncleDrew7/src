package build.dao;

import build.model.StudentDegreeTypeChangeRequest;
import build.model.UserProfile;

import java.util.List;

/**
 * Created by apple on 15/08/2017.
 */
public interface UserProfileDao {

    public String createNewUserProfile(UserProfile userProfile);

    public String createNewStudentProfile(UserProfile userProfile);

    public String updateUserProfile(UserProfile userProfile);

    public String deleteUserProfile(UserProfile userProfile);

    public UserProfile displayUserProfileDetails(long userId);

    public String changeUserProfilePic(long userId, String imgUrl);

    public String  getCurrentProfilePicUrl(long userId);





}
