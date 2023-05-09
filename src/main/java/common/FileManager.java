package common;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;

public class FileManager {

    public static void writeIntoFile(Serializable serialObject, Path path) throws IOException {
        File file = path.toFile();
        if (!file.exists()) {
            boolean result = file.getParentFile().mkdirs();
            result = file.createNewFile();
        }
        try (OutputStream os = new FileOutputStream(file)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os))) {
                oos.writeObject(serialObject);
                oos.flush();
            }
        }
    }

    public static boolean writeIntoFileSafe(Serializable serialObject, Path path) {
        try {
            writeIntoFile(serialObject, path);
        } catch (IOException exc) {
            return false;
        }
        return true;
    }

    public static Serializable readFromFile(Path path) throws IOException, ClassNotFoundException {
        File file = path.toFile();
        try (InputStream is = new FileInputStream(file)) {
            try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is))) {
                return (Serializable) ois.readObject();
            }
        }
    }

    public static Optional<Serializable> readFromFileSafe(Path path) {
        try {
            Serializable serializable = readFromFile(path);
            return Optional.of(serializable);
        } catch (IOException | ClassNotFoundException exc) {
            return Optional.empty();
        }
    }
}
