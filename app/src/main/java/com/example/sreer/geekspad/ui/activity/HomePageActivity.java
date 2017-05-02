package com.example.sreer.geekspad.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.sreer.geekspad.R;
import com.example.sreer.geekspad.ui.fragment.ProfileViewFragment;

public class HomePageActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNav;
    private ProfileViewFragment userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.getMenu().getItem(1).setChecked(true);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
               handleMenuSelection(item);
                return true;
            }
        });
    }

    public void handleMenuSelection(MenuItem item){
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.detailFragment);
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.menu_home:
                    ProfileViewFragment userProfile = new ProfileViewFragment();
                    fragmentTransaction.replace(R.id.detailFragment, userProfile);
                    break;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.log_out:
                logout();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        logout();

    }

    public void logout(){
        startActivity(new Intent(this, LoginActivity.class)); //Go back to home page
        finish();
        Toast.makeText(this, "Logged Out Successfully" , Toast.LENGTH_LONG).show();
    }


}
