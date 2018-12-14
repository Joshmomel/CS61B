
public class ArrayDeque<T> implements Deque<T>{
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    private static final int INITIAL_CAPACITY = 8;
    private static final int RFACTOR = 2;

    public ArrayDeque() {
        items = (T[]) new Object[INITIAL_CAPACITY];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    private void calculateFront() {
        nextFirst = (nextFirst + items.length - 1) % (items.length);
    }

    private void calculateLast() {
        nextLast = (nextLast + 1) % items.length;
    }

    private int respect(int index, int length) {
        return index % length;
    }

    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    private int plusOne(int index, int length) {
        return (index + 1) % length;
    }


    private int minusOne(int index) {
        return (index + items.length - 1) % items.length;
    }

    private int minusOne(int index, int length) {
        return (index + length - 1) % length;
    }

    private boolean isFull() {
        return items.length == size;
    }

    private void extend() {
        reSize(size, RFACTOR * size);
    }

    private void shrink() {
        reSize(items.length, items.length / RFACTOR);
    }

    private boolean checkLarger(int f, int l) {
        return f > l;
    }

    private void reSize(int inputSize, int capacity) {
        T[] a = (T[]) new Object[capacity];

        if (capacity < inputSize) {
            int start = Math.min(nextFirst, nextLast);
            int first = nextFirst;
            int p = respect(start, capacity);
            if (!checkLarger(nextFirst, nextLast)) {
                for (int i = 0; i < capacity; i++) {
                    a[i] = items[start];
                    start = plusOne(start);
                }
                nextFirst = 0;
                nextLast = capacity / 2;
            } else {
                for (int i = 0; i < capacity; i++) {
                    start = minusOne(start, items.length);
                    p = minusOne(p, capacity);
                    a[p] = items[start];

                }
                nextFirst = respect(first, capacity);
                System.out.println();
            }
        } else {
            if (nextFirst == items.length - 1) {
                int temp = minusOne(nextLast);
                System.arraycopy(items, 0, a, 0, nextFirst + 1);
                nextFirst = a.length - 1;
                nextLast = temp + 1;
            } else {
                System.arraycopy(items, 0, a, 0, nextLast);
                System.arraycopy(items, nextLast, a, size + nextFirst + 1, size - nextLast);
                nextFirst = size + nextFirst;
            }
        }
        items = a;
        System.out.println();

    }

    @Override
    public void addFirst(T item) {
        if (isFull()) {
            extend();
        }

        items[nextFirst] = item;
        calculateFront();
        size++;
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            extend();
        }
        items[nextLast] = item;
        calculateLast();
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private boolean isSparse() {
        return items.length >= 16 && size() < items.length / 4;
    }

    @Override
    public T removeFirst() {
        if (isSparse()) {
            shrink();
        }
        nextFirst = plusOne(nextFirst);
        T returnItem = items[nextFirst];
        items[nextFirst] = null;
        size--;
        if (size < 0) {
            size = 0;
        }
        return returnItem;
    }

    @Override
    public T removeLast() {
        if (isSparse()) {
            shrink();
        }
        nextLast = minusOne(nextLast);
        T returnItem = items[nextLast];
        items[nextLast] = null;
        size--;
        if (size < 0) {
            size = 0;
        }
        return returnItem;
    }


    @Override
    public void printDeque() {
        for (int i = 0; i < items.length; i++) {
            T item = get(i);
            if (item == null) {
                System.out.println();
                return;
            }
            System.out.print(item);
            System.out.print(" ");
        }
    }

    @Override
    public T get(int index) {
        return items[(nextFirst + 1 + index) % items.length];
    }


}
