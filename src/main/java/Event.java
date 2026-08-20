/**
 * Represents a task with a start time and an end time.
 */
public class Event extends Task {
    /** The time at which this event starts. */
    private final String from;

    /** The time at which this event ends. */
    private final String to;

    /**
     * Creates an event task.
     *
     * @param description text describing the event
     * @param from date or time at which the event starts
     * @param to date or time at which the event ends
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    protected String getTypeIcon() {
        return "E";
    }

    @Override
    protected String getDetails() {
        return " (from: " + from + " to: " + to + ")";
    }
}
