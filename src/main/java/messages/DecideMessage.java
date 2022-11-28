package messages;

import top.Node;

public class DecideMessage implements Message{
    private Integer senderId;
    private Integer proposedValue;

    public DecideMessage(Integer senderId, Integer proposedValue) {
        this.senderId = senderId;
        this.proposedValue = proposedValue;
    }

    @Override
    public Integer getSender() {
        return senderId;
    }

    @Override
    public String getMessageType() {
        return "Decide";
    }

    @Override
    public void process(Node node) {
        node.decidedValue = proposedValue;
        System.out.println("Learner " + node.getMyId() + " knows that the decided value is " + proposedValue);
        node.acceptedProposalNumber = null;
        node.promisedProposalNumber = null;
        node.acceptedValue = null;
    }
}
