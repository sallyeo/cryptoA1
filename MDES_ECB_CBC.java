/*
Name: Sally
UOW ID: 4603229
*/

import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Arrays;

public class MDES_ECB_CBC
{
    int data;
    int key;

    public MDES_ECB_CBC()
    {
        data = 0;
        key = 0;
    } // End of MDES_ECB_CBC Constructor

    void setData(int dataIn)
    {
        data = dataIn;
    } // End setData

    void setKey(int keyIn)
    {
        key = keyIn;
    } // End keyIn

    // 2-bit left rotation to be called in f function
    int twoBitLeftRotation(int valueIn)
    {
        int valueOut = valueIn;

        // 4 possibilities for 2-bit rotation
        switch (valueIn)
        {
            case 0:
                valueOut = 0;
                break;
            case 1:
                valueOut = 2;
                break;
            case 2:
                valueOut = 1;
                break;
            case 3:
                valueOut = 3;
                break;
        }
        return valueOut;
    } // End twoBitLeftRotation

    int fourBitRightRotation(int valueIn)
    {
        int valueOut = valueIn;

        // 16 possibilities for 4-bit rotation
        switch (valueIn)
        {
            case 0:
                valueOut = 0;
                break;
            case 1:
                valueOut = 8;
                break;
            case 2:
                valueOut = 1;
                break;
            case 3:
                valueOut = 9;
                break;
            case 4:
                valueOut = 2;
                break;
            case 5:
                valueOut = 10;
                break;
            case 6:
                valueOut = 3;
                break;
            case 7:
                valueOut = 11;
                break;
            case 8:
                valueOut = 4;
                break;
            case 9:
                valueOut = 12;
                break;
            case 10:
                valueOut = 5;
                break;
            case 11:
                valueOut = 13;
                break;
            case 12:
                valueOut = 6;
                break;
            case 13:
                valueOut = 14;
                break;
            case 14:
                valueOut = 7;
                break;
            case 15:
                valueOut = 15;
                break;
        }
        return valueOut;
    } // End fourBitRotateRight

    int fourBitLeftRotation(int valueIn)
    {
        int valueOut = valueIn;

        // 16 possibilities for 4-bit rotation
        switch (valueIn)
        {
            case 0:
                valueOut = 0;
                break;
            case 1:
                valueOut = 2;
                break;
            case 2:
                valueOut = 4;
                break;
            case 3:
                valueOut = 6;
                break;
            case 4:
                valueOut = 8;
                break;
            case 5:
                valueOut = 10;
                break;
            case 6:
                valueOut = 12;
                break;
            case 7:
                valueOut = 14;
                break;
            case 8:
                valueOut = 1;
                break;
            case 9:
                valueOut = 3;
                break;
            case 10:
                valueOut = 5;
                break;
            case 11:
                valueOut = 7;
                break;
            case 12:
                valueOut = 9;
                break;
            case 13:
                valueOut = 11;
                break;
            case 14:
                valueOut = 13;
                break;
            case 15:
                valueOut = 15;
                break;
        }
        return valueOut;
    } // End fourBitRotateLeft

    // Round keys scheduling
    int [] roundKeyScheduling(int key)
    {
        int roundKey[];
        roundKey = new int[2]; // two rounds

        // 4 possibilites for 2-bit data
        switch (key)
        {
            case 0:
                roundKey[0] = 0;
                roundKey[1] = 0;
                break;
            case 1:
                roundKey[0] = 0;
                roundKey[1] = 7;
                break;
            case 2:
                roundKey[0] = 7;
                roundKey[1] = 0;
                break;
            case 3:
                roundKey[0] = 7;
                roundKey[1] = 7;
                break;
        }
        return roundKey;
    } // End roundKeyScheduling

    // Expansion function E
    int expand(int data)
    {
        int expandedData = 0;

        // 4 possibilites for 2-bit to 3-bit expansion
        switch(data)
        {
            case 0:
                expandedData = 0;
                break;
            case 1:
                expandedData = 2;
                break;
            case 2:
                expandedData = 5;
                break;
            case 3:
                expandedData = 7;
                break;
        }
        return expandedData;
    } // End expand

