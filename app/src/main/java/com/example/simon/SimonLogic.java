package com.example.simon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;


public class SimonLogic extends AppCompatActivity {

    final private int HOLD_TIMER = 4000;  // in milliseconds
    final private int ONE_SECOND = 1000; // in milliseconds
    final private int FIFTHY_MILLISECONDS = 50; // in milliseconds
    private boolean result_succeed = false;
    private float result_seconds = 0;

    private boolean game_mode = false;
    private boolean reverse_mode = false;
    private HashMap<Integer, TableRow> rows = new HashMap<>();
    private HashMap<Integer, Card> cards = new HashMap<>();
    private Round round;
    private Button playBtn;
    private ProgressBar progBar;
    private gameTimer game;
    private Level currLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_logic);

        /* Init all current round parameters */
        int level = getIntent().getIntExtra("level", 1);

        currLevel = new Level(level);
        reverse_mode = currLevel.reverseMode;
        if (reverse_mode)
            Toast.makeText(this, R.string.reverse_toast, Toast.LENGTH_SHORT).show();

        initTableLayout(currLevel.rows);
        initCardsLayouts(currLevel.cols);
        initColors();

        round = new Round(currLevel.cards,cards);
        game = new gameTimer(currLevel.ROUND_TIME, FIFTHY_MILLISECONDS);

        /* Show the round for the user */
        playBtn = findViewById(R.id.play_Btn);
        progBar = findViewById(R.id.progressBar);
        progBar.getProgressDrawable().setColorFilter(
                (getResources().getColor(R.color.green)), android.graphics.PorterDuff.Mode.SRC_IN);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setClickable(false);
                showRound();
            }
        });
    }

    private void activateCards()
    {
        for (int c = 0; c < cards.size(); c++)
        {
            cards.get(c).setEnabled(true);
        }
    }

    private void deactivateCards()
    {
        for (int c = 0; c < cards.size(); c++)
        {
            cards.get(c).setEnabled(false);
        }
    }

    private void initColors()
    {
        for (int c = 0; c < cards.size(); c++)
        {
            Card card = cards.get(c);
            switch (card.getGameColor()) {
                case 0:
                    card.setColor((getResources().getColor(R.color.red)));
                    //card.setSound("do.wav");
                    break;
                case 1:
                    card.setColor((getResources().getColor(R.color.blue)));
                    card.setSound("re.wav");
                    break;
                case 2:
                    card.setColor((getResources().getColor(R.color.green)));
                    card.setSound("mi.wav");
                    break;
                case 3:
                    card.setColor((getResources().getColor(R.color.yellow)));
                    card.setSound("fa.wav");
                    break;
                case 4:
                    card.setColor((getResources().getColor(R.color.purple)));
                    card.setSound("sol.wav");
                    break;
                case 5:
                    card.setColor((getResources().getColor(R.color.pink)));
                    card.setSound("la.wav");
                    break;
                case 6:
                    card.setColor((getResources().getColor(R.color.blue)));
                    card.setSound("si.wav");
                    break;
            }
        }
    }
    private void initTableLayout(int row)
    {
        TableLayout tableLayout = findViewById(R.id.root_table_layout);

        for (int r = 0; r < row; r++)
        {
            TableRow newRow = new TableRow(SimonLogic.this);
            TableLayout.LayoutParams rowLayParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT );
            rowLayParams.weight = 1.0f;
            newRow.setLayoutParams(rowLayParams);
            newRow.setId(View.generateViewId());

            rows.put(r, newRow);
            tableLayout.addView(newRow);
        }
    }

    private void initCardsLayouts(int col)
    {
        int hashCounter = 0;
        for (int r = 0; r < rows.size(); r++)
        {
            TableRow row = rows.get(r);
            for (int c = 0; c < col; c++)
            {
                Card cardView = new Card(SimonLogic.this, null);
                cards.put(hashCounter, cardView);
                row.addView(cardView);
                hashCounter++;
            }
        }
    }

    private void showRound()
    {
            if (round.getRoundQueue().isEmpty()) {
                prePlay();
                return;
            }
            Card card = round.getRoundQueue().poll();
            round.fillValidationQueues(card);
            new CountDownTimer(currLevel.speed, 100) {
                boolean touched = false;
                MotionEvent motionEvent = MotionEvent.obtain(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN,
                        0.0f,
                        0.0f,
                        0
                );
                @Override
                public void onTick(long millisUntilFinished) {
                    card.setEnabled(true);
                    if (!touched) {
                        card.dispatchTouchEvent(motionEvent);
                        touched = true;
                    }
                }

                @Override
                public void onFinish() {
                    motionEvent.setAction(MotionEvent.ACTION_UP);
                    card.dispatchTouchEvent(motionEvent);
                    card.setEnabled(false);
                    showRound();
                }
            }.start();
        }

    private void prePlay()
    {
        new CountDownTimer(FIFTHY_MILLISECONDS * 3, FIFTHY_MILLISECONDS) {
            @Override
            public void onTick(long millisUntilFinished) {
                playBtn.setText(R.string.go);
                playBtn.setBackgroundColor(getResources().getColor(R.color.green));
            }
            @Override
            public void onFinish() {
                game_mode = true;
                activateCards();
                game.start();
            }
        }.start();
    }

    public void cardTouchHandler(Card card)
    {
        if (game_mode)
        {
            Card nextCard = reverse_mode ? round.getReverseValidationQueue().pop() : round.getValidationQueue().poll();
            if (card != nextCard ) {
                playBtn.setText(R.string.wrong);
                deactivateCards();
                game.cancel();
                result_succeed = false;
                finishGame();
            }
            else if (card == nextCard && round.isValidationEmpty())
            {
                playBtn.setText(R.string.excellent);
                deactivateCards();
                game.cancel();
                result_succeed = true;
                result_seconds = progBar.getProgress() / 20;
                finishGame();
            }
        }
    }

    private class gameTimer extends CountDownTimer {
        public gameTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            progBar.setProgress(progBar.getProgress() + 1);
            int newProg = progBar.getProgress();
            if (newProg >= currLevel.ORANGE_LINE * 20) {
                progBar.getProgressDrawable().setColorFilter((getResources().getColor(R.color.orange)), android.graphics.PorterDuff.Mode.SRC_IN);
                playBtn.setBackgroundColor(getResources().getColor(R.color.orange));
                playBtn.setText(R.string.almost_gone);
            }
            else if (newProg >= currLevel.YELLOW_LINE * 20){
                progBar.getProgressDrawable().setColorFilter((getResources().getColor(R.color.yellow)), android.graphics.PorterDuff.Mode.SRC_IN);
                playBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
                playBtn.setText(R.string.hurry_up);
            }


        }

        @Override
        public void onFinish() {
            progBar.setProgress(progBar.getProgress() + 1);
            progBar.getProgressDrawable().setColorFilter((getResources().getColor(R.color.red)), android.graphics.PorterDuff.Mode.SRC_IN);
            playBtn.setBackgroundColor(getResources().getColor(R.color.red));
            playBtn.setText(R.string.out_of_time);
            deactivateCards();
            result_succeed = false;
            finishGame();
        }
    }

    private void finishGame()
    {
        Intent intent = new Intent();
        intent.putExtra("result_succeed", result_succeed);
        intent.putExtra("result_seconds", result_seconds);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishGame();
        super.onBackPressed();
    }
}