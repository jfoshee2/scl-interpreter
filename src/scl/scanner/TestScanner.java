package scl.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by James on 11/16/2017.
 */
public class TestScanner {
    public static void main(String[] args) {

        SCLScanner sclScanner = null;
        try {
            sclScanner = new SCLScanner(new File("sclex1.scl"));
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
