package edu.cmu.andrew.yiqizhou;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.Scanner;

public class Merkle_Hellman_Knapsack_Crypto_Project {
    // w: the superincreasing sequence of integers that make up part of the private key and used for decryption
    // b: the public key sequence used for encryption
    private SinglyLinkedList w, b;
    private BigInteger startNumW, startNumB;
    private Random rand;
    // q: a random integer that's larger than the sum of w
    // r: a random integer that's coprime with q
    private BigInteger q, r;
    private int length;
    private static final int maxChars = 80; //maximum number of characters allowed in a message
    private static final int binaryLength = maxChars * 8; // length of the longest message allowed in binary (a char is 8-bit long)
    private static final Charset UTF8 = Charset.forName("UTF-8");  // the program only processes UTF-8 encoded messages

    public Merkle_Hellman_Knapsack_Crypto_Project(String message) throws Exception {
        rand = new Random();
        int numbits = 50;
        startNumW = new BigInteger(numbits, rand).add(BigInteger.ONE);
        w = new SinglyLinkedList(new ObjectNode(startNumW, null));
        genKeys(binaryLength, numbits);

    }

    //generate the superincreasing sequence w, number q, number r and sequence b
    public void genKeys(int length, int numbits) throws Exception {

        BigInteger sum = startNumW;
        for(int i = 1; i < length; i++){
            BigInteger newInt = sum.add(new BigInteger(numbits, this.rand).add(BigInteger.ONE));
            sum = sum.add(newInt);
            w.addAtEndNode(sum);
        }

        //generate a number q that is greater than the sum
        q = sum.add(new BigInteger(numbits, this.rand).add(BigInteger.ONE));

        //choose a number r that is in the range [1, q) and is coprime to q
        r = q.subtract(BigInteger.ONE);

        //generate the sequence beta by multiplying each element in w by r mod q
        //the sequence beta makes up the public key
        SinglyLinkedList wCopy = w;
        wCopy.reset();
        ObjectNode currW;
        currW = wCopy.getCurrent();
        startNumB = new BigInteger(currW.toString(currW)).multiply(r).mod(q);
        b = new SinglyLinkedList(new ObjectNode(startNumB, null));

        int count = 0;
        for(int i = 0; i < length;i++){
            wCopy.next();
            BigInteger dataB = new BigInteger(currW.toString(currW)).multiply(r).mod(q);
            b.addAtEndNode(dataB);
            currW = wCopy.getCurrent();
            count++;
        }
    }

    //To encrypt, multiplies each respective bit by the corresponding number in b
    public String encrypt(String message) throws Exception {

        //String binaryMessage = charToBinary(message).toString();
        String binaryMessage = new BigInteger(message.getBytes(UTF8)).toString(2);

        // pad 0 to the left if the converted binary is not as long as the key sequences w and b
        if(message.length() < binaryLength){
            binaryMessage = String.format("%0" + (binaryLength - binaryMessage.length()) + "d", 0) + binaryMessage;
        }

        BigInteger key = BigInteger.ZERO;
        b.reset(); // reset the pointer to the head
        SinglyLinkedList bCopy = b;
        ObjectNode currB;
        currB = bCopy.getCurrent();
        for(int i = 0; i < binaryLength; i++){
            BigInteger tmpB = new BigInteger(currB.toString(currB));
            BigInteger tmpM = new BigInteger(binaryMessage.substring(i, i+1));
            key = key.add(tmpB.multiply(tmpM));
            bCopy.next();
            currB = bCopy.getCurrent();
        }
        return key.toString();
    }

    public String decrypt(String pubKey) throws Exception {

        //multiple public key by the modular inverse of r mod q;
        BigInteger tmp = new BigInteger(pubKey).multiply(r.modInverse(q)).mod(q);
        byte[] decrytedBin = new byte[binaryLength];

        for(int i = binaryLength - 1; i >= 0; i--){
            // found the largest element in w which is less than or equal to tmp
            BigInteger tmpLarge = new BigInteger(w.getObjectAt(i).toString());
            if (tmpLarge.compareTo(tmp) <= 0){
                tmp = tmp.subtract(tmpLarge);
                decrytedBin[i] = 1;
            }
            else{
                decrytedBin[i] = 0;
            }
        }

        StringBuilder binaryMessage = new StringBuilder("0");
        for(int i = 0; i < binaryLength-1; i++){
            binaryMessage.append(decrytedBin[i]);
        }

        return new String(new BigInteger(binaryMessage.toString(), 2).toByteArray());
    }

    public static void main(String[] args) throws Exception {
        Scanner input  = new Scanner(System.in);
        String message;
        while(true){
            System.out.println("Enter a string and I will encrpyt it as single large integer.");
            message = input.nextLine();
            if (message.length() > 80){
                System.out.println("Your message should have at most 80 characters. Please try again.");
            }
            else if(message.length() <= 0){
                System.out.println("Your message should not be empty. Please try again.");
            }
            else break;
        }

        System.out.println("Clear text:");
        System.out.println(message);
        System.out.println("Number of clear text bytes = " + message.getBytes().length);

        Merkle_Hellman_Knapsack_Crypto_Project crypto = new Merkle_Hellman_Knapsack_Crypto_Project(message);

        String encrypted = crypto.encrypt(message);
        System.out.println(message + "\"" + " is encrpyted as:");
        System.out.println(encrypted);

        System.out.print("Result of decryption: ");
        System.out.println(crypto.decrypt(encrypted));
    }

}
