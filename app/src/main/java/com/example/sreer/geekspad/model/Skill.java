package com.example.sreer.geekspad.model;

import java.io.Serializable;

/**
 * Created by kalirajkalimuthu on 5/1/17.
 */

public class Skill implements Serializable{

    public String skillname;
    public String proficency;

    public Skill(String skillname, String proficency) {
        this.skillname = skillname;
        this.proficency = proficency;
    }

    public String getSkillname() {
        return skillname;
    }

    public void setSkillname(String skillname) {
        this.skillname = skillname;
    }

    public String getProficency() {
        return proficency;
    }

    public void setProficency(String proficency) {
        this.proficency = proficency;
    }



}
