package com.example.hashmap_simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class HashMapSimpleDelPutGetTest {
    private HashMapSimple<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new HashMapSimple<String, Integer>();
    }

    @Test
    void testPutAndGetWith1200Elements() {
        for (int i = 0; i < 1200; i++) {
            map.put("key" + i, i);
        }

        for (int i = 0; i < 1200; i++) {
            assertEquals(i, map.get("key" + i), "Value for 'key" + i + "' should be " + i);
        }

        assertNull(map.get("key1200"), "Value for 'key1200' should be null");
    }

    @Test
    void testPutUpdatesValueWith1200Elements() {
        for (int i = 0; i < 1200; i++) {
            map.put("key" + i, i);
        }

        map.put("key500", 1000);
        assertEquals(1000, map.get("key500"), "Value for 'key500' should be updated to 1000");

        for (int i = 0; i < 1200; i++) {
            if (i != 500) {
                assertEquals(i, map.get("key" + i), "Value for 'key" + i + "' should be " + i);
            }
        }
    }

    @Test
    void testDeleteWith1200Elements() {
        for (int i = 0; i < 1200; i++) {
            map.put("key" + i, i);
        }

        assertEquals(500, map.delete("key500"), "Deleted value should be 500");
        assertNull(map.get("key500"), "Key 'key500' should be removed");

        for (int i = 0; i < 1200; i++) {
            if (i != 500) {
                assertEquals(i, map.get("key" + i), "Value for 'key" + i + "' should be " + i);
            }
        }
    }

    @Test
    void testDeleteNonExistentKeyWith1200Elements() {
        for (int i = 0; i < 1200; i++) {
            map.put("key" + i, i);
        }

        assertNull(map.delete("key1200"), "Deleting nonexistent key should return null");

        for (int i = 0; i < 1200; i++) {
            assertEquals(i, map.get("key" + i), "Value for 'key" + i + "' should be " + i);
        }
    }
}