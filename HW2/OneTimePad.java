import java.io.*;
import java.nio.ByteBuffer;


/**
 * Created by kyle on 2/10/17.
 */
public class OneTimePad {
    public static void main(String[] args){

        //this takes in the input strings and creates files
        String option = args[0];
        File key_file = new File(args[1]);
        File ciphertext_file = new File(args[2]);
        File plaintext_file = new File(args[3]);

        if(option == "encrypt"){
            encrypt(plaintext_file, key_file, ciphertext_file);
        }else if(option == "decrypt"){
            decrypt(ciphertext_file, key_file, plaintext_file);
        }else{
            System.out.println("The option you printed was typed wrong.");
        }
    }

    private static void encrypt(File pt, File key, File ct) {
        //converts plaintext_file and key_file to byte[]
        byte[] plaintext = readContentIntoByteArray(pt);
        byte[] keyData = readContentIntoByteArray(key);
        //creates a byte[] for ciphertext to be stored
        byte[] ciphertext = new byte[keyData.length];

        //gets the length from the first 4 bytes of the ciphertext
        //alternatively I could have gotten keyData.length,
        //but this fits the project details better
        int length = ByteBuffer.wrap(ciphertext).getInt();

        //fills the plaintext byte[] with key XOR ciphertext
        for(int i = 0; i < length; i++){
            ciphertext[i] = (byte) (keyData[i] ^ plaintext[i + 4]);
        }

        //writes the output of plaintext[] to plaintext file
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(ct);
            fos.write(ciphertext);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param ct - the ciphertext_file
     * @param key - the key_file
     * @param pt - plaintext_file
     */
    private static void decrypt(File ct, File key, File pt) {
            //converts ciphertext_file and key_file to byte[]
            byte[] ciphertext = readContentIntoByteArray(ct);
            byte[] keyData = readContentIntoByteArray(key);
            byte[] plaintext = new byte[keyData.length];

            //gets the length from the first 4 bytes of the ciphertext
            //alternatively I could have gotten keyData.length,
            //but this fits the project details better
            int length = ByteBuffer.wrap(ciphertext).getInt();

            //fills the plaintext byte[] with key XOR ciphertext
            for(int i = 0; i < length; i++){
                plaintext[i] = (byte) (keyData[i] ^ ciphertext[i + 4]);
            }

            //writes the output of plaintext[] to plaintext file
            FileOutputStream fos = null;
            try{
                fos = new FileOutputStream(pt);
                fos.write(plaintext);
                fos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    /**
     * This is a helper function to convert a file into a byte array
     * @param file
     * @return byte[] of the file
     */
    private static byte[] readContentIntoByteArray(File file)  {
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length()];
        try
        {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return bFile;
    }

}
