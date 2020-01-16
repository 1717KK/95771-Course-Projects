package task3;

public class Transition {
    public static char RIGHT = 'R';
    public static char LEFT = 'L';
    private char read_in;
    private char write_out;
    private char direction;
    private int next_state;

    public Transition(char in, char out, char direction, int next_state){
        this.read_in = in;
        this.write_out = out;
        this.direction = direction; // left, right
        this.next_state = next_state; // 0: halt state, 1: other
    }

    public char get_read_in(){
        return this.read_in;
    }

    public char get_write_out(){
        return this.write_out;
    }

    public int get_next_state(){
        return this.next_state;

    }

    public char get_direction(){
        return this.direction;
    }

}
