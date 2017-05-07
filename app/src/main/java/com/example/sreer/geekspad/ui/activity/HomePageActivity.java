package com.example.sreer.geekspad.ui.activity;


import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.sreer.geekspad.R;
import com.example.sreer.geekspad.ui.fragment.ChatUsersListFragment;
import com.example.sreer.geekspad.ui.fragment.DisplayMapFragment;
import com.example.sreer.geekspad.ui.fragment.ProfileViewFragment;
import com.example.sreer.geekspad.ui.fragment.UsersListViewFragment;

public class HomePageActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNav;
    private ProfileViewFragment userProfile;
    private UsersListViewFragment usersList;
    private DisplayMapFragment usersMap;
    private ChatUsersListFragment chatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.detailFragment);
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        usersList = new UsersListViewFragment();
        fragmentTransaction.replace(R.id.detailFragment, usersList);
        fragmentTransaction.commit();

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

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.detailFragment);
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        switch (item.getItemId()) {
            case R.id.menu_home:
                if(!(fragment instanceof ProfileViewFragment)) {
                    ProfileViewFragment userProfile = new ProfileViewFragment();
                    fragmentTransaction.replace(R.id.detailFragment, userProfile);
                }
                    break;
            case R.id.menu_list:
                if(!(fragment instanceof UsersListViewFragment)) {
                    usersList = new UsersListViewFragment();
                    fragmentTransaction.replace(R.id.detailFragment, usersList);
                }
                     break;
            case R.id.menu_map:
                if(!(fragment instanceof DisplayMapFragment)) {
                    usersMap = new DisplayMapFragment();
                    fragmentTransaction.replace(R.id.detailFragment, usersMap);
                }
                break;
            case R.id.menu_chat:
                if(!(fragment instanceof ChatUsersListFragment)) {
                    chatView = new ChatUsersListFragment();
                    fragmentTransaction.replace(R.id.detailFragment, chatView);
                }
                break;
        }

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


    public void profileRecall(){

    }


}
