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

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            System.out.println("____________________________________________________________");

            if (command.equals("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }

            System.out.println(" " + command);
            System.out.println("____________________________________________________________");
        }
    }
}
