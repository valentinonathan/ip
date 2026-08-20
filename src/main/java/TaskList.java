/**
 * Stores the tasks entered during one run of the chatbot.
 */
public class TaskList {
    /** The fixed-capacity array that holds tasks. */
    private final Task[] tasks;

    /** The number of tasks currently stored. */
    private int taskCount;

    /**
     * Creates an empty task list with the specified maximum number of tasks.
     *
     * @param size maximum number of tasks the list can hold
     */
    public TaskList(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Task list size cannot be negative");
        }
        this.tasks = new Task[size];
        this.taskCount = 0;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task task to store
     * @throws IllegalStateException if the task list is full
     */
    public void addTask(Task task) {
        if (taskCount >= tasks.length) {
            throw new IllegalStateException("Task list is full");
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
     * @throws IllegalArgumentException if the task number does not identify a stored task
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
     * @throws IllegalArgumentException if the task number does not identify a stored task
     */
    public Task unmarkTask(int taskNumber) {
        int taskIndex = getTaskIndex(taskNumber);
        tasks[taskIndex].markAsNotDone();
        return tasks[taskIndex];
    }

    /**
     * Converts a user-facing task number to an array index.
     *
     * @param taskNumber one-based number of the task
     * @return zero-based array index for the task
     * @throws IllegalArgumentException if the task number does not identify a stored task
     */
    private int getTaskIndex(int taskNumber) {
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new IllegalArgumentException("Please provide a valid task number.");
        }
        return taskNumber - 1;
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
