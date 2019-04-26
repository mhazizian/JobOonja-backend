/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.types;

import java.io.Serializable;

/**
 *
 * @author karam
 */
public class SkillUser implements Serializable{
    private String name; // skill name
    private String userId;
    private int point;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return point;
    }

    public void setPoints(int points) {
        this.point = points;
    }

    public SkillUser(String name, int points) {
        this.name = name;
        this.point = points;
    }

    public SkillUser(String name,String userId, int points) {
        this.name = name;
        this.userId = userId;
        this.point = points;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
