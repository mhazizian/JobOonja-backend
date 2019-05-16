/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.types;

public class Endorse {

    private String userIsEndorsed;
    private String skill;
    private String endorser_id;

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

    public String getEndorser_id() {
        return endorser_id;
    }

    public void setEndorser_id(String endorser_id) {
        this.endorser_id = endorser_id;
    }

    public Endorse(String userIsEndorsed, String skill, String endorser_id) {
        this.userIsEndorsed = userIsEndorsed;
        this.skill = skill;
        this.endorser_id = endorser_id;
    }

}
