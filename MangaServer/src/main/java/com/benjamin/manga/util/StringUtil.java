package com.benjamin.manga.util;

import java.security.MessageDigest;
import java.util.UUID;

public class StringUtil {

    private static MessageDigest MESSAGE_DIGEST;

    static {
        try {
            MESSAGE_DIGEST = MessageDigest.getInstance("SHA1");
        } catch (Exception ignored) {
        }
    }

    public static boolean isEmailValid(String email){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static String randomString(){
        return UUID.randomUUID().toString();
    }

    public static boolean validateString(String... args) {
        for (String arg : args) {
            if (arg == null || arg.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static String getSHA1String(String input) {
        byte[] b = MESSAGE_DIGEST.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
