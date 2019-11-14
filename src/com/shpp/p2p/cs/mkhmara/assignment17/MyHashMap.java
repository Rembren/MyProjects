package com.shpp.p2p.cs.mkhmara.assignment17;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This is a class that copies the HashMap container. It has holds it's basic methods
 * that allow comfortable work with hashMap.
 *
 * @param <K> key to the value
 * @param <V> the value
 */
public class MyHashMap<K, V> implements Iterable {
    //initial capacity bu default
    private static final int DEFAULT_CAPACITY = 16;
    //if size * LOAD_FACTOR >= capacity the table will grow and it's elements will be transfered to new locations
    private static final double LOAD_FACTOR = 0.75;
    //table that holds all the nodes
    private Node[] data;
    //the amount of objects stored in this container
    private int size = 0;


    /**
     * Inner class for this container. Represents the entry that we put into the table with elements.
     * Each node holds it's key, value, hash and link to the next node.
     * <p>
     * Link to the next node is used to deal with collisions. When two nodes with different keys get the same hashcode
     * they are put under same cell in the table, and then are treated as if they were parts of the linked list.
     *
     * @param <K> the key, under which the values will be put
     * @param <V> the value that the node will hold
     */
    private class Node<K, V> {
        //next node with the same hashcode (if there is such) made to avoid collisions
        private Node<K, V> next;
        //hashcode of the current node
        private int hash;
        //the key by which the info will be searched
        private K key;
        //the value that the node holds
        private V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            hash = key.hashCode();
        }
    }

    /**
     * Initializes the object of the class  with the default capacity of the array, which is 16 positions.
     */
    public MyHashMap() {
        data = new Node[DEFAULT_CAPACITY];
    }

    /**
     * Initializes the object of the class  with the given capacity.
     */
    MyHashMap(int initialCapacity) {
        data = new Node[initialCapacity];
    }

    /**
     * Puts a value under certain key. If such key already exists changes the value that is stored there
     *
     * @param key the key to teh value
     * @param value the value that the key holds
     */
    public void put(K key, V value) {
        size++;
        checkAndModifySize();

        Node newElement = new Node(key, value);
        int currentHash = newElement.hash % data.length;
        if (currentHash < 0){
            currentHash *= -1;
        }

        if (data[currentHash] == null) {
            data[currentHash] = newElement;
        } else {
            Node currentElement = data[currentHash];
            while (currentElement.next != null) {
                if (currentElement.key == key || currentElement.key.equals(key)) {
                    currentElement.value = value;
                    size--;
                    return;
                }
                currentElement = currentElement.next;
            }
            currentElement.next = newElement;
        }
    }

    /**
     * Checks the amount of objects that hashMap currently holds and modifies it`s size of this amount is
     * equal or greater that array length * LOAD_FACTOR
     */
    private void checkAndModifySize() {
        if (size == data.length * LOAD_FACTOR) {
            resizeAndTransfer();
        }
    }

    /**
     * Creates a new array that will hold the elements and transfers all values and keys
     * from the old array to teh new one
     */
    private void resizeAndTransfer() {
        Node<K, V>[] newData = new Node[data.length * 2];

        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                addExistingElement(new Node(data[i].key, data[i].value), newData);
                Node currentElement = data[i];
                while (currentElement.next != null) {
                    currentElement = currentElement.next;
                    addExistingElement(new Node(currentElement.key, currentElement.value), newData);
                }
            }
        }
        data = newData;
    }

    /**
     * Private method that adds existing values to new array during it's resize
     * @param node node with data
     * @param newData target array
     */
    private void addExistingElement(Node node, Node<K, V>[] newData) {

        int currentHash = node.hash % newData.length;
        if (currentHash < 0){
            currentHash *= -1;
        }
        if (newData[currentHash] == null) {
            newData[currentHash] = node;
        } else {
            Node currentElement = newData[currentHash];
            while (currentElement.next != null) {
                currentElement = currentElement.next;
            }
            currentElement.next = node;
        }
    }

    /**
     * Returns the value that is stored under specific key
     * @param key the key to the value
     * @return the value that is stored under this key
     */
    public V get(K key) {
        int hash = key.hashCode() % data.length;
        if (hash < 0){
            hash *= -1;
        }
        V result = null;
        if (data[hash] != null) {
            Node currentNode = data[hash];
            while (currentNode != null) {
                if (currentNode.key == key || currentNode.key.equals(key)) {
                    result = (V) currentNode.value;
                }
                currentNode = currentNode.next;
            }
        }
        return result;
    }

    /**
     * Returns a value if there is such in the hashMap or returns the default value given by user
     * @param key the key, which value we want to get
     * @param defaultValue the default value in case there is no value under the key
     * @return value of defaultValue
     */
    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * Removes a certain object from the HashMap
     * @param key the key of the object we want to get
     */
    public void remove(K key) {
        size--;
        int hash = key.hashCode() % data.length;
        if (hash < 0){
            hash *= -1;
        }
        if (data[hash] == null) {throw  new NullPointerException("no such key");}
        if (data[hash].key == key || data[hash].equals(key)){
            data[hash] = data[hash].next;
            return;
        }
        Node currentNode = data[hash];
        Node targetNode = data[hash].next;
        while (targetNode.key != key && !targetNode.key.equals(key)) {
            currentNode = currentNode.next;
            targetNode = targetNode.next;
        }
        currentNode.next = targetNode.next;
    }

    /**
     * Checks if the hashMap contains a specific key
     * @param key key we are looking for
     * @return true if there is such key, false otherwise
     */
    public boolean containsKey(K key) {
        int hash = key.hashCode() % data.length;
        if (hash < 0){
            hash *= -1;
        }
        Node targetNode = data[hash];
        while (targetNode != null) {
            if (targetNode.key == key || targetNode.key.equals(key)) {
                return true;
            }
            targetNode = targetNode.next;
        }
        return false;
    }

    /**
     * Checks if the hashMap contains a specific value
     * @param value value we are looking for
     * @return true if there is such value, false otherwise
     */
    public boolean containsValue(V value) {
        for (Node datum : data) {
            if (datum != null) {
                Node targetNode = datum;
                while (targetNode != null) {
                    if (targetNode.value == value || targetNode.value.equals(value)) {
                        return true;
                    }
                    targetNode = targetNode.next;
                }

            }
        }
        return false;
    }

    /**
     * Changes a value of specific key
     * @param key key, which value we want to change
     * @param value the new value to be stored under such key
     */
    public void replace(K key, V value) {
        int hash = key.hashCode() % data.length;
        if (hash < 0){
            hash *= -1;
        }
        if (data[hash] == null) {
            throw new NullPointerException("no such key in the HashMap" + key);
        } else {
            Node currentNode = data[hash];
            while (currentNode != null) {
                if (currentNode.key == key || currentNode.key.equals(key)) {
                    currentNode.value = value;
                    break;
                }
                currentNode = currentNode.next;
            }
        }
    }

    /**
     * @return a Set that holds all the keys in the hashMap
     */
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();

        for (Node datum : data) {
            if (datum != null) {
                Node currentNode = datum;
                while (currentNode != null) {
                    keys.add((K) currentNode.key);
                    currentNode = currentNode.next;
                }
            }
        }
        return keys;
    }

    /**
     * @return an Array with all the values stored in hashMap
     */
    public V[] values() {
        V[] result = (V[]) new Object[size];
        int counter = 0;
        for (Node datum : data) {
            if (datum != null) {
                Node currentNode = datum;
                while (currentNode != null) {
                    result[counter] = (V) currentNode.value;
                    counter++;
                    currentNode = currentNode.next;
                }
            }
        }
        return result;
    }

    /**
     * @return a string with all key = value relations
     */
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (Node datum : data) {
            if (datum != null) {
                Node currentNode = datum;
                while (currentNode != null) {
                    result.append(currentNode.key).append("=").append(currentNode.value).append(", ");
                    currentNode = currentNode.next;
                }
            }
        }
        if (result.length() > 1) {
            result.delete(result.length() - 2, result.length());
        }
        result.append("}");
        return result.toString();
    }

    /**
     * Clears the existing hashMap
     */
    public void clear() {
        data = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * @return the size of the hashMap
     */
    public int size() {
        return size;
    }

    /**
     * @return whether the hashMap is empty or not
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Class to iterate through all the values in the hashMap
     * @param <V> generic class of value
     */
    private class MyIterator<V> implements Iterator {
        int positionInArray = 0;
        int entryCounter = 0;
        Node currentCell = data[0];

        /**
         * Method used to go through cells in array and tell if there is an element after the current
         * @return true if the element is not the last
         */
        @Override
        public boolean hasNext() {
            if (entryCounter == size) {
                return false;
            }

            if (currentCell == null) {
                if (moveToNextCell()) {
                    currentCell = data[positionInArray];
                } else {
                    return false;
                }
            }
            return true;
        }

        /**
         * Returns the value of the element and goes a step further in all the elements
         * @return value of current element
         */
        @Override
        public Object next() {
            V value = (V) currentCell.value;
            currentCell = currentCell.next;
            return value;
        }

        /**
         * Moves to the next cell in the array
         * @return true if there is a not null cell to move to
         */
        private boolean moveToNextCell() {
            positionInArray++;
            while (positionInArray < data.length && data[positionInArray] == null) {
                positionInArray++;
            }
            return positionInArray < data.length;
        }
    }

    /**
     * Iterators gonna iterate
     * @return iterator
     */
    @Override
    public MyIterator<V> iterator() {
        return new MyIterator<V>();
    }

}

