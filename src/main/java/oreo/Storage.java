package oreo;

import oreo.exception.OreoException;

import java.io.File;
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
                throw new OreoException("Unable to create the task data file.");
            }
        }
        this.filePath = Path.of(filePath);
    }

    public String load() {
        try {
            return Files.readString(this.filePath);
        } catch (IOException e) {
            throw new OreoException("Unable to load saved tasks.");
        }
    }

    public void save(String content) {
        try {
            Files.writeString(this.filePath, content);
        } catch (IOException e) {
            throw new OreoException("Unable to save tasks.");
        }
    }
}
