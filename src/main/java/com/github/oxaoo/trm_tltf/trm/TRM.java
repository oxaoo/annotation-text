package com.github.oxaoo.trm_tltf.trm;

import com.github.oxaoo.trm_tltf.TrmTltf;
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

    private List<Fragment> fragments = new ArrayList<>();
    private Map<String, Integer> wordsWeight = new HashMap<>();


    /**
     * Determination the global properties of the text
     *
     * @param sentences list of sentences of the text
     */
    public void determinationGlobalProperties(List<String> sentences) {
        for (String sentence : sentences) {
            List<String> words = TrmTltf.fragmentationByWords(sentence);
            fragments.add(new Fragment(words));
            updateWordsWeight(words);
        }

        for (Fragment fragment : fragments)
            fragment.calcWeight(wordsWeight);

        for (int i = 0; i < fragments.size(); i++) {
            for (int j = i + 1; j < fragments.size(); j++) {
                double similarity = Fragment.scalarProduct(fragments.get(i), fragments.get(j));
                log.debug("Similarity of [" + i + ":" + j + "]: " + similarity);
                if (similarity > 0.95) {
                    fragments.get(i).incImportance();
                    fragments.get(j).incImportance();
                }
            }
        }
        log.debug("finish");
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
}
