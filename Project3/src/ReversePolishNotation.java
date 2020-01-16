import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class ReversePolishNotation {
    private DynamicStack DS;
    private RedBlackTree t;

    ReversePolishNotation(){
        DS = new DynamicStack();
        t = new RedBlackTree();
    }

    public void add() throws Exception {
        Object n1 = DS.pop();
        Object n2 = DS.pop();

        if(n1 instanceof String){
            n1 = t.showValue((String)n1);
        }
        if(n2 instanceof String){
            n2 = t.showValue((String)n2);
        }

        BigInteger result = new BigInteger(n1.toString()).add(new BigInteger(n2.toString()));
        DS.push(result);
    }

    public void substract() throws Exception {
        Object n1 = DS.pop();
        Object n2 = DS.pop();

        if(n1 instanceof String){
            n1 = t.showValue((String)n1);
        }
        if(n2 instanceof String) {
            n2 = t.showValue((String) n2);
        }

        BigInteger result = new BigInteger(n2.toString()).subtract(new BigInteger(n1.toString()));
        DS.push(result);
    }

    public void multiply() throws Exception {
        Object n1 = DS.pop();
        Object n2 = DS.pop();

        if(n1 instanceof String){
            n1 = t.showValue((String)n1);
        }
        if(n2 instanceof String) {
            n2 = t.showValue((String) n2);
        }

        BigInteger result = new BigInteger(n1.toString()).multiply(new BigInteger(n2.toString()));
        DS.push(result);
    }

    public void divide() throws Exception {
        Object n1 = DS.pop();
        Object n2 = DS.pop();

        if(n1 instanceof String){
            n1 = t.showValue((String)n1);
        }
        if(n2 instanceof String) {
            n2 = t.showValue((String) n2);
        }

        BigInteger result = new BigInteger(n2.toString()).divide(new BigInteger(n1.toString()));
        DS.push(result);
    }

    public void mod() throws Exception {
        Object n1 = DS.pop();
        Object n2 = DS.pop();

        if(n1 instanceof String){
            n1 = t.showValue((String)n1);
        }
        if(n2 instanceof String) {
            n2 = t.showValue((String) n2);
        }

        BigInteger result = new BigInteger(n2.toString()).mod(new BigInteger(n1.toString()));
        DS.push(result);
    }

    public void powerMod() throws Exception {
        Object n1 = DS.pop();
        Object n2 = DS.pop();
        Object n3 = DS.pop();

        if(n1 instanceof String){
            n1 = t.showValue((String)n1);
        }
        if(n2 instanceof String) {
            n2 = t.showValue((String) n2);
        }
        if(n3 instanceof String) {
            n3 = t.showValue((String) n2);
        }

        BigInteger result = new BigInteger(n3.toString()).modPow(new BigInteger(n2.toString()), new BigInteger(n1.toString()));
        DS.push(result);
    }

    public void unaryMinus() throws Exception {
        Object n1 = DS.pop();

        if(n1 instanceof String){
            n1 = t.showValue((String)n1);
        }

        BigInteger result = new BigInteger(n1.toString()).negate();
        DS.push(result);
    }

    public void equal() throws Exception {
        String num = DS.pop().toString();//4
        String var = DS.pop().toString();//x
        boolean check;
        try{
            Integer.parseInt(var);
            check = false;
        }catch (Exception e) {
            check = true;
        }

        if (check){
            t.insert(var, num);
            DS.push(var);
        }
        else{
            throw new Exception("error: " + var + " not an lvalue");
        }
    }

    /**
     * return the value from the top of the stack as result
     * @pre the stack has at least one item
     * @post the number on the top of the stack is returned
     */
    public String getResult() throws Exception {
        Object result = DS.pop();
        if(result instanceof String){
            result = t.showValue((String) result);
        }

        DS.push(result);
        return result.toString();
    }

    /**
     * push a number into the stack
     * @pre none
     * @post the number is pushed into stack
     */
    public void pushNum(BigInteger bigInt) throws Exception {
        DS.push(bigInt);
    }

    /**
     * push a variable into the stack
     * @pre none
     * @post the variable is pushed into stack
     */
    public void pushVar(String item) throws Exception {
        DS.push(item);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        ReversePolishNotation cal = new ReversePolishNotation();
        while(true){
            StringTokenizer st = null;
            try{
                st = new StringTokenizer(input.readLine(), " ");
            }catch(IOException i){
                i.printStackTrace();
                break;
            }

            while(st.hasMoreTokens()) {
                String token = st.nextToken();
                try {
                    BigInteger bigInt = new BigInteger(token);
                    cal.pushNum(bigInt);
                } catch (NumberFormatException n) {
                    if (token.equals("+")) {
                        cal.add();
                    }
                    else if (token.equals("-")) {
                        cal.substract();
                    }
                    else if (token.equals("*")) {
                        cal.multiply();
                    }
                    else if (token.equals("/")) {
                        cal.divide();
                    }
                    else if (token.equals("%")) {
                        cal.mod();
                    }
                    else if (token.equals("^")) {
                        cal.powerMod();
                    }
                    else if (token.equals("~")) {
                        cal.unaryMinus();
                    }
                    else if (token.equals("=")) {
                        cal.equal();
                    }
                    else if (token.equals("<return>")) {
                        System.out.println("terminating");
                        System.exit(0);
                        return;
                    }
                    else {
                        cal.pushVar(token);
                    }
                }
            }
            System.out.println(cal.getResult());
        }
    }
}
