
package collection.comparation;


import java.util.ConcurrentModificationException;
import java.util.Iterator;


public final class LinkedListIterator implements Iterator {

    private LinkedList.Node cursor;
    private LinkedList.Node head;
    private LinkedList linkedList;
    private int size;

    public LinkedListIterator(LinkedList linkedList) {
        this.linkedList = linkedList;
        this.head = this.linkedList.getHead();
        this.cursor = this.head;
        this.size = this.linkedList.size();
    }

    @Override
    public void remove() throws ConcurrentModificationException {
        if (this.size != this.linkedList.size()) throw new ConcurrentModificationException("Modification in the loop");
        LinkedList.Node temp = this.cursor.next;
        temp.value = null;
        this.cursor.next.prev = this.cursor.prev;
        this.cursor.prev.next = this.cursor.next;
        this.cursor.next = this.cursor;
        this.cursor.prev = this.cursor;
        this.cursor = temp.prev;
        this.linkedList.setSize(--this.size);
    }

    @Override
    public boolean hasNext() throws ConcurrentModificationException {
        if (this.size != this.linkedList.size()) throw new ConcurrentModificationException("Modification in the loop");
        return this.cursor.next != this.head;
    }

    @Override
    public Object next() throws ConcurrentModificationException {
        if (this.size != this.linkedList.size()) throw new ConcurrentModificationException("Modification in the loop");
        this.cursor = this.cursor.next;
        return this.cursor.value;
    }
}