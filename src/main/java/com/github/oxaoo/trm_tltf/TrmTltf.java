package com.github.oxaoo.trm_tltf;

import com.github.oxaoo.trm_tltf.tltf.TLTF;
import com.github.oxaoo.trm_tltf.trm.Fragment;
import com.github.oxaoo.trm_tltf.trm.TRM;
import com.github.oxaoo.util.IOFile;
import org.apache.log4j.Logger;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrmTltf {
    private final static Logger log = Logger.getLogger(TrmTltf.class.getName());

    private Map<String, Integer> wordsWeight = new HashMap<>();
    private Map<String, Integer> wordsLF = new HashMap<>();

    private final TRM trm;
    private final TLTF tltf;


    public TrmTltf() {
        trm = new TRM();
        tltf = new TLTF();
    }

    /**
     * Annotate text.
     *
     * @param filename name of text file
     */
    public void annotate(String filename) {
        String text = IOFile.read(filename);
        List<String> sentences = fragmentationBySentence(text);
        List<Fragment> fragments = fragmentation(sentences);
        trm.determinationGlobalProperties(fragments);

        calcWordsLF();
        double maxLocalWeight = tltf.determinationLocalProperties(fragments, wordsLF);

        combinatorialAssessment(fragments, maxLocalWeight);

        int idFragment = 0;
        double maxAssessment = 0;
        log.info("Info about fragments:");
        for (Fragment fragment : fragments) {
            log.info("Id " + fragment.getId() + ", assessment: " + fragment.getAssessment());
            if (fragment.getAssessment() > maxAssessment) {
                maxAssessment = fragment.getAssessment();
                idFragment = fragment.getId();
            }
        }

//        log.info("Most important fragment:\n" + fragments.get(idFragment - 1).getWords().toString());
        log.info("Most important fragment:\n" + sentences.get(idFragment - 1));
    }


    private void combinatorialAssessment(List<Fragment> fragments, double maxLocalWeight) {
        double param = 0.65;
        for (Fragment fragment : fragments) {
            double assessment = globalAssessment(param, fragment, fragments.size()) + localAssessment(param, fragment, maxLocalWeight);
            fragment.setAssessment(assessment);
        }
    }

    private double localAssessment(double param, Fragment fragment, double maxLocalWeight) {
        double normalValue = fragment.getLocalWeight() / maxLocalWeight;
        return normalValue * (1 - param);
    }

    private double globalAssessment(double param, Fragment fragment, int numFragments) {
//        int maxNumEdges = (int) (fact(numFragments) / (2 * fact(numFragments - 2)));
        int maxNumEdges = numFragments - 1;
        int normalValue = fragment.getImportance() / maxNumEdges;
        return normalValue * param;
    }

    /*private long fact(int num) {
        long fact = 1;
        for (int i = 1; i < num; i++) fact *= i;
        return fact;
    }*/


    /**
     * Creation of fragments from sentences.
     *
     * @param sentences list of sentences of text
     * @return list of fragments
     */
    private List<Fragment> fragmentation(List<String> sentences) {
        List<Fragment> fragments = new ArrayList<>();

        for (String sentence : sentences) {
            List<String> words = fragmentationByWords(sentence);
            fragments.add(new Fragment(words));
            updateWordsWeight(words);
        }

        for (Fragment fragment : fragments)
            fragment.calcWeight(wordsWeight);

        return fragments;
    }

    /**
     * The fragmentation of the text.
     *
     * @param text input text
     * @return list of sentences
     */
    private List<String> fragmentationBySentence(String text) {
        List<String> sentences = new ArrayList<>();

        log.debug("List of sentences: ");
        BreakIterator iterator = BreakIterator.getSentenceInstance();
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String sentence = text.substring(start, end).replace("\r\n", "");
            if ("null".equals(sentence)) break;
            sentences.add(sentence);
            log.debug(sentence);
        }
        return sentences;
    }

    /**
     * The fragmentation of the sentence.
     *
     * @param sentence input sentence
     * @return list of words
     */
    public static List<String> fragmentationByWords(String sentence) {
        List<String> words = new ArrayList<>();

        log.debug("List of words: ");
        BreakIterator iterator = BreakIterator.getWordInstance();
        iterator.setText(sentence);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
//            String word = sentence.substring(start, end).replaceAll("[^\\w]", ""); //only english language!
//            String word = sentence.substring(start, end).replaceAll("[^а-яА-ЯёЁa-zA-Z0-9]*", ""); //for english and russian languages.
            String word = sentence.substring(start, end).replaceAll("(?![@',&])\\p{Punct}", ""); //for multi-language.
            if (word.length() > 1) {//exclude punctuation and article.
                words.add(word);
                log.debug(word);
            }
        }
        return words;
    }


    //TODO: added search streaming words (cognates and synonyms) !!!

    /**
     * Update the weights used words
     *
     * @param words new words from the next sentence
     */
    private void updateWordsWeight(List<String> words) {
        for (String word : words) {
            String _word = word.toLowerCase();
            Integer weight = wordsWeight.get(_word);
            if (weight == null) wordsWeight.put(_word, 1);
            else wordsWeight.put(_word, ++weight);
        }
    }


    private void calcWordsLF() {
        for (String word : wordsWeight.keySet()) {
            int freq = wordsWeight.get(word);
            int lf = word.length() * freq;
            wordsLF.put(word, lf);
        }
    }
}
