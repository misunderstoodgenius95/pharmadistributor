package pharma.testChat;

import java.io.Serializable;

/**
 * Represents a message exchanged between client and server.
 * CRITICAL: This class MUST be identical in both the client and server projects
 * to prevent deserialization errors. It is best practice to place this class
 * in a shared library or module.
 */
public class ChatMsg implements Serializable {
    /**
     * The serialVersionUID is a unique identifier for this class version.
     * It is crucial for ensuring the client and server are using a compatible
     * version of the class during serialization.
     */
    private static final long serialVersionUID = 20240614L;

    private final String sender;
    private final String message;

    public ChatMsg(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return sender + ": " + message;
    }
}
