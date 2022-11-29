package graph;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import messages.Message;
import top.Node;
import top.Promise;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static guru.nidi.graphviz.attribute.Records.rec;
import static guru.nidi.graphviz.attribute.Records.turn;
import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;
import static javafx.application.Platform.exit;

public class GraphNode {
    Node n;

    public GraphNode(Node n) {
        this.n = n;
    }

    public String getId() {
        return this.n.getMyId().toString();
    }

    public MutableGraph getSubgraph() {
        MutableGraph cluster = mutGraph(n.getMyId() + " cluster").setCluster(true);

        MutableNode image = image();
        cluster.add(image);

        MutableNode state = stateNode();
        cluster.add(state);

        return cluster;
    }

    public MutableNode getStateNode() {
        MutableNode state = stateNode();
        return state;
    }

    private String id() {
        String id = turn(
                rec("id", "Node ID: " + n.getMyId())
        );
        return id;
    }

    private MutableNode image() {
        MutableNode image = mutNode(n.getMyId() + " image");

        if (n.getMyId().equals(0)) {
            image.add(Image.of("garg.PNG"));
        } else {
            image.add(Image.of("vnice.jpg"));
        }
        image.add(Shape.RECTANGLE);
        image.add(Label.of(""));

        return image;
    }

    private String currentMessage() {
        Message currentMessage = n.currentMessage;

        String messageString = "queue is empty";
        if (currentMessage != null) {
            messageString = currentMessage.printMessage(n.getMyId());
        }
        String messageRecord = turn(
                rec("cm", "Message: " + messageString)
        );

        return messageRecord;
    }

    private String proposerState() {
        String proposedValue = "null";
        if (n.proposedValue != null) {
            proposedValue = n.proposedValue.toString();
        }

        String proposalNumber = "null";
        if (n.proposalNumber != null) {
            proposalNumber = n.proposalNumber.toString();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Promise p : n.promises) {
            sb.append(p.getPromiseId());
            sb.append(":");
            sb.append(p.getValue());
            sb.append(", ");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        String promiseString = sb.toString();

        String proposer = turn(
                rec("ps", "Proposer State"),
                rec("pv", "proposedValue: " + proposedValue),
                rec("pn", "proposalNumber: " + proposalNumber),
                rec("na", "numAcks: " + n.numAcks),
                rec("rws", "responseWeightSum: " + n.responseWeightSum),
                rec("rtm", "responseThresholdMet: " + n.responseThresholdMet),
                rec("nws", "nackWeightSum: " + n.nackWeightSum),
                rec("ntm", "nackThresholdMet: " + n.nackThresholdMet),
                rec("p", "promises: " + promiseString)
        );

        return proposer;
    }

    private String acceptorState() {
        String acceptedProposalNumber = "null";
        if (n.acceptedProposalNumber != null) {
            acceptedProposalNumber = n.acceptedProposalNumber.toString();
        }

        String promisedProposalNumber = "null";
        if (n.promisedProposalNumber != null) {
            promisedProposalNumber = n.promisedProposalNumber.toString();
        }

        String acceptedValue = "null";
        if (n.acceptedValue != null) {
            acceptedValue = n.acceptedValue.toString();
        }

        String acceptor = turn(turn(
                rec("as", "Acceptor State"),
                rec("apn", "acceptedProposalNumber: " + acceptedProposalNumber),
                rec("ppn", "promisedProposalNumber: " + promisedProposalNumber),
                rec("av", "acceptedValue: " + acceptedValue)
        ));

        return acceptor;
    }

    private String learnerState() {
        String decidedValue = "null";
        if (n.decidedValue != null) {
            decidedValue = n.decidedValue.toString();
        }

        String learner = turn(turn(
                rec("ls", "Learner State"),
                rec("dv", "decidedValue: " + decidedValue)
        ));

        return learner;
    }

    private MutableNode stateNode() {
        MutableNode stateNode = mutNode(n.getMyId() + " state");
        stateNode.add(Records.of(
                turn(id()),
                turn(currentMessage()),
                turn(
                        turn(
                                acceptorState(),
                                rec("sep", ""),
                                learnerState(),
                                rec("sep1", ""),
                                rec("sep2", "")
                        ),
                        proposerState()
                )
        ));
        return stateNode;
    }
}
