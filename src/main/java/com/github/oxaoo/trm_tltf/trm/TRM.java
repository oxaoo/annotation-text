package com.github.oxaoo.trm_tltf.trm;

import com.github.oxaoo.trm_tltf.Fragment;
import org.apache.log4j.Logger;

import java.util.List;

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
     * @param fragments the list of the fragments which are sentences
     */
    public void determinationGlobalProperties(List<Fragment> fragments) {
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
    }
}
