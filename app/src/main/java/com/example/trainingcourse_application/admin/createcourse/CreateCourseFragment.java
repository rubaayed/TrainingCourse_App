package com.example.trainingcourse_application.admin.createcourse;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.Course;
import com.example.trainingcourse_application.DataBaseHelper;
import com.example.trainingcourse_application.Instructor;
import com.example.trainingcourse_application.R;
import com.example.trainingcourse_application.SignUpInstructor;
import com.example.trainingcourse_application.User;
import com.example.trainingcourse_application.databinding.FragmentCreatecourseBinding;
import com.example.trainingcourse_application.listView;
import com.example.trainingcourse_application.validation;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CreateCourseFragment extends Fragment {

    private FragmentCreatecourseBinding binding;

    ArrayList<String> selectedValues;

    private static final int PICK_IMAGE_REQUEST = 1;  // Request code for image selection
    private static final int PICK_listView_REQUEST = 2;  // Request code for image selection

    Uri imageUri = null;
    byte[] imageBytes;
    private Button selectImageButton;
    private Button create;
    private Button selectPrerequisites;

    boolean chosenImageFlag = false;

    private EditText title;
    private EditText mainTopics;

    boolean flag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCreatecourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final int purple = ContextCompat.getColor(getContext(), R.color.purple);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.custom_edittext);
        drawable.setStroke(2, Color.RED);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawableOri = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.custom_edittext);
        drawableOri.setStroke(2, purple);

        selectImageButton = root.findViewById(R.id.selectImageCourseAdmin);
        create = root.findViewById(R.id.buttonCreateCourseAdmin);
        selectPrerequisites = root.findViewById(R.id.prerequisiteAdmin);

        title = root.findViewById(R.id.titleCourseAdmin);
        mainTopics = root.findViewById(R.id.mainTopicsAdmin);

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
                startActivityForResult(i,PICK_listView_REQUEST);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if(!chosenImageFlag){
                    flag = false;
                    selectImageButton.setTextColor(Color.RED);
                }
                else {
                    selectImageButton.setTextColor(purple);
                }

                if(!flag){
                    Toast.makeText(getActivity(), "Creation Failed, check the red fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getActivity());

                    Course course = new Course();

                    course.setTitle(title.getText().toString());
                    course.setMainTopics(mainTopics.getText().toString());
                    course.setImage(imageBytes);

                    dataBaseHelper.insertCourse(course);

                    Cursor result = dataBaseHelper.NextPossibleCourse();
                    result.moveToLast();

                    int courseNumber = result.getInt(0);
                    if(!(selectedValues == null || selectedValues.size() == 0)) {
                        for (int i = 0; i < selectedValues.size(); i++) {
                            String[] tmp = (selectedValues.get(i)).split(":");
                            dataBaseHelper.insertPrerequisite(courseNumber, Integer.parseInt(tmp[0]));
                        }
                    }

                    Toast.makeText(getActivity(), "Creation Succeeded!", Toast.LENGTH_SHORT).show();

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
}