package oreo.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a start time and an end time.
 */
public class Event extends Task {
    /** The time at which this event starts. */
    private final LocalDate from;

    /** The time at which this event ends. */
    private final LocalDate to;

    /**
     * Creates an event task.
     *
     * @param description text describing the event
     * @param from date or time at which the event starts
     * @param to date or time at which the event ends
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    @Override
    protected String getTypeIcon() {
        return "E";
    }

    @Override
    protected String getDetails() {
        return " (from: " + this.from.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + " to: " + this.to.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    @Override
    protected String storageStringRepresentation() {
        return this.getTypeIcon() + " | " + this.getStatusIcon() + " | " + this.getDescription() + " | "
                + this.from + " | " + this.to;
    }
}
