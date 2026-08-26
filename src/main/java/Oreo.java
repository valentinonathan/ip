import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * The entry point for the chatbot application.
 */
public class Oreo {
    /**
     * Runs the chatbot command loop.
     *
     * @param args command-line arguments, which are not used
     */

    enum Command {
        BYE,
        LIST,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE,
        UNKNOWN;

        public static Command from(String input) {
            if (input.equals("bye")) {
                return BYE;
            } else if (input.equals("list")) {
                return LIST;
            } else if (input.startsWith("mark ")) {
                return MARK;
            } else if (input.startsWith("unmark ")) {
                return UNMARK;
            } else if (input.startsWith("todo ")) {
                return TODO;
            } else if (input.startsWith("deadline ")) {
                return DEADLINE;
            } else if (input.startsWith("event ")) {
                return EVENT;
            } else if (input.startsWith("delete ")) {
                return DELETE;
            }

            return UNKNOWN;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ui ui = new Ui();
        Storage storage = new Storage("./data/Oreo.txt");
        TaskList tasks = new TaskList(storage.load());
        ui.showWelcome();

        while (true) {
            String command = scanner.nextLine();
            ui.showDivider();

            Command commandType = Command.from(command);

            switch (commandType) {
                case BYE:
                    ui.showGoodbye();
                    break;

                case LIST:
                    ui.showTaskList(tasks);
                    continue;

                case MARK:
                    try {
                        int taskNumber = Integer.parseInt(command.substring(5).trim());
                        Task task = tasks.markTask(taskNumber);
                        ui.showTaskMarked(task);
                    } catch (NumberFormatException e) {
                        ui.showError("Please provide a task number to mark.");
                    } catch (OreoException e) {
                        ui.showError(e.getMessage());
                    }

                    ui.showDivider();
                    continue;

                case UNMARK:
                    try {
                        int taskNumber = Integer.parseInt(command.substring(7).trim());
                        Task task = tasks.unmarkTask(taskNumber);
                        ui.showTaskUnmarked(task);
                    } catch (NumberFormatException e) {
                        ui.showError("Please provide a task number to unmark.");
                    } catch (OreoException e) {
                        ui.showError(e.getMessage());
                    }

                    ui.showDivider();
                    continue;

                case TODO:
                    addTask(tasks, ui, new Todo(command.substring(5).trim()));
                    continue;

                case DEADLINE:
                    try {
                        addTask(tasks, ui, createDeadline(command));
                    } catch (OreoException e) {
                        ui.showError(e.getMessage());
                        ui.showDivider();
                    }

                    continue;

                case EVENT:
                    try {
                        addTask(tasks, ui, createEvent(command));
                    } catch (OreoException e) {
                        ui.showError(e.getMessage());
                        ui.showDivider();
                    }

                    continue;

                case DELETE:
                    try {
                        int taskNumber = Integer.parseInt(command.substring(7).trim());
                        Task temp = tasks.deleteTask(taskNumber);

                        ui.showTaskDeleted(temp, tasks.getTaskCount());
                        ui.showDivider();
                    } catch (NumberFormatException e) {
                        ui.showError("Please provide a task number to delete.");
                    } catch (OreoException e) {
                        ui.showError(e.getMessage());
                        ui.showDivider();
                    }

                    continue;

                case UNKNOWN:
                    addTask(tasks, ui, new Todo(command));
                    continue;
            }
            storage.save(tasks.storageStringRepresentation());
        }
    }

    /**
     * Adds a task and prints its confirmation message.
     *
     * @param tasks list to receive the task
     * @param task task to add
     */
    private static void addTask(TaskList tasks, Ui ui, Task task) {
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getTaskCount());
    }

    /**
     * Creates a deadline from a command containing a description and a {@code /by} value.
     *
     * @param command deadline command entered by the user
     * @return a deadline task
     */
    private static Deadline createDeadline(String command) {
        Deadline result = null;
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
            result = new Deadline(description, by);
        } catch (DateTimeParseException e) {
            throw new OreoException("Use: event <description> /from <year>-<month>-<date> " +
                    "/to <year>-<month>-<date>.");
        }
        return result;
    }

    /**
     * Creates an event from a command containing a description, {@code /from}, and {@code /to} values.
     *
     * @param command event command entered by the user
     * @return an event task
     */
    private static Event createEvent(String command) {
        Event result = null;
        try {
            int fromMarker = command.indexOf(" /from ");
            int toMarker = command.indexOf(" /to ", fromMarker + " /from ".length());
            if (fromMarker == -1 || toMarker == -1) {
                throw new OreoException("Use: event <description> /from <year>-<month>-<date> " +
                        "/to <year>-<month>-<date>.");
            }
            String description = command.substring("event ".length(), fromMarker).trim();
            String from = command.substring(fromMarker + " /from ".length(), toMarker).trim();
            String to = command.substring(toMarker + " /to ".length()).trim();
            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new OreoException("Use: event <description> /from <year>-<month>-<date> " +
                        "/to <year>-<month>-<date>.");
            }
            result = new Event(description, from, to);
        } catch (DateTimeParseException e) {
            throw new OreoException("Use: event <description> /from <year>-<month>-<date> " +
                    "/to <year>-<month>-<date>.");
        }
        return result;
    }
}
