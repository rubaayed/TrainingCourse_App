package com.example.trainingcourse_application.admin.createsection;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.Course;
import com.example.trainingcourse_application.DataBaseHelper;
import com.example.trainingcourse_application.R;
import com.example.trainingcourse_application.Section;
import com.example.trainingcourse_application.databinding.FragmentCreatesectionBinding;
import com.example.trainingcourse_application.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CreateSectionFragment extends Fragment {

    private FragmentCreatesectionBinding binding;

    String[] items = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    private Spinner day;
    private String email;
    private int courseId;

    private SearchView searchCourse;
    private ListView listViewCourse;
    private ArrayList<String> courses;
    private ArrayAdapter<String> adapterCourses;

    private SearchView searchViewInstructor;
    private ListView listViewInstructor;
    private ArrayList<String> instructors;
    private ArrayAdapter<String> adapterInstructor;

    private EditText regDate;
    private EditText startDate;
    private EditText endDate;
    private EditText schedule;
    private EditText venue;

    private Button create;
    boolean flag;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCreatesectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        day = root.findViewById(R.id.dayAdmin);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter);

        final int purple = ContextCompat.getColor(getContext(), R.color.purple);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.custom_edittext);
        drawable.setStroke(2, Color.RED);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawableOri = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.custom_edittext);
        drawableOri.setStroke(2, purple);

        searchCourse = root.findViewById(R.id.searchCourseIdSectionAdmin);
        listViewCourse = root.findViewById(R.id.listViewCoursesSectionAdmin);

        searchViewInstructor = root.findViewById(R.id.searchInstructorEmailSectionAdmin);
        listViewInstructor = root.findViewById(R.id.listViewInstructorSectionAdmin);

        regDate = root.findViewById(R.id.regDeadLineAdmin);
        startDate = root.findViewById(R.id.startDateAdmin);
        endDate = root.findViewById(R.id.endDateAdmin);
        venue = root.findViewById(R.id.venueAdmin);
        schedule = root.findViewById(R.id.scheduleAdmin);
        create = root.findViewById(R.id.buttonCreateSectionAdmin);


        listViewInstructor.setVisibility(View.GONE);
        listViewCourse.setVisibility(View.GONE);

        getAllCourses();
        getAllInstructors();

        searchCourse.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(courses != null && courses.contains(s)) {
                    listViewCourse.setVisibility(View.GONE);
                    String[] tmp = s.split(":");
                    courseId = Integer.parseInt(tmp[0]);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listViewCourse.setVisibility(View.VISIBLE);
                adapterCourses.getFilter().filter(s);
                return false;
            }
        });

        listViewCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = adapterCourses.getItem(position);  // Retrieve the selected value
                searchCourse.setQuery(value,false);
            }
        });

        searchViewInstructor.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(instructors != null && instructors.contains(s)) {
                    listViewInstructor.setVisibility(View.GONE);
                    String[] tmp = s.split(":");
                    email = tmp[0];
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listViewInstructor.setVisibility(View.VISIBLE);
                adapterInstructor.getFilter().filter(s);
                return false;
            }
        });

        listViewInstructor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = adapterInstructor.getItem(position);  // Retrieve the selected value
                searchViewInstructor.setQuery(value,false);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                if(!validation.checkDateFormat(regDate.getText().toString())){
                    flag = false;
                    regDate.setBackground(drawable);
                }else{
                    regDate.setBackground(drawableOri);
                }
                if(!validation.checkDateFormat(startDate.getText().toString())){
                    flag = false;
                    startDate.setBackground(drawable);
                }else{
                    startDate.setBackground(drawableOri);
                }
                if(!validation.checkDateFormat(endDate.getText().toString())){
                    flag = false;
                    endDate.setBackground(drawable);
                }else{
                    endDate.setBackground(drawableOri);
                }
                if(!validation.checkTimeFormat(schedule.getText().toString())){
                    flag = false;
                    schedule.setBackground(drawable);
                }else{
                    schedule.setBackground(drawableOri);
                }
                if(!validation.checkVenue(venue.getText().toString())){
                    flag = false;
                    venue.setBackground(drawable);
                }else{
                    venue.setBackground(drawableOri);
                }
                if(!flag){
                    Toast.makeText(getActivity(), "Create Failed! Check the red fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(!isDateLessThan(regDate.getText().toString(),startDate.getText().toString())){
                        Toast.makeText(getActivity(), "Time Conflict!", Toast.LENGTH_SHORT).show();
                        regDate.setBackground(drawable);
                        startDate.setBackground(drawable);
                        flag = false;
                    }
                    else {
                        regDate.setBackground(drawableOri);
                        startDate.setBackground(drawableOri);
                    }
                    if(!isDateLessThan(startDate.getText().toString(),endDate.getText().toString())){
                        Toast.makeText(getActivity(), "Time Conflict!", Toast.LENGTH_SHORT).show();
                        endDate.setBackground(drawable);
                        startDate.setBackground(drawable);
                        flag = false;
                    }
                    else {
                        endDate.setBackground(drawableOri);
                        startDate.setBackground(drawableOri);
                    }
                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());
                    if(!dataBaseHelper.checkInstructorCourse(email,courseId)){
                        Toast.makeText(getActivity(), "This Instructor Can't give this course", Toast.LENGTH_SHORT).show();
                        flag = false;
                    }
                    if(flag) {
                        Section section = new Section();

                        String day_value = (String)day.getSelectedItem();
                        section.setCourseId(courseId);
                        section.setEmail(email);
                        section.setSchedule(schedule.getText().toString()+":"+day_value);
                        section.setRegDeadLine(convertDateToSeconds(regDate.getText().toString()));
                        section.setVenue(venue.getText().toString());
                        section.setStartDate(convertDateToSeconds(startDate.getText().toString()));
                        section.setEndDate(convertDateToSeconds(endDate.getText().toString()));

                        dataBaseHelper.insertSection(section);
                        Toast.makeText(getActivity(), "Create Section Succeeded!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void getAllCourses(){
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getActivity());
        if(courses != null)
            courses.clear();
        else
            courses = new ArrayList<>();

        Cursor allCoursesCursor = dataBaseHelper.getAllCourses();
        while (allCoursesCursor.moveToNext()){
            String tmp = allCoursesCursor.getString(0);
            tmp += ": ";
            tmp += allCoursesCursor.getString(1);
            courses.add(tmp);
        }
        if(courses != null) {
            adapterCourses = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, courses);
            listViewCourse.setAdapter(adapterCourses);
        }
    }

    void getAllInstructors(){
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getActivity());
        if(instructors != null)
            instructors.clear();
        else
            instructors = new ArrayList<>();

        Cursor allInstructorsCursor = dataBaseHelper.getAllInstructors();
        while (allInstructorsCursor.moveToNext()){
            String tmp = allInstructorsCursor.getString(0);
            tmp += ": ";
            tmp += allInstructorsCursor.getString(1);
            tmp += " ";
            tmp += allInstructorsCursor.getString(2);
            instructors.add(tmp);
        }
        if(instructors != null) {
            adapterInstructor = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, instructors);
            listViewInstructor.setAdapter(adapterInstructor);
        }
    }

    boolean isDateLessThan( String dateString1, String dateString2){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date1 = dateFormat.parse(dateString1);
            Date date2 = dateFormat.parse(dateString2);

            // Check if the dates are within the valid range
            if (!isWithinValidRange(date1) || !isWithinValidRange(date2)) {
                return false;
            }

            return date1.before(date2);
        } catch (ParseException e) {
            // Handle parsing exception
            e.printStackTrace();
            return false;
        }
    }

    boolean isWithinValidRange(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Check if the year is within a reasonable range
        if (year < 1900 || year > 2100) {
            return false;
        }

        // Check if the month is within a valid range (1-12)
        if (month < 0 || month > 11) {
            return false;
        }

        // Check if the day is within a valid range for the given month
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (day < 1 || day > maxDay) {
            return false;
        }

        return true;
    }

    long convertDateToSeconds(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date = dateFormat.parse(dateString);
            long milliseconds = date.getTime();
            return TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }
}