package com.eunbi.memo.common;

import org.apache.logging.log4j.message.Message;

import javax.management.StringValueExp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5MashingEncoder {

    // MD5를 통한 해싱 과정 수행
    public static String encode(String message) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            byte[] bytes = message.getBytes();
            messageDigest.update(bytes);
            byte[] digest = messageDigest.digest();


            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                Integer.toHexString(digest[i] & 0xff);
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }
}
