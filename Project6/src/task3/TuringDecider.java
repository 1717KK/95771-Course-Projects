package task3;

import java.util.Scanner;

public class TuringDecider {

    public static void main(String[] args) {
        Turing machine1 = new Turing(5);  // A five state machine

        State s0 = new State(0);
        State s1 = new State(1);
        State s2 = new State(2);
        State s3 = new State(3);

        s0.addTransition(new Transition('0', 'X', Transition.RIGHT, 1));
        s0.addTransition(new Transition('Y', 'Y', Transition.RIGHT, 4));

        s1.addTransition(new Transition('0', '0', Transition.RIGHT, 1));
        s1.addTransition(new Transition('1', '1', Transition.RIGHT, 1));
        s1.addTransition(new Transition('Y', 'Y', Transition.LEFT, 2));
        s1.addTransition(new Transition('B', 'B', Transition.LEFT, 2));

        s2.addTransition(new Transition('1', 'Y', Transition.LEFT, 3));

        s3.addTransition(new Transition('0', '0', Transition.LEFT, 3));
        s3.addTransition(new Transition('1', '1', Transition.LEFT, 3));
        s3.addTransition(new Transition('X', 'X', Transition.RIGHT, 0));

        machine1.addState(s0);                 // Add the state to the machine
        machine1.addState(s1);
        machine1.addState(s2);
        machine1.addState(s3);

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the language L here: ");
        String inTape = scan.next();

        try{
            String outTape = machine1.execute(inTape);  // Execute the machine
            System.out.println(outTape);
        } catch (Exception e) {
            StringBuilder outTape = new StringBuilder();
            outTape.append('0');
            int machine_size = 100;
            for(int i = 1;i<machine_size;i++){
                outTape.append('B');
            }
            System.out.println(outTape.toString());
        }

        //System.out.println(outTape);  // Show the machine’s output
    }
}
