import java.security.SecureRandom;
import java.util.Random;

public class RC4 {
    public static void main(String[] args) {
        RC4 obj = new RC4();
        Random rg = new Random();
        //generates a random keylength between 16 and 32 bits and a random length of the 512 <= plaintext <= 2570 this is the length of pt[]
        int keylength = rg.nextInt(16) + 16;
        int ptLength = rg.nextInt(2048) + 512;

        //intializes the pt and key arrays
        byte[] pt = new byte[ptLength];
        byte[] key = new byte[keylength];

        //generates a random key between 16 and 32 bytes
        key = obj.createKey(key,keylength);

        //create random bytes for pt
        pt = obj.createKey(pt,ptLength);

        //generates a ciphertext using a key and pt as input. A ksa array is also built in this
        byte[] ct = obj.encrypt(pt,key);

        //generate an outputted byte array using the ciphertext and the key
        byte[] output = obj.decrypt(ct,key);

        boolean verified = obj.verifyEncandDec(pt, output);

        System.out.println("The original Plaintext and the text outputted " +
                "after one encrypt and decrypt cycle are: " + verified);
    }

    //this function is used to generate a random key that is stored in the byte array key
    public byte[] createKey(byte[] arr, int length)  {
        SecureRandom rg = new SecureRandom();
        for (int i = 0; i < length; i++) rg.nextBytes(arr);
        return arr;
    }

    public byte[] encrypt(byte[] pt, byte[] key){
        boolean dump = false;
        byte[] ciphertext = new byte[pt.length];
        int[] ksa = new int[256];
        ksa = ksaGenerator(ksa, key);

        int i = 0;
        int j = 0;

        /*  cycles 3072 times before begins cipher text generation this broke my text generation I also tried building
        *   it into the loop below by extending the length, but it broke there as well so I left it without the stream
        *   intializing the first 3072 bits because it worked then. Therefore, this is the RC4 algorithm and not the
        *   RC4 drop n algorithm.
        for (int k = 0; k < 255; k++){
            i = (i + 1) % ksa.length;
            j = (j + ksa[i]) % ksa.length;
            int temp = ksa[i];
            ksa[i] = ksa[j];
            ksa[j] = temp;
        }*/

        //begins ciphertext generation
        for (int k = 0; k < pt.length; k++) {
            i = (i + 1) % ksa.length;
            j = (j + ksa[i]) % ksa.length;
            int temp = ksa[i];
            ksa[i] = ksa[j];
            ksa[j] = temp;
            int tempInt = ksa[(ksa[i] + ksa[j]) % 255];
            ciphertext[k] = (byte) (tempInt ^ pt[k]);
        }

        return ciphertext;
    }

    public byte[] decrypt(byte[] ct, byte[] key){
        byte[] output = new byte[ct.length];
        int[] ksa = new int[256];
        ksa = ksaGenerator(ksa, key);

        int i = 0;
        int j = 0;

        for (int k = 0; k < ct.length; k++) {
            i = (i + 1) % ksa.length;
            j = (j + ksa[i]) % ksa.length;
            int temp = ksa[i];
            ksa[i] = ksa[j];
            ksa[j] = temp;
            int l = 0;
            l = ksa[(ksa[i] + ksa[j]) % 255];
            output[k] = (byte) (l ^ ct[k]);
        }
        return output;
    }

    public int[] ksaGenerator(int[] ksa, byte[] kv){
        for (int i = 0; i < ksa.length; i++){
            ksa[i] = i;
        }

        int j = 0;
        for(int i = 0; i < kv.length; i++){
            j = (j + ksa[i] + (kv[i % 255]) & 0xFF) % ksa.length;
            int temp = ksa[i];
            ksa[i] = ksa[j];
            ksa[j] = temp;
        }

        return ksa;
    }

    public boolean verifyEncandDec(byte[] pt, byte[] output){
        for (int i = 0; i < pt.length; i++) {
            if(pt[i] != output[i]){
                System.out.println("Original pt[" + i + "]: " +  pt[i]);
                System.out.println("Outputted pt[" + i + "]: " +  output[i]);
                System.out.println("Original pt[" + i + "]: " +  pt[i+1]);
                System.out.println("Outputted pt[" + i + "]: " +  output[i+1]);
                System.out.println(i);
                return false;
            }
            System.out.println("Original pt[" + i + "]: " +  pt[i]);
            System.out.println("Outputted pt[" + i + "]: " +  output[i]);
        }


        return true;
    }
}
