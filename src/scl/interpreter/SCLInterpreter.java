package scl.interpreter;

import scl.parser.SCLParser;
import scl.parser.SCLTree;
import scl.parser.SCLTreeNode;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by James on 11/26/2017.
 */
public class SCLInterpreter extends SCLParser {

    public SCLInterpreter(File sclProgram) throws FileNotFoundException {
        super(sclProgram);
    }

    public void interpret() {
        SCLTree parseTree = super.parse();

        System.out.println(displayMyTree(parseTree.getRoot(), 0));

        System.out.println("Debugging");


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
