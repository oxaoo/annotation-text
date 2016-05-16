package com.github.oxaoo.service;

import com.github.oxaoo.srl.Srl;
import com.github.oxaoo.srl.SrlPredicate;
import com.github.oxaoo.srl.SrlWord;
import com.github.oxaoo.trm_tltf.TrmTltf;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ServiceAnnotation {
    private final static Logger log = Logger.getLogger(ServiceAnnotation.class.getName());

    public static String execute(String text, String method) {

        switch (method) {
            case "trm":
                List<String> annotationText = new TrmTltf().annotate(text);
                return makeResultTrm(annotationText);
            case "srl":
                Srl srl = new Srl();
                srl.annotate(text);
                List<SrlWord> srlWords = srl.getSrlWords();
                List<SrlPredicate> srlPredicates = srl.getSrlPredicates();
                return makeResultSrl(srlWords, srlPredicates);
            case "txl":
                return makeResultTxl(text);
            case "space":
                return makeResultSpace(text);
            default:
                log.error("Unexpected annotation method.");
                return "";
        }

//        return text.toUpperCase();
    }

    public static String makeResultTrm(List<String> strings) {
        Map<String, Object> res = new TreeMap<>();
        res.put("method", "trm");
        res.put("strings", strings);
        return new Gson().toJson(res);
    }

    public static String makeResultSrl(List<SrlWord> words, List<SrlPredicate> predicates) {
        Map<String, Object> res = new TreeMap<>();
        res.put("method", "srl");
        res.put("words", words);
        res.put("predicates", predicates);
        return new Gson().toJson(res);
    }

    public static String makeResultTxl(String string) {
        Map<String, Object> res = new TreeMap<>();
        res.put("method", "txl");
        res.put("string", string);
        return new Gson().toJson(res);
    }

    public static String makeResultSpace(String string) {
        Map<String, Object> res = new TreeMap<>();
        res.put("method", "space");
        res.put("string", string);
        return new Gson().toJson(res);
    }
}
