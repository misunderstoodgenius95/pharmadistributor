package pharma.testChat;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import java.util.Optional;

/**
 * A simple JavaFX GUI application for the secure chat client.
 * This class creates the user interface and integrates with the chat.JavaFXChatClient
 * to handle networking in the background.
 */
public class ChatApplication extends Application {

    private JavaFXChatClient client;
    private final ObservableList<String> messages = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Secure ChatPharmacist");

        // --- 1. Prompt for user's name ---
 /*       TextInputDialog nameDialog = new TextInputDialog("User");
        nameDialog.setTitle("Login");
        nameDialog.setHeaderText("Enter your name to join the chat.");
        nameDialog.setContentText("Name:");
        Optional<String> result = nameDialog.showAndWait();
        if (result.isEmpty() || result.get().isBlank()) {
            Platform.exit();
            return;
        }

        String userName = result.get();


  */
        // --- 2. Create UI Components ---
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        ListView<String> messageListView = new ListView<>(messages);
        root.setCenter(messageListView);

        TextField messageField = new TextField();
        messageField.setPromptText("Type a message...");
        Button sendButton = new Button("Send");

        HBox inputBox = new HBox(10, messageField, sendButton);
        HBox.setHgrow(messageField, Priority.ALWAYS);
        root.setBottom(inputBox);

        // --- 3. Initialize and connect the network client ---
        client = new JavaFXChatClient("luca");
        try {
            client.connect("localhost", 8443);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Could not connect to the server:\n" + e.getMessage()).showAndWait();
            Platform.exit();
            return;
        }

        // --- 4. Start a background service to listen for messages ---
        MessageListenerService listenerService = new MessageListenerService();
        listenerService.start();

        // --- 5. Set up UI event handlers ---
        sendButton.setOnAction(event -> sendMessage(messageField));
        messageField.setOnAction(event -> sendMessage(messageField)); // Send on Enter key

        // --- 6. Handle window close event ---
        primaryStage.setOnCloseRequest(event -> {
            client.disconnect();
            Platform.exit();
        });

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Sends the message from the input field and clears it.
     * @param messageField The TextField containing the message.
     */
    private void sendMessage(TextField messageField) {
        String messageText = messageField.getText();
        if (messageText != null && !messageText.isBlank()) {
            client.sendMessage(messageText);
            messages.add("You: " + messageText); // Add our own message to the view
            messageField.clear();
        }
    }

    /**
     * A JavaFX Service that runs in the background to listen for incoming messages
     * without freezing the UI.
     */
    private class MessageListenerService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    // This loop runs on a background thread
                    while (!isCancelled()) {
                        try {
                            // take() will block until a message is available
                            ChatMsg receivedMsg = client.getIncomingMessages().take();

                            // Use Platform.runLater to update the UI on the JavaFX Application Thread
                            Platform.runLater(() -> {
                                messages.add(receivedMsg.toString());
                            });
                        } catch (InterruptedException e) {
                            // If the task is cancelled, break the loop
                            if (isCancelled()) {
                                break;
                            }
                        }
                    }
                    return null;
                }
            };
        }
    }
}
