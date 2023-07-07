package com.example.trainingcourse_application.student.editprofile;



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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.DataBaseHelper;
import com.example.trainingcourse_application.R;
import com.example.trainingcourse_application.StudentHomeEmail;
import com.example.trainingcourse_application.databinding.FragmentEditprofileBinding;
import com.example.trainingcourse_application.validation;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditprofileFragment extends Fragment {

    private FragmentEditprofileBinding binding;

    private static final int PICK_IMAGE_REQUEST = 1;  // Request code for image selection
    Uri imageUri = null;
    byte[] imageBytes;

    private Button edit;
    private Button selectImageButton;
    boolean chosenImageFlag = false;

    private EditText firstName;
    private EditText lastName;
    private EditText mobile;
    private EditText address;
    private EditText password;
    private EditText confirmPassword;

    private boolean flag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEditprofileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String email = StudentHomeEmail.getEmailAddress();

        final int purple = ContextCompat.getColor(getContext(), R.color.purple);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.custom_edittext);
        drawable.setStroke(2, Color.RED);

        // Create a new instance of the custom drawable with the desired stroke color
        final GradientDrawable drawableOri = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.custom_edittext);
        drawableOri.setStroke(2, purple);

        selectImageButton = root.findViewById(R.id.selectImageEditProfileStudent);
        edit = root.findViewById(R.id.editEditProfileStudent);

        firstName = root.findViewById(R.id.firstNameEditProfileStudent);
        lastName = root.findViewById(R.id.lastNameEditProfileStudent);
        mobile = root.findViewById(R.id.mobileEditProfileStudent);
        address = root.findViewById(R.id.addressEditProfileStudent);
        password = root.findViewById(R.id.passwordEditProfileStudent);
        confirmPassword = root.findViewById(R.id.passwordConfirmEditProfileStudent);

        fillValues(email);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
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
                    Toast.makeText(getActivity(), "Edit Failed! Check the red fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());

                    if(chosenImageFlag){
                        dataBaseHelper.editDataImage("Student", email,"IMAGE",imageBytes,"EMAIL");
                    }
                    dataBaseHelper.editData("Student", email,"FIRST_NAME",firstName.getText().toString(),"EMAIL");
                    dataBaseHelper.editData("Student", email,"LAST_NAME",lastName.getText().toString(),"EMAIL");
                    dataBaseHelper.editData("Student", email,"MOBILE",mobile.getText().toString(),"EMAIL");
                    dataBaseHelper.editData("Student", email,"ADDRESS",address.getText().toString(),"EMAIL");
                    dataBaseHelper.editData("USER", email,"PASSWORD",password.getText().toString(),"EMAIL");

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

        Cursor allSectionsCursor = dataBaseHelper.getStudentInfo(email);
        allSectionsCursor.moveToNext();

        firstName.setText(allSectionsCursor.getString(0));
        lastName.setText(allSectionsCursor.getString(1));
        mobile.setText(allSectionsCursor.getString(2));
        address.setText(allSectionsCursor.getString(3));
        password.setText(allSectionsCursor.getString(4));
        confirmPassword.setText(allSectionsCursor.getString(4));
    }
}