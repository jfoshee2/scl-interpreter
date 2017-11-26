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

    public SCLTree parse() { // TODO: Make this return a parse tree when implementing interpreter
        sclSourceLines = super.getSourceLines();

        Lexeme nextLexeme = getNextLexeme();
        nextToken = nextLexeme.getToken();

        while (lineIndex < sclSourceLines.size()) { // while there is still stuff to parse
            switch (nextToken) {
                case IMPORT: importDef(); break;
                case SYMBOLS: symbol(); break;
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
        System.out.println("Parsing import");

        // check to see if next token is a string literal
        nextToken = getNextLexeme().getToken();
        if (nextToken != Token.STRING_LITERAL) {
            System.out.println("Error at line number: " + lineIndex);
        }
        nextToken = getNextLexeme().getToken();

        System.out.println("Finished Parsing import");
    }

    /**
     * symbols :
     | symbols symbol_def
     ;
     */
    private void symbol() {

        System.out.println("Parsing symbols");

        nextToken = getNextLexeme().getToken();

        if (nextToken == Token.SYMBOL) {
            symbolDef();
            symbol(); // Parse next symbol
        }

        System.out.println("Finished Parsing symbols");

    }

    /**
     * symbol_def : SYMBOL IDENTIFIER HCON
     ;
     */
    private void symbolDef() {

        System.out.println("Parsing symbol");

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

        System.out.println("Finished Parsing symbol");

    }

    /**
     * forward_refs :
     | FORWARD frefs
     ;
     */
    private void forwardRefs() {

        System.out.println("Parsing forward_refs");

        nextToken = getNextLexeme().getToken();

        // Check to see if next token is
        if (nextToken != Token.DECLARATIONS) {
            System.out.println("Error at line number: " + lineIndex);
            return;
        }

        nextToken = getNextLexeme().getToken();

        forwardList();

        System.out.println("Finished Parsing forward_refs");

    }

    /**
     * frefs  : REFERENCES forward_list
     | forward_list
     ;
     */
    private void frefs() {

        // TODO: may not be needed.

    }

    /**
     * forward_list : forwards
     | forward_list forwards
     ;
     */
    private void forwardList() {

        System.out.println("Parsing forward_list");

        nextToken = getNextLexeme().getToken();

        forwards();

        System.out.println("Finished parsing forward_list");

    }

    /**
     * forwards :
     | func_main dec_parameters
     ;
     */
    private void forwards() {

        System.out.println("Parsing forwards");
        if (nextToken == Token.FUNCTION) {

            nextToken = getNextLexeme().getToken();

            funcMain();
            parameters();
            forwardList();
        }

        System.out.println("Finished parsing forwards");
    }

    /**
     * func_main :
     | FUNCTION IDENTIFIER oper_type
     | MAIN {dec_main();}
     ;
     */
    private void funcMain() {

        System.out.println("Parsing func_main");

        nextToken = getNextLexeme().getToken();

        if (nextToken == Token.FUNCTION_IDENTIFIER) {
            nextToken = getNextLexeme().getToken();
            operType();
        } else if (nextToken == Token.MAIN) {
            nextToken = getNextLexeme().getToken();
        } else {
            System.out.println("Error at line: " + lineIndex);
        }

        System.out.println("Finished Parsing func_main");

    }

    /**
     * oper_type : RETURN TYPE chk_ptr chk_array ret_type
     ;
     */
    private void operType() {
        System.out.println("Parsing oper_type");

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

        System.out.println("Finished Parsing oper_type");

    }

    /**
     * chk_ptr :
     | POINTER {pointer_flag = true;}
     ;
     */
    private void chkPtr() {

        // TODO: Implement, but may not be needed

    }

    /**
     * chk_array :
     | ARRAY array_dim_list
     ;
     */
    private void chkArray() {

        System.out.println("Parsing chk_array");

        if (nextToken == Token.ARRAY) {
            nextToken = getNextLexeme().getToken();
            arrayDimList();
        }

        System.out.println("Finished Parsing chk_array");

    }

    /**
     * array_dim_list : LB array_index RB
     | array_dim_list LB array_index RB
     ;
     */
    private void arrayDimList() {

        System.out.println("Parsing array_dim_list");

        if (nextToken != Token.LB) {
            System.out.println("Error at line: " + lineIndex);
        }

        arrayIndex();

        if (nextToken != Token.RB) {
            System.out.println("Error at line: " + lineIndex);
        }

        System.out.println("Finished Parsing array_dim_list");
    }

    /**
     * array_index : IDENTIFIER
     | ICON
     ;
     */
    private void arrayIndex() {

        System.out.println("Parsing array_index");

        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.INTEGER_IDENTIFIER && nextToken != Token.INTEGER_LITERAL) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        System.out.println("Finished Parsing array_index");

    }

    /**
     * ret_type  : TYPE type_name
     | STRUCT IDENTIFIER
     | STRUCTYPE IDENTIFIER
     ;
     */
    private void retType() {

        System.out.println("Parsing ret_type");

        if (nextToken == Token.TYPE) { // ARRAY

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

        System.out.println("Finished Parsing ret_type");

    }

    /**
     * type_name       : MVOID
     | INTEGER
     | SHORT
     ;
     */
    private void typeName() {

        System.out.println("Parsing type_name");

        if (nextToken != Token.MVOID && nextToken != Token.INTEGER && nextToken != Token.SHORT) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        System.out.println("Finished Parsing type_name");
    }

    /**
     * specifications  :
     | SPECIFICATIONS spec_list
     ;
     */
    private void specifications() {

        System.out.println("Parsing specifications");

        specList();

        System.out.println("Finished Parsing specifications");
    }

    /**
     * spec_list : spec_def
     | spec_list spec_def
     ;
     */
    private void specList() {

        System.out.println("Parsing spec_list");

        nextToken = getNextLexeme().getToken();
        specDef();

        System.out.println("Finished Parsing spec_list");

    }

    /**
     * spec_def : ENUM
     | STRUCT
     ;
     */
    private void specDef() {

        System.out.println("Parsing spec_def");

        if (nextToken != Token.ENUM && nextToken != Token.STRUCT) {
            System.out.println("Error at line:" + lineIndex);
        }

        System.out.println("Finished Parsing spec_def");

    }

    /**
     * globals :
     | GLOBAL declarations
     ;
     */
    private void globals() {

        System.out.println("Parsing globals");

        if (nextToken != Token.GLOBAL) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            declarations();
        }

        System.out.println("Finished Parsing globals");

    }

    /**
     * declarations :
     | DECLARATIONS const_dec var_dec
     ;
     */
    private void declarations() {

        System.out.println("Parsing declarations");

        if (nextToken != Token.DECLARATIONS) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            constDec();
            varDec();
        }

        System.out.println("Finished Parsing declarations");

    }

    /**
     * const_dec : CONSTANTS const_list
     */
    private void constDec() {

        System.out.println("Parsing const_dec");

        if (nextToken != Token.CONSTANTS) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            constList();
        }

        System.out.println("Finished Parsing const_dec");

    }

    /**
     * const_list : const_list DEFINE identifier equal_op constant_val TYPE DATA_TYPE
     ;
     */
    private void constList() {

        System.out.println("Parsing const_list");

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

        System.out.println("Finished Parsing const_list");
    }

    /**
     * var_dec : VARIABLES var_list
     ;
     */
    private void varDec() {

        System.out.println("Parsing var_dec");

        if (nextToken != Token.VARIABLES) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            varList();
        }

        System.out.println("Finished Parsing var_dec");

    }

    /**
     * var_list : var_list DEFINE identifier rec_type
     ;
     */
    private void varList() {

        System.out.println("Parsing var_list");

        if (nextToken != Token.DEFINE) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.INTEGER_IDENTIFIER && nextToken != Token.FLOAT_IDENTIFIER
                && nextToken != Token.STRING_IDENTIFIER && nextToken != Token.BOOLEAN_IDENTIFIER
                && nextToken != Token.ARRAY_INTEGER_IDENTIFIER && nextToken != Token.ARRAY_FLOAT_IDENTIFIER
                && nextToken != Token.ARRAY_STRING_IDENTIFIER && nextToken != Token.ARRAY_BOOLEAN_IDENTIFIER) {
            System.out.println("Error at line " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        if (nextToken == Token.ARRAY) {
            nextToken = getNextLexeme().getToken();
            nextToken = getNextLexeme().getToken();
        }

        if (nextToken == Token.OF) {
            nextToken = getNextLexeme().getToken();
        }

        retType(); // TODO: double check this

        if (nextToken == Token.DEFINE) {
            varList();
        }

        System.out.println("Finished Parsing var_list");
    }

    /**
     * implement : IMPLEMENTATIONS funct_list
     ;
     */
    private void implement() {

        System.out.println("Parsing implement");

        if (nextToken != Token.IMPLEMENTATIONS) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            functList();
        }

        System.out.println("Finished Parsing implement");

    }

    /**
     * funct_list : funct_def
     | funct_list funct_def
     ;
     */
    private void functList() {

        System.out.println("Parsing funct_list");

        functDef();

        if (nextToken == Token.FUNCTION) {
            functList();
        }

        System.out.println("Finished Parsing funct_list");

    }

    /**
     * funct_def : funct_body
     */
    private void functDef() {

        System.out.println("Parsing funct_def");

        functBody();

        System.out.println("Finished Parsing funct_def");

    }

    /**
     * funct_body: FUNCTION main_head parameters f_body
     ;
     */
    private void functBody() {
        System.out.println("Parsing funct_body");

        if (nextToken != Token.FUNCTION) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            parameters();
            mainHead();
            fBody();
        }

        System.out.println("Finished Parsing funct_body");

    }

    /**
     * main_head : MAIN
     | IDENTIFIER
     ;
     */
    private void mainHead() {

        System.out.println("Parsing main_head");

        if (nextToken == Token.FUNCTION_IDENTIFIER || nextToken == Token.MAIN) {
            nextToken = getNextLexeme().getToken();
        } else {
            System.out.println("Error at line: " + lineIndex);
        }

        System.out.println("Finished Parsing main_head");

    }

    /**
     * parameters :
     | PARAMETERS param_list
     ;
     */
    private void parameters() {

        System.out.println("Parsing parameters");

        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.PARAMETERS) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
            paramList();
        }

        System.out.println("Finished Parsing parameters");
    }

    /**
     * param_list : param_def
     | param_list COMMA param_def
     ;
     */
    private void paramList() {
        System.out.println("Parsing param_list");

        paramDef();

        System.out.println("Finished Parsing param_list");
    }

    /**
     * param_def : identifier chk_const chk_ptr chk_array TYPE type_name
     ;
     */
    private void paramDef() {

        System.out.println("Parsing param_def");

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

        System.out.println("Finished Parsing param_def");
    }

    /**
     * chk_const :
     | CONSTANT
     ;
     */
    private void chkConst() {

        System.out.println("Parsing chk_const");

        if (nextToken != Token.CONSTANTS) {
            System.out.println("Error at line: " + lineIndex);
        } else {
            nextToken = getNextLexeme().getToken();
        }

        System.out.println("Finished Parsing chk_const");

    }

    /**
     * f_body : BEGIN <statement_list> ENDFUN
     ;
     */
    private void fBody() {

        System.out.println("Parsing f_body");

        if (nextToken != Token.BEGIN) {
            System.out.println("Error at line: " + lineIndex);
        }

        statementList();

        if (nextToken != Token.END_FUN) {
            System.out.println("Error at line: " + lineIndex);
        }

        System.out.println("Finished Parsing f_body");

    }

    /**
     * statement_list : statement
     | statement_list statement
     ;
     */
    private void statementList() {
        System.out.println("Parsing statement_list");

        statement();

        System.out.println("Finished Parsing statement_list");

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

        System.out.println("Parsing statement");

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

        System.out.println("Finished Parsing statement");

    }

    /**
     * if_statement : IF boolean_expression THEN statement_list
     ELSE statement_list ENDIF
     ;
     */
    private void ifStatement() {
        System.out.println("Parsing if_statement");

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

        System.out.println("Finished Parsing if_statement");
    }

    /**
     * while_statement : WHILE boolean_expression DO statement_list ENDWHILE
     ;
     */
    private void whileStatement() {

        System.out.println("Parsing while_statement");

        booleanExpression();

        if (nextToken != Token.DO) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        // TODO: Double check this.
        while (nextToken != Token.END_WHILE) {
            statementList();
        }

        System.out.println("Finished Parsing while_statement");

    }

    /**
     * assignment_statement : SET identifier assignment_operator arithmetic_expression
     ;
     */
    private void assignmentStatement() {

        System.out.println("Parsing assignment_statement");

        if (nextToken != Token.INTEGER_IDENTIFIER && nextToken != Token.FLOAT_IDENTIFIER
                && nextToken != Token.STRING_IDENTIFIER && nextToken != Token.BOOLEAN_IDENTIFIER) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        if (nextToken != Token.ASSIGNMENT_OPERATOR) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();

        arithmeticExp();

        System.out.println("Finished Parsing assignment_statement");
    }

    /**
     * repeat_statement : REPEAT statement_list UNTIL boolean_expression ENDREPEAT
     ;
     */
    private void repeatStatement() {

        System.out.println("Parsing repeat_statement");

        if (nextToken != Token.REPEAT) {
            System.out.println("Error at line: " + lineIndex);
        }

        nextToken = getNextLexeme().getToken();


        // TODO: Double check this.
        while (nextToken != Token.END_REPEAT) {
            statementList();
        }

        System.out.println("Finished Parsing repeat_statement");

    }

    /**
     * print_statement : DISPLAY arg_list
     ;
     */
    private void printStatement() {

        System.out.println("Parsing print_statement");

        argList();

        System.out.println("Finished parsing print_statement");

    }

    /**
     * arg_list : args
     | arg_list comma args
     ;
     */
    private void argList() {

        System.out.println("Parsing arg_list");

        args();

        System.out.println("Finished parsing arg_list");
    }

    /**
     * args : identifier
     | constant
     | string
     ;
     */
    private void args() {

        System.out.println("Parsing args");

        // TODO: Possibly create a separate function for this to reduce redundancy
        if (nextToken != Token.INTEGER_IDENTIFIER && nextToken != Token.FLOAT_IDENTIFIER
                && nextToken != Token.STRING_IDENTIFIER && nextToken != Token.BOOLEAN_IDENTIFIER) {
            System.out.println("Error at line: " + lineIndex);
        }

        if (nextToken != Token.CONSTANT_INTEGER_IDENTIFIER && nextToken != Token.CONSTANT_FLOAT_IDENTIFIER
                && nextToken != Token.CONSTANT_STRING_IDENTIFIER && nextToken != Token.CONSTANT_BOOLEAN_IDENTIFIER) {
            System.out.println("Error at line: " + lineIndex);
        }

        if (nextToken != Token.INTEGER_LITERAL && nextToken != Token.FLOAT_LITERAL
                && nextToken != Token.STRING_LITERAL) {
            System.out.println("Error at line: " + lineIndex);
        }

        System.out.println("Finished parsing args");
    }

    /**
     * boolean_expression : arithmetic_exp relative_op arithmetic_exp
     ;
     */
    private void booleanExpression() {
        System.out.println("Parsing boolean_expression");

        arithmeticExp();
        relativeOp();
        arithmeticExp();

        System.out.println("Finished Parsing boolean_expression");
    }

    /**
     * relative_op : le_operator | lt_operator | ge_operator | gt_operator | eq_operator | ne_operator
     ;
     */
    private void relativeOp() {

        System.out.println("Parsing relative_op");

        if (nextToken == Token.LE_OPERATOR || nextToken == Token.LT_OPERATOR || nextToken == Token.GE_OPERATOR
                || nextToken == Token.GT_OPERATOR || nextToken == Token.EQ_OPERATOR || nextToken == Token.NE_OPERATOR) {
            nextToken = getNextLexeme().getToken();
        } else {
            System.out.println("Error at line: " + lineIndex);
        }

        System.out.println("Finished Parsing relative_op");

    }

    /**
     * arithmetic_exp : arithmetic_exp add_operator mulexp
     | arithmetic_exp sub_operator mulexp
     | mulexp
     ;
     */
    private void arithmeticExp() {

        System.out.println("Parsing arithmetic_exp");

        // TODO: Finish implementation, but may not be needed

        System.out.println("Finished Parsing arithmetic_exp");

    }

    /**
     * mulexp : mulexp mul_operator primary
     | mulexp div_operator primary
     | primary
     ;
     */
    private void mulexp() {

        System.out.println("Parsing mulexp");

        // TODO: Finish implementation, but may not be needed

        System.out.println("Finished Parsing mulexp");

    }

    /**
     * primary : left_paren  arithmetic_exp right_paren
     | minus primary
     | constant_val
     | identifier
     ;
     */
    private void primary() {
        System.out.println("Parsing primary");

        // TODO: Finish implementation, but may not be needed

        System.out.println("Finished Parsing primary");

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
