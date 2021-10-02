package com.example.simon;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.HashMap;

public class LevelsMap extends AppCompatActivity {

    final private int LEVELS_NUMBER = 24;
    final private int STARS_NUMBER = 3;
    final private int LEVELS_IN_ROW = 4;
    final private int ROWS_NUMBER = 6;

    private ActivityResultLauncher<Intent> resultLauncher;
    private HashMap<Integer, RatingBar> ratings = new HashMap<>();
    private HashMap<Integer, Button> buttons = new HashMap<>();
    private HashMap<Integer, TableRow> rows = new HashMap<>();
    private TableLayout tableLayout;
    private SharedPreferences sp;
    private int current_level = 1;
    private boolean result_succeed = false;
    private int result_seconds = 0;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public int dpToPx(Context context, float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_map);
        tableLayout = findViewById(R.id.root_levels_table_layout);
        initLauncher();

        sp = getSharedPreferences("levels", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        //resetMap(editor);
        editor.putBoolean(openedString(1), true);
        editor.putInt(starsString(1), 0);
        editor.commit();

        initMap();

    }

    private void initLauncher()
    {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK)
                {
                    Intent data = result.getData();
                    result_succeed = data.getBooleanExtra("result_succeed", false);
                    result_seconds = (int)data.getFloatExtra("result_seconds", 0);
                }
            }

        });
    }

    private void initMap()
    {
        // Init table rows
        for (int r = 0; r < ROWS_NUMBER; r++)
        {
            TableRow newRow = new TableRow(this);
            TableLayout.LayoutParams rowLayParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT );
            rowLayParams.weight = 1.0f;
            newRow.setLayoutParams(rowLayParams);
            newRow.setId(View.generateViewId());
            rows.put(r, newRow);
            tableLayout.addView(newRow);
        }


        int hashCounter = 0;
        for (int r = 0; r < ROWS_NUMBER; r++)
        {
            TableRow row = rows.get(r);
            for (int l = 0; l < LEVELS_IN_ROW; l++)
            {
                TableRow.LayoutParams levelsRow_params = new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                levelsRow_params.setMargins(dpToPx(this,8),dpToPx(this,10),dpToPx(this,8),0);
                levelsRow_params.gravity = Gravity.CENTER;

                LinearLayout cellLayout = new LinearLayout(this);
                cellLayout.setLayoutParams(levelsRow_params);
                cellLayout.setOrientation(LinearLayout.VERTICAL);

                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(10);
                gd.setColor(Color.GRAY);

                TableRow.LayoutParams buttons_params = new TableRow.LayoutParams(dpToPx(this,40), dpToPx(this,40));
                buttons_params.setMargins(0,0,0,0);
                buttons_params.gravity = Gravity.CENTER;
                Button btn = new Button(this);
                btn.setText(String.valueOf(hashCounter + 1));
                btn.setLayoutParams(buttons_params);
                btn.setId(View.generateViewId());
                btn.setBackground(gd);
                btn.setTextColor(Color.WHITE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_level = Integer.valueOf(String.valueOf(((Button)v).getText()));
                        Intent intent = new Intent(LevelsMap.this, SimonLogic.class);
                        int level = Integer.valueOf(String.valueOf(((Button)v).getText()));
                        if (level <= LEVELS_NUMBER / 2)
                            intent.putExtra("level", Integer.valueOf(String.valueOf(((Button)v).getText())));
                        else{
                            intent.putExtra("level", Integer.valueOf(String.valueOf(((Button)v).getText())) - (LEVELS_NUMBER / 2));
                            intent.putExtra("reverse", true);
                        }
                        resultLauncher.launch(intent);
                    }
                });
                btn.setClickable(true);
                buttons.put(hashCounter, btn);
                cellLayout.addView(btn);

                TableRow.LayoutParams rb_params = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                rb_params.setMargins(dpToPx(this,5),dpToPx(this,15),dpToPx(this,5),0);
                rb_params.gravity = Gravity.CENTER;
                RatingBar rb = new RatingBar(this,null,R.attr.ratingBarStyleSmall);
                rb.setId(View.generateViewId());
                rb.setLayoutParams(rb_params);
                rb.setNumStars(STARS_NUMBER);
                rb.setStepSize(1);

                ratings.put(hashCounter, rb);
                cellLayout.addView(rb);

                row.addView(cellLayout);
                hashCounter++;
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (result_succeed)
        {
            sp = getSharedPreferences("levels", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(starsString(current_level), calculateScore());
            editor.putBoolean(openedString(current_level + 1), true);
            editor.commit();
        }

        sp = getSharedPreferences("levels", MODE_PRIVATE);
        for (int l = 0; l < LEVELS_NUMBER; l++)
        {
            if (sp.getBoolean(openedString(l + 1), false))
            {
                Button btn = buttons.get(l);
                btn.setClickable(true);

                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(10);
                gd.setStroke(4, Color.WHITE);
                btn.setBackground(gd);

                RatingBar rating = ratings.get(l);
                float rate = sp.getInt(starsString(l + 1), 0);
                rating.setRating(rate);

                LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();

                switch ((int)rate){
                    case 1:
                        stars.getDrawable(2).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
                        break;
                    case 2:
                        stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        break;
                    case 3:
                        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                        break;
                }
            }
        }
    }



    private String openedString(int level)
    {
        return ("level_" + String.valueOf(level) + "_opened");
    }

    private String starsString(int level)
    {
        return ("level_" + String.valueOf(level) + "_stars");
    }

    private int calculateScore()
    {
        if (result_seconds < 3)
            return  3;
        else if (result_seconds < 7)
            return 2;
        else if (result_seconds < 10)
            return 1;
        else
            return 0;
    }

    private void resetMap(SharedPreferences.Editor editor)
    {
        for (int i = 1; i <= LEVELS_NUMBER; i++)
        {
            editor.putBoolean(openedString(i), false);
            editor.putInt(starsString(i), 0);
        }
        editor.commit();
    }

}