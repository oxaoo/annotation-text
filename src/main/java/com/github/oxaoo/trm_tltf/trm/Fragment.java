package com.github.oxaoo.trm_tltf.trm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Fragment of a document.
 * Represents the weighed vector of words.
 *
 * @author Alexander Kuleshov
 */
public class Fragment {
    private static int count = 0;

    private int id;
    private List<String> words;
    private List<Integer> wordsWeight;
    private int globalWeight;
    private int importance;

    public Fragment(List<String> words) {
        id = ++count;
        globalWeight = 0;
        this.words = words;
    }

    public void calcWeight(Map<String, Integer> globalWordsWeight) {
        wordsWeight = new ArrayList<>(words.size());
        for (String word : words) {
            String _word = word.toLowerCase();
            int weight = 0;
            if (globalWordsWeight.containsKey(_word))
                weight = globalWordsWeight.get(_word);

            wordsWeight.add(weight);
            globalWeight += weight;
        }
        //important!
        Collections.sort(wordsWeight, Collections.<Integer>reverseOrder());
    }

    public void incWeight() {
        globalWeight++;
    }

    /**
     * Scalar product of two fragments
     *
     * @param f1 first fragment
     * @param f2 second fragment
     * @return scalar product
     */
    public static double scalarProduct(Fragment f1, Fragment f2) {
        int len = Math.min(f1.wordsWeight.size(), f2.wordsWeight.size());
        long numerator = 0;
        long denominator1 = 0, denominator2 = 0;

        for (int i = 0; i < len; i++) {
            numerator += f1.wordsWeight.get(i) * f2.wordsWeight.get(i);
            denominator1 += Math.pow(f1.wordsWeight.get(i), 2);
            denominator2 += Math.pow(f2.wordsWeight.get(i), 2);
        }

        return numerator / (Math.sqrt(denominator1) * Math.sqrt(denominator2));
    }

    public void incImportance() {
        importance++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public int getGlobalWeight() {
        return globalWeight;
    }

    public void setGlobalWeight(int globalWeight) {
        this.globalWeight = globalWeight;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fragment fragment = (Fragment) o;

        return id == fragment.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Fragment{" +
                "id=" + id +
                ", globalWeight=" + globalWeight +
                ", importance=" + importance +
                '}';
    }
}