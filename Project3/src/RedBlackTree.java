public class RedBlackTree {
    private RedBlackNode root;
    private RedBlackNode nil;
    private static final int Black = 0;
    private static final int Red = 1;
    private int recentCompare = 0;
    private int size = 0;


    public RedBlackTree(){
        nil = new RedBlackNode(null, "0", Black, null, null, null);
        root = new RedBlackNode(null, "0", Black, nil, nil, nil);
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
            int cmp = v.compareTo(cur.getKey());
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

    public String showValue(String key) throws Exception {
        RedBlackNode cur = root;
        while(cur != nil){
            int cmp = key.compareTo(cur.getKey());
            if(cmp > 0){
                cur = cur.getRc();
            } else if (cmp < 0){
                cur = cur.getLc();
            } else{
                return cur.getValue();
            }
        }
        throw new Exception("error: no variable " + key);
    }

    /**
     * @best: Big_Theta(1)
     * @worst: Big_Theta(Log(n))
     * @pre both key and value are String
     * @post the <key,value> is entered into the tree and the key is unique
     *       within the tree. If the key was in the tree before then its old
     *       value is replaced with this new value.
     */
    public void insert(String key, String value){
        size++;

        if(root.getKey() == null){
            root.setKey(key);
            root.setValue(value);
            return;
        }

        RedBlackNode y = nil;
        RedBlackNode x = root;
        RedBlackNode z;
        int cmp;

        //find the position y of the node z in the binary tree
        while (x != nil){
            y = x;
            cmp = key.compareTo(x.getKey());
            if (cmp == 0){
                x.setValue(value);
                size--;
                return;
            }
            else if (cmp < 0){
                x = x.getLc();
            } else{
                x = x.getRc();
            }
        }

        //if y is nil, set z as root
        if(y == nil){
            root = new RedBlackNode(key, value, Red, y, nil, nil);
        } else{
            cmp = key.compareTo(y.getKey());
            if(cmp < 0) {
                y.setLc(new RedBlackNode(key, value, Red, y, nil, nil));
                z = y.getLc();
                RBInsertFixup(z);
            } else{
                y.setRc(new RedBlackNode(key, value, Red, y, nil, nil));
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


    public static void main(String[] args) throws Exception {

        RedBlackTree rbt = new RedBlackTree();

        for(int j = 1; j <= 50; j++) rbt.insert("var" + j, ""+j);

        System.out.println("var5's value is " + rbt.showValue("var" + 5));

        System.out.println("Insert twice with the same key var5 (i.e. key = var5, value = 555) ..........");
        rbt.insert("var5", "555");
        System.out.println("var5's new value is " + rbt.showValue("var" + 5));
        System.out.println();

        // is 20 in the tree
        System.out.println("Checking if a (key, value) pair is in the tree");
        if(rbt.contains("var"+ 20)) System.out.println("Found var20 and its value is " + rbt.showValue("var" + 20));
        else System.out.println("No var20 found");

        // is 51 in the tree
        if(rbt.contains("var"+ 51)) System.out.println("Found var51");
        else System.out.println("No var51 found");


    }
}

