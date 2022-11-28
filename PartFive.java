/*
Name: Sally
UOW ID: 4603229
*/

public class PartFive 
{
    private final static int DELTA = 0x9E3779B9; // From tutorial slide
    private final static long MASK32 = (1L << 32) -1; // Using the signed left shift operator
    private static int k[] = {0xab1a16be, 0xc4163a89, 0x87e5b018, 0x65ed8705}; // From tutorial slide

    private static long kBits_CFB_With_TEA(long stuNum, int bits) 
	{
        String inputH;
        int noOfBlocks, numberOfLeadingZero;
        long inputL, kBitInput, longTeaOutput, kBitTeaOutput, longTempOutput;

        long cipherText = 0;    // Set ciphertext to 0
        long longIV = -1; // Creating a long value with all bits = 1
        inputL = stuNum;

        System.out.printf("%d Bit CFB TEA Encryption%n", bits);
        System.out.println("-----------------------------------------------------------------");

        // Convert student number to Hex and assign it to string variable
        inputH = Long.toHexString(inputL);
        
        // Calculate number of 4-bit blocks from the HexString
        noOfBlocks = (int)Math.ceil(((double)(inputH.length() * 4 )/ bits)); // get result = 6
        long[] blocks = new long [noOfBlocks];
        
        System.out.printf("%-30s: %d%n","k-Bit Size", bits);
        System.out.printf("%-30s: %s%n", "Input Decimal", Long.toString(inputL));
        System.out.printf("%-30s: %s%n", "Input Binary", Long.toBinaryString(inputL));
        System.out.printf("%-30s: %s%n", "input Hex", inputH);
        System.out.printf("%-30s: %d%n", "inputH length", inputH.length());
        System.out.printf("%-30s: %s%n%n", "inputTextBlock", noOfBlocks);

        // Start recording CFB TEA Algorithm timing
        long fourBitCFBStartTime = System.nanoTime();
        kBitInput = (inputL >> noOfBlocks * bits - bits); // get 4-bit input per block

        // take most left k-bit of TEA output, XOR with k-bit of input block to get ciphertext
        // Subsequent IV is from most left k-bit of ciphertext
        // append k zeros to IV, and then add in the most left k-bit of ciphertext to the IV
        for (int i = 0; i < noOfBlocks; i ++) 
		{
            System.out.println ("CFB Block " + i);
            System.out.printf("%-30s: %s%n", "IV", Long.toBinaryString(longIV));
            
            // Encrypt IV to get E(input)
            longTeaOutput = encrypt(longIV, k);
            System.out.printf("%-30s: %s%n", "TEA output", Long.toBinaryString(longTeaOutput));

			kBitTeaOutput = longTeaOutput >>> (64 - bits); // Get the most left 4-bit of TEA output
            System.out.printf("%-30s: %s%n", "Most left 4-bit of TEA output" , Long.toBinaryString(kBitTeaOutput));
            System.out.printf("%-30s: %s%n", "Plaintext", Long.toBinaryString(inputL));
            System.out.printf("%-30s: %s%n", "Most left 4-bit Plaintext" , Long.toBinaryString(kBitInput));
            
            longTempOutput = kBitInput ^ kBitTeaOutput; // Plaintext XOR with most left 4-bit TEA output to get 4-bit ciphertext
            System.out.printf("%-30s: %s%n", "TEA output XOR k-bit Input", Long.toBinaryString(longTempOutput));
            cipherText = cipherText << bits; // Left shift 4 bits
            cipherText = cipherText | longTempOutput;   // OR with 4-bit ciphertext
            System.out.printf("%-30s: %s%n%n", "Cipher Text" , Long.toBinaryString(cipherText));
            
            // Update next round IV
            // Left shift operator, append 0000 to most right of IV
            longIV = longIV << bits;

            // 0000 | most left 4-bit of ciphertext, will get back the most left 4-bit of ciphertext
            // 1111 | 1 or 0 will get back 1
            longIV = longIV | cipherText;

            // Prepare next kBitInput which will be XOR with 4-bit TEA output
            inputL = inputL ^ (kBitInput << ((noOfBlocks - (i+1) ) * bits));
            kBitInput = (inputL >> (noOfBlocks- (i+1)) * bits - bits);
        }
        double fourBitCFBTime = System.nanoTime() - fourBitCFBStartTime;
		System.out.println("Encrypted Student UOW ID " + stuNum + " using " + bits + "-bits CFB TEA algorithm: " + Long.toHexString(cipherText));
        System.out.println("Duration for encrypting 4-bits CFB with TEA encryption: " + fourBitCFBTime + " nanoseconds");
        System.out.println();

		return cipherText;
	} // End kBits_CFB_With_TEA
    
    // TEA algorithm from tutorial slide
    private static long encrypt(long in, int[] k)
    {
        int v1 = (int) in;
        int v0 = (int) (in >>> 32);
        int sum = 0;

        for (int i = 0; i < 32; i++) 
        {
            sum += DELTA;
            v0 += ((v1 << 4) + k[0]) ^ (v1 + sum) ^ ((v1 >>> 5) + k[1]);
            v1 += ((v0 << 4) + k[2]) ^ (v0 + sum) ^ ((v0 >>> 5) + k[3]);
        }

        return (v0 & MASK32) << 32 | (v1 & MASK32);
    } // End encrypt

