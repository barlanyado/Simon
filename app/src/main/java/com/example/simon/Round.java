package com.example.simon;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Round {

    LinkedBlockingQueue<Card> roundQueue;
    LinkedBlockingQueue<Card> validationQueue;
    HashMap<Integer, Card> cards;
    Integer cardsNumber;
    Integer roundLength;

    public Round(int roundLength, HashMap<Integer, Card> cards)
    {
        roundQueue = new LinkedBlockingQueue<>();
        validationQueue = new LinkedBlockingQueue<>();
        this.cards = cards;
        this.cardsNumber = cards.size();
        this.roundLength = roundLength;
        randomizeRound();
    }

    private void randomizeRound()
    {
        for (int i = 0; i < roundLength; i++)
        {
            Random random = new Random();
            Card card = cards.get(random.nextInt(cardsNumber));
            roundQueue.add(card);
            validationQueue.add(card);
        }
    }

    public LinkedBlockingQueue<Card> getRoundQueue()
    {
        return roundQueue;
    }
    public LinkedBlockingQueue<Card> getValidationQueue()
    {
        return validationQueue;
    }
}
