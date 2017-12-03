import scl.scanner.Lexeme;
import scl.scanner.SCLScanner;
import scl.scanner.SCLSourceLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Nathan Foshee
 * Prof Vaidya
 * CS 4308 Concepts of Programming Languages
 * 30 September 2017
 *
 * This program tests the scl.scanner.SCLScanner class and gives a sample output.
 */
public class TestScanner {
    public static void main(String[] args) {

        SCLScanner sclScanner = null;
        try {
            sclScanner = new SCLScanner(new File("sclex2.scl"));
            List<SCLSourceLine> sclSourceLines = sclScanner.getSourceLines();

            System.out.println("Lexemes found");
            System.out.println("----------------------------------------");

            for (SCLSourceLine sclSourceLine : sclSourceLines) {
                List<Lexeme> lexemes = sclSourceLine.getLexemes();

                System.out.println(sclSourceLine.getLineNumber());
                for (Lexeme lexeme : lexemes) {
                    System.out.println(lexeme.getToken() + "\t\t\t\t\t\t" + lexeme.getLexeme());
                }
                System.out.println("--------------------------------");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }
}
