package scl.scanner;

import com.sun.istack.internal.NotNull;

import java.util.Optional;

/**
 * Used to contain actual values of tokens for lexemes such as literals or identifiers.
 */
public class Lexeme {

    private Token token;
    private String lexeme;

    public Lexeme(Token token, String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
    }

    public Token getToken() {
        return token;
    }

    public String getLexeme() {
        return lexeme;
    }
}
