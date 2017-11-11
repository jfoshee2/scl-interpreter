package interpreter;

import java.util.HashMap;

/**
 * Created by James on 9/12/2017.
 */
public class SCLSourceLine {
    private int lineNumber;
    private HashMap<String, Integer> keywords;
    private HashMap<String, Token> lexemes;

    public SCLSourceLine(int lineNumber, HashMap<String, Integer> keywords, HashMap<String, Token> lexemes) {
        this.lineNumber = lineNumber;
        this.keywords = keywords;
        this.lexemes = lexemes;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public HashMap<String, Integer> getKeywords() {
        return keywords;
    }

    public HashMap<String, Token> getLexemes() {
        return lexemes;
    }
}
