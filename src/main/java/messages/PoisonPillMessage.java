package messages;

import top.Node;
import top.Promise;

import java.util.ArrayList;
import java.util.List;

public class PoisonPillMessage implements Message{
    private Integer senderId;
    private Integer poisonedNode;
    private Integer downTimeInMs;

    public PoisonPillMessage(Integer senderId, Integer poisonedNode, Integer downTimeInMs){
        this.senderId = senderId;
        this.poisonedNode = poisonedNode;
        this.downTimeInMs = downTimeInMs;
    }

    @Override
    public Integer getSender() {
        return senderId;
    }

    @Override
    public String getMessageType() {
        return "PoisonPill";
    }

    @Override
    public void process(Node node) {
        if (poisonedNode.equals(node.getMyId())) {
            try {
                Thread.sleep(Long.valueOf(downTimeInMs));
            } catch (InterruptedException e) {
                System.err.println("Error sleeping when taking the poison pill on node " + node.getMyId());
            }
            // clear all variables, including queue
            node.proposedValue = null;
            node.acceptedProposalNumber = null;
            node.promisedProposalNumber = null;
            node.acceptedValue = null;
            node.decidedValue = null;
            node.getMailbox().clear();
            node.readState();
        }
    }
}
