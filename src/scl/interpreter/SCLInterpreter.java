package scl.interpreter;

import scl.parser.SCLParser;
import scl.parser.SCLTree;
import scl.parser.SCLTreeNode;
import scl.scanner.Lexeme;
import scl.scanner.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by James on 11/26/2017.
 */
public class SCLInterpreter extends SCLParser {

    private HashMap<String, Object> variables; // Used for storing the variables within the program
    private HashMap<String, Object> localVariables; // Used for storing the local variables within the program

    private Scanner input;

    public SCLInterpreter(File sclProgram) throws FileNotFoundException {
        super(sclProgram);
        variables = new HashMap<>();
        localVariables = new HashMap<>();
        input = new Scanner(System.in);
    }

    public void interpret() {
        SCLTree parseTree = super.parse();

        SCLTreeNode rootNode = parseTree.getRoot();

        rootNode.getChildren().forEach(this::interpret);
    }

    private void interpret(SCLTreeNode sclTreeNode) {

        List<Lexeme> lexemes = sclTreeNode.getSclSourceLine().getLexemes();

        if (lexemes.get(0).getToken() == Token.DEFINE) {
            variables.put(lexemes.get(1).getLexeme(), null);

            if (lexemes.get(2).getToken() == Token.ASSIGNMENT_OPERATOR) {
                variables.put(lexemes.get(1).getLexeme(), lexemes.get(3).getLexeme());
            }
        }

        if (lexemes.get(0).getToken() == Token.INPUT) {
            interpretInputStatement(lexemes);
        }

        if (lexemes.get(0).getToken() == Token.SET) {
            interpretAssignmentStatement(lexemes);
        }

        if (lexemes.get(0).getToken() == Token.DISPLAY) {
            interpretDisplayStatement(lexemes);
        }

        for (SCLTreeNode node : sclTreeNode.getChildren()) {
            interpret(node);
        }

    }

    private void interpretInputStatement(List<Lexeme> lexemes) {

        System.out.println(lexemes.get(1).getLexeme()); // Print user prompt

        String userInput = input.nextLine(); // Grab user input

        variables.put(lexemes.get(2).getLexeme(), userInput);

    }

    private void interpretDisplayStatement(List<Lexeme> lexemes) {
        System.out.println(lexemes.get(1).getLexeme());
        System.out.println(variables.get(lexemes.get(2).getLexeme()));
    }

    private void interpretAssignmentStatement(List<Lexeme> lexemes) {

        List<Lexeme> arithmeticExpressionLexemes = new ArrayList<>(lexemes.subList(3, lexemes.size()));

        variables.put(lexemes.get(1).getLexeme(), evaluate(arithmeticExpressionLexemes));
    }

    private float evaluate(List<Lexeme> lexemes) {

        /* Convert infix to postfix */

        HashMap<String, Integer> prec = new HashMap<>();
        prec.put("*", 3);
        prec.put("/", 3);
        prec.put("+", 2);
        prec.put("-", 2);

        List<Lexeme> postFixList = new ArrayList<>();
        Stack<String> opStack = new Stack<>();

        for (Lexeme lexeme : lexemes) {
            if (lexeme.getLexeme().matches("^[a-zA-Z0-9_.-]*$")) {
                postFixList.add(lexeme);
            } else {
                while (!opStack.empty() && prec.get(opStack.peek()) >= prec.get(lexeme.getLexeme())) {
                    postFixList.add(new Lexeme(Token.getToken(opStack.peek()), opStack.pop()));
                }
                opStack.push(lexeme.getLexeme());
            }
        }

        while (!opStack.empty()) {
            postFixList.add(new Lexeme(Token.getToken(opStack.peek()), opStack.pop()));
        }

        /* Evaluate the postfix expression */
        Stack<String> stack = new Stack<>();

        for (Lexeme component : postFixList) {
            switch (component.getToken()) {
                case MUL_OPERATOR: performMulOperation(stack); break;
                case DIV_OPERATOR: performDivOperator(stack); break;
                case ADD_OPERATOR: performAddOperator(stack); break;
                case SUB_OPERATOR: performSubOperator(stack); break;
                default: stack.push(component.getLexeme());
            }
        }

        return Float.parseFloat(stack.pop()); // There should be only one element in stack and that should be a float
    }

