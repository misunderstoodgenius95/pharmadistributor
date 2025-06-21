/*
package pharma.chat.Client;

import javafx.collections.ObservableList;
import javafx.scene.control.skin.TableHeaderRow;
import pharma.chat.Command;
import pharma.chat.Server.ChatMsg;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChachaClient {
    private static final Logger logger = Logger.getLogger(ChachaClient.class.getName());


    private SSLSocket socket;
    private ExecutorService executor;
    private AtomicBoolean connected;
    private   BlockingQueue<ChatMsg> queue_msg_incoming;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String jwt;



    public ChachaClient(String jwt) {
        this.queue_msg_incoming=new LinkedBlockingQueue<>();
        this.jwt = jwt;
        this.executor = Executors.newCachedThreadPool();
        this.connected = new AtomicBoolean(false);

    }

    public void connect() {
        try {
            logger.info("Starting connection process...");

            // Step 1: Configure SSL settings
            if (!configSetting()) {
                logger.severe("Failed to configure SSL settings");

            }

            // Step 2: Start handshake (this will trigger the listener)
            logger.info("Starting SSL handshake...");
            socket.startHandshake();
            connected.set(true);

            logger.info("Starting Handshake");
            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            this.objectOutputStream.flush();
            objectInputStream=new ObjectInputStream(socket.getInputStream());
            readThread();
           send_initial_message();




        } catch (Exception e) {
            logger.severe("Connection failed: " + e.getMessage());
            e.printStackTrace();
            cleanup();

        }
    }




    public void send_initial_message() {
        if (!connected.get()) {
            logger.warning("Cannot send initial message - not connected");
            return;
        }

        ChatMsg chatMsg = new ChatMsg(jwt,"Welcome",Command.Starting,"SERVER");
        send_message(chatMsg);
    }

    private boolean configSetting() {
        try {
            logger.info("Configuring SSL settings...");

            System.setProperty("javax.net.ssl.trustStore", "clienttruststore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");
            // Create socket
        SSLSocketFactory sslSocketFactory=(SSLSocketFactory) SSLSocketFactory.getDefault();
        socket=(SSLSocket)sslSocketFactory.createSocket(InetAddress.getLocalHost(),3000);

            // Configure socket options
            socket.setEnabledProtocols(new String[]{"TLSv1.3"});
            socket.setEnabledCipherSuites(new String[]{"TLS_CHACHA20_POLY1305_SHA256"});



            logger.info("SSL configuration completed");
            return true;

        } catch (IOException e) {
            logger.severe("SSL configuration failed: " + e.getMessage());
            return false;
        }
    }



    private void  readThread() {
        Runnable read_thread = () -> {
            logger.info("Reader thread started");

            while (connected.get() && !Thread.currentThread().isInterrupted()) {
                try {

                    ChatMsg chatMsg = (ChatMsg) objectInputStream.readObject();
                   logger.info("Mesage: "+chatMsg.getMsg());


                } catch (Exception e) {
                    logger.warning(e.getMessage());
                }
            }
        };
            executor.submit(read_thread);
    }

    private void send_message(ChatMsg chatMsg){

        try {
            objectOutputStream.writeObject(chatMsg);
            objectOutputStream.flush();
        } catch (IOException e) {
           logger.warning(e.getMessage());
        }


    }



    private TrustManager[] trustManager() {
        return new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0]; // Return empty array instead of null
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };
    }

    private void process_message(ChatMsg chatMsg) {
        try {
            Command command = chatMsg.getProcessCommand();

            switch (command) {
                case Chatting -> {
                    queue_msg_incoming.put(chatMsg);
                    logger.info("ChatPharmacist message added to UI: " + chatMsg.getMsg());
                }
                case Closing -> {
                    logger.info("Server requested connection close");
                    connected.set(false);
                }
                case Join -> {
                    logger.info("Join response: " + chatMsg.getMsg());
                }

                default -> {
                    logger.warning("Unknown command: " + command);
                }
            }

        } catch (Exception e) {
            logger.severe("Error processing message: " + e.getMessage());
        }
    }

    public void sendMessage(ChatMsg message) {
        if (!connected.get()) {
            logger.warning("Cannot send message - not connected");
            return;
        }

        boolean added = queue_msg_incoming.add(message);
        if (!added) {
            logger.warning("Failed to queue message - queue may be full");
        }
    }

    public boolean isConnected() {
        return connected.get();
    }

    public void disconnect() {
        logger.info("Disconnecting client...");
        connected.set(false);

        // Send goodbye message if possible
        try {
            if (isConnected()) {
                ChatMsg goodbye = new ChatMsg(jwt,"Welcome",Command.Starting,"SERVER");
                sendMessage(goodbye);

                // Give time for message to be sent
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            logger.warning("Error sending goodbye message: " + e.getMessage());
        }

        cleanup();
    }

    private void cleanup() {
        logger.info("Cleaning up client resources...");

        connected.set(false);


        // Close streams
        if (objectOutputStream != null) {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                logger.fine("Error closing output stream: " + e.getMessage());
            }
            objectOutputStream = null;
        }

        if (objectInputStream != null) {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                logger.fine("Error closing input stream: " + e.getMessage());
            }
            objectInputStream = null;
        }

        // Close socket
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                logger.fine("Error closing socket: " + e.getMessage());
            }
            socket = null;
        }

        // Shutdown executor
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        logger.info("Client cleanup completed");
    }
}

*/


package pharma.chat.Client;

