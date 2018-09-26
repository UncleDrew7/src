package build.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import build.model.LogAccess;
/**
 * Created by apple on 15/08/2017.
 */

public class LogAccessRowMapper implements RowMapper<LogAccess> {

    public LogAccess mapRow(ResultSet resultSet, int rowNumber)throws SQLException{

        LogAccess logAccess = new LogAccess();

        logAccess.setId(resultSet.getInt("id"));
        logAccess.setUser_id(resultSet.getInt("user_id"));
        logAccess.setLoginDatetime(resultSet.getString("login_datetime"));
        logAccess.setLogoutDatetime(resultSet.getString("logout_datetime"));

        return logAccess;
    }

}
