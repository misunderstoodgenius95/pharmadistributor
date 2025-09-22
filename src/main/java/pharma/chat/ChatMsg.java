package pharma.chat;

import java.io.Serializable;

public class ChatMsg implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes
    private final String jwt;
    private final String payload;
    private final Command command;
    private final String sender; // e.g., "System" or user's email
    private final String receiver;

    public ChatMsg(String jwt, String payload, Command command, String sender, String receiver) {
        this.jwt = jwt;
        this.payload = payload;
        this.command = command;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getJwt() {
        return jwt;
    }

    public String getPayload() {
        return payload;
    }

    public Command getCommand() {
        return command;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    @Override
    public String toString() {
        // A nice format for logging and for the chat window
        return sender + ": " + payload;
    }
}