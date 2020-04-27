package br.com.aquiaolado.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SegurancaUtils {

    private SegurancaUtils() {
    }

    public static String generateMD5(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(text.getBytes(), 0, text.length());
        return new BigInteger(1, digest.digest()).toString(16);
    }

    public static Boolean match(String password1, String password2){
        return password1.equalsIgnoreCase(password2);
    }

}
