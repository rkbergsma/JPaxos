package graph;

import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import org.apache.commons.io.FileUtils;
import top.Node;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.attribute.Rank.RankDir.LEFT_TO_RIGHT;
import static guru.nidi.graphviz.model.Factory.mutGraph;

public class Snapshot {
    File outputDir;
    List<GraphNode> nodes;
    int snapshotNum = 0;
    boolean inProgress = false;

    public Snapshot(String outputDir) {
        this.outputDir = new File(outputDir);
        this.nodes = new ArrayList<>();
        initOutputDir();
    }

    private void initOutputDir() {
        try {
            if (!outputDir.exists()) {
                Files.createDirectory(outputDir.toPath());
            }
            FileUtils.cleanDirectory(outputDir);
            List<Path> imagePaths = getImagePaths();
            for (Path image : imagePaths) {
                FileUtils.copyFileToDirectory(new File("classes/" + image.toString()), outputDir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNode(Node n) {
        GraphNode gn = new GraphNode(n);
        nodes.add(gn);
    }

    public void drawGraph() {
        if (inProgress) {
            return;
        }

        inProgress = true;
        if (!allNodesReady()) {
            return;
        }

        snapshotNum++;
        MutableGraph g = mutGraph().setDirected(false);
        g.graphAttrs().add(Rank.dir(LEFT_TO_RIGHT));
        g.linkAttrs().add(Style.INVIS);
//        g.nodeAttrs().add(Font.name("Monospaced"));

        List<MutableGraph> clusters = new ArrayList<>();
        for (GraphNode n : nodes) {
            MutableGraph cluster = n.getSubgraph();
            clusters.add(cluster);
        }

        int rowLength = 3;
        int lastAdded = 0;
        for (int i = 1; i < clusters.size(); i++) {
            if (i % rowLength == 0) {
                int adding = i - rowLength;
                g.add(clusters.get(adding));
                lastAdded = adding;
            }
            else {
                clusters.get(i - 1).addLink(clusters.get(i));
            }
        }
        int adding = lastAdded + rowLength;
        g.add(clusters.get(adding));

        long startTime = System.currentTimeMillis();
        try {
            Graphviz.fromGraph(g).basedir(outputDir).height(2000).render(Format.PNG).toFile(new File(outputDir + "/paxos" + snapshotNum + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;
        System.out.println("Drew snapshot " + snapshotNum + " in " + elapsed + "ms");

        unpauseAll();
        inProgress = false;
    }

    private boolean allNodesReady() {
        int numReady = 0;
        int numNodes = nodes.size();

        boolean ready = false;
        while (!ready) {
            numReady = 0;
            for (GraphNode n : nodes) {
                if (n.isPaused()) {
                    numReady++;
                }
            }
            if (numReady == numNodes) {
                ready = true;
            }
        }

        return true;
    }

    public void unpauseAll() {
        for (GraphNode n : nodes) {
            n.unpause();
        }
    }

    private List<Path> getImagePaths() {
        List<Path> paths = new ArrayList<>();

        // get path of the current running JAR
        try {
            String jarPath = getClass().getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath();
//        System.out.println("JAR Path :" + jarPath);

            // file walks JAR
            URI uri = URI.create("jar:file:" + jarPath);
            try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                paths = Files.walk(fs.getPath("images"))
                        .filter(Files::isRegularFile)
                        .map(file -> file.toAbsolutePath())
                        .collect(Collectors.toList());
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        return paths;
    }
}
