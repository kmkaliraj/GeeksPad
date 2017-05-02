package com.example.sreer.geekspad.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sreer.geekspad.R;
import com.example.sreer.geekspad.model.User;
import com.example.sreer.geekspad.utils.Constants;
import com.example.sreer.geekspad.utils.SpinnerUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;


public class SignupActivity extends AppCompatActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mPassword;
    private EditText mBirthDay;
    private Spinner mCountry;
    private Spinner mState;
    private EditText mCity;
    private Button mSubmitButton;
    private TextView mCalendarLink;

    private FirebaseAuth auth;
    public ProgressDialog progressDailog;
    private String country;
    private String state;
    boolean firbaseStatus = true;

    private  List<String> states = new ArrayList<String>(){{
        add("Select State(None)");
    }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Register Here");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirstName = (EditText) findViewById(R.id.fname);
        mLastName = (EditText) findViewById(R.id.lname);
        mPassword = (EditText) findViewById(R.id.input_password);
        mBirthDay = (EditText) findViewById(R.id.input_year);
        mCountry = (Spinner) findViewById(R.id.input_country);
        mState = (Spinner)  findViewById(R.id.input_state);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPhone = (EditText) findViewById(R.id.input_phone);
        mCity  = (EditText) findViewById(R.id.input_city);
        mCalendarLink = (TextView) findViewById(R.id.link_calendar);
        mSubmitButton = (Button) findViewById(R.id.btn_add_skills);

        progressDailog = new ProgressDialog(this);
        progressDailog.setMessage("Loading");
        progressDailog.getWindow().
                setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDailog.setCancelable(false);

        auth = FirebaseAuth.getInstance();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postUserDetails();
            }
        });
        mCalendarLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBirthday();
            }
        });

        mCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                country = (String)adapterView.getAdapter().getItem(position);
                state = null;
                populateStateDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        mState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                state = (String)adapterView.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        populateCountryData();
        populateStateDetails();

    }

    public void postUserDetails(){
        goToSkillsView();
        /*
        if(isEmptyFirstName())
            mFirstName.setError("FirstName is required!");
        else if(!isValidEmail())
            mEmail.setError("Must enter a valid email");
        else if(!isValidPassword())
            mPassword.setError("Must enter password of minimum 6 character");
        else if(!isValidBirthday())
            mBirthDay.setError("Must enter valid date");
        else if(!isValidCountry()) {
            TextView errorText = (TextView)mCountry.getSelectedView();
            errorText.setError("Country is Required");
            errorText.setTextColor(Color.RED);
        }
        else if(!isValidState()){
            TextView errorText = (TextView)mState.getSelectedView();
            errorText.setError("State is Required");
            errorText.setTextColor(Color.RED);
        }
        else {
          signupUserwithFireBase();
        } */

    }


    public void addUserToFirebase(FirebaseUser firebaseuser){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        User user = createUser();

        try {
            database.child(Constants.ARG_USERS)
                    .child(user.cleanEmailAddress())
                    .setValue(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                goToSkillsView();
                            } else {
                                Toast.makeText(getApplicationContext(), "Adding user details to firebase failed",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }



    public boolean isValidEmail(){
        String email = mEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern) && email.length() > 0;

    }

    public void goToHomePage(){
        Intent userView = new Intent(this, HomePageActivity.class);
        startActivity(userView);
        finish();
    }

    public void goToSkillsView(){
        Intent skillsView = new Intent(this, SkillsActivity.class);
        startActivity(skillsView);
        finish();
    }

    public boolean signupUserwithFireBase(){

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            addUserToFirebase(task.getResult().getUser());

                            Log.i("User Details:", "Username and password are added to firbase");
                            //finish();
                        } else {
                            Toast.makeText(SignupActivity.this, "Creation User Account Failed" + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            firbaseStatus = false;
                            Log.w("Authentication Failed", "User Details are not added to firbase:  "+ task.getException().getMessage());
                        }
                    }
                });
        return firbaseStatus;
    }

    public boolean isValidCity(){
        return mCity.getText().toString().length() > 0 ;
    }

    public boolean isEmptyFirstName(){
        return this.mFirstName.getText().toString().length()==0;
    }
    public boolean isEmptyLastName(){
        return this.mFirstName.getText().toString().length()==0;
    }

    public boolean isUserEmailExist(){
        return true;
    }

    public boolean isValidPassword(){
        return mPassword.getText().toString().length() >=6;
    }

    public boolean isValidBirthday(){
        return true;
    }

    public boolean isValidCountry(){
        return !(mCountry.getSelectedItem().toString().contains("none"));
    }

    public boolean isValidState(){
        return !(mState.getSelectedItem().toString().contains("none"));
    }


    public void setBirthday(){
        Intent datePicker = new Intent(this, DatePickActivity.class);
        startActivityForResult(datePicker,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("rew", "Back to Home - " + requestCode+ " " +resultCode);

        if (requestCode == 1 && resultCode == RESULT_OK){
            String birth_day = data.getStringExtra("date");
            mBirthDay.setText(birth_day);
        }
    }


    public void populateCountryData(){
        ArrayAdapter countryAdapter = ArrayAdapter.createFromResource(this,
                R.array.countries, R.layout.spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountry.setAdapter(countryAdapter);

    }

    public void populateStateDetails(){
        states.clear();
        states.add("Select State(None)");
        states.addAll(SpinnerUtil.getStates(this,country));
        ArrayAdapter<String> statesAdapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, states);
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mState.setAdapter(statesAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public User createUser(){
       User user = new User(mFirstName.getText().toString(),mEmail.getText().toString(),country,state);

        if(mLastName.getText().toString().length()>0)
            user.setFname(mLastName.getText().toString());

        if(mPhone.getText().toString().length()>0)
           user.setPhone(mPhone.getText().toString());

        if(mBirthDay.getText().toString().length()>0)
            user.setBirthDate(mBirthDay.getText().toString());

        if(mCity.getText().toString().length()> 0)
            user.setCity(mCity.getText().toString());

        return user;
    }
}
