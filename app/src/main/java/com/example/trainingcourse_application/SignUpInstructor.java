package com.example.trainingcourse_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SignUpInstructor extends AppCompatActivity {

    ArrayList<String> selectedValues;
    private static final int PICK_IMAGE_REQUEST = 1;  // Request code for image selection
    Uri imageUri = null;
    private static final int PICK_listView_REQUEST = 2;  // Request code for image selection

    String[] items = {"BSc","MSc","PhD"};


    private Button register;
    private Button selectImageButton;
    private Button selectCourses;
    boolean chosenImageFlag = false;

    private EditText email;
    private EditText firstName;
    private EditText lastName;
    private EditText mobile;
    private EditText address;
    private EditText specialization;
    private EditText password;
    private EditText confirmPassword;
    private Spinner degree;

    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_instructor);

        final int purple = ContextCompat.getColor(this,R.color.purple);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.custom_edittext);
        drawable.setStroke(2, Color.RED);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawableOri = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.custom_edittext);
        drawableOri.setStroke(2, purple);

        selectImageButton = findViewById(R.id.selectImageInstructor);
        register = findViewById(R.id.registerInstructor);

        email = findViewById(R.id.emailInstructor);
        firstName = findViewById(R.id.firstNameInstructor);
        lastName = findViewById(R.id.lastNameInstructor);
        mobile = findViewById(R.id.MobileInstructor);
        address = findViewById(R.id.addressInstructor);
        specialization = findViewById(R.id.specializationInstructor);
        degree = findViewById(R.id.degreeSpinnerInstructor);
        password = findViewById(R.id.passwordInstructor);
        confirmPassword = findViewById(R.id.passwordInstructorConfirm);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        degree.setAdapter(adapter);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        selectCourses = findViewById(R.id.listViewInstructor);
        selectCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpInstructor.this,listView.class);
                startActivityForResult(i,PICK_listView_REQUEST);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                if(!validation.checkEmail(email.getText().toString())){
                    flag = false;
                    email.setBackground(drawable);
                }else{
                    email.setBackground(drawableOri);
                }
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
                if(!chosenImageFlag){
                    flag = false;
                    selectImageButton.setTextColor(Color.RED);
                }
                else {
                    selectImageButton.setTextColor(purple);
                }

                if(selectedValues == null || selectedValues.size() == 0){
                    flag = false;
                    selectCourses.setTextColor(Color.RED);
                }
                else {
                    selectCourses.setTextColor(purple);
                }

                if(!flag){
                    Toast.makeText(SignUpInstructor.this, "Register Failed, check the red fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(SignUpInstructor.this);
                    if(dataBaseHelper.checkUserExists(email.getText().toString())){
                        Toast.makeText(SignUpInstructor.this, "This Email is already used!", Toast.LENGTH_SHORT).show();
                        email.setBackground(drawable);
                    }
                    else {
                        User newUser = new User();
                        Instructor newInstructor = new Instructor();

                        newUser.setEmail(email.getText().toString());
                        newUser.setPassword(password.getText().toString());
                        newUser.setType("Instructor");

                        newInstructor.setEmail(email.getText().toString());
                        newInstructor.setFirstName(firstName.getText().toString());
                        newInstructor.setLastName(lastName.getText().toString());
                        newInstructor.setPersonalPhoto(imageUri.toString());
                        newInstructor.setMobile(mobile.getText().toString());
                        newInstructor.setAddress(address.getText().toString());
                        newInstructor.setSpecialization(specialization.getText().toString());
                        newInstructor.setDegree(selectedItemDegree);

                        dataBaseHelper.insertUser(newUser);
                        dataBaseHelper.insertInstructor(newInstructor);

                        for(int i=0;i<selectedValues.size();i++){
                            String[] tmp = (selectedValues.get(i)).split(":");
                            dataBaseHelper.insertInstructor_Course(Integer.parseInt(tmp[0]),email.getText().toString());
                        }

                        Toast.makeText(SignUpInstructor.this, "Register Succeeded!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            chosenImageFlag = true;
            imageUri = data.getData();
            // Perform desired action with the selected image URI (e.g., display the image)
        }
        else if (requestCode == PICK_listView_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedValues = data.getStringArrayListExtra("selectedValues");
            // Use the selected values as needed
        }
    }


}
