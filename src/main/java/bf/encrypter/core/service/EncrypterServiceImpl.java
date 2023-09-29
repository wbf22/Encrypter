package bf.encrypter.core.service;


import bf.encrypter.core.objects.old.KeyStoreService;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import static bf.encrypter.core.objects.old.KeyStoreService.getAsBytes;

/**
 *
 * @author brand
 */
public class EncrypterServiceImpl implements EncrypterService {

    public static final int GCM_IV_LENGTH = 12;

    public static final int GCM_TAG_LENGTH = 16;


    @Override
    public String readFile(String password, String filePath) {
        // decrypt file with password and salt as hash



        return null;
    }


    private static byte[] genIv(int size) {
        byte[] initializationVector = new byte[size];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initializationVector);
        return initializationVector;
    }

    @Override
    public Boolean decryptFile(String password, String filePath, String destinationPath) {
        return null;
    }

    @Override
    public Boolean encryptFile(String password, String filePath, String destinationPath) {
        return null;
    }
}
