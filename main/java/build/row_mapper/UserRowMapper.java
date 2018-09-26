package build.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import build.model.User;

/**
 * Created by apple on 15/08/2017.
 */
public class UserRowMapper implements RowMapper<User>{

    public User mapRow(ResultSet resultSet, int rowNumber)throws SQLException{
        User user = new User();
        user.setUserId(resultSet.getLong("user_id"));
        user.setUserName(resultSet.getString("user_name"));
        user.setRoleName(resultSet.getString("role_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString( "password"));
        user.setSalt(resultSet.getString( "salt"));
        return user;
    }
}
