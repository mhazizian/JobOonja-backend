/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.types;

public class ProjectDetails {

    private boolean hasBided;
    private int currentID;

    public boolean isHasBided() {
        return hasBided;
    }

    public void setHasBided(boolean hasBided) {
        this.hasBided = hasBided;
    }

    public int getCurrentID() {
        return currentID;
    }

    public void setCurrentID(int currentID) {
        this.currentID = currentID;
    }

    public ProjectDetails(boolean hasBided, int currentID) {
        this.hasBided = hasBided;
        this.currentID = currentID;
    }

}
