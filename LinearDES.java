/*
Name: Sally
UOW ID: 4603229
*/

import java.io.*;
import java.util.Arrays;

public class LinearDES
{
    int data;
    int key;

    public LinearDES()
    {
        data = 0;
        key = 0;
    } // End of LinearDES Constructor

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

    // Part 3 linear function
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

    // Part 4 non-linear function
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

    // Part 4 MDES Encryption
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

        // Rotate left dataIn (Initial Permutation)
        tempData = fourBitLeftRotation(dataIn);

        // Start two rounds of processing
        for (int round = 1; round <= 2; round++)
        {
            // Split data into A (splitData[0]) and B (splitData[1])
            splitData = splitValue(tempData);
            //System.out.println("left: " + splitData[0] + ", right: " + splitData[0]);
            currentRoundKey = roundKey[2 - round];
            //System.out.println("round key: " + currentRoundKey);

            // Send B and round key to non-linear F function
            fOutput = nonLinearF(splitData[1], currentRoundKey);
            //System.out.println("fOutput: " + fOutput);

            // XOR fOutput with A to form next round B
            B = splitData[0] ^ fOutput;
            A = splitData[1];

            // Combine A and B into 4-bit data
            tempData = combineValue(A, B);
        } // End of round

        // Split data into A (splitData[0]) and B (splitData[1])
        splitData = splitValue(tempData);

        // Swap A and B
        tempData = combineValue(splitData[1], splitData[0]);

        // Rotate right
        outputData = fourBitRightRotation(tempData);

