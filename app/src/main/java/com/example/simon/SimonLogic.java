package com.example.simon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;


public class SimonLogic extends AppCompatActivity {

    HashMap<Integer, TableRow> rows = new HashMap<>();
    HashMap<Integer, Card> cards = new HashMap<>();
    Round round;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_logic);

        /* Init all current round parameters */

        Level currLevel = new Level(getIntent().getIntExtra("level", 1));
        initTableLayout(currLevel.rows);
        initCardsLayouts(currLevel.cols);
        initColors();
        LinearLayout linearLayout = findViewById(R.id.root_linear_layout);
        round = new Round(currLevel.cards,cards);

        /* Play the round for the user */
        Button playBtn = findViewById(R.id.play_Btn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRound(0);
            }
        });
        //Toast.makeText(this, "Let's play !", Toast.LENGTH_SHORT).show();
    }

    private void initColors()
    {
        for (int c = 0; c < cards.size(); c++)
        {
            Card card = cards.get(c);
            switch (card.getGameColor()) {
                case 0:
                    card.setColor((getResources().getColor(R.color.red)));
                    break;
                case 1:
                    card.setColor((getResources().getColor(R.color.blue)));
                    break;
                case 2:
                    card.setColor((getResources().getColor(R.color.green)));
                    break;
                case 3:
                    card.setColor((getResources().getColor(R.color.yellow)));
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

    public void initCardsLayouts(int col)
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

    public void showRound(int cardID)
    {
            if (cardID == cards.size())
                return;
            Card card = cards.get(cardID);
            new CountDownTimer(500, 100) {
                MotionEvent motionEvent = MotionEvent.obtain(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis() + 100,
                        MotionEvent.ACTION_DOWN,
                        0.0f,
                        0.0f,
                        0
                );
                @Override
                public void onTick(long millisUntilFinished) {
                    card.dispatchTouchEvent(motionEvent);
                }

                @Override
                public void onFinish() {
                    motionEvent.setAction(MotionEvent.ACTION_UP);
                    card.dispatchTouchEvent(motionEvent);
                    showRound(cardID + 1);
                }
            }.start();
        }

}