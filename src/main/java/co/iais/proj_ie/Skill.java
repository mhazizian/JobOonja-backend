/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.iais.proj_ie;

import java.io.Serializable;

/**
 *
 * @author karam
 */
class Skill implements Serializable{
    private String name;
    private int points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Skill(String name, int points) {
        this.name = name;
        this.points = points;
    }
}
