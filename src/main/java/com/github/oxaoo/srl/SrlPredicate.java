package com.github.oxaoo.srl;

import se.lth.cs.srl.corpus.Predicate;
import se.lth.cs.srl.corpus.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SrlPredicate {
    String form;
    String sense;
    Map<String, List<String>> argument;


    public SrlPredicate(Predicate predicate) {
        form = predicate.getForm();
        sense = predicate.getSense();
        argument = new TreeMap<>();
        for (Word w : predicate.getArgMap().keySet()) {
            String arg = predicate.getArgMap().get(w);
            List<String> words = new ArrayList<>();
            for (Word word : w.getSpan()) words.add(word.getForm());
            argument.put(arg, words);
        }
    }

    public static List<SrlPredicate> makeSrlPredicates(List<Predicate> predicates) {
        List<SrlPredicate> srlPredicates = new ArrayList<>(predicates.size());

        for (Predicate predicate : predicates)
            srlPredicates.add(new SrlPredicate(predicate));

        return srlPredicates;
    }

    @Override
    public String toString() {
        return "SrlPredicate{" +
                "form='" + form + '\'' +
                ", sense='" + sense + '\'' +
                ", argument=" + argument +
                '}';
    }
}
