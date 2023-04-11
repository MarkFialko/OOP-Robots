package common;

import java.io.*;

public class FileManager {

    public static void writeIntoFile(Serializable serialObject, String fileName) throws IOException {
        File file = new File(fileName);
        try (OutputStream os = new FileOutputStream(file)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os))) {
                oos.writeObject(serialObject);
                oos.flush();
            }
        }
    }

    public static Serializable readFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (InputStream is = new FileInputStream(fileName)) {
            try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is))) {
                return (Serializable) ois.readObject();
            }
        }
    }
}
