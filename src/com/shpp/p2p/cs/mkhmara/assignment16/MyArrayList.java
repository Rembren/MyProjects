package com.shpp.p2p.cs.mkhmara.assignment16;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringJoiner;

/**
 * This class is made to contain the elements of a certain class. it is flexible storage, it's size grows
 * every time we exceed it`s capacity. It stores all the values in an Array.
 * <p>
 * This class also uses custom iterator to work with forEach cycle.
 *
 * @param <E> generic type
 */
public class MyArrayList<E> implements Iterable<E> {

    //the default initial capacity of the arrayList
    private static final int INITIAL_CAPACITY = 10;
    //An array with data
    private E[] data;
    //The variable used to remember the amount of elements in the arrayList
    private int size = 0;


    //When we initialize class with this constructor it creates an array with a size of 0
    public MyArrayList() {
        data = (E[]) new Object[INITIAL_CAPACITY];
    }

    //When we initialize class with this constructor it creates an array with a given size
    MyArrayList(int givenCapacity) {
        data = (E[]) new Object[givenCapacity];
    }

    /**
     * This method is used to delete all the elements that were stored in the arrayList.
     * It does so by simply creation a new empty array of objects.
     */
    public void clear() {
        data = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }


    /**
     * Multiplies the length of the array that holds the data by 2
     */
    private void extend() {
        E[] updated = (E[]) new Object[data.length * 2];
        System.arraycopy(data, 0, updated, 0, data.length);
        data = updated;
    }


    /**
     * Checks if there is a need to modify the size of the arrayList and modifies it if so
     *
     * Cycle while is used here only to ensure that when we add another container to arrayList enough space for
     * all of it's elements will be allocated, even if it is much bigger that the current data array
     */
    private void checkAndModifySize() {
        while (data.length < size) {
            extend();
        }
    }

    /**
     * Adds an element to the end of the ArrayList
     *
     * @param newElement the element we intend to add to the arrayList
     */
    public void add(E newElement) {
        size++;
        checkAndModifySize();
        data[size - 1] = newElement;
    }


    /**
     * Inserts an element into a given position in the ArrayList
     *
     * @param index      the index where we intend to insert an element
     * @param newElement element that is to be inserted into the arrayList.
     */
    public void add(int index, E newElement) {
        checkIndex(index);
        int temp = size;
        size++;
        checkAndModifySize();
        System.arraycopy(data, 0, data, 0, index);
        System.arraycopy(data, index, data, index + 1, temp - index);
        data[index] = newElement;
    }

    /**
     * Adds all the elements from a given collection to the end of the ArrayList
     *
     * @param addition collection with elements to be added to the end of the ArrayList
     */
    public void addAll(Collection<? extends E> addition) {
        E[] additionArray = (E[]) addition.toArray();
        int temp = size;
        size += additionArray.length;
        checkAndModifySize();
        System.arraycopy(additionArray, 0, data, temp, additionArray.length);
    }

    /**
     * Inserts all the elements from given collection to the ArrayList, starting from given index.
     *
     * @param addition collection with elements to be added to the ArrayList
     * @param index    the starting index of element adding
     */
    public void addAll(int index, Collection<? extends E> addition) {
        checkIndex(index);
        E[] additionArray = (E[]) addition.toArray();
        int temp = size;
        size += additionArray.length;
        checkAndModifySize();
        System.arraycopy(data, 0, data, 0, index);
        System.arraycopy(data, index, data, index + additionArray.length, temp - index);
        System.arraycopy(additionArray, 0, data, index, additionArray.length);
    }

    /**
     * Removes the object with a certain index from the ArrayList
     *
     * @param index index of the element to be deleted
     */
    public void remove(int index) {
        checkIndex(index);
        size--;
        System.arraycopy(data, 0, data, 0, index);
        System.arraycopy(data, index + 1, data, index, data.length - index - 1);
    }


    /**
     * Removes a first instance of a given object in the arrayList
     *
     * @param object object in the ArrayList to be deleted
     */
    public void remove(E object) {
        int index = indexOf(object);
        if (index > -1) {
            remove(index);
        }
    }

    /**
     * Checks if there are objects in the ArrayList that are equal to given object.
     * <p>
     * Goes through the Array using forEach cycle, if it find at least one instance of object, that is similar
     * to the one we search returns true.
     *
     * @param element the element we are looking for
     * @return true if there are such elements in the Array, false otherwise
     */
    public boolean contains(E element) {
        return indexOf(element) >= 0;
    }

    /**
     * @return whether the ArrayList is empty or not.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the first index of an instance of a given object.
     *
     * @param element the element which index in the ArrayList we want to know.
     * @return index of the wanted element if it was found, -1 if it is not in the ArrayList
     */
    public int indexOf(E element) {
        for (int i = 0; i < data.length; i++) {
            if (element == data[i] || element.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the last index of an instance of a given object.
     *
     * @param element the element which index in the ArrayList we want to know.
     * @return index of the wanted element if it was found, -1 if it is not in the ArrayList
     */
    public int lastIndexOf(E element) {
        for (int i = size - 1; i >= 0; i--) {
            if (element == data[i] || element.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Transforms ArrayList into array.
     * <p>
     * The returning array is the same size as the ArrayList we were using
     *
     * @return the array holding all the info, that was held by the ArrayList.
     */
    public E[] toArray() {
        E[] result = (E[]) new Object[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }

    /**
     * Transforms all the data that is held in the ArrayList into string, so it is easier to write into console.
     *
     * @return the string with all the element of the ArrayList
     */
    public String toString() {
        StringJoiner string = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            String element = data[i].toString();
            string.add(element);
        }
        return string.toString();
    }

    /**
     * @return the current size of the ArrayList. Always equals to the number of elements it holds.
     */
    public int size() {
        return size;
    }

    /**
     * Returns and element from the ArrayList with a given index.
     *
     * @param index the index of the element, we want to get
     * @return the element, that is held on given index, if the index is out of range, throws exception
     */
    public E get(int index) {
        checkIndex(index);
        return data[index];
    }

    /**
     * Sets the element under given index to another value.
     *
     * @param index    the index of the element we want to change
     * @param newValue the new value we want to assign to the index
     */
    public void set(int index, E newValue) {
        checkIndex(index);
        data[index] = newValue;
    }

    /**
     * Private method that checks whether the index we use in methods that require one is not out of bounds.
     *
     * @param index the index we want to check
     * @throws NullPointerException when the index is out of bounds
     */
    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("My IndexOutOfRange exception: index " + index + " out of length " + size);
        }
    }

    private class MyIterator<E> implements Iterator<E> {

        //the index of the current element
        private int index = 0;
        private int size;

        /**
         * Constructor that takes in the values to be stored
         *
         * @param size length of data on current array
         */
        MyIterator(int size) {
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
            return (E) data[index++];
        }
    }

    /**
     * @return The iterator for current data array, used automatically in forEach cycles.
     */

    public Iterator<E> iterator() {
        return new MyIterator<E>(size);
    }
}
