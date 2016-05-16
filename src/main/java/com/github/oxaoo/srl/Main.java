package com.github.oxaoo.srl;

import org.apache.log4j.Logger;

import java.util.List;

public class Main {
    private final static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
/*
        String text = "Understanding the basic stages of search" +
                "If we could travel back in time (let’s say to 1998), what would be the basic stages of work we would need to perform to build a search engine? " +
                "These stages are the same today as they were in 1998 but we have improved their effectiveness and computational performance. " +
                "The basic stages in conventional searching: crawling, parsing, analyzing and indexing. " +
                "Crawling refers to the process of gathering the documents on which we want to enable the search functionality. It may not be necessary if the documents exist or have been collected already. Parsing is necessary for transforming the documents (XML, HTML, Word, PDF) into a common structure that will represent the fields of indexing in a purely textual form. " +
                "An overview of crawler components " +
                "Web crawlers are used to discover, download, and store content from the Web. " +
                " Web crawler is just a part of a larger application such as a search engine. " +
                " A typical web crawler has the following components: a repository module to keep track of all URLs known to the crawler; a document download module that retrieves documents from the Web using provided set of URLs; a repository module that stores retrieved document metadata and content extracted from the raw documents during the crawling process. " +
                "Design and implementation of individual components depend on what you’re planning to crawl and the scale that the crawler is required to handle. " +
                "For intranet websites, you can get away with a fairly simple implementation as well. These nodes can even be geographically distributed to be closer to the source of data. ";
*/

        String text = "Zenit held talks with representatives of the former Chelsea coach Jose Mourinho in London.";

        Srl srl = new Srl();
        srl.annotate(text);
        List<SrlWord> srlWords = srl.getSrlWords();
        List<SrlPredicate> srlPredicates = srl.getSrlPredicates();


        log.info("Foreach words:");
        for(SrlWord word : srlWords) {
            log.info(word.toString());
        }

        log.info("Foreach predicates:");
        for(SrlPredicate predicate : srlPredicates) {
            log.info(predicate.toString());
        }
    }
}
