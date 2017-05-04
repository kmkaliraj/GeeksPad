package com.example.sreer.geekspad.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sreer.geekspad.R;
import com.example.sreer.geekspad.ui.fragment.ProfileEditFragment;

public class ProfileEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        FragmentManager fragments = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragments.beginTransaction();
        ProfileEditFragment profileEdit = new ProfileEditFragment();
        fragmentTransaction.replace(R.id.profileEdit, profileEdit);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
