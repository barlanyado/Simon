package com.example.simon;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecordsTable extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records_table);
        ListView records_listview = findViewById(R.id.records_listView);
        List<HashMap<String,Object>> records;
        try {
            FileInputStream fis = openFileInput("records");
            ObjectInputStream objIn = new ObjectInputStream(fis);
            records = ((List<HashMap<String,Object>>)objIn.readObject());

            String[] from = {"Rate", "Name", "Level", "Score"};
            int[] to = {R.id.rate_list, R.id.name_list, R.id.level_list, R.id.score_list};
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, records, R.layout.record_item, from, to);
            records_listview.setAdapter(simpleAdapter);
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
