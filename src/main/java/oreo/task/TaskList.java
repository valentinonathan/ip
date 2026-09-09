package oreo.task;

import oreo.exception.OreoException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Stores the tasks entered during one run of the chatbot.
 */
public class TaskList {
    /** Maximum number of tasks that the fixed-capacity list can store. */
    private static final int MAX_TASK_COUNT = 100;

    /** The fixed-capacity array that holds tasks. */
    private final Task[] tasks;

    /** The number of tasks currently stored. */
    private int taskCount;

    /** Creates an empty task list. */
    public TaskList() {
        this("");
    }

    /**
     * Creates a task list containing the supplied tasks in the given order.
     *
     * @param initialTasks zero or more tasks to add to the list
     * @throws OreoException if more tasks than the list can hold are supplied
     */
    public TaskList(Task... initialTasks) {
        this.tasks = new Task[MAX_TASK_COUNT];
        for (Task task : initialTasks) {
            addTask(task);
        }
    }

    /**
     * Creates a task list based on the content from the storage
     *
     * @param content the storage string representation of the tasks
     */
    public TaskList(String content) {
        String[] tasksStr = content.split("\\R");
        this.tasks = new Task[MAX_TASK_COUNT];

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
        assert task != null : "Only constructed tasks may be stored.";
        if (taskCount >= tasks.length) {
            throw new OreoException("Task list is full");
        }
        tasks[taskCount] = task;
        taskCount++;
        assert tasks[taskCount - 1] == task : "Adding a task must occupy the next list position.";
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
        this.tasks[taskCount - 1] = null;
        this.taskCount--;
        assert tasks[taskCount] == null : "The first unused array position must be empty after deletion.";
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
        int taskIndex = taskNumber - 1;
        assert taskIndex >= 0 && taskIndex < taskCount : "A valid task number must map inside the stored range.";
        return taskIndex;
    }

    public String storageStringRepresentation() {
        String lineSeparator = System.lineSeparator();
        return Arrays.stream(tasks, 0, taskCount)
                .map(Task::storageStringRepresentation)
                .collect(Collectors.joining(lineSeparator, "", taskCount == 0 ? "" : lineSeparator));
    }

    /**
     * Returns all tasks whose descriptions contain the supplied keyword, ignoring letter case.
     *
     * @param keyword text to search for in task descriptions
     * @return a numbered display of the matching tasks
     */
    public String findTasks(String keyword) {
        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);
        List<Task> matchingTasks = Arrays.stream(tasks, 0, taskCount)
                .filter(task -> task.getDescription().toLowerCase(Locale.ROOT).contains(normalizedKeyword))
                .toList();
        return formatNumberedTasks(" Here are the matching tasks in your list:", matchingTasks);
    }

    /**
     * Returns a numbered display of every stored task and its completion status.
     *
     * @return the formatted task list
     */
    @Override
    public String toString() {
        return formatNumberedTasks(" Here are the tasks in your list:",
                Arrays.asList(tasks).subList(0, taskCount));
    }

    /** Returns a header followed by the supplied tasks, numbered from one. */
    private String formatNumberedTasks(String header, List<Task> tasksToFormat) {
        String lineSeparator = System.lineSeparator();
        return IntStream.range(0, tasksToFormat.size())
                .mapToObj(index -> " " + (index + 1) + "." + tasksToFormat.get(index))
                .collect(Collectors.joining(lineSeparator,
                        header + (tasksToFormat.isEmpty() ? "" : lineSeparator), ""));
    }
}
