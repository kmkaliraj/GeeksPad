package com.example.sreer.geekspad.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sreer.geekspad.R;
import com.example.sreer.geekspad.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileViewFragment extends Fragment {
    private TextView mProfileLine1, mProfileLine2;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRefDatabase;
    private ProgressDialog progress;
    private Button mEdit;


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
        mAuth = FirebaseAuth.getInstance();
        mEdit = (Button) view.findViewById(R.id.Button_Edit);
        mUserRefDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        progress = new ProgressDialog(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Profile");
        progress.setMessage("Loading...");
        progress.show();
        getUserInfo();
        mEdit.setOnClickListener(new View.OnClickListener() {
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
                            User user = dataSnapshot.getValue(User.class);
                            mProfileLine1.setText(user.getFname()+" "+user.getLname());
                            mProfileLine2.setText("Lives in "+user.getCity()+", "+user.getState()+", "+user.getCountry());
                            progress.dismiss();
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

    public void editProfile(){
        FragmentManager fragments = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragments.beginTransaction();
        ProfileEditFragment profileEdit = new ProfileEditFragment();
        fragmentTransaction.replace(R.id.detailFragment, profileEdit);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