    int[] splitValue(int valueIn)
    {
        int[] valueOut = {0, 0};
        int maskleft = 12;
        int maskright = 3;

        valueOut[0] = (valueIn & maskleft) >> 2;
        valueOut[1] = (valueIn & maskright);
        return valueOut;
    } // End splitValue

    // To combine A and B later on in encryption/decryption function
    int combineValue(int v0, int v1)
    {
        int valueOut;

        valueOut = (v0 << 2) | v1;
        return valueOut;
    } // End combineValue

    // Linear S-box return 2-bit data
    int linearSBox(int inData)
    {
        int outputData = 0;

        // Mapping the decimals according to binary output
        switch(inData)
        {
            case 0:
                outputData = 3;
                break;
            case 1:
                outputData = 2;
                break;
            case 2:
                outputData = 1;
                break;
            case 3:
                outputData = 0;
                break;
            case 4:
                outputData = 3;
                break;
            case 5:
                outputData = 2;
                break;
            case 6:
                outputData = 1;
                break;
            case 7:
                outputData = 0;
                break;
        }
        return outputData;
    } // End linearSBox

    // Non-Linear S-Box return 2-bit data
    int nonLinearSBox (int inData)
    {
        int outputData = 0;

        // Mapping decimals according to the binary given in question
        switch (inData)
        {
            case 0:
                outputData = 0;
                break;
            case 1:
                outputData = 0;
                break;
            case 2:
                outputData = 0;
                break;
            case 3:
                outputData = 1;
                break;
            case 4:
                outputData = 0;
                break;
            case 5:
                outputData = 0;
                break;
            case 6:
                outputData = 2;
                break;
            case 7:
                outputData = 3;
                break;
        }
        return outputData;
    } // End nonLinearSBox

    // Linear F return 2-bit data
    int linearF(int d, int k)
    {
        int outputData;
        int expandedData = expand(d);   // expand 2-bit into 3-bit
        int expandedDataXorKey = expandedData ^ k;  // 3-bit data XOR 3-bit key
        int linearOutput = linearSBox(expandedDataXorKey);

        // 2-bit Left rotate the linearOutput
        outputData = twoBitLeftRotation(linearOutput);

        return outputData;
    } // End linearF

    // Non-linear F return 2-bit data
    int nonLinearF(int d, int k)
    {
        int outputData;

        int expandedData = expand(d);
        int expandedDataXorKey = expandedData ^ k;
        int nonLinearOutput = nonLinearSBox(expandedDataXorKey);

        // 2-bit Left rotate the nonLinearOutput
        outputData = twoBitLeftRotation(nonLinearOutput);

        return outputData;
    } // End nonLinearF

    // Part 3 LDES Encryption
    int lDESEncrypt(int dataIn, int keyIn)
    {
        int outputData;
        int tempData, fOutput;
        int A, B, currentRoundKey;
        int splitData[];
        int roundKey[];

        // Set data and key
        setData(dataIn);
        setKey(keyIn);

        // Generate two round keys
        roundKey = roundKeyScheduling(keyIn);

        // Initial Permutation: 4-bit left rotate the input data
        tempData = fourBitLeftRotation(dataIn);

        // Start two rounds of processing
        for (int round = 1; round <= 2; round++)
        {
            // Split data into 2-bit A (splitData[0]) and 2-bit B (splitData[1])
            splitData = splitValue(tempData);
            //System.out.println("left: " + splitData[0] + ", right: " + splitData[1]);

            // Generate 2-bit key
            currentRoundKey = roundKey[round - 1];
            //System.out.println("round key: " + currentRoundKey);

            // Send 2-bit B and current 2-bit round key to linear F function
            fOutput = linearF(splitData[1], currentRoundKey);
            //System.out.println("fOutput: " + fOutput);
            
            // 2-bit fOuput XOR 2-bit A to form next round 2-bit B
            B = splitData[0] ^ fOutput;
            A = splitData[1]; // set next round A = previous round B

            // Combine 2-bit A and 2-bit B back into 4-bit
            tempData = combineValue(A, B);
        } // End of round

        // Split 4-bit data into 2-bit A (splitData[0]) and 2-bit B (splitData[1])
        splitData = splitValue(tempData);

        // Swap A and B
        tempData = combineValue(splitData[1], splitData[0]);

        // 4-bit Rotate right
        outputData = fourBitRightRotation(tempData);

        return outputData;
    } // End of lDESEncrypt

