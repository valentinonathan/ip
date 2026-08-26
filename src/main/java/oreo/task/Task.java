package oreo.task;

/**
 * Represents the shared state and behaviour of a task.
 */
public abstract class Task {
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

    /**
     * Returns the icon identifying this kind of task.
     *
     * @return task type icon
     */
    protected abstract String getTypeIcon();

    /**
     * Returns optional type-specific details to append to the task description.
     *
     * @return formatted task details, or an empty string when none apply
     */
    protected abstract String getDetails();

    protected abstract String storageStringRepresentation();

    /**
     * Returns a formatted representation of this task.
     *
     * @return task type, completion status, description, and type-specific details
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description + getDetails();
    }
}
