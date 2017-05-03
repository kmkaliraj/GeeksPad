package com.example.sreer.geekspad.utils;

import com.example.sreer.geekspad.model.Skill;
import com.example.sreer.geekspad.model.User;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sreer on 03-05-2017.
 */

public class FireBaseHelper {
    public static User DataProcessor(DataSnapshot dataSnapshot){
        User user = new User();
        if(dataSnapshot.hasChild("skills")) {
            DataSnapshot skillSnapshot = dataSnapshot.child("skills");
            for (DataSnapshot skillItem : skillSnapshot.getChildren()) {
                Skill skill = skillItem.getValue(Skill.class);
                user.addSkill(skill);
            }
            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                String dataKey = userSnapshot.getKey();
                if (dataKey.equals("fname"))
                    user.setFname(String.valueOf(userSnapshot.getValue()));
                if (dataKey.equals("lname"))
                    user.setLname(String.valueOf(userSnapshot.getValue()));
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
}
