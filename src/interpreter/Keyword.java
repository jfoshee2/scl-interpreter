package interpreter;

/**
 * Created by James on 9/1/2017.
 */
public enum Keyword {

    SPECIFICATIONS("specifications", 5001),

    SYMBOL("symbol", 5002),

    FORWARD("forward", 5003),

    REFERENCES("references", 5004),

    FUNCTION("function", 5005),

    POINTER("pointer", 5006),

    ARRAY("array|array\\[.*?\\]", 5007),

    TYPE("type", 5008),

    STRUCT("struct", 5009),

    /* Data types */

    INTEGER("integer", 6001),

    FLOAT("float", 6002),

    STRING("string", 6003),

    BOOLEAN("boolean", 6004),

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

    OF("of", 7021);

    private final String value;
    private final int numCode;

    Keyword(String value, int numCode) {
        this.value = value;
        this.numCode = numCode;
    }

    public String getValue() {
        return value;
    }

    public int getNumCode() {
        return numCode;
    }

    public static int getNumCode(String target) {
        for (Keyword word : Keyword.values()) {
            if (target.matches(word.getValue())) {
                return word.getNumCode();
            }
        }
        return -1;
    }

}

