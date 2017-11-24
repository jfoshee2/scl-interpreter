package scl.scanner;

import java.util.List;

/**
 * Created by James on 11/16/2017.
 */
public class SCLSourceLine {

    private int lineNumber;
    private List<Lexeme> lexemes;
    private String rawString;

    public SCLSourceLine(int lineNumber, List<Lexeme> lexemes, String rawString) {
        this.lineNumber = lineNumber;
        this.lexemes = lexemes;
        this.rawString = rawString;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setLexemes(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public void setRawString(String rawString) {
        this.rawString = rawString;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public List<Lexeme> getLexemes() {
        return lexemes;
    }

    public String getRawString() {
        return rawString;
    }
}
