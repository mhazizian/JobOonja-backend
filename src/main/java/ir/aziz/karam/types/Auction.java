/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.types;

import java.io.Serializable;

/**
 *
 * @author karam
 */
public class Auction implements Serializable{
    private String projectTitle;

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public Auction(String projectTitle) {
        this.projectTitle = projectTitle;
    }
    
}
