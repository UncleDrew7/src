package build.dao;
import build.model.UserRole;

/**
 * Created by apple on 29/08/2017.
 */
public interface UserRoleDao {

    public String addUserRole(long userId, int roleId);
}
