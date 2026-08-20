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
                } catch (IllegalArgumentException e) {
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
                } catch (IllegalArgumentException e) {
                    System.out.println(" " + e.getMessage());
                }
                System.out.println("____________________________________________________________");
                continue;
            }

            tasks.addTask(new Task(command));
            System.out.println(" added: " + command);
            System.out.println("____________________________________________________________");
        }
    }
}
