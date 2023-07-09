package com.example.trainingcourse_application.admin.editdeletecourse;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.DataBaseHelper;
import com.example.trainingcourse_application.R;
import com.example.trainingcourse_application.admin.createcourse.Prerequisites;
import com.example.trainingcourse_application.databinding.FragmentEditdeletecourseBinding;
import com.example.trainingcourse_application.listView;
import com.example.trainingcourse_application.validation;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class EditDeleteCourseFragment extends Fragment {

    private FragmentEditdeletecourseBinding binding;

    private static final int PICK_IMAGE_REQUEST = 1;  // Request code for image selection
    private static final int PICK_listView_REQUEST = 2;  // Request code for image selection
    Uri imageUri = null;
    byte[] imageBytes;
    boolean chosenImageFlag = false;
    String[] items = {"Edit","Delete"};
    private Spinner operation;

    private SearchView searchView;
    private ListView listView;
    private ArrayList<String> courses;
    private ArrayAdapter<String> adapterCourses;
    ArrayList<String> selectedValues;

    private int CourseID ;
    private TextView courseId;
    private EditText title;
    private EditText mainTopics;
    private Button selectImageButton;
    private Button edit;
    private Button selectPrerequisites;
    private TextView prerequisites;


    private Button delete;
    private TextView courseId2;

    private View editLayout;
    private View deleteLayout;
    boolean flag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEditdeletecourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        operation = root.findViewById(R.id.spinnerEditDeleteAdmin);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operation.setAdapter(adapter);

        final int purple = ContextCompat.getColor(getContext(), R.color.purple);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.custom_edittext);
        drawable.setStroke(2, Color.RED);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawableOri = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.custom_edittext);
        drawableOri.setStroke(2, purple);


        searchView = root.findViewById(R.id.searchViewCoursesStudent);
        listView = root.findViewById(R.id.listViewCoursesStudent);

        courseId = root.findViewById(R.id.idCourseEditAdmin);
        title = root.findViewById(R.id.titleCourseEditAdmin);
        mainTopics = root.findViewById(R.id.mainTopicsEditAdmin);
        selectImageButton = root.findViewById(R.id.selectImageCourseEditAdmin);
        selectPrerequisites = root.findViewById(R.id.prerequisiteEditAdmin);
        edit = root.findViewById(R.id.buttonEditCourseEditAdmin);
        prerequisites = root.findViewById(R.id.preCoursesStudent);



        courseId2 = root.findViewById(R.id.idCourseDeleteAdmin);
        delete = root.findViewById(R.id.buttonDeleteCourseDeleteAdmin);

        editLayout = root.findViewById(R.id.editCourseLayoutAdmin);
        deleteLayout = root.findViewById(R.id.deleteCourseLayoutAdmin);


        getAllCourses();
        listView.setVisibility(View.GONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(courses != null && courses.contains(s)) {
                    listView.setVisibility(View.GONE);
                    String[] tmp = s.split(":");
                    CourseID = Integer.parseInt(tmp[0]);
                    if(operation.getSelectedItem().equals("Edit")){
                        editLayout.setVisibility(View.VISIBLE);
                        deleteLayout.setVisibility(View.INVISIBLE);
                        fillValues(tmp[0]);
                    }else {
                        editLayout.setVisibility(View.INVISIBLE);
                        deleteLayout.setVisibility(View.VISIBLE);
                        courseId2.setText("Course ID: " + tmp[0]);
                    }
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listView.setVisibility(View.VISIBLE);
                editLayout.setVisibility(View.INVISIBLE);
                deleteLayout.setVisibility(View.INVISIBLE);
                adapterCourses.getFilter().filter(s);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = courses.get(position);  // Retrieve the selected value
                searchView.setQuery(value,false);
            }
        });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        selectPrerequisites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Prerequisites.class);
                i.putExtra("courseId",CourseID);
                startActivityForResult(i,PICK_listView_REQUEST);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                if(!validation.checkTitle(title.getText().toString())){
                    flag = false;
                    title.setBackground(drawable);
                }else{
                    title.setBackground(drawableOri);
                }
                if(!validation.checkMainTopics(mainTopics.getText().toString())){
                    flag = false;
                    mainTopics.setBackground(drawable);
                }else{
                    mainTopics.setBackground(drawableOri);
                }
                if(!flag){
                    Toast.makeText(getActivity(), "Edit Failed! Check the red fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());

                    if(chosenImageFlag){
                        dataBaseHelper.editDataImage("Course", CourseID+"","IMAGE",imageBytes,"COURSE_ID");
                    }
                    dataBaseHelper.editData("Course", CourseID+"","TITLE",title.getText().toString(),"COURSE_ID");
                    dataBaseHelper.editData("Course", CourseID+"","MAIN_TOPICS",mainTopics.getText().toString(),"COURSE_ID");

                    if(!(selectedValues == null || selectedValues.size() == 0)) {
                        dataBaseHelper.deleteData("Prerequisite",CourseID+"","ID_1");
                        for (int i = 0; i < selectedValues.size(); i++) {
                            String[] tmp = (selectedValues.get(i)).split(":");
                            dataBaseHelper.insertPrerequisite(CourseID, Integer.parseInt(tmp[0]));
                        }
                    }
                    Toast.makeText(getActivity(), "Edit Succeeded!", Toast.LENGTH_SHORT).show();
                    getAllCourses();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());
                dataBaseHelper.deleteData("Course",CourseID+"","COURSE_ID");
                Toast.makeText(getActivity(), "Course Deleted successfully!", Toast.LENGTH_SHORT).show();
                getAllCourses();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            chosenImageFlag = true;
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                // Convert the bitmap to byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageBytes = stream.toByteArray();
            }  catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (requestCode == PICK_listView_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedValues = data.getStringArrayListExtra("selectedValues");
        }
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
            listView.setAdapter(adapterCourses);
        }
    }

    void fillValues(String courseid){
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());

        Cursor allSectionsCursor = dataBaseHelper.getCourseInfo(courseid);
        allSectionsCursor.moveToNext();

        courseId.setText("Course ID: " +courseid);
        title.setText(allSectionsCursor.getString(0));
        mainTopics.setText(allSectionsCursor.getString(1));

        String pre = "Prerequisites";
        Cursor course_Prerequisites = dataBaseHelper.getCoursePrerequisites(String.valueOf(courseid));
        while (course_Prerequisites.moveToNext()){
            String tmp = course_Prerequisites.getString(0);
            Cursor tmpCourseInfo = dataBaseHelper.getCourseInfo(tmp);
            tmpCourseInfo.moveToNext();
            tmp += ": ";
            tmp += tmpCourseInfo.getString(0);
            pre += tmp + "\n";
        }
        if( pre.equals("Prerequisites")){
            pre = "Course has No Prerequisites";
        }
        prerequisites.setText(pre);

    }
}