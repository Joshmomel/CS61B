public class LinkedListDeque<T> {
    private class Node{
        public Node pre;
        public T item;
        public Node next;

        public Node(T i) {
            pre = null;
            item = i;
            next = null;
        }

        public Node(){
            pre = null;
            item = null;
            next = null;
        }
    }

    private Node sentinel;
    private int size;

    /* Creates an empty linked list deque. */
    public LinkedListDeque(){
        size = 0;
        sentinel = new Node();
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
    }

    public void addFirst(T item){
        size ++;
        Node n = new Node(item);
        sentinel.next.pre = n;
        n.next = sentinel.next;
        n.pre = sentinel;
        sentinel.next = n;
    }

    public void addLast(T item){
        size ++;
        Node n = new Node(item);
        n.pre = sentinel.pre;
        n.next = sentinel;
        sentinel.pre.next = n;
        sentinel.pre = n;
    }

    public boolean isEmpty(){
        return sentinel.next == sentinel;
    }

    public int size(){
        return size;
    }


    public void printDeque(){
        Node p = sentinel;
        while(p.next.next != sentinel){
            p = p.next;
            System.out.print(p.item);
            System.out.print(" ");
        }
        System.out.println(p.next.item);
    }

    public T removeFirst(){
        size --;
        if(sentinel.next == sentinel){
            return null;
        }
        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;

        return item;
    }

    public T removeLast(){
        size --;
        if(sentinel.next == sentinel){
            return null;
        }
        T item = sentinel.pre.item;
        sentinel.pre.pre.next = sentinel;
        sentinel.pre = sentinel.pre.pre;

        return item;
    }

    public T get(int index){
        if (index > size){
            return null;
        }
        Node n = sentinel;
        if (n.next == n){
            return null;
        }
        n = n.next;
        for (int i = 0; i < index; i++) {
            n = n.next;
        }

        return n.item;
    }

    public T getRecursive(int index){
        Node p =sentinel;
        if (p.next == p){
            return null;
        }
        return recursiveGet(p, index);
    }

    private T recursiveGet(Node n, int index){
        if (index == 0){
            return n.next.item;
        }
        return recursiveGet(n.next, index - 1);
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> LinkedListDeque = new LinkedListDeque<>();
        LinkedListDeque.addFirst(0);
        LinkedListDeque.addLast(1);
        LinkedListDeque.addLast(2);
        LinkedListDeque.addFirst(3);
        System.out.println( LinkedListDeque.isEmpty());
        LinkedListDeque.removeLast();
        LinkedListDeque.addFirst(6);
        LinkedListDeque.addFirst(7);
        LinkedListDeque.addLast(8);
        LinkedListDeque.removeFirst();
        LinkedListDeque.addLast(10);
        System.out.println(LinkedListDeque.size());

    }

}
