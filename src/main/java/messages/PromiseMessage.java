package messages;

import top.Node;
import top.Promise;

public class PromiseMessage implements Message{
    private Integer senderId;
    private Integer promisedProposalNumber;
    private Promise promise;

    public PromiseMessage(Integer senderId, Integer promisedProposalNumber, Promise promise){
        this.senderId = senderId;
        this.promisedProposalNumber = promisedProposalNumber;
        this.promise = promise;
    }

    @Override
    public Integer getSender() {
        return senderId;
    }

    @Override
    public String getMessageType() {
        return "top.Promise";
    }

    @Override
    public void process(Node node) {
        if (!promisedProposalNumber.equals(node.proposalNumber)){
            return;
        }
        node.promises.add(promise);
        node.promiseWeightSum = node.promiseWeightSum + node.getWeight(senderId);
//        if (node.promises.size() == (node.getPeers().size() + 1) / 2){   // this should be acceptors size, but we assume all peers are acceptors and graph it completely connected
        if (!node.promiseThresholdMet && Double.compare(node.promiseWeightSum, 0.5) > 0){
            node.promiseThresholdMet = true;
            Integer maxPromiseValue = null;
            for (Promise p : node.promises){
                if (p.getValue() != null){
                    if (maxPromiseValue == null || p.getValue() > maxPromiseValue){
                        maxPromiseValue = p.getValue();
                    }
                }
            }
            if (maxPromiseValue != null){
                node.proposedValue = maxPromiseValue;
            }
            for (Integer peerId : node.getPeers().keySet()) {
                node.getPeers().get(peerId).offer(new AcceptMessage(node.getMyId(), node.proposalNumber, node.proposedValue));
            }
        }
    }
}
