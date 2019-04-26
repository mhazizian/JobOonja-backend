/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.types;

import ir.aziz.karam.model.dataLayer.dataMappers.skillUser.SkillUserMapper;
import ir.aziz.karam.model.exception.SkillNotFoundException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author karam
 */
public class User implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String PictureUrl;
    private List<SkillUser> skills;
    private String bio;
    private List<Endorse> endorses = new ArrayList<>();

    public List<SkillUser> getSkills() throws SQLException {
        this.skills = SkillUserMapper.getInstance().getSkillOfUser(this);

        return this.skills;
    }

    public void setSkills(List<SkillUser> skills) {
        this.skills = skills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPictureUrl() {
        return PictureUrl;
    }

    public void setPictureUrl(String PictureUrl) {
        this.PictureUrl = PictureUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Endorse> getEndorses() {
        return endorses;
    }

    public void setEndorses(List<Endorse> endorses) {
        this.endorses = endorses;
    }

    public void addEndorses(Endorse endorse) {
        this.endorses.add(endorse);
    }

    public SkillUser getUserSkillByName(String skillName) throws SkillNotFoundException{
        try {
            return SkillUserMapper.getInstance().find(this.getId(), skillName);

        } catch (SQLException e) {
            throw new SkillNotFoundException(skillName + " skill not found!");
        }
    }

    public User(String id, String firstName, String lastName, String jobTitle, String PictureUrl, List<SkillUser> skills, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.PictureUrl = PictureUrl;
        this.skills = skills;
        this.bio = bio;
    }

    public User(String id, String firstName, String lastName, String jobTitle, String PictureUrl, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.PictureUrl = PictureUrl;
        this.bio = bio;
    }

}
