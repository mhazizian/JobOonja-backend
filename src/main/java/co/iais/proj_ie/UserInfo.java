/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.iais.proj_ie;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author karam
 */
public class UserInfo implements Serializable{
    private String username;
    private List<Skill> skills;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public UserInfo(String username, List<Skill> skills) {
        this.username = username;
        this.skills = skills;
    }
    
}
