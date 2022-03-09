package com.bahadir;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;

public class RadixTree {
    RadixNode root;
    List<String> rhymes;


    public RadixTree() {
        this.root = new RadixNode();
        this.rhymes = new ArrayList<String>();
    }

    public List<String> collectRhymes(String word) {
        if (word == null || 
            word.equals("") || 
            !word.matches("[a-zA-Z]+"))
            return new ArrayList<String>();

        word = new StringBuilder(word)
            .reverse()
            .toString()
            .toLowerCase();

        RadixNode node = visitPrefixes(root, RadixNode.START_WORD_NODE + word, 0);

        Set<String> setOfRhymingNodes = prefixAllLeafs(node);

        return new ArrayList<String> (setOfRhymingNodes);
    }

    private Set<String> prefixAllLeafs(RadixNode node) {
        Set<String> prefixes = new TreeSet<String>();

        if (node == null)
            return prefixes;

        if (node.getValueChar() == RadixNode.START_WORD_NODE
            && !node.hasChildren())
            return prefixes;
        

        if (node != null)
            prefixes.add(
                new StringBuilder(node.getPrefix()).reverse().toString());
        
        if (node.hasChildren())
            node.getChildren().forEach((key, val) -> {
                RadixNode child = node.getChild(key);
                prefixes.addAll(prefixAllLeafs(child));
            });
        
        return prefixes;

    }

    private RadixNode visitPrefixes(RadixNode node, String prefix, int prefixIndex) {

        if( node.hasChildren() 
            && prefix.charAt(prefixIndex) == node.getValueChar()) {
            prefixIndex++;

            if(prefixIndex >= prefix.length())
                return node;

            RadixNode child = node.getChild(prefix.charAt(prefixIndex));

            if(child != null)
                return visitPrefixes(child, prefix, prefixIndex++);

            
        } else if (prefixIndex < prefix.length() - 1)
            return null;
        return node;
    }

    private void addNode(String word) {
        word = new StringBuilder(word)
            .reverse()
            .toString()
            .toLowerCase();

        RadixNode node = root;
        int index = 0;

        while (index < word.length()) {
            if (node.isDuplicate(word)) {
                return;
            }

            char c = word.charAt(index);
            RadixNode child = node.getChild(c);


            if (child == null || child.getValueChar() == RadixNode.START_WORD_NODE) {
                RadixNode newNode = new RadixNode(c, word.substring(index + 1), word);
                node.addChild(c, newNode);
                return;
            }

            node = child;
            index++;
        }

        node.addChild(RadixNode.END_WORD_NODE,
            new RadixNode(RadixNode.END_WORD_NODE, null, word));
    }

    public void buildTree(List<String> words) {
        for (String word : words) {
            addNode(word);
        }
    }

    public static List<String> readFile(String fileName) {
        try {
            return Files.readAllLines(Paths.get(fileName));
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
}