package pharma;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
 * MAIN SERVER CLASS
 * This is the entry point that manages the entire chat system
 */
public class SecureChatServer {
    
    // Server configuration
    private final int port;
    private final SSLServerSocket serverSocket;
    private final ExecutorService threadPool;
    
    // SHARED DATA STRUCTURES - Thread-safe collections for managing clients
    // This map stores all active client connections
    private final ConcurrentHashMap<String, ClientHandler> activeClients = new ConcurrentHashMap<>();
    
    // This map stores chat rooms and their members
    private final ConcurrentHashMap<String, Set<String>> chatRooms = new ConcurrentHashMap<>();
    
    // Server state
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicInteger clientIdCounter = new AtomicInteger(0);
    
    /**
     * CONSTRUCTOR - Initializes the server
     */
    public SecureChatServer(int port, String keystorePath, char[] password) throws Exception {
        this.port = port;
        
        // STEP 1: Create SSL server socket
        this.serverSocket = createSSLServerSocket(keystorePath, password);
        
        // STEP 2: Create thread pool for handling clients
        this.threadPool = new ThreadPoolExecutor(
            10,                      // Core pool size
            50,                      // Maximum pool size
            60L,                     // Keep-alive time
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),  // Task queue
            new ThreadFactory() {
                private final AtomicInteger threadId = new AtomicInteger(0);
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setName("ChatWorker-" + threadId.incrementAndGet());
                    return t;
                }
            }
        );
        
        // STEP 3: Initialize default chat room
        chatRooms.put("lobby", ConcurrentHashMap.newKeySet());
    }
    
    /**
     * START SERVER - Main server loop
     */
    public void start() {
        running.set(true);
        System.out.println("ChatPharmacist server started on port " + port);
        
        // MAIN ACCEPT LOOP - Continuously accept new connections
        while (running.get()) {
            try {
                // STEP 1: Wait for client connection
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                
                // STEP 2: Configure socket
                clientSocket.setSoTimeout(300000); // 5 minute timeout
                clientSocket.setKeepAlive(true);
                
                // STEP 3: Generate unique client ID
                String clientId = "client-" + clientIdCounter.incrementAndGet();
                
                // STEP 4: Create handler and submit to thread pool
                ClientHandler handler = new ClientHandler(clientId, clientSocket);
                threadPool.submit(handler);
                
                System.out.println("New client connected: " + clientId);
                
            } catch (IOException e) {
                if (running.get()) {
                    System.err.println("Error accepting connection: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * CLIENT HANDLER - Manages individual client connections
     * This runs in a thread from the thread pool
     */
    private class ClientHandler implements Runnable {
        private final String clientId;
        private final SSLSocket socket;
        private String username;
        private String currentRoom = "lobby";
        
        // I/O streams
        private BufferedReader reader;
        private PrintWriter writer;
        
        // Message queues for async communication
        private final BlockingQueue<String> outgoingMessages = new LinkedBlockingQueue<>();
        
        // Thread management
        private Thread readerThread;
        private Thread writerThread;
        private final AtomicBoolean connected = new AtomicBoolean(true);
        
        public ClientHandler(String clientId, SSLSocket socket) {
            this.clientId = clientId;
            this.socket = socket;
            this.username = clientId; // Default username
        }
        
        @Override
        public void run() {
            try {
                // STEP 1: Initialize I/O streams
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);
                
                // STEP 2: Register client
                activeClients.put(clientId, this);
                joinRoom("lobby");
                
                // STEP 3: Send welcome message
                sendMessage("SYSTEM:Welcome to the chat server! You are " + clientId);
                sendMessage("SYSTEM:Type /help for commands");
                
                // STEP 4: Start reader and writer threads
                startReaderThread();
                startWriterThread();
                
                // STEP 5: Wait for threads to complete
                readerThread.join();
                writerThread.join();
                
            } catch (Exception e) {
                System.err.println("Error handling client " + clientId + ": " + e.getMessage());
            } finally {
                // STEP 6: Cleanup on disconnection
                disconnect();
            }
        }
        
        /**
         * READER THREAD - Handles incoming messages from client
         */
        private void startReaderThread() {
            readerThread = new Thread(() -> {
                try {
                    String message;
                    
                    // Continuously read messages
                    while (connected.get() && (message = reader.readLine()) != null) {
                        System.out.println(clientId + " sent: " + message);
                        
                        // Process the message
                        processMessage(message);
                    }
                    
                } catch (IOException e) {
                    System.out.println(clientId + " disconnected while reading");
                } finally {
                    connected.set(false);
                }
            }, clientId + "-reader");
            
            readerThread.start();
        }
        
        /**
         * WRITER THREAD - Handles outgoing messages to client
         */
        private void startWriterThread() {
            writerThread = new Thread(() -> {
                try {
                    while (connected.get()) {
                        // Wait for messages in queue (blocks if empty)
                        String message = outgoingMessages.poll(1, TimeUnit.SECONDS);
                        
                        if (message != null) {
                            // Send message to client
                            writer.println(message);
                            writer.flush();
                            
                            // Check for write errors
                            if (writer.checkError()) {
                                throw new IOException("Write error detected");
                            }
                        }
                    }
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (IOException e) {
                    System.out.println(clientId + " disconnected while writing");
                } finally {
                    connected.set(false);
                }
            }, clientId + "-writer");
            
            writerThread.start();
        }
        
        /**
         * MESSAGE PROCESSING - Routes messages based on type
         */
        private void processMessage(String message) {
            if (message.startsWith("/")) {
                // Handle commands
                processCommand(message);
            } else {
                // Regular chat message - broadcast to room
                broadcastToRoom(currentRoom, username + ": " + message, null);
            }
        }
        
        /**
         * COMMAND PROCESSING - Handles special commands
         */
        private void processCommand(String command) {
            String[] parts = command.split(" ", 2);
            String cmd = parts[0].toLowerCase();
            String args = parts.length > 1 ? parts[1] : "";
            
            switch (cmd) {
                case "/help":
                    sendMessage("SYSTEM:Available commands:");
                    sendMessage("SYSTEM:/nick <name> - Change nickname");
                    sendMessage("SYSTEM:/join <room> - Join a room");
                    sendMessage("SYSTEM:/rooms - List all rooms");
                    sendMessage("SYSTEM:/users - List users in current room");
                    sendMessage("SYSTEM:/msg <user> <message> - Private message");
                    sendMessage("SYSTEM:/quit - Disconnect");
                    break;
                    
                case "/nick":
                    if (!args.isEmpty()) {
                        String oldName = username;
                        username = args;
                        broadcastToRoom(currentRoom, 
                            "SYSTEM:" + oldName + " is now known as " + username, null);
                    }
                    break;
                    
                case "/join":
                    if (!args.isEmpty()) {
                        leaveRoom(currentRoom);
                        joinRoom(args);
                    }
                    break;
                    
                case "/rooms":
                    sendMessage("SYSTEM:Available rooms: " + String.join(", ", chatRooms.keySet()));
                    break;
                    
                case "/users":
                    listUsersInRoom();
                    break;
                    
                case "/msg":
                    handlePrivateMessage(args);
                    break;
                    
                case "/quit":
                    connected.set(false);
                    break;
                    
                default:
                    sendMessage("SYSTEM:Unknown command. Type /help for help.");
            }
        }
        
        /**
         * ROOM MANAGEMENT - Join a chat room
         */
        private void joinRoom(String roomName) {
            // Create room if it doesn't exist
            chatRooms.putIfAbsent(roomName, ConcurrentHashMap.newKeySet());
            
            // Add client to room
            Set<String> roomMembers = chatRooms.get(roomName);
            roomMembers.add(clientId);
            currentRoom = roomName;
            
            // Notify user
            sendMessage("SYSTEM:Joined room: " + roomName);
            
            // Notify others in room
            broadcastToRoom(roomName, "SYSTEM:" + username + " joined the room", clientId);
        }
        
        /**
         * ROOM MANAGEMENT - Leave a chat room
         */
        private void leaveRoom(String roomName) {
            Set<String> roomMembers = chatRooms.get(roomName);
            if (roomMembers != null) {
                roomMembers.remove(clientId);
                
                // Notify others in room
                broadcastToRoom(roomName, "SYSTEM:" + username + " left the room", clientId);
                
                // Remove empty rooms (except lobby)
                if (roomMembers.isEmpty() && !"lobby".equals(roomName)) {
                    chatRooms.remove(roomName);
                }
            }
        }
        
        /**
         * BROADCASTING - Send message to all users in a room
         */
        private void broadcastToRoom(String roomName, String message, String excludeClient) {
            Set<String> roomMembers = chatRooms.get(roomName);
            if (roomMembers != null) {
                for (String memberId : roomMembers) {
                    // Skip excluded client (usually the sender)
                    if (excludeClient != null && excludeClient.equals(memberId)) {
                        continue;
                    }
                    
                    // Get client handler and send message
                    ClientHandler client = activeClients.get(memberId);
                    if (client != null) {
                        client.sendMessage(message);
                    }
                }
            }
        }
        
        /**
         * PRIVATE MESSAGING
         */
        private void handlePrivateMessage(String args) {
            String[] parts = args.split(" ", 2);
            if (parts.length < 2) {
                sendMessage("SYSTEM:Usage: /msg <username> <message>");
                return;
            }
            
            String targetUser = parts[0];
            String message = parts[1];
            
            // Find target client
            for (ClientHandler client : activeClients.values()) {
                if (client.username.equals(targetUser)) {
                    client.sendMessage("PRIVATE from " + username + ": " + message);
                    sendMessage("PRIVATE to " + targetUser + ": " + message);
                    return;
                }
            }
            
            sendMessage("SYSTEM:User " + targetUser + " not found");
        }
        
        /**
         * LIST USERS in current room
         */
        private void listUsersInRoom() {
            Set<String> roomMembers = chatRooms.get(currentRoom);
            if (roomMembers != null) {
                List<String> usernames = new ArrayList<>();
                for (String memberId : roomMembers) {
                    ClientHandler client = activeClients.get(memberId);
                    if (client != null) {
                        usernames.add(client.username);
                    }
                }
                sendMessage("SYSTEM:Users in " + currentRoom + ": " + String.join(", ", usernames));
            }
        }
        
        /**
         * SEND MESSAGE - Adds message to outgoing queue
         */
        public void sendMessage(String message) {
            try {
                outgoingMessages.put(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        /**
         * DISCONNECT - Clean up client resources
         */
        private void disconnect() {
            if (connected.compareAndSet(true, false)) {
                System.out.println(clientId + " disconnecting...");
                
                // Leave all rooms
                leaveRoom(currentRoom);
                
                // Remove from active clients
                activeClients.remove(clientId);
                
                // Interrupt threads
                if (readerThread != null) readerThread.interrupt();
                if (writerThread != null) writerThread.interrupt();
                
                // Close socket
                try {
                    socket.close();
                } catch (IOException e) {
                    // Ignore
                }
                
                System.out.println(clientId + " disconnected");
            }
        }
    }
    
    /**
     * CREATE SSL SERVER SOCKET
     */
    private SSLServerSocket createSSLServerSocket(String keystorePath, char[] password) 
            throws Exception {
        // Load keystore
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(keystorePath)) {
            keyStore.load(fis, password);
        }
        
        // Create SSL context
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keyStore, password);
        
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
        
        // Create server socket
        SSLServerSocketFactory factory = sslContext.getServerSocketFactory();
        SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(port);
        
        // Configure SSL
        serverSocket.setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.3"});
        
        return serverSocket;
    }
    
    /**
     * SHUTDOWN - Gracefully stop the server
     */
    public void shutdown() {
        running.set(false);
        
        // Close server socket
        try {
            serverSocket.close();
        } catch (IOException e) {
            // Ignore
        }
        
        // Disconnect all clients
        for (ClientHandler client : activeClients.values()) {
            client.disconnect();
        }
        
        // Shutdown thread pool
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
        }
    }
    
    /**
     * MAIN METHOD - Entry point
     */
    public static void main(String[] args) {
        try {
            SecureChatServer server = new SecureChatServer(
                8443, 
                "server.keystore", 
                "password".toCharArray()
            );
            
            // Shutdown hook for graceful shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
            
            // Start server
            server.start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}