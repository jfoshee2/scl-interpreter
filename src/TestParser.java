import scl.parser.SCLParser;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Nathan Foshee
 * Prof Vaidya
 * CS 4308 Concepts of Programming Languages
 * 26 November 2017
 *
 * This program tests the scl.parser.SCLParser class and gives a sample output.
 */
public class TestParser {
    public static void main(String[] args) {
        File file = new File("sclex2.scl");

        try {
            SCLParser sclParser = new SCLParser(file);

            sclParser.parse();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
