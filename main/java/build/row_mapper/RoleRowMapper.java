package build.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import build.model.Role;


/**
 * Created by apple on 14/08/2017.
 */
public class RoleRowMapper implements RowMapper<Role> {

    public Role mapRow(ResultSet resultSet,int rowNumber)throws SQLException{
        Role role = new Role();
        role.setRoleId(resultSet.getInt("role_id"));
        role.setRole_name(resultSet.getString("role_name"));
        return role;
    }
}
