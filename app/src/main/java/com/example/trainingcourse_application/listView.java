package com.example.trainingcourse_application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class listView extends AppCompatActivity {

    private ArrayList<String> list = null;

    private ArrayList<String> selectedValues = new ArrayList<>();
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView listView = (ListView) findViewById(R.id.listView);
        returnButton = findViewById(R.id.returnButton);

        getAllCourses();

        if(list != null) {
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = list.get(position);  // Retrieve the selected value
                if (selectedValues.contains(value)) {
                    selectedValues.remove(value);  // Remove the value if already selected
                } else {
                    selectedValues.add(value);  // Add the value if not already selected
                }
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to return the selected values
                Intent intent = new Intent();
                intent.putStringArrayListExtra("selectedValues", selectedValues);

                // Set the result and finish the activity
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    void getAllCourses(){
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(listView.this);
        if(list != null)
            list.clear();

        Cursor allCoursesCursor = dataBaseHelper.getAllCourses();
        while (allCoursesCursor.moveToNext()){
            String tmp = allCoursesCursor.getString(0);
            tmp += ": ";
            tmp += allCoursesCursor.getString(1);
            list.add(tmp);
        }
    }
}
