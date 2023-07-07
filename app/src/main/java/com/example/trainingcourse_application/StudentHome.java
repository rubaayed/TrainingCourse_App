package com.example.trainingcourse_application;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trainingcourse_application.databinding.ActivityStudentHomeBinding;

public class StudentHome extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityStudentHomeBinding binding;

    Uri imageUri;
    byte[] imageBytes;
    ImageView imageView;
    TextView StudentName;
    TextView StudentEmail;

    int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStudentHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String email = getIntent().getStringExtra("email");
        StudentHomeEmail.setEmailAddress(email);

        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(StudentHome.this);
        Cursor studentInfo = dataBaseHelper.getStudentInfo(email);

        studentInfo.moveToFirst();






        setSupportActionBar(binding.appBarStudentHome.toolbar);
        binding.appBarStudentHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View headerView = navigationView.getHeaderView(0);
        imageView = headerView.findViewById(R.id.imageViewHeader);
        StudentName = headerView.findViewById(R.id.nameHeader);
        StudentEmail = headerView.findViewById(R.id.emailHeader);

        String name = studentInfo.getString(0) + " " + studentInfo.getString(1);

        imageBytes = studentInfo.getBlob(5);
        if(imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(bitmap);
        }
        else {
            Toast.makeText(StudentHome.this, ""+studentInfo.getBlob(5), Toast.LENGTH_SHORT).show();
        }
        StudentName.setText(name);
        StudentEmail.setText(email);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home,R.id.notifications,R.id.courses,R.id.studiedCourses,R.id.coursesHistory,
                R.id.editProfile,R.id.logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_student_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_student_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}