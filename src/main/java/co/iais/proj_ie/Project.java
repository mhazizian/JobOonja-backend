/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.iais.proj_ie;

import java.util.List;

/**
 *
 * @author karam
 */
public class Project {
    private String title;
    private List<Skill> skills;
    private int budget;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Project(String title, List<Skill> skills, int budget) {
        this.title = title;
        this.skills = skills;
        this.budget = budget;
    }
}
