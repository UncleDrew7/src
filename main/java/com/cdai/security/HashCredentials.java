package com.cdai.security;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * Created by apple on 06/11/2017.
 */
public class HashCredentials {

    private String salt;
    private String unSecurePassword;
    private String securePassword;
    private int hashIterations;

    public HashCredentials() {
        super();
    }

    public HashCredentials(String salt, String unSecurePassword, int hashIterations) {
        this.salt = salt;
        this.unSecurePassword = unSecurePassword;
        this.hashIterations = hashIterations;
    }

    public HashCredentials(String salt, String unSecurePassword) {
        this.salt = salt;
        this.unSecurePassword = unSecurePassword;
    }

    public String securePass(String salt, String unSecurePassword) {
        String hashAlgorithmName = "MD5";
        int hashIterations = 1024;
        Object obj = new SimpleHash(hashAlgorithmName, unSecurePassword, salt, hashIterations);
        String securePass =  obj.toString();
        return securePass;
    }
}
