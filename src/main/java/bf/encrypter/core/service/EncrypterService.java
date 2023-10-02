package bf.encrypter.core.service;

public interface EncrypterService {


    /**
     * Reads an encrypted file and returns the content as a string. DOES
     * NOT create new file with decrypted contents
     */
    String readFile(String password, String filePath);


    /**
     * Decrypts an encrypted file into a new file @destinationPath
     *
     * @param password password to decrypt the file with
     * @param filePath path to encrypted file
     * @param destinationPath path for decrypted file to be saved
     */
    void decryptFile(String password, String filePath, String destinationPath);


    /**
     * Encrypts a file into a new file @destinationPath
     *
     * @param password password to encrypt with
     * @param filePath path to file which is to be encrypted
     * @param destinationPath path for encrypted file to be saved
     */
    void encryptFile(String password, String filePath, String destinationPath);


    /**
     * Decrypts an encrypted file and then encrypts it with fresh a fresh salt and iv.
     *
     * @param password password to encrypt with
     * @param filePath path to file which is to be re-encrypted
     */
    void reEncryptFile(String password, String filePath);
}
