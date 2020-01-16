package edu.cmu.andrew.yiqizhou;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MerkleTree {
    private static BufferedReader br;

    //Recursive process to store data in the SinglyLinkedList and recursive calling until the length of the list is 1
    public static SinglyLinkedList rootGeneration(SinglyLinkedList value) throws Exception {
        if (value.hasNext() == false) {
            return value;
        }

        //copy the last node and then add it to the end of the list
        //if it is found that the list has an odd number of nodes
        if (value.countNodes() % 2 != 0){
            value.addAtEndNode(value.getLast());
        }

        int maxVal = value.countNodes();
        StringBuilder first = new StringBuilder(value.getObjectAt(0).toString());
        StringBuilder second = new StringBuilder(value.getObjectAt(1).toString());
        StringBuilder newFirst = first.append(second);

        //the newList would contain all new concatenated lists
        SinglyLinkedList newList = new SinglyLinkedList(new ObjectNode(h(newFirst.toString()), null));

        //concatenate each two nodes
        for(int i = 2; i < maxVal; i += 2){
            String newHash = value.getObjectAt(i).toString() + value.getObjectAt(i+1).toString();
            String hashVal = null;

            try{
                hashVal = h(newHash);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            newList.addAtEndNode(hashVal);
        }
        return rootGeneration(newList);
    }

    //encrypt the text
    public static String h(String text) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        for(int i=0; i <= 31; i++){
            byte b = hash[i];
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Please enter the absolute address of the file: ");
        Scanner input  = new Scanner(System.in);
        String filename = input.nextLine();
        br = new BufferedReader(new FileReader(new File(filename)));
        String line = br.readLine();

        //use a SinglyLinked list to store the first hash of data blocks
        SinglyLinkedList value = new SinglyLinkedList(new ObjectNode(h(line), null));
        while((line = br.readLine()) != null){
            try{
                value.addAtEndNode(h(line));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String output = rootGeneration(value).getObjectAt(0).toString();
        System.out.println("The Merkel root for " + filename + " is " + "\n" + output);

        /**
         * The file named CrimeLatLonXY has the Merkle root that we seek
         */

    }
}
