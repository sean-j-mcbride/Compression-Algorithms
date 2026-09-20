import java.io.*;
import java.util.*;

/** program to find compression ratio using LZW compression
 */
public class Main {

	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();
		String inputFileName = args[0];
		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);
		
		// read in the data and do the work here
        // read a line at a time to enable newlines to be detected and included in the compression

		// read in line by line building up a whole string of text
		StringBuilder textBuilder = new StringBuilder();
        while (in.hasNextLine()) {
			String line = in.nextLine();

            textBuilder.append(line);
            textBuilder.append('\n');
        }

        in.close();
        reader.close();

		String text = textBuilder.toString();

		// initialise original bits and compressed bits
		long originalBits = (long) text.length() * 8;
		long compBits = 0;

		// initialise trie (dictionary) with every standard ASCII value
		Trie trie = new Trie();
		for (int i = 0; i < 128; i++) {
			trie.insertAscVal(i, i);
		}

		int k = 8;
		int i = 0;
		int nextCode = 128;

		// bitwise left shift
		int threshold = 1 << k;

		// loop for every index in text
		while (i < text.length()) {

			// array so we can change it in the findLongestString function
    		int[] strLen = new int[1];
    		Node prefixNode = trie.findLongestPrefixNode(text, i, strLen);

    		// if we're exactly at the end, don't add another output
    		if (i + strLen[0] >= text.length()) {
				break;
			}

			// running total of compressed file length in bits
    		compBits += k;

    		char c = text.charAt(i + strLen[0]);

			// if we managed to insert new sequence increment nextCode
    		if (trie.insertSequence(prefixNode, c, nextCode)) {
				nextCode++;
			}
			
			// condition for increasing codeword
    		if (nextCode == threshold) {
        		k++;
        		threshold <<= 1;
    		}

			// increment i for the length of last sequence, will get exponentially larger as we go
    		i += strLen[0];
		}
        
		// account for code missed from breaking out the loop
		compBits += 8;
		
        double ratio = (double) compBits / originalBits;

		// output the results here
		System.out.println("Original file length in bits = " + originalBits);
        System.out.println("Compressed file length in bits = " + compBits);
        System.out.println("Compression ratio = " + String.format("%.4f", ratio));

		// end timer and print elapsed time as last line of output
		long end = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (end - start) + " milliseconds");
	}

}
