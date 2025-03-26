package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> cmp;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        cmp = c;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }
        T maxium = this.get(0);
        for (T i : this) {
            if (cmp.compare(i,maxium) > 0) {
                maxium = i;
            }
        }
        return maxium;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }

        T maxium = this.get(0);
        for(T i : this) {
            if(c.compare(i, maxium) > 0) {
                maxium = i;
            }
        }
        return maxium;
    }

    public static void main(String[] args) {
    }
}
