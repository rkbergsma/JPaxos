package graph;


import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import top.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static guru.nidi.graphviz.attribute.Rank.RankDir.LEFT_TO_RIGHT;
import static guru.nidi.graphviz.model.Factory.mutGraph;

public class Snapshot {
    String outputDir = "";
    List<GraphNode> nodes;
    int snapshotNum = 0;

    public Snapshot(String outputDir) {
        this.outputDir = outputDir;
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node n) {
        GraphNode gn = new GraphNode(n);
        nodes.add(gn);
    }

    public void drawGraph() {
        snapshotNum++;
        MutableGraph g = mutGraph().setDirected(false);
        g.graphAttrs().add(Rank.dir(LEFT_TO_RIGHT));
        g.linkAttrs().add(Style.INVIS);

        List<MutableNode> idNodes = new ArrayList<>();
        List<MutableGraph> clusters = new ArrayList<>();
        List<MutableNode> states = new ArrayList<>();
        for (GraphNode n : nodes) {
            MutableGraph cluster = n.getSubgraph();
            clusters.add(cluster);
//            MutableNode s = n.getStateNode();
//            states.add(s);
        }

        int rowLength = 3;
        int lastAdded = 0;
        for (int i = 1; i < clusters.size(); i++) {
            if (i % rowLength == 0) {
                int adding = i - rowLength;
//                System.out.println("Adding clusters[" + adding + "] to graph");
                g.add(clusters.get(adding));
                lastAdded = adding;
            }
            else {
//                System.out.println("Adding link from " + (i - 1) + " to " + i);
                clusters.get(i - 1).addLink(clusters.get(i));
            }
        }
        int adding = lastAdded + rowLength;
//        System.out.println("Adding clusters[" + adding + "] to graph");
        g.add(clusters.get(adding));

        try {
            Graphviz.fromGraph(g).height(709).render(Format.PNG).toFile(new File(outputDir + "/paxos" + snapshotNum + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
