package com.matiasgluck.befeatureflag.utils;

import java.security.SecureRandom;

public class UserUtils {

    public static String generateRandomKey(int length) {
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789-";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}
