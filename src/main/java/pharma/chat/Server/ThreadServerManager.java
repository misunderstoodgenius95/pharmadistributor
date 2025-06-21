/*

package pharma.chat.Server;

import pharma.config.auth.ExpireToken;
import pharma.config.auth.UserService;
import pharma.config.auth.UserServiceResponse;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.SocketTimeoutException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadServerManager implements Runnable {

    private  SSLSocket sslSocket;
    private  String client_socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private ExecutorService executor_read;
    private ExecutorService executor_write;
    private  AtomicBoolean connection;
    private  AtomicBoolean shutdownRequested;
    private  ConcurrentLinkedQueue<ChatMsg> outgoing_msg;
   // private  BlockingQueue<ChatMsg> outgoing_msg;
    private  UserService userService;
    private  Logger logger;

    // Timeout constants
    private static final int SOCKET_TIMEOUT_MS = 30000; // 30 seconds instead of 30 million
    private static final int WRITE_POLL_TIMEOUT_SEC = 1;
    private static final int SHUTDOWN_TIMEOUT_SEC = 5;

    public ThreadServerManager() {
    }

    public ThreadServerManager(SSLSocket sslSocket, UserService userService) {
        this.sslSocket = sslSocket;
        this.client_socket = sslSocket.getRemoteSocketAddress().toString();
        this.logger = Logger.getLogger(ThreadServerManager.class.getName());
        this.userService = userService;

        // Initialize thread-safe collections and state
        this.connection = new AtomicBoolean(false);
        this.shutdownRequested = new AtomicBoolean(false);
        this.outgoing_msg=new ConcurrentLinkedQueue<>();
//       this.outgoing_msg = new LinkedBlockingDeque<>();

        // Create named thread pools for better debugging
        this.executor_read = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "Reader-" + client_socket);
            t.setDaemon(true);
            return t;
        });
        this.executor_write = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "Writer-" + client_socket);
            t.setDaemon(true);
            return t;
        });

        logger.info("ThreadServerManager created for client: " + client_socket);
    }

    @Override
    public void run() {
        try {
            initializeConnection();
            startCommunicationThreads();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize connection for " + client_socket, e);
        } finally {
            // Ensure cleanup happens regardless of how we exit
        }
    }

    private void initializeConnection() throws IOException {
        sslSocket.setSoTimeout(SOCKET_TIMEOUT_MS);
        sslSocket.startHandshake();

        // Initialize streams
        outputStream = new ObjectOutputStream(sslSocket.getOutputStream());
        outputStream.flush(); // Important: flush the header
        inputStream = new ObjectInputStream(sslSocket.getInputStream());

        connection.set(true);
        logger.info("Connection initialized successfully for " + client_socket);
    }

    private void startCommunicationThreads() {
        startReader();
        startWriter();
    }

    private void startReader() {
        executor_read.execute(() -> {
            logger.info("Reader thread started for " + client_socket);

            try {
                while (connection.get() && !shutdownRequested.get()) {
                    logger.info("Read ");
                    try {
                        if(inputStream!=null) {
                            Object object = inputStream.readObject();

                            if (object instanceof ChatMsg chatMsg) {
                                handleChatMessage(chatMsg);
                            } else {
                                logger.warning("Received unexpected object type: " +
                                        (object != null ? object.getClass().getName() : "null"));
                            }
                        }

                    } catch (EOFException e) {
                        logger.info("Client  "+ "disconnected gracefully (EOF)");
                        break;

                    } catch (SocketTimeoutException e) {
                        // Timeout is normal, just continue if connection is still active
                        logger.fine("Socket read timeout for " + client_socket + " (normal)");
                        continue;

                    } catch (ClassNotFoundException e) {
                        logger.warning("Unknown class received from "  + ": " + e.getMessage());
                        // Continue processing - might be version mismatch

                    } catch (IOException e) {
                        if (connection.get()) {
                            logger.info("IO error reading from "  +  e.getMessage());
                        }
                        break;
                    }
                }

            } catch (Exception e) {
                logger.log(Level.WARNING, "Unexpected error in reader thread for " +  e);
            } finally {
                logger.info("Reader thread ending for " + client_socket);
                requestShutdown();
            }
        });
    }

    private void handleChatMessage(ChatMsg chatMsg) {
        try {
            String info = validate_token(chatMsg.getJwt());

        } catch (ExpireToken e) {
            logger.warning("Expired token received from " + client_socket);
            // Could send error response to client here

        } catch (Exception e) {
            logger.log(Level.WARNING, "Error processing chat message from " + client_socket, e);
        }
    }

    private void startWriter() {
        executor_write.execute(() -> {
            logger.info("Writer thread started for " + client_socket);

            try {
                while (connection.get()) {
                    logger.info("Write ");
                    try {
                        if(!outgoing_msg.isEmpty() &&  outputStream!=null) {
                            ChatMsg chatMsg = outgoing_msg.poll();

                            if (chatMsg != null && outputStream != null) {
                                logger.info("chatmsg is not null");


                                outputStream.writeObject(chatMsg);
                                outputStream.flush();
                                outputStream.reset();


                                logger.fine("Message sent to " + client_socket);

                            }
                        }

                //   } catch (InterruptedException e) {
                    //    logger.info("Writer thread for    was interrupted");
                 //       Thread.currentThread().interrupt();
               //         break;

                    } catch (IOException e) {
                        if (connection.get()) {
                            logger.warning("IO error writing to " + client_socket + ": " + e.getMessage());
                        }
                        break;
                    }
                }

            } catch (Exception e) {
                logger.log(Level.WARNING, "Unexpected error in writer thread for " + client_socket, e);
            } finally {
                logger.info("Writer thread ending for " + client_socket);
                requestShutdown();
            }
        });
    }

    String validate_token(String token) throws ExpireToken {
        UserServiceResponse response = userService.authenticate_jwt(token);
        return response.getBody();
    }

    public void store_message(ChatMsg chatMsg) {


             outgoing_msg.add(chatMsg);
             logger.info("Outgoing insert with "+outgoing_msg.size());

    }

    public void setConnection(boolean value) {
        connection.set(value);
        if (!value) {
            requestShutdown();
        }
    }

    private void requestShutdown() {
        if (shutdownRequested.compareAndSet(false, true)) {
            logger.info("Shutdown requested for " + client_socket);
            connection.set(false);
        }
    }

    private void cleanup() {
        logger.info("Starting cleanup for " + client_socket);

        requestShutdown();

        // Shutdown executors
        shutdownExecutor(executor_read, "Reader");
        shutdownExecutor(executor_write, "Writer");

        // Close streams
        closeStream(outputStream, "OutputStream");
        closeStream(inputStream, "InputStream");

        // Close socket
        try {
            if (sslSocket != null && !sslSocket.isClosed()) {
                sslSocket.close();
                logger.info("Socket closed for " + client_socket);
            }
        } catch (IOException e) {
            logger.warning("Error closing socket for " + client_socket + ": " + e.getMessage());
        }

        // Clear the outgoing message queue
        int droppedMessages = outgoing_msg.size();
        outgoing_msg.clear();
        if (droppedMessages > 0) {
            logger.info("Dropped " + droppedMessages + " queued messages for " + client_socket);
        }

        logger.info("Cleanup completed for " + client_socket);
    }

    private void shutdownExecutor(ExecutorService executor, String name) {
        if (executor != null && !executor.isShutdown()) {
            try {
                executor.shutdown();
                if (!executor.awaitTermination(SHUTDOWN_TIMEOUT_SEC, TimeUnit.SECONDS)) {
                    logger.warning(name + " executor did not terminate gracefully for " + client_socket);
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                logger.warning("Interrupted while shutting down " + name + " executor for " + client_socket);
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    private void closeStream(Closeable stream, String name) {
        if (stream != null) {
            try {
                stream.close();
                logger.fine(name + " closed for " + client_socket);
            } catch (IOException e) {
                logger.warning("Error closing " + name + " for " + client_socket + ": " + e.getMessage());
            }
        }
    }

    // Public method to trigger cleanup (useful for testing or manual shutdown)
    public void shutdown() {
        cleanup();
    }

    // Getters for monitoring
    public boolean isConnected() {
        return connection.get() && !shutdownRequested.get();
    }

    public int getQueuedMessageCount() {
        return outgoing_msg.size();
    }

    public String getClientAddress() {
        return client_socket;
    }
} */
/*
package pharma.chat.Server;

import org.apache.commons.math3.analysis.function.Log;
import pharma.Controller.subpanel.ChatPharmacist;
import pharma.chat.Command;
import pharma.config.auth.ExpireToken;
import pharma.config.auth.UserService;
import pharma.config.auth.UserServiceResponse;
import pharma.security.Stytch.StytchClient;
import pharma.security.TokenUtility;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadServerManager implements Runnable {

    private SSLSocket sslSocket;
    private  String client_socket;
    private  ObjectOutputStream outputStream;
    private  ObjectInputStream inputStream;
    private ExecutorService executor_read;
    private ExecutorService executor_write;
    private  AtomicBoolean connection;
    private ConcurrentLinkedQueue<ChatMsg> outgoing_msg;
    private UserService userService;
    private Logger logger;
    public ThreadServerManager(SSLSocket sslSocket,UserService userService) {
        this.sslSocket = sslSocket;
        logger=Logger.getLogger(ThreadServerManager.class.getName());
        System.out.println("new Thread");
        executor_read=Executors.newCachedThreadPool();
        executor_write=Executors.newCachedThreadPool();
        connection=new AtomicBoolean(false);

        outgoing_msg=new ConcurrentLinkedQueue();
        this.userService=userService;



    }

    public ThreadServerManager() {
    }

    @Override
    public void run() {
        try {
            outputStream=new ObjectOutputStream(sslSocket.getOutputStream());
            outputStream.flush();
            inputStream=new ObjectInputStream(sslSocket.getInputStream());
            sslSocket.setSoTimeout(30000000);
            connection.set(true);
            read();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }







   public void  read() {
       logger.info("execute reader server");
       while (connection.get()) {
           try {

               Object object = inputStream.readObject();
               if (object instanceof ChatMsg chatMsg) {
                   String info = validate_token(chatMsg.getJwt());
                   logger.info("Message it is arriving! " + chatMsg.getProcessCommand());
                   ProcessCommands.add_user(chatMsg,this);


               }
           } catch (EOFException e) {
               logger.warning("Finished EOF");
               connection.set(false);

           } catch (IOException e) {
               throw new RuntimeException(e);
           } catch (ClassNotFoundException e) {
               logger.warning(e.getMessage());
           } catch (ExpireToken e) {
               logger.warning(e.getMessage());
           }
       }
   }






    public   String validate_token(String token) throws ExpireToken{

            UserServiceResponse response=userService.authenticate_jwt(token);
            return response.getBody();




    }







    public void store_message(ChatMsg chatMsg)  {

        try {
            outputStream.writeObject(chatMsg);
            outputStream.flush();
            logger.info("Send Message!");
        }catch (IOException e){
            logger.warning(e.getMessage());
        }


    }
    public void SetConnection(boolean value){
        connection.set(value);

    }


}

*//*





package pharma.chat.Server;

import org.apache.commons.math3.analysis.function.Log;
import pharma.config.auth.ExpireToken;
import pharma.config.auth.UserService;
import pharma.config.auth.UserServiceResponse;
import pharma.security.Stytch.StytchClient;
import pharma.security.TokenUtility;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;


public class ThreadServerManager implements Runnable {

    private SSLSocket sslSocket;
    private  String client_socket;
    private  ObjectOutputStream outputStream;
    private  ObjectInputStream inputStream;
    private ExecutorService executor_read;
    private ExecutorService executor_write;
    private  AtomicBoolean connection;
    private BlockingQueue<ChatMsg> outgoing_msg;
    private UserService userService;
    private Logger logger;
    public ThreadServerManager(SSLSocket sslSocket,UserService userService) {
        this.sslSocket = sslSocket;
        logger=Logger.getLogger(ThreadServerManager.class.getName());
        System.out.println("new Thread");
        executor_read=Executors.newCachedThreadPool();
        executor_write=Executors.newCachedThreadPool();
        connection=new AtomicBoolean(false);


        outgoing_msg=new LinkedBlockingQueue<>();
        this.userService=userService;
        try {
            outputStream=new ObjectOutputStream(sslSocket.getOutputStream());
            inputStream=new ObjectInputStream(sslSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public ThreadServerManager() {
    }

    @Override
    public void run() {
        try {
            sslSocket.setSoTimeout(30000000);
            sslSocket.startHandshake();
            connection.set(true);
            executor_read.submit(new ReadThread());
            executor_write.submit(new Write());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



    private class  ReadThread  implements  Runnable {

        @Override
        public void run() {

            try {
                logger.info("execute reader server");

                while (connection.get()) {
                    logger.info("Read Tread");

                    Object object = inputStream.readObject();

                    if (object instanceof ChatMsg chatMsg) {
                        try {
                            String info = validate_token(chatMsg.getJwt());
                            ProcessCommands.command(chatMsg, info, ThreadServerManager.this);


                        } catch (ExpireToken e) {
                            Logger.getLogger("Warning expire token");
                        } finally {
                            // connection.set(false);
                        }


                    }
                }
            } catch (EOFException e) {
                logger.info("Client disconnected gracefully (EOF)");
                // connection.set(false);


            } catch (IOException e) {
                if (connection.get()) {
                    logger.info("Client disconnected unexpectedly: " + e.getMessage());
                    connection.set(false);
                }


            } catch (ClassNotFoundException e) {
                logger.warning("Unknown class received: " + e.getMessage());
                // Continue processing other messages

            } catch (Exception e) {
                logger.log(Level.WARNING, "Error processing message: " + e.getMessage(), e);
                // Don't break the connection for processing errors unless it's critical
                if (e instanceof RuntimeException && e.getCause() instanceof IOException) {
                    logger.info("Connection lost due to IO error");
                    connection.set(false);

                }
            }


        }
    }



    private  String validate_token(String token) throws ExpireToken{

        UserServiceResponse response=userService.authenticate_jwt(token);
        return response.getBody();




    }




    private  class  Write implements  Runnable {

        @Override
        public void run() {
            logger.info("starting write");
            while (connection.get()) {
                logger.info("Write");
                try {
                    if (!outgoing_msg.isEmpty()) {
                        ChatMsg chatMsg = outgoing_msg.take();
                        if (chatMsg != null && outputStream != null) {

                            outputStream.writeObject(chatMsg);
                            outputStream.flush();
                            logger.info("Send Message!");
                        }

                    }


                } catch (IOException e) {
                    if (connection.get()) {
                        logger.warning("IO Error on writer thread for " + sslSocket.getRemoteSocketAddress() + ": " + e.getMessage());
                    }
                } catch (InterruptedException e) {
                 logger.warning(e.getMessage());
                }
            }
        }




    }


    public void store_message(ChatMsg chatMsg){

        outgoing_msg.add(chatMsg);
    }
    public void SetConnection(boolean value){
        connection.set(value);

    }


}
*/
