package com.shpp.p2p.cs.mkhmara.assignment17;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {

    MyHashMap<String, Integer> actual = new MyHashMap<>();
    HashMap<String, Integer> expected = new HashMap<>();
    String[] keys = new String[5000];
    Random random = new Random();
    @BeforeEach
    void setUp() {
        for (int i = 0; i < 5000; i++) {


            String key = generateString();
            int value = random.nextInt(1000);
            actual.put(key, value);
            expected.put(key, value);
            keys[i] = key;
        }
    }

    private String generateString(){
        byte[] array = new byte[7]; // length is bounded by 7
        for (int j = 0; j < array.length; j++){
            array[j] =(byte) (33 + random.nextInt(92));
        }
        return new String(array, StandardCharsets.US_ASCII);
    }

    @Test
    void get() {
        for (String key : actual.keySet()){
            assertEquals(actual.get(key), expected.get(key));
        }
    }

    @Test
    void getOrDefault() {

        for (int i = 0; i < 10000; i++){
            int defaultVal = random.nextInt(322);
            String key = generateString();
            assertEquals(actual.getOrDefault(key, defaultVal), expected.getOrDefault(key, defaultVal));
        }
    }

    @Test
    void remove() {
        for (int i = 0; i < 5; i++) {
            int pos = random.nextInt(keys.length);
            actual.remove(keys[pos]);
            expected.remove(keys[pos]);
            for (String key : actual.keySet()) {
                assertEquals(actual.get(key), expected.get(key));
            }
            for (String key : expected.keySet()) {
                assertEquals(actual.get(key), expected.get(key));
            }
        }
    }

    @Test
    void containsKey() {
        for (int i = 0; i < 500; i++){
            assertEquals(actual.containsKey(keys[i]), expected.containsKey(keys[i]));
        }
    }

    @Test
    void replace() {
        for (int i = 0; i < 500; i++) {
            int value = random.nextInt(322);
            int pos = random.nextInt(keys.length);
            actual.replace(keys[pos], value);
            expected.replace(keys[pos], value);
        }
        for (String key : actual.keySet()) {
            assertEquals(actual.get(key), expected.get(key));
        }
        for (String key : expected.keySet()) {
            assertEquals(actual.get(key), expected.get(key));
        }
    }


    @Test
    void clear() {
        actual.clear();
        expected.clear();
        assertEquals(actual.size(), expected.size());
        Assertions.assertEquals(actual.keySet(), expected.keySet());
        assertEquals(actual.toString(), expected.toString());
    }

    @Test
    void size() {
        assertEquals(actual.size(), expected.size());
    }

    @Test
    void isEmpty() {
        assertEquals(actual.isEmpty(), expected.isEmpty());
        actual.clear();
        assertNotEquals(actual.isEmpty(), expected.isEmpty());
        expected.clear();
        assertEquals(actual.isEmpty(), expected.isEmpty());
    }

}