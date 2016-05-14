package com.github.oxaoo.srl;

import se.lth.cs.srl.CompletePipeline;
import se.lth.cs.srl.corpus.Predicate;
import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word;
import se.lth.cs.srl.options.CompletePipelineCMDLineOptions;
import se.lth.cs.srl.options.FullPipelineOptions;

import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipException;


public class Main {
    private static String[] pipelineOptions = new String[]{
            "eng",                                        // language
            "-lemma", "src/main/resources/models/small/lemma-small-eng.model",            // lemmatization mdoel
            "-tagger", "src/main/resources/models/small/tagger-small-eng.model",        // tagger model
            "-parser", "src/main/resources/models/small/parse-small-eng.model",        // parsing model
            "-srl", "src/main/resources/models/srl-eng.model",    // SRL model
            "-tokenize",                                // turn on word tokenization
            "-reranker"                                    // turn on reranking (part of SRL)
    };

    /*private static String[] pipelineOptions = new String[]{
            "eng",                                        // language
            "-lemma", "src/main/resources/models/lemma-small-eng.model",            // lemmatization mdoel
            "-tagger", "src/main/resources/models/tagger-small-eng.model",        // tagger model
            "-parser", "src/main/resources/models/parse-small-eng.model",        // parsing model
            "-srl", "src/main/resources/models/srl-eng.model",    // SRL model
            "-tokenize",                                // turn on word tokenization
            "-reranker"                                    // turn on reranking (part of SRL)
    };*/


    public static void main(String[] args) throws Exception {
        String text = "Zenit held talks with representatives of the former Chelsea coach Jose Mourinho in London.";
        Main demo = new Main(pipelineOptions); // replace with "args" if options from command line are to be used

        demo.parse(text);
    }

    CompletePipeline pipeline;

    public Main(String[] commandlineoptions) throws ZipException, ClassNotFoundException, IOException {
        FullPipelineOptions options = new CompletePipelineCMDLineOptions();
        options.parseCmdLineArgs(commandlineoptions); // process options
        pipeline = CompletePipeline.getCompletePipeline(options); // initialize pipeline
    }

    public void parse(String text) throws Exception {
        String[] tokens = pipeline.pp.tokenize(text); // this is how you tokenize your text
        Sentence s = pipeline.parse(Arrays.asList(tokens)); // this is how you then process the text (tokens)

        System.out.println();

        // a sentence is just a list of words
        int size = s.size();
        for (int i = 1; i < size; i++) {
            Word w = s.get(i); // skip word number 0 (ROOT token)
            // each word object contains information about a word's actual word form / lemma / POS
            System.out.println(w.getForm() + "\t " + w.getLemma() + "\t" + w.getPOS());
        }

        System.out.println();

        // some words in a sentence are recognized as predicates
        for (Predicate p : s.getPredicates()) {
            // every predicate has a sense that defines its semantic frame
            System.out.println(p.getForm() + " (" + p.getSense() + ")");
            // show arguments from the semantic frame that are instantiated in a sentence
            for (Word arg : p.getArgMap().keySet()) {
                System.out.print("\t" + p.getArgMap().get(arg) + ":");
                // "arg" is just the syntactic head word; let's iterate through all words in the argument span
                for (Word w : arg.getSpan())
                    System.out.print(" " + w.getForm());
                System.out.println();
            }

            System.out.println();

        }

    }
}

