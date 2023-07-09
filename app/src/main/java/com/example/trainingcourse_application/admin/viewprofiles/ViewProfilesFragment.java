package com.example.trainingcourse_application.admin.viewprofiles;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.R;
import com.example.trainingcourse_application.databinding.FragmentViewprofilesBinding;

public class ViewProfilesFragment extends Fragment {

    private FragmentViewprofilesBinding binding;
    private TextView textViewEmail;
    private TextView textViewFirstName;
    private TextView textViewLastName;
    private TextView textViewMobile;
    private TextView textViewAddress;
    private ImageView imageViewProfile;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_viewprofiles, container, false);

        textViewEmail = view.findViewById(R.id.Email);
        textViewFirstName = view.findViewById(R.id.FName);
        textViewLastName = view.findViewById(R.id.LName);
        textViewMobile = view.findViewById(R.id.Mobile);
        textViewAddress = view.findViewById(R.id.Address);
        imageViewProfile = view.findViewById(R.id.image_Ins);

        // Initialize the database helper
       //dbHelper = new DataBaseHelper(container);


        binding = FragmentViewprofilesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String[] options = {"Instructor", "Student"};
        final Spinner unitSpinner = binding.spinnerViewProfile;
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, options);
        unitSpinner.setAdapter(objGenderArr);

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Optional callback - handle when nothing is selected
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