    // Part 3 LDES Decryption
    int lDESDecrypt (int dataIn, int keyIn)
    {
        int outputData;
        int tempData, fOutput;
        int A, B, currentRoundKey;
        int splitData[];
        int roundKey[];

        // Set dataIn and keyIn
        setData(dataIn);
        setKey(keyIn);

        // Generate two round keys
        roundKey = roundKeyScheduling(keyIn);

        // Rotate left dataIn (Initial Permutation)
        tempData = fourBitLeftRotation(dataIn);

        // Start two rounds of processing
        for (int round = 1; round <= 2; round++)
        {
            // Split 4-bit data into 2-bit A (splitData[0]) and 2-bit B (splitData[1])
            splitData = splitValue(tempData);
            currentRoundKey = roundKey[2 - round];

            // Send 2-bit B and current 2-bit round key to linear F function
            fOutput = linearF(splitData[1], currentRoundKey);

            // XOR 2-bit fOutput with 2-bit A to form next round 2-bit B
            B = splitData[0] ^ fOutput;
            A = splitData[1];

            // Combine 2-bit A and 2-bit B into 4-bit data
            tempData = combineValue(A, B);
        } // End of round

        // Split 4-bit data into 2-bit A (splitData[0]) and 2-bit B (splitData[1])
        splitData = splitValue(tempData);

        // Swap left (A) and right (B)
        tempData = combineValue(splitData[1], splitData[0]);

        // Inverse Initial Permutation: 4-bit right rotate
        outputData = fourBitRightRotation(tempData);
        return outputData;
    } // End of lDESDecrypt

    // Part 4 MDES_ECB_CBC Encryption return 4-bit data
    int nonLinearDESEncrypt (int dataIn, int keyIn)
    {
        int outputData;
        int tempData, fOutput;
        int A, B, currentRoundKey;
        int splitData[];
        int roundKey[];

        // Set dataIn and keyIn
        setData(dataIn);
        setKey(keyIn);

        // Generate two round keys
        roundKey = roundKeyScheduling(keyIn);

        // Initial Permutation: 4-bit rotate left dataIn
        tempData = fourBitLeftRotation(dataIn);

        // Start two rounds of processing
        for (int round = 1; round <= 2; round++)
        {
            // Split 4-bit data into 2-bit A (splitData[0]) and 2-bit B (splitData[1])
            splitData = splitValue(tempData);

            // Generate key
            currentRoundKey = roundKey[2 - round];

            // Send 2-bit B and 2-bit round key to non-linear F function
            fOutput = nonLinearF(splitData[1], currentRoundKey);

            // XOR 2-bit fOutput with 2-bit A to form next round 2-bit B
            B = splitData[0] ^ fOutput;
            A = splitData[1];

            // Combine 2-bit A and 2-bit B into 4-bit data
            tempData = combineValue(A, B);
        } // End of round

        // Split 4-bit data into 2-bit A (splitData[0]) and 2-bit B (splitData[1])
        splitData = splitValue(tempData);

        // Swap A and B
        tempData = combineValue(splitData[1], splitData[0]);

        // 4-bit rotate right
        outputData = fourBitRightRotation(tempData);

        return outputData;
    } // End of nonLinearDESEncrypt

    // Part 4 MDES_ECB_CBC Decryption return 4-bit data
    int nonLinearDESDecrypt(int dataIn, int keyIn)
    {
        int outputData;
        int tempData, fOutput;
        int A, B, currentRoundKey;
        int splitData[], roundKey[];

        // Set dataIn and keyIn
        setData(dataIn);
        setKey(keyIn);

        // Generate two round keys
        roundKey = roundKeyScheduling(keyIn);

        // Rotate left dataIn (Initial Permutation)
        tempData = fourBitLeftRotation(dataIn);

        // Start two rounds of processing
        for (int round = 1; round <= 2; round++)
        {
            // Split 4-bit data into 2-bit A (splitData[0]) and 2-bit B (splitData[1])
            splitData = splitValue(tempData);
           
            // Generate key
            currentRoundKey = roundKey[2 - round];

            // Send 2-bit B and 2-bit round key to non-linear F function
            fOutput = nonLinearF(splitData[1], currentRoundKey);

            // XOR 2-bit fOutput with 2-bit A to form next round 2-bit B
            B = splitData[0] ^ fOutput;
            A = splitData[1];

            // Combine 2-bit A and 2-bit B into 4-bit data
            tempData = combineValue(A, B);
        } // End of round

        // Split 4-bit data into 2-bit A (splitData[0]) and 2-bit B (splitData[1])
        splitData = splitValue(tempData);

        // Swap A and B
        tempData = combineValue(splitData[1], splitData[0]);

        // 4-bit rotate right
        outputData = fourBitRightRotation(tempData);

        return outputData;
    } // End of nonLinearDESDecrypt

