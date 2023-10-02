package bf.encrypter.core.service;


import bf.encrypter.core.objects.CipherBundle;
import bf.encrypter.core.service.exception.EncrypterException;
import bf.encrypter.core.service.util.CipherUtil;
import bf.encrypter.core.service.util.FileUtil;


/**
 *
 * @author brand
 */
public class EncrypterServiceImpl implements EncrypterService {



    @Override
    public String readFile(String password, String filePath) {

        try {
            CipherBundle bundle = FileUtil.readObjectFromFile(filePath, CipherBundle.class);

            return CipherUtil.decrypt(bundle, password);
        } catch (Exception e) {
            throw new EncrypterException("Error during read: ", e);
        }
    }

    @Override
    public void decryptFile(String password, String filePath, String destinationPath) {

        try {
            CipherBundle bundle = FileUtil.readObjectFromFile(filePath, CipherBundle.class);

            String fileContents = CipherUtil.decrypt(bundle, password);

            FileUtil.writeFile(destinationPath, fileContents);
        } catch (Exception e) {
            throw new EncrypterException("Error decryption: ", e);
        }
    }

    @Override
    public void encryptFile(String password, String filePath, String destinationPath) {

        try {
            String fileContent = FileUtil.readFile(filePath);

            CipherBundle bundle = CipherUtil.encrypt(fileContent, password);

            FileUtil.writeObjectToFile(destinationPath, bundle);

        } catch (Exception e) {
            throw new EncrypterException("Error encryption: ", e);
        }
    }

    @Override
    public void reEncryptFile(String password, String filePath) {

        try {
            CipherBundle bundle = FileUtil.readObjectFromFile(filePath, CipherBundle.class);

            String fileContents = CipherUtil.decrypt(bundle, password);

            bundle = CipherUtil.encrypt(fileContents, password);

            FileUtil.writeObjectToFile(filePath, bundle);
        } catch (Exception e) {
            throw new EncrypterException("Error during re-encrypt: ", e);
        }
    }
}