import javafx.collections.ObservableList;
import org.w3c.dom.stylesheets.LinkStyle;
import pharma.Storage.FileStorage;
import pharma.chat.Command;
import pharma.chat.Server.ChatMsg;
import javax.net.ssl.*;
import java.io.*;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChachaClient {
    private  SSLSocket socket;
    private ExecutorService executor;
    private AtomicBoolean connected;
    private BlockingQueue<ChatMsg> queue_msg_incoming;
    private ObservableList<ChatMsg>  outside_msg;
    private  ObjectOutputStream objectOutputStream;
    private  ObjectInputStream objectInputStream;
    private  String jwt;
    private static final Logger logger = Logger.getLogger(ChachaClient.class.getName());
    public ChachaClient(BlockingQueue<ChatMsg> queue_msg_incoming, ObservableList<ChatMsg> outside_msg, String jwt) {


        this.queue_msg_incoming =queue_msg_incoming;
        this.outside_msg=outside_msg;
        this.jwt=jwt;
      this.executor= Executors.newCachedThreadPool();

          configSetting();



    }

    private void   listener() {
        socket.addHandshakeCompletedListener(new HandshakeCompletedListener() {
            @Override
            public void handshakeCompleted(HandshakeCompletedEvent event) {
                System.out.println("✅ SSL Handshake completed successfully!");
                System.out.println("Cipher: " + event.getCipherSuite());
                System.out.println("Protocol: " + event.getSession().getProtocol());
                try {
                    objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.flush();
                    logger.info("Output is created!");
                    Thread.sleep(4000);
                    objectInputStream=new ObjectInputStream(socket.getInputStream());
                    logger.info("Input is created!");
                    connected.set(true);



            handleThread();
                 send_initial_message();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

        });
    }

    public BlockingQueue<ChatMsg> getQueue_msg_incoming() {
        return queue_msg_incoming;
    }
    public void send_initial_message(){
        ChatMsg chatMsg=new ChatMsg(jwt,Command.Starting);
        try {
            queue_msg_incoming.put(chatMsg);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void configSetting() {

        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
            sslContext.init(null, trustManager(), new SecureRandom());
            SSLSocketFactory factory = sslContext.getSocketFactory();
            socket = (SSLSocket) factory.createSocket(InetAddress.getLocalHost(), 3000);
            socket.setEnabledProtocols(new String[]{"TLSv1.3"});
            socket.setKeepAlive(true);
            socket.setTcpNoDelay(true);
            socket.setSoTimeout(30000);
            socket.setEnabledCipherSuites(new String[]{"TLS_CHACHA20_POLY1305_SHA256"});
            listener();


            socket.startHandshake();


        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private void handleThread() throws IOException, ClassNotFoundException {



       executor.submit(new ReadThread());
       executor.submit(new WriterThread());
    }


    private  class ReadThread implements Runnable{
      @Override
      public void run() {
          System.out.println("execute read");
          while(connected.get()) {
              System.out.println("reader");
              try {
                  Object object=objectInputStream.readObject();

                  if (object instanceof ChatMsg chatMsg) {
                      process_message(chatMsg);


                  }else{
                      System.out.println("No object");

                  }
              } catch (SocketTimeoutException e) {
                  System.out.println("ReadThread: Socket timeout (normal)");

              } catch (EOFException e) {
                  System.out.println("ReadThread: EOF - Server closed connection");
                  connected.set(false);
                  break;
              } catch (SocketException e) {
                  System.out.println("ReadThread: Socket closed - " + e.getMessage());
                  connected.set(false);
                  break;
              } catch (IOException e) {
                  System.err.println("ReadThread: IO Error - " + e.getMessage());
                  connected.set(false);
                  break;
              } catch (ClassNotFoundException | IllegalAccessException e) {
                  throw new RuntimeException(e);
              }


          }


}


      }





  private TrustManager[] trustManager(){

      return  new TrustManager[] {
              new X509TrustManager() {
                  public X509Certificate[] getAcceptedIssuers() { return null; }
                  public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                  public void checkServerTrusted(X509Certificate[] certs, String authType) {}
              }
      };

  }

  private  class WriterThread implements  Runnable{
        Logger logger=Logger.getGlobal();
      @Override
      public void run() {
          System.out.println("client writer");

          while(connected.get()){
              System.out.println("write ");
              try {


                      ChatMsg chatMsg = queue_msg_incoming.poll(2, TimeUnit.SECONDS);
                      if(chatMsg!=null && objectOutputStream!=null) {
                    synchronized (objectOutputStream) {
                        objectOutputStream.writeObject(chatMsg);
                        objectOutputStream.flush();

                    }
                          System.out.println("send messge");

                      }


              } catch (IOException e) {
                  logger.warning("Writer IO error: " + e.getMessage());

                  break;

              } catch (InterruptedException e) {
                  logger.info("Writer thread interrupted");
                  Thread.currentThread().interrupt();
                  break;

              } catch (Exception e) {
                  logger.log(Level.SEVERE, "Unexpected error in writer", e);
                  break;
              }

          }
          System.out.println("exit write ");
          logger.info("Client writer thread terminated");



      }
  }

  private void process_message(ChatMsg chatMsg) throws IllegalAccessException {
        Command command=chatMsg.getProcessCommand();
        switch (command){
            case Chatting ->outside_msg.add(chatMsg);
            case Closing ->connected.set(false);
            case Join-> System.out.println(chatMsg.getMsg());
        }


  }



  }










