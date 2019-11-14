package com.shpp.p2p.cs.mkhmara.assignment16;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringJoiner;

public class MyLinkedList<E> implements Iterable<E> {

    //The length of the current Linked List
    private int size = 0;
    //The first element of the Linked List
    private Node<E> firstElement;
    //The last element of the Linked List
    private Node<E> lastElement;

    /**
     * This class is used to express each element of the LinkedList.
     * <p>
     * It's elements contain a certain value, and links to the next and previous element of the list.
     *
     * @param <E> generic type
     */
    private static class Node<E> {
        //contains the value of the node
        E value;
        //contains the link to the next node in the list
        Node<E> next;
        //contains the link to the previous node in the list
        Node<E> previous;

        /**
         * Creates a node with a given parameters
         *
         * @param previous link to the previous node in the list
         * @param value    the desired value of the node
         * @param next     the next node in the list
         */
        Node(Node<E> previous, E value, Node<E> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }


    /**
     * Adds a specific value to the end of the linked list.
     *
     * @param value that we intend to add
     */
    public void add(E value) {
        if (isEmpty()) {
            firstElement = lastElement = new Node<E>(null, value, null);
        } else {
            lastElement = new Node<>(lastElement, value, null);
            lastElement.previous.next = lastElement;
        }
        size++;
    }

    /**
     * Inserts a value into specific index in the LinkedList
     *
     * @param index index where the new element should be inserted
     * @param value the value, that we want to insert
     */
    public void add(int index, E value) {
        checkIndex(index);
        Node<E> current = firstElement;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        // if the element isn't teh first one we just add new element before it
        try {
            Node<E> newElem = new Node<>(current.previous, value, current);
            current.previous = newElem;
            newElem.previous.next = newElem;
        //otherwise we make the new element the first element in the linked list
        } catch (NullPointerException e){
            firstElement = new Node<>(null, value, current);
            firstElement.next.previous = firstElement;
        }
        size++;
    }

    /**
     * Adds all the  values from the given collection into the end of the Linked List.
     * <p>
     * The collection should hold objects of class that either extend E or are equal to it.
     *
     * @param addition the collection that we desire to add.
     */
    public void addAll(Collection<? extends E> addition) {
        E[] addableArray = (E[]) addition.toArray();
        for (E element : addableArray) {
            add(element);
        }
    }

    /**
     * Adds all the values from the given collection to the Linked list, starting from defined index.
     * <p>
     * The collection should hold objects of class that either extend E or are equal to it.
     *
     * @param addition the collection that we desire to add.
     */
    public void addAll(int index, Collection<? extends E> addition) {
        E[] addableArray = (E[]) addition.toArray();
        for (int element = 0; element < addableArray.length; element++, index++) {
            add(index, addableArray[element]);
        }
    }


    /**
     * @return true if the size of the linked list is 0; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the first index of the given element or -1 if there is no such element in the list
     *
     * @param element the element we are looking for in the Linked List
     * @return the first index of this element's instance
     */
    public int indexOf(E element) {
        Node<E> target = firstElement;

        for (int i = 0; i < size; i++) {
            if (target.value.equals(element)) {
                return i;
            }
            target = target.next;
        }

        return -1;
    }

    /**
     * Returns the index of the last instance of a certain object in the linked list
     *
     * @param element the element that we are looking for
     * @return the last index of the wanted element or -1 if there is no such element in the Linked List.
     */
    public int lastIndexOf(E element) {
        Node<E> target = lastElement.previous;
        for (int i = size - 1; i >= 0; i--) {
            if (target.value == element || target.value.equals(element)) {
                return i - 1;
            }
            target = target.previous;
        }
        return -1;
    }

    /**
     * Shows whether the LinkedList contains the instance of an object we are looking for.
     * <p>
     * Goes through the Linked list and returns true if it finds the needed object.
     *
     * @param object the object that we expect to find in the LinkedList
     * @return true if the object was found, false otherwise
     */
    boolean contains(E object) {
        return indexOf(object) >= 0;
    }

