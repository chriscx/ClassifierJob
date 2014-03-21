/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.capgemini.tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ccaillea
 */
public class TFIDF {

    private ArrayList<String> documents
            = new ArrayList<String>();
//    private HashMap<Integer, HashMap<String, Double>> termFrequencies
//            = new HashMap<Integer, HashMap<String, Double>>();
    private HashMap<String, Double> inverseDocumentFrequencies
            = new HashMap<String, Double>();
    private HashMap<String, Double> tfidf
            = new HashMap<String, Double>();

    private TF tf = null;
    private IDF idf = null;

    public TFIDF(ArrayList<String> doc) {
        this.documents = doc;
    }

    public void process() {

        idf = new IDF(documents);
        idf.process();
        inverseDocumentFrequencies = idf.getIdf();

        for (int i = 0; i < documents.size(); i++) {
            tf = new TF(documents.get(i));
            tf.process();
            
            HashMap<String, Double> termFrequencies;
            termFrequencies = tf.getTF();
            
            for (Map.Entry<String, Double> entry : termFrequencies.entrySet()) {
                
                String term = entry.getKey();
                Double tfValue = entry.getValue();
                Double idfValue = inverseDocumentFrequencies.get(term);
                Double tfidfValue = tfValue * idfValue;
                
                if(!tfidf.containsKey(term)) {
                    tfidf.put(term, tfidfValue);
                }
                else {
                    if(tfidf.get(term) < tfidfValue) {
                        tfidf.put(term, tfidfValue);
                    }
                }
            }
        }
    }
}
