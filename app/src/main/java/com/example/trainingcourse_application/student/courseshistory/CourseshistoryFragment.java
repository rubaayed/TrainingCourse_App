package com.example.trainingcourse_application.student.courseshistory;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.DataBaseHelper;
import com.example.trainingcourse_application.R;
import com.example.trainingcourse_application.databinding.FragmentCourseshistoryBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CourseshistoryFragment extends Fragment {

    private FragmentCourseshistoryBinding binding;

    SearchView searchView;
    ListView listView;
    ArrayList<String> allCourses;
    ArrayAdapter<String> adapter;
    TextView sectionId;
    TextView courseId;
    TextView courseTitle;
    TextView courseMainTopics;
    TextView coursePrerequisites;
    TextView courseInstructorName;
    TextView courseRegistrationDeadline;
    TextView courseStartDate;
    TextView courseSchedule;
    TextView courseVenue;
    TextView courseEndDate;
    ScrollView scrollView;
    ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCourseshistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchView = root.findViewById(R.id.searchViewCoursesHistoryStudent);
        listView = root.findViewById(R.id.listViewCoursesHistoryStudent);
        sectionId = root.findViewById(R.id.sectionIdCoursesHistoryStudent);
        courseId = root.findViewById(R.id.courseIdCoursesHistoryStudent);
        courseTitle = root.findViewById(R.id.courseTitleCoursesHistoryStudent);
        courseMainTopics = root.findViewById(R.id.mainTopicsCoursesHistoryStudent);
        coursePrerequisites = root.findViewById(R.id.prerequisitesCoursesHistoryStudent);
        courseInstructorName = root.findViewById(R.id.instructorCoursesHistoryStudent);
        courseRegistrationDeadline = root.findViewById(R.id.regdeadlineCoursesHistoryStudent);
        courseStartDate = root.findViewById(R.id.startDateCoursesHistoryStudent);
        courseSchedule = root.findViewById(R.id.scheduleCoursesHistoryStudent);
        courseVenue = root.findViewById(R.id.venueCoursesHistoryStudent);
        courseEndDate = root.findViewById(R.id.endDateCoursesHistoryStudent);
        scrollView = root.findViewById(R.id.scrollView2CoursesHistoryStudent);
        imageView = root.findViewById(R.id.imageCourseCoursesHistoryStudent);

        AllCourses();

        scrollView.setVisibility(View.INVISIBLE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(allCourses != null && allCourses.contains(s)) {
                    scrollView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    String[] tmp = s.split(":");
                    InsertDataInTextView(tmp[0]);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listView.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.INVISIBLE);
                adapter.getFilter().filter(s);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = allCourses.get(position);  // Retrieve the selected value
                searchView.setQuery(value,false);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void AllCourses(){
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());
        if(allCourses != null)
            allCourses.clear();
        else
            allCourses = new ArrayList<>();

        Cursor allSectionsCursor = dataBaseHelper.getAllSections();
        while (allSectionsCursor.moveToNext()){
            String tmp = allSectionsCursor.getString(0);
            tmp += ": ";
            tmp += allSectionsCursor.getString(1);
            allCourses.add(tmp);
        }

        if(allCourses != null) {
            adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, allCourses);
            listView.setAdapter(adapter);
        }
    }

    void InsertDataInTextView(String section_id){
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());

        Cursor SectionInfo = dataBaseHelper.getSectionInfo(section_id);
        SectionInfo.moveToNext();
        sectionId.setText(String.valueOf(SectionInfo.getInt(0)));
        courseId.setText(String.valueOf(SectionInfo.getInt(1)));
        courseTitle.setText(SectionInfo.getString(2));
        courseMainTopics.setText(SectionInfo.getString(3));

        byte[] imageBytes = SectionInfo.getBlob(4);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imageView.setImageBitmap(bitmap);

        String pre = "";
        Cursor course_Prerequisites = dataBaseHelper.getCoursePrerequisites(String.valueOf(SectionInfo.getInt(1)));
        while (course_Prerequisites.moveToNext()){
            String tmp = course_Prerequisites.getString(0);
            tmp += ": ";
            tmp += course_Prerequisites.getString(1);
            pre += tmp + "\n";
        }
        if( pre.equals("")){
            pre = "Course has No Prerequisites";
        }
        coursePrerequisites.setText(pre);
        String nameInstructor = SectionInfo.getString(5) + " " + SectionInfo.getString(6);
        courseInstructorName.setText(nameInstructor);

        Date date = new Date(SectionInfo.getInt(7) * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        courseRegistrationDeadline.setText(dateString);

        date = new Date(SectionInfo.getInt(8) * 1000);
        dateString = dateFormat.format(date);
        courseStartDate.setText(dateString);

        courseSchedule.setText(SectionInfo.getString(9));
        courseSchedule.setText(SectionInfo.getString(10));

        date = new Date(SectionInfo.getInt(11) * 1000);
        dateString = dateFormat.format(date);
        courseEndDate.setText(dateString);
    }
}