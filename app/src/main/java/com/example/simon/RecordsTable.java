package com.example.simon;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class RecordsTable extends ListActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records_table);

        ArrayList<Record> records = new ArrayList<>();
        records.add(new Record("test1"));
        records.add(new Record("test2"));
        records.add(new Record("test3"));
        records.add(new Record("test4"));
        records.add(new Record("test5"));
        records.add(new Record("test6"));
        records.add(new Record("test7"));
        records.add(new Record("test8"));
        records.add(new Record("test9"));
        records.add(new Record("test10"));
        records.add(new Record("test11"));
        records.add(new Record("test12"));
        records.add(new Record("test13"));
        records.add(new Record("test14"));
        records.add(new Record("test15"));
        records.add(new Record("test16"));
        records.add(new Record("test17"));
        records.add(new Record("test18"));


        ArrayAdapter<Record> adapter = new ArrayAdapter<Record>(this, android.R.layout.simple_list_item_1,records);
        setListAdapter(adapter);

    }
}
