import java.io.*;

public class LZWCompression {
    private HashTable<String, Integer> compress_table;
    private HashTable<Integer, String> decompress_table;
    private byte[] buffer = new byte[3];
    private byte buffer_size = 0;
    private SinglyLinkedList<Integer> codeword_read = new SinglyLinkedList<>();

    /**
     * Initialize the table used in compress and decompress.
     */
    private void init_table(){
        compress_table = new HashTable<>();
        decompress_table = new HashTable<>();
        for(int i=-128; i < 128; i++){
            //put all possbile 1 byte values into the compress table
            compress_table.put(String.valueOf((char) i), i+128);
            decompress_table.put(i+128, String.valueOf((char) i));
        }
    }


    /**
     * Write a given codeword to file as a 12-bit chunk.
     *
     * @param codeword codeword to be written to file
     * @param output the file output stream
     */
    public void write_chunk(int codeword, DataOutputStream output) throws IOException {
        if (buffer_size == 0){
            // this will be the first chunk to be written to buffer
            buffer[0] = (byte) (codeword >>> 4);
            buffer[1] = (byte) (codeword << 4);
            buffer_size++;
        }
        else{
            buffer[1] = (byte)(buffer[1] | (codeword >>> 8));
            buffer[2] = (byte)(codeword);
            for (int i=0;i<buffer.length;i++){
                output.writeByte(buffer[i]);
            }
            buffer_size = 0;
        }
    }

    /**
     * Extract codeword from the data chunk; This method handles 2 chunks (a total of 3 bytes) at a time.
     *
     * @param input the input stream
     */
    public void read_chunk(DataInputStream input) throws IOException {
        try{
            int first_byte = input.readByte() & 0xFF;// 0xFF properly pads 0 to the higher bits when converting to integers
            int second_byte = input.readByte() & 0xFF;
            codeword_read.add_at_ent((first_byte << 4) | (second_byte >>> 4));
            int third_byte = input.readByte() & 0xFF;
            codeword_read.add_at_ent(((second_byte & 0x0F) << 8) | third_byte);
        } catch (EOFException e) {
            input.close();
        } catch (IOException e){
        }
    }

    /**
     * This takes in the value from decompress table and write it to file.
     *
     * @param data the value from the codewordDic
     * @param output the output stream
     */
    public void write_byte(String data, DataOutputStream output) throws IOException {
        for(char c: data.toCharArray()){
            output.writeByte(c);
        }
    }

    /**
     * Compress and output given data.
     *
     * @param input the input stream
     * @param output the output stream
     */

    public void compress(DataInputStream input, DataOutputStream output) throws IOException {
        //initialize the compress table
        init_table();
        char byteIn;// current read-in byte
        int codeword;
        StringBuilder tmp = new StringBuilder();
        String tmpString;
        int idx = 256;
        try{
            //byteIn = (char)input.readByte();
            tmp.append((char)input.readByte());
            while(true){
                byteIn = (char)input.readByte();
                tmp.append(byteIn);
                tmpString = tmp.toString();
                if (compress_table.containsKey(tmpString)){
                    continue;
                }else{
                    // get codeword for the previous byte sequence
                    codeword = compress_table.get_value(tmp.substring(0, tmp.length()-1));
                    // write the codeword as a 12-bit chunk
                    write_chunk(codeword, output);
                    if(idx <= 2047){
                        compress_table.put(tmpString, idx++);
                    }
                    else{
                        init_table();
                        idx = 256;
                    }
                    // a new output string that starts at the current char
                    tmp = new StringBuilder(String.valueOf(byteIn));
                }
            }

        } catch (EOFException e) {
            input.close();
        }

        if(tmp.length()>0){
            codeword = compress_table.get_value(tmp.toString());
            write_chunk(codeword, output);
        }

        if(buffer_size != 0){
            output.writeByte(buffer[0]);
            output.writeByte(buffer[1]);
            if (buffer_size == 2){
                output.writeByte(buffer[2]);
            }
            buffer_size = 0;
        }
    }


    /**
     * Extract and output given compressed data.
     *
     * @param input the input stream
     * @param output the output stream
     */
    public void decompress(DataInputStream input, DataOutputStream output) throws IOException {
        init_table();
        int ind = 256;  // index for a new prefix

        read_chunk(input);  // read in the first two chunks and load to codewordReadBuffer

        if (!codeword_read.isEmpty()) {
            // handle the first codeword
            int priorCodeword = codeword_read.get_first();
            write_byte(decompress_table.get_value(priorCodeword), output);

            int currCodeword;
            // while there're still codewords left
            while (!codeword_read.isEmpty()) {
                currCodeword = codeword_read.get_first();
                if (decompress_table.containsKey(currCodeword)) {
                    // if the codewordDic contains the current codeword,
                    // just output the byte sequence it represents and
                    // put the new prefix to codewordDic (codeword table)
                    if (ind <= 2047) {
                        decompress_table.put(ind++, decompress_table.get_value(priorCodeword)
                                + decompress_table.get_value(currCodeword).charAt(0));
                    } else {  // codeword table is full; starts new
                        init_table();
                        ind = 256;
                    }
                    write_byte(decompress_table.get_value(currCodeword), output);

                } else {
                    // there's one special case where the codewordDic doesn't contain
                    // the current codeword: when the last char is the same as the first
                    // char in the string (e.g. "abca") that corresponds to the current codeword.
                    // Try compressing then de-compressing this string as an example: "abcabcabcabcabcabc".
                    String newCodeword = decompress_table.get_value(priorCodeword)
                            + decompress_table.get_value(priorCodeword).charAt(0);
                    // System.out.println("newCodeword: " + newCodeword);
                    if (ind <= 2047) {
                        decompress_table.put(ind++, newCodeword);
                    } else {
                        init_table();
                        ind = 256;
                    }
                    write_byte(newCodeword, output);
                }

                priorCodeword = currCodeword;

                // read in the next 2 chunks
                read_chunk(input);
            }
        }
    }

    /**
     * My program will store all possible byte value into compress table which will be used for compression, and
     * treat bytes as strings before being concatenated and served as keys. Values will be limited to [0, 2047] (12-bit chunk)
     *
     * Degree of compression:
     * (1)words.html: 2436KB -> 1150KB(Compressed) -> 2436KB(Decompressed)
     * (2)CrimeLatlonXY1990.csv: 2548KB -> 1500KB(Compressed) -> 2548KB(Decompressed)
     * (3)01_Overview.mp4: 24423KB -> 33911KB(Compressed) -> 24423KB(Decompressed)
     */
    public static void main(String[] args) {
        try (
                DataInputStream input =
                        new DataInputStream(
                                new BufferedInputStream(
                                        new FileInputStream(args[1])));
                DataOutputStream output =
                        new DataOutputStream(
                                new BufferedOutputStream(
                                        new FileOutputStream(args[2])))
        ){
            LZWCompression lzw = new LZWCompression();
            if(args[0].toLowerCase().equals("-c")){
                System.out.println("Compressing.....");
                lzw.compress(input, output);
                System.out.println("Compress Done!");
            }
            else if(args[0].toLowerCase().equals("-d")){
                System.out.println("Decompressing.....");
                lzw.decompress(input, output);
                System.out.println("Decompress Done!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
