package com.example.hashmap_simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты обработки null-ключей в CustomHashMap.
 */
public class HashMapSimpleNullTest {
    private HashMapSimple<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new HashMapSimple<String, Integer>();
    }

    @Test
    void testPutAndGetWithNullKey() {
        map.put(null, 0);
        assertEquals(0, map.get(null), "Value for null key should be 0");

        map.put(null, 100);
        assertEquals(100, map.get(null), "Value for null key should be updated to 100");
    }

    @Test
    void testDeleteNullKey() {
        map.put(null, 0);
        assertEquals(0, map.delete(null), "Deleted value for null key should be 0");
        assertNull(map.get(null), "Null key should be removed");
    }
}