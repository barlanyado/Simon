package com.example.simon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.String;
import java.util.concurrent.TimeUnit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.title_anim);
        letterS.startAnimation(anim);
        letterI.startAnimation(anim);
        letterM.startAnimation(anim);
        letterO.startAnimation(anim);
        letterN.startAnimation(anim);

        Button startExhBtn = findViewById(R.id.start_exh_Button);
        Button startCareerBtn = findViewById(R.id.start_car_Button);

        startCareerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, LevelsMap.class);
                //intent.putExtra("playerName", inputName.getText().toString());
                startActivity(intent); // variable to collect user input
            }
        });

        startExhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputName = new EditText(Menu.this);
                new AlertDialog.Builder(Menu.this).setTitle("Enter name")
                        .setView(inputName)
                        .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel(); // closes dialog
                                Intent intent = new Intent(Menu.this, ExhibitionMode.class);
                                intent.putExtra("playerName", inputName.getText().toString());
                                startActivity(intent); // variable to collect user input
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel(); // closes dialog
                            }
                        })
                        .show();
            }
        });

        Button recordsExhBtn = findViewById(R.id.table_Button);
        recordsExhBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, RecordsTable.class);
                startActivity(intent);
            }
        });
    }
}