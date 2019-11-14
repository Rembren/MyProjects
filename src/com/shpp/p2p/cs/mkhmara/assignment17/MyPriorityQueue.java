package com.shpp.p2p.cs.mkhmara.assignment17;


import java.util.Comparator;

/**
 * Priority queue built on binary heap based on array. sorts the elements highest priority first by default.
 * Could be sent comparator for comparing by any parameter given by user.
 * <p>
 * If the object is of class, made by user it should implement Comparable interface to be correctly compared.
 *
 * @param <E> generic class
 */
public class MyPriorityQueue<E extends java.lang.Comparable> {
    //default capacity of the array
    private static final int DEFAULT_CAPACITY = 16;
    //array where all the elements are stored
    private E[] data;
    //the amount of elements currently stored in priority queue
    private int size = 0;
    //comparator, null by default, can be received via parameters, used to compare objects
    private Comparator<E> comparator;

    /**
     * Initializes array with default capacity
     */
    public MyPriorityQueue() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Initializes array with given capacity
     */
    MyPriorityQueue(int initialSize) {
        data = (E[]) new Object[initialSize];
    }

    /**
     * Initializes array with default capacity and links given value to the comparator
     */
    MyPriorityQueue(Comparator<E> comparator) {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        this.comparator = comparator;
    }

    /**
     * Initializes array with given capacity and links given value to the comparator
     */
    MyPriorityQueue(int initialSize, Comparator<E> comparator) {
        data = (E[]) new Object[initialSize];
        this.comparator = comparator;
    }

    /**
     * Sorts elements from bottom of the tree to top, calls different methods depending on whether the
     * comparator was initialized or not
     */
    private void sort() {
        for (int currentPosition = size / 2; currentPosition >= 0; currentPosition--) {
            if (comparator == null) {
                buildHeapWithoutComparator(currentPosition);
            } else {
                buildHeapWithComparator(currentPosition);
            }
        }
    }

    /**
     * Swaps the element with it's children if they have greater comparison value,
     * calls itself if it swapped something to ensure that the children of the swapped element are in order
     * <p>
     * Recursive method, called if there was no comparator initialized.
     *
     * @param currentPosition the position of the parent element in teh array
     */
    private void buildHeapWithoutComparator(int currentPosition) {
        Comparable<E> comparable = (Comparable<E>) data[currentPosition];
        int leftChild = 2 * currentPosition + 1;
        int rightChild = 2 * currentPosition + 2;
        if (leftChild < size && comparable.compareTo(data[leftChild]) < 0) {
            swap(currentPosition, leftChild);
            buildHeapWithoutComparator(leftChild);
        }
        if (rightChild < size && comparable.compareTo(data[rightChild]) < 0) {
            swap(currentPosition, rightChild);
            buildHeapWithoutComparator(rightChild);
        }
    }

    /**
     * Swaps the element with it's children if they have greater comparison value,
     * calls itself if it swapped something to ensure that the children of the swapped element are in order
     * <p>
     * Recursive method, called if the comparator was initialized
     *
     * @param currentPosition the position of the parent element in teh array
     */
    private void buildHeapWithComparator(int currentPosition) {
        int leftChild = 2 * currentPosition + 1;
        int rightChild = 2 * currentPosition + 2;
        if (leftChild < size && comparator.compare(data[currentPosition], data[leftChild]) < 0) {
            swap(currentPosition, leftChild);
            buildHeapWithComparator(leftChild);
        }
        if (rightChild < size && comparator.compare(data[currentPosition], data[rightChild]) < 0) {
            swap(currentPosition, rightChild);
            buildHeapWithComparator(rightChild);
        }
    }

    private void swap(int largest, int currentPosition){
        E temp = data[largest];
        data[largest] = data[currentPosition];
        data[currentPosition] = temp;
    }

    /**
     * Adds a new value to the priority queue, increases size and sorts the queue afterwards.
     *
     * @param element the element ot be added to the queue.
     */
    public void add(E element) {
        data[size++] = element;
        if (size >= data.length)
            grow();
        sort();
    }

    /**
     * Doubles the size of the array, copies all the elements from the data array into the new one,
     * makes data point at new array
     */
    private void grow() {
        E[] newArray = (E[]) new Object[data.length * 2];
        System.arraycopy(data, 0, newArray, 0, data.length);
        data = newArray;
    }

    /**
     * Returns the first-to-go element in the queue
     *
     * @return first to go element
     */
    public E peek() {
        return data[0];
    }

    /**
     * Returns the first-to-go element in the queue, removes it from the array, sorts the array afterwards
     *
     * @return first to go element
     */
    public E poll() {
        E result = data[0];

        System.arraycopy(data, 1, data, 0, size);
        size--;
        sort();

        return result;
    }

    /**
     * Creates a new array with default capacity
     */
    public void clear() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * @return true if the queue holds no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the amount of elements held in the queue
     */
    public int size() {
        return size;
    }

    /**
     * @return the array with all the values currently stored in the queue
     */
    public E[] toArray() {
        E[] result = (E[]) new Object[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }

}
