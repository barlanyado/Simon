package com.example.simon;

public class Level {

    private final int EASY_ROW = 2;
    private final int EASY_COL = 2;
    private final int MODERATE_ROW = 3;
    private final int MODERATE_COL = 3;
    private final int HARD_ROW = 4;
    private final int HARD_COL = 4;
    private final int EXPERT_ROW = 5;
    private final int EXPERT_COL = 5;

    private final int EASY_CARDS = 4;
    private final int MODERATE_CARDS = 6;
    private final int HARD_CARDS = 10;
    private final int EXPERT_CARDS = 13;

    private final int MAX_SPEED = 750;
    private final int MIN_SPEED = 200;


    public int ROUND_TIME = 10000; // in milliseconds
    public int YELLOW_LINE = 4;
    public int ORANGE_LINE = 7;

    private int level;
    public int rows;
    public int cols;
    public int speed;
    public int cards;
    public boolean reverseMode;

    public Level(int level) {
        this.level = level;
        calculateStructure();
        calculateCardsNumber();
        calculateSpeed();
        setReverse();
    }

    private void calculateStructure()
    {
        switch (level){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                rows = EASY_ROW;
                cols = EASY_COL;
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                rows = MODERATE_ROW;
                cols = MODERATE_COL;
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                rows = HARD_ROW;
                cols = HARD_COL;
                break;
            default:
                rows = EXPERT_ROW;
                cols = EXPERT_COL;
                break;
        }
    }

    private void calculateCardsNumber()
    {
        switch (level){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                cards = EASY_CARDS;
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                cards = MODERATE_CARDS;
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                cards = HARD_CARDS;
                break;
            default:
                cards = EXPERT_CARDS;
                break;
        }
    }

    private void calculateSpeed()
    {
        if (level <= 12)
            speed = MAX_SPEED - (45 * level);
        else
            speed = MIN_SPEED;
    }

    private void setReverse()
    {
        switch (level){
            case 5:
            case 6:
            case 7:
            case 8:
            case 13:
            case 14:
            case 15:
            case 16:
            case 21:
            case 22:
            case 23:
            case 24:
                reverseMode = true;
                break;
            default:
                reverseMode = false;
                break;
        }
    }
}
