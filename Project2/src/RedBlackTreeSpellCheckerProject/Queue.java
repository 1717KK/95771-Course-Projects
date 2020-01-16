package RedBlackTreeSpellCheckerProject;

public class Queue {
    private int rear; //end
    private Object [] q;
    private int front; //start
    private int manyItems; //the number of items
    private int size; // the size of queue

    public Queue(){
        rear = front = 0;
        q = new Object[5]; //the queue class will begin with an array of size 5
        manyItems = 0;
        size = q.length;
    }

    /**
     * @pre none
     * @post remove and return reference in front of queue.
     */
    public Object deQueue(){
           Object item = q[front];
           q[front] = null;
           front = (front + 1) % size;
           manyItems--;
           return item;
    }

    /**
     * @pre none
     * @post add an object reference to the rear of the queue.
     */
    public void enQueue(Object x){

        if (isEmpty()){
            rear = front = 0;
        }

        else if (isFull()){
            front = size;
            doublesize(2 * size);
            rear = (rear + 1) % size;
        }
        else {
            rear = (rear + 1) % size;
        }
        q[rear] = x;
        manyItems++;

    }

    /**
     * @pre the queue is not emply
     * @post return a double-size queue.
     */
    public void doublesize(int newSize){
        Object [] newQ = new Object[newSize];
        int oldSize = size;
        for(int i = 0; i < oldSize; i++){
            newQ[i] = q[i];
        }
        front = 0;
        size = newSize;
        q = newQ;
    }

    /**
     * @pre the queue is not emply
     * @post return the front of the queue without removing it.
     */
    public Object getFront(){
        return q[front];
    }

    /**
     * @pre none
     * @post return true on empty queue, false otherwise.
     */
    public boolean isEmpty(){
        return manyItems == 0;
    }

    /**
     * @pre none
     * @post return true if queue is currently at capacity, false otherwise.
     */
    public boolean isFull(){
        return manyItems == size;
    }


    /**
     * @pre none
     * @post return a String representation of the current queue contents.
     */
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i = front;i <= rear;i++){
            str.append(q[i]);
        }
        return str.toString();
    }

    //main method is to test the queue routines.
    public static void main(String[] a){
        Queue Q = new Queue();
        for(char item = 'A'; item <= 'Z'; item ++){
            Q.enQueue(item);
        }

        System.out.println(Q.getFront());

        System.out.println("dequeue item: " + Q.deQueue());

        System.out.println("dequeue item: " + Q.deQueue());

        System.out.println("the front of the dequeued queue: " + Q.getFront());

        System.out.println(Q.toString());
    }
}
