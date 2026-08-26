/**
 * Coordinates the chatbot's user interface, task list, command parser, and storage.
 */
public class Oreo {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    /** Creates the chatbot and loads its saved tasks. */
    public Oreo(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.load());
        } catch (OreoException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /** Runs the chatbot command loop until the user exits or input ends. */
    public void run() {
        ui.showWelcome();
        while (ui.hasNextCommand()) {
            String command = ui.readCommand();
            ui.showDivider();

            switch (parser.parseCommand(command)) {
                case BYE:
                    storage.save(tasks.storageStringRepresentation());
                    ui.showGoodbye();
                    return;
                case LIST:
                    ui.showTaskList(tasks);
                    break;
                case MARK:
                    updateTaskStatus(command, true);
                    break;
                case UNMARK:
                    updateTaskStatus(command, false);
                    break;
                case TODO:
                    addTask(parser.parseTodo(command));
                    break;
                case DEADLINE:
                    tryAddDeadline(command);
                    break;
                case EVENT:
                    tryAddEvent(command);
                    break;
                case DELETE:
                    deleteTask(command);
                    break;
                case UNKNOWN:
                    addTask(new Todo(command));
                    break;
                default:
                    throw new AssertionError("Unhandled command");
            }
        }
    }

    /** Adds a task and displays confirmation to the user. */
    private void addTask(Task task) {
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getTaskCount());
    }

    /** Marks or unmarks a task, depending on the supplied state. */
    private void updateTaskStatus(String command, boolean shouldMark) {
        try {
            String commandWord = shouldMark ? "mark " : "unmark ";
            int taskNumber = Integer.parseInt(command.substring(commandWord.length()).trim());
            Task task = shouldMark ? tasks.markTask(taskNumber) : tasks.unmarkTask(taskNumber);
            if (shouldMark) {
                ui.showTaskMarked(task);
            } else {
                ui.showTaskUnmarked(task);
            }
        } catch (NumberFormatException e) {
            ui.showError("Please provide a task number to " + (shouldMark ? "mark." : "unmark."));
        } catch (OreoException e) {
            ui.showError(e.getMessage());
        }
        ui.showDivider();
    }

    /** Parses and adds a deadline, displaying an error when it is invalid. */
    private void tryAddDeadline(String command) {
        try {
            addTask(parser.parseDeadline(command));
        } catch (OreoException e) {
            ui.showError(e.getMessage());
            ui.showDivider();
        }
    }

    /** Parses and adds an event, displaying an error when it is invalid. */
    private void tryAddEvent(String command) {
        try {
            addTask(parser.parseEvent(command));
        } catch (OreoException e) {
            ui.showError(e.getMessage());
            ui.showDivider();
        }
    }

    /** Deletes the task identified by the command and displays the result. */
    private void deleteTask(String command) {
        try {
            int taskNumber = Integer.parseInt(command.substring("delete ".length()).trim());
            Task task = tasks.deleteTask(taskNumber);
            ui.showTaskDeleted(task, tasks.getTaskCount());
        } catch (NumberFormatException e) {
            ui.showError("Please provide a task number to delete.");
        } catch (OreoException e) {
            ui.showError(e.getMessage());
        }
        ui.showDivider();
    }

    /** Starts the chatbot using the standard task-data location. */
    public static void main(String[] args) {
        new Oreo("data/Oreo.txt").run();
    }
}
