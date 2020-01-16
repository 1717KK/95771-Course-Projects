package RedBlackTreeSpellCheckerProject;

import java.io.*;
import java.util.Scanner;

public class RedBlackTreeSpellChecker {
    private static BufferedReader br;

    public static void main(String[] args) throws IOException {

        RedBlackTree rbt = new RedBlackTree();
        System.out.println("Please enter the absolute address of the file: ");
        Scanner input  = new Scanner(System.in);
        String filename = input.nextLine();
        br = new BufferedReader(new FileReader(new File(filename)));
        String line;
        while((line = br.readLine()) != null){
            rbt.insert(line);
        }
        br.close();

        System.out.println("java RedBlackTreeSpellChecker" + filename);
        System.out.println("Loading a tree of English words from " + filename);
        System.out.println("Red Black Tree is loaded with " + rbt.getSize() + " words");
        System.out.println("Initial tree height is " + rbt.height());
        System.out.println("Never worse than 2 * Lg(n+1) = " + 2 * (Math.log(rbt.getSize() + 1) / Math.log(2)));

        System.out.println("Legal commands are: ");
        System.out.println("d  display the entire word tree with a level order traversal.");
        System.out.println("s  print the words of the tree in sorted order (use an inorder traversal).");
        System.out.println("r  print the words of the tree in reverse sorted order.");
        System.out.println("c  <word> to spell check this word");
        System.out.println("a  <word> add word to tree.");
        System.out.println("f  <fileName> to spell check a text file for spelling errors.");
        System.out.println("i  display the diameter of the tree");
        System.out.println("m  view this menu");
        System.out.println("!  to quit.");

        while(true){
            Scanner in = new Scanner(System.in);
            String commands = in.nextLine();
            String [] cm = commands.split(" ");
            if(cm[0].equals(">!")){
                System.out.println("Bye");
            } else if(cm[0].equals(">d")){
                System.out.println("Level order traversal");
                rbt.levelOrderTraversal();
            } else if(cm[0].equals(">s")){
                System.out.println("In order traversal");
                rbt.inOrderTraversal();
            } else if(cm[0].equals(">r")) {
                System.out.println("Reverse order traversal");
                rbt.reverseOrderTraversal();
            } else if(cm[0].equals(">i")){
                int diameter = rbt.diameter();
                System.out.println("Diameter of Red Black Tree is " + diameter);
            } else if(cm[0].equals(">c")){
                if(rbt.contains(cm[1])){
                    System.out.println("Found " + cm[1] + " after " + rbt.getRecentCompares() + " comparisons");
                } else {
                    System.out.println(cm[1] + " Not in the dictionary. Perhaps you mean");
                    System.out.println(rbt.closeBy(cm[1]));
                }
            } else if(cm[0].equals(">a")){
                rbt.insert(cm[1]);
                System.out.println(cm[1] + " was added to dictionary");
            } else if(cm[0].equals(">f")){
                String checkFile = cm[1];
                FileInputStream fs = new FileInputStream(checkFile);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(fs));
                String line2;
                boolean contain = true;
                while((line2 = br2.readLine()) != null){
                    String[] cm2 = line2.split(" ");

                    if(cm2.length == 1){
                        if(!rbt.contains(line2)){
                            contain = false;
                            System.out.println("'"+ line2 + "' was not found in the dictionary.");
                        }
                    }else{
                        for(String s: cm2){
                            if(!rbt.contains(s)){
                                contain = false;
                                System.out.println("'" + s + "' was not found in the dictionary.");
                            }
                        }
                    }
                }
                if (contain == true) {
                    System.out.println("No spelling errors found");
                }
            }
        }
    }
}
