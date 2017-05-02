package com.example.sreer.geekspad.ui.activity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sreer.geekspad.R;
import com.example.sreer.geekspad.ui.fragment.SkillsViewFragment;

public class SkillsActivity extends AppCompatActivity {

    private SkillsViewFragment skillsViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_skills);
            FragmentManager fragments = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragments.beginTransaction();
            skillsViewFragment = new SkillsViewFragment();
            fragmentTransaction.add(R.id.skills_fragment, skillsViewFragment);
            fragmentTransaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
