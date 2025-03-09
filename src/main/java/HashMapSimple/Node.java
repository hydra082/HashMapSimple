package com.example.hashmap_simple;

/**
 * Узел хэш-таблицы, содержащий пару ключ-значение.
 * Используется для хранения данных в цепочках при обработке коллизий в хэш-таблице.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class Node<K, V> implements MapSimple.Entry<K, V> {
    /** Хэш-код ключа, используемый для сравнения. */
    final int hash;
    /** Ключ, хранимый в узле. */
    final K key;
    /** Значение, связанное с ключом. */
    V value;
    /** Ссылка на следующий узел в цепочке (для обработки коллизий). */
    Node<K, V> next;

    /**
     * Конструктор, который создаёт новый узел хэш-таблицы с заданными параметрами.
     *
     * @param hash хэш-код ключа
     * @param key ключ, который будет храниться в узле
     * @param value значение, связанное с ключом
     * @param next ссылка на следующий узел в цепочке, или null, если узел последний
     */
    Node (int hash, K key, V value, Node <K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    /**
     * Возвращает ключ, хранимый в узле.
     *
     * @return ключ
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * Возвращает значение, связанное с ключом в узле.
     *
     * @return значение
     */
    @Override
    public V getValue() {
        return value;
    }
}
