package messages;

import top.Node;

public class AcceptedMessage implements Message{
    private Integer senderId;
    private Integer proposalNumber;

    public AcceptedMessage(Integer senderId, Integer proposalNumber) {
        this.senderId = senderId;
        this.proposalNumber = proposalNumber;
    }

    @Override
    public Integer getSender() {
        return senderId;
    }

    @Override
    public String getMessageType() {
        return "Accepted";
    }

    @Override
    public void process(Node node) {
        if (!proposalNumber.equals(node.proposalNumber)){
            return;
        }
        node.numAcks = node.numAcks + 1;
        node.responseWeightSum = node.responseWeightSum + node.getWeight(senderId);
//        System.out.println("top.Node " + node.getMyId() + " responseThresholdMet: " + node.responseThresholdMet);
//        System.out.println("top.Node " + node.getMyId() + " responseWeightSum: " + node.responseWeightSum + " after adding " + node.getWeight(senderId));
//        System.out.println("top.Node " + node.getMyId() + " responseWeightSum compared to 0.5: " + Double.compare(node.responseWeightSum, 0.5));
        if (!node.responseThresholdMet && Double.compare(node.responseWeightSum, 0.5) > 0) { //majority based on weight
            node.responseThresholdMet = true;
            System.out.println("Decided on a value with " + node.numAcks + " acks");
        //if (node.numAcks == (node.getPeers().size() + 1) / 2) { //majority (2)
            node.writeOfficialLog();
            node.getMailbox().offer(new DecideMessage(node.getMyId(), node.proposedValue));
            for (Integer peerId : node.getPeers().keySet()) {
                node.getPeers().get(peerId).offer(new DecideMessage(node.getMyId(), node.proposedValue));
            }
        }
    }
}
