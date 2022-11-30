package top;

import config.ConfigParser;
import config.PaxosConfig;
import config.Proposal;
import graph.Snapshot;
import messages.Message;
import messages.PoisonPillMessage;
import messages.ProposeMessage;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Orchestrator {
    private static CommandLine cmd;
    private static Snapshot snapshot = new Snapshot("snapshots/");

    public static void main (String[] args) {
        parseCommandLineArgs(args);
        if (cmd.hasOption("config")) {
            runFullTest();
        } else {
            runInteractiveTest();
        }
    }

    public static void updateGraph() {
        snapshot.drawGraph();
    }

    private static void parseCommandLineArgs(String[] args) {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helper = new HelpFormatter();
        Options options = getOptions();
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helper.printHelp("Usage:", options);
            System.exit(0);
        }
    }
    public static Options getOptions() {
        Options options = new Options();

        Option config = Option.builder("c").longOpt("config")
                .argName("config")
                .hasArg()
                .required(false)
                .desc("Config file for test")
                .build();
        options.addOption(config);

        Option nodes = Option.builder("n").longOpt("nodes")
                .argName("nodes")
                .hasArg()
                .required(false)
                .desc("Number of nodes")
                .build();
        options.addOption(nodes);

        Option weightType = Option.builder("w").longOpt("weightType")
                .argName("weightType")
                .hasArg()
                .required(false)
                .desc("Weight type (random, uniform, exponential, SPLIT5050, SPLIT6040, SPLIT8020, SPLIT9010)")
                .build();
        options.addOption(weightType);

        Option messageDelay = Option.builder("d").longOpt("messageDelay")
                .argName("messageDelay")
                .hasArg()
                .required(false)
                .desc("Message delay in ms")
                .build();
        options.addOption(messageDelay);

        Option snapshotEnable = Option.builder("s").longOpt("snapshotEnable")
                .argName("snapshotEnable")
                .hasArg()
                .required(false)
                .desc("Enable taking snapshots and drawing with graphviz")
                .build();
        options.addOption(snapshotEnable);

        return options;
    }

    private static void runFullTest() {
        PaxosConfig paxosConfig;
        try {
            paxosConfig = ConfigParser.parseConfig(cmd.getOptionValue("config"));
        } catch (IOException e) {
            System.err.println("Error parsing config file!");
            throw new RuntimeException(e);
        }

        Map<Integer, Queue<Message>> nodes = makeNodes(paxosConfig.getNumberNodes());
        List<Double> weights = Weight.getWeights(paxosConfig.getWeightType(), nodes.size());
        startNodes(nodes, weights, Long.valueOf(paxosConfig.getMessageDelayInMs()), paxosConfig.isSnapshotEnable());
        snapshot.drawGraph();
        runTestFromConfig(paxosConfig, nodes);
    }

    private static void runTestFromConfig(PaxosConfig paxosConfig, Map<Integer, Queue<Message>> nodes) {
        long startTime = System.nanoTime();
        long poisonInterval = System.nanoTime();
        boolean allPoisoned = false;
        for (Proposal proposal : paxosConfig.getProposals()) {
            if (nodes.containsKey(proposal.getNode())){
                System.out.println("Sending propose message to " + proposal.getNode());
                nodes.get(proposal.getNode()).offer(new ProposeMessage(69, proposal.getValue()));
            }

            try {
                Thread.sleep(Long.valueOf(paxosConfig.getProposalIntervalInMs()));
            } catch (InterruptedException e) {
                System.err.println("Error sleeping between proposals!");
            }

//            System.out.println("poison interval for diff: " + poisonInterval);
//
//            System.out.println("Time diff: " + (System.nanoTime() - poisonInterval)/1000 + ", poisonRate: " + paxosConfig.getpoisonRateInMs());
            if (((System.nanoTime() - poisonInterval)/1000000) > paxosConfig.getpoisonRateInMs()){
                if (paxosConfig.isPoisonAllAtOnce()){
                    if (!allPoisoned){
                        if (paxosConfig.getNodesToPoison().isEmpty()){
                            Random r = new Random();
                            Integer pid = r.nextInt(nodes.size());
                            if (nodes.containsKey(pid)){
                                System.out.println("Sending poison message to " + pid);
                                nodes.get(pid).offer(new PoisonPillMessage(69, pid, paxosConfig.getnodeDownTimeInMs()));
                            }
                        } else {
                            for (Integer pid : paxosConfig.getNodesToPoison()){
                                if (nodes.containsKey(pid)){
                                    System.out.println("Sending poison message to " + pid);
                                    nodes.get(pid).offer(new PoisonPillMessage(69, pid, paxosConfig.getnodeDownTimeInMs()));
                                }
                            }
                        }
                        allPoisoned = true;
                    }
                } else {
                    Random r = new Random();
                    Integer pid = r.nextInt(nodes.size());
                    if (nodes.containsKey(pid)){
                        System.out.println("Sending poison message to " + pid);
                        nodes.get(pid).offer(new PoisonPillMessage(69, pid, paxosConfig.getnodeDownTimeInMs()));
                    }
                }
                poisonInterval = System.nanoTime();
            }
        }
//        boolean allDone = false;
//        while (!allDone){
//            allDone = true;
//            for (Queue<Message> node : nodes.values()) {
//                if (node.size() > 0){
//                    allDone = false;
//                }
//            }
//        }
        long totalElapsedTime = (System.nanoTime() - startTime)/1000;
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            System.err.println("Error sleeping at end!");
        }
        System.out.println("Total time in us: " + totalElapsedTime);
        System.exit(0);
    }

    private static void runInteractiveTest() {
        Integer numNodes = Integer.parseInt(cmd.getOptionValue("nodes", "10"));
        Map<Integer, Queue<Message>> nodes = makeNodes(numNodes);
        List<Double> weights = Weight.getWeights(Weight.WeightType.valueOf(cmd.getOptionValue("weightType", "exponential").toUpperCase()), nodes.size());
        startNodes(nodes, weights, Long.parseLong(cmd.getOptionValue("messageDelay", "50")), cmd.hasOption("snapshotEnable"));
        snapshot.drawGraph();
        takeUserInput(nodes);
    }

    private static void takeUserInput(Map<Integer, Queue<Message>> nodes) {
        // loop scanner to take user input
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()) {
            String cmd = sc.nextLine();
            String[] tokens = cmd.split(" ");

            if (tokens[0].equals("die")) {
                break;
            } else if (tokens[0].equals("poison")) {
                Integer pid = Integer.parseInt(tokens[1]);
                Integer downTime = Integer.parseInt(tokens[2]);
                for (Queue<Message> node : nodes.values()) {
                    node.offer(new PoisonPillMessage(69, pid, downTime));
                }
            } else if (tokens[0].equals("propose")) {
                Integer pid = Integer.parseInt(tokens[1]);
                Integer proposalValue = Integer.parseInt(tokens[2]);
                if (nodes.containsKey(pid)){
                    System.out.println("Sending propose message to " + pid);
                    nodes.get(pid).offer(new ProposeMessage(69, proposalValue));
                }
            } else{
                System.out.println("Would do something with your command here.");
            }
        }
        sc.close();
    }

    private static void startNodes(Map<Integer, Queue<Message>> nodes, List<Double> weights, long sleepTime, boolean snapshotEnable) {
        for (Integer id : nodes.keySet()){
            Map<Integer, Queue<Message>> peerMapping = new HashMap<>();
            for (Integer peerId : nodes.keySet()){
                if (!id.equals(peerId)){
                    peerMapping.put(peerId, nodes.get(peerId));
                }
            }
            Node node = new Node(id, nodes.get(id), peerMapping, weights, sleepTime, snapshotEnable);
            snapshot.addNode(node);
            node.start();
        }
    }

    private static Map<Integer, Queue<Message>> makeNodes(int numNodes) {
        Map<Integer, Queue<Message>> nodes = new HashMap<>();
        for (Integer i = 0; i < numNodes; i++) {
            Queue<Message> mailbox = new ConcurrentLinkedQueue<>();
            nodes.put(i, mailbox);
        }
        return nodes;
    }
}
