package HashMapSimple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class HashMapSimpleIteratorsTest {
    private HashMapSimple<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new HashMapSimple<String, Integer>();
    }

    @Test
    void testKeySet() {
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        Iterable<String> keys = map.keySet();
        Iterator<String> iterator = keys.iterator();

        int count = 0;
        boolean hasOne = false, hasTwo = false, hasThree = false;
        while (iterator.hasNext()) {
            String key = iterator.next();
            if ("one".equals(key)) hasOne = true;
            if ("two".equals(key)) hasTwo = true;
            if ("three".equals(key)) hasThree = true;
            count++;
        }

        assertEquals(3, count, "KeySet should contain 3 keys");
        assertTrue(hasOne && hasTwo && hasThree, "KeySet should contain all keys");
    }

    @Test
    void testValues() {
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 2); // Дубликат значения

        Iterable<Integer> values = map.values();
        Iterator<Integer> iterator = values.iterator();

        int count = 0;
        int sum = 0;
        while (iterator.hasNext()) {
            sum += iterator.next();
            count++;
        }

        assertEquals(3, count, "Values should contain 3 elements");
        assertEquals(5, sum, "Sum of values should be 1 + 2 + 2");
    }

    @Test
    void testEntrySet() {
        map.put("one", 1);
        map.put("two", 2);

        Iterable<HashMapSimple.Entry<String, Integer>> entries = map.entrySet();
        Iterator<HashMapSimple.Entry<String, Integer>> iterator = entries.iterator();

        int count = 0;
        boolean hasOne = false, hasTwo = false;
        while (iterator.hasNext()) {
            HashMapSimple.Entry<String, Integer> entry = iterator.next();
            if ("one".equals(entry.getKey()) && entry.getValue() == 1) hasOne = true;
            if ("two".equals(entry.getKey()) && entry.getValue() == 2) hasTwo = true;
            count++;
        }

        assertEquals(2, count, "EntrySet should contain 2 entries");
        assertTrue(hasOne && hasTwo, "EntrySet should contain all entries");
    }

    @Test
    void testIteratorThrowsExceptionOnEmptyMap() {
        Iterator<String> keyIterator = map.keySet().iterator();
        assertThrows(IllegalStateException.class, keyIterator::next,
                "next() on empty keySet should throw IllegalStateException");

        Iterator<Integer> valueIterator = map.values().iterator();
        assertThrows(IllegalStateException.class, valueIterator::next,
                "next() on empty values should throw IllegalStateException");

        Iterator<HashMapSimple.Entry<String, Integer>> entryIterator = map.entrySet().iterator();
        assertThrows(IllegalStateException.class, entryIterator::next,
                "next() on empty entrySet should throw IllegalStateException");
    }
}