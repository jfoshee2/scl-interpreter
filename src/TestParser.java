import parser.SCLParser;
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
 * Created by James on 11/11/2017.
 */
public class TestParser {
    public static void main(String[] args) {

        File file = new File("sclex1.scl");
        File outputFile = new File("output.txt");

        try {
            SCLParser sclParser = new SCLParser(file);

            sclParser.startParsing();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
