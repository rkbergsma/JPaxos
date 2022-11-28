package messages;

import top.Node;

public interface Message {
    Integer getSender();

    String getMessageType();

    void process(Node node);

    default String printMessage(Integer receiverId) {
        return "Node " + receiverId + " received " + getMessageType() + " message from sender ID: " + getSender();
    }
}