    // Should return integer to be passed into encryption algorithm 
    // in ECB mode
    int[] hexToDecimalArray(String hex) 
    {      
        int valueOutArr[] = new int [7];
        for (int i = 0; i < hex.length(); i++)
        {
            char c = hex.charAt(i);
            String valueIn = Character.toString(c);
            
            switch (valueIn)
            {
                case "0":
                valueOutArr[i] = 0;
                    break;
                case "1":
                valueOutArr[i] = 1;
                    break;
                case "2":
                valueOutArr[i] = 2;
                    break;
                case "3":
                valueOutArr[i] = 3;
                    break;
                case "4":
                valueOutArr[i] = 4;
                    break;
                case "5":
                valueOutArr[i] = 5;
                    break;
                case "6":
                valueOutArr[i] = 6;
                    break;
                case "7":
                valueOutArr[i] = 7;
                    break;
                case "8":
                valueOutArr[i] = 8;
                    break;
                case "9":
                valueOutArr[i] = 9;
                    break;
                case "a":
                valueOutArr[i] = 5;
                    break;
                case "b":
                valueOutArr[i] = 7;
                    break;
                case "c":
                valueOutArr[i] = 9;
                    break;
                case "d":
                valueOutArr[i] = 11;
                    break;
                case "e":
                valueOutArr[i] = 13;
                    break;
                case "f":
                valueOutArr[i] = 15;
                    break;
            }
        }
        return valueOutArr;
    } // End hexToDecimalArray

    int hexToDecimal(String hex) 
    {      
        int valueOut = 0;

        for (int i = 0; i < hex.length(); i++)
        {
            char c = hex.charAt(i);
            String valueIn = Character.toString(c);     
            switch (valueIn)
            {
                case "0":
                valueOut = 0;
                    break;
                case "1":
                valueOut = 1;
                    break;
                case "2":
                valueOut = 2;
                    break;
                case "3":
                valueOut = 3;
                    break;
                case "4":
                valueOut = 4;
                    break;
                case "5":
                valueOut = 5;
                    break;
                case "6":
                valueOut = 6;
                    break;
                case "7":
                valueOut = 7;
                    break;
                case "8":
                valueOut = 8;
                    break;
                case "9":
                valueOut = 9;
                    break;
                case "a":
                valueOut = 5;
                    break;
                case "b":
                valueOut = 7;
                    break;
                case "c":
                valueOut = 9;
                    break;
                case "d":
                valueOut = 11;
                    break;
                case "e":
                valueOut = 13;
                    break;
                case "f":
                valueOut = 15;
                    break;
            }
        }
        return valueOut;
    } // End hexToDecimal
    
    // Passing in each individual letter of input in integer form
    long cbcMode (int inputInt, long initialValue, int bits)
    {        
        MDES_ECB_CBC cbc = new MDES_ECB_CBC();
        long kBitInput;
        long xorOutput = 0;    
        String inputH;
        int noOfBlocks;

        kBitInput = (long)(inputInt);   // input in long form

        // Convert input to Hex and assign it to string variable
        inputH = Long.toHexString(kBitInput);

        // Since input is hexadecimal, length of input * 4 / number of bits to get the number of blocks that is needed.
        noOfBlocks = (int)Math.ceil(((double)(inputH.length() * 4 )/ bits));
        
        // For debugging
        //System.out.printf("CBC Mode%n");
        //System.out.println("-----------------------------------------------------------------");
        //System.out.printf("%-30s: %d%n", "Input Decimal", kBitInput);
        
        for (int i = 0 ; i < noOfBlocks; i++)
        {
            xorOutput = initialValue ^ kBitInput; // 4-bit input XOR with 4-bit IV output to get 4-bit output

            // For debugging
            //System.out.printf("%-30s:", "4-bit IV");
            //cbc.bitPattern(((int)(initialValue)), 4);
            //System.out.println();
            //System.out.printf("%-30s:", "4-bit of Input");
            //cbc.bitPattern(((int)(kBitInput)), 4);
            //System.out.println();
            //System.out.printf("%-30s:", "IV XOR Input");
            //cbc.bitPattern(((int)(xorOutput)), 4);
            //System.out.println();
        }

        return xorOutput;
    } // End cbcMode

