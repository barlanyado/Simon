package com.example.simon;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class Round {

    private LinkedBlockingQueue<Card> roundQueue;
    private LinkedBlockingQueue<Card> validationQueue;
    private Stack<Card> reverseValidationQueue;
    private HashMap<Integer, Card> cards;
    private Integer cardsNumber;
    private Integer roundLength;

    public Round(int roundLength, HashMap<Integer, Card> cards)
    {
        roundQueue = new LinkedBlockingQueue<>();
        validationQueue = new LinkedBlockingQueue<>();
        reverseValidationQueue = new Stack<>();
        this.cards = cards;
        this.cardsNumber = cards.size();
        this.roundLength = roundLength;
        randomizeRound();
    }

    private void randomizeRound()
    {
        for (int i = 0; i < roundLength; i++)
        {
            SecureRandom random = new SecureRandom();
            int num =  random.nextInt((cardsNumber + 1) * 1000000);
            num += num < 1000000 ? 1000000 : 0;
            Card card = cards.get((num / 1000000) - 1);
            roundQueue.add(card);

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
    public Stack<Card> getReverseValidationQueue() { return reverseValidationQueue; }

    public boolean isValidationEmpty()
    {
        return reverseValidationQueue.isEmpty() || validationQueue.isEmpty();
    }
    public void fillValidationQueues(Card card)
    {
        validationQueue.add(card);
        reverseValidationQueue.push(card);
    }

}
