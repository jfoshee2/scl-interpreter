package interpreter;

/**
 * Created by James on 8/30/2017.
 */
public enum Token {

    KEY_WORD("", 1),

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

    DIV_OPERATOR("/", 24);


    private String val;
    private int numVal;

    Token(String val, int numVal) {
        this.val = val;
        this.numVal = numVal;
    }

    public String getVal() {
        return val;
    }

    public int getNumVal() {
        return numVal;
    }

    public static int getNumCode(String target) {
        for (Token token : Token.values()) {
            if (target.matches(token.getVal())) {
                return token.getNumVal();
            }
        }
        return -1;
    }
}
