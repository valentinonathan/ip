import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Storage {
    Path filePath;

    public Storage(String filePath) {
        File tempFile = new File(filePath);
        if (!tempFile.exists()) {
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.filePath = Path.of(filePath);
    }

    public String load() {
        try {
            String content = Files.readString(this.filePath);
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(String content) {
        try {
            Files.writeString(this.filePath, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
