package oreo;

import oreo.exception.OreoException;
import oreo.task.Deadline;
import oreo.task.Event;
import oreo.task.Todo;

import java.time.format.DateTimeParseException;

/** Interprets user commands and constructs the tasks described by them. */
public class Parser {
    /** The command categories understood by the application. */
    public enum Command {
        BYE, LIST, FIND, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN
    }

    /** Identifies the command category for a line of user input. */
    public Command parseCommand(String input) {
        if (input.equals("bye")) {
            return Command.BYE;
        } else if (input.equals("list")) {
            return Command.LIST;
        } else if (input.startsWith("find ")) {
            return Command.FIND;
        } else if (input.startsWith("mark ")) {
            return Command.MARK;
        } else if (input.startsWith("unmark ")) {
            return Command.UNMARK;
        } else if (input.startsWith("todo ")) {
            return Command.TODO;
        } else if (input.startsWith("deadline ")) {
            return Command.DEADLINE;
        } else if (input.startsWith("event ")) {
            return Command.EVENT;
        } else if (input.startsWith("delete ")) {
            return Command.DELETE;
        }
        return Command.UNKNOWN;
    }

    /** Extracts the keyword supplied to a find command. */
    public String parseFindKeyword(String command) {
        String keyword = command.substring("find ".length()).trim();
        if (keyword.isEmpty()) {
            throw new OreoException("Use: find <keyword>.");
        }
        return keyword;
    }

    /** Creates a to-do task from a command. */
    public Todo parseTodo(String command) {
        return new Todo(command.substring("todo ".length()).trim());
    }

    /** Creates a deadline from a command with a description and {@code /by} value. */
    public Deadline parseDeadline(String command) {
        try {
            int byMarker = command.indexOf(" /by ");
            if (byMarker == -1) {
                throw new OreoException("Use: deadline <description> /by <year>-<month>-<date>.");
            }
            String description = command.substring("deadline ".length(), byMarker).trim();
            String by = command.substring(byMarker + " /by ".length()).trim();
            if (description.isEmpty() || by.isEmpty()) {
                throw new OreoException("Use: deadline <description> /by <year>-<month>-<date>.");
            }
            return new Deadline(description, by);
        } catch (DateTimeParseException e) {
            throw new OreoException("Use: deadline <description> /by <year>-<month>-<date>.");
        }
    }

    /** Creates an event from a command with description, {@code /from}, and {@code /to} values. */
    public Event parseEvent(String command) {
        try {
            int fromMarker = command.indexOf(" /from ");
            int toMarker = command.indexOf(" /to ", fromMarker + " /from ".length());
            if (fromMarker == -1 || toMarker == -1) {
                throw new OreoException("Use: event <description> /from <year>-<month>-<date> "
                        + "/to <year>-<month>-<date>.");
            }
            String description = command.substring("event ".length(), fromMarker).trim();
            String from = command.substring(fromMarker + " /from ".length(), toMarker).trim();
            String to = command.substring(toMarker + " /to ".length()).trim();
            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new OreoException("Use: event <description> /from <year>-<month>-<date> "
                        + "/to <year>-<month>-<date>.");
            }
            return new Event(description, from, to);
        } catch (DateTimeParseException e) {
            throw new OreoException("Use: event <description> /from <year>-<month>-<date> "
                    + "/to <year>-<month>-<date>.");
        }
    }
}
