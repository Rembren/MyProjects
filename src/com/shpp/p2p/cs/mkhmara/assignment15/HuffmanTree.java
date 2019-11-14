package com.shpp.p2p.cs.mkhmara.assignment15;

import com.shpp.p2p.cs.mkhmara.assignment17.MyHashMap;
import com.shpp.p2p.cs.mkhmara.assignment17.MyPriorityQueue;

import java.util.HashMap;

/**
 * this class creates a huffman tree based on incoming HashMap<Character, Integer> with characters of the text and
 * their frequencies.
 */
class HuffmanTree {

    //the original root of the tree
    private Node root;
    //internal  hashMap with characters and their frequencies
    private MyHashMap<Character, Integer> frequencies;
    //HashMap with characters and their encodings based on the Huffman tree
    private MyHashMap<Character, String> encodings = new MyHashMap<>();

    /**
     * this constructor is used when we create an object of this class from another class,
     *
     * @param frequencies hashMap with unique characters of the text and their frequency
     */
    HuffmanTree(MyHashMap<Character, Integer> frequencies) {
        this.frequencies = frequencies;
        buildHuffmanTree();
    }

    /**
     * this is basically an element of the tree, contains value, character and links to it's children
     */
    private static class Node implements Comparable<Node> {

        private int frequency;
        private char character;
        private Node leftChild;
        private Node rightChild;

        /**
         * this constructor is used to create an element based on given frequency and character
         *
         * @param frequency frequency of the character in the text
         * @param character a unique character of the text
         */
        Node(int frequency, char character) {
            this.frequency = frequency;
            this.character = character;
        }

        /**
         * this constructor is used to create and element without character value but with frequency,
         * used for connecting two other Elements together and giving them summing frequency
         *
         * @param left  first element in priority queue with trees
         * @param right second element in priority queue with the trees
         */
        Node(Node left, Node right) {
            frequency = left.frequency + right.frequency;
            leftChild = left;
            rightChild = right;
        }

        @Override
        public int compareTo(Node node) {
            return node.frequency - frequency;
        }
    }

    /**
     * first creates a tree out of every set of character - frequency and puts them all into priority queue, with
     * overriden comparison conditions.
     */
    private void buildHuffmanTree() {
        MyPriorityQueue<Node> nodes = new MyPriorityQueue<>();
        for (Character key : frequencies.keySet()) {
            nodes.add(new Node(frequencies.get(key), key));
        }
        while (nodes.size() != 1) {
            Node firstElement = nodes.poll();
            Node secondElement = nodes.poll();
            nodes.add(new Node(firstElement, secondElement));
        }
        root = nodes.poll();
    }


    /**
     * generate encoding for every character in the tree using depth first search
     * we add 0 or 1 to byte code depending on which child we are going to, if there are no children left
     * we go back and delete values in the encoding
     *
     * @param current  the current element of the huffman tree
     * @param encoding the string with current encoding
     */
    private void generateEncodings(Node current, StringBuilder encoding) {
        // \u0000 - default value of char after it's initialization
        if (current.character != '\u0000') {
            encodings.put(current.character, encoding.toString());
        } else {
            generateEncodings(current.rightChild, encoding.append("1"));
            encoding.deleteCharAt(encoding.length() - 1);
            generateEncodings(current.leftChild, encoding.append("0"));
            encoding.deleteCharAt(encoding.length() - 1);
        }
    }

    /**
     * @return HashMap<Character, String> with encoding for every symbol
     */
    MyHashMap<Character, String> getEncodingTable() {
        StringBuilder encoding = new StringBuilder();
        generateEncodings(root, encoding);
        return encodings;
    }

    /**
     * Decodes the given string by looking at current character and descending the huffman tree in the correct order
     *
     * @param encoded encoded string with characters that represent bits
     * @return decoded string with data
     */
    public String decode(String encoded) {
        Node current = root;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < encoded.length(); i++) {
            if (current.character != '\u0000') {
                result.append(current.character);
                current = root;
            }
            if (encoded.charAt(i) == '0') {
                current = current.leftChild;
            } else {
                current = current.rightChild;
            }
        }
        return result.toString();
    }

}
