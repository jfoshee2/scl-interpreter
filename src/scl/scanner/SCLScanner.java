package scl.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by James on 11/16/2017.
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

    /**
     * Used to get {@link List} of {@link SCLSourceLine}
     * <p>
     * Iterates through every line of scl file.
     *
     * @return
     */
    public List<SCLSourceLine> getSourceLines() { // TODO: take some code out into private inner methods
        List<SCLSourceLine> sclSourceLines = new ArrayList<>();

        int lineNumber = 1;
        boolean canModify = true;   // switch for whether or not variables can be modified
        boolean ignore = false;     // switch for whether a line can be ignored, used for block comments and description
        boolean parameters = false; // switch for whether there are parameters.

        while (input.hasNext()) {

            boolean addedParameters = false;

            List<Lexeme> lexemes = new ArrayList<>(); // List of tokens found in each line

            String line = input.nextLine();

            String rawLine = line;

            if (line.contains("description")) {
                ignore = true;
            } else if (line.contains("*/")) {
                ignore = false;
            }

            // if it's a comment ignore it.
            if (line.contains("//")) {
                line = line.substring(0, line.indexOf("//"));
            }

            if (line.contains("\"")) {
                lexemes.add(new Lexeme(
                                Token.STRING_LITERAL,
                                line.substring(line.indexOf("\""), line.lastIndexOf("\"") + 1)
                        )
                );
                line = line.substring(0, line.indexOf("\"")) + line.substring(line.lastIndexOf("\"") + 1);
            }

            String trimmedLine = line.trim();


            String[] parts = trimmedLine.split("\\s");


            // Read parameters
            if (parameters) {
                // TODO: remove magic numbers
                if (parts.length == 5) { // this will be an array parameter
                    lexemes.add(new Lexeme(Token.ARRAY_INTEGER_IDENTIFIER, parts[0]));
                    lexemes.add(new Lexeme(Token.getToken(parts[1]), parts[1]));
                    lexemes.add(new Lexeme(Token.getToken(String.valueOf(parts[2].charAt(0))), String.valueOf(parts[2].charAt(0))));
                    lexemes.add(new Lexeme(Token.getToken(String.valueOf(parts[2].charAt(1))), String.valueOf(parts[2].charAt(1))));
                    lexemes.add(new Lexeme(Token.getToken(parts[3]), parts[3]));
                    if (parts[parts.length - 1].charAt(parts[parts.length - 1].length() - 1) == ',') {
                        lexemes.add(new Lexeme(Token.getToken(parts[4].substring(0, parts[4].length() - 1)), parts[4]));
                    } else {
                        lexemes.add(new Lexeme(Token.getToken(parts[4]), parts[4]));
                    }

                } else if (parts.length == 3) { // this will be a regular parameter
                    lexemes.add(new Lexeme(Token.INTEGER_IDENTIFIER, parts[0]));
                    lexemes.add(new Lexeme(Token.getToken(parts[1]), parts[1]));
                    if (parts[parts.length - 1].charAt(parts[parts.length - 1].length() - 1) == ',') {
                        lexemes.add(new Lexeme(Token.getToken(parts[2].substring(0, parts[2].length() - 2)), parts[2]));
                    } else {
                        lexemes.add(new Lexeme(Token.getToken(parts[2]), parts[2]));
                    }
                }

                if (parts[parts.length - 1].charAt(parts[parts.length - 1].length() - 1) != ',') {
                    parameters = false;
                    addedParameters = true;
                }
            }




            String variable = "";       // variable that will possibly be assigned
            boolean define = false;     // tells if a variable is about to be defined
            boolean symbol = false;     // tells whether there is a symbol on the current line
            boolean array = false;      // tells if a variable is an array

            if (!ignore && !parameters && !addedParameters) { // TODO: this may have to be rewritten
                // Add tokens to tokens list
                for (String part : parts) {

                    boolean addedToLexemes = false; // flag for if part was added the list of lexemes.

                    Token token = Token.getToken(part);

                    // check to see if it is a function
                    if (token == Token.FUNCTION) {
                        lexemes.add(new Lexeme(Token.FUNCTION_IDENTIFIER,
                                parts[Arrays.asList(parts).indexOf(part) + 1]));
                        addedToLexemes = true;
                    }

                    if (token == Token.PARAMETERS) {
                        parameters = true;
                    }

                /*
                if it is a literal
                 */
                    if (token == Token.INTEGER_LITERAL || token == Token.FLOAT_LITERAL || token == Token.BOOLEAN_LITERAL) {
                        lexemes.add(new Lexeme(token, part));
                        addedToLexemes = true;
                    }

                /*
                if the token has a number code greater than 5000
                all key words have number code greater than 5000
                 */
                    if (token != null && token.getNumCode() > 5000) {
                        lexemes.add(new Lexeme(token, part));
                        addedToLexemes = true;
                    }

                    if (token == Token.CONSTANTS) {
                        canModify = false;
                    }

                    if (token == Token.VARIABLES) {
                        canModify = true;
                    }

                    if (token == Token.SYMBOL) {
                        symbol = true;
                    }

                    if (token == Token.DEFINE) {
                        define = true;
                    }

                    if (token == Token.ARRAY) {
                        array = true;
                    }

                    // if it is not a token
                    if (token == Token.NOT_DEFINED && (define || symbol)) {
                        variable = part; // assign part to variable because it is probably an identifier
                    }

                    boolean dataType = token == Token.INTEGER || token == Token.FLOAT ||
                            token == Token.STRING || token == Token.BOOLEAN;

                /*
                if a variables is about be defined
                and it is not a blank string
                and it is a data type
                 */
                    if (!variable.equals("") && define && dataType) {
                        if (array) {
                            switch (token) {
                                case INTEGER:
                                    lexemes.add(new Lexeme(Token.ARRAY_INTEGER_IDENTIFIER, variable));
                                    addedToLexemes = true;
                                    break;
                                case FLOAT:
                                    lexemes.add(new Lexeme(Token.ARRAY_FLOAT_IDENTIFIER, variable));
                                    addedToLexemes = true;
                                    break;
                                case STRING:
                                    lexemes.add(new Lexeme(Token.ARRAY_STRING_IDENTIFIER, variable));
                                    addedToLexemes = true;
                                    break;
                                case BOOLEAN:
                                    lexemes.add(new Lexeme(Token.ARRAY_BOOLEAN_IDENTIFIER, variable));
                                    addedToLexemes = true;
                                    break;
                            }
                        } else if (canModify) {
                            switch (token) {
                                case INTEGER:
                                    lexemes.add(new Lexeme(Token.INTEGER_IDENTIFIER, variable));
                                    addedToLexemes = true;
                                    break;
                                case FLOAT:
                                    lexemes.add(new Lexeme(Token.FLOAT_IDENTIFIER, variable));
                                    addedToLexemes = true;
                                    break;
                                case STRING:
                                    lexemes.add(new Lexeme(Token.STRING_IDENTIFIER, variable));
                                    addedToLexemes = true;
                                    break;
                                case BOOLEAN:
                                    lexemes.add(new Lexeme(Token.BOOLEAN_IDENTIFIER, variable));
                                    addedToLexemes = true;
                                    break;
                            }
                        } else {
                            switch (token) {
                                case INTEGER:
                                    lexemes.add(new Lexeme(Token.CONSTANT_INTEGER_IDENTIFIER, variable));
                                    addedToLexemes = true;
                                    break;
                                case FLOAT:
                                    lexemes.add(new Lexeme(Token.CONSTANT_FLOAT_IDENTIFIER, variable));
                                    addedToLexemes = true;
                                    break;
                                case STRING:
                                    lexemes.add(new Lexeme(Token.CONSTANT_STRING_IDENTIFIER, variable));
                                    addedToLexemes = true;
                                    break;
                                case BOOLEAN:
                                    lexemes.add(new Lexeme(Token.CONSTANT_BOOLEAN_IDENTIFIER, variable));
                                    addedToLexemes = true;
                                    break;
                            }
                        }
                    } else if (!variable.equals("") && symbol) {
                        lexemes.add(new Lexeme(Token.SYMBOL_IDENTIFIER, variable));
                        addedToLexemes = true;
                        symbol = false; // hack way of doing this but this makes sure that the variable is not added twice
                    }

                    if (!addedToLexemes && token != Token.NOT_DEFINED) {
                        lexemes.add(new Lexeme(token, part));
                    }
                }

            }

            SCLSourceLine sclSourceLine = new SCLSourceLine(lineNumber++, lexemes, rawLine);
            adjustSCLSourceLine(sclSourceLine);
            sclSourceLines.add(sclSourceLine);
        }

        return sclSourceLines;
    }

    private static void adjustSCLSourceLine(SCLSourceLine sclSourceLine) {
        String trimmedLine = sclSourceLine.getRawString().trim();
        String[] parts = trimmedLine.split("\\s");

        if (sclSourceLine.getLineNumber() == 39) {
            System.out.println("debug");
        }

        List<Lexeme> sortedLexemes = new ArrayList<>();

        // TODO: optimize, this should not be O(n^2)
        for (String part : parts) {
            for (Lexeme lexeme : sclSourceLine.getLexemes()) {
                if (part.equals(lexeme.getLexeme())) {
                    sortedLexemes.add(lexeme);
                }
            }
        }

        if (Token.getToken(parts[0]) == Token.INPUT || Token.getToken(parts[0]) == Token.DISPLAY) {
            List<Lexeme> lexemes = new ArrayList<>();
            lexemes.add(new Lexeme(Token.getToken(parts[0]), parts[0]));
            lexemes.add(new Lexeme(Token.STRING_LITERAL, trimmedLine.substring(trimmedLine.indexOf("\""), trimmedLine.lastIndexOf("\"") + 1)));
            lexemes.add(new Lexeme(Token.IDENTIFIER, parts[parts.length - 1]));
            sclSourceLine.setLexemes(lexemes);
        } else if (Token.getToken(parts[0]) == Token.SET) {
            List<Lexeme> lexemes = new ArrayList<>();
            lexemes.add(new Lexeme(Token.getToken(parts[0]), parts[0]));
            lexemes.add(new Lexeme(Token.IDENTIFIER, parts[1]));
            lexemes.add(new Lexeme(Token.getToken(parts[2]), parts[2])); // Add assignment operator
            for (int i = 3; i < parts.length; i++) {
                Token token = Token.getToken(parts[i]);

                if (token == Token.NOT_DEFINED) {
                    lexemes.add(new Lexeme(Token.IDENTIFIER, parts[i]));
                } else {
                    lexemes.add(new Lexeme(Token.getToken(parts[i]), parts[i]));
                }
            }
            sclSourceLine.setLexemes(lexemes);
        } else {
            sclSourceLine.setLexemes(sortedLexemes);
        }

    }

}
