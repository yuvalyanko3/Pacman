package utils;

/**
 * this class represents a grid location as a node.
 * it is used with the class 'ShortestPath'
 */

public class Node {
	public int x, y;
	//the different costs that are calculated in 'ShortestPath'
	public int costG, costH, costF;
	public int depth;
	public Node parent;
	
	public Node() {
		super();
	}
	
	public Node(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Node(int x, int y, Node parent, int costG, int costH) {
		super();
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.costG = costG;
		this.costH = costH;
		this.costF = this.costG + this.costH;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getCost() {
		return costG;
	}
	public void setCost(int cost) {
		this.costG = cost;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Node [x=" + x + ", y=" + y + "]";
	}
}
