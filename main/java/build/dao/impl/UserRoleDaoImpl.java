package build.dao.impl;

import build.model.UserRole;
import build.dao.UserRoleDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 30/08/2017.
 */
public class UserRoleDaoImpl extends JdbcDaoSupport implements UserRoleDao{
    @Override
    public String addUserRole(long userId, int roleId) {
        String sql = " INSERT INTO user_role(user_id, role_id) VALUES (?,?);";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{userId,roleId }
        );
        if(1 == returnValue)
            return "ROLE ADD SUCESS MESSAGE ";
        else
            return "ROLE ADD FAILURE MESSAGE";
    }
}
