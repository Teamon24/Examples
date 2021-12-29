package collection.comparation;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.TreeSet;

/**
 *  13.12.2016.
 *______________________________________________________________________________________________________________________
 */
public final class LinkedList<T> implements Iterable<T> {
    private int size = 0;

    public class Node implements Serializable {
        public T    value = null;
        public Node prev  = null;
        public Node next  = null;
    }

    private Node head        = new Node();
    private Node cursor      = this.head;
    private int  cursorIndex = -1;

    {
        head.prev = head;
        head.next = head;
    }

    public LinkedList() {}

    public Node getHead() {
        return this.head;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        if (size == 0) return true;
        else return false;
    }

    private Node gotoNumber(int index) throws IndexOutOfBoundsException {
        if ((index >= 0) && (index < size)) {
            if (index < cursorIndex) {
                if (index < cursorIndex - index) {
                    cursor = head;
                    for (int i = -1; i < index; i++)
                        cursor = cursor.next;
                } else {
                    for (int i = cursorIndex; i > index; i--)
                        cursor = cursor.prev;
                }
            } else {
                if (index - cursorIndex < size - index) {
                    for (int i = cursorIndex; i < index; i++)
                        cursor = cursor.next;
                } else {
                    cursor = head;
                    for (int i = size; i > index; i--)
                        cursor = cursor.prev;
                }
            }
            cursorIndex = index;
            return cursor;
        } else
            throw new IndexOutOfBoundsException(index);
    }

    public T get(int index) throws IndexOutOfBoundsException {
        if ((index >= size) || (index < 0)) {
            throw new IndexOutOfBoundsException(index);
        }
        return this.gotoNumber(index).value;
    }

    public T getFirst() throws IndexOutOfBoundsException {
        if (size == 0) throw new IndexOutOfBoundsException(1);
        cursor = head.next;
        return cursor.value;
    }

    public T getLast() throws IndexOutOfBoundsException {
        if (size == 0) throw new IndexOutOfBoundsException(1);
        cursor = head.prev;
        return cursor.value;
    }

    public void setFirst(T value) {
        if (size == 0) {
            this.addFirst(value);
            return;
        }
        head.next.value = value;
    }

    public void setLast(T value) {
        if (size == 0) {
            this.addLast(value);
        }
        head.prev.value = value;
    }

