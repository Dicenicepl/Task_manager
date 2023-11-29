package com.example.task_manager.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    public String Hasher(String passwordToHash){
        String readyPassword = null;
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(passwordToHash.getBytes());
            byte[] converter = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < converter.length; i++){
                stringBuilder.append(Integer.toString((converter[i] & 0xff) + 0x100, 16).substring(1));
            }
            readyPassword = stringBuilder.toString();
        }catch (NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
        }
        return readyPassword;
    }
}
