/**
 * Stores the tasks entered during one run of the chatbot.
 */
public class TaskList {
    /** The fixed-capacity array that holds task descriptions. */
    private final String[] tasks;

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
        this.tasks = new String[size];
        this.taskCount = 0;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task task description to store
     * @throws IllegalStateException if the task list is full
     */
    public void addTask(String task) {
        if (taskCount >= tasks.length) {
            throw new IllegalStateException("Task list is full");
        }
        tasks[taskCount] = task;
        taskCount++;
    }

    /**
     * Returns a numbered display of every stored task.
     *
     * @return the formatted task list
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < taskCount; i++) {
            result.append(" ").append(i + 1).append(". ").append(tasks[i]);
            if (i < taskCount - 1) {
                result.append(System.lineSeparator());
            }
        }
        return result.toString();
    }
}
