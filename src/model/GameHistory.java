package model;


public class GameHistory {
	private String number;
	private String name;
	private String date;
	private String finalScore;
	private String questionsDone;
	private String rightAnswers;
	
	
	public GameHistory(String number, String name, String date, String finalScore,String questionsDone , String rightAnswers) {
		super();
		this.number = number;
		this.name = name;
		this.date = date;
		this.finalScore = finalScore;
		this.questionsDone = questionsDone;
		this.rightAnswers = rightAnswers;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}

	public String getRightAnswers() {
		return rightAnswers;
	}

	public void setRightAnswers(String rightAnswers) {
		this.rightAnswers = rightAnswers;
	}

	public String getQuestionsDone() {
		return questionsDone;
	}

	public void setQuestionsDone(String questionsDone) {
		this.questionsDone = questionsDone;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
