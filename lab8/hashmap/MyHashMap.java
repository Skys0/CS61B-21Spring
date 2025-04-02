package hashmap;

import javax.print.DocFlavor;
import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Skys0
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private double loadFactor;
    private int maxsize;
    private HashSet<K> table;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        size = 0;
        loadFactor = 0.75;
        maxsize = 16;
        buckets = createTable(16);
        table = new HashSet<K>();
    }

    public MyHashMap(int initialSize) {
        loadFactor = 0.75;
        size = 0;
        maxsize = initialSize;
        buckets = createTable(initialSize);
        table = new HashSet<K>();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.loadFactor = maxLoad;
        size = 0;
        maxsize = initialSize;
        buckets = createTable(initialSize);
        table = new HashSet<K>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<Node>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    private int hash(K key) {
        int Hash = key.hashCode();
        Hash ^= (Hash >>> 20) ^ (Hash >>> 12) ^ (Hash >>> 7) ^ (Hash >>> 4);
        return Hash & (maxsize - 1);
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public void clear() {
        for (int i = 0;i < maxsize;i++) {
            if(buckets[i] != null)
                buckets[i].clear();
        }
        table.clear();
        size = 0;
    }


    @Override
    public V get(K key) {
        int h = hash(key);
        if (buckets[h] == null) {
            return null;
        }
        for (Node t : buckets[h]) {
            if (t.key.equals(key)) {
                return t.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return (get(key) != null);
    }


    @Override
    public void put(K key, V value) {
        if ((size / (double) maxsize) >= loadFactor) {
            resize();
        }
        int h = hash(key);
        Node temp = new Node(key, value);
        if (buckets[h] == null) {
            buckets[h] = createBucket();
        }
        if (containsKey(key)) {
            for (Node i : buckets[h]) {
                if(i.key.equals(key)) {
                    buckets[h].remove(i);
                    size -= 1;
                    break;
                }
            }
        }

        buckets[h].add(temp);
        table.add(key);
        size += 1;
    }


    @Override
    public Set<K> keySet() {
        return table;
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("This function not support finish in Lab 8.");
    }


    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("This function not support finish in Lab 8.");
    }


    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K>{
        Iterator<K> temp;
        private MyHashMapIterator() {
            temp = table.iterator();
        }

        @Override
        public boolean hasNext() {
            return temp.hasNext();
        }

        @Override
        public K next() {
            return temp.next();
        }
    }

    private void resize() {
        MyHashMap<K, V> temp = new MyHashMap<K, V>(2 * maxsize);
        for (K key : this) {
            temp.put(key, this.get(key));
        }

        maxsize *= 2;
        this.buckets = temp.buckets;
    }

    public static void main(String[] args) {
        MyHashMap<String, String> a = new MyHashMap<>();
        a.put("hello", "world");
        a.put("hello", "kevin");
    }
}