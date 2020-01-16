import java.util.Iterator;

public class SinglyLinkedList<E> implements Iterable<E> {
    private Node head;
    private Node tail;
    private int size;

    public class Node{
        public E data;
        public Node next;

        public Node(E data, Node next){
            this.data = data;
            this.next = next;
        }
    }

    public SinglyLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Add an item to the end of the linked list.
     *
     * @param item is an element to be added
     */
    public void add_at_ent(E item){
        if (head == null){
            head = new Node(item, null);
            tail = head;
        }
        else{
            tail.next = new Node(item, null);
            tail = tail.next;
        }
        this.size++;
    }


    public E get_first(){
        Node first = head;
        head = head.next;
        if(head == null){
            tail = null;
        }
        this.size--;
        return first.data;
    }


    /**
     * Returns true if the list is empty false otherwise.
     *
     * @return true if the list empty false otherwise
     */
    public boolean isEmpty(){
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                Node result = current;
                current = current.next;
                return result.data;
            }
        };
    }

}
