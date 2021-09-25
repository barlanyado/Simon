package com.example.simon;

public class Level {

    private final int EASY_ROW = 2;
    private final int EASY_COL = 2;
    private final int MODERATE_ROW = 3;
    private final int MODERATE_COL = 3;
    private final int HARD_ROW = 4;
    private final int HARD_COL = 4;
    private final int EASY_CARDS = 4;
    private final int MODERATE_CARDS = 6;
    private final int HARD_CARDS = 9;
    private final int EASY_SPEED = 4;
    private final int MODERATE_SPEED = 4;
    private final int HARD_SPEED = 4;

    public int ROUND_TIME = 10000; // in milliseconds
    public int YELLOW_LINE = 4;
    public int ORANGE_LINE = 7;

    public int rows;
    public int cols;
    public int speed;
    public int cards;

    public Level(int level) {
        if (level < 4){
            rows = EASY_ROW;
            cols = EASY_COL;
            cards = EASY_CARDS;
        }
        else if (level < 8){
            rows = MODERATE_ROW;
            cols = MODERATE_COL;
            cards = MODERATE_CARDS;
        }
        else{
            rows = HARD_ROW;
            cols = HARD_COL;
            cards = HARD_CARDS;
        }
        speed = 1;
    }
}
