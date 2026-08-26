/**
 * Handles all console input and output for the chatbot.
 */
public class Ui {
    /** A divider used to visually separate chatbot messages. */
    private static final String DIVIDER = "____________________________________________________________";

    /** The chatbot banner shown when the application starts. */
    private static final String BANNER = """
             ██████╗ ██████╗ ███████╗ ██████╗
            ██╔═══██╗██╔══██╗██╔════╝██╔═══██╗
            ██║   ██║██████╔╝█████╗  ██║   ██║
            ██║   ██║██╔══██╗██╔══╝  ██║   ██║
            ╚██████╔╝██║  ██║███████╗╚██████╔╝
             ╚═════╝ ╚═╝  ╚═╝╚══════╝ ╚═════╝

                 ◉ ◉ ◉  O R E O  ◉ ◉ ◉
                 ─────────────────────""";

    /** The greeting shown after the banner. */
    private static final String GREETING = """
            ____________________________________________________________
            Hello! I'm OREO, your personal cookie-themed chatbot: crisp, creamy, and ready to help.
            What can I do for you? Let's make today a little sweeter.
            ____________________________________________________________""";

    /** Displays the startup banner and greeting. */
    public void showWelcome() {
        System.out.println(BANNER);
        System.out.println(GREETING);
    }

    /** Displays the standard divider. */
    public void showDivider() {
        System.out.println(DIVIDER);
    }

    /** Displays the farewell message. */
    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
        showDivider();
    }

    /** Displays all tasks in the list. */
    public void showTaskList(TaskList tasks) {
        System.out.println(tasks);
        showDivider();
    }

    /** Displays confirmation that a task was added. */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        showDivider();
    }

    /** Displays confirmation that a task was marked as done. */
    public void showTaskMarked(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   [X] " + task.getDescription());
    }

    /** Displays confirmation that a task was marked as not done. */
    public void showTaskUnmarked(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   [ ] " + task.getDescription());
    }

    /** Displays confirmation that a task was deleted. */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task: \n" + task
                + "\n Now you have " + taskCount + " tasks in the list.");
    }

    /** Displays an error message. */
    public void showError(String message) {
        System.out.println(" " + message);
    }
}
