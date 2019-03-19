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
public class Skill implements Serializable{
    private String name;
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

    public Skill(String name, int points) {
        this.name = name;
        this.point = points;
    }
}
