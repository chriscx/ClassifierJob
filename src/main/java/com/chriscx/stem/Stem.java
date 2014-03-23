/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chriscx.stem;

import java.io.BufferedReader;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

/**
 *
 * @author ccaillea
 */
public class Stem {

    private String result = new String();

    public String evaluate(BufferedReader input) {
        if (input == null) {
            return null;
        }

        CharArraySet stopWordsSet = new CharArraySet(Version.LUCENE_46, 10000, true);
        String stopWords = "a afin ai ainsi après attendu au aujourd auquel aussi "
                + "autre autres aux auxquelles auxquels avait avant avec c car ce "
                + "ceci cela celle celles celui cependant certain certaine certaines "
                + "certains ces cet cette ceux chez ci combien comme comment "
                + "concernant contre d dans de debout dedans dehors delà depuis "
                + "derrière des désormais desquelles desquels devers devra doit "
                + "donc dont du duquel durant dûs elle elles en entre environ est"
                + " et etc eu eux excepté hormis hors hélas hui il ils j je jusqu "
                + "jusque l la laquelle le lequel les lesquelles lesquels leur leurs "
                + "lorsque lui lû ma mais malgré me merci mes mien mienne miennes "
                + "miens moins mon moyennant même mêmes n ne ni non nos notre nous "
                + "néanmoins nôtre nôtres on ont ou outre où par parmi partant pas "
                + "passé pendant plein plus plusieurs pour pourquoi proche près "
                + "puisque qu quand que quel quelle quelles quels qui quoi quoique"
                + " revoici revoilà s sa sans sauf se selon seront ses si sien "
                + "sienne siennes siens sinon soi soit son sont sous suivant sur "
                + "ta te tes tien tienne tiennes tiens ton tous tout toute toutes"
                + " tu un une va vers voici voilà vos votre vous vu vôtre vôtres y "
                + "à été être î";
        String[] stopWordsTab = stopWords.split(" ");
        for (String word : stopWordsTab) {
            stopWordsSet.add(word);
        }

        Analyzer analyzer = new FrenchAnalyzer(Version.LUCENE_46, stopWordsSet);

        result = "";
        try {
            String line = input.readLine();

            line = line.replaceAll("(\\S)+@(\\S)+.(\\S)+", "");
            line = line.replaceAll("(0[0-68]([-. ]?\\d{2}){4}[-. ]?)|\\d+", "");
            line = line.replaceAll("(_|-)+", "");
            line = line.replaceAll("(\\n|\\r|\\t)+", "");
            line = line.replaceAll("(?![\\._])\\p{P}", "");
            while (line != null) {

                TokenStream stream = analyzer.tokenStream(null, line);
                stream.reset();
                while (stream.incrementToken()) {
                    String wordset = stream.getAttribute(CharTermAttribute.class).toString();
                    wordset = wordset.replaceAll("(0[0-68]([-. ]?\\d{2}){4}[-. ]?)|\\d+", "");
                    result += wordset + " ";
                }
                result += "\n";
                stream.close();
                line = input.readLine();
            }

            input.close();
            return result;
        } catch (IOException e) {
            // not thrown b/c we're using a string reader...
            throw new RuntimeException(e);
        }
    }
}
