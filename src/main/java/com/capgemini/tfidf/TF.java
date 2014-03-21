/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.capgemini.tfidf;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ccaillea
 */
public class TF {
    
    private String doc;
    private HashMap<String, Double> tf;
    
    public TF(String doc) {
        this.doc    = doc;
        this.tf    = new HashMap<String, Double>();
    }
    
    /**
     * 
     */
    public void process() {
        String[] terms = doc.split("\\s");
        
        for (int i = 0; i < terms.length; i++) {
            if(tf.containsKey(terms[i])) {
                
                double val = tf.get(terms[i]);
                tf.put(terms[i], val + 1.0);
            }
            else {
                tf.put(terms[i], 1.0);
            }
        }
        
        for (Map.Entry<String, Double> entry : tf.entrySet()) {
            double val = entry.getValue();
            entry.setValue(val / terms.length);
        }
    }
    
    /**
     * 
     * @return 
     */
    public HashMap<String, Double> getTF() {
        return tf;
    }
}
