package com.example.simon;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Round {

    LinkedBlockingQueue<Card> roundQueue;
    LinkedBlockingQueue<Card> validationQueue;
    HashMap<Integer, Card> cards;
    Integer cardsNumber;
    Integer roundSize;

    public Round(int roundSize, int cardsNumber, HashMap<Integer, Card> cards)
    {
        roundQueue = new LinkedBlockingQueue<>();
        validationQueue = new LinkedBlockingQueue<>();
        this.cards = cards;
        this.cardsNumber = cardsNumber;
        this.roundSize = roundSize;
    }

    public void randomizeRound()
    {
        for (int i = 0; i < roundSize; i++)
        {
            Random random = new Random();
            Card card = cards.get(random.nextInt(cardsNumber));
            roundQueue.add(card);
            validationQueue.add(card);
        }
    }
}
