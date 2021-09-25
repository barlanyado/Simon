package com.example.simon;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ExhibitionMode extends AppCompatActivity {

    String playerName;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibition_mode);

        playerName = getIntent().getStringExtra("playerName");

        initLauncher();
        Intent intent = new Intent(this,SimonLogic.class);
        intent.putExtra("level", 12);
        resultLauncher.launch(intent);
    }

    private void initLauncher()
    {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK)
                {
                    Intent data = result.getData();
                    boolean result_succeed = data.getBooleanExtra("result_succeed", false);
                    float result_seconds = data.getFloatExtra("result_seconds", 0);
                }
            }

        });
    }
}