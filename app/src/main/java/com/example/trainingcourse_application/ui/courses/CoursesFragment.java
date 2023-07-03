package com.example.trainingcourse_application.ui.courses;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.DataBaseHelper;
import com.example.trainingcourse_application.R;
import com.example.trainingcourse_application.databinding.FragmentCoursesBinding;
import com.example.trainingcourse_application.listView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class CoursesFragment extends Fragment {

    private FragmentCoursesBinding binding;

    SearchView searchView;
    ListView listView;
    ArrayList<String> sections;
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


    private EditText text;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchView = root.findViewById(R.id.searchViewCoursesStudent);
        listView = root.findViewById(R.id.listViewCoursesStudent);
        sectionId = root.findViewById(R.id.sectionIdCoursesStudent);
        courseId = root.findViewById(R.id.courseIdCoursesStudent);
        courseTitle = root.findViewById(R.id.courseTitleCoursesStudent);
        courseMainTopics = root.findViewById(R.id.mainTopicsCoursesStudent);
        coursePrerequisites = root.findViewById(R.id.prerequisitesCoursesStudent);
        courseInstructorName = root.findViewById(R.id.instructorCoursesStudent);
        courseRegistrationDeadline = root.findViewById(R.id.regdeadlineCoursesStudent);
        courseStartDate = root.findViewById(R.id.startDateCoursesStudent);
        courseSchedule = root.findViewById(R.id.scheduleCoursesStudent);
        courseVenue = root.findViewById(R.id.venueCoursesStudent);
        courseEndDate = root.findViewById(R.id.endDateCoursesStudent);
        scrollView = root.findViewById(R.id.scrollView2);

        imageView = root.findViewById(R.id.imageCourseCoursesStudent);

        getAllSections();


        listView.setVisibility(View.GONE);
        scrollView.setVisibility(View.INVISIBLE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                scrollView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                String[] tmp = s.split(":");
                InsertDataInTextView(tmp[0]);
                return true;
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
                String value = sections.get(position);  // Retrieve the selected value
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

    void getAllSections(){
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());
        if(sections != null)
            sections.clear();
        else
            sections = new ArrayList<>();

        Cursor allSectionsCursor = dataBaseHelper.getAllAvailableSections();
        while (allSectionsCursor.moveToNext()){
            String tmp = allSectionsCursor.getString(0);
            tmp += ": ";
            tmp += allSectionsCursor.getString(1);
            sections.add(tmp);
        }

        if(sections != null) {
            adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, sections);
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

        Uri imageUri = Uri.parse(SectionInfo.getString(4));
        imageView.setImageURI(imageUri);

        String pre = "";
        Cursor course_Prerequisites = dataBaseHelper.getCoursePrerequisites(String.valueOf(SectionInfo.getInt(1)));
        while (course_Prerequisites.moveToNext()){
            String tmp = course_Prerequisites.getString(0);
            tmp += ": ";
            tmp += course_Prerequisites.getString(1);
            pre += tmp + "\n";
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