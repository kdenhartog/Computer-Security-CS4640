package HW1.Problem1;

/**
 * Created by kyle on 1/26/17.
 */
import java.io.*;
import java.util.*;

public class Problem1 {
    public static void main(String[] args) {
        Problem1 method_caller = new Problem1();
        String conv_type = args[0];
        File input_file = new File(args[1]);
        File output_file = new File(args[2]);
        switch (conv_type){
            case "bin2hex":
                method_caller.filePrinter(input_file,output_file,0);
                break;
            case "hex2bin":
                method_caller.filePrinter(input_file,output_file,1);
                break;
            case "dec2hex":
                method_caller.filePrinter(input_file,output_file,2);
                break;
            case "dec2bin":
                method_caller.filePrinter(input_file,output_file,3);
                break;
            default:
                System.out.println("Argument one is invalid.");
                System.exit(1);
        }
    }//close main

    private void filePrinter(File input_file, File output_file, int option) {
        try {
            Scanner sc = new Scanner(input_file);
            PrintWriter printer = new PrintWriter(output_file);
            int num_of_lines = sc.nextInt();
            printer.write(num_of_lines + "\n");
            while (num_of_lines != 0) {
                //gets next line

                switch (option){
                    case 0: //this case is for bin2hex conversion line by line
                        sc.nextInt(); //used to get rid of the string length int
                        String bin2hexInput = sc.nextLine();
                        bin2hexInput = bin2hexInput.substring(1);
                        String bin2hexOutput = bin2hexConv(bin2hexInput);
                        //formatting for output file
                        printer.write(bin2hexOutput + "\n");
                        break;
                    case 1: //this case is for hex2bin conversion line by line
                        sc.nextInt(); //used to get rid of the string length int
                        String hex2binInput = sc.nextLine();
                        hex2binInput = hex2binInput.substring(3);
                        String hex2binOutput = hex2binConv(hex2binInput);
                        printer.write(hex2binOutput + "\n");
                        break;
                    case 2: //this case is for dec2hex conversion line by line
                        int dec2hexInput = sc.nextInt();
                        String dec2hexOutput = dec2hexConv(dec2hexInput);
                        printer.write(dec2hexOutput + "\n");
                        break;
                    case 3: //this case is for dec2bin conversion line by line
                        int dec2binInput = sc.nextInt();
                        String dec2binOutput = dec2binConv(dec2binInput);
                        printer.write(dec2binOutput + "\n");
                        break;
                }



                //decrement while loop
                num_of_lines--;
            }
            printer.close();
        } catch(FileNotFoundException e){
            System.err.println("File not found. Please enter a valid second argument.");
        }
    }

    private String dec2binConv(int decNum) {
        String output = dec2binAlgo(decNum);

        //Pad with 0s to the left of the base binary string allows for longer than 32 bit numbers as well
        while(output.length() % 32 != 0){
            output = "0" + output;
        }
        output = "" + output.length() + " " + output;
        return output;
    }

    private String dec2hexConv(int decNum) {
        String output = "";

        while(decNum != 0){
            Integer tempToOutput = (decNum % 16);
            if (tempToOutput > 10) {
                char convToChar = (char) (tempToOutput + 55);
                output = convToChar + output;
            }else {
                output = tempToOutput.toString() + output;
            }
            decNum = decNum / 16;
        }

        while(output.length() % 8 != 0){
            output = "0" + output;
        }

        output = "" + (output.length() + 2) + " 0X" + output;
        return output;

    }

    private String hex2binConv(String hexNum) {
        String output = "";
        char[] hexAsArray = hexNum.toCharArray();

        for (int i = 0; i < hexAsArray.length; i++) {
            if(hexAsArray[i] > 64 && hexAsArray[i] <= 70) {
                int decNum = hexAsArray[i] - 55;
                output =output +dec2binAlgo(decNum);
            }else if(hexAsArray[i] > 47 && hexAsArray[i] <= 57){
                int decNum = hexAsArray[i] - 48;
                output = output + dec2binAlgo(decNum);
            }else {
                System.out.println("There was an error parsing.");
                System.exit(1);
            }
        }

        while(output.length() % 4 != 0){
            output = "0" + output;
        }

        output = "" + output.length() + " " + output;
        return output;
    }

    private String bin2hexConv(String binNum){
        String output = "";
        //converts string to length that is divisible by 4 for easier conversion by padding left side with 0s
        while(binNum.length() % 4 != 0){
            binNum = "0" + binNum;
        }

        //simple method to convert 4 bits to a hex char because I was having issues with int, string, and char conversion.
        while(binNum.length() != 0){
            String temp_string = binNum.substring(0, 4);
            switch(temp_string){
                case "0000":
                    output = output + '0';
                    break;
                case "0001":
                    output = output + '1';
                    break;
                case "0010":
                    output = output + "2";
                    break;
                case "0011":
                    output = output + "3";
                    break;
                case "0100":
                    output = output + "4";
                    break;
                case "0101":
                    output = output + "5";
                    break;
                case "0110":
                    output = output + "6";
                    break;
                case "0111":
                    output = output + "7";
                    break;
                case "1000":
                    output = output + "8";
                    break;
                case "1001":
                    output = output + "9";
                    break;
                case "1010":
                    output = output + "A";
                    break;
                case "1011":
                    output = output + "B";
                    break;
                case "1100":
                    output = output + "C";
                    break;
                case "1101":
                    output = output + "D";
                    break;
                case "1110":
                    output = output + "E";
                    break;
                case "1111":
                    output = output + "F";
                    break;
                default:
                    System.out.println("Invalid input passed.");
            }

            //removes 4 bits from the string
            binNum = binNum.substring(4);
        }

        output = "" + (output.length() + 2) + " 0X" + output;
        return output;
    }// closes method bin2hexConv

    private String dec2binAlgo(int decNum){
        String output = "";
        while (decNum != 0){
            if(decNum % 2 == 0)
                output = "0" + output;
            else
                output = "1" + output;
            decNum = decNum / 2;
        }
        return output;
    }

}//close class