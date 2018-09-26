package build.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import build.model.Role;
import build.dao.RoleDao;
import build.row_mapper.RoleRowMapper;

/**
 * Created by apple on 14/08/2017.
 */
public class RoleDaoImpl extends JdbcDaoSupport implements RoleDao{

    @Override
    public String createNewRole(Role newRole) {
       String sql = "INSERT INTO role (role_name) VALUES(?)";
       int returnValue = getJdbcTemplate().update(
               sql,
               new Object[]{newRole.getRole_name()}
       );
       return null;
    }

    @Override
    public List<Role> showCurrentRoles() {

        String sql = "SELECT id , role_name FROM role";
        List<Role> entityList = getJdbcTemplate().query(
                sql,
                new RoleRowMapper()
        );
        return entityList;
    }

    @Override
    public Role getUserRoleByUserId(long userId) {
        String sql = "SELECT " +
                " ur.role_id, " +
                "r.role_name," +
                "r.role_description" +
                " FROM user_role ur, role r" +
                " WHERE ur.role_id = r.id" +
                " AND ur.user_id = ?";
        Role userRole = getJdbcTemplate().queryForObject(
                sql,
                new Object[]{userId},
                new RowMapper<Role>() {
                    @Override
                    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                        Role role = new Role();

                        role.setRoleId(resultSet.getInt("role_id"));
                        role.setRole_name(resultSet.getString("role_name"));
                        role.setRoleDescription(resultSet.getString("role_description"));

                        return role;
                    }


                }
        );
        return userRole;
    }

    @Override
    public String addUserRole(long userId, int roleId) {
        String sql = " INSERT INTO user_role(user_id, role_id) VALUES (?,?);";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{userId,roleId }
        );
      try{
          if(1 == returnValue)
              return "ROLE ADD SUCESS MESSAGE ";
          else
              return "ROLE ADD FAILURE MESSAGE";

      }catch(Exception e){
          e.printStackTrace();
          return "ERROR!";
      }
    }

    @Override
    public String updateUserRole(long userId, int roleId) {
        String sql = "UPDATE user_role SET  " +
                "role_id = ? " +
                "WHERE user_id = ? ";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{roleId,userId}
        );
        try{
            if(1 == returnValue)
                return "ROLE UPDATED SUCCESSFULLY ";
            else
                return "ROLE UPDATED FAILURE ";

        }catch(Exception e){
            e.printStackTrace();
            return "ERROR!";
        }
    }
}
