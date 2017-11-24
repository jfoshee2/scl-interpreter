package parser;

import scanner.Keyword;
import scanner.SCLScanner;
import scanner.SCLSourceLine;
import scanner.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.Key;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by James on 9/3/2017.
 */
public class SCLParser extends SCLScanner {

    private List<SCLSourceLine> sclSourceLines;

    private SCLSourceLine prev;
    private SCLSourceLine current;
    private SCLSourceLine next;

    /**
     * Creates instance of scanner.SCLScanner
     *
     * @param sclProgram - the SCL file to be read.
     * @throws FileNotFoundException if the SCL file does not exist
     */
    public SCLParser(File sclProgram) throws FileNotFoundException {
        super(sclProgram);
    }

    public void startParsing() throws FileNotFoundException {
        sclSourceLines = super.getSourceLines();

        for (SCLSourceLine line : sclSourceLines) {
            HashMap<String, Integer> keywords = line.getKeywords();

            if (keywords.get("symbol") == Keyword.SYMBOL.getNumCode()) {
                // symbols()
                symbols(line);
            } else if (keywords.get("forward") == Keyword.FORWARD.getNumCode()) {
                // forwardRefs()
                forwardRefs(line);
            } else if (keywords.get("specifications") == Keyword.SPECIFICATIONS.getNumCode()) {
                // specifications()
            } else if (keywords.get("globals") == Keyword.GLOBAL.getNumCode()) {
                // globals()

            } else if (keywords.get("implementations") == Keyword.IMPLEMENTATIONS.getNumCode()) {
                // implement()
            } else {
                System.out.println("ERROR NOTHING TO PARSE");
            }
        }

        // specifications()

        // globals()

        // implement()

    }

    private void symbols(SCLSourceLine line) {
        HashMap<String, Token> lexemes = line.getLexemes();

        Object[] value = lexemes.values().toArray();

        if (!value[0].equals(Token.SYMBOL_IDENTIFIER)) {
            System.out.println("Symbol identifier not found");
        }

        if (!value[1].equals(Token.INTEGER_LITERAL)) {
            System.out.println("Symbol identifier not initialized");
        }
    }

    private void forwardRefs(SCLSourceLine line) {
        int lineNumber = sclSourceLines.indexOf(line);


    }

    private void parseFunc(SCLSourceLine line) {

        int lineNumber = sclSourceLines.indexOf(line);

        HashMap<String, Integer> keywords = line.getKeywords();

        Object[] words = keywords.keySet().toArray();

        // Parse function keyword
        if (!words[0].equals(Keyword.FUNCTION.getValue())) {
            System.out.println("Error at line: " + sclSourceLines.indexOf(line));
        }

        // Parse return type
        if (!words[1].equals(Keyword.INTEGER.getValue()) || !words[1].equals(Keyword.FLOAT.getValue())
                || !words[1].equals(Keyword.STRING.getValue()) || !words[1].equals(Keyword.BOOLEAN.getValue())) {
            System.out.println("Error at line: " + sclSourceLines.indexOf(line));
        }

        // Parse type keyword
        if (!words[2].equals(Keyword.TYPE.getValue())) {
            System.out.println("Error at line: " + sclSourceLines.indexOf(line));
        }

        // Parse return keyword
        if (!words[3].equals(Keyword.RETURN.getValue())) {
            System.out.println("Error at line: " + sclSourceLines.indexOf(line));
        }

        // Parse through next line for parameters
        SCLSourceLine nextLine = sclSourceLines.get(lineNumber + 1);
        if (!nextLine.getKeywords().containsKey(Keyword.PARAMETERS.getValue()) ||
                nextLine.getKeywords().size() != 1) {
            System.out.println("Error at line: " + sclSourceLines.indexOf(line));
        }



    }

    private void parseParameters(SCLSourceLine sclSourceLine, int lineNumber) {
        String rawString = sclSourceLine.getRawString().trim();
        String[] parts = rawString.split(" ");

        if (!parts[0].matches("[a-zA-Z0-9]+")) {
            System.out.println("Error at line + " + lineNumber);
            return;
        }

        if (!parts[1].equals(Keyword.CONSTANTS.getValue()) ||
                !parts[1].equals(Keyword.POINTER.getValue()) ||
                !parts[1].equals(Keyword.ARRAY.getValue()) && !parts[1].equals(Keyword.TYPE.getValue())) {
            System.out.println("Error at line " + lineNumber);
            return;
        }

        if (!parts[2].equals(Keyword.TYPE.getValue())) {
            System.out.println("Finish this if statement");
        }

    }

    private static void expr(SCLSourceLine sclSourceLine) {

        // term()

        /* As long as the next token is + or -, get   the next token and parse the next term */
//        while (nextToken == ADD_OP || nextToken == SUB_OP) {
//            lex();
//            term();
//        }

    }



    private static void term(String term) {

        // factor()

        /* As long as the next token is * or /, get the   next token and parse the next factor */
//        while (nextToken == MULT_OP || nextToken == DIV_OP) {
//            lex();
//            factor();
//        }

    }

    private static void factor() {
//        /* Determine which RHS */
//        if (nextToken == IDENT || nextToken == INT_LIT)
//            /* Get the next token */
//            lex();
//        /* If the RHS is ( <expr>), call lex to pass over the
//           left parenthesis, call expr, and check for the right
//           parenthesis */
//        else {
//            if (nextToken == LEFT_PAREN) {
//                lex();
//                expr();
//                if (nextToken == RIGHT_PAREN)
//                    lex();
//                else
//                    error();
//            }  /* End of if (nextToken == ... */
//            /* It was not an id, an integer literal, or a left   parenthesis */
//            else
//                error();
//        }  /* End of else */
    }
}
