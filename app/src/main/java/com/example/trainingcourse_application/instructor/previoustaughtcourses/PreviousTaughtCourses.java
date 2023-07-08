package com.example.trainingcourse_application.instructor.previoustaughtcourses;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.databinding.FragmentPrevioustaughtcoursesBinding;

public class PreviousTaughtCourses extends Fragment {

    private FragmentPrevioustaughtcoursesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPrevioustaughtcoursesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}