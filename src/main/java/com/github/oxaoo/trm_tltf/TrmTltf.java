package com.github.oxaoo.trm_tltf;

import com.github.oxaoo.trm_tltf.trm.TRM;
import com.github.oxaoo.util.IOFile;
import org.apache.log4j.Logger;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class TrmTltf {
    private final static Logger log = Logger.getLogger(TrmTltf.class.getName());

    private final TRM trm;
//    private TLTF tltf;


    public TrmTltf() {
        trm = new TRM();
    }

    /**
     * Annotate text.
     *
     * @param filename name of text file
     */
    public void annotate(String filename) {
        String text = IOFile.read(filename);
        List<String> fragments = fragmentationBySentence(text);
        trm.determinationGlobalProperties(fragments);
    }

    /**
     * The fragmentation of the text.
     *
     * @param text input text
     * @return list of sentences
     */
    private static List<String> fragmentationBySentence(String text) {
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
            String word = sentence.substring(start, end).replaceAll("[^\\w]", "");
            log.debug(word);
            if (word.length() > 1) //exclude punctuation and article.
                words.add(word);
        }
        return words;
    }
}
