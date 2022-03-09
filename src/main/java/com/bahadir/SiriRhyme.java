package com.bahadir;

import java.util.List;


public class SiriRhyme {
    public static void main(String[] args) {
        RadixTree radix = new RadixTree();

        radix.buildTree(RadixTree.readFile(args[0]));
        List<String> a = radix.collectRhymes(args[1]);

        for (String s : a) {
            System.out.println(s);
        }

    }

}