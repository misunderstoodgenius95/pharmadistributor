package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.Storage.FileStorage;
import pharma.chat.Client.ClientWebserver;
import pharma.config.PathConfig;
import pharma.config.auth.UserGateway;
import pharma.security.Stytch.StytchClient;
import pharma.Utility.TokenUtility;
import pharma.Utility.Chacha20;
import pharma.Utility.SHA256Signature;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Chat implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(Chat.class);
    public TextField text_field_id;
    public TextArea textarea_id;
    @FXML
    public Button btn_send_id;
    @FXML
    public Button btn_disconnect_id;
    public VBox aside_user;

    private ClientWebserver clientWebserver;
    private UserGateway userGateway;
    private List<String> pharmacists;
    private String email;
    private String selectedPharmacist; // Farmacista attualmente selezionato
    private ConcurrentLinkedQueue<String> messageQueue;
    private Thread queueProcessorThread;
    private final URI serverUri = URI.create("ws://localhost:8081");

    public Chat() {
        pharmacists = new ArrayList<>();
        messageQueue = new ConcurrentLinkedQueue<>();
        selectedPharmacist = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HashMap<String, String> hashMap_json = null;
        String user_id = "";

        try {
            hashMap_json = FileStorage.getProperties(
                    List.of("project_id", "secret", "url"),
                    new FileReader(PathConfig.STYTCH_CONF.getValue())
            );
            String jwt = FileStorage.getProperty("jwt", new FileReader(PathConfig.JWT.getValue()));
            user_id = TokenUtility.extract_sub(jwt);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        userGateway = new UserGateway(
                new StytchClient(
                        hashMap_json.get("project_id"),
                        hashMap_json.get("secret"),
                        hashMap_json.get("url")
                )
        );

        String body = userGateway.GettingUserInfoByUserId(user_id).getBody();
        email = TokenUtility.extractEmailByUserId(body);

        // Inizializza la connessione
        connectToServer();

        btn_aside();

        // Disabilita i pulsanti finché non c'è una connessione attiva
        btn_send_id.setDisable(true);
        btn_disconnect_id.setText("Disconnect");
    }

    private void connectToServer() {
        // Crea una nuova istanza del client WebSocket
        clientWebserver = new ClientWebserver(serverUri, email, messageQueue);
        clientWebserver.connect();

        // Avvia il processore della coda messaggi
        startQueueProcessor();

        log.info("Connecting to server...");
    }

    private void disconnectFromServer() {
        if (clientWebserver != null && clientWebserver.isOpen()) {
            clientWebserver.close();
            log.info("Disconnected from server");
        }

        // Ferma il thread di processamento della coda
        if (queueProcessorThread != null && queueProcessorThread.isAlive()) {
            queueProcessorThread.interrupt();
        }

        // Pulisci la lista dei farmacisti
        pharmacists.clear();
        selectedPharmacist = null;
        aside_user.getChildren().clear();

        // Disabilita i pulsanti
        btn_send_id.setDisable(true);
    }

    public void btn_aside() {
        aside_user.setOnMouseClicked(event -> {
            // Change to other pharmacist
        });
    }

    public String extracted_message(String message) throws Exception {
        JSONObject jsonObject = new JSONObject(message);
        log.info("Extracted_msg: " + jsonObject);
        String type = jsonObject.getString("type");

        switch (type) {
            case "Chat" -> {
                String from = jsonObject.getString("from");
                JSONObject message_cipher = new JSONObject(jsonObject.getString("messages"));
                String nonce = message_cipher.getString("nonce");
                String cipherText = message_cipher.getString("cipherText");
                String decryptedMessage = Chacha20.decryptedString(
                        Chacha20.decrypt(
                                Chacha20.hexToByte(cipherText),
                                Chacha20.hexToByte(nonce)
                        ).get()
                );
                return from + ": " + decryptedMessage;
            }
            case "Join" -> {
                btn_send_id.setDisable(false);
                btn_disconnect_id.setDisable(false);
                String from = jsonObject.getString("from");

                if (!pharmacists.contains(from)) {
                    pharmacists.add(from);

                    // Se non c'è un farmacista selezionato, seleziona il primo
                    if (selectedPharmacist == null) {
                        selectedPharmacist = from;
                    }

                    // Aggiungi il pulsante sulla UI thread
                    Platform.runLater(() -> {
                        Button pharmacistButton = new Button(from);
                        pharmacistButton.setOnAction(e -> {
                            selectedPharmacist = from;
                            log.info("Selected pharmacist: " + from);
                            textarea_id.appendText("--- Now chatting with: " + from + " ---\n");

                            // Opzionale: evidenzia visivamente il pulsante selezionato
                            aside_user.getChildren().forEach(node -> {
                                if (node instanceof Button) {
                                    node.setStyle("");
                                }
                            });
                            pharmacistButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                        });

                        // Se è il primo farmacista, evidenzialo
                        if (from.equals(selectedPharmacist)) {
                            pharmacistButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                        }

                        aside_user.getChildren().add(pharmacistButton);
                    });
                }
                return jsonObject.getString("message");
            }
            case "serverAuth" -> {
                log.info("Server Auth: " + message);
                String publicKey = jsonObject.getString("publicKey");
                String messagesSigned = jsonObject.getString("message");
                String signature = jsonObject.getString("signature");
                boolean value = SHA256Signature.verifySignature(messagesSigned, signature, publicKey);

                if (!value) {
                    clientWebserver.close();
                    return "ERROR: Server authentication failed!";
                } else {
                    return "Info: Connected to the server!";
                }
            }
            case "UserDisconnected" -> {
                log.info("User disconnected");
                String disconnectedEmail = jsonObject.getString("username");
                String disconnectionMessage = jsonObject.getString("message");

                // Rimuovi dalla lista
                pharmacists.remove(disconnectedEmail);

                // Se il farmacista disconnesso era quello selezionato
                if (disconnectedEmail.equals(selectedPharmacist)) {
                    selectedPharmacist = pharmacists.isEmpty() ? null : pharmacists.getFirst();

                    // Se non ci sono più farmacisti, disabilita l'invio
                    if (selectedPharmacist == null) {
                        Platform.runLater(() -> {
                            btn_send_id.setDisable(true);
                        });
                    }
                }

                // Rimuovi il pulsante dalla UI
                Platform.runLater(() -> {
                    aside_user.getChildren().removeIf(node ->
                            node instanceof Button &&
                                    ((Button) node).getText().equals(disconnectedEmail)
                    );

                    // Se c'è ancora un farmacista selezionato, evidenzialo
                    if (selectedPharmacist != null) {
                        aside_user.getChildren().forEach(node -> {
                            if (node instanceof Button) {
                                Button btn = (Button) node;
                                if (btn.getText().equals(selectedPharmacist)) {
                                    btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                                } else {
                                    btn.setStyle("");
                                }
                            }
                        });
                    }
                });

                return  disconnectionMessage;
            }
            case "Error" -> {
                log.error("Server error: " + jsonObject.getString("message"));
                return "ERROR: " + jsonObject.getString("message");
            }
        }

        return "";
    }

    private void startQueueProcessor() {
        // Ferma il vecchio thread se esiste
        if (queueProcessorThread != null && queueProcessorThread.isAlive()) {
            queueProcessorThread.interrupt();
        }

        queueProcessorThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String message = messageQueue.poll();

                    if (message != null) {
                        log.info("Received: " + message);

                        // Update UI on JavaFX thread
                        Platform.runLater(() -> {
                            try {
                                String extracted = extracted_message(message);
                                log.info("Extracted: " + extracted);

                                if (!extracted.isEmpty()) {
                                    textarea_id.appendText(extracted + "\n");
                                }
                            } catch (Exception e) {
                                log.error("Error processing message", e);
                                textarea_id.appendText("ERROR: Failed to process message\n");
                            }
                        });
                    }

                    // Small delay to avoid CPU spinning
                    Thread.sleep(50);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    log.error("Error processing queue", e);
                }
            }
        });

        queueProcessorThread.setDaemon(true);
        queueProcessorThread.setName("QueueProcessor");
        queueProcessorThread.start();
    }

    public void btn_action(ActionEvent event) {
        String messageText = text_field_id.getText();
        if (messageText == null || messageText.trim().isEmpty()) {
            return;
        }

        if (pharmacists.isEmpty() || selectedPharmacist == null) {
            textarea_id.appendText("ERROR: No pharmacists available\n");
            return;
        }

        textarea_id.appendText("Me → " + selectedPharmacist + ": " + messageText + "\n");

        // Encrypt message
        byte[] nonce = Chacha20.generateNonce();
        byte[] plaintext = messageText.getBytes(StandardCharsets.UTF_8);
        byte[] cipherText = Chacha20.encrypt(plaintext, nonce).get();
        String cipherTextHex = Chacha20.byteToHex(cipherText);

        // Create encrypted message object
        JSONObject encryptedMessage = new JSONObject();
        encryptedMessage.put("cipherText", cipherTextHex);
        encryptedMessage.put("nonce", Chacha20.byteToHex(nonce));

        // Create main message object
        JSONObject message = new JSONObject();
        message.put("from", email);
        message.put("to", selectedPharmacist);  // Usa il farmacista selezionato
        message.put("type", "Chat");
        message.put("messages", encryptedMessage.toString());

        // Send message
        if (clientWebserver != null && clientWebserver.isOpen()) {
            clientWebserver.send(message.toString());
            log.info("Message sent: " + message.toString());
            text_field_id.clear();
        } else {
            log.error("WebSocket is not open!");
            textarea_id.appendText("ERROR: Not connected to server\n");
        }
    }

    public void btn_disconnect_action(ActionEvent event) {
        if (clientWebserver != null && clientWebserver.isOpen()) {
            // Disconnetti
            disconnectFromServer();
            textarea_id.appendText("Disconnected from server\n");
            btn_disconnect_id.setText("Connect");
            btn_send_id.setDisable(true);
        } else {
            // Riconnetti creando una nuova istanza
            textarea_id.clear();
            textarea_id.appendText("Reconnecting to server...\n");
            connectToServer();
            btn_disconnect_id.setText("Disconnect");
        }
    }
}