package com.example.sreer.geekspad.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sreer.geekspad.R;
import com.example.sreer.geekspad.model.Skill;
import com.example.sreer.geekspad.model.User;
import com.example.sreer.geekspad.ui.activity.ProfileEditActivity;
import com.example.sreer.geekspad.ui.adapter.SkillSetRecyclerAdapter;
import com.example.sreer.geekspad.utils.FireBaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileViewFragment extends Fragment {
    private TextView mProfileLine1, mProfileLine2, mProfileLine3;
    private ImageView mEditIcon;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRefDatabase;
    private ProgressDialog mProgress;
    private RecyclerView mSkills;


    public ProfileViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);
        mProfileLine1 = (TextView) view.findViewById(R.id.TextView_line1);
        mProfileLine2 = (TextView) view.findViewById(R.id.TextView_line2);
        mProfileLine3 = (TextView) view.findViewById(R.id.TextView_line3);
        mAuth = FirebaseAuth.getInstance();
        mEditIcon = (ImageView) view.findViewById(R.id.imageView_edit_icon);
        mUserRefDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mProgress = new ProgressDialog(getActivity());
        mSkills = (RecyclerView) view.findViewById(R.id.recycler_profile_skills);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Profile");
        mProgress.setMessage("Loading...");
        mProgress.show();
        getUserInfo();
        mEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
    }

    public void getUserInfo() {
        User user = new User();
        if (mAuth.getCurrentUser() != null) {
        user.setEmail(mAuth.getCurrentUser().getEmail());
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(user.cleanEmailAddress())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            User user = FireBaseHelper.DataProcessor(dataSnapshot);
                            mProfileLine1.setText(user.getFname()+" "+user.getLname());
                            mProfileLine2.setText("Lives in "+user.getCity()+", "+user.getState()+", "+user.getCountry());
                            mProfileLine3.setText("Email: "+user.getEmail()+"\n"+"Phone: "+user.getPhone());
                            displaySkills(user);
                            mProgress.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(),"Loading Failed",Toast.LENGTH_SHORT);
                        mProgress.dismiss();
                    }
                });
    }
}

    public void editProfile(){
        Intent editProfile = new Intent(getActivity(), ProfileEditActivity.class);
        startActivity(editProfile);
    }

    public void displaySkills(User user){
        List<Skill> skillList = new ArrayList<>();
        skillList.addAll(user.getSkills().values());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mSkills.setLayoutManager(mLayoutManager);
        SkillSetRecyclerAdapter skillSetRecyclerAdapter = new SkillSetRecyclerAdapter(skillList);
        mSkills.setAdapter(skillSetRecyclerAdapter);
    }
}

