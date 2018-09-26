package build.dao.impl;
import build.model.UserSettings;
import build.dao.UserSettingsDao;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 30/08/2017.
 */
public class UserSettingsDaoImpl extends JdbcDaoSupport implements UserSettingsDao{
    @Override
    public String addUserSettings(UserSettings userSettings) {
        String sql = "INSERT INTO user_settings ( userid,hide_email,preferred_language) VALUES( ? ,?,?)";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{userSettings.getUserId() ,userSettings.getHideEmail(),userSettings.getPreferredLanguage() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }
}
