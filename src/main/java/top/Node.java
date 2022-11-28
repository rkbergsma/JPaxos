package top;

import messages.Message;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Node extends Thread{
    private final Integer myId;
    private final Queue<Message> mailbox;
    private final Map<Integer, Queue<Message>> peers;
    private final List<Double> weights;

    // To be set by top.Orchestrator:
    private long sleepTime;
    private final String outputFile;  // use this to write state for each node. Each node could have own file or all write to one file (use lock)

    // Paxos pseudocode reference: https://www.mydistributed.systems/2021/04/paxos.html
    // Proposer State:
    public Integer proposedValue = null;   //proposed value
    public Integer proposalNumber;     //proposal number
    public Integer numAcks = 0;            //number of acks received from acceptors
    public Double responseWeightSum = 0.0;
    public boolean responseThresholdMet = false;
    public Double nackWeightSum = 0.0;
    public boolean nackThresholdMet = false;
    public List<Promise> promises = new ArrayList<>();   //promises received from acceptors
    public Double promiseWeightSum = 0.0;
    public boolean promiseThresholdMet = false;

    // Acceptor State:
    public Integer acceptedProposalNumber = null;   //the proposal number of the value accepted by the acceptor
    public Integer promisedProposalNumber = null;   //the highest proposal number the acceptor has received
    public Integer acceptedValue = null;                   //the value accepted by the acceptor

    // Learner State:
    public Integer decidedValue = null;

    // Current message being processed for Graphviz
    public Message currentMessage;

    public Node (Integer myId, Queue<Message> mailbox, Map<Integer, Queue<Message>> peers, List<Double> weights, long sleepTime){
        this.myId = myId;
        this.mailbox = mailbox;
        this.peers = peers;
        this.outputFile = "/tmp/node_" + myId.toString() + "_state.txt";
        this.proposalNumber = myId;
        this.weights = weights;
        this.sleepTime = sleepTime;
        //this.myWeight = weights.get(myId);
    }

    public void run() {
        System.out.println("Starting node " + myId + " with weight " + getWeight(myId));
        while (true) {
            Message message = mailbox.poll();
            currentMessage = message;
            if (message != null){
                processMessage(message);
            }
        }
    }

    private void processMessage(Message message){
        System.out.println(message.printMessage(myId));
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        message.process(this);
        Orchestrator.updateGraph();
    }

    public void writeState(){
        StringBuilder state = new StringBuilder();
        state.append("acceptedProposalNumber=");
        state.append(acceptedProposalNumber);
        state.append(",promisedProposalNumber=");
        state.append(promisedProposalNumber);
        state.append(",acceptedValue=");
        state.append(acceptedValue);
        state.append("\n");

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                Files.newOutputStream(Paths.get(outputFile)), StandardCharsets.UTF_8))) {
            writer.write(state.toString());
        } catch (IOException e) {
            System.err.println("Error writing state for node " + myId);
        }
    }

    public void readState(){
        File file = new File(outputFile);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = br.readLine()) != null){
                String[] stateComponents = line.split(",");
                if (stateComponents[0] != null) {
                    acceptedProposalNumber = stateComponents[0].split("=")[1].equals("null") ? null : Integer.parseInt(stateComponents[0].split("=")[1]);
                } else {
                    acceptedProposalNumber = null;
                }
                if (stateComponents[1] != null) {
                    promisedProposalNumber = stateComponents[1].split("=")[1].equals("null") ? null : Integer.parseInt(stateComponents[1].split("=")[1]);
                } else {
                    promisedProposalNumber = null;
                }
                if (stateComponents[2] != null) {
                    acceptedValue = stateComponents[2].split("=")[1].equals("null") ? null : Integer.parseInt(stateComponents[2].split("=")[1]);
                } else {
                    acceptedValue = null;
                }
            }
        } catch (IOException e){
            System.err.println("Error reading node state");
        }
    }

    public void writeOfficialLog(){
        StringBuilder finalValueToLog = new StringBuilder();
        finalValueToLog.append("officialDecidedValue=");
        finalValueToLog.append(proposedValue);
        finalValueToLog.append("\n");

        try (BufferedWriter out = new BufferedWriter(
                new FileWriter("/tmp/paxos_out.log", true));) {
            out.write(finalValueToLog.toString());
        } catch (IOException e) {
            System.err.println("Error writing logfile");
        }
    }

    public Integer getMyId() {
        return myId;
    }

    public Queue<Message> getMailbox() {
        return mailbox;
    }

    public Map<Integer, Queue<Message>> getPeers() {
        return peers;
    }

    public void incrementProposalNumber() {
        proposalNumber = proposalNumber + 1;
        while (proposalNumber % (peers.size() + 1) != myId){
            proposalNumber = proposalNumber + 1;
        }
    }

    public Double getWeight(Integer nodeNumber) {
        return weights.get(nodeNumber);
    }
}
