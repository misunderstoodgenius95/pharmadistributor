package pharma.test2;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import pharma.config.Utility;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatClientConnection {
    private final String host;
    private final int port;
    private final String jwt;
   // private final Consumer<ChatMsg> onMessageCallback;
    private final Logger logger = Logger.getLogger(ChatClientConnection.class.getName());
    private final ObjectProperty<ChatMsg> listener_msg;
    private SSLSocket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private final AtomicBoolean isConnected = new AtomicBoolean(false);
    private final BlockingQueue<ChatMsg> outgoingMessages = new LinkedBlockingQueue<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    public ChatClientConnection(String host, int port, String jwt,ObjectProperty<ChatMsg> listener_msg){// Consumer<ChatMsg> onMessageCallback) {
        this.host = host;
        this.port = port;
        this.jwt = jwt;
       // this.onMessageCallback = onMessageCallback;
        this.listener_msg=listener_msg;

    }

    public void connect() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, createTrustAllManagers(), new java.security.SecureRandom());
            SSLSocketFactory factory = sslContext.getSocketFactory();
            socket = (SSLSocket) factory.createSocket(host, port);
            socket.setEnabledCipherSuites(new String[]{"TLS_CHACHA20_POLY1305_SHA256"});
            socket.startHandshake();

            // Crucial: Create output stream and flush *before* creating input stream
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(socket.getInputStream());

            isConnected.set(true);
            logger.info("Successfully connected to server.");

            executor.submit(this::readLoop);
            executor.submit(this::writeLoop);

            // Send the initial message to register with the server
            sendMessage(new ChatMsg(jwt, "Joining", Command.STARTING, "","SYSTEM"));

        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {

            logger.log(Level.SEVERE, "Connection failed", e);
            cleanup();
            Platform.runLater(()->{
                Utility.create_alert(Alert.AlertType.ERROR,"Problems Error",e.getMessage());
            });


        }



    }

    private void readLoop() {
        while (isConnected.get()) {
            try {
                Object obj = inputStream.readObject();
                if (obj instanceof ChatMsg msg) {
                   listener_msg.setValue(msg);

                }
            } catch (EOFException | SocketException e) {
                logger.warning("Disconnected from server.");
                break;
            } catch (IOException | ClassNotFoundException e) {
                if(isConnected.get()) logger.log(Level.SEVERE, "Error reading from server", e);
                break;
            }
        }
        cleanup();
    }

    private void writeLoop() {
        try {
            while (isConnected.get()) {
                ChatMsg msg = outgoingMessages.take();
                outputStream.writeObject(msg);
                outputStream.flush();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            if(isConnected.get()) logger.log(Level.WARNING, "Error writing to server", e);
        }
        cleanup();
    }

    public void sendMessage(ChatMsg msg) {
        if (isConnected.get()) {
            outgoingMessages.add(msg);
        }
    }

    public void disconnect() {
        sendMessage(new ChatMsg(jwt, "Leaving", Command.CLOSING, "",""));
        cleanup();
    }

    private void cleanup() {
        if (isConnected.compareAndSet(true, false)) {
            logger.info("Cleaning up connection...");
            executor.shutdownNow();
            try {
                if (!socket.isClosed()) socket.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    // A trust manager that does not validate certificate chains.
    // WARNING: This is insecure and should ONLY be used for self-signed certificates in development.
    private static TrustManager[] createTrustAllManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };
    }
}