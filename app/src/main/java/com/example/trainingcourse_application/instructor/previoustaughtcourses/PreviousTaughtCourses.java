package com.example.trainingcourse_application.instructor.previoustaughtcourses;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.DataBaseHelper;
import com.example.trainingcourse_application.R;
import com.example.trainingcourse_application.databinding.FragmentPrevioustaughtcoursesBinding;
import com.example.trainingcourse_application.databinding.FragmentScheduleBinding;

import java.util.ArrayList;



public class PreviousTaughtCourses extends Fragment {

    // ARRAY LIST
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> selectedValues = new ArrayList<>();
    private FragmentPrevioustaughtcoursesBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPrevioustaughtcoursesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        ListView listView = root.findViewById(R.id.PTC_listView);
        getAllCourses(getContext());
        if (!list.isEmpty()) {
            ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
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

        return root;
    }

    void getAllCourses(Context context) {
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(context);
        list.clear();

        Cursor allCoursesCursor = dataBaseHelper.getAllCourses();
        while (allCoursesCursor.moveToNext()) {
            String tmp = allCoursesCursor.getString(0);
            tmp += ": ";
            tmp += allCoursesCursor.getString(1);
            list.add(tmp);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
