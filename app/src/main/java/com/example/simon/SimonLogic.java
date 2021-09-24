package com.example.simon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.HashMap;


public class SimonLogic extends AppCompatActivity {

    HashMap<Integer, TableRow> rows = new HashMap<>();
    HashMap<Integer, Card> cards = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_logic);
        Level currLevel = new Level(1);
        initTableLayout(currLevel.rows);
        initCardsLayouts(currLevel.cols);
        initColors();
        LinearLayout linearLayout = findViewById(R.id.root_linear_layout);
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
}