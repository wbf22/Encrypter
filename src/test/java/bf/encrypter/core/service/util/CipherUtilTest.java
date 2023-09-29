package bf.encrypter.core.service.util;

import bf.encrypter.core.objects.CipherBundle;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CipherUtilTest {

    @Test
    void encrypt_decrypt() throws Exception {
        String message = "Hello world";
        CipherBundle bundle = CipherUtil.encrypt(message, "password");

        String result = CipherUtil.decrypt(bundle, "password");

        assertEquals(message, result);
    }


}
