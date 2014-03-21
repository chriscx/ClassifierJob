package com.capgemini.classifier;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws ParseException {

        Options options = new Options();

        options.addOption("-t", true, "categories");
        options.addOption("i", true, "input folder");
        options.addOption("o", true, "output folder");

        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse(options, args);
        
        // Create a new bayes classifier with string categories and string features.
        Classifier<String, String> bayes = new BayesClassifier<String, String>();

// Two examples to learn from.
        Set<String[]> list = new HashSet<String[]>();
        
        String[] cat1Text = ("Décide des programmes et des réalisations "
                + "immobilières en fonction des opportunités et des besoins "
                + "d'une clientèle potentielle. Assure le lancement et la "
                + "médiatisation du programme.{/n}Coordonne les différentes "
                + "opérations de la réalisation. Se charge ou charge ses "
                + "collaborateurs des fonctions financière, juridique, "
                + "technique et commerciale de l'opération. Peut aussi assurer"
                + " la prospection de terrains à bâtir.").split("\\s");
        
        String[] cat2Text = ("Effectue la saisie des prix à l'aide d'un clavier,"
                + " d'un lecteur optique, d'un lecteur de cartes. Encaisse les"
                + " sommes correspondant aux marchandises ou aux prestations et"
                + " services vendus. Peut aussi participer à l'accueil et à "
                + "l'information du client, voire à la vente ou à la mise en "
                + "rayon dans un petit magasin.").split("\\s");

// Learn by classifying examples.
// New categories can be added on the fly, when they are first used.
// A classification consists of a category and a list of features
// that resulted in the classification in that category.
        bayes.learn("Responsable d'opérations immobilières", Arrays.asList(cat1Text));
        bayes.learn("Caissier (hors grande distribution)", Arrays.asList(cat2Text));

// Here are two unknown sentences to classify.
        String[] unknownText1 = ("Un agent immobilier est un intermédiaire dans "
                + "les transactions portant sur des biens immobiliers : ventes "
                + "et locations. Cet agent peut être un travailleur indépendant "
                + "ou une entreprise, l'agence immobilière, employant des "
                + "négociateurs. Il est généralement en contact avec ses "
                + "confrères de la même branche, avec les mairies et communautés "
                + "urbaines, les collectivités, les institutions, les avocats "
                + "et les tribunaux. En soi, il n'y a aucune obligation de "
                + "passer par un agent immobilier pour réaliser une vente "
                + "immobilière. Il en va autrement du banquier ou du notaire "
                + "qui eux ont une compétence et des connaissances "
                + "incontournables.").split("\\s");
        
        String[] unknownText2 = ("La caissière enregistre les produits grâce à "
                + "un lecteur (fixe) de codes-barres ou à une « scannette » "
                + "(mobile) qu'elle positionne directement sur le produit. "
                + "Le rayon lumineux émis par ces machines lit le prix et la "
                + "nature du produit (fromage, lessive...). Dans certains "
                + "magasins (de tissus, par exemple), la caissière saisit le "
                + "prix à partir des étiquettes ou de la fiche donnée par le "
                + "vendeur.").split("\\s");

        System.out.println( // will output "positive"
                bayes.classify(Arrays.asList(unknownText1)).getCategory());
        System.out.println( // will output "negative"
                bayes.classify(Arrays.asList(unknownText2)).getCategory());

// Get more detailed classification result.
        ((BayesClassifier<String, String>) bayes).classifyDetailed(
                Arrays.asList(unknownText1));
        

// Change the memory capacity. New learned classifications (using
// learn method are stored in a queue with the size given here and
// used to classify unknown sentences.
        bayes.setMemoryCapacity(500);
    }
}
