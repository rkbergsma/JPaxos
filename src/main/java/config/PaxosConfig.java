package config;

import top.Weight;

import java.util.ArrayList;
import java.util.List;

public class PaxosConfig {
    // Basic set up
    private Weight.WeightType weightType = Weight.WeightType.UNIFORM;
    private Integer numberNodes = 10;

    // Poison related
    private Integer poisonRateInMs = 90;
    private Integer nodeDownTimeInMs = 10;
    private boolean poisonAllAtOnce = false;
    private List<Integer> nodesToPoison = new ArrayList<>();

    // Message delay and proposals
    private Integer proposalIntervalInMs = 1000;
    private Integer messageDelayInMs = 50;
    private List<Proposal> proposals = new ArrayList<>();

    // Graphviz snapshots
    private boolean snapshotEnable = false;

    public Weight.WeightType getWeightType() {
        return weightType;
    }

    public void setWeightType(Weight.WeightType weightType) {
        this.weightType = weightType;
    }

    public Integer getNumberNodes() {
        return numberNodes;
    }

    public void setNumberNodes(Integer numberNodes) {
        this.numberNodes = numberNodes;
    }

    public Integer getpoisonRateInMs() {
        return poisonRateInMs;
    }

    public void setpoisonRateInMs(Integer poisonRateInMs) {
        this.poisonRateInMs = poisonRateInMs;
    }

    public Integer getnodeDownTimeInMs() {
        return nodeDownTimeInMs;
    }

    public void setnodeDownTimeInMs(Integer nodeDownTimeInMs) {
        this.nodeDownTimeInMs = nodeDownTimeInMs;
    }

    public Integer getProposalIntervalInMs() {
        return proposalIntervalInMs;
    }

    public void setProposalIntervalInMs(Integer proposalIntervalInMs) {
        this.proposalIntervalInMs = proposalIntervalInMs;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public Integer getMessageDelayInMs() {
        return messageDelayInMs;
    }

    public void setMessageDelayInMs(Integer messageDelayInMs) {
        this.messageDelayInMs = messageDelayInMs;
    }

    public List<Integer> getNodesToPoison() {
        return nodesToPoison;
    }

    public void setNodesToPoison(List<Integer> nodesToPoison) {
        this.nodesToPoison = nodesToPoison;
    }

    public boolean isPoisonAllAtOnce() {
        return poisonAllAtOnce;
    }

    public void setPoisonAllAtOnce(boolean poisonAllAtOnce) {
        this.poisonAllAtOnce = poisonAllAtOnce;
    }

    public boolean isSnapshotEnable() {
        return snapshotEnable;
    }

    public void setSnapshotEnable(boolean snapshotEnable) {
        this.snapshotEnable = snapshotEnable;
    }
}
