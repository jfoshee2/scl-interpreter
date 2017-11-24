package scanner;

import java.util.HashMap;

/**
 * Created by James on 9/12/2017.
 */
public class SCLSourceLine {
    private int lineNumber;
    private HashMap<String, Integer> keywords;
    private HashMap<String, Token> lexemes;
    private String rawString;

    public SCLSourceLine(
            int lineNumber,
            HashMap<String, Integer> keywords,
            HashMap<String, Token> lexemes,
            String rawString
    ) {
        this.lineNumber = lineNumber;
        this.keywords = keywords;
        this.lexemes = lexemes;
        this.rawString = rawString;
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

    public String getRawString() {
        return rawString;
    }
}
