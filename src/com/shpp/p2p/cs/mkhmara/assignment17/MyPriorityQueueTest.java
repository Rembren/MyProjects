package com.shpp.p2p.cs.mkhmara.assignment17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MyPriorityQueueTest {

    Comparator<Integer> comparator = (o1, o2) -> o2-o1;
    MyPriorityQueue<Integer> actual = new MyPriorityQueue<>(comparator);
    PriorityQueue<Integer> expected = new PriorityQueue<>();
    Random random = new Random();

    @BeforeEach
    void setUp() {
        int value;
        for (int i = 0; i < 5000; i++){
            value = random.nextInt(1500);
            actual.add(value);
            expected.add(value);
        }
    }

    @Test
    void peek() {
        for (int i = 0; i < 5000; i++) {
            assertEquals(actual.peek(), expected.peek());
            actual.poll();
            expected.poll();
        }
    }

    @Test
    void poll() {
        for (int i = 0; i < 5000; i++) {
            assertEquals(actual.poll(), expected.poll());
        }
    }

    @Test
    void isEmpty() {
        assertEquals(actual.isEmpty(), expected.isEmpty());
        actual.clear();
        assertNotEquals(actual.isEmpty(), expected.isEmpty());
        expected.clear();
        assertEquals(actual.isEmpty(), expected.isEmpty());
    }

    @Test
    void size() {
        for (int i = 0; i < 5000; i++){
            assertEquals(actual.size(), expected.size());
            actual.poll();
            expected.poll();
            if (i == 25){
                actual.clear();
                expected.clear();
                assertEquals(actual.size(), expected.size());
                return;
            }
        }
    }
}