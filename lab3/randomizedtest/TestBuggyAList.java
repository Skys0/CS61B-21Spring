package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        correct.addLast(5);
        correct.addLast(10);
        correct.addLast(15);

        broken.addLast(5);
        broken.addLast(10);
        broken.addLast(15);

        assertEquals(correct.size(), broken.size());

        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                broken.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size1 = correct.size();
                int size2 = broken.size();
                System.out.println("size1: " + size1);
                System.out.println("size2: " + size2);
            } else if (operationNumber == 2) {
                if (correct.size() > 0) {
                    int randVal1 = correct.getLast();
                    int randVal2 = broken.getLast();
                    System.out.println("getLast(" + randVal1 + ")");
                    System.out.println("getLast(" + randVal2 + ")");
                }
            } else if (operationNumber == 3) {
                if (correct.size() > 0) {
                    int randVal1 = correct.removeLast();
                    int randVal2 = broken.removeLast();
                    System.out.println("removeLast(" + randVal1 + ")");
                    System.out.println("removeLast(" + randVal2 + ")");
                }
            }
        }
    }
}
