package com.example.hashmap_simple;

/**
 * Простой интерфейс для ассоциативного массива (хэш-таблицы).
 * Определяет основные операции для работы с парами ключ-значение.
 *
 * @param <K> тип ключей, хранимых в таблице
 * @param <V> тип значений, связанных с ключами
 */
public interface MapSimple <K, V> {
    V get (K key);
    V put (K key, V value);
    V delete (K key);
    Iterable <K> keySet();
    Iterable <V> values();
    Iterable <Entry<K, V>> entrySet();


    /**
     * Вложенный интерфейс, представляющий одну пару ключ-значение в таблице.
     *
     * @param <K> тип ключа
     * @param <V> тип значения
     */
    interface Entry<K, V> {
        K getKey();
        V getValue();
    }
}
