package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList<Integer> N = new AList<>();
        AList<Double> time = new AList<>();
        AList<Integer> op = new AList<>();

        int x = 1000;
        for(int i = 0;i < 8;i ++) {
            N.addLast(x);
            op.addLast(x);
            x = x * 2;

            Stopwatch sw = new Stopwatch();
            AList<Integer> temp = new AList<>();
            for(int j = 0;j < x;j ++) {
                temp.addLast(j);
            }
            double timeInSeconds = sw.elapsedTime();

            time.addLast(timeInSeconds);
        }

        printTimingTable(N, time, op);
    }
}
