package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private ArbitNode sentinel;
    private int size;

    private class ArbitNode {
        private ArbitNode prev;
        private T item;
        private ArbitNode next;

        private ArbitNode(T i, ArbitNode n) {
            item = i;
            next = n;
        }
    }


    // Create a List with empty.
    public LinkedListDeque() {
        sentinel = new ArbitNode(null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

        size = 0;
    }

    // Add an item to the front of the deque.
    public void addFirst(T item)  {
        ArbitNode p = sentinel;
        ArbitNode temp = new ArbitNode(item, null);

        p.next.prev = temp;
        temp.next = p.next;
        p.next = temp;
        temp.prev = p;
        size += 1;
    }

    // Add an item to the bottom of the deque.
    public void addLast(T item) {
        ArbitNode p = sentinel;
        ArbitNode temp = new ArbitNode(item, null);

        p.prev.next = temp;
        temp.prev = p.prev;
        p.prev = temp;
        temp.next = p;
        size += 1;
    }

    // Get the size of List
    public int size() {
        return size;
    }

    // Get the `index` position of the List.
    public T get(int index) {
        ArbitNode p = sentinel;
        for (int i = 0; i <= index; i++) {
            p = p.next;
        }

        return p.item;
    }

    // Print all of a List.
    public void printDeque() {
        ArbitNode p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
        System.out.print("\n");
    }

    // Remove the list of the first.
    public T removeFirst() {
        ArbitNode p = sentinel.next;
        if (p == sentinel) {
            return null;
        }

        T temp = p.item;
        p.prev.next = p.next;
        p.next.prev = p.prev;
        size -= 1;
        return temp;
    }

    // Remove the list of the Last.
    public T removeLast() {
        ArbitNode p = sentinel.prev;
        if (p == sentinel) {
            return null;
        }

        T temp = p.item;

        p.prev.next = p.next;
        p.next.prev = p.prev;
        size -= 1;
        return temp;
    }

    // Use recursion to get member.
    public T getRecursive(int index) {
        return get1(sentinel.next, index);
    }

    private T get1(ArbitNode node, int index) {
        if (index == 0) {
            return node.item;
        }
        return get1(node.next, index - 1);
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private int wizPos;

        public LinkedListIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> t = (Deque<T>) o;
        if (t.size() != size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!(t.get(i).equals(this.get(i)))) {
                return false;
            }
        }
        return true;
    }


//    public static void main(String[] args) {
//        LinkedListDeque<Integer> a = new LinkedListDeque<>();
//        a.addFirst(1);
//        a.addFirst(2);
//        a.addFirst(3);
//        for (int i : a) {
//            System.out.print(i);
//        }
//    }
}
