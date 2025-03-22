package deque;

import java.util.Deque;
import java.util.Iterator;

public class LinkedListDeque<Generics> implements Iterable<Generics> {
    private ArbitNode sentinel;
    private int size;

    public class ArbitNode {
        public ArbitNode prev;
        public Generics item;
        public ArbitNode next;

        public ArbitNode(Generics i, ArbitNode n) {
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

    // Create a List with Copy other.
    public LinkedListDeque(LinkedListDeque<Generics> other) {
        sentinel = new ArbitNode(null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

        for(int i = 0;i < other.size();i ++) {
            Generics t = other.get(i);
            this.addLast(t);
        }
    }

    // Add an item to the front of the deque.
    public void addFirst(Generics item)  {
        ArbitNode p = sentinel;
        ArbitNode temp = new ArbitNode(item, null);

        p.next.prev = temp;
        temp.next = p.next;
        p.next = temp;
        temp.prev = p;
        size += 1;
    }

    // Add an item to the bottom of the deque.
    public void addLast(Generics item) {
        ArbitNode p = sentinel;
        ArbitNode temp = new ArbitNode(item, null);

        p.prev.next = temp;
        temp.prev = p.prev;
        p.prev = temp;
        temp.next = p;
        size += 1;
    }

    // Check the List is Empty.
    public boolean isEmpty() {
        ArbitNode p = sentinel;
        return p.next == p && p.prev == p;
    }

    // Get the size of List
    public int size() {
        return size;
    }

    // Get the `index` position of the List.
    public Generics get(int index) {
        ArbitNode p = sentinel;
        for (int i = 0;i <= index;i ++)
            p = p.next;

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
    public Generics removeFirst() {
        ArbitNode p = sentinel.next;
        if (p == sentinel)      return null;

        Generics temp = p.item;
        p.prev.next = p.next;
        p.next.prev = p.prev;
        size -= 1;
        return temp;
    }

    // Remove the list of the Last.
    public Generics removeLast() {
        ArbitNode p = sentinel.prev;
        if(p == sentinel)   return null;

        Generics temp = p.item;

        p.prev.next = p.next;
        p.next.prev = p.prev;
        size -= 1;
        return temp;
    }

    // Use recursion to get member.
    public Generics getRecursive(int index) {
        return get1(sentinel.next, index);
    }

    private Generics get1(ArbitNode node, int index) {
        if (index == 0) {
            return node.item;
        }
        return get1(node.next, index - 1);
    }

    @Override
    public Iterator<Generics> iterator(){
        return new LLDIterator();
    }

    private class LLDIterator implements Iterator<Generics>{
        private int currentIndex = 0;
        @Override
        public boolean hasNext(){
            return  currentIndex<size && get(currentIndex) !=null;
        }

        @Override
        public Generics next(){
            Generics temp = get(currentIndex);
            currentIndex = currentIndex + 1;
            return temp;
        }

    }

    @Override
//    public boolean equals(Object o) {
//        if (o == null) {
//            return false;
//        }
//        if (this == o) {
//            return true;
//        }
//
//        if (!(o instanceof Deque)) {
//            return  false;
//        }
//        Deque<Generics> other = (Deque<Generics>) o;
//        if (this.size() != other.size()) {
//            return false;
//        }
//
//        for (int i = 0; i < size(); i++) {
//            if (!other.get(i).equals(this.get(i))) {//这里记得考虑嵌套数组的情况不能使用==来判定相等
//                return false;
//            }
//        }
//        return true;
//    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> a = new LinkedListDeque<>();
    }
}