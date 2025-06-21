package pharma.chat.Client;

import pharma.chat.Server.ChatMsg;

import javax.net.ssl.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SSLClient {
    private SSLSocket socket;
    private AtomicBoolean connected = new AtomicBoolean(false);
    private ExecutorService executor = Executors.newCachedThreadPool();
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public SSLClient() {
        configSetting();
    }

    private void configSetting() {
        try {
            // SSL setup
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };
            sslContext.init(null, trustAllCerts, new SecureRandom());

            SSLSocketFactory factory = sslContext.getSocketFactory();
            socket = (SSLSocket) factory.createSocket(InetAddress.getLocalHost(), 3000);
            socket.setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.3"});

            // ✅ Set up handshake listener to create streams
            socket.addHandshakeCompletedListener(event -> {
                System.out.println("🔒 Handshake completed: " + event.getCipherSuite());

                try {
                    // ✅ Create streams ONCE after handshake
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.flush(); // Flush header immediately

                    inputStream = new ObjectInputStream(socket.getInputStream());

                    connected.set(true);

                    // ✅ Start threads AFTER streams are ready
                    handleThread();


                } catch (IOException e) {
                    System.err.println("Failed to initialize streams: " + e.getMessage());
                    connected.set(false);
                }
            });

            // Start handshake
            socket.startHandshake();

        } catch (Exception e) {
            System.err.println("SSL setup error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleThread() {
        System.out.println("Starting communication threads");
        executor.submit(new ReadThread());
        executor.submit(new WriterThread());
    }

    private class ReadThread implements Runnable {
        @Override
        public void run() {
            System.out.println("ReadThread started");

            while(connected.get() && !socket.isClosed()) {
                try {
                    System.out.println("Waiting for message...");

                    // ✅ Use existing stream
                    Object obj = inputStream.readObject();

                    if(obj instanceof ChatMsg chatMsg) {
                        System.out.println("Received: " + chatMsg);
                        process_message(chatMsg);
                    }

                } catch (SocketTimeoutException e) {
                    // Normal timeout, continue
                    continue;
                } catch (EOFException e) {
                    System.out.println("Server closed connection");
                    connected.set(false);
                    break;
                } catch (IOException e) {
                    System.err.println("Read error: " + e.getMessage());
                    connected.set(false);
                    break;
                } catch (ClassNotFoundException e) {
                    System.err.println("Unknown message type: " + e.getMessage());
                }
            }

            System.out.println("ReadThread terminated");
        }
    }

    private class WriterThread implements Runnable {
        @Override
        public void run() {
            System.out.println("WriterThread started");

            // Example: Send periodic heartbeat
            while(connected.get() && !socket.isClosed()) {
                try {
                    Thread.sleep(10000); // Wait 10 seconds

                    if(connected.get()) {

                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            System.out.println("WriterThread terminated");
        }
    }

    public void sendMessage(ChatMsg message) {
        try {
            if(outputStream != null && connected.get()) {
                outputStream.writeObject(message);
                outputStream.flush();
                System.out.println("Sent: " + message);
            }
        } catch (IOException e) {
            System.err.println("Send error: " + e.getMessage());
            connected.set(false);
        }
    }

    private void process_message(ChatMsg message) {
        System.out.println("Processing: " + message);
        // Your message processing logic
    }
}
