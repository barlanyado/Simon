package com.example.simon;

public class Level {

    private final int EASY_ROW = 2;
    private final int EASY_COL = 2;
    private final int MODERATE_ROW = 3;
    private final int MODERATE_COL = 3;
    private final int HARD_ROW = 4;
    private final int HARD_COL = 4;
    private final int STARTING_TIMER = 0;
    private final int STARTING_CARDS = 4;

    public int rows;
    public int cols;
    public int timer;
    public int cards;

    public Level(int level) {
        if (level < 4){
            rows = EASY_ROW;
            cols = EASY_COL;
        }
        else if (level < 8){
            rows = MODERATE_ROW;
            cols = MODERATE_COL;
        }
        else{
            rows = HARD_ROW;
            cols = HARD_COL;
        }
        timer = 1;
        cards = STARTING_CARDS;
    }
}
