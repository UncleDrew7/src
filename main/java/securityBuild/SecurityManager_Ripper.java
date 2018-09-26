package securityBuild;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;

/**
 * Created by apple on 16/09/2017.
 */
public class SecurityManager_Ripper {
    Realm realm;
    SecurityManager securityManager = new DefaultSecurityManager(realm);

}
