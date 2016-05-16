package com.github.oxaoo.srl;

import com.github.oxaoo.util.IOFile;
import org.apache.log4j.Logger;
import se.lth.cs.srl.CompletePipeline;
import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word;
import se.lth.cs.srl.options.CompletePipelineCMDLineOptions;
import se.lth.cs.srl.options.FullPipelineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Srl {
    private final static Logger log = Logger.getLogger(Srl.class.getName());

    private static CompletePipeline pipeline;

    static {
        String[] pipelineOptions = new String[]{
                "eng",                                        // language
                "-lemma", "src/main/resources/models/small/lemma-small-eng.model",            // lemmatization mdoel
                "-tagger", "src/main/resources/models/small/tagger-small-eng.model",        // tagger model
                "-parser", "src/main/resources/models/small/parse-small-eng.model",        // parsing model
                "-srl", "src/main/resources/models/srl-eng.model",    // SRL model
                "-tokenize",
                "-reranker"
        };

        FullPipelineOptions options = new CompletePipelineCMDLineOptions();
        options.parseCmdLineArgs(pipelineOptions); // process options
        try {
            pipeline = CompletePipeline.getCompletePipeline(options); // initialize pipeline
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            log.error("Exception while get pipeline: " + e);
        }
    }

    private List<SrlPredicate> srlPredicates = new ArrayList<>();
    private List<SrlWord> srlWords = new ArrayList<>();

    /**
     * Annotate text.
     *
     * @param filename name of text file
     */
    public void annotateFile(String filename) {
        String text = IOFile.read(filename);
        annotate(text);
    }

    public void annotate(String text) {
        Sentence s = null;
        String[] tokens = pipeline.pp.tokenize(text); // this is how you tokenize your text
        try {
            s = pipeline.parse(Arrays.asList(tokens));
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("Exception while parse text's tokens");
        }

        assert s != null;

        srlPredicates = SrlPredicate.makeSrlPredicates(s.getPredicates());
        srlWords = new ArrayList<>();
        for (int i = 1; i < s.size(); i++) {
            srlWords.add(new SrlWord(s.get(i)));
        }
    }

    public List<SrlPredicate> getSrlPredicates() {
        return srlPredicates;
    }

    public void setSrlPredicates(List<SrlPredicate> srlPredicates) {
        this.srlPredicates = srlPredicates;
    }

    public List<SrlWord> getSrlWords() {
        return srlWords;
    }

    public void setSrlWords(List<SrlWord> srlWords) {
        this.srlWords = srlWords;
    }
}
