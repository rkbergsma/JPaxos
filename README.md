# JPaxos

This project is an implementation of Paxos in Java. Specifically, this is an implementation of a weighted Paxos. To understand Paxos, please reference [Paxos Made Simple](https://www.microsoft.com/en-us/research/uploads/prod/2016/12/paxos-simple-Copy.pdf) by Leslie Lamport.

## Downloading the Project
To download the project, you can either clone the repository or download the jar from the [Releases Page](https://github.com/rkbergsma/JPaxos/releases).  

To clone the repository, it is recommended to clone the repo into an IDE that supports Maven for easy building. We used IntelliJ to build the project. There are good instructions [here](https://www.jetbrains.com/help/idea/cloning-repository.html#clone_project_from_welcome_screen) to clone a repository into IntelliJ.  

Alternatively, if you want to simply run the project, you can download the jar and run it command-line. The [Releases Page](https://github.com/rkbergsma/JPaxos/releases) has all of the releases listed. Simply download the jar and ensure you have at least Java8 on your system. Then you can run the code using:
```
java -jar JPaxos-1.0-SNAPSHOT-shaded.jar [OPTIONS]
```

The options will be as follows (more details in the "Running the Code" section):
```
usage: Usage:
 -c,--config <config>               Config file for test
 -d,--messageDelay <messageDelay>   Message delay in ms
 -n,--nodes <nodes>                 Number of nodes
 -w,--weightType <weightType>       Weight type (random, uniform,
                                    exponential, SPLIT5050, SPLIT6040,
                                    SPLIT8020, SPLIT9010)
```

## Running the Code
The code can be run in two modes, either in an interactive command-line mode, or in a full test configuration file mode.  

To start the program in interactive mode, either call the jar without any arguments, or specify any of the following:
```
usage: Usage:                                                          
 -c,--config <config>               Config file for test               
 -d,--messageDelay <messageDelay>   Message delay in ms                
 -n,--nodes <nodes>                 Number of nodes                    
 -s,--snapshotEnable                Enable taking snapshots and drawing
                                    with graphviz                      
 -w,--weightType <weightType>       Weight type (random, uniform,      
                                    exponential, SPLIT5050, SPLIT6040, 
                                    SPLIT8020, SPLIT9010)
```

In the interactive mode, the user can propose a value to a given node and then watch as all the nodes come to a consensus. The prompt then returns to the user, at which point the user can propose another value and watch the nodes again come to consensus. The user also has an option to send a "poison" message to kill a node for a specified amount of time (in seconds). These two commands should be formatted as follows:
```
// Propose message:
propose NODE_NUMBER PROPOSAL_VALUE

// Poison message:
poison NODE_NUMBER POISON_TIME_S

// Die message (to end the program):
die
```

The other way to run the program is to specify a config.json file that is fed to the program. This is handy for testing a series of proposals in a quick back-to-back fashion. Pre-made configuration files can be found [here](https://github.com/rkbergsma/JPaxos/tree/master/testConfigs). The configuration file can have any of the following fields (defaults shown below):
```
{
  "weightType": "uniform",
  "numberNodes": 10,
  "poisonRateInMs": 90,
  "nodeDownTimeInMs": 10,
  "poisonAllAtOnce": false
  "nodesToPoison": [1, 5, 3, ...],
  "proposalIntervalInMs": 1000,
  "messageDelayInMs": 50,
  "proposals": [
    {"node":8,"value":44},
    {"node":2,"value":50},
    ...
  ],
  "snapshotEnable": false
}
```

To kick off the program via a config file, run it as:
```
java -jar JPaxos-1.0-SNAPSHOT.jar -c /path/to/config.json
```

Currently, when running in either mode, the program outputs the output files to the `/tmp` directory or stdout. There will be a main file, called `paxos_out.log`. This file can be thought of as the "final say" of all nodes collectively once consensus has been reached. There will also be one file per node, called `node_*_state.txt`. This file holds the state of each node, since the algorithm requires that the state is written prior to certain messages being sent. Finally, stdout can be watched or piped to a file; the stdout will show what messages are being sent from which node and to which node, as well as when values are decided upon by each node. If the stdout is piped to a file, it can later be grepped for each Learner to show that all Learners learn the values in the same order, which is the purpose and intention of the algorithm.

## Description of Paxos Concepts
Paxos as an algorithm employs 3 agents: proposers, acceptors, and learners. A node can be any combination of the 3 agents, and it is common for a node to act as all 3 agents. The proposer first sends a [Prepare Message](https://github.com/rkbergsma/JPaxos/blob/master/src/main/java/messages/PrepareMessage.java) to the set of acceptors, asking for a promise in return. The acceptors send back a [Promise Message](https://github.com/rkbergsma/JPaxos/blob/master/src/main/java/messages/PromiseMessage.java) if certain criteria are met, and a [Nack Message](https://github.com/rkbergsma/JPaxos/blob/master/src/main/java/messages/NackMessage.java) otherwise. Then, upon receiving a sufficient number of Promise messages, a Proposer sends an [Accept Message](https://github.com/rkbergsma/JPaxos/blob/master/src/main/java/messages/AcceptMessage.java) to the acceptors. The acceptors then respond with an [Accepted Message](https://github.com/rkbergsma/JPaxos/blob/master/src/main/java/messages/AcceptedMessage.java). Finally, the proposer will send a [Decide Message](https://github.com/rkbergsma/JPaxos/blob/master/src/main/java/messages/DecideMessage.java) to the learners so that everyone learns the new decided-upon value. This process can then repeat in cycles with new proposals.

## Description of Architecture
The code is broken down into several packages. There are three main packages: config, messages, and top package.  

### config
The `config` package houses the ConfigParser, PaxosConfig, and Proposal classes. These three classes are used to parse a user's config input, which determine how the overall distributed system is set up and how it behaves at runtime.

### messages
The `messages` package holds the interface `Message` which defines what a message is. Then, there are 8 message implementations:
1. AcceptedMessage - Sent by acceptor to proposer once proposal has been finalized
2. AcceptMessage - Sent by proposer to acceptor asking to accept this proposal
3. DecideMessage - Sent by proposer to learner to disseminate that a value has been decided
4. NackMessage - Sent by acceptor to proposer when criteria are not met on either Accept or Prepare
5. PoisonPillMessage - Sent by the Orchestrator to all nodes to simulate a node going offline/crashing
6. PrepareMessage - Sent by proposer to acceptor to prepare a new proposal
7. PromiseMessage - Sent by acceptor to proposer when proposal criteria are met
8. ProposeMessage - Sent by Orchestrator to proposer to propose a new value

### top
The `top` package holds the top-level classes need to run the program, including Node, Orchestrator, Promise, and Weight. The Orchestrator can be thought of as Main, as it creates all the Queues used for constructing each node and it then starts each node as its own thread. A node is a continuously running thread that has a Queue mailbox for incoming messages, as well as a HashMap of Queues for its peer nodes. This allows each node to processes each message in sequence and update state as necessary, as well as sending along any other messages. The Weight class contains the code and definitions for different weight schemes when using weighted Paxos. These weights give some nodes more say than others when deciding a proposed value. When `UNIFORM` weights are used, it reduces to regular Paxos. However, the user can also specify `RANDOM`, `EXPONENTIAL`, or various `SPLIT` weights.

### graph
The `graph` package contains the code to generate the snapshot pictures. By default, the drawing of snapshot pictures is disabled. To enable, set the `-s` or `--snapshotEnable` flag on the command line. The code utilizes [the graphviz-java](https://github.com/nidi3/graphviz-java) implementation of GraphViz to draw the graphs. Each graph depicts the system state and is saved as a `.png` file in the `target/snapshots/` output directory if the full repository is cloned, otherwise they will be saved to the current working directory of the jar. Each node will attempt to initiate a snapshot right after it receives a new message and it won't look at new messages until the snapshot is done. By default, the code will utilize the javascript rendering engine to draw the pictures. There can be issues with the font and spacing when using this method. To ensure properly formatted text, please download and install [the GraphViz command line tool](https://graphviz.org/download/) according to your system.
