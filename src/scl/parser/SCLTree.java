package scl.parser;

/**
 * Created by James on 11/18/2017.
 */
public class SCLTree {

    private SCLTreeNode root;

    /**
     * Creates instance of tree
     * @param root - root node
     */
    public SCLTree(SCLTreeNode root) {
        this.root = root;
    }

    /**
     * Returns the tree's root node
     * @return - the root node
     */
    public SCLTreeNode getRoot() {
        return root;
    }



    /**
     * Converts tree to a displayable string
     * @return - tree as a string
     */
    @Override
    public String toString() {
        return displayMyTree(root, 0);
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

        result += root.getData() + "\n";
        for (SCLTreeNode child: root.getChildren()) {
            result += displayMyTree(child, level + 1);
        }
        return result;
    }
}
