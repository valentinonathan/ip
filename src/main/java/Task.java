/**
 * Represents one task and whether it has been completed.
 */
public class Task {
    /** The text entered by the user to describe the task. */
    private final String description;

    /** Whether the task has been marked as completed. */
    private boolean isDone;

    /**
     * Creates a task that is initially not done.
     *
     * @param description text describing the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the task description.
     *
     * @return task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the display icon for this task's completion state.
     *
     * @return {@code X} for completed tasks, otherwise a blank space
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }
}
