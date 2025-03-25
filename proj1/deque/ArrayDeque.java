package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        T[] a = (T[]) new Object[items.length];
        a[0] = item;
        System.arraycopy(items, 0, a, 1, size);
        items = a;
        size += 1;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[size] = item;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for(int i = 0;i < size;i ++) {
            System.out.print(items[i]);
            System.out.print(" ");
        }
        System.out.print("\n");
    }

    public T removeFirst() {
        if (isEmpty())      return null;

        if ((size < items.length / 4) && (size > 8)) {
            resize(items.length / 2);
        }

        T item = items[0];
        System.arraycopy(items, 1, items, 0,size - 1);
        size -= 1;
        return item;
    }

    public T removeLast() {
        if (isEmpty())      return null;

        if ((size < items.length / 4) && (size > 8)) {
            resize(items.length / 2);
        }

        T item = items[size - 1];

        items[size - 1] = null;
        size -= 1;
        return item;
    }

    public T get(int index) {
        if(index >= size) {
            return null;
        }

        return items[index];
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;

        public ArrayDequeIterator() {
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
        if(t.size() != size) {
            return false;
        }

        for (int i = 0;i < size;i ++) {
            if (! t.get(i).equals(this.get(i)))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        ArrayDeque<String> s = new ArrayDeque<>();
        s.addLast("333");
        s.addLast("444");
        s.addFirst("222");
        s.addFirst("111");
        s.removeFirst();
        for (String item : s) {
            System.out.print(item);
        }
    }
}
