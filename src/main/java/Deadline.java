/**
 * Represents a task that must be completed by a specified time.
 */
public class Deadline extends Task {
    /** The date or time by which this task should be completed. */
    private final String by;

    /**
     * Creates a deadline task.
     *
     * @param description text describing the task
     * @param by date or time by which the task should be completed
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    protected String getTypeIcon() {
        return "D";
    }

    @Override
    protected String getDetails() {
        return " (by: " + by + ")";
    }

    @Override
    protected String storageStringRepresentation() {
        return this.getTypeIcon() + " | " + this.getStatusIcon() + " | " + this.getDescription() + " | " + this.by;
    }
}
