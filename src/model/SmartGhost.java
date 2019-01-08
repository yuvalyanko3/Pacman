package model;

import java.util.ArrayList;

import controller.Game;
import utils.Consts;
import utils.Node;
import utils.ObjectId;
import utils.ShortestPath;

public class SmartGhost extends BasicGhost {
	private ShortestPath aStar;
	private ArrayList<Node> path;

	public SmartGhost(int x, int y, int speed, ObjectId id, Game game) {
		super(x, y, speed, id, game);
	}

	@Override
	public void tick() {
		if (!game.getPacman().isDead()) {
			animDown.tick();
			animUp.tick();
			animLeft.tick();
			animRight.tick();
			if(game.getPacman().getGridX() < Consts.SIZE)
				 followPacman();
		}
	}

	private void followPacman() {
		int xa = 0;
		int ya = 0;

		// initialize a path from the ghost to pacman
		aStar = new ShortestPath(game.getMaze(), this, game.getPacman());
		// get the path
		path = aStar.getPath();
		if (path != null) {
			if (path.size() > 0) {
				// get the closest tile to the ghost
				Node temp = path.get(path.size() - 1);
				Node node = new Node((temp.getX()) * 32, temp.getY() * 32);
				// sets the next move for the ghost according to the tile
				if (x < node.getX())
					xa += speed;
				if (x > node.getX())
					xa -= speed;
				if (y < node.getY())
					ya += speed;
				if (y > node.getY())
					ya -= speed;
			}
		}
		// used to close the gap between the objects bounding box
		if (Math.abs(getGridX() - game.getPacman().getGridX()) == 0
				|| Math.abs(getGridY() - game.getPacman().getGridY()) == 0) {
			if (path.size() == 0) {
				if (game.getPacman().getY() == y)
					move(Consts.LEFT);
				else
					move(Consts.UP);
			}
		}

		if (xa > 0)
			move(Consts.RIGHT);
		if (xa < 0)
			move(Consts.LEFT);
		if (ya > 0)
			move(Consts.DOWN);
		if (ya < 0)
			move(Consts.UP);
	}
}
