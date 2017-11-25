package scl.scanner;

/**
 * Created by James on 11/16/2017.
 */
public enum Token {

    NOT_DEFINED("", 1),

    INTEGER_IDENTIFIER("", 2),

    FLOAT_IDENTIFIER("", 3),

    STRING_IDENTIFIER("", 4),

    BOOLEAN_IDENTIFIER("", 5),

    /* Literals */

    INTEGER_LITERAL("\\d+", 6),

    FLOAT_LITERAL("", 7),

    STRING_LITERAL("\".*\"", 8),

    BOOLEAN_LITERAL("", 9),

    /* Constant Identifiers */

    CONSTANT_INTEGER_IDENTIFIER("", 10),

    CONSTANT_FLOAT_IDENTIFIER("", 11),

    CONSTANT_STRING_IDENTIFIER("", 12),

    CONSTANT_BOOLEAN_IDENTIFIER("", 13),

    /* Operators */

    ASSIGNMENT_OPERATOR("=", 14),

    LE_OPERATOR("<=", 15),

    LT_OPERATOR("<", 16),

    GE_OPERATOR(">=", 17),

    GT_OPERATOR(">", 18),

    EQ_OPERATOR("==", 19),

    NE_OPERATOR("~=", 20),

    ADD_OPERATOR("\\+", 21),

    SUB_OPERATOR("-", 22),

    MUL_OPERATOR("\\*", 23),

    DIV_OPERATOR("/", 24),

    /* Symbol Identifier*/
    SYMBOL_IDENTIFIER("", 25),

    FUNCTION_IDENTIFIER("", 26),

    LB("\\[", 27),

    RB("\\]", 28),

    /** Keywords **/
    SPECIFICATIONS("specifications", 5001),

    SYMBOL("symbol", 5002),

    FORWARD("forward", 5003),

    REFERENCES("references", 5004),

    FUNCTION("function", 5005),

    POINTER("pointer", 5006),

    ARRAY("array|array\\[.*?\\]", 5007),

    TYPE("type", 5008),

    STRUCT("struct", 5009),

    IMPORT("import", 5010),

    INPUT("input", 5011),

    SYMBOLS("symbols", 5012),

    /* Data types */

    INTEGER("integer", 6001),

    FLOAT("float", 6002),

    STRING("string", 6003),

    BOOLEAN("boolean", 6004),

    SHORT("short", 6005),

    MVOID("mvoid", 6006),

    ENUM("enum", 7001),

    GLOBAL("global", 7002),

    DECLARATIONS("declarations", 7003),

    IMPLEMENTATIONS("implementations", 7004),

    MAIN("main", 7005),

    PARAMETERS("parameters", 7006),

    CONSTANTS("constants", 7007),

    BEGIN("begin", 7008),

    END_FUN("endfun", 7009),

    IF("if", 7010),

    THEN("then", 7011),

    ELSE("else", 7012),

    ENDIF("endif", 7013),

    REPEAT("repeat", 7014),

    UNTIL("until", 7015),

    END_REPEAT("endrepeat", 7016),

    DISPLAY("display", 7017),

    SET("set", 7018),

    RETURN("return", 7019),

    DEFINE("define", 7020),

    OF("of", 7021),

    VARIABLES("variables", 7022),

    WHILE("while", 7023),

    DO("do", 7024),

    END_WHILE("endwhile", 7025);

    private String val;
    private int numCode;

    Token(String val, int numCode) {
        this.val = val;
        this.numCode = numCode;
    }

    public String getValue() {
        return val;
    }

    public int getNumCode() {
        return numCode;
    }

    public static int getNumCode(String target) {
        for (Token token : Token.values()) {
            if (target.matches(token.getValue())) {
                return token.getNumCode();
            }
        }
        return -1;
    }

    public static Token getToken(String target) {
        for (Token token : Token.values()) {
            if (target.matches(token.getValue())) {
                return token;
            }
        }
        return Token.NOT_DEFINED;
    }

}
