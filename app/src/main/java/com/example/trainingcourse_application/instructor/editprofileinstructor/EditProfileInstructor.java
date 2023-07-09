package com.example.trainingcourse_application.instructor.editprofileinstructor;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.DataBaseHelper;
import com.example.trainingcourse_application.Instructor;
import com.example.trainingcourse_application.InstructorHomeEmail;
import com.example.trainingcourse_application.R;
import com.example.trainingcourse_application.SignUpInstructor;
import com.example.trainingcourse_application.StudentHomeEmail;
import com.example.trainingcourse_application.User;
import com.example.trainingcourse_application.databinding.FragmentEditprofileinstructorBinding;
import com.example.trainingcourse_application.listView;
import com.example.trainingcourse_application.validation;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class EditProfileInstructor extends Fragment {

    private FragmentEditprofileinstructorBinding binding;

    ArrayList<String> selectedValues;
    private static final int PICK_IMAGE_REQUEST = 1;  // Request code for image selection
    Uri imageUri = null;
    byte [] imageBytes;
    private static final int PICK_listView_REQUEST = 2;  // Request code for image selection

    String[] items = {"BSc","MSc","PhD"};


    private Button edit;
    private Button selectImageButton;
    private Button selectCourses;
    boolean chosenImageFlag = false;

    private EditText firstName;
    private EditText lastName;
    private EditText mobile;
    private EditText address;
    private EditText specialization;
    private EditText password;
    private EditText confirmPassword;
    private Spinner degree;
    private TextView courses;
    private boolean flag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEditprofileinstructorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final int purple = ContextCompat.getColor(getContext(), R.color.purple);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.custom_edittext);
        drawable.setStroke(2, Color.RED);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawableOri = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.custom_edittext);
        drawableOri.setStroke(2, purple);

        String email = InstructorHomeEmail.getEmailAddress();

        selectImageButton = root.findViewById(R.id.selectImageInstructorEditInstructor);
        edit = root.findViewById(R.id.editInstructorEditInstructor);

        firstName = root.findViewById(R.id.firstNameInstructorEditInstructor);
        lastName = root.findViewById(R.id.lastNameInstructorEditInstructor);
        mobile = root.findViewById(R.id.MobileInstructorEditInstructor);
        address = root.findViewById(R.id.addressInstructorEditInstructor);
        specialization = root.findViewById(R.id.specializationInstructorEditInstructor);
        degree = root.findViewById(R.id.degreeSpinnerInstructorEditInstructor);
        password = root.findViewById(R.id.passwordInstructorEditInstructor);
        confirmPassword = root.findViewById(R.id.passwordInstructorConfirmEditInstructor);
        courses = root.findViewById(R.id.coursesToTaughtEditInstructor);

        fillValues(email);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        degree.setAdapter(adapter);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        selectCourses = root.findViewById(R.id.listViewInstructorEditInstructor);
        selectCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), listView.class);
                startActivityForResult(i,PICK_listView_REQUEST);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                if(!validation.checkFirstName(firstName.getText().toString())){
                    flag = false;
                    firstName.setBackground(drawable);
                }else{
                    firstName.setBackground(drawableOri);
                }
                if(!validation.checkLastName(lastName.getText().toString())){
                    flag = false;
                    lastName.setBackground(drawable);
                }else{
                    lastName.setBackground(drawableOri);
                }
                if(!validation.checkMobile(mobile.getText().toString())){
                    flag = false;
                    mobile.setBackground(drawable);
                }else{
                    mobile.setBackground(drawableOri);
                }
                if(!validation.checkAddress(address.getText().toString())){
                    flag = false;
                    address.setBackground(drawable);
                }else{
                    address.setBackground(drawableOri);
                }
                if(!validation.checkSpecialization(specialization.getText().toString())){
                    flag = false;
                    specialization.setBackground(drawable);
                }else{
                    specialization.setBackground(drawableOri);
                }
                String selectedItemDegree= null;
                if(degree != null && degree.getSelectedItem() !=null ) {
                    selectedItemDegree = (String)degree.getSelectedItem();
                    specialization.setBackground(drawableOri);

                } else  {
                    flag = false;
                    degree.setBackground(drawable);
                }
                if(!validation.checkPassword(password.getText().toString())){
                    flag = false;
                    password.setBackground(drawable);
                }else{
                    password.setBackground(drawableOri);
                }
                if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                    flag = false;
                    confirmPassword.setBackground(drawable);
                }
                else {
                    confirmPassword.setBackground(drawableOri);
                }

                if(!flag){
                    Toast.makeText(getActivity(), "Edit Failed, check the red fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());

                    if(chosenImageFlag){
                        dataBaseHelper.editDataImage("Instructor", email,"IMAGE",imageBytes,"EMAIL");
                    }
                    dataBaseHelper.editData("Instructor", email,"FIRST_NAME",firstName.getText().toString(),"EMAIL");
                    dataBaseHelper.editData("Instructor", email,"LAST_NAME",lastName.getText().toString(),"EMAIL");
                    dataBaseHelper.editData("Instructor", email,"MOBILE",mobile.getText().toString(),"EMAIL");
                    dataBaseHelper.editData("Instructor", email,"ADDRESS",address.getText().toString(),"EMAIL");
                    dataBaseHelper.editData("Instructor", email,"SPECIALIZATION",specialization.getText().toString(),"EMAIL");
                    dataBaseHelper.editData("Instructor", email,"DEGREE",selectedItemDegree,"EMAIL");

                    dataBaseHelper.editData("USER", email,"PASSWORD",password.getText().toString(),"EMAIL");

                    if(!(selectedValues == null || selectedValues.size() == 0)) {
                        dataBaseHelper.deleteData("Instructor_Course",email,"EMAIL_INST");
                        for (int i = 0; i < selectedValues.size(); i++) {
                            String[] tmp = (selectedValues.get(i)).split(":");
                            dataBaseHelper.insertInstructor_Course(Integer.parseInt(tmp[0]), email);
                        }
                    }
                    Toast.makeText(getActivity(), "Edit Succeeded!", Toast.LENGTH_SHORT).show();
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);
                // Convert the bitmap to byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageBytes = stream.toByteArray();
            }  catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void fillValues(String email){
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());

        Cursor InstructorInfo = dataBaseHelper.getInstructorInfo(email);
        InstructorInfo.moveToNext();

        firstName.setText(InstructorInfo.getString(0));
        lastName.setText(InstructorInfo.getString(1));
        mobile.setText(InstructorInfo.getString(2));
        address.setText(InstructorInfo.getString(3));
        password.setText(InstructorInfo.getString(4));
        confirmPassword.setText(InstructorInfo.getString(4));
        specialization.setText(InstructorInfo.getString(5));
        int spinnerValue ;
        if(InstructorInfo.getString(6).equals("BSc"))
            spinnerValue = 0;
        else if(InstructorInfo.getString(6).equals("MSc"))
            spinnerValue = 1;
        else
            spinnerValue = 2;

        degree.setSelection(spinnerValue);

        String pre = "courses:\n";
        Cursor InstructorCourses = dataBaseHelper.getInstructorCourses(email);
        while (InstructorCourses.moveToNext()){
            String tmp = InstructorCourses.getString(0);
            Cursor tmpCourseInfo = dataBaseHelper.getCourseInfo(tmp);
            tmpCourseInfo.moveToNext();
            tmp += ": ";
            tmp += tmpCourseInfo.getString(0);
            pre += tmp + "\n";
        }
        if( pre.equals("courses:\n")){
            pre = "Instructor can't taught any Course";
        }
        courses.setText(pre);
    }
}