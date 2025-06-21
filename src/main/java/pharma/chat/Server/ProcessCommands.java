/*
package pharma.chat.Server;


import pharma.chat.ActiveClient;
import pharma.chat.Command;
import pharma.config.auth.ExpireToken;
import pharma.security.TokenUtility;

import java.util.logging.Logger;

public class ProcessCommands {

    private final static Logger logger = Logger.getLogger(ProcessCommands.class.getName());

    public static void command(ChatMsg chatMsg, String info, ThreadServerManager threadServerManager) throws IllegalAccessException {

        switch (chatMsg.getProcessCommand()) {

            case Command.Starting -> ProcessCommands.starting_command(chatMsg, info, threadServerManager);

*/
/*
          case Command.Chatting -> ProcessCommands.chatting(chatMsg,info);
          case Command.Closing -> ProcessCommands.quit(threadServerManager,chatMsg,info);
*//*


        }

    }


    private static void starting_command(ChatMsg chatMsg_old, String info, ThreadServerManager threadServerManager) throws IllegalAccessException {

        String role = TokenUtility.extractRole(info);
        String email = TokenUtility.extract_email(info);

        String msg_join = "";
        String msg_welcome = "";
        if (role.equals("seller")) {
            boolean ins = ActiveClient.setSeller_map(email, threadServerManager);
            if (ins) {
                msg_welcome = "Welcome  Seller " + email;
            }
// verify that exist almost one pharmacist and this pharmacist is not present in other pharmacist
          */
/*  if (!ActiveClient.wait_pharmacist().isEmpty()) {
                String pharmacist = ActiveClient.wait_pharmacist().getFirst();
                ActiveClient.setJoinChat(pharmacist, email);
                msg_join = "Join start with Farmacist: " + email;
            }*//*


        } else if (role.equals("pharmacist")) {
            boolean res = ActiveClient.setPharmacist_map(email, threadServerManager);
            if (res) {
                msg_welcome = "Welcome  Pharmacist " + email;
            }
         */
/* if (!ActiveClient.getSeller_map().isEmpty()) {
            String seller = ActiveClient.get_random_seller();
            ActiveClient.setJoinChat(email, seller);
            msg_join = "Join with Seller  " + email;
        }*//*

    }else

    {
        throw new IllegalAccessException("Unknown User");
    }

      if(!msg_welcome.isEmpty())

    {


        threadServerManager.store_message(new ChatMsg(chatMsg_old.getJwt(), msg_welcome, Command.Join, "SERVER"));
        logger.info("process" + msg_welcome);
    }


*/
/*
    // Processing message to Pharmacist to Seller and viceVersa
    private static void chatting(ChatMsg chatMsg, String info) {
        try {
            String receiver_email = chatMsg.getReceiver();
            String role = TokenUtility.extractRole(info);
// if role is pharmacist(send) and receiver seller
            if (role.equals("pharmacist")) {
                ThreadServerManager threadServerManager = ActiveClient.getInstanceSellerByEmail(receiver_email);
                threadServerManager.store_message(chatMsg);

            } else if (role.equals("Seller")) {
                ThreadServerManager threadServerManager = ActiveClient.getInstanceThreadPharmacistByEmail(receiver_email);
                threadServerManager.store_message(chatMsg);

            }


        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }
*//*


  */
/*  // Destry connection and chat
    private static void quit(ThreadServerManager serverManager, ChatMsg chatMsg, String info) {

        String email = TokenUtility.extract_email(info);
        String role = TokenUtility.extractRole(info);
        if (role.equals("Seller")) {
            ActiveClient.removeSeller(email);
            ActiveClient.removeSellerChat(email);
        } else if (role.equals("Pharmacist")) {
            ActiveClient.removePharmacist(email);
            ActiveClient.removePharmacistIntoChat(email);
        }
    }


    public static void add_user(ChatMsg chatMsg, ThreadServerManager threadServerManager) {
        try {
            String info = threadServerManager.validate_token(chatMsg.getJwt());

            String role = TokenUtility.extractRole(info);
            String email = TokenUtility.extract_email(info);
            if (role.equals("seller")) {
                if (ActiveClient.setPharmacist_map(email, threadServerManager)) {
                    logger.info("STore message");


                }

            } else if (role.equals("pharmacist")) {
                if (ActiveClient.setPharmacist_map(email, threadServerManager)) {
                    logger.info("store message");

                }

            }
            ChatMsg chatMsg_join = new ChatMsg(chatMsg.getJwt(), "Join", Command.Join, "SYSTEM");
            threadServerManager.store_message(chatMsg_join);


        } catch (ExpireToken e) {
            throw new RuntimeException(e);
        }

*//*

    }

























}
*/
