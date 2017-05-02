package com.example.sreer.geekspad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by sreer on 28-04-2017.
 */

public class User implements Serializable {

    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String birthDate;
    private String country;
    private String State;
    private String city;
    private List skills;


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
        State = state;
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
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List getSkills() {
        return skills;
    }

    public void setSkills(List skills) {
        this.skills = skills;
    }

    public String cleanEmailAddress(){
        return this.email.replace(".","-");
    }

}
