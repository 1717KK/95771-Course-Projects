package task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Turing {
    private char[] tape;
    private ArrayList<State> state_lst;
    private int idx_halt_state;

    public Turing(int n){
        this.tape = new char[100];
        for (int i = 0;i<100;i++){
            tape[i] = 'B';
        }
        this.state_lst = new ArrayList<>();
        this.idx_halt_state = n-1;

    }

    public void addState(State s){
        this.state_lst.add(s);
    }

    public void initial_tape(String str){
        for (int i = 0;i<str.length();i++){
            this.tape[i] = str.charAt(i);
            if ( str.charAt(i) == ' '){
                this.tape[i] = 'B';
            }
        }
    }

    public String execute(String str){
        initial_tape(str);
        int idx_current_state = 0;
        State current_state = this.state_lst.get(0); // begin with state 0
        int i = 0;
        while (idx_current_state != this.idx_halt_state){
            char c = this.tape[i];
            HashMap<Character, Transition> transition = current_state.get_transitions();
            Transition t = transition.get(c);
            char write_out = t.get_write_out();
            char direction = t.get_direction();
            this.tape[i] = write_out;
            idx_current_state = t.get_next_state();
            if (idx_current_state != this.idx_halt_state) {
                current_state = this.state_lst.get(idx_current_state);

                if (direction == 'L') {
                    i--;
                } else if (direction == 'R') {
                    i++;
                }
            }else{
                break;
            }
        }
        String outTape = toString();
        return outTape;
    }

    public String toString(){
        StringBuilder outTape = new StringBuilder();
        for(int i=0;i<tape.length;i++){
            outTape.append(tape[i]);
        }
        return outTape.toString();
    }
}
