/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.types;

import java.util.List;

public class UserDetails {

    private String currentID;
    private List<Skill> otherSkills;
    private List<String> endorseSkills;

    public String getCurrentID() {
        return currentID;
    }

    public void setCurrentID(String currentID) {
        this.currentID = currentID;
    }

    public List<Skill> getOtherSkills() {
        return otherSkills;
    }

    public void setOtherSkills(List<Skill> otherSkills) {
        this.otherSkills = otherSkills;
    }

    public List<String> getEndorseSkills() {
        return endorseSkills;
    }

    public void setEndorseSkills(List<String> endorseSkills) {
        this.endorseSkills = endorseSkills;
    }

    public UserDetails(String currentID, List<Skill> otherSkills, List<String> endorseSkills) {
        this.currentID = currentID;
        this.otherSkills = otherSkills;
        this.endorseSkills = endorseSkills;
    }

}
