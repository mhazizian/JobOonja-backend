/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.types;

public class Endorse {

    private int userIsEndorsed;
    private String skill;
    private int endorser_id;

    public int getUserIsEndorsed() {
        return userIsEndorsed;
    }

    public void setUserIsEndorsed(int userIsEndorsed) {
        this.userIsEndorsed = userIsEndorsed;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Endorse(int userIsEndorsed, String skill) {
        this.userIsEndorsed = userIsEndorsed;
        this.skill = skill;
    }

    public Endorse() {
    }

    public int getEndorser_id() {
        return endorser_id;
    }

    public void setEndorser_id(int endorser_id) {
        this.endorser_id = endorser_id;
    }

    public Endorse(int userIsEndorsed, String skill, int endorser_id) {
        this.userIsEndorsed = userIsEndorsed;
        this.skill = skill;
        this.endorser_id = endorser_id;
    }

}
