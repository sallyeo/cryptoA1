/*
Name: Sally
UOW ID: 4603229
*/

import java.util.*;

public class streamCipher
{
    public static void main(String[] args)
    {   
        int option, k, i, t, counter, encoded, decoded;
        String m = "";
        String c = "";

        Scanner in=new Scanner(System.in);

        System.out.println("Enter 1 to Encrypt or 2 to Decrypt:");

        option = in.nextInt();

        if(option == 1)     // Encryption
        {
            counter = 1;

            // Prompt user to key in message and the key
            System.out.println("Enter the message and key:");

            // Assign user input to variable m
            m=in.next();

            // Assign user input to variable k
            k=in.nextInt();

            // Convert message to uppercase and display on screen
            m=m.toUpperCase();
            System.out.println("The input text: " + m + "\n");

            for(i = 0; i < m.length(); i++)
            {
                // Subtract each character with the ASCII value to map A-Z to 0-25
                t = ((int)m.charAt(i)-65);
                t = t % 26;

                // Calculating the Ki = Kn-1 + n (mod 26)
                k = (k * k + counter);
                k = k % 26;
                
                // Ci = Mi + Ki (mod 26) = encoded
                encoded = (t + k) % 26;
                encoded = encoded + 65;
                c = c + (char)encoded;
              
                counter++;
            }

            System.out.println("The encrypted text is:" + c);
        }
        else if(option == 2)    // Decryption
        {
            counter = 1;
            // Prompt user to key in ciphertext and the key
            System.out.println("Enter the cypher text and key:");

            // Assign user input to m variable
            m = in.next();

            // Assign user input to k variable
            k = in.nextInt();

            // Convert message to upppsercase
            m = m.toUpperCase();
            System.out.println("The input text: " + m + "\n");

            // Loop thru each character
            for(i = 0; i < m.length(); i++)
            {
                // Subtract each character with the ASCII value to map A-Z to 0-25
                t = ((int)m.charAt(i)-65);
                t = t % 26;

                // Calculating the Ki = Kn-1 + n (mod 26)
                k = (k * k + counter);
                k = k % 26;
                
                // Ci = Mi - Ki (mod 26) = decoded
                decoded = (t - k) % 26;
                if (decoded < 0)
                {
                    decoded = decoded + 26 + 65;
                }
                else
                {
                    decoded = decoded + 65;
                }
                
                c = c + (char)decoded;

                counter++;
            }

            System.out.println("The decrypted text is:"+c);
        }
    } // End of main class
} // End of temp class