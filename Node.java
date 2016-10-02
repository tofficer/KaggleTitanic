import java.util.ArrayList;

public class Node {
	String name; //is either a feature or label or says "root"
	String value; //what it got from its parent
	boolean isLeaf;
	ArrayList<Node> children;

	public Node (String name, boolean isLeaf) {
		this.name = name;
		this.isLeaf = isLeaf;
		this.children = new ArrayList<>();
	}

	public Node (String name, String value, boolean isLeaf) {
		this.name = name;
		this.isLeaf = isLeaf;
		this.value = value;
		this.children = new ArrayList<>();
	}

}