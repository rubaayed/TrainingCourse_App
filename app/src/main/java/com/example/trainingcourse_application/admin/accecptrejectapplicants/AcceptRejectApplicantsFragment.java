package com.example.trainingcourse_application.admin.accecptrejectapplicants;



import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trainingcourse_application.DataBaseHelper;
import com.example.trainingcourse_application.R;
import com.example.trainingcourse_application.databinding.FragmentAcceptrejectapplicantsBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AcceptRejectApplicantsFragment extends Fragment {

    private FragmentAcceptrejectapplicantsBinding binding;

    ListView listView;
    ArrayList<String> applications;
    ArrayAdapter<String> adapter;
    TextView name;
    TextView email;
    TextView courseTitle;
    TextView sectionNumber;
    TextView NumberStudents;
    Switch acceptReject;
    Button submit;
    ScrollView scrollView;
    boolean flag = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAcceptrejectapplicantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.listViewAcceptAdmin);
        name = root.findViewById(R.id.studentNameAcceptAdmin);
        email = root.findViewById(R.id.studentEmailAcceptAdmin);
        courseTitle = root.findViewById(R.id.courseTitleAcceptAdmin);
        sectionNumber = root.findViewById(R.id.sectionNumberAcceptAdmin);
        NumberStudents = root.findViewById(R.id.noStudentsSectionAcceptAdmin);

        acceptReject = root.findViewById(R.id.switch1AcceptAdmin);
        submit = root.findViewById(R.id.submitAcceptAdmin);

        scrollView = root.findViewById(R.id.scrollView2CoursesStudent);


        scrollView.setVisibility(View.INVISIBLE);
        getAllInfo();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = applications.get(position);  // Retrieve the selected value
                InsertDataInTextView(value);
                scrollView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                flag = true;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag) {
                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());
                    if(acceptReject.isChecked()){
                        dataBaseHelper.acceptApplication("Enrollment",sectionNumber.getText().toString(),email.getText().toString(),
                                "STATUS","Reject","SECTION_ID","EMAIL");
                    }
                    else {
                        dataBaseHelper.acceptApplication("Enrollment",sectionNumber.getText().toString(),email.getText().toString(),
                                "STATUS","Accept","SECTION_ID","EMAIL");                    }
                    flag = false;
                    getAllInfo();
                    listView.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
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

    void getAllInfo(){
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());
        if(applications != null)
            applications.clear();
        else
            applications = new ArrayList<>();


        Cursor InfoForAcceptAndReject = dataBaseHelper.getWaitingStudents();
        while (InfoForAcceptAndReject.moveToNext()){
            String tmp = InfoForAcceptAndReject.getString(0);
            tmp += ": ";
            tmp += InfoForAcceptAndReject.getString(1);
            applications.add(tmp);
        }

        if(applications != null) {
            adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, applications);
            listView.setAdapter(adapter);
        }
    }

    void InsertDataInTextView(String info){
        String[] parts = info.split(":");

        String section_id = parts[0].trim();     // "id"
        String student_email = parts[1].trim();  // "email"

        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());

        Cursor SectionInfo = dataBaseHelper.getInfoForAcceptAndReject(section_id,student_email);
        SectionInfo.moveToNext();

        sectionNumber.setText(String.valueOf(SectionInfo.getString(0)));
        email.setText(String.valueOf(SectionInfo.getString(1)));
        String nameInstructor = SectionInfo.getString(2) + " " + SectionInfo.getString(3);
        name.setText(nameInstructor);
        courseTitle.setText(SectionInfo.getString(4));

        Cursor numStudents = dataBaseHelper.getStudentsNumber(section_id);
        numStudents.moveToNext();

        NumberStudents.setText(numStudents.getString(0));
    }
}