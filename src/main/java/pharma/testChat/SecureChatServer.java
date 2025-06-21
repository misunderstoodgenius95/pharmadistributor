package pharma.testChat;


import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SecureChatServer {

    private final int port;
    private final ExecutorService clientExecutor;
    // A thread-safe set to hold all active client handlers.
    private final Set<ClientHandler> clientHandlers = new CopyOnWriteArraySet<>();

    public SecureChatServer(int port) {
        this.port = port;
        this.clientExecutor = Executors.newCachedThreadPool();
    }

    public void start() {
        try {
            // Setup SSL context
            char[] password = "password".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("serverkeystore.jks"), password);


            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);
            SSLContext sslContext=SSLContext.getInstance("TLSv1.3");
            sslContext.init(kmf.getKeyManagers(),null,null);
            SSLServerSocketFactory ssf = sslContext.getServerSocketFactory();

            try (SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(port)) {
                System.out.println("Secure ChatPharmacist Server started on port " + port);

                while (true) {
                    SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());
                    ClientHandler handler = new ClientHandler(clientSocket, this);
                    clientHandlers.add(handler);
                    clientExecutor.submit(handler);
                }
            }
        } catch (Exception e) {
            System.err.println("Server startup error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Broadcasts a message to all connected clients.
     * @param message The message to broadcast.
     * @param sender The client handler who sent the original message, to avoid echoing.
     */
    void broadcast(ChatMsg message, ClientHandler sender) {
        System.out.println("Broadcasting: " + message);
        for (ClientHandler client : clientHandlers) {
            if (client != sender) { // Don't send the message back to the original sender
                client.sendMessage(message);
            }
        }
    }

    /**
     * Removes a client handler from the active set when they disconnect.
     * @param handler The client handler to remove.
     */
    void removeClient(ClientHandler handler) {
        clientHandlers.remove(handler);
        System.out.println("Client disconnected: " + handler.getClientAddress());
    }

    public static void main(String[] args) {
        int port = 8443;
        SecureChatServer server = new SecureChatServer(port);
        server.start();
    }
}

/**
 * Handles a single client connection.
 */
class ClientHandler implements Runnable {
    private final SSLSocket socket;
    private final SecureChatServer server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String clientAddress;

    public ClientHandler(SSLSocket socket, SecureChatServer server) {
        this.socket = socket;
        this.server = server;
        this.clientAddress = socket.getRemoteSocketAddress().toString();
    }

    public String getClientAddress() {
        return clientAddress;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                ChatMsg receivedMsg = (ChatMsg) in.readObject();
                server.broadcast(receivedMsg, this);
            }
        } catch (EOFException | ClassNotFoundException e) {
            // Client disconnected gracefully or sent an invalid object.
        } catch (IOException e) {
            System.err.println("IO Error for client " + clientAddress + ": " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    /**
     * Sends a message to this specific client.
     * @param message The message to send.
     */
    public void sendMessage(ChatMsg message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println("Failed to send message to " + clientAddress);
            cleanup();
        }
    }

    private void cleanup() {
        server.removeClient(this);
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            // Ignore exceptions during cleanup
        }
    }
}
