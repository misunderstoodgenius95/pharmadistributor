package pharma.chat.Server;

import pharma.chat.Command;


import java.io.Serial;
import java.io.Serializable;

public class ChatMsg  implements Serializable {
    private static final long serialVersionUID = 1L;
        private String jwt;
        private String msg;
        private Command command;
        private  String receiver;

    public ChatMsg(String jwt, Command command) {
        this.jwt = jwt;
        this.command = command;
    }



    public ChatMsg(String jwt, String msg, Command command,  String receiver) {
        this.jwt = jwt;
        this.msg = msg;
        this.command = command;
        this.receiver = receiver;
    }

    public String getJwt() {
        return jwt;
    }

    public String getMsg() throws IllegalAccessException {
        if (msg == null) {
         throw new IllegalAccessException("Msg is not null");
        }
        return msg;
    }


    public Command getProcessCommand() {
        return command;
    }


    public String getReceiver() throws IllegalAccessException {
        if(receiver==null){

            throw new IllegalAccessException("Receiver is null");
        }
        return receiver;
    }
}
