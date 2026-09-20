import java.io.*;
import java.util.*;

/** program to find compression ratio using Huffman coding
 */
public class Main {

	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();
		String inputFileName = args[0];
		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);
		
		// read in the data and do the work here
		// read a line at a time to enable newlines to be detected and included in the compression

		// Use an integer array to store the frequency of each ASCII character
		int[] charFreq = new int[128];
		long charCount = 0;

		// read in differently from LZW so charFreq can be built up
		while (in.hasNextLine()) {
			String line = in.nextLine();
			
			for (char c: line.toCharArray()) {

				// Add to the index of the array for current ASCII character
				if (c < 128) {
					charFreq[c]++;
					charCount ++;
				}
			}

			charFreq['\n']++;
			charCount ++;
		}
			

		reader.close();
		in.close();

		// initialise original bits and compressed bits
		long originalBits = (long) charCount * 8;
        long compBits = 0;

		// initialise a min heap using a priority queue
		PriorityQueue<HuffNode> queue = new PriorityQueue<>(
			// Always sort HuffNodes as they are added
			(node1, node2) -> Integer.compare(node1.getWeight(), node2.getWeight())
		);

		// create a new HuffNode for each standard ASCII character in the file
		for (int i = 0; i < 128; i++) {
			if (charFreq[i] > 0) {
				HuffNode leaf = new HuffNode((char)i, charFreq[i], null, null);

				// add to min heap
				queue.add(leaf);
			}
		}

		// loop until min heap only contains the root
		while (queue.size() > 1) {
			// get and remove the first 2 elements in the min heap
			HuffNode child1 = queue.poll();
			HuffNode child2 = queue.poll();
			int weight = child1.getWeight() + child2.getWeight();

			// create a parent of the 2 child nodes, which only contains the sum of their weight
			HuffNode parent = new HuffNode('\0', weight, child1, child2);
			queue.add(parent);
		}

		HuffNode root = queue.poll();
		
		if (root != null) {
			// if root is the only node
			if (root.isLeaf()) {
				compBits = (long) root.getWeight() * 1;
			} else {
				compBits = root.calculateWPL(0);
			}
		}

		double compRatio = 0.0;

		if (originalBits > 0) {
			compRatio = (double) compBits / originalBits;
		}
		
		// output the results here
		System.out.println("Original file length in bits = " + originalBits);
        System.out.println("Compressed file length in bits = " + compBits);
        System.out.println("Compression ratio = " + String.format("%.4f", compRatio));

		// end timer and print elapsed time as last line of output
		long end = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (end - start) + " milliseconds");
	}
}
