import java.math.BigInteger;

public class DynamicStack {
    private int top;
    private Object[] stack;
    private int manyItems;
    private int size;

    DynamicStack(){
        top = -1;
        stack = new Object[6];
        manyItems = 0;
        size = stack.length;
    }

    /**
     * @best: Big_Theta(1)
     * @worst: Big_Theta(n)
     */
    public void push(Object n) {
        if (isFull()){
            doubleSize(2*size);
        }
        top++;
        stack[top] = n;
        manyItems++;
    }

    /**
     * @best: Big_Theta(1)
     * @worst: Big_Theta(1)
     */
    public Object pop() throws Exception {
        if(isEmpty()){
            throw new Exception("error: stack underflow exception");
        }
        Object item = stack[top];
        top--;
        manyItems--;
        return item;
    }

    /**
     * @best: Big_Theta(n)
     * @worst: Big_Theta(n)
     */
    public void doubleSize(int newSize){
        Object[] newStack = new Object[newSize];
        int oldSize = size;
        for(int i = 0; i < oldSize; i++){
            newStack[i] = stack[i];
        }
        size = newSize;
        stack = newStack;
    }

    /**
     * @best: Big_Theta(1)
     * @worst: Big_Theta(1)
     */
    public boolean isFull(){
        return size == manyItems;
    }

    /**
     * @best: Big_Theta(1)
     * @worst: Big_Theta(1)
     */
    public boolean isEmpty(){
        if(top == -1){
            return true;
        }
        return false;
    }

    /**
     * @best: Big_Theta(n)
     * @worst: Big_Theta(n)
     */
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i = 0;i <= top;i++){
            str.append(stack[i]);
        }
        return str.toString();
    }

    public static void main(String[] a) throws Exception {
        DynamicStack s = new DynamicStack();
        for (int i = 1; i <= 1000; i++) {
            String num = Integer.toString(i);
            BigInteger number = new BigInteger(num);
            s.push(number.toString());
        }

        System.out.println("stack: " + s.toString());

        for (int i=0;i<1000;i++){
            Object item = s.pop();
            System.out.println("Deleted: " + item.toString());
        }

        System.out.println("Empty?: " + s.isEmpty());

        s.pop();

    }
}

