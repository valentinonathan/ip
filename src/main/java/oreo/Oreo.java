package oreo;

import oreo.exception.OreoException;
import oreo.task.Task;
import oreo.task.TaskList;
import oreo.task.Todo;

/** Coordinates Oreo's command parser, task list, storage, and response messages. */
public class Oreo {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private final Parser parser;
    private boolean hasExited;
    private String loadingMessage = "";

    /** Creates the chatbot and loads its saved tasks. */
    public Oreo(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.load());
        } catch (OreoException e) {
            loadingMessage = "Unable to load saved tasks. Starting with an empty task list.";
            tasks = new TaskList();
        }
    }

    /** Returns Oreo's initial greeting, including any recoverable loading warning. */
    public String getWelcomeMessage() {
        return loadingMessage.isEmpty() ? ui.getWelcomeMessage()
                : loadingMessage + System.lineSeparator() + ui.getWelcomeMessage();
    }

    /**
     * Processes one command and returns the message that should be shown to the user.
     *
     * @param command command entered by the user
     * @return Oreo's response to the command
     */
    public String getResponse(String command) {
        if (hasExited) {
            return "Oreo has already said goodbye. Please restart the app to continue.";
        }

        return switch (parser.parseCommand(command)) {
        case BYE -> exit();
        case LIST -> ui.getTaskListMessage(tasks);
        case FIND -> findTasks(command);
        case MARK -> updateTaskStatus(command, true);
        case UNMARK -> updateTaskStatus(command, false);
        case TODO -> addTask(parser.parseTodo(command));
        case DEADLINE -> tryAddDeadline(command);
        case EVENT -> tryAddEvent(command);
        case DELETE -> deleteTask(command);
        case UNKNOWN -> addTask(new Todo(command));
        };
    }

    /** Returns whether the user has ended this Oreo session. */
    public boolean hasExited() {
        return hasExited;
    }

    /** Saves the task list and returns a farewell. */
    private String exit() {
        storage.save(tasks.storageStringRepresentation());
        hasExited = true;
        return ui.getGoodbyeMessage();
    }

    /** Adds a task and returns its confirmation message. */
    private String addTask(Task task) {
        tasks.addTask(task);
        return ui.getTaskAddedMessage(task, tasks.getTaskCount());
    }

    /** Finds tasks whose descriptions contain the supplied keyword. */
    private String findTasks(String command) {
        try {
            return ui.getMatchingTasksMessage(tasks.findTasks(parser.parseFindKeyword(command)));
        } catch (OreoException e) {
            return ui.getErrorMessage(e.getMessage());
        }
    }

    /** Marks or unmarks a task, depending on the supplied state. */
    private String updateTaskStatus(String command, boolean shouldMark) {
        try {
            String commandWord = shouldMark ? "mark " : "unmark ";
            int taskNumber = Integer.parseInt(command.substring(commandWord.length()).trim());
            Task task = shouldMark ? tasks.markTask(taskNumber) : tasks.unmarkTask(taskNumber);
            return shouldMark ? ui.getTaskMarkedMessage(task) : ui.getTaskUnmarkedMessage(task);
        } catch (NumberFormatException e) {
            return ui.getErrorMessage("Please provide a task number to " + (shouldMark ? "mark." : "unmark."));
        } catch (OreoException e) {
            return ui.getErrorMessage(e.getMessage());
        }
    }

    /** Parses and adds a deadline, returning an error when it is invalid. */
    private String tryAddDeadline(String command) {
        try {
            return addTask(parser.parseDeadline(command));
        } catch (OreoException e) {
            return ui.getErrorMessage(e.getMessage());
        }
    }

    /** Parses and adds an event, returning an error when it is invalid. */
    private String tryAddEvent(String command) {
        try {
            return addTask(parser.parseEvent(command));
        } catch (OreoException e) {
            return ui.getErrorMessage(e.getMessage());
        }
    }

    /** Deletes a task and returns the outcome. */
    private String deleteTask(String command) {
        try {
            int taskNumber = Integer.parseInt(command.substring("delete ".length()).trim());
            Task task = tasks.deleteTask(taskNumber);
            return ui.getTaskDeletedMessage(task, tasks.getTaskCount());
        } catch (NumberFormatException e) {
            return ui.getErrorMessage("Please provide a task number to delete.");
        } catch (OreoException e) {
            return ui.getErrorMessage(e.getMessage());
        }
    }
}
