/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.capgemini.similarity;

import java.util.Vector;

/**
 *
 * @author ccaillea
 */
public class CosineSimilarity {
    
    private Double sim;
    /**
     * Make sure the two vectors has the same size
     * @param a
     * @param b
     * @param size 
     */
    public static Double compute(Vector<Double> A, Vector<Double> B, int size) {
        
        Double num = 0.0;
        Double squareA = 0.0;
        Double squareB = 0.0;
        
        for (int i = 0; i < size; i++) {
            Double a = A.get(i), b = B.get(i);
            num += a * b;
            squareA += a * a;
            squareB += b * b;
        }

        // perform the nth root with n the size of vector
        return num / ((Math.log(size)/squareA) * (Math.log(size)/squareB));
    }
}
