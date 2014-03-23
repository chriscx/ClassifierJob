/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.chriscx.similarity;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 *
 * @author Chris
 */
public class SimilarityApp {
    
    public static void main(String[] args) throws ParseException {
        
        Options options = new Options();
        
        options.addOption("i", true, "input folder");
        options.addOption("o", true, "output folder");

        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse(options, args);
        
        if(args.length > 1) {
            
            
        }
    }
}
