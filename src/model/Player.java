package model;

import controller.Game;

public class Player {
	private String numPlayer;
	private String userName;
	private int candiesEaten = 0;
	private int score;
	private int lifeScore;
	private int correctAnswer;
	private int answersDone;
	private int numOfGames;
	private GameState gameState;
	private Game game;
	private boolean firstTurn;
	private boolean finishCandies;

	public Player(String userName) {
		this.numPlayer = "";
		this.userName = userName;
		this.score = utils.Consts.STARTING_SCORE;
		this.lifeScore = utils.Consts.STARTING_LIFE;
		this.correctAnswer = 0;
		this.answersDone = 0;
		this.numOfGames = 1;
		this.firstTurn = true;
		this.finishCandies = false;
	}

	public void initGame(Game game) {
		this.game = game;
		this.gameState = new GameState(game);
	}
	
	public void addlife() {
		this.lifeScore++;
	}

	public void removeLife() {
		this.lifeScore--;
	}

	public String getNumPlayer() {
		return numPlayer;
	}

	public void setNumPlayer(String numPlayer) {
		this.numPlayer = numPlayer;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score += score;
	}

	public int getLifeScore() {
		return lifeScore;
	}

	public void setLifeScore(int lifeScore) {
		this.lifeScore = lifeScore;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public Game getGame() {
		return game;
	}

	public GameState getGameState() {
		return gameState;
	}

	public boolean isFirstTurn() {
		return firstTurn;
	}

	public void setFirstTurn(boolean firstTurn) {
		this.firstTurn = firstTurn;
	}
	
	public void setCandiesEaten(int candiesEaten) {
		this.candiesEaten = candiesEaten;
	}
	
	public int getCandiesEaten() {
		return candiesEaten;
	}

	public int getAnswersDone() {
		return answersDone;
	}

	public void setAnswersDone(int answersDone) {
		this.answersDone = answersDone;
	}

	public boolean isFinishCandies() {
		return finishCandies;
	}

	public void setFinishCandies(boolean finishCandies) {
		this.finishCandies = finishCandies;
	}
	
	public int getNumOfGames() {
		return numOfGames;
	}

	public void setNumOfGames(int numOfGames) {
		this.numOfGames = numOfGames;
	}

}
