package build.dao;

import build.model.Role;
import java.util.List;

/**
 * Created by apple on 14/08/2017.
 */
public interface RoleDao {
    //DEFINE API

//    CREATES NEW ROLE
    public String createNewRole(Role newRole);

//    DISPLAYS ALL ROLES
    public List<Role> showCurrentRoles();

    //SECURITY REALM
    public Role getUserRoleByUserId(long userId);

    //ADMIN ADD USER FORM PROCESS
    public String addUserRole(long userId, int roleId);

    //ADMIN UPDATE USER ROLE
    public String updateUserRole(long userId, int roleId);
}
