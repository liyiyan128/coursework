import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // head <- ... <- tail
    private Node head;
    private Node tail;
    private int size = 0;
    
    private class Node {
        Item item;
        Node prev;
        Node next;
    }
    
    // construct an empty deque
    public Deque() { }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
    
        Node node = new Node();
        node.item = item;

        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.prev = head;
            head.next = node;
            head = node;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }

        Node node = new Node();
        node.item = item;

        if (tail == null) {
            tail = node;
            head = node;
        } else {
            node.next = tail;
            tail.prev = node;
            tail = node;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) { throw new NoSuchElementException(); }
    
        Item item = head.item;

        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.prev;
            head.next = null;
        
            if (size == 2) {
                tail = head;
            }
        
        }
        
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) { throw new NoSuchElementException(); }

        Item item = tail.item;

        if (size == 1) {
            tail = null;
            head = null;
        } else {
            tail = tail.next;
            tail.prev = null;

            if (size == 2) {
                head = tail;
            }
        }

        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }

            Item item = current.item;
            current = current.prev;  // update current node
            return item;
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        String sf = String.format("deque is empty: %b", deque.isEmpty());
        System.out.println(sf);

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.removeFirst();
        deque.removeLast();
        StringBuffer stringBuffer = new StringBuffer();
        for (int s : deque) {
            stringBuffer.append(s).append(", ");
        }
        sf = String.format("Deque [%s] of size %d", stringBuffer, deque.size());
        System.out.println(sf);
    }

}