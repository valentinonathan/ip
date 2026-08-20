import java.util.Scanner;

/**
 * The entry point for the chatbot application.
 */
public class Oreo {
    /**
     * Runs the chatbot command loop.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        String banner = """
                 ██████╗ ██████╗ ███████╗ ██████╗
                ██╔═══██╗██╔══██╗██╔════╝██╔═══██╗
                ██║   ██║██████╔╝█████╗  ██║   ██║
                ██║   ██║██╔══██╗██╔══╝  ██║   ██║
                ╚██████╔╝██║  ██║███████╗╚██████╔╝
                 ╚═════╝ ╚═╝  ╚═╝╚══════╝ ╚═════╝
                
                     ◉ ◉ ◉  O R E O  ◉ ◉ ◉
                     ─────────────────────""";
        String greet = """
                ____________________________________________________________
                Hello! I'm OREO, your personal cookie-themed chatbot: crisp, creamy, and ready to help.
                What can I do for you? Let's make today a little sweeter.
                ____________________________________________________________""";
        System.out.println(banner);
        System.out.println(greet);

        TaskList tasks = new TaskList(100);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            System.out.println("____________________________________________________________");

            if (command.equals("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (command.equals("list")) {
                System.out.println(tasks);
                System.out.println("____________________________________________________________");
                continue;
            } else if (command.startsWith("mark ")) {
                try {
                    int taskNumber = Integer.parseInt(command.substring(5).trim());
                    Task task = tasks.markTask(taskNumber);
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   [X] " + task.getDescription());
                } catch (NumberFormatException e) {
                    System.out.println(" Please provide a task number to mark.");
                } catch (OreoException e) {
                    System.out.println(" " + e.getMessage());
                }
                System.out.println("____________________________________________________________");
                continue;
            } else if (command.startsWith("unmark ")) {
                try {
                    int taskNumber = Integer.parseInt(command.substring(7).trim());
                    Task task = tasks.unmarkTask(taskNumber);
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   [ ] " + task.getDescription());
                } catch (NumberFormatException e) {
                    System.out.println(" Please provide a task number to unmark.");
                } catch (OreoException e) {
                    System.out.println(" " + e.getMessage());
                }
                System.out.println("____________________________________________________________");
                continue;
            } else if (command.startsWith("todo ")) {
                addTask(tasks, new Todo(command.substring(5).trim()));
                continue;
            } else if (command.startsWith("deadline ")) {
                try {
                    addTask(tasks, createDeadline(command));
                } catch (OreoException e) {
                    System.out.println(" " + e.getMessage());
                    System.out.println("____________________________________________________________");
                }
                continue;
            } else if (command.startsWith("event ")) {
                try {
                    addTask(tasks, createEvent(command));
                } catch (OreoException e) {
                    System.out.println(" " + e.getMessage());
                    System.out.println("____________________________________________________________");
                }
                continue;
            } else if (command.startsWith("delete ")) {
                try {
                    int taskNumber = Integer.parseInt(command.substring(7).trim());
                    Task temp = tasks.deleteTask(taskNumber);
                    System.out.println("Noted. I've removed this task: \n" + temp + "\n Now you have "
                            + tasks.getTaskCount() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } catch (NumberFormatException e) {
                    System.out.println(" Please provide a task number to delete.");
                } catch (OreoException e) {
                    System.out.println(" " + e.getMessage());
                    System.out.println("____________________________________________________________");
                }
                continue;
            }

            addTask(tasks, new Todo(command));
        }
    }

    /**
     * Adds a task and prints its confirmation message.
     *
     * @param tasks list to receive the task
     * @param task task to add
     */
    private static void addTask(TaskList tasks, Task task) {
        tasks.addTask(task);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + tasks.getTaskCount() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static void deleteTask(TaskList tasks, String command) {

    }

    /**
     * Creates a deadline from a command containing a description and a {@code /by} value.
     *
     * @param command deadline command entered by the user
     * @return a deadline task
     */
    private static Deadline createDeadline(String command) {
        int byMarker = command.indexOf(" /by ");
        if (byMarker == -1) {
            throw new OreoException("Use: deadline <description> /by <date/time>.");
        }
        String description = command.substring("deadline ".length(), byMarker).trim();
        String by = command.substring(byMarker + " /by ".length()).trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new OreoException("Use: deadline <description> /by <date/time>.");
        }
        return new Deadline(description, by);
    }

    /**
     * Creates an event from a command containing a description, {@code /from}, and {@code /to} values.
     *
     * @param command event command entered by the user
     * @return an event task
     */
    private static Event createEvent(String command) {
        int fromMarker = command.indexOf(" /from ");
        int toMarker = command.indexOf(" /to ", fromMarker + " /from ".length());
        if (fromMarker == -1 || toMarker == -1) {
            throw new OreoException("Use: event <description> /from <date/time> /to <date/time>.");
        }
        String description = command.substring("event ".length(), fromMarker).trim();
        String from = command.substring(fromMarker + " /from ".length(), toMarker).trim();
        String to = command.substring(toMarker + " /to ".length()).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new OreoException("Use: event <description> /from <date/time> /to <date/time>.");
        }
        return new Event(description, from, to);
    }
}
