package controller;

import model.AnswerGhost;
import model.BasicCandy;
import model.BasicGhost;
import model.GameObject;
import model.Pacman;
import model.PosionCandy;
import model.QuestionCandy;
import model.SmartGhost;
import utils.Consts;
import utils.ObjectId;

public class ObjectFactory {

	/**
	 * creates a specific object that represents the object id it was given
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param game
	 * @return
	 */
	public GameObject createObject(ObjectId id, int x, int y, Game game) {
		GameObject toReturn = null;
		if (id == ObjectId.BasicCandy)
			toReturn = new BasicCandy(x, y, id, Consts.YELLOW_CANDY_SCORE, game);
		else if (id == ObjectId.SilverCandy)
			toReturn = new BasicCandy(x, y, id, Consts.SILVER_CANDY_SCORE, game);
		else if (id == ObjectId.QuestionCandy) {
			int numP = game.isSingleGame() ? 1 : 2;
			// Question question;
			// if its a multiplayer game, add two times the number of questions
			for (int i = 0; i < numP; i++)
				SysData.getInstance().addRandomQuestion();
			toReturn = new QuestionCandy(x, y, id, 0, null, game);
		} else if (id == ObjectId.PoisonCandy)
			toReturn = new PosionCandy(x, y, id, 0, game);
		else if (id == ObjectId.BasicGhost)
			toReturn = new BasicGhost(x, y, Consts.GHOST_SPEED, id, game);
		else if (id == ObjectId.SmartGhost)
			toReturn = new SmartGhost(x, y, Consts.GHOST_SPEED, id, game);
		else if (id == ObjectId.AnswerGhost)
			toReturn = new AnswerGhost(x, y, Consts.GHOST_SPEED, id, null, null, game);
		else if (id == ObjectId.Pacman)
			toReturn = new Pacman(x, y, Consts.PACMAN_SPEED, id, game);
		return toReturn;
	}
}
