package pharma.chat;

import pharma.config.auth.ExpireToken;
import pharma.config.auth.UserService;
import pharma.config.auth.UserServiceResponse;
import pharma.security.TokenUtility;

import javax.net.ssl.SSLSocket;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadServerManager implements Runnable {


    private final SSLSocket sslSocket;
    private final UserService userService;
    private final Logger logger;
    private String email_identifier;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private final ExecutorService readerExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService writerExecutor = Executors.newSingleThreadExecutor();
    private final AtomicBoolean isConnected = new AtomicBoolean(false);
    private final BlockingQueue<ChatMsg> outgoingMessages = new LinkedBlockingQueue<>();


    public ThreadServerManager(SSLSocket sslSocket, UserService userService) {
        this.sslSocket = sslSocket;
        this.userService = userService;
        this.logger = Logger.getLogger(ThreadServerManager.class.getName());
    }

    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(sslSocket.getOutputStream());
            outputStream.flush(); // Crucial to send header and avoid client/server deadlock
            inputStream = new ObjectInputStream(sslSocket.getInputStream());

            isConnected.set(true);
            logger.info("Connection initialized for " + sslSocket.getRemoteSocketAddress());

            startReader();
            startWriter();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Initialization failed for " + sslSocket.getRemoteSocketAddress(), e);
        }
    }

    private void startReader() {
        readerExecutor.execute(() -> {
            logger.info("Reader thread started for " + sslSocket.getRemoteSocketAddress());
            while (isConnected.get()) {
                try {
                    Object obj = inputStream.readObject();
                    if (obj instanceof ChatMsg msg) {
                        processIncomingMessage(msg);
                    }
                } catch (EOFException | SocketException e) {
                    logger.info("Client disconnected: " + sslSocket.getRemoteSocketAddress());
                    break;
                } catch (IOException | ClassNotFoundException e) {
                    if (isConnected.get()) {
                        logger.log(Level.WARNING, "Reader error", e);
                    }
                    break;
                } catch (ExpireToken e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void startWriter() {
        writerExecutor.execute(() -> {
            logger.info("Writer thread started for " + sslSocket.getRemoteSocketAddress());
            while (isConnected.get()) {
                try {
                    ChatMsg msg = outgoingMessages.take(); // Blocks until a message is available
                    outputStream.writeObject(msg);
                    outputStream.flush();
                } catch (InterruptedException e) {
                    logger.info("Writer thread interrupted.");
                    Thread.currentThread().interrupt();
                    break;
                } catch (IOException e) {
                    if (isConnected.get()) {
                        logger.log(Level.WARNING, "Writer IO error: " + e.getMessage());
                    }
                    break;
                }
            }

        });
    }

    private void processIncomingMessage(ChatMsg msg) throws ExpireToken {
        String token = msg.getJwt();
        UserServiceResponse userServiceResponse = userService.authenticate_jwt(token);
        email_identifier = TokenUtility.extract_email(userServiceResponse.getBody());
        String role = TokenUtility.extractRole(userServiceResponse.getBody());
        if (userServiceResponse.getStatus() == 200) {
            switch (msg.getCommand()) {
                case Command.STARTING -> {

                    if (role.equals("seller")) {
                        ActiveClient.setSeller_map(email_identifier, this);
                        logger.info("email_udem: "+email_identifier);
                        Optional<String> random_pharmacist= ActiveClient.random_pharmacist();
                        if(random_pharmacist.isEmpty()){
                            // The receiver is always the client that can be conected to sever in wait
                          ChatMsg chatMsg=create_msg("Wait to Join "+role, Command.WAIT,msg.getJwt(),"SERVER",email_identifier);
                          sendMessage(chatMsg);
                        }else{
                           ActiveClient.setJoinChat(random_pharmacist.get(),email_identifier);


                        }
                        // Pharmacist
                    } else {
                        ActiveClient.setPharmacist_map(email_identifier, this);
                        Optional<String> random_seller= ActiveClient.get_random_seller();
                        if(random_seller.isEmpty()){
                            // The receiver is always the client that can be conected to sever in wait
                            ChatMsg chatMsg=create_msg("Wait to Join "+role, Command.WAIT,msg.getJwt(),"SERVER",email_identifier);
                            sendMessage(chatMsg);
                        }else{
                            ActiveClient.setJoinChat(email_identifier,random_seller.get());
                        }
                    }
                    logger.info("Send message");

                }
                case Command.CHATTING -> {

                    if (role.equals("pharmacist")) {


                        String seller_email = ActiveClient.get_SellerJoinChat(msg.getSender());
                        ThreadServerManager seller_thread = ActiveClient.getInstanceSellerByEmail(seller_email);
                        seller_thread.sendMessage(msg);
                        //ChatPharmacist
                        // Cerco il farmacista ed ottengo il seller al suo interno

                    } else if (role.equals("seller")) {
                        ThreadServerManager serverManager_p= ActiveClient.getInstanceThreadPharmacistByEmail(msg.getReceiver());
                        serverManager_p.sendMessage(msg);







                        // Cerco il seller -> N farmacisti


                    }


                }
            }
        }
    }

    private Optional<String> random_user(String role) {
        if (role.equals("seller")) {
            return ActiveClient.get_random_seller();
        }
        else{
            return ActiveClient.random_pharmacist();


            }
    }
    private ChatMsg create_msg(String message, Command command, String jwt, String sender, String receiver){
       return new ChatMsg(jwt,message,command,sender,receiver);

    }





    public void sendMessage(ChatMsg msg) {
        if (isConnected.get()) {
            outgoingMessages.add(msg);
        }
    }



}