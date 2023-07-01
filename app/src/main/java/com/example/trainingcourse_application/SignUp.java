package com.example.trainingcourse_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private Button admin;
    private Button student;
    private Button instructor;
    private TextView signInText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        admin = findViewById(R.id.Admin);
        student = findViewById(R.id.Student);
        instructor = findViewById(R.id.Instructor);
        signInText = findViewById(R.id.signInText);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this,SignUpAdmin.class);
                startActivity(i);
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this,SignUpStudent.class);
                startActivity(i);
            }
        });

        instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this,SignUpInstructor.class);
                startActivity(i);
            }
        });

    }

    public void alreadyHaveAnAccount(View view) {
        Intent i = new Intent(SignUp.this,MainActivity.class);
        startActivity(i);
    }
}
