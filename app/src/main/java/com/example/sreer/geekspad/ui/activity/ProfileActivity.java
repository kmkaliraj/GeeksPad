package com.example.sreer.geekspad.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sreer.geekspad.R;
import com.example.sreer.geekspad.ui.fragment.ProfileEditFragment;
import com.example.sreer.geekspad.ui.fragment.ProfileViewFragment;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FragmentManager fragments = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragments.beginTransaction();
        ProfileViewFragment profileEdit = new ProfileViewFragment();
        fragmentTransaction.replace(R.id.profileFragment, profileEdit);
        fragmentTransaction.commit();
    }
}
