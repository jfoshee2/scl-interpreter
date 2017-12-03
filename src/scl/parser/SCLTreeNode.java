package scl.parser;

import scl.scanner.SCLSourceLine;

import java.util.ArrayList;

/**
 * Created by James on 11/18/2017.
 */
public class SCLTreeNode {
    private String data;
    private SCLSourceLine sclSourceLine;
    private SCLTreeNode parent;
    private ArrayList<SCLTreeNode> children;

    public SCLSourceLine getSclSourceLine() {
        return sclSourceLine;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public SCLTreeNode getParent() {
        return parent;
    }

    public void setParent(SCLTreeNode parent) {
        this.parent = parent;
    }

    public void setChildren(ArrayList<SCLTreeNode> children) {
        this.children = children;
    }

    /**
     * Creates instance of SCLTreeNode
     * @param data - behavior/response
     */
    public SCLTreeNode(String data) {
        this.data = data;
        children = new ArrayList<>();
    }

    public SCLTreeNode(SCLSourceLine sclSourceLine) {
        this.sclSourceLine = sclSourceLine;
        children = new ArrayList<>();
    }

    /**
     * Default constructor for creating SCLTreeNode instance
     */
    public SCLTreeNode() {
        children = new ArrayList<>();
    }

    /**
     * Adds children to node
     * @param child - the child that is to be added to the node
     */
    public void addChild(SCLTreeNode child) {
        children.add(child);
    }

    /**
     * Returns the node's children
     * @return - list of node's children
     */
    public ArrayList<SCLTreeNode> getChildren() {
        return children;
    }
}
