package com.example.trainingcourse_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpStudent extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;  // Request code for image selection
    Uri imageUri = null;

    private Button register;
    private Button selectImageButton;
    boolean chosenImageFlag = false;

    private EditText email;
    private EditText firstName;
    private EditText lastName;
    private EditText mobile;
    private EditText address;
    private EditText password;
    private EditText confirmPassword;

    private boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student);

        final int purple = ContextCompat.getColor(this,R.color.purple);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.custom_edittext);
        drawable.setStroke(2, Color.RED);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawableOri = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.custom_edittext);
        drawableOri.setStroke(2, purple);

        selectImageButton = findViewById(R.id.selectImageStudent);
        register = findViewById(R.id.registerStudent);

        email = findViewById(R.id.emailStudent);
        firstName = findViewById(R.id.firstNameStudent);
        lastName = findViewById(R.id.lastNameStudent);
        mobile = findViewById(R.id.mobileStudent);
        address = findViewById(R.id.addressStudent);
        password = findViewById(R.id.passwordStudent);
        confirmPassword = findViewById(R.id.passwordStudentConfirm);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
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

                if(!flag){
                    Toast.makeText(SignUpStudent.this, "Register Failed, check the red fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(SignUpStudent.this);
                    if(dataBaseHelper.checkUserExists(email.getText().toString())){
                        Toast.makeText(SignUpStudent.this, "This Email is already used!", Toast.LENGTH_SHORT).show();
                        email.setBackground(drawable);
                    }
                    else {
                        User newUser = new User();
                        Student newStudent = new Student();

                        newUser.setEmail(email.getText().toString());
                        newUser.setPassword(password.getText().toString());
                        newUser.setType("Student");

                        newStudent.setEmail(email.getText().toString());
                        newStudent.setFirstName(firstName.getText().toString());
                        newStudent.setLastName(lastName.getText().toString());
                        newStudent.setPersonalPhoto(imageUri.toString());
                        newStudent.setMobile(mobile.getText().toString());
                        newStudent.setAddress(address.getText().toString());

                        dataBaseHelper.insertUser(newUser);
                        dataBaseHelper.insertStudent(newStudent);


                        Toast.makeText(SignUpStudent.this, "Register Succeeded!", Toast.LENGTH_SHORT).show();
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
    }
}
