package com.github.oxaoo.trm_tltf.tltf;

import com.github.oxaoo.trm_tltf.Fragment;
import org.apache.log4j.Logger;

import java.util.*;

public class TLTF {
    private final static Logger log = Logger.getLogger(TLTF.class.getName());

    private Map<String, Integer> wordsLF;
    private double threshold;

    public double determinationLocalProperties(List<Fragment> fragments, Map<String, Integer> wordsLF) {
        this.wordsLF = wordsLF;
        threshold = Collections.max(wordsLF.values()) / 3.0; //for english language.
//        threshold = Collections.max(wordsLF.values()) / 1.5; //for russian language.
        double maxLocalWeight = 0;

        for (Fragment fragment : fragments) {
            List<Integer> clusters = fragmentationByClusters(fragment);
            int numWords = fragment.getWords().size();
            double localWeight = findLargeCluster(clusters, numWords);
            fragment.setLocalWeight(localWeight);

            if (localWeight > maxLocalWeight) maxLocalWeight = localWeight;
        }

        return maxLocalWeight;
    }

    public List<Integer> fragmentationByClusters(Fragment fragment) {
        List<Integer> clusters = new ArrayList<>();

        List<String> words = fragment.getWords();
//        Stack<String> stackWords = new Stack<>();
        int signWords = 0, nonSignWords = 0;

        for (String word : words) {
            String _word = word.toLowerCase();
            int lf = wordsLF.get(_word);
            if (lf > threshold) { // is signification word
//                stackWords.push(word);
                signWords++;
                nonSignWords = 0;
            } else {
                nonSignWords++;
                if (nonSignWords == 3) {
                    clusters.add(signWords);
//                    stackWords.empty();
                    signWords = 0;
                    nonSignWords = 0;
                }
//                else stackWords.push(word);
            }
        }

        return clusters;
    }

    private double findLargeCluster(List<Integer> clusters, int numWords) {
        /*double maxValue = 0;

        for(int cluster : clusters) {
            double value = (double) (cluster * cluster) / (double) numWords;
            if (value > maxValue) maxValue = value;
        }*/

        //alternative
        int maxCluster = 0;
        if (!clusters.isEmpty()) maxCluster = Collections.max(clusters);
        return (double) (maxCluster * maxCluster) / (double) numWords;
    }
}
