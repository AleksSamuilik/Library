package it.alex.mylab.library.account;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunction {

    private String hash(String eMail, String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passHash = digest.digest((password + eMail).getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < passHash.length; i++) {
                sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

   protected String authentication(String eMail, String password){
        return hash(eMail,password);
    }
}
