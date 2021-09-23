package com.example.simon;

import androidx.appcompat.app.AppCompatActivity;
import java.lang.String;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageView letterS = findViewById(R.id.letter_s_view);
        ImageView letterI = findViewById(R.id.letter_i_view);
        ImageView letterM = findViewById(R.id.letter_m_view);
        ImageView letterO = findViewById(R.id.letter_o_view);
        ImageView letterN = findViewById(R.id.letter_n_view);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.title_anim);
        letterS.startAnimation(anim);
        letterI.startAnimation(anim);
        letterM.startAnimation(anim);
        letterO.startAnimation(anim);
        letterN.startAnimation(anim);
    }
}