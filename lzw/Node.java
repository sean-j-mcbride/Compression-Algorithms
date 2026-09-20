public class Node {
	
	// initialise both for every node
	private int code = -1; 
	private Node[] children = new Node[128];
	
	// create a new node with specified code
	public Node(int code){
		this.code = code;
	}


	// getters and setters
	public int getCode() {
		return this.code;
	}

	public Node getChild(int index) {
		if (index < 0 || index >= children.length) {
			return null;
		}
		return children[index];
	}

	public void setChild(int index, Node node) {
		if (index >= 0 && index < children.length) {
			children[index] = node;
		}
	}
}