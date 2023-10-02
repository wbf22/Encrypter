package bf.encrypter.core.service.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtil {

    private FileUtil() {}



    public static void writeObjectToFile(String destinationPath, Object object) throws IOException {
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(destinationPath)) ) {
            oos.writeObject(object);
        }
    }

    public static <T> T readObjectFromFile(String filePath, Class<T> objectType) throws IOException {
        T object = null;
        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath)); ) {
            Object obj = ois.readObject();
            if (objectType.isInstance(obj)) {
                object = objectType.cast(obj);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return object;
    }


    public static void writeFile(String desitnationPath, String fileContents) throws IOException {
        File file = new File(desitnationPath);

        FileOutputStream fileOutputStream;
        fileOutputStream = new FileOutputStream(file);

        fileOutputStream.write(fileContents.getBytes(StandardCharsets.UTF_8));
        fileOutputStream.close();
    }

    public static String readFile(String filePath) throws IOException {
        File file = new File(filePath);

        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] fileContent = fileInputStream.readAllBytes();
        fileInputStream.close();

        return new String(fileContent, StandardCharsets.UTF_8);
    }
}
