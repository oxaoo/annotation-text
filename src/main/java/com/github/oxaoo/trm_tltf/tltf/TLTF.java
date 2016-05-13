package com.github.oxaoo.trm_tltf.tltf;

import com.github.oxaoo.trm_tltf.TrmTltf;
import org.apache.log4j.Logger;

import java.util.List;

public class TLTF {
    private final static Logger log = Logger.getLogger(TLTF.class.getName());

    public void determinationLocalProperties(List<String> sentences) {
        for (String sentence : sentences) {
            //...
        }
    }

    public String fragmentationByClusters(String sentence) {
        List<String> words = TrmTltf.fragmentationByWords(sentence);
        for (String word : words) {
            //...
        }

        return null;
    }
}
