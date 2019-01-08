package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.BasicGhost;
import model.Maze;
import model.Pacman;

@SuppressWarnings("unused")
public class ShortestPath {
	private Maze maze;
	private Pacman pacman;
	private int mazeGrid[][];
	private Node nodes[][];
	private ArrayList<Node> closed;
	private ArrayList<Node> open;
	private ArrayList<Node> path;
	
	public ShortestPath(Maze maze, BasicGhost ghost, Pacman pacman) {
		this.maze = maze;
		this.pacman = pacman;
		this.mazeGrid = maze.getMazeGrid();
		closed = new ArrayList<>();
		open = new ArrayList<>();
		nodes = new Node[Consts.SIZE][Consts.SIZE];
		path = new ArrayList<>();
		//sets the array to have new nodes
		initNodes();
		path = findPath(getTilesX(ghost.getX()), getTilesY(ghost.getY()), getTilesX(pacman.getX()), getTilesY(pacman.getY()));
	}
	
	private void initNodes() {
		for(int i = 0 ; i < Consts.SIZE ; i++)
			for(int j = 0 ; j < Consts.SIZE ; j++)
			{
				Node temp = new Node();
				temp.x = j;
				temp.y = i;
				nodes[i][j] = temp;
			}
	}

	protected int getTilesX(float x) {
		return (int) x / Consts.GRID_SIZE;
	}
	
	protected int getTilesY(float y) {
		return (int)(y) / Consts.GRID_SIZE;
	}
	
	/**
	 * returns the shortest path from one node to another (A*).
	 * @param sx starting location for the ghost (x)
	 * @param sy starting location for the ghost (y)
	 * @param tx pacman location (x)
	 * @param ty pacman location (y)
	 * @return
	 */
	public ArrayList<Node> findPath(int sx, int sy, int tx, int ty) {
		
		Node current = new Node(sx, sy, null, 0, (int)getEstimateDistance(sx, sy, tx, ty));
		open.add(current);
		
		while(!open.isEmpty())
		{
			Collections.sort(open, nodeSort);
			current = open.get(0);
			if((current.x == tx) && (current.y == ty)) {
				ArrayList<Node> path = new ArrayList<>();
				while(current.parent != null)
				{
					path.add(current);
					current = current.parent;
				}
				open.clear();
				closed.clear();
				return path;
			}
			open.remove(current);
			closed.add(current);
			for(int y = -1 ; y < 2 ; y++)
			{
				for(int x = -1 ; x < 2 ; x++)
				{
					//this is the current node
					if((x == 0) && (y==0))
						continue;
					//we don't want to check diagonally
					if((x != 0) && (y != 0))
						continue;
					int xp = x + current.x;
					int yp = y + current.y;
					//if the next node we want to check if a valid note (not a wall, etc)
					if(isValidTile(xp, yp))
					{
						Node neighbor = nodes[yp][xp];
						//calculate the G cost (the cost to the next tile)
						int gCost = current.costG + 
								(int)getEstimateDistance(current.x, current.y, neighbor.x, neighbor.y);
						//calculate the H cost (the direct distance from the next node to the target)
						int hCost = (int)getEstimateDistance(neighbor.x, neighbor.y, tx, ty);
						//create new node which will calculate the F cost (G cost + H cost)
						neighbor = new Node(xp, yp, current, gCost, hCost);
						if(closed.contains(neighbor) && gCost >= neighbor.costG)
							continue;
						if(!open.contains(neighbor) || gCost < neighbor.costG)
							open.add(neighbor);
					}
				}
			}
		}
		closed.clear();
		return null;
	}
	
	
	private boolean isValidTile(int xp, int yp) {
		//check if x or y went out of maze bounds
		boolean invalid = (xp < 0) || (yp < 0) || (xp >= Consts.SIZE || (yp >= Consts.SIZE));
		//returns true if x and y are in the bounds of the maze
		//and the next tile is not a wall
		//System.out.println(yp+" "+xp);
		return (!invalid) && mazeGrid[yp][xp] != 1;		
	}
	
	private double getEstimateDistance(int sx, int sy, int tx, int ty) {
		return Math.sqrt((sx * tx) + (sy * ty));
	}
	
	public ArrayList<Node> getPath(){
		return path;
	}
	
	private static final Comparator<Node> nodeSort = new Comparator<Node>() {
		@Override
		public int compare(Node o1, Node o2) {
			if(o2.costF < o1.costF)
				return 1;
			if(o2.costF > o1.costF)
				return -1;
			else 
				return 0;
		}
	};
}

