package com.shpp.p2p.cs.mkhmara.assignment17;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class ttt {
    public static void main(String[] args) {
        Comparator<Integer> comparator = (o1, o2) -> o2-o1;
        MyPriorityQueue<Integer> actual = new MyPriorityQueue<>(comparator);
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        Random random = new Random();
        int value;
        for (int i = 0; i < 500; i++){
            value = random.nextInt(1500);
            actual.add(value);
            expected.add(value);
        }
        System.out.println(Arrays.toString(actual.toArray()));
        System.out.println(Arrays.toString(expected.toArray()));
    }
}
