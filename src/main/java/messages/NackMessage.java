package messages;

import top.Node;

public class NackMessage implements Message{
    private Integer senderId;
    private Integer proposalNumber;

    public NackMessage(Integer senderId, Integer proposalNumber){
        this.senderId = senderId;
        this.proposalNumber = proposalNumber;
    }

    @Override
    public Integer getSender() {
        return senderId;
    }

    @Override
    public String getMessageType() {
        return "Nack";
    }

    @Override
    public void process(Node node) {
        if (!proposalNumber.equals(node.proposalNumber)) {
            return;
        }
        node.nackWeightSum = node.nackWeightSum + node.getWeight(senderId);
        if (!node.nackThresholdMet && Double.compare(node.nackWeightSum, 0.5) > 0) { //majority based on weight
            node.nackThresholdMet = true;
            try {
                Thread.sleep((long)(Math.random() * 1000));
            } catch (InterruptedException e) {
                System.err.println("Error while sleeping");
            }
            node.getMailbox().offer(new ProposeMessage(node.getMyId(), node.proposedValue));
        }
    }
}
