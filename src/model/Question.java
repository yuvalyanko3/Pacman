package model;

import java.util.ArrayList;

public class Question {

	private String question;
	private String team;
	private Answer answer;
	private String level;
	private boolean isAssigned;
	private ArrayList<AnswerGhost> ghosts;
	private int correctScore;
	private int wrongScore;

	public Question(String question) {
		super();
		this.question = question;
	}

	public Question(String question, ArrayList<String> answers, String correctAnswer, String team, String level) {
		this.question = question;
		this.team = team;
		this.level = level;
		this.isAssigned = false;
		this.answer = new Answer(answers, correctAnswer);
	}

	public String getQuestionString() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public String getCorrectAnswer() {
		return answer.correctAnswer;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public boolean isAssigned() {
		return isAssigned;
	}

	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public ArrayList<AnswerGhost> getGhosts() {
		return ghosts;
	}

	public void setGhosts(ArrayList<AnswerGhost> ghosts) {
		this.ghosts = ghosts;
	}

	public int getCorrectScore() {
		return correctScore;
	}

	public void setCorrectScore(int correctScore) {
		this.correctScore = correctScore;
	}

	public int getWrongScore() {
		return wrongScore;
	}

	public void setWrongScore(int wrongScore) {
		this.wrongScore = wrongScore;
	}

	/**
	 * checks the user answer
	 * 
	 * @param answer
	 * @return true if correct, false otherwise
	 */
	public boolean isCorrect(String answer) {
		return this.answer.isCorrect(answer);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((question == null) ? 0 : question.hashCode());
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
		Question other = (Question) obj;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}

	public class Answer {
		private ArrayList<String> answers;
		private String correctAnswer;

		public Answer(ArrayList<String> answers, String correctAnswer) {
			this.answers = answers;
			this.correctAnswer = correctAnswer;
		}

		public ArrayList<String> getAnswers() {
			return answers;
		}

		public void setAnswers(ArrayList<String> answers) {
			this.answers = answers;
		}

		/**
		 * checks the user answer
		 * 
		 * @param answer
		 * @return true if correct, false otherwise
		 */
		private boolean isCorrect(String answer) {
			return answer.equals(correctAnswer);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			int i = 1;
			for (String a : answers) {
				if (!a.isEmpty()) {
					sb.append((i++) + ". " + a + ".\n");
					sb.append("\n");
				}
			}

			return sb.toString();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(question);
		return sb.toString();
	}
}
