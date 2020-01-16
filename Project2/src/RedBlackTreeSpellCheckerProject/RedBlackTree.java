package RedBlackTreeSpellCheckerProject;

import java.io.*;

public class RedBlackTree {
    private RedBlackNode root;
    private RedBlackNode nil;
    private static final int Black = 0;
    private static final int Red = 1;
    private int recentCompare = 0;
    private int size = 0;


    public RedBlackTree(){
        nil = new RedBlackNode(null, Black, null, null, null);
        root = new RedBlackNode(null, Black, nil, nil, nil);
    }

    /**
     * @best: Big_Theta(1)
     * @worst: Big_Theta(Log(n))
     * @pre v is a String
     * @post return a value close to v in the tree.
     */
    public String closeBy(String v){
        RedBlackNode current = root;
        RedBlackNode answer = root;
        while(current != nil) {
            int cmp = v.compareTo(current.getData());
            if (cmp > 0) {
                answer = current;
                current = current.getRc();
            } else if(cmp<0){
                answer = current;
                current = current.getLc();
            } else{
                return v;
            }
        }
        return answer.getData();
    }

    /**
     * @best: Big_Theta(1)
     * @worst: Big_Theta(Log(n))
     * @pre v is a String
     * @post returns true if the String v is in the RedBlackTree and false otherwise.
     */
    public boolean contains(String v){
        recentCompare = 0;
        RedBlackNode cur = root;
        while(cur != nil){
            recentCompare++;
            int cmp = v.compareTo(cur.getData());
            if(cmp > 0){
                cur = cur.getRc();
            } else if (cmp < 0){
                cur = cur.getLc();
            } else{
                return true;
            }
        }
        return false;
    }

    /**
     * @pre none
     * @post return the recentCompares
     */
    public int getRecentCompares(){
        return recentCompare;
    }

    /**
     * @pre none
     * @post the size of the tree will be returned
     */
    public int getSize(){
        return size;
    }

    /**
     * @pre none
     * @post return height of the tree
     */
    public int height(){
        return height(root);
    }

    //This a recursive routine that is used to compute the height of the red black tree.
    private int height(RedBlackNode t){
        if(t == nil){
            return -1;
        }
        int answer = Math.max(height(t.getLc()), height(t.getRc())+1);
        return answer;
    }

    private int heightForDiameter(RedBlackNode t){
        if(t == nil){
            return 0;
        }
        int answer = Math.max(heightForDiameter(t.getLc()) + 1, heightForDiameter(t.getRc())+1);
        return answer;
    }

    public int diameter(){
        return diameter(root);
    }

    private int diameter(RedBlackNode t){

        if(t == nil)
            return 0;

        int leftHeight = heightForDiameter(t.getLc());
        int rightHeight = heightForDiameter(t.getRc());

        int leftDiameter = diameter(t.getLc());
        int rightDiameter = diameter(t.getRc());

        return Math.max(leftHeight + rightHeight + 1, Math.max(leftDiameter, rightDiameter));
    }

    /**
     * @best: Big_Theta(1)
     * @worst: Big_Theta(Log(n))
     * @pre value is String
     * @post a new node will be inserted into the red black tree
     * The insert() method places a data item into the tree.
     */
    public void insert(String value){
        size++;

        if(root.getData() == null){
            root.setData(value);
            return;
        }

        RedBlackNode y = nil;
        RedBlackNode x = root;
        RedBlackNode z;
        int cmp;

        //find the position y of the node z in the binary tree
        while (x != nil){
            y = x;
            cmp = value.compareTo(x.getData());
            if (cmp < 0){
                x = x.getLc();
            } else{
                x = x.getRc();
            }
        }

        //if y is nil, set z as root
        if(y == nil){
            root = new RedBlackNode(value, Red, y, nil, nil);
        } else{
            cmp = value.compareTo(y.getData());
            if(cmp < 0) {
                y.setLc(new RedBlackNode(value, Red, y, nil, nil));
                z = y.getLc();
                RBInsertFixup(z);
            } else{
                y.setRc(new RedBlackNode(value, Red, y, nil, nil));
                z = y.getRc();
                RBInsertFixup(z);
            }
        }
    }

