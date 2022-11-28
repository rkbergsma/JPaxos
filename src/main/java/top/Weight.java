package top;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Weight {
    public enum WeightType {
        @SerializedName("random")
        RANDOM,
        @SerializedName("uniform")
        UNIFORM,
        @SerializedName("exponential")
        EXPONENTIAL,
        @SerializedName("split5050")
        SPLIT5050,
        @SerializedName("split6040")
        SPLIT6040,
        @SerializedName("split8020")
        SPLIT8020,
        @SerializedName("split9010")
        SPLIT9010
    }

    public static List<Double> getWeights(WeightType weightType, int size) {
        List<Double> weights = new ArrayList<>();;
        if (weightType == WeightType.EXPONENTIAL){
            weights = getExponentialWeights(size);
        } else if (weightType == WeightType.RANDOM){
            weights = getRandomWeights(size);
        } else if (weightType == WeightType.UNIFORM){
            weights = getUniformWeights(size);
        } else if (weightType == WeightType.SPLIT5050){
            weights = getSplitWeights(size, size/2);
        } else if (weightType == WeightType.SPLIT6040){
            weights = getSplitWeights(size, size*3/5);
        } else if (weightType == WeightType.SPLIT8020){
            weights = getSplitWeights(size, size*4/5);
        } else if (weightType == WeightType.SPLIT9010){
            weights = getSplitWeights(size, size*9/10);
        }
        weights = normalizeWeights(weights);
        return weights;
    }

    static List<Double> getRandomWeights(int size) {
        List<Double> weights = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < size; i++){
            weights.add(rand.nextDouble());
        }

        return weights;
    }

    static List<Double> getUniformWeights(int size) {
        List<Double> weights = new ArrayList<>();
        for (int i = 0; i < size; i++){
            weights.add(1.0);
        }

        return weights;
    }

    static List<Double> getExponentialWeights(int size) {
        Double currentWeight = (double) size;
        List<Double> weights = new ArrayList<>();
        for (int i = 0; i < size; i++){
            weights.add(currentWeight);
            currentWeight = currentWeight / 2.0;
        }

        return weights;
    }

    static List<Double> getSplitWeights(int numNodes, int numTrusted) {
        List<Double> weights = new ArrayList<>();
        int trustedCount = 0;
        for (int i = 0; i < numNodes; i++){
            if (trustedCount < numTrusted){
                weights.add(1.0);
            } else {
                weights.add(0.0);
            }
            trustedCount = trustedCount + 1;
        }

        return weights;
    }

    static List<Double> normalizeWeights(List<Double> weights) {
        Double sum = 0.0;
        for (int i = 0; i < weights.size(); i++){
            sum = sum + weights.get(i);
        }

        List<Double> normalizedWeights = new ArrayList<>();
        for(Double w : weights) {
            normalizedWeights.add(w/sum);
        }

        return normalizedWeights;
    }
}
