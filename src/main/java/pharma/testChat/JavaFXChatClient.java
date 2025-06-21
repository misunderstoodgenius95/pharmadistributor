package pharma.testChat;


import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A secure chat client designed to be used with a GUI like JavaFX.
 * This class handles all networking logic, including connecting, sending,
 * and receiving messages, and provides thread-safe queues for communication
 * with the UI thread.
 */
public class JavaFXChatClient {

    private final String name;
    private final ExecutorService executor;
    private final BlockingQueue<ChatMsg> incomingMessages;

    private SSLSocket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Constructs a new chat client.
     * @param name The name of the user for this client session.
     */
    public JavaFXChatClient(String name) {
        this.name = name;
        // A single background thread is sufficient for listening to the server.
        this.executor = Executors.newSingleThreadExecutor();
        this.incomingMessages = new LinkedBlockingQueue<>();
    }

    /**
     * Connects to the server and starts the listener thread.
     * @param host The server hostname.
     * @param port The server port.
     * @throws Exception if the connection fails.
     */
    public void connect(String host, int port) throws Exception {
        // Configure the client to trust the server's self-signed certificate.
        // In a real app, this might be handled more dynamically.
        System.setProperty("javax.net.ssl.trustStore", "clienttruststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");

        SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        this.socket = (SSLSocket) ssf.createSocket(host, port);

        // IMPORTANT: Create output stream and flush before creating input stream to avoid deadlock.
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.out.flush();
        this.in = new ObjectInputStream(socket.getInputStream());

        System.out.println("Successfully connected to secure server.");

        // Start the background thread to listen for incoming messages.
        startListener();
    }

    /** Read Thread
     * Submits the listener task to the executor service.
     */
    private void startListener() {
        Runnable listenerTask = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // This blocks until a message is received.
                    ChatMsg serverMsg = (ChatMsg) in.readObject();
                    // Add the received message to the queue for the UI thread to process.
                    incomingMessages.put(serverMsg);
                }
            } catch (Exception e) {
                // If an error occurs (e.g., server disconnects), we can signal this
                // by putting a special "error" message in the queue.
                System.out.println("Connection to server lost. Listener thread shutting down.");
                try {
                    incomingMessages.put(new ChatMsg("System", "Connection lost."));
                } catch (InterruptedException ignored) {}
            }
        };
        executor.submit(listenerTask);
    }

    /**
     * Sends a chat message to the server. This method is thread-safe.
     * @param messageText The text of the message to send.
     */
    public void sendMessage(String messageText) {
        if (out == null) {
            System.err.println("Cannot send message, not connected.");
            return;
        }
        try {
            ChatMsg userMsg = new ChatMsg(this.name, messageText);
            out.writeObject(userMsg);
            out.flush();
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    /**
     * Disconnects from the server and cleans up resources.
     */
    public void disconnect() {
        System.out.println("Disconnecting client...");
        executor.shutdownNow(); // Interrupt the listener thread.
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            // Ignore errors on disconnect.
        }
    }

    /**
     * Provides the UI thread with access to the queue of incoming messages.
     * The UI should poll this queue to display new messages.
     * @return The thread-safe queue of received messages.
     */
    public BlockingQueue<ChatMsg> getIncomingMessages() {
        return incomingMessages;
    }
}