    /**
     * @pre z is a valid RedBlackNode
     * @post tree will be fixed up and Red Black Properties are preserved.
     */
    public void RBInsertFixup(RedBlackNode z) {

        //if the color of parent is red
        while (z.getP().getColor() == Red) {
            // if parent is the left child of grandparent
            if (z.getP() == z.getP().getP().getLc()) {
                RedBlackNode uncle = z.getP().getP().getRc();
                //case1: if the color of uncle is red
                if (uncle.getColor() == Red) {
                    //color parent and uncle black
                    z.getP().setColor(Black);
                    uncle.setColor(Black);
                    //color grandparent red
                    z.getP().getP().setColor(Red);
                    //set z to grandparent
                    z = z.getP().getP();
                } else {
                    //case2: if the color of uncle is black and current node is right child
                    if (z == z.getP().getRc()) {
                        //set z to parent
                        z = z.getP();
                        //left-rotate to zig zig
                        leftRotate(z);
                    }
                    //case3: if the color of uncle is black and current node is left child
                    //color parent of z black
                    z.getP().setColor(Black);
                    //color grandparent of z red
                    z.getP().getP().setColor(Red);
                    //right-rotate grandparent
                    rightRotate(z.getP().getP());
                }
            } else {//parent is the grandparent's right child
                RedBlackNode uncle = z.getP().getP().getLc();
                //case1: the color of uncle is red
                if (uncle.getColor() == Red) {
                    //set parent black
                    z.getP().setColor(Black);
                    //set uncle black
                    uncle.setColor(Black);
                    //set grandparent red
                    z.getP().getP().setColor(Red);
                    //set z to grandparent
                    z = z.getP().getP();
                } else {
                    //case2: if the color of uncle is black and current node is left child
                    if (z == z.getP().getLc()) {
                        z = z.getP();
                        rightRotate(z);
                    } else {//case3: the color of uncle is black and current node is right child
                        //color parent black
                        z.getP().setColor(Black);
                        //color grandparent red
                        z.getP().getP().setColor(Red);
                        //left-rotate grandparent
                        leftRotate(z.getP().getP());
                    }
                }
            }
        }
        //color root black
        root.setColor(Black);
    }

    //The no argument inOrderTraversal() method calls the recursive inOrderTraversal(RedBlackNode) - passing the root.
    public void inOrderTraversal(){
        inOrderTraversal(root);
    }

    /**
     * @pre t is a valid RedBlackNode
     * @post print nodes in inOrder order
     * This method perfroms an inorder traversal of the tree.
     */
    private void inOrderTraversal(RedBlackNode t){
        if (t == nil) {
            return;
        }
        // go left
        inOrderTraversal(t.getLc());

        // deal with the root
        String color;
        if(t.getColor() == 0){
            color = "Black";
        } else{
            color = "Red";
        }

        String parent;
        if(t.getP() == nil){
            parent = "-1";
        } else{
            parent = t.getP().getData();
        }

        String lc;
        if(t.getLc() == nil){
            lc = "-1";
        } else{
            lc = t.getLc().getData();
        }

        String rc;
        if(t.getRc() == nil){
            rc = "-1";
        } else{
            rc = t.getRc().getData();
        }

        System.out.println("[data = " + t.getData() + ":Color = " + color
                        + ":Parent = " + parent + ": LC = " + lc + ": RC = "
                        + rc + "]");

        // go right
        inOrderTraversal(t.getRc());
    }

    //The no argument reverseOrderTraversal() method calls the recursive reverseOrderTraversal(RedBlackNode) - passing the root.
    public void reverseOrderTraversal(){
        reverseOrderTraversal(root);
    }

