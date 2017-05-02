package com.example.sreer.geekspad.ui.activity;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sreer.geekspad.R;

public class HomePageActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.getMenu().getItem(1).setChecked(true);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
              //  handleMenuSelection(item);
                return true;
            }
        });
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
