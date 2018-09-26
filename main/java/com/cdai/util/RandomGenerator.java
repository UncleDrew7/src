package com.cdai.util;

import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by apple on 24/11/2017.
 */
public class RandomGenerator {


    public RandomGenerator() {
        super();
    }

    public String getRandomString(){
        StringBuilder hexString = new StringBuilder();

        try{
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] messageDigest = instance.digest(String.valueOf(System.nanoTime()).getBytes());
            for (int i = 0; i < 8; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    // could use a for loop, but we're only dealing with a single
                    // byte
                    hexString.append('0');
                }
                hexString.append(hex);
            }
           return hexString.toString();

        }catch (Exception e){
            e.printStackTrace();
        }

        return hexString.toString();
    }

    public String getQuickRandomString(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }
}
