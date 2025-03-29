package bstmap;

import java.awt.*;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node (K key,V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public BSTMap () {
        clear();
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int size () {
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        return check(root, key);
    }

    private boolean check(Node T,K key) {
        if (T == null) {
            return false;
        }

        int cmp = key.compareTo(T.key);
        if (cmp < 0) {
            return check(T.left, key);
        } else if (cmp > 0) {
            return check(T.right, key);
        } else {
            return true;
        }
    }

    @Override
    public void put(K key, V value) {
        root = Insert(root, key, value);
        this.size += 1;
    }

    private Node Insert(Node T, K key, V value) {
        if (T == null) {
            return new Node(key,value);
        }

        int cmp = key.compareTo(T.key);
        if (cmp < 0) {
            T.left = Insert(T.left, key, value);
        } else if (cmp > 0) {
            T.right = Insert(T.right, key, value);
        }
        return T;
    }

    @Override
    public V get(K key) {
        return getValue(root, key);
    }

    private V getValue(Node T, K key) {
        if (T == null) {
            return null;
        }

        int cmp = key.compareTo(T.key);

        if (cmp < 0) {
            return getValue(T.left, key);
        } else if (cmp > 0) {
            return getValue(T.right, key);
        } else {
            return T.value;
        }
    }

    public void printInOrder() {
        Print(root);
    }

    private void Print(Node T) {
        if(T == null){
            return;
        }

        Print(T.left);
        System.out.print(T.key + " ");
        Print(T.right);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("This function not required for Lab 7.");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("This function not required for Lab 7.");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("This function not required for Lab 7.");
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("This function not required for Lab 7.");
    }
}