    public T set(int index, T value) throws IndexOutOfBoundsException {
        if ((index >= size) || (index < 0)) {
            try {
                throw new IndexOutOfBoundsException(index);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        this.gotoNumber(index).value = value;
        return value;
    }

    public void add(int i, T value) throws IndexOutOfBoundsException {
        Node newNode = new Node();
        newNode.value = value;
        if (size == 0) {
            newNode.next = head;
            head.prev = newNode;
            head.next = newNode;
            newNode.prev = head;
        } else {
            if (i == 0) {
                addFirst(value);
                return;
            }
            if (i == size) {
                addLast(value);
                return;
            }
            if (i > 0 && i < size) {
                Node temp = gotoNumber(i - 1);
                newNode.prev = temp;
                newNode.next = temp.next;
                temp.next.prev = newNode;
                temp.next = newNode;
            } else
                throw new IndexOutOfBoundsException(i);
        }
        size++;
        cursorIndex = i;
        cursor = newNode;
    }

    public void addFirst(T value) {
        size++;
        Node newFirst = new Node();
        newFirst.value = value;
        switch (size) {
            case 0:
                newFirst.next = head;
                newFirst.prev = head;
                head.prev = newFirst;
                break;
            default:
                newFirst.next = head.next;
                newFirst.prev = head;
                head.next.prev = newFirst;
                break;
        }
        head.next = newFirst;

    }

    public void addLast(T value) {
        size++;
        Node newLast = new Node();
        newLast.value = value;
        switch (size) {
            case 0:
                newLast.next = head;
                newLast.prev = head;
                head.next = newLast;
                break;
            default:
                newLast.next = head;
                newLast.prev = head.prev;
                head.prev.next = newLast;
                break;
        }
        head.prev = newLast;
    }

    public T remove(int index) throws IndexOutOfBoundsException {
        Node temp = gotoNumber(index);
        T value = temp.value;
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        temp.value = null;
        size--;
        cursorIndex = index - 1;
        cursor = temp.prev;
        return value;
    }

    public void removeFirst() throws IndexOutOfBoundsException {
        if (size == 0) throw new IndexOutOfBoundsException(1);
        Node temp = head.next;
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        temp.next = temp;
        temp.prev = temp;
        temp.value = null;
        size--;
    }

    public void removeLast() throws IndexOutOfBoundsException {
        if (size == 0) throw new IndexOutOfBoundsException(1);
        Node temp = head.prev;
        temp.prev.next = head;
        head.prev = temp.prev;
        temp.next = temp;
        temp.prev = temp;
        temp.value = null;
        size--;
    }

    public <T> T[] toArray(T[] a) {
        Node iter = head;
        Object[] objects = new Object[size];
        for (int i = 0; i < size() && iter.next != head; i++, iter = iter.next) {
            objects[i] = iter.next.value;
        }
        a = (T[]) objects;
        return a;
    }

    @Override
    public LinkedListIterator iterator() {
        return new LinkedListIterator(this);
    }

    @Override
    public Object clone() {
        LinkedList result = null;
        try {
            result = (LinkedList) super.clone();
            result.head = new Node();
            result.head.next = result.head;
            result.head.prev = result.head;
            result.size = 0;
            result.cursorIndex = -1;
            result.cursor = result.head;

            for (int i = 0; i < this.size(); i++) {
                result.add(i, this.get(i));
            }
        } catch (CloneNotSupportedException ex) {
            System.out.print("Не удалось создать клон");
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkedList)) return false;

        LinkedList<?> that = (LinkedList<?>) o;

        if (size != that.size) return false;
        if (cursorIndex != that.cursorIndex) return false;
        if (!getHead().equals(that.getHead())) return false;
        return cursor.equals(that.cursor);

    }

    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + getHead().hashCode();
        result = 31 * result + cursor.hashCode();
        result = 31 * result + cursorIndex;
        return result;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static void main(String[] args) {
        int n = 65_000;
        Timer timer   = new Timer(System::currentTimeMillis);
        Random random = new Random();
        int[] indexes = new int[n];

        java.util.LinkedList<Integer> list = new java.util.LinkedList<>();
        LinkedList<Integer> list2          = new LinkedList<>();
        HashSet<Integer> hashSet = new HashSet<>();
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        TreeSet<Integer>  treeSet = new TreeSet<>();

        for (int i = 0; i < n; i++) {
            list.addLast(i);
            list2.addLast(i);
            hashSet.add(i);
            indexes[i] = random.nextInt(n);
        }

        timer.start();
        for (int i = 0; i < n; i++) {
            list.get(indexes[i]);
        }
        timer.finish();

        System.out.printf("java.util.LinkedList " + "#get " + n + ": " + timer.result());

        timer.start();
        for (int i = 0; i < n; i++) {
            try {
                list2.get(indexes[i]);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        timer.finish();

        System.out.printf("ru.goldtaxi.utilities.LinkedList " + "#get " + n + ": " + timer.result());

        timer.start();
        for (int i = 0; i < n; i++) {
            hashSet.contains(indexes[i]);
        }
        timer.finish();

        System.out.printf("java.util.HashSet " + "#contains " + n + ": " + timer.result());

        timer.start();
        for (int i = 0; i < n; i++) {
            linkedHashSet.contains(indexes[i]);
        }
        timer.finish();

        System.out.printf("java.util.LinkedHashSet " + "#contains " + n + ": " + timer.result());

        timer.start();
        for (int i = 0; i < n; i++) {
            treeSet.contains(indexes[i]);
        }
        timer.finish();

        System.out.printf("java.util.TreeSet " + "#contains " + n + ": " + timer.result());
    }
}

