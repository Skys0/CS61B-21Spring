package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int first;
    private int last;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        first = 0;
        last = 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int temp = 0;
        for (int i = 0; i < size; i++) {
            a[temp] = items[(first + i) % items.length];
            temp += 1;
        }
        items = a;
        first = 0;
        if (isEmpty()) {
            last = 0;
        } else {
            last = temp - 1;
        }
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if(!isEmpty())
            first = (first - 1 + items.length) % items.length;
        items[first] = item;
        size ++;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[size] = item;
        last = (last + 1) % items.length;
        size += 1;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for(int i = 0;i < size;i ++) {
            System.out.print(items[(first + i) % items.length]);
            System.out.print(" ");
        }
        System.out.print("\n");
    }

    public T removeFirst() {
        if (isEmpty())      return null;

        if (items.length >= 16 && size < items.length * 0.25) {
            resize(items.length / 2);
        }

        T item = items[first];
        items[first] = null;
        first = (first + 1) % items.length;
        size -= 1;
        return item;
    }

    public T removeLast() {
        if (isEmpty())      return null;

        if (items.length >= 16 && size < items.length * 0.25) {
            resize(items.length / 2);
        }

        last = (last - 1 + items.length) % items.length;
        T item = items[last];
        items[last] = null;
        size -= 1;
        return item;
    }

    public T get(int index) {
        if(index < 0 || index >= size) {
            return null;
        }

        return items[(first + index) % items.length];
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
        s.isEmpty();
        s.printDeque();
        for (String item : s) {
            System.out.print(item);
        }
    }
}
