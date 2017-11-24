package scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Purpose of this class is to read an SCL file and return the lexemes and tokens
 */
public class SCLScanner {

    private Scanner input;  // Scanner to read the SCL file
    private File sclProgram;

    /**
     * Creates instance of scanner.SCLScanner
     *
     * @param sclProgram - the SCL file to be read.
     * @throws FileNotFoundException if the SCL file does not exist
     */
    public SCLScanner(File sclProgram) throws FileNotFoundException {
        this.sclProgram = sclProgram;
        input = new Scanner(sclProgram);
    }

    public ArrayList<SCLSourceLine> getSourceLines() throws FileNotFoundException {
        ArrayList<SCLSourceLine> sourceLines = new ArrayList<>();

        HashMap<String, Integer> keywords;
        HashMap<String, Token> lexemes;

        boolean canModify = true; // boolean variable for whether or not identifiers can be modified

        int lineCount = 1; // Used to keep track of the number of lines scanned

        while (input.hasNext()) {
            keywords = new HashMap<>();
            lexemes = new HashMap<>();

            String line = input.nextLine();
            if (line.contains("\"")) {
                lexemes.put(line.substring(line.indexOf("\""), line.lastIndexOf("\"") + 1), Token.STRING_LITERAL);
            }
            String[] parts = line.split(" ");

            // boolean variable for whether or not a source line is going to define a variable
            boolean isDefined = false;

            // TODO: Add constants and variables to scanner.Keyword enum
            if (line.contains("constants")) {
                canModify = false;
            } else if (line.contains("variables")) {
                canModify = true;
            }

            String variable = "";       // variable to possibly be assigned
            boolean isSymbol = false;   // check to see if there is a symbol

            for (String part : parts) {

                // if it is a reserved word
                if (Keyword.getNumCode(part) > 0) {
                    keywords.put(part, Keyword.getNumCode(part));
                }

                if (Keyword.getNumCode(part) == Keyword.DEFINE.getNumCode()) {
                    isDefined = true;
                }

                // if it is not a reserved word or a token.
                if (Keyword.getNumCode(part) <= 0 && isDefined && Token.getNumCode(part) < 0 || isSymbol) {
                    variable = part;
                }

                boolean isDataType = Keyword.getNumCode(part) == Keyword.INTEGER.getNumCode() ||
                        Keyword.getNumCode(part) == Keyword.FLOAT.getNumCode() ||
                        Keyword.getNumCode(part) == Keyword.STRING.getNumCode() ||
                        Keyword.getNumCode(part) == Keyword.BOOLEAN.getNumCode();

                if (Keyword.getKeyword(part) == Keyword.SYMBOL)
                    isSymbol = true;


                /*
                if a variables is about be defined
                it is not a blank string
                it is not a key word
                 */
                if (isDefined && !variable.equals("") && isDataType) { // check for data types

                    if (canModify) {
                        switch (Keyword.getNumCode(part)) {
                            case 6001:
                                lexemes.put(variable, Token.INTEGER_IDENTIFIER);
                                break;
                            case 6002:
                                lexemes.put(variable, Token.FLOAT_IDENTIFIER);
                                break;
                            case 6003:
                                lexemes.put(variable, Token.STRING_IDENTIFIER);
                                break;
                            case 6004:
                                lexemes.put(variable, Token.BOOLEAN_IDENTIFIER);
                                break;
                        }
                    } else {
                        switch (Keyword.getNumCode(part)) {
                            case 6001:
                                lexemes.put(variable, Token.CONSTANT_INTEGER_IDENTIFIER);
                                break;
                            case 6002:
                                lexemes.put(variable, Token.CONSTANT_FLOAT_IDENTIFIER);
                                break;
                            case 6003:
                                lexemes.put(variable, Token.CONSTANT_STRING_IDENTIFIER);
                                break;
                            case 6004:
                                lexemes.put(variable, Token.CONSTANT_BOOLEAN_IDENTIFIER);
                                break;
                        }
                    }

                    isDefined = false; // set it back to false so it doesn't keep on assigning.
                } else if (isSymbol && !variable.equals("")) {
                    System.out.println("debug");
                    lexemes.put(variable, Token.SYMBOL_IDENTIFIER);
                }

                // if it is a number
                if (Token.getNumCode(part) == Token.INTEGER_LITERAL.getNumVal()) {
                    lexemes.put(part, Token.INTEGER_LITERAL);
                }
            }

            sourceLines.add(new SCLSourceLine(lineCount, keywords, lexemes, line));

            ++lineCount;

        }


        return sourceLines;
    }

}