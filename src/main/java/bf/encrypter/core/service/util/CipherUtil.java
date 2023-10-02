package bf.encrypter.core.service.util;

import bf.encrypter.core.objects.CipherBundle;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class CipherUtil {


    public static CipherBundle encrypt(String fileContent, String password) throws Exception {
        byte[] salt = new byte[8];
        new SecureRandom().nextBytes(salt);

        // Derive a secret key from the password using PBKDF2
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        SecretKey secretKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");

        // Initialize the cipher for encryption
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = new byte[12]; // Use a 12-byte IV for GCM
        new SecureRandom().nextBytes(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));

        // Encrypt the fileContent
        byte[] encryptedBytes = cipher.doFinal(fileContent.getBytes());

        // Return the encrypted fileContent and salt
        return new CipherBundle(encryptedBytes, salt, iv);
    }

    public static String decrypt(CipherBundle cipherBundle, String password) throws Exception {

        byte[] salt = cipherBundle.getSalt();
        byte[] encryptedText = cipherBundle.getEncryptedBytes();

        // Derive the secret key from the password and salt using PBKDF2
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        SecretKey secretKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");

        // Initialize the cipher for decryption using AES/GCM
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, cipherBundle.getIv()));

        // Decrypt the message
        byte[] decryptedBytes = cipher.doFinal(encryptedText);

        // Convert the decrypted bytes to a string
        return new String(decryptedBytes);
    }

}
