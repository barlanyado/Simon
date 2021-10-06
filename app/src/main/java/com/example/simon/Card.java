package com.example.simon;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
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

    private final int DOWN = 1;
    private final int UP = 0;

    private MediaPlayer mMediaPlayer;
    private String sound;
    private int gameColor;
    private boolean hidden;
    private static GradientDrawable cardHideShape;
    private SimonLogic parentActivity;
    private int lastAction;
    public static boolean soundAllowed;

    public Card(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gameColor = 0;
        hidden = true;
        parentActivity = (SimonLogic) context;
        mMediaPlayer = new MediaPlayer();
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
        this.lastAction = UP;

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Card b = findViewById(v.getId());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (b.lastAction == UP) {
                            b.lastAction = DOWN;
                            b.setBackgroundColor(b.getGameColor());
                            if (soundAllowed) {
                                b.mMediaPlayer = MediaPlayer.create(parentActivity, b.getSoundID());
                                b.mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                b.mMediaPlayer.setLooping(false);
                                b.mMediaPlayer.start();
                            }
                            parentActivity.cardTouchHandler(b);
                            Log.i("ActionDOwnEvent", "HERE!");
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        b.lastAction = UP;
                        b.setHidden();
                        break;
                }
                return true;
            }
        });
    }

    public void setSound(String sound)
    {
        this.sound = sound;
    }
    public static int randomColor()
    {
        Random random = new Random();
        return random.nextInt(7);
    }


    private int getSoundID()
    {
        switch (this.sound){
            case "do_.wav":
                return R.raw.do_;
            case "re.wav":
                return R.raw.re;
            case "si.wav":
                return R.raw.si;
            case "mi.wav":
                return R.raw.mi;
            case "fa.wav":
                return R.raw.fa;
            case "sol.wav":
                return R.raw.sol;
            case "la.wav":
                return R.raw.la;
        }
        return -1;
    }

}
