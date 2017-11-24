package scl.parser;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by James on 11/21/2017.
 */
public class TestParser {
    public static void main(String[] args) {
        File file = new File("sclex1.scl");

        try {
            SCLParser sclParser = new SCLParser(file);

            sclParser.parse();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
