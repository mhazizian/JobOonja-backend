/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.types;

public class Endorse {

    private String userIsEndorsed;
    private String skill;

    public String getUserIsEndorsed() {
        return userIsEndorsed;
    }

    public void setUserIsEndorsed(String userIsEndorsed) {
        this.userIsEndorsed = userIsEndorsed;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Endorse(String userIsEndorsed, String skill) {
        this.userIsEndorsed = userIsEndorsed;
        this.skill = skill;
    }

    public Endorse() {
    }

}
