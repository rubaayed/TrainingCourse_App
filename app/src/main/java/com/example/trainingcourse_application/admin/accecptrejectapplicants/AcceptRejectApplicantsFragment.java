package com.example.trainingcourse_application.admin.accecptrejectapplicants;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.databinding.FragmentAcceptrejectapplicantsBinding;

public class AcceptRejectApplicantsFragment extends Fragment {

    private FragmentAcceptrejectapplicantsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAcceptrejectapplicantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}