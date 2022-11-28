package messages;

import top.Node;

public class AcceptMessage implements Message{
    private Integer senderId;
    private Integer proposalNumber;
    private Integer proposedValue;

    public AcceptMessage(Integer senderId, Integer proposalNumber, Integer proposedValue) {
        this.senderId = senderId;
        this.proposalNumber = proposalNumber;
        this.proposedValue = proposedValue;
    }

    @Override
    public Integer getSender() {
        return senderId;
    }

    @Override
    public String getMessageType() {
        return "Accept";
    }

    @Override
    public void process(Node node) {
        if (node.promisedProposalNumber != null && node.promisedProposalNumber <= proposalNumber){
            node.promisedProposalNumber = proposalNumber;
            node.acceptedProposalNumber = proposalNumber;
            node.acceptedValue = proposedValue;
            node.writeState();
            node.getPeers().get(senderId).offer(new AcceptedMessage(node.getMyId(), proposalNumber));
        } else {
            node.getPeers().get(senderId).offer(new NackMessage(node.getMyId(), proposalNumber));
        }
    }
}
