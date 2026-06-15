package entity;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.util.Base64;

public class CryptoSample {

    public static String sha256(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static SecretKey generateAesKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("SHA-256: " + sha256("hello cbomkit"));
        System.out.println("AES key algorithm: " + generateAesKey().getAlgorithm());
    }
}