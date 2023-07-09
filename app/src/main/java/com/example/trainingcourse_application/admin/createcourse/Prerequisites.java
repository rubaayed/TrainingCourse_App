package com.example.trainingcourse_application.admin.createcourse;

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

import com.example.trainingcourse_application.DataBaseHelper;
import com.example.trainingcourse_application.R;
import com.example.trainingcourse_application.listView;

import java.util.ArrayList;

public class Prerequisites extends AppCompatActivity {

    private ArrayList<String> list = null;

    private ArrayList<String> selectedValues = new ArrayList<>();
    Button returnButton;
    int CourseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prerequisites);

        ListView listView = (ListView) findViewById(R.id.listViewPrerequisites);
        returnButton = findViewById(R.id.returnButtonPrerequisites);

        Intent intent = getIntent();
        int back = intent.getIntExtra("courseId",-1);
        if(back != -1){
            CourseID = back;
        }
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
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(Prerequisites.this);
        if(list != null)
            list.clear();
        else
            list = new ArrayList<>();

        Cursor allCoursesCursor = dataBaseHelper.getAllCourses();
        while (allCoursesCursor.moveToNext()){
            String tmp = allCoursesCursor.getString(0);
            if(Integer.parseInt(tmp) == CourseID)
                continue;
            tmp += ": ";
            tmp += allCoursesCursor.getString(1);
            list.add(tmp);
        }
    }
}