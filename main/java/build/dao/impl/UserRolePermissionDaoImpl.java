package build.dao.impl;

import build.model.UserRolePermission;
import build.dao.UserRolePermissionDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 30/08/2017.
 */
public class UserRolePermissionDaoImpl extends JdbcDaoSupport implements UserRolePermissionDao{
    @Override
    public String addUserRolePermission(UserRolePermission userRolePermission) {
        String sql = "INSERT INTO user_roles_permission ( permission,role_name,email ) VALUES( ?,?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{userRolePermission.getPermission() ,userRolePermission.getRoleName(),userRolePermission.getEmail() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }
}