        return outputData;
    } // End of nonLinearDESEncrypt

    // Part 4 MDES Decryption
    int nonLinearDESDecrypt(int dataIn, int keyIn)
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
            // Split data into A (splitData[0]) and B (splitData[1])
            splitData = splitValue(tempData);
            // For debugging
            //System.out.println("left: " + splitData[0] + ", right: " + splitData[0]);
            currentRoundKey = roundKey[2 - round];
            //System.out.println("round key: " + currentRoundKey);

            // Send B and round key to non-linear F function
            fOutput = nonLinearF(splitData[1], currentRoundKey);
            //System.out.println("fOutput: " + fOutput);

            // XOR fOutput with A to form next round B
            B = splitData[0] ^ fOutput;
            A = splitData[1];

            // Combine A and B into 4-bit data
            tempData = combineValue(A, B);
        } // End of round

        // Split data into A (splitData[0]) and B (splitData[1])
        splitData = splitValue(tempData);

        // Swap A and B
        tempData = combineValue(splitData[1], splitData[0]);

        // Rotate right
        outputData = fourBitRightRotation(tempData);

        return outputData;
    } // End of nonLinearDESDecrypt

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
        // Initialize data and key
        int[] data = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        int[] key = {0,1,2,3};
        int inData, k, cText, pText, xorResult;
        LinearDES ldesObj = new LinearDES();

        System.out.println("LDES Encryption: ");
        System.out.print("Key\\Message:     ");
        // Printing 4-bit data
        for (int i = 0; i < 16 ; i++)
        {
            for (int j = 0; j < 1; j++)
            {
                int x = data[i];
                ldesObj.bitPattern(x, 4);
            }
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < key.length; i++)
        {
            // Printing 2-bit key
            k = key[i];
            System.out.print("  ");
            ldesObj.bitPattern(k, 2);
            System.out.print("            ");
            
            for (int j = 0; j < data.length; j++)
            {
                inData = data[j];

                // LDES Encryption
                cText = ldesObj.lDESEncrypt(inData, k);
                
                // Printing 4-bit encrypted output
                for (int m = 0; m < 1; m++)
                {
                    ldesObj.bitPattern(cText, 4);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("LDES Decryption: ");
        System.out.print("Key\\Message:     ");
        // Printing 4-bit data
        for (int i = 0; i < 16 ; i++)
        {
            for (int j = 0; j < 1; j++)
            {
                int x = data[i];
                ldesObj.bitPattern(x, 4);
            }
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < key.length; i++)
        {
            // Printing 2-bit keys
            k = key[i];
            System.out.print("  ");
            ldesObj.bitPattern(k, 2);
            System.out.print("            ");
            
            for (int j = 0; j < data.length; j++)
            {
                inData = data[j];

                // LDES Encryption
                cText = ldesObj.lDESDecrypt(inData, k);

                // Printing 4-bit decrypted output
                for (int m = 0; m < 1; m++)
                {
                    ldesObj.bitPattern(cText, 4);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Verification:");
        System.out.printf("Equation: %19s = %5s ^ %5s ^ %5s", "1100", "1000", "0100", "0000");
        System.out.println();

        for (int i = 0; i < 4; i++)
        {
            int [] tempData = {12,8,4,0};
            k = key[i];

            System.out.print("Verified with key:");
            ldesObj.bitPattern(k, 2);
            System.out.print("-> ");
            for (int j = 0; j < 1; j++)
            {
                cText = ldesObj.lDESEncrypt(tempData[0], k);
                ldesObj.bitPattern(cText, 4);   

                System.out.print(" = ");

                cText = ldesObj.lDESEncrypt(tempData[1], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[2], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[3], k);
                ldesObj.bitPattern(cText, 4);

                // Verification
                int pText1 = ldesObj.lDESEncrypt(tempData[1], k);
                int pText2 = ldesObj.lDESEncrypt(tempData[2], k); 
                int pText3 = ldesObj.lDESEncrypt(tempData[3], k);
                xorResult = pText1 ^ pText2 ^ pText3;
                pText = ldesObj.lDESEncrypt(tempData[0], k);
                
                if (pText == xorResult)
                {
                    System.out.print("  ");
                    System.out.print("Verified: ");
                    ldesObj.bitPattern(pText, 4);
                }
            }
            System.out.println();   
        }
        System.out.println();
        
        System.out.println("Verification:");
        System.out.printf("Equation: %19s = %5s ^ %5s ^ %5s", "0011", "0010", "0001", "0000");
        System.out.println();

        for (int i = 0; i < 4; i++)
        {
            int [] tempData = {3,2,1,0};
            k = key[i];

            System.out.print("Verified with key:");
            ldesObj.bitPattern(k, 2);
            System.out.print("-> ");
            for (int j = 0; j < 1; j++)
            {
                cText = ldesObj.lDESEncrypt(tempData[0], k);
                ldesObj.bitPattern(cText, 4);   

                System.out.print(" = ");

                cText = ldesObj.lDESEncrypt(tempData[1], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[2], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[3], k);
                ldesObj.bitPattern(cText, 4);

                // Verification
                int pText1 = ldesObj.lDESEncrypt(tempData[1], k);
                int pText2 = ldesObj.lDESEncrypt(tempData[2], k); 
                int pText3 = ldesObj.lDESEncrypt(tempData[3], k);
                xorResult = pText1 ^ pText2 ^ pText3;
                pText = ldesObj.lDESEncrypt(tempData[0], k);
                
                if (pText == xorResult)
                {
                    System.out.print("  ");
                    System.out.print("Verified: ");
                    ldesObj.bitPattern(pText, 4);
                }
            }
            System.out.println();    
        }
        System.out.println();

        System.out.println("Verification:");
        System.out.printf("Equation: %19s = %5s ^ %5s ^ %5s", "1010", "1000", "0010", "0000");
        System.out.println();

        for (int i = 0; i < 4; i++)
        {
            int [] tempData = {10,8,2,0};
            k = key[i];

            System.out.print("Verified with key:");
            ldesObj.bitPattern(k, 2);
            System.out.print("-> ");
            for (int j = 0; j < 1; j++)
            {
                cText = ldesObj.lDESEncrypt(tempData[0], k);
                ldesObj.bitPattern(cText, 4);   

                System.out.print(" = ");

                cText = ldesObj.lDESEncrypt(tempData[1], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[2], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[3], k);
                ldesObj.bitPattern(cText, 4);

                // Verification
                int pText1 = ldesObj.lDESEncrypt(tempData[1], k);
                int pText2 = ldesObj.lDESEncrypt(tempData[2], k); 
                int pText3 = ldesObj.lDESEncrypt(tempData[3], k);
                xorResult = pText1 ^ pText2 ^ pText3;
                pText = ldesObj.lDESEncrypt(tempData[0], k);
                
                if (pText == xorResult)
                {
                    System.out.print("  ");
                    System.out.print("Verified: ");
                    ldesObj.bitPattern(pText, 4);
                }
            }
            System.out.println();    
        }
        System.out.println();

        System.out.println("Verification:");
        System.out.printf("Equation: %19s = %5s ^ %5s ^ %5s", "1001", "1000", "0001", "0000");
        System.out.println();

        for (int i = 0; i < 4; i++)
        {
            int [] tempData = {9,8,1,0};
            k = key[i];

            System.out.print("Verified with key:");
            ldesObj.bitPattern(k, 2);
            System.out.print("-> ");
            for (int j = 0; j < 1; j++)
            {
                cText = ldesObj.lDESEncrypt(tempData[0], k);
                ldesObj.bitPattern(cText, 4);   

                System.out.print(" = ");

                cText = ldesObj.lDESEncrypt(tempData[1], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[2], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[3], k);
                ldesObj.bitPattern(cText, 4);

                // Verification
                int pText1 = ldesObj.lDESEncrypt(tempData[1], k);
                int pText2 = ldesObj.lDESEncrypt(tempData[2], k); 
                int pText3 = ldesObj.lDESEncrypt(tempData[3], k);
                xorResult = pText1 ^ pText2 ^ pText3;
                pText = ldesObj.lDESEncrypt(tempData[0], k);
                
                if (pText == xorResult)
                {
                    System.out.print("  ");
                    System.out.print("Verified: ");
                    ldesObj.bitPattern(pText, 4);
                }
            }
            System.out.println();    
        }
        System.out.println();

        System.out.println("Verification:");
        System.out.printf("Equation: %19s = %5s ^ %5s ^ %5s", "0110", "0100", "0010", "0000");
        System.out.println();

        for (int i = 0; i < 4; i++)
        {
            int [] tempData = {6,4,2,0};
            k = key[i];

            System.out.print("Verified with key:");
            ldesObj.bitPattern(k, 2);
            System.out.print("-> ");
            for (int j = 0; j < 1; j++)
            {
                cText = ldesObj.lDESEncrypt(tempData[0], k);
                ldesObj.bitPattern(cText, 4);   

                System.out.print(" = ");

                cText = ldesObj.lDESEncrypt(tempData[1], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[2], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[3], k);
                ldesObj.bitPattern(cText, 4);

                // Verification
                int pText1 = ldesObj.lDESEncrypt(tempData[1], k);
                int pText2 = ldesObj.lDESEncrypt(tempData[2], k); 
                int pText3 = ldesObj.lDESEncrypt(tempData[3], k);
                xorResult = pText1 ^ pText2 ^ pText3;
                pText = ldesObj.lDESEncrypt(tempData[0], k);
                
                if (pText == xorResult)
                {
                    System.out.print("  ");
                    System.out.print("Verified: ");
                    ldesObj.bitPattern(pText, 4);
                }
            }
            System.out.println();    
        }
        System.out.println();

        System.out.println("Verification:");
        System.out.printf("Equation: %19s = %5s ^ %5s ^ %5s", "0101", "0100", "0001", "0000");
        System.out.println();

        for (int i = 0; i < 4; i++)
        {
            int [] tempData = {5,4,1,0};
            k = key[i];

            System.out.print("Verified with key:");
            ldesObj.bitPattern(k, 2);
            System.out.print("-> ");
            for (int j = 0; j < 1; j++)
            {
                cText = ldesObj.lDESEncrypt(tempData[0], k);
                ldesObj.bitPattern(cText, 4);   

                System.out.print(" = ");

                cText = ldesObj.lDESEncrypt(tempData[1], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[2], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[3], k);
                ldesObj.bitPattern(cText, 4);

                // Verification
                int pText1 = ldesObj.lDESEncrypt(tempData[1], k);
                int pText2 = ldesObj.lDESEncrypt(tempData[2], k); 
                int pText3 = ldesObj.lDESEncrypt(tempData[3], k);
                xorResult = pText1 ^ pText2 ^ pText3;
                pText = ldesObj.lDESEncrypt(tempData[0], k);
                
                if (pText == xorResult)
                {
                    System.out.print("  ");
                    System.out.print("Verified: ");
                    ldesObj.bitPattern(pText, 4);
                }
            }
            System.out.println();    
        }
        System.out.println();

        System.out.println("Verification:");
        System.out.printf("Equation: %19s = %5s ^ %5s ^ %5s", "0011", "0010", "0001", "0000");
        System.out.println();

        for (int i = 0; i < 4; i++)
        {
            int [] tempData = {3,2,1,0};
            k = key[i];

            System.out.print("Verified with key:");
            ldesObj.bitPattern(k, 2);
            System.out.print("-> ");
            for (int j = 0; j < 1; j++)
            {
                cText = ldesObj.lDESEncrypt(tempData[0], k);
                ldesObj.bitPattern(cText, 4);   

                System.out.print(" = ");

                cText = ldesObj.lDESEncrypt(tempData[1], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[2], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[3], k);
                ldesObj.bitPattern(cText, 4);

                // Verification
                int pText1 = ldesObj.lDESEncrypt(tempData[1], k);
                int pText2 = ldesObj.lDESEncrypt(tempData[2], k); 
                int pText3 = ldesObj.lDESEncrypt(tempData[3], k);
                xorResult = pText1 ^ pText2 ^ pText3;
                pText = ldesObj.lDESEncrypt(tempData[0], k);
                
                if (pText == xorResult)
                {
                    System.out.print("  ");
                    System.out.print("Verified: ");
                    ldesObj.bitPattern(pText, 4);
                }
            }
            System.out.println();    
        }
        System.out.println();

        System.out.println("Verification:");
        System.out.printf("Equation: %19s = %5s ^ %5s ^ %5s", "0111", "0100", "0010", "0001");
        System.out.println();

        for (int i = 0; i < 4; i++)
        {
            int [] tempData = {7,4,2,1};
            k = key[i];

            System.out.print("Verified with key:");
            ldesObj.bitPattern(k, 2);
            System.out.print("-> ");
            for (int j = 0; j < 1; j++)
            {
                cText = ldesObj.lDESEncrypt(tempData[0], k);
                ldesObj.bitPattern(cText, 4);   

                System.out.print(" = ");

                cText = ldesObj.lDESEncrypt(tempData[1], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[2], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[3], k);
                ldesObj.bitPattern(cText, 4);

                // Verification
                int pText1 = ldesObj.lDESEncrypt(tempData[1], k);
                int pText2 = ldesObj.lDESEncrypt(tempData[2], k); 
                int pText3 = ldesObj.lDESEncrypt(tempData[3], k);
                xorResult = pText1 ^ pText2 ^ pText3;
                pText = ldesObj.lDESEncrypt(tempData[0], k);
                
                if (pText == xorResult)
                {
                    System.out.print("  ");
                    System.out.print("Verified: ");
                    ldesObj.bitPattern(pText, 4);
                }
            }
            System.out.println();    
        }
        System.out.println();

        System.out.println("Verification:");
        System.out.printf("Equation: %19s = %5s ^ %5s ^ %5s", "1011", "1000", "0010", "0001");
        System.out.println();

        for (int i = 0; i < 4; i++)
        {
            int [] tempData = {11,8,2,1};
            k = key[i];

            System.out.print("Verified with key:");
            ldesObj.bitPattern(k, 2);
            System.out.print("-> ");
            for (int j = 0; j < 1; j++)
            {
                cText = ldesObj.lDESEncrypt(tempData[0], k);
                ldesObj.bitPattern(cText, 4);   

                System.out.print(" = ");

                cText = ldesObj.lDESEncrypt(tempData[1], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[2], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[3], k);
                ldesObj.bitPattern(cText, 4);

                // Verification
                int pText1 = ldesObj.lDESEncrypt(tempData[1], k);
                int pText2 = ldesObj.lDESEncrypt(tempData[2], k); 
                int pText3 = ldesObj.lDESEncrypt(tempData[3], k);
                xorResult = pText1 ^ pText2 ^ pText3;
                pText = ldesObj.lDESEncrypt(tempData[0], k);
                
                if (pText == xorResult)
                {
                    System.out.print("  ");
                    System.out.print("Verified: ");
                    ldesObj.bitPattern(pText, 4);
                }
            }
            System.out.println();    
        }
        System.out.println();

        System.out.println("Verification:");
        System.out.printf("Equation: %19s = %5s ^ %5s ^ %5s", "1101", "1000", "0100", "0001");
        System.out.println();

        for (int i = 0; i < 4; i++)
        {
            int [] tempData = {13,8,4,1};
            k = key[i];

            System.out.print("Verified with key:");
            ldesObj.bitPattern(k, 2);
            System.out.print("-> ");
            for (int j = 0; j < 1; j++)
            {
                cText = ldesObj.lDESEncrypt(tempData[0], k);
                ldesObj.bitPattern(cText, 4);   

                System.out.print(" = ");

                cText = ldesObj.lDESEncrypt(tempData[1], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[2], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[3], k);
                ldesObj.bitPattern(cText, 4);

                // Verification
                int pText1 = ldesObj.lDESEncrypt(tempData[1], k);
                int pText2 = ldesObj.lDESEncrypt(tempData[2], k); 
                int pText3 = ldesObj.lDESEncrypt(tempData[3], k);
                xorResult = pText1 ^ pText2 ^ pText3;
                pText = ldesObj.lDESEncrypt(tempData[0], k);
                
                if (pText == xorResult)
                {
                    System.out.print("  ");
                    System.out.print("Verified: ");
                    ldesObj.bitPattern(pText, 4);
                }
            }
            System.out.println();    
        }
        System.out.println();

        System.out.println("Verification:");
        System.out.printf("Equation: %19s = %5s ^ %5s ^ %5s", "1110", "1000", "0100", "0010");
        System.out.println();

        for (int i = 0; i < 4; i++)
        {
            int [] tempData = {14,8,4,2};
            k = key[i];

            System.out.print("Verified with key:");
            ldesObj.bitPattern(k, 2);
            System.out.print("-> ");
            for (int j = 0; j < 1; j++)
            {
                cText = ldesObj.lDESEncrypt(tempData[0], k);
                ldesObj.bitPattern(cText, 4);   

                System.out.print(" = ");

                cText = ldesObj.lDESEncrypt(tempData[1], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[2], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[3], k);
                ldesObj.bitPattern(cText, 4);

                // Verification
                int pText1 = ldesObj.lDESEncrypt(tempData[1], k);
                int pText2 = ldesObj.lDESEncrypt(tempData[2], k); 
                int pText3 = ldesObj.lDESEncrypt(tempData[3], k);
                xorResult = pText1 ^ pText2 ^ pText3;
                pText = ldesObj.lDESEncrypt(tempData[0], k);
                
                if (pText == xorResult)
                {
                    System.out.print("  ");
                    System.out.print("Verified: ");
                    ldesObj.bitPattern(pText, 4);
                }
            }
            System.out.println();    
        }
        System.out.println();

        System.out.println("Verification:");
        System.out.printf("Equation: %19s = %5s ^ %5s ^ %5s ^ %5s ^ %5s", "1111", "1000", "0100", "0010", "0001", "0000");
        System.out.println();

        for (int i = 0; i < 4; i++)
        {
            int [] tempData = {15,8,4,2,1,0};
            k = key[i];

            System.out.print("Verified with key:");
            ldesObj.bitPattern(k, 2);
            System.out.print("-> ");
            for (int j = 0; j < 1; j++)
            {
                cText = ldesObj.lDESEncrypt(tempData[0], k);
                ldesObj.bitPattern(cText, 4);   

                System.out.print(" = ");

                cText = ldesObj.lDESEncrypt(tempData[1], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[2], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[3], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[4], k);
                ldesObj.bitPattern(cText, 4);

                System.out.print(" ^ ");

                cText = ldesObj.lDESEncrypt(tempData[5], k);
                ldesObj.bitPattern(cText, 4);

                // Verification
                int pText1 = ldesObj.lDESEncrypt(tempData[1], k);
                int pText2 = ldesObj.lDESEncrypt(tempData[2], k); 
                int pText3 = ldesObj.lDESEncrypt(tempData[3], k);
                int pText4 = ldesObj.lDESEncrypt(tempData[4], k);
                int pText5 = ldesObj.lDESEncrypt(tempData[5], k);
                xorResult = pText1 ^ pText2 ^ pText3 ^ pText4 ^ pText5;
                pText = ldesObj.lDESEncrypt(tempData[0], k);
                
                if (pText == xorResult)
                {
                    System.out.print("  ");
                    System.out.print("Verified: ");
                    ldesObj.bitPattern(pText, 4);
                }
            }
            System.out.println();   
        }
        System.out.println(); 
    } // End main
} // End LinearDES class
