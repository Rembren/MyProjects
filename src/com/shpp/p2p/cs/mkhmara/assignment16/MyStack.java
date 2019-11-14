package com.shpp.p2p.cs.mkhmara.assignment16;

import java.util.Iterator;

/**
 * This class is made to have the main features of java stack. It has the ability to add elements,
 * remove elements from the top of the stack and look at the element that is currently on the top of the stack.
 * <p>
 * This class uses simply connected list to simulate the behaviour of stack.
 *
 * @param <E> the undefined class of objects that will be stored in this stack
 */
public class MyStack<E> implements Iterable<E> {

    //The length of the current stack
    private int size = 0;
    //The top element in the stack
    private Node<E> topElement;

    /**
     * A simple Node class very similar to the one we used in linked list or queue.
     * <p>
     * This one has a link only to it's previous element.
     *
     * @param <E> the undefined class of objects that will be stored in this stack
     */
    private static class Node<E> {
        //contains the value of the node
        E value;
        //contains the link to the previous node in the list
        Node<E> previous;

        /**
         * Creates a node with a given parameters
         *
         * @param previous link to the previous node in the list
         * @param value    the desired value of the node
         */
        Node(Node<E> previous, E value) {
            this.value = value;
            this.previous = previous;
        }
    }

    /**
     * Adds an element to the top of the stack.
     *
     * @param value the value we intend to add to the stack
     */
    public void add(E value) {
        topElement = new Node(topElement, value);
        size++;
    }

    /**
     * @return Returns the value that was the last to be added to the stack
     * @throws NullPointerException when the stack is empty
     */
    public E peek() {
        if (isEmpty())
            throw new NullPointerException("The stack is empty");
        return topElement.value;
    }

    /**
     * Removes the last added element from the stack and returns it's value
     *
     * @return the value of the removed element or null if the stack is empty
     */
    public E pop() {
        if (isEmpty())
            return null;
        E value = topElement.value;
        topElement = topElement.previous;
        size--;
        return value;
    }

    /**
     * Removes the last added element from the stack and returns it's value
     *
     * @return the value of the removed element or
     * @throws NullPointerException if the stack is empty
     */
    public E remove() {
        if (isEmpty())
            throw new NullPointerException("The stack is empty");
        E value = topElement.value;
        topElement = topElement.previous;
        size--;
        return value;
    }

    /**
     * Returns tha amount of {@code pop()} we have to make to reach a certain element in the stack
     *
     * @param object the object we are looking for
     * @return the amount of pop() to make to reach this object of -1 of there is no such object in the stack
     */
    public int search(E object) {
        Node current = topElement;
        for (int distance = 0; distance < size; distance++) {
            if (current.value == object || checkObjectEquals(current.value, object)) {
                return distance;
            }
            current = current.previous;
        }
        return -1;
    }

    /**
     * Checks the values of current object and the one we are looking for
     * @param currentValue value that is in the current node
     * @param valueToCheck value that we intend to compare
     * @return true if objects are equal, false otherwise
     */
    private boolean checkObjectEquals(Object currentValue, E valueToCheck) {
        try {
            return currentValue.equals(valueToCheck);
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Generates an array out of all the elements of the stack from bottom to top.
     *
     * @return the array with the elements of the stack
     */
    public E[] toArray() {
        Node<E> current = topElement;
        E[] result = (E[]) new Object[size];
        for (int i = 0; i < size; i++) {
            result[size - i - 1] = current.value;
            current = current.previous;
        }
        return result;
    }

    /**
     * Removes the elements one by one until the size of the stack becomes zero
     */
    public void clear() {
        topElement = null;
        size = 0;
    }

    /**
     * @return the current amount of elements held by the stack
     */
    public int size() {
        return size;
    }

    /**
     * @return true if the size of stack is 0, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

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