    // Print binary in size-bit pattern
    void bitPattern(int n, int size)
    {
        int mask = 1 << size - 1;
        int counter = 0;
        while (mask != 0)
        {
            if (counter % size == 0)
            {
                System.out.print(" ");
            }

            if ((mask & n) == 0)
            {
                System.out.print("0");
            }
            else
            {
                System.out.print("1");
            }

            counter++;
            mask = mask >>> 1;
        }
    } // End bitPattern

    public static void main (String args[])
    {  
        MDES_ECB_CBC cbcObj = new MDES_ECB_CBC();
        MDES_ECB_CBC ecbObj = new MDES_ECB_CBC();

        String args0 = args[0];
        String key = args[1];
        String args2 = args[2];
        String mode = args[3];
        
        int i; 
        int convertedKey = 0;
   
        if (mode.equals("CBC"))
        {
            String initialValue = args[5];
            String option = args[6];
            String userInputHexStr = args[7];
            String[] encryptedOut = new String[7];
            String[] decryptedOut = new String[7];
            int hexIvToInt = cbcObj.hexToDecimal(initialValue);
            long IV = (long)(hexIvToInt);
            String xorHexStr;
            
            if (option.equals("-encrypt"))
            {
                // Convert user input into array of integer
                int[] inputIntArr = cbcObj.hexToDecimalArray(userInputHexStr);

                // Convert String key to int key
                switch(key)
                {
                    case "00":
                        convertedKey = 0;
                        break;
                    case "01":
                        convertedKey = 1;
                        break;
                    case "10":
                        convertedKey = 2;
                        break;
                    case "11":
                    convertedKey = 3;
                        break;
                }
                
                // Loop thru each integer to do encryption
                for (i = 0; i < inputIntArr.length; i++)
                {
                    long xorOutput = cbcObj.cbcMode(inputIntArr[i], IV, 4);   // XOR process
                    xorHexStr = Long.toHexString(xorOutput);              // convert xorOutput to Hex String
                    int hexStrToInt = cbcObj.hexToDecimal(xorHexStr);      // convert xorOutput hex to int

                    int outputInt = cbcObj.nonLinearDESEncrypt(hexStrToInt, convertedKey);  // Non-Linear DES Encryption to get ciphertext

                    String strOutput = Integer.toHexString(outputInt); // convert ciphertext to Hex
                    encryptedOut[i] = strOutput; // Store each encrypted output

                    // Update next round IV
                    IV = (long)(outputInt);
                }
                
                // Print out the encrypted output
                System.out.printf("CBC Encryption Mode%n");
                System.out.println("-----------------------------------------------------------------");
                System.out.printf("%-30s: %s%n", "Input Text", userInputHexStr);
                System.out.printf("%-30s: ", "CBC Encrypted Output"); 
                for (i = 0; i < encryptedOut.length; i++)
                {
                    System.out.print(encryptedOut[i]);
                }
                System.out.println(); 
            }
            else if (option.equals("-decrypt"))
            {
                // Convert user input into array of integer
                int[] inputIntArr = cbcObj.hexToDecimalArray(userInputHexStr);

                // Convert String key to int key
                switch(key)
                {
                    case "00":
                        convertedKey = 0;
                        break;
                    case "01":
                        convertedKey = 1;
                        break;
                    case "10":
                        convertedKey = 2;
                        break;
                    case "11":
                    convertedKey = 3;
                        break;
                }
                
                // Loop thru each integer to do decryption
                for (i = 0; i < inputIntArr.length; i++)
                {
                    // pre prepare the IV
                    if (i > 0)
                    {
                        IV = inputIntArr[i-1];
                    }

                    int outputInt = cbcObj.nonLinearDESDecrypt(inputIntArr[i], convertedKey);  // Non-Linear DES Decryption
                    long xorOutput = cbcObj.cbcMode(outputInt, IV, 4);   // XOR process to get plaintext
                    
                    xorHexStr = Long.toHexString(xorOutput);             // convert plaintext to Hex String
                    String strOutput = xorHexStr;
                    decryptedOut[i] = strOutput;                        // Store each decrypted output
                }
                
                // Print out the decrypted output
                System.out.printf("CBC Decryption Mode%n");
                System.out.println("-----------------------------------------------------------------");
                System.out.printf("%-30s: %s%n", "Input Text", userInputHexStr);
                System.out.printf("%-30s: ", "CBC decrypted Output"); 
                for (i = 0; i < decryptedOut.length; i++)
                {
                    System.out.print(decryptedOut[i]);
                }
                System.out.println(); 
            }
        }
        else if (mode.equals("ECB"))
        {
            String option = args[4];
            String userInputHexStr = args[5];
            String[] encryptedOut = new String[7];
            String[] decryptedOut = new String[7];
            String xorHexStr;

            if (option.equals("-encrypt"))
            {
                // Convert user input into array of integer
                int[] inputIntArr = ecbObj.hexToDecimalArray(userInputHexStr);

                // Convert String key to int key
                switch(key)
                {
                    case "00":
                        convertedKey = 0;
                        break;
                    case "01":
                        convertedKey = 1;
                        break;
                    case "10":
                        convertedKey = 2;
                        break;
                    case "11":
                    convertedKey = 3;
                        break;
                }
                
                // Loop thru each integer to do encryption
                for (i = 0; i < inputIntArr.length; i++)
                {
                    int outputInt = ecbObj.nonLinearDESEncrypt(inputIntArr[i], convertedKey);  // Non-Linear DES Encryption

                    String strOutput = Integer.toHexString(outputInt);
                    encryptedOut[i] = strOutput; // Store each encrypted output
                }
                
                // Print out the e"ncrypted output
                System.out.printf("ECB Encryption Mode%n");
                System.out.println("-----------------------------------------------------------------");
                System.out.printf("%-30s: %s%n", "Input Text", userInputHexStr);
                System.out.printf("%-30s: ", "ECB Encrypted Output"); 
                for (i = 0; i < encryptedOut.length; i++)
                {
                    System.out.print(encryptedOut[i]);
                }
                System.out.println("\n-----------------------------------------------------------------");
                System.out.println(); 
            }
            else if (option.equals("-decrypt"))
            {
                // Convert user input into array of integer
                int[] inputIntArr = ecbObj.hexToDecimalArray(userInputHexStr);

                // Convert String key to int key
                switch(key)
                {
                    case "00":
                        convertedKey = 0;
                        break;
                    case "01":
                        convertedKey = 1;
                        break;
                    case "10":
                        convertedKey = 2;
                        break;
                    case "11":
                    convertedKey = 3;
                        break;
                }
                
                // Loop thru each integer to do decryption
                for (i = 0; i < inputIntArr.length; i++)
                {
                    int outputInt = ecbObj.nonLinearDESDecrypt(inputIntArr[i], convertedKey);  // Non-Linear DES Decryption
                    xorHexStr = Long.toHexString((long)(outputInt));              // convert plaintext to Hex String
                    
                    String strOutput = xorHexStr;
                    decryptedOut[i] = strOutput; // Store each decrypted output
                }
                
                // Print out the decrypted output
                System.out.printf("ECB Decryption Mode%n");
                System.out.println("-----------------------------------------------------------------");
                System.out.printf("%-30s: %s%n", "Input Text", userInputHexStr);
                System.out.printf("%-30s: ", "ECB decrypted Output"); 
                for (i = 0; i < decryptedOut.length; i++)
                {
                    System.out.print(decryptedOut[i]);
                }
                System.out.println("\n-----------------------------------------------------------------");
                System.out.println(); 
            }
        }
    } // End main
} // End MDES_ECB_CBC class
