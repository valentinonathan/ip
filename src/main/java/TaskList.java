import java.util.Arrays;
import java.util.Objects;

/**
 * Stores the tasks entered during one run of the chatbot.
 */
public class TaskList {
    /** The fixed-capacity array that holds tasks. */
    private final Task[] tasks;

    /** The number of tasks currently stored. */
    private int taskCount;

    /** Creates an empty task list. */
    public TaskList() {
        this("");
    }

    /**
     * Creates a task list based on the content from the storage
     *
     * @param content the storage string representation of the tasks
     */
    public TaskList(String content) {
        String[] tasksStr = content.split("\\R");
        this.tasks = new Task[100];

        if (!content.isEmpty()) {
            try {
                for (int i = 0; i < tasksStr.length; ++i) {
                    String[] tempStr = tasksStr[i].split(" \\| ");

                    if (Objects.equals(tempStr[0], "T")) {
                        this.tasks[i] = new Todo(tempStr[2]);
                    } else if (Objects.equals(tempStr[0], "D")) {
                        this.tasks[i] = new Deadline(tempStr[2], tempStr[3]);
                    } else if (Objects.equals(tempStr[0], "E")) {
                        this.tasks[i] = new Event(tempStr[2], tempStr[3], tempStr[4]);
                    } else {
                        throw new OreoException("Unknown task type in storage");
                    }

                    if (Objects.equals(tempStr[1], "X")) {
                        this.tasks[i].markAsDone();
                    }

                    this.taskCount++;
                }
            } catch (Exception e) {
                System.out.println(" " + e.getMessage());
            }
        }
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task task to store
     * @throws OreoException if the task list is full
     */
    public void addTask(Task task) {
        if (taskCount >= tasks.length) {
            throw new OreoException("Task list is full");
        }
        tasks[taskCount] = task;
        taskCount++;
    }

    /**
     * Returns the number of tasks stored in this list.
     *
     * @return task count
     */
    public int getTaskCount() {
        return taskCount;
    }

    /**
     * Marks a task as completed.
     *
     * @param taskNumber one-based number of the task to mark
     * @return the marked task
     * @throws OreoException if the task number does not identify a stored task
     */
    public Task markTask(int taskNumber) {
        int taskIndex = getTaskIndex(taskNumber);
        tasks[taskIndex].markAsDone();
        return tasks[taskIndex];
    }

    /**
     * Marks a task as not completed.
     *
     * @param taskNumber one-based number of the task to unmark
     * @return the unmarked task
     * @throws OreoException if the task number does not identify a stored task
     */
    public Task unmarkTask(int taskNumber) {
        int taskIndex = getTaskIndex(taskNumber);
        tasks[taskIndex].markAsNotDone();
        return tasks[taskIndex];
    }

    /**
     * Deletes a task from the list
     *
     * @param taskNumber one-based number of the task to unmark
     * @return void
     * @throws OreoException if the task number does not identify a stored task
     */
    public Task deleteTask(int taskNumber) {
        int taskIndex = getTaskIndex(taskNumber);
        Task temp = this.tasks[taskIndex];
        for (int i = taskIndex + 1; i < this.taskCount; ++i) {
            this.tasks[i - 1] = this.tasks[i];
        }
        this.tasks[taskCount] = null;
        this.taskCount--;
        return temp;
    }

    /**
     * Converts a user-facing task number to an array index.
     *
     * @param taskNumber one-based number of the task
     * @return zero-based array index for the task
     * @throws OreoException if the task number does not identify a stored task
     */
    private int getTaskIndex(int taskNumber) {
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new OreoException("Please provide a valid task number.");
        }
        return taskNumber - 1;
    }

    public String storageStringRepresentation() {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < taskCount; i++) {
            result.append(this.tasks[i].storageStringRepresentation()).append(System.lineSeparator());
        }
        return result.toString();
    }

    /**
     * Returns a numbered display of every stored task and its completion status.
     *
     * @return the formatted task list
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(" Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            result.append(System.lineSeparator())
                    .append(" ").append(i + 1).append(".")
                    .append(tasks[i]);
        }
        return result.toString();
    }
}
