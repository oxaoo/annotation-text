package com.github.oxaoo.srl;

import se.lth.cs.srl.corpus.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SrlWord {
    private String form;
    private String lemma;
    private String role;
//    private String description;

/*    private static Map<String, String> descMap = new HashMap<String, String>() {{
       put("A0", "Агент");
       put("A1", "Тема");
       put("A2", "Инструмент");
       put("AM-TMP", "Время");
       put("AM-LOC", "Место");
       put("AM-DIR", "Направление");
       put("AM-MNR", "Манера");
       put("AM-PNC", "Цель");
    }};*/

    public SrlWord(Word word) {
        form = word.getForm();
        lemma = word.getLemma();
        role = word.getPOS();
//        if (!descMap.containsKey(role)) description = "-";
//        else description = descMap.get(role);
    }

    public static List<SrlWord> makeWords(List<Word> words) {
        List<SrlWord> srlWords = new ArrayList<>();
        for (Word w: words) srlWords.add(new SrlWord(w));
        return srlWords;
    }

    @Override
    public String toString() {
        return "SrlWord{" +
                "form='" + form + '\'' +
                ", lemma='" + lemma + '\'' +
                ", role='" + role + '\'' +
//                ", description='" + description + '\'' +
                '}';
    }
}
