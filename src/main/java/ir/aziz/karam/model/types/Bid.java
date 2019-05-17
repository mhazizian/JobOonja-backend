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
public class Bid implements Serializable{
    private int biddingUser;
    private String projectTitle;
    private int bidAmount;

    public Bid(int biddingUser, String projectTitle, int bidAmount) {
        this.biddingUser = biddingUser;
        this.projectTitle = projectTitle;
        this.bidAmount = bidAmount;
    }

    public int getBiddingUser() {
        return biddingUser;
    }

    public void setBiddingUser(int biddingUser) {
        this.biddingUser = biddingUser;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }
    
}
