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

    private final int ONE_TO_THREE_CARDS = 4;
    private final int FOUR_TO_SEVEN_CARDS = 6;
    private final int EIGHT_TO_TEN_CARDS = 10;
    private final int ELEVEN_TO_UNLIMITED_CARDS = 13;

    private final int MAX_SPEED = 750;
    private final int MIN_SPEED = 150;


    public int ROUND_TIME = 10000; // in milliseconds
    public int YELLOW_LINE = 4;
    public int ORANGE_LINE = 7;

    private int level;
    public int rows;
    public int cols;
    public int speed;
    public int cards;

    public Level(int level) {
        this.level = level;
        calculateStructure();
        calculateCardsNumber();
        speed = 250;

    }

    private void calculateStructure()
    {
        switch (level){
            case 1:
            case 2:
            case 3:
                rows = EASY_ROW;
                cols = EASY_COL;
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                rows = MODERATE_ROW;
                cols = MODERATE_COL;
                break;
            case 8:
            case 9:
            case 10:
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
                cards = ONE_TO_THREE_CARDS;
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                cards = FOUR_TO_SEVEN_CARDS;
                break;
            case 8:
            case 9:
            case 10:
                cards = EIGHT_TO_TEN_CARDS;
                break;
            default:
                cards = ELEVEN_TO_UNLIMITED_CARDS;
                break;
        }
    }

    private void calculateSpeed()
    {
        if (level <= 12)
            speed = MAX_SPEED - (50 * level);
        else
            speed = MIN_SPEED;
    }
}