    // Pass in UOW ID, key, k-bits to encrypt using k-bit OFB TEA algorithm
    private static long kBits_OFB_TEA_Encryption (long stuNum, int [] key, int bits)
    {
        System.out.printf("%d Bit OFB TEA Encryption%n", bits);
        System.out.println("-----------------------------------------------------------------");

        long longCtext = 0; // Set ciphertext to 0
        long longIV = -1;
        long longTeaOutput, longTempOutput, inputL ,kBitInput, kBitTeaOutput;

        String inputH;
        int noOfBlocks;

        inputL = stuNum;

        // Convert student number to Hex and assign it to string variable
        inputH = Long.toHexString(inputL);

        // Since input is hexadecimal, length of input * 4 / number of bits to get the number of blocks that is needed.
        noOfBlocks = (int)Math.ceil(((double)(inputH.length() * 4 )/ bits));
        
        System.out.printf("%-30s: %d%n","k-Bit Size", bits);
        System.out.printf("%-30s: %s%n", "Input Decimal", Long.toString(inputL));
        System.out.printf("%-30s: %s%n", "Input Binary", Long.toBinaryString(inputL));
        System.out.printf("%-30s: %s%n", "Input Hex", inputH);
        System.out.printf("%-30s: %d%n", "Input Hex length", inputH.length());
        System.out.printf("%-30s: %s%n%n", "Input Blocks", noOfBlocks);
        
        // Start recording OFB TEA Algorithm timing
        long cBitOFBStartTime = System.nanoTime();
        kBitInput = (inputL >> noOfBlocks * bits - bits);
        
        // take most left k-bit of TEA output, XOR with k-bit of input block to get ciphertext
        // Subsequent IV is from most left k-bit of TEA output
        // append k zeros to IV, and then add in the most left k-bit of TEA output to the IV
        for (int i = 0 ; i < noOfBlocks; i++)
        {
            System.out.println ("OFB Block " + i);
            System.out.printf("%-30s: %s%n", "IV", Long.toBinaryString(longIV));
            
            // Encrypt IV to get E(input)
            longTeaOutput = encrypt(longIV, key);
            System.out.printf("%-30s: %s%n", "TEA output", Long.toBinaryString(longTeaOutput));

            /* Alternative way of getting the most left k-bit of TEA output
            //kBitTeaOutput = longTeaOutput & (longAllOne << 64 - bits);
            //kBitTeaOutput = Long.rotateRight(kBitTeaOutput, 64-bits);
            */

            kBitTeaOutput = longTeaOutput >>> (64 - bits); // Get the most left k-bit of TEA output
            System.out.printf("%-30s: %s%n", "Most left k-bit of TEA output" , Long.toBinaryString(kBitTeaOutput));
            System.out.printf("%-30s: %s%n", "Plaintext", Long.toBinaryString(inputL));
            System.out.printf("%-30s: %s%n", "Most left k-bit of Input" , Long.toBinaryString(kBitInput));

            // IV shifts k-bits OR with k-bit TEA output to get the next round's IV
            longIV = (longIV << bits) | kBitTeaOutput;

            longTempOutput = (kBitTeaOutput ^ kBitInput); // Plaintext XOR with most left k-bit TEA output to get k-bit ciphertext
            System.out.printf("%-30s: %s%n", "TEA output XOR k-bit Input", Long.toBinaryString(longTempOutput));
            longCtext = ((longCtext << bits) | longTempOutput);
            System.out.printf("%-30s: %s%n%n", "Cipher Text", Long.toBinaryString(longCtext));

            // Prepare next kBitInput which will be XOR with k-bit TEA output
            inputL = inputL ^ (kBitInput << ((noOfBlocks - (i+1) ) * bits));
            kBitInput = (inputL >> (noOfBlocks - (i+1)) * bits - bits);
        }

        long cBitOFBTime = System.nanoTime() - cBitOFBStartTime;
        System.out.println("Encrypted Student UOW ID " + stuNum + " using " + bits + "-bits OFB TEA algorithm: " + Long.toHexString(longCtext));
        System.out.println("Duration for encrypting " + bits + "-bits OFB with TEA encryption: " + cBitOFBTime + " nanoseconds");
        return longCtext;
    } // End kBits_OFB_TEA_Encryption

    public static void main (String [] args)
    {
        // For debugging
        //long stuNum = 305419896; // Tutorial example
        //int bits = 7; // Tutorial example

        long stuNum = 4603229; // UOW ID
        int bits = 4; 

        kBits_CFB_With_TEA(stuNum, bits);
        
        // Add all the digits of student number mod 8
        int tempNum = (int) stuNum;
        int bitSize = 0;
        while (tempNum > 0) 
        {
			bitSize += tempNum % 10;
			tempNum /= 10;
        }
        bitSize = bitSize % 8 ;	

        // For debugging
        //bitSize = 7; // Tutorial example

        // if result of mod operation is equal to 0 or 4, implement 3-bit OFB TEA
        if (bitSize == 0 || bitSize == 4 )
        {
            kBits_OFB_TEA_Encryption(stuNum, k , 3);
        }
        else
        {
            kBits_OFB_TEA_Encryption(stuNum, k , bitSize);
        }   
    } // End Main
} // End PartFive class