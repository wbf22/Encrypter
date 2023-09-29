package bf.encrypter.core.service.util;

import bf.encrypter.core.objects.CipherBundle;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class CipherUtil {


    public static CipherBundle encrypt(String message, String password) throws Exception {
        byte[] salt = new byte[8];

        // Derive a secret key from the password using PBKDF2
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey secretKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");

        // Initialize the cipher for encryption
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

        // Encrypt the message
        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

        // Return the encrypted message and salt
        return new CipherBundle(encryptedBytes, salt, iv);
    }

    public static String decrypt(CipherBundle cipherBundle, String password) throws Exception {

        byte[] salt = cipherBundle.getSalt();
        byte[] encryptedText = cipherBundle.getEncryptedBytes();

        // Derive the secret key from the password and salt using PBKDF2
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey secretKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");

        // Initialize the cipher for decryption
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(cipherBundle.getIv()));

        // Decrypt the message
        byte[] decryptedBytes = cipher.doFinal(encryptedText);

        // Convert the decrypted bytes to a string
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}
