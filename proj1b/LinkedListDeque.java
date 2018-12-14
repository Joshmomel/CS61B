public class LinkedListDeque<T> implements Deque<T>{

    private class Node {
        private Node pre;
        private T item;
        private Node next;

        public Node(T i) {
            pre = null;
            item = i;
            next = null;
        }

        public Node() {
            pre = null;
            item = null;
            next = null;
        }
    }

    private Node sentinel;
    private int size;

    /* Creates an empty linked list deque. */
    public LinkedListDeque() {
        size = 0;
        sentinel = new Node();
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T item) {
        size++;
        Node n = new Node(item);
        sentinel.next.pre = n;
        n.next = sentinel.next;
        n.pre = sentinel;
        sentinel.next = n;
    }

    @Override
    public void addLast(T item) {
        size++;
        Node n = new Node(item);
        n.pre = sentinel.pre;
        n.next = sentinel;
        sentinel.pre.next = n;
        sentinel.pre = n;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public int size() {
        if (size <= 0) {
            return 0;
        }
        return size;
    }

    @Override
    public void printDeque() {
        Node p = sentinel;
        while (p.next.next != sentinel) {
            p = p.next;
            System.out.print(p.item);
            System.out.print(" ");
        }
        System.out.println(p.next.item);
    }

    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        size--;

        return item;
    }

    @Override
    public T removeLast() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T item = sentinel.pre.item;
        sentinel.pre.pre.next = sentinel;
        sentinel.pre = sentinel.pre.pre;
        size--;

        return item;
    }

    @Override
    public T get(int index) {
        if (index > size) {
            return null;
        }
        Node n = sentinel;
        if (n.next == n) {
            return null;
        }
        n = n.next;
        for (int i = 0; i < index; i++) {
            n = n.next;
        }

        return n.item;
    }


    public T getRecursive(int index) {
        Node p = sentinel;
        if (p.next == p) {
            return null;
        }
        return recursiveGet(p, index);
    }

    private T recursiveGet(Node n, int index) {
        if (index == 0) {
            return n.next.item;
        }
        return recursiveGet(n.next, index - 1);
    }


}
