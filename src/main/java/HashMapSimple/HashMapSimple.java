package HashMapSimple;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Простая реализация хэш-таблицы для хранения пар ключ-значение.
 * Этот класс не является потокобезопасным и поддерживает null-ключи. Использует метод цепочек для обработки коллизий
 * и автоматически увеличивает размер при превышении коэффициента загрузки 0.75.
 *
 * @param <K> тип ключей, хранимых в таблице
 * @param <V> тип значений, связанных с ключами
 */
public class HashMapSimple<K, V> implements MapSimple<K, V> {
    /** Начальная ёмкость хэш-таблицы по умолчанию. */
    private static final int DEFAULT_CAPACITY = 16;
    /**Коэффициент загрузки для увеличения размера таблицы. */
    private static final float LOAD_FACTOR = 0.75f;

    /** Массив узлов, представляющий хэш-таблицу. */
    private Node<K, V>[] table;
    /** Количество пар ключ-значение в таблице. */
    private int size;

    /**
     * Создаёт пустую хэш-таблицу с начальной ёмкостью по умолчанию (16).
     */
    @SuppressWarnings("unchecked")
    public HashMapSimple() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Вычисляет индекс в хэш-таблице для заданного хэш-кода и текущей ёмкости.
     *
     * @param hash хэш-код ключа
     * @param capacity текущая ёмкость таблицы
     * @return индекс в таблице, куда следует поместить пару ключ-значение
     */
    private int getIndex(int hash, int capacity) {
        return hash & (capacity - 1);
    }

    /**
     * Увеличивает размер хэш-таблицы, удваивая её ёмкость и перераспределяя все существующие записи.
     * Метод вызывается автоматически, когда размер превышает порог коэффициента загрузки (LOAD_FACTOR).
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length * 2];

        for (Node<K, V> node : oldTable) {
            while (node != null) {
                int newIndex = getIndex(node.hash, table.length);
                Node<K, V> next = node.next;
                node.next = table[newIndex];
                table[newIndex] = node;
                node = next;
            }
        }
    }

    private boolean keysMatch(Node<K, V> node, K key, int hash) {
        return node.hash == hash && (Objects.equals(key, node.key));
    }

    /**
     * Возвращает значение, связанное с указанным ключом.
     *
     * @param key ключ, для которого нужно найти значение
     * @return значение, связанное с ключом, или null, если ключ не найден
     */
    @Override
    public V get(K key) {
        int hash = key == null ? 0 : key.hashCode();
        int index = getIndex(hash, table.length);

        Node<K, V> node = table[index];
        while (node != null) {
            if (keysMatch(node, key, hash)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    /**
     * Связывает указанное значение с указанным ключом в таблице.
     * Если таблица уже содержит запись для этого ключа, старое значение заменяется.
     *
     * @param key ключ, с которым нужно связать значение
     * @param value значение, которое нужно связать с ключом
     * @return предыдущее значение, связанное с ключом, или null, если ключа не было
     */
    @Override
    public V put(K key, V value) {
        int hash = key == null ? 0 : key.hashCode();
        int index = getIndex(hash, table.length);

        Node<K, V> node = table[index];
        if (node == null) {
            table[index] = new Node<>(hash, key, value, null);
            size++;
        } else {
            Node<K, V> current = node;
            while (true) {
                if (keysMatch(current, key, hash)) {
                    V oldValue = current.value;
                    current.value = value;
                    return oldValue;
                }
                if (current.next == null) break;
                current = current.next;
            }
            current.next = new Node<>(hash, key, value, null);
            size++;
        }

        if (size > table.length * LOAD_FACTOR) {
            resize();
        }
        return null;
    }

    /**
     * Удаляет запись для указанного ключа из таблицы, если она существует.
     *
     * @param key ключ, запись для которого нужно удалить
     * @return предыдущее значение, связанное с ключом, или null, если ключа не было
     */
    @Override
    public V delete(K key) {
        int hash = key == null ? 0 : key.hashCode();
        int index = getIndex(hash, table.length);

        Node<K, V> node = table[index];
        if (node == null) return null;

        if (keysMatch(node, key, hash)) {
            table[index] = node.next;
            size--;
            return node.value;
        }

        while (node.next != null) {
            if (keysMatch(node.next, key, hash)) {
                V oldValue = node.next.value;
                node.next = node.next.next;
                size--;
                return oldValue;
            }
            node = node.next;
        }
        return null;
    }

    /**
     * Возвращает итерируемый набор всех ключей в таблице.
     *
     * @return объект Iterable, содержащий все ключи
     */
    @Override
    public Iterable<K> keySet() {
        return () -> new Iterator<>() {
            private int index = 0;
            private Node<K, V> current = null;

            @Override
            public boolean hasNext() {
                while (current == null && index < table.length) {
                    current = table[index++];
                }
                return current != null;
            }

            @Override
            public K next() {
                if (!hasNext()) throw new NoSuchElementException();
                K key = current.key;
                current = current.next;
                return key;
            }
        };
    }

    /**
     * Возвращает итерируемый набор всех значений в таблице.
     *
     * @return объект Iterable, содержащий все значения
     */
    @Override
    public Iterable<V> values() {
        return () -> new Iterator<>() {
            private int index = 0;
            private Node<K, V> current = null;

            @Override
            public boolean hasNext() {
                while (current == null && index < table.length) {
                    current = table[index++];
                }
                return current != null;
            }

            @Override
            public V next() {
                if (!hasNext()) throw new NoSuchElementException();
                V value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    /**
     * Возвращает итерируемый набор всех пар ключ-значение в таблице.
     *
     * @return объект Iterable, содержащий все записи (Entry)
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        return () -> new Iterator<>() {
            private int index = 0;
            private Node<K, V> current = null;

            @Override
            public boolean hasNext() {
                while (current == null && index < table.length) {
                    current = table[index++];
                }
                return current != null;
            }

            @Override
            public Entry<K, V> next() {
                if (!hasNext()) throw new NoSuchElementException();
                Node<K, V> entry = current;
                current = current.next;
                return entry;
            }
        };
    }
}