    /**
     * @pre t is a valid RedBlackNode
     * @post print nodes in reverseOrder order
     * This method performs a reverseOrder traversal of the tree.
     */
    private void reverseOrderTraversal(RedBlackNode t){
        if (t == nil) {
            return;
        }
        // go right
        reverseOrderTraversal(t.getRc());

        // deal with the root
        String color;
        if(t.getColor() == 0){
            color = "Black";
        } else{
            color = "Red";
        }

        String parent;
        if(t.getP() == nil){
            parent = "-1";
        } else{
            parent = t.getP().getData();
        }

        String lc;
        if(t.getLc() == nil){
            lc = "-1";
        } else{
            lc = t.getLc().getData();
        }

        String rc;
        if(t.getRc() == nil){
            rc = "-1";
        } else{
            rc = t.getRc().getData();
        }

        System.out.println("[data = " + t.getData() + ":Color = " + color
                + ":Parent = " + parent + ": LC = " + lc + ": RC = "
                + rc + "]");

        // go left
        reverseOrderTraversal(t.getLc());
    }

    /**
     * @pre none
     * @post display the RedBlackTree in level order
     */
    public void levelOrderTraversal(){
        Queue q = new Queue();
        q.enQueue(root);
        while(!q.isEmpty()){
            RedBlackNode t = (RedBlackNode) q.deQueue();
            String color;
            if(t.getColor() == 0){
                color = "Black";
            } else{
                color = "Red";
            }

            String parent;
            if(t.getP() == nil){
                parent = "-1";
            } else{
                parent = t.getP().getData();
            }

            String lc;
            if(t.getLc() == nil){
                lc = "-1";
            } else{
                lc = t.getLc().getData();
            }

            String rc;
            if(t.getRc() == nil){
                rc = "-1";
            } else{
                rc = t.getRc().getData();
            }

            System.out.println("[data = " + t.getData() + ":Color = " + color
                    + ":Parent = " + parent + ": LC = " + lc + ": RC = "
                    + rc + "]");

            if(t.getLc() != nil){
                q.enQueue(t.getLc());
            }
            if(t.getRc() != nil){
                q.enQueue(t.getRc());
            }
        }
    }

    /**
     * @pre right child of x is not nil and root's parent is nil
     * @post tree will be left rotated
     */
    private void leftRotate(RedBlackNode x){
        //set y as x's right child
        RedBlackNode y = x.getRc();
        //set y's left child as x's right child
        x.setRc(y.getLc());
        //set x as the parent of y's left child
        y.getLc().setP(x);
        //set parent of x as parent of y
        RedBlackNode parent = x.getP();
        y.setP(parent);
        //if parent of x is null, set y as root
        if(parent == nil){
            root = y;
        } else{
            //if x is left child, set y as left child of x's parent
            if(x == parent.getLc()){
                parent.setLc(y);
            } else{
                //set y as right child of x's parent
                parent.setRc(y);
            }
        }
        //set x as y's left child
        y.setLc(x);
        //set y as x's parent
        x.setP(y);
    }

    /**
     * @pre right child of x is not nil and root's parent is nil
     * @post tree will be right rotated
     */
    private void rightRotate(RedBlackNode x){
        //set y as x's left child
        RedBlackNode y = x.getLc();
        //set y's right child as x's left child
        x.setLc(y.getRc());
        //set x as the parent of y's left child
        y.getRc().setP(x);
        //set parent of x as parent of y
        RedBlackNode parent = x.getP();
        y.setP(parent);
        //if parent of x is null, set y as root
        if(parent == nil){
            root = y;
        } else{
            //if x is left child, set y as left child of x's parent
            if(x == parent.getLc()){
                parent.setLc(y);
            } else{
                //set y as right child of x's parent
                parent.setRc(y);
            }
        }
        //set x as y's left child
        y.setRc(x);
        //set y as x's parent
        x.setP(y);
    }

    public static void main(String[] args) throws IOException {

        RedBlackTree rbt = new RedBlackTree();

        for(int j = 1; j <= 5; j++) rbt.insert(""+j);

        // after 1..5 are inserted
        System.out.println("RBT in order");
        rbt.inOrderTraversal();
        System.out.println("RBT level order");
        rbt.levelOrderTraversal();

        // is 3 in the tree

        if(rbt.contains(""+3)) System.out.println("Found 3");
        else System.out.println("No 3 found");

        // display the height
        System.out.println("The height is " + rbt.height());

        int diameter = rbt.diameter();
        System.out.println("Diameter of Red Black Tree is " + diameter);

    }
}

