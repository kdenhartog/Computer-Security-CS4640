package HW1.Problem2;

import java.io.*;
/**
 * Created by kyle on 1/27/17.
 */
public class Problem2 {
    public static void main(String[] args) {
        Problem2 method_caller = new Problem2();
        File input_file = new File(args[0]);
        File output_file = new File(args[1]);

        //converts file to a large bytesArray
        byte[] data = new byte[(int) input_file.length()];
        try {
            FileInputStream fis = new FileInputStream(input_file);
            fis.read(data);
        }catch (IOException e){
            e.printStackTrace();
        }

        //gets number of Lines
        int numOfLines = method_caller.lengthReader(data,0);
        System.out.println("Found Length: " + numOfLines);

        //gets first lineLength
        int lineLength = data[4];
        int startIndex = 8;
        String hexString = method_caller.reader(data,lineLength, startIndex);
        System.out.println(lineLength + 2 + " 0X" + hexString);

       for (int i = 1; i < numOfLines; i++) {
            //get next Line Length
            startIndex += lineLength + 4;
            lineLength = data[startIndex];

            //gets hexString and prints
            hexString = method_caller.reader(data,lineLength, startIndex);
            System.out.println(lineLength + 2  + " 0X" + hexString);
        }
    }//close main

    private String reader(byte[] buff, int length, int startPOS) {
        byte[] tempByteString = new byte[length];
        for (int i = 0; i < length; i++) {
            tempByteString[i] = buff[startPOS + i];
        }

        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[tempByteString.length * 2];
        for ( int i = 0; i < tempByteString.length; i++ ) {
            int v = tempByteString[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private int lengthReader(byte[] data, int startPOS){
        int length = 0;
        return length;
    }
}

