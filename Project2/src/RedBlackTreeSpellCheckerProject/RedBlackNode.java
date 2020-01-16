package RedBlackTreeSpellCheckerProject;

public class RedBlackNode {
    private static final int Black = 0;
    private static final int Red = 1;
    private int color;
    private String data;
    private RedBlackNode lc;
    private RedBlackNode rc;
    private RedBlackNode p;

    //Construct a RedBlackNode with data, color, parent pointer, left child pointer and right child pointer.
    public RedBlackNode(String data, int color,
                        RedBlackNode p, RedBlackNode lc, RedBlackNode rc){
        this.color = color;
        this.data = data;
        this.p = p;
        this.lc = lc;
        this.rc = rc;

    }

    //The getColor() method returns RED or BLACK.
    public int getColor(){
        if (color == Black){
            return Black;
        }
        else{
            return Red;
        }
    }

    //The getData() method returns the data in the node.
    public String getData(){
        return data;
    }

    //The getLc() method returns the left child of the RedBlackNode.
    public RedBlackNode getLc(){
        return lc;
    }

    //The getP() method returns the parent of the RedBlackNode.
    public RedBlackNode getP(){
        return p;
    }

    //The getRc() method returns the right child of the RedBlackNode.
    public RedBlackNode getRc(){
        return rc;
    }


    //The setColor() method sets the color of the RedBlackNode.
    public void setColor(int color){
        this.color = color;
    }

    //The setData() method sets the data or key of the RedBlackNode.
    public void setData(String data){
        this.data = data;
    }

    //The setLc() method sets the left child of the RedBlackNode.
    public void setLc(RedBlackNode lc){
        this.lc = lc;
    }

    //The setP() method sets the parent of the RedBlackNode.
    public void setP(RedBlackNode p){
        this.p = p;
    }

    //The setRc() method sets the right child of the RedBlackNode.
    public void setRc(RedBlackNode rc){
        this.rc = rc;
    }

    //The toString() methods returns a string representation of the RedBlackNode.
    public String toString(){
        return data; // need to make sure
    }
}
