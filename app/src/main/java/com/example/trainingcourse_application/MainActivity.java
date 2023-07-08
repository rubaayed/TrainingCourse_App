package com.example.trainingcourse_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button loginButton;
    private CheckBox checkBox;

    SharedPrefManager sharedPrefManager;

    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this,AdminHome.class);
        startActivity(intent);


        final DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(MainActivity.this);

        sharedPrefManager =SharedPrefManager.getInstance(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        checkBox = findViewById(R.id.RememberMe);

        if(!sharedPrefManager.readString("email","noValue").equals("noValue")){
            email.setText(sharedPrefManager.readString("email","noValue"));
            password.setText(sharedPrefManager.readString("password","noValue"));
        }



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                if(!validation.checkEmail(email.getText().toString())){
                    flag = false;
                }
                if(!validation.checkPassword(password.getText().toString())){
                    flag = false;
                }
                if(!flag){
                    Toast.makeText(MainActivity.this, "Email or password is wrong! Retype them!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Cursor  value = dataBaseHelper.returnUserValues(email.getText().toString());
                    if(value == null){
                        Toast.makeText(MainActivity.this, "The user does not exist!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        value.moveToNext();
                        String pass = value.getString(1);
                        if(!pass.equals(password.getText().toString())){
                            Toast.makeText(MainActivity.this, "Password is incorrect!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(checkBox.isChecked()){
                                sharedPrefManager.writeString("email",email.getText().toString());
                                sharedPrefManager.writeString("password",password.getText().toString());
                            }
                            if(value.getString(2).equals("Admin")){
                                Intent intent = new Intent(MainActivity.this,AdminHome.class);
                                intent.putExtra("email", email.getText().toString());
                                startActivity(intent);                            }
                            else if(value.getString(2).equals("Student")){
                                Intent intent = new Intent(MainActivity.this,StudentHome.class);
                                intent.putExtra("email", email.getText().toString());
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(MainActivity.this,InstructorHome.class);
                                intent.putExtra("email", email.getText().toString());
                                startActivity(intent);                            }
                        }
                    }
                }
            }
        });
    }
    public void onNotRegisteredClick(View view) {
        Intent i = new Intent(MainActivity.this,SignUp.class);
        startActivity(i);
    }
}
