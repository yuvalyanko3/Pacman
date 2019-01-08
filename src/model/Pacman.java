package model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Iterator;

import controller.Game;
import controller.GameLogic;
import controller.SysData;
import utils.Animation;
import utils.Consts;
import utils.KeyInput;
import utils.ObjectId;

public class Pacman extends GameObject {
	private Player player;
	private boolean questionActive;
	private BufferedImage[] deathTextures;
	private Animation deathAnim;
	private int candiesEaten;
	private QuestionCandy currentQuestion;
	private boolean isDead;

	public Pacman(int x, int y, int speed, ObjectId id, Game game) {
		super(x, y, id, game);
		setSpeed(speed);
		questionActive = false;
		isDead = false;
		deathTextures = new BufferedImage[8];
	}

	public Pacman(int x, int y, ObjectId id, Player player, Game game) {
		super(x, y, id, game);
		this.player = player;
		this.candiesEaten = game.getCurrantPlayer().getCandiesEaten();
	}

	@Override
	public void tick() {
		collision();
		animDown.tick();
		animUp.tick();
		animLeft.tick();
		animRight.tick();
		if (!isDead) {
			if (game.getInput().isUp())
				move(Consts.UP);
			if (game.getInput().isDown())
				move(Consts.DOWN);
			if (game.getInput().isLeft())
				move(Consts.LEFT);
			if (game.getInput().isRight())
				move(Consts.RIGHT);
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(currentFrame, x, y, null);
	}

	/**
	 * checks if pacman has collided with another object and defines the proper
	 * actions that should be taken.
	 */
	@Override
	public void collision() {
		String flag = ""; // for correct / incorrect answer
		Iterator<AnswerGhost> iter = game.getHandler().getAnswerGhosts().iterator();
		Iterator<GameObject> iterator = game.getHandler().getGameObjects().iterator();
		while (iterator.hasNext()) {
			// set this object bounds to be 32 x 32
			Rectangle bounds = getBounds();
			bounds.width += 2;
			bounds.height += 2;
			GameObject temp = iterator.next();
			if (temp.getBounds() != null) {
				if (temp.getId().equals(ObjectId.BasicCandy) || temp.getId().equals(ObjectId.SilverCandy))
					if (getBounds().intersects(temp.getBounds())) {
						// remove the object from the board and add the score to the player
						GameLogic.getInstance().destroyObject(game.getCurrantPlayer(), temp);
						GameLogic.getInstance().addScore(game.getCurrantPlayer(), ((BasicCandy) temp).getCandyScore(),
								flag);
					}
				if (temp.getId().equals(ObjectId.PoisonCandy))
					if (getBounds().intersects(temp.getBounds())) {
						// start the death sequence
						isDead = true;
						GameLogic.getInstance().deathSequence(true, true);
					}
				if (temp.getId().equals(ObjectId.QuestionCandy))
					if (getBounds().intersects(temp.getBounds())) {
						// if there is no other question in progress
						if (!isQuestionActive()) {
							// get the question and add the relevant answer ghosts
							Question q = SysData.getInstance().getRandomGameQuestion();
							((QuestionCandy) temp).setQuestion(q);
							game.setAnswerGhosts(q.getGhosts());
							for (AnswerGhost g : q.getGhosts())
								GameLogic.getInstance().addAnswerGhost(g);
							// remove question candy
							GameLogic.getInstance().destroyObject(game.getCurrantPlayer(), temp);
							// show the question to the player
							GameLogic.getInstance().showQuestion(q);
							currentQuestion = ((QuestionCandy) temp);
							setQuestionActive(true);
						}
					}
				if (temp.getId().equals(ObjectId.BasicGhost) || temp.getId().equals(ObjectId.SmartGhost))
					if (bounds.intersects(temp.getBounds())) {
						// start the death sequence
						isDead = true;
						GameLogic.getInstance().deathSequence(false, true);							
					}
			}
		}
		// checks for collision with answer ghosts that are on the screen
		while (iter.hasNext()) {
			AnswerGhost temp = iter.next();
			int score;
			if (getBounds().intersects(temp.getBounds())) {
				Question q = temp.getQuestion();
				score = (temp.getAnswer().equals(q.getCorrectAnswer())) ? q.getCorrectScore() : q.getWrongScore();
				if (temp.getAnswer().equals(q.getCorrectAnswer()))
					flag = "correct";
				else
					flag = "incorrect";
				if (!isDead)
					GameLogic.getInstance().addScore(game.getCurrantPlayer(), score, flag);

				if (flag.equals("incorrect")) {
					isDead = true;
					GameLogic.getInstance().deathSequence(false, false);
				} else
					GameLogic.getInstance().removeAnswerGhosts(q.getGhosts());
			}
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, Consts.PACMAN_WIDTH - 2, Consts.PACMAN_HEIGHT - 2);
	}

	/**
	 * initializes the death animation
	 */
	public void deathAnimation() {
		deathAnim = new Animation(200, Arrays.copyOf(deathTextures, deathTextures.length));
	}

	/**
	 * play death animation
	 * 
	 * @param poisonCandy
	 * @param addScore
	 */
	public void startDeathAnim(boolean poisonCandy, boolean addScore) {
		if (isDead) {
			deathAnim.tick();
			this.currentFrame = deathAnim.getCurrentFrame();
			// if animation is over
			if (deathAnim.getIndex() == 11) {
				isDead = false;
				// if death caused by a poison candy, remove it from the board
				if (poisonCandy)
					GameLogic.getInstance().destroyObject(game.getCurrantPlayer(), game.getMaze().getPosionCandy());
				// if death was caused by a wrong answer, remove answer ghosts from the board
				if (isQuestionActive()) {
					GameLogic.getInstance().removeAnswerGhosts(game.getAnswerGhosts());
					//if we need to add a score for question candy after death (true only for poison candy and regular ghosts)
					if(addScore)
						game.getCurrantPlayer().setCandiesEaten(game.getCurrantPlayer().getCandiesEaten() + 1);
				}
				GameLogic.getInstance().deathSequence(poisonCandy, addScore);
				setStartingAnimation();
			}
		}
	}

	public int getPixelsX() {
		return x;
	}

	public int getPixelsY() {
		return y;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getCandiesEaten() {
		return candiesEaten;
	}

	public void setCandiesEaten(int candiesEaten) {
		this.candiesEaten = candiesEaten;
	}

	public void setTextures(BufferedImage[] t) {
		this.textures = t;
	}

	public void setStartingAnimation() {
		this.currentFrame = animLeft.getCurrentFrame();
	}

	public boolean isQuestionActive() {
		return questionActive;
	}

	public void setQuestionActive(boolean questionActive) {
		this.questionActive = questionActive;
	}

	public QuestionCandy getCurrentQuestion() {
		return currentQuestion;
	}

	public void setDeadTextures(BufferedImage[] t) {
		this.deathTextures = t;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean dead) {
		this.isDead = dead;
	}

}
