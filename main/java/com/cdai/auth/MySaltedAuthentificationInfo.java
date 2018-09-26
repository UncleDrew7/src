package com.cdai.auth;

import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;

/**o
 * Created by apple on 05/08/2017.
 */
public class MySaltedAuthentificationInfo implements SaltedAuthenticationInfo {

    private final String username;
    private final String password;
    private final String salt;

    public MySaltedAuthentificationInfo(String username, String password, String salt){
        this.username = username;
        this.password = password;
        this.salt      = salt;
    }

    @Override
    public Object getCredentials(){
        return password;
    }

    @Override
    public org.apache.shiro.util.ByteSource getCredentialsSalt() {
        return new SimpleByteSource(Base64.decode(salt));
    }

    @Override
    public PrincipalCollection getPrincipals() {
        PrincipalCollection coll = new SimplePrincipalCollection(username,
                username);
        return coll;
    }
}
