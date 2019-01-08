package utils;

public class Consts {
	
	//nubmer of rows and cols
	public static final int SIZE = 21;
	//each block in the grid is GRID_SIZE x GRID SIZE
	public static final int GRID_SIZE = 32;
	//map offset in the y direction
	public static final int MAP_Y_OFFSET = 64;
	public static final int STARTING_LIFE = 3;
	public static final int STARTING_SCORE = 0;
	public static final int YELLOW_CANDY_SCORE = 1;
	public static final int SILVER_CANDY_SCORE = 50;
	public static final int EASY_QUESTION_SCORE = 100;
	public static final int AVERAGE_QUESTION_SCORE = 200;
	public static final int DIFFICULT_QUESTION_SCORE = 500;
	public static final int WRONG_EASY_ANSWER_SCORE = -250;
	public static final int WRONG_AVERAGE_ANSWER_SCORE = -100;
	public static final int WRONG_DIFFICULT_ANSWER_SCORE = -50;
	public static final int MAZE_COMPLETION_BOUNS = 100;
	public static final int SINGLE_QUESTIONS_NUM = 4;
	//maze text file indicators
	public static final int BASIC_CANDY = 2;
	public static final int SILVER_CANDY = 3;
	public static final int QUESTION_CANDY = 4;
	public static final int POISON_CANDY = 5;
	public static final int PACMAN = 6;
	public static final int BASIC_GHOST = 7;
	public static final int SMART_GHOST = 9;
	//Candy dimensions
	public static final int BASIC_CANDY_X = 14;
	public static final int BASIC_CANDY_Y = 13;
	public static final int BASIC_CANDY_WIDTH = 8;
	public static final int BASIC_CANDY_HEIGHT = 8;
	public static final int SILVER_CANDY_X = 9;
	public static final int SILVER_CANDY_Y = 9;
	public static final int SILVER_CANDY_WIDTH = 16;
	public static final int SILVER_CANDY_HEIGHT = 16;
	public static final int POSION_CANDY_X = 7;
	public static final int POSION_CANDY_Y = 8;
	public static final int POSION_CANDY_WIDTH = 18;
	public static final int POSION_CANDY_HEIGHT = 18;
	public static final int QUESTION_CANDY_X = 8;
	public static final int QUESTION_CANDY_Y = 9;
	public static final int QUESTION_CANDY_WIDTH = 16;
	public static final int QUESTION_CANDY_HEIGHT = 16;
	//Ghost dimensions
	public static final int GHOST_WIDTH = 32;
	public static final int GHOST_HEIGHT = 32;
	//Pacman dimensions
	public static final int PACMAN_WIDTH = 32;
	public static final int PACMAN_HEIGHT = 32;
	//objects speed (in pixels per frame)
	public static final int PACMAN_SPEED = 2;
	public static final int GHOST_SPEED = 1;
	//movment
	public static final int UP = 8;
	public static final int DOWN = 2;
	public static final int LEFT = 4;
	public static final int RIGHT = 6;
	//round time in multiplayer mode
	public static final int ROUND_TIME = 40;
	// number of candies on maze
	public static final int JUNGLE_CANDIES = 203; //203
	public static final int NORMAL_CANDIES = 195; //195
	public static final int SPACE_CANDIES = 202; //202
}
