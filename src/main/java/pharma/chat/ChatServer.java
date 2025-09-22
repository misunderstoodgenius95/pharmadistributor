package pharma.chat;


import pharma.config.auth.UserService;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


public class ChatServer {

    private static final int PORT = 3000;
    private static final Logger logger = Logger.getLogger(ChatServer.class.getName());
    private static final ExecutorService clientExecutor = Executors.newCachedThreadPool();


    // A thread-safe map to hold all active client connections
    private static final Map<String, ThreadServerManager> activeClients = new ConcurrentHashMap<>();

    public ChatServer(UserService userService)  {

        try {
            SSLContext sslContext = createSSLContext();
            SSLServerSocketFactory ssf = sslContext.getServerSocketFactory();
            try (SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(PORT)) {

                serverSocket.setEnabledProtocols(new String[]{"TLSv1.3"});
                serverSocket.setEnabledCipherSuites(new String[]{"TLS_CHACHA20_POLY1305_SHA256"});
                serverSocket.setNeedClientAuth(false); // Client auth not required for this example

                logger.info("ChatPharmacist Server started on port " + PORT);

                while (true) {
                    SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                    logger.info("Client connected: " + clientSocket.getRemoteSocketAddress());
                    ThreadServerManager clientManager = new ThreadServerManager(clientSocket, userService);
                    clientExecutor.submit(clientManager);
                }
            }
        } catch (Exception e) {
            logger.severe("Server startup failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static SSLContext createSSLContext() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        // Load the keystore
        char[] password = "password".toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        try (FileInputStream fis = new FileInputStream("serverkeystore.jks")) {
            ks.load(fis, password);
        }

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        return sslContext;
    }

    /*public static void registerClient(String clientId, ThreadServerManager manager) {
        activeClients.put(clientId, manager);
        logger.info("Client registered: " + clientId);
    }

    public static void removeClient(String clientId) {
        activeClients.remove(clientId);
        logger.info("Client removed: " + clientId);
    }

    public static void broadcastMessage(ChatMsg message, String senderId) {
        logger.info("Broadcasting message from " + senderId + ": " + message.getPayload());
        for (Map.Entry<String, ThreadServerManager> entry : activeClients.entrySet()) {
            // Don't send the message back to the original sender
            if (!entry.getKey().equals(senderId)) {
                entry.getValue().sendMessage(message);
            }
        }
    }*/
}


