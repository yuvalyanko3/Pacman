package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.GameHistory;
import model.Player;
import model.Question;

public class SysData {
	private Map<String, Question> quMap = new HashMap<>();
	private ArrayList<Question> questions = new ArrayList<>();
	private Map<Player, Integer> gameScores = new HashMap<>();
	private ArrayList<Question> currentGameQuestions = new ArrayList<>();
	private ArrayList<GameHistory> gamesHistory = new ArrayList<>();

	private static SysData instance;

	public static SysData getInstance() {
		if (instance == null)
			instance = new SysData();
		return instance;
	}

	/**
	 * Read json file
	 */
	@SuppressWarnings("unchecked")
	public void readJson() {
		try {
			ArrayList<String> answers = new ArrayList<>();
			String question = "";
			String correctAnswer = "";
			String level = "";
			String team = "";
			JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader("assets/questions/q.json"));
			JSONArray arr = (JSONArray) obj.get("questions");
			Iterator<JSONObject> iterator = arr.iterator();
			while (iterator.hasNext()) {
				JSONObject temp = iterator.next();
				question = (String) temp.get("question");
				correctAnswer = (String) temp.get("correct_ans");
				level = (String) temp.get("level");
				team = (String) temp.get("team");
				JSONArray tempAnswers = (JSONArray) temp.get("answers");
				Iterator<String> iterator2 = tempAnswers.iterator();
				while (iterator2.hasNext())
					answers.add(iterator2.next());
				questions.add(new Question(question, new ArrayList<>(answers), correctAnswer, team, level));
				Question tempQ = new Question(question, new ArrayList<>(answers), correctAnswer, team, level);
				quMap.put(question, tempQ);
				answers.clear();
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add question to json file
	 * 
	 * @param question
	 * @param answers
	 * @param correctAnswer
	 * @param level
	 * @return true if question was added, false otherwise
	 */
	@SuppressWarnings("unchecked")
	public boolean addQuestion(String question, ArrayList<String> answers, String correctAnswer, String level) {
		if (question == "" || answers.isEmpty() || answers.size() < 2 || correctAnswer == "" || level == "")
			return false;
		if (this.getQuestions().contains(new Question(question))) {
			this.getQuestions().remove(this.getQuestions().indexOf(new Question(question)));
			deleteQuestion(question);
		}
		try {
			JSONObject q = (JSONObject) new JSONParser().parse(new FileReader("assets/questions/q.json"));
			JSONArray arr = (JSONArray) q.get("questions");
			JSONObject obj = new JSONObject();
			obj.put("question", question);
			// create the new question
			JSONArray temp = new JSONArray();
			for (String a : answers)
				temp.add(a);
			obj.put("answers", temp);
			obj.put("correct_ans", correctAnswer);
			obj.put("level", level);
			obj.put("team", "Zebra");
			arr.add(obj);
			// create a new json object to hold the array and the array key
			JSONObject toAdd = new JSONObject();
			toAdd.put("questions", arr);
			// write json object to file
			FileWriter file = new FileWriter("assets/questions/q.json", false);
			file.write(toAdd.toJSONString());
			file.flush();
			file.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * delete question from json file
	 * 
	 * @param question
	 * @return true if question was deleted, false otherwise
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteQuestion(String question) {
		if (question.equals("") || !quMap.containsKey(question))
			return false;
		JSONObject q;
		try {
			q = (JSONObject) new JSONParser().parse(new FileReader("assets/questions/q.json"));
			JSONArray arr = (JSONArray) q.get("questions");
			Iterator<JSONObject> iterator = arr.iterator();
			while (iterator.hasNext()) {
				JSONObject temp = iterator.next();
				if (temp.get("question").equals(question)) {
					arr.remove(temp);
					JSONObject toAdd = new JSONObject();
					toAdd.put("questions", arr);
					FileWriter file = new FileWriter("assets/questions/q.json", false);
					file.write(toAdd.toJSONString());
					file.flush();
					file.close();
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * get random question from json file to the current list
	 * 
	 * @return Question object
	 */
	public Question addRandomQuestion() {
		Random rand = new Random();
		Question q = getQuestions().get(rand.nextInt(questions.size()));
		// this loop is to make sure there are no duplicate questions
		while (currentGameQuestions.contains(q) && currentGameQuestions.size() != questions.size())
			q = getQuestions().get(rand.nextInt(questions.size()));
		currentGameQuestions.add(q);
		return q;
	}

	/**
	 * get random question from the relevant game
	 * 
	 * @return Question object
	 */
	public Question getRandomGameQuestion() {
		Random rand = new Random();
		Question q = currentGameQuestions.get(rand.nextInt(currentGameQuestions.size()));
		// this loop is to make sure there are no duplicate questions
		while (q.isAssigned())
			q = currentGameQuestions.get(rand.nextInt(currentGameQuestions.size()));
		q.setAssigned(true);
		return q;
	}

	/**
	 * Adds new history game record
	 * 
	 * @param name
	 *            of player
	 * @param date
	 *            of the game
	 * @param finalScore
	 *            at the end of the game
	 * @param rightAnswers
	 *            answered by player
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean addHistoryGame(String number, String name, String date, String questionsDone, String finalScore,
			String rightAnswers) {
		try {
			if (name == "" || date == "" || finalScore == "" || rightAnswers == "" || questionsDone == "")
				return false;
			JSONObject history = (JSONObject) new JSONParser().parse(new FileReader("assets/history/history.json"));
			JSONArray gamesarr = (JSONArray) history.get("gamesHistory");
			// create new history game record
			JSONObject obj = new JSONObject();
			obj.put("number", number);
			obj.put("date", date);
			obj.put("name", name);
			obj.put("finalScore", finalScore);
			obj.put("questionsDone", questionsDone);
			obj.put("rightAnswers", rightAnswers);
			gamesarr.add(obj);
			// create a new json object to hold the array and the array key
			JSONObject toAdd = new JSONObject();
			toAdd.put("gamesHistory", gamesarr);
			// write json object to file
			FileWriter file = new FileWriter("assets/history/history.json", false);
			file.write(toAdd.toJSONString());
			file.flush();
			file.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Read games history json file
	 */
	@SuppressWarnings("unchecked")
	public void readHistoryJson() {
		try {
			String number = "";
			String name = "";
			String date = "";
			String finalScore = "";
			String questionsDone = "";
			String rightAnswers = "";
			JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader("assets/history/history.json"));
			JSONArray HistoryArr = (JSONArray) obj.get("gamesHistory");
			Iterator<JSONObject> iterator = HistoryArr.iterator();
			gamesHistory.clear();
			while (iterator.hasNext()) {
				JSONObject temp = iterator.next();
				number = (String) temp.get("number");
				name = (String) temp.get("name");
				date = (String) temp.get("date");
				finalScore = (String) temp.get("finalScore");
				rightAnswers = (String) temp.get("rightAnswers");
				questionsDone = (String) temp.get("questionsDone");
				gamesHistory.add(new GameHistory(number, name, date, finalScore, questionsDone, rightAnswers));
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read & check json for last game number
	 * 
	 * @return last game number
	 */
	public String maxGameNumber() {
		String max = String.valueOf(0);
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader("assets/history/history.json"));
			JSONArray HistoryArr = (JSONArray) obj.get("gamesHistory");
			if (!HistoryArr.isEmpty()) {
				for (int i = 0; i < HistoryArr.size(); i++) {
					JSONObject hg = (JSONObject) HistoryArr.get(i);
					String number = hg.get("number").toString();
					if (Integer.parseInt(number) >= Integer.parseInt(max))
						max = number;
				}
			}
			else
				return max;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return max;
	}

	/**
	 * get the top 10 game history
	 * 
	 * @return
	 */
	public ArrayList<GameHistory> getTop10() {
		ArrayList<GameHistory> h = SysData.getInstance().getGamesHistory();
		ArrayList<GameHistory> top = new ArrayList<>();
		Collections.sort(h, new Comparator<GameHistory>() {
			@Override
			public int compare(GameHistory g1, GameHistory g2) {
				if (Integer.valueOf(g1.getFinalScore()) > Integer.valueOf(g2.getFinalScore()))
					return -1;
				if (Integer.valueOf(g1.getFinalScore()) < Integer.valueOf(g2.getFinalScore()))
					return 1;
				return 0;
			}
		});
		for (int i = 0; i < 10; i++) {
			if (i < h.size())
				top.add(h.get(i));
		}
		return top;
	}

	public ArrayList<Question> getQuestions() {
		questions.clear();
		readJson();
		return questions;
	}

	public Map<String, Question> getQuMap() {
		return quMap;
	}

	public ArrayList<GameHistory> getGamesHistory() {
		gamesHistory.clear();
		readHistoryJson();
		return gamesHistory;
	}

	public void setGamesHistory(ArrayList<GameHistory> gamesHistory) {
		this.gamesHistory = gamesHistory;
	}
	
	public ArrayList<Question> getGameQuestions() {
		return currentGameQuestions;
	}

	public Map<Player, Integer> getGameScores() {
		return gameScores;
	}

	public void setGameScores(Map<Player, Integer> gameScores) {
		this.gameScores = gameScores;
	}

	public void clear() {
		currentGameQuestions.clear();
	}
}
