import jdk.swing.interop.SwingInterOpUtils;

/**
 * A singly-linked list implementation of the SimpleList interface
 * Now with tail pointer
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012,
 * based on a more feature-ful version by Tom Cormen and Scot Drysdale
 * @author CBK, Spring 2016, cleaned up inner class, extended testing
 */
public class SinglyLinkedHT<T> {
    private Element head;    // front of the linked list
    private Element tail;    // end
    private int size;        // # elements in the list

    /**
     * The linked elements in the list: each has a piece of data and a next pointer
     */
    private class Element {
        private T data;
        private Element next;

        private Element(T data, Element next) {
            this.data = data;
            this.next = next;
        }
    }

    public SinglyLinkedHT() {
        // TODO: this is just copied from SinglyLinked; modify as needed
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    /**
     * Helper function, advancing to the nth Element in the list and returning it
     * (exception if not that many elements)
     */
    private Element advance(int n) throws Exception {
        Element e = head;
        while (n > 0) {
            // Just follow the next pointers
            e = e.next;
            if (e == null) throw new Exception("invalid index");
            n--;
        }
        return e;
    }

    public void add(int idx, T item) throws Exception {

        if (idx == 0) {

            // Insert at head
            head = new Element(item, head);
            if (size==idx){
                tail = head;
            }
        }
        else if (idx ==size){
            Element e = advance(idx - 1);
            tail = new Element(item,null);
            e.next = tail;
        }
        else {
            // It's the next thing after element # idx-1
            Element e = advance(idx - 1);
            // Splice it in
            e.next = new Element(item, e.next);
        }

        size++;
    }

    public void remove(int idx) throws Exception {
        // TODO: this is just copied from SinglyLinked; modify as needed
        if (idx == 0) {
            // Just pop off the head
            if (head == null) throw new Exception("invalid index");
            head = head.next;
        } else {
            // It's the next thing after element # idx-1
            Element e = advance(idx - 1);
            if (e.next == null) throw new Exception("invalid index");
            // Splice it out
            e.next = e.next.next;
        }
        size--;
    }

    public T get(int idx) throws Exception {
        Element e = advance(idx);
        return e.data;
    }
    public void set(int idx, T item) throws Exception {
        Element e = advance(idx);
        e.data = item;
    }
    /**
     * Appends other to the end of this in constant time, by manipulating head/tail pointers
     */
    public void append(SinglyLinkedHT<T> list2) {
        // takes 2 linked lists and adds them to each other
        //Be sure to update list size and handle cases where either or both lists are empty.
        //this is if both lists exist
        if (head != null) {
            if (tail!= null) {//'head' and 'tail' by themselves imply list1
            if (list2.head != null) {
                    if (list2.tail != null) {
                        tail.next = list2.head;
                    }
                }
            }
        }
        //this is if only list 1 exists
        else if (list2.head==null){
            if (list2.tail==null){
                if (head!=null){
                        //do nothing here
                }
            }
        }
        //this is if list1 is empty but list 2 is not
        else{
            head = list2.head;
            tail = list2.tail;
        }
    }

    public String toString() {
        String result = "";
        for (Element x = head; x != null; x = x.next)
            result += x.data + "->";
        result += "[/]";

        return result;
    }


    //now let's get this f***ing party started


    public static void main(String[] args) throws Exception {
        SinglyLinkedHT<String> list1 = new SinglyLinkedHT<String>();
        SinglyLinkedHT<String> list2 = new SinglyLinkedHT<String>();

        //populate list1
        list1.add(0, "a");
        System.out.println(list1);
        list1.add(1, "b");
        list1.add(2, "c");
        System.out.println(list1);

        //populate list2
        list2.add(0, "z");
        list2.add(1, "y");
        list2.add(2, "x");
        System.out.println(list2);

        System.out.println(list1 + " + " + list2);
        list1.append(list2);
        System.out.println(" = " + list1);

    }
}