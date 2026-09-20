public class HuffNode {
    private char character;
    private int weight;
    
    private HuffNode leftChild;
    private HuffNode rightChild;

    // constructor
    public HuffNode(char character, int weight, HuffNode leftChild, HuffNode rightChild) {
        this.character = character;
        this.weight = weight;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    // getters
    public char getCharacter() {
        return this.character;
    }

    public int getWeight() {
        return this.weight;
    }

    public HuffNode getLeftChild() {
        return this.leftChild;
    }

    public HuffNode getRightChild() {
        return this.rightChild;
    }

    public boolean isLeaf() {
        return this.leftChild == null && this.rightChild == null;
    }

    // method to get the Weighted Path Length
	public long calculateWPL(int depth) {

        // base case, if this is a leaf node, return (weight * depth)
        if (this.isLeaf()) {
            return (long) this.weight * depth;
        }

        // recursive step, sum the WPL of the left and right children
        // increment the depth for the next level
        return this.leftChild.calculateWPL(depth + 1) + this.rightChild.calculateWPL(depth + 1);
    }
}