    private void performMulOperation(Stack<String> stack) {
        String first = stack.pop();
        String second = stack.pop();

        float firstValue;
        if (Token.getToken(first) == Token.INTEGER_LITERAL) {
            firstValue = (float) Integer.parseInt(first);
        } else if (Token.getToken(first) == Token.FLOAT_LITERAL) {
            firstValue = Float.parseFloat(first);
        } else {
            firstValue = Float.parseFloat(String.valueOf(variables.get(first)));
        }

        float secondValue;
        if (Token.getToken(second) == Token.INTEGER_LITERAL) {
            secondValue = (float) Integer.parseInt(second);
        } else if (Token.getToken(second) == Token.FLOAT_LITERAL) {
            secondValue = Float.parseFloat(second);
        } else {
            secondValue = Float.parseFloat(String.valueOf(variables.get(second)));
        }

        float result = firstValue * secondValue;

        stack.push(result + "");
    }

    private void performDivOperator(Stack<String> stack) {
        String first = stack.pop();
        String second = stack.pop();

        float firstValue;
        if (Token.getToken(first) == Token.INTEGER_LITERAL) {
            firstValue = (float) Integer.parseInt(first);
        } else {
            firstValue = (float) variables.get(first);
        }

        float secondValue;
        if (Token.getToken(second) == Token.INTEGER_LITERAL) {
            secondValue = (float) Integer.parseInt(second);
        } else {
            secondValue = (float) Integer.parseInt(String.valueOf(variables.get(second)));
        }

        float result = secondValue / firstValue;

        stack.push(result + "");
    }

    private void performAddOperator(Stack<String> stack) {
        String first = stack.pop();
        String second = stack.pop();

        float firstValue;
        if (Token.getToken(first) == Token.INTEGER_LITERAL) {
            firstValue = (float) Integer.parseInt(first);
        } else {
            firstValue = (float) variables.get(first);
        }

        float secondValue;
        if (Token.getToken(second) == Token.INTEGER_LITERAL) {
            secondValue = (float) Integer.parseInt(second);
        } else {
            secondValue = (float) variables.get(second);
        }

        float result = firstValue + secondValue;

        stack.push(result + "");

    }

    private void performSubOperator(Stack<String> stack) {
        String first = stack.pop();
        String second = stack.pop();

        float firstValue;
        if (Token.getToken(first) == Token.INTEGER_LITERAL) {
            firstValue = (float) Integer.parseInt(first);
        } else {
            firstValue = (float) variables.get(first);
        }

        float secondValue;
        if (Token.getToken(second) == Token.INTEGER_LITERAL) {
            secondValue = (float) Integer.parseInt(second);
        } else {
            secondValue = (float) variables.get(second);
        }

        float result = secondValue - firstValue;

        stack.push(result + "");

    }

    private float infixToPostfixEval(String arithmeticExpression) {

        /* Convert infix to postfix */

        HashMap<String, Integer> prec = new HashMap<>();
        prec.put("*", 3);
        prec.put("/", 3);
        prec.put("+", 2);
        prec.put("-", 2);

        List<String> postFixList = new ArrayList<>();
        Stack<String> opStack = new Stack<>();
        String[] parts = arithmeticExpression.split(" ");

        for (String part : parts) {
            if (part.matches("^[a-zA-Z0-9_.-]*$")) {
                postFixList.add(part);
            } else {
                while(!opStack.empty() && prec.get(opStack.peek()) >= prec.get(part)) {
                    postFixList.add(opStack.pop());
                }
                opStack.push(part);
            }
        }

        while (!opStack.empty()) {
            postFixList.add(opStack.pop());
        }

        /* Evaluate the post fix expression */
        Stack<String> stack = new Stack<>();

        for (String component : postFixList) {
            switch (component) {
                case "*":
                    break;
                case "/": break;
                case "+": break;
                case "-": break;
                default: stack.push(component);
            }
        }

        return (float) 0.0;
    }

    /**
     * Builds a String starting from root node
     * @param root - root node of MyTree instance
     * @param level - level the method is at in the tree
     * @return MyTree instance as a String
     */
    private static String displayMyTree(SCLTreeNode root, int level) {

        String result = "";

        // Build tabs if necessary
        for (int i = 0; i < level; i++) {
            result += "\t";
        }

        result += root.getSclSourceLine().getRawString() + "\n";
        for (SCLTreeNode child: root.getChildren()) {
            result += displayMyTree(child, level + 1);
        }

        return result;
    }
}
