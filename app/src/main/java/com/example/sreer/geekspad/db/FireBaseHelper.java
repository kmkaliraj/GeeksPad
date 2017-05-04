package com.example.sreer.geekspad.db;

import com.example.sreer.geekspad.model.Skill;
import com.example.sreer.geekspad.model.User;
import com.example.sreer.geekspad.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sreer on 03-05-2017.
 */

public class FireBaseHelper {

    private  GetAllUsersInterface getAllUsersInterface;

    public FireBaseHelper(){

    }

    public FireBaseHelper(GetAllUsersInterface getAllUsersInterface){
        this.getAllUsersInterface = getAllUsersInterface;
    }




    public  static  User getUserFromSnapShot(DataSnapshot dataSnapshot){

        User user = new User();
        if(dataSnapshot.hasChild("skills")) {
            DataSnapshot skillSnapshot = dataSnapshot.child("skills");
            for (DataSnapshot skillItem : skillSnapshot.getChildren()) {
                Skill skill = skillItem.getValue(Skill.class);
                user.addSkill(skill);
            }
            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                String dataKey = userSnapshot.getKey();
                if (dataKey.equals("firstname"))
                    user.setFirstname(String.valueOf(userSnapshot.getValue()));
                if (dataKey.equals("lastname"))
                    user.setLastname(String.valueOf(userSnapshot.getValue()));
                if (dataKey.equals("email"))
                    user.setEmail(String.valueOf(userSnapshot.getValue()));
                if (dataKey.equals("phone"))
                    user.setPhone(String.valueOf(userSnapshot.getValue()));
                if (dataKey.equals("birthDate"))
                    user.setBirthDate(String.valueOf(userSnapshot.getValue()));
                if (dataKey.equals("country"))
                    user.setCountry(String.valueOf(userSnapshot.getValue()));
                if (dataKey.equals("state"))
                    user.setState(String.valueOf(userSnapshot.getValue()));
                if (dataKey.equals("city"))
                    user.setCity(String.valueOf(userSnapshot.getValue()));
            }
        }
        else {
            user = dataSnapshot.getValue(User.class);
        }
        return  user;
    }


    public  List<User> getAllUsers(){


        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> allUsers = new ArrayList<User>();
                if (dataSnapshot.getValue() != null) {
                   for(DataSnapshot userDataSnapshot: dataSnapshot.getChildren())
                       allUsers.add(getUserFromSnapShot(userDataSnapshot));
                    getAllUsersInterface.onSuccessGetAllUsers(allUsers);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               getAllUsersInterface.onFailureGetAllUsers();
            }
        });

        return null;
    }

    /**
     * Created by kalirajkalimuthu on 5/3/17.
     */

    public static interface GetAllUsersInterface {
        public void onSuccessGetAllUsers(List<User> users);
        public void onFailureGetAllUsers();
    }
}
