package com.bahadir;

import java.util.Hashtable;
import java.util.Map;

public class RadixNode {
    private Map<Character, RadixNode> children = null;
    private String leafNodeString = null;
    private char val; 
    private String prefix = null;

    final static char END_WORD_NODE = '9';
    final static char START_WORD_NODE = '0';


    public RadixNode(char val, String leafNodeString, String prefix) {
        this.val = val;
        this.leafNodeString = leafNodeString;
        this.prefix = prefix;
    }

    public RadixNode(char val) {
        this.val = val;
        this.prefix = "";
    }

    public RadixNode(char val, String prefix) {
        this.val = val;
        this.prefix = prefix;
    }

    public RadixNode() {
        val = START_WORD_NODE;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean hasChildren() {
        return children != null;
    }

    public Map<Character, RadixNode> getChildren() {
        return children;
    }

    public RadixNode getChild(char c) {
        if (leafNodeString != null && children == null) {
            breakupLeaf();
        }

        if (children == null)
            return null;
        return children.containsKey(c) ? children.get(c) : null;
    }


    public void addChild(char c, RadixNode newNode) {
        if (!this.hasChildren()) {
            children = new Hashtable<Character, RadixNode>();
            this.children.put(c, newNode);
        } else {
            if (children.containsKey(c))
                breakupLeaf();

            children.put(c, newNode);
        }
    }

    private void breakupLeaf() {
        RadixNode child;
        char nodeVal;

        if (!hasChildren())
            children = new Hashtable<Character, RadixNode>();
        
        
        if (leafNodeString.length() >= 1) {
            nodeVal = leafNodeString.charAt(0);
            child = new RadixNode(nodeVal, leafNodeString.substring(1), prefix);
            children.put(nodeVal, child);
        }

        leafNodeString = null; 
    }


    public char getValueChar() {
        return val;
    }

    public String getValueStr() {
        return leafNodeString;
    }

    public boolean isDuplicate(String prefix) {
        if (this.prefix == null)
            return false;
        return this.prefix.equals(prefix);
    }

}
