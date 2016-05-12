package com.github.oxaoo.trm_tltf;

import org.apache.log4j.Logger;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Text Relationship Map.
 *
 * @author Alexander Kuleshov
 */
public class TRM {
    private final static Logger log = Logger.getLogger(TRM.class.getName());


    /**
     * Determination the global properties of the text
     *
     * @param fragments list of sentences of the text
     */
    public void determinationGlobalProperties(List<String> fragments) {
//        Map<String, Integer> weightWords = getWeightWords
        for (String fragment : fragments) {
            List<String> words = fragmentationByWords(fragment);
            for (String word : words) {
                //TODO: calculate the global weight of fragment (#28)...
            }
        }
    }

    /**
     * The fragmentation of the sentence.
     *
     * @param sentence input sentence
     * @return list of words
     */
    private List<String> fragmentationByWords(String sentence) {
        List<String> words = new ArrayList<>();

        log.debug("List of words: ");
        BreakIterator iterator = BreakIterator.getWordInstance();
        iterator.setText(sentence);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String word = sentence.substring(start, end);
            words.add(word);
            log.debug(word);
        }
        return words;
    }
}
