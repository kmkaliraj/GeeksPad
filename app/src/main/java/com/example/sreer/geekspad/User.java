package com.example.sreer.geekspad;

import java.util.Date;
import java.util.List;

/**
 * Created by sreer on 28-04-2017.
 */

public class User {

    private String mEmail;
    private String mPassword;
    private String mFirstName;
    private String mLastName;
    private String mGender;
    private long mPhone;
    private String mCountry;
    private String mState;
    private String mCity;
    private Date mBirthDate;
    private List mSkills;

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public long getmPhone() {
        return mPhone;
    }

    public void setmPhone(long mPhone) {
        this.mPhone = mPhone;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public Date getmBirthDate() {
        return mBirthDate;
    }

    public void setmBirthDate(Date mBirthDate) {
        this.mBirthDate = mBirthDate;
    }

    public List getmSkills() {
        return mSkills;
    }

    public void setmSkills(List mSkills) {
        this.mSkills = mSkills;
    }
}
