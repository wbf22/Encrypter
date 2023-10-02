package bf.encrypter.core.service.util;

import bf.encrypter.core.objects.CipherBundle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CipherUtilTest {

    @Test
    void encrypt_decrypt() throws Exception {
        String message = "Hello world";
        CipherBundle bundle = CipherUtil.encrypt(message, "password");

        String result = CipherUtil.decrypt(bundle, "password");

        assertEquals(message, result);
    }


    @Test
    void encrypt_decrypt_readme() throws Exception {
        String message = FileUtil.readFile("./README.md");
        CipherBundle bundle = CipherUtil.encrypt(message, "password");

        String result = CipherUtil.decrypt(bundle, "password");

        assertEquals(message, result);
    }


}
