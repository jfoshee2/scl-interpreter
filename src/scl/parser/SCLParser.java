package scl.parser;

import scl.scanner.Lexeme;
import scl.scanner.SCLScanner;
import scl.scanner.SCLSourceLine;
import scl.scanner.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 11/18/2017.
 */
public class SCLParser extends SCLScanner {

    private List<SCLSourceLine> sclSourceLines;
    private int lineIndex;      // Keeps track of which line the parser is on
    private int tokenIndex;     // Keeps track of which index of the list tokens the parser is on
    private Token nextToken;    // Keeps track of the next token to parse

    /**
     * Creates instance of scanner.SCLScanner
     *
     * @param sclProgram - the SCL file to be read.
     * @throws FileNotFoundException if the SCL file does not exist
     */
    public SCLParser(File sclProgram) throws FileNotFoundException {
        super(sclProgram);
        lineIndex = 0;
        tokenIndex = 0;
        nextToken = null;
    }

    public SCLTree parse() { // TODO: Make this return a parse tree
        sclSourceLines = super.getSourceLines();

        Lexeme nextLexeme = getNextLexeme();
        nextToken = nextLexeme.getToken();

        while (lineIndex < sclSourceLines.size()) { // while there is still stuff to parse
            switch (nextToken) {
                case IMPORT: importDef(); break;
                case SYMBOL: symbolDef(); break;
                case FORWARD: forwardRefs(); break;
                case SPECIFICATIONS: specifications(); break;
                case GLOBAL: globals(); break;
                case IMPLEMENTATIONS: implement(); break;
                default:
                    nextToken = getNextLexeme().getToken();
            }
        }


        return null;
    }

    /**
     * import: IMPORT STRING_LITERAL;
     */
    private void importDef() {
        // check to see if next token is a string literal
        nextToken = getNextLexeme().getToken();
        if (nextToken != Token.STRING_LITERAL) {
            System.out.println("Error at line number: " + lineIndex);
        }
        nextToken = getNextLexeme().getToken();
    }

    /**
     * symbols :
     | symbols symbol_def
     ;
     */
    private void symbol() {
        // TODO: function may not be needed.
    }

    /**
     * symbol_def : SYMBOL IDENTIFIER HCON
     ;
     */
    private void symbolDef() {

        nextToken = getNextLexeme().getToken();

        // Check to see if next token is a SYMBOL_IDENTIFIER
        if (nextToken != Token.SYMBOL_IDENTIFIER) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        // Check to see if next token is an INTEGER_LITERAL
        if (nextToken != Token.INTEGER_LITERAL) {
            System.out.println("Error at line: " + lineIndex);
        }

    }

    /**
     * forward_refs :
     | FORWARD frefs
     ;
     */
    private void forwardRefs() {

        nextToken = getNextLexeme().getToken();

        // Check to see if next token is
        if (nextToken != Token.DECLARATIONS) {
            System.out.println("Error at line number: " + lineIndex);
            return;
        }

        nextToken = getNextLexeme().getToken();

        forwardList();

    }

    /**
     * frefs  : REFERENCES forward_list
     | forward_list
     ;
     */
    private void frefs() {

    }

    /**
     * forward_list : forwards
     | forward_list forwards
     ;
     */
    private void forwardList() {

        nextToken = getNextLexeme().getToken();

        if (nextToken == Token.FUNCTION) {

            nextToken = getNextLexeme().getToken();

            funcMain();
            parameters();
            forwardList();
        }

    }

    /**
     * forwards :
     | func_main dec_parameters
     ;
     */
    private void forwards() {
        // TODO: function may not be needed
    }

    /**
     * func_main :
     | FUNCTION IDENTIFIER oper_type
     | MAIN {dec_main();}
     ;
     */
    private void funcMain() {
        nextToken = getNextLexeme().getToken();

        if (nextToken == Token.FUNCTION_IDENTIFIER) {
            nextToken = getNextLexeme().getToken();
            operType();
        } else if (nextToken == Token.MAIN) {
            nextToken = getNextLexeme().getToken();
        } else {
            System.out.println("Error at line: " + lineIndex);
        }

    }

