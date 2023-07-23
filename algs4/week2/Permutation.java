import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

/**
 *  Take an integer k as a command-line argument;
 *  read a sequence of strings from standard input using StdIn.readString();
 *  and prints exactly k of them, uniformly at random.
 *  Print each item from the sequence at most once.
 */
public class Permutation {

    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        while (true) {
            String line = null;
            try {
                line = StdIn.readString();
            } finally {
                if (line == null) {
                    break;
                }
            }
            randomizedQueue.enqueue(line);
        }

        Iterator<String> iterator = randomizedQueue.iterator();

        while (count-- > 0) {
            StdOut.println(iterator.next());
        }
    }
}
