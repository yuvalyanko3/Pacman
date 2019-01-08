package model;

import controller.Game;
import utils.Consts;
import utils.ObjectId;

public class QuestionCandy extends BasicCandy {
	private Question question;
//	boolean isUsed;

	public QuestionCandy(int x, int y, ObjectId id, int candyScore, Question question, Game game) {
		super(x, y, id, candyScore, game);
		this.question = question;
//		isUsed = false;
	}

	public boolean isCorrect(String answer) {
		return question.isCorrect(answer);
	}

	public Question getQuestion() {
		return question;
	}

	/**
	 * sets the score for the given question, and assign it to this question candy
	 * @param question
	 */
	public void setQuestion(Question question) {
		int correctScore = (question.getLevel().equals("1")) ? Consts.EASY_QUESTION_SCORE :
						   (question.getLevel().equals("2")) ? Consts.AVERAGE_QUESTION_SCORE :
							   								   Consts.DIFFICULT_QUESTION_SCORE;
		int wrongScore = (question.getLevel().equals("1")) ? Consts.WRONG_EASY_ANSWER_SCORE :
						 (question.getLevel().equals("2")) ? Consts.WRONG_AVERAGE_ANSWER_SCORE :
							 							     Consts.WRONG_DIFFICULT_ANSWER_SCORE;
		question.setCorrectScore(correctScore);
		question.setWrongScore(wrongScore);
		this.question = question;
	}
	
//	public boolean isUsed() {
//		return isUsed;
//	}
//	
//	public void setIsUsed(boolean isUsed) {
//		this.isUsed = isUsed;
//	}
}
