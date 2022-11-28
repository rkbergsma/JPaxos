package messages;

import top.Node;

import java.util.ArrayList;

public class ProposeMessage implements Message {
    private Integer senderId;
    private Integer proposedNumber;

    public ProposeMessage(Integer senderId, Integer proposedNumber){
        this.senderId = senderId;
        this.proposedNumber = proposedNumber;
    }

    @Override
    public Integer getSender() {
        return senderId;
    }

    @Override
    public String getMessageType() {
        return "Propose";
    }

    @Override
    public void process(Node node) {
        node.incrementProposalNumber();
        node.proposedValue = proposedNumber;

        node.numAcks = 0;
        node.responseThresholdMet = false;
        node.responseWeightSum = 0.0;

        node.nackThresholdMet = false;
        node.nackWeightSum = 0.0;

        node.promiseWeightSum = 0.0;
        node.promiseThresholdMet = false;

        node.promises = new ArrayList<>();
        for (Integer peerId : node.getPeers().keySet()) {
            node.getPeers().get(peerId).offer(new PrepareMessage(node.getMyId(), node.proposalNumber));
        }
    }
}
