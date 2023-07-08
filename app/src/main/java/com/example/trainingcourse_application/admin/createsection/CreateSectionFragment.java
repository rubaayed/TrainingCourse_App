package com.example.trainingcourse_application.admin.createsection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.R;
import com.example.trainingcourse_application.databinding.FragmentCreatesectionBinding;

public class CreateSectionFragment extends Fragment {

    private FragmentCreatesectionBinding binding;

    String[] items = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    private Spinner day;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCreatesectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        day = root.findViewById(R.id.dayAdmin);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}