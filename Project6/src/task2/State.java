package task2;

import java.util.HashMap;

public class State {
    private int state;
    private HashMap<Character, Transition> transition;

    public State(int n){
        this.state = n;
        this.transition = new HashMap<>();
    }

    public void addTransition(Transition t){
        char key = t.get_read_in();
        this.transition.put(key, t);
    }

    public HashMap get_transitions(){
        return this.transition;
    }
}
