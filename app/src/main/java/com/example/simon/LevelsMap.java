package com.example.simon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;

public class LevelsMap extends AppCompatActivity {
    int level_number = 12;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public int dpToPx(Context context, float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels_map);

        LinearLayout levels_layout = findViewById(R.id.levels);

        LinearLayout.LayoutParams levelsRow_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        levelsRow_params.setMargins(dpToPx(this,5),dpToPx(this,10),dpToPx(this,5),0);

        LinearLayout.LayoutParams ratingRow_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ratingRow_params.setMargins(dpToPx(this,5),dpToPx(this,10),0,0);

        LinearLayout.LayoutParams buttons_params = new LinearLayout.LayoutParams(dpToPx(this,50), dpToPx(this,50));
        buttons_params.setMargins(dpToPx(this,25),dpToPx(this,10),dpToPx(this,20),0);

        LinearLayout levels_row = new LinearLayout(this);
        levels_row.setLayoutParams(levelsRow_params);
        levels_row.setOrientation(LinearLayout.HORIZONTAL);

        levels_layout.addView(levels_row);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(10);
        gd.setStroke(4, Color.WHITE);

        Button btn = new Button(this);
        btn.setText("1");
        btn.setTextColor(Color.WHITE);
        btn.setLayoutParams(buttons_params);
        btn.setId(1);
        btn.setBackground(gd);
        levels_row.addView(btn);

        LinearLayout rating_row = new LinearLayout(this);
        rating_row.setLayoutParams(ratingRow_params);
        rating_row.setOrientation(LinearLayout.HORIZONTAL);

        levels_layout.addView(rating_row);

        LinearLayout.LayoutParams rb_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        rb_params.setMargins(dpToPx(this,25),0,dpToPx(this,23),0);


        RatingBar rb = new RatingBar(this,null,R.attr.ratingBarStyleSmall);
        rb.setLayoutParams(rb_params);
        rb.setNumStars(3);
        rating_row.addView(rb);
        rb.setId(101);
        int i = 2;

        for(; i <= level_number; i++)
        {
            if(i % 4 == 1)
            {
                levels_row = new LinearLayout(this);
                levels_row.setLayoutParams(levelsRow_params);
                levels_row.setOrientation(LinearLayout.HORIZONTAL);

                rating_row = new LinearLayout(this);
                rating_row.setLayoutParams(ratingRow_params);
                rating_row.setOrientation(LinearLayout.HORIZONTAL);

                levels_layout.addView(levels_row);
                levels_layout.addView(rating_row);
            }
            gd = new GradientDrawable();
            gd.setCornerRadius(10);
            gd.setColor(Color.GRAY);

            btn = new Button(this);
            btn.setText(String.valueOf(i));
            btn.setLayoutParams(buttons_params);
            btn.setId(i);
            btn.setBackground(gd);
            btn.setTextColor(Color.WHITE);
            btn.setClickable(false);
            levels_row.addView(btn);

            rb = new RatingBar(this,null,R.attr.ratingBarStyleSmall);
            rb.setLayoutParams(rb_params);
            rb.setNumStars(3);
            rb.setId((100 + i));
            rating_row.addView(rb);

        }
    }
}