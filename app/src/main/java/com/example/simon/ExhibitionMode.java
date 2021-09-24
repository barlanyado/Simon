package com.example.simon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ExhibitionMode extends AppCompatActivity {

    String playerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibition_mode);

        playerName = getIntent().getStringExtra("playerName");
        Intent intent = new Intent(this,SimonLogic.class);
        startActivity(intent); // variable to collect user input
    }
}