    /**
     * oper_type : RETURN TYPE chk_ptr chk_array ret_type
     ;
     */
    private void operType() {

        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.RETURN) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.TYPE) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        // if the next token is not a return (data) type
        if (nextToken != Token.INTEGER && nextToken != Token.FLOAT &&
                nextToken != Token.STRING &&  nextToken != Token.BOOLEAN) {
            System.out.println("Error at line: " + lineIndex);
        }



    }

    /**
     * chk_ptr :
     | POINTER {pointer_flag = true;}
     ;
     */
    private void chkPtr() {

        // TODO: Implement

    }

    /**
     * chk_array :
     | ARRAY array_dim_list
     ;
     */
    private void chkArray() {

        // TODO: Implement

    }

    /**
     * array_dim_list : LB array_index RB
     | array_dim_list LB array_index RB
     ;
     */
    private void arrayDimList() {

        // TODO: Implement

    }

    /**
     * array_index : IDENTIFIER
     | ICON
     ;
     */
    private void arrayIndex() {
        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.INTEGER_IDENTIFIER && nextToken != Token.INTEGER_LITERAL) {
            System.out.println("Error at line: " + lineIndex);
        }

    }

    /**
     * ret_type  : TYPE type_name
     | STRUCT IDENTIFIER
     | STRUCTYPE IDENTIFIER
     ;
     */
    private void retType() {

        nextToken = getNextLexeme().getToken();

        if (nextToken == Token.TYPE) { // ARRAy

            nextToken = getNextLexeme().getToken();

            // if the next token is not a return (data) type
            if (nextToken != Token.INTEGER && nextToken != Token.FLOAT &&
                    nextToken != Token.STRING &&  nextToken != Token.BOOLEAN) {
                System.out.println("Error at line: " + lineIndex);
            }
        } else if (nextToken == Token.STRUCT) {
            System.out.println("Make Scanner pick up Struct identifiers and structype identifiers");
        } else {
            System.out.println("Error at line: " + lineIndex);
        }

    }

    /**
     * type_name       : MVOID
     | INTEGER
     | SHORT
     ;
     */
    private void typeName() {

        // TODO: this may not be needed

    }

    /**
     * specifications  :
     | SPECIFICATIONS spec_list
     ;
     */
    private void specifications() {

        // TODO: Implement

    }

    /**
     * spec_list : spec_def
     | spec_list spec_def
     ;
     */
    private void specList() {

        // TODO: Implement

    }

    /**
     * spec_def : ENUM
     | STRUCT
     ;
     */
    private void specDef() {

        // TODO: Implement

    }

    /**
     * globals :
     | GLOBAL declarations
     ;
     */
    private void globals() {

        if (nextToken != Token.GLOBAL) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            declarations();
        }

    }

    /**
     * declarations :
     | DECLARATIONS const_dec var_dec
     ;
     */
    private void declarations() {

        if (nextToken != Token.DECLARATIONS) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            constDec();
            varDec();
        }

    }

    /**
     * const_dec : CONSTANTS const_list
     */
    private void constDec() {

        if (nextToken != Token.CONSTANTS) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            constList();
        }

    }

    /**
     * const_list : const_list DEFINE identifier equal_op constant_val TYPE DATA_TYPE
     ;
     */
    private void constList() {

        if (nextToken != Token.DEFINE) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.CONSTANT_INTEGER_IDENTIFIER && nextToken != Token.CONSTANT_FLOAT_IDENTIFIER
                && nextToken != Token.CONSTANT_STRING_IDENTIFIER && nextToken != Token.CONSTANT_BOOLEAN_IDENTIFIER) {
            System.out.println("Error at line " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.ASSIGNMENT_OPERATOR) {
            System.out.println("Error at line " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        // TODO: double check boolean logic
        if (nextToken != Token.INTEGER_LITERAL && nextToken != Token.FLOAT_LITERAL
                && nextToken != Token.STRING_LITERAL && nextToken != Token.BOOLEAN_LITERAL) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.TYPE) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.INTEGER && nextToken != Token.FLOAT
                && nextToken != Token.STRING && nextToken != Token.BOOLEAN) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        if (nextToken == Token.DEFINE) {
            constList();
        }
    }

    /**
     * var_dec : VARIABLES var_list
     ;
     */
    private void varDec() {

        if (nextToken != Token.VARIABLES) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            varList();
        }

    }

    /**
     * var_list : var_list DEFINE identifier rec_type
     ;
     */
    private void varList() {
        if (nextToken != Token.DEFINE) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.INTEGER_IDENTIFIER && nextToken != Token.FLOAT_IDENTIFIER
                && nextToken != Token.STRING_IDENTIFIER && nextToken != Token.BOOLEAN_IDENTIFIER) {
            System.out.println("Error at line " + lineIndex);
        }

        retType(); // TODO: double check this

        if (nextToken == Token.DEFINE) {
            varList();
        }
    }

    /**
     * implement : IMPLEMENTATIONS funct_list
     ;
     */
    private void implement() {

        if (nextToken != Token.IMPLEMENTATIONS) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            functList();
        }

    }

    /**
     * funct_list : funct_def
     | funct_list funct_def
     ;
     */
    private void functList() {

        functDef();

        if (nextToken == Token.FUNCTION) {
            functList();
        }

    }

    /**
     * funct_def : funct_body
     */
    private void functDef() {

        functBody();

    }

    /**
     * funct_body: FUNCTION main_head parameters f_body
     ;
     */
    private void functBody() {

        if (nextToken != Token.FUNCTION) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            parameters();
            mainHead();
            fBody();
        }

    }

    /**
     * main_head : MAIN
     | IDENTIFIER
     ;
     */
    private void mainHead() {

        if (nextToken == Token.FUNCTION_IDENTIFIER || nextToken == Token.MAIN) {
            nextToken = getNextLexeme().getToken();
        } else {
            System.out.println("Error at line: " + lineIndex);
        }

    }

    /**
     * parameters :
     | PARAMETERS param_list
     ;
     */
    private void parameters() {
        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.PARAMETERS) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            paramList();
        }
    }

    /**
     * param_list : param_def
     | param_list COMMA param_def
     ;
     */
    private void paramList() {
        paramDef();
    }

    /**
     * param_def : identifier chk_const chk_ptr chk_array TYPE type_name
     ;
     */
    private void paramDef() {

        if (nextToken != Token.INTEGER_IDENTIFIER && nextToken != Token.FLOAT_IDENTIFIER
                && nextToken != Token.STRING_IDENTIFIER && nextToken != Token.BOOLEAN_IDENTIFIER) {
            System.out.println("Error at line " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            if (nextToken != Token.TYPE) {
                // TODO: finish this.
            } else {
                nextToken = getNextLexeme().getToken();
                typeName();
            }
        }
    }

    /**
     * chk_const :
     | CONSTANT
     ;
     */
    private void chkConst() {

        if (nextToken != Token.CONSTANTS) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
        }

    }

    /**
     * f_body : BEGIN <statement_list> ENDFUN
     ;
     */
    private void fBody() {

        if (nextToken != Token.BEGIN) {
            System.out.println("Error at line: " + lineIndex);
        }

        statementList();

        if (nextToken != Token.END_FUN) {
            System.out.println("Error at line: " + lineIndex);
        }

    }

    /**
     * statement_list : statement
     | statement_list statement
     ;
     */
    private void statementList() {

        statement();

    }

    /**
     * statement : if_statement
     | assignment_statement
     | while_statement
     | print_statement
     | repeat_statement
     ;
     */
    private void statement() {

        if (nextToken == Token.IF) {
            nextToken = getNextLexeme().getToken();
            ifStatement();
        }

        if (nextToken == Token.SET) {
            nextToken = getNextLexeme().getToken();
            assignmentStatement();
        }

        if (nextToken == Token.WHILE) {
            nextToken = getNextLexeme().getToken();
            whileStatement();
        }

        if (nextToken == Token.DISPLAY) {
            nextToken = getNextLexeme().getToken();
            printStatement();
        }

        if (nextToken == Token.REPEAT) {
            nextToken = getNextLexeme().getToken();
            repeatStatement();
        }

    }

    /**
     * if_statement : IF boolean_expression THEN statement_list
     ELSE statement_list ENDIF
     ;
     */
    private void ifStatement() {
        booleanExpression();

        if (nextToken != Token.THEN) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        statementList();

        if (nextToken == Token.ELSE) {
            nextToken = getNextLexeme().getToken();
            statementList();
        } else if (nextToken == Token.ENDIF) {
            nextToken = getNextLexeme().getToken();
        } else {
            System.out.println("Error at line: " + lineIndex);
        }
    }

    /**
     * while_statement : WHILE boolean_expression DO statement_list ENDWHILE
     ;
     */
    private void whileStatement() {

        booleanExpression();

        if (nextToken != Token.DO) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        // TODO: Double check this.
        while (nextToken != Token.END_WHILE) {
            statementList();
        }

    }

    /**
     * assignment_statement : SET identifier assignment_operator arithmetic_expression
     ;
     */
    private void assignmentStatement() {

        if (nextToken != Token.INTEGER_IDENTIFIER && nextToken != Token.FLOAT_IDENTIFIER
                && nextToken != Token.STRING_IDENTIFIER && nextToken != Token.BOOLEAN_IDENTIFIER) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.ASSIGNMENT_OPERATOR) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        arithmenticExp();
    }

    /**
     * repeat_statement : REPEAT statement_list UNTIL boolean_expression ENDREPEAT
     ;
     */
    private void repeatStatement() {

    }

    /**
     * print_statement : DISPLAY arg_list
     ;
     */
    private void printStatement() {

        argList();

    }

    /**
     * arg_list : args
     | arg_list comma args
     ;
     */
    private void argList() {

        args();
    }

    /**
     * args : identifier
     | constant
     | string
     ;
     */
    private void args() {

        // TODO: Finish implementation

    }

    /**
     * boolean_expression : arithmetic_exp relative_op arithmetic_exp
     ;
     */
    private void booleanExpression() {
        arithmenticExp();
        relativeOp();
        arithmenticExp();
    }

    /**
     * relative_op : le_operator | lt_operator | ge_operator | gt_operator | eq_operator | ne_operator
     ;
     */
    private void relativeOp() {

        if (nextToken == Token.LE_OPERATOR || nextToken == Token.LT_OPERATOR || nextToken == Token.GE_OPERATOR
                || nextToken == Token.GT_OPERATOR || nextToken == Token.EQ_OPERATOR || nextToken == Token.NE_OPERATOR) {
            nextToken = getNextLexeme().getToken();
        } else {
            System.out.println("Error at line: " + lineIndex);
        }

    }

    /**
     * arithmetic_exp : arithmetic_exp add_operator mulexp
     | arithmetic_exp sub_operator mulexp
     | mulexp
     ;
     */
    private void arithmenticExp() {

        // TODO: Finish implementation

    }

    /**
     * mulexp : mulexp mul_operator primary
     | mulexp div_operator primary
     | primary
     ;
     */
    private void mulexp() {

        // TODO: Finish implementation

    }

    /**
     * primary : left_paren  arithmetic_exp right_paren
     | minus primary
     | constant_val
     | identifier
     ;
     */
    private void primary() {

    }

    private Lexeme getNextLexeme() {

        if (tokenIndex > sclSourceLines.get(lineIndex).getLexemes().size() - 1) {
            ++lineIndex;
            tokenIndex = 0;
        }

        if (lineIndex < sclSourceLines.size() - 1) {
            while(sclSourceLines.get(lineIndex).getLexemes().isEmpty() || lineIndex > sclSourceLines.size() - 1)
                ++lineIndex;
        }

        try {
            return sclSourceLines.get(lineIndex).getLexemes().get(tokenIndex++);
        } catch (Exception e) {
            return new Lexeme(Token.NOT_DEFINED, "");
        }
    }

    private void iterate() {
        nextToken = getNextLexeme().getToken();
    }

    private List<Token> getAllTokens() {
        List<Token> tokens = new ArrayList<>();
        for (SCLSourceLine line : sclSourceLines) {
            for (Lexeme lexeme : line.getLexemes()) {
                tokens.add(lexeme.getToken());
            }
        }

        return tokens;
    }

}
