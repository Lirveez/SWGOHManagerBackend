package services;


import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Password {
    private static SecureRandom random;
    private static final String CHARSET = "UTF-8";
    private static final String ENCRYPTION_ALGORITHM = "SHA-512";
    private static Base64.Decoder decoder = Base64.getDecoder();
    private static Base64.Encoder encoder = Base64.getEncoder();

    public static byte[] getSalt(int length) {
        random = new SecureRandom();
        byte bytes[] = new byte[length];
        random.nextBytes(bytes);
        return bytes;

    }

    public static byte[] hashWithSalt(String password, byte[] salt) {
        byte[] hash = null;
        try {
            byte[] bytesOfMessage = password.getBytes(CHARSET);
            MessageDigest md;
            md = MessageDigest.getInstance(ENCRYPTION_ALGORITHM);
            md.reset();
            md.update(salt);
            md.update(bytesOfMessage);
            hash = md.digest();

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return hash;
    }

    public static String base64FromBytes(byte[] text) {
        return encoder.encodeToString(text);
    }

    public static byte[] bytesFrombase64(String text) {
        byte[] textBytes = null;
        textBytes = decoder.decode(text);
        return textBytes;
    }

}
