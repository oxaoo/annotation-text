package com.github.oxaoo.trm_tltf;

import com.github.oxaoo.util.IOFile;
import org.apache.log4j.Logger;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class TrmTltf {
    private final static Logger log = Logger.getLogger(TrmTltf.class.getName());

    /**
     * Annotate text.
     *
     * @param filename name of text file
     */
    public void annotate(String filename) {
        String text = IOFile.read(filename);
        List<String> fragments = fragmentation(text);

    }

    /**
     * The fragmentation of the text.
     *
     * @param text input text
     * @return list of sentences
     */
    private List<String> fragmentation(String text) {
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
}