    /**
     * Returns the element of the linked list under the specific index
     *
     * @param index index of the element we need to get
     * @return the element under this exact index
     */
    public E get(int index) {
        checkIndex(index);
        Node<E> target = firstElement;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        return target.value;
    }


    /**
     * Sets a new value for the element under defined index.
     *
     * @param index the index of the element to be changed
     * @param value the new value of the element under the index
     */
    public void set(int index, E value) {
        checkIndex(index);
        Node<E> target = firstElement;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        target.value = value;
    }

    /**
     * Unlinks the first node with specified value from the Linked List by calling {@code remove(index)} method.
     *
     * @param element the element, first instance of which should be removed
     */
    public void remove(E element) {
        Node<E> target = firstElement;
        for (int i = 0; i < size; i++) {
            if (target.value == element || target.value.equals(element)) {
                // if the element we are removing is the first one
                if (target.previous == null) {
                    firstElement = target.next;
                } else {
                    target.previous.next = target.next;
                }
                // if the element  we are removing is the last one
                if (target.next == null) {
                    lastElement = target.previous;
                } else {
                    target.next.previous = target.previous;
                }
                break;
            }
            target = target.next;
        }
        size--;
    }


    /**
     * Unlinks the element under specific index from the Linked List.
     *
     * @param index the index of the element that has to be removed
     */
    public void removeAt(int index) {
        checkIndex(index);
        Node<E> target = firstElement;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        // if the element we are removing is the first one
        if (target.previous == null) {
            firstElement = target.next;
        } else {
            target.previous.next = target.next;
        }
        // if the element  we are removing is the last one
        if (target.next == null) {
            lastElement = target.previous;
        } else {
            target.next.previous = target.previous;
        }
        size--;
    }


    /**
     * Transforms the linked list into string to make it easier to show it on the console.
     *
     * @return string with the elements of the linked list
     */
    public String toString() {
        StringJoiner result = new StringJoiner(", ", "[", "]");
        Node<E> current = firstElement;
        for (int i = 0; i < size; i++) {
            result.add(current.value.toString());
            current = current.next;
        }
        return result.toString();
    }


    /**
     * Creates an array based on values stored in the LinkedList, the size is equal to the size of the LinkedList
     *
     * @return array with the content of Linked List
     */
    public E[] toArray() {
        Node target = firstElement;
        E[] result = (E[]) new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = (E)target.value;
            target = target.next;
        }
        return result;
    }

    /**
     * Resets the linked list
     */
    public void clear() {
        lastElement = firstElement = null;
        size = 0;
    }

    /**
     * Checks whether the given index lies within the bounds of LinkedList length. Throws an exception if it is not.
     *
     * @param index index to be checked
     * @throws NullPointerException when the index is out of bounds
     */
    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("IndexOutOfRange exception: index " + index + " out of length " + size);
        }
    }


    /**
     * Returns the Integer size of the Linked List
     *
     * @return int size
     */
    public int size() {
        return size;
    }

    private static class MyIterator<E> implements Iterator<E> {

        //values that the given collection contains
        private Node<E> current;
        //the length of data
        private int size;
        //the index of the current element
        private int index = 0;

        /**
         * Constructor that takes in the values to be stored
         *
         * @param firstNode the first node in the linked list
         * @param size the amount of elements in the linked list
         */
        MyIterator(Node<E> firstNode, int size) {
            current = firstNode;
            this.size = size;
        }


        /**
         * @return boolean value that shows if the element is the last one in the list
         */
        @Override
        public boolean hasNext() {
            return index < size;
        }

        /**
         * Returns the current element and increments the index, so it returns the next value when in is called
         *
         * @return the current value
         */
        @Override
        public E next() {
            E value = current.value;
            current = current.next;
            index++;
            return value;
        }
    }

    /**
     * @return iterator for going through the elements of the list and using forEach
     */
    @Override
    public Iterator<E> iterator() {
        return new MyIterator(firstElement, size);
    }

}
