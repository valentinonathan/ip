package oreo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/** Controls the conversation display and input area in Oreo's main window. */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Oreo oreo;

    /** Connects the controller to the chatbot and shows its greeting. */
    public void setOreo(Oreo oreo) {
        this.oreo = oreo;
        addDialog(oreo.getWelcomeMessage(), "bot-message");
        Platform.runLater(() -> userInput.requestFocus());
    }

    /** Sends the command in the input field when the user presses Enter or Send. */
    @FXML
    private void handleUserInput() {
        String command = userInput.getText().trim();
        if (command.isEmpty() || oreo == null) {
            return;
        }

        addDialog(command, "user-message");
        userInput.clear();
        addDialog(oreo.getResponse(command), "bot-message");

        if (oreo.hasExited()) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }

    /** Adds one chat bubble and scrolls the conversation to it. */
    private void addDialog(String message, String styleClass) {
        Label dialog = new Label(message);
        dialog.setWrapText(true);
        dialog.setMaxWidth(390);
        dialog.getStyleClass().addAll("dialog", styleClass);
        if (message.startsWith("Here are the tags and their tasks:")) {
            dialog.getStyleClass().add("tag-summary");
        }
        dialogContainer.getChildren().add(dialog);
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }
}
