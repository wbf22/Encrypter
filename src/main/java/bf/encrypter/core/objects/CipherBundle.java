package bf.encrypter.core.objects;

public class CipherBundle {

    private byte[] encryptedBytes;
    private byte[] salt;
    private byte[] iv;


    public CipherBundle(byte[] encryptedBytes, byte[] salt, byte[] iv) {
        this.encryptedBytes = encryptedBytes;
        this.salt = salt;
        this.iv = iv;
    }


    public byte[] getEncryptedBytes() {
        return encryptedBytes;
    }

    public void setEncryptedBytes(byte[] encryptedBytes) {
        this.encryptedBytes = encryptedBytes;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }
}
