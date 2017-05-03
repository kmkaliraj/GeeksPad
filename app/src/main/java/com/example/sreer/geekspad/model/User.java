package com.example.sreer.geekspad.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sreer on 28-04-2017.
 */

public class User implements Parcelable {

    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String birthDate;
    private String country;
    private String state;
    private String city;
    private Map<String, Skill> skills;


    public User(){

    }

    public User(String fname, String email) {
        this.fname = fname;
        this.email = email;

    }


    public User(String fname, String email, String country, String state) {
        this.fname = fname;
        this.email = email;
        this.country = country;
        state = state;
    }

    public User(Parcel in){
        String[] data = new String[8];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.fname = data[0];
        this.lname = data[1];
        this.email = data[2];
        this.phone= data[3];
        this.birthDate = data[4];
        this.country = data[5];
        this.state = data[6];
        this.city = data[7];
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Map getSkills() {
        return skills;
    }

    public void setSkills(Map skills) {
        this.skills = skills;
    }

    public void addSkill(Skill skill){
        if(skills==null)
            skills = new LinkedHashMap<>();
        skills.put(skill.skillname, skill);
    }

    public  void removeSkill(Skill skill){
        if(skills !=null){
            skills.remove(skill.skillname);
        }
    }
    public String cleanEmailAddress(){
        return this.email.replace(".","-");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.fname,
                this.lname,
                this.email,
                this.phone,
                this.birthDate,
                this.country,
                this.state,
                this.city});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };


}
