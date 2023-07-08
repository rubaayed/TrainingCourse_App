package com.example.trainingcourse_application.admin.viewprofiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.databinding.FragmentViewprofilesBinding;

public class ViewProfilesFragment extends Fragment {

    private FragmentViewprofilesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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
