package com.example.simon;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Card extends Button {

    private int gameColor;
    private boolean hidden;
    private static GradientDrawable cardHideShape;
    private SimonLogic parentActivity;
    public Card(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gameColor = 0;
        hidden = true;
        parentActivity = (SimonLogic) context;
        initCard();

    }

    private void initCardHiddenShape()
    {
        cardHideShape = new GradientDrawable();
        cardHideShape.setShape(GradientDrawable.RECTANGLE);
        cardHideShape.setCornerRadius(15);
        cardHideShape.setColor(getResources().getColor(R.color.transparent));
        cardHideShape.setStroke(10, getResources().getColor(R.color.white));
    }

    public void setHidden()
    {
        this.setBackground(cardHideShape);
        this.hidden = true;
    }

    public void setColor(int color)
    {
        this.gameColor = color;
    }

    public int getGameColor()
    {
        return this.gameColor;
    }

    private void initCard()
    {
        TableRow.LayoutParams cardLayParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        cardLayParams.setMargins(10, 10, 10, 10);
        cardLayParams.weight = 1.0f;
        this.setLayoutParams(cardLayParams);
        this.setId(View.generateViewId());
        this.setPadding(10, 10, 10, 10);
        initCardHiddenShape();
        this.setHidden();
        this.setColor(randomColor());
        this.setEnabled(false); // set card untouchable at first

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Card b = findViewById(v.getId());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        b.setBackgroundColor(b.getGameColor());
                        parentActivity.cardTouchHandler(b);
                        break;
                    case MotionEvent.ACTION_UP:
                        b.setHidden();
                        break;
                }
                return true;
            }
        });
    }

    public static int randomColor()
    {
        Random random = new Random();
        return random.nextInt(4);
    }
}
