public class Trie {
	
	// create root of the trie, doesn't represent a character
	private Node root; 
	
	public Trie() {
		root = new Node(-1);
	}

	public Node getRoot() {
		return root;
	}

	// insert a single ASCII character into the trie
	public void insertAscVal(int byteVal, int code) {
		root.setChild(byteVal, new Node(code));
	}

	public boolean insertSequence(Node prefixNode, char c, int code) {
		if (prefixNode.getChild(c) == null) {
			prefixNode.setChild(c, new Node(code));
			return true; // added new sequence
		}
		return false; // sequence already exists
	}
	
	// find the longest sequence from the starting index
    public Node findLongestPrefixNode(String text, int start, int[] strLength) {
        Node node = root;				// current node
		Node lastValidNode = root;		// last node that represents a valid code
		int lastValidIndex = start - 1;	// index of last valid char
        int i = start;

		// loop while the next charcter exists in children
        while (i < text.length()) {
			char c = text.charAt(i);
			Node nextNode = node.getChild(c);

			if (nextNode == null) {
				break;
			}
		
        	node = nextNode;

			// if valid code is found
			if (node.getCode() != -1) {
            	lastValidNode = node;
				lastValidIndex = i;
       		}
			i++;
		}

        // length of longest valid sequence
		strLength[0] = lastValidIndex - start + 1;
		return lastValidNode;
    }
	
	
}
