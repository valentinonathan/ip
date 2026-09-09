package oreo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Tests tag commands through Oreo's command-processing interface. */
class OreoTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void getResponse_tagCommands_returnsExpectedMessagesAndGroupedTags() {
        Oreo oreo = new Oreo(temporaryDirectory.resolve("Oreo.txt").toString());

        assertEquals("Got it. I've added this task:" + System.lineSeparator()
                + "  [T][ ] watch a movie #fun" + System.lineSeparator()
                + "Now you have 1 tasks in the list.", oreo.getResponse("todo watch a movie #fun"));
        assertEquals("Got it. I've added these tags to this task:" + System.lineSeparator()
                + "  [T][ ] watch a movie #fun #weekend", oreo.getResponse("tag 1 #weekend"));
        assertEquals("Here are the tags and their tasks:" + System.lineSeparator()
                + "#fun" + System.lineSeparator()
                + "  1.[T][ ] watch a movie #fun #weekend" + System.lineSeparator()
                + "#weekend" + System.lineSeparator()
                + "  1.[T][ ] watch a movie #fun #weekend", oreo.getResponse("list tags"));
        assertEquals("Got it. I've removed these tags from this task:" + System.lineSeparator()
                + "  [T][ ] watch a movie #weekend", oreo.getResponse("untag 1 #fun"));
    }

    @Test
    void getResponse_invalidTagCommand_returnsUsageMessage() {
        Oreo oreo = new Oreo(temporaryDirectory.resolve("Oreo.txt").toString());

        assertEquals("Use: untag <task number> <#tag>...", oreo.getResponse("untag1 #fun"));
    }

    @Test
    void getResponse_tagUpdate_tagsPersistWithoutWaitingForExit() throws IOException {
        Path storageFile = temporaryDirectory.resolve("Oreo.txt");
        Oreo oreo = new Oreo(storageFile.toString());

        oreo.getResponse("todo watch a movie");
        oreo.getResponse("tag 1 #fun");

        assertEquals("T |   | watch a movie | tags: #fun" + System.lineSeparator(),
                Files.readString(storageFile));
        Oreo reloadedOreo = new Oreo(storageFile.toString());
        assertEquals("Here are the tasks in your list:" + System.lineSeparator()
                + " 1.[T][ ] watch a movie #fun", reloadedOreo.getResponse("list"));
    }
}
