package controller;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import model.AnswerGhost;
import model.BasicCandy;
import model.GameObject;
import utils.Key;

public class Handler {
	// all of the game objects
	private LinkedList<GameObject> gameObjects;
	// all answer ghosts the relevant to a specific game
	private LinkedList<AnswerGhost> answerGhosts;
	// used to iterate over the different objects
	private ListIterator<GameObject> iterator;
	private ListIterator<AnswerGhost> iterator1;
	// a map that contains the candies. the key is a set of both the x and the y
	// location of the object
	private HashMap<Key, BasicCandy> candyMap;

	public Handler() {
		gameObjects = new LinkedList<>();
		answerGhosts = new LinkedList<>();
		candyMap = new HashMap<>();
	}

	/**
	 * calls the 'tick' method in all the objects
	 */
	public void tick() {
		for (iterator = gameObjects.listIterator(); iterator.hasNext();) {
			GameObject temp = iterator.next();
			temp.tick();
		}
		for (iterator1 = answerGhosts.listIterator(); iterator1.hasNext();) {
			AnswerGhost temp = iterator1.next();
			temp.tick();
		}
	}

	/**
	 * calls the 'render' method in all the objects
	 * 
	 * @param g
	 */
	public void render(Graphics g) {
		for (iterator = gameObjects.listIterator(); iterator.hasNext();) {
			GameObject temp = iterator.next();
			temp.render(g);
		}

		for (iterator1 = answerGhosts.listIterator(); iterator1.hasNext();) {
			AnswerGhost temp = iterator1.next();
			temp.render(g);
		}
	}

	/**
	 * adds a object to the game
	 * 
	 * @param obj
	 */
	public void addGameObject(GameObject obj) {
		gameObjects.add(obj);
	}

	/**
	 * removes an object from the game
	 * 
	 * @param obj
	 */
	public void removeGameObject(GameObject obj) {
		gameObjects.remove(obj);
	}

	public LinkedList<GameObject> getGameObjects() {
		return gameObjects;
	}

	public LinkedList<AnswerGhost> getAnswerGhosts() {
		return answerGhosts;
	}

	public GameObject getMapCandy(Key key) {
		return candyMap.get(key);
	}

	public void addMapCandy(Key key, BasicCandy obj) {
		candyMap.put(key, obj);
	}

	public HashMap<Key, BasicCandy> getCandyMap() {
		return candyMap;
	}

}
