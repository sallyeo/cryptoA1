/*
 * Student Name	: SALLY
 * UOW ID		: 4603229
 * Code Reference: https://www.geeksforgeeks.org/implementation-affine-cipher/
 * Code Reference: https://stackoverflow.com/questions/19605465/how-to-write-logic-for-affine-cipher-decryption-in-java
 * Code Reference: https://stackoverflow.com/questions/21750365/how-to-find-the-most-frequently-occurring-character-in-a-string-with-java
 * Code Reference: https://www.w3resource.com/java-exercises/string/java-string-exercise-34.php
 * Code Reference: http://www.cs.trincoll.edu/~crypto/student/emilio/IC.java
 * Code Reference: https://www.dreamincode.net/forums/topic/238757-index-of-coincidence/
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


class affineCipher
{
    private static final int ASCII_SIZE = 256;

    static int a;
	static int b;
	static boolean primeCheck; 
	static String cipherText;
    static String plainText;

    
    // Function to check invalid key
    private static boolean isPrime(int num)
    {
        int gcd = 0;
        int i;

        for (i = 1; i <= num && i <= 26; ++i)
        {
            // Checks if i is a factor of both integers
            if (num % i == 0 && 26 % i == 0)
            {
                gcd = i;
            }
        }

        // global variable a is used here
        if (gcd == 1 && a < 26)
		{
			return true;
		}
		else
		{
			return false;
		}
    } // End isPrime

    // Get most frequent character to check the English text possibility
    // Ref: https://stackoverflow.com/questions/21750365/how-to-find-the-most-frequently-occurring-character-in-a-string-with-java
	private static char getMostChar(String str)
    {
        // Construct array to keep the count of individual characters & initialize the array as 0
        int countChar[] = new int[ASCII_SIZE];
        
        // Removes all whitespaces and non-visible characters
        String entire = str.replaceAll("\\s+","");
        int i;
        
        // Construct character count array from the input string.
        for (i = 0; i < entire.length(); i++)
		{
            countChar[entire.charAt(i)]++;
        }
        
        int max = -1;  // Initialize max count
        char result = ' ';   // Initialize result
      
        // Looping through the string and maintaining the count of each character
        for (i = 0; i < entire.length(); i++) 
        {
            if (countChar[str.charAt(i)] > max) 
            {
                max = countChar[str.charAt(i)];
                result = str.charAt(i);
            }
        }

        return result;
    } // End getMostChar
    
    // Get next most frequent character to check the English text possibility
    // Ref: https://www.w3resource.com/java-exercises/string/java-string-exercise-34.php
	private static char getNextMostChar(String str) 
	{
        int[] countChar = new int[ASCII_SIZE];
        int i;

		for (i = 0; i < str.length(); i++)
		{
			(countChar[str.charAt(i)]) ++;
		}
		
		int ctr_first = 0, ctr_second = 0;
		
        for (i = 0; i < ASCII_SIZE; i++) 
        {
			if (countChar[i] > countChar[ctr_first]) 
			{
				ctr_second = ctr_first;
				ctr_first = i;
			} 
			else if (countChar[i] > countChar[ctr_second] && countChar[i] != countChar[ctr_first])
			{
				ctr_second = i;
			}
        }
        
		return (char) ctr_second;
	} // End getNextMostChar

    // Function to encrypt message
    // Ref: https://www.geeksforgeeks.org/implementation-affine-cipher/
    // Ref: https://stackoverflow.com/questions/19605465/how-to-write-logic-for-affine-cipher-decryption-in-java
    private static String encryptMessage(char[] msg)
    {
        // Cipher text initially empty
        String cipher = "";
        int i;

        for (i = 0; i < msg.length; i++)
        {
            // Avoid space to be encrypted
            /* Applying encryption formula (a x + b) mod m {here x is msg[i] and m is 26}*/
            /* Added 'A' to bring it in range of ascii alphabet [65-90 | A-Z]*/
            /* Added 'a' to lowercase to bring it in range of ascii alphabet [97-122 | a-z] */

            if (msg[i] != ' ')  // if message not empty
            {
                if (msg[i] >= 'a' && msg[i] <= 'z')     // Lowercase
                {
                    int encoded = (((a * (msg[i] - 'a')) + b) % 26);
                    cipher = cipher + (char) (encoded + 'a');
                }
                else if (msg[i] >= 'A' && msg[i] <= 'Z')    // Uppercase
                {
                    int encoded = (((a * (msg[i] - 'A')) + b) % 26);
                    cipher = cipher + (char) (encoded + 'A'); 
                }
                else // if plaintext is non alphabets
                {
                    cipher = cipher + msg[i];  
                }
            }
            else if (msg[i] == ' ')     // else simply append space character 
            {
                cipher += ' '; 
            }
        }

        return cipher;
    } // End encryptMessage

    // Function to decrypt message
    // Ref: https://www.geeksforgeeks.org/implementation-affine-cipher/
    // Ref: https://stackoverflow.com/questions/19605465/how-to-write-logic-for-affine-cipher-decryption-in-java
    private static String decryptCipher(String cipher)
    {
        // Cipher text initially empty
        String msg = ""; 
        int a_inv = 0; 
        int flag = 0;
        int i;

        // Find a^-1 (the multiplicative inverse of a in the group of integers modulo m.) 
        for (i = 0; i < 26; i++)  
        { 
            // Use the global variable a
            flag = (a * i) % 26; 
  
            // Check if (a*i)%26 == 1, 
            // then i will be the multiplicative inverse of a 
            if (flag == 1)  
            { 
                a_inv = i; 
            } 
        }

        for (i = 0; i < cipher.length(); i++)  
        { 
            /*Applying decryption formula a_inverse ( x - b ) mod m {here x is cipher[i] and m is 26}*/
            /*added 'A' to bring it in range of ASCII alphabet[ 65-90 | A-Z ] */ 
            if (cipher.charAt(i) != ' ')    // if ciphertext not empty
            { 
				if (cipher.charAt(i) >= 'a' && cipher.charAt(i) <= 'z')     // Lowercase
				{
                    int decoded = ((a_inv * ((cipher.charAt(i) - 'a') - b + 26)) % 26);
                    msg = msg + (char) (decoded + 'a'); 
				}
				else if (cipher.charAt(i) >= 'A' && cipher.charAt(i) <= 'Z')    // Uppercase
				{
                    int decoded = ((a_inv * ((cipher.charAt(i) + 'A') - b)) % 26);
					msg = msg + (char) ((decoded % 26) + 'A'); 
                }
                else 
                { 
                    msg += cipher.charAt(i); 
                } 
            }  
            else if (cipher.charAt(i) == ' ')   // else simply append space character 
            { 
                msg += cipher.charAt(i); 
            } 
        }

        return msg;
    } // End decryptCipher
    
    private static int getIndexOfCharacter(char character) 
    {
        return (int)(character - 'A');
    } // End getIndexOfCharacter

    private static boolean isAlphabet(char character) 
    {
        int indexOfCharacter = getIndexOfCharacter(character);

        return indexOfCharacter > -1 && indexOfCharacter < 26;
    } // End isAlphabet

    private static int[] calculateFrequencies(String text) 
    {
        int[] frequencies = new int[26];

        for (int i = 0; i < frequencies.length; i++) 
        {
            frequencies[i] = 0;
        }

        for (int i = 0; i < text.length(); i++) 
        {
            char character = text.charAt(i);

            if (!isAlphabet(character)) 
            { 
                continue; 
            }

            frequencies[getIndexOfCharacter(character)]++;
        }

        return frequencies;
    } // End calculateFrequencies

    private static int calculateSumOfFrequencies(int[] frequencies) 
    {
        int sum = 0;

        for (int i = 0; i < frequencies.length; i++) 
        {
            sum += frequencies[i] * (frequencies[i] - 1);
        }

        return sum;
    } // End calculateSumOfFrequencies

    private static int countTotalNumberOfAlphabets(String text) 
    {
        int count = 0;

        for (int i = 0; i < text.length(); i++) 
        {
            if (isAlphabet(text.charAt(i))) 
            { 
                count++; 
            }
        }

        return count;
    } // End countTotalNumberOfAlphabets

    // Part 2 Task 1: Compute IOC of a text
    // Ref: http://www.cs.trincoll.edu/~crypto/student/emilio/IC.java
    private static double calculateIndexOfCoincidence(String text) 
    {
        text = text.toUpperCase();

        int[] frequencies = calculateFrequencies(text);
        int sumOfFrequencies = calculateSumOfFrequencies(frequencies);
        int numberOfAlphabets = countTotalNumberOfAlphabets(text);
        double indexOfCoincidence = (double) sumOfFrequencies / (numberOfAlphabets * (numberOfAlphabets - 1));

        return indexOfCoincidence;
    } // End calculateIndexOfCoincidence

    private static int calculateMutualSumOfFrequencies(int[] frequenciesA, int[] frequenciesB) 
    {
        int sum = 0;

        for (int i = 0; i < frequenciesA.length; i++) 
        {
            sum += frequenciesA[i] * frequenciesB[i];
        }

        return sum;
    } // End calculateMutualSumOfFrequencies

    // Part 2 Task 2
    private static double calculateMutualIndexOfCoincidence(String textA, String textB) 
    {
        textA = textA.toUpperCase();
        textB = textB.toUpperCase();

        int[] frequenciesA = calculateFrequencies(textA);
        int numberOfAlphabetsA = countTotalNumberOfAlphabets(textA);

        int[] frequenciesB = calculateFrequencies(textB);
        int numberOfAlphabetsB = countTotalNumberOfAlphabets(textB);

        int mutualSumOfFrequencies = calculateMutualSumOfFrequencies(frequenciesA, frequenciesB);
        int mutualNumberOfAlphabetsB = numberOfAlphabetsA * numberOfAlphabetsB;

        double mutualIndexOfCoincidence = (double) mutualSumOfFrequencies / mutualNumberOfAlphabetsB;

        return mutualIndexOfCoincidence;
    } // End calculateMutualIndexOfCoincidence


    // Main class
    public static void main(String[] args) throws IOException
    {
        String option = args[0];
        if (option.equals("-key"))
		{
            System.out.println();
            
			// Convert input into integer
            a = Integer.parseInt(args[1]);
            
			/* Checking input a is prime or not and assign
			the boolean value to primeCheck global variable.*/
			primeCheck = isPrime(a);
            if (primeCheck == true)
			{
				// Convert input into integer
				b = Integer.parseInt(args[2]);
                String name = args[3];
                String inputFile = args[5];
                String outputFile = args[7];
				String msg = "";

				try
				{
					msg = new String(Files.readAllBytes(Paths.get(inputFile)));
				}
				catch (IOException e)
				{
					e.printStackTrace();
                }

                // Write into output file
				FileWriter inFile = new FileWriter(new File(outputFile));
				if (name.equals("-encrypt"))
				{
					// Call encryption function
					cipherText = encryptMessage(msg.toCharArray());
					System.out.println("Plaintext Message is : " + msg);
					System.out.println("\nEncrypted Message is : " + cipherText);
					inFile.write(cipherText);
					inFile.close();
				}
				else if (name.equals("-decrypt"))
				{
					// Call decryption function
					plainText = decryptCipher(msg);
					System.out.println("Encrypted Message is : " + msg);
					System.out.println("\nDecrypted Message is : " + plainText);
					inFile.write(plainText);
					inFile.close();
				}
			}
			else
			{
				System.out.println ("The key is invalid!");
				System.exit(0);
			}
		}
        else if (option.equals("-decrypt"))
		{
            String msg = "";
            
            // Read cipherText from input file
			msg = new String(Files.readAllBytes(Paths.get(args[2])));
			
			// Removes all whitespaces and non-visible characters
			String cipherWithNoSpace = msg.replaceAll("\\s+","");
			
			// Find highest occurring character in ciphertext
            String cipherE = String.valueOf(getMostChar(cipherWithNoSpace));
			
			// Find second highest occurring character in ciphertext
            String cipherT = String.valueOf(getNextMostChar(cipherWithNoSpace));

			// Loop through to find key
			for(int i = 1; i < 26; i++)
			{
				for(int j = 0; j < 26; j++)
				{
                    primeCheck = isPrime(i);
                    if(primeCheck == true)
					{
						a = i;
                        b = j;
                        
						// Highest occurring character decrypted should be e
						String plainE = decryptCipher(cipherE);
						// Second occurring character decrypted should be t
						String plainT = decryptCipher(cipherT);
                        
                        // If it's English
                        if((plainE.equals("e")) && (plainT.equals("t")) || (plainE.equals("E")) && (plainT.equals("T")))
						{
                            // Write decrypted text into output file
                            FileWriter inFile = new FileWriter(new File(args[4]));
                            
							// Call decryption function passing in message read from input file
							plainText = decryptCipher(msg);
							System.out.println("Encrypted Message is : " + msg);
							System.out.println("\nDecrypted Message is : " + plainText);
							System.out.printf("\nThe keys are (%d, %d)\n", a, b);
							inFile.write(plainText);
							inFile.close();
						}
					}
				} 
			}
			
			String [] text = plainText.split(":");
			String[] tA = text[2].split("Text B");
			String tA_ = tA[0];
			String tB = text[3];
            
            // Part 2 Task 1 & 2
			double indexOfCoincidence = calculateIndexOfCoincidence(tA_);
			double mutualIndexOfCoincidence = calculateMutualIndexOfCoincidence(tA_, tB);

            System.out.printf("%-50s: %.3f%n", "The index of coincidence of text A", indexOfCoincidence);
            System.out.printf("%-50s: %.3f%n", "The mutual index of coincidence of text A & B", mutualIndexOfCoincidence);
        }
        
    } // end of main class
} // end of affineCipher class
