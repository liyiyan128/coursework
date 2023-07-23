import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {    // conss2truct an empty randomized queue
    private Item[] storage;
    private int size = 0;
    
    public RandomizedQueue() {
        storage = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        
        storage[size] = item;
        size++;
        resize();
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) { throw new NoSuchElementException(); }

        int index = StdRandom.uniform(size);
        Item item = storage[index];
        // remove the last element, overwrite the element at index
        // and then decrement size
        storage[index] = storage[--size];
        resize();
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) { throw new NoSuchElementException(); }

        return storage[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] indices = new int[size];
        private int currentIndex = 0;

        public RandomizedQueueIterator() {
            // initialise indices
            for (int i = 0; i < size; i++) {
                indices[i] = i;
            }
            // randomize indices
            StdRandom.shuffle(indices);
        }

        public boolean hasNext() {
            return currentIndex < size;
        }
        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            return storage[indices[currentIndex++]];
        }

        public void remove() { throw new UnsupportedOperationException(); }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        String sf = String.format("Queue is empty: %b", randomizedQueue.isEmpty());
        System.out.println(sf);

        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.dequeue();
        System.out.println("randomizedQueue size: " + randomizedQueue.size());
        
        // random sample
        sf = String.format("Random sample: %d", randomizedQueue.sample());

        System.out.println("Loop over ramdomizedQueue:");
        for (int i : randomizedQueue) {
            System.out.print(i +  ", ");
        }
    }

    private void resize() {
        // double the size when full
        if (size == storage.length) {
            Item[] newStorage = (Item[]) new Object[storage.length * 2];
            // copy
            for (int i = 0; i < size; i++) {
                newStorage[i] = storage[i];
            }
            storage = newStorage;  // update storage
        } else if (size <= storage.length / 4) {  // halve the size if less than 1 / 4
            Item[] newStorage = (Item[]) new Object[storage.length / 2];
            // copy
            for (int i = 0; i < size; i++) {
                newStorage[i] = storage[i];
            }
            storage = newStorage;  // update storage
        }
    }

}