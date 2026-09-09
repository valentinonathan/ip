package oreo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import oreo.exception.OreoException;
import oreo.task.Deadline;
import oreo.task.Event;
import oreo.task.Todo;

/** Tests tag-related command parsing. */
class ParserTest {
    private final Parser parser = new Parser();

    @Test
    void parseTodo_inlineTags_tagsAreSeparateFromDescriptionAndNormalized() {
        Todo todo = parser.parseTodo("todo watch a movie #Fun #weekend");

        assertEquals("watch a movie", todo.getDescription());
        assertEquals(List.of("#fun", "#weekend"), todo.getTags());
    }

    @Test
    void parseDeadlineAndEvent_inlineTags_tagsAreAppliedAfterDateArguments() {
        Deadline deadline = parser.parseDeadline("deadline submit report /by 2026-09-30 #school");
        Event event = parser.parseEvent("event team meeting /from 2026-09-15 /to 2026-09-16 #project");

        assertEquals(List.of("#school"), deadline.getTags());
        assertEquals(List.of("#project"), event.getTags());
    }

    @Test
    void parseTagUpdate_multipleTags_returnsTaskNumberAndNormalizedTags() {
        Parser.TagUpdate tagUpdate = parser.parseTagUpdate("tag 3 #Fun #weekend", "tag");

        assertEquals(3, tagUpdate.taskNumber());
        assertEquals(List.of("#fun", "#weekend"), tagUpdate.tags());
    }

    @Test
    void parseTodo_tagFollowedByOrdinaryWord_invalidTagSequenceRejected() {
        assertThrows(OreoException.class, () -> parser.parseTodo("todo watch #fun movie"));
    }

    @Test
    void parseTagUpdate_tagWithSpace_invalidTagRejected() {
        assertThrows(OreoException.class, () -> parser.parseTagUpdate("tag 1 #fun day", "tag"));
    }
}
