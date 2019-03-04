/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.aziz.karam.model.types;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author karam
 */
public class Project {

    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private List<Skill> skills;
    private List<Bid> bids;
    private int budget;
    private long deadline;
    private User winner;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescrption() {
        return description;
    }

    public void setDescrption(String descrption) {
        this.description = descrption;
    }

    public String getImageURL() {
        return imageUrl;
    }

    public void setImageURL(String imageURL) {
        this.imageUrl = imageURL;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(Bid bid) {
        if (this.bids == null)
            this.bids = new ArrayList<>();
        this.bids.add(bid);
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public Project(String title, List<Skill> skills, int budget) {
        this.title = title;
        this.skills = skills;
        this.budget = budget;
        this.bids = new ArrayList<>();
    }

    public boolean hasBided(User user) {
        if (this.bids == null)
            return false;

        for (int i = 0; i < this.bids.size(); i++) {
            if (this.bids.get(i).getBiddingUser().equals(user.getId())) {
               return true;
            }
        }
        return false;
    }
}