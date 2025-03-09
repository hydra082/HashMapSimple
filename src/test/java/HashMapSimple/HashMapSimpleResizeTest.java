package com.example.hashmap_simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class HashMapSimpleResizeTest {
    private HashMapSimple<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new HashMapSimple<String, Integer>();
    }

    @Test
    void testResize() {
        for (int i = 0; i < 13; i++) {
            map.put("key" + i, i);
        }

        for (int i = 0; i < 13; i++) {
            assertEquals(i, map.get("key" + i), "Value for 'key" + i + "' should be " + i);
        }
    }
}