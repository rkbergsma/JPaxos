package config;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ConfigParser {
    public static PaxosConfig parseConfig(String config) throws IOException {
        PaxosConfig paxosConfig;
        Gson gson = new Gson();

        try (Reader reader = new FileReader(config)){
            paxosConfig = gson.fromJson(reader, PaxosConfig.class);
            System.out.println("numberNodes: " + paxosConfig.getNumberNodes());
            System.out.println("numberProposals: " + paxosConfig.getProposals().size());
            System.out.println("proposalDelay: " + paxosConfig.getProposalIntervalInMs());
            System.out.println("weightType: " + paxosConfig.getWeightType());
            System.out.println("poisonAllAtOnce: " + paxosConfig.isPoisonAllAtOnce());
            System.out.println("poisonFrequency: " + paxosConfig.getpoisonRateInMs());
            System.out.println("poisonTime: " + paxosConfig.getnodeDownTimeInMs());
            return paxosConfig;
        } catch (FileNotFoundException e) {
            System.err.println("Could not find config file!");
            throw e;
        }
    }
}
