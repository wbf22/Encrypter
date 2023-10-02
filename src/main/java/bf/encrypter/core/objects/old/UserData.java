package bf.encrypter.core.objects.old;

import java.io.*;

/**
 *
 * @author brand
 */
public class UserData implements Serializable {
    private Object[][] data;




    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }


    public byte[] getAsBytes() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(data);
        oos.flush();
        return bos.toByteArray();
    }


    public Object[][] getObjectArray(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object[][] deserialized = (Object[][]) ois.readObject();
        data = deserialized;
        return deserialized;
    }




}
