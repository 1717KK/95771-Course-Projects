package edu.cmu.andrew.yiqizhou;

public class SinglyLinkedList {
    private ObjectNode head;
    private ObjectNode tail;
    private ObjectNode current;
    private int countNodes;

    public SinglyLinkedList(ObjectNode initialData){
        head = initialData;
        tail = initialData;
        current = head;
        countNodes = 1; //the list already has a default start data 'A'
    }

    //Add a node containing the Object c to the end of the linked list.
    public void addAtEndNode(Object c) throws Exception {

        if (tail == null){
            throw new Exception("This list does not have a tail!");
        }

        //update the tail to the last node of the list
        for(ObjectNode node = head; node != null; node = node.getLink()){
            tail = node;
        }

        //update the link of new node
        tail.setLink(new ObjectNode(c, null));

        countNodes++;
    }

    //Add a node containing the Object c to the head of the linked list.
    public void addAtFrontNode(Object c) throws Exception {

        if (head == null){
            throw new Exception("This list does not have a head!");
        }

        head = new ObjectNode(c, head);
        countNodes++;
    }

    public ObjectNode getHead(){
        return head;
    }

    public ObjectNode getTail(){
        return tail;
    }

    public ObjectNode getCurrent(){
        return current;
    }

    //Counts the number of nodes in the list
    public int countNodes(){
        return countNodes;
    }

    //Returns the data in the tail of the list
    public Object getLast(){
        return tail.getLink().getData();
    }

    //Returns a reference (0 based) to the object with list index i.
    public Object getObjectAt(int i) throws Exception {
        if( i + 1 > countNodes){
            throw new Exception("The index is out of range!");
        }
        ObjectNode target;
        target = head;
        for(int idx = 0; idx < i ; idx++){
            target = target.getLink();
        }
        return target.getData();
    }

    //check if the current node has next node
    public boolean hasNext(){
        if (current.getLink() == null)
            return false;
        return true;
    }

    //return the Object pointed to by the iterator and increment the iterator to the next node in the list.
    public Object next(){
        current = current.getLink();
        return this.current.getData().toString();
    }

    //reset the iterator to the beginning of the list That is, set a reference to the head of the list.
    public void reset(){
        current = head;
    }

    //Returns the list as a String
    public String toString(){
        if (head == null){
            return null;}
        else {
            StringBuilder stringView = new StringBuilder();
            for(ObjectNode node = head; node != null; node = node.getLink()){
                stringView.append(node.toString(node));
            }
            return stringView.toString();
        }
    }

    public static void main(String[] args) throws Exception {
        SinglyLinkedList s = new SinglyLinkedList(new ObjectNode('A', null));
        for(char alphabet = 'B'; alphabet <= 'Z'; alphabet++){
            s.addAtEndNode(alphabet);
        }

        s.addAtFrontNode('a');

        //test all methods in this class
        System.out.println("The List is " + s.toString());
        System.out.println("The number of nodes in list is " + s.countNodes);
        System.out.println("The last node in the list is " + s.getLast().toString());
        System.out.println("The third node in the list is " + s.getObjectAt(2));

        s.reset();
        while(s.hasNext()){
            System.out.println(s.next());
        }
    }
}
