package com.shpp.p2p.cs.mkhmara.assignment16;

import java.util.Iterator;

/**
 * This class was made to simulate the queue. It has all basic methods of queue:
 * {@code add}, {@code peek}, {@code remove}.
 *
 * To simulate the queue this class uses a doubly linked list
 * @param <E> generic class
 */
public class MyQueue<E> implements Iterable<E> {

    //The length of the current queue
    private int size = 0;
    //The first to exit element in the queue
    private Node<E> firstElement;
    //The last added element in the queue
    private Node<E> lastElement;

    /**
     * This class is almost identical to the class we  used in linked list.
     *
     * represents a link in the list, that contains a certain value and points at the next and the previous element.
     *
     * @param <E> generic class
     */
    private static class Node<E> {
        //contains the value of the node
        E value;
        //contains the link to the next node in the queue
        Node<E> next;
        //contains the link to the previous node in the queue
        Node<E> previous;

        /**
         * Creates a node with a given parameters
         *
         * @param next  link to the next node in the list
         * @param value the desired value of the node
         */
        Node(Node<E> previous, E value, Node<E> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    /**
     * Adds an element with a given value below the topNode.
     *
     * @param value value that we desire to add to the queue
     */
    public void add(E value) {
        if (isEmpty()){
            firstElement = lastElement = new Node<E>(null, value, null);
        } else {
            lastElement = new Node<>(lastElement, value, null);
            lastElement.previous.next = lastElement;
        }
        size++;
    }

    /**
     * @return the value of the element that is pointed by the firstNode.
     * @throws NullPointerException when the queue is empty
     */
    public E peek() {
        if (isEmpty())
            throw new NullPointerException("The queue is empty");
        return firstElement.value;
    }

    /**
     * @return the element that is currently teh first element to go, returns null if the queue is empty
     */
    public E poll() {
        if (isEmpty())
            return null;
        E value = firstElement.value;
        firstElement = firstElement.next;
        size--;
        return value;
    }

    /**
     * @return the element that is currently teh first element to go, throws a null pointer exception if the queue is empty
     * @throws NullPointerException when the queue is empty
     */
    public E remove() {
        if (isEmpty())
            throw new NullPointerException("The queue is empty");
        E value = firstElement.value;
        firstElement = firstElement.next;
        size--;
        return value;
    }

    /**
     * Clears all the elements that were stored in a queue
     */
    public void clear(){
        lastElement = firstElement = null;
        size = 0;
    }

    /**
     * Generates an Array out of all the elements of the que from first to last.
     * @return the array with all the elements stored in the queue
     */
    public E[] toArray(){
        Node<E> current = firstElement;
        E[] result = (E[]) new Object[size];
        for (int i = 0; i < size; i++){
            result[i] = current.value;
            current = current.next;
        }
        return result;
    }

    /**
     * @return the current amount of elements held by the stack
     */
    public int size(){
        return size;
    }

    /**
     * @return true if the size of the queue is zero, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Iterator class to go through the elements of the queue
     * @param <E> generic class
     */
    private static class MyIterator<E> implements Iterator<E> {

        //values that the given collection contains
        private E[] values;
        //the index of the current element
        private int index = 0;

        /**
         * Constructor that takes in the values to be stored
         *
         * @param values array with values of certain type to be stored
         */
        MyIterator(E[] values) {
            this.values = values;
        }


        /**
         * @return boolean value that shows if the element is the last one in the list
         */
        @Override
        public boolean hasNext() {
            return index < values.length;
        }

        /**
         * Returns the current element and increments the index, so it returns the next value when in is called
         *
         * @return the current value
         */
        @Override
        public E next() {
            return values[index++];
        }
    }

    /**
     * @return The iterator for current data array, used automatically in forEach cycles.
     */
    @Override
    public Iterator<E> iterator() {
        return new MyIterator<E>(toArray());
    }
}
