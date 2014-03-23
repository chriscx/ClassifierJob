/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chriscx.tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ccaillea
 */
public class IDF {

    private ArrayList<String> docs;
    private Set<String> terms = new HashSet<String>();
    private HashMap<String, Double> idf;

    /**
     * 
     * @param docs 
     */
    public IDF(ArrayList<String> docs) {
        this.docs = docs;
    }

    /**
     * 
     */
    private void prepare() {
        for (String doc : docs) {
            String[] docTerms = doc.split("\\s");
            for (String docTerm : docTerms) {
                if (!terms.contains(docTerm)) {
                    terms.add(docTerm);
                }
            }
        }
    }

    /**
     * 
     */
    public void process() {
        
        this.prepare();
        
        for (String term : terms) {
            int count = 0;
            for (String doc : docs) {
                count++;
            }
            
            idf.put(term, Math.log(docs.size() / count));
        }
    }

    /**
     * 
     * @return 
     */
    public HashMap<String, Double> getIdf() {
        return idf;
    }

}
