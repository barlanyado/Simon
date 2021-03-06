package com.example.simon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExhibitionMode extends AppCompatActivity {

    private String playerName;
    private ActivityResultLauncher<Intent> resultLauncher;
    private boolean result_succeed;
    private HashMap<String,Object> record = new HashMap<>();
    private float result_seconds;
    private int current_level;
    private SharedPreferences emSP;
    private int current_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibition_mode);
        playerName = getIntent().getStringExtra("playerName");
        record.put("Name",playerName);
        record.put("Level",1);
        record.put("Rate",0);
        record.put("Score",0);
        result_succeed = true;
        current_level = 0;
        current_score = 0;
        initLauncher();

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
                    result_seconds = data.getFloatExtra("result_seconds", 0);
                }
            }
        });
    }

    private void callLevel(int current_level){
        Intent intent = new Intent(this,SimonLogic.class);
        intent.putExtra("level", current_level);
        resultLauncher.launch(intent);
    }

    private int calculateScore()
    {
        if (result_seconds < 2.5)
            return  3;
        else if (result_seconds < 6)
            return 2;
        else if (result_seconds < 10)
            return 1;
        else
            return 0;
    }

    private List<HashMap<String, Object>> sortRecords(List<HashMap<String, Object>> record_list){
        HashMap<String,Object> tmp1 = null;
        HashMap<String,Object> tmp2 = null;
        int i;
        for (i = 0; i < record_list.size(); i++)
        {
            if (((int)record.get("Score") > (int)record_list.get(i).get("Score")) || ((int)record.get("Score") == (int)record_list.get(i).get("Score") && (int)record.get("Level") < (int)record_list.get(i).get("Level"))) {
                tmp1 = record_list.get(i);
                record.put("Rate",i+1);
                record_list.set(i, record);
                i++;
                while (i < record_list.size()) {
                    tmp1.put("Rate",i+1);
                    tmp2 = record_list.get(i);
                    record_list.set(i, tmp1);
                    tmp1 = tmp2;
                    i++;
                }
                tmp1.put("Rate",i+1);
                record_list.add(tmp1);

            }
        }
        if ((int)record.get("Rate") == 0)
        {
            //current_record.setRate(i+1);
            record.put("Rate",i+1);
            record_list.add(record);
        }

        return record_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<HashMap<String,Object>> record_list;
        if (this.result_succeed){
            Menu.soundOff();
            callLevel(current_level+1);
            if (current_level != 0)
                current_score += calculateScore();
            //current_record.setScore(current_record.getScore()+calculateScore());
            //record.put("Score",(int)record.get("Score")+calculateScore());
            this.current_level++;
        }
        else if (current_level > 1){
            record.put("Level",current_level-1);
            record.put("Score",current_score);
            emSP = getSharedPreferences("sound", MODE_PRIVATE);
            if (emSP.getBoolean("soundOn", true))
                Menu.soundOn();
            else
                Menu.soundOff();

            try {

                FileInputStream fis = openFileInput("records");
                ObjectInputStream objIn = new ObjectInputStream(fis);


                record_list = sortRecords((List<HashMap<String,Object>>)objIn.readObject());

                objIn.close();

                FileOutputStream fos = openFileOutput("records",MODE_PRIVATE);
                ObjectOutputStream objOut = new ObjectOutputStream(fos);

                objOut.writeObject(record_list);
                objOut.close();

            } catch (FileNotFoundException e) {
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput("records",MODE_PRIVATE);
                    ObjectOutputStream objOut = new ObjectOutputStream(fos);
                    record.put("Rate",1);
                    record_list = new ArrayList<>();
                    record_list.add(record);
                    objOut.writeObject(record_list);
                    objOut.close();

                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            finish();
        }
        else {
            record.put("Level", current_level - 1);
            emSP = getSharedPreferences("sound", MODE_PRIVATE);
            if (emSP.getBoolean("soundOn", true))
                Menu.soundOn();
            else
                Menu.soundOff();
            finish();
        }
    }
}