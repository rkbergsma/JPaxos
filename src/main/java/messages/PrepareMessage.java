package messages;

import top.Node;
import top.Promise;

public class PrepareMessage implements Message{
    private Integer senderId;
    private Integer proposalNumber;

    public PrepareMessage(Integer senderId, Integer proposalNumber){
        this.senderId = senderId;
        this.proposalNumber = proposalNumber;
    }

    @Override
    public Integer getSender() {
        return senderId;
    }

    @Override
    public String getMessageType() {
        return "Prepare";
    }

    @Override
    public void process(Node node) {
        if (node.promisedProposalNumber == null || node.promisedProposalNumber < proposalNumber) {
            node.promisedProposalNumber = proposalNumber;
            node.writeState();
            Promise promise = new Promise(node.acceptedProposalNumber, node.acceptedValue);
            node.getPeers().get(senderId).offer(new PromiseMessage(node.getMyId(), node.promisedProposalNumber, promise));
        } else {
            node.getPeers().get(senderId).offer(new NackMessage(node.getMyId(), proposalNumber));
        }
    }
}
