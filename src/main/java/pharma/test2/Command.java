package pharma.test2;

import java.io.Serializable;

public enum Command implements Serializable {
    STARTING,      // Initial message from client to join
    JOIN_SUCCESS,  // Server confirmation that join was successful
    CHATTING,      // A regular chat message from a user
    SYSTEM_MESSAGE,// A message from the server to the client (e.g., user joins/leaves)
    CLOSING,// A notification that a client is disconnecting
    WAIT
}