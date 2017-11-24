import scanner.SCLScanner;
import scanner.SCLSourceLine;
import scanner.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Nathan Foshee
 * Prof Vaidya
 * CS 4308 Concepts of Programming Languages
 * 30 September 2017
 *
 * This program tests the scanner.SCLScanner class and gives a sample output.
 */

public class Main {

    public static void main(String[] args) throws FileNotFoundException {


        File file = new File("sclex1.scl");
        File outputFile = new File("output.txt");
        PrintWriter outputSample = new PrintWriter(outputFile);

        try {
            SCLScanner sclScanner = new SCLScanner(file);

            ArrayList<SCLSourceLine> sourceLines = sclScanner.getSourceLines();

            for (SCLSourceLine sourceLine : sourceLines) {
                outputSample.println("Line number: " + sourceLine.getLineNumber());
                outputSample.println("Keywords in line: ");
                HashMap<String, Integer> keywords = sourceLine.getKeywords();
                for (Map.Entry<String, Integer> e : keywords.entrySet()) {
                    outputSample.println(e.getKey() + "\t\t\t\t\t\t" + e.getValue());
                }
                outputSample.println("Lexemes in line: ");
                HashMap<String, Token> lexemes = sourceLine.getLexemes();
                for (Map.Entry<String, Token> e : lexemes.entrySet()) {
                    outputSample.println(e.getKey() + "\t\t\t\t\t\t" + e.getValue());
                }
                outputSample.println("------------------------------------");
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        outputSample.close();
    }
}
