package com.example.trainingcourse_application.shared.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.MainActivity;
import com.example.trainingcourse_application.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}