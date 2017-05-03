package com.example.sreer.geekspad.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sreer.geekspad.R;
import com.example.sreer.geekspad.model.User;
import com.example.sreer.geekspad.ui.activity.DatePickActivity;
import com.example.sreer.geekspad.utils.Constants;
import com.example.sreer.geekspad.utils.FireBaseHelper;
import com.example.sreer.geekspad.utils.SpinnerUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class ProfileEditFragment extends Fragment {
    private Button mSave;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mPassword;
    private EditText mBirthDay;
    private Spinner mCountry;
    private Spinner mState;
    private EditText mCity;
    private TextView mCalendarLink;
    private FirebaseAuth mAuth;
    private String country;
    private String state;
    private ProgressDialog progress;
    private DatabaseReference mUserRefDatabase;
    private List<String> countries = new ArrayList<String>(){{
        add("Select Country(None)");
    }};
    private  List<String> states = new ArrayList<String>(){{
        add("Select State(None)");
    }};

    public ProfileEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_signup, container, false);
        mUserRefDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(getActivity());
        mSave = (Button) view.findViewById(R.id.btn_add_skills);
        mFirstName = (EditText) view.findViewById(R.id.fname);
        mLastName = (EditText) view.findViewById(R.id.lname);
        mPassword = (EditText) view.findViewById(R.id.input_password);
        mBirthDay = (EditText) view.findViewById(R.id.input_year);
        mCountry = (Spinner) view.findViewById(R.id.input_country);
        mState = (Spinner)  view.findViewById(R.id.input_state);
        mEmail = (EditText) view.findViewById(R.id.input_email);
        mPhone = (EditText) view.findViewById(R.id.input_phone);
        mCity  = (EditText) view.findViewById(R.id.input_city);
        mCalendarLink = (TextView) view.findViewById(R.id.link_calendar);
        mSave.setText("Save");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Edit Profile");
        populateCountryData();
        progress.setMessage("Loading...");
        progress.show();
        getUserInfo();
        mCalendarLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBirthday();
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postUserDetails();
            }
        });

    }

    public void setupSpinners(){
        mCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                country = (String)adapterView.getAdapter().getItem(position);
                state = null;
                populateStateDetails(null);
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
    }

    public void setBirthday(){
        Intent datePicker = new Intent(getActivity(), DatePickActivity.class);
        startActivityForResult(datePicker,1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("rew", "Back to Home - " + requestCode+ " " +resultCode);

        if (requestCode == 1 && resultCode == RESULT_OK){
            String birth_day = data.getStringExtra("date");
            mBirthDay.setText(birth_day);
        }
    }

    public void populateCountryData(){

        ArrayAdapter countryAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.countries, R.layout.spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountry.setAdapter(countryAdapter);

    }

    public void populateStateDetails(String state){
        states.clear();
        states.add("Select State(None)");
        states.addAll(SpinnerUtil.getStates(getActivity(),country));
        ArrayAdapter<String> statesAdapter=new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, states);
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mState.setAdapter(statesAdapter);
        if(state != null )
            mState.setSelection(getIndex(mState,state));

    }

    public void postUserDetails(){

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
            updateUserInFirebase(mAuth.getCurrentUser());
        }

    }

    public boolean isValidEmail(){
        String email = mEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern) && email.length() > 0;

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

    public void updateUserInFirebase(FirebaseUser firebaseuser){
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
                                goToProfile();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Adding user details to firebase failed",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public User createUser(){
        User user = new User(mFirstName.getText().toString(),mEmail.getText().toString(),country,state);

        if(mLastName.getText().toString().length()>0)
            user.setLname(mLastName.getText().toString());

        if(mPhone.getText().toString().length()>0)
            user.setPhone(mPhone.getText().toString());

        if(mBirthDay.getText().toString().length()>0)
            user.setBirthDate(mBirthDay.getText().toString());

        if(mCity.getText().toString().length()> 0)
            user.setCity(mCity.getText().toString());

        return user;
    }

    public void goToProfile(){
        FragmentManager fragments = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragments.beginTransaction();
        ProfileViewFragment profile = new ProfileViewFragment();
        fragmentTransaction.replace(R.id.detailFragment, profile);
        fragmentTransaction.commit();
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
                                mCountry.setSelection(getIndex(mCountry,user.getCountry()));
                                mFirstName.setText(user.getFname());
                                mBirthDay.setText(user.getBirthDate());
                                mLastName.setText(user.getLname());
                                mEmail.setText(user.getEmail());
                                mPhone.setText(user.getPhone());
                                country = user.getCountry();
                                populateStateDetails(user.getState());
                                mCity.setText(user.getCity());
                                progress.dismiss();
                                setupSpinners();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getActivity(),"Loading Failed",Toast.LENGTH_SHORT);
                            progress.dismiss();
                        }
                    });
        }
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

}
