package com.example.simon;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class RecordsTable extends ListActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records_table);

        ArrayList<Record> records = null;
        try {
            FileInputStream fis = openFileInput("records");
            ObjectInputStream objIn = new ObjectInputStream(fis);
            records = (ArrayList<Record>) objIn.readObject();
                        ArrayAdapter<Record> adapter = new ArrayAdapter<Record>(this, android.R.layout.simple_gallery_item,records);
            setListAdapter(adapter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
